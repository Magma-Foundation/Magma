/* private interface Result<T, X>  */{/* <R> R match(Function<T, R> whenOk, Function<X, R> whenErr); */
/*  */

};
/* private interface Option<T>  */{/* void ifPresent(Consumer<T> consumer); */
/* T orElseGet(Supplier<T> supplier); */
/* Option<T> or(Supplier<Option<T>> other); */
/* boolean isPresent(); */
/* <R> Option<R> map(Function<T, R> mapper); */
/* <R> Option<R> flatMap(Function<T, Option<R>> mapper); */
/* T orElse(T other); */
/*  */

};
/* private interface Error  */{/* String display(); */
/*  */

};
/* private @interface Actual  */{/*  */

};
/* private interface Value  */{/* String generate(); */
/*  */

};
/* private record IOError(IOException exception) implements Error  */{/*  */

};
/* record None<T>() implements Option<T>  */{/*  */

};
/* private record Some<T>(T value) implements Option<T>  */{/*  */

};
/* private record Ok<T, X>(T value) implements Result<T, X>  */{/*  */

};
/* private record Err<T, X>(X error) implements Result<T, X>  */{/*  */

};
/* private static class DivideState  */{/* private final String input; */
/* private final List<String> segments; */
/* private final int index; */
/* private StringBuilder buffer; */
/* private int depth; */
/*  */

};
/* private record Frame(Map<String, Integer> counters, List<String> statements)  */{/*  */

};
/* private record CompileState(List<String> structs, List<String> functions, List<Frame> frames)  */{/*  */

};
/* private record Tuple<A, B>(A left, B right)  */{/*  */

};
/* private record Definition(
            List<String> annotations,
            List<String> modifiers,
            String beforeType,
            String type,
            String name
    )  */{/*  */

};
/* private record StringValue(String value) implements Value  */{/*  */

};
/* private record Symbol(String value) implements Value  */{/*  */

};
/* private record Invocation(Value caller, List<Value> arguments) implements Value  */{/*  */

};
/* private record DataAccess(Value parent, String child) implements Value  */{/*  */

};
/* private record MethodAccess(String parent, String child) implements Value  */{/*  */

};
/* private record Content(String input) implements Value  */{/*  */

};
/* private record Operation(Value left, Operator operator, Value right) implements Value  */{/*  */

};
/* private static class Whitespace implements Value  */{/*  */

};
/* public class Main  */{/* private enum Operator {
        ADD("+");
        private final String representation;

        Operator(String representation) {
            this.representation = representation;
        }
    } */
/* public static final Path SOURCE = Paths.get(".", "src", "java", "magma", "Main.java"); */
/* public static final Path TARGET = SOURCE.resolveSibling("main.c"); */
expect /* private static */template Option<struct IOError> writeTarget(char* output);
expect /* private static */template Result<char*, struct IOError> readSource();
/*  */

};
@Override
/* public */char* display(){
	auto writer = struct StringWriter::new();
	this.exception.printStackTrace(this.exception, struct PrintWriter::new(writer));
	return writer.toString(writer, );
}
@Override
/* public */void ifPresent(template Consumer<struct T> consumer){
}
@Override
/* public */struct T orElseGet(template Supplier<struct T> supplier){
	return supplier.get(supplier, );
}
@Override
/* public */template Option<struct T> or(template Supplier<template Option<struct T>> other){
	return other.get(other, );
}
@Override
/* public */struct boolean isPresent(){
	return false;
}
@Override
/* public <R> */template Option<struct R> map(/* Function<T */, /* R> */ mapper){
	return template None<struct >::new();
}
@Override
/* public <R> */template Option<struct R> flatMap(/* Function<T */, template Option</* R> */> mapper){
	return template None<struct >::new();
}
@Override
/* public */struct T orElse(struct T other){
	return other;
}
@Override
/* public */void ifPresent(template Consumer<struct T> consumer){
	consumer.accept(consumer, this.value);
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
/* public */struct boolean isPresent(){
	return true;
}
@Override
/* public <R> */template Option<struct R> map(/* Function<T */, /* R> */ mapper){
	return template Some<struct >::new(mapper.apply(mapper, this.value));
}
@Override
/* public <R> */template Option<struct R> flatMap(/* Function<T */, template Option</* R> */> mapper){
	return mapper.apply(mapper, this.value);
}
@Override
/* public */struct T orElse(struct T other){
	return this.value;
}
@Override
/* public <R> */struct R match(/* Function<T */, /* R> */ whenOk, /*  Function<X */, /* R> */ whenErr){
	return whenOk.apply(whenOk, this.value);
}
@Override
/* public <R> */struct R match(/* Function<T */, /* R> */ whenOk, /*  Function<X */, /* R> */ whenErr){
	return whenErr.apply(whenErr, this.error);
}
struct public DivideState(char* input){
	this(input, template ArrayList<struct >::new(), struct StringBuilder::new(), /*  0 */, /*  0 */);
}
struct public DivideState(char* input, template List<char*> segments, struct StringBuilder buffer, struct int index, struct int depth){
	/* this.input  */ = input;
	/* this.segments  */ = segments;
	/* this.index  */ = index;
	/* this.depth  */ = depth;
	/* this.buffer  */ = buffer;
}
/* private */struct DivideState enter(){
	/* this.depth  */ = /*  this.depth + 1 */;
	return this;
}
/* private */struct DivideState exit(){
	/* this.depth  */ = /*  this.depth - 1 */;
	return this;
}
/* private */struct DivideState advance(){
	auto local0 = this.segments(this, );
	add(local0, this.buffer.toString(this.buffer, ));
	/* this.buffer  */ = struct StringBuilder::new();
	return this;
}
/* public */template List<char*> segments(){
	return this.segments;
}
/* private */struct boolean isShallow(){
	return /* this.depth == 1 */;
}
/* public */struct boolean isLevel(){
	return /* this.depth == 0 */;
}
/* public */template Option<struct DivideState> popAndAppendToOption(){
	auto local0 = this.popAndAppendToTuple(this, );
	return map(local0, struct Tuple::right);
}
/* public */template Option</* Tuple<Character */, /* DivideState> */> popAndAppendToTuple(){
	auto local0 = this.pop(this, );
	return map(local0, /* tuple -> {
                return new Tuple<>(tuple.left */, /*  tuple.right.append(tuple.left));
            } */);
}
/* private */struct DivideState append(struct char c){
	this.buffer.append(this.buffer, c);
	return this;
}
/* public */template Option</* Tuple<Character */, /* DivideState> */> pop(){
	if (/* this.index < this.input.length() */){
		auto c = this.input.charAt(this.input, this.index);
		return template Some<struct >::new(/* new Tuple<>(c */, /*  new DivideState(this.input */, this.segments, this.buffer, /*  this.index + 1 */, /*  this.depth)) */);
	}
	/* else */{
		return template None<struct >::new();
	}
}
struct public Frame(){
	this(template HashMap<struct >::new(), template ArrayList<struct >::new());
}
/* public */template Tuple<char*, struct Frame> createName(char* category){
	if (/* !this.counters.containsKey(category) */){
		this.counters.put(this.counters, category, /*  0 */);
	}
	auto oldCounter = this.counters.get(this.counters, category);
	auto name = category + oldCounter;
	auto newCounter = /*  oldCounter + 1 */;
	this.counters.put(this.counters, category, newCounter);
	return template Tuple<struct >::new(name, this);
}
/* public */struct Frame addStatement(char* statement){
	this.statements.add(this.statements, statement);
	return this;
}
struct public CompileState(){
	this(template ArrayList<struct >::new(), template ArrayList<struct >::new(), template ArrayList<struct >::new(Collections.singletonList(Collections, struct Frame::new())));
}
/* public */struct CompileState addFunction(char* generated){
	this.functions.add(this.functions, generated);
	return this;
}
/* public */template Tuple<char*, struct CompileState> createName(char* category){
	auto frame = this.frames.getLast(this.frames, );
	auto nameTuple = frame.createName(frame, category);
	this.frames.set(this.frames, /* this.frames.size() - 1 */, nameTuple.right);
	return template Tuple<struct >::new(nameTuple.left, this);
}
/* public */struct CompileState addStatement(char* statement){
	auto local0 = this.frames.getLast(this.frames, );
	addStatement(local0, statement);
	return this;
}
/* public */struct CompileState enter(){
	this.frames.add(this.frames, struct Frame::new());
	return this;
}
/* public */struct CompileState exit(){
	this.frames.removeLast(this.frames, );
	return this;
}
/* public */struct CompileState addStruct(char* generated){
	this.structs.add(this.structs, generated);
	return this;
}
/* private */char* generate(){
	/* String annotationsStrings */;
	if (this.annotations.isEmpty(this.annotations, )){
		/* annotationsStrings  */ = "";
	}
	/* else */{
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
	auto local0 = /* var joined = this.arguments.stream()
                    .map(Value */::generate);
	collect(local0, Collectors.joining(Collectors, ", "));
	return this.caller.generate(this.caller, ) + "(" + joined + ")";
}
@Override
/* public */char* generate(){
	return this.parent.generate(this.parent, ) + "." + this.child;
}
@Override
/* public */char* generate(){
	auto local0 = /* this.parent() + " */::" + this;
	return child(local0, );
}
@Override
/* public */char* generate(){
	return generatePlaceholder(this.input);
}
@Override
/* public */char* generate(){
	return this.left.generate(this.left, ) + " " + this.operator.representation + " " + this.right.generate(this.left.generate(this.left, ) + " " + this.operator.representation + " " + this.right, );
}
@Override
/* public */char* generate(){
	return "";
}
auto lambda0(auto error){
	return System.err.println(System.err, error.display(error, ));
}
auto lambda1(auto input){
	auto output = compile(input);
	return writeTarget(output);
}
/* public static */void main(){
	auto local0 = readSource();
	auto local1 = match(local0, lambda1, struct Some::new);
	ifPresent(local1, lambda0);
}
/* private static */char* compile(char* input){
	auto state = struct CompileState::new();
	auto tuple = compileStatements(state, input, struct Main::compileRootSegment);
	auto joinedStructs = String.join(String, "", tuple.left.structs);
	auto joinedFunctions = String.join(String, "", tuple.left.functions);
	return joinedStructs + joinedFunctions + tuple.right;
}
/* private static */template Tuple<struct CompileState, char*> compileRootSegment(struct CompileState state, char* input){
	auto local0 = compileStructure(state, input, "class ");
	return orElseGet(local0, /* () -> {
            return new Tuple<>(state */, /*  generatePlaceholder(input));
        } */);
}
/* private static */template Option</* Tuple<CompileState */, /* String> */> compileStructure(struct CompileState state, char* input, char* infix){
	auto stripped = input.strip(input, );
	if (stripped.endsWith(stripped, "}")){
		auto withoutEnd = stripped.substring(stripped, /* 0 */, /*  stripped.length() - "}".length() */);
		auto contentStart = withoutEnd.indexOf(withoutEnd, "{");
		if (/* contentStart >= 0 */){
	auto local0 = contentStart + "{";
	auto local1 = contentStart + "{";
			auto left = withoutEnd.substring(withoutEnd, /* 0 */, contentStart);
			auto right = withoutEnd.substring(withoutEnd, length(local1, ));
			if (/* left.indexOf(infix) >= 0 */){
				auto result = compileStatements(state, right, struct Main::compileStructSegment);
				auto generated = generatePlaceholder(left) + "{" + result.right + "\n};\n";
				return template Some<struct >::new(template Tuple<struct >::new(/* result.left.addStruct(generated */), /*  "") */);
			}
		}
	}
	return template None<struct >::new();
}
/* private static */template Tuple<struct CompileState, char*> compileStatements(struct CompileState state, char* input, /*  BiFunction<CompileState */, /*  String */, /*  Tuple<CompileState */, /* String>> */ mapper){
	auto tuple = parseStatements(state, input, mapper);
	return template Tuple<struct >::new(tuple.left, generateStatements(tuple.right));
}
/* private static */char* generateStatements(template List<char*> elements){
	return generateAll(elements, struct Main::mergeStatements);
}
/* private static */template Tuple<struct CompileState, template List<char*>> parseStatements(struct CompileState state, char* input, /*  BiFunction<CompileState */, /*  String */, /*  Tuple<CompileState */, /* String>> */ mapper){
	return parseAll(state, input, struct Main::foldStatementChar, mapper);
}
/* private static <T> */template Tuple<struct CompileState, template List<struct T>> parseAll(struct CompileState initial, char* input, /* 
            BiFunction<DivideState */, /*  Character */, /* DivideState> */ folder, /* 
            BiFunction<CompileState */, /*  String */, /*  Tuple<CompileState */, /* T>> */ mapper){
	auto segments = divideAll(input, folder);
	auto current = initial;
	auto compiled = template ArrayList<struct T>::new();
	/* for (var segment : segments) */{
		auto mapped = mapper.apply(mapper, current, segment);
		/* current  */ = mapped.left;
		compiled.add(compiled, mapped.right);
	}
	return template Tuple<struct >::new(current, compiled);
}
/* private static */template List<char*> divideAll(char* input, /*  BiFunction<DivideState */, /*  Character */, /* DivideState> */ folder){
	auto current = struct DivideState::new(input);
	/* while (true) */{
	auto local0 = foldSingleQuotes(popped.right, popped.left);
	auto local1 = or(local0, /* () -> foldDoubleQuotes(popped.right */, /*  popped.left) */);
		auto maybePopped = current.pop(current, );
		if (/* !(maybePopped instanceof Some(var popped)) */){
			/* break */;
		}
		/* current  */ = orElseGet(local1, /* () -> folder.apply(popped.right */, /*  popped.left) */);
	}
	return current.advance(current, ).segments;
}
/* private static */template Option<struct DivideState> foldDoubleQuotes(struct DivideState state, struct char maybeDoubleQuotes){
	if (/* maybeDoubleQuotes != '\"' */){
		return template None<struct >::new();
	}
	auto current = state.append(state, maybeDoubleQuotes);
	/* while (true) */{
		if (/* !(current.popAndAppendToTuple() instanceof Some(var popped)) */){
			/* break */;
		}
		auto next = popped.left;
		/* current  */ = popped.right;
		if (/* next == '\\' */){
	auto local0 = current.popAndAppendToOption(current, );
			/* current  */ = orElse(local0, current);
		}
		if (/* next == '\"' */){
			/* break */;
		}
	}
	return template Some<struct >::new(current);
}
/* private static */template Option<struct DivideState> foldSingleQuotes(struct DivideState state, struct char c){
	auto local0 = appended.popAndAppendToTuple(appended, );
	auto local1 = flatMap(local0, /* popped -> popped.left == '\\' ? popped.right.popAndAppendToOption() : new Some<>(popped.right) */);
	if (/* c != '\'' */){
		return template None<struct >::new();
	}
	auto appended = state.append(state, c);
	return flatMap(local1, struct DivideState::popAndAppendToOption);
}
/* private static */char* generateAll(template List<char*> elements, /*  BiFunction<StringBuilder */, /*  String */, /* StringBuilder> */ merger){
	auto output = struct StringBuilder::new();
	/* for (var element : elements) */{
		/* output  */ = merger.apply(merger, output, element);
	}
	return output.toString(output, );
}
/* private static */struct StringBuilder mergeStatements(struct StringBuilder output, char* mapped){
	return output.append(output, mapped);
}
/* private static */struct DivideState foldStatementChar(struct DivideState state, struct char c){
	auto appended = state.append(state, c);
	if (/* c == ';' && appended.isLevel() */){
		return appended.advance(appended, );
	}
	if (/* c == '}' && appended.isShallow() */){
	auto local0 = appended.advance(appended, );
		return exit(local0, );
	}
	/* else if (c == ' */{
		/* ' || */ c = /* = '(') {
            return appended.enter() */;
	}
	/* else if (c == '}' || c == ')') */{
		return appended.exit(appended, );
	}
	return appended;
}
/* private static */template Tuple<struct CompileState, char*> compileStructSegment(struct CompileState state, char* input){
	auto local0 = or(state, input, /*  List.of(
                type((state0 */, /*  input0) -> compileStructure(state0 */, /*  input0 */, /*  "record")) */, /* 
                type((state0 */, /*  input0) -> compileStructure(state0 */, /*  input0 */, /*  "class")) */, /* 
                type((state0 */, /*  input0) -> compileStructure(state0 */, /*  input0 */, /*  "interface")) */, type(struct Main::compileMethod)));
	auto stripped = input.strip(input, );
	return orElseGet(local0, /* () -> new Tuple<>(state */, generatePlaceholder(/* stripped) + "\n" */));
}
/* private static */template Option</* Tuple<CompileState */, /* String> */> compileMethod(struct CompileState state, char* stripped){
	auto local0 = withoutContentEnd.substring(withoutContentEnd, /* 0 */, contentStart);
	auto local1 = contentStart + "{";
	auto local2 = contentStart + "{";
	auto local3 = paramStart + "(";
	auto local4 = paramStart + "(";
	auto local5 = statementsState.frames(statementsState, );
	if (/* !stripped.endsWith("}") */){
		return template None<struct >::new();
	}
	auto withoutContentEnd = stripped.substring(stripped, /* 0 */, /*  stripped.length() - "}".length() */);
	auto contentStart = withoutContentEnd.indexOf(withoutContentEnd, "{");
	if (/* contentStart < 0 */){
		return template None<struct >::new();
	}
	auto beforeContent = strip(local0, );
	auto right = withoutContentEnd.substring(withoutContentEnd, length(local2, ));
	if (/* !beforeContent.endsWith(")") */){
		return template None<struct >::new();
	}
	auto withoutParamEnd = beforeContent.substring(beforeContent, /* 0 */, /*  beforeContent.length() - ")".length() */);
	auto paramStart = withoutParamEnd.indexOf(withoutParamEnd, "(");
	if (/* paramStart < 0 */){
		return template None<struct >::new();
	}
	auto definitionString = withoutParamEnd.substring(withoutParamEnd, /* 0 */, paramStart);
	auto inputParams = withoutParamEnd.substring(withoutParamEnd, length(local4, ));
	if (/* !(parseDefinition(state, definitionString) instanceof Some(var definitionTuple)) */){
		return template None<struct >::new();
	}
	auto definition = definitionTuple.right;
	auto paramsTuple = compileValues(definitionTuple.left, inputParams, /*  (state2 */, /*  input) -> {
            return compileWhitespace(state2 */, /*  input).orElseGet(() -> {
                return compileDefinitionOrPlaceholder(state2 */, /*  input);
            });
        } */);
	auto paramsState = paramsTuple.left;
	auto paramsString = paramsTuple.right;
	auto header = definition.generate(definition, ) + "(" + paramsString + ")";
	if (definition.modifiers.contains(definition.modifiers, "expect")){
		return template Some<struct >::new(/* new Tuple<>(paramsState */, /*  header + ";\n") */);
	}
	auto statementsTuple = parseStatements(paramsState.enter(paramsState, ), right, /*  (state1 */, /*  input1) -> compileFunctionSegment(state1 */, /*  input1 */, /*  1) */);
	auto statementsState = statementsTuple.left;
	auto statements = statementsTuple.right;
	auto oldStatements = template ArrayList<char*>::new();
	oldStatements.addAll(oldStatements, getLast(local5, ).statements);
	oldStatements.addAll(oldStatements, statements);
	auto generated = header + "{" + generateStatements(oldStatements) + "\n}\n";
	return template Some<struct >::new(template Tuple<struct >::new(/* statementsState.exit().addFunction(generated */), /*  "") */);
}
/* private static */template Option</* Tuple<CompileState */, /* String> */> compileWhitespace(struct CompileState state, char* input){
	if (input.isBlank(input, )){
		return template Some<struct >::new(/* new Tuple<>(state */, /*  "") */);
	}
	return template None<struct >::new();
}
/* private static */template Tuple<struct CompileState, char*> compileFunctionSegment(struct CompileState state, char* input, struct int depth){
	auto local0 = "\n" + "\t";
	auto local1 = compileBlock(state, depth, stripped, indent);
	auto stripped = input.strip(input, );
	if (stripped.isEmpty(stripped, )){
		return template Tuple<struct >::new(state, "");
	}
	auto indent = repeat(local0, depth);
	if (stripped.endsWith(stripped, ";")){
		auto withoutEnd = stripped.substring(stripped, /* 0 */, /*  stripped.length() - ";".length() */);
		auto statements = compileFunctionStatementValue(withoutEnd, state, depth);
		return template Tuple<struct >::new(statements.left, indent + statements.right + ";");
	}
	return orElseGet(local1, /* () -> new Tuple<>(state */, generatePlaceholder(/* stripped) */));
}
/* private static */template Option</* Tuple<CompileState */, /* String> */> compileBlock(struct CompileState state, struct int depth, char* stripped, char* indent){
	auto local0 = contentStart + "{";
	auto local1 = contentStart + "{";
	if (/* !stripped.endsWith("}") */){
		return template None<struct >::new();
	}
	auto withoutEnd = stripped.substring(stripped, /* 0 */, /*  stripped.length() - "}".length() */);
	auto contentStart = withoutEnd.indexOf(withoutEnd, "{");
	if (/* contentStart < 0 */){
		return template None<struct >::new();
	}
	auto beforeContent = withoutEnd.substring(withoutEnd, /* 0 */, contentStart);
	auto content = withoutEnd.substring(withoutEnd, length(local1, ));
	struct CompileState state2 = state.enter(state, );
	auto tuple = parseStatements(/* state2 */, content, /*  (state1 */, /*  input1) -> compileFunctionSegment(state1 */, /*  input1 */, /*  depth + 1) */);
	auto oldStatements = template ArrayList<char*>::new();
	oldStatements.addAll(oldStatements, tuple.left.frames.getLast(tuple.left.frames, ).statements);
	oldStatements.addAll(oldStatements, tuple.right);
	auto string = compileBlockHeader(tuple.left.exit(tuple.left, ), beforeContent, depth);
	return template Some<struct >::new(/* new Tuple<>(string.left */, indent + string.right + "{" + generateStatements(/* oldStatements) + indent + "}" */));
}
/* private static */template Tuple<struct CompileState, char*> compileBlockHeader(struct CompileState state, char* input, struct int depth){
	auto stripped = input.strip(input, );
	if (stripped.startsWith(stripped, "if")){
	auto local0 = "if";
	auto local1 = "if";
	auto local2 = stripped.substring(stripped, length(local1, ));
		auto withoutPrefix = strip(local2, );
		if (/* withoutPrefix.startsWith("(") && withoutPrefix.endsWith(")") */){
			auto value = withoutPrefix.substring(withoutPrefix, /* 1 */, /*  withoutPrefix.length() - 1 */);
			auto tuple = compileValueOrPlaceholder(state, value, depth);
			return template Tuple<struct >::new(tuple.left, "if (" + tuple.right + ")");
		}
	}
	return template Tuple<struct >::new(state, generatePlaceholder(stripped));
}
/* private static */template Tuple<struct CompileState, char*> compileFunctionStatementValue(char* input, struct CompileState state, struct int depth){
	auto local0 = compileReturn(state, input, depth);
	auto local1 = or(local0, /* () -> compileInvokable(state */, input, /*  depth).map(tuple -> new Tuple<>(tuple.left */, tuple.right.generate(tuple.right, /* )) */));
	auto local2 = or(local1, /* () -> compileAssignment(state */, input, /*  depth) */);
	return orElseGet(local2, /* () -> new Tuple<>(state */, generatePlaceholder(/* input) */));
}
/* private static */template Option</* Tuple<CompileState */, /* String> */> compileReturn(struct CompileState state, char* input, struct int depth){
	auto stripped = input.strip(input, );
	if (stripped.startsWith(stripped, "return ")){
	auto local0 = "return ";
	auto local1 = "return ";
		auto right = stripped.substring(stripped, length(local1, ));
		auto tuple = compileValueOrPlaceholder(state, right, depth);
		return template Some<struct >::new(/* new Tuple<>(tuple.left */, /*  "return " + tuple.right) */);
	}
	return template None<struct >::new();
}
/* private static */template Option</* Tuple<CompileState */, /* String> */> compileAssignment(struct CompileState state, char* input, struct int depth){
	auto local0 = valueSeparator + "=";
	auto local1 = valueSeparator + "=";
	auto valueSeparator = input.indexOf(input, "=");
	if (/* valueSeparator < 0 */){
		return template None<struct >::new();
	}
	auto left = input.substring(input, /* 0 */, valueSeparator);
	auto right = input.substring(input, length(local1, ));
	auto definitionTuple = compileDefinitionOrPlaceholder(state, left);
	auto valueTuple = compileValueOrPlaceholder(definitionTuple.left, right, depth);
	return template Some<struct >::new(/* new Tuple<>(valueTuple.left */, /*  definitionTuple.right + " = " + valueTuple.right) */);
}
/* private static */template Tuple<struct CompileState, char*> compileDefinitionOrPlaceholder(struct CompileState state, char* input){
	auto local0 = compileDefinition(state, input);
	return orElseGet(local0, /* () -> new Tuple<>(state */, generatePlaceholder(/* input) */));
}
auto lambda0(auto tuple){
	return template Tuple<struct >::new(/* tuple.left( */);
}
/* private static */template Option</* Tuple<CompileState */, /* String> */> compileDefinition(struct CompileState state, char* input){
	auto local0 = tuple.right(tuple, );
	auto local1 = parseDefinition(state, input);
	return map(local1, lambda0, generate(local0, /* ) */));
}
/* private static */template Option</* Tuple<CompileState */, /* Definition> */> parseDefinition(struct CompileState state, char* input){
	auto stripped = input.strip(input, );
	auto valueSeparator = stripped.lastIndexOf(stripped, " ");
	if (/* valueSeparator >= 0 */){
	auto local0 = valueSeparator + " ";
	auto local1 = valueSeparator + " ";
	auto local2 = stripped.substring(stripped, length(local1, ));
	auto local3 = beforeName.substring(beforeName, /* 0 */, annotationSeparator);
	auto local4 = strip(local3, );
	auto local5 = /* var annotations = Arrays.stream(annotationsArray)
                    .map(String */::strip);
	auto local6 = map(local5, /* slice -> slice.isEmpty() ? "" : slice.substring(1) */);
	auto local7 = annotationSeparator + "\n";
	auto local8 = annotationSeparator + "\n";
		auto beforeName = stripped.substring(stripped, /* 0 */, valueSeparator);
		auto name = strip(local2, );
		auto annotationSeparator = beforeName.lastIndexOf(beforeName, "\n");
		if (/* annotationSeparator < 0 */){
			return definitionWithAnnotations(state, Collections.emptyList(Collections, ), beforeName, name);
		}
		auto annotationsArray = split(local4, Pattern.quote(Pattern, "\n"));
		toList(local6, );
		auto beforeName0 = beforeName.substring(beforeName, length(local8, ));
		return definitionWithAnnotations(state, annotations, /*  beforeName0 */, name);
	}
	return template None<struct >::new();
}
/* private static */template Option</* Tuple<CompileState */, /* Definition> */> definitionWithAnnotations(struct CompileState state, template List<char*> annotations, char* withoutAnnotations, char* name){
	auto stripped = withoutAnnotations.strip(withoutAnnotations, );
	if (/* findTypeSeparator(stripped) instanceof Some(var slices) */){
		auto beforeType = slices.left;
		auto type = slices.right;
		return definitionWithBeforeType(state, annotations, beforeType, type, name);
	}
	return definitionWithBeforeType(state, annotations, "", stripped, name);
}
/* private static */template Option</* Tuple<String */, /* String> */> findTypeSeparator(char* input){
	auto divisions = divideAll(input.strip(input, ), struct Main::foldTypeSeparator);
	if (/* divisions.size() >= 2 */){
		auto left = divisions.subList(divisions, /* 0 */, /*  divisions.size() - 1 */);
		auto joinedLeft = String.join(String, " ", left);
		return template Some<struct >::new(/* new Tuple<>(joinedLeft */, divisions.getLast(divisions, /* ) */));
	}
	return template None<struct >::new();
}
/* private static */struct DivideState foldTypeSeparator(struct DivideState state, struct Character c){
	if (/* c == ' ' && state.isLevel() */){
		return state.advance(state, );
	}
	auto appended = state.append(state, c);
	if (/* c == '<' */){
		return appended.enter(appended, );
	}
	if (/* c == '>' */){
		return appended.exit(appended, );
	}
	return appended;
}
/* private static */template Some</* Tuple<CompileState */, /* Definition> */> definitionWithBeforeType(struct CompileState state, template List<char*> annotations, char* beforeType, char* type, char* name){
	auto typeResult = compileType(state, type);
	auto newAnnotations = template ArrayList<char*>::new();
	auto newModifiers = template ArrayList<char*>::new();
	/* for (var annotation : annotations) */{
		if (annotation.equals(annotation, "Actual")){
			newModifiers.add(newModifiers, "expect");
		}
		/* else */{
			newAnnotations.add(newAnnotations, annotation);
		}
	}
	return template Some<struct >::new(/* new Tuple<>(typeResult.left */, /*  new Definition(newAnnotations */, newModifiers, beforeType, typeResult.right, /*  name)) */);
}
/* private static */template Tuple<struct CompileState, char*> compileType(struct CompileState state, char* input){
	auto stripped = input.strip(input, );
	if (stripped.equals(stripped, "var")){
		return template Tuple<struct >::new(state, "auto");
	}
	if (stripped.equals(stripped, "void")){
		return template Tuple<struct >::new(state, "void");
	}
	if (stripped.equals(stripped, "String")){
		return template Tuple<struct >::new(state, "char*");
	}
	if (isSymbol(stripped)){
		return template Tuple<struct >::new(state, "struct " + stripped);
	}
	if (stripped.endsWith(stripped, ">")){
		auto withoutEnd = stripped.substring(stripped, /* 0 */, /*  stripped.length() - ">".length() */);
		auto argsStart = withoutEnd.indexOf(withoutEnd, "<");
		if (/* argsStart >= 0 */){
	auto local0 = argsStart + "<";
	auto local1 = argsStart + "<";
			auto base = withoutEnd.substring(withoutEnd, /* 0 */, argsStart);
			auto args = withoutEnd.substring(withoutEnd, length(local1, ));
			auto newArgs = compileValues(state, args, struct Main::compileType);
			return template Tuple<struct >::new(newArgs.left, "template " + base + "<" + newArgs.right + ">");
		}
	}
	return template Tuple<struct >::new(state, generatePlaceholder(stripped));
}
/* private static */template Option</* Tuple<CompileState */, /* Invocation> */> compileInvokable(struct CompileState state, char* input, struct int depth){
	auto stripped = input.strip(input, );
	if (/* !stripped.endsWith(")") */){
		return template None<struct >::new();
	}
	auto withoutEnd = stripped.substring(stripped, /* 0 */, /*  stripped.length() - ")".length() */);
	auto divisions = divideAll(withoutEnd, struct Main::foldInvocationStart);
	if (/* divisions.size() < 2 */){
		return template None<struct >::new();
	}
	auto joined = String.join(String, "", /*  divisions.subList(0 */, divisions.size(divisions, /* ) - 1 */));
	auto callerString = joined.substring(joined, /* 0 */, /*  joined.length() - ")".length() */);
	auto inputArguments = divisions.getLast(divisions, );
	auto argumentsTuple = parseValues(state, inputArguments, /*  (state1 */, /*  input1) -> {
            return parseArgument(depth */, /*  state1 */, /*  input1);
        } */);
	auto argumentState = argumentsTuple.left;
	auto oldArguments = argumentsTuple.right;
	if (callerString.startsWith(callerString, "new ")){
	auto local0 = "new ";
	auto local1 = "new ";
		auto withoutPrefix = callerString.substring(callerString, length(local1, ));
		auto callerTuple = compileType(argumentState, withoutPrefix);
		return template Some<struct >::new(/* new Tuple<>(callerTuple.left */, /*  new Invocation(new MethodAccess(callerTuple.right */, /*  "new") */, /*  oldArguments)) */);
	}
	if (/* parseValue(argumentState, callerString, depth) instanceof Some(var callerTuple) */){
		auto callerState = callerTuple.left;
		auto oldCaller = callerTuple.right;
		auto nextState = callerState;
		struct Value newCaller = oldCaller;
		auto newArguments = template ArrayList<struct Value>::new();
		if (/* oldCaller instanceof DataAccess(Value parent, var property) */){
			/* Value thisArgument */;
			if (/* parent instanceof Symbol symbol */){
				/* thisArgument  */ = symbol;
			}
			/* else if (parent instanceof DataAccess access) */{
				/* thisArgument  */ = access;
			}
			/* else */{
				auto localTuple = nextState.createName(nextState, "local");
				/* thisArgument  */ = struct Symbol::new(localTuple.left);
				/* newCaller  */ = struct Symbol::new(property);
				/* nextState  */ = localTuple.right.addStatement(localTuple.right, "\n\tauto " + localTuple.left + " = " + parent.generate() + ";");
			}
			newArguments.add(newArguments, thisArgument);
		}
		newArguments.addAll(newArguments, oldArguments);
		return template Some<struct >::new(/* new Tuple<>(nextState */, /*  new Invocation(newCaller */, /*  newArguments)) */);
	}
	return template None<struct >::new();
}
/* private static */template Tuple<struct CompileState, struct Value> parseArgument(struct int depth, struct CompileState state1, char* input1){
	auto local0 = or(/* state1 */, /*  input1 */, List.of(List, /* type(Main */::parseWhitespace), /* 
                type((state2 */, /*  input2) -> parseValue(state2 */, /*  input2 */, /*  depth))
        ) */);
	return orElseGet(local0, /* () -> new Tuple<>(state1 */, struct Content::new(/* input1) */));
}
/* private static */template Option</* Tuple<CompileState */, /* Whitespace> */> parseWhitespace(struct CompileState state, char* input){
	if (input.isBlank(input, )){
		return template Some<struct >::new(/* new Tuple<>(state */, struct Whitespace::new(/* ) */));
	}
	/* else */{
		return template None<struct >::new();
	}
}
/* private static */template Tuple<struct CompileState, char*> compileValues(struct CompileState state, char* input, /*  BiFunction<CompileState */, /*  String */, /*  Tuple<CompileState */, /* String>> */ compiler){
	auto tuple = parseValues(state, input, compiler);
	return template Tuple<struct >::new(tuple.left, generateValues(tuple.right));
}
/* private static */char* generateValues(template List<char*> elements){
	return generateAll(elements, struct Main::mergeValues);
}
/* private static <T> */template Tuple<struct CompileState, template List<struct T>> parseValues(struct CompileState state, char* input, /* 
            BiFunction<CompileState */, /*  String */, /*  Tuple<CompileState */, /* T>> */ compiler){
	return parseAll(state, input, struct Main::foldValueChar, compiler);
}
/* private static */struct StringBuilder mergeValues(struct StringBuilder cache, char* element){
	auto local0 = cache.append(cache, ", ");
	if (cache.isEmpty(cache, )){
		return cache.append(cache, element);
	}
	return append(local0, element);
}
/* private static */struct DivideState foldValueChar(struct DivideState state, struct char c){
	if (/* c == ',' && state.isLevel() */){
		return state.advance(state, );
	}
	return state.append(state, c);
}
/* private static */template Tuple<struct CompileState, char*> compileValueOrPlaceholder(struct CompileState state, char* input, struct int depth){
	auto local0 = compileValue(state, input, depth);
	return orElseGet(local0, /* () -> {
            return new Tuple<>(state */, /*  generatePlaceholder(input));
        } */);
}
/* private static */template Option</* Tuple<CompileState */, /* String> */> compileValue(struct CompileState state, char* input, struct int depth){
	auto local0 = parseValue(state, input, depth);
	return map(local0, /* tuple -> new Tuple<>(tuple.left */, tuple.right.generate(tuple.right, /* ) */));
}
/* private static */template Option</* Tuple<CompileState */, /* Value> */> parseValue(struct CompileState state, char* input, struct int depth){
	return or(state, input, List.of(List, /* type(Main */::compileString), /* 
                type((state0 */, /*  input0) -> compileLambda(state0 */, /*  input0 */, /*  depth)) */, /* 
                type((state0 */, /*  input0) -> compileInvokable(state0 */, /*  input0 */, /*  depth)) */, /* 
                type((state0 */, /*  input0) -> compileAccess(state0 */, /*  input0 */, /*  depth)) */, type(struct Main::compileSymbolValue), type(struct Main::compileMethodReference), /* 
                type((state1 */, /*  input1) -> compileOperator(state1 */, /*  input1 */, /*  depth))
        ) */);
}
/* private static */template Option</* Tuple<CompileState */, /* Value> */> compileOperator(struct CompileState state, char* input, struct int depth){
	auto index = input.indexOf(input, "+");
	if (/* index >= 0 */){
	auto local0 = index + "+";
	auto local1 = index + "+";
		auto left = input.substring(input, /* 0 */, index);
		auto right = input.substring(input, length(local1, ));
		if (/* parseValue(state, left, depth) instanceof Some(var leftTuple) */){
			if (/* parseValue(leftTuple.left, right, depth) instanceof Some(var rightTuple) */){
				auto operation = struct Operation::new(leftTuple.right, Operator.ADD, rightTuple.right);
				return template Some<struct >::new(/* new Tuple<>(rightTuple.left */, /*  operation) */);
			}
		}
	}
	return template None<struct >::new();
}
/* private static <T> */template Option</* Tuple<CompileState */, /* T> */> or(struct CompileState state, char* input, /* 
            List<BiFunction<CompileState */, /*  String */, /*  Option<Tuple<CompileState */, /* T>>>> */ rules){
	/* for (var rule : rules) */{
		auto applied = rule.apply(rule, state, input);
		if (applied.isPresent(applied, )){
			return applied;
		}
	}
	return template None<struct >::new();
}
/* private static <S, T extends S> */template BiFunction<struct CompileState, char*, /* Option<Tuple<CompileState */, /* S>> */> type(/* BiFunction<CompileState */, /*  String */, /*  Option<Tuple<CompileState */, /* T>>> */ mapper){
	return /* (state, input) -> mapper.apply(state, input).map(value -> new Tuple<>(value.left, value.right)) */;
}
/* private static */template Option</* Tuple<CompileState */, /* MethodAccess> */> compileMethodReference(struct CompileState state, char* input){
	auto local0 = input.strip(input, );
	auto local1 = input.strip(input, );
	auto local2 = /* functionSeparator + " */::";
	auto local3 = /* var right = input.strip().substring(functionSeparator + " */::".length());
	auto functionSeparator = indexOf(local0, "::");
	if (/* functionSeparator < 0 */){
		return template None<struct >::new();
	}
	auto left = substring(local1, /* 0 */, functionSeparator);
	strip(local3, );
	auto leftTuple = compileType(state, left);
	return template Some<struct >::new(/* new Tuple<>(leftTuple.left */, /*  new MethodAccess(leftTuple.right */, /*  right)) */);
}
/* private static */template Option</* Tuple<CompileState */, /* Symbol> */> compileSymbolValue(struct CompileState state, char* input){
	auto stripped = input.strip(input, );
	if (isSymbol(stripped)){
		return template Some<struct >::new(/* new Tuple<CompileState */, /*  Symbol>(state */, struct Symbol::new(/* stripped) */));
	}
	return template None<struct >::new();
}
/* private static */template Option</* Tuple<CompileState */, /* DataAccess> */> compileAccess(struct CompileState state, char* input, struct int depth){
	auto local0 = input.strip(input, );
	auto local1 = input.strip(input, );
	auto local2 = separator + ".";
	auto local3 = separator + ".";
	auto local4 = input.strip(input, );
	auto separator = lastIndexOf(local0, ".");
	if (/* separator < 0 */){
		return template None<struct >::new();
	}
	auto parent = substring(local1, /* 0 */, separator);
	auto child = substring(local4, length(local3, ));
	if (/* !isSymbol(child) || !(parseValue(state, parent, depth) instanceof Some(var tuple)) */){
		return template None<struct >::new();
	}
	return template Some<struct >::new(/* new Tuple<CompileState */, /*  DataAccess>(tuple.left */, /*  new DataAccess(tuple.right */, /*  child)) */);
}
/* private static */template Option</* Tuple<CompileState */, /* StringValue> */> compileString(struct CompileState state, char* input){
	auto stripped = input.strip(input, );
	if (/* !stripped.startsWith("\"") || !stripped.endsWith("\"") */){
		return template None<struct >::new();
	}
	return template Some<struct >::new(/* new Tuple<>(state */, /*  new StringValue(stripped.substring(1 */, stripped.length(stripped, /* ) - 1)) */));
}
/* private static */template Option</* Tuple<CompileState */, /* Symbol> */> compileLambda(struct CompileState state, char* input, struct int depth){
	auto arrowIndex = input.indexOf(input, "->");
	if (/* arrowIndex >= 0 */){
	auto local0 = input.substring(input, /* 0 */, arrowIndex);
	auto local1 = arrowIndex + "->";
	auto local2 = arrowIndex + "->";
		auto beforeArrow = strip(local0, );
		auto afterArrow = input.substring(input, length(local2, ));
		if (isSymbol(beforeArrow)){
			auto withBraces = afterArrow.strip(afterArrow, );
			/* if (withBraces.startsWith(" */{
				@) && withBraces.endsWith("}")) {
auto content = /*  withBraces.substring(1, withBraces.length() - 1);
                    var result = compileStatements(state, content, (state1, input1) -> compileFunctionSegment(state1, input1, depth));
                    return assembleLambda(result.left, beforeArrow, result.right) */;
			}
			/* else */{
				if (/* compileValue(state, afterArrow, depth) instanceof Some(var valueTuple) */){
					return assembleLambda(valueTuple.left, beforeArrow, "\n\treturn " + valueTuple.right + ";");
				}
			}
		}
	}
	return template None<struct >::new();
}
/* private static */template Option</* Tuple<CompileState */, /* Symbol> */> assembleLambda(struct CompileState state, char* beforeArrow, char* content){
	auto nameTuple = state.createName(state, "lambda");
	auto name = nameTuple.left;
	return template Some<struct >::new(template Tuple<struct >::new(/* nameTuple.right.addFunction("auto " + name + "(auto " + beforeArrow + "){" + content + "\n}\n" */), struct Symbol::new(/* name) */));
}
/* private static */struct boolean isSymbol(char* input){
	auto stripped = input.strip(input, );
	/* for (var i = 0; i < stripped.length(); i++) */{
		auto c = stripped.charAt(stripped, i);
		if (Character.isLetter(Character, c)){
			/* continue */;
		}
		return false;
	}
	return true;
}
/* private static */struct DivideState foldInvocationStart(struct DivideState state, struct char c){
	auto appended = state.append(state, c);
	if (/* c == '(' */){
		auto entered = appended.enter(appended, );
		if (appended.isShallow(appended, )){
			return entered.advance(entered, );
		}
		/* else */{
			return entered;
		}
	}
	if (/* c == ')' */){
		return appended.exit(appended, );
	}
	return appended;
}
/* private static */char* generatePlaceholder(char* stripped){
	return "/* " + stripped + " */";
}
/* package magma; *//* 

import java.io.IOException; *//* 
import java.io.PrintWriter; *//* 
import java.io.StringWriter; *//* 
import java.nio.file.Files; *//* 
import java.nio.file.Path; *//* 
import java.nio.file.Paths; *//* 
import java.util.ArrayList; *//* 
import java.util.Arrays; *//* 
import java.util.Collections; *//* 
import java.util.HashMap; *//* 
import java.util.List; *//* 
import java.util.Map; *//* 
import java.util.function.BiFunction; *//* 
import java.util.function.Consumer; *//* 
import java.util.function.Function; *//* 
import java.util.function.Supplier; *//* 
import java.util.regex.Pattern; *//* 
import java.util.stream.Collectors; *//* 
 */