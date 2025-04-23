/* package magma; */
/* import java.io.IOException; */
/* import java.nio.file.Files; */
/* import java.nio.file.Path; */
/* import java.nio.file.Paths; */
/* import java.util.ArrayList; */
/* import java.util.Arrays; */
/* import java.util.Collections; */
/* import java.util.List; */
/* import java.util.function.BiFunction; */
/* import java.util.function.Consumer; */
/* import java.util.function.Function; */
/* import java.util.function.Supplier; */
/* import java.util.stream.Collectors; */
/* import static magma.StandardLibrary.emptyString; */
struct Node {
};
struct String_ {
	struct String_ concatChar(struct String_ this, char c);
	char* toSlice(struct String_ this);
};
struct State {
	List<struct String_> segments;
	struct String_ buffer;
	int depth;
};
struct Definition {
	List<char*> modifiers;
	char* value;
};
struct ConstructorHeader {
	char* value;
};
struct Main {
	void ifPresent(struct Main this, Consumer<struct T> consumer);/* 

    sealed interface Result<T, X> permits Ok, Err {
    } *//* 

    record Ok<T, X>(T value) implements Result<T, X> {
    } *//* 

    record Err<T, X>(X error) implements Result<T, X> {
    } *//* 

    private record Some<T>(T value) implements Optional<T> {
        @Override
        public void ifPresent(Consumer<T> consumer) {
            consumer.accept(this.value);
        }

        @Override
        public T orElse(T other) {
            return this.value;
        }

        @Override
        public <R> Optional<R> map(Function<T, R> mapper) {
            return new Some<>(mapper.apply(this.value));
        }

        @Override
        public <R> Optional<R> flatMap(Function<T, Optional<R>> mapper) {
            return mapper.apply(this.value);
        }

        @Override
        public Optional<T> or(Supplier<Optional<T>> other) {
            return this;
        }

        @Override
        public boolean isPresent() {
            return true;
        }

        @Override
        public T orElseGet(Supplier<T> other) {
            return this.value;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }
    } */
	List<char*> structs = new_ArrayList<>();
	struct Path SOURCE = Paths.get(".", "src", "java", "magma", "Main.java");
	struct Path TARGET = Paths.get(".", "main.c");
	List<char*> methods = new_ArrayList<>();
};
struct State new_State(List<struct String_> segments, struct String_ buffer, int depth){
	struct State this;
	this.segments = segments;
	this.buffer = buffer;
	this.depth = depth;
	return this;
}
struct State createInitial(){
	return new_State(new_ArrayList<>(), emptyString(), 0);
}
int isLevel(struct State this){
	return this.depth == 0;
}
int isShallow(struct State this){
	return this.depth == 1;
}
void advance(struct State this){
	this.segments.add(this.buffer);
	this.buffer = emptyString();
}
void exit(struct State this){
	this.depth = this.depth - 1;
}
void enter(struct State this){
	this.depth = this.depth + 1;
}
void append(struct State this, char c){
	this.buffer = this.buffer.concatChar(c);
}
void ifPresent(struct Some<T> this, Consumer<struct T> consumer){
	consumer.accept(this.value);
}
struct T orElse(struct Some<T> this, struct T other){
	return this.value;
}
Optional<struct R> map(struct Some<T> this, /* Function<T */, /* R> */ mapper){
	return new_Some<>(mapper.apply(this.value));
}
Optional<struct R> flatMap(struct Some<T> this, /* Function<T */, Optional</* R> */> mapper){
	return mapper.apply(this.value);
}
Optional<struct T> or(struct Some<T> this, Supplier<Optional<struct T>> other){
	return this;
}
int isPresent(struct Some<T> this){
	return true;
}
struct T orElseGet(struct Some<T> this, Supplier<struct T> other){
	return this.value;
}
int isEmpty(struct Some<T> this){
	return false;
}
void ifPresent(struct None<T> implements Optional<T> this, Consumer<struct T> consumer){
}
struct T orElse(struct None<T> implements Optional<T> this, struct T other){
	return other;
}
Optional<struct R> map(struct None<T> implements Optional<T> this, /* Function<T */, /* R> */ mapper){
	return new_None<>();
}
Optional<struct R> flatMap(struct None<T> implements Optional<T> this, /* Function<T */, Optional</* R> */> mapper){
	return new_None<>();
}
Optional<struct T> or(struct None<T> implements Optional<T> this, Supplier<Optional<struct T>> other){
	return other.get();
}
int isPresent(struct None<T> implements Optional<T> this){
	return false;
}
struct T orElseGet(struct None<T> implements Optional<T> this, Supplier<struct T> other){
	return other.get();
}
int isEmpty(struct None<T> implements Optional<T> this){
	return true;
}
void ifPresent(Consumer<struct T> consumer){
	/* }

        @Override
        public T orElse(T other) {
            return other */;
	/* }

        @Override
        public <R> Optional<R> map */(/* Function<T */, /* R> mapper) {
            return new None<>( */);
	/* }

        @Override
        public <R> Optional<R> flatMap */(/* Function<T */, /* Optional<R>> mapper) {
            return new None<>( */);
	/* }

        @Override
        public Optional<T> or */(/* Supplier<Optional<T>> other) {
            return other.get( */);
	/* }

        @Override
        public boolean isPresent() {
            return false */;
	/* }

        @Override
        public T orElseGet */(/* Supplier<T> other) {
            return other.get( */);
	/* }

        @Override
        public boolean isEmpty() {
            return true */;
	/* } */
}
void __main__(char** args){
	/* Optional<IOException> result = switch (readString(SOURCE)) {
            case Err<String, IOException>(IOException error) -> new Some<>(error);
            case Ok<String, IOException>(String value) -> {
                String output = compile(value);
                yield switch (writeString(TARGET, output)) {
                    case None<IOException> _ -> build();
                    case Some<IOException>(IOException error) -> new Some<>(error);
                };
            }
        } */
	/*  */;
	result.ifPresent(/* Throwable::printStackTrace */);
}
Optional<struct IOException> build(){
	/* try {
            new ProcessBuilder("clang", "-o", "main.exe", "main.c")
                    .inheritIO()
                    .start()
                    .waitFor();
            return new None<>();
        } */
	/* catch (IOException e) {
            return new Some<>(e);
        } */
	/* catch (InterruptedException e) {
            return new Some<>(new IOException(e));
        } */
}
Optional<struct IOException> writeString(struct Path target, char* output){
	/* try {
            Files.writeString(target, output);
            return new None<>();
        } */
	/* catch (IOException e) {
            return new Some<>(e);
        } */
}
/* IOException> */ readString(struct Path source){
	/* try {
            return new Ok<>(Files.readString(source));
        } */
	/* catch (IOException e) {
            return new Err<>(e);
        } */
}
char* compile(char* input){
	char* output = compileAllStatements(input, /* Main::compileRootSegment).orElse("" */);
	char* joinedStructs = String.join("", structs);
	char* joinedMethods = String.join("", methods);
	return output + joinedStructs + joinedMethods;
}
Optional<char*> compileAllStatements(char* input, /*  Function<String */, Optional</* String> */> compiler){
	return compileAll(input, /* Main::foldStatementChar */, compiler, /* Main::mergeStatements */);
}
Optional<char*> compileAll(char* input, /* 
            BiFunction<State */, /*  Character */, /* State> */ folder, /* 
            Function<String */, Optional</* String> */> compiler, /* 
            BiFunction<String */, /*  String */, /* String> */ merger){
	return parseAll(input, folder, /* compiler)
                .map(output */ - /* > generateAll(merger */, /* output) */);
}
Optional<List<char*>> parseAll(char* input, /*  BiFunction<State */, /*  Character */, /* State> */ folder, /*  Function<String */, Optional</* String> */> compiler){
	struct State state = State.createInitial();
	/* (int */ i = 0;
	/* i < input */.length();
	/* i++) {
            char c = input.charAt(i);
            if (c == '\'') {
                state.append(c);

                i++;
                char c1 = input.charAt(i);
                state.append(c1);

                if (c1 == '\\') {
                    i++;
                    state.append(input.charAt(i));
                }

                i++;
                state.append(input.charAt(i));

                continue;
            }

            state = folder.apply(state, c);
        } */
	state.advance();
	List<char*> segments = state.segments.stream(/* )
                .map(string */ - /* > string.toSlice())
                .toList( */);
	Optional<List<char*>> maybeOutput = new_Some<>(new_ArrayList<String>());
	/* for (String segment : segments) {
            Optional<String> maybeCompiled = compiler.apply(segment);
            maybeOutput = maybeOutput.flatMap(output -> {
                return maybeCompiled.map(compiled -> {
                    output.add(compiled);
                    return output;
                });
            });
        } */
	return maybeOutput;
}
char* generateAll(/* BiFunction<String */, /*  String */, /* String> */ merger, List<char*> output){
	char* cache = "";
	/* for (String element : output) {
            cache = merger.apply(cache, element);
        } */
	return cache;
}
char* mergeStatements(char* output, char* compiled){
	return output + compiled;
}
struct State foldStatementChar(struct State state, char c){
	state.append(c);
	/* if (c == ';' && state.isLevel()) {
            state.advance();
        } */
	/* else if (c == '}' && state.isShallow()) {
            state.advance();
            state.exit();
        } */
	/* else {
            if (c == '{') {
                state.enter();
            }
            if (c == '}') {
                state.exit();
            }
        } */
	return state;
}
Optional<char*> compileRootSegment(char* input){
	/* if (input.isBlank()) {
            return new Some<>("");
        } */
	return compileClass(/* input) */.or((/* ) */ - /* > new Some<> */(/* generatePlaceholder(input.strip( */)) + "\n"));
}
Optional<char*> compileClass(char* input){
	return compileStructured(input, "class ");
}
Optional<char*> compileStructured(char* input, char* infix){
	int classIndex = input.indexOf(infix);
	/* if (classIndex < 0) {
            return new None<>();
        } */
	char* afterKeyword = input.substring(classIndex + infix.length());
	/* int contentStart = afterKeyword.indexOf("{");
        if (contentStart < 0) {
            return new None<>();
        }
        String beforeContent = afterKeyword.substring(0, contentStart).strip();

        int permitsIndex = beforeContent.indexOf(" permits ");
        String beforeContent1 = permitsIndex >= 0
                ? beforeContent.substring(0, permitsIndex).strip()
                : beforeContent;

        int paramStart = beforeContent1.indexOf("(");
        String name;
        List<String> recordParameters;
        if (paramStart >= 0) {
            name = beforeContent1.substring(0, paramStart).strip();
            String withEnd = beforeContent1.substring(paramStart + "(".length());
            int paramEnd = withEnd.indexOf(")");
            if (paramEnd >= 0) {
                String paramString = withEnd.substring(0, paramEnd).strip();
                recordParameters = parseParameters(paramString).orElse(Collections.emptyList());
            }
            else {
                name = beforeContent1;
                recordParameters = Collections.emptyList();
            }
        }
        else {
            name = beforeContent1;
            recordParameters = Collections.emptyList();
        }

        String withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
        if (!withEnd.endsWith("}")) {
            return new None<>();
        }
        String inputContent = withEnd.substring(0, withEnd.length() - "} */
	".length(/* ) */);
	char* finalName = name;
	/* return compileAllStatements(inputContent, input1 -> compileClassSegment(input1, finalName).or(() -> new Some<>(generatePlaceholder(input1)))).flatMap(outputContent -> {
            if (!isSymbol(finalName)) {
                return new None<>();
            }

            String joined = recordParameters.stream()
                    .map(Main::formatStatement)
                    .collect(Collectors.joining());

            String generated = "struct " + finalName + " {" + joined + outputContent + "\n};\n";
            structs.add(generated);
            return new Some<>("");
        } */
	/* ) */;
}
Optional<char*> compileClassSegment(char* input, char* structName){
	return compileWhitespace(/* input) */.or(/* ( */) - /* > compileClass */(/* input))
                .or(( */) - /* > compileStructured(input */, /* "interface ")) */.or(/* ( */) - /* > compileStructured(input */, /* "record ")) */.or(/* ( */) - /* > compileMethod(input */, /* structName)) */.or(/* ( */) - /* > compileStatement(input */, (/* value) */ - /* > compileDefinition */(/* value) */.flatMap(/* Main::generateDefinition).or(( */) - /* > compileAssigner */(/* value))))
                .or(( */) - /* > new Some<>(generatePlaceholder(input */)));
}
Optional<char*> generateDefinition(struct Node node){
	/* if (node instanceof Definition definition) {
            return new Some<>(definition.value);
        } */
	/* else {
            return new None<>();
        } */
}
Optional<char*> compileWhitespace(char* input){
	/* if (input.isBlank()) {
            return new Some<>("");
        } */
	return new_None<>();
}
Optional<char*> compileDefinitionStatement(char* input){
	char* stripped = input.strip();
	/* if (!stripped.endsWith(" */;
	/* ")) {
            return new None<>();
        } */
	char* slice = stripped.substring(0, /* stripped.length( */) - ";
	".length(/* ) */);
	return compileDefinition(/* slice)
                .flatMap(Main::generateDefinition)
                .map(Main::formatStatement */);
}
char* formatStatement(char* inner){
	return "\n\t" + inner + ";
	/* " */;
}
Optional<char*> compileMethod(char* input, char* structName){
	int paramStart = input.indexOf("(");
	/* if (paramStart < 0) {
            return new None<>();
        } */
	char* beforeParamsString = input.substring(0, /* paramStart).strip( */);
	/* return compileDefinition(beforeParamsString)
                .or(() -> compileConstructorHeader(structName, beforeParamsString))
                .flatMap(beforeName -> {
                    String withParams = input.substring(paramStart + "(".length());
                    int paramEnd = withParams.indexOf(")");
                    if (paramEnd < 0) {
                        return new None<>();
                    }

                    String inputParams = withParams.substring(0, paramEnd).strip();
                    return parseParameters(inputParams)
                            .map(params -> {
                                if (beforeName instanceof Definition definition) {
                                    if (!definition.modifiers.contains("static")) {
                                        ArrayList<String> copy = new ArrayList<>();
                                        copy.add("struct " + structName + " this");

                                        params.forEach(param -> {
                                            if (!param.isEmpty()) {
                                                copy.add(param);
                                            }
                                        });
                                        return copy;
                                    }
                                }
                                return params;

                            })
                            .map(Main::generateParams).flatMap(outputParams -> {
                                String withBraces = withParams.substring(paramEnd + ")".length()).strip();

                                return compileMethodBeforeName(beforeName).flatMap(outputBeforeName -> {
                                    String header = outputBeforeName + "(" + outputParams + ")";
                                    if (!withBraces.startsWith("{") || !withBraces.endsWith("}")) {
                                        return new Some<>(formatStatement(header));
                                    }

                                    String content = withBraces.substring(1, withBraces.length() - 1);
                                    return parseStatements(content)
                                            .map(statements -> modifyMethodBody(structName, beforeName, statements))
                                            .map(Main::generateStatements).flatMap(outputContent -> {
                                                String outputBody = "{" + outputContent + "\n}\n";
                                                String generated = header + outputBody;
                                                methods.add(generated);
                                                return new Some<>("");
                                            });
                                });
                            });
                } */
	/* ) */;
}
Optional<List<char*>> parseParameters(char* inputParams){
	return parseAllValues(inputParams, /* Main::compileParam */);
}
Optional<char*> compileMethodBeforeName(struct Node node){
	/* if (node instanceof Definition definition) {
            return new Some<>(definition.value);
        } */
	/* if (node instanceof ConstructorHeader(String value)) {
            return new Some<>(value);
        } */
	return new_None<>();
}
char* generateParams(List<char*> output){
	return generateAll(/* Main::mergeValues */, output);
}
Optional<char*> compileParam(char* param){
	return compileWhitespace(/* param) */.or((/* ) */ - /* > compileDefinition */(/* param).flatMap(Main::generateDefinition))
                .or(( */) - /* > new Some<>(generatePlaceholder(param */)));
}
Optional<List<char*>> parseAllValues(char* inputParams, /*  Function<String */, Optional</* String> */> compiler){
	return parseAll(inputParams, /* Main::foldValueChar */, compiler);
}
List<char*> modifyMethodBody(char* structName, struct Node beforeName, List<char*> statements){
	/* if (beforeName instanceof ConstructorHeader) {
            List<String> copy = new ArrayList<>();
            copy.add(formatStatement("struct " + structName + " this"));
            copy.addAll(statements);
            copy.add(formatStatement("return this"));
            return copy;
        } */
	/* else {
            return statements;
        } */
}
char* generateStatements(List<char*> output){
	return generateAll(/* Main::mergeStatements */, output);
}
Optional<List<char*>> parseStatements(char* content){
	return parseAll(content, /* Main::foldStatementChar */, /* Main::compileStatementOrBlock */);
}
Optional<struct Node> compileConstructorHeader(char* structName, char* definition){
	/* String stripped0 */ = definition.strip();
	int index = /* stripped0 */.lastIndexOf(" ");
	/* if (index >= 0) {
            String constructorName = stripped0.substring(index + " ".length());
            if (constructorName.equals(structName)) {
                String generated = "struct " + structName + " new_" + structName;
                return new Some<>(new ConstructorHeader(generated));
            }
        } */
	return new_None<>();
}
Optional<char*> compileAllValues(char* inputParams, /*  Function<String */, Optional</* String> */> compiler){
	return compileAll(inputParams, /* Main::foldValueChar */, compiler, /* Main::mergeValues */);
}
struct State foldValueChar(struct State state, char c){
	/* if (c == ',') {
            state.advance();
            return state;
        } */
	state.append(c);
	/* if (c == '(') {
            state.enter();
        } */
	/* else if (c == ')') {
            state.exit();
        } */
	return state;
}
char* mergeValues(char* cache, char* element){
	/* if (cache.isEmpty()) {
            return element;
        } */
	return cache + ", " + element;
}
Optional<struct Node> compileDefinition(char* input){
	char* stripped = input.strip();
	int nameSeparator = stripped.lastIndexOf(" ");
	/* if (nameSeparator < 0) {
            return new None<>();
        } */
	char* beforeName = stripped.substring(0, /* nameSeparator).strip( */);
	char* oldName = stripped.substring(nameSeparator + /* " ".length()).strip( */);
	/* if (!isSymbol(oldName)) {
            return new None<>();
        } */
	/* String newName */;
	/* if (oldName.equals("main")) {
            newName = "__main__";
        } */
	/* else {
            newName = oldName;
        } */
	int typeSeparator = beforeName.lastIndexOf(" ");
	/* List<String> modifiers */;
	/* String type */;
	/* if (typeSeparator >= 0) {
            String modifiersString = beforeName.substring(0, typeSeparator).strip();
            type = beforeName.substring(typeSeparator + " ".length());

            modifiers = Arrays.asList(modifiersString.split(" "));
        } */
	/* else {
            modifiers = Collections.emptyList();
            type = beforeName;
        } */
	/* return compileType(type).flatMap(outputType -> {
            String outputDefinition = outputType + " " + newName;
            return new Some<>(new Definition(modifiers, outputDefinition));
        } */
	/* ) */;
}
Optional<char*> compileType(char* type){
	char* stripped = type.strip();
	/* if (stripped.equals("private") || stripped.equals("public")) {
            return new None<>();
        } */
	/* if (stripped.equals("void")) {
            return new Some<>("void");
        } */
	/* if (stripped.equals("String")) {
            return new Some<>("char*");
        } */
	/* if (stripped.equals("char")) {
            return new Some<>("char");
        } */
	/* if (stripped.equals("int") || stripped.equals("boolean")) {
            return new Some<>("int");
        } */
	/* if (stripped.endsWith("[]")) {
            return compileType(stripped.substring(0, stripped.length() - "[]".length())).map(result -> result + "*");
        } */
	/* if (stripped.endsWith(">")) {
            String slice = stripped.substring(0, stripped.length() - ">".length());
            int typeArgsStart = slice.indexOf("<");
            if (typeArgsStart >= 0) {
                String base = slice.substring(0, typeArgsStart).strip();
                String arguments = slice.substring(typeArgsStart + "<".length()).strip();
                return compileAllValues(arguments, Main::compileType).map(newArguments -> base + "<" + newArguments + ">");
            }
        } */
	/* if (isSymbol(stripped)) {
            return new Some<>("struct " + stripped);
        } */
	return new_Some<>(generatePlaceholder(stripped));
}
Optional<char*> compileStatementOrBlock(char* input){
	return compileWhitespace(/* input) */.or(/* ( */) - /* > compileStatement(input */, /* Main::compileStatementValue)) */.or((/* ) */ - /* > new Some<> */("\n\t" + /* generatePlaceholder(input.strip( */))));
}
Optional<char*> compileStatement(char* input, /*  Function<String */, Optional</* String> */> compileStatementValue){
	char* stripped = input.strip();
	/* if (stripped.endsWith(" */;
	/* ")) {
            String slice = stripped.substring(0, stripped.length() - ";".length());
            return compileStatementValue.apply(slice).map(Main::formatStatement);
        } */
	return new_None<>();
}
Optional<char*> compileStatementValue(char* input){
	char* stripped = input.strip();
	/* if (stripped.startsWith("return ")) {
            String slice = stripped.substring("return ".length());
            return new Some<>("return " + compileValue(slice));
        } */
	Optional<char*> destination = compileAssigner(stripped);
	/* if (destination.isPresent()) {
            return destination;
        } */
	Optional<char*> maybeInvokable = compileInvokable(stripped);
	/* if (maybeInvokable.isPresent()) {
            return maybeInvokable;
        } */
	return new_Some<>(generatePlaceholder(stripped));
}
Optional<char*> compileAssigner(char* stripped){
	int valueSeparator = stripped.indexOf("=");
	/* if (valueSeparator < 0) {
            return new None<>();
        } */
	char* destination = stripped.substring(0, /* valueSeparator).strip( */);
	char* source = stripped.substring(valueSeparator + /* "=".length()).strip( */);
	char* newDestination = compileDefinition(/* destination) */.flatMap(/* Main::generateDefinition) */.orElseGet(/* ( */) - /* > compileValue(destination */));
	return new_Some<>(newDestination + " = " + compileValue(source));
}
char* compileValue(char* input){
	char* stripped = input.strip();
	/* if (stripped.startsWith("\"") && stripped.endsWith("\"")) {
            return stripped;
        } */
	/* if (isNumber(stripped)) {
            return stripped;
        } */
	/* if (isSymbol(stripped)) {
            return stripped;
        } */
	Optional<char*> maybeInvokable = compileInvokable(stripped);
	/* if (maybeInvokable.isPresent()) {
            return maybeInvokable.orElse(null);
        } */
	int lastSeparator = stripped.lastIndexOf(".");
	/* if (lastSeparator >= 0) {
            String parent = stripped.substring(0, lastSeparator);
            String property = stripped.substring(lastSeparator + ".".length());
            if (isSymbol(property)) {
                return compileValue(parent) + "." + property;
            }
        } */
	return compileOperator(stripped, " == /* ") */.or(/* ( */) - /* > compileOperator(stripped */, " + /* ")) */.or(/* ( */) - /* > compileOperator(stripped */, " - /* ")) */.orElseGet(() - /* > generatePlaceholder(stripped */));
}
Optional<char*> compileOperator(char* input, char* operator){
	int equalsIndex = input.indexOf(operator);
	/* if (equalsIndex < 0) {
            return new None<>();
        } */
	char* left = input.substring(0, equalsIndex);
	char* right = input.substring(equalsIndex + operator.length());
	return new_Some<>(compileValue(/* left) */ + " " + operator + " " + /* compileValue(right */));
}
Optional<char*> compileInvokable(char* input){
	char* stripped = input.strip();
	/* if (!stripped.endsWith(")")) {
            return new None<>();
        } */
	char* withoutEnd = stripped.substring(0, stripped.length(/* ) */ - /* ")".length( */));
	int argumentStart = withoutEnd.indexOf("(");
	/* if (argumentStart < 0) {
            return new None<>();
        } */
	char* beforeArguments = withoutEnd.substring(0, /* argumentStart).strip( */);
	char* arguments = withoutEnd.substring(argumentStart + /* "(".length()).strip( */);
	Optional<char*> maybeNewArguments = compileAllValues(arguments, argument - /* > new Some<> */(compileValue(argument)));
	/* if (maybeNewArguments.isEmpty()) {
            return new None<>();
        } */
	char* newArguments = maybeNewArguments.orElse(null);
	/* if (!beforeArguments.startsWith("new ")) {
            String caller = compileValue(beforeArguments);
            return generateInvocation(caller, newArguments);
        } */
	char* type = beforeArguments.substring(/* "new ".length()).strip( */);
	char* caller = "new_" + type;
	return generateInvocation(caller, newArguments);
}
Optional<char*> generateInvocation(char* caller, char* arguments){
	return new_Some<>(caller + "(" + arguments + ")");
}
int isNumber(char* input){
	/* (int */ i = 0;
	/* i < input */.length();
	/* i++) {
            char c = input.charAt(i);
            if (Character.isDigit(c)) {
                continue;
            }
            return false;
        } */
	return true;
}
int isSymbol(char* input){
	/* (int */ i = 0;
	/* i < input */.length();
	/* i++) {
            char c = input.charAt(i);
            if (Character.isLetter(c) || c == '_') {
                continue;
            }
            return false;
        } */
	return true;
}
char* generatePlaceholder(char* input){
	return "/* " + input + " */";
}
