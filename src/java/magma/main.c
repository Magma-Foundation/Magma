#include "./java/util/Deque"
#include "./java/util/LinkedList"
#include "./java/util/Optional"
#include "./java/util/function/BiFunction"
#include "./java/util/function/Consumer"
#include "./java/util/function/Function"
#include "./java/util/function/Predicate"
#include "./java/util/regex/Pattern"
#include "./java/util/stream/Collectors"
#include "./java/util/stream/IntStream"
struct List_<T> {
List_<struct T> add(struct T element);void addAll(List_<struct T> elements);Iterator<struct T> iter();Optional<Tuple<struct T, List_<struct T>>> popFirst();};
struct Iterator<T> {
<R> R fold(struct R initial, BiFunction<struct R, struct T, struct R> folder);<R> Iterator<R> map(Function<struct T, struct R> mapper);<C> C collect(Collector<struct T, struct C> collector);int anyMatch(Predicate<struct T> predicate);void forEach(Consumer<struct T> consumer);Iterator<struct T> filter(Predicate<struct T> predicate);int allMatch(Predicate<struct T> predicate);Iterator<struct T> concat(Iterator<struct T> other);Optional<struct T> next();};
struct Head<T> {
Optional<struct T> next();};
struct Collector<T, C> {
struct C createInitial();struct C fold(struct C current, struct T element);};
struct Result<T, X> {
<R> R match(Function<struct T, struct R> whenOk, Function<struct X, struct R> whenErr);};
struct IOError {
struct String display();};
struct Path_ {
struct Path_ resolveSibling(struct String sibling);List_<struct String> listNames();};
struct Err<T, X>(X error) implements Result<T, X> {
};
struct Ok<T, X>(T value) implements Result<T, X> {
};
struct State {
	Deque<char> queue;
	List_<struct String> segments;
	struct StringBuilder buffer;
	int depth;
};
struct Tuple<A, B>(A left, B right) {
};
struct Joiner(String delimiter) implements Collector<String, Optional<String>> {
};
struct RangeHead implements Head<Integer> {
	int length;
};
struct HeadedIterator<T>(Head<T> head) implements Iterator<T> {
};
struct EmptyHead<T> implements Head<T> {
};
struct Iterators {
};
struct ListCollector<T> implements Collector<T, List_<T>> {
};
struct SingleHead<T> implements Head<T> {
	struct T value;
};
struct ", Impl.emptyList());
        if (maybeClass.isPresent()) {
	struct return maybeClass;
/* 
        }

        return generatePlaceholder(input);
     */};
