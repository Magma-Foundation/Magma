/* import java.io.IOException; */
/* import java.io.PrintWriter; */
/* import java.io.StringWriter; */
/* import java.nio.file.Files; */
/* import java.nio.file.Path; */
/* import java.nio.file.Paths; */
/* import java.util.ArrayList; */
/* import java.util.HashMap; */
/* import java.util.List; */
/* import java.util.Map; */
/* import java.util.Optional; */
/* import java.util.function.BiFunction; */
/* import java.util.function.Consumer; */
/* import java.util.function.Function; */
/* import java.util.function.Supplier; */
/* private static Tuple<CompileState, String> compileRootSegment(CompileState methods, String input) {
        String stripped = input.strip();
        if (stripped.startsWith("package ")) {
            return new Tuple<>(methods, "");
        }

        return compileClass(methods, stripped)
                .orElseGet(() -> new Tuple<>(methods, generatePlaceholder(stripped) + "\n"));
    } */
/* private static Option<Tuple<CompileState, String>> compileClass(CompileState state, String input) {
        int classIndex = input.indexOf("class ");
        if (classIndex < 0) {
            return Option.empty();
        }
        String modifiers = input.substring(0, classIndex);
        String afterKeyword = input.substring(classIndex + "class ".length());
        int contentStart = afterKeyword.indexOf("{");
        if (contentStart < 0) {
            return Option.empty();
        }
        String name = afterKeyword.substring(0, contentStart).strip();
        String withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
        if (!withEnd.endsWith("}")) {
            return Option.empty();
        }
        String inputContent = withEnd.substring(0, withEnd.length() - "}".length());
        if (!isSymbol(name)) {
            return Option.empty();
        }
        Tuple<CompileState, String> outputContent = compileStatements(state.pushStructName(name), inputContent, Main::compileClassSegment);
        String generated = generatePlaceholder(modifiers) + "struct " + name + " {" + outputContent.right + "};\n";
        return Option.of(new Tuple<>(outputContent.left
                .popStructName().addStruct(generated), ""));
    } */
/* private static boolean isSymbol(String input) {
        if (input.equals("private") || input.equals("record") || input.equals("public")) {
            return false;
        }

        for (int i = 0; i < input.length(); i++) {
            if (!Character.isLetter(input.charAt(i))) {
                return false;
            }
        }
        return true;
    } */
/* private static Tuple<CompileState, String> compileClassSegment(CompileState state, String input) {
        return compileClass(state, input)
                .or(() -> compileMethod(state, input))
                .orElseGet(() -> new Tuple<>(state, generatePlaceholder(input)));
    } */
/* private static Option<Tuple<CompileState, String>> compileMethod(CompileState state, String input) {
        String stripped = input.strip();
        int paramStart = stripped.indexOf("(");
        if (paramStart < 0) {
            return Option.empty();
        }
        String definition = stripped.substring(0, paramStart).strip();
        return compileDefinition(state, definition).flatMap(outputDefinition -> {
            String withParams = stripped.substring(paramStart + "(".length());
            int paramEnd = withParams.indexOf(")");
            if (paramEnd < 0) {
                return Option.empty();
            }
            String oldParameters = withParams.substring(0, paramEnd);
            Tuple<CompileState, String> paramTuple = compileValues(outputDefinition.left, oldParameters, Main::compileParameter);
            String withBraces = withParams.substring(paramEnd + ")".length()).strip();
            if (!withBraces.startsWith("{") || !withBraces.endsWith("}")) {
                return Option.empty();
            }
            String inputContent = withBraces.substring(1, withBraces.length() - 1);
            CompileState paramState = paramTuple.left;
            String paramOutput = paramTuple.right;

            Tuple<CompileState, String> outputContent = compileStatements(paramState, inputContent, Main::compileStatementOrBlock);
            String generated = outputDefinition.right + "(" + paramOutput + "){" + outputContent.right + "\n}\n";
            CompileState compileState = outputContent.left.addMethod(generated);
            return Option.of(new Tuple<>(compileState, ""));
        });
    } */
