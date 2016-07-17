package org.nhalase.slack.pokemon;

import com.google.common.collect.ImmutableMap;
import org.gmjm.slack.api.message.SlackMessageBuilder;
import org.gmjm.slack.api.message.SlackMessageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.function.Function;

@Service
public class PokemonCommandHandlers {

    private static final Logger logger = LoggerFactory.getLogger(PokemonCommandHandlers.class);

    private final SlackMessageFactory slackMessageFactory;
    private final ImmutableMap<String, Function<PokemonRequestContext, SlackMessageBuilder>> commands;

    @Autowired
    public PokemonCommandHandlers(@NotNull SlackMessageFactory slackMessageFactory) {
        this.slackMessageFactory = slackMessageFactory;
        this.commands = ImmutableMap.<String, Function<PokemonRequestContext, SlackMessageBuilder>>builder()
                .put("help_ephemeral", this::help)
                .put("register-me_ephemeral", this::registerTrainer)
                .put("reset_ephemeral", this::reset)
                .build();
    }

    @NotNull
    SlackMessageBuilder handle(@NotNull String handlerName, PokemonRequestContext pokemonRequestContext) {
        if (handlerNameIsValid(handlerName)) {
            return commands.get(handlerName).apply(pokemonRequestContext);
        } else {
            logger.info("Unknown handler called. Returning with \"help.\"");
            return commands.get("help_ephemeral").apply(pokemonRequestContext);
        }
    }

    private boolean handlerNameIsValid(@NotNull String handlerName) {
        return commands.containsKey(handlerName);
    }

    @NotNull
    private SlackMessageBuilder help(@NotNull PokemonRequestContext pokemonRequestContext) {
        logger.info("help");
        return slackMessageFactory.createMessageBuilder().setText(
                "---help---" + "\n" +
                        "> -- Entering `/pokemon` or `/pokemon <unknown command>` will display this help text. " + "\n"
        );
    }

    @NotNull
    private SlackMessageBuilder registerTrainer(@NotNull PokemonRequestContext pokemonRequestContext) {
        logger.info("register-trainer");
        final TeamMembershipRepository teamMembershipRepository = pokemonRequestContext.getTeamMembershipRepository();
        final String slackUserId = pokemonRequestContext.getSlackCommand().getUserId();
        if (teamMembershipRepository.findBySlackUserId(slackUserId).isPresent()) {
            return slackMessageFactory.createMessageBuilder().setText("updated");
        } else {
            TeamMembership teamMembership = new TeamMembership();
            teamMembership.setSlackUserId(slackUserId);
            teamMembershipRepository.save(teamMembership);
            return slackMessageFactory.createMessageBuilder().setText("saved");
        }
    }

    @NotNull
    private SlackMessageBuilder reset(@NotNull PokemonRequestContext pokemonRequestContext) {
        logger.info("reset");
        pokemonRequestContext.getTeamMembershipRepository().deleteAll();
        return slackMessageFactory.createMessageBuilder().setText("done");
    }

}
