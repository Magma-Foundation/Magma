/* public  */class Main {
	/* private static  */class State {
		/* private final */ segments : /* List<String> */;
		/* private */ buffer : /* StringBuilder */;
		/* private */ depth : /* int */;/* 

        public State(List<String> segments, StringBuilder buffer, int depth) {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
        } *//* 

        public State() {
            this(new ArrayList<>(), new StringBuilder(), 0);
        } *//* 

        private State advance() {
            this.segments.add(this.buffer.toString());
            this.buffer = new StringBuilder();
            return this;
        } *//* 

        private State append(char c) {
            this.buffer.append(c);
            return this;
        } *//* 

        public State enter() {
            this.depth++;
            return this;
        } *//* 

        public boolean isLevel() {
            return this.depth == 0;
        } *//* 

        public State exit() {
            this.depth--;
            return this;
        } *//* 

        public boolean isShallow() {
            return this.depth == 1;
        } *//* 
     */}/* 

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
    } *//* 

    private static String compile(String input) {
        return compileStatements(input, Main::compileRootSegment);
    } *//* 

    private static String compileStatements(String input, Function<String, String> mapper) {
        var segments = divide(input);

        var output = new StringBuilder();
        for (var segment : segments) {
            output.append(mapper.apply(segment));
        }

        return output.toString();
    } *//* 

    private static List<String> divide(String input) {
        var current = new State();
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            current = fold(current, c);
        }

        return current.advance().segments;
    } *//* 

    private static State fold(State state, char c) {
        var append = state.append(c);
        if (c == ';' && append.isLevel()) {
            return append.advance();
        }
        if (c == '} *//* ' && append.isShallow()) {
            return append.advance().exit();
        } *//* 
        if (c == '{') {
            return append.enter();
        }
        if (c == '} *//* ') {
            return append.exit();
        } *//* 
        return append; *//* 
     */
}
/* private static String compileRootSegment(String input) {
        var stripped = input.strip();
        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return "";
        }

        return compileClass(stripped, 0).orElseGet(() -> generatePlaceholder(stripped));
    } *//* private static Optional<String> compileClass(String stripped, int depth) {
        return compileFirst(stripped, "class ", (left, right) -> {
            return compileFirst(right, "{", (name, withEnd) -> {
                var strippedWithEnd = withEnd.strip();
                return compileSuffix(strippedWithEnd, "}", content1 -> {
                    var strippedName = name.strip();
                    if (!isSymbol(strippedName)) {
                        return Optional.empty();
                    }

                    var beforeIndent = depth == 0 ? "" : "\n\t";
                    var afterIndent = depth == 0 ? "\n" : "";

                    var statements = compileStatements(content1, input -> compileClassSegment(input, depth + 1));
                    return Optional.of(beforeIndent + generatePlaceholder(left) + "class " + strippedName + " {" + statements + afterIndent + "}" + afterIndent);
                });
            });
        });
    } *//* private static boolean isSymbol(String input) {
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (Character.isLetter(c)) {
                continue;
            }
            return false;
        }
        return true;
    } *//* private static Optional<String> compileSuffix(String input, String suffix, Function<String, Optional<String>> mapper) {
        if (!input.endsWith(suffix)) {
            return Optional.empty();
        }

        var slice = input.substring(0, input.length() - suffix.length());
        return mapper.apply(slice);
    } *//* private static String compileClassSegment(String input, int depth) {
        return compileClass(input, depth)
                .or(() -> compileDefinitionStatement(input, depth))
                .orElseGet(() -> generatePlaceholder(input));
    } *//* private static Optional<String> compileDefinitionStatement(String input, int depth) {
        return compileSuffix(input.strip(), ";", withoutEnd -> {
            return compileLast(withoutEnd, " ", (s, name) -> {
                return compileLast(s, " ", (beforeType, type) -> {
                    return Optional.of("\n" + "\t".repeat(depth) + generatePlaceholder(beforeType) + " " + name.strip() + " : " + generatePlaceholder(type) + ";");
                });
            });
        });
    } *//* private static Optional<String> compileLast(String input, String infix, BiFunction<String, String, Optional<String>> mapper) {
        return compileInfix(input, infix, Main::findLast, mapper);
    } *//* private static Optional<Integer> findLast(String input, String infix) {
        var index = input.lastIndexOf(infix);
        return index == -1 ? Optional.empty() : Optional.of(index);
    } *//* private static Optional<String> compileFirst(String input, String infix, BiFunction<String, String, Optional<String>> mapper) {
        return compileInfix(input, infix, Main::findFirst, mapper);
    } *//* private static Optional<String> compileInfix(String input, String infix, BiFunction<String, String, Optional<Integer>> locator, BiFunction<String, String, Optional<String>> mapper) {
        return locator.apply(input, infix).flatMap(index -> {
            var left = input.substring(0, index);
            var right = input.substring(index + infix.length());
            return mapper.apply(left, right);
        });
    } *//* private static Optional<Integer> findFirst(String input, String infix) {
        var index = input.indexOf(infix);
        return index == -1 ? Optional.empty() : Optional.of(index);
    } *//* private static String generatePlaceholder(String input) {
        var replaced = input
                .replace("content-start", "content-start")
                .replace("content-end", "content-end");

        return "content-start " + replaced + " content-end";
    } *//* } */