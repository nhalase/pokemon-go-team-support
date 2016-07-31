package org.nhalase.slack.pokemon;

import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Arrays;

@Component
public class PokemonCommand {

    private final String command;
    private final String text;

    private PokemonCommand() {
        this.command = "";
        this.text = "";
    }

    public PokemonCommand(@NotNull String inputText) {
        final String[] splitText = inputText.trim().split(" ");
        this.command = splitText[0];
        this.text = Arrays.stream(splitText)
                .skip(1)
                .reduce((a, b) -> a + " " + b)
                .orElse("");
    }

    String getCommand() {
        return command;
    }

    String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PokemonCommand that = (PokemonCommand) o;

        return command.equals(that.command) && text.equals(that.text);
    }

    @Override
    public int hashCode() {
        int result = command.hashCode();
        result = 31 * result + text.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "PokemonCommand{" +
                "command='" + command + '\'' +
                ", text='" + text + '\'' +
                '}';
    }

}
