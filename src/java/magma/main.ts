/* public  */class Main {/* private static class State {
        private final List<String> segments;
        private StringBuilder buffer;
        private int depth;

        public State(List<String> segments, StringBuilder buffer, int depth) {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
        }

        public State() {
            this(new ArrayList<>(), new StringBuilder(), 0);
        }

        private State advance() {
            this.segments.add(this.buffer.toString());
            this.buffer = new StringBuilder();
            return this;
        }

        private State append(char c) {
            this.buffer.append(c);
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

    public static void main() {
        try {
            var parent = Paths.get(".", "src", "java", "magma");
            var source = parent.resolve("Main.java");
            var target = parent.resolve("main.ts");

            var input = Files.readString(source);
            Files.writeString(target, compile(input));

            new ProcessBuilder("cmd", "/c", "npm", "exec", "tsc")
                    .inheritIO()
                    .start()
                    .waitFor();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static String compile(String input) {
        var segments = divide(input);

        var output = new StringBuilder();
        for (var segment : segments) {
            output.append(compileRootSegment(segment));
        }

        return output.toString();
    }

    private static List<String> divide(String input) {
        var current = new State();
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            current = fold(current, c);
        }

        return current.advance().segments;
    }

    private static State fold(State state, char c) {
        var append = state.append(c);
        if (c == ';' && append.isLevel()) {
            return append.advance();
        }
        if (c == '{') {
            return append.enter();
        }
        if (c == '}') {
            return append.exit();
        }
        return append;
    }

    private static String compileRootSegment(String input) {
        var stripped = input.strip();
        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return "";
        }

        return compileFirst(stripped, "class ", (left, right) -> {
            return compileFirst(right, "{", (name, withEnd) -> {
                var strippedWithEnd = withEnd.strip();
                if (strippedWithEnd.endsWith("}")) {
                    var content = strippedWithEnd.substring(0, strippedWithEnd.length() - "}".length());
                    return Optional.of(generatePlaceholder(left) + "class " + name.strip() + " {" + generatePlaceholder(content) + "}");
                }

                return Optional.empty();
            });
        }).orElseGet(() -> generatePlaceholder(stripped));
    }

    private static Optional<String> compileFirst(String input, String infix, BiFunction<String, String, Optional<String>> mapper) {
        var classIndex = input.indexOf(infix);
        if (classIndex >= 0) {
            var left = input.substring(0, classIndex);
            var right = input.substring(classIndex + infix.length());
            return mapper.apply(left, right);
        }
        return Optional.empty();
    }

    private static String getValue(String left, String right) {
        return generatePlaceholder(left) + "class " + generatePlaceholder(right);
    }

    private static String generatePlaceholder(String input) {
        var replaced = input
                .replace("content-start", "content-start")
                .replace("content-end", "content-end");

        return "content-start " + replaced + " content-end";
    }
 */}