/* private static Tuple<CompileState, String> compileValues(CompileState state, String input, BiFunction<CompileState, String, Tuple<CompileState, String>> compiler) {
        return compileAll(state, input, Main::foldValueChar, compiler, Main::mergeValues);
    } */
/* private static Tuple<CompileState, String> compileStatementOrBlock(CompileState state, String input) {
        String stripped = input.strip();
        if (stripped.endsWith(";")) {
            String substring = stripped.substring(0, stripped.length() - ";".length());
            Tuple<CompileState, String> compiled = compileStatementValue(state, substring);
            return new Tuple<>(compiled.left, "\n\t" + compiled.right + ";");
        }

        return new Tuple<>(state, generatePlaceholder(stripped));
    } */
/* private static Tuple<CompileState, String> compileStatementValue(CompileState state, String input) {
        return parseInvocation(input, state)
                .map(tuple -> new Tuple<>(tuple.left, generateInvocation(tuple.right)))
                .orElseGet(() -> new Tuple<>(state, generatePlaceholder(input)));
    } */
/* private static Option<Tuple<CompileState, Node>> parseInvocation(String input, CompileState state) {
        String stripped = input.strip();
        if (!stripped.endsWith(")")) {
            return Option.empty();
        }
        String withoutEnd = stripped.substring(0, stripped.length() - ")".length());

        int paramStart = -1;
        int depth = 0;
        for (int i = withoutEnd.length() - 1; i >= 0; i--) {
            char c = withoutEnd.charAt(i);
            if (c == '(' && depth == 0) {
                paramStart = i;
                break;
            }
            if (c == ')') {
                depth++;
            }
            if (c == '(') {
                depth--;
            }
        }

        if (paramStart < 0) {
            return Option.empty();
        }
        String caller = withoutEnd.substring(0, paramStart).strip();
        String arguments = withoutEnd.substring(paramStart + "(".length()).strip();

        Tuple<CompileState, String> compiledCaller = compileValue(state, caller);
        Tuple<CompileState, String> compiledArguments = compileValues(compiledCaller.left, arguments, Main::compileValue);

        Node node = new Node()
                .withString("caller", compiledCaller.right)
                .withString("arguments", compiledArguments.right);

        return Option.of(new Tuple<>(compiledArguments.left, node));
    } */
/* private static String generateInvocation(Node node) {
        String caller = node.findString("caller").orElse("");
        String arguments = node.findString("arguments").orElse("");

        return caller + "(" + arguments + ")";
    } */
/* private static Tuple<CompileState, String> compileValue(CompileState state, String input) {
        return parseInvocation(input, state)
                .map(tuple -> new Tuple<>(tuple.left, generateInvocation(tuple.right)))
                .or(() -> compileDataAccess(state, input))
                .or(() -> compileSymbol(state, input))
                .orElseGet(() -> new Tuple<>(state, generatePlaceholder(input)));
    } */
/* private static Option<Tuple<CompileState, String>> compileSymbol(CompileState state, String input) {
        if (isSymbol(input.strip())) {
            return Option.of(new Tuple<>(state, input.strip()));
        }
        return Option.empty();
    } */
/* private static Option<Tuple<CompileState, String>> compileDataAccess(CompileState state, String input) {
        int propertySeparator = input.lastIndexOf(".");
        if (propertySeparator >= 0) {
            String parent = input.substring(0, propertySeparator);
            String property = input.substring(propertySeparator + ".".length());

            Tuple<CompileState, String> compiled = compileValue(state, parent);
            return Option.of(new Tuple<>(compiled.left, compiled.right + "." + property));
        }
        return Option.empty();
    } */
/* private static String mergeValues(String cache, String element) {
        if (cache.isEmpty()) {
            return element;
        }
        return cache + ", " + element;
    } */
/* private static DivideState foldValueChar(DivideState state, Character c) {
        if (c == ',') {
            return state.advance();
        }
        return state.append(c);
    } */
/* private static Tuple<CompileState, String> compileParameter(CompileState state, String element) {
        return compileDefinition(state, element).orElseGet(() -> new Tuple<>(state, generatePlaceholder(element)));
    } */
