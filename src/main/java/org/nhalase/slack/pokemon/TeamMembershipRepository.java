package org.nhalase.slack.pokemon;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.Set;

@Repository
public interface TeamMembershipRepository extends JpaRepository<TeamMembership, Long> {

    Optional<TeamMembership> findBySlackUserIdAndSlackTeamId(@NotNull String slackUserId, @NotNull String slackTeamId);
    Set<TeamMembership> findBySlackTeamId(@NotNull String slackTeamId);

}
