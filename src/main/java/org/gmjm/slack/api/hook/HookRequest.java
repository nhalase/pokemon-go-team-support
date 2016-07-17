package org.gmjm.slack.api.hook;

/**
 * A simple interface to send a message.  These objects should be created
 * using an implementation of a HookRequestFactory.
 */
public interface HookRequest {

	/**
	 * Send a message using the underlying implementation of HookRequest.
	 * No exceptions should escape this method, and should be captured in
	 * the returned HookResponse.  This should make processing a stream of
	 * HookRequest and HookResponse objects.
	 *
	 * @param message
	 * @return
	 */
	HookResponse send(String message);
}
