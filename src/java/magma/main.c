/* private */struct Defined extends Node {
};
/* private */struct Value extends Node {
};
/* private */struct Node {
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
/* private static */ struct State fromInput(struct State this, char* input){
	return struct State(input, listEmpty(), "", 0, 0);
}
auto popAndAppendToTuple_local1(auto tuple){
	struct var poppedChar = tuple.left;
	struct var poppedState = tuple.right;
	struct var appended = poppedState.append(poppedState, poppedChar);
	return Tuple</*  */>(poppedChar, appended);
}
/* private */ Option<Tuple<char, struct State>> popAndAppendToTuple(struct State this){
	auto popAndAppendToTuple_local3 = this.pop(this);
	return popAndAppendToTuple_local3.map(popAndAppendToTuple_local3, popAndAppendToTuple_local1);
}
/* private */ int isLevel(struct State this){
	return this.depth == 0;
}
/* private */ struct State enter(struct State this){
	return struct State(this.input, this.segments, this.buffer, this.depth + 1, this.index);
}
/* private */ struct State exit(struct State this){
	return struct State(this.input, this.segments, this.buffer, this.depth - 1, this.index);
}
/* private */ struct State advance(struct State this){
	return struct State(this.input, this.segments.addLast(this.segments, this.buffer), "", this.depth, this.index);
}
/* private */ int isShallow(struct State this){
	return this.depth == 1;
}
/* private */ Option<Tuple<char, struct State>> pop(struct State this){
	if (this.index >= this.input.length(this.index >= this.input)){
		return None</*  */>();
	}
	struct var escaped = this.input.charAt(this.input, this.index);
	return Some</*  */>(Tuple<char, struct State>(escaped, struct State(this.input, this.segments, this.buffer, this.depth, this.index + 1)));
}
/* private */ struct State append(struct State this, struct char c){
	return struct State(this.input, this.segments, this.buffer + c, this.depth, this.index);
}
/* public */ Option<struct State> popAndAppend(struct State this){
	auto popAndAppend_local1 = this.popAndAppendToTuple(this);
	return popAndAppend_local1.map(popAndAppend_local1, /* Tuple::right */);
}
/* public */ Option<char> peek(struct State this){
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
/* @Override
        public */ Option<char*> createInitial(struct Joiner this){
	return None</*  */>();
}
/* @Override
        public */ Option<char*> fold(struct Joiner this, Option<char*> current, char* element){
	auto fold_local1 = current.map(current, /* inner -> inner */ + this.delimiter + element);
	return Some</*  */>(fold_local1.orElse(fold_local1, element));
}
struct public Definition(struct Definition this, char* type, char* name){
	/* this(new None<>(), type, name); */
}
/* @Override
        public */ char* generate(struct Definition this){
	auto generate_local1 = this.beforeType(this);
	auto generate_local2 = generate_local1.map(generate_local1, /* Main::generatePlaceholder */);
	auto generate_local3 = generate_local2.map(generate_local2, /* inner -> inner */ + " ");
	struct var joined = generate_local3.orElse(generate_local3, "");
	return joined + this.type() + " " + this.name(joined + this.type() + " " + this);
}
/* @Override
        public */ char* generate(struct Content this){
	return generatePlaceholder(this.input);
}
/* @Override
        public */ char* generate(struct Whitespace implements Defined, Value this){
	return "";
}
/* @Override
        public */ char* generate(struct StringValue this){
	return "\"" + this.value + "\"";
}
/* @Override
        public */ char* generate(struct Symbol this){
	return this.value;
}
/* @Override
        public */ char* generate(struct Invocation this){
	return this.caller.generate() + "(" + generateValueList(this.args) + ")";
}
/* @Override
        public */ char* generate(struct DataAccess this){
	return this.parent.generate() + "." + this.property;
}
/* @Override
        public */ char* generate(struct Operation this){
	return this.left.generate() + " " + this.operator.representation + " " + this.right.generate(this.left.generate() + " " + this.operator.representation + " " + this.right);
}
/* public static */ void main(struct Main this){
	/* try */{
		struct var source = Paths.get(Paths, /* " */.", "src", "java", "magma", /* "Main */.java");
		struct var target = source.resolveSibling(source, /* "main */.c");
		struct var input = Files.readString(Files, source);
		/* Files.writeString(target, compileRoot(input)); */
	}
	/* catch (IOException e) */{
		/* e.printStackTrace(); */
	}
}
auto compileRoot_local1(auto tuple){
	if (expandables.containsKey(expandables, tuple.left)){
	auto compileRoot_local4 = expandable.apply(expandable, tuple.right);
		struct var expandable = expandables.get(expandables, tuple.left);
		return compileRoot_local4.orElse(compileRoot_local4, "");
	}
	else {
		return "// " + tuple.left + "<" + join(tuple.right, ", ") + ">\n";
	}
}
/* private static */ char* compileRoot(struct Main this, char* input){
	auto compileRoot_local6 = expansions.iter(expansions);
	auto compileRoot_local7 = compileRoot_local6.map(compileRoot_local6, compileRoot_local1);
	auto compileRoot_local8 = compileRoot_local7.collect(compileRoot_local7, struct Joiner());
	struct var compiled = compileStatements(input, /* Main::compileRootSegment */);
	struct var joinedExpansions = compileRoot_local8.orElse(compileRoot_local8, "");
	return compiled + join(structs) + joinedExpansions + join(methods);
}
/* private static */ char* join(struct Main this, List<char*> list){
	return join(list, "");
}
/* private static */ char* join(struct Main this, List<char*> list, char* delimiter){
	auto join_local1 = list.iter(list);
	auto join_local2 = join_local1.collect(join_local1, struct Joiner(delimiter));
	return join_local2.orElse(join_local2, "");
}
/* private static */ char* compileStatements(struct Main this, char* input, char* (*)(char*) compiler){
	return generateStatements(parseStatements(input, compiler));
}
/* private static */ char* generateStatements(struct Main this, List<char*> parsed){
	return generateAll(/* Main::mergeStatements */, parsed);
}
/* private static */ List<char*> parseStatements(struct Main this, char* input, char* (*)(char*) compiler){
	return parseAll(input, /* Main::foldStatementChar */, compiler);
}
/* private static */ char* compileAll(struct Main this, char* input, struct State (*)(struct State, char) folder, char* (*)(char*) compiler, char* (*)(char*, char*) merger){
	return generateAll(merger, parseAll(input, folder, compiler));
}
/* private static */ char* generateAll(struct Main this, char* (*)(char*, char*) merger, List<char*> parsed){
	auto generateAll_local1 = parsed.iter(parsed);
	return generateAll_local1.fold(generateAll_local1, "", merger);
}
/* private static <T> */ List<struct T> parseAll(struct Main this, char* input, struct State (*)(struct State, char) folder, struct T (*)(char*) compiler){
	auto parseAll_local0 = divideAll(input, folder);
	auto parseAll_local1 = parseAll_local0.iter(parseAll_local0);
	auto parseAll_local2 = parseAll_local1.map(parseAll_local1, compiler);
	return parseAll_local2.collect(parseAll_local2, ListCollector</*  */>());
}
/* private static */ char* mergeStatements(struct Main this, char* buffer, char* element){
	return buffer + element;
}
/* private static */ List<char*> divideAll(struct Main this, char* input, struct State (*)(struct State, char) folder){
	struct State state = State.fromInput(State, input);
	/* while (true) */{
	auto divideAll_local4 = foldSingleQuotes(withoutNext, next);
	auto divideAll_local5 = /* () -> folder */;
	auto divideAll_local6 = divideAll_local4.or(divideAll_local4, /* () -> foldDoubleQuotes */(withoutNext, next));
		struct var maybeNextTuple = state.pop(state);
		if (maybeNextTuple.isEmpty(maybeNextTuple)){
			/* break; */
		}
		struct var nextTuple = maybeNextTuple.orElse(maybeNextTuple, null);
		struct var next = nextTuple.left;
		struct var withoutNext = nextTuple.right;
		/* state  */ = divideAll_local6.orElseGet(divideAll_local6, divideAll_local5.apply(divideAll_local5, withoutNext, next));
	}
	return state.advance(state).segments;
}
/* private static */ Option<struct State> foldDoubleQuotes(struct Main this, struct State withoutNext, struct char c){
	if (/* c != '\"' */){
		return None</*  */>();
	}
	struct var current = withoutNext.append(withoutNext, c);
	/* while (true) */{
		struct var maybeNext = current.popAndAppendToTuple(current);
		if (/* ! */(/* maybeNext instanceof Some */(/* var next */))){
			/* break; */
		}
		/* current  */ = next.right;
		if (next.left == '"'){
			/* break; */
		}
		if (next.left == '\\'){
	auto foldDoubleQuotes_local3 = current.popAndAppend(current);
			/* current  */ = foldDoubleQuotes_local3.orElse(foldDoubleQuotes_local3, current);
		}
	}
	return Some</*  */>(current);
}
/* private static */ Option<struct State> foldSingleQuotes(struct Main this, struct State state, struct char next){
	auto foldSingleQuotes_local3 = appended.popAndAppendToTuple(appended);
	auto foldSingleQuotes_local4 = foldSingleQuotes_local3.flatMap(foldSingleQuotes_local3, /* maybeSlash -> maybeSlash */.left == '\\' ? maybeSlash.right.popAndAppend() : new Some<>(/* maybeSlash -> maybeSlash */.left == '\\' ? maybeSlash.right, maybeSlash.right));
	if (/* next != '\'' */){
		return None</*  */>();
	}
	struct var appended = state.append(state, next);
	return foldSingleQuotes_local4.flatMap(foldSingleQuotes_local4, /* State::popAndAppend */);
}
/* private static */ struct State foldStatementChar(struct Main this, struct State state, struct char c){
	auto foldStatementChar_local2 = /* c == ';' && appended */;
	auto foldStatementChar_local5 = /* c == '}' && appended */;
	struct var appended = state.append(state, c);
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
/* private static */ char* compileRootSegment(struct Main this, char* input){
	auto compileRootSegment_local3 = compileClass(stripped);
	struct var stripped = input.strip(input);
	if (stripped.isEmpty(stripped)){
		return "";
	}
	if (stripped.startsWith("package ") || stripped.startsWith(stripped.startsWith("package ") || stripped, "import ")){
		return "";
	}
	return compileRootSegment_local3.orElseGet(compileRootSegment_local3, /* () -> generatePlaceholder */(stripped));
}
/* private static */ Option<char*> compileClass(struct Main this, char* stripped){
	return compileStructure(stripped, "class ");
}
/* private static */ Option<char*> compileStructure(struct Main this, char* input, char* infix){
	struct var classIndex = input.indexOf(input, infix);
	if (/* classIndex >= 0 */){
	auto compileStructure_local2 = input.substring(input, 0, classIndex);
	auto compileStructure_local3 = classIndex + infix;
		struct var beforeClass = compileStructure_local2.strip(compileStructure_local2);
		struct var afterClass = input.substring(input, compileStructure_local3.length(compileStructure_local3));
		struct var contentStart = afterClass.indexOf(afterClass, "{");
		if (/* contentStart >= 0 */){
	auto compileStructure_local7 = afterClass.substring(afterClass, 0, contentStart);
	auto compileStructure_local9 = /* permitsIndex >= 0
                        ? beforeContent */;
	auto compileStructure_local11 = contentStart + "{";
	auto compileStructure_local13 = afterClass.substring(afterClass, compileStructure_local11.length(compileStructure_local11));
			struct var beforeContent = compileStructure_local7.strip(compileStructure_local7);
			struct var permitsIndex = beforeContent.indexOf(beforeContent, " permits");
			struct var withoutPermits = compileStructure_local9.substring(compileStructure_local9, 0, permitsIndex).strip()
                        : beforeContent;
			struct var paramStart = withoutPermits.indexOf(withoutPermits, "(");
			struct var withEnd = compileStructure_local13.strip(compileStructure_local13);
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
/* private static */ Option<char*> getString(struct Main this, char* beforeContent, char* beforeClass, char* withEnd){
	auto getString_local0 = /* !withEnd */;
	if (getString_local0.endsWith(getString_local0, "}")){
		return None</*  */>();
	}
	struct var content = withEnd.substring(withEnd, 0, withEnd.length() - "}".length(withEnd.length() - "}"));
	struct var strippedBeforeContent = beforeContent.strip(beforeContent);
	if (strippedBeforeContent.endsWith(strippedBeforeContent, ">")){
		struct var withoutEnd = strippedBeforeContent.substring(strippedBeforeContent, 0, strippedBeforeContent.length() - ">".length(strippedBeforeContent.length() - ">"));
		struct var typeParamStart = withoutEnd.indexOf(withoutEnd, "<");
		if (/* typeParamStart >= 0 */){
	auto getString_local8 = withoutEnd.substring(withoutEnd, 0, typeParamStart);
	auto getString_local9 = typeParamStart + "<";
			struct var name = getString_local8.strip(getString_local8);
			struct var substring = withoutEnd.substring(withoutEnd, getString_local9.length(getString_local9));
			struct var typeParameters = listFromArray(substring.split(substring, Pattern.quote(Pattern, ",")));
			return assembleStructure(typeParameters, name, beforeClass, content);
		}
	}
	return assembleStructure(listEmpty(), strippedBeforeContent, beforeClass, content);
}
/* private static */ Option<char*> assembleStructure(struct Main this, List<char*> typeParams, char* name, char* beforeClass, char* content){
	auto assembleStructure_local0 = /* !typeParams */;
	if (assembleStructure_local0.isEmpty(assembleStructure_local0)){
		/* expandables.put(name, typeArgs */ /* -> { */ typeParameters = /* typeParams;
                typeArguments = typeArgs;

                var newName = name */ + "<" + join(typeArgs, ", ") + /* ">";
                return generateStructure */(newName, beforeClass, /* content);
            } */);
		return Some</*  */>("");
	}
	return generateStructure(name, beforeClass, content);
}
/* private static */ Option<char*> generateStructure(struct Main this, char* name, char* beforeClass, char* content){
	/* structNames  */ = structNames.addLast(structNames, name);
	struct var compiled = compileStatements(content, /* Main::compileClassSegment */);
	/* structNames  */ = structNames.removeLast(structNames);
	struct var generated = generatePlaceholder(beforeClass) + "struct " + name + " {" + compiled + "\n};\n";
	/* structs.addLast(generated); */
	return Some</*  */>("");
}
/* private static */ char* compileClassSegment(struct Main this, char* input){
	auto compileClassSegment_local2 = compileStructure(stripped, "record ");
	auto compileClassSegment_local3 = compileClassSegment_local2.or(compileClassSegment_local2, /* () -> compileStructure */(stripped, "interface "));
	auto compileClassSegment_local4 = compileClassSegment_local3.or(compileClassSegment_local3, /* () -> compileClass */(stripped));
	auto compileClassSegment_local5 = compileClassSegment_local4.or(compileClassSegment_local4, /* () -> compileMethod */(stripped));
	auto compileClassSegment_local6 = compileClassSegment_local5.or(compileClassSegment_local5, /* () -> compileDefinitionStatement */(stripped));
	struct var stripped = input.strip(input);
	if (stripped.isEmpty(stripped)){
		return "";
	}
	return compileClassSegment_local6.orElseGet(compileClassSegment_local6, /* () -> generatePlaceholder */(stripped));
}
/* private static */ Option<char*> compileDefinitionStatement(struct Main this, char* input){
	struct var stripped = input.strip(input);
	if (stripped.endsWith(stripped, ";")){
		struct var withoutEnd = stripped.substring(stripped, 0, stripped.length() - ";".length(stripped.length() - ";"));
		return Some</*  */>("\n\t" + compileDefinitionOrPlaceholder(withoutEnd) + ";");
	}
	return None</*  */>();
}
/* private static */ Option<char*> compileMethod(struct Main this, char* stripped){
	auto compileMethod_local3 = paramStart + "(";
	auto compileMethod_local7 = paramEnd + ")";
	auto compileMethod_local11 = parseValues(params, /* Main::parseParameter */);
	auto compileMethod_local12 = compileMethod_local11.iter(compileMethod_local11);
	auto compileMethod_local13 = compileMethod_local12.filter(compileMethod_local12, /* parameter -> ! */(/* parameter instanceof Whitespace */));
	auto compileMethod_local15 = "struct " + structNames;
	auto compileMethod_local16 = Lists.<Defined>listEmpty(Lists);
	auto compileMethod_local17 = compileMethod_local16.addLast(compileMethod_local16, struct Definition(compileMethod_local15.last(compileMethod_local15), "this"));
	struct var paramStart = stripped.indexOf(stripped, "(");
	if (/* paramStart < 0 */){
		return None</*  */>();
	}
	struct var inputDefinition = stripped.substring(stripped, 0, paramStart);
	struct var defined = parseDefinitionOrPlaceholder(inputDefinition);
	if (/* defined instanceof Definition definition */){
		/* functionName  */ = definition.name;
		/* functionLocalCounter  */ = 0;
	}
	struct var outputDefinition = defined.generate(defined);
	struct var afterParams = stripped.substring(stripped, compileMethod_local3.length(compileMethod_local3));
	struct var paramEnd = afterParams.indexOf(afterParams, ")");
	if (/* paramEnd < 0 */){
		return None</*  */>();
	}
	struct var params = afterParams.substring(afterParams, 0, paramEnd);
	struct var withoutParams = afterParams.substring(afterParams, compileMethod_local7.length(compileMethod_local7));
	struct var withBraces = withoutParams.strip(withoutParams);
	/* if (!withBraces.startsWith(" */{
		/* ") || !withBraces.endsWith("}")) {
            return new Some<>(""); */
	}
	struct var content = withBraces.substring(withBraces, 1, withBraces.length() - 1);
	struct var newParams = compileMethod_local13.collect(compileMethod_local13, ListCollector</*  */>());
	struct var copy = compileMethod_local17.addAll(compileMethod_local17, newParams);
	struct var outputParams = generateValueList(copy);
	return assembleMethod(outputDefinition, outputParams, content);
}
/* private static <T extends Node> */ char* generateValueList(struct Main this, List<struct T> copy){
	auto generateValueList_local1 = copy.iter(copy);
	auto generateValueList_local2 = generateValueList_local1.map(generateValueList_local1, /* Node::generate */);
	auto generateValueList_local3 = generateValueList_local2.collect(generateValueList_local2, struct Joiner(", "));
	return generateValueList_local3.orElse(generateValueList_local3, "");
}
/* private static */ Option<char*> assembleMethod(struct Main this, char* definition, char* outputParams, char* content){
	struct var parsed = parseStatementsWithLocals(content, /* input -> compileFunctionSegment */(input, 1));
	struct var generated = definition + "(" + outputParams + "){" + generateStatements(parsed) + "\n}\n";
	/* methods.addLast(generated); */
	return Some</*  */>("");
}
/* private static */ List<char*> parseStatementsWithLocals(struct Main this, char* content, char* (*)(char*) compiler){
	auto parseStatementsWithLocals_local4 = Lists.<String>listEmpty(Lists);
	auto parseStatementsWithLocals_local5 = parseStatementsWithLocals_local4.addAll(parseStatementsWithLocals_local4, elements);
	/* statements  */ = statements.addLast(statements, Lists.listEmpty(Lists));
	/* var parsed1  */ = parseStatements(content, compiler);
	struct var elements = statements.removeAndGetLast(statements);
	return parseStatementsWithLocals_local5.addAll(parseStatementsWithLocals_local5, /* parsed1 */);
}
/* private static */ struct Defined parseParameter(struct Main this, char* input){
	auto parseParameter_local0 = parseWhitespace(input);
	auto parseParameter_local1 = /* () -> parseDefinition */(input);
	auto parseParameter_local2 = parseParameter_local0.<Defined>map(parseParameter_local0, /* value -> value */);
	auto parseParameter_local3 = parseParameter_local2.or(parseParameter_local2, parseParameter_local1.map(parseParameter_local1, /* value -> value */));
	return parseParameter_local3.orElseGet(parseParameter_local3, /* () -> new Content */(input));
}
/* private static */ Option<struct Whitespace> parseWhitespace(struct Main this, char* input){
	if (input.isBlank(input)){
		return Some</*  */>(struct Whitespace());
	}
	else {
		return None</*  */>();
	}
}
/* private static */ char* compileFunctionSegment(struct Main this, char* input, int depth){
	auto compileFunctionSegment_local2 = "\n" + "\t";
	struct var stripped = input.strip(input);
	if (stripped.isEmpty(stripped)){
		return "";
	}
	struct var indent = compileFunctionSegment_local2.repeat(compileFunctionSegment_local2, depth);
	if (stripped.endsWith(stripped, ";")){
	auto compileFunctionSegment_local5 = stripped.substring(stripped, 0, stripped.length() - ";".length(stripped.length() - ";"));
		struct var withoutEnd = compileFunctionSegment_local5.strip(compileFunctionSegment_local5);
		struct var maybeStatementValue = compileStatementValue(withoutEnd);
		if (/* maybeStatementValue instanceof Some */(/* var statementValue */)){
			return indent + statementValue + ";";
		}
	}
	if (stripped.endsWith(stripped, "}")){
		struct var withoutEnd = stripped.substring(stripped, 0, stripped.length() - "}".length(stripped.length() - "}"));
		struct var contentStart = withoutEnd.indexOf(withoutEnd, "{");
		if (/* contentStart >= 0 */){
	auto compileFunctionSegment_local11 = contentStart + "{";
			struct var beforeBlock = withoutEnd.substring(withoutEnd, 0, contentStart);
			struct var content = withoutEnd.substring(withoutEnd, compileFunctionSegment_local11.length(compileFunctionSegment_local11));
			struct var outputContent = parseStatementsWithLocals(content, /* input1 -> compileFunctionSegment */(/* input1 */, depth + 1));
			return indent + compileBeforeBlock(beforeBlock) + "{" + join(outputContent) + indent + "}";
		}
	}
	return indent + generatePlaceholder(stripped);
}
/* private static */ Option<char*> compileStatementValue(struct Main this, char* input){
	if (input.startsWith(input, "return ")){
	auto compileStatementValue_local0 = "return ";
		struct var value = input.substring(input, compileStatementValue_local0.length(compileStatementValue_local0));
		return Some</*  */>("return " + compileValue(value));
	}
	struct var valueSeparator = input.indexOf(input, "=");
	if (/* valueSeparator >= 0 */){
	auto compileStatementValue_local5 = valueSeparator + "=";
	auto compileStatementValue_local7 = parseDefinitionOrPlaceholder(definition);
		struct var definition = input.substring(input, 0, valueSeparator);
		struct var value = input.substring(input, compileStatementValue_local5.length(compileStatementValue_local5));
		return Some</*  */>(compileStatementValue_local7.generate() + " = " + compileValue(compileStatementValue_local7, value));
	}
	return None</*  */>();
}
/* private static */ char* compileBeforeBlock(struct Main this, char* input){
	struct var stripped = input.strip(input);
	if (stripped.startsWith(stripped, "if")){
	auto compileBeforeBlock_local1 = "if";
	auto compileBeforeBlock_local3 = stripped.substring(stripped, compileBeforeBlock_local1.length(compileBeforeBlock_local1));
		struct var withoutPrefix = compileBeforeBlock_local3.strip(compileBeforeBlock_local3);
		if (withoutPrefix.startsWith("(") && withoutPrefix.endsWith(withoutPrefix.startsWith("(") && withoutPrefix, ")")){
			struct var condition = withoutPrefix.substring(withoutPrefix, 1, withoutPrefix.length() - 1);
			return "if (" + compileValue(condition) + ")";
		}
	}
	if (stripped.equals(stripped, "else")){
		return "else ";
	}
	return generatePlaceholder(stripped);
}
/* private static */ char* compileValue(struct Main this, char* input){
	auto compileValue_local0 = parseValue(input);
	return compileValue_local0.generate(compileValue_local0);
}
/* private static */ struct Value parseValue(struct Main this, char* input){
	struct var stripped = input.strip(input);
	if (stripped.isEmpty(stripped)){
		return struct Whitespace();
	}
	if (stripped.endsWith(stripped, ")")){
	auto parseValue_local4 = stripped.substring(stripped, 0, stripped.length() - ")".length(stripped.length() - ")"));
		struct var withoutEnd = parseValue_local4.strip(parseValue_local4);
		struct var divisions = divideAll(withoutEnd, /* Main::foldInvokableStart */);
		if (divisions.size() >= 2){
	auto parseValue_local12 = parseValues(arguments, /* Main::parseValue */);
	auto parseValue_local13 = parseValue_local12.iter(parseValue_local12);
	auto parseValue_local14 = parseValue_local13.filter(parseValue_local13, /* value -> ! */(/* value instanceof Whitespace */));
	auto parseValue_local16 = Lists.<Value>listEmpty(Lists);
	auto parseValue_local17 = parseValue_local16.addLast(parseValue_local16, symbol);
			struct var joined = join(divisions.subList(divisions, 0, divisions.size() - 1), "");
			struct var caller = joined.substring(joined, 0, joined.length() - ")".length(joined.length() - ")"));
			struct var arguments = divisions.last(divisions);
			/* Value parsedCaller; */
			if (caller.startsWith(caller, "new ")){
	auto parseValue_local9 = "new ";
				/* parsedCaller  */ = struct Symbol(compileType(caller.substring(caller, parseValue_local9.length(parseValue_local9))));
			}
			else {
				/* parsedCaller  */ = parseValue(caller);
			}
			struct var parsedArgs = parseValue_local14.collect(parseValue_local14, ListCollector</*  */>());
			if (/* ! */(/* parsedCaller instanceof DataAccess */(/* var parent */, /* var property */))){
				return struct Invocation(parsedCaller, parsedArgs);
			}
			struct var name = generateName();
			/* Value symbol; */
			if (/* parent instanceof Symbol || parent instanceof DataAccess */){
				/* symbol  */ = parent;
			}
			else {
				struct var statement = "\n\tauto " + name + " = " + parent.generate() + ";";
				/* statements.last().addLast(statement); */
				/* symbol  */ = struct Symbol(name);
			}
			struct var newArgs = parseValue_local17.addAll(parseValue_local17, parsedArgs);
			return struct Invocation(struct DataAccess(symbol, property), newArgs);
		}
	}
	if (isSymbol(stripped)){
		return struct Symbol(stripped);
	}
	if (isNumber(stripped)){
		return struct Symbol(stripped);
	}
	struct var arrowIndex = stripped.indexOf(stripped, "->");
	if (/* arrowIndex >= 0 */){
	auto parseValue_local21 = stripped.substring(stripped, 0, arrowIndex);
	auto parseValue_local22 = arrowIndex + "->";
	auto parseValue_local24 = stripped.substring(stripped, parseValue_local22.length(parseValue_local22));
		struct var beforeArrow = parseValue_local21.strip(parseValue_local21);
		struct var afterArrow = parseValue_local24.strip(parseValue_local24);
		/* if (afterArrow.startsWith(" */{
			/* ") && afterArrow.endsWith("}")) {
                var */ content = afterArrow.substring(1, afterArrow.length() - 1);
                var name = generateName();
                assembleMethod("auto " + name, "auto " + beforeArrow, content);
                return new Symbol(afterArrow.substring(1, afterArrow, name);
		}
	}
	struct var separator = stripped.lastIndexOf(stripped, /* " */.");
	if (/* separator >= 0 */){
	auto parseValue_local30 = stripped.substring(stripped, separator + /* " */.".length(separator + /* " */."));
		struct var value = stripped.substring(stripped, 0, separator);
		struct var property = parseValue_local30.strip(parseValue_local30);
		return struct DataAccess(parseValue(value), property);
	}
	if (stripped.length() >= 2 && stripped.startsWith("\"") && stripped.endsWith(stripped.length() >= 2 && stripped.startsWith("\"") && stripped, "\"")){
		return struct StringValue(stripped.substring(stripped, 1, stripped.length() - 1));
	}
	/* for (var operator : Operator.values()) */{
		struct var operatorIndex = stripped.indexOf(stripped, operator.representation);
		if (/* operatorIndex >= 0 */){
			struct var left = stripped.substring(stripped, 0, operatorIndex);
			struct var right = stripped.substring(stripped, operatorIndex + operator.representation.length(operatorIndex + operator.representation));
			return struct Operation(parseValue(left), operator, parseValue(right));
		}
	}
	return struct Content(stripped);
}
/* private static */ char* generateName(struct Main this){
	struct var name = functionName + "_local" + functionLocalCounter;
	/* functionLocalCounter++; */
	return name;
}
/* private static */ struct State foldInvokableStart(struct Main this, struct State state, char c){
	struct var appended = state.append(state, c);
	if (/* c == '(' */){
		struct var maybeAdvanced = appended.isLevel() ? appended.advance() : appended;
		return maybeAdvanced.enter(maybeAdvanced);
	}
	if (/* c == ')' */){
		return appended.exit(appended);
	}
	return appended;
}
/* private static */ int isNumber(struct Main this, char* input){
	if (input.isEmpty(input)){
		return false;
	}
	/* for (var i = 0; i < input.length(); i++) */{
		struct var c = input.charAt(input, i);
		if (Character.isDigit(Character, c)){
			/* continue; */
		}
		return false;
	}
	return true;
}
/* private static */ char* compileDefinitionOrPlaceholder(struct Main this, char* input){
	auto compileDefinitionOrPlaceholder_local0 = parseDefinitionOrPlaceholder(input);
	return compileDefinitionOrPlaceholder_local0.generate(compileDefinitionOrPlaceholder_local0);
}
/* private static */ struct Defined parseDefinitionOrPlaceholder(struct Main this, char* input){
	auto parseDefinitionOrPlaceholder_local0 = parseDefinition(input);
	auto parseDefinitionOrPlaceholder_local1 = parseDefinitionOrPlaceholder_local0.<Defined>map(parseDefinitionOrPlaceholder_local0, /* value -> value */);
	return parseDefinitionOrPlaceholder_local1.orElseGet(parseDefinitionOrPlaceholder_local1, /* () -> new Content */(input));
}
/* private static */ Option<struct Definition> parseDefinition(struct Main this, char* input){
	auto parseDefinition_local3 = nameSeparator + " ";
	struct var stripped = input.strip(input);
	struct var nameSeparator = stripped.lastIndexOf(stripped, " ");
	if (/* nameSeparator < 0 */){
		return None</*  */>();
	}
	struct var beforeName = stripped.substring(stripped, 0, nameSeparator);
	struct var name = stripped.substring(stripped, parseDefinition_local3.length(parseDefinition_local3));
	if (/* !isSymbol */(name)){
		return None</*  */>();
	}
	struct var divisions = divideAll(beforeName, /* Main::foldByTypeSeparator */);
	if (divisions.size() == 1){
		return Some</*  */>(struct Definition(None</*  */>(), compileType(beforeName), name));
	}
	struct var beforeType = join(divisions.subList(divisions, 0, divisions.size() - 1), " ");
	struct var type = divisions.last(divisions);
	return Some</*  */>(struct Definition(Some</*  */>(beforeType), compileType(type), name));
}
/* private static */ struct State foldByTypeSeparator(struct Main this, struct State state, struct char c){
	auto foldByTypeSeparator_local1 = /* c == ' ' && state */;
	if (foldByTypeSeparator_local1.isLevel(foldByTypeSeparator_local1)){
		return state.advance(state);
	}
	struct var appended = state.append(state, c);
	if (/* c == '<' */){
		return appended.enter(appended);
	}
	if (/* c == '>' */){
		return appended.exit(appended);
	}
	return appended;
}
/* private static */ char* compileType(struct Main this, char* input){
	struct var stripped = input.strip(input);
	struct var maybeTypeParamIndex = typeParameters.indexOf(typeParameters, stripped);
	if (maybeTypeParamIndex.isPresent(maybeTypeParamIndex)){
		struct var typeParamIndex = maybeTypeParamIndex.orElse(maybeTypeParamIndex, null);
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
	}
	if (stripped.endsWith(stripped, ">")){
		struct var withoutEnd = stripped.substring(stripped, 0, stripped.length() - ">".length(stripped.length() - ">"));
		struct var index = withoutEnd.indexOf(withoutEnd, "<");
		if (/* index >= 0 */){
	auto compileType_local9 = withoutEnd.substring(withoutEnd, 0, index);
	auto compileType_local10 = index + "<";
	auto compileType_local20 = /* !expansions */;
			struct var base = compileType_local9.strip(compileType_local9);
			struct var substring = withoutEnd.substring(withoutEnd, compileType_local10.length(compileType_local10));
			struct var parsed = parseValues(substring, /* Main::compileType */);
			if (base.equals(base, "Function")){
				/* var arg0  */ = parsed.get(parsed, 0);
				struct var returns = parsed.get(parsed, 1);
				return returns + " (*)(" + arg0 + ")";
			}
			if (base.equals(base, "BiFunction")){
				/* var arg0  */ = parsed.get(parsed, 0);
				/* var arg1  */ = parsed.get(parsed, 1);
				struct var returns = parsed.get(parsed, 2);
				return returns + " (*)(" + arg0 + ", " + arg1 + ")";
			}
			if (compileType_local20.contains(compileType_local20, Tuple</*  */>(base, parsed))){
				/* expansions  */ = expansions.addLast(expansions, Tuple</*  */>(base, parsed));
			}
			return base + "<" + generateValues(parsed) + ">";
		}
	}
	if (isSymbol(stripped)){
		return "struct " + stripped;
	}
	return generatePlaceholder(stripped);
}
/* private static */ char* generateValues(struct Main this, List<char*> values){
	return generateAll(/* Main::mergeValues */, values);
}
/* private static <T> */ List<struct T> parseValues(struct Main this, char* input, struct T (*)(char*) compiler){
	return parseAll(input, /* Main::foldValueChar */, compiler);
}
/* private static */ char* mergeValues(struct Main this, char* builder, char* element){
	if (builder.isEmpty(builder)){
		return builder + element;
	}
	return builder + ", " + element;
}
/* private static */ struct State foldValueChar(struct Main this, struct State state, struct char c){
	auto foldValueChar_local1 = /* c == ',' && state */;
	if (foldValueChar_local1.isLevel(foldValueChar_local1)){
		return state.advance(state);
	}
	struct var appended = state.append(state, c);
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
/* private static */ int isSymbol(struct Main this, char* input){
	struct var stripped = input.strip(input);
	if (stripped.isEmpty(stripped)){
		return false;
	}
	/* for (var i = 0; i < stripped.length(); i++) */{
		struct var c = stripped.charAt(stripped, i);
		if (Character.isLetter(Character, c)){
			/* continue; */
		}
		return false;
	}
	return true;
}
/* private static */ char* generatePlaceholder(struct Main this, char* input){
	return "/* " + input + " */";
}
/* @Override
        public <R> */ Option<struct R> map(struct None</*  */> this, struct R (*)(/*  */) mapper){
	return None</*  */>();
}
/* @Override
        public */ int isPresent(struct None</*  */> this){
	return false;
}
/* @Override
        public */ /*  */ orElse(struct None</*  */> this, /*  */ other){
	return other;
}
/* @Override
        public */ int isEmpty(struct None</*  */> this){
	return true;
}
/* @Override
        public */ /*  */ orElseGet(struct None</*  */> this, Supplier</*  */> supplier){
	return supplier.get(supplier);
}
/* @Override
        public <R> */ Option<struct R> flatMap(struct None</*  */> this, Option<struct R> (*)(/*  */) mapper){
	return None</*  */>();
}
/* @Override
        public */ Option</*  */> or(struct None</*  */> this, Supplier<Option</*  */>> supplier){
	return supplier.get(supplier);
}
/* @Override
        public <R> */ Option<struct R> map(struct Some</*  */> this, struct R (*)(/*  */) mapper){
	return Some</*  */>(mapper.apply(mapper, this.value));
}
/* @Override
        public */ int isPresent(struct Some</*  */> this){
	return true;
}
/* @Override
        public */ /*  */ orElse(struct Some</*  */> this, /*  */ other){
	return this.value;
}
/* @Override
        public */ int isEmpty(struct Some</*  */> this){
	return false;
}
/* @Override
        public */ /*  */ orElseGet(struct Some</*  */> this, Supplier</*  */> supplier){
	return this.value;
}
/* @Override
        public <R> */ Option<struct R> flatMap(struct Some</*  */> this, Option<struct R> (*)(/*  */) mapper){
	return mapper.apply(mapper, this.value);
}
/* @Override
        public */ Option</*  */> or(struct Some</*  */> this, Supplier<Option</*  */>> supplier){
	return this;
}
/* @Override
        public */ List<struct T> createInitial(struct ListCollector</*  */> this){
	return listEmpty();
}
/* @Override
        public */ List<struct T> fold(struct ListCollector</*  */> this, List<struct T> current, struct T element){
	return current.addLast(current, element);
}