/* private static Option<Tuple<CompileState, String>> compileDefinition(CompileState state, String input) {
        String stripped = input.strip();
        int nameSeparator = stripped.lastIndexOf(" ");
        if (nameSeparator < 0) {
            return Option.empty();
        }
        String beforeName = stripped.substring(0, nameSeparator).strip();
        String name = stripped.substring(nameSeparator + " ".length()).strip();
        String newName;
        if (name.equals("main")) {
            newName = "__main__";
        }
        else {
            newName = name + "_" + state.structNames.last();
        }

        int typeSeparator = beforeName.lastIndexOf(" ");
        if (typeSeparator >= 0) {
            String beforeType = beforeName.substring(0, typeSeparator);
            String type = beforeName.substring(typeSeparator + " ".length());
            String outputType = compileType(type).orElseGet(() -> generatePlaceholder(type));

            String compiled = generatePlaceholder(beforeType) + " " + outputType;
            return new Some<>(new Tuple<>(state, compiled + " " + newName));
        }
        else {
            return compileType(beforeName).map(compiled -> {
                return new Tuple<>(state, compiled + " " + newName);
            });
        }
    } */
/* private static Option<String> compileType(String type) {
        String stripped = type.strip();
        if (stripped.endsWith("[]")) {
            return compileType(stripped.substring(0, stripped.length() - "[]".length()))
                    .map(result -> result + "*");
        }

        if (type.equals("boolean")) {
            return Option.of("int");
        }

        if (type.equals("char")) {
            return Option.of("char");
        }

        if (type.equals("void")) {
            return Option.of("void");
        }

        if (type.equals("String")) {
            return Option.of("char*");
        }

        if (isSymbol(type)) {
            return Option.of("struct " + stripped);
        }
        return Option.empty();
    } */
/* private static String generatePlaceholder(String input) {
        String replaced = input
                .replace("<comment-start>", "<comment-start>")
                .replace("<comment-end>", "<comment-end>");

        return "<comment-start> " + replaced + " <comment-end>";
    } */
/* } */
/* 

    private static  */struct DivideState {/* private final JavaList<String> segments; *//* 
        private final String buffer; *//* 
        private final int depth; *//* 

        private DivideState(JavaList<String> segments, String buffer, int depth) {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
        } *//* 

        public DivideState() {
            this(new JavaList<>(), "", 0);
        } *//* 
     */};
/* public  */struct Main {/* interface Result<T, X> {
        <R> R match(Function<T, R> whenOk, Function<X, R> whenErr);
    } *//* 

    interface Error {
        String display();
    } *//* 

    record Some<T>(T value) implements Option<T> {
        @Override
        public void ifPresent(Consumer<T> ifPresent) {
            ifPresent.accept(this.value);
        }

        @Override
        public Option<T> or(Supplier<Option<T>> other) {
            return this;
        }

        @Override
        public T orElseGet(Supplier<T> other) {
            return this.value;
        }

        @Override
        public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
            return mapper.apply(this.value);
        }

        @Override
        public <R> Option<R> map(Function<T, R> mapper) {
            return new Some<>(mapper.apply(this.value));
        }

        @Override
        public T orElse(T other) {
            return this.value;
        }
    } *//* 

    record CompileState(JavaList<String> structs, JavaList<String> methods, JavaList<String> structNames) {
        public CompileState() {
            this(new JavaList<>(), new JavaList<>(), new JavaList<>());
        }

        public CompileState addMethod(String method) {
            return new CompileState(this.structs, this.methods.addLast(method), this.structNames);
        }

        public CompileState addStruct(String struct) {
            return new CompileState(this.structs.addLast(struct), this.methods, this.structNames);
        }

        public CompileState pushStructName(String structName) {
            return new CompileState(this.structs, this.methods, this.structNames.addLast(structName));
        }

        public CompileState popStructName() {
            return new CompileState(this.structs, this.methods, this.structNames.removeLast());
        }
    } *//* 

    record Ok<T, X>(T value) implements Result<T, X> {
        @Override
        public <R> R match(Function<T, R> whenOk, Function<X, R> whenErr) {
            return whenOk.apply(this.value);
        }
    } *//* 

    record Err<T, X>(X error) implements Result<T, X> {
        @Override
        public <R> R match(Function<T, R> whenOk, Function<X, R> whenErr) {
            return whenErr.apply(this.error);
        }
    } *//* 

    record ThrowableError(Throwable throwable) implements Error {
        @Override
        public String display() {
            StringWriter writer = new StringWriter();
            this.throwable.printStackTrace(new PrintWriter(writer));
            return writer.toString();
        }
    } *//* 

    record Node(Map<String, String> strings) {
        public Node() {
            this(new HashMap<>());
        }

        public Node withString(String propertyKey, String propertyValue) {
            this.strings.put(propertyKey, propertyValue);
            return this;
        }

        public Option<String> findString(String propertyKey) {
            if (this.strings.containsKey(propertyKey)) {
                return new Some<>(this.strings.get(propertyKey));
            }
            else {
                return new None<>();
            }
        }
    } *//* ' && appended.isShallow()) {
            return appended.advance().exit();
        } *//* 
        if (c == '{') {
            return appended.enter();
        }
        if (c == '} *//* ') {
            return appended.exit();
        } *//* 
        return appended; *//* 
     */};
