package magma.compile.rule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Rules {
    public static Optional<Integer> wrapIndex(int index) {
        return index == -1 ? Optional.empty() : Optional.of(index);
    }

    public static List<String> split(String input) {
        var current = new State();
        for (int i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            current = processChar(current.append(c), c);
        }

        return current.advance().tokens;
    }

    private static State processChar(State state, char c) {
        if (c == ';' && state.isLevel()) return state.advance();
        if (c == '}' && state.isShallow()) return state.exit().advance();
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

        public boolean isShallow() {
            return depth == 1;
        }
    }
}
