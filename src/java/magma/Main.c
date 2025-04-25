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
        Boolean("int") */;
	/* private final */ char* value;
};
struct Operator {
	/* Equals(" */ = /* =") */;
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
                .or(() -> parseValue(input1).map(arg -> arg))
                .or(() -> new Some<>(new Content(input1)));
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
	Option<R> local0 = supplier();
	return local0.map(local0, /* otherValue -> new Tuple<>(this.value, otherValue) */);
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
/* @Override
        public */ char* generate_Definition(struct Definition this){
	/* /* inner -> !inner */ */ local0 = /* inner -> !inner */;
	/* /* var beforeType = this */.maybeBeforeType.filter(/* var beforeType = this */.maybeBeforeType, local0.isEmpty(local0)) */ local1 = /* var beforeType = this */.maybeBeforeType.filter(/* var beforeType = this */.maybeBeforeType, local0.isEmpty(local0));
	/* local1.map(local1, /* inner -> generatePlaceholder(inner) + " " */) */ local2 = local1.map(local1, /* inner -> generatePlaceholder(inner) + " " */);
	char* joinedTypeParams;/* 
            if (this.typeParams.isEmpty()) {
                joinedTypeParams = "";
            } *//* 
            else {
                joinedTypeParams = "<" + String.join(", ", this.typeParams) + ">";
            } */
	local2.orElse(local2, /* "" */);
	char* generatedWithName;/* 
            if (this.type instanceof Functional functional) {
                generatedWithName = functional.generateWithName(this.name);
            } *//* 
            else {
                generatedWithName = this.type.generate() + " " + this.name;
            } */
	return /* beforeType + generatedWithName + joinedTypeParams */;
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
struct Struct new_Struct(char* name){
	struct Struct this;
	this(name, Collections.emptyList(Collections));
	return this;
}
/* @Override
        public */ char* generate_Struct(struct Struct this){
	struct var typeParamString = /*  this.typeParams.isEmpty() ? "" : "<" + String.join(", ", this.typeParams) + ">" */;
	return /* "struct " + this.name + typeParamString */;
}
/* @Override
        public */ char* stringify_Struct(struct Struct this){
	return this.name;
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
	return this.generateWithName(this, /* "" */);
}
/* @Override
        public */ char* stringify_Functional(struct Functional this){
	/* /* var joined = this */.paramTypes.stream(/* var joined = this */.paramTypes) */ local0 = /* var joined = this */.paramTypes.stream(/* var joined = this */.paramTypes);
	/* local0.map(local0, /* Type::stringify */) */ local1 = local0.map(local0, /* Type::stringify */);
	local1.collect(local1, Collectors.joining(Collectors, /* "_" */));
	return /* "fn_" + this.returnType.stringify() + "_" + joined */;
}
/* public */ char* generateWithName_Functional(struct Functional this, char* name){
	/* /* var joined = this */.paramTypes.stream(/* var joined = this */.paramTypes) */ local0 = /* var joined = this */.paramTypes.stream(/* var joined = this */.paramTypes);
	/* local0.map(local0, /* Type::generate */) */ local1 = local0.map(local0, /* Type::generate */);
	local1.collect(local1, /* Collectors.joining(" */, /*  ") */);
	return /* this.returnType.generate() + " (*" +
                    name +
                    ")(" + joined + ")" */;
}
/* @Override
        public */ char* generate_Generic(struct Generic this){
	/* /* var joinedArgs = this */ */ local0 = /* var joinedArgs = this */;
	/* local0.args(local0) */ local1 = local0.args(local0);
	/* local1.stream(local1) */ local2 = local1.stream(local1);
	/* local2.map(local2, /* Type::generate */) */ local3 = local2.map(local2, /* Type::generate */);
	local3.collect(local3, /* Collectors.joining(" */, /*  ") */);
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
	/* /* arg -> arg */ */ local0 = /* arg -> arg */;
	/* /* var joined = this */.args.stream(/* var joined = this */.args) */ local1 = /* var joined = this */.args.stream(/* var joined = this */.args);
	/* local1.map(local1, local0.stringify(local0)) */ local2 = local1.map(local1, local0.stringify(local0));
	local2.collect(local2, Collectors.joining(Collectors, /* "_" */));
	return /* this.base + "_" + joined */;
}
/* private */ struct Definition toDefinition_ConstructorDefinition(struct ConstructorDefinition this){
	/* new_DefinitionBuilder() */ local0 = new_DefinitionBuilder();
	/* /* "new_" + this */ */ local1 = /* "new_" + this */;
	/* local0.withType(local0, new_Struct(this.name(this))) */ local2 = local0.withType(local0, new_Struct(this.name(this)));
	/* local2.withName(local2, local1.name(local1)) */ local3 = local2.withName(local2, local1.name(local1));
	return local3.complete(local3);
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
	return /* this.assignable.generate() + " = " + this */.value.generate(/* this.assignable.generate() + " = " + this */.value);
}
/* @Override
        public */ char* generate_DataAccess(struct DataAccess this){
	return /* this.parent.generate() + "." + this */.property;
}
/* @Override
        public */ char* generate_Symbol(struct Symbol this){
	return this.name;
}
/* @Override
        public */ char* generate_Return(struct Return this){
	return /* "return " + this */.value.generate(/* "return " + this */.value);
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
	/* /* var joined = this */.values.stream(/* var joined = this */.values) */ local0 = /* var joined = this */.values.stream(/* var joined = this */.values);
	/* local0.map(local0, /* Value::generate */) */ local1 = local0.map(local0, /* Value::generate */);
	local1.collect(local1, /* Collectors.joining(" */, /*  ") */);
	return /* "new " + this.type.generate() + "(" + joined + ")" */;
}
/* private */ struct Invocation toInvocation_Construction(struct Construction this){
	/* var typeAsString = this */.type.stringify(/* var typeAsString = this */.type);
	return new_Invocation(new_Symbol(/* "new_" + typeAsString */), this.values);
}
/* @Override
        public */ char* generate_Invocation(struct Invocation this){
	/* /* var joined = this */.arguments.stream(/* var joined = this */.arguments) */ local0 = /* var joined = this */.arguments.stream(/* var joined = this */.arguments);
	/* local0.map(local0, /* Value::generate */) */ local1 = local0.map(local0, /* Value::generate */);
	local1.collect(local1, /* Collectors.joining(" */, /*  ") */);
	return /* this.caller.generate() + "(" + joined + ")" */;
}
/* private */ struct Invocation toInvocation_Invocation(struct Invocation this){
	return this;
}
struct public Frame_Frame(struct Frame this, struct StructNode node){
	this(node, new_HashMap_Whitespace());
}
/* public static  */ Stream<T> toStream_Options<T>(struct Options this, Option<T> option){
	/* option.map(option, /* Stream::of */) */ local0 = option.map(option, /* Stream::of */);
	return local0.orElseGet(local0, /* Stream::empty */);
}
/* public static  */ Option<T> fromNative_Options<T>(struct Options this, Optional<T> optional){
	/* /* optional.<Option<T>>map(Some::new) */ */ local0 = /* optional.<Option<T>>map(Some::new) */;
	return local0.orElseGet(local0, /* None::new */);
}
/* @Override
        public */ char* generate_Ternary(struct Ternary this){
	return /* this.condition.generate() + " ? " + this.whenTrue.generate() + " : " + this */.whenFalse.generate(/* this.condition.generate() + " ? " + this.whenTrue.generate() + " : " + this */.whenFalse);
}
/* @Override
        public */ char* generate_Number(struct Number this){
	return this.value;
}
/* @Override
        public */ char* generate_Operation(struct Operation this){
	return /* this.left.generate() + " " + this.operator.representation + " " + this */.right.generate(/* this.left.generate() + " " + this.operator.representation + " " + this */.right);
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
	/* /* var joinedStructs = String */ */ local0 = /* var joinedStructs = String */;
	/* /* var joinedFunctions = String */ */ local1 = /* var joinedFunctions = String */;
	struct var output = compileStatements(input, /*  input1 -> new Some<>(compileRootSegment(input1)) */);
	local0.join(local0, /* "" */, structs);
	local1.join(local1, /* "" */, functions);
	return /* output + joinedStructs + joinedFunctions */;
}
/* private static */ char* compileStatements_Main(struct Main this, char* input, Option<char*> (*compiler)(char*)){
	return compileAll(input, /*  Main::foldStatementChar */, compiler, /*  Main::mergeStatements */);
}
/* private static */ char* compileAll_Main(struct Main this, char* input, BiFunction<struct State, struct Character, struct State> folder, Option<char*> (*compiler)(char*), BiFunction<struct StringBuilder, char*, struct StringBuilder> merger){
	/* parseAll(input, folder, compiler) */ local0 = parseAll(input, folder, compiler);
	/* local0.map(local0, /* listOption -> generateAll(merger, listOption) */) */ local1 = local0.map(local0, /* listOption -> generateAll(merger, listOption) */);
	return local1.orElse(local1, /* "" */);
}
/* private static */ char* generateAll_Main(struct Main this, BiFunction<struct StringBuilder, char*, struct StringBuilder> merger, List<char*> compiled){
	struct var output = new_StringBuilder();/* 
        for (var segment : compiled) {
            output = merger.apply(output, segment);
        } */
	return output.toString(output);
}
/* private static  */ Option<List<T>> parseAll_Main<T>(struct Main this, char* input, BiFunction<struct State, struct Character, struct State> folder, Option<T> (*compiler)(char*)){
	struct var segments = divide(input, folder);
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
	/* /* var current = State */ */ local0 = /* var current = State */;
	/* /* i < input */ */ local1 = /* i < input */;
	local0.createDefault(local0);
	/* for (var i  */ = 0;
	local1.length(local1);/*  i++) {
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
	/* /* var appended = current */ */ local0 = /* var appended = current */;
	local0.append(local0, c);/* 
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
	/* /* var stripped = input */ */ local0 = /* var stripped = input */;
	/* compileClass(stripped) */ local1 = compileClass(stripped);
	local0.strip(local0);/* 

        if (stripped.startsWith("package ")) {
            return "";
        } */
	return local1.orElseGet(local1, /* () -> generatePlaceholder(stripped) + "\n" */);
}
/* private static */ Option<char*> compileClass_Main(struct Main this, char* stripped){
	return compileStructured(stripped, /*  "class " */);
}
/* private static */ Option<char*> compileStructured_Main(struct Main this, char* stripped, char* infix){
	/* /* var classIndex = stripped */ */ local0 = /* var classIndex = stripped */;
	/* /* classIndex + infix */ */ local1 = /* classIndex + infix */;
	/* /* var afterKeyword = stripped */ */ local2 = /* var afterKeyword = stripped */;
	/* /* " */ */ local3 = /* " */;
	local0.indexOf(local0, infix);/* 
        if (classIndex < 0) {
            return new None<>();
        } */
	local2.substring(local2, local1.length(local1));/* 
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
	local3.length(local3, /* ) */);
	struct var typeParamString = /*  typeParams.isEmpty() ? "" : "<" + String.join(", ", typeParams) + ">" */;
	struct var structNode = new_StructNode(name, typeParams);
	frames.addLast(frames, new_Frame(structNode));/* 

        var generated = "struct " + name + typeParamString + " {" +
                compileStatements(content, input1 -> new Some<>(compileClassSegment(input1))) +
                "\n} */
	/*  */;
	/* \n" */;
	frames.removeLast(frames);
	structs.add(structs, generated);
	return new_Some_Whitespace(/* "" */);
}
/* private static */ int isSymbol_Main(struct Main this, char* input){
	/* /* i < input */ */ local0 = /* i < input */;/* 
        if (input.isEmpty()) {
            return false;
        } */
	/* for (var i  */ = 0;
	local0.length(local0);/*  i++) {
            var c = input.charAt(i);
            if (Character.isLetter(c)) {
                continue;
            }
            return false;
        } */
	return true;
}
/* private static */ char* compileClassSegment_Main(struct Main this, char* input){
	/* compileWhitespace(input) */ local0 = compileWhitespace(input);
	/* local0.or(local0, /* () -> compileClass(input) */) */ local1 = local0.or(local0, /* () -> compileClass(input) */);
	/* local1.or(local1, /* () -> compileStructured(input, "enum ") */) */ local2 = local1.or(local1, /* () -> compileStructured(input, "enum ") */);
	/* local2.or(local2, /* () -> compileStructured(input, "record ") */) */ local3 = local2.or(local2, /* () -> compileStructured(input, "record ") */);
	/* local3.or(local3, /* () -> compileStructured(input, "interface ") */) */ local4 = local3.or(local3, /* () -> compileStructured(input, "interface ") */);
	/* local4.or(local4, /* () -> compileMethod(input) */) */ local5 = local4.or(local4, /* () -> compileMethod(input) */);
	/* local5.or(local5, /* () -> compileClassStatement(input) */) */ local6 = local5.or(local5, /* () -> compileClassStatement(input) */);
	return local6.orElseGet(local6, /* () -> "\n\t" + generatePlaceholder(input.strip()) */);
}
/* private static */ Option<char*> compileWhitespace_Main(struct Main this, char* input){
	/* parseWhitespace(input) */ local0 = parseWhitespace(input);
	return local0.map(local0, /* Whitespace::generate */);
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
	/* /* var paramStart = input */ */ local0 = /* var paramStart = input */;
	/* /* var beforeParams = input */ */ local1 = /* var beforeParams = input */;
	/* local1.substring(local1, 0, paramStart) */ local2 = local1.substring(local1, 0, paramStart);
	/* /* var withParams = input */ */ local3 = /* var withParams = input */;
	/* /* () -> compileConstructorDefinition(beforeParams) */ */ local4 = /* () -> compileConstructorDefinition(beforeParams) */;
	/* /* var maybeHeader = parseDefinition(beforeParams)
                .<Definable>map(value -> value)
                 */ */ local5 = /* var maybeHeader = parseDefinition(beforeParams)
                .<Definable>map(value -> value)
                 */;
	/* /* var paramEnd = withParams */ */ local6 = /* var paramEnd = withParams */;
	/* /* var paramStrings = withParams */ */ local7 = /* var paramStrings = withParams */;
	/* local7.substring(local7, 0, paramEnd) */ local8 = local7.substring(local7, 0, paramEnd);
	/* /* var afterParams = withParams.substring(paramEnd + ")" */ */ local9 = /* var afterParams = withParams.substring(paramEnd + ")" */;
	local0.indexOf(local0, /* "(" */);/* 
        if (paramStart < 0) {
            return new None<>();
        } */
	local2.strip(local2);
	local3.substring(local3, /* paramStart + "(".length() */);
	struct var currentStruct = frames.getLast(frames).node;
	struct var currentStructName = currentStruct.name;
	struct var currentStructTypeParams = currentStruct.typeParams;
	typeParams.addAll(typeParams, currentStructTypeParams);
	local5.or(local5, local4.map(local4, /* value -> value */));/* 

        if (!(maybeHeader instanceof Some(var header))) {
            return new None<>();
        } */
	local6.indexOf(local6, /* ")" */);/* 
        if (paramEnd < 0) {
            return new None<>();
        } */
	local8.strip(local8);
	local9.length(local9, /* )).strip( */);/* 
        if (afterParams.startsWith("{") && afterParams.endsWith("} *//* ")) {
            var content = afterParams.substring(1, afterParams.length() - 1);

            var maybeInputParams = parseValues(paramStrings, Main::parseParameter);
            if (maybeInputParams instanceof Some(var inputParams)) {
                var definitions = inputParams.stream()
                        .map(Parameter::toDefinition)
                        .flatMap(Options::toStream)
                        .toList();

                var lastDefinitions = frames.getLast().definitions;
                for (var definition : definitions) {
                    lastDefinitions.put(definition.name, definition.type);
                }

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
                }
            }
        } */
	/* if (afterParams.equals(" */;/* ")) {
            return new Some<>("");
        } */
	return new_None_Whitespace();
}
/* private static  */ Option<List<T>> parseStatements_Main<T>(struct Main this, char* content, Option<T> (*compiler)(char*)){
	return parseAll(content, /*  Main::foldStatementChar */, compiler);
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
	/* /* var stripped = input */ */ local0 = /* var stripped = input */;
	/* /* var nameSeparator = stripped */ */ local1 = /* var nameSeparator = stripped */;
	local0.strip(local0);
	local1.lastIndexOf(local1, /* " " */);/* 
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
	/* /* () -> parseStatementWithoutBraces(input, Main::parseStatementValue) */ */ local0 = /* () -> parseStatementWithoutBraces(input, Main::parseStatementValue) */;
	/* /* parseWhitespace(input)
                .<FunctionSegment>map(value -> value)
                 */ */ local1 = /* parseWhitespace(input)
                .<FunctionSegment>map(value -> value)
                 */;
	/* local1.or(local1, local0.map(local0, /* value -> value */)) */ local2 = local1.or(local1, local0.map(local0, /* value -> value */));
	return local2.orElseGet(local2, /* () -> new Content(input) */);
}
/* private static */ struct StatementValue parseStatementValue_Main(struct Main this, char* input){
	/* /* () -> parsePostIncrement(input) */ */ local0 = /* () -> parsePostIncrement(input) */;
	/* /* parseReturn(input).<StatementValue>map(value -> value)
                 */ */ local1 = /* parseReturn(input).<StatementValue>map(value -> value)
                 */;
	/* /* () -> parsePostDecrement(input) */ */ local2 = /* () -> parsePostDecrement(input) */;
	/* local1.or(local1, local0.map(local0, /* value -> value */)) */ local3 = local1.or(local1, local0.map(local0, /* value -> value */));
	/* /* () -> parseInvocation(input) */ */ local4 = /* () -> parseInvocation(input) */;
	/* local3.or(local3, local2.map(local2, /* value -> value */)) */ local5 = local3.or(local3, local2.map(local2, /* value -> value */));
	/* /* () -> parseAssignment(input) */ */ local6 = /* () -> parseAssignment(input) */;
	/* local5.or(local5, local4.map(local4, /* value -> value */)) */ local7 = local5.or(local5, local4.map(local4, /* value -> value */));
	/* /* () -> parseDefinition(input) */ */ local8 = /* () -> parseDefinition(input) */;
	/* local7.or(local7, local6.map(local6, /* value -> value */)) */ local9 = local7.or(local7, local6.map(local6, /* value -> value */));
	/* local9.or(local9, local8.map(local8, /* value -> value */)) */ local10 = local9.or(local9, local8.map(local8, /* value -> value */));
	return local10.orElseGet(local10, /* () -> new Content(input) */);
}
/* private static */ Option<struct PostDecrement> parsePostDecrement_Main(struct Main this, char* input){
	/* /* var stripped = input */ */ local0 = /* var stripped = input */;
	local0.strip(local0);/* 
        if (stripped.endsWith("--")) {
            return parseValue(stripped.substring(0, stripped.length() - "--".length())).map(PostDecrement::new);
        } *//* 
        else {
            return new None<>();
        } */
}
/* private static */ Option<struct PostIncrement> parsePostIncrement_Main(struct Main this, char* input){
	/* /* var stripped = input */ */ local0 = /* var stripped = input */;
	local0.strip(local0);/* 
        if (stripped.endsWith("++")) {
            return parseValue(stripped.substring(0, stripped.length() - "++".length())).map(PostIncrement::new);
        } *//* 
        else {
            return new None<>();
        } */
}
/* private static */ Option<struct Return> parseReturn_Main(struct Main this, char* input){
	/* /* var stripped = input */ */ local0 = /* var stripped = input */;
	local0.strip(local0);/* 
        if (stripped.startsWith("return ")) {
            return new Some<>(new Return(parseValueOrPlaceholder(stripped.substring("return ".length()))));
        } */
	return new_None_Whitespace();
}
/* private static */ Option<char*> compileClassStatement_Main(struct Main this, char* input){
	return compileStatement(input, /*  Main::compileClassStatementValue */);
}
/* private static */ Option<char*> compileStatement_Main(struct Main this, char* input, struct StatementValue (*compiler)(char*)){
	/* parseStatementWithoutBraces(input, compiler) */ local0 = parseStatementWithoutBraces(input, compiler);
	return local0.map(local0, /* Statement::generate */);
}
/* private static */ Option<struct Statement> parseStatementWithoutBraces_Main(struct Main this, char* input, struct StatementValue (*compiler)(char*)){
	/* /* var stripped = input */ */ local0 = /* var stripped = input */;
	local0.strip(local0);
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
	/* /* () -> parseDefinition(input) */ */ local0 = /* () -> parseDefinition(input) */;
	/* /* parseAssignment(input).<StatementValue>map(value -> value)
                 */ */ local1 = /* parseAssignment(input).<StatementValue>map(value -> value)
                 */;
	/* local1.or(local1, local0.map(local0, /* value -> value */)) */ local2 = local1.or(local1, local0.map(local0, /* value -> value */));
	return local2.orElseGet(local2, /* () -> new Content(input) */);
}
/* private static */ Option<struct Assignment> parseAssignment_Main(struct Main this, char* input){
	/* /* var valueSeparator = input */ */ local0 = /* var valueSeparator = input */;
	local0.indexOf(local0, /* "=" */);/* 
        if (valueSeparator >= 0) {
            var inputDefinition = input.substring(0, valueSeparator);
            var value = input.substring(valueSeparator + "=".length());

            var destination = parseDefinition(inputDefinition)
                    .<Assignable>map(result -> result)
                    .orElseGet(() -> parseValueOrPlaceholder(inputDefinition));

            return new Some<>(new Assignment(destination, parseValueOrPlaceholder(value)));
        } */
	return new_None_Whitespace();
}
/* private static */ struct Value parseValueOrPlaceholder_Main(struct Main this, char* input){
	/* parseValue(input) */ local0 = parseValue(input);
	return local0.orElseGet(local0, /* () -> new Content(input) */);
}
/* private static */ Option<struct Value> parseValue_Main(struct Main this, char* input){
	/* /* var stripped = input */ */ local0 = /* var stripped = input */;
	/* /* var separator = stripped */ */ local1 = /* var separator = stripped */;
	/* /* var conditionSeparator = stripped */ */ local2 = /* var conditionSeparator = stripped */;
	/* /* var operatorIndex = input */ */ local3 = /* var operatorIndex = input */;
	local0.strip(local0);/* 

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
	struct var maybeInvocation = parseInvocation(stripped);/* 
        if (maybeInvocation instanceof Some(var invocation)) {
            return new Some<>(invocation);
        } */
	local1.lastIndexOf(local1, /* "." */);/* 
        if (separator >= 0) {
            var parentString = stripped.substring(0, separator);
            var property = stripped.substring(separator + ".".length()).strip();
            if (isSymbol(property)) {
                var parent = parseValueOrPlaceholder(parentString);
                var type = resolveType(parent);
                if (type instanceof Functional) {
                    return new Some<>(parent);
                }

                return new Some<>(new DataAccess(parent, property));
            }
        } */
	local2.indexOf(local2, /* "?" */);/* 
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
        } */
	struct var operator = Operator.Equals;
	local3.indexOf(local3, operator.representation);/* 
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
	/* /* i < input */ */ local0 = /* i < input */;
	/* for (var i  */ = 0;
	local0.length(local0);/*  i++) {
            var c = input.charAt(i);
            if (Character.isDigit(c)) {
                continue;
            }
            return false;
        } */
	return true;
}
/* private static */ struct Type resolveType_Main(struct Main this, struct Value value){/* 
        if (value instanceof Invocation(var base, var _)) {
            var resolved = resolveType(base);
            if (resolved instanceof Functional functional) {
                return functional.returnType;
            }
        } *//* 

        if (value instanceof Symbol(var name)) {
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
	struct var definitions = frame.definitions;/* 
        if (definitions.containsKey(name)) {
            return new Some<>(definitions.get(name));
        } *//* 
        else {
            return new None<>();
        } */
}
/* private static  */ Option<struct R> parseInvokable_Main<T,  R>(struct Main this, char* input, Option<T> (*beforeArgsCaller)(char*), BiFunction<T, List<struct Value>, struct R> builder){
	/* /* var withoutPrefix = input */ */ local0 = /* var withoutPrefix = input */;
	/* /* var withoutEnd = withoutPrefix.substring(0, withoutPrefix.length() - ")" */ */ local1 = /* var withoutEnd = withoutPrefix.substring(0, withoutPrefix.length() - ")" */;
	/* /* var beforeLast = slices */ */ local2 = /* var beforeLast = slices */;
	/* /* var joined = String */ */ local3 = /* var joined = String */;
	/* local3.join(local3, /* "" */, beforeLast) */ local4 = local3.join(local3, /* "" */, beforeLast);
	/* /* var beforeArgsStart = joined */ */ local5 = /* var beforeArgsStart = joined */;
	/* /* var args = slices */ */ local6 = /* var args = slices */;
	/* /* values -> builder */ */ local7 = /* values -> builder */;
	/* parseValues(args, /*  Main::parseArgument */) */ local8 = parseValues(args, /*  Main::parseArgument */);
	local0.strip(local0);/* 
        if (!withoutPrefix.endsWith(")")) {
            return new None<>();
        } */
	local1.length(local1, /* ) */);
	struct var slices = divideAll(withoutEnd, /*  Main::foldInvocationStart */);
	local2.subList(local2, 0, /*  slices.size() - 1 */);
	local4.strip(local4);/* 
        if (!joined.endsWith("(")) {
            return new None<>();
        } */
	local5.substring(local5, 0, /*  joined.length() - 1 */);
	local6.getLast(local6);/* 

        if (!(beforeArgsCaller.apply(beforeArgsStart) instanceof Some(var outputBeforeArgs))) {
            return new None<>();
        } */
	return local8.map(local8, local7.apply(local7, outputBeforeArgs, values));
}
/* private static */ struct State foldInvocationStart_Main(struct Main this, struct State state, char c){
	/* /* var appended = state */ */ local0 = /* var appended = state */;
	local0.append(local0, c);/* 
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
	/* /* () -> parseDefinition(input) */ */ local0 = /* () -> parseDefinition(input) */;
	/* /* parseWhitespace(input)
                .<Parameter>map(result -> result)
                 */ */ local1 = /* parseWhitespace(input)
                .<Parameter>map(result -> result)
                 */;
	return local1.or(local1, local0.map(local0, /* result -> result */));
}
/* private static */ Option<struct Definition> parseDefinition_Main(struct Main this, char* input){
	/* /* var stripped = input */ */ local0 = /* var stripped = input */;
	/* /* var nameSeparator = stripped */ */ local1 = /* var nameSeparator = stripped */;
	/* /* var beforeName = stripped */ */ local2 = /* var beforeName = stripped */;
	/* local2.substring(local2, 0, nameSeparator) */ local3 = local2.substring(local2, 0, nameSeparator);
	/* /* nameSeparator + " " */ */ local4 = /* nameSeparator + " " */;
	/* /* var name = stripped */ */ local5 = /* var name = stripped */;
	/* local5.substring(local5, local4.length(local4)) */ local6 = local5.substring(local5, local4.length(local4));
	/* /* var withName = new DefinitionBuilder() */ */ local7 = /* var withName = new DefinitionBuilder() */;
	local0.strip(local0);
	local1.lastIndexOf(local1, /* " " */);/* 
        if (nameSeparator < 0) {
            return new None<>();
        } */
	local3.strip(local3);
	local6.strip(local6);/* 
        if (!isSymbol(name)) {
            return new None<>();
        } */
	local7.withName(local7, name);/* 
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
	struct var depth = 0;
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
	/* parseType(input) */ local0 = parseType(input);
	return local0.map(local0, /* Type::flatten */);
}
/* private static */ Option<struct Type> parseType_Main(struct Main this, char* input){
	/* /* var stripped = input */ */ local0 = /* var stripped = input */;
	local0.strip(local0);/* 
        if (stripped.equals("private")) {
            return new None<>();
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
	return parseAll(args, /*  Main::foldValueChar */, compiler);
}
/* private static */ struct State foldValueChar_Main(struct Main this, struct State state, char c){
	/* /* var appended = state */ */ local0 = /* var appended = state */;/* 
        if (c == ',' && state.isLevel()) {
            return state.advance();
        } */
	local0.append(local0, c);/* 
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
