#include "./java/io/IOException"
#include "./java/nio/file/Path"
#include "./java/nio/file/Paths"
#include "./java/util/ArrayList"
#include "./java/util/Arrays"
#include "./java/util/Collections"
#include "./java/util/Deque"
#include "./java/util/LinkedList"
#include "./java/util/List"
#include "./java/util/Optional"
#include "./java/util/function/BiFunction"
#include "./java/util/function/Function"
#include "./java/util/regex/Pattern"
#include "./java/util/stream/Collectors"
#include "./java/util/stream/IntStream"
struct Result<T, X> {
<R> R match(Function<struct T, struct R> whenOk, Function<struct X, struct R> whenErr);};
struct Err<T, X>(X error) implements Result<T, X> {
};
struct Ok<T, X>(T value) implements Result<T, X> {
};
struct State {
	Deque<char> queue;
	List<struct String> segments;
	struct StringBuilder buffer;
	int depth;
};
struct Main {
};
List<struct String> imports = ArrayList<>();
List<struct String> structs = ArrayList<>();
List<struct String> globals = ArrayList<>();
List<struct String> methods = ArrayList<>();
int counter = 0;
<R> R match(Function<struct T, struct R> whenOk, Function<struct X, struct R> whenErr) {
	return whenErr.apply(this.error);
}
<R> R match(Function<struct T, struct R> whenOk, Function<struct X, struct R> whenErr) {
	return whenOk.apply(this.value);
}
struct private State(Deque<char> queue, List<struct String> segments, struct StringBuilder buffer, int depth) {
	this.queue = queue;
	this.segments = segments;
	this.buffer = buffer;
	this.depth = depth;
}
struct public State(Deque<char> queue) {
	this(queue, ArrayList<>(), struct StringBuilder(), 0);
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
List<struct String> segments() {
	return this.segments;
}
char peek() {
	return this.queue.peek();
}
auto __lambda0__(auto input) {
	return compileAndWrite(input, source);
}
auto __lambda1__() {
	return struct Optional.of()
}
auto __lambda2__() {
	return struct Throwable.printStackTrace()
}
void main(struct String* args) {
	struct Path source = Paths.get(".", "src", "java", "magma", "Main.java");
	magma.Files.readString(source).match(__lambda0__, __lambda1__).ifPresent(__lambda2__);
}
Optional<struct IOException> compileAndWrite(struct String input, struct Path source) {
	struct Path target = source.resolveSibling("main.c");
	struct String output = compile(input);
	return magma.Files.writeString(target, output);
}
auto __lambda3__() {
	return struct Main.divideStatementChar()
}
auto __lambda4__() {
	return struct Main.compileRootSegment()
}
auto __lambda5__(auto list) {
	List<struct String> copy = ArrayList<struct String>();
	copy.addAll(imports);
	copy.addAll(structs);
	copy.addAll(globals);
	copy.addAll(methods);
	copy.addAll(list);
	return copy;
}
auto __lambda6__() {
	return struct Main.mergeStatements()
}
auto __lambda7__(auto compiled) {
	return mergeAll(compiled, __lambda6__);
}
auto __lambda8__(auto ) {
	return generatePlaceholder(input);
}
struct String compile(struct String input) {
	List<struct String> segments = divide(input, __lambda3__);
	return parseAll(segments, __lambda4__).map(__lambda5__).map(__lambda7__).or(__lambda8__).orElse("");
}
auto __lambda9__() {
	return struct Main.divideStatementChar()
}
auto __lambda10__() {
	return struct Main.mergeStatements()
}
Optional<struct String> compileStatements(struct String input, Function<struct String, Optional<struct String>> compiler) {
	return compileAndMerge(divide(input, __lambda9__), compiler, __lambda10__);
}
auto __lambda11__(auto compiled) {
	return mergeAll(compiled, merger);
}
Optional<struct String> compileAndMerge(List<struct String> segments, Function<struct String, Optional<struct String>> compiler, BiFunction<struct StringBuilder, struct String, struct StringBuilder> merger) {
	return parseAll(segments, compiler).map(__lambda11__);
}
auto __lambda12__(auto _, auto next) {
	return next;
}
struct String mergeAll(List<struct String> compiled, BiFunction<struct StringBuilder, struct String, struct StringBuilder> merger) {
	return compiled.stream().reduce(struct StringBuilder(), merger, __lambda12__).toString();
}
auto __lambda13__(auto compiledSegment) {
	allCompiled.add(compiledSegment);
	return allCompiled;
}
auto __lambda14__(auto allCompiled) {
	return compiler.apply(segment).map(__lambda13__);
}
auto __lambda15__(auto maybeCompiled, auto segment) {
	return maybeCompiled.flatMap(__lambda14__);
}
auto __lambda16__(auto _, auto next) {
	return next;
}
Optional<List<struct String>> parseAll(List<struct String> segments, Function<struct String, Optional<struct String>> compiler) {
	return segments.stream().reduce(Optional.of(ArrayList<struct String>()), __lambda15__, __lambda16__);
}
struct StringBuilder mergeStatements(struct StringBuilder output, struct String compiled) {
	return output.append(compiled);
}
auto __lambda17__() {
	return struct input.charAt()
}
auto __lambda18__() {
	return struct LinkedList.new()
}
List<struct String> divide(struct String input, BiFunction<struct State, struct Character, struct State> divider) {
	LinkedList<char> queue = IntStream.range(0, input.length()).mapToObj(__lambda17__).collect(Collectors.toCollection(__lambda18__));
	struct State state = struct State(queue);
	while (state.hasElements()) {
		char c = state.pop();
		if (/* c == '\'' */) {
			state.append(c);
			char maybeSlash = state.pop();
			state.append(maybeSlash);
			if (/* maybeSlash == '\\' */) state.append(state.pop());
			state.append(state.pop());/* 
                continue; */
		}
		if (/* c == '\"' */) {
			state.append(c);
			while (state.hasElements()) {
				char next = state.pop();
				state.append(next);
				if (/* next == '\\' */) state.append(state.pop());
				if (/* next == '"' */) {/* 
                        break; */
				}
			}/* 

                continue; */
		}
		state = divider.apply(state, c);
	}
	return state.advance().segments();
}
struct State divideStatementChar(struct State state, char c) {
	struct State appended = state.append(c);
	if (/* c == ';' && appended */.isLevel()) /* return appended */.advance();
	if (/* c == '}' && isShallow */(appended)) /* return appended */.advance().exit();
	/* if (c */ = /* = '{' || c == '(') return appended */.enter();
	if (/* c == '}' || c == ' */) /* ') return appended */.exit();
	return appended;
}
int isShallow(struct State state) {
	return state.depth == 1;
}
Optional<struct String> compileRootSegment(struct String input) {
	if (input.startsWith("package ")) /* return Optional */.of("");
	struct String stripped = input.strip();
	if (stripped.startsWith("import ")) {
		struct String right = stripped.substring("import ".length());
		if (right.endsWith(";")) {
			struct String content = right.substring(0, right.length() - ";".length());
			struct String joined = String.join("/", content.split(Pattern.quote(".")));
			imports.add("#include \"./" + joined + "\"\n");
			return Optional.of("");
		}
	}
	Optional<struct String> maybeClass = compileToStruct(input, "class ", ArrayList<>());
	if (maybeClass.isPresent()) /* return maybeClass; */
	return generatePlaceholder(input);
}
auto __lambda19__(auto outputContent) {
			structs.add("struct " + name + " {\n" + outputContent + "};\n");
			return "";
}
Optional<struct String> compileToStruct(struct String input, struct String infix, List<struct String> typeParams) {
	int classIndex = input.indexOf(infix);
	if (/* classIndex < 0 */) /* return Optional */.empty();
	struct String afterKeyword = input.substring(/* classIndex + infix */.length());
	int contentStart = afterKeyword.indexOf("{");
	if (/* contentStart >= 0 */) {
		struct String name = afterKeyword.substring(0, contentStart).strip();
		struct String withEnd = afterKeyword.substring(/* contentStart + "{" */.length()).strip();
		if (withEnd.endsWith("}")) {
			struct String inputContent = withEnd.substring(0, withEnd.length() - "}".length());
			return compileStatements(inputContent, /* input1 -> compileClassMember */(/* input1 */, typeParams)).map(__lambda19__);
		}
	}
	return Optional.empty();
}
auto __lambda20__(auto ) {
	return compileToStruct(input, "interface ", typeParams);
}
auto __lambda21__(auto ) {
	return compileToStruct(input, "record ", typeParams);
}
auto __lambda22__(auto ) {
	return compileToStruct(input, "class ", typeParams);
}
auto __lambda23__(auto ) {
	return compileGlobalInitialization(input, typeParams);
}
auto __lambda24__(auto ) {
	return compileDefinitionStatement(input);
}
auto __lambda25__(auto ) {
	return compileMethod(input, typeParams);
}
auto __lambda26__(auto ) {
	return generatePlaceholder(input);
}
Optional<struct String> compileClassMember(struct String input, List<struct String> typeParams) {
	return compileWhitespace(input).or(__lambda20__).or(__lambda21__).or(__lambda22__).or(__lambda23__).or(__lambda24__).or(__lambda25__).or(__lambda26__);
}
auto __lambda27__(auto result) {
	return "\t" + result + ";\n";
}
Optional<struct String> compileDefinitionStatement(struct String input) {
	struct String stripped = input.strip();
	if (stripped.endsWith(";")) {
		struct String content = stripped.substring(0, stripped.length() - ";".length());
		return compileDefinition(content).map(__lambda27__);
	}
	return Optional.empty();
}
auto __lambda28__(auto generated) {
	globals.add(/* generated + ";\n" */);
	return "";
}
Optional<struct String> compileGlobalInitialization(struct String input, List<struct String> typeParams) {
	return compileInitialization(input, typeParams, 0).map(__lambda28__);
}
auto __lambda29__(auto outputValue) {
	return /* outputDefinition + " = " + outputValue */;
}
auto __lambda30__(auto outputDefinition) {
	return compileValue(value, typeParams, depth).map(__lambda29__);
}
Optional<struct String> compileInitialization(struct String input, List<struct String> typeParams, int depth) {
	if (!input.endsWith(";")) /* return Optional */.empty();
	struct String withoutEnd = input.substring(0, input.length() - ";".length());
	int valueSeparator = withoutEnd.indexOf("=");
	if (/* valueSeparator < 0 */) /* return Optional */.empty();
	struct String definition = withoutEnd.substring(0, valueSeparator).strip();
	struct String value = withoutEnd.substring(/* valueSeparator + "=" */.length()).strip();
	return compileDefinition(definition).flatMap(__lambda30__);
}
Optional<struct String> compileWhitespace(struct String input) {
	if (input.isBlank()) /* return Optional */.of("");
	return Optional.empty();
}
auto __lambda31__(auto ) {
	return compileDefinition(definition);
}
auto __lambda32__(auto ) {
	return generatePlaceholder(definition);
}
auto __lambda33__(auto definition) {
	return compileWhitespace(definition).or(__lambda31__).or(__lambda32__);
}
auto __lambda34__(auto outputParams) {
	return /* {
                String header = "\t" */.repeat(0) + outputDefinition + "(" + outputParams + ")";
                String body = withParams.substring(paramEnd + ")".length();
}
Optional<struct String> compileMethod(struct String input, List<struct String> typeParams) {
	int paramStart = input.indexOf("(");
	if (/* paramStart < 0 */) /* return Optional */.empty();
	struct String inputDefinition = input.substring(0, paramStart).strip();
	struct String withParams = input.substring(paramStart + "(".length());
	return compileDefinition(inputDefinition).flatMap(outputDefinition -> {
            int paramEnd = withParams.indexOf(")");
            if (paramEnd < 0) return Optional.empty();

            String params = withParams.substring(0, paramEnd);
            return compileValues(params, __lambda33__).flatMap(__lambda34__).strip();
                if (body.startsWith("{") && body.endsWith("}")) {
                    String inputContent = body.substring("{".length(), body.length() - "}".length());
                    return compileStatements(inputContent, /* input1 -> compileStatementOrBlock */(/* input1 */, typeParams, 1)).flatMap(outputContent -> {
                        methods.add(header + " {" + outputContent + "\n}\n");
                        return Optional.of("");
                    });
                }

                return Optional.of(header + ";");
            });
        });
}
auto __lambda35__() {
	return struct Main.divideValueChar()
}
Optional<struct String> compileValues(struct String input, Function<struct String, Optional<struct String>> compiler) {
	List<struct String> divided = divide(input, __lambda35__);
	return compileValues(divided, compiler);
}
struct State divideValueChar(struct State state, char c) {
	if (/* c == '-' */) {
		if (state.peek() == '>') {
			state.pop();
			return state.append(/* '-' */).append(/* '>' */);
		}
	}
	if (/* c == ',' && state */.isLevel()) /* return state */.advance();
	struct State appended = state.append(c);
	/* if (c */ = /* = '<' || c == '(') return appended */.enter();
	if (/* c == '>' || c == ' */) /* ') return appended */.exit();
	return appended;
}
auto __lambda36__() {
	return struct Main.mergeValues()
}
Optional<struct String> compileValues(List<struct String> params, Function<struct String, Optional<struct String>> compiler) {
	return compileAndMerge(params, compiler, __lambda36__);
}
auto __lambda37__(auto ) {
	return compileConditional(input, typeParams, "if ", depth);
}
auto __lambda38__(auto ) {
	return compileConditional(input, typeParams, "while ", depth);
}
auto __lambda39__(auto result) {
	return formatStatement(depth, result);
}
auto __lambda40__(auto ) {
	return compileInitialization(input, typeParams, depth).map(__lambda39__);
}
auto __lambda41__(auto result) {
	return formatStatement(depth, result);
}
auto __lambda42__(auto ) {
	return compileStatement(input, typeParams, depth).map(__lambda41__);
}
auto __lambda43__(auto ) {
	return generatePlaceholder(input);
}
Optional<struct String> compileStatementOrBlock(struct String input, List<struct String> typeParams, int depth) {
	return compileWhitespace(input).or(__lambda37__).or(__lambda38__).or(__lambda40__).or(__lambda42__).or(__lambda43__);
}
struct String formatStatement(int depth, struct String value) {
	return /* createIndent(depth) + value + ";" */;
}
struct String createIndent(int depth) {
	return "\n" + "\t".repeat(depth);
}
auto __lambda44__(auto statement) {
	return compileStatementOrBlock(statement, typeParams, /*  depth + 1 */);
}
auto __lambda45__(auto statements) {
		return /* withCondition +
                            " {" + statements + "\n" +
                            "\t" */.repeat(depth) +
                            "}";
}
auto __lambda46__(auto newCondition) {
	struct String withCondition = /* createIndent(depth) + prefix + "(" + newCondition + ")" */;
	if (withBraces.startsWith("{") && withBraces.endsWith("}")) {
		struct String content = withBraces.substring(1, withBraces.length() - 1);
		return compileStatements(content, __lambda44__).map(__lambda45__);
	}/*  else {
                return compileValue(withBraces, typeParams, depth).map(result -> {
                    return withCondition + " " + result;
                });
            } */
}
Optional<struct String> compileConditional(struct String input, List<struct String> typeParams, struct String prefix, int depth) {
	struct String stripped = input.strip();
	if (!stripped.startsWith(prefix)) /* return Optional */.empty();
	struct String afterKeyword = stripped.substring(prefix.length()).strip();
	/* if (!afterKeyword */.startsWith("(")) return Optional.empty();
	struct String withoutConditionStart = afterKeyword.substring(1);
	int conditionEnd = findConditionEnd(withoutConditionStart);
	if (/* conditionEnd < 0 */) /* return Optional */.empty();
	struct String oldCondition = withoutConditionStart.substring(0, conditionEnd).strip();
	struct String withBraces = withoutConditionStart.substring(conditionEnd + ")".length()).strip();
	return compileValue(oldCondition, typeParams, depth).flatMap(__lambda46__);
}
int findConditionEnd(struct String withoutConditionStart) {
	int conditionEnd = /* -1 */;
	/* int depth0 */ = 0;/* 
        for (int i = 0; i < withoutConditionStart.length(); i++) {
            char c = withoutConditionStart.charAt(i);
            if (c == ')' && depth0 == 0) {
                conditionEnd = i;
                break;
            }
            if (c == '(') depth0++;
            if (c == ')') depth0--;
        } */
	return conditionEnd;
}
auto __lambda47__(auto result) {
	return /* "return " + result */;
}
auto __lambda48__(auto newSource) {
			return /* newDest + " = " + newSource */;
}
auto __lambda49__(auto newDest) {
			return compileValue(source, typeParams, depth).map(__lambda48__);
}
Optional<struct String> compileStatement(struct String input, List<struct String> typeParams, int depth) {
	struct String stripped = input.strip();
	if (stripped.endsWith(";")) {
		struct String withoutEnd = stripped.substring(0, stripped.length() - ";".length());
		if (withoutEnd.startsWith("return ")) {
			return compileValue(withoutEnd.substring("return ".length()), typeParams, depth).map(__lambda47__);
		}
		int valueSeparator = withoutEnd.indexOf("=");
		if (/* valueSeparator >= 0 */) {
			struct String destination = withoutEnd.substring(0, valueSeparator).strip();
			struct String source = withoutEnd.substring(/* valueSeparator + "=" */.length()).strip();
			return compileValue(destination, typeParams, depth).flatMap(__lambda49__);
		}
		Optional<struct String> maybeInvocation = compileInvocation(withoutEnd, typeParams, depth);
		if (maybeInvocation.isPresent()) /* return maybeInvocation; */
	}
	return Optional.empty();
}
auto __lambda50__(auto result) {
	return /* "!" + result */;
}
auto __lambda51__(auto compiled) {
			return generateLambdaWithReturn(Collections.emptyList(), "\n\treturn " + compiled + "." + property + "()");
}
auto __lambda52__(auto compiled) {
	return /* compiled + " */." + property;
}
Optional<struct String> compileValue(struct String input, List<struct String> typeParams, int depth) {
	struct String stripped = input.strip();
	if (stripped.startsWith("\"") && stripped.endsWith("\"")) {
		return Optional.of(stripped);
	}
	if (stripped.startsWith("new ")) {
		struct String slice = stripped.substring("new ".length());
		int argsStart = slice.indexOf("(");
		if (/* argsStart >= 0 */) {
			struct String type = slice.substring(0, argsStart);
			struct String withEnd = slice.substring(argsStart + "(".length()).strip();
			if (withEnd.endsWith(")") /* ) {
                    String argsString = withEnd */.substring(0, withEnd.length() - ")".length());
                    return compileType(type, typeParams).flatMap(outputType -> compileArgs(argsString, typeParams, depth).map(value -> outputType + value));
                }
		}
	}
	if (stripped.startsWith("!")) {
		return compileValue(stripped.substring(1), typeParams, depth).map(__lambda50__);
	}
	Optional<struct String> value = compileLambda(stripped, typeParams, depth);
	if (value.isPresent()) /* return value; */
	Optional<struct String> invocation = compileInvocation(input, typeParams, depth);
	if (invocation.isPresent()) /* return invocation; */
	int methodIndex = stripped.lastIndexOf("::");
	if (/* methodIndex >= 0 */) {
		struct String type = stripped.substring(0, methodIndex).strip();
		struct String property = stripped.substring(/* methodIndex + "::" */.length()).strip();
		if (isSymbol(property)) {
			return compileType(type, typeParams).flatMap(__lambda51__);
		}
	}
	int separator = input.lastIndexOf(".");
	if (/* separator >= 0 */) {
		struct String object = input.substring(0, separator).strip();
		struct String property = input.substring(/* separator + " */.".length()).strip();
		return compileValue(object, typeParams, depth).map(__lambda52__);
	}
	if (/* isSymbol(stripped) || isNumber */(stripped)) {
		return Optional.of(stripped);
	}
	return generatePlaceholder(input);
}
auto __lambda53__(auto statement) {
	return compileStatementOrBlock(statement, typeParams, depth);
}
auto __lambda54__(auto result) {
		return generateLambdaWithReturn(paramNames, result);
}
auto __lambda55__(auto newValue) {
	return generateLambdaWithReturn(paramNames, "\n\treturn " + newValue + ";");
}
Optional<struct String> compileLambda(struct String input, List<struct String> typeParams, int depth) {
	int arrowIndex = input.indexOf("->");
	if (/* arrowIndex < 0 */) /* return Optional */.empty();
	struct String beforeArrow = input.substring(0, arrowIndex).strip();/* 
        List<String> paramNames; */
	if (isSymbol(beforeArrow)) {
		paramNames = Collections.singletonList(beforeArrow);
	}/*  else if (beforeArrow.startsWith("(") && beforeArrow.endsWith(")")) {
            String inner = beforeArrow.substring(1, beforeArrow.length() - 1);
            paramNames = Arrays.stream(inner.split(Pattern.quote(",")))
                    .map(String::strip)
                    .toList();
        } *//*  else {
            return Optional.empty();
        } */
	struct String value = input.substring(/* arrowIndex + "->" */.length()).strip();
	if (value.startsWith("{") && value.endsWith("}")) {
		struct String slice = value.substring(1, value.length() - 1);
		return compileStatements(slice, __lambda53__).flatMap(__lambda54__);
	}
	return compileValue(value, typeParams, depth).flatMap(__lambda55__);
}
auto __lambda56__(auto name) {
	return /* "auto " + name */;
}
Optional<struct String> generateLambdaWithReturn(List<struct String> paramNames, struct String returnValue) {
	int current = counter;/* 
        counter++; */
	struct String lambdaName = "__lambda" + current + "__";
	struct String joined = paramNames.stream().map(__lambda56__).collect(Collectors.joining(", ", "(", ")"));
	methods.add("auto " + lambdaName + joined + " {" + returnValue + "\n}\n");
	return Optional.of(lambdaName);
}
auto __lambda57__() {
	return struct input.charAt()
}
auto __lambda58__() {
	return char.isDigit()
}
int isNumber(struct String input) {
	return IntStream.range(0, input.length()).map(__lambda57__).allMatch(__lambda58__);
}
Optional<struct String> compileInvocation(struct String input, List<struct String> typeParams, int depth) {
	struct String stripped = input.strip();
	if (stripped.endsWith(")") /* ) {
            String sliced = stripped */.substring(0, stripped.length() - ")".length());

            int argsStart = findInvocationStart(sliced);

            if (argsStart >= 0) {
                String type = sliced.substring(0, argsStart);
                String withEnd = sliced.substring(argsStart + "(".length()).strip();
                return compileValue(type, typeParams, depth).flatMap(caller -> {
                    return compileArgs(withEnd, typeParams, depth).map(value -> caller + value);
                });
            }
        }
	return Optional.empty();
}
int findInvocationStart(struct String sliced) {
	int argsStart = /* -1 */;
	/* int depth0 */ = 0;
	int i = sliced.length() - 1;
	while (/* i >= 0 */) {
		char c = sliced.charAt(i);/* 
            if (c == '(' && depth0 == 0) {
                argsStart = i;
                break;
            } */
		if (/* c == ' */) /* ') depth0++; */
		/* if (c */ = /* = '(') depth0-- */;/* 
            i--; */
	}
	return argsStart;
}
auto __lambda59__(auto ) {
	return compileValue(arg, typeParams, depth);
}
auto __lambda60__(auto arg) {
	return compileWhitespace(arg).or(__lambda59__);
}
auto __lambda61__(auto args) {
	return "(" + args + ")";
}
Optional<struct String> compileArgs(struct String argsString, List<struct String> typeParams, int depth) {
	return compileValues(argsString, __lambda60__).map(__lambda61__);
}
struct StringBuilder mergeValues(struct StringBuilder cache, struct String element) {
	if (cache.isEmpty()) /* return cache */.append(element);
	return cache.append(", ").append(element);
}
auto __lambda62__() {
	return struct String.strip()
}
auto __lambda63__() {
	return struct Main.isSymbol()
}
auto __lambda64__(auto outputType) {
	return Optional.of(generateDefinition(typeParams, outputType, name));
}
Optional<struct String> compileDefinition(struct String definition) {
	int nameSeparator = definition.lastIndexOf(" ");
	if (/* nameSeparator < 0 */) /* return Optional */.empty();
	struct String beforeName = definition.substring(0, nameSeparator).strip();
	struct String name = definition.substring(/* nameSeparator + " " */.length()).strip();
	if (!isSymbol(name)) /* return Optional */.empty();
	int typeSeparator = /* -1 */;
	int depth = 0;
	int i = beforeName.length() - 1;
	while (/* i >= 0 */) {
		char c = beforeName.charAt(i);
		if (/* c == ' ' && depth == 0 */) {
			typeSeparator = i;/* 
                break; */
		}/*  else {
                if (c == '>') depth++;
                if (c == '<') depth--;
            } *//* 
            i--; */
	}
	if (/* typeSeparator >= 0 */) {
		struct String beforeType = beforeName.substring(0, typeSeparator).strip();
		struct String beforeTypeParams = beforeType;/* 
            List<String> typeParams; */
		if (beforeType.endsWith(">")) {
			struct String withoutEnd = beforeType.substring(0, beforeType.length() - ">".length());
			int typeParamStart = withoutEnd.indexOf("<");
			if (/* typeParamStart >= 0 */) {
				beforeTypeParams = withoutEnd.substring(0, typeParamStart);
				struct String substring = withoutEnd.substring(/* typeParamStart + 1 */);
				typeParams = splitValues(substring);
			}/*  else {
                    typeParams = Collections.emptyList();
                } */
		}/*  else {
                typeParams = Collections.emptyList();
            } */
		struct String strippedBeforeTypeParams = beforeTypeParams.strip();/* 

            String modifiersString; */
		int annotationSeparator = strippedBeforeTypeParams.lastIndexOf("\n");
		if (/* annotationSeparator >= 0 */) {
			modifiersString = strippedBeforeTypeParams.substring(/* annotationSeparator + "\n" */.length());
		}/*  else {
                modifiersString = strippedBeforeTypeParams;
            } */
		int allSymbols = Arrays.stream(modifiersString.split(Pattern.quote(" "))).map(__lambda62__).allMatch(__lambda63__);
		if (!allSymbols) {
			return Optional.empty();
		}
		struct String inputType = beforeName.substring(/* typeSeparator + " " */.length());
		return compileType(inputType, typeParams).flatMap(__lambda64__);
	}/*  else {
            return compileType(beforeName, Collections.emptyList()).flatMap(outputType -> Optional.of(generateDefinition(Collections.emptyList(), outputType, name)));
        } */
}
auto __lambda65__() {
	return struct String.strip()
}
auto __lambda66__(auto param) {
	return !param.isEmpty();
}
List<struct String> splitValues(struct String substring) {
	struct String* paramsArrays = substring.strip().split(Pattern.quote(","));
	return Arrays.stream(paramsArrays).map(__lambda65__).filter(__lambda66__).toList();
}
struct String generateDefinition(List<struct String> maybeTypeParams, struct String type, struct String name) {/* 
        String typeParamsString; */
	if (maybeTypeParams.isEmpty()) {
		typeParamsString = "";
	}/*  else {
            typeParamsString = "<" + String.join(", ", maybeTypeParams) + "> ";
        } */
	return /* typeParamsString + type + " " + name */;
}
auto __lambda67__(auto value) {
	return /* value + "*" */;
}
auto __lambda68__(auto ) {
	return compileType(type, typeParams);
}
auto __lambda69__(auto type) {
			return compileWhitespace(type).or(__lambda68__);
}
auto __lambda70__(auto compiled) {
			return /* base + "<" + compiled + ">" */;
}
Optional<struct String> compileType(struct String input, List<struct String> typeParams) {
	if (input.equals("void")) /* return Optional */.of("void");
	if (input.equals("int") || input.equals("Integer") || input.equals("boolean") || input.equals("Boolean")) {
		return Optional.of("int");
	}
	if (input.equals("char") || input.equals("Character")) {
		return Optional.of("char");
	}
	if (input.endsWith("[]")) {
		return compileType(input.substring(0, input.length() - "[]".length()), typeParams).map(__lambda67__);
	}
	struct String stripped = input.strip();
	if (isSymbol(stripped)) {
		if (typeParams.contains(stripped)) {
			return Optional.of(stripped);
		}/*  else {
                return Optional.of("struct " + stripped);
            } */
	}
	if (stripped.endsWith(">")) {
		struct String slice = stripped.substring(0, stripped.length() - ">".length());
		int argsStart = slice.indexOf("<");
		if (/* argsStart >= 0 */) {
			struct String base = slice.substring(0, argsStart).strip();
			struct String params = slice.substring(/* argsStart + "<" */.length()).strip();
			return compileValues(params, __lambda69__).map(__lambda70__);
		}
	}
	return generatePlaceholder(input);
}
auto __lambda71__() {
	return struct input.charAt()
}
auto __lambda72__() {
	return char.isLetter()
}
int isSymbol(struct String input) {
	return IntStream.range(0, input.length()).mapToObj(__lambda71__).allMatch(__lambda72__);
}
Optional<struct String> generatePlaceholder(struct String input) {
	return Optional.of("/* " + input + " */");
}
/* 
 */