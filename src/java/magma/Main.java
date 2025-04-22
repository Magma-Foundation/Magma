package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private record State(List<String> segments, StringBuilder buffer, int depth) {
        public static State createEmpty() {
            return new State(new ArrayList<>(), new StringBuilder(), 0);
        }

        private State advance() {
            List<String> copy = new ArrayList<>(this.segments);
            copy.add(this.buffer.toString());

            return new State(copy, new StringBuilder(), this.depth);
        }

        private State append(char c) {
            return new State(this.segments, this.buffer.append(c), this.depth);
        }

        public boolean isLevel() {
            return this.depth == 0;
        }

        public State enter() {
            return new State(this.segments, this.buffer, this.depth + 1);
        }

        public State exit() {
            return new State(this.segments, this.buffer, this.depth - 1);
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
        State current = State.createEmpty();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            current = foldStatementChar(current, c);
        }
        List<String> copy = current.advance().segments;

        StringBuilder output = new StringBuilder();
        for (String segment : copy) {
            output.append(compileRootSegment(segment));
        }
        return output.toString();
    }

    private static State foldStatementChar(State state, char c) {
        State appended = state.append(c);
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
        int classIndex = stripped.indexOf("class ");
        if (classIndex >= 0) {
            String afterKeyword = stripped.substring(classIndex + "class ".length());
            int contentStart = afterKeyword.indexOf("{");
            if (contentStart >= 0) {
                String name = afterKeyword.substring(0, contentStart).strip();
                return "struct " + name + " {\n};\n";
            }
        }
        return "/* " + stripped + " */\n";
    }
}