struct Main {
};
int counter = 0;
int retrieved = false;
List_<struct String> imports = Impl.emptyList();
List_<struct String> structs = Impl.emptyList();
List_<struct String> globals = Impl.emptyList();
List_<struct String> methods = Impl.emptyList();
int counter = 0;
<R> R match(Function<struct T, struct R> whenOk, Function<struct X, struct R> whenErr) {
	return whenErr.apply(this.error);
}
<R> R match(Function<struct T, struct R> whenOk, Function<struct X, struct R> whenErr) {
	return whenOk.apply(this.value);
}
struct private State(Deque<char> queue, List_<struct String> segments, struct StringBuilder buffer, int depth) {
	this.queue = queue;
	this.segments = segments;
	this.buffer = buffer;
	this.depth = depth;
}
struct public State(Deque<char> queue) {
	this(queue, Impl.emptyList(), struct StringBuilder(), 0);
}
struct State advance() {
	this.segments.add(this.buffer.toString());
	this.buffer = struct StringBuilder();
	return this;
}
struct State append(char c) {
	this.buffer.append(c);
	return this;
}
int isLevel() {
	return this.depth == 0;
}
char pop() {
	return this.queue.pop();
}
int hasElements() {
	return !this.queue.isEmpty();
}
struct State exit() {
	this.depth = this.depth - 1;
	return this;
}
struct State enter() {
	this.depth = this.depth + 1;
	return this;
}
List_<struct String> segments() {
	return this.segments;
}
char peek() {
	return this.queue.peek();
}
Optional<struct String> createInitial() {
	return Optional.empty();
}
auto __lambda0__(auto inner) {
	return inner + this.delimiter + element;
}
Optional<struct String> fold(Optional<struct String> current, struct String element) {
	return Optional.of(current.map(__lambda0__).orElse(element));
}
struct public RangeHead(int length) {
	this.length = length;
}
Optional<int> next() {
	if (this.counter >= this.length) {
		return Optional.empty();
	}
	int value = this.counter;this.counter++;
	return Optional.of(value);
}
auto __lambda1__(auto next) {
	return folder.apply(finalCurrent, next);
}
<R> R fold(struct R initial, BiFunction<struct R, struct T, struct R> folder) {
	struct R current = initial;
	while (true) {
		struct R finalCurrent = current;
		Optional<struct R> maybeCurrent = this.head.next().map(__lambda1__);
		if (maybeCurrent.isPresent()) {
			current = maybeCurrent.get();
		}
		else {
			return current;
		}
	}
}
auto __lambda2__() {
	return this.head.next().map(mapper);
}
<R> Iterator<R> map(Function<struct T, struct R> mapper) {
	return HeadedIterator<>(__lambda2__);
}
auto __lambda3__() {
	return struct collector.fold()
}
<C> C collect(Collector<struct T, struct C> collector) {
	return this.fold(collector.createInitial(), __lambda3__);
}
auto __lambda4__(auto aBoolean, auto t) {
	return aBoolean || predicate.test(t);
}
int anyMatch(Predicate<struct T> predicate) {
	return this.fold(false, __lambda4__);
}
void forEach(Consumer<struct T> consumer) {
	while (true) {
		Optional<struct T> next = this.head.next();
		if (next.isEmpty()) {
			break;
		}
		next.ifPresent(consumer);
	}
}
auto __lambda5__(auto value) {
	return HeadedIterator<>(predicate.test(value)
                    ? new SingleHead<>(value)
                    : new EmptyHead<>());
}
Iterator<struct T> filter(Predicate<struct T> predicate) {
	return this.flatMap(__lambda5__);
}
auto __lambda6__(auto aBoolean, auto t) {
	return aBoolean && predicate.test(t);
}
int allMatch(Predicate<struct T> predicate) {
	return this.fold(true, __lambda6__);
}
auto __lambda7__() {
	return struct other.next()
}
auto __lambda8__() {
	return this.head.next().or(__lambda7__);
}
Iterator<struct T> concat(Iterator<struct T> other) {
	return HeadedIterator<>(__lambda8__);
}
Optional<struct T> next() {
	return this.head.next();
}
auto __lambda9__() {
	return struct Iterator.concat()
}
<R> Iterator<R> flatMap(Function<struct T, Iterator<struct R>> mapper) {
	return this.map(mapper).fold(Iterators.empty(), __lambda9__);
}
Optional<struct T> next() {
	return Optional.empty();
}
<T> Iterator<T> fromArray(struct T* array) {
	return HeadedIterator<>(struct RangeHead(array.length)).map(index -> array[index]);
}
<T> Iterator<T> empty() {
	return HeadedIterator<>(EmptyHead<>());
}
List_<struct T> createInitial() {
	return Impl.emptyList();
}
List_<struct T> fold(List_<struct T> current, struct T element) {
	return current.add(element);
}
struct public SingleHead(struct T value) {
	this.value = value;
}
Optional<struct T> next() {
	if (this.retrieved) {
		return Optional.empty();
	}
	this.retrieved = true;
	return Optional.of(this.value);
}
auto __lambda10__(auto input) {
	return compileAndWrite(input, source);
}
auto __lambda11__() {
	return struct Optional.of()
}
auto __lambda12__() {
	return struct IOError.display()
}
void main(struct String* args) {
	struct Path_ source = Impl.get(".", "src", "java", "magma", "Main.java");
	Impl.readString(source).match(__lambda10__, __lambda11__).ifPresent(__lambda12__);
}
Optional<struct IOError> compileAndWrite(struct String input, struct Path_ source) {
	struct Path_ target = source.resolveSibling("main.c");
	struct String output = compile(input);
	return Impl.writeString(target, output);
}
auto __lambda13__() {
	return struct Main.divideStatementChar()
}
auto __lambda14__() {
	return struct Main.compileRootSegment()
}
auto __lambda15__(auto list) {
	List_<struct String> copy = Impl.emptyList();
	copy.addAll(imports);
	copy.addAll(structs);
	copy.addAll(globals);
	copy.addAll(methods);
	copy.addAll(list);
	return copy;
}
auto __lambda16__() {
	return struct Main.mergeStatements()
}
auto __lambda17__(auto compiled) {
	return mergeAll(compiled, __lambda16__);
}
auto __lambda18__() {
	return generatePlaceholder(input);
}
struct String compile(struct String input) {
	List_<struct String> segments = divide(input, __lambda13__);
	return parseAll(segments, __lambda14__).map(__lambda15__).map(__lambda17__).or(__lambda18__).orElse("");
}
auto __lambda19__() {
	return struct Main.divideStatementChar()
}
auto __lambda20__() {
	return struct Main.mergeStatements()
}
Optional<struct String> compileStatements(struct String input, Function<struct String, Optional<struct String>> compiler) {
	return compileAndMerge(divide(input, __lambda19__), compiler, __lambda20__);
}
auto __lambda21__(auto compiled) {
	return mergeAll(compiled, merger);
}
Optional<struct String> compileAndMerge(List_<struct String> segments, Function<struct String, Optional<struct String>> compiler, BiFunction<struct StringBuilder, struct String, struct StringBuilder> merger) {
	return parseAll(segments, compiler).map(__lambda21__);
}
struct String mergeAll(List_<struct String> compiled, BiFunction<struct StringBuilder, struct String, struct StringBuilder> merger) {
	return compiled.iter().fold(struct StringBuilder(), merger).toString();
}
auto __lambda22__(auto compiledSegment) {
	allCompiled.add(compiledSegment);
	return allCompiled;
}
auto __lambda23__(auto allCompiled) {
	return compiler.apply(segment).map(__lambda22__);
}
auto __lambda24__(auto maybeCompiled, auto segment) {
	return maybeCompiled.flatMap(__lambda23__);
}
Optional<List_<struct String>> parseAll(List_<struct String> segments, Function<struct String, Optional<struct String>> compiler) {
	return segments.iter().fold(Optional.of(Impl.emptyList()), __lambda24__);
}
struct StringBuilder mergeStatements(struct StringBuilder output, struct String compiled) {
	return output.append(compiled);
}
auto __lambda25__() {
	return struct input.charAt()
}
auto __lambda26__() {
	return struct LinkedList.new()
}
List_<struct String> divide(struct String input, BiFunction<struct State, struct Character, struct State> divider) {
	LinkedList<char> queue = IntStream.range(0, input.length()).mapToObj(__lambda25__).collect(Collectors.toCollection(__lambda26__));
	struct State state = struct State(queue);
	while (state.hasElements()) {
		char c = state.pop();
		if (c == '\'') {
			state.append(c);
			char maybeSlash = state.pop();
			state.append(maybeSlash);
			if (maybeSlash == '\\') {
				state.append(state.pop());
			}
			state.append(state.pop());
			continue;
		}
		if (c == '\"') {
			state.append(c);
			while (state.hasElements()) {
				char next = state.pop();
				state.append(next);
				if (next == '\\') {
					state.append(state.pop());
				}
				if (next == '"') {
					break;
				}
			}
			continue;
		}
		state = divider.apply(state, c);
	}
	return state.advance().segments();
}
struct State divideStatementChar(struct State state, char c) {
	struct State appended = state.append(c);
	if (c == ';' && appended.isLevel()) {
		return appended.advance();
	}
	if (c == '}' && isShallow(appended)) {
		return appended.advance().exit();
	}
	if (c == '{' || c == '(') {
		return appended.enter();
	}
	if (c == '}' || c == ')') {
		return appended.exit();
	}
	return appended;
}
int isShallow(struct State state) {
	return state.depth == 1;
}
auto __lambda27__(auto input1) {
	return compileClassMember(input1, typeParams);
}
auto __lambda28__(auto outputContent) {
			structs.add("struct " + name + " {\n" + outputContent + "};\n");
			return "";
}
Optional<struct String> compileToStruct(struct String input, struct String infix, List_<struct String> typeParams) {
	int classIndex = input.indexOf(infix);
	if (classIndex < 0) {
		return Optional.empty();
	}
	struct String afterKeyword = input.substring(classIndex + infix.length());
	int contentStart = afterKeyword.indexOf("{");
	if (contentStart >= 0) {
		struct String name = afterKeyword.substring(0, contentStart).strip();
		struct String withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
		if (withEnd.endsWith("}")) {
			struct String inputContent = withEnd.substring(0, withEnd.length() - "}".length());
			return compileStatements(inputContent, __lambda27__).map(__lambda28__);
		}
	}
	return Optional.empty();
}
auto __lambda29__() {
	return compileToStruct(input, "interface ", typeParams);
}
auto __lambda30__() {
	return compileToStruct(input, "record ", typeParams);
}
auto __lambda31__() {
	return compileToStruct(input, "class ", typeParams);
}
auto __lambda32__() {
	return compileGlobalInitialization(input, typeParams);
}
auto __lambda33__() {
	return compileDefinitionStatement(input);
}
auto __lambda34__() {
	return compileMethod(input, typeParams);
}
auto __lambda35__() {
	return generatePlaceholder(input);
}
Optional<struct String> compileClassMember(struct String input, List_<struct String> typeParams) {
	return compileWhitespace(input).or(__lambda29__).or(__lambda30__).or(__lambda31__).or(__lambda32__).or(__lambda33__).or(__lambda34__).or(__lambda35__);
}
auto __lambda36__(auto result) {
	return "\t" + result + ";\n";
}
Optional<struct String> compileDefinitionStatement(struct String input) {
	struct String stripped = input.strip();
	if (stripped.endsWith(";")) {
		struct String content = stripped.substring(0, stripped.length() - ";".length());
		return compileDefinition(content).map(__lambda36__);
	}
	return Optional.empty();
}
auto __lambda37__(auto generated) {
	globals.add(generated + ";\n");
	return "";
}
Optional<struct String> compileGlobalInitialization(struct String input, List_<struct String> typeParams) {
	return compileInitialization(input, typeParams, 0).map(__lambda37__);
}
auto __lambda38__(auto outputValue) {
	return outputDefinition + " = " + outputValue;
}
auto __lambda39__(auto outputDefinition) {
	return compileValue(value, typeParams, depth).map(__lambda38__);
}
Optional<struct String> compileInitialization(struct String input, List_<struct String> typeParams, int depth) {
	if (!input.endsWith(";")) {
		return Optional.empty();
	}
	struct String withoutEnd = input.substring(0, input.length() - ";".length());
	int valueSeparator = withoutEnd.indexOf("=");
	if (valueSeparator < 0) {
		return Optional.empty();
	}
	struct String definition = withoutEnd.substring(0, valueSeparator).strip();
	struct String value = withoutEnd.substring(valueSeparator + "=".length()).strip();
	return compileDefinition(definition).flatMap(__lambda39__);
}
Optional<struct String> compileWhitespace(struct String input) {
	if (input.isBlank()) {
		return Optional.of("");
	}
	return Optional.empty();
}
auto __lambda40__() {
	return struct Main.compileParameter()
}
Optional<struct String> compileMethod(struct String input, List_<struct String> typeParams) {
	int paramStart = input.indexOf("(");
	if (paramStart < 0) {
		return Optional.empty();
	}
	struct String inputDefinition = input.substring(0, paramStart).strip();
	struct String withParams = input.substring(paramStart + "(".length());
	return compileDefinition(inputDefinition).flatMap(outputDefinition -> {
            int paramEnd = withParams.indexOf(")");
            if (paramEnd < 0) {
                return Optional.empty();
            }

            String params = withParams.substring(0, paramEnd);
            return compileValues(params, __lambda40__).flatMap(outputParams -> assembleMethodBody(typeParams, outputDefinition, outputParams, withParams.substring(paramEnd + ")".length()).strip()));
        });
}
auto __lambda41__(auto input1) {
	return compileStatementOrBlock(input1, typeParams, 1);
}
auto __lambda42__(auto outputContent) {
		methods.add(header + " {" + outputContent + "\n}\n");
		return Optional.of("");
}
Optional<struct String> assembleMethodBody(List_<struct String> typeParams, struct String definition, struct String params, struct String body) {
	struct String header = "\t".repeat(0) + definition + "(" + params + ")";
	if (body.startsWith("{") && body.endsWith("}")) {
		struct String inputContent = body.substring("{".length(), body.length() - "}".length());
		return compileStatements(inputContent, __lambda41__).flatMap(__lambda42__);
	}
	return Optional.of(header + ";");
}
auto __lambda43__() {
	return compileDefinition(definition);
}
auto __lambda44__() {
	return generatePlaceholder(definition);
}
Optional<struct String> compileParameter(struct String definition) {
	return compileWhitespace(definition).or(__lambda43__).or(__lambda44__);
}
auto __lambda45__() {
	return struct Main.divideValueChar()
}
Optional<struct String> compileValues(struct String input, Function<struct String, Optional<struct String>> compiler) {
	List_<struct String> divided = divide(input, __lambda45__);
	return compileValues(divided, compiler);
}
struct State divideValueChar(struct State state, char c) {
	if (c == '-') {
		if (state.peek() == '>') {
			state.pop();
			return state.append('-').append('>');
		}
	}
	if (c == ',' && state.isLevel()) {
		return state.advance();
	}
	struct State appended = state.append(c);
	if (c == ' < ' || c == '(') {
		return appended.enter();
	}
	if (c == '>' || c == ')') {
		return appended.exit();
	}
	return appended;
}
auto __lambda46__() {
	return struct Main.mergeValues()
}
Optional<struct String> compileValues(List_<struct String> params, Function<struct String, Optional<struct String>> compiler) {
	return compileAndMerge(params, compiler, __lambda46__);
}
auto __lambda47__() {
	return compileKeywordStatement(input, depth, "continue");
}
auto __lambda48__() {
	return compileKeywordStatement(input, depth, "break");
}
auto __lambda49__() {
	return compileConditional(input, typeParams, "if ", depth);
}
auto __lambda50__() {
	return compileConditional(input, typeParams, "while ", depth);
}
auto __lambda51__() {
	return compileElse(input, typeParams, depth);
}
auto __lambda52__() {
	return compilePostOperator(input, typeParams, depth, "++");
}
auto __lambda53__() {
	return compilePostOperator(input, typeParams, depth, "--");
}
auto __lambda54__(auto result) {
	return formatStatement(depth, result);
}
auto __lambda55__() {
	return compileReturn(input, typeParams, depth).map(__lambda54__);
}
auto __lambda56__(auto result) {
	return formatStatement(depth, result);
}
auto __lambda57__() {
	return compileInitialization(input, typeParams, depth).map(__lambda56__);
}
auto __lambda58__(auto result) {
	return formatStatement(depth, result);
}
auto __lambda59__() {
	return compileAssignment(input, typeParams, depth).map(__lambda58__);
}
auto __lambda60__(auto result) {
	return formatStatement(depth, result);
}
auto __lambda61__() {
	return compileInvocationStatement(input, typeParams, depth).map(__lambda60__);
}
auto __lambda62__() {
	return compileDefinitionStatement(input);
}
auto __lambda63__() {
	return generatePlaceholder(input);
}
Optional<struct String> compileStatementOrBlock(struct String input, List_<struct String> typeParams, int depth) {
	return compileWhitespace(input).or(__lambda47__).or(__lambda48__).or(__lambda49__).or(__lambda50__).or(__lambda51__).or(__lambda52__).or(__lambda53__).or(__lambda55__).or(__lambda57__).or(__lambda59__).or(__lambda61__).or(__lambda62__).or(__lambda63__);
}
auto __lambda64__(auto value) {
	return value + operator + ";";
}
Optional<struct String> compilePostOperator(struct String input, List_<struct String> typeParams, int depth, struct String operator) {
	struct String stripped = input.strip();
	if (stripped.endsWith(operator + ";")) {
		struct String slice = stripped.substring(0, stripped.length() -(operator + ";").length());
		return compileValue(slice, typeParams, depth).map(__lambda64__);
	}
	else {
		return Optional.empty();
	}
}
auto __lambda65__(auto statement) {
	return compileStatementOrBlock(statement, typeParams, depth + 1);
}
auto __lambda66__(auto result) {
	return indent + "else {" + result + indent + "}";
}
auto __lambda67__(auto result) {
	return "else " + result;
}
Optional<struct String> compileElse(struct String input, List_<struct String> typeParams, int depth) {
	struct String stripped = input.strip();
	if (stripped.startsWith("else ")) {
		struct String withoutKeyword = stripped.substring("else ".length()).strip();
		if (withoutKeyword.startsWith("{") && withoutKeyword.endsWith("}")) {
			struct String indent = createIndent(depth);
			return compileStatements(withoutKeyword.substring(1, withoutKeyword.length() - 1), __lambda65__).map(__lambda66__);
		}
		else {
			return compileStatementOrBlock(withoutKeyword, typeParams, depth).map(__lambda67__);
		}
	}
	return Optional.empty();
}
Optional<struct String> compileKeywordStatement(struct String input, int depth, struct String keyword) {
	if (input.strip().equals(keyword + ";")) {
		return Optional.of(formatStatement(depth, keyword));
	}
	else {
		return Optional.empty();
	}
}
struct String formatStatement(int depth, struct String value) {
	return createIndent(depth) + value + ";";
}
struct String createIndent(int depth) {
	return "\n" + "\t".repeat(depth);
}
auto __lambda68__(auto statement) {
	return compileStatementOrBlock(statement, typeParams, depth + 1);
}
auto __lambda69__(auto statements) {
		return withCondition + " {" + statements + "\n" +
                            "\t".repeat(depth) +
                            "}";
}
auto __lambda70__(auto result) {
		return withCondition + " " + result;
}
auto __lambda71__(auto newCondition) {
	struct String withCondition = createIndent(depth) + prefix + "(" + newCondition + ")";
	if (withBraces.startsWith("{") && withBraces.endsWith("}")) {
		struct String content = withBraces.substring(1, withBraces.length() - 1);
		return compileStatements(content, __lambda68__).map(__lambda69__);
	}
	else {
		return compileStatementOrBlock(withBraces, typeParams, depth).map(__lambda70__);
	}
}
Optional<struct String> compileConditional(struct String input, List_<struct String> typeParams, struct String prefix, int depth) {
	struct String stripped = input.strip();
	if (!stripped.startsWith(prefix)) {
		return Optional.empty();
	}
	struct String afterKeyword = stripped.substring(prefix.length()).strip();
	if (!afterKeyword.startsWith("(")) {
		return Optional.empty();
	}
	struct String withoutConditionStart = afterKeyword.substring(1);
	int conditionEnd = findConditionEnd(withoutConditionStart);
	if (conditionEnd < 0) {
		return Optional.empty();
	}
	struct String oldCondition = withoutConditionStart.substring(0, conditionEnd).strip();
	struct String withBraces = withoutConditionStart.substring(conditionEnd + ")".length()).strip();
	return compileValue(oldCondition, typeParams, depth).flatMap(__lambda71__);
}
auto __lambda72__(auto index) {
	return Tuple<>(index, input.charAt(index));
}
auto __lambda73__() {
	return struct LinkedList.new()
}
int findConditionEnd(struct String input) {
	int conditionEnd = -1;
	int depth0 = 0;
	LinkedList<Tuple<int, struct Character>> queue = IntStream.range(0, input.length()).mapToObj(__lambda72__).collect(Collectors.toCollection(__lambda73__));
	while (!queue.isEmpty()) {
		Tuple<int, struct Character> pair = queue.pop();
		int i = pair.left;
		char c = pair.right;
		if (c == '\'') {
			if (queue.pop().right == '\\') {
				queue.pop();
			}
			queue.pop();
			continue;
		}
		if (c == '"') {
			while (!queue.isEmpty()) {
				Tuple<int, struct Character> next = queue.pop();
				if (next.right == '\\') {
					queue.pop();
				}
				if (next.right == '"') {
					break;
				}
			}
			continue;
		}
		if (c == ')' && depth0 == 0) {
			conditionEnd = i;
			break;
		}
		if (c == '(') {depth0++;
		}
		if (c == ')') {depth0--;
		}
	}
	return conditionEnd;
}
Optional<struct String> compileInvocationStatement(struct String input, List_<struct String> typeParams, int depth) {
	struct String stripped = input.strip();
	if (stripped.endsWith(";")) {
		struct String withoutEnd = stripped.substring(0, stripped.length() - ";".length());
		Optional<struct String> maybeInvocation = compileInvocation(withoutEnd, typeParams, depth);
		if (maybeInvocation.isPresent()) {
			return maybeInvocation;
		}
	}
	return Optional.empty();
}
auto __lambda74__(auto newSource) {
			return newDest + " = " + newSource;
}
auto __lambda75__(auto newDest) {
			return compileValue(source, typeParams, depth).map(__lambda74__);
}
Optional<struct String> compileAssignment(struct String input, List_<struct String> typeParams, int depth) {
	struct String stripped = input.strip();
	if (stripped.endsWith(";")) {
		struct String withoutEnd = stripped.substring(0, stripped.length() - ";".length());
		int valueSeparator = withoutEnd.indexOf("=");
		if (valueSeparator >= 0) {
			struct String destination = withoutEnd.substring(0, valueSeparator).strip();
			struct String source = withoutEnd.substring(valueSeparator + "=".length()).strip();
			return compileValue(destination, typeParams, depth).flatMap(__lambda75__);
		}
	}
	return Optional.empty();
}
auto __lambda76__(auto result) {
	return "return " + result;
}
Optional<struct String> compileReturn(struct String input, List_<struct String> typeParams, int depth) {
	struct String stripped = input.strip();
	if (stripped.endsWith(";")) {
		struct String withoutEnd = stripped.substring(0, stripped.length() - ";".length());
		if (withoutEnd.startsWith("return ")) {
			return compileValue(withoutEnd.substring("return ".length()), typeParams, depth).map(__lambda76__);
		}
	}
	return Optional.empty();
}
auto __lambda77__(auto value) {
	return outputType + value;
}
auto __lambda78__(auto outputType) {
	return compileArgs(argsString, typeParams, depth).map(__lambda77__);
}
auto __lambda79__(auto result) {
	return "!" + result;
}
auto __lambda80__(auto compiled) {
			return generateLambdaWithReturn(Impl.emptyList(), "\n\treturn " + compiled + "." + property + "()");
}
auto __lambda81__(auto compiled) {
	return compiled + "." + property;
}
auto __lambda82__() {
	return compileOperator(input, typeParams, depth, "<");
}
auto __lambda83__() {
	return compileOperator(input, typeParams, depth, "+");
}
auto __lambda84__() {
	return compileOperator(input, typeParams, depth, ">=");
}
auto __lambda85__() {
	return compileOperator(input, typeParams, depth, "&&");
}
auto __lambda86__() {
	return compileOperator(input, typeParams, depth, "==");
}
auto __lambda87__() {
	return compileOperator(input, typeParams, depth, "!=");
}
auto __lambda88__() {
	return generatePlaceholder(input);
}
Optional<struct String> compileValue(struct String input, List_<struct String> typeParams, int depth) {
	struct String stripped = input.strip();
	if (stripped.startsWith("\"") && stripped.endsWith("\"")) {
		return Optional.of(stripped);
	}
	if (stripped.startsWith("'") && stripped.endsWith("'")) {
		return Optional.of(stripped);
	}
	if (isSymbol(stripped) || isNumber(stripped)) {
		return Optional.of(stripped);
	}
	if (stripped.startsWith("new ")) {
		struct String slice = stripped.substring("new ".length());
		int argsStart = slice.indexOf("(");
		if (argsStart >= 0) {
			struct String type = slice.substring(0, argsStart);
			struct String withEnd = slice.substring(argsStart + "(".length()).strip();
			if (withEnd.endsWith(")")) {
				struct String argsString = withEnd.substring(0, withEnd.length() - ")".length());
				return compileType(type, typeParams).flatMap(__lambda78__);
			}
		}
	}
	if (stripped.startsWith("!")) {
		return compileValue(stripped.substring(1), typeParams, depth).map(__lambda79__);
	}
	Optional<struct String> value = compileLambda(stripped, typeParams, depth);
	if (value.isPresent()) {
		return value;
	}
	Optional<struct String> invocation = compileInvocation(input, typeParams, depth);
	if (invocation.isPresent()) {
		return invocation;
	}
	int methodIndex = stripped.lastIndexOf("::");
	if (methodIndex >= 0) {
		struct String type = stripped.substring(0, methodIndex).strip();
		struct String property = stripped.substring(methodIndex + "::".length()).strip();
		if (isSymbol(property)) {
			return compileType(type, typeParams).flatMap(__lambda80__);
		}
	}
	int separator = input.lastIndexOf(".");
	if (separator >= 0) {
		struct String object = input.substring(0, separator).strip();
		struct String property = input.substring(separator + ".".length()).strip();
		return compileValue(object, typeParams, depth).map(__lambda81__);
	}
	return compileOperator(input, typeParams, depth, "||").or(__lambda82__).or(__lambda83__).or(__lambda84__).or(__lambda85__).or(__lambda86__).or(__lambda87__).or(__lambda88__);
}
auto __lambda89__(auto rightResult) {
	return leftResult + " " + operator + " " + rightResult;
}
auto __lambda90__(auto leftResult) {
	return compileValue(right, typeParams, depth).map(__lambda89__);
}
Optional<struct String> compileOperator(struct String input, List_<struct String> typeParams, int depth, struct String operator) {
	int operatorIndex = input.indexOf(operator);
	if (operatorIndex < 0) {
		return Optional.empty();
	}
	struct String left = input.substring(0, operatorIndex);
	struct String right = input.substring(operatorIndex + operator.length());
	return compileValue(left, typeParams, depth).flatMap(__lambda90__);
}
auto __lambda91__() {
	return struct String.strip()
}
auto __lambda92__(auto value) {
	return !value.isEmpty();
}
auto __lambda93__(auto statement) {
	return compileStatementOrBlock(statement, typeParams, depth);
}
auto __lambda94__(auto result) {
		return generateLambdaWithReturn(paramNames, result);
}
auto __lambda95__(auto newValue) {
	return generateLambdaWithReturn(paramNames, "\n\treturn " + newValue + ";");
}
Optional<struct String> compileLambda(struct String input, List_<struct String> typeParams, int depth) {
	int arrowIndex = input.indexOf("->");
	if (arrowIndex < 0) {
		return Optional.empty();
	}
	struct String beforeArrow = input.substring(0, arrowIndex).strip();	List_<struct String> paramNames;

	if (isSymbol(beforeArrow)) {
		paramNames = Impl.listOf(beforeArrow);
	}else 
	if (beforeArrow.startsWith("(") && beforeArrow.endsWith(")")) {
		struct String inner = beforeArrow.substring(1, beforeArrow.length() - 1);
		paramNames = Iterators.fromArray(inner.split(Pattern.quote(","))).map(__lambda91__).filter(__lambda92__).collect(ListCollector<>());
	}
	else {
		return Optional.empty();
	}
	struct String value = input.substring(arrowIndex + "->".length()).strip();
	if (value.startsWith("{") && value.endsWith("}")) {
		struct String slice = value.substring(1, value.length() - 1);
		return compileStatements(slice, __lambda93__).flatMap(__lambda94__);
	}
	return compileValue(value, typeParams, depth).flatMap(__lambda95__);
}
auto __lambda96__(auto name) {
	return "auto " + name;
}
Optional<struct String> generateLambdaWithReturn(List_<struct String> paramNames, struct String returnValue) {
	int current = counter;counter++;
	struct String lambdaName = "__lambda" + current + "__";
	struct String joinedLambdaParams = paramNames.iter().map(__lambda96__).collect(struct Joiner(", ")).orElse("");
	methods.add("auto " + lambdaName + "(" + joinedLambdaParams + ")" + " {" + returnValue + "\n}\n");
	return Optional.of(lambdaName);
}
auto __lambda97__(auto index) {
	char c = input.charAt(index);
	return (index == 0 && c == '-') || Character.isDigit(c);
}
int isNumber(struct String input) {
	return IntStream.range(0, input.length()).allMatch(__lambda97__);
}
auto __lambda98__(auto value) {
	return caller + value;
}
auto __lambda99__(auto caller) {
			return compileArgs(withEnd, typeParams, depth).map(__lambda98__);
}
Optional<struct String> compileInvocation(struct String input, List_<struct String> typeParams, int depth) {
	struct String stripped = input.strip();
	if (stripped.endsWith(")")) {
		struct String sliced = stripped.substring(0, stripped.length() - ")".length());
		int argsStart = findInvocationStart(sliced);
		if (argsStart >= 0) {
			struct String type = sliced.substring(0, argsStart);
			struct String withEnd = sliced.substring(argsStart + "(".length()).strip();
			return compileValue(type, typeParams, depth).flatMap(__lambda99__);
		}
	}
	return Optional.empty();
}
int findInvocationStart(struct String sliced) {
	int argsStart = -1;
	int depth0 = 0;
	int i = sliced.length() - 1;
	while (i >= 0) {
		char c = sliced.charAt(i);
		if (c == '(' && depth0 == 0) {
			argsStart = i;
			break;
		}
		if (c == ')') {depth0++;
		}
		if (c == '(') {depth0--;
		}i--;
	}
	return argsStart;
}
auto __lambda100__() {
	return compileValue(arg, typeParams, depth);
}
auto __lambda101__(auto arg) {
	return compileWhitespace(arg).or(__lambda100__);
}
auto __lambda102__(auto args) {
	return "(" + args + ")";
}
Optional<struct String> compileArgs(struct String argsString, List_<struct String> typeParams, int depth) {
	return compileValues(argsString, __lambda101__).map(__lambda102__);
}
struct StringBuilder mergeValues(struct StringBuilder cache, struct String element) {
	if (cache.isEmpty()) {
		return cache.append(element);
	}
	return cache.append(", ").append(element);
}
auto __lambda103__() {
	return struct String.strip()
}
auto __lambda104__(auto value) {
	return !value.isEmpty();
}
auto __lambda105__() {
	return struct Main.isSymbol()
}
auto __lambda106__(auto outputType) {
	return Optional.of(generateDefinition(typeParams, outputType, name));
}
auto __lambda107__(auto outputType) {
	return Optional.of(generateDefinition(Impl.emptyList(), outputType, name));
}
Optional<struct String> compileDefinition(struct String definition) {
	struct String stripped = definition.strip();
	int nameSeparator = stripped.lastIndexOf(" ");
	if (nameSeparator < 0) {
		return Optional.empty();
	}
	struct String beforeName = stripped.substring(0, nameSeparator).strip();
	struct String name = stripped.substring(nameSeparator + " ".length()).strip();
	if (!isSymbol(name)) {
		return Optional.empty();
	}
	int typeSeparator = -1;
	int depth = 0;
	int i = beforeName.length() - 1;
	while (i >= 0) {
		char c = beforeName.charAt(i);
		if (c == ' ' && depth == 0) {
			typeSeparator = i;
			break;
		}
		else {
			if (c == '>') {depth++;
			}
			if (c == ' < ') {depth--;
			}
		}i--;
	}
	if (typeSeparator >= 0) {
		struct String beforeType = beforeName.substring(0, typeSeparator).strip();
		struct String beforeTypeParams = beforeType;	List_<struct String> typeParams;

		if (beforeType.endsWith(">")) {
			struct String withoutEnd = beforeType.substring(0, beforeType.length() - ">".length());
			int typeParamStart = withoutEnd.indexOf("<");
			if (typeParamStart >= 0) {
				beforeTypeParams = withoutEnd.substring(0, typeParamStart);
				struct String substring = withoutEnd.substring(typeParamStart + 1);
				typeParams = splitValues(substring);
			}
			else {
				typeParams = Impl.emptyList();
			}
		}
		else {
			typeParams = Impl.emptyList();
		}
		struct String strippedBeforeTypeParams = beforeTypeParams.strip();	struct String modifiersString;

		int annotationSeparator = strippedBeforeTypeParams.lastIndexOf("\n");
		if (annotationSeparator >= 0) {
			modifiersString = strippedBeforeTypeParams.substring(annotationSeparator + "\n".length());
		}
		else {
			modifiersString = strippedBeforeTypeParams;
		}
		int allSymbols = Iterators.fromArray(modifiersString.split(Pattern.quote(" "))).map(__lambda103__).filter(__lambda104__).allMatch(__lambda105__);
		if (!allSymbols) {
			return Optional.empty();
		}
		struct String inputType = beforeName.substring(typeSeparator + " ".length());
		return compileType(inputType, typeParams).flatMap(__lambda106__);
	}
	else {
		return compileType(beforeName, Impl.emptyList()).flatMap(__lambda107__);
	}
}
auto __lambda108__() {
	return struct String.strip()
}
auto __lambda109__(auto param) {
	return !param.isEmpty();
}
List_<struct String> splitValues(struct String substring) {
	struct String* paramsArrays = substring.strip().split(Pattern.quote(","));
	return Iterators.fromArray(paramsArrays).map(__lambda108__).filter(__lambda109__).collect(ListCollector<>());
}
auto __lambda110__(auto inner) {
	return "<" + inner + "> ";
}
struct String generateDefinition(List_<struct String> maybeTypeParams, struct String type, struct String name) {
	struct String typeParamsString = maybeTypeParams.iter().collect(struct Joiner(", ")).map(__lambda110__).orElse("");
	return typeParamsString + type + " " + name;
}
auto __lambda111__(auto value) {
	return value + "*";
}
auto __lambda112__() {
	return struct String.equals()
}
auto __lambda113__() {
	return compileType(type, typeParams);
}
auto __lambda114__(auto type) {
			return compileWhitespace(type).or(__lambda113__);
}
auto __lambda115__(auto compiled) {
			return base + " < " + compiled + ">";
}
Optional<struct String> compileType(struct String input, List_<struct String> typeParams) {
	if (input.equals("void")) {
		return Optional.of("void");
	}
	if (input.equals("int") || input.equals("Integer") || input.equals("boolean") || input.equals("Boolean")) {
		return Optional.of("int");
	}
	if (input.equals("char") || input.equals("Character")) {
		return Optional.of("char");
	}
	if (input.endsWith("[]")) {
		return compileType(input.substring(0, input.length() - "[]".length()), typeParams).map(__lambda111__);
	}
	struct String stripped = input.strip();
	if (isSymbol(stripped)) {
		if (Impl.contains(typeParams, stripped, __lambda112__)) {
			return Optional.of(stripped);
		}
		else {
			return Optional.of("struct " + stripped);
		}
	}
	if (stripped.endsWith(">")) {
		struct String slice = stripped.substring(0, stripped.length() - ">".length());
		int argsStart = slice.indexOf("<");
		if (argsStart >= 0) {
			struct String base = slice.substring(0, argsStart).strip();
			struct String params = slice.substring(argsStart + " < ".length()).strip();
			return compileValues(params, __lambda114__).map(__lambda115__);
		}
	}
	return generatePlaceholder(input);
}
auto __lambda116__(auto index) {
	char c = input.charAt(index);
	return c == '_' || Character.isLetter(c) ||(index != 0 && Character.isDigit(c));
}
int isSymbol(struct String input) {
	if (input.isBlank()) {
		return false;
	}
	return IntStream.range(0, input.length()).allMatch(__lambda116__);
}
Optional<struct String> generatePlaceholder(struct String input) {
	return Optional.of("/* " + input + " */");
}
