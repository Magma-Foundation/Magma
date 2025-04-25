/* import java.io.IOException; */
/* import java.nio.file.Files; */
/* import java.nio.file.Paths; */
/* import java.util.ArrayList; */
/* import java.util.Collections; */
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
struct Option<T> {
};
struct FunctionSegment {
};
struct StatementValue {
};
struct Assignable {
};
struct Value {
};
struct Parameter {
};
struct Primitive {
	/* I8("char"),
        I32("int") */;
	private final char* value;
	/* Primitive(String value) {
            this.value = value;
        } */
};
struct Some<T> {
};
struct None<T> {
};
struct State {
	private final List<char*> segments;
	private int depth;
	private struct StringBuilder buffer;
	/* private State(List<String> segments, StringBuilder buffer, int depth) {
            this.buffer = buffer;
            this.segments = segments;
            this.depth = depth;
        } */
};
struct Definition {
	private final Option<char*> maybeBeforeType;
	private final struct Type type;
	private final char* name;
	private final List<char*> typeParams;
	/* Definition(
                Option<String> maybeBeforeType,
                Type type, String name, List<String> typeParams) {
            this.maybeBeforeType = maybeBeforeType;
            this.type = type;
            this.name = name;
            this.typeParams = typeParams;
        } */
};
struct Struct {
	/* private Struct(String name) {
            this(name, Collections.emptyList());
        } */
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
struct DefinitionBuilder {
	private Option<char*> maybeBeforeType = /* new None<String>() */;
	private struct Type type;
	private char* name;
	private List<char*> typeParams = /* new ArrayList<>() */;
};
struct Tuple<A,  B> {
};
struct TypeParam {
};
struct Main {
	private static final List<char*> typeParams = /* new ArrayList<>() */;
	public static List<char*> structs;
	private static List<char*> functions;
	private static List<Tuple<char*, List<char*>>> stack;
};
@Override
        public char* generate_Primitive(struct Primitive this){
	return this.value;
}
/* @Override
        public  */ Option<R> map_Some<T, R>(struct Some<T> this, Function<T, struct R> mapper){
	return /* new Some<>(mapper.apply(this.value)) */;
}
@Override
        public T orElse_Some<T>(struct Some<T> this, T other){
	return this.value;
}
@Override
        public T orElseGet_Some<T>(struct Some<T> this, Supplier<T> other){
	return this.value;
}
@Override
        public Option<T> or_Some<T>(struct Some<T> this, Supplier<Option<T>> other){
	return this;
}
/* @Override
        public  */ Option<R> flatMap_Some<T, R>(struct Some<T> this, Function<T, Option<R>> mapper){
	return /* mapper.apply(this.value) */;
}
@Override
        public Option<T> filter_Some<T>(struct Some<T> this, Predicate<T> predicate){
	return /* predicate.test(this.value) ? this : new None<>() */;
}
@Override
        public struct boolean isPresent_Some<T>(struct Some<T> this){
	return true;
}
/* @Override
        public  */ Option<R> map_None<T, R>(struct None<T> this, Function<T, struct R> mapper){
	return /* new None<>() */;
}
@Override
        public T orElse_None<T>(struct None<T> this, T other){
	return other;
}
@Override
        public T orElseGet_None<T>(struct None<T> this, Supplier<T> other){
	return /* other.get() */;
}
@Override
        public Option<T> or_None<T>(struct None<T> this, Supplier<Option<T>> other){
	return /* other.get() */;
}
/* @Override
        public  */ Option<R> flatMap_None<T, R>(struct None<T> this, Function<T, Option<R>> mapper){
	return /* new None<>() */;
}
@Override
        public Option<T> filter_None<T>(struct None<T> this, Predicate<T> predicate){
	return this;
}
@Override
        public struct boolean isPresent_None<T>(struct None<T> this){
	return false;
}
private static struct State createDefault_State(struct State this){
	return /* new State(new ArrayList<>(), new StringBuilder(), 0) */;
}
private struct State advance_State(struct State this){
	/* this.segments.add(this.buffer.toString()) */;
	this.buffer = /* new StringBuilder() */;
	return this;
}
private struct State append_State(struct State this, struct char c){
	/* this.buffer.append(c) */;
	return this;
}
public struct boolean isLevel_State(struct State this){
	return /* this.depth == 0 */;
}
public struct State enter_State(struct State this){
	/* this.depth++ */;
	return this;
}
public struct State exit_State(struct State this){
	/* this.depth-- */;
	return this;
}
public struct boolean isShallow_State(struct State this){
	return /* this.depth == 1 */;
}
@Override
        public char* generate_Definition(struct Definition this){
	char* joinedTypeParams;/* 
            if (this.typeParams.isEmpty()) {
                joinedTypeParams = "";
            } *//* 
            else {
                joinedTypeParams = "<" + String.join(", ", this.typeParams) + ">";
            } */
	struct var beforeType = /* this.maybeBeforeType.map(inner -> inner + " ").orElse("") */;
	return /* beforeType + this.type.generate() + " " + this.name + joinedTypeParams */;
}
public struct Definition rename_Definition(struct Definition this, char* name){
	return /* new Definition(this.maybeBeforeType, this.type, name, this.typeParams) */;
}
public struct Definition mapTypeParams_Definition(struct Definition this, Function<List<char*>, List<char*>> mapper){
	return /* new Definition(this.maybeBeforeType, this.type, this.name, mapper.apply(this.typeParams)) */;
}
@Override
        public char* generate_Struct(struct Struct this){
	struct var typeParamString = /* this.typeParams.isEmpty() ? "" : "<" + String.join(", ", this.typeParams) + ">" */;
	return /* "struct " + this.name + typeParamString */;
}
@Override
        public char* generate_Ref(struct Ref this){
	return /* this.type.generate() + "*" */;
}
@Override
        public char* generate_Content(struct Content this){
	return /* generatePlaceholder(this.input) */;
}
@Override
        public char* generate_Generic(struct Generic this){
	struct var joinedArgs = /* this.args().stream()
                    .map(Type::generate)
                    .collect(Collectors.joining(", ")) */;
	return /* this.base() + "<" + joinedArgs + ">" */;
}
private struct Definition toDefinition_ConstructorDefinition(struct ConstructorDefinition this){
	return /* new DefinitionBuilder().withType(new Struct(this.name())).withName("new_" + this.name()).complete() */;
}
@Override
        public char* generate_ConstructorDefinition(struct ConstructorDefinition this){
	return this.name;
}
@Override
        public char* generate_Statement(struct Statement this){
	return /* "\n\t" + this.content.generate() + " */;
	/* " */;
}
@Override
        public char* generate_Whitespace(struct Whitespace this){
	return /* "" */;
}
@Override
        public char* generate_Assignment(struct Assignment this){
	return /* this.assignable.generate() + " = " + this.value.generate() */;
}
@Override
        public char* generate_DataAccess(struct DataAccess this){
	return /* this.parent.generate() + "." + this */.property;
}
@Override
        public char* generate_Symbol(struct Symbol this){
	return this.name;
}
@Override
        public char* generate_Return(struct Return this){
	return /* "return " + this.value.generate() */;
}
public struct DefinitionBuilder withBeforeType_DefinitionBuilder(struct DefinitionBuilder this, char* beforeType){
	this.maybeBeforeType = /* new Some<>(beforeType) */;
	return this;
}
public struct DefinitionBuilder withType_DefinitionBuilder(struct DefinitionBuilder this, struct Type type){
	this.type = type;
	return this;
}
public struct DefinitionBuilder withName_DefinitionBuilder(struct DefinitionBuilder this, char* name){
	this.name = name;
	return this;
}
public struct Definition complete_DefinitionBuilder(struct DefinitionBuilder this){
	return /* new Definition(this.maybeBeforeType, this.type, this.name, this.typeParams) */;
}
public struct DefinitionBuilder withMaybeBeforeType_DefinitionBuilder(struct DefinitionBuilder this, Option<char*> maybeBeforeType){
	this.maybeBeforeType = maybeBeforeType;
	return this;
}
public struct DefinitionBuilder withTypeParams_DefinitionBuilder(struct DefinitionBuilder this, List<char*> typeParams){
	this.typeParams = typeParams;
	return this;
}
@Override
        public char* generate_TypeParam(struct TypeParam this){
	return this.input;
}
public static struct void main_Main(struct Main this){
	structs = /* new ArrayList<>() */;
	functions = /* new ArrayList<>() */;
	stack = /* new ArrayList<>() */;/* 

        try {
            var source = Paths.get(".", "src", "java", "magma", "Main.java");
            var input = Files.readString(source);

            var target = source.resolveSibling("Main.c");
            Files.writeString(target, compileRoot(input));
        } *//*  catch (IOException e) {
            e.printStackTrace();
        } */
}
private static char* compileRoot_Main(struct Main this, char* input){
	struct var output = /* compileStatements(input, Main::compileRootSegment) */;
	struct var joinedStructs = /* String.join("", structs) */;
	struct var joinedFunctions = /* String.join("", functions) */;
	return /* output + joinedStructs + joinedFunctions */;
}
private static char* compileStatements_Main(struct Main this, char* input, Function<char*, char*> compiler){
	return /* compileAll(input, Main::foldStatementChar, compiler, Main::mergeStatements) */;
}
private static char* compileAll_Main(struct Main this, char* input, BiFunction<struct State, struct Character, struct State> folder, Function<char*, char*> compiler, BiFunction<struct StringBuilder, char*, struct StringBuilder> merger){
	return /* generateAll(merger, parseAll(input, folder, compiler)) */;
}
private static char* generateAll_Main(struct Main this, BiFunction<struct StringBuilder, char*, struct StringBuilder> merger, List<char*> compiled){
	struct var output = /* new StringBuilder() */;/* 
        for (var segment : compiled) {
            output = merger.apply(output, segment);
        } */
	return /* output.toString() */;
}
/* private static  */ List<T> parseAll_Main<T>(struct Main this, char* input, BiFunction<struct State, struct Character, struct State> folder, Function<char*, struct T> compiler){
	struct var segments = /* divide(input, folder) */;
	struct var compiled = /* new ArrayList<T>() */;/* 
        for (var segment : segments) {
            compiled.add(compiler.apply(segment));
        } */
	return compiled;
}
private static struct StringBuilder mergeStatements_Main(struct Main this, struct StringBuilder output, char* compiled){
	return /* output.append(compiled) */;
}
private static List<char*> divide_Main(struct Main this, char* input, BiFunction<struct State, struct Character, struct State> folder){
	return /* divideAll(input, folder) */;
}
private static List<char*> divideAll_Main(struct Main this, char* input, BiFunction<struct State, struct Character, struct State> folder){
	struct var current = /* State.createDefault() */;
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
	return /* current.advance() */.segments;
}
private static struct State foldStatementChar_Main(struct Main this, struct State current, struct char c){
	struct var appended = /* current.append(c) */;/* 
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
	return appended;
}
private static char* compileRootSegment_Main(struct Main this, char* input){
	struct var stripped = /* input.strip() */;/* 

        if (stripped.startsWith("package ")) {
            return "";
        } */
	return /* compileClass(stripped).orElseGet(() -> generatePlaceholder(stripped) + "\n") */;
}
private static Option<char*> compileClass_Main(struct Main this, char* stripped){
	return /* compileStructured(stripped, "class ") */;
}
private static Option<char*> compileStructured_Main(struct Main this, char* stripped, char* infix){
	struct var classIndex = /* stripped.indexOf(infix) */;/* 
        if (classIndex < 0) {
            return new None<>();
        } */
	struct var afterKeyword = /* stripped.substring(classIndex + infix.length()) */;/* 
        var contentStart = afterKeyword.indexOf("{");
        if (contentStart < 0) {
            return new None<>();
        }
        var beforeContent = afterKeyword.substring(0, contentStart).strip();
        var withEnd = afterKeyword.substring(contentStart + "{".length());

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
        if (typeParamStart >= 0) {
            String name = withoutParameters.substring(0, typeParamStart).strip();
            var withTypeParamEnd = withoutParameters.substring(typeParamStart + "<".length()).strip();
            if (withTypeParamEnd.endsWith(">")) {
                var typeParamsString = withTypeParamEnd.substring(0, withTypeParamEnd.length() - ">".length());
                var typeParams = parseValues(typeParamsString, Function.identity());
                return assembleStructured(name, withEnd, typeParams);
            }
        }

        return assembleStructured(withoutParameters, withEnd, Collections.emptyList());
    }

    private static Option<String> assembleStructured(String name, String input, List<String> typeParams) {
        if (!isSymbol(name)) {
            return new None<>();
        }

        String withEnd = input.strip();
        if (!withEnd.endsWith("}")) {
            return new None<>();
        }
        var content = withEnd.substring(0, withEnd.length() - "} */
	/* ".length()) */;
	struct var typeParamString = /* typeParams.isEmpty() ? "" : "<" + String.join(", ", typeParams) + ">" */;
	/* stack.addLast(new Tuple<>(name, typeParams)) */;/* 
        var generated = "struct " + name + typeParamString + " {" +
                compileStatements(content, Main::compileClassSegment) +
                "\n} */
	/*  */;
	/* \n" */;
	/* stack.removeLast() */;
	/* structs.add(generated) */;
	return /* new Some<>("") */;
}
private static struct boolean isSymbol_Main(struct Main this, char* input){
	/* for (var i */ = /* 0 */;
	/* i < input.length() */;/*  i++) {
            var c = input.charAt(i);
            if (Character.isLetter(c)) {
                continue;
            }
            return false;
        } */
	return true;
}
private static char* compileClassSegment_Main(struct Main this, char* input){
	return /* compileWhitespace(input)
                .or(() -> compileClass(input))
                .or(() -> compileStructured(input, "enum "))
                .or(() -> compileStructured(input, "record "))
                .or(() -> compileStructured(input, "interface "))
                .or(() -> compileMethod(input))
                .or(() -> compileClassStatement(input))
                .orElseGet(() -> "\n\t" + generatePlaceholder(input.strip())) */;
}
private static Option<char*> compileWhitespace_Main(struct Main this, char* input){
	return /* parseWhitespace(input).map(Whitespace::generate) */;
}
private static Option<struct Whitespace> parseWhitespace_Main(struct Main this, char* input){/* 
        if (input.isBlank()) {
            return new Some<>(new Whitespace());
        } *//* 
        else {
            return new None<>();
        } */
}
private static Option<char*> compileMethod_Main(struct Main this, char* input){
	struct var paramStart = /* input.indexOf("(") */;/* 
        if (paramStart < 0) {
            return new None<>();
        } */
	struct var beforeParams = /* input.substring(0, paramStart).strip() */;
	struct var withParams = /* input.substring(paramStart + "(".length()) */;
	struct var currentStruct = /* stack.getLast() */;
	struct var currentStructName = currentStruct.left;
	struct var currentStructTypeParams = currentStruct.right;
	/* typeParams.addAll(currentStructTypeParams) */;
	struct var maybeHeader = /* parseDefinition(beforeParams)
                .<Definable>map(value -> value)
                .or(() -> compileConstructorDefinition(beforeParams).map(value -> value)) */;/* 

        if (!(maybeHeader instanceof Some(var header))) {
            return new None<>();
        } */
	struct var paramEnd = /* withParams.indexOf(")") */;/* 
        if (paramEnd < 0) {
            return new None<>();
        } */
	struct var paramStrings = /* withParams.substring(0, paramEnd).strip() */;
	struct var afterParams = /* withParams.substring(paramEnd + ")".length()).strip() */;/* 
        if (afterParams.startsWith("{") && afterParams.endsWith("} *//* ")) {
            var content = afterParams.substring(1, afterParams.length() - 1);

            var inputParams = parseValues(paramStrings, Main::parseParameter);
            var oldStatements = parseStatements(content, Main::parseStatement);
            typeParams.clear();

            ArrayList<FunctionSegment> newStatements;
            if (header instanceof ConstructorDefinition(var name)) {
                var copy = new ArrayList<FunctionSegment>();
                copy.add(new Statement(new DefinitionBuilder()
                        .withType(new Struct(name, currentStructTypeParams))
                        .withName("this")
                        .complete()));

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

            Definable newDefinition;
            var outputParams = new ArrayList<Parameter>();
            if (header instanceof Definition oldDefinition) {
                outputParams.add(new DefinitionBuilder()
                        .withType(new Struct(currentStructName, currentStructTypeParams))
                        .withName("this")
                        .complete());

                newDefinition = oldDefinition.rename(oldDefinition.name + "_" + currentStructName).mapTypeParams(typeParams -> {
                    ArrayList<String> copy = new ArrayList<>(currentStructTypeParams);
                    copy.addAll(typeParams);
                    return copy;
                });
            }
            else if (header instanceof ConstructorDefinition constructorDefinition) {
                newDefinition = constructorDefinition.toDefinition();
            }
            else {
                newDefinition = header;
            }

            outputParams.addAll(inputParams
                    .stream()
                    .filter(node -> !(node instanceof Whitespace))
                    .toList());

            var outputParamStrings = outputParams.stream()
                    .map(Parameter::generate)
                    .collect(Collectors.joining(", "));

            var constructor = newDefinition.generate() + "(" + outputParamStrings + "){" + outputContent + "\n}\n";
            functions.add(constructor);
            return new Some<>("");
        } */
	/* if (afterParams.equals(" */;/* ")) {
            return new Some<>("");
        } */
	return /* new None<>() */;
}
/* private static  */ List<T> parseStatements_Main<T>(struct Main this, char* content, Function<char*, struct T> compiler){
	return /* parseAll(content, Main::foldStatementChar, compiler) */;
}
private static Option<struct ConstructorDefinition> compileConstructorDefinition_Main(struct Main this, char* input){/* 
        return findConstructorDefinitionName(input).flatMap(name -> {
            if (stack.getLast().equals(name)) {
                return new Some<>(new ConstructorDefinition(name));
            }
            return new None<>();
        } */
	/* ) */;
}
private static Option<char*> findConstructorDefinitionName_Main(struct Main this, char* input){
	struct var stripped = /* input.strip() */;
	struct var nameSeparator = /* stripped.lastIndexOf(" ") */;/* 
        if (nameSeparator >= 0) {
            var name = stripped.substring(nameSeparator + " ".length());
            return new Some<>(name);
        } *//* 
        if (isSymbol(stripped)) {
            return new Some<>(stripped);
        } */
	return /* new None<>() */;
}
private static struct FunctionSegment parseStatement_Main(struct Main this, char* input){
	return /* parseWhitespace(input)
                .<FunctionSegment>map(value -> value)
                .or(() -> parseStatementWithoutBraces(input, Main::parseStatementValue).map(value -> value))
                .orElseGet(() -> new Content(input)) */;
}
private static struct StatementValue parseStatementValue_Main(struct Main this, char* input){
	return /* compileReturn(input).<StatementValue>map(value -> value)
                .or(() -> compileAssignment(input).map(value -> value))
                .or(() -> parseDefinition(input).map(value -> value))
                .orElseGet(() -> new Content(input)) */;
}
private static Option<struct Return> compileReturn_Main(struct Main this, char* input){
	struct var stripped = /* input.strip() */;/* 
        if (stripped.startsWith("return ")) {
            return new Some<>(new Return(parseValue(stripped.substring("return ".length()))));
        } */
	return /* new None<>() */;
}
private static Option<char*> compileClassStatement_Main(struct Main this, char* input){
	return /* compileStatement(input, Main::compileClassStatementValue) */;
}
private static Option<char*> compileStatement_Main(struct Main this, char* input, Function<char*, struct StatementValue> compiler){
	return /* parseStatementWithoutBraces(input, compiler).map(Statement::generate) */;
}
private static Option<struct Statement> parseStatementWithoutBraces_Main(struct Main this, char* input, Function<char*, struct StatementValue> compiler){
	struct var stripped = /* input.strip() */;
	/* if (stripped.endsWith(" */;/* ")) {
            var withoutEnd = stripped.substring(0, stripped.length() - ";".length());
            var content = compiler.apply(withoutEnd);
            return new Some<>(new Statement(content));
        } *//* 
        else {
            return new None<>();
        } */
}
private static struct StatementValue compileClassStatementValue_Main(struct Main this, char* input){
	return /* compileAssignment(input)
                .map(value -> value)
                .or(() -> parseDefinition(input).map(value -> value))
                .orElseGet(() -> new Content(input)) */;
}
private static Option<struct StatementValue> compileAssignment_Main(struct Main this, char* input){
	struct var valueSeparator = /* input.indexOf("=") */;/* 
        if (valueSeparator >= 0) {
            var inputDefinition = input.substring(0, valueSeparator);
            var value = input.substring(valueSeparator + "=".length());

            var destination = parseDefinition(inputDefinition)
                    .<Assignable>map(result -> result)
                    .orElseGet(() -> parseValue(inputDefinition));

            return new Some<>(new Assignment(destination, parseValue(value)));
        } */
	return /* new None<>() */;
}
private static struct Value parseValue_Main(struct Main this, char* input){
	struct var stripped = /* input.strip() */;
	struct var separator = /* stripped.lastIndexOf(".") */;/* 
        if (separator >= 0) {
            var parent = stripped.substring(0, separator);
            var property = stripped.substring(separator + ".".length()).strip();
            if (isSymbol(property)) {
                var value = parseValue(parent);
                return new DataAccess(value, property);
            }
        } *//* 

        if (isSymbol(stripped)) {
            return new Symbol(stripped);
        } */
	return /* new Content(stripped) */;
}
private static struct Parameter parseParameter_Main(struct Main this, char* input){
	return /* parseWhitespace(input)
                .<Parameter>map(result -> result)
                .or(() -> parseDefinition(input).map(result -> result))
                .orElseGet(() -> new Content(input)) */;
}
private static Option<struct Definition> parseDefinition_Main(struct Main this, char* input){
	struct var stripped = /* input.strip() */;
	struct var nameSeparator = /* stripped.lastIndexOf(" ") */;/* 
        if (nameSeparator < 0) {
            return new None<>();
        } */
	struct var beforeName = /* stripped.substring(0, nameSeparator).strip() */;
	struct var name = /* stripped.substring(nameSeparator + " ".length()).strip() */;/* 
        if (!isSymbol(name)) {
            return new None<>();
        } */
	struct var withName = /* new DefinitionBuilder().withName(name) */;/* 
        return switch (findTypeSeparator(beforeName)) {
            case None<Integer> _ -> parseType(beforeName).map(type -> new DefinitionBuilder()
                    .withType(type)
                    .withName(name)
                    .complete());
            case Some<Integer>(var typeSeparator) -> {
                var beforeType = beforeName.substring(0, typeSeparator).strip();
                var inputType = beforeName.substring(typeSeparator + " ".length()).strip();
                if (beforeType.endsWith(">")) {
                    var withTypeParamStart = beforeType.substring(0, beforeType.length() - ">".length());

                    var typeParamStart = withTypeParamStart.lastIndexOf("<");
                    if (typeParamStart >= 0) {
                        var withoutTypeParams = beforeType.substring(0, typeParamStart);
                        var typeParamString = withTypeParamStart.substring(typeParamStart + "<".length());

                        var typeParams = parseValues(typeParamString, Function.identity());
                        Main.typeParams.addAll(typeParams);
                        var maybeOutputType = parseType(inputType);

                        yield maybeOutputType.map(outputType -> withName
                                .withBeforeType(generatePlaceholder(withoutTypeParams))
                                .withTypeParams(typeParams)
                                .withType(outputType)
                                .complete());
                    }
                }
                yield parseType(inputType).map(outputType -> withName
                        .withBeforeType(beforeType)
                        .withType(outputType)
                        .complete());
            }
        } */
	/*  */;
}
private static Option<struct Integer> findTypeSeparator_Main(struct Main this, char* input){
	struct var depth = /* 0 */;
	/* for (var i */ = /* input.length() - 1 */;
	/* i > */ = /* 0 */;/*  i--) {
            var c = input.charAt(i);
            if (c == ' ' && depth == 0) {
                return new Some<>(i);
            }
            if (c == '>') {
                depth++;
            }
            if (c == '<') {
                depth--;
            }
        } */
	return /* new None<>() */;
}
private static Option<struct Type> parseType_Main(struct Main this, char* input){
	struct var stripped = /* input.strip() */;/* 
        if (stripped.equals("private")) {
            return new None<>();
        } *//* 

        if (stripped.equals("int")) {
            return new Some<>(Primitive.I32);
        } *//* 

        if (stripped.equals("String")) {
            return new Some<>(new Ref(Primitive.I8));
        } *//* 

        if (typeParams.contains(input)) {
            return new Some<>(new TypeParam(input));
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
	return /* new None<>() */;
}
private static char* compileValues_Main(struct Main this, char* args, Function<char*, char*> compiler){
	return /* generateValues(parseValues(args, compiler)) */;
}
private static char* generateValues_Main(struct Main this, List<char*> values){
	return /* generateAll(Main::mergeValues, values) */;
}
/* private static  */ List<T> parseValues_Main<T>(struct Main this, char* args, Function<char*, struct T> compiler){
	return /* parseAll(args, Main::foldValueChar, compiler) */;
}
private static struct State foldValueChar_Main(struct Main this, struct State state, struct char c){/* 
        if (c == ',' && state.isLevel()) {
            return state.advance();
        } */
	struct var appended = /* state.append(c) */;/* 
        if (c == '<') {
            return appended.enter();
        } *//* 
        if (c == '>') {
            return appended.exit();
        } */
	return appended;
}
private static struct StringBuilder mergeValues_Main(struct Main this, struct StringBuilder buffer, char* element){/* 
        if (buffer.isEmpty()) {
            return buffer.append(element);
        } */
	return /* buffer.append(", ").append(element) */;
}
private static char* generatePlaceholder_Main(struct Main this, char* stripped){
	return /* "/* " + stripped + " */" */;
}
