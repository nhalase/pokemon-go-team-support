package org.gmjm.slack.core.message;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.gmjm.slack.api.message.AttachmentBuilder;
import org.gmjm.slack.api.message.SlackMessageBuilder;

public class SlackMessageBuilderJsonImpl extends JsonBuilder implements SlackMessageBuilder {

	List<Map<String, Object>> attachments = new ArrayList<>();

	public SlackMessageBuilderJsonImpl() {
		super(false);
		fields.put("attachments", attachments);
	}

	/**
	 * Sets text, defaulted to use markdown.
	 *
	 * @param text
	 */
	@Override
	public SlackMessageBuilder setText(String text) {
		setText(text, true);
		return this;
	}

	@Override
	public SlackMessageBuilder setIconEmoji(String iconEmoji) {
		setField("icon_emoji", ":" + iconEmoji + ":", false);
		return this;
	}

	@Override
	public SlackMessageBuilder setIconUrl(String iconUrl) {
		setField("icon_url", iconUrl, false);
		return this;
	}

	@Override
	public SlackMessageBuilder setText(String text, boolean markdownEnabled) {
		setField("text", text, markdownEnabled);
		return this;
	}

	@Override
	public SlackMessageBuilder setChannelId(String channelId) {
		setField("channel", channelId, false);
		return this;
	}

	@Override
	public SlackMessageBuilder setChannel(String channelName) {
		setField("channel", "#" + channelName, false);
		return this;
	}

	@Override
	public SlackMessageBuilder setUserAsChannel(String userName) {
		setField("channel", "@" + userName, false);
		return this;
	}

	@Override
	public SlackMessageBuilder addAttachment(AttachmentBuilder attachmentBuilder) {
		attachments.add(((AttachmentBuilderJsonImpl) attachmentBuilder).getBackingMap());
		return this;
	}

	@Override
	public SlackMessageBuilder setResponseType(String responseType) {
		setField("response_type", responseType, false);
		return this;
	}

	@Override
	public SlackMessageBuilder setUsername(String username) {
		setField("username", username, false);
		return this;
	}

	@Override
	public String build() {
		try {
			return super.buildJsonString();
		}
		catch (Exception e) {
			throw new RuntimeException("Failed to build JSON string.", e);
		}
	}

}
