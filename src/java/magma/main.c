/* import java.io.IOException; */
/* import java.nio.file.Files; */
/* import java.nio.file.Paths; */
/* import java.util.ArrayList; */
/* import java.util.List; */
/* import java.util.Optional; */
/* import java.util.function.Function; */
struct ");
        if (classIndex < 0) {/* return Optional.empty(); */
/* 
        }

        var afterKeyword = stripped.substring(classIndex + "class ".length());
        var contentStart = afterKeyword.indexOf("{"); */
/* 
        if (contentStart < 0) {
            return Optional.empty();
        }

        var name = afterKeyword.substring(0, contentStart).strip(); */
/* 
        var withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
        if (!withEnd.endsWith("}")) {
            return Optional.empty();
        }

        var content = withEnd.substring(0, withEnd.length() - "}".length());
        var generated = "struct " + name + " {" + compileStatements(content, Main::compileClassSegment) + "}";
        structs.add(generated);
        return Optional.of("");
    }

    private static String compileClassSegment(String input) {
        return compileClass(input).orElseGet(() -> generatePlaceholder(input));
    }

    private static String generatePlaceholder(String stripped) {
        return "/* " + stripped + " */\n";
     */
}struct Main {/* private static class State {
        private final List<String> segments;
        private StringBuilder buffer;
        private int depth;

        private State(List<String> segments, StringBuilder buffer, int depth) {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
        }

        public State() {
            this(new ArrayList<>(), new StringBuilder(), 0);
        }

        private State append(char c) {
            this.buffer.append(c);
            return this;
        }

        private State advance() {
            this.segments.add(this.buffer.toString());
            this.buffer = new StringBuilder();
            return this;
        }

        public State enter() {
            this.depth++;
            return this;
        }

        public boolean isLevel() {
            return this.depth == 0;
        }

        public State exit() {
            this.depth--;
            return this;
        }
    }

    private static final List<String> structs = new ArrayList<>(); */
}