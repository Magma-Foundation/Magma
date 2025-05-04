/* private interface Result<T, X>  */{/* 
        <R> R match(Function<T, R> whenOk, Function<X, R> whenErr); *//* 
     */
};
/* private interface Option<T>  */{/* 
        void ifPresent(Consumer<T> consumer); *//* 

        T orElseGet(Supplier<T> supplier); *//* 

        Option<T> or(Supplier<Option<T>> other); *//* 

        boolean isPresent(); *//* 

        <R> Option<R> map(Function<T, R> mapper); *//* 

        <R> Option<R> flatMap(Function<T, Option<R>> mapper); *//* 

        T orElse(T other); *//* 
     */
};
/* private interface Error  */{/* 
        String display(); *//* 
     */
};
/* private @interface Actual  */{/* 
     */
};
/* private interface Value  */{/* 
        String generate(); *//* 
     */
};
/* private record IOError(IOException exception) implements Error  */{/* 
     */
};
/* record None<T>() implements Option<T>  */{/* 
     */
};
/* private record Some<T>(T value) implements Option<T>  */{/* 
     */
};
/* private record Ok<T, X>(T value) implements Result<T, X>  */{/* 
     */
};
/* private record Err<T, X>(X error) implements Result<T, X>  */{/* 
     */
};
/* private static class DivideState  */{/* 
        private final String input; *//* 
        private final List<String> segments; *//* 
        private final int index; *//* 
        private StringBuilder buffer; *//* 
        private int depth; *//* 
     */
};
/* private record Frame(Map<String, Integer> counters, List<String> statements)  */{/* 
     */
};
/* private record CompileState(List<String> structs, List<String> functions, List<Frame> frames)  */{/* 
     */
};
/* private record Tuple<A, B>(A left, B right)  */{/* 
     */
};
/* private record Definition(
            List<String> annotations,
            List<String> modifiers,
            String beforeType,
            String type,
            String name
    )  */{/* 
     */
};
/* private record StringValue(String value) implements Value  */{/* 
     */
};
/* private record Symbol(String value) implements Value  */{/* 
     */
};
/* private record Invocation(Value caller, List<Value> arguments) implements Value  */{/* 
     */
};
/* private record DataAccess(Value parent, String child) implements Value  */{/* 
     */
};
/* private record MethodAccess(String parent, String child) implements Value  */{/* 
     */
};
/* private record Content(String input) implements Value  */{/* 
     */
};
/* private record Operation(Value left, Operator operator, Value right) implements Value  */{/* 
     */
};
/* private static class Whitespace implements Value  */{/* 
     */
};
/* private record TupleNode(Value first, Value second) implements Value  */{/* 
     */
};
/* private record NumberValue(String value) implements Value  */{/* 
     */
};
/* private record CharValue(String value) implements Value  */{/* 
     */
};
/* private static BiFunction<CompileState, String, Option<Tuple<CompileState, String>>> structure(String record)  */{/* 
        return (state0, input0) -> {
            var stripped = input0.strip();
            if (stripped.endsWith("}")) {
                var withoutEnd = stripped.substring(0, stripped.length() - "}".length());
                var contentStart = withoutEnd.indexOf("{");
                if (contentStart >= 0) {
                    var left = withoutEnd.substring(0, contentStart);
                    var right = withoutEnd.substring(contentStart + "{".length());
                    if (left.indexOf(record) >= 0) {
                        var maybeResult = compileStatements(state0, right, Main::compileStructSegment);
                        if (maybeResult instanceof Some(var result)) {
                            var generated = generatePlaceholder(left) + "{" + result.right + "\n};\n";
                            return new Some<>(new Tuple<>(result.left.addStruct(generated), ""));
                        }
                    }
                }
            }

            return new None<>();
        } *//* ; *//* 
     */
};
/* public class Main  */{/* 

    private enum Operator {
        ADD("+"),
        SUBTRACT("-"),
        GREATER_THAN_OR_EQUALS(">="),
        EQUALS("==");

        private final String representation;

        Operator(String representation) {
            this.representation = representation;
        }
    } *//* 

    public static final Path SOURCE = Paths.get(".", "src", "java", "magma", "Main.java"); *//* 
    public static final Path TARGET = SOURCE.resolveSibling("main.c"); */expect /* private static */template Option<struct IOError> writeTarget(char* output);
expect /* private static */template Result<char*, struct IOError> readSource();
/* 
 */
};
@Override
/* public */char* display(){
	auto writer = struct StringWriter::new();
	printStackTrace(this.exception, struct PrintWriter::new(writer));
	return toString(writer);
}
@Override
/* public */void ifPresent(template Consumer<struct T> consumer){
}
@Override
/* public */struct T orElseGet(template Supplier<struct T> supplier){
	return get(supplier);
}
@Override
/* public */template Option<struct T> or(template Supplier<template Option<struct T>> other){
	return get(other);
}
@Override
/* public */int isPresent(){
	return 0;
}
@Override
/* public <R> */template Option<struct R> map(template Function<struct T, struct R> mapper){
	return template None<struct >::new();
}
@Override
/* public <R> */template Option<struct R> flatMap(template Function<struct T, template Option<struct R>> mapper){
	return template None<struct >::new();
}
@Override
/* public */struct T orElse(struct T other){
	return other;
}
@Override
/* public */void ifPresent(template Consumer<struct T> consumer){
	accept(consumer, this.value);
}
@Override
/* public */struct T orElseGet(template Supplier<struct T> supplier){
	return this.value;
}
@Override
/* public */template Option<struct T> or(template Supplier<template Option<struct T>> other){
	return this;
}
@Override
/* public */int isPresent(){
	return 1;
}
@Override
/* public <R> */template Option<struct R> map(template Function<struct T, struct R> mapper){
	return template Some<struct >::new(apply(mapper, this.value));
}
@Override
/* public <R> */template Option<struct R> flatMap(template Function<struct T, template Option<struct R>> mapper){
	return apply(mapper, this.value);
}
@Override
/* public */struct T orElse(struct T other){
	return this.value;
}
@Override
/* public <R> */struct R match(template Function<struct T, struct R> whenOk, template Function<struct X, struct R> whenErr){
	return apply(whenOk, this.value);
}
@Override
/* public <R> */struct R match(template Function<struct T, struct R> whenOk, template Function<struct X, struct R> whenErr){
	return apply(whenErr, this.error);
}
struct public DivideState(char* input){
	this(input, template ArrayList<struct >::new(), struct StringBuilder::new(), 0, 0);
}
struct public DivideState(char* input, template List<char*> segments, struct StringBuilder buffer, struct int index, struct int depth){
	/* this.input  */ = input;
	/* this.segments  */ = segments;
	/* this.index  */ = index;
	/* this.depth  */ = depth;
	/* this.buffer  */ = buffer;
}
/* private */struct DivideState enter(){
	/* this.depth  */ = this.depth + 1;
	return this;
}
/* private */struct DivideState exit(){
	/* this.depth  */ = this.depth - 1;
	return this;
}
/* private */struct DivideState advance(){
	add(segments(this), toString(this.buffer));
	/* this.buffer  */ = struct StringBuilder::new();
	return this;
}
/* public */template List<char*> segments(){
	return this.segments;
}
/* private */int isShallow(){
	return this.depth == 1;
}
/* public */int isLevel(){
	return this.depth == 0;
}
/* public */template Option<struct DivideState> popAndAppendToOption(){
	return map(popAndAppendToTuple(this), struct Tuple::right);
}
auto lambda0(auto tuple){
	return (tuple.left, append(tuple.right, tuple.left));
}
/* public */template Option<(struct Character, struct DivideState)> popAndAppendToTuple(){
	return map(pop(this), lambda0);
}
/* private */struct DivideState append(char c){
	append(this.buffer, c);
	return this;
}
/* public */template Option<(struct Character, struct DivideState)> pop(){
	if (/* this.index < this.input.length() */){
		auto c = charAt(this.input, this.index);
		return template Some<struct >::new((c, struct DivideState::new(this.input, this.segments, this.buffer, this.index + 1, this.depth)));
	}
	else {
		return template None<struct >::new();
	}
}
/* public */char peek(){
	return charAt(this.input, this.index);
}
struct public Frame(){
	this(template HashMap<struct >::new(), template ArrayList<struct >::new());
}
/* public */(char*, struct Frame) createName(char* category){
	if (/* !this.counters.containsKey(category) */){
		put(this.counters, category, 0);
	}
	auto oldCounter = get(this.counters, category);
	auto name = category + oldCounter;
	auto newCounter = oldCounter + 1;
	put(this.counters, category, newCounter);
	return (name, this);
}
/* public */struct Frame addStatement(char* statement){
	add(this.statements, statement);
	return this;
}
struct public CompileState(){
	this(template ArrayList<struct >::new(), template ArrayList<struct >::new(), template ArrayList<struct >::new(singletonList(Collections, struct Frame::new())));
}
/* public */struct CompileState addFunction(char* generated){
	add(this.functions, generated);
	return this;
}
/* public */(char*, struct CompileState) createName(char* category){
	auto frame = getLast(this.frames);
	auto nameTuple = createName(frame, category);
	set(this.frames, size(this.frames) - 1, nameTuple.right);
	return (nameTuple.left, this);
}
/* public */struct CompileState addStatement(char* statement){
	addStatement(getLast(this.frames), statement);
	return this;
}
/* public */struct CompileState enter(){
	add(this.frames, struct Frame::new());
	return this;
}
/* public */struct CompileState exit(){
	removeLast(this.frames);
	return this;
}
/* public */struct CompileState addStruct(char* generated){
	add(this.structs, generated);
	return this;
}
/* public */struct int depth(){
	return size(this.frames);
}
/* private */char* generate(){
	/* String annotationsStrings */;
	if (isEmpty(this.annotations)){
		/* annotationsStrings  */ = "";
	}
	else {
		/* annotationsStrings  */ = /*  this.annotations.stream().map(value -> "@" + value).collect(Collectors.joining("\n")) + "\n" */;
	}
	auto modifiersString = /*  this.modifiers.isEmpty() ? "" : String.join(" ", this.modifiers) + " " */;
	auto beforeTypeString = /*  this.beforeType.isEmpty() ? "" : generatePlaceholder(this.beforeType) */;
	return annotationsStrings + modifiersString + beforeTypeString + this.type + " " + this.name;
}
@Override
/* public */char* generate(){
	return "\"" + this.value + "\"";
}
@Override
/* public */char* generate(){
	return this.value;
}
@Override
/* public */char* generate(){
	collect(/* var joined = this.arguments.stream()
                    .map(Value */::generate), joining(Collectors, ", "));
	return generate(this.caller) + "(" + joined + ")";
}
@Override
/* public */char* generate(){
	return generate(this.parent) + "." + this.child;
}
@Override
/* public */char* generate(){
	return parent(this) + "::" + child(this);
}
@Override
/* public */char* generate(){
	return generatePlaceholder(this.input);
}
@Override
/* public */char* generate(){
	return generate(this.left) + " " + this.operator.representation + " " + generate(this.right);
}
@Override
/* public */char* generate(){
	return "";
}
@Override
/* public */char* generate(){
	return "(" + this.first.generate() + ", " + this.second.generate() + ")";
}
@Override
/* public */char* generate(){
	return this.value;
}
@Override
/* public */char* generate(){
	return "'" + this.value + "'";
}
auto lambda0(auto error){
	return println(System.err, display(error));
}
auto lambda1(auto input){
	auto output = compile(input);
	return writeTarget(output);
}
/* public static */void main(){
	ifPresent(match(readSource(), lambda1, struct Some::new), lambda0);
}
/* private static */char* compile(char* input){
	auto state = struct CompileState::new();
	auto maybeTuple = compileStatements(state, input, struct Main::compileRootSegment);
	if (/* !(maybeTuple instanceof Some(var tuple)) */){
		return "";
	}
	auto joinedStructs = join(String, "", tuple.left.structs);
	auto joinedFunctions = join(String, "", tuple.left.functions);
	return joinedStructs + joinedFunctions + tuple.right;
}
/* private static */template Option<(struct CompileState, char*)> compileRootSegment(struct CompileState state, char* input){
	return or(state, input, of(List, type(struct Main::namespaced), type(structure("class")), type(struct Main::compileContent)));
}
/* private static */template Option<(struct CompileState, char*)> namespaced(struct CompileState state, char* input){
	auto stripped = strip(input);
	if (/* stripped.startsWith("package ") || stripped.startsWith("import ") */){
		return template Some<struct >::new((state, ""));
	}
	return template None<struct >::new();
}
auto lambda0(auto tuple){
	return (tuple.left, generateStatements(tuple.right));
}
/* private static */template Option<(struct CompileState, char*)> compileStatements(struct CompileState state, char* input, template BiFunction<struct CompileState, char*, template Option<(struct CompileState, char*)>> mapper){
	return map(parseStatements(state, input, mapper), lambda0);
}
/* private static */char* generateStatements(template List<char*> elements){
	return generateAll(elements, struct Main::mergeStatements);
}
/* private static */template Option<(struct CompileState, template List<char*>)> parseStatements(struct CompileState state, char* input, template BiFunction<struct CompileState, char*, template Option<(struct CompileState, char*)>> mapper){
	return parseAll(state, input, struct Main::foldStatementChar, mapper);
}
/* private static <T> */template Option<(struct CompileState, template List<struct T>)> parseAll(struct CompileState initial, char* input, template BiFunction<struct DivideState, struct Character, struct DivideState> folder, template BiFunction<struct CompileState, char*, template Option<(struct CompileState, struct T)>> mapper){
	auto segments = divideAll(input, folder);
	(struct CompileState, template List<struct T>) current = (initial, template ArrayList<struct T>::new());
	/* for (var segment : segments) */{
		auto maybeMapped = apply(mapper, current.left, segment);
		if (/* maybeMapped instanceof Some(var mapped) */){
			add(current.right, mapped.right);
			/* current  */ = (mapped.left, current.right);
		}
		else {
			return template None<struct >::new();
		}
	}
	return template Some<struct >::new(current);
}
auto lambda0(){
	return apply(folder, popped.right, popped.left);
}
auto lambda1(){
	return foldDoubleQuotes(popped.right, popped.left);
}
auto lambda2(){
	return apply(folder, popped.right, popped.left);
}
auto lambda3(){
	return foldDoubleQuotes(popped.right, popped.left);
}
/* private static */template List<char*> divideAll(char* input, template BiFunction<struct DivideState, struct Character, struct DivideState> folder){
	auto current = struct DivideState::new(input);
	/* while (true) */{
		auto maybePopped = pop(current);
		if (/* !(maybePopped instanceof Some(var popped)) */){
			/* break */;
		}
		/* current  */ = orElseGet(or(foldSingleQuotes(popped.right, popped.left), lambda3), lambda2);
	}
	return advance(current).segments;
}
/* private static */template Option<struct DivideState> foldDoubleQuotes(struct DivideState state, char maybeDoubleQuotes){
	if (/* maybeDoubleQuotes != '\"' */){
		return template None<struct >::new();
	}
	auto current = append(state, maybeDoubleQuotes);
	/* while (true) */{
		if (/* !(current.popAndAppendToTuple() instanceof Some(var popped)) */){
			/* break */;
		}
		auto next = popped.left;
		/* current  */ = popped.right;
		if (next == '\\'){
			/* current  */ = orElse(popAndAppendToOption(current), current);
		}
		if (next == '\"'){
			/* break */;
		}
	}
	return template Some<struct >::new(current);
}
auto lambda0(auto popped){
	return popped.left;
}
auto lambda1(auto popped){
	return popped.left;
}
/* private static */template Option<struct DivideState> foldSingleQuotes(struct DivideState state, char c){
	if (/* c != '\'' */){
		return template None<struct >::new();
	}
	auto appended = append(state, c);
	return /* appended.popAndAppendToTuple()
                .flatMap(popped -> popped.left == '\\' ? popped.right.popAndAppendToOption() : new Some<>(popped.right))
                .flatMap(DivideState */::popAndAppendToOption);
}
/* private static */char* generateAll(template List<char*> elements, template BiFunction<struct StringBuilder, char*, struct StringBuilder> merger){
	auto output = struct StringBuilder::new();
	/* for (var element : elements) */{
		/* output  */ = apply(merger, output, element);
	}
	return toString(output);
}
/* private static */struct StringBuilder mergeStatements(struct StringBuilder output, char* mapped){
	return append(output, mapped);
}
/* private static */struct DivideState foldStatementChar(struct DivideState state, char c){
	auto appended = append(state, c);
	if (/* c == ';' && appended.isLevel() */){
		return advance(appended);
	}
	if (/* c == '}' && appended.isShallow() */){
		return exit(advance(appended));
	}
	/* else if (c == ' */{
		/* ' || */ c = /* = '(') {
            return appended.enter() */;
	}
	/* else if (c == '}' || c == ')') */{
		return exit(appended);
	}
	return appended;
}
/* private static */template Option<(struct CompileState, char*)> compileStructSegment(struct CompileState state, char* input){
	return or(state, input, of(List, type(structure("record")), type(structure("class")), type(structure("interface")), type(struct Main::compileMethod), type(struct Main::compileContent)));
}
/* private static */template Option<(struct CompileState, char*)> compileMethod(struct CompileState state, char* stripped){
	if (/* !stripped.endsWith("}") */){
		return template None<struct >::new();
	}
	auto withoutContentEnd = substring(stripped, 0, length(stripped) - length("}"));
	auto contentStart = indexOf(withoutContentEnd, "{");
	if (/* contentStart < 0 */){
		return template None<struct >::new();
	}
	auto beforeContent = strip(substring(withoutContentEnd, 0, contentStart));
	auto right = substring(withoutContentEnd, contentStart + length("{"));
	if (/* !beforeContent.endsWith(")") */){
		return template None<struct >::new();
	}
	auto withoutParamEnd = substring(beforeContent, 0, length(beforeContent) - length(")"));
	auto paramStart = indexOf(withoutParamEnd, "(");
	if (/* paramStart < 0 */){
		return template None<struct >::new();
	}
	auto definitionString = substring(withoutParamEnd, 0, paramStart);
	auto inputParams = substring(withoutParamEnd, paramStart + length("("));
	if (/* !(parseDefinition(state, definitionString) instanceof Some(var definitionTuple)) */){
		return template None<struct >::new();
	}
	auto definition = definitionTuple.right;
	auto maybeParamsTuple = compileValues(definitionTuple.left, inputParams, struct Main::compileParameter);
	if (/* maybeParamsTuple instanceof Some(var paramsTuple) */){
		auto paramsState = paramsTuple.left;
		auto paramsString = paramsTuple.right;
		auto header = generate(definition) + "(" + paramsString + ")";
		if (contains(definition.modifiers, "expect")){
			return template Some<struct >::new((paramsState, header + ";\n"));
		}
		auto maybeStatementsTuple = /*  parseStatements(paramsState.enter(), right, (state1, input1) -> compileFunctionSegment(state1, input1)) */;
		if (/* maybeStatementsTuple instanceof Some(var statementsTuple) */){
			auto statementsState = statementsTuple.left;
			auto statements = statementsTuple.right;
			auto oldStatements = template ArrayList<char*>::new();
			addAll(oldStatements, getLast(frames(statementsState)).statements);
			addAll(oldStatements, statements);
			auto generated = header + "{" + generateStatements(oldStatements) + "\n}\n";
			return template Some<struct >::new((addFunction(exit(statementsState), generated), ""));
		}
	}
	return template None<struct >::new();
}
/* private static */template Option<(struct CompileState, char*)> compileParameter(struct CompileState state2, char* input){
	return /* or(state2, input, List.of(
                type(Main */::compileWhitespace),
                type(Main::compileDefinition),
                type(Main::compileContent)
        ));
}
/* private static */template Option<(struct CompileState, char*)> compileWhitespace(struct CompileState state, char* input){
	if (isBlank(input)){
		return template Some<struct >::new((state, ""));
	}
	return template None<struct >::new();
}
/* private static */template Option<(struct CompileState, char*)> compileFunctionSegment(struct CompileState state, char* input){
	return or(state, input, of(List, type(struct Main::compileWhitespace), type(struct Main::compileStatement), type(struct Main::compileBlock), type(struct Main::compileContent)));
}
/* private static */template Some<(struct CompileState, char*)> compileContent(struct CompileState state1, char* s){
	return /* new Some<>(new Tuple<>(state1, generatePlaceholder(s))) */;
}
/* private static */template Option<(struct CompileState, char*)> compileBlock(struct CompileState state, char* s){
	char* indent = "\n" + repeat("\t", depth(state) - 1);
	if (/* !s.endsWith("}") */){
		return template None<struct >::new();
	}
	auto withoutEnd = substring(s, 0, length(s) - length("}"));
	auto contentStart = indexOf(withoutEnd, "{");
	if (/* contentStart < 0 */){
		return template None<struct >::new();
	}
	auto beforeContent = substring(withoutEnd, 0, contentStart);
	auto content = substring(withoutEnd, contentStart + length("{"));
	struct CompileState state3 = enter(state);
	auto maybeStatements = /* parseStatements(state3, content, Main */::compileFunctionSegment);
	if (/* maybeStatements instanceof Some(var statementsTuple) */){
		auto oldStatements = template ArrayList<char*>::new();
		addAll(oldStatements, getLast(statementsTuple.left.frames).statements);
		addAll(oldStatements, statementsTuple.right);
		auto string = compileBlockHeader(exit(statementsTuple.left), beforeContent);
		return template Some<struct >::new((string.left, indent + string.right + "{" + generateStatements(oldStatements) + indent + "}"));
	}
	return template None<struct >::new();
}
/* private static */template Option<(struct CompileState, char*)> compileStatement(struct CompileState state, char* input){
	auto stripped = strip(input);
	if (endsWith(stripped, ";")){
		auto withoutEnd = substring(stripped, 0, length(stripped) - length(";"));
		auto statements = compileFunctionStatementValue(state, withoutEnd);
		return template Some<struct >::new((statements.left, "\n" + "\t".repeat(state.depth() - 1) + statements.right + ";"));
	}
	return template None<struct >::new();
}
/* private static */(struct CompileState, char*) compileBlockHeader(struct CompileState state, char* input){
	auto stripped = strip(input);
	if (equals(stripped, "else")){
		return (state, "else ");
	}
	if (startsWith(stripped, "if")){
		auto withoutPrefix = strip(substring(stripped, length("if")));
		if (/* withoutPrefix.startsWith("(") && withoutPrefix.endsWith(")") */){
			auto value = substring(withoutPrefix, 1, length(withoutPrefix) - 1);
			auto tuple = compileValueOrPlaceholder(state, value);
			return (tuple.left, "if (" + tuple.right + ")");
		}
	}
	return (state, generatePlaceholder(stripped));
}
auto lambda0(){
	return (state, generatePlaceholder(input));
}
auto lambda1(){
	return compileAssignment(state, input);
}
auto lambda2(auto tuple){
	return (tuple.left, generate(tuple.right));
}
auto lambda3(){
	return map(compileInvokable(state, input), lambda2);
}
/* private static */(struct CompileState, char*) compileFunctionStatementValue(struct CompileState state, char* input){
	return orElseGet(or(or(compileReturn(state, input), lambda3), lambda1), lambda0);
}
/* private static */template Option<(struct CompileState, char*)> compileReturn(struct CompileState state, char* input){
	auto stripped = strip(input);
	if (startsWith(stripped, "return ")){
		auto right = substring(stripped, length("return "));
		auto tuple = compileValueOrPlaceholder(state, right);
		return template Some<struct >::new((tuple.left, "return " + tuple.right));
	}
	return template None<struct >::new();
}
/* private static */template Option<(struct CompileState, char*)> compileAssignment(struct CompileState state, char* input){
	auto valueSeparator = indexOf(input, "=");
	if (/* valueSeparator < 0 */){
		return template None<struct >::new();
	}
	auto left = substring(input, 0, valueSeparator);
	auto right = substring(input, valueSeparator + length("="));
	auto definitionTuple = compileDefinitionOrPlaceholder(state, left);
	auto valueTuple = compileValueOrPlaceholder(definitionTuple.left, right);
	return template Some<struct >::new((valueTuple.left, definitionTuple.right + " = " + valueTuple.right));
}
auto lambda0(){
	return (state, generatePlaceholder(input));
}
/* private static */(struct CompileState, char*) compileDefinitionOrPlaceholder(struct CompileState state, char* input){
	return orElseGet(compileDefinition(state, input), lambda0);
}
auto lambda0(auto tuple){
	return (left(tuple), generate(right(tuple)));
}
/* private static */template Option<(struct CompileState, char*)> compileDefinition(struct CompileState state, char* input){
	return map(parseDefinition(state, input), lambda0);
}
/* private static */template Option<(struct CompileState, struct Definition)> parseDefinition(struct CompileState state, char* input){
	auto stripped = strip(input);
	auto valueSeparator = lastIndexOf(stripped, " ");
	if (valueSeparator >= 0){
		auto beforeName = substring(stripped, 0, valueSeparator);
		auto name = strip(substring(stripped, valueSeparator + length(" ")));
		auto annotationSeparator = lastIndexOf(beforeName, "\n");
		if (/* annotationSeparator < 0 */){
			return definitionWithAnnotations(state, emptyList(Collections), beforeName, name);
		}
		auto annotationsArray = split(strip(substring(beforeName, 0, annotationSeparator)), quote(Pattern, "\n"));
		toList(/* var annotations = Arrays.stream(annotationsArray)
                    .map(String */::strip)
                    .map(slice -> slice.isEmpty() ? "" : slice.substring(1)));
		auto beforeName0 = substring(beforeName, annotationSeparator + length("\n"));
		return /* definitionWithAnnotations(state, annotations, beforeName0, name) */;
	}
	return template None<struct >::new();
}
/* private static */template Option<(struct CompileState, struct Definition)> definitionWithAnnotations(struct CompileState state, template List<char*> annotations, char* withoutAnnotations, char* name){
	auto stripped = strip(withoutAnnotations);
	if (/* findTypeSeparator(stripped) instanceof Some(var slices) */){
		auto beforeType = slices.left;
		auto type = slices.right;
		return definitionWithBeforeType(state, annotations, beforeType, type, name);
	}
	return definitionWithBeforeType(state, annotations, "", stripped, name);
}
/* private static */template Option<(char*, char*)> findTypeSeparator(char* input){
	auto divisions = divideAll(strip(input), struct Main::foldTypeSeparator);
	if (size(divisions) >= 2){
		auto left = subList(divisions, 0, size(divisions) - 1);
		auto joinedLeft = join(String, " ", left);
		return template Some<struct >::new((joinedLeft, getLast(divisions)));
	}
	return template None<struct >::new();
}
/* private static */struct DivideState foldTypeSeparator(struct DivideState state, struct Character c){
	if (/* c == ' ' && state.isLevel() */){
		return advance(state);
	}
	auto appended = append(state, c);
	if (c == '<'){
		return enter(appended);
	}
	if (c == '>'){
		return exit(appended);
	}
	return appended;
}
auto lambda0(auto typeResult){
	auto newAnnotations = template ArrayList<char*>::new();
	auto newModifiers = template ArrayList<char*>::new();
	/* for (var annotation : annotations) */{
		if (equals(annotation, "Actual")){
			add(newModifiers, "expect");
		}
		else {
			add(newAnnotations, annotation);
		}
	}
	return template Some<struct >::new((typeResult.left, struct Definition::new(newAnnotations, newModifiers, beforeType, typeResult.right, name)));
}
/* private static */template Option<(struct CompileState, struct Definition)> definitionWithBeforeType(struct CompileState state, template List<char*> annotations, char* beforeType, char* type, char* name){
	return flatMap(compileType(state, type), lambda0);
}
/* private static */template Option<(struct CompileState, char*)> compileType(struct CompileState state, char* input){
	auto stripped = strip(input);
	if (equals(stripped, "var")){
		return template Some<struct >::new((state, "auto"));
	}
	if (equals(stripped, "char")){
		return template Some<struct >::new((state, "char"));
	}
	if (equals(stripped, "void")){
		return template Some<struct >::new((state, "void"));
	}
	if (equals(stripped, "String")){
		return template Some<struct >::new((state, "char*"));
	}
	if (equals(stripped, "boolean")){
		return template Some<struct >::new((state, "int"));
	}
	if (isSymbol(stripped)){
		return template Some<struct >::new((state, "struct " + stripped));
	}
	if (endsWith(stripped, ">")){
		auto withoutEnd = substring(stripped, 0, length(stripped) - length(">"));
		auto argsStart = indexOf(withoutEnd, "<");
		if (argsStart >= 0){
			auto base = strip(substring(withoutEnd, 0, argsStart));
			auto argsString = substring(withoutEnd, argsStart + length("<"));
			auto maybeArgsTuple = parseValues(state, argsString, struct Main::compileType);
			if (/* maybeArgsTuple instanceof Some(var argsTuple) */){
				auto args = argsTuple.right;
				if (/* base.equals("Tuple") && args.size() >= 2 */){
					auto first = get(args, 0);
					auto second = get(args, 1);
					return template Some<struct >::new((argsTuple.left, "(" + first + ", " + second + ")"));
				}
				return template Some<struct >::new((argsTuple.left, "template " + base + "<" + generateValues(args) + ">"));
			}
		}
	}
	return template Some<struct >::new((state, generatePlaceholder(stripped)));
}
/* private static */template Option<(struct CompileState, struct Value)> compileInvokable(struct CompileState state, char* input){
	auto stripped = strip(input);
	if (/* !stripped.endsWith(")") */){
		return template None<struct >::new();
	}
	auto withoutEnd = substring(stripped, 0, length(stripped) - length(")"));
	auto divisions = divideAll(withoutEnd, struct Main::foldInvocationStart);
	if (/* divisions.size() < 2 */){
		return template None<struct >::new();
	}
	auto joined = join(String, "", subList(divisions, 0, size(divisions) - 1));
	auto callerString = substring(joined, 0, length(joined) - length(")"));
	auto inputArguments = getLast(divisions);
	if (/* parseValues(state, inputArguments, Main */::parseArgument) instanceof Some(var argumentsTuple)){
		auto argumentState = argumentsTuple.left;
		auto oldArguments = /*  argumentsTuple.right
                    .stream()
                    .filter(arg -> !(arg instanceof Whitespace))
                    .toList() */;
		if (startsWith(callerString, "new ")){
			auto withoutPrefix = substring(callerString, length("new "));
			if (/* withoutPrefix.equals("Tuple<>") && oldArguments.size() >= 2 */){
				return template Some<struct >::new((argumentState, struct TupleNode::new(get(oldArguments, 0), get(oldArguments, 1))));
			}
			auto maybeCallerTuple = compileType(argumentState, withoutPrefix);
			if (/* maybeCallerTuple instanceof Some(var callerTuple) */){
				auto invocation = struct Invocation::new(struct MethodAccess::new(callerTuple.right, "new"), oldArguments);
				return template Some<struct >::new((callerTuple.left, invocation));
			}
		}
		if (/* parseValue(argumentState, callerString) instanceof Some(var callerTuple) */){
			auto callerState = callerTuple.left;
			auto oldCaller = callerTuple.right;
			struct Value newCaller = oldCaller;
			auto newArguments = template ArrayList<struct Value>::new();
			if (/* oldCaller instanceof DataAccess(Value parent, var property) */){
				add(newArguments, parent);
				/* newCaller  */ = struct Symbol::new(property);
			}
			addAll(newArguments, oldArguments);
			return template Some<struct >::new((callerState, struct Invocation::new(newCaller, newArguments)));
		}
	}
	return template None<struct >::new();
}
/* private static */template Option<(struct CompileState, struct Value)> parseArgument(struct CompileState state1, char* input1){
	return /* or(state1, input1, List.of(
                type(Main */::parseWhitespace),
                type((state2, input2) -> parseValue(state2, input2))
        ));
}
/* private static */template Option<(struct CompileState, struct Whitespace)> parseWhitespace(struct CompileState state, char* input){
	if (isBlank(input)){
		return template Some<struct >::new((state, struct Whitespace::new()));
	}
	else {
		return template None<struct >::new();
	}
}
auto lambda0(auto tuple){
	return (tuple.left, generateValues(tuple.right));
}
/* private static */template Option<(struct CompileState, char*)> compileValues(struct CompileState state, char* input, template BiFunction<struct CompileState, char*, template Option<(struct CompileState, char*)>> compiler){
	return map(parseValues(state, input, compiler), lambda0);
}
/* private static */char* generateValues(template List<char*> elements){
	return generateAll(elements, struct Main::mergeValues);
}
/* private static <T> */template Option<(struct CompileState, template List<struct T>)> parseValues(struct CompileState state, char* input, template BiFunction<struct CompileState, char*, template Option<(struct CompileState, struct T)>> compiler){
	return parseAll(state, input, struct Main::foldValueChar, compiler);
}
/* private static */struct StringBuilder mergeValues(struct StringBuilder cache, char* element){
	if (isEmpty(cache)){
		return append(cache, element);
	}
	return append(append(cache, ", "), element);
}
/* private static */struct DivideState foldValueChar(struct DivideState state, char c){
	if (/* c == ',' && state.isLevel() */){
		return advance(state);
	}
	auto appended = append(state, c);
	if (c == '-' && appended.peek() == '>'){
		return orElse(popAndAppendToOption(appended), appended);
	}
	if (c == '(' || c == '<'){
		return enter(appended);
	}
	if (c == ')' || c == '>'){
		return exit(appended);
	}
	return appended;
}
auto lambda0(){
	return (state, generatePlaceholder(input));
}
/* private static */(struct CompileState, char*) compileValueOrPlaceholder(struct CompileState state, char* input){
	return orElseGet(compileValue(state, input), lambda0);
}
auto lambda0(auto tuple){
	return (tuple.left, generate(tuple.right));
}
/* private static */template Option<(struct CompileState, char*)> compileValue(struct CompileState state, char* input){
	return map(parseValue(state, input), lambda0);
}
/* private static */template Option<(struct CompileState, struct Value)> parseValue(struct CompileState state, char* input){
	template List<template BiFunction<struct CompileState, char*, template Option<(struct CompileState, struct Value)>>> beforeOperators = of(List, type(struct Main::compileString), type(struct Main::compileChar), type(struct Main::compileLambda));
	template List<template BiFunction<struct CompileState, char*, template Option<(struct CompileState, struct Value)>>> afterOperators = of(List, type(struct Main::compileInvokable), type(struct Main::compileAccess), type(struct Main::parseBooleanValue), type(struct Main::compileSymbolValue), type(struct Main::compileMethodReference), type(struct Main::parseNumber));
	auto rules = template ArrayList<template BiFunction<struct CompileState, char*, template Option<(struct CompileState, struct Value)>>>::new(beforeOperators);
	/* for (var value : Operator.values()) */{
		/* rules.add(type((state1, input1) -> compileOperator(state1, input1, value))) */;
	}
	addAll(rules, afterOperators);
	return or(state, input, rules);
}
/* private static */template Option<(struct CompileState, struct Value)> parseBooleanValue(struct CompileState state, char* input){
	auto stripped = strip(input);
	if (equals(stripped, "false")){
		return template Some<struct >::new((state, struct NumberValue::new("0")));
	}
	if (equals(stripped, "true")){
		return template Some<struct >::new((state, struct NumberValue::new("1")));
	}
	return template None<struct >::new();
}
/* private static */template Option<(struct CompileState, struct Value)> compileChar(struct CompileState state, char* input){
	auto stripped = strip(input);
	if (/* stripped.startsWith("'") && stripped.endsWith("'") && stripped.length() >= 3 */){
		return template Some<struct >::new((state, struct CharValue::new(substring(stripped, 1, length(stripped) - 1))));
	}
	else {
		return template None<struct >::new();
	}
}
/* private static */template Option<(struct CompileState, struct Value)> parseNumber(struct CompileState state, char* input){
	auto stripped = strip(input);
	if (isNumber(stripped)){
		return template Some<struct >::new((state, struct NumberValue::new(stripped)));
	}
	else {
		return template None<struct >::new();
	}
}
/* private static */int isNumber(char* input){
	/* for (var i = 0; i < input.length(); i++) */{
		auto c = charAt(input, i);
		if (isDigit(Character, c)){
			/* continue */;
		}
		return 0;
	}
	return 1;
}
/* private static */template Option<(struct CompileState, struct Value)> compileOperator(struct CompileState state, char* input, struct Operator operator){
	auto index = indexOf(input, operator.representation);
	if (index >= 0){
		auto left = substring(input, 0, index);
		auto right = substring(input, index + length(operator.representation));
		if (/* parseValue(state, left) instanceof Some(var leftTuple) */){
			if (/* parseValue(leftTuple.left, right) instanceof Some(var rightTuple) */){
				auto operation = struct Operation::new(leftTuple.right, operator, rightTuple.right);
				return template Some<struct >::new((rightTuple.left, operation));
			}
		}
	}
	return template None<struct >::new();
}
/* private static <T> */template Option<(struct CompileState, struct T)> or(struct CompileState state, char* input, template List<template BiFunction<struct CompileState, char*, template Option<(struct CompileState, struct T)>>> rules){
	/* for (var rule : rules) */{
		auto applied = apply(rule, state, input);
		if (isPresent(applied)){
			return applied;
		}
	}
	return template None<struct >::new();
}
auto lambda0(auto value){
	return (value.left, value.right);
}
auto lambda1(auto value){
	return (value.left, value.right);
}
/* private static <S, T extends S> */template BiFunction<struct CompileState, char*, template Option<(struct CompileState, struct S)>> type(template BiFunction<struct CompileState, char*, template Option<(struct CompileState, struct T)>> mapper){
	return /* (state, input) -> mapper.apply(state, input).map(value -> new Tuple<>(value.left, value.right)) */;
}
/* private static */template Option<(struct CompileState, struct MethodAccess)> compileMethodReference(struct CompileState state, char* input){
	auto functionSeparator = indexOf(strip(input), "::");
	if (/* functionSeparator < 0 */){
		return template None<struct >::new();
	}
	auto left = substring(strip(input), 0, functionSeparator);
	strip(/* var right = input.strip().substring(functionSeparator + " */::".length()));
	auto maybeLeftTuple = compileType(state, left);
	if (/* maybeLeftTuple instanceof Some(var leftTuple) */){
		return template Some<struct >::new((leftTuple.left, struct MethodAccess::new(leftTuple.right, right)));
	}
	return template None<struct >::new();
}
/* private static */template Option<(struct CompileState, struct Symbol)> compileSymbolValue(struct CompileState state, char* input){
	auto stripped = strip(input);
	if (isSymbol(stripped)){
		return template Some<struct >::new((struct CompileState, struct Symbol)::new(state, struct Symbol::new(stripped)));
	}
	return template None<struct >::new();
}
/* private static */template Option<(struct CompileState, struct DataAccess)> compileAccess(struct CompileState state, char* input){
	auto separator = lastIndexOf(strip(input), ".");
	if (/* separator < 0 */){
		return template None<struct >::new();
	}
	auto parent = substring(strip(input), 0, separator);
	auto child = substring(strip(input), separator + length("."));
	if (/* !isSymbol(child) || !(parseValue(state, parent) instanceof Some(var tuple)) */){
		return template None<struct >::new();
	}
	return template Some<struct >::new((struct CompileState, struct DataAccess)::new(tuple.left, struct DataAccess::new(tuple.right, child)));
}
/* private static */template Option<(struct CompileState, struct StringValue)> compileString(struct CompileState state, char* input){
	auto stripped = strip(input);
	if (/* !stripped.startsWith("\"") || !stripped.endsWith("\"") || stripped.length() < 2 */){
		return template None<struct >::new();
	}
	return template Some<struct >::new((state, struct StringValue::new(substring(stripped, 1, length(stripped) - 1))));
}
auto lambda0(auto result){
			return assembleLambda(result.left, paramNames, result.right);
}
/* private static */template Option<(struct CompileState, struct Symbol)> compileLambda(struct CompileState state, char* input){
	auto arrowIndex = indexOf(input, "->");
	if (arrowIndex >= 0){
		auto beforeArrow = strip(substring(input, 0, arrowIndex));
		auto afterArrow = substring(input, arrowIndex + length("->"));
		/* List<String> paramNames */;
		if (isSymbol(beforeArrow)){
			/* paramNames  */ = singletonList(Collections, beforeArrow);
		}
		/* else if (beforeArrow.equals("()")) */{
			/* paramNames  */ = emptyList(Collections);
		}
		else {
			return template None<struct >::new();
		}
		auto withBraces = strip(afterArrow);
		/* if (withBraces.startsWith(" */{
			@) && withBraces.endsWith("}")) {
auto content = flatMap(/* withBraces.substring(1, withBraces.length() - 1);
                return compileStatements(state, content, Main */::compileFunctionSegment), lambda0);
		}
		else {
			if (/* compileValue(state, afterArrow) instanceof Some(var valueTuple) */){
				return assembleLambda(valueTuple.left, paramNames, "\n\treturn " + valueTuple.right + ";");
			}
		}
	}
	return template None<struct >::new();
}
auto lambda0(auto name){
	return "auto " + name;
}
auto lambda1(auto name){
	return "auto " + name;
}
/* private static */template Option<(struct CompileState, struct Symbol)> assembleLambda(struct CompileState state, template List<char*> paramNames, char* content){
	auto nameTuple = createName(state, "lambda");
	auto generatedName = nameTuple.left;
	auto joinedParams = collect(map(stream(paramNames), lambda1), joining(Collectors, ", "));
	return template Some<struct >::new((addFunction(nameTuple.right, "auto " + generatedName + "(" + joinedParams + "){" + content + "\n}\n"), struct Symbol::new(generatedName)));
}
/* private static */int isSymbol(char* input){
	auto stripped = strip(input);
	/* for (var i = 0; i < stripped.length(); i++) */{
		auto c = charAt(stripped, i);
		if (isLetter(Character, c)){
			/* continue */;
		}
		return 0;
	}
	return 1;
}
/* private static */struct DivideState foldInvocationStart(struct DivideState state, char c){
	auto appended = append(state, c);
	if (c == '('){
		auto entered = enter(appended);
		if (isShallow(appended)){
			return advance(entered);
		}
		else {
			return entered;
		}
	}
	if (c == ')'){
		return exit(appended);
	}
	return appended;
}
/* private static */char* generatePlaceholder(char* stripped){
	return "/* " + stripped + " */";
}
/* 
 */