package /*magma*/;

import /*java.io.IOException*/;
import /*java.nio.file.Files*/;
import /*java.nio.file.Path*/;
import /*java.nio.file.Paths*/;
import /*java.util.ArrayList*/;
import /*java.util.List*/;
import /*java.util.Optional*/;
import /*java.util.function.Function*/;
import /*java.util.stream.Collectors*/;
import /*java.util.stream.Stream*/;/*

public */class Main {/*
    private */interface DivideState {/*
        DivideState advance();*//*

        DivideState append(char c);*//*

        Stream<String> stream();*//*

        boolean isLevel();*//*

        DivideState enter();*//*

        DivideState exit();*//*

        boolean isShallow();*//*
    */}/*

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
    }*//*

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
    }*//*

    private static String compile(String input) {
        return compileStatements(input, Main::compileRootSegment);
    }*//*

    private static String compileStatements(String input, Function<String, String> compiler) {
        return divide(input, new MutableDivideState())
                .map(compiler)
                .collect(Collectors.joining());
    }*//*

    private static Stream<String> divide(String input, DivideState state) {
        DivideState current = state;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            current = foldStatementChar(current, c);
        }

        return current.advance().stream();
    }*//*

    private static DivideState foldStatementChar(DivideState state, char c) {
        DivideState appended = state.append(c);
        if (c == ';' && appended.isLevel()) {
            return appended.advance();
        }
        if (c == '}*//*' && appended.isShallow()) {
            return appended.advance().exit();
        }*//*
        if (c == '{') {
            return appended.enter();
        }
        if (c == '}*//*') {
            return appended.exit();
        }*//*
        return appended;*//*
    */}/*

    private static String compileRootSegment(String input) {
        return createNamespacedRuled(input, "package ")
                .or(() -> createNamespacedRuled(input, "import "))
                .or(() -> compileClass(input))
                .orElseGet(() -> generatePlaceholder(input));
    }*//*

    private static Optional<String> createNamespacedRuled(String input, String infix) {
        return compileInfix(input, Main::compileContent, infix, afterKeyword -> compileSuffix(afterKeyword, ";", Main::compileContent));
    }*//*

    private static Optional<String> compileClass(String stripped) {
        return compileStructured(stripped, "class ");
    }*//*

    private static Optional<String> compileStructured(String stripped, String infix) {
        return compileInfix(stripped, Main::compileContent, infix, afterKeyword -> {
            return compileInfix(afterKeyword, Main::compileString, "{", withEnd -> {
                return compileSuffix(withEnd, "}", content -> {
                    return Optional.of(compileStatements(content, Main::compileClassSegment));
                });
            });
        });
    }*//*

    private static String compileClassSegment(String input) {
        return compileStructured(input, "interface ")
                .orElseGet(() -> generatePlaceholder(input));
    }*//*

    private static Optional<String> compileInfix(
            String input,
            Function<String, Optional<String>> leftRule,
            String infix,
            Function<String, Optional<String>> rightRule
    ) {
        int contentStart = input.indexOf(infix);
        if (contentStart < 0) {
            return Optional.empty();
        }

        String left = input.substring(0, contentStart);
        String right = input.substring(contentStart + infix.length());

        return leftRule.apply(left).flatMap(compiledLeft -> {
            return rightRule.apply(right).map(compiledRight -> {
                return compiledLeft + infix + compiledRight;
            });
        });
    }*//*

    private static Optional<String> compileString(String name) {
        return Optional.of(name);
    }*//*

    private static Optional<String> compileSuffix(String input, String suffix, Function<String, Optional<String>> childRule) {
        if (!input.endsWith(suffix)) {
            return Optional.empty();
        }
        String slice = input.substring(0, input.length() - suffix.length());
        return childRule.apply(slice).map(inner -> inner + suffix);
    }*//*

    private static Optional<String> compilePrefix(String input, String prefix, Function<String, Optional<String>> childRule) {
        if (!input.startsWith(prefix)) {
            return Optional.empty();
        }
        String slice = input.substring(prefix.length());
        return childRule.apply(slice).map(inner -> prefix + inner);
    }*//*

    private static Optional<String> compileContent(String content) {
        String generated = content.isBlank() ? content : generatePlaceholder(content);
        return Optional.of(generated);
    }*//*

    private static String generatePlaceholder(String stripped) {
        return "/*" + stripped + "*/";
    }*//*
}*/