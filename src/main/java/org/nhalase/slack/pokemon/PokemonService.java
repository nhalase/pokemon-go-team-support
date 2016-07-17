package org.nhalase.slack.pokemon;

import org.gmjm.slack.api.hook.HookRequest;
import org.gmjm.slack.api.hook.HookRequestFactory;
import org.gmjm.slack.api.hook.HookResponse;
import org.gmjm.slack.api.message.SlackMessageBuilder;
import org.gmjm.slack.api.model.SlackCommand;
import org.nhalase.slack.InvalidRequestContextException;
import org.nhalase.slack.command.processor.SlackCommandProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

import static org.gmjm.slack.api.hook.HookResponse.*;

@Service("pokemonService")
public class PokemonService implements SlackCommandProcessor {

    private static final Logger logger = LoggerFactory.getLogger(PokemonService.class);

    private static final String POKEMON_BOT_USERNAME = "professor-slack";
    private static final String POKEBALL_EMOJI = "pokeball";

    private final HookRequestFactory hookRequestFactory;
    private final PokemonCommandHandlers pokemonCommandHandlers;
    private final TeamMembershipRepository teamMembershipRepository;

    @Autowired
    public PokemonService(
            HookRequestFactory hookRequestFactory,
            PokemonCommandHandlers pokemonCommandHandlers,
            TeamMembershipRepository teamMembershipRepository
    ) {
        this.hookRequestFactory = hookRequestFactory;
        this.pokemonCommandHandlers = pokemonCommandHandlers;
        this.teamMembershipRepository = teamMembershipRepository;
    }

    @Override
    public void process(@NotNull final SlackCommand slackCommand) throws InvalidRequestContextException {

        final PokemonCommand pokemonCommand = new PokemonCommand(slackCommand.getText());
        final PokemonRequestContext pokemonRequestContext = PokemonRequestContext.builder(slackCommand)
                .teamMembershipRepository(teamMembershipRepository)
                .pokemonCommand(pokemonCommand)
                .user(slackCommand.getUserName())
                .build();

        sendPrivate(pokemonRequestContext);

    }

    private void sendPrivate(@NotNull PokemonRequestContext pokemonRequestContext) {

        try {

            final String handlerName = pokemonRequestContext.getPokemonCommand().getCommand() + "_ephemeral";
            final SlackMessageBuilder messageBuilder = pokemonCommandHandlers.handle(handlerName, pokemonRequestContext);

            messageBuilder.setIconEmoji(POKEBALL_EMOJI);
            messageBuilder.setResponseType("ephemeral");
            messageBuilder.setUsername(POKEMON_BOT_USERNAME);

            final HookRequest responseHook = hookRequestFactory.createHookRequest(pokemonRequestContext.getSlackCommand().getResponseUrl());
            final HookResponse hookResponse = responseHook.send(messageBuilder.build());
            if (Status.FAILED.equals(hookResponse.getStatus())) {
                logger.error("Failed to send response: " + hookResponse.getMessage());
            }

        } catch (Exception e) {
            logger.error("Failed to send hookRequest." + e);
        }

    }

}
