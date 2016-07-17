package org.gmjm.slack.core.hook;

import org.apache.commons.io.IOUtils;
import org.gmjm.slack.api.hook.HookRequest;
import org.gmjm.slack.api.hook.HookResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import java.io.OutputStream;
import java.net.URL;

class HookRequestHttpsUrlConnectionImpl implements HookRequest {

	private static final Logger logger = LoggerFactory.getLogger(HookRequestHttpsUrlConnectionImpl.class);

	private String slackHookUrl;

	public HookRequestHttpsUrlConnectionImpl() { }

	public HookRequestHttpsUrlConnectionImpl(String slackHookUrl) {
		this.slackHookUrl = slackHookUrl;
	}

	@Override
	public HookResponse send(String message) {

		logger.info(String.format("Sending to: %s \n message: \n %s \n", slackHookUrl, message));

		try {
			URL url = new URL(slackHookUrl);
			HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.setRequestProperty("Content-Type", "application/json");
			OutputStream os = con.getOutputStream();

			os.write(message.getBytes());

			os.flush();

			String response = IOUtils.toString(con.getInputStream());

			int responseCode = con.getResponseCode();

			con.disconnect();

			return HookResponseFactory.success(response, responseCode);
		}
		catch (Exception e) {
			return HookResponseFactory.fail(e.getMessage(), 500, e);
		}

	}
}
