package org.gmjm.slack.api.message;

/**
 * Use a factory implementation instance to create all instances of related
 * message building objects.  This ensures the .build methods will work as
 * intended.
 * <p>
 * Implementations of this factory are intended to be thread safe.
 */
public interface SlackMessageFactory {

	/**
	 * This object is not threadsafe, or reusable.
	 *
	 * @return A new instance of SlackMessageBuilder.
	 */
	SlackMessageBuilder createMessageBuilder();

	/**
	 * This object is not threadsafe, or reusable.
	 *
	 * @return A new instance of AttachmentBuilder.
	 */
	AttachmentBuilder createAttachmentBuilder();
}
