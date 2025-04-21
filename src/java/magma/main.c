package magma;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
/*public */class /*Main {
    private interface DivideState {
        DivideState advance();

        DivideState append(char c);

        Stream<String> stream();

        boolean isLevel();

        DivideState enter();

        DivideState exit();
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
        return divide(input, new MutableDivideState())
                .map(Main::compileRootSegment)
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
        if (c == '{') {
            return appended.enter();
        }
        if (c == '}') {
            return appended.exit();
        }
        return appended;
    }

    private static String compileRootSegment(String input) {
        String stripped = input.strip();
        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return stripped + "\n";
        }

        int classIndex = stripped.indexOf("class ");
        if (classIndex >= 0) {
            String modifiers = stripped.substring(0, classIndex);
            String afterKeyword = stripped.substring(classIndex + "class ".length());
            return generatePlaceholder(modifiers) + "class " + generatePlaceholder(afterKeyword);
        }

        return generatePlaceholder(stripped);
    }

    private static String generatePlaceholder(String stripped) {
        return "/*" + stripped + "*/";
    }
}*/