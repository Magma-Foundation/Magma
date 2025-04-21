package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    interface Rule {
        Optional<String> compile(String input);
    }

    private interface Locator {
        Optional<Integer> locate(String input, String infix);
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

    private record InfixRule(
            Rule leftRule,
            String infix,
            Locator locator,
            Rule rightRule
    ) implements Rule {
        private InfixRule(Rule leftRule, String infix, Rule rightRule) {
            this(leftRule, infix, new FirstLocator(), rightRule);
        }

        @Override
        public Optional<String> compile(
                String input) {
            return this.locator().locate(input, this.infix()).flatMap(index -> {
                String left = input.substring(0, index);
                String right = input.substring(index + this.infix().length());

                return this.leftRule().compile(left).flatMap(compiledLeft -> {
                    return this.rightRule().compile(right).map(compiledRight -> {
                        return compiledLeft + this.infix() + compiledRight;
                    });
                });
            });
        }
    }

    public static class FirstLocator implements Locator {
        @Override
        public Optional<Integer> locate(String input, String infix) {
            int index = input.indexOf(infix);
            return index == -1
                    ? Optional.empty()
                    : Optional.of(index);
        }
    }

    private record PrefixRule(String prefix, Rule childRule) implements Rule {
        @Override
        public Optional<String> compile(String input) {
            return new InfixRule(new ContentRule(), this.prefix(), this.childRule()).compile(input);
        }
    }

    private record SuffixRule(Rule childRule, String suffix) implements Rule {
        @Override
        public Optional<String> compile(String input) {
            return new InfixRule(this.childRule(), this.suffix(), Main::locateLast, new ContentRule()).compile(input);
        }
    }

    public static class ContentRule implements Rule {
        @Override
        public Optional<String> compile(String content) {
            String generated = content.isBlank() ? content : "/*" + content + "*/";
            return Optional.of(generated);
        }
    }

    public static class StringRule implements Rule {
        @Override
        public Optional<String> compile(String name) {
            return Optional.of(name);
        }
    }

    private record DivideRule(Rule rule) implements Rule {
        @Override
        public Optional<String> compile(String input) {
            return divide(input, new MutableDivideState()).reduce(Optional.of(""),
                    (maybeCurrent, element) -> maybeCurrent.flatMap(current -> this.rule().compile(element).map(compiled -> current + compiled)),
                    (_, next) -> next);
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
        return new DivideRule(Main::compileRootSegment).compile(input).orElse("");
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

    private static Optional<String> compileRootSegment(String input) {
        return createNamespaceRule("package ").compile(input)
                .or(() -> createNamespaceRule("import ").compile(input))
                .or(() -> createClassRule().compile(input))
                .or(() -> new ContentRule().compile(input));
    }

    private static Rule createNamespaceRule(String infix) {
        return new PrefixRule(infix, new SuffixRule(new ContentRule(), ";"));
    }

    private static Optional<Integer> locateLast(String input, String infix) {
        int index = input.lastIndexOf(infix);
        return index == -1
                ? Optional.empty()
                : Optional.of(index);
    }

    private static Rule createClassRule() {
        return createStructuredRule("class ");
    }

    private static Rule createStructuredRule(String infix) {
        return new PrefixRule(infix, new InfixRule(new StringRule(), "{",
                new SuffixRule(content -> new DivideRule(Main::compileClassSegment).compile(content), "}")));
    }

    private static Optional<String> compileClassSegment(String input) {
        return createStructuredRule("interface ").compile(input)
                .or(() -> new ContentRule().compile(input));
    }
}