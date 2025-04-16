struct State {/* private final List<String> segments; *//* private int depth; *//* private StringBuilder buffer; *//* private State(List<String> segments, StringBuilder buffer, int depth) {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
        }

        public State() {
            this(new ArrayList<>(), new StringBuilder(), 0);
        }

        private Stream<String> stream() {
            return this.segments.stream();
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

    private record Tuple<A, B>(A left, B right) {
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
        Tuple<List<String>, String> tuple = compileStatements(input, new ArrayList<String>(), Main::compileRootSegment); *//* List<String> elements = new ArrayList<>(tuple.left); *//* elements.add(tuple.right); *//* String joined = String.join("", elements); *//* return joined + "int main(){\n\treturn 0;\n}\n"; *//* }

    private static Tuple<List<String>, String> compileStatements(String input, List<String> structs, BiFunction<List<String>, String, Tuple<List<String>, String>> compiler) {
        return divideStatements(input).reduce(new Tuple<>(structs, ""), (tuple, element) -> foldSegment(tuple, element, compiler), (_, next) -> next); *//* }

    private static Tuple<List<String>, String> foldSegment(Tuple<List<String>, String> tuple, String element, BiFunction<List<String>, String, Tuple<List<String>, String>> compiler) {
        List<String> currentStructs = tuple.left; *//* String currentOutput = tuple.right; *//* Tuple<List<String>, String> compiledStruct = compiler.apply(currentStructs, element); *//* List<String> compiledStructs = compiledStruct.left; *//* String compiledElement = compiledStruct.right; *//* return new Tuple<>(compiledStructs, currentOutput + compiledElement); *//* }

    private static Stream<String> divideStatements(String input) {
        State current = new State(); *//* for (int i = 0; *//* i < input.length(); *//* i++) {
            char c = input.charAt(i);
            current = divideStatementChar(current, c);
        }
        return current.advance().stream(); *//* }

    private static State divideStatementChar(State state, char c) {
        State appended = state.append(c); *//* if (c == '; *//* ' && appended.isLevel()) {
            return appended.advance();
        }
        if (c == '{') {
            return appended.enter();
        }
        if (c == '}') {
            return appended.exit();
        }
        return appended; *//* }

    private static Tuple<List<String>, String> compileRootSegment(List<String> structs, String input) {
        String stripped = input.strip(); *//* if (stripped.startsWith("package ")) {
            return new Tuple<>(structs, "");
        }

        if (stripped.startsWith("import ")) {
            return new Tuple<>(structs, "// #include <temp.h>\n");
        }

        int classIndex = stripped.indexOf("class "); *//* if (classIndex >= 0) {
            String afterKeyword = stripped.substring(classIndex + "class ".length());
            int contentStart = afterKeyword.indexOf("{");
            if (contentStart >= 0) {
                String name = afterKeyword.substring(0, contentStart).strip();
                String withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
                if (withEnd.endsWith("}")) {
                    String inputContent = withEnd.substring(0, withEnd.length() - "}".length());
                    Tuple<List<String>, String> outputTuple = compileStatements(inputContent, structs, Main::compileRootSegment);
                    List<String> outputStructs = outputTuple.left;
                    String outputContent = outputTuple.right;

                    List<String> copy = new ArrayList<>(outputStructs);
                    String generated = "struct %s {%s\n};\n".formatted(name, outputContent);
                    copy.add(generated);

                    return new Tuple<>(copy, "");
                }
            }
        }

        return new Tuple<>(structs, "/* " + stripped + " */"); *//*  */
};
struct Main {
};
// #include <temp.h>
// #include <temp.h>
// #include <temp.h>
// #include <temp.h>
// #include <temp.h>
// #include <temp.h>
// #include <temp.h>
// #include <temp.h>
int main(){
	return 0;
}
