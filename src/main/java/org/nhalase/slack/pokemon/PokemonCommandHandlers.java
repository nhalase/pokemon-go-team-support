package org.nhalase.slack.pokemon;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.gmjm.slack.api.message.SlackMessageBuilder;
import org.gmjm.slack.api.message.SlackMessageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.function.Function;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.*;

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
                .put("register_ephemeral", this::registerTrainer)
                .put("team_ephemeral", this::setTeam)
                .put("level_ephemeral", this::setLevel)
                .put("report_ephemeral", this::reportAll)
                .put("reset_ephemeral", this::reset)
                .build();
    }

    @NotNull
    SlackMessageBuilder handle(@NotNull String handlerName, PokemonRequestContext ctx) {
        if (handlerNameIsValid(handlerName)) {
            return commands.get(handlerName).apply(ctx);
        } else {
            logger.info("Unknown handler called. Returning with \"help.\"");
            return commands.get("help_ephemeral").apply(ctx);
        }
    }

    private boolean handlerNameIsValid(@NotNull String handlerName) {
        return commands.containsKey(handlerName);
    }

    @NotNull
    private SlackMessageBuilder help(@NotNull PokemonRequestContext ctx) {
        logger.info("help");
        return slackMessageFactory.createMessageBuilder().setText(
                "---help---" + "\n" +
                        "> -- Entering `/pokemon` or `/pokemon <unknown command>` will display this help text." + "\n" +
                        "> -- *register <your Pokémon Go trainer name>* -- Registers yourself in the database." +
                        " Doing this multiple times overwrites the previous value." + "\n" +
                        "> -- *team <your Pokémon Go team name>* -- Sets your team (Instinct, Mystic or Valor)." +
                        " You must register yourself as a trainer first." +
                        " Doing this multiple times overwrites the previous value." + "\n" +
                        "> -- *level <your Pokémon Go trainer level>* -- Sets your trainer level." +
                        " You must register yourself as a trainer first." +
                        " Doing this multiple times overwrites the previous value." + "\n" +
                        "> -- *report* -- Displays a report of trainers grouped by team, and sorted by descending level."
        );
    }

    @NotNull
    private Optional<TeamMembership> getMembership(PokemonRequestContext ctx) {
        final TeamMembershipRepository teamMembershipRepository = ctx.getTeamMembershipRepository();
        final String slackUserId = ctx.getSlackCommand().getUserId();
        final String slackTeamId = ctx.getSlackCommand().getTeamId();
        return teamMembershipRepository.findBySlackUserIdAndSlackTeamId(slackUserId, slackTeamId);
    }

    @NotNull
    private TeamMembership makeMembership(
            @NotNull final String slackUserId,
            @NotNull final String slackTeamId,
            @NotNull final String slackUserName,
            @NotNull final String pokemonGoTrainerName
    ) {
        return new TeamMembership(
                slackUserId,
                slackTeamId,
                slackUserName,
                pokemonGoTrainerName
        );
    }

    @NotNull
    private SlackMessageBuilder registerTrainer(@NotNull PokemonRequestContext ctx) {
        logger.info("register trainer");
        final TeamMembershipRepository teamMembershipRepository = ctx.getTeamMembershipRepository();

        final String slackUserId = ctx.getSlackCommand().getUserId();
        final String slackTeamId = ctx.getSlackCommand().getTeamId();
        final String slackUserName = "@" + ctx.getSlackCommand().getUserName();
        final String pokemonGoTrainerName = trimToEmpty(ctx.getPokemonCommand().getText()).length() > 0 ?
                trimToEmpty(ctx.getPokemonCommand().getText()) :
                "Unknown Trainer";

        final Optional<TeamMembership> possibleMembership = getMembership(ctx);

        if (possibleMembership.isPresent()) {
            final TeamMembership membership = possibleMembership.get();
            final String oldName = membership.getPokemonGoTrainerName();
            membership.setPokemonGoTrainerName(pokemonGoTrainerName);
            membership.setSlackUserName(slackUserName);
            final TeamMembership updated = teamMembershipRepository.save(membership);
            return slackMessageFactory
                    .createMessageBuilder()
                    .setText("Registration updated.\nOld name: " + oldName + "\nNew name: " + updated.getPokemonGoTrainerName());
        } else {
            final TeamMembership saved = teamMembershipRepository.save(
                    makeMembership(slackUserId, slackTeamId, slackUserName, pokemonGoTrainerName)
            );
            final String savedName = saved.getPokemonGoTrainerName();
            final String unknownTrainerHelp = savedName.length() > 0 ? "" :
                    "To update your registration with your actual trainer name, simply run `/pokemon register <your Pokémon Go trainer name>`";
            return slackMessageFactory
                    .createMessageBuilder()
                    .setText(String.format("Registered. Welcome, %s! %s", savedName, unknownTrainerHelp));
        }
    }

    private static final ImmutableSet<String> TEAM_NAMES = ImmutableSet.of("instinct", "mystic", "valor");

    @NotNull
    private SlackMessageBuilder setTeam(@NotNull PokemonRequestContext ctx) {
        logger.info("set team");
        final TeamMembershipRepository teamMembershipRepository = ctx.getTeamMembershipRepository();

        final Optional<TeamMembership> possibleMembership = getMembership(ctx);
        if (!possibleMembership.isPresent()) {
            return slackMessageFactory.createMessageBuilder().setText(
                    "You must register as a trainer first. Enter `/pokemon register <your Pokémon Go trainer name>`."
            );
        }

        final TeamMembership membership = possibleMembership.get();

        final String requestedTeamName = trimToEmpty(ctx.getPokemonCommand().getText());
        if (requestedTeamName.length() == 0 || !TEAM_NAMES.contains(requestedTeamName.toLowerCase())) {
            return slackMessageFactory.createMessageBuilder().setText(
                    "You must set a valid Pokémon Go team name: Instinct, Mystic or Valor."
            );
        }

        final String teamName = capitalize(requestedTeamName);
        membership.setPokemonGoTeam(teamName);

        TeamMembership updated = teamMembershipRepository.save(membership);

        return slackMessageFactory.createMessageBuilder().setText(
                "You've been registered as a trainer representing Team " + updated.getPokemonGoTeam() + "."
        );
    }

    @NotNull
    private SlackMessageBuilder setLevel(@NotNull PokemonRequestContext ctx) {
        logger.info("set level");
        final TeamMembershipRepository teamMembershipRepository = ctx.getTeamMembershipRepository();

        final Optional<TeamMembership> possibleMembership = getMembership(ctx);
        if (!possibleMembership.isPresent()) {
            return slackMessageFactory.createMessageBuilder().setText(
                    "You must register as a trainer first. Enter `/pokemon register <your Pokémon Go trainer name>`."
            );
        }

        final TeamMembership membership = possibleMembership.get();

        final String requestedLevel = trimToEmpty(ctx.getPokemonCommand().getText());
        if (!isNumeric(requestedLevel)) {
            return slackMessageFactory.createMessageBuilder().setText(
                    "You must set a numeric level. No decimals or +/- signs."
            );
        }

        membership.setPokemonGoLevel(new Integer(requestedLevel));
        TeamMembership updated = teamMembershipRepository.save(membership);

        return slackMessageFactory.createMessageBuilder().setText(
                "You're now recognized as being a level " + updated.getPokemonGoLevel() + " trainer."
        );
    }

    @NotNull
    private SlackMessageBuilder reportAll(@NotNull PokemonRequestContext ctx) {
        logger.info("report");
        final TeamMembershipRepository teamMembershipRepository = ctx.getTeamMembershipRepository();
        final ImmutableSet<TeamMembership> memberships = ImmutableSet.copyOf(teamMembershipRepository
                .findBySlackTeamId(ctx.getSlackCommand().getTeamId()));
        if (memberships.size() == 0) {
            return slackMessageFactory.createMessageBuilder().setText("There are no registered trainers to report on.");
        }

        return slackMessageFactory.createMessageBuilder().setText(getReportAll(memberships));
    }

    @NotNull
    String getReportAll(@NotNull final ImmutableSet<TeamMembership> memberships) {
        final StringBuilder stringBuilder = new StringBuilder();
        memberships.stream()
                .sorted( (tm1, tm2) -> {
                    String tm1Name = tm1.getPokemonGoTeam();
                    if (tm1Name.equals("Undecided")) {
                        tm1Name = "";
                    }
                    String tm2Name = tm2.getPokemonGoTeam();
                    if (tm2Name.equals("Undecided")) {
                        tm2Name = "";
                    }
                    return tm1Name.compareTo(tm2Name);
                })
                .collect( groupingBy(TeamMembership::getPokemonGoTeam, LinkedHashMap::new, toList()) )
                .forEach( (teamName, teamMemberships) -> {
                    final String displayedName = teamName.equals("Undecided") ? "_Undecided_" : "*" + teamName + "*";
                    stringBuilder.append(displayedName).append("\n");
                    Collections.sort(teamMemberships,
                            (tm1, tm2) -> tm2.getPokemonGoLevel().compareTo(tm1.getPokemonGoLevel())
                    );
                    teamMemberships
                            .forEach(membership -> stringBuilder
                                    .append(prettyLineItemMembership(membership))
                                    .append("\n")
                            );
                });
        return stringBuilder.toString();
    }

    @NotNull
    String prettyLineItemMembership(@NotNull TeamMembership membership) {
        return String.format("%s (%s) is level %s",
                membership.getSlackUserName(),
                membership.getPokemonGoTrainerName(),
                membership.getPokemonGoLevel()
        );
    }

    @NotNull
    private SlackMessageBuilder reset(@NotNull PokemonRequestContext ctx) { // TODO: remove; temp
        logger.info("reset");
        if (!ctx.getSlackCommand().getUserName().equals("nhalase") && !ctx.getSlackCommand().getUserName().equals("nick")) {
            return slackMessageFactory.createMessageBuilder().setText("you don't have access to this function");
        }
        ctx.getTeamMembershipRepository().deleteAll();
        return slackMessageFactory.createMessageBuilder().setText("done");
    }

}
