package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

public class Main {
    private interface TriFunction<A, B, C, D> {
        D apply(A a, B b, C c);
    }

    private interface Rule {
        Optional<Tuple<State, String>> apply(State state, String input);
    }

    private record DivideState(List<String> segments, StringBuilder buffer, int depth) {
        public static DivideState createEmpty() {
            return new DivideState(new ArrayList<>(), new StringBuilder(), 0);
        }

        private DivideState advance() {
            List<String> copy = new ArrayList<>(this.segments);
            copy.add(this.buffer.toString());

            return new DivideState(copy, new StringBuilder(), this.depth);
        }

        private DivideState append(char c) {
            return new DivideState(this.segments, this.buffer.append(c), this.depth);
        }

        public boolean isLevel() {
            return this.depth == 0;
        }

        public DivideState enter() {
            return new DivideState(this.segments, this.buffer, this.depth + 1);
        }

        public DivideState exit() {
            return new DivideState(this.segments, this.buffer, this.depth - 1);
        }

        public boolean isShallow() {
            return this.depth == 1;
        }
    }

    private record State(List<String> structs) {
        public State() {
            this(new ArrayList<>());
        }

        public State addStruct(String struct) {
            ArrayList<String> copy = new ArrayList<>(this.structs);
            copy.add(struct);
            return new State(copy);
        }
    }

    private record Tuple<A, B>(A left, B right) {
    }

    private record StripRule(Rule mapper) implements Rule {
        @Override
        public Optional<Tuple<State, String>> apply(
                State state,
                String input) {
            return this.mapper().apply(state, input.strip());
        }
    }

    private record SuffixRule(
            Rule mapper, String suffix
    ) implements Rule {
        @Override
        public Optional<Tuple<State, String>> apply(
                State state,
                String input) {
            if (!input.endsWith(this.suffix())) {
                return Optional.empty();
            }
            String slice = input.substring(0, input.length() - this.suffix().length());
            return this.mapper().apply(state, slice);
        }
    }

    private static class StringRule implements Rule {
        @Override
        public Optional<Tuple<State, String>> apply(State state, String s) {
            return Optional.of(new Tuple<>(state, s));
        }
    }

    private record InfixRule(
            Rule leftRule,
            String infix,
            Rule rightRule,
            TriFunction<State, String, String, Tuple<State, String>> merger
    ) implements Rule {
        @Override
        public Optional<Tuple<State, String>> apply(State state, String input) {
            int index = input.indexOf(this.infix());
            if (index < 0) {
                return Optional.empty();
            }
            String left = input.substring(0, index);
            String right = input.substring(index + this.infix().length());

            return this.leftRule().apply(state, left).flatMap(leftResult -> {
                return this.rightRule().apply(leftResult.left, right).map(rightResult -> {
                    return this.merger().apply(rightResult.left, leftResult.right, rightResult.right);
                });
            });
        }
    }

    private static class PlaceholderRule implements Rule {
        @Override
        public Optional<Tuple<State, String>> apply(State state1, String s) {
            return Optional.of(new Tuple<>(state1, generatePlaceholder(s)));
        }
    }

    public static void main(String[] args) {
        try {
            Path source = Paths.get(".", "src", "java", "magma", "Main.java");
            String input = Files.readString(source);

            Path target = source.resolveSibling("main.c");
            Files.writeString(target, compileRoot(input));
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    private static String compileRoot(String input) {
        Tuple<State, String> compiled = compileAll(new State(), input, Main::compileRootSegment);
        List<String> structs = compiled.left.structs;
        return String.join("", structs) + compiled.right;
    }

    private static Tuple<State, String> compileAll(
            State state,
            String input,
            BiFunction<State, String, Tuple<State, String>> compiler
    ) {
        DivideState current = DivideState.createEmpty();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            current = foldStatementChar(current, c);
        }
        List<String> copy = current.advance().segments;

        State parsed = state;
        StringBuilder output = new StringBuilder();
        for (String segment : copy) {
            Tuple<State, String> compiled = compiler.apply(parsed, segment);
            parsed = compiled.left;
            output.append(compiled.right);
        }
        return new Tuple<>(parsed, output.toString());
    }

    private static DivideState foldStatementChar(DivideState state, char c) {
        DivideState appended = state.append(c);
        if (c == ';' && appended.isLevel()) {
            return appended.advance();
        }
        if (c == '}' && appended.isShallow()) {
            return appended.exit().advance();
        }
        if (c == '{') {
            return appended.enter();
        }
        if (c == '}') {
            return appended.exit();
        }
        return appended;
    }

    private static Tuple<State, String> compileRootSegment(State state, String input) {
        return compileClass(input, state).orElseGet(() -> new Tuple<>(state, generatePlaceholder(input.strip()) + "\n"));
    }

    private static Optional<Tuple<State, String>> compileClass(String input, State state) {
        return compileStructured(state, input, "class ");
    }

    private static Optional<Tuple<State, String>> compileStructured(State state, String input, String infix) {
        return createStructuredRule(infix).apply(state, input);
    }

    private static InfixRule createStructuredRule(String infix) {
        Rule rule = (state1, input1) -> Optional.of(compileAll(state1, input1, Main::compileClassSegment));
        Rule afterKeyword = new InfixRule(new StringRule(), "{", new StripRule(new SuffixRule(rule, "}")), concat("{"));
        return new InfixRule(new PlaceholderRule(), infix, afterKeyword, (state, s, s2) -> {
            return new Tuple<>(state.addStruct(s + "struct " + s2), "");
        });
    }

    private static TriFunction<State, String, String, Tuple<State, String>> concat(String infix) {
        return (state, left, right) -> new Tuple<>(state, left + infix + right);
    }

    private static Tuple<State, String> compileClassSegment(State state, String input) {
        return compileStructured(state, input, "record ")
                .orElseGet(() -> new Tuple<>(state, generatePlaceholder(input)));
    }

    private static String generatePlaceholder(String stripped) {
        return "/* " + stripped + " */";
    }
}
