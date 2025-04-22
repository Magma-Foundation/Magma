/* package magma; */
/* import java.io.IOException; */
/* import java.nio.file.Files; */
/* import java.nio.file.Path; */
/* import java.nio.file.Paths; */
/* import java.util.ArrayList; */
/* import java.util.List; */
/* import java.util.Optional; */
/* import java.util.function.BiFunction; */
/* import java.util.function.Function; */
/* 

public  */struct Main {/* private record State(List<String> segments, StringBuilder buffer, int depth) {
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

        public boolean isShallow() {
            return this.depth == 1;
        }
    } *//* 

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
    } *//* 

    private static String compileRoot(String input) {
        return compileAll(input, Main::compileRootSegment);
    } *//* 

    private static String compileAll(String input, Function<String, String> compiler) {
        State current = State.createEmpty();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            current = foldStatementChar(current, c);
        }
        List<String> copy = current.advance().segments;

        StringBuilder output = new StringBuilder();
        for (String segment : copy) {
            output.append(compiler.apply(segment));
        }
        return output.toString();
    } *//* 

    private static State foldStatementChar(State state, char c) {
        State appended = state.append(c);
        if (c == ';' && appended.isLevel()) {
            return appended.advance();
        }
        if (c == '} *//* ' && appended.isShallow()) {
            return appended.exit().advance();
        } *//* 
        if (c == '{') {
            return appended.enter();
        }
        if (c == '} *//* ') {
            return appended.exit();
        } *//* 
        return appended; *//* 
     */
};
/* private static String compileRootSegment(String input) {
        return compileClass(input).orElseGet(() -> generatePlaceholder(input.strip()) + "\n");
    } */
/* 

    private static Optional<String> compileClass(String input) {
        return compileInfix(input, " */struct ", (modifiers, afterKeyword) ->
                compileInfix(afterKeyword, " {/* ", (left, withEnd) ->
                        compileStripped(withEnd, withEnd2 ->
                                compileSuffix(withEnd2, "}", content ->
                                        compileStripped(left, name -> {
                                            return Optional.of(generatePlaceholder(modifiers) + "struct " + name + " {" +
                                                    compileAll(content, Main::compileClassSegment) +
                                                    "\n} *//* ; *//* \n"); *//* 
                                        })))));
     */
};
/* private static String compileClassSegment(String classSegment) {
        return generatePlaceholder(classSegment);
    } */
/* private static Optional<String> compileInfix(String input, String infix, BiFunction<String, String, Optional<String>> mapper) {
        int index = input.indexOf(infix);
        if (index < 0) {
            return Optional.empty();
        }
        String left = input.substring(0, index);
        String right = input.substring(index + infix.length());
        return mapper.apply(left, right);
    } */
/* private static Optional<String> compileStripped(String input, Function<String, Optional<String>> mapper) {
        return mapper.apply(input.strip());
    } */
/* private static Optional<String> compileSuffix(String input, String suffix, Function<String, Optional<String>> mapper) {
        if (!input.endsWith(suffix)) {
            return Optional.empty();
        }
        String slice = input.substring(0, input.length() - suffix.length());
        return mapper.apply(slice);
    } */
/* private static String generatePlaceholder(String stripped) {
        return "/* " + stripped + " */";
    } */
/* } */
