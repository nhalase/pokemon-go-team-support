package org.gmjm.slack.api.hook;

public interface HookRequestFactory {

	HookRequest createHookRequest(String url);
}
