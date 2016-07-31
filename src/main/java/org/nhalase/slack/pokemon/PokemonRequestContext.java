package org.nhalase.slack.pokemon;

import org.gmjm.slack.api.model.SlackCommand;
import org.nhalase.slack.InvalidRequestContextException;

import javax.validation.constraints.NotNull;

final class PokemonRequestContext {

    private final SlackCommand slackCommand;
    private final TeamMembershipRepository teamMembershipRepository;
    private final PokemonCommand pokemonCommand;
    private final String user;

    private PokemonRequestContext(
            @NotNull SlackCommand slackCommand,
            @NotNull TeamMembershipRepository teamMembershipRepository,
            @NotNull PokemonCommand pokemonCommand,
            @NotNull String user
    ) {
        this.slackCommand = slackCommand;
        this.teamMembershipRepository = teamMembershipRepository;
        this.pokemonCommand = pokemonCommand;
        this.user = user;
    }

    SlackCommand getSlackCommand() {
        return slackCommand;
    }

    TeamMembershipRepository getTeamMembershipRepository() {
        return teamMembershipRepository;
    }

    PokemonCommand getPokemonCommand() {
        return pokemonCommand;
    }

    String getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PokemonRequestContext that = (PokemonRequestContext) o;

        return slackCommand.equals(that.slackCommand)
                && teamMembershipRepository.equals(that.teamMembershipRepository)
                && pokemonCommand.equals(that.pokemonCommand)
                && user.equals(that.user);
    }

    @Override
    public int hashCode() {
        int result = slackCommand.hashCode();
        result = 31 * result + teamMembershipRepository.hashCode();
        result = 31 * result + pokemonCommand.hashCode();
        result = 31 * result + user.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "PokemonRequestContext{" +
                "slackCommand=" + slackCommand +
                ", teamMembershipRepository=" + teamMembershipRepository +
                ", pokemonCommand=" + pokemonCommand +
                ", user='" + user + '\'' +
                '}';
    }

    static Builder builder(@NotNull SlackCommand slackCommand) {
        return new Builder(slackCommand);
    }

    @SuppressWarnings("WeakerAccess") // cannot be private
    static final class Builder {

        private SlackCommand slackCommand;
        private TeamMembershipRepository teamMembershipRepository;
        private PokemonCommand pokemonCommand;
        private String user;

        Builder(@NotNull SlackCommand slackCommand) {
            this.slackCommand = slackCommand;
        }

        Builder teamMembershipRepository(@NotNull TeamMembershipRepository teamMembershipRepository) {
            this.teamMembershipRepository = teamMembershipRepository;
            return this;
        }

        Builder pokemonCommand(@NotNull PokemonCommand pokemonCommand) {
            this.pokemonCommand = pokemonCommand;
            return this;
        }

        Builder user(@NotNull String user) {
            this.user = user;
            return this;
        }

        PokemonRequestContext build() throws InvalidRequestContextException {
            if (this.slackCommand == null ||
                    this.teamMembershipRepository == null ||
                    this.pokemonCommand == null ||
                    this.user == null) {
                throw new InvalidRequestContextException("Cannot build an incomplete RequestContext!");
            }
            return new PokemonRequestContext(
                    slackCommand,
                    teamMembershipRepository,
                    pokemonCommand,
                    user
            );
        }

    }

}
