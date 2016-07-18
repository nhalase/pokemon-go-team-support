package org.nhalase.slack.pokemon;

import com.google.common.collect.ImmutableSet;
import org.gmjm.slack.core.message.JsonMessageFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PokemonCommandHandlersTest {

    private PokemonCommandHandlers handlers;

    @Before
    public void setup() {
        handlers = new PokemonCommandHandlers(new JsonMessageFactory());
    }

    @Test
    public void assertsLineItemMembershipDisplayFormatting() throws Exception {

        TeamMembership tm1 = new TeamMembership();
        tm1.setId(1L);
        tm1.setPokemonGoTrainerName("Nick");
        tm1.setSlackUserName("@nick");
        tm1.setPokemonGoTeam("Valor");
        tm1.setSlackTeamId("A1234B");
        tm1.setSlackUserId("B4321A");
        tm1.setPokemonGoLevel(15);

        String result = handlers.prettyLineItemMembership(tm1);

        assertNotNull(result);
        assertEquals("@nick (Nick) is level 15", result);

    }

    @Test
    public void assertsReportAllDisplayFormatting() throws Exception {

        TeamMembership tm1 = new TeamMembership();
        tm1.setId(1L);
        tm1.setSlackUserName("@nick");
        tm1.setPokemonGoTeam("Valor");
        tm1.setSlackTeamId("A1234B");
        tm1.setSlackUserId("B4321A");
        tm1.setPokemonGoLevel(15);

        TeamMembership tm2 = new TeamMembership();
        tm2.setId(2L);
        tm2.setPokemonGoTrainerName("Jimmy");
        tm2.setSlackUserName("@jimmy");
        tm2.setPokemonGoTeam("Instinct");
        tm2.setSlackTeamId("A1234B");
        tm2.setSlackUserId("ASDF");
        tm2.setPokemonGoLevel(10);

        TeamMembership tm3 = new TeamMembership();
        tm3.setId(2L);
        tm3.setPokemonGoTrainerName("Evan");
        tm3.setSlackUserName("@evan");
        tm3.setPokemonGoTeam("Mystic");
        tm3.setSlackTeamId("A1234B");
        tm3.setSlackUserId("fdsaf");
        tm3.setPokemonGoLevel(14);

        TeamMembership tm4 = new TeamMembership();
        tm4.setId(3L);
        tm4.setPokemonGoTrainerName("Chris");
        tm4.setSlackUserName("@chris");
        tm4.setPokemonGoTeam("Undecided");
        tm4.setSlackTeamId("A1234B");
        tm4.setSlackUserId("hjgfrg");
        tm4.setPokemonGoLevel(12);

        TeamMembership tm5 = new TeamMembership();
        tm5.setId(4L);
        tm5.setPokemonGoTrainerName("Christa");
        tm5.setSlackUserName("@christa");
        tm5.setPokemonGoTeam("Valor");
        tm5.setSlackTeamId("A1234B");
        tm5.setSlackUserId("grgrgr");
        tm5.setPokemonGoLevel(20);

        String result = handlers.getReportAll(ImmutableSet.of(tm1, tm2, tm3, tm4, tm5));
        assertNotNull(result);
        assertEquals("_Undecided_\n" +
                "@chris (Chris) is level 12\n" +
                "*Instinct*\n" +
                "@jimmy (Jimmy) is level 10\n" +
                "*Mystic*\n" +
                "@evan (Evan) is level 14\n" +
                "*Valor*\n" +
                "@christa (Christa) is level 20\n" +
                "@nick (Unknown Trainer) is level 15\n", result);

    }

}
