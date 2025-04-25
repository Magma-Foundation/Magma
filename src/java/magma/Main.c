/* import java.io.IOException; */
/* import java.nio.file.Files; */
/* import java.nio.file.Paths; */
/* import java.util.ArrayList; */
/* import java.util.List; */
/* import java.util.function.BiFunction; */
/* import java.util.function.Function; */
/* import java.util.function.Predicate; */
/* import java.util.function.Supplier; */
/* import java.util.stream.Collectors; */
/*  */
struct Type {
};
struct Definable {
};
struct Optional {
};
struct FunctionSegment {
};
struct StatementValue {
};
struct Assignable {
};
struct Value {
};
struct Primitive {
	/* I8("char"),
        I32("int") */;
	/* private final */ char* value;
};
struct Some {
};
struct None {
};
struct State {
	/* private final */ List<char*> segments;
	/* private */ int depth;
	/* private */ struct StringBuilder buffer;
};
struct Definition {
};
struct Struct {
};
struct Ref {
};
struct Content {
};
struct Generic {
};
struct ConstructorDefinition {
};
struct Statement {
};
struct Whitespace {
};
struct Assignment {
};
struct DataAccess {
};
struct Symbol {
};
struct Return {
};
struct Main {
	/* public static */ List<char*> structs;
	/* private static */ List<char*> functions;
	/* private static */ Optional<char*> currentStructName;
};
struct Primitive new_Primitive(char* value){
	struct Primitive this;
	this.value = value;
	return this;
}
/* @Override
        public */ char* generate(/*  */){
	/* return this.value */;
}
/* @Override
        public <R> */ Optional<struct R> map(/* Function<T */, /*  R> mapper */){
	/* return new Some<>(mapper.apply(this.value)) */;
}
/* @Override
        public */ struct T orElse(struct T other){
	/* return this.value */;
}
/* @Override
        public */ struct T orElseGet(Supplier<struct T> other){
	/* return this.value */;
}
/* @Override
        public */ Optional<struct T> or(Supplier<Optional<struct T>> other){
	struct return this;
}
/* @Override
        public <R> */ Optional<struct R> flatMap(/* Function<T */, Optional</* R> */> mapper){
	/* return mapper.apply(this.value) */;
}
/* @Override
        public */ Optional<struct T> filter(Predicate<struct T> predicate){
	/* return predicate.test(this.value) ? this : new None<>() */;
}
/* @Override
        public */ struct boolean isPresent(/*  */){
	struct return true;
}
/* @Override
        public <R> */ Optional<struct R> map(/* Function<T */, /*  R> mapper */){
	/* return new None<>() */;
}
/* @Override
        public */ struct T orElse(struct T other){
	struct return other;
}
/* @Override
        public */ struct T orElseGet(Supplier<struct T> other){
	/* return other.get() */;
}
/* @Override
        public */ Optional<struct T> or(Supplier<Optional<struct T>> other){
	/* return other.get() */;
}
/* @Override
        public <R> */ Optional<struct R> flatMap(/* Function<T */, Optional</* R> */> mapper){
	/* return new None<>() */;
}
/* @Override
        public */ Optional<struct T> filter(Predicate<struct T> predicate){
	struct return this;
}
/* @Override
        public */ struct boolean isPresent(/*  */){
	struct return false;
}
struct State new_State(List<char*> segments, struct StringBuilder buffer, int depth){
	struct State this;
	this.buffer = buffer;
	this.segments = segments;
	this.depth = depth;
	return this;
}
/* private static */ struct State createDefault(/*  */){
	/* return new State(new ArrayList<>(), new StringBuilder(), 0) */;
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
	/* return this */.depth = /* = 0 */;
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
	/* return this */.depth = /* = 1 */;
}
struct public Definition(struct Type type, char* name){
	/* this(new None<String>(), type, name) */;
}
/* @Override
        public */ char* generate(/*  */){
	struct var beforeType = this.maybeBeforeType().map(inner -> inner + " ").orElse("");
	/* return beforeType + this.type().generate() + " " + this.name() */;
}
/* @Override
        public */ struct Definition toDefinition(/*  */){
	struct return this;
}
/* @Override
        public */ char* generate(/*  */){
	/* return "struct " + this.name() */;
}
/* @Override
        public */ char* generate(/*  */){
	/* return this.type.generate() + "*" */;
}
/* @Override
        public */ char* generate(/*  */){
	/* return generatePlaceholder(this.input) */;
}
/* @Override
        public */ char* generate(/*  */){
	struct var joinedArgs = this.args().stream().map(Type::generate).collect(Collectors.joining(", "));
	/* return this.base() + "<" + joinedArgs + ">" */;
}
/* @Override
        public */ struct Definition toDefinition(/*  */){
	/* return new Definition(new Struct(this.name()), "new_" + this.name()) */;
}
/* @Override
        public */ char* generate(/*  */){
	/* return "\n\t" + this.content.generate() + " */;
	/* " */;
}
/* @Override
        public */ char* generate(/*  */){
	/* return "" */;
}
/* @Override
        public */ char* generate(/*  */){
	/* return this */.assignable.generate() + " = /* " + this */.value.generate();
}
/* @Override
        public */ char* generate(/*  */){
	/* return this.parent.generate() + "." + this.property */;
}
/* @Override
        public */ char* generate(/*  */){
	/* return this.name */;
}
/* @Override
        public */ char* generate(/*  */){
	/* return "return " + this.value.generate() */;
}
/* public static */ struct void main(/*  */){
	structs = /* new ArrayList<>() */;
	functions = /* new ArrayList<>() */;
	currentStructName = /* new None<>() */;/* 

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
	/* return output + joinedStructs + joinedFunctions */;
}
/* private static */ char* compileStatements(char* input, /*  Function<String */, /*  String> compiler */){
	/* return compileAll(input, Main::foldStatementChar, compiler, Main::mergeStatements) */;
}
/* private static */ char* compileAll(char* input, /* 
            BiFunction<State */, /*  Character */, /*  State> folder */, /* 
            Function<String */, /*  String> compiler */, /* 
            BiFunction<StringBuilder */, /*  String */, /*  StringBuilder> merger */){
	/* return generateAll(merger, parseAll(input, folder, compiler)) */;
}
/* private static */ char* generateAll(/* BiFunction<StringBuilder */, /*  String */, /*  StringBuilder> merger */, List<char*> compiled){
	struct var output = /* new StringBuilder() */;/* 
        for (var segment : compiled) {
            output = merger.apply(output, segment);
        } */
	/* return output.toString() */;
}
/* private static <T> */ List<struct T> parseAll(char* input, /* 
            BiFunction<State */, /*  Character */, /*  State> folder */, /* 
            Function<String */, /*  T> compiler */){
	struct var segments = /* divide(input, folder) */;
	struct var compiled = /* new ArrayList<T>() */;/* 
        for (var segment : segments) {
            compiled.add(compiler.apply(segment));
        } */
	struct return compiled;
}
/* private static */ struct StringBuilder mergeStatements(struct StringBuilder output, char* compiled){
	/* return output.append(compiled) */;
}
/* private static */ List<char*> divide(char* input, /*  BiFunction<State */, /*  Character */, /*  State> folder */){
	/* return divideAll(input, folder) */;
}
/* private static */ List<char*> divideAll(char* input, /*  BiFunction<State */, /*  Character */, /*  State> folder */){
	struct var current = State.createDefault();
	/* for (var i */ = /* 0 */;
	/* i < input.length() */;/*  i++) {
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
	/* return current.advance().segments */;
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
	/* return compileClass(stripped).orElseGet(() -> generatePlaceholder(stripped) + "\n") */;
}
/* private static */ Optional<char*> compileClass(char* stripped){
	/* return compileStructured(stripped, "class ") */;
}
/* private static */ Optional<char*> compileStructured(char* stripped, char* infix){
	struct var classIndex = stripped.indexOf(infix);/* 
        if (classIndex < 0) {
            return new None<>();
        } */
	struct var afterKeyword = stripped.substring(classIndex + infix.length());/* 
        var contentStart = afterKeyword.indexOf("{");
        if (contentStart < 0) {
            return new None<>();
        }
        var beforeContent = afterKeyword.substring(0, contentStart).strip();

        var implementsIndex = beforeContent.indexOf(" implements ");
        var withoutImplements = implementsIndex >= 0
                ? beforeContent.substring(0, implementsIndex).strip()
                : beforeContent;

        var extendsIndex = withoutImplements.indexOf(" extends ");
        var withoutExtends = extendsIndex >= 0
                ? withoutImplements.substring(0, extendsIndex).strip()
                : withoutImplements;

        var paramStart = withoutExtends.indexOf("(");
        var withoutParameters = paramStart >= 0
                ? withoutExtends.substring(0, paramStart).strip()
                : withoutExtends;

        var typeParamStart = withoutParameters.indexOf("<");
        var name = typeParamStart >= 0
                ? withoutParameters.substring(0, typeParamStart).strip()
                : withoutParameters;

        if (!isSymbol(name)) {
            return new None<>();
        }
        var withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
        if (!withEnd.endsWith("}")) {
            return new None<>();
        }
        var content = withEnd.substring(0, withEnd.length() - "} */
	/* ".length()) */;
	currentStructName = /* new Some<>(name) */;/* 
        var generated = "struct " + name + " {" +
                compileStatements(content, Main::compileClassSegment) +
                "\n} */
	/*  */;
	/* \n" */;
	/* structs.add(generated) */;
	/* return new Some<>("") */;
}
/* private static */ struct boolean isSymbol(char* input){
	/* for (var i */ = /* 0 */;
	/* i < input.length() */;/*  i++) {
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
                .or(() -> compileStructured(input, "enum "))
                .or(() -> compileStructured(input, "record "))
                .or(() -> compileStructured(input, "interface "))
                .or(() -> compileMethod(input))
                .or(() -> compileClassStatement(input))
                .orElseGet(() -> "\n\t" + generatePlaceholder(input.strip())) */;
}
/* private static */ Optional<char*> compileWhitespace(char* input){
	/* return parseWhitespace(input).map(Whitespace::generate) */;
}
/* private static */ Optional<struct Whitespace> parseWhitespace(char* input){/* 
        if (input.isBlank()) {
            return new Some<>(new Whitespace());
        } *//* 
        else {
            return new None<>();
        } */
}
/* private static */ Optional<char*> compileMethod(char* input){
	struct var paramStart = input.indexOf("(");/* 
        if (paramStart < 0) {
            return new None<>();
        } */
	struct var beforeParams = input.substring(0, paramStart).strip();
	struct var withParams = input.substring(paramStart + "(".length());
	struct var maybeHeader = /* parseDefinition(beforeParams) */.<Definable>map(value -> value).or(() -> compileConstructorDefinition(beforeParams).map(value -> value));/* 

        if (!(maybeHeader instanceof Some(var header))) {
            return new None<>();
        } */
	struct var paramEnd = withParams.indexOf(")");/* 
        if (paramEnd >= 0) {
            var inputParams = withParams.substring(0, paramEnd).strip();
            var afterParams = withParams.substring(paramEnd + ")".length()).strip();
            if (afterParams.startsWith("{") && afterParams.endsWith("}")) {
                var content = afterParams.substring(1, afterParams.length() - 1);
                var outputParams = compileValues(inputParams, Main::compileDefinitionOrPlaceholder);

                var oldStatements = parseStatements(content, Main::parseStatement);
                ArrayList<FunctionSegment> newStatements;
                if (header instanceof ConstructorDefinition(var name)) {
                    var copy = new ArrayList<FunctionSegment>();
                    copy.add(new Statement(new Definition(new Struct(name), "this")));
                    copy.addAll(oldStatements);
                    copy.add(new Statement(new Return(new Symbol("this"))));
                    newStatements = copy;
                }
                else {
                    newStatements = new ArrayList<>(oldStatements);
                }

                var outputContent = newStatements
                        .stream()
                        .map(FunctionSegment::generate)
                        .collect(Collectors.joining());

                var constructor = header.toDefinition().generate() + "(" + outputParams + "){" + outputContent + "\n}\n";
                functions.add(constructor);
                return new Some<>("");
            }
            if (afterParams.equals(";")) {
                return new Some<>("");
            }
        } */
	/* return new None<>() */;
}
/* private static <T> */ List<struct T> parseStatements(char* content, /*  Function<String */, /*  T> compiler */){
	/* return parseAll(content, Main::foldStatementChar, compiler) */;
}
/* private static */ Optional<struct ConstructorDefinition> compileConstructorDefinition(char* input){/* 
        return findConstructorDefinitionName(input).flatMap(name -> {
            if (currentStructName.filter(structName -> structName.equals(name)).isPresent()) {
                return new Some<>(new ConstructorDefinition(name));
            }
            return new None<>();
        } */
	/* ) */;
}
/* private static */ Optional<char*> findConstructorDefinitionName(char* input){
	struct var stripped = input.strip();
	struct var nameSeparator = stripped.lastIndexOf(" ");/* 
        if (nameSeparator >= 0) {
            var name = stripped.substring(nameSeparator + " ".length());
            return new Some<>(name);
        } *//* 
        if (isSymbol(stripped)) {
            return new Some<>(stripped);
        } */
	/* return new None<>() */;
}
/* private static */ struct FunctionSegment parseStatement(char* input){
	/* return parseWhitespace(input)
                .<FunctionSegment>map(value -> value)
                .or(() -> parseStatementWithoutBraces(input, Main::parseStatementValue).map(value -> value))
                .orElseGet(() -> new Content(input)) */;
}
/* private static */ struct StatementValue parseStatementValue(char* input){
	/* return compileAssignment(input)
                .map(value -> value)
                .or(() -> parseDefinition(input).map(value -> value))
                .orElseGet(() -> new Content(input)) */;
}
/* private static */ Optional<char*> compileClassStatement(char* input){
	/* return compileStatement(input, Main::compileClassStatementValue) */;
}
/* private static */ Optional<char*> compileStatement(char* input, /*  Function<String */, /*  StatementValue> compiler */){
	/* return parseStatementWithoutBraces(input, compiler).map(Statement::generate) */;
}
/* private static */ Optional<struct Statement> parseStatementWithoutBraces(char* input, /*  Function<String */, /*  StatementValue> compiler */){
	struct var stripped = input.strip();
	/* if (stripped.endsWith(" */;/* ")) {
            var withoutEnd = stripped.substring(0, stripped.length() - ";".length());
            var content = compiler.apply(withoutEnd);
            return new Some<>(new Statement(content));
        } *//* 
        else {
            return new None<>();
        } */
}
/* private static */ struct StatementValue compileClassStatementValue(char* input){
	/* return compileAssignment(input)
                .map(value -> value)
                .or(() -> parseDefinition(input).map(value -> value))
                .orElseGet(() -> new Content(input)) */;
}
/* private static */ Optional<struct StatementValue> compileAssignment(char* input){
	struct var valueSeparator = input.indexOf("=");/* 
        if (valueSeparator >= 0) {
            var inputDefinition = input.substring(0, valueSeparator);
            var value = input.substring(valueSeparator + "=".length());

            var destination = parseDefinition(inputDefinition)
                    .<Assignable>map(result -> result)
                    .orElseGet(() -> parseValue(inputDefinition));

            return new Some<>(new Assignment(destination, parseValue(value)));
        } */
	/* return new None<>() */;
}
/* private static */ struct Value parseValue(char* input){
	struct var stripped = input.strip();
	struct var separator = stripped.lastIndexOf(".");/* 
        if (separator >= 0) {
            var parent = stripped.substring(0, separator);
            var property = stripped.substring(separator + ".".length()).strip();
            var value = parseValue(parent);
            return new DataAccess(value, property);
        } *//* 

        if (isSymbol(stripped)) {
            return new Symbol(stripped);
        } */
	/* return new Content(stripped) */;
}
/* private static */ char* compileDefinitionOrPlaceholder(char* input){
	/* return parseDefinition(input).map(Definition::generate).orElseGet(() -> generatePlaceholder(input)) */;
}
/* private static */ Optional<struct Definition> parseDefinition(char* input){
	struct var stripped = input.strip();
	struct var nameSeparator = stripped.lastIndexOf(" ");/* 
        if (nameSeparator < 0) {
            return new None<>();
        } */
	struct var beforeName = stripped.substring(0, nameSeparator).strip();
	struct var name = stripped.substring(nameSeparator + " ".length()).strip();/* 
        if (!isSymbol(name)) {
            return new None<>();
        } */
	struct var typeSeparator = beforeName.lastIndexOf(" ");/* 
        if (typeSeparator < 0) {
            return parseType(beforeName).map(type -> new Definition(new None<String>(), type, name));
        } */
	struct var beforeType = beforeName.substring(0, typeSeparator).strip();
	struct var inputType = beforeName.substring(typeSeparator + " ".length()).strip();
	/* return parseType(inputType).map(outputType -> new Definition(new Some<String>(generatePlaceholder(beforeType)), outputType, name)) */;
}
/* private static */ Optional<struct Type> parseType(char* input){
	struct var stripped = input.strip();/* 
        if (stripped.equals("private")) {
            return new None<>();
        } *//* 

        if (stripped.equals("int")) {
            return new Some<>(Primitive.I32);
        } *//* 

        if (stripped.equals("String")) {
            return new Some<>(new Ref(Primitive.I8));
        } *//* 

        if (stripped.endsWith(">")) {
            var withoutEnd = stripped.substring(0, stripped.length() - ">".length());
            var argsStart = withoutEnd.indexOf("<");
            if (argsStart >= 0) {
                var base = withoutEnd.substring(0, argsStart).strip();
                var argsString = withoutEnd.substring(argsStart + "<".length()).strip();
                var args = parseValues(argsString, input1 -> parseType(input1).orElseGet(() -> new Content(input1)));

                return new Some<>(new Generic(base, args));
            }
        } *//* 

        if (isSymbol(stripped)) {
            return new Some<>(new Struct(stripped));
        } */
	/* return new None<>() */;
}
/* private static */ char* compileValues(char* args, /*  Function<String */, /*  String> compiler */){
	/* return generateValues(parseValues(args, compiler)) */;
}
/* private static */ char* generateValues(List<char*> values){
	/* return generateAll(Main::mergeValues, values) */;
}
/* private static <T> */ List<struct T> parseValues(char* args, /*  Function<String */, /*  T> compiler */){
	/* return parseAll(args, Main::foldValueChar, compiler) */;
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
	/* return buffer.append(", ").append(element) */;
}
/* private static */ char* generatePlaceholder(char* stripped){
	/* return "/* " + stripped + " */" */;
}
