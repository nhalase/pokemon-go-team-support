package org.gmjm.slack.api.message;

/**
 * AttachementBuilder is used to build up a complex, formatted attachment
 * for inclusion in a SlackMessageBuilder.
 * <p>
 * https://api.slack.com/docs/attachments
 */
public interface AttachmentBuilder {

	/**
	 * Sets the title of the Attachment.
	 * Set's the link of the Attachment.
	 *
	 * @param title
	 * @param link
	 * @return this
	 */
	AttachmentBuilder setTitle(String title, String link);

	/**
	 * Set the title of the Attachment.
	 *
	 * @param title
	 * @return this
	 */
	AttachmentBuilder setTitle(String title);

	/**
	 * Set the text of the attachment body.
	 * <p>
	 * Markdown enabled by default.
	 *
	 * @param text
	 * @return this
	 */
	AttachmentBuilder setText(String text);

	/**
	 * Set the text of the attachment body.
	 * Specify if markdown is enabled
	 *
	 * @param text
	 * @param markdownEnabled
	 * @return this
	 */
	AttachmentBuilder setText(String text, boolean markdownEnabled);

	/**
	 * Set the pre text of the attachment.  This will appear below the
	 * title.
	 * Markdown enabled by default.
	 *
	 * @param preText
	 * @return this
	 */
	AttachmentBuilder setPreText(String preText);

	/**
	 * Set the pre text of the attachment.  This will appear below the title.
	 * Specify if markdown is enable.d
	 *
	 * @param preText
	 * @param markdownEnabled
	 * @return this
	 */
	AttachmentBuilder setPreText(String preText, boolean markdownEnabled);

	/**
	 * @return the attachment as a String
	 */
	String build();
}
