package org.nhalase.slack.pokemon;

import org.gmjm.slack.api.model.SlackCommand;
import org.gmjm.slack.core.model.SlackCommandMapImpl;
import org.nhalase.slack.InvalidRequestContextException;
import org.nhalase.slack.command.processor.SlackCommandProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(value = "/pokemon")
public final class PokemonController {

    private static final Logger logger = LoggerFactory.getLogger(PokemonController.class);

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @Value("#{'${slack.pokemon.valid_command_tokens}'.split(',')}")
    private Set<String> tokens;
    private final SlackCommandProcessor pokemonService;

    @Autowired
    public PokemonController(@Qualifier("pokemonService") SlackCommandProcessor pokemonService) {
        this.pokemonService = pokemonService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> pokemon(@RequestParam Map<String, String> params) {

        final SlackCommand slackCommand = new SlackCommandMapImpl(params);

        if (!tokens.contains(slackCommand.getToken())) {
            return new ResponseEntity<>("Invalid token.", HttpStatus.FORBIDDEN);
        }

        try {
            pokemonService.process(slackCommand);
        } catch (InvalidRequestContextException e) {
            logger.error("Invalid command.", e);
            return new ResponseEntity<>("Invalid command.", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);

    }

}
