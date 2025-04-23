/* package magma; */
/* import java.io.IOException; */
/* import java.nio.file.Files; */
/* import java.nio.file.Path; */
/* import java.nio.file.Paths; */
/* import java.util.ArrayList; */
/* import java.util.Arrays; */
/* import java.util.Collections; */
/* import java.util.List; */
/* import java.util.Map; */
/* import java.util.function.BiFunction; */
/* import java.util.function.Consumer; */
/* import java.util.function.Function; */
/* import java.util.function.Supplier; */
/* import java.util.stream.Collectors; */
/* import static magma.StandardLibrary.emptyString; */
struct Option {
	void ifPresent(struct Option this, Consumer<struct T> consumer);
	struct T orElse(struct Option this, struct T other);
	Option<struct R> map(struct Option this, Function<struct T, struct R> mapper);
	Option<struct R> flatMap(struct Option this, Function<struct T, Option<struct R>> mapper);
	Option<struct T> or(struct Option this, Supplier<Option<struct T>> other);
	int isPresent(struct Option this);
	struct T orElseGet(struct Option this, Supplier<struct T> other);
	int isEmpty(struct Option this);
};
struct BeforeName {
};
struct String_ {
	struct String_ concatChar(struct String_ this, char c);
	char* toSlice(struct String_ this);
};
struct Result {
};
struct Type {
	char* generate(struct Type this);
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
struct Ok {
	struct T value;
};
struct Err {
	struct X error;
};
struct Some {
	struct T value;
};
struct None {
};
struct Ref {
	struct Type type;
};
struct Generic {
	char* base;
	List<struct Type> arguments;
};
struct Struct {
	char* name;
};
struct Placeholder {
	char* value;
};
struct Main {/* enum Primitive implements Type, BeforeName {
        I8,
        I32,
        Void;

        public static final Map<Primitive, String> mapper = Map.of(
                I8, "char",
                I32, "int",
                Void, "void"
        );

        @Override
        public String generate() {
            return mapper.getOrDefault(this, "?");
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
void ifPresent(struct Some this, Consumer<struct T> consumer){
	consumer.accept(this.value);
}
struct T orElse(struct Some this, struct T other){
	return this.value;
}
Option<struct R> map(struct Some this, Function<struct T, struct R> mapper){
	return new_Some<>(mapper.apply(this.value));
}
Option<struct R> flatMap(struct Some this, Function<struct T, Option<struct R>> mapper){
	return mapper.apply(this.value);
}
Option<struct T> or(struct Some this, Supplier<Option<struct T>> other){
	return this;
}
int isPresent(struct Some this){
	return true;
}
struct T orElseGet(struct Some this, Supplier<struct T> other){
	return this.value;
}
int isEmpty(struct Some this){
	return false;
}
void ifPresent(struct None this, Consumer<struct T> consumer){
}
struct T orElse(struct None this, struct T other){
	return other;
}
Option<struct R> map(struct None this, Function<struct T, struct R> mapper){
	return new_None<>();
}
Option<struct R> flatMap(struct None this, Function<struct T, Option<struct R>> mapper){
	return new_None<>();
}
Option<struct T> or(struct None this, Supplier<Option<struct T>> other){
	return other.get();
}
int isPresent(struct None this){
	return false;
}
struct T orElseGet(struct None this, Supplier<struct T> other){
	return other.get();
}
int isEmpty(struct None this){
	return true;
}
char* generate(struct Ref this){
	return this.type.generate() + "*";
}
char* generate(struct Generic this){
	char* joinedArguments = this.arguments.stream(/* ) */.map(/* Type::generate)
                    .collect(Collectors.joining(", " */));
	return this.base + "<" + joinedArguments + ">";
}
char* generate(struct Struct this){
	return "struct " + this.name;
}
char* generate(struct Placeholder this){
	return this.value;
}
void __main__(){
	/* Option<IOException> result = switch (readString(SOURCE)) {
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
Option<struct IOException> build(){
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
Option<struct IOException> writeString(struct Path target, char* output){
	/* try {
            Files.writeString(target, output);
            return new None<>();
        } */
	/* catch (IOException e) {
            return new Some<>(e);
        } */
}
Result<char*, struct IOException> readString(struct Path source){
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
Option<char*> compileAllStatements(char* input, Function<char*, Option<char*>> compiler){
	return compileAll(input, /* Main::foldStatementChar */, compiler, /* Main::mergeStatements */);
}
Option<char*> compileAll(char* input, BiFunction<struct State, struct Character, struct State> folder, Function<char*, Option<char*>> compiler, BiFunction<char*, char*, char*> merger){
	return parseAll(input, folder, /* compiler)
                .map(output */ - /* > generateAll(merger */, /* output) */);
}
Option<List<struct T>> parseAll(char* input, BiFunction<struct State, struct Character, struct State> folder, Function<char*, Option<struct T>> compiler){
	struct State state = State.createInitial();
	(int i = 0;
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
	Option<List<struct T>> maybeOutput = new_Some<>(new_ArrayList<T>());
	/* for (String segment : segments) {
            Option<T> maybeCompiled = compiler.apply(segment);
            maybeOutput = maybeOutput.flatMap(output -> {
                return maybeCompiled.map(compiled -> {
                    output.add(compiled);
                    return output;
                });
            });
        } */
	return maybeOutput;
}
char* generateAll(BiFunction<char*, char*, char*> merger, List<char*> output){
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
Option<char*> compileRootSegment(char* input){
	/* if (input.isBlank()) {
            return new Some<>("");
        } */
	return compileClass(/* input) */.or((/* ) */ - /* > new Some<> */(/* generatePlaceholder(input.strip( */)) + "\n"));
}
Option<char*> compileClass(char* input){
	return compileStructured(input, "class ");
}
Option<char*> compileStructured(char* input, char* infix){
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
        String withoutParams;
        List<String> recordParameters;
        if (paramStart >= 0) {
            withoutParams = beforeContent1.substring(0, paramStart).strip();
            String withEnd = beforeContent1.substring(paramStart + "(".length());
            int paramEnd = withEnd.indexOf(")");
            if (paramEnd >= 0) {
                String paramString = withEnd.substring(0, paramEnd).strip();
                recordParameters = parseParameters(paramString).orElse(Collections.emptyList());
            }
            else {
                withoutParams = beforeContent1;
                recordParameters = Collections.emptyList();
            }
        }
        else {
            withoutParams = beforeContent1;
            recordParameters = Collections.emptyList();
        }

        int typeParamStart = withoutParams.indexOf("<");
        String name = typeParamStart >= 0
                ? withoutParams.substring(0, typeParamStart).strip()
                : withoutParams;

        String withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
        if (!withEnd.endsWith("}")) {
            return new None<>();
        }
        String inputContent = withEnd.substring(0, withEnd.length() - "} */
	".length(/* ) */);
	/* return compileAllStatements(inputContent, input1 -> compileClassSegment(input1, name).or(() -> new Some<>(generatePlaceholder(input1)))).flatMap(outputContent -> {
            if (!isSymbol(name)) {
                return new None<>();
            }

            String joined = recordParameters.stream()
                    .map(Main::formatStatement)
                    .collect(Collectors.joining());

            String generated = "struct " + name + " {" + joined + outputContent + "\n};\n";
            structs.add(generated);
            return new Some<>("");
        } */
	/* ) */;
}
Option<char*> compileClassSegment(char* input, char* structName){
	return compileWhitespace(/* input) */.or((/* ) */ - /* > compileClass */(/* input))
                .or(( */) - /* > compileStructured */(input, /* "interface "))
                .or(( */) - /* > compileStructured */(input, /* "record "))
                .or(( */) - /* > compileMethod */(input, /* structName))
                .or(( */) - /* > compileStatement */(input, /* (value */) - /* > compileDefinition */(/* value).flatMap(Main::generateDefinition).or(( */) - /* > compileAssigner */(/* value))))
                .or(( */) - /* > new Some<>(generatePlaceholder(input */)));
}
Option<char*> generateDefinition(struct BeforeName node){
	/* if (node instanceof Definition definition) {
            return new Some<>(definition.value);
        } */
	/* else {
            return new None<>();
        } */
}
Option<char*> compileWhitespace(char* input){
	/* if (input.isBlank()) {
            return new Some<>("");
        } */
	return new_None<>();
}
char* formatStatement(char* inner){
	return "\n\t" + inner + ";
	/* " */;
}
Option<char*> compileMethod(char* input, char* structName){
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
Option<List<char*>> parseParameters(char* inputParams){
	return parseAllValues(inputParams, /* Main::compileParam */);
}
Option<char*> compileMethodBeforeName(struct BeforeName node){
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
Option<char*> compileParam(char* param){
	return compileWhitespace(/* param) */.or((/* ) */ - /* > compileDefinition */(/* param).flatMap(Main::generateDefinition))
                .or(( */) - /* > new Some<>(generatePlaceholder(param */)));
}
Option<List<struct T>> parseAllValues(char* inputParams, Function<char*, Option<struct T>> compiler){
	return parseAll(inputParams, /* Main::foldValueChar */, compiler);
}
List<char*> modifyMethodBody(char* structName, struct BeforeName beforeName, List<char*> statements){
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
Option<List<char*>> parseStatements(char* content){
	return parseAll(content, /* Main::foldStatementChar */, /* Main::compileStatementOrBlock */);
}
Option<struct BeforeName> compileConstructorHeader(char* structName, char* definition){
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
Option<char*> compileAllValues(char* inputParams, Function<char*, Option<char*>> compiler){
	return compileAll(inputParams, /* Main::foldValueChar */, compiler, /* Main::mergeValues */);
}
struct State foldValueChar(struct State state, char c){
	/* if (c == ',' && state.isLevel()) {
            state.advance();
            return state;
        } */
	state.append(c);
	/* if (c == '(' || c == '<') {
            state.enter();
        } */
	/* else if (c == ')' || c == '>') {
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
Option<struct BeforeName> compileDefinition(char* input){
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
	/* List<String> modifiers */;
	/* String type */;
	/* switch (findTypeSeparator(beforeName)) {
            case None<Integer> _ -> {
                modifiers = Collections.emptyList();
                type = beforeName;
            }
            case Some<Integer>(int typeSeparator) -> {
                String modifiersString = beforeName.substring(0, typeSeparator).strip();
                modifiers = Arrays.asList(modifiersString.split(" "));
                type = beforeName.substring(typeSeparator + " ".length());
            }
        } */
	/* return parseType(type).map(Type::generate).map(outputType -> {
            String outputDefinition = outputType + " " + newName;
            return new Definition(modifiers, outputDefinition);
        } */
	/* ) */;
}
Option<struct Integer> findTypeSeparator(char* input){
	int depth = 0;
	(int i = input.length() - 1;
	/* i > */ = 0;
	/* i--) {
            char c = input.charAt(i);
            if (c == ' ' && depth == 0) {
                return new Some<>(i);
            }
            else if (c == '>') {
                depth++;
            }
            else if (c == '<') {
                depth--;
            }
        } */
	return new_None<>();
}
Option<struct Type> parseType(char* type){
	char* stripped = type.strip();
	/* if (stripped.equals("private") || stripped.equals("public")) {
            return new None<>();
        } */
	/* if (stripped.equals("void")) {
            return new Some<>(Primitive.Void);
        } */
	/* if (stripped.equals("char")) {
            return new Some<>(Primitive.I8);
        } */
	/* if (stripped.equals("String")) {
            return new Some<>(new Ref(Primitive.I8));
        } */
	/* if (stripped.equals("int") || stripped.equals("boolean")) {
            return new Some<>(Primitive.I32);
        } */
	/* if (stripped.endsWith("[]")) {
            String slice = stripped.substring(0, stripped.length() - "[]".length());
            return parseType(slice).map(Ref::new);
        } */
	/* if (stripped.endsWith(">")) {
            String slice = stripped.substring(0, stripped.length() - ">".length());
            int typeArgsStart = slice.indexOf("<");
            if (typeArgsStart >= 0) {
                String base = slice.substring(0, typeArgsStart).strip();
                String inputArguments = slice.substring(typeArgsStart + "<".length()).strip();
                return parseAllValues(inputArguments, Main::parseType)
                        .map(arguments -> new Generic(base, arguments));
            }
        } */
	/* if (isSymbol(stripped)) {
            return new Some<>(new Struct(stripped));
        } */
	return new_Some<>(new_Placeholder(stripped));
}
Option<char*> compileStatementOrBlock(char* input){
	return compileWhitespace(/* input) */.or(/* ( */) - /* > compileStatement(input */, /* Main::compileStatementValue)) */.or((/* ) */ - /* > new Some<> */("\n\t" + /* generatePlaceholder(input.strip( */))));
}
Option<char*> compileStatement(char* input, Function<char*, Option<char*>> compileStatementValue){
	char* stripped = input.strip();
	/* if (stripped.endsWith(" */;
	/* ")) {
            String slice = stripped.substring(0, stripped.length() - ";".length());
            return compileStatementValue.apply(slice).map(Main::formatStatement);
        } */
	return new_None<>();
}
Option<char*> compileStatementValue(char* input){
	char* stripped = input.strip();
	/* if (stripped.startsWith("return ")) {
            String slice = stripped.substring("return ".length());
            return new Some<>("return " + compileValue(slice));
        } */
	Option<char*> destination = compileAssigner(stripped);
	/* if (destination.isPresent()) {
            return destination;
        } */
	Option<char*> maybeInvokable = compileInvokable(stripped);
	/* if (maybeInvokable.isPresent()) {
            return maybeInvokable;
        } */
	return new_Some<>(generatePlaceholder(stripped));
}
Option<char*> compileAssigner(char* stripped){
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
	Option<char*> maybeInvokable = compileInvokable(stripped);
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
	return compileOperator(stripped, " == /* ") */.or(/* ( */) - /* > compileOperator(stripped */, " + /* ")) */.or(() - /* > compileOperator(stripped */, " - /* ")) */.orElseGet(/* ( */) - /* > generatePlaceholder(stripped */));
}
Option<char*> compileOperator(char* input, char* operator){
	int equalsIndex = input.indexOf(operator);
	/* if (equalsIndex < 0) {
            return new None<>();
        } */
	char* left = input.substring(0, equalsIndex);
	char* right = input.substring(equalsIndex + operator.length());
	return new_Some<>(compileValue(/* left) */ + " " + operator + " " + /* compileValue(right */));
}
Option<char*> compileInvokable(char* input){
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
	Option<char*> maybeNewArguments = compileAllValues(arguments, argument - /* > new Some<> */(compileValue(argument)));
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
Option<char*> generateInvocation(char* caller, char* arguments){
	return new_Some<>(caller + "(" + arguments + ")");
}
int isNumber(char* input){
	(int i = 0;
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
	(int i = 0;
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
