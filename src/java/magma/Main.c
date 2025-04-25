/* import java.io.IOException; */
/* import java.nio.file.Files; */
/* import java.nio.file.Paths; */
/* import java.util.ArrayList; */
/* import java.util.List; */
/* import java.util.Optional; */
/* import java.util.function.BiFunction; */
/* import java.util.function.Function; */
/* import java.util.stream.Collectors; */
/*  */
struct Type {
	char* generate();
};
struct State {
	/* private final */ List<char*> segments;
	/* private */ int depth;
	/* private */ struct StringBuilder buffer;
};
struct Main {
	/* private enum Primitive implements Type {
        I8("char"),
        I32("int");

        private final String value;

        Primitive(String value) {
            this.value = value;
        }

        @Override
        public String generate() {
            return this.value;
        }
    } */
	/* private record Struct(String name) implements Type {
        @Override
        public String generate() {
            return "struct " + this.name();
        }
    } */
	/* private record Ref(Type type) implements Type {
        @Override
        public String generate() {
            return this.type.generate() + "*";
        }
    } */
	/* private record Content(String input) implements Type {
        @Override
        public String generate() {
            return generatePlaceholder(this.input);
        }
    } */
	/* private record Generic(String base, List<Type> args) implements Type {
        @Override
        public String generate() {
            var joinedArgs = this.args().stream()
                    .map(Type::generate)
                    .collect(Collectors.joining(", "));

            return this.base() + "<" + joinedArgs + ">";
        }
    } */
	/* public static */ List<char*> structs;
	/* private static */ List<char*> functions;
	/* private static */ Optional<char*> currentStructName;
};
struct State new_State(List<char*> segments, struct StringBuilder buffer, int depth){
	this.buffer = buffer;
	this.segments = segments;
	this.depth = depth;
}
/* private static */ struct State createDefault(/*  */){
	/* return new State(new ArrayList<>(), new */ struct StringBuilder(), 0);
}
/* private */ struct State advance(/*  */){
	/* this.segments.add(this.buffer.toString()) */;
	this.buffer = /* new StringBuilder() */;
	struct return this;
}
/* private */ struct State append(struct char c){
	/* this.buffer.append(c) */;
	struct return this;
}
/* public */ struct boolean isLevel(/*  */){
	struct return this.depth = /* = 0 */;
}
/* public */ struct State enter(/*  */){
	/* this.depth++ */;
	struct return this;
}
/* public */ struct State exit(/*  */){
	/* this.depth-- */;
	struct return this;
}
/* public */ struct boolean isShallow(/*  */){
	struct return this.depth = /* = 1 */;
}
/* private */ struct record Definition(Optional<char*> maybeBeforeType, struct Type type, char* name){/* 
        public Definition(Type type, String name) {
            this(Optional.empty(), type, name);
        } *//* 

        private String generate() {
            var beforeType = this.maybeBeforeType().map(inner -> inner + " ").orElse("");
            return beforeType + this.type().generate() + " " + this.name();
        } */
}
/* public static */ struct void main(/*  */){
	structs = /* new ArrayList<>() */;
	functions = /* new ArrayList<>() */;
	currentStructName = Optional.empty();/* 

        try {
            var source = Paths.get(".", "src", "java", "magma", "Main.java");
            var input = Files.readString(source);

            var target = source.resolveSibling("Main.c");
            Files.writeString(target, compileRoot(input));
        } *//*  catch (IOException e) {
            e.printStackTrace();
        } */
}
/* private static */ char* compileRoot(char* input){
	struct var output = /* compileStatements(input, Main::compileRootSegment) */;
	struct var joinedStructs = String.join("", structs);
	struct var joinedFunctions = String.join("", functions);
	/* return output + joinedStructs */ struct + joinedFunctions;
}
/* private static */ char* compileStatements(char* input, /*  Function<String */, struct String> compiler){
	/* return compileAll(input, Main::foldStatementChar, */ struct compiler, Main::mergeStatements);
}
/* private static */ char* compileAll(char* input, /* 
            BiFunction<State */, /*  Character */, struct State> folder, /* 
            Function<String */, struct String> compiler, /* 
            BiFunction<StringBuilder */, /*  String */, struct StringBuilder> merger){
	/* return generateAll(merger, parseAll(input, */ struct folder, compiler));
}
/* private static */ char* generateAll(/* BiFunction<StringBuilder */, /*  String */, struct StringBuilder> merger, List<char*> compiled){
	struct var output = /* new StringBuilder() */;/* 
        for (var segment : compiled) {
            output = merger.apply(output, segment);
        } */
	struct return output.toString();
}
/* private static <T> */ List<struct T> parseAll(char* input, /* 
            BiFunction<State */, /*  Character */, struct State> folder, /* 
            Function<String */, struct T> compiler){
	struct var segments = /* divide(input, folder) */;
	struct var compiled = /* new ArrayList<T>() */;/* 
        for (var segment : segments) {
            compiled.add(compiler.apply(segment));
        } */
	struct return compiled;
}
/* private static */ struct StringBuilder mergeStatements(struct StringBuilder output, char* compiled){
	struct return output.append(compiled);
}
/* private static */ List<char*> divide(char* input, /*  BiFunction<State */, /*  Character */, struct State> folder){
	/* return */ struct divideAll(input, folder);
}
/* private static */ List<char*> divideAll(char* input, /*  BiFunction<State */, /*  Character */, struct State> folder){
	struct var current = State.createDefault();
	/* for */ struct (var i = /* 0 */;
	/* i */ struct < input.length();/*  i++) {
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
        } */
	struct return current.advance().segments;
}
/* private static */ struct State foldStatementChar(struct State current, struct char c){
	struct var appended = current.append(c);/* 
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
        } */
	struct return appended;
}
/* private static */ char* compileRootSegment(char* input){
	struct var stripped = input.strip();/* 

        if (stripped.startsWith("package ")) {
            return "";
        } */
	/* return compileClass(stripped).orElseGet(() -> generatePlaceholder(stripped) */ struct + "\n");
}
/* private static */ Optional<char*> compileClass(char* stripped){
	/* return compileStructured(stripped, */ struct "class ");
}
/* private static */ Optional<char*> compileStructured(char* stripped, char* infix){
	struct var classIndex = stripped.indexOf(infix);/* 
        if (classIndex < 0) {
            return Optional.empty();
        } */
	struct var afterKeyword = stripped.substring(classIndex + infix.length());/* 
        var contentStart = afterKeyword.indexOf("{");
        if (contentStart < 0) {
            return Optional.empty();
        }
        var name = afterKeyword.substring(0, contentStart).strip();
        if (!isSymbol(name)) {
            return Optional.empty();
        }
        var withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
        if (!withEnd.endsWith("}")) {
            return Optional.empty();
        }
        var content = withEnd.substring(0, withEnd.length() - "} */
	/* ".length()) */;
	currentStructName = Optional.of(name);/* 
        var generated = "struct " + name + " {" +
                compileStatements(content, Main::compileClassSegment) +
                "\n} */
	/*  */;
	/* \n" */;
	/* structs.add(generated) */;
	struct return Optional.of("");
}
/* private static */ struct boolean isSymbol(char* input){
	/* for */ struct (var i = /* 0 */;
	/* i */ struct < input.length();/*  i++) {
            var c = input.charAt(i);
            if (Character.isLetter(c)) {
                continue;
            }
            return false;
        } */
	struct return true;
}
/* private static */ char* compileClassSegment(char* input){
	/* return compileWhitespace(input)
                .or(() -> compileClass(input))
                .or(() -> compileStructured(input, "interface "))
                .or(() -> compileStructured(input, "record "))
                .or(() -> compileClassStatement(input))
                .or(() -> compileMethod(input))
                .orElseGet(() -> "\n\t" */ struct + generatePlaceholder(input.strip()));
}
/* private static */ Optional<char*> compileWhitespace(char* input){/* 
        if (input.isBlank()) {
            return Optional.of("");
        } *//* 
        else {
            return Optional.empty();
        } */
}
/* private static */ Optional<char*> compileMethod(char* input){
	struct var paramStart = input.indexOf("(");/* 
        if (paramStart >= 0) {
            var beforeParams = input.substring(0, paramStart).strip();
            var withParams = input.substring(paramStart + "(".length());

            var header = compileDefinition(beforeParams)
                    .or(() -> compileConstructorDefinition(beforeParams))
                    .orElseGet(() -> compileDefinitionOrPlaceholder(beforeParams));

            var paramEnd = withParams.indexOf(")");
            if (paramEnd >= 0) {
                var params = withParams.substring(0, paramEnd).strip();
                var withBraces = withParams.substring(paramEnd + ")".length()).strip();
                if (withBraces.startsWith("{") && withBraces.endsWith("}")) {
                    var content = withBraces.substring(1, withBraces.length() - 1);
                    var constructor = header + "(" + compileValues(params, Main::compileDefinitionOrPlaceholder) + "){" + compileStatements(content, Main::compileStatementOrBlock) + "\n}\n";
                    functions.add(constructor);
                    return Optional.of("");
                }
            }
        } */
	struct return Optional.empty();
}
/* private static */ Optional<char*> compileConstructorDefinition(char* beforeParams){
	struct var nameSeparator = beforeParams.lastIndexOf(" ");/* 
        if (nameSeparator >= 0) {
            var name = beforeParams.substring(nameSeparator + " ".length());
            if (currentStructName.isPresent() && currentStructName.get().equals(name)) {
                return Optional.of(new Definition(new Struct(name), "new_" + name).generate());
            }
        } */
	struct return Optional.empty();
}
/* private static */ char* compileStatementOrBlock(char* input){
	/* return compileWhitespace(input)
                .or(() -> compileStatement(input, Main::compileStatementValue))
                .orElseGet(() */ struct -> generatePlaceholder(input));
}
/* private static */ char* compileStatementValue(char* input){
	/* return compileAssignable(input)
                .or(() -> compileDefinition(input))
                .orElseGet(() */ struct -> generatePlaceholder(input));
}
/* private static */ Optional<char*> compileClassStatement(char* input){
	/* return */ struct compileStatement(input, Main::compileClassStatementValue);
}
/* private static */ Optional<char*> compileStatement(char* input, /*  Function<String */, struct String> compiler){
	struct var stripped = input.strip();
	struct if (stripped.endsWith(";/* ")) {
            var withoutEnd = stripped.substring(0, stripped.length() - ";".length());
            return Optional.of("\n\t" + compiler.apply(withoutEnd) + ";");
        } *//* 
        else {
            return Optional.empty();
        } */
}
/* private static */ char* compileClassStatementValue(char* input){
	/* return compileAssignable(input)
                .or(() -> compileDefinition(input))
                .orElseGet(() */ struct -> generatePlaceholder(input));
}
/* private static */ Optional<char*> compileAssignable(char* input){
	struct var valueSeparator = input.indexOf("=");/* 
        if (valueSeparator >= 0) {
            var inputDefinition = input.substring(0, valueSeparator);
            var value = input.substring(valueSeparator + "=".length());

            var destination = compileDefinition(inputDefinition).orElseGet(() -> compileValue(inputDefinition));
            return Optional.of(destination + " = " + compileValue(value));
        } */
	struct return Optional.empty();
}
/* private static */ char* compileValue(char* input){
	struct var stripped = input.strip();
	struct var separator = stripped.lastIndexOf(".");/* 
        if (separator >= 0) {
            var parent = stripped.substring(0, separator);
            var property = stripped.substring(separator + ".".length()).strip();
            return compileValue(parent) + "." + property;
        } *//* 

        if (isSymbol(stripped)) {
            return stripped;
        } */
	struct return generatePlaceholder(stripped);
}
/* private static */ char* compileDefinitionOrPlaceholder(char* input){
	/* return compileDefinition(input).orElseGet(() */ struct -> generatePlaceholder(input));
}
/* private static */ Optional<char*> compileDefinition(char* input){
	struct var stripped = input.strip();
	struct var nameSeparator = stripped.lastIndexOf(" ");/* 
        if (nameSeparator < 0) {
            return Optional.empty();
        } */
	struct var beforeName = stripped.substring(0, nameSeparator).strip();
	struct var name = stripped.substring(nameSeparator + " ".length()).strip();
	struct var typeSeparator = beforeName.lastIndexOf(" ");/* 
        if (typeSeparator < 0) {
            return parseType(beforeName).map(type -> new Definition(Optional.empty(), type, name).generate());
        } */
	struct var beforeType = beforeName.substring(0, typeSeparator).strip();
	struct var inputType = beforeName.substring(typeSeparator + " ".length()).strip();
	/* return parseType(inputType).map(outputType -> new Definition(Optional.of(generatePlaceholder(beforeType)), */ struct outputType, name).generate());
}
/* private static */ Optional<char*> compileType(char* input){
	struct return parseType(input).map(Type::generate);
}
/* private static */ Optional<struct Type> parseType(char* input){
	struct var stripped = input.strip();/* 
        if (stripped.equals("private")) {
            return Optional.empty();
        } *//* 

        if (stripped.equals("int")) {
            return Optional.of(Primitive.I32);
        } *//* 

        if (stripped.equals("String")) {
            return Optional.of(new Ref(Primitive.I8));
        } *//* 

        if (stripped.endsWith(">")) {
            var withoutEnd = stripped.substring(0, stripped.length() - ">".length());
            var argsStart = withoutEnd.indexOf("<");
            if (argsStart >= 0) {
                var base = withoutEnd.substring(0, argsStart).strip();
                var argsString = withoutEnd.substring(argsStart + "<".length()).strip();
                var args = parseValues(argsString, input1 -> parseType(input1).orElseGet(() -> new Content(input1)));

                return Optional.of(new Generic(base, args));
            }
        } */
	/* return */ struct Optional.of(new Struct(stripped));
}
/* private static */ char* compileValues(char* args, /*  Function<String */, struct String> compiler){
	/* return */ struct generateValues(parseValues(args, compiler));
}
/* private static */ char* generateValues(List<char*> values){
	/* return */ struct generateAll(Main::mergeValues, values);
}
/* private static <T> */ List<struct T> parseValues(char* args, /*  Function<String */, struct T> compiler){
	/* return parseAll(args, */ struct Main::foldValueChar, compiler);
}
/* private static */ struct State foldValueChar(struct State state, struct char c){/* 
        if (c == ',') {
            return state.advance();
        } *//* 
        else {
            return state.append(c);
        } */
}
/* private static */ struct StringBuilder mergeValues(struct StringBuilder buffer, char* element){/* 
        if (buffer.isEmpty()) {
            return buffer.append(element);
        } */
	/* return */ struct buffer.append(", ").append(element);
}
/* private static */ char* generatePlaceholder(char* stripped){
	/* return "/* " + stripped + */ struct " */";
}
