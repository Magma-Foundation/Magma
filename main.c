/* package magma; */
/* import java.io.IOException; */
/* import java.nio.file.Files; */
/* import java.nio.file.Path; */
/* import java.nio.file.Paths; */
/* import java.util.ArrayList; */
/* import java.util.Arrays; */
/* import java.util.Collections; */
/* import java.util.List; */
/* import java.util.Optional; */
/* import java.util.function.BiFunction; */
/* import java.util.function.Function; */
/* import java.util.stream.Collectors; */
struct Node {
}
struct String_ {
	struct String_ concatChar(struct String_ this, struct char c);
	char* toSlice(struct String_ this);
}
struct State {
	List<struct String_> segments;
	struct String_ buffer;
	int depth;
}
struct Definition {
	List<char*> modifiers;
	char* value;
}
struct ConstructorHeader {
	char* value;
}
struct Main {
	List<char*> structs;
	List<char*> methods;
}
struct State new_State(List<struct String_> segments, struct String_ buffer, int depth){
	struct State this;
	this.segments = segments;
	this.buffer = buffer;
	this.depth = depth;
	return this;
}
struct State createInitial(){
	return new_State(new_ArrayList<>(), Strings.empty(), 0);
}
int isLevel(struct State this){
	return this.depth == 0;
}
int isShallow(struct State this){
	return this.depth == 1;
}
void advance(struct State this){
	this.segments.add(this.buffer);
	/* this.buffer = Strings */.empty();
}
void exit(struct State this){
	this.depth = this.depth - 1;
}
void enter(struct State this){
	this.depth = this.depth + 1;
}
void append(struct State this, struct char c){
	/* this.buffer = this */.buffer.concatChar(c);
}
void __main__(/* String[] */ args){
	/* try {
            Path source = Paths.get(".", "src", "java", "magma", "Main.java");
            String input = Files.readString(source);

            Path target = Paths.get(".", "main.c");
            Files.writeString(target, compile(input));

            new ProcessBuilder("clang", "-o", "main.exe", "main.c")
                    .inheritIO()
                    .start()
                    .waitFor();
        } */
	/* catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        } */
}
char* compile(char* input){
	/* String output = compileAllStatements */(input, /* Main::compileRootSegment).orElse("" */);
	/* String joinedStructs = String */.join("", structs);
	/* String joinedMethods = String */.join("", methods);
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
	/* State state = State */.createInitial();
	/* for (int i */ = 0;
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
	/* List<String> segments = state */.segments.stream(/* )
                .map(string */ - /* > string.toSlice())
                .toList( */);
	/* Optional<List<String>> maybeOutput = Optional */.of(new_ArrayList<String>());
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
	/* String cache */ = "";
	/* for (String element : output) {
            cache = merger.apply(cache, element);
        } */
	return cache;
}
char* mergeStatements(char* output, char* compiled){
	return output + compiled;
}
struct State foldStatementChar(struct State state, struct char c){
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
            return Optional.of("");
        } */
	return compileClass(/* input) */.or((/* ) */ - /* > Optional */.of(/* generatePlaceholder(input.strip( */)) + "\n"));
}
Optional<char*> compileClass(char* input){
	return compileStructured(input, "class ");
}
Optional<char*> compileStructured(char* input, char* infix){
	/* int classIndex = input */.indexOf(infix);
	/* if (classIndex < 0) {
            return Optional.empty();
        } */
	/* String afterKeyword = input */.substring(classIndex + infix.length());
	/* int contentStart = afterKeyword.indexOf("{");
        if (contentStart < 0) {
            return Optional.empty();
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
            return Optional.empty();
        }
        String inputContent = withEnd.substring(0, withEnd.length() - "} */
	".length(/* ) */);
	/* String finalName */ = name;
	/* return compileAllStatements(inputContent, input1 -> compileClassSegment(input1, finalName).or(() -> Optional.of(generatePlaceholder(input1)))).flatMap(outputContent -> {
            if (!isSymbol(finalName)) {
                return Optional.empty();
            }

            String joined = recordParameters.stream()
                    .map(Main::formatStatement)
                    .collect(Collectors.joining());

            String generated = "struct " + finalName + " {" + joined + outputContent + "\n}\n";
            structs.add(generated);
            return Optional.of("");
        } */
	/* ) */;
}
Optional<char*> compileClassSegment(char* input, char* structName){
	return compileWhitespace(/* input) */.or(/* ( */) - /* > compileClass */(/* input))
                .or(( */) - /* > compileStructured(input */, /* "interface ")) */.or(/* ( */) - /* > compileStructured(input */, /* "record ")) */.or(/* ( */) - /* > compileMethod(input */, /* structName)) */.or((/* ) */ - /* > compileInitialization */(/* input))
                .or(( */) - /* > compileDefinitionStatement */(/* input))
                .or(( */) - /* > Optional.of(generatePlaceholder(input */)));
}
Optional<char*> compileInitialization(char* input){
	/* int valueSeparator = input */.indexOf("=");
	/* if (valueSeparator < 0) {
            return Optional.empty();
        } */
	return compileDefinition(/* input.substring(0 */, /* valueSeparator))
                .flatMap(Main::generateDefinition)
                .map(Main::formatStatement */);
}
Optional<char*> generateDefinition(struct Node node){
	/* if (node instanceof Definition definition) {
            return Optional.of(definition.value);
        } */
	/* else {
            return Optional.empty();
        } */
}
Optional<char*> compileWhitespace(char* input){
	/* if (input.isBlank()) {
            return Optional.of("");
        } */
	return Optional.empty();
}
Optional<char*> compileDefinitionStatement(char* input){
	/* String stripped = input */.strip();
	/* if (!stripped.endsWith(" */;
	/* ")) {
            return Optional.empty();
        } */
	/* String slice */ = stripped.substring(0, /* stripped.length( */) - ";
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
	/* int paramStart = input */.indexOf("(");
	/* if (paramStart < 0) {
            return Optional.empty();
        } */
	/* String beforeParamsString = input */.substring(0, /* paramStart).strip( */);
	/* return compileDefinition(beforeParamsString)
                .or(() -> compileConstructorHeader(structName, beforeParamsString))
                .flatMap(beforeName -> {
                    String withParams = input.substring(paramStart + "(".length());
                    int paramEnd = withParams.indexOf(")");
                    if (paramEnd < 0) {
                        return Optional.empty();
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
                                        return Optional.of(formatStatement(header));
                                    }

                                    String content = withBraces.substring(1, withBraces.length() - 1);
                                    return parseStatements(content)
                                            .map(statements -> modifyMethodBody(structName, beforeName, statements))
                                            .map(Main::generateStatements).flatMap(outputContent -> {
                                                String outputBody = "{" + outputContent + "\n}\n";
                                                String generated = header + outputBody;
                                                methods.add(generated);
                                                return Optional.of("");
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
            return Optional.of(definition.value);
        } */
	/* if (node instanceof ConstructorHeader(String value)) {
            return Optional.of(value);
        } */
	return Optional.empty();
}
char* generateParams(List<char*> output){
	return generateAll(/* Main::mergeValues */, output);
}
Optional<char*> compileParam(char* param){
	return compileWhitespace(/* param) */.or((/* ) */ - /* > compileDefinition */(/* param).flatMap(Main::generateDefinition))
                .or(( */) - /* > Optional.of(generatePlaceholder(param */)));
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
	/* String stripped0 = definition */.strip();
	/* int index = stripped0 */.lastIndexOf(" ");
	/* if (index >= 0) {
            String constructorName = stripped0.substring(index + " ".length());
            if (constructorName.equals(structName)) {
                String generated = "struct " + structName + " new_" + structName;
                return Optional.of(new ConstructorHeader(generated));
            }
        } */
	return Optional.empty();
}
Optional<char*> compileAllValues(char* inputParams, /*  Function<String */, Optional</* String> */> compiler){
	return compileAll(inputParams, /* Main::foldValueChar */, compiler, /* Main::mergeValues */);
}
struct State foldValueChar(struct State state, struct char c){
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
	/* String stripped = input */.strip();
	/* int nameSeparator = stripped */.lastIndexOf(" ");
	/* if (nameSeparator < 0) {
            return Optional.empty();
        } */
	/* String beforeName = stripped */.substring(0, /* nameSeparator).strip( */);
	/* String oldName = stripped */.substring(nameSeparator + /* " ".length()).strip( */);
	/* if (!isSymbol(oldName)) {
            return Optional.empty();
        } */
	/* String newName */;
	/* if (oldName.equals("main")) {
            newName = "__main__";
        } */
	/* else {
            newName = oldName;
        } */
	/* int typeSeparator = beforeName */.lastIndexOf(" ");
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
            return Optional.of(new Definition(modifiers, outputDefinition));
        } */
	/* ) */;
}
Optional<char*> compileType(char* type){
	/* String stripped = type */.strip();
	/* if (stripped.equals("private") || stripped.equals("public")) {
            return Optional.empty();
        } */
	/* if (stripped.equals("void")) {
            return Optional.of("void");
        } */
	/* if (stripped.equals("String")) {
            return Optional.of("char*");
        } */
	/* if (stripped.equals("int") || stripped.equals("boolean")) {
            return Optional.of("int");
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
            return Optional.of("struct " + stripped);
        } */
	return Optional.of(generatePlaceholder(stripped));
}
Optional<char*> compileStatementOrBlock(char* input){
	return compileWhitespace(/* input) */.or((/* ) */ - /* > compileStatement */(/* input)) */.or(/* ( */) - /* > Optional.of("\n\t" */ + /* generatePlaceholder(input.strip( */))));
}
Optional<char*> compileStatement(char* input){
	/* String stripped = input */.strip();
	/* if (stripped.endsWith(" */;
	/* ")) {
            String slice = stripped.substring(0, stripped.length() - ";".length());
            return compileStatementValue(slice).map(Main::formatStatement);
        } */
	return Optional.empty();
}
Optional<char*> compileStatementValue(char* input){
	/* String stripped = input */.strip();
	/* if (stripped.startsWith("return ")) {
            String slice = stripped.substring("return ".length());
            return Optional.of("return " + compileValue(slice));
        } */
	/* Optional<String> maybeInvokable = compileInvokable */(stripped);
	/* if (maybeInvokable.isPresent()) {
            return maybeInvokable;
        } */
	/* int valueSeparator = stripped */.indexOf("=");
	/* if (valueSeparator >= 0) {
            String destination = stripped.substring(0, valueSeparator).strip();
            String source = stripped.substring(valueSeparator + "=".length()).strip();

            return Optional.of(compileValue(destination) + " = " + compileValue(source));
        } */
	return Optional.of(generatePlaceholder(stripped));
}
char* compileValue(char* input){
	/* String stripped = input */.strip();
	/* if (stripped.startsWith("\"") && stripped.endsWith("\"")) {
            return stripped;
        } */
	/* if (isNumber(stripped)) {
            return stripped;
        } */
	/* if (isSymbol(stripped)) {
            return stripped;
        } */
	/* Optional<String> maybeInvokable = compileInvokable */(stripped);
	/* if (maybeInvokable.isPresent()) {
            return maybeInvokable.get();
        } */
	/* int lastSeparator = stripped */.lastIndexOf(".");
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
	/* int equalsIndex = input */.indexOf(operator);
	/* if (equalsIndex < 0) {
            return Optional.empty();
        } */
	/* String left = input */.substring(0, equalsIndex);
	/* String right = input */.substring(equalsIndex + operator.length());
	return Optional.of(compileValue(/* left) */ + " " + operator + " " + /* compileValue(right */));
}
Optional<char*> compileInvokable(char* input){
	/* String stripped = input */.strip();
	/* if (stripped.endsWith(")")) {
            String withoutEnd = stripped.substring(0, stripped.length() - ")".length());
            int argumentStart = withoutEnd.indexOf("(");
            if (argumentStart >= 0) {
                String beforeArguments = withoutEnd.substring(0, argumentStart).strip();
                String arguments = withoutEnd.substring(argumentStart + "(".length()).strip();
                Optional<String> maybeNewArguments = compileAllValues(arguments, argument -> Optional.of(compileValue(argument)));
                if (maybeNewArguments.isPresent()) {
                    String newArguments = maybeNewArguments.get();
                    if (beforeArguments.startsWith("new ")) {
                        String type = beforeArguments.substring("new ".length()).strip();
                        String caller = "new_" + type;
                        return generateInvocation(caller, newArguments);
                    }
                    else {
                        String caller = compileValue(beforeArguments);
                        return generateInvocation(caller, newArguments);
                    }
                }
            }
        } */
	return Optional.empty();
}
Optional<char*> generateInvocation(char* caller, char* arguments){
	return Optional.of(caller + "(" + arguments + ")");
}
int isNumber(char* input){
	/* for (int i */ = 0;
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
	/* for (int i */ = 0;
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
