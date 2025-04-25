/* import java.io.IOException; */
/* import java.nio.file.Files; */
/* import java.nio.file.Paths; */
/* import java.util.ArrayList; */
/* import java.util.List; */
/* import java.util.Optional; */
/* import java.util.function.BiFunction; */
/* import java.util.function.Function; */
/*  */
struct State {
	/* private final */ List<char*> segments;
	/* private */ int depth;
	/* private */ struct StringBuilder buffer;
};
struct Main {
	/* public static final List<String> structs = */ struct new ArrayList<>();
	/* private static final List<String> functions = */ struct new ArrayList<>();
	/* private static Optional<String> currentStructName */ struct = Optional.empty();
};
struct private State(List<char*> segments, struct StringBuilder buffer, int depth){/* 
            this.buffer = buffer; *//* 
            this.segments = segments; *//* 
            this.depth = depth; *//* 
         */
}
/* private static */ struct State createDefault(/*  */){/* 
            return new State(new ArrayList<>(), new StringBuilder(), 0); *//* 
         */
}
/* private */ struct State advance(/*  */){/* 
            this.segments.add(this.buffer.toString()); *//* 
            this.buffer = new StringBuilder(); *//* 
            return this; *//* 
         */
}
/* private */ struct State append(struct char c){/* 
            this.buffer.append(c); *//* 
            return this; *//* 
         */
}
/* public */ struct boolean isLevel(/*  */){/* 
            return this.depth == 0; *//* 
         */
}
/* public */ struct State enter(/*  */){/* 
            this.depth++; *//* 
            return this; *//* 
         */
}
/* public */ struct State exit(/*  */){/* 
            this.depth--; *//* 
            return this; *//* 
         */
}
/* public */ struct boolean isShallow(/*  */){/* 
            return this.depth == 1; *//* 
         */
}
/* public static */ struct void main(/*  */){/* 
        try {
            var source = Paths.get(".", "src", "java", "magma", "Main.java");
            var input = Files.readString(source);

            var target = source.resolveSibling("Main.c");
            Files.writeString(target, compileRoot(input));
        } *//*  catch (IOException e) {
            e.printStackTrace();
        } *//* 
     */
}
/* private static */ char* compileRoot(char* input){/* 
        var output = compileStatements(input, Main::compileRootSegment); *//* 
        var joinedStructs = String.join("", structs); *//* 
        var joinedFunctions = String.join("", functions); *//* 
        return output + joinedStructs + joinedFunctions; *//* 
     */
}
/* private static */ char* compileStatements(char* input, /*  Function<String */, struct String> compiler){/* 
        return compileAll(input, Main::foldStatementChar, compiler, Main::mergeStatements); *//* 
     */
}
/* private static */ char* compileAll(char* input, /* 
            BiFunction<State */, /*  Character */, struct State> folder, /* 
            Function<String */, struct String> compiler, /* 
            BiFunction<StringBuilder */, /*  String */, struct StringBuilder> merger){/* 
        var segments = divide(input, folder); *//* 

        var output = new StringBuilder(); *//* 
        for (var segment : segments) {
            var compiled = compiler.apply(segment);
            output = merger.apply(output, compiled);
        } *//* 

        return output.toString(); *//* 
     */
}
/* private static */ struct StringBuilder mergeStatements(struct StringBuilder output, char* compiled){/* 
        return output.append(compiled); *//* 
     */
}
/* private static */ List<char*> divide(char* input, /*  BiFunction<State */, /*  Character */, struct State> folder){/* 
        return divideAll(input, folder); *//* 
     */
}
/* private static */ List<char*> divideAll(char* input, /*  BiFunction<State */, /*  Character */, struct State> folder){/* 
        var current = State.createDefault(); *//* 
        for (var i = 0; *//*  i < input.length(); *//*  i++) {
            var c = input.charAt(i);

            if (c == '\'') {
                current.append(c);

                i++;
                var maybeSlash = input.charAt(i);
                current.append(maybeSlash);

                if (maybeSlash == '\\') {
                    i++;
                    current.append(input.charAt(i));
                }

                i++;
                current.append(input.charAt(i));
                continue;
            }

            current = folder.apply(current, c);
        } *//* 
        return current.advance().segments; *//* 
     */
}
/* private static */ struct State foldStatementChar(struct State current, struct char c){/* 
        var appended = current.append(c); *//* 
        if (c == ';' && appended.isLevel()) {
            return appended.advance();
        } *//* 
        if (c == '}' && appended.isShallow()) {
            return appended.advance().exit();
        } *//* 
        if (c == '{') {
            return appended.enter();
        } *//* 
        if (c == '}') {
            return appended.exit();
        } *//* 

        return appended; *//* 
     */
}
/* private static */ char* compileRootSegment(char* input){/* 
        var stripped = input.strip(); *//* 

        if (stripped.startsWith("package ")) {
            return "";
        } *//* 

        return compileClass(stripped).orElseGet(() -> generatePlaceholder(stripped) + "\n"); *//* 
     */
}
/* private static */ Optional<char*> compileClass(char* stripped){/* 
        var classIndex = stripped.indexOf("class "); *//* 
        if (classIndex >= 0) {
            var afterKeyword = stripped.substring(classIndex + "class ".length());
            var contentStart = afterKeyword.indexOf("{");
            if (contentStart >= 0) {
                var name = afterKeyword.substring(0, contentStart).strip();
                if (isSymbol(name)) {
                    var withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
                    if (withEnd.endsWith("}")) {
                        var content = withEnd.substring(0, withEnd.length() - "}".length());

                        currentStructName = Optional.of(name);
                        var generated = "struct " + name + " {" +
                                compileStatements(content, Main::compileClassSegment) +
                                "\n};\n";
                        structs.add(generated);
                        return Optional.of("");
                    }
                }
            }
        } *//* 
        return Optional.empty(); *//* 
     */
}
/* private static */ struct boolean isSymbol(char* input){/* 
        for (var i = 0; *//*  i < input.length(); *//*  i++) {
            var c = input.charAt(i);
            if (Character.isLetter(c)) {
                continue;
            }
            return false;
        } *//* 
        return true; *//* 
     */
}
/* private static */ char* compileClassSegment(char* input){/* 
        return compileWhitespace(input)
                .or(() -> compileClass(input))
                .or(() -> compileDefinitionStatement(input))
                .or(() -> compileMethod(input))
                .orElseGet(() -> "\n\t" + generatePlaceholder(input.strip())); *//* 
     */
}
/* private static */ Optional<char*> compileWhitespace(char* input){/* 
        if (input.isBlank()) {
            return Optional.of("");
        } *//* 
        else {
            return Optional.empty();
        } *//* 
     */
}
/* private static */ Optional<char*> compileMethod(char* input){/* 
        var paramStart = input.indexOf("("); *//* 
        if (paramStart >= 0) {
            var beforeParams = input.substring(0, paramStart).strip();
            var withParams = input.substring(paramStart + "(".length());

            var header = compileDefinition(beforeParams)
                    .or(() -> compileConstructorHeader(beforeParams))
                    .orElseGet(() -> compileDefinitionOrPlaceholder(beforeParams));

            var paramEnd = withParams.indexOf(")");
            if (paramEnd >= 0) {
                var params = withParams.substring(0, paramEnd).strip();
                var withBraces = withParams.substring(paramEnd + ")".length()).strip();
                if (withBraces.startsWith("{") && withBraces.endsWith("}")) {
                    var content = withBraces.substring(1, withBraces.length() - 1);
                    return assembleMethod(header, params, content);
                }
            }
        } *//* 

        return Optional.empty(); *//* 
     */
}
/* private static */ Optional<char*> compileConstructorHeader(char* beforeParams){/* 
        var nameSeparator = beforeParams.lastIndexOf(" "); *//* 
        if (nameSeparator >= 0) {
            var name = beforeParams.substring(nameSeparator + " ".length());
            if (currentStructName.isPresent() && currentStructName.get().equals(name)) {
                var header = "struct " + name + " new_" + name;
                return Optional.of(header);
            }
        } *//* 
        return Optional.empty(); *//* 
     */
}
/* private static */ Optional<char*> assembleMethod(char* header, char* params, char* content){/* 
        var constructor = header + "(" + compileValues(params, Main::compileDefinitionOrPlaceholder) + "){" + compileStatements(content, Main::compileStatement) + "\n} *//* \n"; *//* 
        functions.add(constructor); *//* 
        return Optional.of(""); *//* 
     */
}
/* private static */ char* compileStatement(char* input){/* 
        return generatePlaceholder(input); *//* 
     */
}
/* private static */ Optional<char*> compileDefinitionStatement(char* input){/* 
        var stripped = input.strip(); *//* 
        if (stripped.endsWith("; *//* ")) {
            var withoutEnd = stripped.substring(0, stripped.length() - ";".length());
            return Optional.of("\n\t" + compileDefinitionOrPlaceholder(withoutEnd) + ";");
        } *//* 
        else {
            return Optional.empty();
        } *//* 
     */
}
/* private static */ char* compileDefinitionOrPlaceholder(char* input){/* 
        return compileDefinition(input).orElseGet(() -> generatePlaceholder(input)); *//* 
     */
}
/* private static */ Optional<char*> compileDefinition(char* input){/* 
        var stripped = input.strip(); *//* 
        var nameSeparator = stripped.lastIndexOf(" "); *//* 
        if (nameSeparator < 0) {
            return Optional.empty();
        } *//* 

        var beforeName = stripped.substring(0, nameSeparator).strip(); *//* 
        var name = stripped.substring(nameSeparator + " ".length()).strip(); *//* 

        var typeSeparator = beforeName.lastIndexOf(" "); *//* 
        if (typeSeparator < 0) {
            return Optional.of(generateDefinition("", compileType(beforeName), name));
        } *//* 

        var beforeType = beforeName.substring(0, typeSeparator).strip(); *//* 
        var type = beforeName.substring(typeSeparator + " ".length()).strip(); *//* 
        return Optional.of(generateDefinition(generatePlaceholder(beforeType) + " ", compileType(type), name)); *//* 
     */
}
/* private static */ char* generateDefinition(char* beforeType, char* type, char* name){/* 
        return beforeType + type + " " + name; *//* 
     */
}
/* private static */ char* compileType(char* input){/* 
        var stripped = input.strip(); *//* 
        if (stripped.equals("int")) {
            return "int";
        } *//* 

        if (stripped.equals("String")) {
            return "char*";
        } *//* 

        if (stripped.endsWith(">")) {
            var withoutEnd = stripped.substring(0, stripped.length() - ">".length());
            var argsStart = withoutEnd.indexOf("<");
            if (argsStart >= 0) {
                var base = withoutEnd.substring(0, argsStart).strip();
                var args = withoutEnd.substring(argsStart + "<".length()).strip();
                return base + "<" + compileValues(args, Main::compileType) + ">";
            }
        } *//* 

        return "struct " + stripped; *//* 
     */
}
/* private static */ char* compileValues(char* args, /*  Function<String */, struct String> compiler){/* 
        return compileAll(args, Main::foldValueChar, compiler, Main::mergeValues); *//* 
     */
}
/* private static */ struct State foldValueChar(struct State state, struct char c){/* 
        if (c == ',') {
            return state.advance();
        } *//* 
        else {
            return state.append(c);
        } *//* 
     */
}
/* private static */ struct StringBuilder mergeValues(struct StringBuilder buffer, char* element){/* 
        if (buffer.isEmpty()) {
            return buffer.append(element);
        } *//* 
        return buffer.append(", ").append(element); *//* 
     */
}
/* private static */ char* generatePlaceholder(char* stripped){/* 
        return "/* " + stripped + " */"; *//* 
     */
}
