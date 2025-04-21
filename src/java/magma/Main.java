package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    private interface DivideState {
        DivideState advance();

        DivideState append(char c);

        Stream<String> stream();
    }

    private static class MutableDivideState implements DivideState {
        private final List<String> segments;
        private StringBuilder buffer;

        private MutableDivideState(List<String> segments, StringBuilder buffer) {
            this.segments = segments;
            this.buffer = buffer;
        }

        public MutableDivideState() {
            this(new ArrayList<>(), new StringBuilder());
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
        if (c == ';') {
            return appended.advance();
        }
        return appended;
    }

    private static String compileRootSegment(String input) {
        String stripped = input.strip();
        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return stripped + "\n";
        }
        return "/* " + stripped + "*/";
    }
}
