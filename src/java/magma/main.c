struct DivideState {
	/* private */ /* List<String> */ segments;
	/* private */ int depth;
	/* private */ char* buffer;
	/* private DivideState(List<String> segments, String buffer, int depth) {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
        }

        public DivideState() {
            this(Lists.empty(), "", 0);
        }

        private Stream<String> stream() {
            return this.segments.stream();
        }

        private DivideState advance() {
            this.segments = this.segments.add(this.buffer);
            this.buffer = "";
            return this;
        }

        private DivideState append(char c) {
            this.buffer = this.buffer + c;
            return this;
        }

        public boolean isLevel() {
            return this.depth == 0;
        }

        public DivideState enter() {
            this.depth++;
            return this;
        }

        public DivideState exit() {
            this.depth--;
            return this;
        }
    }

    private record Tuple<A, B>(A left, B right) {
    }

    private record CompilerState(List<String> structs) {
        public CompilerState() {
            this(Lists.empty());
        }

        public CompilerState add(String element) {
            return new CompilerState(this.structs.add(element));
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
        Tuple<CompilerState, String> tuple = compileStatements(input, new */ /* CompilerState(), */ Main::compileRootSegment);
	/* CompilerState elements */ /* = */ tuple.left.add(tuple.right);
	/* String joined = */ /* elements.structs.stream() */ .collect(Collectors.joining());
	/* return joined + "int */ /* main(){\n\treturn */ 0;\n}\n";
	/* }

    private static Tuple<CompilerState, String> compileStatements(String input, CompilerState structs, BiFunction<CompilerState, String, Tuple<CompilerState, String>> compiler) {
        return divideStatements(input).reduce(new Tuple<>(structs, ""), (tuple, element) -> foldSegment(tuple, element, compiler), (_, next) */ /* -> */ next);
	/* }

    private static Tuple<CompilerState, String> foldSegment(Tuple<CompilerState, String> tuple, String element, BiFunction<CompilerState, String, Tuple<CompilerState, String>> compiler) {
        CompilerState currentStructs */ /* = */ tuple.left;
	/* String currentOutput */ /* = */ tuple.right;
	/* Tuple<CompilerState, String> compiledStruct = */ /* compiler.apply(currentStructs, */ element);
	/* CompilerState compiledStructs */ /* = */ compiledStruct.left;
	/* String compiledElement */ /* = */ compiledStruct.right;
	/* return new Tuple<>(compiledStructs, currentOutput */ /* + */ compiledElement);
	/* }

    private static Stream<String> divideStatements(String input) {
        DivideState current = */ /* new */ DivideState();
	/* for (int i */ /* = */ 0;
	/* i */ /* < */ input.length();
	/* i++) {
            char c = input.charAt(i);
            current = divideStatementChar(current, c);
        } */ /* return */ current.advance().stream();
	/* }

    private static DivideState divideStatementChar(DivideState divideState, char c) {
        DivideState appended */ /* = */ divideState.append(c);
	/* if (c */ /* == */ ';
	/* ' && appended.isLevel()) {
            return appended.advance();
        }
        if (c == '{') {
            return appended.enter();
        }
        if (c == '}') {
            return appended.exit();
        } */ /* return */ appended;
	/* }

    private static Tuple<CompilerState, String> compileRootSegment(CompilerState state, String input) {
        String stripped */ /* = */ input.strip();
	/* if (stripped.startsWith("package ")) {
            return new Tuple<>(state, "");
        }

        if (stripped.startsWith("import ")) {
            return new Tuple<>(state, "// #include <temp.h>\n");
        }

        return compileClass(state, stripped).orElseGet(() -> */ /* generatePlaceholderToTuple(state, */ stripped));
	/* }

    private static Tuple<CompilerState, String> generatePlaceholderToTuple(CompilerState state, String stripped) {
        return new */ /* Tuple<>(state, */ generatePlaceholder(stripped));
	/* }

    private static String generatePlaceholder(String stripped) {
        return "/* " + stripped + */ /* " */ */";
	/* }

    private static Optional<Tuple<CompilerState, String>> compileClass(CompilerState state, String stripped) {
        int classIndex = */ /* stripped.indexOf("class */ ");
	/* if (classIndex < 0) {
            return Optional.empty();
        }

        String afterKeyword = stripped.substring(classIndex + */ /* "class */ ".length());
	/* int contentStart = afterKeyword.indexOf("{");
        if (contentStart < 0) {
            return Optional.empty();
        }

        String name = afterKeyword.substring(0, contentStart).strip();
        String withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
        if (!withEnd.endsWith("}")) {
            return Optional.empty();
        }

        String inputContent = withEnd.substring(0, withEnd.length() */ /* - */ "}".length());
	/* Tuple<CompilerState, String> outputTuple = compileStatements(inputContent, */ /* state, */ Main::compileClassSegment);
	/* CompilerState outputStructs */ /* = */ outputTuple.left;
	/* String outputContent */ /* = */ outputTuple.right;
	/* String generated = "struct */ /* %s */ {%s\n};
	/* \n".formatted(name, */ outputContent);
	/* CompilerState withGenerated */ /* = */ outputStructs.add(generated);
	/* return Optional.of(new */ /* Tuple<>(withGenerated, */ ""));
	/* }

    private static Tuple<CompilerState, String> compileClassSegment(CompilerState state, String input) {
        String stripped */ /* = */ input.strip();
	/* return compileClass(state, stripped)
                .or(() -> compileDefinition(state, stripped))
                .orElseGet(() -> */ /* generatePlaceholderToTuple(state, */ stripped));
	/* }

    private static Optional<Tuple<CompilerState, String>> compileDefinition(CompilerState state, String input) { */ /* if */ (!input.endsWith(";
	/* ")) {
            return Optional.empty();
        }

        String withoutEnd = input.substring(0, input.length() */ /* - */ ";/* ".length()).strip(); */
	/* int nameSeparator = */ /* withoutEnd.lastIndexOf(" */ ");
	/* if (nameSeparator < 0) {
            return Optional.empty();
        }

        String beforeName = */ /* withoutEnd.substring(0, */ nameSeparator).strip();
	/* int typeSeparator = */ /* beforeName.lastIndexOf(" */ ");
	char* outputBeforeString;
	/* if (typeSeparator >= 0) {
            String beforeType = beforeName.substring(0, typeSeparator).strip();
            String type = beforeName.substring(typeSeparator + " ".length()).strip();
            outputBeforeString = generatePlaceholder(beforeType) + " " + compileType(type);
        }
        else {
            outputBeforeString = compileType(beforeName);
        }

        String name = withoutEnd.substring(nameSeparator + */ /* " */ ".length()).strip();
	/* return Optional.of(new Tuple<>(state, "\n\t" + outputBeforeString + " " + name */ /* + */ ";/* ")); */
	/* }

    private static String compileType(String input) {
        String stripped */ /* = */ input.strip();
	/* if (stripped.equals("int")) {
            return "int";
        }
        if (stripped.equals("String")) {
            return "char*";
        } */ /* return */ generatePlaceholder(stripped);/*  */
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
