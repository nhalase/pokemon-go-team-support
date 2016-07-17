package org.gmjm.slack.core.message;

import org.gmjm.slack.api.message.AttachmentBuilder;

public class AttachmentBuilderJsonImpl extends JsonBuilder implements AttachmentBuilder {

	public AttachmentBuilderJsonImpl() {
		super();
	}

	@Override
	public AttachmentBuilder setTitle(String title, String link) {
		setField("title", title, true);
		setField("title_link", link, false);
		return this;
	}

	@Override
	public AttachmentBuilder setTitle(String title) {
		setField("title", title, true);
		return this;
	}

	/**
	 * Sets text, defaulted to use markdown.
	 *
	 * @param text
	 */
	@Override
	public AttachmentBuilder setText(String text) {
		setText(text, true);
		return this;
	}

	@Override
	public AttachmentBuilder setText(String text, boolean markdownEnabled) {
		setField("text", text, markdownEnabled);
		return this;
	}

	/**
	 * Sets text, defaulted to use markdown.
	 *
	 * @param preText
	 */
	@Override
	public AttachmentBuilder setPreText(String preText) {
		setText(preText, true);
		return this;
	}

	@Override
	public AttachmentBuilder setPreText(String preText, boolean markdownEnabled) {
		setField("pretext", preText, markdownEnabled);
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
