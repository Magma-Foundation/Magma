/*public */class Main {/*private static */class State {/*private final List<String> segments;*//*
        private StringBuilder buffer;*//*
        private int depth;*//*

        private State(List<String> segments, StringBuilder buffer, int depth) {
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

        public boolean isLevel() {
            return this.depth == 0;
        }

        public State enter() {
            this.depth++;
            return this;
        }

        public State exit() {
            this.depth--;
            return this;
        }
    }

    public static void main() {
        var source = Paths.get(".", "src", "java", "magma", "Main.java");*//*
        var target = source.resolveSibling("main.ts");*//*
        try {
            var input = Files.readString(source);
            Files.writeString(target, compileRoot(input));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String compileRoot(String input) {
        return compileSegments(input, Main::compileRootSegment);*//*
    }

    private static String compileSegments(String input, Function<String, String> mapper) {
        var divisions = divide(input);*//*
        var output = new StringBuilder();*//*
        for (var segment : divisions) {
            output.append(mapper.apply(segment));
        }

        return output.toString();*//*
    }

    private static List<String> divide(String input) {
        var current = new State();*//*

        for (var i = 0;*//* i < input.length();*//* i++) {
            var c = input.charAt(i);
            current = fold(current, c);
        }

        return current.advance().segments;*//*
    }

    private static State fold(State state, char c) {
        var appended = state.append(c);*//*
        if (c == ';*//*' && appended.isLevel()) {
            return appended.advance();
        }
        if (c == '{') {
            return appended.enter();
        }
        if (c == '}') {
            return appended.exit();
        }
        return appended;*//*
    }

    private static String compileRootSegment(String input) {
        var stripped = input.strip();*//*
        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return "";
        }

        return compileClassSegment(stripped);*//*
    }

    private static String compileClassSegment(String input) {
        return compileInfix(input, "class ", (beforeKeyword, right1) -> {
            return compileInfix(right1, "{", (name, withEnd) -> {
                return compileSuffix(withEnd.strip(), "}", inputContent -> {
                    var outputContent = compileSegments(inputContent, Main::compileClassSegment);
                    return Optional.of(placeholder(beforeKeyword) + "class " + name.strip() + " {" + outputContent + "}");
                });
            });
        }).orElseGet(() -> placeholder(input));*//*
    }

    private static Optional<String> compileSuffix(String input, String suffix, Function<String, Optional<String>> mapper) {
        if (input.endsWith(suffix)) {
            var content = input.substring(0, input.length() - suffix.length());
            return mapper.apply(content);
        }
        else {
            return Optional.empty();
        }
    }

    private static Optional<String> compileInfix(String stripped, String infix, BiFunction<String, String, Optional<String>> mapper) {
        var index = stripped.indexOf(infix);*//*
        if (index >= 0) {
            var left = stripped.substring(0, index);
            var right = stripped.substring(index + infix.length());
            return mapper.apply(left, right);
        }

        return Optional.empty();*//*
    }

    private static String placeholder(String input) {
        var replaced = input
                .replace("start", "start")
                .replace("end", "end");*//*

        return "start" + replaced + "end";*//*
    */}}