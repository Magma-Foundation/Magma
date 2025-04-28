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
	auto popAndAppendToTuple_local2 = poppedState;
	struct var poppedChar = tuple.left;
	struct var poppedState = tuple.right;
	struct var appended = popAndAppendToTuple_local2.append(popAndAppendToTuple_local2, poppedChar);
	return Tuple</*  */>(poppedChar, appended);
}
/* private */ Option<Tuple<char, struct State>> popAndAppendToTuple(struct State this){
	auto popAndAppendToTuple_local0 = this;
	auto popAndAppendToTuple_local3 = popAndAppendToTuple_local0.pop(popAndAppendToTuple_local0);
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
	auto advance_local0 = this.segments;
	return struct State(this.input, advance_local0.addLast(advance_local0, this.buffer), "", this.depth, this.index);
}
/* private */ int isShallow(struct State this){
	return this.depth == 1;
}
/* private */ Option<Tuple<char, struct State>> pop(struct State this){
	auto pop_local0 = this.index >= this.input;
	auto pop_local1 = this.input;
	if (pop_local0.length(pop_local0)){
		return None</*  */>();
	}
	struct var escaped = pop_local1.charAt(pop_local1, this.index);
	return Some</*  */>(Tuple<char, struct State>(escaped, struct State(this.input, this.segments, this.buffer, this.depth, this.index + 1)));
}
/* private */ struct State append(struct State this, struct char c){
	return struct State(this.input, this.segments, this.buffer + c, this.depth, this.index);
}
/* public */ Option<struct State> popAndAppend(struct State this){
	auto popAndAppend_local0 = this;
	auto popAndAppend_local1 = popAndAppend_local0.popAndAppendToTuple(popAndAppend_local0);
	return popAndAppend_local1.map(popAndAppend_local1, /* Tuple::right */);
}
/* public */ Option<char> peek(struct State this){
	auto peek_local1 = this.index < this.input;
	if (peek_local1.length(peek_local1)){
	auto peek_local0 = this.input;
		return Some</*  */>(peek_local0.charAt(peek_local0, this.index));
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
	auto fold_local0 = current;
	auto fold_local1 = fold_local0.map(fold_local0, /* inner -> inner */ + this.delimiter + element);
	return Some</*  */>(fold_local1.orElse(fold_local1, element));
}
struct public Definition(struct Definition this, char* type, char* name){
	/* this(new None<>(), type, name); */
}
/* @Override
        public */ char* generate(struct Definition this){
	auto generate_local0 = this;
	auto generate_local1 = generate_local0.beforeType(generate_local0);
	auto generate_local2 = generate_local1.map(generate_local1, /* Main::generatePlaceholder */);
	auto generate_local3 = generate_local2.map(generate_local2, /* inner -> inner */ + " ");
	auto generate_local4 = joined + this.type() + " " + this;
	struct var joined = generate_local3.orElse(generate_local3, "");
	return generate_local4.name(generate_local4);
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
	auto generate_local0 = this.left.generate() + " " + this.operator.representation + " " + this.right;
	return generate_local0.generate(generate_local0);
}
/* public static */ void main(struct Main this){
	/* try */{
	auto main_local0 = Paths;
	auto main_local1 = source;
	auto main_local2 = Files;
		struct var source = main_local0.get(main_local0, /* " */.", "src", "java", "magma", /* "Main */.java");
		struct var target = main_local1.resolveSibling(main_local1, /* "main */.c");
		struct var input = main_local2.readString(main_local2, source);
		/* Files.writeString(target, compileRoot(input)); */
	}
	/* catch (IOException e) */{
		/* e.printStackTrace(); */
	}
}
auto compileRoot_local1(auto tuple){
	auto compileRoot_local5 = expandables;
	if (compileRoot_local5.containsKey(compileRoot_local5, tuple.left)){
	auto compileRoot_local2 = expandables;
	auto compileRoot_local3 = expandable;
	auto compileRoot_local4 = compileRoot_local3.apply(compileRoot_local3, tuple.right);
		struct var expandable = compileRoot_local2.get(compileRoot_local2, tuple.left);
		return compileRoot_local4.orElse(compileRoot_local4, "");
	}
	else {
		return "// " + tuple.left + "<" + join(tuple.right, ", ") + ">\n";
	}
}
/* private static */ char* compileRoot(struct Main this, char* input){
	auto compileRoot_local0 = expansions;
	auto compileRoot_local6 = compileRoot_local0.iter(compileRoot_local0);
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
	auto join_local0 = list;
	auto join_local1 = join_local0.iter(join_local0);
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
	auto generateAll_local0 = parsed;
	auto generateAll_local1 = generateAll_local0.iter(generateAll_local0);
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
	auto divideAll_local0 = State;
	auto divideAll_local7 = state;
	struct State state = divideAll_local0.fromInput(divideAll_local0, input);
	/* while (true) */{
	auto divideAll_local1 = state;
	auto divideAll_local2 = maybeNextTuple;
	auto divideAll_local3 = maybeNextTuple;
	auto divideAll_local4 = foldSingleQuotes(withoutNext, next);
	auto divideAll_local5 = /* () -> folder */;
	auto divideAll_local6 = divideAll_local4.or(divideAll_local4, /* () -> foldDoubleQuotes */(withoutNext, next));
		struct var maybeNextTuple = divideAll_local1.pop(divideAll_local1);
		if (divideAll_local2.isEmpty(divideAll_local2)){
			/* break; */
		}
		struct var nextTuple = divideAll_local3.orElse(divideAll_local3, null);
		struct var next = nextTuple.left;
		struct var withoutNext = nextTuple.right;
		/* state  */ = divideAll_local6.orElseGet(divideAll_local6, divideAll_local5.apply(divideAll_local5, withoutNext, next));
	}
	return divideAll_local7.advance(divideAll_local7).segments;
}
/* private static */ Option<struct State> foldDoubleQuotes(struct Main this, struct State withoutNext, struct char c){
	auto foldDoubleQuotes_local0 = withoutNext;
	if (/* c != '\"' */){
		return None</*  */>();
	}
	struct var current = foldDoubleQuotes_local0.append(foldDoubleQuotes_local0, c);
	/* while (true) */{
	auto foldDoubleQuotes_local1 = current;
		struct var maybeNext = foldDoubleQuotes_local1.popAndAppendToTuple(foldDoubleQuotes_local1);
		if (/* ! */(/* maybeNext instanceof Some */(/* var next */))){
			/* break; */
		}
		/* current  */ = next.right;
		if (next.left == '"'){
			/* break; */
		}
		if (next.left == '\\'){
	auto foldDoubleQuotes_local2 = current;
	auto foldDoubleQuotes_local3 = foldDoubleQuotes_local2.popAndAppend(foldDoubleQuotes_local2);
			/* current  */ = foldDoubleQuotes_local3.orElse(foldDoubleQuotes_local3, current);
		}
	}
	return Some</*  */>(current);
}
/* private static */ Option<struct State> foldSingleQuotes(struct Main this, struct State state, struct char next){
	auto foldSingleQuotes_local0 = state;
	auto foldSingleQuotes_local1 = appended;
	auto foldSingleQuotes_local2 = /* maybeSlash -> maybeSlash */.left == '\\' ? maybeSlash.right;
	auto foldSingleQuotes_local3 = foldSingleQuotes_local1.popAndAppendToTuple(foldSingleQuotes_local1);
	auto foldSingleQuotes_local4 = foldSingleQuotes_local3.flatMap(foldSingleQuotes_local3, foldSingleQuotes_local2.popAndAppend() : new Some<>(foldSingleQuotes_local2, maybeSlash.right));
	if (/* next != '\'' */){
		return None</*  */>();
	}
	struct var appended = foldSingleQuotes_local0.append(foldSingleQuotes_local0, next);
	return foldSingleQuotes_local4.flatMap(foldSingleQuotes_local4, /* State::popAndAppend */);
}
/* private static */ struct State foldStatementChar(struct Main this, struct State state, struct char c){
	auto foldStatementChar_local0 = state;
	auto foldStatementChar_local2 = /* c == ';' && appended */;
	auto foldStatementChar_local5 = /* c == '}' && appended */;
	struct var appended = foldStatementChar_local0.append(foldStatementChar_local0, c);
	if (foldStatementChar_local2.isLevel(foldStatementChar_local2)){
	auto foldStatementChar_local1 = appended;
		return foldStatementChar_local1.advance(foldStatementChar_local1);
	}
	if (foldStatementChar_local5.isShallow(foldStatementChar_local5)){
	auto foldStatementChar_local3 = appended;
	auto foldStatementChar_local4 = foldStatementChar_local3.advance(foldStatementChar_local3);
		return foldStatementChar_local4.exit(foldStatementChar_local4);
	}
	/* if (c == ' */{
		/* ' || */ c = /* = '(') {
            return appended */.enter();
	}
	if (/* c == '}' || c == ')' */){
	auto foldStatementChar_local6 = appended;
		return foldStatementChar_local6.exit(foldStatementChar_local6);
	}
	return appended;
}
/* private static */ char* compileRootSegment(struct Main this, char* input){
	auto compileRootSegment_local0 = input;
	auto compileRootSegment_local1 = stripped;
	auto compileRootSegment_local2 = stripped.startsWith("package ") || stripped;
	auto compileRootSegment_local3 = compileClass(stripped);
	struct var stripped = compileRootSegment_local0.strip(compileRootSegment_local0);
	if (compileRootSegment_local1.isEmpty(compileRootSegment_local1)){
		return "";
	}
	if (compileRootSegment_local2.startsWith(compileRootSegment_local2, "import ")){
		return "";
	}
	return compileRootSegment_local3.orElseGet(compileRootSegment_local3, /* () -> generatePlaceholder */(stripped));
}
/* private static */ Option<char*> compileClass(struct Main this, char* stripped){
	return compileStructure(stripped, "class ");
}
/* private static */ Option<char*> compileStructure(struct Main this, char* input, char* infix){
	auto compileStructure_local0 = input;
	struct var classIndex = compileStructure_local0.indexOf(compileStructure_local0, infix);
	if (/* classIndex >= 0 */){
	auto compileStructure_local1 = input;
	auto compileStructure_local2 = compileStructure_local1.substring(compileStructure_local1, 0, classIndex);
	auto compileStructure_local3 = classIndex + infix;
	auto compileStructure_local4 = input;
	auto compileStructure_local5 = afterClass;
		struct var beforeClass = compileStructure_local2.strip(compileStructure_local2);
		struct var afterClass = compileStructure_local4.substring(compileStructure_local4, compileStructure_local3.length(compileStructure_local3));
		struct var contentStart = compileStructure_local5.indexOf(compileStructure_local5, "{");
		if (/* contentStart >= 0 */){
	auto compileStructure_local6 = afterClass;
	auto compileStructure_local7 = compileStructure_local6.substring(compileStructure_local6, 0, contentStart);
	auto compileStructure_local8 = beforeContent;
	auto compileStructure_local9 = /* permitsIndex >= 0
                        ? beforeContent */;
	auto compileStructure_local10 = withoutPermits;
	auto compileStructure_local11 = contentStart + "{";
	auto compileStructure_local12 = afterClass;
	auto compileStructure_local13 = compileStructure_local12.substring(compileStructure_local12, compileStructure_local11.length(compileStructure_local11));
			struct var beforeContent = compileStructure_local7.strip(compileStructure_local7);
			struct var permitsIndex = compileStructure_local8.indexOf(compileStructure_local8, " permits");
			struct var withoutPermits = compileStructure_local9.substring(compileStructure_local9, 0, permitsIndex).strip()
                        : beforeContent;
			struct var paramStart = compileStructure_local10.indexOf(compileStructure_local10, "(");
			struct var withEnd = compileStructure_local13.strip(compileStructure_local13);
			if (/* paramStart >= 0 */){
	auto compileStructure_local14 = withoutPermits;
	auto compileStructure_local15 = compileStructure_local14.substring(compileStructure_local14, 0, paramStart);
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
	auto getString_local1 = withEnd.length() - "}";
	auto getString_local2 = withEnd;
	auto getString_local3 = beforeContent;
	auto getString_local13 = strippedBeforeContent;
	if (getString_local0.endsWith(getString_local0, "}")){
		return None</*  */>();
	}
	struct var content = getString_local2.substring(getString_local2, 0, getString_local1.length(getString_local1));
	struct var strippedBeforeContent = getString_local3.strip(getString_local3);
	if (getString_local13.endsWith(getString_local13, ">")){
	auto getString_local4 = strippedBeforeContent.length() - ">";
	auto getString_local5 = strippedBeforeContent;
	auto getString_local6 = withoutEnd;
		struct var withoutEnd = getString_local5.substring(getString_local5, 0, getString_local4.length(getString_local4));
		struct var typeParamStart = getString_local6.indexOf(getString_local6, "<");
		if (/* typeParamStart >= 0 */){
	auto getString_local7 = withoutEnd;
	auto getString_local8 = getString_local7.substring(getString_local7, 0, typeParamStart);
	auto getString_local9 = typeParamStart + "<";
	auto getString_local10 = withoutEnd;
	auto getString_local11 = Pattern;
	auto getString_local12 = substring;
			struct var name = getString_local8.strip(getString_local8);
			struct var substring = getString_local10.substring(getString_local10, getString_local9.length(getString_local9));
			struct var typeParameters = listFromArray(getString_local12.split(getString_local12, getString_local11.quote(getString_local11, ",")));
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
	auto generateStructure_local0 = structNames;
	auto generateStructure_local1 = structNames;
	/* structNames  */ = generateStructure_local0.addLast(generateStructure_local0, name);
	struct var compiled = compileStatements(content, /* Main::compileClassSegment */);
	/* structNames  */ = generateStructure_local1.removeLast(generateStructure_local1);
	struct var generated = generatePlaceholder(beforeClass) + "struct " + name + " {" + compiled + "\n};\n";
	/* structs.addLast(generated); */
	return Some</*  */>("");
}
/* private static */ char* compileClassSegment(struct Main this, char* input){
	auto compileClassSegment_local0 = input;
	auto compileClassSegment_local1 = stripped;
	auto compileClassSegment_local2 = compileStructure(stripped, "record ");
	auto compileClassSegment_local3 = compileClassSegment_local2.or(compileClassSegment_local2, /* () -> compileStructure */(stripped, "interface "));
	auto compileClassSegment_local4 = compileClassSegment_local3.or(compileClassSegment_local3, /* () -> compileClass */(stripped));
	auto compileClassSegment_local5 = compileClassSegment_local4.or(compileClassSegment_local4, /* () -> compileMethod */(stripped));
	auto compileClassSegment_local6 = compileClassSegment_local5.or(compileClassSegment_local5, /* () -> compileDefinitionStatement */(stripped));
	struct var stripped = compileClassSegment_local0.strip(compileClassSegment_local0);
	if (compileClassSegment_local1.isEmpty(compileClassSegment_local1)){
		return "";
	}
	return compileClassSegment_local6.orElseGet(compileClassSegment_local6, /* () -> generatePlaceholder */(stripped));
}
/* private static */ Option<char*> compileDefinitionStatement(struct Main this, char* input){
	auto compileDefinitionStatement_local0 = input;
	auto compileDefinitionStatement_local3 = stripped;
	struct var stripped = compileDefinitionStatement_local0.strip(compileDefinitionStatement_local0);
	if (compileDefinitionStatement_local3.endsWith(compileDefinitionStatement_local3, ";")){
	auto compileDefinitionStatement_local1 = stripped.length() - ";";
	auto compileDefinitionStatement_local2 = stripped;
		struct var withoutEnd = compileDefinitionStatement_local2.substring(compileDefinitionStatement_local2, 0, compileDefinitionStatement_local1.length(compileDefinitionStatement_local1));
		return Some</*  */>("\n\t" + compileDefinitionOrPlaceholder(withoutEnd) + ";");
	}
	return None</*  */>();
}
/* private static */ Option<char*> compileMethod(struct Main this, char* stripped){
	auto compileMethod_local0 = stripped;
	auto compileMethod_local1 = stripped;
	auto compileMethod_local2 = defined;
	auto compileMethod_local3 = paramStart + "(";
	auto compileMethod_local4 = stripped;
	auto compileMethod_local5 = afterParams;
	auto compileMethod_local6 = afterParams;
	auto compileMethod_local7 = paramEnd + ")";
	auto compileMethod_local8 = afterParams;
	auto compileMethod_local9 = withoutParams;
	auto compileMethod_local10 = withBraces;
	auto compileMethod_local11 = parseValues(params, /* Main::parseParameter */);
	auto compileMethod_local12 = compileMethod_local11.iter(compileMethod_local11);
	auto compileMethod_local13 = compileMethod_local12.filter(compileMethod_local12, /* parameter -> ! */(/* parameter instanceof Whitespace */));
	auto compileMethod_local14 = Lists;
	auto compileMethod_local15 = "struct " + structNames;
	auto compileMethod_local16 = compileMethod_local14.<Defined>listEmpty(compileMethod_local14);
	auto compileMethod_local17 = compileMethod_local16.addLast(compileMethod_local16, struct Definition(compileMethod_local15.last(compileMethod_local15), "this"));
	struct var paramStart = compileMethod_local0.indexOf(compileMethod_local0, "(");
	if (/* paramStart < 0 */){
		return None</*  */>();
	}
	struct var inputDefinition = compileMethod_local1.substring(compileMethod_local1, 0, paramStart);
	struct var defined = parseDefinitionOrPlaceholder(inputDefinition);
	if (/* defined instanceof Definition definition */){
		/* functionName  */ = definition.name;
		/* functionLocalCounter  */ = 0;
	}
	struct var outputDefinition = compileMethod_local2.generate(compileMethod_local2);
	struct var afterParams = compileMethod_local4.substring(compileMethod_local4, compileMethod_local3.length(compileMethod_local3));
	struct var paramEnd = compileMethod_local5.indexOf(compileMethod_local5, ")");
	if (/* paramEnd < 0 */){
		return None</*  */>();
	}
	struct var params = compileMethod_local6.substring(compileMethod_local6, 0, paramEnd);
	struct var withoutParams = compileMethod_local8.substring(compileMethod_local8, compileMethod_local7.length(compileMethod_local7));
	struct var withBraces = compileMethod_local9.strip(compileMethod_local9);
	/* if (!withBraces.startsWith(" */{
		/* ") || !withBraces.endsWith("}")) {
            return new Some<>(""); */
	}
	struct var content = compileMethod_local10.substring(compileMethod_local10, 1, withBraces.length() - 1);
	struct var newParams = compileMethod_local13.collect(compileMethod_local13, ListCollector</*  */>());
	struct var copy = compileMethod_local17.addAll(compileMethod_local17, newParams);
	struct var outputParams = generateValueList(copy);
	return assembleMethod(outputDefinition, outputParams, content);
}
/* private static <T extends Node> */ char* generateValueList(struct Main this, List<struct T> copy){
	auto generateValueList_local0 = copy;
	auto generateValueList_local1 = generateValueList_local0.iter(generateValueList_local0);
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
	auto parseStatementsWithLocals_local0 = Lists;
	auto parseStatementsWithLocals_local1 = statements;
	auto parseStatementsWithLocals_local2 = statements;
	auto parseStatementsWithLocals_local3 = Lists;
	auto parseStatementsWithLocals_local4 = parseStatementsWithLocals_local3.<String>listEmpty(parseStatementsWithLocals_local3);
	auto parseStatementsWithLocals_local5 = parseStatementsWithLocals_local4.addAll(parseStatementsWithLocals_local4, elements);
	/* statements  */ = parseStatementsWithLocals_local1.addLast(parseStatementsWithLocals_local1, parseStatementsWithLocals_local0.listEmpty(parseStatementsWithLocals_local0));
	/* var parsed1  */ = parseStatements(content, compiler);
	struct var elements = parseStatementsWithLocals_local2.removeAndGetLast(parseStatementsWithLocals_local2);
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
	auto parseWhitespace_local0 = input;
	if (parseWhitespace_local0.isBlank(parseWhitespace_local0)){
		return Some</*  */>(struct Whitespace());
	}
	else {
		return None</*  */>();
	}
}
/* private static */ char* compileFunctionSegment(struct Main this, char* input, int depth){
	auto compileFunctionSegment_local0 = input;
	auto compileFunctionSegment_local1 = stripped;
	auto compileFunctionSegment_local2 = "\n" + "\t";
	auto compileFunctionSegment_local6 = stripped;
	auto compileFunctionSegment_local13 = stripped;
	struct var stripped = compileFunctionSegment_local0.strip(compileFunctionSegment_local0);
	if (compileFunctionSegment_local1.isEmpty(compileFunctionSegment_local1)){
		return "";
	}
	struct var indent = compileFunctionSegment_local2.repeat(compileFunctionSegment_local2, depth);
	if (compileFunctionSegment_local6.endsWith(compileFunctionSegment_local6, ";")){
	auto compileFunctionSegment_local3 = stripped.length() - ";";
	auto compileFunctionSegment_local4 = stripped;
	auto compileFunctionSegment_local5 = compileFunctionSegment_local4.substring(compileFunctionSegment_local4, 0, compileFunctionSegment_local3.length(compileFunctionSegment_local3));
		struct var withoutEnd = compileFunctionSegment_local5.strip(compileFunctionSegment_local5);
		struct var maybeStatementValue = compileStatementValue(withoutEnd);
		if (/* maybeStatementValue instanceof Some */(/* var statementValue */)){
			return indent + statementValue + ";";
		}
	}
	if (compileFunctionSegment_local13.endsWith(compileFunctionSegment_local13, "}")){
	auto compileFunctionSegment_local7 = stripped.length() - "}";
	auto compileFunctionSegment_local8 = stripped;
	auto compileFunctionSegment_local9 = withoutEnd;
		struct var withoutEnd = compileFunctionSegment_local8.substring(compileFunctionSegment_local8, 0, compileFunctionSegment_local7.length(compileFunctionSegment_local7));
		struct var contentStart = compileFunctionSegment_local9.indexOf(compileFunctionSegment_local9, "{");
		if (/* contentStart >= 0 */){
	auto compileFunctionSegment_local10 = withoutEnd;
	auto compileFunctionSegment_local11 = contentStart + "{";
	auto compileFunctionSegment_local12 = withoutEnd;
			struct var beforeBlock = compileFunctionSegment_local10.substring(compileFunctionSegment_local10, 0, contentStart);
			struct var content = compileFunctionSegment_local12.substring(compileFunctionSegment_local12, compileFunctionSegment_local11.length(compileFunctionSegment_local11));
			struct var outputContent = parseStatementsWithLocals(content, /* input1 -> compileFunctionSegment */(/* input1 */, depth + 1));
			return indent + compileBeforeBlock(beforeBlock) + "{" + join(outputContent) + indent + "}";
		}
	}
	return indent + generatePlaceholder(stripped);
}
/* private static */ Option<char*> compileStatementValue(struct Main this, char* input){
	auto compileStatementValue_local2 = input;
	auto compileStatementValue_local3 = input;
	if (compileStatementValue_local2.startsWith(compileStatementValue_local2, "return ")){
	auto compileStatementValue_local0 = "return ";
	auto compileStatementValue_local1 = input;
		struct var value = compileStatementValue_local1.substring(compileStatementValue_local1, compileStatementValue_local0.length(compileStatementValue_local0));
		return Some</*  */>("return " + compileValue(value));
	}
	struct var valueSeparator = compileStatementValue_local3.indexOf(compileStatementValue_local3, "=");
	if (/* valueSeparator >= 0 */){
	auto compileStatementValue_local4 = input;
	auto compileStatementValue_local5 = valueSeparator + "=";
	auto compileStatementValue_local6 = input;
	auto compileStatementValue_local7 = parseDefinitionOrPlaceholder(definition);
		struct var definition = compileStatementValue_local4.substring(compileStatementValue_local4, 0, valueSeparator);
		struct var value = compileStatementValue_local6.substring(compileStatementValue_local6, compileStatementValue_local5.length(compileStatementValue_local5));
		return Some</*  */>(compileStatementValue_local7.generate() + " = " + compileValue(compileStatementValue_local7, value));
	}
	return None</*  */>();
}
/* private static */ char* compileBeforeBlock(struct Main this, char* input){
	auto compileBeforeBlock_local0 = input;
	auto compileBeforeBlock_local6 = stripped;
	auto compileBeforeBlock_local7 = stripped;
	struct var stripped = compileBeforeBlock_local0.strip(compileBeforeBlock_local0);
	if (compileBeforeBlock_local6.startsWith(compileBeforeBlock_local6, "if")){
	auto compileBeforeBlock_local1 = "if";
	auto compileBeforeBlock_local2 = stripped;
	auto compileBeforeBlock_local3 = compileBeforeBlock_local2.substring(compileBeforeBlock_local2, compileBeforeBlock_local1.length(compileBeforeBlock_local1));
	auto compileBeforeBlock_local5 = withoutPrefix.startsWith("(") && withoutPrefix;
		struct var withoutPrefix = compileBeforeBlock_local3.strip(compileBeforeBlock_local3);
		if (compileBeforeBlock_local5.endsWith(compileBeforeBlock_local5, ")")){
	auto compileBeforeBlock_local4 = withoutPrefix;
			struct var condition = compileBeforeBlock_local4.substring(compileBeforeBlock_local4, 1, withoutPrefix.length() - 1);
			return "if (" + compileValue(condition) + ")";
		}
	}
	if (compileBeforeBlock_local7.equals(compileBeforeBlock_local7, "else")){
		return "else ";
	}
	return generatePlaceholder(stripped);
}
/* private static */ char* compileValue(struct Main this, char* input){
	auto compileValue_local0 = parseValue(input);
	return compileValue_local0.generate(compileValue_local0);
}
/* private static */ struct Value parseValue(struct Main this, char* input){
	auto parseValue_local0 = input;
	auto parseValue_local1 = stripped;
	auto parseValue_local18 = stripped;
	auto parseValue_local19 = stripped;
	auto parseValue_local26 = stripped;
	auto parseValue_local32 = stripped.length() >= 2 && stripped.startsWith("\"") && stripped;
	struct var stripped = parseValue_local0.strip(parseValue_local0);
	if (parseValue_local1.isEmpty(parseValue_local1)){
		return struct Whitespace();
	}
	if (parseValue_local18.endsWith(parseValue_local18, ")")){
	auto parseValue_local2 = stripped.length() - ")";
	auto parseValue_local3 = stripped;
	auto parseValue_local4 = parseValue_local3.substring(parseValue_local3, 0, parseValue_local2.length(parseValue_local2));
		struct var withoutEnd = parseValue_local4.strip(parseValue_local4);
		struct var divisions = divideAll(withoutEnd, /* Main::foldInvokableStart */);
		if (divisions.size() >= 2){
	auto parseValue_local5 = divisions;
	auto parseValue_local6 = joined.length() - ")";
	auto parseValue_local7 = joined;
	auto parseValue_local8 = divisions;
	auto parseValue_local11 = caller;
	auto parseValue_local12 = parseValues(arguments, /* Main::parseValue */);
	auto parseValue_local13 = parseValue_local12.iter(parseValue_local12);
	auto parseValue_local14 = parseValue_local13.filter(parseValue_local13, /* value -> ! */(/* value instanceof Whitespace */));
	auto parseValue_local15 = Lists;
	auto parseValue_local16 = parseValue_local15.<Value>listEmpty(parseValue_local15);
	auto parseValue_local17 = parseValue_local16.addLast(parseValue_local16, symbol);
			struct var joined = join(parseValue_local5.subList(parseValue_local5, 0, divisions.size() - 1), "");
			struct var caller = parseValue_local7.substring(parseValue_local7, 0, parseValue_local6.length(parseValue_local6));
			struct var arguments = parseValue_local8.last(parseValue_local8);
			/* Value parsedCaller; */
			if (parseValue_local11.startsWith(parseValue_local11, "new ")){
	auto parseValue_local9 = "new ";
	auto parseValue_local10 = caller;
				/* parsedCaller  */ = struct Symbol(compileType(parseValue_local10.substring(parseValue_local10, parseValue_local9.length(parseValue_local9))));
			}
			else {
				/* parsedCaller  */ = parseValue(caller);
			}
			struct var parsedArgs = parseValue_local14.collect(parseValue_local14, ListCollector</*  */>());
			if (/* ! */(/* parsedCaller instanceof DataAccess */(/* var parent */, /* var property */))){
				return struct Invocation(parsedCaller, parsedArgs);
			}
			struct var name = generateName();
			struct var statement = "\n\tauto " + name + " = " + parent.generate() + ";";
			/* statements.last().addLast(statement); */
			struct var symbol = struct Symbol(name);
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
	struct var arrowIndex = parseValue_local19.indexOf(parseValue_local19, "->");
	if (/* arrowIndex >= 0 */){
	auto parseValue_local20 = stripped;
	auto parseValue_local21 = parseValue_local20.substring(parseValue_local20, 0, arrowIndex);
	auto parseValue_local22 = arrowIndex + "->";
	auto parseValue_local23 = stripped;
	auto parseValue_local24 = parseValue_local23.substring(parseValue_local23, parseValue_local22.length(parseValue_local22));
		struct var beforeArrow = parseValue_local21.strip(parseValue_local21);
		struct var afterArrow = parseValue_local24.strip(parseValue_local24);
		/* if (afterArrow.startsWith(" */{
	auto parseValue_local25 = afterArrow.substring(1, afterArrow;
			/* ") && afterArrow.endsWith("}")) {
                var */ content = parseValue_local25.length() - 1);
                var name = generateName();
                assembleMethod("auto " + name, "auto " + beforeArrow, content);
                return new Symbol(parseValue_local25, name);
		}
	}
	struct var separator = parseValue_local26.lastIndexOf(parseValue_local26, /* " */.");
	if (/* separator >= 0 */){
	auto parseValue_local27 = stripped;
	auto parseValue_local28 = separator + /* " */.";
	auto parseValue_local29 = stripped;
	auto parseValue_local30 = parseValue_local29.substring(parseValue_local29, parseValue_local28.length(parseValue_local28));
		struct var value = parseValue_local27.substring(parseValue_local27, 0, separator);
		struct var property = parseValue_local30.strip(parseValue_local30);
		return struct DataAccess(parseValue(value), property);
	}
	if (parseValue_local32.endsWith(parseValue_local32, "\"")){
	auto parseValue_local31 = stripped;
		return struct StringValue(parseValue_local31.substring(parseValue_local31, 1, stripped.length() - 1));
	}
	/* for (var operator : Operator.values()) */{
	auto parseValue_local33 = stripped;
		struct var operatorIndex = parseValue_local33.indexOf(parseValue_local33, operator.representation);
		if (/* operatorIndex >= 0 */){
	auto parseValue_local34 = stripped;
	auto parseValue_local35 = operatorIndex + operator.representation;
	auto parseValue_local36 = stripped;
			struct var left = parseValue_local34.substring(parseValue_local34, 0, operatorIndex);
			struct var right = parseValue_local36.substring(parseValue_local36, parseValue_local35.length(parseValue_local35));
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
	auto foldInvokableStart_local0 = state;
	struct var appended = foldInvokableStart_local0.append(foldInvokableStart_local0, c);
	if (/* c == '(' */){
	auto foldInvokableStart_local1 = maybeAdvanced;
		struct var maybeAdvanced = appended.isLevel() ? appended.advance() : appended;
		return foldInvokableStart_local1.enter(foldInvokableStart_local1);
	}
	if (/* c == ')' */){
	auto foldInvokableStart_local2 = appended;
		return foldInvokableStart_local2.exit(foldInvokableStart_local2);
	}
	return appended;
}
/* private static */ int isNumber(struct Main this, char* input){
	auto isNumber_local0 = input;
	if (isNumber_local0.isEmpty(isNumber_local0)){
		return false;
	}
	/* for (var i = 0; i < input.length(); i++) */{
	auto isNumber_local1 = input;
	auto isNumber_local2 = Character;
		struct var c = isNumber_local1.charAt(isNumber_local1, i);
		if (isNumber_local2.isDigit(isNumber_local2, c)){
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
	auto parseDefinition_local0 = input;
	auto parseDefinition_local1 = stripped;
	auto parseDefinition_local2 = stripped;
	auto parseDefinition_local3 = nameSeparator + " ";
	auto parseDefinition_local4 = stripped;
	auto parseDefinition_local5 = divisions;
	auto parseDefinition_local6 = divisions;
	struct var stripped = parseDefinition_local0.strip(parseDefinition_local0);
	struct var nameSeparator = parseDefinition_local1.lastIndexOf(parseDefinition_local1, " ");
	if (/* nameSeparator < 0 */){
		return None</*  */>();
	}
	struct var beforeName = parseDefinition_local2.substring(parseDefinition_local2, 0, nameSeparator);
	struct var name = parseDefinition_local4.substring(parseDefinition_local4, parseDefinition_local3.length(parseDefinition_local3));
	if (/* !isSymbol */(name)){
		return None</*  */>();
	}
	struct var divisions = divideAll(beforeName, /* Main::foldByTypeSeparator */);
	if (divisions.size() == 1){
		return Some</*  */>(struct Definition(None</*  */>(), compileType(beforeName), name));
	}
	struct var beforeType = join(parseDefinition_local5.subList(parseDefinition_local5, 0, divisions.size() - 1), " ");
	struct var type = parseDefinition_local6.last(parseDefinition_local6);
	return Some</*  */>(struct Definition(Some</*  */>(beforeType), compileType(type), name));
}
/* private static */ struct State foldByTypeSeparator(struct Main this, struct State state, struct char c){
	auto foldByTypeSeparator_local1 = /* c == ' ' && state */;
	auto foldByTypeSeparator_local2 = state;
	if (foldByTypeSeparator_local1.isLevel(foldByTypeSeparator_local1)){
	auto foldByTypeSeparator_local0 = state;
		return foldByTypeSeparator_local0.advance(foldByTypeSeparator_local0);
	}
	struct var appended = foldByTypeSeparator_local2.append(foldByTypeSeparator_local2, c);
	if (/* c == '<' */){
	auto foldByTypeSeparator_local3 = appended;
		return foldByTypeSeparator_local3.enter(foldByTypeSeparator_local3);
	}
	if (/* c == '>' */){
	auto foldByTypeSeparator_local4 = appended;
		return foldByTypeSeparator_local4.exit(foldByTypeSeparator_local4);
	}
	return appended;
}
/* private static */ char* compileType(struct Main this, char* input){
	auto compileType_local0 = input;
	auto compileType_local1 = typeParameters;
	auto compileType_local4 = maybeTypeParamIndex;
	auto compileType_local21 = stripped;
	struct var stripped = compileType_local0.strip(compileType_local0);
	struct var maybeTypeParamIndex = compileType_local1.indexOf(compileType_local1, stripped);
	if (compileType_local4.isPresent(compileType_local4)){
	auto compileType_local2 = maybeTypeParamIndex;
	auto compileType_local3 = typeArguments;
		struct var typeParamIndex = compileType_local2.orElse(compileType_local2, null);
		return compileType_local3.get(compileType_local3, typeParamIndex);
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
	if (compileType_local21.endsWith(compileType_local21, ">")){
	auto compileType_local5 = stripped.length() - ">";
	auto compileType_local6 = stripped;
	auto compileType_local7 = withoutEnd;
		struct var withoutEnd = compileType_local6.substring(compileType_local6, 0, compileType_local5.length(compileType_local5));
		struct var index = compileType_local7.indexOf(compileType_local7, "<");
		if (/* index >= 0 */){
	auto compileType_local8 = withoutEnd;
	auto compileType_local9 = compileType_local8.substring(compileType_local8, 0, index);
	auto compileType_local10 = index + "<";
	auto compileType_local11 = withoutEnd;
	auto compileType_local14 = base;
	auto compileType_local18 = base;
	auto compileType_local20 = /* !expansions */;
			struct var base = compileType_local9.strip(compileType_local9);
			struct var substring = compileType_local11.substring(compileType_local11, compileType_local10.length(compileType_local10));
			struct var parsed = parseValues(substring, /* Main::compileType */);
			if (compileType_local14.equals(compileType_local14, "Function")){
	auto compileType_local12 = parsed;
	auto compileType_local13 = parsed;
				/* var arg0  */ = compileType_local12.get(compileType_local12, 0);
				struct var returns = compileType_local13.get(compileType_local13, 1);
				return returns + " (*)(" + arg0 + ")";
			}
			if (compileType_local18.equals(compileType_local18, "BiFunction")){
	auto compileType_local15 = parsed;
	auto compileType_local16 = parsed;
	auto compileType_local17 = parsed;
				/* var arg0  */ = compileType_local15.get(compileType_local15, 0);
				/* var arg1  */ = compileType_local16.get(compileType_local16, 1);
				struct var returns = compileType_local17.get(compileType_local17, 2);
				return returns + " (*)(" + arg0 + ", " + arg1 + ")";
			}
			if (compileType_local20.contains(compileType_local20, Tuple</*  */>(base, parsed))){
	auto compileType_local19 = expansions;
				/* expansions  */ = compileType_local19.addLast(compileType_local19, Tuple</*  */>(base, parsed));
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
	auto mergeValues_local0 = builder;
	if (mergeValues_local0.isEmpty(mergeValues_local0)){
		return builder + element;
	}
	return builder + ", " + element;
}
/* private static */ struct State foldValueChar(struct Main this, struct State state, struct char c){
	auto foldValueChar_local1 = /* c == ',' && state */;
	auto foldValueChar_local2 = state;
	if (foldValueChar_local1.isLevel(foldValueChar_local1)){
	auto foldValueChar_local0 = state;
		return foldValueChar_local0.advance(foldValueChar_local0);
	}
	struct var appended = foldValueChar_local2.append(foldValueChar_local2, c);
	if (/* c == '-' */){
	auto foldValueChar_local5 = appended;
		if (foldValueChar_local5.peek() instanceof Some(foldValueChar_local5, /* var maybeArrow */)){
			if (/* maybeArrow == '>' */){
	auto foldValueChar_local3 = appended;
	auto foldValueChar_local4 = foldValueChar_local3.popAndAppend(foldValueChar_local3);
				return foldValueChar_local4.orElse(foldValueChar_local4, appended);
			}
		}
	}
	if (/* c == '<' || c == '(' */){
	auto foldValueChar_local6 = appended;
		return foldValueChar_local6.enter(foldValueChar_local6);
	}
	if (/* c == '>' || c == ')' */){
	auto foldValueChar_local7 = appended;
		return foldValueChar_local7.exit(foldValueChar_local7);
	}
	return appended;
}
/* private static */ int isSymbol(struct Main this, char* input){
	auto isSymbol_local0 = input;
	auto isSymbol_local1 = stripped;
	struct var stripped = isSymbol_local0.strip(isSymbol_local0);
	if (isSymbol_local1.isEmpty(isSymbol_local1)){
		return false;
	}
	/* for (var i = 0; i < stripped.length(); i++) */{
	auto isSymbol_local2 = stripped;
	auto isSymbol_local3 = Character;
		struct var c = isSymbol_local2.charAt(isSymbol_local2, i);
		if (isSymbol_local3.isLetter(isSymbol_local3, c)){
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
	auto orElseGet_local0 = supplier;
	return orElseGet_local0.get(orElseGet_local0);
}
/* @Override
        public <R> */ Option<struct R> flatMap(struct None</*  */> this, Option<struct R> (*)(/*  */) mapper){
	return None</*  */>();
}
/* @Override
        public */ Option</*  */> or(struct None</*  */> this, Supplier<Option</*  */>> supplier){
	auto or_local0 = supplier;
	return or_local0.get(or_local0);
}
/* @Override
        public <R> */ Option<struct R> map(struct Some</*  */> this, struct R (*)(/*  */) mapper){
	auto map_local0 = mapper;
	return Some</*  */>(map_local0.apply(map_local0, this.value));
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
	auto flatMap_local0 = mapper;
	return flatMap_local0.apply(flatMap_local0, this.value);
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
	auto fold_local0 = current;
	return fold_local0.addLast(fold_local0, element);
}
