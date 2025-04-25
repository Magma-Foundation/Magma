/* import java.io.IOException; */
/* import java.nio.file.Files; */
/* import java.nio.file.Paths; */
/* import java.util.ArrayList; */
/* import java.util.Collections; */
/* import java.util.HashMap; */
/* import java.util.List; */
/* import java.util.Map; */
/* import java.util.Optional; */
/* import java.util.function.BiFunction; */
/* import java.util.function.Function; */
/* import java.util.function.Predicate; */
/* import java.util.function.Supplier; */
/* import java.util.stream.Collectors; */
/* import java.util.stream.Stream; */
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
        I32("int"),
        Boolean("int"),
        Auto("auto") */;
	/* private final */ char* value;
};
struct Operator {
	/* Equals(" */ = /* ="),
        Add("+") */;
	/* private final */ char* representation;
};
struct Some<T> {
};
struct None<T> {
};
struct State {
	/* private final */ List<char*> segments;
	/* private */ int depth;
	/* private */ struct StringBuilder buffer;
};
struct Definition {
	/* private final */ Option<char*> maybeBeforeType;
	/* private final */ struct Type type;
	/* private final */ char* name;
	/* private final */ List<char*> typeParams;
};
struct Struct {
};
struct Ref {
};
struct Content {
};
struct Functional {
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
	/* private */ Option<char*> maybeBeforeType = new_None_I8_star();
	/* private */ struct Type type;
	/* private */ char* name;
	/* private */ List<char*> typeParams = new_ArrayList_Whitespace();
};
struct Tuple<A,  B> {
};
struct TypeParam {
};
struct Construction {
};
struct Invocation {
};
struct StructNode {
};
struct Frame {
};
struct Options {
};
struct Ternary {
};
struct Number {
};
struct Operation {
};
struct PostIncrement {
};
struct PostDecrement {
};
struct Main {
	/* private static final */ List<char*> typeParams = new_ArrayList_Whitespace();
	/* private static final */ List<struct StatementValue> statements = new_ArrayList_Whitespace();
	/* public static */ List<char*> structs;
	/* public static */ int localCounter = 0;
	/* private static */ List<char*> functions;
	/* private static */ List<struct Frame> frames;
	/* private static Option<Value> parseArgument(String input1) {
        return parseWhitespace(input1)
                .<Value>map(arg -> arg)
                .or(() -> parseValue(input1).map(arg -> arg));
    } */
};
/* default */ struct Type flatten_Type(struct Type this){
	return this;
}
struct Primitive new_Primitive(char* value){
	struct Primitive this;
	this.value = value;
	return this;
}
/* @Override
        public */ char* generate_Primitive(struct Primitive this){
	return this.value;
}
/* @Override
        public */ char* stringify_Primitive(struct Primitive this){
	return this.name(this);
}
struct Operator new_Operator(char* representation){
	struct Operator this;
	this.representation = representation;
	return this;
}
/* @Override
        public  */ Option<R> map_Some<T, R>(struct Some<T> this, R (*mapper)(T)){
	return new_Some_Whitespace(mapper(this.value));
}
/* @Override
        public */ T orElse_Some<T>(struct Some<T> this, T other){
	return this.value;
}
/* @Override
        public */ T orElseGet_Some<T>(struct Some<T> this, T (*other)()){
	return this.value;
}
/* @Override
        public */ Option<T> or_Some<T>(struct Some<T> this, Option<T> (*other)()){
	return this;
}
/* @Override
        public  */ Option<R> flatMap_Some<T, R>(struct Some<T> this, Option<R> (*mapper)(T)){
	return mapper(this.value);
}
/* @Override
        public */ Option<T> filter_Some<T>(struct Some<T> this, int (*predicate)(T)){
	return predicate(this.value) ? this : new_None_Whitespace();
}
/* @Override
        public  */ Option<Tuple<T, R>> and_Some<T, R>(struct Some<T> this, Option<R> (*supplier)()){
	return /* supplier.get().map(otherValue -> new Tuple<>(this.value, otherValue)) */;
}
/* @Override
        public  */ Option<R> map_None<T, R>(struct None<T> this, R (*mapper)(T)){
	return new_None_Whitespace();
}
/* @Override
        public */ T orElse_None<T>(struct None<T> this, T other){
	return other;
}
/* @Override
        public */ T orElseGet_None<T>(struct None<T> this, T (*other)()){
	return other();
}
/* @Override
        public */ Option<T> or_None<T>(struct None<T> this, Option<T> (*other)()){
	return other();
}
/* @Override
        public  */ Option<R> flatMap_None<T, R>(struct None<T> this, Option<R> (*mapper)(T)){
	return new_None_Whitespace();
}
/* @Override
        public */ Option<T> filter_None<T>(struct None<T> this, int (*predicate)(T)){
	return this;
}
/* @Override
        public  */ Option<Tuple<T, R>> and_None<T, R>(struct None<T> this, Option<R> (*supplier)()){
	return new_None_Whitespace();
}
struct State new_State(List<char*> segments, struct StringBuilder buffer, int depth){
	struct State this;
	this.buffer = buffer;
	this.segments = segments;
	this.depth = depth;
	return this;
}
/* private static */ struct State createDefault_State(struct State this){
	return new_State(new_ArrayList_Whitespace(), new_StringBuilder(), 0);
}
/* private */ struct State advance_State(struct State this){
	this.segments.add(this.segments, this.buffer.toString(this.buffer));
	this.buffer = new_StringBuilder();
	return this;
}
/* private */ struct State append_State(struct State this, char c){
	this.buffer.append(this.buffer, c);
	return this;
}
/* public */ int isLevel_State(struct State this){
	return this.depth == 0;
}
/* public */ struct State enter_State(struct State this){
	this.depth++;
	return this;
}
/* public */ struct State exit_State(struct State this){
	this.depth--;
	return this;
}
/* public */ int isShallow_State(struct State this){
	return this.depth == 1;
}
struct Definition new_Definition(Option<char*> maybeBeforeType, struct Type type, char* name, List<char*> typeParams){
	struct Definition this;
	this.maybeBeforeType = maybeBeforeType;
	this.type = type;
	this.name = name;
	this.typeParams = typeParams;
	return this;
}
/* private */ char* generatedWithName_Definition(struct Definition this){/* 
            if (this.type instanceof Functional functional) {
                return functional.generateWithName(this.name);
            } */
	return /* this.type.generate() + " " + this.name */;
}
/* private */ char* generateBeforeType_Definition(struct Definition this){
	return /* this.maybeBeforeType
                    .filter(inner -> !inner.isEmpty())
                    .map(inner -> generatePlaceholder(inner) + " ")
                    .orElse("") */;
}
/* private */ char* generateTypeParams_Definition(struct Definition this){/* 
            if (this.typeParams.isEmpty()) {
                return "";
            } */
	return /* "<" + String.join(", ", this.typeParams) + ">" */;
}
/* @Override
        public */ Option<struct Definition> toDefinition_Definition(struct Definition this){
	return new_Some_Whitespace(this);
}
/* public */ struct Definition rename_Definition(struct Definition this, char* name){
	return new_Definition(this.maybeBeforeType, this.type, name, this.typeParams);
}
/* public */ struct Definition mapTypeParams_Definition(struct Definition this, List<char*> (*mapper)(List<char*>)){
	return new_Definition(this.maybeBeforeType, this.type, this.name, mapper(this.typeParams));
}
/* public */ struct Definition withType_Definition(struct Definition this, struct Type type){
	return new_Definition(this.maybeBeforeType, type, this.name, this.typeParams);
}
/* @Override
        public */ char* generate_Definition(struct Definition this){
	char* joinedTypeParams = this.generateTypeParams(this);
	char* beforeType = this.generateBeforeType(this);
	char* generatedWithName = this.generatedWithName(this);
	return beforeType + generatedWithName + joinedTypeParams;
}
struct Struct new_Struct(char* name){
	struct Struct this;
	this(name, Collections.emptyList(Collections), new_HashMap_Whitespace());
	return this;
}
struct public Struct_Struct(struct Struct this, char* name, List<char*> typeParams){
	this(name, typeParams, new_HashMap_Whitespace());
}
/* @Override
        public */ char* generate_Struct(struct Struct this){
	/* /*  this.typeArgs.isEmpty() ? "" : "<" + String.join(", ", this.typeArgs) + ">" */ */ typeParamString = /*  this.typeArgs.isEmpty() ? "" : "<" + String.join(", ", this.typeArgs) + ">" */;
	return /* "struct " + this.name + typeParamString */;
}
/* @Override
        public */ char* stringify_Struct(struct Struct this){
	return this.name;
}
/* public */ Option<struct Type> find_Struct(struct Struct this, char* property){/* 
            if (this.definitions.containsKey(property)) {
                return new Some<>(this.definitions.get(property));
            } */
	return new_None_Whitespace();
}
/* @Override
        public */ char* generate_Ref(struct Ref this){
	return /* this.type.generate() + "*" */;
}
/* @Override
        public */ char* stringify_Ref(struct Ref this){
	return /* this.type.stringify() + "_star" */;
}
/* @Override
        public */ char* generate_Content(struct Content this){
	return generatePlaceholder(this.input);
}
/* @Override
        public */ char* stringify_Content(struct Content this){
	return this.input;
}
/* @Override
        public */ Option<struct Definition> toDefinition_Content(struct Content this){
	return new_None_Whitespace();
}
/* @Override
        public */ char* generate_Functional(struct Functional this){
	return /* this.generateWithName("") */;
}
/* @Override
        public */ char* stringify_Functional(struct Functional this){
	/* /*  this.paramTypes.stream()
                    .map(Type::stringify)
                    .collect(Collectors.joining("_")) */ */ joined = /*  this.paramTypes.stream()
                    .map(Type::stringify)
                    .collect(Collectors.joining("_")) */;
	return /* "fn_" + this.returnType.stringify() + "_" + joined */;
}
/* public */ char* generateWithName_Functional(struct Functional this, char* name){
	/* /*  this.paramTypes.stream()
                    .map(Type::generate)
                    .collect(Collectors.joining(", ")) */ */ joined = /*  this.paramTypes.stream()
                    .map(Type::generate)
                    .collect(Collectors.joining(", ")) */;
	return /* this.returnType.generate() + " (*" +
                    name +
                    ")(" + joined + ")" */;
}
/* @Override
        public */ char* generate_Generic(struct Generic this){
	/* /* this */ */ local0 = this.args(this);
	/* /*  this.args().stream()
                    .map(Type::generate)
                    .collect(Collectors.joining(", ")) */ */ joinedArgs = /*  this.args().stream()
                    .map(Type::generate)
                    .collect(Collectors.joining(", ")) */;
	return /* this.base() + "<" + joinedArgs + ">" */;
}
/* @Override
        public */ struct Type flatten_Generic(struct Generic this){/* 
            if (this.base.equals("Function")) {
                var param = this.args.getFirst();
                var returns = this.args.get(1);
                return new Functional(Collections.singletonList(param), returns);
            } *//* 

            if (this.base.equals("Supplier")) {
                var returns = this.args.getFirst();
                return new Functional(Collections.emptyList(), returns);
            } *//* 

            if (this.base.equals("Predicate")) {
                var param = this.args.getFirst();
                return new Functional(Collections.singletonList(param), Primitive.Boolean);
            } */
	return this;
}
/* @Override
        public */ char* stringify_Generic(struct Generic this){
	/* /*  this.args.stream()
                    .map(arg -> arg.stringify())
                    .collect(Collectors.joining("_")) */ */ joined = /*  this.args.stream()
                    .map(arg -> arg.stringify())
                    .collect(Collectors.joining("_")) */;
	return /* this.base + "_" + joined */;
}
/* private */ struct Definition toDefinition_ConstructorDefinition(struct ConstructorDefinition this){
	/* /* new_DefinitionBuilder */ */ local0 = new_DefinitionBuilder();
	return /* new DefinitionBuilder().withType(new Struct(this.name())).withName("new_" + this.name()).complete() */;
}
/* @Override
        public */ char* generate_ConstructorDefinition(struct ConstructorDefinition this){
	return this.name;
}
/* @Override
        public */ char* generate_Statement(struct Statement this){
	return /* "\n\t" + this.content.generate() + " */;
	/* " */;
}
/* @Override
        public */ char* generate_Whitespace(struct Whitespace this){
	return /* "" */;
}
/* @Override
        public */ char* stringify_Whitespace(struct Whitespace this){
	return /* "Whitespace" */;
}
/* @Override
        public */ Option<struct Definition> toDefinition_Whitespace(struct Whitespace this){
	return new_None_Whitespace();
}
/* @Override
        public */ char* generate_Assignment(struct Assignment this){
	return /* this.assignable.generate() + " = " + this.value.generate() */;
}
/* @Override
        public */ char* generate_DataAccess(struct DataAccess this){
	return /* this.parent.generate() + "." + this.property */;
}
/* @Override
        public */ char* generate_Symbol(struct Symbol this){
	return this.name;
}
/* @Override
        public */ char* generate_Return(struct Return this){
	return /* "return " + this.value.generate() */;
}
/* public */ struct DefinitionBuilder withBeforeType_DefinitionBuilder(struct DefinitionBuilder this, char* beforeType){
	this.maybeBeforeType = new_Some_Whitespace(beforeType);
	return this;
}
/* public */ struct DefinitionBuilder withType_DefinitionBuilder(struct DefinitionBuilder this, struct Type type){
	this.type = type;
	return this;
}
/* public */ struct DefinitionBuilder withName_DefinitionBuilder(struct DefinitionBuilder this, char* name){
	this.name = name;
	return this;
}
/* public */ struct Definition complete_DefinitionBuilder(struct DefinitionBuilder this){
	return new_Definition(this.maybeBeforeType, this.type, this.name, this.typeParams);
}
/* public */ struct DefinitionBuilder withTypeParams_DefinitionBuilder(struct DefinitionBuilder this, List<char*> typeParams){
	this.typeParams = typeParams;
	return this;
}
/* @Override
        public */ char* generate_TypeParam(struct TypeParam this){
	return this.input;
}
/* @Override
        public */ char* stringify_TypeParam(struct TypeParam this){
	return this.input;
}
/* @Override
        public */ char* generate_Construction(struct Construction this){
	/* /*  this.values.stream()
                    .map(Value::generate)
                    .collect(Collectors.joining(", ")) */ */ joined = /*  this.values.stream()
                    .map(Value::generate)
                    .collect(Collectors.joining(", ")) */;
	return /* "new " + this.type.generate() + "(" + joined + ")" */;
}
/* private */ struct Invocation toInvocation_Construction(struct Construction this){
	/* /* this */ */ typeAsString = this.type.stringify(this.type);
	return /* new Invocation(new Symbol("new_" + typeAsString), this.values) */;
}
/* @Override
        public */ char* generate_Invocation(struct Invocation this){
	/* /*  this.arguments.stream()
                    .map(Value::generate)
                    .collect(Collectors.joining(", ")) */ */ joined = /*  this.arguments.stream()
                    .map(Value::generate)
                    .collect(Collectors.joining(", ")) */;
	return /* this.caller.generate() + "(" + joined + ")" */;
}
/* private */ struct Invocation toInvocation_Invocation(struct Invocation this){
	return this;
}
struct public Frame_Frame(struct Frame this, struct StructNode node){
	this(node, new_HashMap_Whitespace());
}
/* public static  */ Stream<T> toStream_Options<T>(struct Options this, Option<T> option){
	return /* option.map(Stream::of).orElseGet(Stream::empty) */;
}
/* public static  */ Option<T> fromNative_Options<T>(struct Options this, Optional<T> optional){
	return /* optional.<Option<T>>map(Some::new).orElseGet(None::new) */;
}
/* @Override
        public */ char* generate_Ternary(struct Ternary this){
	return /* this.condition.generate() + " ? " + this.whenTrue.generate() + " : " + this.whenFalse.generate() */;
}
/* @Override
        public */ char* generate_Number(struct Number this){
	return this.value;
}
/* @Override
        public */ char* generate_Operation(struct Operation this){
	return /* this.left.generate() + " " + this.operator.representation + " " + this.right.generate() */;
}
/* @Override
        public */ char* generate_PostIncrement(struct PostIncrement this){
	return /* this.value.generate() + "++" */;
}
/* @Override
        public */ char* generate_PostDecrement(struct PostDecrement this){
	return /* this.value.generate() + "--" */;
}
/* public static */ struct void main_Main(struct Main this){
	structs = new_ArrayList_Whitespace();
	functions = new_ArrayList_Whitespace();
	frames = new_ArrayList_Whitespace();/* 

        try {
            var source = Paths.get(".", "src", "java", "magma", "Main.java");
            var input = Files.readString(source);

            var target = source.resolveSibling("Main.c");
            Files.writeString(target, compileRoot(input));
        } *//*  catch (IOException e) {
            e.printStackTrace();
        } */
}
/* private static */ char* compileRoot_Main(struct Main this, char* input){
	/* /*  compileStatements(input, input1 -> new Some<>(compileRootSegment(input1))) */ */ output = /*  compileStatements(input, input1 -> new Some<>(compileRootSegment(input1))) */;
	/* /*  String.join("", structs) */ */ joinedStructs = /*  String.join("", structs) */;
	/* /*  String.join("", functions) */ */ joinedFunctions = /*  String.join("", functions) */;
	return output + joinedStructs + joinedFunctions;
}
/* private static */ char* compileStatements_Main(struct Main this, char* input, Option<char*> (*compiler)(char*)){
	return /* compileAll(input, Main::foldStatementChar, compiler, Main::mergeStatements) */;
}
/* private static */ char* compileAll_Main(struct Main this, char* input, BiFunction<struct State, struct Character, struct State> folder, Option<char*> (*compiler)(char*), BiFunction<struct StringBuilder, char*, struct StringBuilder> merger){
	return /* parseAll(input, folder, compiler)
                .map(listOption -> generateAll(merger, listOption))
                .orElse("") */;
}
/* private static */ char* generateAll_Main(struct Main this, BiFunction<struct StringBuilder, char*, struct StringBuilder> merger, List<char*> compiled){
	/* /* new_StringBuilder */ */ output = new_StringBuilder();/* 
        for (var segment : compiled) {
            output = merger.apply(output, segment);
        } */
	return output.toString(output);
}
/* private static  */ Option<List<T>> parseAll_Main<T>(struct Main this, char* input, BiFunction<struct State, struct Character, struct State> folder, Option<T> (*compiler)(char*)){
	/* /* divide */ */ segments = divide(input, folder);
	Option<List<T>> compiled = new_Some_Whitespace(new_ArrayList_T());/* 
        for (var segment : segments) {
            compiled = compiled.and(() -> compiler.apply(segment)).map(tuple -> {
                tuple.left.add(tuple.right);
                return tuple.left;
            });
        } */
	return compiled;
}
/* private static */ struct StringBuilder mergeStatements_Main(struct Main this, struct StringBuilder output, char* compiled){
	return output.append(output, compiled);
}
/* private static */ List<char*> divide_Main(struct Main this, char* input, BiFunction<struct State, struct Character, struct State> folder){
	return divideAll(input, folder);
}
/* private static */ List<char*> divideAll_Main(struct Main this, char* input, BiFunction<struct State, struct Character, struct State> folder){
	/* /* State */ */ current = State.createDefault(State);
	/* for (var i  */ = 0;
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
	return current.advance(current).segments;
}
/* private static */ struct State foldStatementChar_Main(struct Main this, struct State current, char c){
	/* /* State */ */ appended = current.append(current, c);/* 
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
/* private static */ char* compileRootSegment_Main(struct Main this, char* input){
	/* /* I8_star */ */ stripped = input.strip(input);/* 

        if (stripped.startsWith("package ")) {
            return "";
        } */
	return /* compileClass(stripped).orElseGet(() -> generatePlaceholder(stripped) + "\n") */;
}
/* private static */ Option<char*> compileClass_Main(struct Main this, char* stripped){
	return /* compileStructured(stripped, "class ") */;
}
/* private static */ Option<char*> compileStructured_Main(struct Main this, char* stripped, char* infix){
	/* classIndex + infix */ local0 = classIndex + infix;
	/* /* I8_star */ */ classIndex = stripped.indexOf(stripped, infix);/* 
        if (classIndex < 0) {
            return new None<>();
        } */
	/* /* I8_star */ */ afterKeyword = stripped.substring(stripped, local0.length(local0));/* 
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
                var maybeTypeParams = parseValues(typeParamsString, Some::new);
                if (maybeTypeParams instanceof Some(var actualTypeParams)) {
                    return assembleStructured(name, withEnd, actualTypeParams);
                }
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
	/* /*  typeParams.isEmpty() ? "" : "<" + String.join(", ", typeParams) + ">" */ */ typeParamString = /*  typeParams.isEmpty() ? "" : "<" + String.join(", ", typeParams) + ">" */;
	/* /* new_StructNode */ */ structNode = new_StructNode(name, typeParams);
	frames.addLast(frames, new_Frame(structNode));/* 

        var generated = "struct " + name + typeParamString + " {" +
                compileStatements(content, input1 -> new Some<>(compileClassSegment(input1))) +
                "\n} */
	/*  */;
	/* \n" */;
	frames.removeLast(frames);
	structs.add(structs, generated);
	return /* new Some<>("") */;
}
/* private static */ int isSymbol_Main(struct Main this, char* input){/* 
        if (input.isEmpty()) {
            return false;
        } */
	/* for (var i  */ = 0;
	/* i < input.length() */;/*  i++) {
            var c = input.charAt(i);
            if (Character.isLetter(c)) {
                continue;
            }
            return false;
        } */
	return true;
}
/* private static */ char* compileClassSegment_Main(struct Main this, char* input){
	return /* compileWhitespace(input)
                .or(() -> compileClass(input))
                .or(() -> compileStructured(input, "enum "))
                .or(() -> compileStructured(input, "record "))
                .or(() -> compileStructured(input, "interface "))
                .or(() -> compileMethod(input))
                .or(() -> compileClassStatement(input))
                .orElseGet(() -> "\n\t" + generatePlaceholder(input.strip())) */;
}
/* private static */ Option<char*> compileWhitespace_Main(struct Main this, char* input){
	return /* parseWhitespace(input).map(Whitespace::generate) */;
}
/* private static */ Option<struct Whitespace> parseWhitespace_Main(struct Main this, char* input){/* 
        if (input.isBlank()) {
            return new Some<>(new Whitespace());
        } *//* 
        else {
            return new None<>();
        } */
}
/* private static */ Option<char*> compileMethod_Main(struct Main this, char* input){
	/* /* I8_star */ */ local0 = input.substring(input, 0, paramStart);
	/* /* withParams */ */ local1 = withParams.substring(withParams, 0, paramEnd);
	/* /*  input.indexOf("(") */ */ paramStart = /*  input.indexOf("(") */;/* 
        if (paramStart < 0) {
            return new None<>();
        } */
	/* /* local0 */ */ beforeParams = local0.strip(local0);
	/* /*  input.substring(paramStart + "(".length()) */ */ withParams = /*  input.substring(paramStart + "(".length()) */;
	/* /* frames */ */ currentStruct = frames.getLast(frames).node;
	/* currentStruct */ currentStructName = currentStruct.name;
	/* currentStruct */ currentStructTypeParams = currentStruct.typeParams;
	typeParams.addAll(typeParams, currentStructTypeParams);
	/* /*  parseDefinition(beforeParams)
                .<Definable>map(value -> value)
                .or(() -> compileConstructorDefinition(beforeParams).map(value -> value)) */ */ maybeHeader = /*  parseDefinition(beforeParams)
                .<Definable>map(value -> value)
                .or(() -> compileConstructorDefinition(beforeParams).map(value -> value)) */;/* 

        if (!(maybeHeader instanceof Some(var header))) {
            return new None<>();
        } */
	/* /*  withParams.indexOf(")") */ */ paramEnd = /*  withParams.indexOf(")") */;/* 
        if (paramEnd < 0) {
            return new None<>();
        } */
	/* /* local1 */ */ paramStrings = local1.strip(local1);
	/* /*  withParams.substring(paramEnd + ")".length()).strip() */ */ afterParams = /*  withParams.substring(paramEnd + ")".length()).strip() */;/* 
        if (afterParams.startsWith("{") && afterParams.endsWith("} *//* ")) {
            var content = afterParams.substring(1, afterParams.length() - 1);

            var maybeInputParams = parseValues(paramStrings, Main::parseParameter);
            if (maybeInputParams instanceof Some(var inputParams)) {
                var paramDefinitions = inputParams.stream()
                        .map(Parameter::toDefinition)
                        .flatMap(Options::toStream)
                        .toList();

                var params = inputParams
                        .stream()
                        .filter(node -> !(node instanceof Whitespace))
                        .toList();

                defineAll(paramDefinitions);

                var maybeOldStatements = parseStatements(content, (String input1) -> new Some<>(parseStatement(input1)));
                typeParams.clear();

                if (maybeOldStatements instanceof Some(var oldStatements)) {
                    var list = statements.stream()
                            .map(Statement::new)
                            .toList();
                    statements.clear();
                    localCounter = 0;

                    ArrayList<FunctionSegment> newStatements;
                    if (header instanceof ConstructorDefinition(var name)) {
                        var copy = new ArrayList<FunctionSegment>(list);

                        copy.add(new Statement(new DefinitionBuilder()
                                .withType(new Struct(name, currentStructTypeParams))
                                .withName("this")
                                .complete()));

                        copy.addAll(oldStatements);
                        copy.add(new Statement(new Return(new Symbol("this"))));
                        newStatements = copy;
                    }
                    else {
                        newStatements = new ArrayList<>(list);
                        newStatements.addAll(oldStatements);
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

                        var paramTypes = paramDefinitions.stream()
                                .map(definition -> definition.type)
                                .toList();

                        var complete = new DefinitionBuilder()
                                .withName(oldDefinition.name)
                                .withType(new Functional(paramTypes, oldDefinition.type))
                                .complete();
                        define(complete);

                        newDefinition = oldDefinition.rename(oldDefinition.name + "_" + currentStructName).mapTypeParams(typeParams1 -> {
                            ArrayList<String> copy = new ArrayList<>(currentStructTypeParams);
                            copy.addAll(typeParams1);
                            return copy;
                        });
                    }
                    else if (header instanceof ConstructorDefinition constructorDefinition) {
                        var definition = constructorDefinition.toDefinition();
                        newDefinition = definition;

                        define(definition);
                    }
                    else {
                        newDefinition = header;
                    }

                    outputParams.addAll(params);

                    var outputParamStrings = outputParams.stream()
                            .map(Parameter::generate)
                            .collect(Collectors.joining(", "));

                    var constructor = newDefinition.generate() + "(" + outputParamStrings + "){" + outputContent + "\n}\n";
                    functions.add(constructor);
                    return new Some<>("");
                }
            }
        } */
	/* if (afterParams.equals(" */;/* ")) {
            return new Some<>("");
        } */
	return new_None_Whitespace();
}
/* private static */ struct void defineAll_Main(struct Main this, List<struct Definition> definitions){/* 
        for (var definition : definitions) {
            define(definition);
        } */
}
/* private static */ struct void define_Main(struct Main this, struct Definition definition){
	frames.getLast(frames).definitions.put(frames.getLast(frames).definitions, definition.name, definition.type);
}
/* private static  */ Option<List<T>> parseStatements_Main<T>(struct Main this, char* content, Option<T> (*compiler)(char*)){
	return /* parseAll(content, Main::foldStatementChar, compiler) */;
}
/* private static */ Option<struct ConstructorDefinition> compileConstructorDefinition_Main(struct Main this, char* input){/* 
        return findConstructorDefinitionName(input).flatMap(name -> {
            if (frames.getLast().node.name.equals(name)) {
                return new Some<>(new ConstructorDefinition(name));
            }
            return new None<>();
        } */
	/* ) */;
}
/* private static */ Option<char*> findConstructorDefinitionName_Main(struct Main this, char* input){
	/* /* I8_star */ */ stripped = input.strip(input);
	/* /*  stripped.lastIndexOf(" ") */ */ nameSeparator = /*  stripped.lastIndexOf(" ") */;/* 
        if (nameSeparator >= 0) {
            var name = stripped.substring(nameSeparator + " ".length());
            return new Some<>(name);
        } *//* 
        if (isSymbol(stripped)) {
            return new Some<>(stripped);
        } */
	return new_None_Whitespace();
}
/* private static */ struct FunctionSegment parseStatement_Main(struct Main this, char* input){
	return /* parseWhitespace(input)
                .<FunctionSegment>map(value -> value)
                .or(() -> parseStatementWithoutBraces(input, Main::parseStatementValue).map(value -> value))
                .orElseGet(() -> new Content(input)) */;
}
/* private static */ struct StatementValue parseStatementValue_Main(struct Main this, char* input){
	return /* parseReturn(input).<StatementValue>map(value -> value)
                .or(() -> parsePostIncrement(input).map(value -> value))
                .or(() -> parsePostDecrement(input).map(value -> value))
                .or(() -> parseInvocation(input).map(value -> value))
                .or(() -> parseAssignment(input).map(value -> value))
                .or(() -> parseDefinition(input).map(value -> value))
                .orElseGet(() -> new Content(input)) */;
}
/* private static */ Option<struct PostDecrement> parsePostDecrement_Main(struct Main this, char* input){
	/* /* I8_star */ */ stripped = input.strip(input);/* 
        if (stripped.endsWith("--")) {
            return parseValue(stripped.substring(0, stripped.length() - "--".length())).map(PostDecrement::new);
        } *//* 
        else {
            return new None<>();
        } */
}
/* private static */ Option<struct PostIncrement> parsePostIncrement_Main(struct Main this, char* input){
	/* /* I8_star */ */ stripped = input.strip(input);/* 
        if (stripped.endsWith("++")) {
            return parseValue(stripped.substring(0, stripped.length() - "++".length())).map(PostIncrement::new);
        } *//* 
        else {
            return new None<>();
        } */
}
/* private static */ Option<struct Return> parseReturn_Main(struct Main this, char* input){
	/* /* I8_star */ */ stripped = input.strip(input);/* 
        if (stripped.startsWith("return ")) {
            return new Some<>(new Return(parseValueOrPlaceholder(stripped.substring("return ".length()))));
        } */
	return new_None_Whitespace();
}
/* private static */ Option<char*> compileClassStatement_Main(struct Main this, char* input){
	return /* compileStatement(input, Main::compileClassStatementValue) */;
}
/* private static */ Option<char*> compileStatement_Main(struct Main this, char* input, struct StatementValue (*compiler)(char*)){
	return /* parseStatementWithoutBraces(input, compiler).map(Statement::generate) */;
}
/* private static */ Option<struct Statement> parseStatementWithoutBraces_Main(struct Main this, char* input, struct StatementValue (*compiler)(char*)){
	/* /* I8_star */ */ stripped = input.strip(input);
	/* if (stripped.endsWith(" */;/* ")) {
            var withoutEnd = stripped.substring(0, stripped.length() - ";".length());
            var content = compiler.apply(withoutEnd);
            return new Some<>(new Statement(content));
        } *//* 
        else {
            return new None<>();
        } */
}
/* private static */ struct StatementValue compileClassStatementValue_Main(struct Main this, char* input){
	return /* parseAssignment(input).<StatementValue>map(value -> value)
                .or(() -> parseDefinition(input).map(value -> value))
                .orElseGet(() -> new Content(input)) */;
}
/* private static */ Option<struct Assignment> parseAssignment_Main(struct Main this, char* input){
	/* /*  input.indexOf("=") */ */ valueSeparator = /*  input.indexOf("=") */;/* 
        if (valueSeparator >= 0) {
            var inputDefinition = input.substring(0, valueSeparator);
            var value = input.substring(valueSeparator + "=".length());
            var parsedValue = parseValueOrPlaceholder(value);

            var destination = parseDefinition(inputDefinition)
                    .<Assignable>map(result -> {
                        if (result.type.equals(Primitive.Auto)) {
                            return result.withType(resolveType(parsedValue));
                        }

                        return result;
                    })
                    .orElseGet(() -> parseValueOrPlaceholder(inputDefinition));

            return new Some<>(new Assignment(destination, parsedValue));
        } */
	return new_None_Whitespace();
}
/* private static */ struct Value parseValueOrPlaceholder_Main(struct Main this, char* input){
	return /* parseValue(input).orElseGet(() -> new Content(input)) */;
}
/* private static */ Option<struct Value> parseValue_Main(struct Main this, char* input){
	/* /* I8_star */ */ stripped = input.strip(input);/* 

        if (isSymbol(stripped)) {
            return new Some<>(new Symbol(stripped));
        } *//* 

        if (isNumber(stripped)) {
            return new Some<>(new Number(stripped));
        } *//* 

        if (stripped.startsWith("new ")) {
            var substring = stripped.substring("new ".length());
            var maybeInvokable = parseInvokable(substring, Main::parseType, Construction::new);
            if (maybeInvokable instanceof Some(var invokable)) {
                return new Some<>(invokable.toInvocation());
            }
        } */
	/* /* parseInvocation */ */ maybeInvocation = parseInvocation(stripped);/* 
        if (maybeInvocation instanceof Some(var invocation)) {
            return new Some<>(invocation);
        } */
	/* /*  stripped.lastIndexOf(".") */ */ separator = /*  stripped.lastIndexOf(".") */;/* 
        if (separator >= 0) {
            var parentString = stripped.substring(0, separator);
            var property = stripped.substring(separator + ".".length()).strip();
            if (isSymbol(property) && parseValue(parentString) instanceof Some(var parent)) {
                var type = resolveType(parent);
                if (type instanceof Functional) {
                    return new Some<>(parent);
                }

                return new Some<>(new DataAccess(parent, property));
            }
        } */
	/* /*  stripped.indexOf("?") */ */ conditionSeparator = /*  stripped.indexOf("?") */;/* 
        if (conditionSeparator >= 0) {
            var conditionString = stripped.substring(0, conditionSeparator);
            var afterCondition = stripped.substring(conditionSeparator + "?".length());
            var actionSeparator = afterCondition.indexOf(":");
            if (actionSeparator >= 0) {
                var whenTrueString = afterCondition.substring(0, actionSeparator);
                var whenFalseString = afterCondition.substring(actionSeparator + ":".length());

                var maybeCondition = parseValue(conditionString);
                var maybeWhenTrue = parseValue(whenTrueString);
                var maybeWhenFalse = parseValue(whenFalseString);

                if (maybeCondition instanceof Some(var condition)
                        && maybeWhenTrue instanceof Some(var whenTrue)
                        && maybeWhenFalse instanceof Some(var whenFalse)) {
                    return new Some<>(new Ternary(condition, whenTrue, whenFalse));
                }
            }
        } *//* 

        for (var operator : Operator.values()) {
            if(parseOperator(input, operator) instanceof Some<Value> some) {
                return some;
            }
        } */
	return new_None_Whitespace();
}
/* private static */ Option<struct Value> parseOperator_Main(struct Main this, char* input, struct Operator operator){
	/* /* I8_star */ */ operatorIndex = input.indexOf(input, operator.representation);/* 
        if (operatorIndex >= 0) {
            var leftString = input.substring(0, operatorIndex);
            var rightString = input.substring(operatorIndex + operator.representation.length());

            if (parseValue(leftString) instanceof Some(var left)
                    && parseValue(rightString) instanceof Some(var right)) {
                return new Some<>(new Operation(left, operator, right));
            }
        } */
	return new_None_Whitespace();
}
/* private static */ Option<struct Invocation> parseInvocation_Main(struct Main this, char* stripped){/* 
        return parseInvokable(stripped, Main::parseValue, Invocation::new).map(invocation -> {
            var caller = invocation.caller;
            if (caller instanceof DataAccess(var parent, var property)) {
                var resolved = resolveType(parent);

                Value newParent;
                if (parent instanceof Symbol || parent instanceof DataAccess) {
                    newParent = parent;
                }
                else {
                    var name = "local" + localCounter;
                    newParent = new Symbol(name);
                    localCounter++;

                    statements.add(new Assignment(new DefinitionBuilder()
                            .withType(resolved)
                            .withName(name)
                            .complete(), parent));
                }

                var arguments = new ArrayList<Value>();
                arguments.add(newParent);
                arguments.addAll(invocation.arguments
                        .stream()
                        .filter(argument -> !(argument instanceof Whitespace))
                        .toList());

                return new Invocation(new DataAccess(newParent, property), arguments);
            }

            return invocation;
        } */
	/* ) */;
}
/* private static */ int isNumber_Main(struct Main this, char* input){
	/* for (var i  */ = 0;
	/* i < input.length() */;/*  i++) {
            var c = input.charAt(i);
            if (Character.isDigit(c)) {
                continue;
            }
            return false;
        } */
	return true;
}
/* private static */ struct Type resolveType_Main(struct Main this, struct Value value){/* 
        if (value instanceof DataAccess(var parent, var property)) {
            var type = resolveType(parent);
            if (type instanceof Struct struct) {
                var maybeFound = struct.find(property);
                if (maybeFound instanceof Some(var found)) {
                    return found;
                }
            }

            return new Content(type.stringify());
        } *//* 

        if (value instanceof Invocation(var base, var _)) {
            var resolved = resolveType(base);
            if (resolved instanceof Functional functional) {
                return functional.returnType;
            }
            else {
                return new Content(resolved.generate());
            }
        } *//* 

        if (value instanceof Symbol(var name)) {
            if (name.equals("this")) {
                var definitions = frames.getLast().definitions;
                return new Struct(name, Collections.emptyList(), definitions);
            }

            var maybeType = Options.fromNative(frames.stream()
                    .map(frame -> findNameInFrame(name, frame))
                    .flatMap(Options::toStream)
                    .findFirst());

            if (maybeType instanceof Some(var type)) {
                return type;
            }
        } */
	return new_Content(value.generate(value));
}
/* private static */ Option<struct Type> findNameInFrame_Main(struct Main this, char* name, struct Frame frame){
	/* Frame */ definitions = frame.definitions;/* 
        if (definitions.containsKey(name)) {
            return new Some<>(definitions.get(name));
        } *//* 
        else {
            return new None<>();
        } */
}
/* private static  */ Option<struct R> parseInvokable_Main<T,  R>(struct Main this, char* input, Option<T> (*beforeArgsCaller)(char*), BiFunction<T, List<struct Value>, struct R> builder){
	/* /* I8_star */ */ withoutPrefix = input.strip(input);/* 
        if (!withoutPrefix.endsWith(")")) {
            return new None<>();
        } */
	/* /*  withoutPrefix.substring(0, withoutPrefix.length() - ")".length()) */ */ withoutEnd = /*  withoutPrefix.substring(0, withoutPrefix.length() - ")".length()) */;
	/* /*  divideAll(withoutEnd, Main::foldInvocationStart) */ */ slices = /*  divideAll(withoutEnd, Main::foldInvocationStart) */;
	/* /*  slices.subList(0, slices.size() - 1) */ */ beforeLast = /*  slices.subList(0, slices.size() - 1) */;
	/* /*  String.join("", beforeLast).strip() */ */ joined = /*  String.join("", beforeLast).strip() */;/* 
        if (!joined.endsWith("(")) {
            return new None<>();
        } */
	/* /*  joined.substring(0, joined.length() - 1) */ */ beforeArgsStart = /*  joined.substring(0, joined.length() - 1) */;
	/* /* slices */ */ args = slices.getLast(slices);/* 

        if (!(beforeArgsCaller.apply(beforeArgsStart) instanceof Some(var outputBeforeArgs))) {
            return new None<>();
        } */
	return /* parseValues(args, Main::parseArgument).map(values -> builder.apply(outputBeforeArgs, values)) */;
}
/* private static */ struct State foldInvocationStart_Main(struct Main this, struct State state, char c){
	/* /* State */ */ appended = state.append(state, c);/* 
        if (c == '(') {
            State advanced = appended.isLevel() ? appended.advance() : appended;
            return advanced.enter();
        } *//* 
        if (c == ')') {
            return appended.exit();
        } */
	return appended;
}
/* private static */ Option<struct Parameter> parseParameter_Main(struct Main this, char* input){
	return /* parseWhitespace(input)
                .<Parameter>map(result -> result)
                .or(() -> parseDefinition(input).map(result -> result)) */;
}
/* private static */ Option<struct Definition> parseDefinition_Main(struct Main this, char* input){
	/* /* I8_star */ */ local0 = stripped.substring(stripped, 0, nameSeparator);
	/* /* new_DefinitionBuilder */ */ local1 = new_DefinitionBuilder();
	/* /* I8_star */ */ stripped = input.strip(input);
	/* /*  stripped.lastIndexOf(" ") */ */ nameSeparator = /*  stripped.lastIndexOf(" ") */;/* 
        if (nameSeparator < 0) {
            return new None<>();
        } */
	/* /* local0 */ */ beforeName = local0.strip(local0);
	/* /*  stripped.substring(nameSeparator + " ".length()).strip() */ */ name = /*  stripped.substring(nameSeparator + " ".length()).strip() */;/* 
        if (!isSymbol(name)) {
            return new None<>();
        } */
	/* /* local1 */ */ withName = local1.withName(local1, name);/* 
        return switch (findTypeSeparator(beforeName)) {
            case None<Integer> _ -> parseAndFlattenType(beforeName).map(type -> new DefinitionBuilder()
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

                        var maybeTypeParams = parseValues(typeParamString, Some::new);
                        if (maybeTypeParams instanceof Some(var actualTypeParams)) {
                            Main.typeParams.addAll(actualTypeParams);
                            var maybeOutputType = parseAndFlattenType(inputType);

                            yield maybeOutputType.map(outputType -> withName
                                    .withBeforeType(withoutTypeParams)
                                    .withTypeParams(actualTypeParams)
                                    .withType(outputType)
                                    .complete());
                        }
                    }
                }
                yield parseAndFlattenType(inputType).map(outputType -> withName
                        .withBeforeType(beforeType)
                        .withType(outputType)
                        .complete());
            }
        } */
	/*  */;
}
/* private static */ Option<struct Integer> findTypeSeparator_Main(struct Main this, char* input){
	/* 0 */ depth = 0;
	/* for (var i  */ = /*  input.length() - 1 */;
	/* i > */ = 0;/*  i--) {
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
	return new_None_Whitespace();
}
/* private static */ Option<struct Type> parseAndFlattenType_Main(struct Main this, char* input){
	return /* parseType(input).map(Type::flatten) */;
}
/* private static */ Option<struct Type> parseType_Main(struct Main this, char* input){
	/* /* I8_star */ */ stripped = input.strip(input);/* 
        if (stripped.equals("var")) {
            return new Some<>(Primitive.Auto);
        } *//* 

        if (stripped.equals("int")) {
            return new Some<>(Primitive.I32);
        } *//* 

        if (stripped.equals("char")) {
            return new Some<>(Primitive.I8);
        } *//* 

        if (stripped.equals("String")) {
            return new Some<>(new Ref(Primitive.I8));
        } *//* 

        if (stripped.equals("boolean")) {
            return new Some<>(Primitive.Boolean);
        } *//* 

        if (stripped.equals("private")) {
            return new None<>();
        } *//* 

        if (typeParams.contains(stripped)) {
            return new Some<>(new TypeParam(stripped));
        } *//* 

        if (stripped.endsWith(">")) {
            var withoutEnd = stripped.substring(0, stripped.length() - ">".length());
            var argsStart = withoutEnd.indexOf("<");
            if (argsStart >= 0) {
                var base = withoutEnd.substring(0, argsStart).strip();
                var argsString = withoutEnd.substring(argsStart + "<".length()).strip();
                var args = parseValues(argsString, input1 -> parseWhitespace(input1)
                        .<Type>map(type -> type)
                        .or(() -> parseAndFlattenType(input1)))
                        .orElse(new ArrayList<>());

                return new Some<>(new Generic(base, args));
            }
        } *//* 

        if (isSymbol(stripped)) {
            return new Some<>(new Struct(stripped));
        } */
	return new_None_Whitespace();
}
/* private static  */ Option<List<T>> parseValues_Main<T>(struct Main this, char* args, Option<T> (*compiler)(char*)){
	return /* parseAll(args, Main::foldValueChar, compiler) */;
}
/* private static */ struct State foldValueChar_Main(struct Main this, struct State state, char c){/* 
        if (c == ',' && state.isLevel()) {
            return state.advance();
        } */
	/* /* State */ */ appended = state.append(state, c);/* 
        if (c == '<') {
            return appended.enter();
        } *//* 
        if (c == '>') {
            return appended.exit();
        } */
	return appended;
}
/* private static */ char* generatePlaceholder_Main(struct Main this, char* stripped){
	return /* "/* " + stripped + " */" */;
}
