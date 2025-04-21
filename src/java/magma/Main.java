package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    private interface DivideState {
        DivideState advance();

        DivideState append(char c);

        Stream<String> stream();

        boolean isLevel();

        DivideState enter();

        DivideState exit();

        boolean isShallow();
    }

    interface Rule extends Function<String, Optional<String>> {
    }

    private static class MutableDivideState implements DivideState {
        private final List<String> segments;
        private StringBuilder buffer;
        private int depth;

        private MutableDivideState(List<String> segments, StringBuilder buffer, int depth) {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
        }

        public MutableDivideState() {
            this(new ArrayList<>(), new StringBuilder(), 0);
        }

        @Override
        public DivideState advance() {
            this.segments.add(this.buffer.toString());
            this.buffer = new StringBuilder();
            return this;
        }

        @Override
        public DivideState append(char c) {
            this.buffer.append(c);
            return this;
        }

        @Override
        public Stream<String> stream() {
            return this.segments.stream();
        }

        @Override
        public boolean isLevel() {
            return this.depth == 0;
        }

        @Override
        public DivideState enter() {
            this.depth++;
            return this;
        }

        @Override
        public DivideState exit() {
            this.depth--;
            return this;
        }

        @Override
        public boolean isShallow() {
            return this.depth == 1;
        }
    }

    public static void main(String[] args) {
        try {
            Path source = Paths.get(".", "src", "java", "magma", "Main.java");
            String input = Files.readString(source);
            Path target = source.resolveSibling("main.c");
            Files.writeString(target, compile(input));
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    private static String compile(String input) {
        return compileStatements(input, Main::compileRootSegment);
    }

    private static String compileStatements(String input, Function<String, String> compiler) {
        return divide(input, new MutableDivideState())
                .map(compiler)
                .collect(Collectors.joining());
    }

    private static Stream<String> divide(String input, DivideState state) {
        DivideState current = state;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            current = foldStatementChar(current, c);
        }

        return current.advance().stream();
    }

    private static DivideState foldStatementChar(DivideState state, char c) {
        DivideState appended = state.append(c);
        if (c == ';' && appended.isLevel()) {
            return appended.advance();
        }
        if (c == '}' && appended.isShallow()) {
            return appended.advance().exit();
        }
        if (c == '{') {
            return appended.enter();
        }
        if (c == '}') {
            return appended.exit();
        }
        return appended;
    }

    private static String compileRootSegment(String input) {
        return createNamespacedRuled(input, "package ")
                .or(() -> createNamespacedRuled(input, "import "))
                .or(() -> compileClass(input))
                .orElseGet(() -> generatePlaceholder(input));
    }

    private static Optional<String> createNamespacedRuled(String input, String infix) {
        return compilePrefix(input, infix, afterKeyword -> compileSuffix(afterKeyword, Main::compileContent, ";"));
    }

    private static Optional<String> compileSuffix(String input, Rule childRule, String suffix) {
        return compileInfix(input, childRule, suffix, Main::locateLast, Main::compileContent);
    }

    private static Optional<Integer> locateLast(String input, String infix) {
        int index = input.lastIndexOf(infix);
        return index == -1
                ? Optional.empty()
                : Optional.of(index);
    }

    private static Optional<String> compilePrefix(String input, String prefix, Rule childRule) {
        return compileFirstInfix(input, Main::compileContent, prefix, childRule);
    }

    private static Optional<String> compileClass(String input) {
        return compileStructured("class ", input);
    }

    private static Optional<String> compileStructured(String input, String infix) {
        return compilePrefix(infix, input, afterKeyword -> {
            return compileFirstInfix(afterKeyword, Main::compileString, "{", withEnd -> {
                return compileSuffix(withEnd, content -> {
                    return Optional.of(compileStatements(content, Main::compileClassSegment));
                }, "}");
            });
        });
    }

    private static String compileClassSegment(String input) {
        return compileStructured("interface ", input)
                .orElseGet(() -> generatePlaceholder(input));
    }

    private static Optional<String> compileFirstInfix(
            String input,
            Rule leftRule,
            String infix,
            Rule rightRule
    ) {
        return compileInfix(input, leftRule, infix, Main::locateFirst, rightRule);
    }

    private static Optional<String> compileInfix(
            String input,
            Rule leftRule,
            String infix,
            BiFunction<String, String, Optional<Integer>> locator,
            Rule rightRule
    ) {
        return locator.apply(input, infix).flatMap(index -> {
            String left = input.substring(0, index);
            String right = input.substring(index + infix.length());

            return leftRule.apply(left).flatMap(compiledLeft -> {
                return rightRule.apply(right).map(compiledRight -> {
                    return compiledLeft + infix + compiledRight;
                });
            });
        });
    }

    private static Optional<Integer> locateFirst(String input, String infix) {
        int index = input.indexOf(infix);
        return index == -1
                ? Optional.empty()
                : Optional.of(index);
    }

    private static Optional<String> compileString(String name) {
        return Optional.of(name);
    }

    private static Optional<String> compileContent(String content) {
        String generated = content.isBlank() ? content : generatePlaceholder(content);
        return Optional.of(generated);
    }

    private static String generatePlaceholder(String stripped) {
        return "/*" + stripped + "*/";
    }
}