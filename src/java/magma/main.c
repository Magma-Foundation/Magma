/* private */struct Defined extends Assignable {
};
/* private */struct Value extends Assignable {
};
/* private */struct Node {
};
/* private */struct Assignable extends Node {
};
/* private */struct State {
};
/* private */struct Joiner {
};
/* private */struct Definition {
};
/* private */struct Content {
};
/* private static */struct Whitespace implements Defined, Value {
};
/* private */struct StringValue {
};
/* private */struct Symbol {
};
/* private */struct Invocation {
};
/* private */struct DataAccess {
};
/* private */struct Operation {
};
/* private static */struct Iterators {
};
/* public */struct Main {
	/* private static String functionName = "" */;
	/* private static int functionLocalCounter = 0 */;
};
/* private */struct Tuple<char, struct State> {
};
/* public sealed */struct Option<Tuple<char, struct State>> {
};
/* private */struct Tuple</*  */> {
};
/* public */struct None</*  */> {
};
/* public */struct Some</*  */> {
};
/* public sealed */struct Option<struct State> {
};
/* public sealed */struct Option<char> {
};
/* public sealed */struct Option<char*> {
};
/* public */struct Iterator<struct T> {
};
/* public */struct List<char*> {
};
/* public */struct List<struct T> {
};
/* private static */struct ListCollector</*  */> {
};
/* public sealed */struct Option<struct Whitespace> {
};
/* public sealed */struct Option<struct Definition> {
};
struct State fromInput(struct State this, char* input){
	return struct State(input, listEmpty(), "", 0, 0);
}
auto popAndAppendToTuple_local1(auto tuple){
	auto poppedChar = tuple.left;
	auto poppedState = tuple.right;
	auto appended = poppedState.append(poppedState, poppedChar);
	return Tuple</*  */>(poppedChar, appended);
}
Option<Tuple<char, struct State>> popAndAppendToTuple(struct State this){
	auto popAndAppendToTuple_local3 = this.pop(this);
	return popAndAppendToTuple_local3.map(popAndAppendToTuple_local3, popAndAppendToTuple_local1);
}
int isLevel(struct State this){
	return this.depth == 0;
}
struct State enter(struct State this){
	return struct State(this.input, this.segments, this.buffer, this.depth + 1, this.index);
}
struct State exit(struct State this){
	return struct State(this.input, this.segments, this.buffer, this.depth - 1, this.index);
}
struct State advance(struct State this){
	return struct State(this.input, this.segments.addLast(this.segments, this.buffer), "", this.depth, this.index);
}
int isShallow(struct State this){
	return this.depth == 1;
}
Option<Tuple<char, struct State>> pop(struct State this){
	if (this.index >= this.input.length(this.index >= this.input)){
		return None</*  */>();
	}
	auto escaped = this.input.charAt(this.input, this.index);
	return Some</*  */>(Tuple<char, struct State>(escaped, struct State(this.input, this.segments, this.buffer, this.depth, this.index + 1)));
}
struct State append(struct State this, struct char c){
	return struct State(this.input, this.segments, this.buffer + c, this.depth, this.index);
}
Option<struct State> popAndAppend(struct State this){
	auto popAndAppend_local1 = this.popAndAppendToTuple(this);
	return popAndAppend_local1.map(popAndAppend_local1, /* Tuple::right */);
}
Option<char> peek(struct State this){
	if (this.index < this.input.length(this.index < this.input)){
		return Some</*  */>(this.input.charAt(this.input, this.index));
	}
	else {
		return None</*  */>();
	}
}
struct private Joiner(struct Joiner this){
	/* this(""); */
}
Option<char*> createInitial(struct Joiner this){
	return None</*  */>();
}
auto fold_local0(auto inner){
	return inner + this.delimiter + element;
}
Option<char*> fold(struct Joiner this, Option<char*> current, char* element){
	auto fold_local2 = current.map(current, fold_local0);
	return Some</*  */>(fold_local2.orElse(fold_local2, element));
}
char* generate(struct Definition this){
	return this.type() + " " + this.name(this.type() + " " + this);
}
char* generate(struct Content this){
	return generatePlaceholder(this.input);
}
char* generate(struct Whitespace implements Defined, Value this){
	return "";
}
char* generate(struct StringValue this){
	return "\"" + this.value + "\"";
}
char* generate(struct Symbol this){
	return this.value;
}
char* generate(struct Invocation this){
	return this.caller.generate() + "(" + generateValueList(this.args) + ")";
}
char* generate(struct DataAccess this){
	return this.parent.generate() + "." + this.property;
}
char* generate(struct Operation this){
	return this.left.generate() + " " + this.operator.representation + " " + this.right.generate(this.left.generate() + " " + this.operator.representation + " " + this.right);
}
auto fromArray_local0(auto index){
	return /* /* array[index] */ */;
}
Iterator<struct T> fromArray(struct Iterators this, /* T[] */ array){
	return /* Iterator<>(new RangeHead(array.length)).map */(fromArray_local0);
}
void main(struct Main this){
	/* try */{
		auto source = Paths.get(Paths, /* " */.", "src", "java", "magma", /* "Main */.java");
		auto target = source.resolveSibling(source, /* "main */.c");
		auto input = Files.readString(Files, source);
		/* Files.writeString(target, compileRoot(input)); */
	}
	/* catch (IOException e) */{
		/* e.printStackTrace(); */
	}
}
auto compileRoot_local1(auto tuple){
	if (expandables.containsKey(expandables, tuple.left)){
	auto compileRoot_local4 = expandable.apply(expandable, tuple.right);
		auto expandable = expandables.get(expandables, tuple.left);
		return compileRoot_local4.orElse(compileRoot_local4, "");
	}
	else {
		return "// " + tuple.left + "<" + join(tuple.right, ", ") + ">\n";
	}
}
char* compileRoot(struct Main this, char* input){
	auto compileRoot_local6 = expansions.iter(expansions);
	auto compileRoot_local7 = compileRoot_local6.map(compileRoot_local6, compileRoot_local1);
	auto compileRoot_local8 = compileRoot_local7.collect(compileRoot_local7, struct Joiner());
	auto compiled = compileStatements(input, /* Main::compileRootSegment */);
	auto joinedExpansions = compileRoot_local8.orElse(compileRoot_local8, "");
	return compiled + join(structs) + joinedExpansions + join(methods);
}
char* join(struct Main this, List<char*> list){
	return join(list, "");
}
char* join(struct Main this, List<char*> list, char* delimiter){
	auto join_local1 = list.iter(list);
	auto join_local2 = join_local1.collect(join_local1, struct Joiner(delimiter));
	return join_local2.orElse(join_local2, "");
}
char* compileStatements(struct Main this, char* input, char* (*)(char*) compiler){
	return generateStatements(parseStatements(input, compiler));
}
char* generateStatements(struct Main this, List<char*> parsed){
	return generateAll(/* Main::mergeStatements */, parsed);
}
List<char*> parseStatements(struct Main this, char* input, char* (*)(char*) compiler){
	return parseAll(input, /* Main::foldStatementChar */, compiler);
}
char* compileAll(struct Main this, char* input, struct State (*)(struct State, char) folder, char* (*)(char*) compiler, char* (*)(char*, char*) merger){
	return generateAll(merger, parseAll(input, folder, compiler));
}
char* generateAll(struct Main this, char* (*)(char*, char*) merger, List<char*> parsed){
	auto generateAll_local1 = parsed.iter(parsed);
	return generateAll_local1.fold(generateAll_local1, "", merger);
}
List<struct T> parseAll(struct Main this, char* input, struct State (*)(struct State, char) folder, struct T (*)(char*) compiler){
	auto parseAll_local0 = divideAll(input, folder);
	auto parseAll_local1 = parseAll_local0.iter(parseAll_local0);
	auto parseAll_local2 = parseAll_local1.map(parseAll_local1, compiler);
	return parseAll_local2.collect(parseAll_local2, ListCollector</*  */>());
}
char* mergeStatements(struct Main this, char* buffer, char* element){
	return buffer + element;
}
auto divideAll_local4(){
	return foldDoubleQuotes(withoutNext, next);
}
auto divideAll_local7(){
	return folder.apply(folder, folder, withoutNext, next);
}
List<char*> divideAll(struct Main this, char* input, struct State (*)(struct State, char) folder){
	struct State state = State.fromInput(State, input);
	while (1){
	auto divideAll_local5 = foldSingleQuotes(withoutNext, next);
	auto divideAll_local9 = divideAll_local5.or(divideAll_local5, divideAll_local4);
		auto maybeNextTuple = state.pop(state);
		if (maybeNextTuple.isEmpty(maybeNextTuple)){
			break;
		}
		auto nextTuple = maybeNextTuple.orElse(maybeNextTuple, null);
		auto next = nextTuple.left;
		auto withoutNext = nextTuple.right;
		state = divideAll_local9.orElseGet(divideAll_local9, divideAll_local7);
	}
	return state.advance(state).segments;
}
Option<struct State> foldDoubleQuotes(struct Main this, struct State withoutNext, struct char c){
	if (/* c != '\"' */){
		return None</*  */>();
	}
	auto current = withoutNext.append(withoutNext, c);
	while (1){
		auto maybeNext = current.popAndAppendToTuple(current);
		if (/* ! */(/* maybeNext instanceof Some */(/* var next */))){
			break;
		}
		current = next.right;
		if (next.left == '"'){
			break;
		}
		if (next.left == '\\'){
	auto foldDoubleQuotes_local3 = current.popAndAppend(current);
			current = foldDoubleQuotes_local3.orElse(foldDoubleQuotes_local3, current);
		}
	}
	return Some</*  */>(current);
}
auto foldSingleQuotes_local3(auto maybeSlash){
	return maybeSlash.left == '\\' ? maybeSlash.right.popAndAppend() : new Some<>(maybeSlash.left == '\\' ? maybeSlash.right, maybeSlash.left == '\\' ? maybeSlash.right, maybeSlash.right);
}
Option<struct State> foldSingleQuotes(struct Main this, struct State state, struct char next){
	auto foldSingleQuotes_local5 = appended.popAndAppendToTuple(appended);
	auto foldSingleQuotes_local6 = foldSingleQuotes_local5.flatMap(foldSingleQuotes_local5, foldSingleQuotes_local3);
	if (/* next != '\'' */){
		return None</*  */>();
	}
	auto appended = state.append(state, next);
	return foldSingleQuotes_local6.flatMap(foldSingleQuotes_local6, /* State::popAndAppend */);
}
struct State foldStatementChar(struct Main this, struct State state, struct char c){
	auto foldStatementChar_local2 = /* c == ';' && appended */;
	auto foldStatementChar_local5 = /* c == '}' && appended */;
	auto appended = state.append(state, c);
	if (foldStatementChar_local2.isLevel(foldStatementChar_local2)){
		return appended.advance(appended);
	}
	if (foldStatementChar_local5.isShallow(foldStatementChar_local5)){
	auto foldStatementChar_local4 = appended.advance(appended);
		return foldStatementChar_local4.exit(foldStatementChar_local4);
	}
	/* if (c == ' */{
		/* ' || */ c = /* = '(') {
            return appended */.enter();
	}
	if (/* c == '}' || c == ')' */){
		return appended.exit(appended);
	}
	return appended;
}
auto compileRootSegment_local3(){
	return generatePlaceholder(stripped);
}
char* compileRootSegment(struct Main this, char* input){
	auto compileRootSegment_local4 = compileClass(stripped);
	auto stripped = input.strip(input);
	if (stripped.isEmpty(stripped)){
		return "";
	}
	if (stripped.startsWith("package ") || stripped.startsWith(stripped.startsWith("package ") || stripped, "import ")){
		return "";
	}
	return compileRootSegment_local4.orElseGet(compileRootSegment_local4, compileRootSegment_local3);
}
Option<char*> compileClass(struct Main this, char* stripped){
	return compileStructure(stripped, "class ");
}
Option<char*> compileStructure(struct Main this, char* input, char* infix){
	auto classIndex = input.indexOf(input, infix);
	if (/* classIndex >= 0 */){
	auto compileStructure_local2 = input.substring(input, 0, classIndex);
	auto compileStructure_local3 = classIndex + infix;
		auto beforeClass = compileStructure_local2.strip(compileStructure_local2);
		auto afterClass = input.substring(input, compileStructure_local3.length(compileStructure_local3));
		auto contentStart = afterClass.indexOf(afterClass, "{");
		if (/* contentStart >= 0 */){
	auto compileStructure_local7 = afterClass.substring(afterClass, 0, contentStart);
	auto compileStructure_local9 = /* permitsIndex >= 0
                        ? beforeContent */;
	auto compileStructure_local11 = contentStart + "{";
	auto compileStructure_local13 = afterClass.substring(afterClass, compileStructure_local11.length(compileStructure_local11));
			auto beforeContent = compileStructure_local7.strip(compileStructure_local7);
			auto permitsIndex = beforeContent.indexOf(beforeContent, " permits");
			auto withoutPermits = compileStructure_local9.substring(compileStructure_local9, 0, permitsIndex).strip()
                        : beforeContent;
			auto paramStart = withoutPermits.indexOf(withoutPermits, "(");
			auto withEnd = compileStructure_local13.strip(compileStructure_local13);
			if (/* paramStart >= 0 */){
	auto compileStructure_local15 = withoutPermits.substring(withoutPermits, 0, paramStart);
				char* withoutParams = compileStructure_local15.strip(compileStructure_local15);
				return getString(withoutParams, beforeClass, withEnd);
			}
			else {
				return getString(withoutPermits, beforeClass, withEnd);
			}
		}
	}
	return None</*  */>();
}
Option<char*> getString(struct Main this, char* beforeContent, char* beforeClass, char* withEnd){
	auto getString_local0 = /* !withEnd */;
	if (getString_local0.endsWith(getString_local0, "}")){
		return None</*  */>();
	}
	auto content = withEnd.substring(withEnd, 0, withEnd.length() - "}".length(withEnd.length() - "}"));
	auto strippedBeforeContent = beforeContent.strip(beforeContent);
	if (strippedBeforeContent.endsWith(strippedBeforeContent, ">")){
		auto withoutEnd = strippedBeforeContent.substring(strippedBeforeContent, 0, strippedBeforeContent.length() - ">".length(strippedBeforeContent.length() - ">"));
		auto typeParamStart = withoutEnd.indexOf(withoutEnd, "<");
		if (/* typeParamStart >= 0 */){
	auto getString_local8 = withoutEnd.substring(withoutEnd, 0, typeParamStart);
	auto getString_local9 = typeParamStart + "<";
			auto name = getString_local8.strip(getString_local8);
			auto substring = withoutEnd.substring(withoutEnd, getString_local9.length(getString_local9));
			auto typeParameters = listFromArray(substring.split(substring, Pattern.quote(Pattern, ",")));
			return assembleStructure(typeParameters, name, beforeClass, content);
		}
	}
	return assembleStructure(listEmpty(), strippedBeforeContent, beforeClass, content);
}
Option<char*> assembleStructure(struct Main this, List<char*> typeParams, char* name, char* beforeClass, char* content){
	auto assembleStructure_local0 = /* !typeParams */;
	if (assembleStructure_local0.isEmpty(assembleStructure_local0)){
		/* -> { */ typeParameters = /* typeParams;
                typeArguments = typeArgs;

                var newName = name */ + "<" + join(typeArgs, ", ") + /* ">";
                return generateStructure */(newName, beforeClass, /* content);
            } */);
		return Some</*  */>("");
	}
	return generateStructure(name, beforeClass, content);
}
Option<char*> generateStructure(struct Main this, char* name, char* beforeClass, char* content){
	structNames = structNames.addLast(structNames, name);
	auto compiled = compileStatements(content, /* Main::compileClassSegment */);
	structNames = structNames.removeLast(structNames);
	auto generated = generatePlaceholder(beforeClass) + "struct " + name + " {" + compiled + "\n};\n";
	/* structs.addLast(generated); */
	return Some</*  */>("");
}
auto compileClassSegment_local2(){
	return compileStructure(stripped, "interface ");
}
auto compileClassSegment_local4(){
	return compileClass(stripped);
}
auto compileClassSegment_local6(){
	return compileMethod(stripped);
}
auto compileClassSegment_local8(){
	return compileDefinitionStatement(stripped);
}
auto compileClassSegment_local10(){
	return generatePlaceholder(stripped);
}
char* compileClassSegment(struct Main this, char* input){
	auto compileClassSegment_local3 = compileStructure(stripped, "record ");
	auto compileClassSegment_local5 = compileClassSegment_local3.or(compileClassSegment_local3, compileClassSegment_local2);
	auto compileClassSegment_local7 = compileClassSegment_local5.or(compileClassSegment_local5, compileClassSegment_local4);
	auto compileClassSegment_local9 = compileClassSegment_local7.or(compileClassSegment_local7, compileClassSegment_local6);
	auto compileClassSegment_local11 = compileClassSegment_local9.or(compileClassSegment_local9, compileClassSegment_local8);
	auto stripped = input.strip(input);
	if (stripped.isEmpty(stripped)){
		return "";
	}
	return compileClassSegment_local11.orElseGet(compileClassSegment_local11, compileClassSegment_local10);
}
Option<char*> compileDefinitionStatement(struct Main this, char* input){
	auto stripped = input.strip(input);
	if (stripped.endsWith(stripped, ";")){
		auto withoutEnd = stripped.substring(stripped, 0, stripped.length() - ";".length(stripped.length() - ";"));
		return Some</*  */>("\n\t" + compileDefinitionOrPlaceholder(withoutEnd) + ";");
	}
	return None</*  */>();
}
auto compileMethod_local12(auto parameter){
	return /* /* ! */ */(/* /* parameter instanceof Whitespace */ */);
}
Option<char*> compileMethod(struct Main this, char* stripped){
	auto compileMethod_local3 = paramStart + "(";
	auto compileMethod_local7 = paramEnd + ")";
	auto compileMethod_local11 = parseValues(params, /* Main::parseParameter */);
	auto compileMethod_local13 = compileMethod_local11.iter(compileMethod_local11);
	auto compileMethod_local14 = compileMethod_local13.filter(compileMethod_local13, compileMethod_local12);
	auto compileMethod_local16 = "struct " + structNames;
	auto compileMethod_local17 = Lists.<Defined>listEmpty(Lists);
	auto compileMethod_local18 = compileMethod_local17.addLast(compileMethod_local17, struct Definition(compileMethod_local16.last(compileMethod_local16), "this"));
	auto paramStart = stripped.indexOf(stripped, "(");
	if (/* paramStart < 0 */){
		return None</*  */>();
	}
	auto inputDefinition = stripped.substring(stripped, 0, paramStart);
	auto defined = parseDefinitionOrPlaceholder(inputDefinition);
	if (/* defined instanceof Definition definition */){
		functionName = definition.name;
		functionLocalCounter = 0;
	}
	auto outputDefinition = defined.generate(defined);
	auto afterParams = stripped.substring(stripped, compileMethod_local3.length(compileMethod_local3));
	auto paramEnd = afterParams.indexOf(afterParams, ")");
	if (/* paramEnd < 0 */){
		return None</*  */>();
	}
	auto params = afterParams.substring(afterParams, 0, paramEnd);
	auto withoutParams = afterParams.substring(afterParams, compileMethod_local7.length(compileMethod_local7));
	auto withBraces = withoutParams.strip(withoutParams);
	/* if (!withBraces.startsWith(" */{
		/* ") || !withBraces.endsWith("}")) {
            return new Some<>(""); */
	}
	auto content = withBraces.substring(withBraces, 1, withBraces.length() - 1);
	auto newParams = compileMethod_local14.collect(compileMethod_local14, ListCollector</*  */>());
	auto copy = compileMethod_local18.addAll(compileMethod_local18, newParams);
	auto outputParams = generateValueList(copy);
	return assembleMethod(outputDefinition, outputParams, content);
}
char* generateValueList(struct Main this, List<struct T> copy){
	auto generateValueList_local1 = copy.iter(copy);
	auto generateValueList_local2 = generateValueList_local1.map(generateValueList_local1, /* Node::generate */);
	auto generateValueList_local3 = generateValueList_local2.collect(generateValueList_local2, struct Joiner(", "));
	return generateValueList_local3.orElse(generateValueList_local3, "");
}
auto assembleMethod_local0(auto input){
	return compileFunctionSegment(input, 1);
}
Option<char*> assembleMethod(struct Main this, char* definition, char* outputParams, char* content){
	auto parsed = parseStatementsWithLocals(content, assembleMethod_local0);
	auto generated = definition + "(" + outputParams + "){" + generateStatements(parsed) + "\n}\n";
	/* methods.addLast(generated); */
	return Some</*  */>("");
}
List<char*> parseStatementsWithLocals(struct Main this, char* content, char* (*)(char*) compiler){
	auto parseStatementsWithLocals_local4 = Lists.<String>listEmpty(Lists);
	auto parseStatementsWithLocals_local5 = parseStatementsWithLocals_local4.addAll(parseStatementsWithLocals_local4, elements);
	statements = statements.addLast(statements, Lists.listEmpty(Lists));
	/* var parsed1 */ = parseStatements(content, compiler);
	auto elements = statements.removeAndGetLast(statements);
	return parseStatementsWithLocals_local5.addAll(parseStatementsWithLocals_local5, /* parsed1 */);
}
auto parseParameter_local0(auto value){
	return value;
}
auto parseParameter_local2(auto value){
	return value;
}
auto parseParameter_local4(){
	auto parseParameter_local5 = /* parseParameter_local3 */;
	return parseParameter_local5.map(parseParameter_local5, /* parseParameter_local3 */, /* parseParameter_local2 */);
}
auto parseParameter_local7(){
	return /* struct Content */(input);
}
struct Defined parseParameter(struct Main this, char* input){
	auto parseParameter_local1 = parseWhitespace(input);
	auto parseParameter_local3 = parseDefinition(input);
	auto parseParameter_local6 = parseParameter_local1.<Defined>map(parseParameter_local1, parseParameter_local0);
	auto parseParameter_local8 = parseParameter_local6.or(parseParameter_local6, parseParameter_local4);
	return parseParameter_local8.orElseGet(parseParameter_local8, parseParameter_local7);
}
Option<struct Whitespace> parseWhitespace(struct Main this, char* input){
	if (input.isBlank(input)){
		return Some</*  */>(struct Whitespace());
	}
	else {
		return None</*  */>();
	}
}
char* compileFunctionSegment(struct Main this, char* input, int depth){
	auto compileFunctionSegment_local2 = "\n" + "\t";
	auto stripped = input.strip(input);
	if (stripped.isEmpty(stripped)){
		return "";
	}
	auto indent = compileFunctionSegment_local2.repeat(compileFunctionSegment_local2, depth);
	if (stripped.endsWith(stripped, ";")){
	auto compileFunctionSegment_local5 = stripped.substring(stripped, 0, stripped.length() - ";".length(stripped.length() - ";"));
		auto withoutEnd = compileFunctionSegment_local5.strip(compileFunctionSegment_local5);
		auto maybeStatementValue = compileStatementValue(withoutEnd);
		if (/* maybeStatementValue instanceof Some */(/* var statementValue */)){
			return indent + statementValue + ";";
		}
	}
	if (stripped.endsWith(stripped, "}")){
		auto withoutEnd = stripped.substring(stripped, 0, stripped.length() - "}".length(stripped.length() - "}"));
		auto contentStart = withoutEnd.indexOf(withoutEnd, "{");
		if (/* contentStart >= 0 */){
	auto compileFunctionSegment_local11 = contentStart + "{";
			auto beforeBlock = withoutEnd.substring(withoutEnd, 0, contentStart);
			auto content = withoutEnd.substring(withoutEnd, compileFunctionSegment_local11.length(compileFunctionSegment_local11));
			auto outputContent = parseStatementsWithLocals(content, /* input1 -> compileFunctionSegment */(/* input1 */, depth + 1));
			return indent + compileBeforeBlock(beforeBlock) + "{" + join(outputContent) + indent + "}";
		}
	}
	return indent + generatePlaceholder(stripped);
}
Option<char*> compileStatementValue(struct Main this, char* input){
	auto stripped = input.strip(input);
	if (stripped.equals(stripped, "break")){
		return Some</*  */>("break");
	}
	if (stripped.startsWith(stripped, "return ")){
	auto compileStatementValue_local2 = "return ";
		auto value = stripped.substring(stripped, compileStatementValue_local2.length(compileStatementValue_local2));
		return Some</*  */>("return " + compileValue(value));
	}
	auto valueSeparator = stripped.indexOf(stripped, "=");
	if (/* valueSeparator >= 0 */){
	auto compileStatementValue_local7 = valueSeparator + "=";
		auto definition = stripped.substring(stripped, 0, valueSeparator);
		auto value = stripped.substring(stripped, compileStatementValue_local7.length(compileStatementValue_local7));
		auto assignable = parseAssignable(definition);
		return Some</*  */>(assignable.generate() + " = " + compileValue(assignable, value));
	}
	return None</*  */>();
}
auto parseAssignable_local1(){
	return parseValue(definition);
}
struct Assignable parseAssignable(struct Main this, char* definition){
	auto parseAssignable_local0 = parseDefinition(definition);
	auto parseAssignable_local2 = parseAssignable_local0.<Assignable>map(parseAssignable_local0, /* value1 -> value1 */);
	return parseAssignable_local2.orElseGet(parseAssignable_local2, parseAssignable_local1);
}
auto compileBeforeBlock_local2(){
	return compileConditional(stripped, "while");
}
auto compileBeforeBlock_local4(){
	return generatePlaceholder(stripped);
}
char* compileBeforeBlock(struct Main this, char* input){
	auto compileBeforeBlock_local3 = compileConditional(stripped, "if");
	auto compileBeforeBlock_local5 = compileBeforeBlock_local3.or(compileBeforeBlock_local3, compileBeforeBlock_local2);
	auto stripped = input.strip(input);
	if (stripped.equals(stripped, "else")){
		return "else ";
	}
	return compileBeforeBlock_local5.orElseGet(compileBeforeBlock_local5, compileBeforeBlock_local4);
}
Option<char*> compileConditional(struct Main this, char* stripped, char* prefix){
	if (stripped.startsWith(stripped, prefix)){
	auto compileConditional_local2 = stripped.substring(stripped, prefix.length(prefix));
		auto withoutPrefix = compileConditional_local2.strip(compileConditional_local2);
		if (withoutPrefix.startsWith("(") && withoutPrefix.endsWith(withoutPrefix.startsWith("(") && withoutPrefix, ")")){
			auto condition = withoutPrefix.substring(withoutPrefix, 1, withoutPrefix.length() - 1);
			return Some</*  */>(prefix + " (" + compileValue(condition) + ")");
		}
	}
	return None</*  */>();
}
char* compileValue(struct Main this, char* input){
	auto compileValue_local0 = parseValue(input);
	return compileValue_local0.generate(compileValue_local0);
}
auto parseValue_local17(auto value){
	auto parseValue_local18 = /* parseValue_local16 */;
	return parseValue_local18.isEmpty(parseValue_local18, /* parseValue_local16 */);
}
auto parseValue_local33(auto value){
	return /* /* ! */ */(/* /* value instanceof Whitespace */ */);
}
struct Value parseValue(struct Main this, char* input){
	auto stripped = input.strip(input);
	if (stripped.isEmpty(stripped)){
		return struct Whitespace();
	}
	if (stripped.equals(stripped, "false")){
		return BooleanValue.False;
	}
	if (stripped.equals(stripped, "true")){
		return BooleanValue.True;
	}
	auto arrowIndex = stripped.indexOf(stripped, "->");
	if (/* arrowIndex >= 0 */){
	auto parseValue_local6 = stripped.substring(stripped, 0, arrowIndex);
	auto parseValue_local7 = arrowIndex + "->";
	auto parseValue_local9 = stripped.substring(stripped, parseValue_local7.length(parseValue_local7));
		auto beforeArrow = parseValue_local6.strip(parseValue_local6);
		auto afterArrow = parseValue_local9.strip(parseValue_local9);
		if (isSymbol(beforeArrow)){
			return assembleLambda(afterArrow, Lists.listFrom(Lists, beforeArrow));
		}
		if (beforeArrow.startsWith("(") && beforeArrow.endsWith(beforeArrow.startsWith("(") && beforeArrow, ")")){
	auto parseValue_local13 = beforeArrow.substring(beforeArrow, 1, beforeArrow.length() - 1);
	auto parseValue_local15 = Iterators.fromArray(Iterators, parseValue_local13.split(parseValue_local13, Pattern.quote(Pattern, ",")));
	auto parseValue_local16 = /* !value */;
	auto parseValue_local19 = parseValue_local15.map(parseValue_local15, /* String::strip */);
	auto parseValue_local20 = parseValue_local19.filter(parseValue_local19, parseValue_local17);
			auto args = parseValue_local20.collect(parseValue_local20, ListCollector</*  */>());
			return assembleLambda(afterArrow, args);
		}
	}
	if (stripped.endsWith(stripped, ")")){
	auto parseValue_local24 = stripped.substring(stripped, 0, stripped.length() - ")".length(stripped.length() - ")"));
		auto withoutEnd = parseValue_local24.strip(parseValue_local24);
		auto divisions = divideAll(withoutEnd, /* Main::foldInvokableStart */);
		if (divisions.size() >= 2){
	auto parseValue_local32 = parseValues(arguments, /* Main::parseValue */);
	auto parseValue_local34 = parseValue_local32.iter(parseValue_local32);
	auto parseValue_local35 = parseValue_local34.filter(parseValue_local34, parseValue_local33);
	auto parseValue_local37 = Lists.<Value>listEmpty(Lists);
	auto parseValue_local38 = parseValue_local37.addLast(parseValue_local37, symbol);
			auto joined = join(divisions.subList(divisions, 0, divisions.size() - 1), "");
			auto caller = joined.substring(joined, 0, joined.length() - ")".length(joined.length() - ")"));
			auto arguments = divisions.last(divisions);
			/* Value parsedCaller; */
			if (caller.startsWith(caller, "new ")){
	auto parseValue_local29 = "new ";
				parsedCaller = struct Symbol(compileType(caller.substring(caller, parseValue_local29.length(parseValue_local29))));
			}
			else {
				parsedCaller = parseValue(caller);
			}
			auto parsedArgs = parseValue_local35.collect(parseValue_local35, ListCollector</*  */>());
			if (/* ! */(/* parsedCaller instanceof DataAccess */(/* var parent */, /* var property */))){
				return struct Invocation(parsedCaller, parsedArgs);
			}
			auto name = generateName();
			/* Value symbol; */
			if (/* parent instanceof Symbol || parent instanceof DataAccess */){
				symbol = parent;
			}
			else {
				auto statement = "\n\tauto " + name + " = " + parent.generate() + ";";
				/* statements.last().addLast(statement); */
				symbol = struct Symbol(name);
			}
			auto newArgs = parseValue_local38.addAll(parseValue_local38, parsedArgs);
			return struct Invocation(struct DataAccess(symbol, property), newArgs);
		}
	}
	if (isSymbol(stripped)){
		return struct Symbol(stripped);
	}
	if (isNumber(stripped)){
		return struct Symbol(stripped);
	}
	auto separator = stripped.lastIndexOf(stripped, /* " */.");
	if (/* separator >= 0 */){
	auto parseValue_local44 = stripped.substring(stripped, separator + /* " */.".length(separator + /* " */."));
		auto value = stripped.substring(stripped, 0, separator);
		auto property = parseValue_local44.strip(parseValue_local44);
		return struct DataAccess(parseValue(value), property);
	}
	if (stripped.length() >= 2 && stripped.startsWith("\"") && stripped.endsWith(stripped.length() >= 2 && stripped.startsWith("\"") && stripped, "\"")){
		return struct StringValue(stripped.substring(stripped, 1, stripped.length() - 1));
	}
	/* for (var operator : Operator.values()) */{
		auto operatorIndex = stripped.indexOf(stripped, operator.representation);
		if (/* operatorIndex >= 0 */){
			auto left = stripped.substring(stripped, 0, operatorIndex);
			auto right = stripped.substring(stripped, operatorIndex + operator.representation.length(operatorIndex + operator.representation));
			return struct Operation(parseValue(left), operator, parseValue(right));
		}
	}
	return struct Content(stripped);
}
auto assembleLambda_local1(auto name){
	return "auto " + name;
}
struct Symbol assembleLambda(struct Main this, char* afterArrow, List<char*> names){
	auto assembleLambda_local2 = names.iter(names);
	auto assembleLambda_local3 = assembleLambda_local2.map(assembleLambda_local2, assembleLambda_local1);
	auto assembleLambda_local4 = assembleLambda_local3.collect(assembleLambda_local3, struct Joiner(", "));
	auto params = assembleLambda_local4.orElse(assembleLambda_local4, "");
	/* if (afterArrow.startsWith(" */{
		/* ") && afterArrow.endsWith("}")) {
            var */ content = afterArrow.substring(1, afterArrow.length() - 1);
            var name = generateName();
            assembleMethod("auto " + name, params, content);
            return new Symbol(afterArrow.substring(1, afterArrow, name);
	}
	auto newValue = compileValue(afterArrow);
	auto name = generateName();
	/* assembleMethod("auto " + name, params, "\n\treturn " + newValue + ";"); */
	return struct Symbol(name);
}
char* generateName(struct Main this){
	auto name = functionName + "_local" + functionLocalCounter;
	/* functionLocalCounter++; */
	return name;
}
struct State foldInvokableStart(struct Main this, struct State state, char c){
	auto appended = state.append(state, c);
	if (/* c == '(' */){
		auto maybeAdvanced = appended.isLevel() ? appended.advance() : appended;
		return maybeAdvanced.enter(maybeAdvanced);
	}
	if (/* c == ')' */){
		return appended.exit(appended);
	}
	return appended;
}
int isNumber(struct Main this, char* input){
	if (input.isEmpty(input)){
		return 0;
	}
	/* for (var i = 0; i < input.length(); i++) */{
		auto c = input.charAt(input, i);
		if (Character.isDigit(Character, c)){
			/* continue; */
		}
		return 0;
	}
	return 1;
}
char* compileDefinitionOrPlaceholder(struct Main this, char* input){
	auto compileDefinitionOrPlaceholder_local0 = parseDefinitionOrPlaceholder(input);
	return compileDefinitionOrPlaceholder_local0.generate(compileDefinitionOrPlaceholder_local0);
}
auto parseDefinitionOrPlaceholder_local0(auto value){
	return value;
}
auto parseDefinitionOrPlaceholder_local2(){
	return /* struct Content */(input);
}
struct Defined parseDefinitionOrPlaceholder(struct Main this, char* input){
	auto parseDefinitionOrPlaceholder_local1 = parseDefinition(input);
	auto parseDefinitionOrPlaceholder_local3 = parseDefinitionOrPlaceholder_local1.<Defined>map(parseDefinitionOrPlaceholder_local1, parseDefinitionOrPlaceholder_local0);
	return parseDefinitionOrPlaceholder_local3.orElseGet(parseDefinitionOrPlaceholder_local3, parseDefinitionOrPlaceholder_local2);
}
Option<struct Definition> parseDefinition(struct Main this, char* input){
	auto parseDefinition_local3 = nameSeparator + " ";
	auto stripped = input.strip(input);
	auto nameSeparator = stripped.lastIndexOf(stripped, " ");
	if (/* nameSeparator < 0 */){
		return None</*  */>();
	}
	auto beforeName = stripped.substring(stripped, 0, nameSeparator);
	auto name = stripped.substring(stripped, parseDefinition_local3.length(parseDefinition_local3));
	if (/* !isSymbol */(name)){
		return None</*  */>();
	}
	auto divisions = divideAll(beforeName, /* Main::foldByTypeSeparator */);
	if (divisions.size() == 1){
		return Some</*  */>(struct Definition(compileType(beforeName), name));
	}
	auto beforeType = join(divisions.subList(divisions, 0, divisions.size() - 1), " ");
	auto type = divisions.last(divisions);
	return Some</*  */>(struct Definition(compileType(type), name));
}
struct State foldByTypeSeparator(struct Main this, struct State state, struct char c){
	auto foldByTypeSeparator_local1 = /* c == ' ' && state */;
	if (foldByTypeSeparator_local1.isLevel(foldByTypeSeparator_local1)){
		return state.advance(state);
	}
	auto appended = state.append(state, c);
	if (/* c == '<' */){
		return appended.enter(appended);
	}
	if (/* c == '>' */){
		return appended.exit(appended);
	}
	return appended;
}
char* compileType(struct Main this, char* input){
	auto stripped = input.strip(input);
	auto maybeTypeParamIndex = typeParameters.indexOf(typeParameters, stripped);
	if (maybeTypeParamIndex.isPresent(maybeTypeParamIndex)){
		auto typeParamIndex = maybeTypeParamIndex.orElse(maybeTypeParamIndex, null);
		return typeArguments.get(typeArguments, typeParamIndex);
	}
	/* switch (stripped) */{
		/* case "int", "boolean" -> */{
			return "int";
		}
		/* case "Character" -> */{
			return "char";
		}
		/* case "void" -> */{
			return "void";
		}
		/* case "String" -> */{
			return "char*";
		}
		/* case "var" -> */{
			return "auto";
		}
	}
	if (stripped.endsWith(stripped, ">")){
		auto withoutEnd = stripped.substring(stripped, 0, stripped.length() - ">".length(stripped.length() - ">"));
		auto index = withoutEnd.indexOf(withoutEnd, "<");
		if (/* index >= 0 */){
	auto compileType_local9 = withoutEnd.substring(withoutEnd, 0, index);
	auto compileType_local10 = index + "<";
	auto compileType_local20 = /* !expansions */;
			auto base = compileType_local9.strip(compileType_local9);
			auto substring = withoutEnd.substring(withoutEnd, compileType_local10.length(compileType_local10));
			auto parsed = parseValues(substring, /* Main::compileType */);
			if (base.equals(base, "Function")){
				/* var arg0 */ = parsed.get(parsed, 0);
				auto returns = parsed.get(parsed, 1);
				return returns + " (*)(" + arg0 + ")";
			}
			if (base.equals(base, "BiFunction")){
				/* var arg0 */ = parsed.get(parsed, 0);
				/* var arg1 */ = parsed.get(parsed, 1);
				auto returns = parsed.get(parsed, 2);
				return returns + " (*)(" + arg0 + ", " + arg1 + ")";
			}
			if (compileType_local20.contains(compileType_local20, Tuple</*  */>(base, parsed))){
				expansions = expansions.addLast(expansions, Tuple</*  */>(base, parsed));
			}
			return base + "<" + generateValues(parsed) + ">";
		}
	}
	if (isSymbol(stripped)){
		return "struct " + stripped;
	}
	return generatePlaceholder(stripped);
}
char* generateValues(struct Main this, List<char*> values){
	return generateAll(/* Main::mergeValues */, values);
}
List<struct T> parseValues(struct Main this, char* input, struct T (*)(char*) compiler){
	return parseAll(input, /* Main::foldValueChar */, compiler);
}
char* mergeValues(struct Main this, char* builder, char* element){
	if (builder.isEmpty(builder)){
		return builder + element;
	}
	return builder + ", " + element;
}
struct State foldValueChar(struct Main this, struct State state, struct char c){
	auto foldValueChar_local1 = /* c == ',' && state */;
	if (foldValueChar_local1.isLevel(foldValueChar_local1)){
		return state.advance(state);
	}
	auto appended = state.append(state, c);
	if (/* c == '-' */){
		if (appended.peek() instanceof Some(appended, /* var maybeArrow */)){
			if (/* maybeArrow == '>' */){
	auto foldValueChar_local4 = appended.popAndAppend(appended);
				return foldValueChar_local4.orElse(foldValueChar_local4, appended);
			}
		}
	}
	if (/* c == '<' || c == '(' */){
		return appended.enter(appended);
	}
	if (/* c == '>' || c == ')' */){
		return appended.exit(appended);
	}
	return appended;
}
int isSymbol(struct Main this, char* input){
	auto stripped = input.strip(input);
	if (stripped.isEmpty(stripped)){
		return 0;
	}
	/* for (var i = 0; i < stripped.length(); i++) */{
		auto c = stripped.charAt(stripped, i);
		if (Character.isLetter(Character, c)){
			/* continue; */
		}
		return 0;
	}
	return 1;
}
char* generatePlaceholder(struct Main this, char* input){
	return "/* " + input + " */";
}
Option<struct R> map(struct None</*  */> this, struct R (*)(/*  */) mapper){
	return None</*  */>();
}
int isPresent(struct None</*  */> this){
	return 0;
}
/*  */ orElse(struct None</*  */> this, /*  */ other){
	return other;
}
int isEmpty(struct None</*  */> this){
	return 1;
}
/*  */ orElseGet(struct None</*  */> this, Supplier</*  */> supplier){
	return supplier.get(supplier);
}
Option<struct R> flatMap(struct None</*  */> this, Option<struct R> (*)(/*  */) mapper){
	return None</*  */>();
}
Option</*  */> or(struct None</*  */> this, Supplier<Option</*  */>> supplier){
	return supplier.get(supplier);
}
Option<struct R> map(struct Some</*  */> this, struct R (*)(/*  */) mapper){
	return Some</*  */>(mapper.apply(mapper, this.value));
}
int isPresent(struct Some</*  */> this){
	return 1;
}
/*  */ orElse(struct Some</*  */> this, /*  */ other){
	return this.value;
}
int isEmpty(struct Some</*  */> this){
	return 0;
}
/*  */ orElseGet(struct Some</*  */> this, Supplier</*  */> supplier){
	return this.value;
}
Option<struct R> flatMap(struct Some</*  */> this, Option<struct R> (*)(/*  */) mapper){
	return mapper.apply(mapper, this.value);
}
Option</*  */> or(struct Some</*  */> this, Supplier<Option</*  */>> supplier){
	return this;
}
auto map_local2(){
	auto map_local3 = /* map_local1 */;
	return map_local3.map(map_local3, /* map_local1 */, mapper);
}
Iterator<struct R> map(struct Iterator<struct T> this, struct R (*)(struct T) mapper){
	auto map_local1 = this.head.next(this.head);
	return Iterator</*  */>(map_local2);
}
struct C collect(struct Iterator<struct T> this, Collector<struct T, struct C> collector){
	return this.fold(this, collector.createInitial(collector), /* collector::fold */);
}
auto fold_local2(auto next){
	return folder.apply(folder, folder, finalCurrent, next);
}
struct R fold(struct Iterator<struct T> this, struct R initial, struct R (*)(struct R, struct T) folder){
	auto current = initial;
	while (1){
	auto fold_local4 = this.head.next(this.head);
		struct R finalCurrent = current;
		auto optional = fold_local4.map(fold_local4, fold_local2);
		if (optional.isPresent(optional)){
			current = optional.orElse(optional, null);
		}
		else {
			return current;
		}
	}
}
auto filter_local1(auto element){
	return /* Iterator</*  */> */(predicate.test(element) ? new SingleHead<>(element) : new EmptyHead<>(predicate, predicate));
}
Iterator<struct T> filter(struct Iterator<struct T> this, Predicate<struct T> predicate){
	return this.flatMap(this, filter_local1);
}
Iterator<struct R> flatMap(struct Iterator<struct T> this, Iterator<struct R> (*)(struct T) mapper){
	auto flatMap_local1 = this.map(this, mapper);
	return flatMap_local1.fold(flatMap_local1, Iterator</*  */>(EmptyHead</*  */>()), /* Iterator::concat */);
}
auto concat_local2(){
	auto concat_local3 = /* concat_local1 */;
	return concat_local3.or(concat_local3, /* concat_local1 */, other.head::next);
}
Iterator<struct T> concat(struct Iterator<struct T> this, Iterator<struct T> other){
	auto concat_local1 = this.head.next(this.head);
	return Iterator</*  */>(concat_local2);
}
List<struct T> createInitial(struct ListCollector</*  */> this){
	return listEmpty();
}
List<struct T> fold(struct ListCollector</*  */> this, List<struct T> current, struct T element){
	return current.addLast(current, element);
}
