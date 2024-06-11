package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            var source = Paths.get(".", "magmac", "src", "magma", "Main.java");
            var input = Files.readString(source);
            var target = source.resolveSibling("Main.mgs");
            Files.writeString(target, compile(input));
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    private static String compile(String input) {
        var current = new State();
        for (int i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            current = processChar(current.append(c), c);
        }

        var inputTokens = current.advance().tokens;
        return String.join("", inputTokens);
    }

    private static State processChar(State state, char c) {
        return c == ';' ? state.advance() : state;
    }

    record State(List<String> tokens, StringBuilder buffer) {
        public State() {
            this(Collections.emptyList(), new StringBuilder());
        }

        private State advance() {
            var copy = new ArrayList<>(this.tokens);
            copy.add(this.buffer.toString());
            return new State(copy, new StringBuilder());
        }

        private State append(char c) {
            return new State(tokens, this.buffer.append(c));
        }
    }
}
