package org.gmjm.slack.core.model;

import java.util.Map;

import org.gmjm.slack.api.model.SlackCommand;

public class SlackRequestFactoryMapImpl implements SlackRequestFactory {

	@Override
	public SlackCommand create(Map<String, String> requestParameters) {
		return new SlackCommandMapImpl(requestParameters);
	}
}
