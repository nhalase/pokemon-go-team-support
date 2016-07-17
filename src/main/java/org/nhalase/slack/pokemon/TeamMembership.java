package org.nhalase.slack.pokemon;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "team_membership")
class TeamMembership {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = 0L;
    @Column(name = "slack_user_id", nullable = false)
    private String slackUserId = "";
    @Column(name = "slack_team_id", nullable = false)
    private String slackTeamId = "";
    @Column(name = "pokemon_go_team", nullable = false)
    private String pokemonGoTeam = "";
    @Column(name = "pokemon_go_level", nullable = false)
    private Integer pokemonGoLevel = 0;

    TeamMembership() {
        id = 0L;
        slackUserId = "";
        slackTeamId = "";
        pokemonGoTeam = "";
        pokemonGoLevel = 0;
    }

    @NotNull
    public Long getId() {
        return id;
    }

    public void setId(@NotNull Long id) {
        this.id = id;
    }

    @NotNull
    public String getSlackUserId() {
        return slackUserId;
    }

    public void setSlackUserId(@NotNull String slackUserId) {
        this.slackUserId = slackUserId;
    }

    @NotNull
    public String getSlackTeamId() {
        return slackTeamId;
    }

    public void setSlackTeamId(@NotNull String slackTeamId) {
        this.slackTeamId = slackTeamId;
    }

    @NotNull
    public String getPokemonGoTeam() {
        return pokemonGoTeam;
    }

    public void setPokemonGoTeam(@NotNull String pokemonGoTeam) {
        this.pokemonGoTeam = pokemonGoTeam;
    }

    @NotNull
    public Integer getPokemonGoLevel() {
        return pokemonGoLevel;
    }

    public void setPokemonGoLevel(@NotNull Integer pokemonGoLevel) {
        this.pokemonGoLevel = pokemonGoLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TeamMembership that = (TeamMembership) o;

        if (!id.equals(that.id)) return false;
        if (!slackUserId.equals(that.slackUserId)) return false;
        if (!slackTeamId.equals(that.slackTeamId)) return false;
        if (!pokemonGoTeam.equals(that.pokemonGoTeam)) return false;
        return pokemonGoLevel.equals(that.pokemonGoLevel);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + slackUserId.hashCode();
        result = 31 * result + slackTeamId.hashCode();
        result = 31 * result + pokemonGoTeam.hashCode();
        result = 31 * result + pokemonGoLevel.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "TeamMembership{" +
                "id=" + id +
                ", slackUserId='" + slackUserId + '\'' +
                ", slackTeamId='" + slackTeamId + '\'' +
                ", pokemonGoTeam='" + pokemonGoTeam + '\'' +
                ", pokemonGoLevel=" + pokemonGoLevel +
                '}';
    }

}
