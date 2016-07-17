package org.gmjm.slack.api.message;

/**
 * SlackMessageBuilder can be used to build up a complex, formatted slack message.
 * <p>
 * https://api.slack.com/docs/formatting
 */
public interface SlackMessageBuilder {

	/**
	 * Set the text of the message.  Markdown enabled by default.
	 *
	 * @param text
	 * @return
	 */
	SlackMessageBuilder setText(String text);

	/**
	 * Specify an emoji as an icon.
	 *
	 * @param iconEmoji
	 * @return
	 */
	SlackMessageBuilder setIconEmoji(String iconEmoji);

	/**
	 * Specify a URL to use as an icon. Any publicly reachable resource will work.
	 *
	 * @param iconUrl
	 * @return
	 */
	SlackMessageBuilder setIconUrl(String iconUrl);

	/**
	 * Set the text of the message.
	 * Specify if markdown is enabled.
	 *
	 * @param text
	 * @param markdownEnabled
	 * @return
	 */
	SlackMessageBuilder setText(String text, boolean markdownEnabled);

	/**
	 * Set the channelId the message should be sent to.
	 * <p>
	 * (Will override setChannel and setUserAsChannel)
	 *
	 * @param channelId
	 * @return
	 */
	SlackMessageBuilder setChannelId(String channelId);

	/**
	 * Set the channelName the message should be sent to.
	 * <p>
	 * (Will override setChannelId and setUserAsChannel)
	 *
	 * @param channelName
	 * @return
	 */
	SlackMessageBuilder setChannel(String channelName);

	/**
	 * Set the userName (PM) the message should be sent to.
	 * <p>
	 * (Will override setChannel and setChannelId)
	 *
	 * @param userName
	 * @return
	 */
	SlackMessageBuilder setUserAsChannel(String userName);

	/**
	 * Add an attachment to this message.
	 *
	 * @param attachmentBuilder
	 * @return
	 */
	SlackMessageBuilder addAttachment(AttachmentBuilder attachmentBuilder);

	/**
	 * Set the response type.  If responding directly to a request, or to
	 * URL obtained from slackCommand.getResponseUrl(), you can specify the following:
	 * <p>
	 * ephemeral -  This will respond so only the user who issued the command can see the message.
	 * <p>
	 * in_channel - This will respond to the entire channel the command was issued in.
	 *
	 * @param responseType
	 * @return
	 */
	SlackMessageBuilder setResponseType(String responseType);

	/**
	 * Set the username that should appear on the message.
	 *
	 * @param username
	 * @return
	 */
	SlackMessageBuilder setUsername(String username);

	/**
	 * Build up the current message into a String.
	 *
	 * @return
	 */
	String build();
}
