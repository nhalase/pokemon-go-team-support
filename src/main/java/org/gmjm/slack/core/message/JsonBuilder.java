package org.gmjm.slack.core.message;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

class JsonBuilder implements BuilderBackingMap {

	private ObjectMapper om = new ObjectMapper();

	protected Map<String, Object> fields = new HashMap<>();
	private Set<String> markdownEnabledFields = new HashSet<String>();

	public JsonBuilder() {
		fields.put("mrkdwn_in", markdownEnabledFields);
	}

	public JsonBuilder(boolean allowMarkdown) {
		if (allowMarkdown) {
			fields.put("mrkdwn_in", markdownEnabledFields);
		}
	}

	public String buildJsonString() throws JsonProcessingException {
		return om.writeValueAsString(this.fields);
	}

	protected void setField(String fieldName, String fieldText, boolean markdownEnabled) {
		fields.put(fieldName, fieldText);
		if (markdownEnabled) {
			markdownEnabledFields.add(fieldName);
		}
	}

	@Override
	public Map<String, Object> getBackingMap() {
		return fields;
	}
}
