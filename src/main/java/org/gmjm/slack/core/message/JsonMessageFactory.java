package org.gmjm.slack.core.message;

import org.gmjm.slack.api.message.AttachmentBuilder;
import org.gmjm.slack.api.message.SlackMessageBuilder;
import org.gmjm.slack.api.message.SlackMessageFactory;

public class JsonMessageFactory implements SlackMessageFactory {

	@Override
	public SlackMessageBuilder createMessageBuilder() {
		return new SlackMessageBuilderJsonImpl();
	}

	@Override
	public AttachmentBuilder createAttachmentBuilder() {
		return new AttachmentBuilderJsonImpl();
	}

}
