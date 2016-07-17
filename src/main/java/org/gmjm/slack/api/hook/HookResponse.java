package org.gmjm.slack.api.hook;

/**
 * Contains information on the status of the HookRequest once it's been submitted.
 */
public interface HookResponse {

	enum Status {SUCCESS, FAILED}

	/**
	 * Implementations should never return null.
	 *
	 * @return a success or error message.
	 */
	String getMessage();

	/**
	 * @return
	 */
	int getStatusCode();

	/**
	 * Implementations should never return null.
	 *
	 * @return a Status enumeration denoting if the request was successful.
	 */
	Status getStatus();

	/**
	 * Should return null in most cases.
	 *
	 * @return null if no exception captured, throwable if an exception occurred.
	 */
	Throwable getThrowable();
}
