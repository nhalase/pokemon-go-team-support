package org.gmjm.slack.core.hook;

import org.gmjm.slack.api.hook.HookRequest;
import org.gmjm.slack.api.hook.HookRequestFactory;

public class HttpsHookRequestFactory implements HookRequestFactory {

	@Override
	public HookRequest createHookRequest(String url) {
		return new HookRequestHttpsUrlConnectionImpl(url);
	}
}
