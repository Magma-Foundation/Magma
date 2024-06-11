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
        var inputTokens = split(input);

        var outputTokens = new ArrayList<String>();
        for (var inputToken : inputTokens) {
            outputTokens.add(compileRootStatement(inputToken.strip()));
        }

        return String.join("", outputTokens);
    }

    private static String compileRootStatement(String strippedInputToken) {
        if (strippedInputToken.isEmpty() || strippedInputToken.startsWith("package ")) {
            return "";
        }

        if(strippedInputToken.startsWith("import ")) {
            return strippedInputToken + "\n";
        }

        return strippedInputToken;
    }

    private static List<String> split(String input) {
        var current = new State();
        for (int i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            current = processChar(current.append(c), c);
        }

        return current.advance().tokens;
    }

    private static State processChar(State state, char c) {
        if (c == ';' && state.isLevel()) return state.advance();
        if (c == '{') return state.enter();
        if (c == '}') return state.exit();
        return state;
    }

    record State(List<String> tokens, StringBuilder buffer, int depth) {
        public State() {
            this(Collections.emptyList(), new StringBuilder(), 0);
        }

        private State advance() {
            var copy = new ArrayList<>(this.tokens);
            copy.add(this.buffer.toString());
            return new State(copy, new StringBuilder(), depth);
        }

        private State append(char c) {
            return new State(tokens, this.buffer.append(c), depth);
        }

        public boolean isLevel() {
            return this.depth == 0;
        }

        public State enter() {
            return new State(tokens, buffer, depth + 1);
        }

        public State exit() {
            return new State(tokens, buffer, depth - 1);
        }
    }
}