/* interface Option<T> {
        static <T> */ /* Option<T> */ of_Main(struct T value_Main){
	/* return new Some<> */(value);
	/* }

        static <T> Option<T> empty() {
            return new None<> */();
	/* }

        void ifPresent(Consumer<T> ifPresent);

        Option<T> or(Supplier<Option<T>> other);

        T orElseGet(Supplier<T> other);

        <R> Option<R> flatMap(Function<T, Option<R>> mapper);

        <R> Option<R> map(Function<T, R> mapper);

        T orElse */(/* T other */);
}
/* private */ int isShallow_DivideState(/*  */){
	/* return this.depth == 1 */;/*  */
}
/* private */ int isLevel_DivideState(/*  */){
	/* return this.depth == 0 */;/*  */
}
/* private */ struct DivideState append_DivideState(char c_DivideState){
	/* return new DivideState */(this.segments, this.buffer + c, this.depth);/*  */
}
/* private */ struct DivideState advance_DivideState(/*  */){
	/* return new DivideState */(this.segments.addLast(this.buffer), /*  "" */, this.depth);/*  */
}
/* private */ struct DivideState enter_DivideState(/*  */){
	/* return new DivideState */(this.segments, this.buffer, this.depth + 1);/*  */
}
/* private */ struct DivideState exit_DivideState(/*  */){
	/* return new DivideState */(this.segments, this.buffer, this.depth - 1);/*  */
}
/* private */ /* record */ JavaList<T>_Main(/* List<T> list */){/* public JavaList() {
            this(new ArrayList<>());
        } *//* public JavaList<T> addLast(T element) {
            ArrayList<T> copy = new ArrayList<>(this.list);
            copy.add(element);
            return new JavaList<>(copy);
        } *//* public JavaList<T> removeLast() {
            return new JavaList<>(this.list.subList(0, this.list.size() - 1));
        } *//* public T last() {
            return this.list.getLast();
        } *//*  */
}
/* private record */ /* Tuple<A, */ B>_Main(struct A left_Main, struct B right_Main){/*  */
}
/* static class None<T> implements Option<T> {
        @Override
        public */ void ifPresent_Main(/* Consumer<T> ifPresent */){
	/* }

        @Override
        public Option<T> or(Supplier<Option<T>> other) {
            return other */.get();
	/* }

        @Override
        public T orElseGet(Supplier<T> other) {
            return other */.get();
	/* }

        @Override
        public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
            return new None<> */();
	/* }

        @Override
        public <R> Option<R> map(Function<T, R> mapper) {
            return new None<> */();
	/* }

        @Override
        public T orElse(T other) {
            return other */;/* } */
}
/* public static */ void __main__(char** args_Main){
	run().ifPresent(/* error -> System */.err.println(error.display()));/*  */
}
/* private static */ /* Option<Error> */ run_Main(/*  */){
	/* Path source = Paths */.get(/* " */.", /*  "src" */, /*  "java" */, /*  "magma" */, /*  "Main */.java");
	/* return readString */(source).match(/* input -> runWithInput(source */, /*  input) */, /*  Option::of */).map(/* ThrowableError::new */);/*  */
}
/* private static */ /* Option<IOException> */ runWithInput_Main(struct Path source_Main, char* input_Main){
	/* Path target = source */.resolveSibling(/* "main */.c");
	/* String compile = compile */(input);
	/* return writeString */(target, compile).or(/* Main::build */);/*  */
}
/* private static */ /* Option<IOException> */ build_Main(/*  */){/* return start().match(process -> {
            try {
                process.waitFor();
                return Option.empty();
            } catch (InterruptedException e) {
                return Option.of(new IOException(e));
            }
        } */
	/* , Option::of) */;/*  */
}
/* private static Result<Process, */ /* IOException> */ start_Main(/*  */){/* try {
            return new Ok<>(new ProcessBuilder("cmd.exe", "/c", "build.bat")
                    .inheritIO()
                    .start()
            );
        } *//* catch (IOException e) {
            return new Err<>(e);
        } *//*  */
}
/* private static */ /* Option<IOException> */ writeString_Main(struct Path target_Main, char* compile_Main){/* try {
            Files.writeString(target, compile);
            return Option.empty();
        } *//* catch (IOException e) {
            return Option.of(e);
        } *//*  */
}
/* private static Result<String, */ /* IOException> */ readString_Main(struct Path path_Main){/* try {
            return new Ok<>(Files.readString(path));
        } *//* catch (IOException e) {
            return new Err<>(e);
        } *//*  */
}
/* private static */ char* compile_Main(char* input_Main){
	/* Tuple<CompileState, String> compiled = compileStatements */(/* new CompileState */(), input, /*  Main::compileRootSegment */);
	/* CompileState newState = compiled.left */;
	/* String output = compiled.right */;
	/* String joinedStructs = String */.join(/* "" */, newState.structs.list);
	/* String joinedMethods = String */.join(/* "" */, newState.methods.list);
	/* String joined = output + joinedStructs + joinedMethods */;/* return joined + "int main(){\n\treturn 0;\n} */
	/* \n" */;/*  */
}
/* private static Tuple<CompileState, */ /* String> */ compileStatements_Main(struct CompileState methods_Main, char* input_Main, /* 
            BiFunction<CompileState */, /*  String */, /*  Tuple<CompileState */, /*  String>> compiler
     */){
	/* return compileAll */(methods, input, /*  Main::foldStatementChar */, compiler, /*  Main::mergeStatements */);/*  */
}
/* private static Tuple<CompileState, */ /* String> */ compileAll_Main(struct CompileState methods_Main, char* input_Main, /*  BiFunction<DivideState */, /*  Character */, /*  DivideState> folder */, /*  BiFunction<CompileState */, /*  String */, /*  Tuple<CompileState */, /*  String>> compiler */, /*  BiFunction<String */, /*  String */, /*  String> merger */){
	/* List<String> segments = divide */(input, folder);
	/* CompileState current = methods */;
	/* String output = "" */;/* for (String segment : segments) {
            Tuple<CompileState, String> compiled = compiler.apply(current, segment);
            current = compiled.left;
            output = merger.apply(output, compiled.right);
        } */
	/* return new Tuple<> */(current, output);/*  */
}
/* private static */ /* List<String> */ divide_Main(char* input_Main, /*  BiFunction<DivideState */, /*  Character */, /*  DivideState> folder */){
	/* DivideState current = new DivideState */();
	/* for (int i = 0 */;
	/* i < input */.length();/* i++) {
            char c = input.charAt(i);
            current = folder.apply(current, c);
        } */
	/* return current.advance().segments.list */;/*  */
}
/* private static */ char* mergeStatements_Main(char* buffer_Main, char* element_Main){
	/* return buffer + element */;/*  */
}
/* private static */ struct DivideState foldStatementChar_Main(struct DivideState current_Main, char c_Main){
	/* DivideState appended = current */.append(c);
	/* if (c == ' */;/* ' && appended.isLevel()) {
            return appended.advance();
        } *//* if (c == ' */
}
int main(){
	return 0;
}
