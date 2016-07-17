package org.nhalase.slack.command.processor;

import org.gmjm.slack.api.model.SlackCommand;
import org.nhalase.slack.InvalidRequestContextException;

import javax.validation.constraints.NotNull;

public interface SlackCommandProcessor {

    void process(@NotNull SlackCommand slackCommand) throws InvalidRequestContextException;

}
