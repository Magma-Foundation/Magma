#include "./org/jetbrains/annotations/NotNull"
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
auto __lambda0__() {
	return magma.Files.readString(source)
                .match(input -> compileAndWrite(input, source), Optional.of)
                .ifPresent()
}
auto __lambda1__() {
	return Throwable.printStackTrace()
}
void main(struct String* args) {
	struct Path source = Paths.get(".", "src", "java", "magma", "Main.java");
	__lambda0__;(__lambda1__;);
}
Optional<struct IOException> compileAndWrite(struct String input, struct Path source) {
	struct Path target = source.resolveSibling("main.c");
	struct String output = compile(input);
	return magma.Files.writeString(target, output);
}
auto __lambda2__() {
	return Main.divideStatementChar()
}
auto __lambda3__() {
	return Main.mergeStatements))
                .or(() -> generatePlaceholder(input)).orElse(""()
}
struct String compile(struct String input) {
	List<struct String> segments = divide(input, __lambda2__;);
	List<struct String> copy = ArrayList<struct String>(/* );
                    copy */.addAll(imports);
                    copy.addAll(structs);
                    copy.addAll(globals);
                    copy.addAll(methods);
                    copy.addAll(list);
                    return copy;
                }).map(compiled -> mergeAll(compiled, __lambda3__;);
}
auto __lambda4__() {
	return Main.divideStatementChar()
}
auto __lambda5__() {
	return Main.mergeStatements()
}
Optional<struct String> compileStatements(struct String input, Function<struct String, Optional<struct String>> compiler) {
	return compileAndMerge(divide(input, __lambda4__;), compiler, __lambda5__;);
}
auto __lambda6__(auto compiled) {
	return mergeAll(compiled, merger);
}
Optional<struct String> compileAndMerge(List<struct String> segments, Function<struct String, Optional<struct String>> compiler, BiFunction<struct StringBuilder, struct String, struct StringBuilder> merger) {
	return parseAll(segments, compiler).map(__lambda6__);
}
auto __lambda7__(auto _, auto next) {
	return next;
}
struct String mergeAll(List<struct String> compiled, BiFunction<struct StringBuilder, struct String, struct StringBuilder> merger) {
	return compiled.stream().reduce(struct StringBuilder(), merger, __lambda7__).toString();
}
auto __lambda8__(auto compiledSegment) {
	allCompiled.add(compiledSegment);
	return allCompiled;
}
auto __lambda9__(auto allCompiled) {
	return compiler.apply(segment).map(__lambda8__);
}
auto __lambda10__(auto maybeCompiled, auto segment) {
	return maybeCompiled.flatMap(__lambda9__);
}
auto __lambda11__(auto _, auto next) {
	return next;
}
Optional<List<struct String>> parseAll(List<struct String> segments, Function<struct String, Optional<struct String>> compiler) {
	return segments.stream().reduce(Optional.of(ArrayList<struct String>()), __lambda10__, __lambda11__);
}
struct StringBuilder mergeStatements(struct StringBuilder output, struct String compiled) {
	return output.append(compiled);
}
auto __lambda12__() {
	return IntStream.range(0, input.length())
                .mapToObj(input.charAt)
                .collect()
}
auto __lambda13__() {
	return LinkedList.new()
}
List<struct String> divide(struct String input, BiFunction<struct State, struct Character, struct State> divider) {
	LinkedList<char> queue = __lambda12__;(Collectors.toCollection(__lambda13__;));
	struct State state = struct State(queue);/* 
        while (state.hasElements()) {
            char c = state.pop();

            if (c == '\'') {
                state.append(c);
                char maybeSlash = state.pop();
                state.append(maybeSlash);

                if (maybeSlash == '\\') state.append(state.pop());
                state.append(state.pop());
                continue;
            }

            if (c == '\"') {
                state.append(c);

                while (state.hasElements()) {
                    char next = state.pop();
                    state.append(next);

                    if (next == '\\') state.append(state.pop());
                    if (next == '"') {
                        break;
                    }
                }

                continue;
            }

            state = divider.apply(state, c);
        } */
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
	struct String stripped = input.strip();/* 
        if (stripped.startsWith("import ")) {
            String right = stripped.substring("import ".length());
            if (right.endsWith(";")) {
                String content = right.substring(0, right.length() - ";".length());
                String joined = String.join("/", content.split(Pattern.quote(".")));
                imports.add("#include \"./" + joined + "\"\n");
                return Optional.of("");
            }
        } */
	Optional<struct String> maybeClass = compileToStruct(input, "class ", ArrayList<>());
	if (maybeClass.isPresent()) /* return maybeClass; */
	return generatePlaceholder(input);
}
Optional<struct String> compileToStruct(struct String input, struct String infix, List<struct String> typeParams) {
	int classIndex = input.indexOf(infix);
	if (/* classIndex < 0 */) /* return Optional */.empty();
	struct String afterKeyword = input.substring(/* classIndex + infix */.length());
	int contentStart = afterKeyword.indexOf("{");/* 
        if (contentStart >= 0) {
            String name = afterKeyword.substring(0, contentStart).strip();
            String withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
            if (withEnd.endsWith("}")) {
                String inputContent = withEnd.substring(0, withEnd.length() - "}".length());
                return compileStatements(inputContent, input1 -> compileClassMember(input1, typeParams)).map(outputContent -> {
                    structs.add("struct " + name + " {\n" + outputContent + "};\n");
                    return "";
                });
            }
        } */
	return Optional.empty();
}
auto __lambda14__(auto ) {
	return compileToStruct(input, "interface ", typeParams);
}
auto __lambda15__(auto ) {
	return compileToStruct(input, "record ", typeParams);
}
auto __lambda16__(auto ) {
	return compileToStruct(input, "class ", typeParams);
}
auto __lambda17__(auto ) {
	return compileGlobalInitialization(input, typeParams);
}
auto __lambda18__(auto ) {
	return compileDefinitionStatement(input);
}
auto __lambda19__(auto ) {
	return compileMethod(input, typeParams);
}
auto __lambda20__(auto ) {
	return generatePlaceholder(input);
}
Optional<struct String> compileClassMember(struct String input, List<struct String> typeParams) {
	return compileWhitespace(input).or(__lambda14__).or(__lambda15__).or(__lambda16__).or(__lambda17__).or(__lambda18__).or(__lambda19__).or(__lambda20__);
}
auto __lambda21__(auto result) {
	return "\t" + result + ";\n";
}
Optional<struct String> compileDefinitionStatement(struct String input) {
	struct String stripped = input.strip();/* 
        if (stripped.endsWith(";")) {
            String content = stripped.substring(0, stripped.length() - ";".length());
            return compileDefinition(content).map(result -> "\t" + result + ";\n");
        } */
	return Optional.empty();
}
auto __lambda22__(auto generated) {
	globals.add(/* generated + ";\n" */);
	return "";
}
Optional<struct String> compileGlobalInitialization(struct String input, List<struct String> typeParams) {
	return compileInitialization(input, typeParams).map(__lambda22__);
}
auto __lambda23__(auto outputValue) {
	return /* outputDefinition + " = " + outputValue */;
}
auto __lambda24__(auto outputDefinition) {
	return compileValue(value, typeParams).map(__lambda23__);
}
Optional<struct String> compileInitialization(struct String input, List<struct String> typeParams) {
	if (!input.endsWith(";")) /* return Optional */.empty();
	struct String withoutEnd = input.substring(0, input.length() - ";".length());
	int valueSeparator = withoutEnd.indexOf("=");
	if (/* valueSeparator < 0 */) /* return Optional */.empty();
	struct String definition = withoutEnd.substring(0, valueSeparator).strip();
	struct String value = withoutEnd.substring(/* valueSeparator + "=" */.length()).strip();
	return compileDefinition(definition).flatMap(__lambda24__);
}
Optional<struct String> compileWhitespace(struct String input) {
	if (input.isBlank()) /* return Optional */.of("");
	return Optional.empty();
}
auto __lambda25__(auto ) {
	return compileDefinition(definition);
}
auto __lambda26__(auto ) {
	return generatePlaceholder(definition);
}
auto __lambda27__(auto definition) {
	return compileWhitespace(definition).or(__lambda25__).or(__lambda26__);
}
auto __lambda28__(auto outputParams) {
	return /* {
                String header = "\t" */.repeat(0) + outputDefinition + "(" + outputParams + ")";
                String body = withParams.substring(paramEnd + ")".length();
}
Optional<struct String> compileMethod(struct String input, List<struct String> typeParams) {
	int paramStart = input.indexOf("(");
	if (/* paramStart < 0 */) /* return Optional */.empty();
	struct String inputDefinition = input.substring(0, paramStart).strip();
	struct String withParams = input.substring(paramStart + "(".length());
	int paramEnd = withParams.indexOf(")");
            if (paramEnd < 0) return Optional.empty();

            String params = withParams.substring(0, paramEnd);
            return compileValues(params, __lambda27__).flatMap(__lambda28__).strip();
                if (body.startsWith("{") && body.endsWith("}")) {
                    String inputContent = body.substring("{".length(), body.length() - "}".length());
                    return compileStatements(inputContent, /* input1 -> compileStatementOrBlock */(/* input1 */, typeParams)).flatMap(outputContent -> {
                        methods.add(header + " {" + outputContent + "\n}\n");
                        return Optional.of("");
                    });
                }

                return Optional.of(header + ";");
            });
        });
}
auto __lambda29__() {
	return Main.divideValueChar()
}
Optional<struct String> compileValues(struct String input, Function<struct String, Optional<struct String>> compiler) {
	List<struct String> divided = divide(input, __lambda29__;);
	return compileValues(divided, compiler);
}
struct State divideValueChar(struct State state, char c) {/* 
        if (c == '-') {
            if (state.peek() == '>') {
                state.pop();
                return state.append('-').append('>');
            }
        } */
	if (/* c == ',' && state */.isLevel()) /* return state */.advance();
	struct State appended = state.append(c);
	/* if (c */ = /* = '<' || c == '(') return appended */.enter();
	if (/* c == '>' || c == ' */) /* ') return appended */.exit();
	return appended;
}
auto __lambda30__() {
	return Main.mergeValues()
}
Optional<struct String> compileValues(List<struct String> params, Function<struct String, Optional<struct String>> compiler) {
	return compileAndMerge(params, compiler, __lambda30__;);
}
auto __lambda31__(auto ) {
	return compileConditional(input, typeParams, "if ");
}
auto __lambda32__(auto ) {
	return compileConditional(input, typeParams, "while ");
}
auto __lambda33__(auto value) {
	return "\n\t" + value + ";";
}
auto __lambda34__(auto ) {
	return compileInitialization(input, typeParams).map(__lambda33__);
}
auto __lambda35__(auto result) {
	return "\n\t" + result + ";";
}
auto __lambda36__(auto ) {
	return compileStatement(input, typeParams).map(__lambda35__);
}
auto __lambda37__(auto ) {
	return generatePlaceholder(input);
}
Optional<struct String> compileStatementOrBlock(struct String input, List<struct String> typeParams) {
	return compileWhitespace(input).or(__lambda31__).or(__lambda32__).or(__lambda34__).or(__lambda36__).or(__lambda37__);
}
Optional<struct String> compileConditional(struct String input, List<struct String> typeParams, struct String prefix) {
	struct String stripped = input.strip();/* 
        if (stripped.startsWith(prefix)) {
            String afterKeyword = stripped.substring(prefix.length()).strip();
            if (afterKeyword.startsWith("(")) {
                String withoutConditionStart = afterKeyword.substring(1);
                int conditionEnd = -1;
                int depth = 0;
                for (int i = 0; i < withoutConditionStart.length(); i++) {
                    char c = withoutConditionStart.charAt(i);
                    if (c == ')' && depth == 0) {
                        conditionEnd = i;
                        break;
                    }
                    if (c == '(') depth++;
                    if (c == ')') depth--;
                }

                if (conditionEnd >= 0) {
                    String oldCondition = withoutConditionStart.substring(0, conditionEnd).strip();
                    String withBraces = withoutConditionStart.substring(conditionEnd + ")".length()).strip();
                    return compileValue(oldCondition, typeParams).flatMap(newCondition -> {
                        if (withBraces.startsWith("{") && withBraces.endsWith("}")) {
                            String content = withBraces.substring(1, withBraces.length() - 1);
                            return compileStatements(content, statement -> compileStatement(statement, typeParams)).map(statements -> {
                                return "\n\t" + prefix + "(" + newCondition + ") " + statements;
                            });
                        } else {
                            return compileValue(withBraces, typeParams).map(result -> {
                                return "\n\t" + prefix + "(" + newCondition + ") " + result;
                            });
                        }
                    });
                }
            }
        } */
	return Optional.empty();
}
Optional<struct String> compileStatement(struct String input, List<struct String> typeParams) {
	struct String stripped = input.strip();/* 
        if (stripped.endsWith(";")) {
            String withoutEnd = stripped.substring(0, stripped.length() - ";".length());
            if (withoutEnd.startsWith("return ")) {
                return compileValue(withoutEnd.substring("return ".length()), typeParams).map(result -> "return " + result);
            }

            int valueSeparator = withoutEnd.indexOf("=");
            if (valueSeparator >= 0) {
                String destination = withoutEnd.substring(0, valueSeparator).strip();
                String source = withoutEnd.substring(valueSeparator + "=".length()).strip();
                return compileValue(destination, typeParams).flatMap(newDest -> {
                    return compileValue(source, typeParams).map(newSource -> {
                        return newDest + " = " + newSource;
                    });
                });
            }

            Optional<String> maybeInvocation = compileInvocation(withoutEnd, typeParams);
            if (maybeInvocation.isPresent()) return maybeInvocation;
        } */
	return Optional.empty();
}
auto __lambda38__(auto result) {
	return /* "!" + result */;
}
auto __lambda39__() {
	return stripped.substring(methodIndex + ".".length()).strip()
}
auto __lambda40__(auto result) {
	return /* result + ";" */;
}
auto __lambda41__(auto compiled) {
	return /* compiled + " */." + property;
}
Optional<struct String> compileValue(struct String input, List<struct String> typeParams) {
	struct String stripped = input.strip();/* 
        if (stripped.startsWith("\"") && stripped.endsWith("\"")) {
            return Optional.of(stripped);
        } *//* 

        if (stripped.startsWith("new ")) {
            String slice = stripped.substring("new ".length());
            int argsStart = slice.indexOf("(");
            if (argsStart >= 0) {
                String type = slice.substring(0, argsStart);
                String withEnd = slice.substring(argsStart + "(".length()).strip();
                if (withEnd.endsWith(")")) {
                    String argsString = withEnd.substring(0, withEnd.length() - ")".length());
                    return compileType(type, typeParams).flatMap(outputType -> compileArgs(argsString, typeParams).map(value -> outputType + value));
                }
            }
        } *//* 

        if (stripped.startsWith("!")) {
            return compileValue(stripped.substring(1), typeParams).map(result -> "!" + result);
        } */
	Optional<struct String> value = compileLambda(stripped, typeParams);
	if (value.isPresent()) /* return value; */
	Optional<struct String> invocation = compileInvocation(input, typeParams);
	if (invocation.isPresent()) /* return invocation; */
	int methodIndex = stripped.lastIndexOf("::");/* 
        if (methodIndex >= 0) {
            String type = stripped.substring(0, methodIndex).strip();
            String property = stripped.substring(methodIndex + "::".length()).strip();

            return generateLambdaWithReturn(Collections.emptyList(), "\n\treturn " + type + "." + property + "()").map(result -> result + ";");
        } */
	int separator = input.lastIndexOf(".");/* 
        if (separator >= 0) {
            String object = input.substring(0, separator).strip();
            String property = input.substring(separator + ".".length()).strip();
            return compileValue(object, typeParams).map(compiled -> compiled + "." + property);
        } *//* 

        if (isSymbol(stripped) || isNumber(stripped)) {
            return Optional.of(stripped);
        } */
	return generatePlaceholder(input);
}
auto __lambda42__(auto statement) {
	return compileStatementOrBlock(statement, typeParams);
}
auto __lambda43__(auto result) {
	return generateLambdaWithReturn(paramNames, result);
}
auto __lambda44__(auto newValue) {
	return generateLambdaWithReturn(paramNames, "\n\treturn " + newValue + ";");
}
Optional<struct String> compileLambda(struct String input, List<struct String> typeParams) {
	int arrowIndex = input.indexOf("->");
	if (/* arrowIndex < 0 */) /* return Optional */.empty();
	struct String beforeArrow = input.substring(0, arrowIndex).strip();/* 
        List<String> paramNames; *//* 
        if (isSymbol(beforeArrow)) {
            paramNames = Collections.singletonList(beforeArrow);
        } *//*  else if (beforeArrow.startsWith("(") && beforeArrow.endsWith(")")) {
            String inner = beforeArrow.substring(1, beforeArrow.length() - 1);
            paramNames = Arrays.stream(inner.split(Pattern.quote(",")))
                    .map(String::strip)
                    .toList();
        } *//*  else {
            return Optional.empty();
        } */
	struct String value = input.substring(/* arrowIndex + "->" */.length()).strip();/* 
        if (value.startsWith("{") && value.endsWith("}")) {
            String slice = value.substring(1, value.length() - 1);
            return compileStatements(slice, statement -> compileStatementOrBlock(statement, typeParams)).flatMap(result -> {
                return generateLambdaWithReturn(paramNames, result);
            });
        } */
	return compileValue(value, typeParams).flatMap(__lambda44__);
}
auto __lambda45__(auto name) {
	return /* "auto " + name */;
}
Optional<struct String> generateLambdaWithReturn(List<struct String> paramNames, struct String returnValue) {
	int current = counter;/* 
        counter++; */
	struct String lambdaName = "__lambda" + current + "__";
	struct String joined = paramNames.stream().map(__lambda45__).collect(Collectors.joining(", ", "(", ")"));
	methods.add("auto " + lambdaName + joined + " {" + returnValue + "\n}\n");
	return Optional.of(lambdaName);
}
auto __lambda46__() {
	return IntStream.range(0, input.length())
                .map(input.charAt)
                .allMatch()
}
auto __lambda47__() {
	return Character.isDigit()
}
int isNumber(struct String input) {
	return __lambda46__;(__lambda47__;);
}
Optional<struct String> compileInvocation(struct String input, List<struct String> typeParams) {
	struct String stripped = input.strip();
	if (stripped.endsWith(")") /* ) {
            String sliced = stripped */.substring(0, stripped.length() - ")".length());

            int argsStart = -1;
            int depth = 0;
            int i = sliced.length() - 1;
            while (i >= 0) {
                char c = sliced.charAt(i);
                if (c == '(' && depth == 0) {
                    argsStart = i;
                    break;
                }

                if (c == ')') depth++;
                if (c == '(') depth--;
                i--;
            }

            if (argsStart >= 0) {
                String type = sliced.substring(0, argsStart);
                String withEnd = sliced.substring(argsStart + "(".length()).strip();
                return compileValue(type, typeParams).flatMap(caller -> {
                    return compileArgs(withEnd, typeParams).map(value -> caller + value);
                });
            }
        }
	return Optional.empty();
}
auto __lambda48__(auto ) {
	return compileValue(arg, typeParams);
}
auto __lambda49__(auto arg) {
	return compileWhitespace(arg).or(__lambda48__);
}
auto __lambda50__(auto args) {
	return "(" + args + ")";
}
Optional<struct String> compileArgs(struct String argsString, List<struct String> typeParams) {
	return compileValues(argsString, __lambda49__).map(__lambda50__);
}
struct StringBuilder mergeValues(struct StringBuilder cache, struct String element) {
	if (cache.isEmpty()) /* return cache */.append(element);
	return cache.append(", ").append(element);
}
Optional<struct String> compileDefinition(struct String definition) {
	int nameSeparator = definition.lastIndexOf(" ");
	if (/* nameSeparator < 0 */) /* return Optional */.empty();
	struct String beforeName = definition.substring(0, nameSeparator).strip();
	struct String name = definition.substring(/* nameSeparator + " " */.length()).strip();
	if (!isSymbol(name)) /* return Optional */.empty();
	int typeSeparator = /* -1 */;
	int depth = 0;
	int i = beforeName.length() - 1;/* 
        while (i >= 0) {
            char c = beforeName.charAt(i);
            if (c == ' ' && depth == 0) {
                typeSeparator = i;
                break;
            } else {
                if (c == '>') depth++;
                if (c == '<') depth--;
            }
            i--;
        } *//* 

        if (typeSeparator >= 0) {
            String beforeType = beforeName.substring(0, typeSeparator).strip();

            List<String> typeParams;
            if (beforeType.endsWith(">")) {
                String withoutEnd = beforeType.substring(0, beforeType.length() - ">".length());
                int typeParamStart = withoutEnd.indexOf("<");
                if (typeParamStart >= 0) {
                    String substring = withoutEnd.substring(typeParamStart + 1);
                    typeParams = splitValues(substring);
                } else {
                    typeParams = Collections.emptyList();
                }
            } else {
                typeParams = Collections.emptyList();
            }

            String inputType = beforeName.substring(typeSeparator + " ".length());
            return compileType(inputType, typeParams).flatMap(outputType -> Optional.of(generateDefinition(typeParams, outputType, name)));
        } *//*  else {
            return compileType(beforeName, Collections.emptyList()).flatMap(outputType -> Optional.of(generateDefinition(Collections.emptyList(), outputType, name)));
        } */
}
auto __lambda51__() {
	return Arrays.stream(paramsArrays)
                .map(String.strip)
                .filter(param -> !param.isEmpty())
                .toList()
}
List<struct String> splitValues(struct String substring) {
	struct String* paramsArrays = substring.strip().split(Pattern.quote(","));
	return __lambda51__;();
}
struct String generateDefinition(List<struct String> maybeTypeParams, struct String type, struct String name) {/* 
        String typeParamsString; *//* 
        if (maybeTypeParams.isEmpty()) {
            typeParamsString = "";
        } *//*  else {
            typeParamsString = "<" + String.join(", ", maybeTypeParams) + "> ";
        } */
	return /* typeParamsString + type + " " + name */;
}
auto __lambda52__(auto value) {
	return /* value + "*" */;
}
Optional<struct String> compileType(struct String input, List<struct String> typeParams) {
	if (input.equals("void")) /* return Optional */.of("void");/* 

        if (input.equals("int") || input.equals("Integer") || input.equals("boolean") || input.equals("Boolean")) {
            return Optional.of("int");
        } *//* 

        if (input.equals("char") || input.equals("Character")) {
            return Optional.of("char");
        } *//* 

        if (input.endsWith("[]")) {
            return compileType(input.substring(0, input.length() - "[]".length()), typeParams)
                    .map(value -> value + "*");
        } */
	struct String stripped = input.strip();/* 
        if (isSymbol(stripped)) {
            if (typeParams.contains(stripped)) {
                return Optional.of(stripped);
            } else {
                return Optional.of("struct " + stripped);
            }
        } *//* 

        if (stripped.endsWith(">")) {
            String slice = stripped.substring(0, stripped.length() - ">".length());
            int argsStart = slice.indexOf("<");
            if (argsStart >= 0) {
                String base = slice.substring(0, argsStart).strip();
                String params = slice.substring(argsStart + "<".length()).strip();
                return compileValues(params, type -> {
                    return compileWhitespace(type).or(() -> compileType(type, typeParams));
                }).map(compiled -> {
                    return base + "<" + compiled + ">";
                });
            }
        } */
	return generatePlaceholder(input);
}
auto __lambda53__() {
	return IntStream.range(0, input.length())
                .mapToObj(input.charAt)
                .allMatch()
}
auto __lambda54__() {
	return Character.isLetter()
}
int isSymbol(struct String input) {
	return __lambda53__;(__lambda54__;);
}
Optional<struct String> generatePlaceholder(struct String input) {
	return Optional.of("/* " + input + " */");
}
/* 
 */