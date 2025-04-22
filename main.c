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
struct State {
	List<char*> segments;
	char* buffer;
	int depth;/* 

        private State(List<String> segments, String buffer, int depth) {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
        } */
}
struct Main {
	List<char*> structs;
	List<char*> methods;
}
/* public */ new_State(){
	/* this(new ArrayList<>(), "", 0) */;
}
/* boolean */ isLevel(){
	/* return this */.depth = /* = 0 */;
}
/* boolean */ isShallow(){
	/* return this */.depth = /* = 1 */;
}
void advance(){
	/* this.segments.add(this.buffer) */;
	this.buffer = /* "" */;
}
void exit(){
	this.depth = this.depth - 1;
}
void enter(){
	this.depth = this.depth + 1;
}
void append(/* char */ c){
	this.buffer = this.buffer + c;
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
	/* String output */ = /* compileAllStatements(input, Main::compileRootSegment) */.orElse("");
	/* String joinedStructs */ = String.join("", structs);
	/* String joinedMethods */ = String.join("", methods);
	/* return output + joinedStructs + joinedMethods */;
}
Optional<char*> compileAllStatements(char* input, /*  Function<String */, Optional</* String> */> compiler){
	/* return compileAll(input, Main::foldStatementChar, compiler, Main::mergeStatements) */;
}
Optional<char*> compileAll(char* input, /* 
            BiFunction<State */, /*  Character */, /* State> */ folder, /* 
            Function<String */, Optional</* String> */> compiler, /* 
            BiFunction<String */, /*  String */, /* String> */ merger){
	/* State state */ = /* new State() */;
	/* for (int i */ = /* 0 */;
	/* i < input.length() */;
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
	/* state.advance() */;
	/* List<String> segments */ = state.segments;
	/* Optional<String> maybeOutput */ = Optional.of("");
	/* for (String segment : segments) {
            Optional<String> maybeCompiled = compiler.apply(segment);
            maybeOutput = maybeOutput.flatMap(output -> {
                return maybeCompiled.map(compiled -> {
                    return merger.apply(output, compiled);
                });
            });
        } */
	/* return maybeOutput */;
}
char* mergeStatements(char* output, char* compiled){
	/* return output + compiled */;
}
/* State */ foldStatementChar(/* State */ state, /* char */ c){
	/* state.append(c) */;
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
	/* return state */;
}
Optional<char*> compileRootSegment(char* input){
	/* if (input.isBlank()) {
            return Optional.of("");
        } */
	/* return compileClass(input)
                .or(() -> Optional.of(generatePlaceholder(input.strip()) + "\n")) */;
}
Optional<char*> compileClass(char* input){
	/* int classIndex */ = input.indexOf("class ");
	/* if (classIndex < 0) {
            return Optional.empty();
        } */
	/* String afterKeyword */ = input.substring(classIndex + "class ".length());
	/* int contentStart = afterKeyword.indexOf("{");
        if (contentStart < 0) {
            return Optional.empty();
        }
        String name = afterKeyword.substring(0, contentStart).strip();
        String withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
        if (!withEnd.endsWith("}")) {
            return Optional.empty();
        }
        String inputContent = withEnd.substring(0, withEnd.length() - "} */
	/* ".length()) */;
	/* return compileAllStatements(inputContent, input1 -> compileClassSegment(input1, name).or(() -> Optional.of(generatePlaceholder(input1)))).flatMap(outputContent -> {
            if (!isSymbol(name)) {
                return Optional.empty();
            }
            String generated = "struct " + name + " {" + outputContent + "\n}\n";
            structs.add(generated);
            return Optional.of("");
        } */
	/* ) */;
}
Optional<char*> compileClassSegment(char* input, char* structName){
	/* return compileWhitespace(input)
                .or(() -> compileClass(input))
                .or(() -> compileMethod(input, structName))
                .or(() -> compileInitialization(input))
                .or(() -> compileDefinitionStatement(input))
                .or(() -> Optional.of(generatePlaceholder(input))) */;
}
Optional<char*> compileInitialization(char* input){
	/* int valueSeparator */ = input.indexOf("=");
	/* if (valueSeparator >= 0) {
            return compileDefinition(input.substring(0, valueSeparator), "temp")
                    .map(value -> "\n\t" + value + ";");
        } */
	/* else {
            return Optional.empty();
        } */
}
Optional<char*> compileWhitespace(char* input){
	/* if (input.isBlank()) {
            return Optional.of("");
        } */
	/* return Optional.empty() */;
}
Optional<char*> compileDefinitionStatement(char* input){
	/* String stripped */ = input.strip();
	/* if (stripped.endsWith(" */;
	/* ")) {
            String slice = stripped.substring(0, stripped.length() - ";".length());
            return compileDefinition(slice, "temp").map(inner -> "\n\t" + inner + ";");
        } */
	/* else {
            return Optional.empty();
        } */
}
Optional<char*> compileMethod(char* input, char* structName){
	/* int paramStart */ = input.indexOf("(");
	/* if (paramStart < 0) {
            return Optional.empty();
        } */
	/* String definition */ = input.substring(0, paramStart).strip();
	/* return compileDefinition(definition, structName).flatMap(outputDefinition -> {
            String withParams = input.substring(paramStart + "(".length());
            int paramEnd = withParams.indexOf(")");
            if (paramEnd < 0) {
                return Optional.empty();
            }

            String inputParams = withParams.substring(0, paramEnd).strip();
            return compileAllValues(inputParams, param -> compileWhitespace(param).or(() -> compileDefinition(param, structName)
                    .or(() -> Optional.of(generatePlaceholder(param))))).flatMap(outputParams -> {
                String withBraces = withParams.substring(paramEnd + ")".length()).strip();
                if (!withBraces.startsWith("{") || !withBraces.endsWith("}")) {
                    return Optional.empty();
                }

                String content = withBraces.substring(1, withBraces.length() - 1);
                return compileAllStatements(content, Main::compileStatementOrBlock).flatMap(outputContent -> {
                    String generated = outputDefinition + "(" + outputParams + "){" + outputContent + "\n}\n";
                    methods.add(generated);
                    return Optional.of("");
                });
            });
        } */
	/* ) */;
}
Optional<char*> compileAllValues(char* inputParams, /*  Function<String */, Optional</* String> */> compiler){
	/* return compileAll(inputParams, Main::foldValueChar, compiler, Main::mergeValues) */;
}
/* State */ foldValueChar(/* State */ state, /* char */ c){
	/* if (c == ',') {
            state.advance();
        } */
	/* else {
            state.append(c);
        } */
	/* return state */;
}
char* mergeValues(char* cache, char* element){
	/* if (cache.isEmpty()) {
            return element;
        } */
	/* return cache + ", " + element */;
}
Optional<char*> compileDefinition(char* input, char* structName){
	/* String stripped */ = input.strip();
	/* int nameSeparator */ = stripped.lastIndexOf(" ");
	/* if (nameSeparator < 0) {
            return Optional.empty();
        } */
	/* String beforeName */ = stripped.substring(0, nameSeparator).strip();
	/* String oldName */ = stripped.substring(nameSeparator + " ".length()).strip();
	/* if (!isSymbol(oldName)) {
            return Optional.empty();
        } */
	/* String newName */;
	/* if (oldName.equals("main")) {
            newName = "__main__";
        } */
	/* else if (oldName.equals(structName)) {
            newName = "new_" + oldName;
        } */
	/* else {
            newName = oldName;
        } */
	/* int typeSeparator */ = beforeName.lastIndexOf(" ");
	/* String type */ = /* typeSeparator >= 0
                ? beforeName */.substring(typeSeparator + " ".length())
                : beforeName;
	/* return compileType(type).flatMap(outputType -> {
            String outputDefinition = outputType + " " + newName;
            return Optional.of(outputDefinition);
        } */
	/* ) */;
}
Optional<char*> compileType(char* type){
	/* String stripped */ = type.strip();
	/* if (stripped.equals("private")) {
            return Optional.empty();
        } */
	/* if (stripped.equals("void")) {
            return Optional.of("void");
        } */
	/* if (stripped.equals("String")) {
            return Optional.of("char*");
        } */
	/* if (stripped.equals("int")) {
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
	/* return Optional.of(generatePlaceholder(stripped)) */;
}
Optional<char*> compileStatementOrBlock(char* input){
	/* return compileWhitespace(input)
                .or(() -> compileStatement(input))
                .or(() -> Optional.of("\n\t" + generatePlaceholder(input.strip()))) */;
}
Optional<char*> compileStatement(char* input){
	/* String stripped */ = input.strip();
	/* if (stripped.endsWith(" */;
	/* ")) {
            String slice = stripped.substring(0, stripped.length() - ";".length());
            return compileStatementValue(slice).map(result -> "\n\t" + result + ";");
        } */
	/* return Optional.empty() */;
}
Optional<char*> compileStatementValue(char* input){
	/* int valueSeparator */ = input.indexOf("=");
	/* if (valueSeparator >= 0) {
            String destination = input.substring(0, valueSeparator).strip();
            String source = input.substring(valueSeparator + "=".length()).strip();

            return Optional.of(compileValue(destination) + " = " + compileValue(source));
        } */
	/* return Optional.of(generatePlaceholder(input)) */;
}
char* compileValue(char* input){
	/* String stripped */ = input.strip();
	/* int lastSeparator */ = stripped.lastIndexOf(".");
	/* if (lastSeparator >= 0) {
            String parent = stripped.substring(0, lastSeparator);
            String property = stripped.substring(lastSeparator + ".".length());
            return compileValue(parent) + "." + property;
        } */
	/* if (isSymbol(stripped)) {
            return stripped;
        } */
	/* return generatePlaceholder(stripped) */;
}
/* boolean */ isSymbol(char* input){
	/* for (int i */ = /* 0 */;
	/* i < input.length() */;
	/* i++) {
            char c = input.charAt(i);
            if (Character.isLetter(c)) {
                continue;
            }
            return false;
        } */
	/* return true */;
}
char* generatePlaceholder(char* input){
	/* return "/* " + input + " */" */;
}
