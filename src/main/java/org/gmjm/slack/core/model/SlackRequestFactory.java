package org.gmjm.slack.core.model;

import java.util.Map;

import org.gmjm.slack.api.model.SlackCommand;

public interface SlackRequestFactory {

	SlackCommand create(Map<String, String> requestParameters);
}
