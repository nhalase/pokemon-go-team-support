package org.gmjm.slack.core.model;

import com.google.common.collect.ImmutableMap;
import org.gmjm.slack.api.model.SlackCommand;

import javax.validation.constraints.NotNull;
import java.util.Map;

public class SlackCommandMapImpl implements SlackCommand {

	private final ImmutableMap<String, String> requestParams;

	public SlackCommandMapImpl(@NotNull Map<String, String> requestParams) {
	    this.requestParams = ImmutableMap.copyOf(requestParams);
	}

	@Override
	@NotNull
	public String getText() {
		return get("text");
	}

	@Override
    @NotNull
	public String getCommand() {
		return get("command");
	}

	@Override
    @NotNull
	public String getUserName() {
		return get("user_name");
	}

	@Override
    @NotNull
	public String getToken() {
		return get("token");
	}

	@Override
    @NotNull
	public String getUserId() {
		return get("user_id");
	}

	@Override
    @NotNull
	public String getResponseUrl() {
		return get("response_url");
	}

	/**
	 * User returend in message friendly format.
	 *
	 * @return
	 */
	@Override
    @NotNull
	public String getMsgFriendlyUser() {
		return String.format("<@%s|%s>", getUserId(), getUserName());
	}

	@Override
    @NotNull
	public Map<String, String> getAll() {
		return ImmutableMap.copyOf(requestParams);
	}

	@Override
    @NotNull
	public String getTeamId() {
		return get("team_id");
	}

	@Override
    @NotNull
	public String getTeamDomain() {
		return get("team_domain");
	}

	@Override
    @NotNull
	public String getChannelId() {
		return get("channel_id");
	}

	@Override
    @NotNull
	public String getChannelName() {
		return get("channel_name");
	}

	@NotNull
	private String get(@NotNull String key) {
		String value = this.requestParams.get(key);
		return value != null ? value : "";
	}

}
