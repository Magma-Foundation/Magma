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
/* public */struct Some<char*> {
};
/* public sealed */struct Option<struct Whitespace> {
};
/* public sealed */struct Option<struct Definition> {
};
/* private static */ struct State fromInput(struct State this, char* input){
	return struct State(input, listEmpty(), "", 0, 0);
}
auto popAndAppendToTuple_local1(auto tuple){
	auto popAndAppendToTuple_local0 = this;
	auto popAndAppendToTuple_local2 = poppedState;
	struct var poppedChar = tuple.left;
	struct var poppedState = tuple.right;
	struct var appended = poppedState.append(popAndAppendToTuple_local2, poppedChar);
	return Tuple</*  */>(poppedChar, appended);
}
/* private */ Option<Tuple<char, struct State>> popAndAppendToTuple(struct State this){
	auto popAndAppendToTuple_local3 = this.pop(popAndAppendToTuple_local0);
	return this.pop(popAndAppendToTuple_local0).map(popAndAppendToTuple_local3, popAndAppendToTuple_local1);
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
	return struct State(this.input, this.segments.addLast(advance_local0, this.buffer), "", this.depth, this.index);
}
/* private */ int isShallow(struct State this){
	return this.depth == 1;
}
/* private */ Option<Tuple<char, struct State>> pop(struct State this){
	auto pop_local0 = this.index >= this.input;
	auto pop_local1 = this.input;
	if (this.index >= this.input.length(pop_local0)){
		return None</*  */>();
	}
	struct var escaped = this.input.charAt(pop_local1, this.index);
	return Some</*  */>(Tuple<char, struct State>(escaped, struct State(this.input, this.segments, this.buffer, this.depth, this.index + 1)));
}
/* private */ struct State append(struct State this, struct char c){
	return struct State(this.input, this.segments, this.buffer + c, this.depth, this.index);
}
/* public */ Option<struct State> popAndAppend(struct State this){
	auto popAndAppend_local0 = this;
	auto popAndAppend_local1 = this.popAndAppendToTuple(popAndAppend_local0);
	return this.popAndAppendToTuple(popAndAppend_local0).map(popAndAppend_local1, /* Tuple::right */);
}
/* public */ Option<char> peek(struct State this){
	auto peek_local0 = this.input;
	auto peek_local1 = this.index < this.input;
	if (this.index < this.input.length(peek_local1)){
		return Some</*  */>(this.input.charAt(peek_local0, this.index));
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
	auto fold_local1 = current.map(fold_local0, /* inner -> inner */ + Symbol[value=this].delimiter + element);
	return Some</*  */>(current.map(fold_local0, /* inner -> inner */ + Symbol[value=this].delimiter + element).orElse(fold_local1, element));
}
struct public Definition(struct Definition this, char* type, char* name){
	/* this(new None<>(), type, name); */
}
/* @Override
        public */ char* generate(struct Definition this){
	auto generate_local0 = this;
	auto generate_local1 = this.beforeType(generate_local0);
	auto generate_local2 = this.beforeType(generate_local0).map(generate_local1, /* Main::generatePlaceholder */);
	auto generate_local3 = this.beforeType(generate_local0).map(generate_local1, /* Main::generatePlaceholder */).map(generate_local2, /* inner -> inner */ + StringValue[value= ]);
	auto generate_local4 = joined + Symbol[value=this].type() + " " + this;
	struct var joined = this.beforeType(generate_local0).map(generate_local1, /* Main::generatePlaceholder */).map(generate_local2, /* inner -> inner */ + StringValue[value= ]).orElse(generate_local3, "");
	return joined + Symbol[value=this].type() + " " + this.name(generate_local4);
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
	return "\"" + Symbol[value=this].value + "\"";
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
	return this.left.generate() + " " + this.operator.representation + " " + this.right;
}
/* public static */ void main(struct Main this){
	auto main_local0 = Paths;
	auto main_local1 = source;
	auto main_local2 = Files;
	/* try */{
		struct var source = Paths.get(main_local0, /* " */.", "src", "java", "magma", /* "Main */.java");
		struct var target = source.resolveSibling(main_local1, /* "main */.c");
		struct var input = Files.readString(main_local2, source);
		/* Files.writeString(target, compileRoot(input)); */
	}
	/* catch (IOException e) */{
		/* e.printStackTrace(); */
	}
}
auto compileRoot_local1(auto tuple){
	auto compileRoot_local0 = expansions;
	auto compileRoot_local2 = expandables;
	auto compileRoot_local3 = expandable;
	auto compileRoot_local4 = expandable.apply(compileRoot_local3, tuple.right);
	auto compileRoot_local5 = expandables;
	if (expandables.containsKey(compileRoot_local5, tuple.left)){
		struct var expandable = expandables.get(compileRoot_local2, tuple.left);
		return expandable.apply(compileRoot_local3, tuple.right).orElse(compileRoot_local4, "");
	}
	else {
		return "// " + Symbol[value=tuple].left + "<" + join(tuple.right, ", ") + ">\n";
	}
}
/* private static */ char* compileRoot(struct Main this, char* input){
	auto compileRoot_local6 = expansions.iter(compileRoot_local0);
	auto compileRoot_local7 = expansions.iter(compileRoot_local0).map(compileRoot_local6, compileRoot_local1);
	auto compileRoot_local8 = expansions.iter(compileRoot_local0).map(compileRoot_local6, compileRoot_local1).collect(compileRoot_local7, struct Joiner());
	struct var compiled = compileStatements(input, /* Main::compileRootSegment */);
	struct var joinedExpansions = expansions.iter(compileRoot_local0).map(compileRoot_local6, compileRoot_local1).collect(compileRoot_local7, struct Joiner()).orElse(compileRoot_local8, "");
	return compiled + Operation[left=Invocation[caller=Symbol[value=join], args=JavaList[elements=[Symbol[value=structs]]]], operator=ADD, right=Operation[left=Symbol[value=joinedExpansions], operator=ADD, right=Symbol[value=join]]](methods);
}
/* private static */ char* join(struct Main this, List<char*> list){
	return join(list, "");
}
/* private static */ char* join(struct Main this, List<char*> list, char* delimiter){
	auto join_local0 = list;
	auto join_local1 = list.iter(join_local0);
	auto join_local2 = list.iter(join_local0).collect(join_local1, struct Joiner(delimiter));
	return list.iter(join_local0).collect(join_local1, struct Joiner(delimiter)).orElse(join_local2, "");
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
	auto generateAll_local1 = parsed.iter(generateAll_local0);
	return parsed.iter(generateAll_local0).fold(generateAll_local1, "", merger);
}
/* private static <T> */ List<struct T> parseAll(struct Main this, char* input, struct State (*)(struct State, char) folder, struct T (*)(char*) compiler){
	auto parseAll_local0 = divideAll(input, folder);
	auto parseAll_local1 = divideAll(input, folder).iter(parseAll_local0);
	auto parseAll_local2 = divideAll(input, folder).iter(parseAll_local0).map(parseAll_local1, compiler);
	return divideAll(input, folder).iter(parseAll_local0).map(parseAll_local1, compiler).collect(parseAll_local2, ListCollector</*  */>());
}
/* private static */ char* mergeStatements(struct Main this, char* buffer, char* element){
	return buffer + Symbol[value=element];
}
/* private static */ List<char*> divideAll(struct Main this, char* input, struct State (*)(struct State, char) folder){
	auto divideAll_local0 = State;
	auto divideAll_local1 = state;
	auto divideAll_local2 = maybeNextTuple;
	auto divideAll_local3 = maybeNextTuple;
	auto divideAll_local4 = foldSingleQuotes(withoutNext, next);
	auto divideAll_local5 = /* () -> folder */;
	auto divideAll_local6 = foldSingleQuotes(withoutNext, next).or(divideAll_local4, /* () -> foldDoubleQuotes */(withoutNext, next));
	auto divideAll_local7 = state;
	struct State state = State.fromInput(divideAll_local0, input);
	/* while (true) */{
		struct var maybeNextTuple = state.pop(divideAll_local1);
		if (maybeNextTuple.isEmpty(divideAll_local2)){
			/* break; */
		}
		struct var nextTuple = maybeNextTuple.orElse(divideAll_local3, null);
		struct var next = nextTuple.left;
		struct var withoutNext = nextTuple.right;
		/* state  */ = foldSingleQuotes(withoutNext, next).or(divideAll_local4, /* () -> foldDoubleQuotes */(withoutNext, next)).orElseGet(divideAll_local6, /* () -> folder */.apply(divideAll_local5, withoutNext, next));
	}
	return state.advance(divideAll_local7).segments;
}
/* private static */ Option<struct State> foldDoubleQuotes(struct Main this, struct State withoutNext, struct char c){
	auto foldDoubleQuotes_local0 = withoutNext;
	auto foldDoubleQuotes_local1 = current;
	auto foldDoubleQuotes_local2 = current;
	auto foldDoubleQuotes_local3 = current.popAndAppend(foldDoubleQuotes_local2);
	if (/* c != '\"' */){
		return None</*  */>();
	}
	struct var current = withoutNext.append(foldDoubleQuotes_local0, c);
	/* while (true) */{
		struct var maybeNext = current.popAndAppendToTuple(foldDoubleQuotes_local1);
		if (/* ! */(/* maybeNext instanceof Some */(/* var next */))){
			/* break; */
		}
		/* current  */ = next.right;
		if (next.left == '"'){
			/* break; */
		}
		if (next.left == '\\'){
			/* current  */ = current.popAndAppend(foldDoubleQuotes_local2).orElse(foldDoubleQuotes_local3, current);
		}
	}
	return Some</*  */>(current);
}
/* private static */ Option<struct State> foldSingleQuotes(struct Main this, struct State state, struct char next){
	auto foldSingleQuotes_local0 = state;
	auto foldSingleQuotes_local1 = appended;
	auto foldSingleQuotes_local2 = /* maybeSlash -> maybeSlash */.left == '\\' ? maybeSlash.right;
	auto foldSingleQuotes_local3 = appended.popAndAppendToTuple(foldSingleQuotes_local1);
	auto foldSingleQuotes_local4 = appended.popAndAppendToTuple(foldSingleQuotes_local1).flatMap(foldSingleQuotes_local3, /* maybeSlash -> maybeSlash */.left == '\\' ? maybeSlash.right.popAndAppend() : new Some<>(foldSingleQuotes_local2, maybeSlash.right));
	if (/* next != '\'' */){
		return None</*  */>();
	}
	struct var appended = state.append(foldSingleQuotes_local0, next);
	return appended.popAndAppendToTuple(foldSingleQuotes_local1).flatMap(foldSingleQuotes_local3, /* maybeSlash -> maybeSlash */.left == '\\' ? maybeSlash.right.popAndAppend() : new Some<>(foldSingleQuotes_local2, maybeSlash.right)).flatMap(foldSingleQuotes_local4, /* State::popAndAppend */);
}
/* private static */ struct State foldStatementChar(struct Main this, struct State state, struct char c){
	auto foldStatementChar_local0 = state;
	auto foldStatementChar_local1 = appended;
	auto foldStatementChar_local2 = /* c == ';' && appended */;
	auto foldStatementChar_local3 = appended;
	auto foldStatementChar_local4 = appended.advance(foldStatementChar_local3);
	auto foldStatementChar_local5 = /* c == '}' && appended */;
	auto foldStatementChar_local6 = appended;
	struct var appended = state.append(foldStatementChar_local0, c);
	if (/* c == ';' && appended */.isLevel(foldStatementChar_local2)){
		return appended.advance(foldStatementChar_local1);
	}
	if (/* c == '}' && appended */.isShallow(foldStatementChar_local5)){
		return appended.advance(foldStatementChar_local3).exit(foldStatementChar_local4);
	}
	/* if (c == ' */{
		/* ' || */ c = /* = '(') {
            return appended */.enter();
	}
	if (/* c == '}' || c == ')' */){
		return appended.exit(foldStatementChar_local6);
	}
	return appended;
}
/* private static */ char* compileRootSegment(struct Main this, char* input){
	auto compileRootSegment_local0 = input;
	auto compileRootSegment_local1 = stripped;
	auto compileRootSegment_local2 = stripped.startsWith("package ") || stripped;
	auto compileRootSegment_local3 = compileClass(stripped);
	struct var stripped = input.strip(compileRootSegment_local0);
	if (stripped.isEmpty(compileRootSegment_local1)){
		return "";
	}
	if (stripped.startsWith("package ") || stripped.startsWith(compileRootSegment_local2, "import ")){
		return "";
	}
	return compileClass(stripped).orElseGet(compileRootSegment_local3, /* () -> generatePlaceholder */(stripped));
}
/* private static */ Option<char*> compileClass(struct Main this, char* stripped){
	return compileStructure(stripped, "class ");
}
/* private static */ Option<char*> compileStructure(struct Main this, char* input, char* infix){
	auto compileStructure_local0 = input;
	auto compileStructure_local1 = input;
	auto compileStructure_local2 = input.substring(compileStructure_local1, 0, classIndex);
	auto compileStructure_local3 = classIndex + Symbol[value=infix];
	auto compileStructure_local4 = input;
	auto compileStructure_local5 = afterClass;
	auto compileStructure_local6 = afterClass;
	auto compileStructure_local7 = afterClass.substring(compileStructure_local6, 0, contentStart);
	auto compileStructure_local8 = beforeContent;
	auto compileStructure_local9 = /* permitsIndex >= 0
                        ? beforeContent */;
	auto compileStructure_local10 = withoutPermits;
	auto compileStructure_local11 = contentStart + StringValue[value={];
	auto compileStructure_local12 = afterClass;
	auto compileStructure_local13 = afterClass.substring(compileStructure_local12, contentStart + StringValue[value={].length(compileStructure_local11));
	auto compileStructure_local14 = withoutPermits;
	auto compileStructure_local15 = withoutPermits.substring(compileStructure_local14, 0, paramStart);
	struct var classIndex = input.indexOf(compileStructure_local0, infix);
	if (/* classIndex >= 0 */){
		struct var beforeClass = input.substring(compileStructure_local1, 0, classIndex).strip(compileStructure_local2);
		struct var afterClass = input.substring(compileStructure_local4, classIndex + Symbol[value=infix].length(compileStructure_local3));
		struct var contentStart = afterClass.indexOf(compileStructure_local5, "{");
		if (/* contentStart >= 0 */){
			struct var beforeContent = afterClass.substring(compileStructure_local6, 0, contentStart).strip(compileStructure_local7);
			struct var permitsIndex = beforeContent.indexOf(compileStructure_local8, " permits");
			struct var withoutPermits = /* permitsIndex >= 0
                        ? beforeContent */.substring(compileStructure_local9, 0, permitsIndex).strip()
                        : beforeContent;
			struct var paramStart = withoutPermits.indexOf(compileStructure_local10, "(");
			struct var withEnd = afterClass.substring(compileStructure_local12, contentStart + StringValue[value={].length(compileStructure_local11)).strip(compileStructure_local13);
			if (/* paramStart >= 0 */){
				char* withoutParams = withoutPermits.substring(compileStructure_local14, 0, paramStart).strip(compileStructure_local15);
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
	auto getString_local4 = strippedBeforeContent.length() - ">";
	auto getString_local5 = strippedBeforeContent;
	auto getString_local6 = withoutEnd;
	auto getString_local7 = withoutEnd;
	auto getString_local8 = withoutEnd.substring(getString_local7, 0, typeParamStart);
	auto getString_local9 = typeParamStart + StringValue[value=<];
	auto getString_local10 = withoutEnd;
	auto getString_local11 = Pattern;
	auto getString_local12 = substring;
	auto getString_local13 = strippedBeforeContent;
	if (/* !withEnd */.endsWith(getString_local0, "}")){
		return None</*  */>();
	}
	struct var content = withEnd.substring(getString_local2, 0, withEnd.length() - "}".length(getString_local1));
	struct var strippedBeforeContent = beforeContent.strip(getString_local3);
	if (strippedBeforeContent.endsWith(getString_local13, ">")){
		struct var withoutEnd = strippedBeforeContent.substring(getString_local5, 0, strippedBeforeContent.length() - ">".length(getString_local4));
		struct var typeParamStart = withoutEnd.indexOf(getString_local6, "<");
		if (/* typeParamStart >= 0 */){
			struct var name = withoutEnd.substring(getString_local7, 0, typeParamStart).strip(getString_local8);
			struct var substring = withoutEnd.substring(getString_local10, typeParamStart + StringValue[value=<].length(getString_local9));
			struct var typeParameters = listFromArray(substring.split(getString_local12, Pattern.quote(getString_local11, ",")));
			return assembleStructure(typeParameters, name, beforeClass, content);
		}
	}
	return assembleStructure(listEmpty(), strippedBeforeContent, beforeClass, content);
}
/* private static */ Option<char*> assembleStructure(struct Main this, List<char*> typeParams, char* name, char* beforeClass, char* content){
	auto assembleStructure_local0 = /* !typeParams */;
	if (/* !typeParams */.isEmpty(assembleStructure_local0)){
		/* expandables.put(name, typeArgs */ /* -> { */ typeParameters = /* typeParams;
                typeArguments = typeArgs;

                var newName = name */ + Operation[left=StringValue[value=<], operator=ADD, right=Operation[left=Invocation[caller=Symbol[value=join], args=JavaList[elements=[Symbol[value=typeArgs], StringValue[value=, ]]]], operator=ADD, right=Content[input=">";
                return generateStructure]]](newName, beforeClass, /* content);
            } */);
		return Some</*  */>("");
	}
	return generateStructure(name, beforeClass, content);
}
/* private static */ Option<char*> generateStructure(struct Main this, char* name, char* beforeClass, char* content){
	auto generateStructure_local0 = structNames;
	auto generateStructure_local1 = structNames;
	/* structNames  */ = structNames.addLast(generateStructure_local0, name);
	struct var compiled = compileStatements(content, /* Main::compileClassSegment */);
	/* structNames  */ = structNames.removeLast(generateStructure_local1);
	struct var generated = generatePlaceholder(beforeClass) + StringValue[value=struct " + name + " {" + compiled + "\n};\n];
	/* structs.addLast(generated); */
	return Some</*  */>("");
}
/* private static */ char* compileClassSegment(struct Main this, char* input){
	auto compileClassSegment_local0 = input;
	auto compileClassSegment_local1 = stripped;
	auto compileClassSegment_local2 = compileStructure(stripped, "record ");
	auto compileClassSegment_local3 = compileStructure(stripped, "record ").or(compileClassSegment_local2, /* () -> compileStructure */(stripped, "interface "));
	auto compileClassSegment_local4 = compileStructure(stripped, "record ").or(compileClassSegment_local2, /* () -> compileStructure */(stripped, "interface ")).or(compileClassSegment_local3, /* () -> compileClass */(stripped));
	auto compileClassSegment_local5 = compileStructure(stripped, "record ").or(compileClassSegment_local2, /* () -> compileStructure */(stripped, "interface ")).or(compileClassSegment_local3, /* () -> compileClass */(stripped)).or(compileClassSegment_local4, /* () -> compileMethod */(stripped));
	auto compileClassSegment_local6 = compileStructure(stripped, "record ").or(compileClassSegment_local2, /* () -> compileStructure */(stripped, "interface ")).or(compileClassSegment_local3, /* () -> compileClass */(stripped)).or(compileClassSegment_local4, /* () -> compileMethod */(stripped)).or(compileClassSegment_local5, /* () -> compileDefinitionStatement */(stripped));
	struct var stripped = input.strip(compileClassSegment_local0);
	if (stripped.isEmpty(compileClassSegment_local1)){
		return "";
	}
	return compileStructure(stripped, "record ").or(compileClassSegment_local2, /* () -> compileStructure */(stripped, "interface ")).or(compileClassSegment_local3, /* () -> compileClass */(stripped)).or(compileClassSegment_local4, /* () -> compileMethod */(stripped)).or(compileClassSegment_local5, /* () -> compileDefinitionStatement */(stripped)).orElseGet(compileClassSegment_local6, /* () -> generatePlaceholder */(stripped));
}
/* private static */ Option<char*> compileDefinitionStatement(struct Main this, char* input){
	auto compileDefinitionStatement_local0 = input;
	auto compileDefinitionStatement_local1 = stripped.length() - ";";
	auto compileDefinitionStatement_local2 = stripped;
	auto compileDefinitionStatement_local3 = stripped;
	struct var stripped = input.strip(compileDefinitionStatement_local0);
	if (stripped.endsWith(compileDefinitionStatement_local3, ";")){
		struct var withoutEnd = stripped.substring(compileDefinitionStatement_local2, 0, stripped.length() - ";".length(compileDefinitionStatement_local1));
		return Some</*  */>("\n\t" + compileDefinitionOrPlaceholder(withoutEnd) + ";");
	}
	return None</*  */>();
}
/* private static */ Option<char*> compileMethod(struct Main this, char* stripped){
	auto compileMethod_local0 = stripped;
	auto compileMethod_local1 = stripped;
	auto compileMethod_local2 = defined;
	auto compileMethod_local3 = paramStart + StringValue[value=(];
	auto compileMethod_local4 = stripped;
	auto compileMethod_local5 = afterParams;
	auto compileMethod_local6 = afterParams;
	auto compileMethod_local7 = paramEnd + StringValue[value=)];
	auto compileMethod_local8 = afterParams;
	auto compileMethod_local9 = withoutParams;
	auto compileMethod_local10 = withBraces;
	auto compileMethod_local11 = parseValues(params, /* Main::parseParameter */);
	auto compileMethod_local12 = parseValues(params, /* Main::parseParameter */).iter(compileMethod_local11);
	auto compileMethod_local13 = parseValues(params, /* Main::parseParameter */).iter(compileMethod_local11).filter(compileMethod_local12, /* parameter -> ! */(/* parameter instanceof Whitespace */));
	auto compileMethod_local14 = Lists;
	auto compileMethod_local15 = "struct " + Symbol[value=structNames];
	auto compileMethod_local16 = Lists.<Defined>listEmpty(compileMethod_local14);
	auto compileMethod_local17 = Lists.<Defined>listEmpty(compileMethod_local14).addLast(compileMethod_local16, struct Definition("struct " + Symbol[value=structNames].last(compileMethod_local15), "this"));
	struct var paramStart = stripped.indexOf(compileMethod_local0, "(");
	if (/* paramStart < 0 */){
		return None</*  */>();
	}
	struct var inputDefinition = stripped.substring(compileMethod_local1, 0, paramStart);
	struct var defined = parseDefinitionOrPlaceholder(inputDefinition);
	if (/* defined instanceof Definition definition */){
		/* functionName  */ = definition.name;
		/* functionLocalCounter  */ = 0;
	}
	struct var outputDefinition = defined.generate(compileMethod_local2);
	struct var afterParams = stripped.substring(compileMethod_local4, paramStart + StringValue[value=(].length(compileMethod_local3));
	struct var paramEnd = afterParams.indexOf(compileMethod_local5, ")");
	if (/* paramEnd < 0 */){
		return None</*  */>();
	}
	struct var params = afterParams.substring(compileMethod_local6, 0, paramEnd);
	struct var withoutParams = afterParams.substring(compileMethod_local8, paramEnd + StringValue[value=)].length(compileMethod_local7));
	struct var withBraces = withoutParams.strip(compileMethod_local9);
	/* if (!withBraces.startsWith(" */{
		/* ") || !withBraces.endsWith("}")) {
            return new Some<>(""); */
	}
	struct var content = withBraces.substring(compileMethod_local10, 1, withBraces.length() - 1);
	struct var newParams = parseValues(params, /* Main::parseParameter */).iter(compileMethod_local11).filter(compileMethod_local12, /* parameter -> ! */(/* parameter instanceof Whitespace */)).collect(compileMethod_local13, ListCollector</*  */>());
	struct var copy = Lists.<Defined>listEmpty(compileMethod_local14).addLast(compileMethod_local16, struct Definition("struct " + Symbol[value=structNames].last(compileMethod_local15), "this")).addAll(compileMethod_local17, newParams);
	struct var outputParams = generateValueList(copy);
	return assembleMethod(outputDefinition, outputParams, content);
}
/* private static <T extends Node> */ char* generateValueList(struct Main this, List<struct T> copy){
	auto generateValueList_local0 = copy;
	auto generateValueList_local1 = copy.iter(generateValueList_local0);
	auto generateValueList_local2 = copy.iter(generateValueList_local0).map(generateValueList_local1, /* Node::generate */);
	auto generateValueList_local3 = copy.iter(generateValueList_local0).map(generateValueList_local1, /* Node::generate */).collect(generateValueList_local2, struct Joiner(", "));
	return copy.iter(generateValueList_local0).map(generateValueList_local1, /* Node::generate */).collect(generateValueList_local2, struct Joiner(", ")).orElse(generateValueList_local3, "");
}
/* private static */ Some<char*> assembleMethod(struct Main this, char* definition, char* outputParams, char* content){
	auto assembleMethod_local0 = Lists;
	auto assembleMethod_local1 = Lists.<String>listEmpty(assembleMethod_local0);
	auto assembleMethod_local2 = Lists.<String>listEmpty(assembleMethod_local0).addAll(assembleMethod_local1, statements);
	auto assembleMethod_local3 = Lists;
	/* var parsed1  */ = parseStatements(content, /* input -> compileFunctionSegment */(input, 1));
	struct var parsed = Lists.<String>listEmpty(assembleMethod_local0).addAll(assembleMethod_local1, statements).addAll(assembleMethod_local2, /* parsed1 */);
	/* statements  */ = Lists.listEmpty(assembleMethod_local3);
	struct var generated = definition + StringValue[value=(" + outputParams + "){" + generateStatements(parsed) + "\n}\n];
	/* methods.addLast(generated); */
	return Some</*  */>("");
}
/* private static */ struct Defined parseParameter(struct Main this, char* input){
	auto parseParameter_local0 = parseWhitespace(input);
	auto parseParameter_local1 = /* () -> parseDefinition */(input);
	auto parseParameter_local2 = parseWhitespace(input).<Defined>map(parseParameter_local0, /* value -> value */);
	auto parseParameter_local3 = parseWhitespace(input).<Defined>map(parseParameter_local0, /* value -> value */).or(parseParameter_local2, /* () -> parseDefinition */(input).map(parseParameter_local1, /* value -> value */));
	return parseWhitespace(input).<Defined>map(parseParameter_local0, /* value -> value */).or(parseParameter_local2, /* () -> parseDefinition */(input).map(parseParameter_local1, /* value -> value */)).orElseGet(parseParameter_local3, /* () -> new Content */(input));
}
/* private static */ Option<struct Whitespace> parseWhitespace(struct Main this, char* input){
	auto parseWhitespace_local0 = input;
	if (input.isBlank(parseWhitespace_local0)){
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
	auto compileFunctionSegment_local3 = stripped.length() - ";";
	auto compileFunctionSegment_local4 = stripped;
	auto compileFunctionSegment_local5 = stripped.substring(compileFunctionSegment_local4, 0, stripped.length() - ";".length(compileFunctionSegment_local3));
	auto compileFunctionSegment_local6 = stripped;
	auto compileFunctionSegment_local7 = stripped.length() - "}";
	auto compileFunctionSegment_local8 = stripped;
	auto compileFunctionSegment_local9 = withoutEnd;
	auto compileFunctionSegment_local10 = withoutEnd;
	auto compileFunctionSegment_local11 = contentStart + StringValue[value={];
	auto compileFunctionSegment_local12 = withoutEnd;
	auto compileFunctionSegment_local13 = stripped;
	struct var stripped = input.strip(compileFunctionSegment_local0);
	if (stripped.isEmpty(compileFunctionSegment_local1)){
		return "";
	}
	struct var indent = "\n" + "\t".repeat(compileFunctionSegment_local2, depth);
	if (stripped.endsWith(compileFunctionSegment_local6, ";")){
		struct var withoutEnd = stripped.substring(compileFunctionSegment_local4, 0, stripped.length() - ";".length(compileFunctionSegment_local3)).strip(compileFunctionSegment_local5);
		struct var maybeStatementValue = compileStatementValue(withoutEnd);
		if (/* maybeStatementValue instanceof Some */(/* var statementValue */)){
			return indent + Operation[left=Symbol[value=statementValue], operator=ADD, right=StringValue[value=;]];
		}
	}
	if (stripped.endsWith(compileFunctionSegment_local13, "}")){
		struct var withoutEnd = stripped.substring(compileFunctionSegment_local8, 0, stripped.length() - "}".length(compileFunctionSegment_local7));
		struct var contentStart = withoutEnd.indexOf(compileFunctionSegment_local9, "{");
		if (/* contentStart >= 0 */){
			struct var beforeBlock = withoutEnd.substring(compileFunctionSegment_local10, 0, contentStart);
			struct var content = withoutEnd.substring(compileFunctionSegment_local12, contentStart + StringValue[value={].length(compileFunctionSegment_local11));
			struct var outputContent = compileStatements(content, /* input1 -> compileFunctionSegment */(/* input1 */, depth + Symbol[value=1]));
			return indent + Operation[left=Invocation[caller=Symbol[value=compileBeforeBlock], args=JavaList[elements=[Symbol[value=beforeBlock]]]], operator=ADD, right=StringValue[value={" + outputContent + indent + "}]];
		}
	}
	return indent + Symbol[value=generatePlaceholder](stripped);
}
/* private static */ Option<char*> compileStatementValue(struct Main this, char* input){
	auto compileStatementValue_local0 = "return ";
	auto compileStatementValue_local1 = input;
	auto compileStatementValue_local2 = input;
	auto compileStatementValue_local3 = input;
	auto compileStatementValue_local4 = input;
	auto compileStatementValue_local5 = valueSeparator + StringValue[value==];
	auto compileStatementValue_local6 = input;
	auto compileStatementValue_local7 = parseDefinitionOrPlaceholder(definition);
	if (input.startsWith(compileStatementValue_local2, "return ")){
		struct var value = input.substring(compileStatementValue_local1, "return ".length(compileStatementValue_local0));
		return Some</*  */>("return " + Symbol[value=compileValue](value));
	}
	struct var valueSeparator = input.indexOf(compileStatementValue_local3, "=");
	if (/* valueSeparator >= 0 */){
		struct var definition = input.substring(compileStatementValue_local4, 0, valueSeparator);
		struct var value = input.substring(compileStatementValue_local6, valueSeparator + StringValue[value==].length(compileStatementValue_local5));
		return Some</*  */>(parseDefinitionOrPlaceholder(definition).generate() + " = " + compileValue(compileStatementValue_local7, value));
	}
	return None</*  */>();
}
/* private static */ char* compileBeforeBlock(struct Main this, char* input){
	auto compileBeforeBlock_local0 = input;
	auto compileBeforeBlock_local1 = "if";
	auto compileBeforeBlock_local2 = stripped;
	auto compileBeforeBlock_local3 = stripped.substring(compileBeforeBlock_local2, "if".length(compileBeforeBlock_local1));
	auto compileBeforeBlock_local4 = withoutPrefix;
	auto compileBeforeBlock_local5 = withoutPrefix.startsWith("(") && withoutPrefix;
	auto compileBeforeBlock_local6 = stripped;
	auto compileBeforeBlock_local7 = stripped;
	struct var stripped = input.strip(compileBeforeBlock_local0);
	if (stripped.startsWith(compileBeforeBlock_local6, "if")){
		struct var withoutPrefix = stripped.substring(compileBeforeBlock_local2, "if".length(compileBeforeBlock_local1)).strip(compileBeforeBlock_local3);
		if (withoutPrefix.startsWith("(") && withoutPrefix.endsWith(compileBeforeBlock_local5, ")")){
			struct var condition = withoutPrefix.substring(compileBeforeBlock_local4, 1, withoutPrefix.length() - 1);
			return "if (" + compileValue(condition) + ")";
		}
	}
	if (stripped.equals(compileBeforeBlock_local7, "else")){
		return "else ";
	}
	return generatePlaceholder(stripped);
}
/* private static */ char* compileValue(struct Main this, char* input){
	auto compileValue_local0 = parseValue(input);
	return parseValue(input).generate(compileValue_local0);
}
/* private static */ struct Value parseValue(struct Main this, char* input){
	auto parseValue_local0 = input;
	auto parseValue_local1 = stripped;
	auto parseValue_local2 = stripped.length() - ")";
	auto parseValue_local3 = stripped;
	auto parseValue_local4 = stripped.substring(parseValue_local3, 0, stripped.length() - ")".length(parseValue_local2));
	auto parseValue_local5 = divisions;
	auto parseValue_local6 = joined.length() - ")";
	auto parseValue_local7 = joined;
	auto parseValue_local8 = divisions;
	auto parseValue_local9 = "new ";
	auto parseValue_local10 = caller;
	auto parseValue_local11 = caller;
	auto parseValue_local12 = parseValues(arguments, /* Main::parseValue */);
	auto parseValue_local13 = parseValues(arguments, /* Main::parseValue */).iter(parseValue_local12);
	auto parseValue_local14 = parseValues(arguments, /* Main::parseValue */).iter(parseValue_local12).filter(parseValue_local13, /* value -> ! */(/* value instanceof Whitespace */));
	auto parseValue_local15 = statements;
	auto parseValue_local16 = Lists;
	auto parseValue_local17 = Lists.<Value>listEmpty(parseValue_local16);
	auto parseValue_local18 = Lists.<Value>listEmpty(parseValue_local16).addLast(parseValue_local17, struct Symbol(name));
	auto parseValue_local19 = stripped;
	auto parseValue_local20 = stripped;
	auto parseValue_local21 = stripped;
	auto parseValue_local22 = stripped.substring(parseValue_local21, 0, arrowIndex);
	auto parseValue_local23 = arrowIndex + StringValue[value=->];
	auto parseValue_local24 = stripped;
	auto parseValue_local25 = stripped.substring(parseValue_local24, arrowIndex + StringValue[value=->].length(parseValue_local23));
	auto parseValue_local26 = afterArrow.substring(1, afterArrow;
	auto parseValue_local27 = stripped;
	auto parseValue_local28 = stripped;
	auto parseValue_local29 = separator + Content[input="].";
	auto parseValue_local30 = stripped;
	auto parseValue_local31 = stripped.substring(parseValue_local30, separator + Content[input="].".length(parseValue_local29));
	auto parseValue_local32 = stripped;
	auto parseValue_local33 = stripped.length() >= 2 && stripped.startsWith("\"") && stripped;
	auto parseValue_local34 = stripped;
	auto parseValue_local35 = stripped;
	auto parseValue_local36 = operatorIndex + Symbol[value=operator].representation;
	auto parseValue_local37 = stripped;
	struct var stripped = input.strip(parseValue_local0);
	if (stripped.isEmpty(parseValue_local1)){
		return struct Whitespace();
	}
	if (stripped.endsWith(parseValue_local19, ")")){
		struct var withoutEnd = stripped.substring(parseValue_local3, 0, stripped.length() - ")".length(parseValue_local2)).strip(parseValue_local4);
		struct var divisions = divideAll(withoutEnd, /* Main::foldInvokableStart */);
		if (divisions.size() >= 2){
			struct var joined = join(divisions.subList(parseValue_local5, 0, divisions.size() - 1), "");
			struct var caller = joined.substring(parseValue_local7, 0, joined.length() - ")".length(parseValue_local6));
			struct var arguments = divisions.last(parseValue_local8);
			/* Value parsedCaller; */
			if (caller.startsWith(parseValue_local11, "new ")){
				/* parsedCaller  */ = struct Symbol(compileType(caller.substring(parseValue_local10, "new ".length(parseValue_local9))));
			}
			else {
				/* parsedCaller  */ = parseValue(caller);
			}
			struct var parsedArgs = parseValues(arguments, /* Main::parseValue */).iter(parseValue_local12).filter(parseValue_local13, /* value -> ! */(/* value instanceof Whitespace */)).collect(parseValue_local14, ListCollector</*  */>());
			/* List<Value> newArgs; */
			if (/* parsedCaller instanceof DataAccess */(/* var parent */, /* _ */)){
				struct var name = generateName();
				/* statements  */ = statements.addLast(parseValue_local15, "\n\tauto " + Operation[left=Symbol[value=name], operator=ADD, right=Operation[left=StringValue[value= = ], operator=ADD, right=Symbol[value=parent]]].generate() + ";");
				/* newArgs  */ = Lists.<Value>listEmpty(parseValue_local16).addLast(parseValue_local17, struct Symbol(name)).addAll(parseValue_local18, parsedArgs);
			}
			else {
				/* newArgs  */ = parsedArgs;
			}
			return struct Invocation(parsedCaller, newArgs);
		}
	}
	if (isSymbol(stripped)){
		return struct Symbol(stripped);
	}
	if (isNumber(stripped)){
		return struct Symbol(stripped);
	}
	struct var arrowIndex = stripped.indexOf(parseValue_local20, "->");
	if (/* arrowIndex >= 0 */){
		struct var beforeArrow = stripped.substring(parseValue_local21, 0, arrowIndex).strip(parseValue_local22);
		struct var afterArrow = stripped.substring(parseValue_local24, arrowIndex + StringValue[value=->].length(parseValue_local23)).strip(parseValue_local25);
		/* if (afterArrow.startsWith(" */{
			/* ") && afterArrow.endsWith("}")) {
                var */ content = afterArrow.substring(1, afterArrow.length() - 1);
                var name = generateName();
                assembleMethod("auto " + name, "auto " + beforeArrow, content);
                return new Symbol(parseValue_local26, name);
		}
	}
	struct var separator = stripped.lastIndexOf(parseValue_local27, /* " */.");
	if (/* separator >= 0 */){
		struct var value = stripped.substring(parseValue_local28, 0, separator);
		struct var property = stripped.substring(parseValue_local30, separator + Content[input="].".length(parseValue_local29)).strip(parseValue_local31);
		return struct DataAccess(parseValue(value), property);
	}
	if (stripped.length() >= 2 && stripped.startsWith("\"") && stripped.endsWith(parseValue_local33, "\"")){
		return struct StringValue(stripped.substring(parseValue_local32, 1, stripped.length() - 1));
	}
	/* for (var operator : Operator.values()) */{
		struct var operatorIndex = stripped.indexOf(parseValue_local34, operator.representation);
		if (/* operatorIndex >= 0 */){
			struct var left = stripped.substring(parseValue_local35, 0, operatorIndex);
			struct var right = stripped.substring(parseValue_local37, operatorIndex + Symbol[value=operator].representation.length(parseValue_local36));
			return struct Operation(parseValue(left), operator, parseValue(right));
		}
	}
	return struct Content(stripped);
}
/* private static */ char* generateName(struct Main this){
	struct var name = functionName + Operation[left=StringValue[value=_local], operator=ADD, right=Symbol[value=functionLocalCounter]];
	/* functionLocalCounter++; */
	return name;
}
/* private static */ struct State foldInvokableStart(struct Main this, struct State state, char c){
	auto foldInvokableStart_local0 = state;
	auto foldInvokableStart_local1 = maybeAdvanced;
	auto foldInvokableStart_local2 = appended;
	struct var appended = state.append(foldInvokableStart_local0, c);
	if (/* c == '(' */){
		struct var maybeAdvanced = appended.isLevel() ? appended.advance() : appended;
		return maybeAdvanced.enter(foldInvokableStart_local1);
	}
	if (/* c == ')' */){
		return appended.exit(foldInvokableStart_local2);
	}
	return appended;
}
/* private static */ int isNumber(struct Main this, char* input){
	auto isNumber_local0 = input;
	auto isNumber_local1 = input;
	auto isNumber_local2 = Character;
	if (input.isEmpty(isNumber_local0)){
		return false;
	}
	/* for (var i = 0; i < input.length(); i++) */{
		struct var c = input.charAt(isNumber_local1, i);
		if (Character.isDigit(isNumber_local2, c)){
			/* continue; */
		}
		return false;
	}
	return true;
}
/* private static */ char* compileDefinitionOrPlaceholder(struct Main this, char* input){
	auto compileDefinitionOrPlaceholder_local0 = parseDefinitionOrPlaceholder(input);
	return parseDefinitionOrPlaceholder(input).generate(compileDefinitionOrPlaceholder_local0);
}
/* private static */ struct Defined parseDefinitionOrPlaceholder(struct Main this, char* input){
	auto parseDefinitionOrPlaceholder_local0 = parseDefinition(input);
	auto parseDefinitionOrPlaceholder_local1 = parseDefinition(input).<Defined>map(parseDefinitionOrPlaceholder_local0, /* value -> value */);
	return parseDefinition(input).<Defined>map(parseDefinitionOrPlaceholder_local0, /* value -> value */).orElseGet(parseDefinitionOrPlaceholder_local1, /* () -> new Content */(input));
}
/* private static */ Option<struct Definition> parseDefinition(struct Main this, char* input){
	auto parseDefinition_local0 = input;
	auto parseDefinition_local1 = stripped;
	auto parseDefinition_local2 = stripped;
	auto parseDefinition_local3 = nameSeparator + StringValue[value= ];
	auto parseDefinition_local4 = stripped;
	auto parseDefinition_local5 = divisions;
	auto parseDefinition_local6 = divisions;
	struct var stripped = input.strip(parseDefinition_local0);
	struct var nameSeparator = stripped.lastIndexOf(parseDefinition_local1, " ");
	if (/* nameSeparator < 0 */){
		return None</*  */>();
	}
	struct var beforeName = stripped.substring(parseDefinition_local2, 0, nameSeparator);
	struct var name = stripped.substring(parseDefinition_local4, nameSeparator + StringValue[value= ].length(parseDefinition_local3));
	if (/* !isSymbol */(name)){
		return None</*  */>();
	}
	struct var divisions = divideAll(beforeName, /* Main::foldByTypeSeparator */);
	if (divisions.size() == 1){
		return Some</*  */>(struct Definition(None</*  */>(), compileType(beforeName), name));
	}
	struct var beforeType = join(divisions.subList(parseDefinition_local5, 0, divisions.size() - 1), " ");
	struct var type = divisions.last(parseDefinition_local6);
	return Some</*  */>(struct Definition(Some</*  */>(beforeType), compileType(type), name));
}
/* private static */ struct State foldByTypeSeparator(struct Main this, struct State state, struct char c){
	auto foldByTypeSeparator_local0 = state;
	auto foldByTypeSeparator_local1 = /* c == ' ' && state */;
	auto foldByTypeSeparator_local2 = state;
	auto foldByTypeSeparator_local3 = appended;
	auto foldByTypeSeparator_local4 = appended;
	if (/* c == ' ' && state */.isLevel(foldByTypeSeparator_local1)){
		return state.advance(foldByTypeSeparator_local0);
	}
	struct var appended = state.append(foldByTypeSeparator_local2, c);
	if (/* c == '<' */){
		return appended.enter(foldByTypeSeparator_local3);
	}
	if (/* c == '>' */){
		return appended.exit(foldByTypeSeparator_local4);
	}
	return appended;
}
/* private static */ char* compileType(struct Main this, char* input){
	auto compileType_local0 = input;
	auto compileType_local1 = typeParameters;
	auto compileType_local2 = maybeTypeParamIndex;
	auto compileType_local3 = typeArguments;
	auto compileType_local4 = maybeTypeParamIndex;
	auto compileType_local5 = stripped.length() - ">";
	auto compileType_local6 = stripped;
	auto compileType_local7 = withoutEnd;
	auto compileType_local8 = withoutEnd;
	auto compileType_local9 = withoutEnd.substring(compileType_local8, 0, index);
	auto compileType_local10 = index + StringValue[value=<];
	auto compileType_local11 = withoutEnd;
	auto compileType_local12 = parsed;
	auto compileType_local13 = parsed;
	auto compileType_local14 = base;
	auto compileType_local15 = parsed;
	auto compileType_local16 = parsed;
	auto compileType_local17 = parsed;
	auto compileType_local18 = base;
	auto compileType_local19 = expansions;
	auto compileType_local20 = /* !expansions */;
	auto compileType_local21 = stripped;
	struct var stripped = input.strip(compileType_local0);
	struct var maybeTypeParamIndex = typeParameters.indexOf(compileType_local1, stripped);
	if (maybeTypeParamIndex.isPresent(compileType_local4)){
		struct var typeParamIndex = maybeTypeParamIndex.orElse(compileType_local2, null);
		return typeArguments.get(compileType_local3, typeParamIndex);
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
	if (stripped.endsWith(compileType_local21, ">")){
		struct var withoutEnd = stripped.substring(compileType_local6, 0, stripped.length() - ">".length(compileType_local5));
		struct var index = withoutEnd.indexOf(compileType_local7, "<");
		if (/* index >= 0 */){
			struct var base = withoutEnd.substring(compileType_local8, 0, index).strip(compileType_local9);
			struct var substring = withoutEnd.substring(compileType_local11, index + StringValue[value=<].length(compileType_local10));
			struct var parsed = parseValues(substring, /* Main::compileType */);
			if (base.equals(compileType_local14, "Function")){
				/* var arg0  */ = parsed.get(compileType_local12, 0);
				struct var returns = parsed.get(compileType_local13, 1);
				return returns + StringValue[value= (*)(" + arg0 + ")];
			}
			if (base.equals(compileType_local18, "BiFunction")){
				/* var arg0  */ = parsed.get(compileType_local15, 0);
				/* var arg1  */ = parsed.get(compileType_local16, 1);
				struct var returns = parsed.get(compileType_local17, 2);
				return returns + StringValue[value= (*)(" + arg0 + ", " + arg1 + ")];
			}
			if (/* !expansions */.contains(compileType_local20, Tuple</*  */>(base, parsed))){
				/* expansions  */ = expansions.addLast(compileType_local19, Tuple</*  */>(base, parsed));
			}
			return base + StringValue[value=<" + generateValues(parsed) + ">];
		}
	}
	if (isSymbol(stripped)){
		return "struct " + Symbol[value=stripped];
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
	if (builder.isEmpty(mergeValues_local0)){
		return builder + Symbol[value=element];
	}
	return builder + Operation[left=StringValue[value=, ], operator=ADD, right=Symbol[value=element]];
}
/* private static */ struct State foldValueChar(struct Main this, struct State state, struct char c){
	auto foldValueChar_local0 = state;
	auto foldValueChar_local1 = /* c == ',' && state */;
	auto foldValueChar_local2 = state;
	auto foldValueChar_local3 = appended;
	auto foldValueChar_local4 = appended.popAndAppend(foldValueChar_local3);
	auto foldValueChar_local5 = appended;
	auto foldValueChar_local6 = appended;
	auto foldValueChar_local7 = appended;
	if (/* c == ',' && state */.isLevel(foldValueChar_local1)){
		return state.advance(foldValueChar_local0);
	}
	struct var appended = state.append(foldValueChar_local2, c);
	if (/* c == '-' */){
		if (appended.peek() instanceof Some(foldValueChar_local5, /* var maybeArrow */)){
			if (/* maybeArrow == '>' */){
				return appended.popAndAppend(foldValueChar_local3).orElse(foldValueChar_local4, appended);
			}
		}
	}
	if (/* c == '<' || c == '(' */){
		return appended.enter(foldValueChar_local6);
	}
	if (/* c == '>' || c == ')' */){
		return appended.exit(foldValueChar_local7);
	}
	return appended;
}
/* private static */ int isSymbol(struct Main this, char* input){
	auto isSymbol_local0 = input;
	auto isSymbol_local1 = stripped;
	auto isSymbol_local2 = stripped;
	auto isSymbol_local3 = Character;
	struct var stripped = input.strip(isSymbol_local0);
	if (stripped.isEmpty(isSymbol_local1)){
		return false;
	}
	/* for (var i = 0; i < stripped.length(); i++) */{
		struct var c = stripped.charAt(isSymbol_local2, i);
		if (Character.isLetter(isSymbol_local3, c)){
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
	return supplier.get(orElseGet_local0);
}
/* @Override
        public <R> */ Option<struct R> flatMap(struct None</*  */> this, Option<struct R> (*)(/*  */) mapper){
	return None</*  */>();
}
/* @Override
        public */ Option</*  */> or(struct None</*  */> this, Supplier<Option</*  */>> supplier){
	auto or_local0 = supplier;
	return supplier.get(or_local0);
}
/* @Override
        public <R> */ Option<struct R> map(struct Some</*  */> this, struct R (*)(/*  */) mapper){
	auto map_local0 = mapper;
	return Some</*  */>(mapper.apply(map_local0, this.value));
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
	return mapper.apply(flatMap_local0, this.value);
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
	return current.addLast(fold_local0, element);
}
/* @Override
        public <R> */ Option<struct R> map(struct Some<char*> this, struct R (*)(char*) mapper){
	auto map_local0 = mapper;
	return Some</*  */>(mapper.apply(map_local0, this.value));
}
/* @Override
        public */ int isPresent(struct Some<char*> this){
	return true;
}
/* @Override
        public */ char* orElse(struct Some<char*> this, char* other){
	return this.value;
}
/* @Override
        public */ int isEmpty(struct Some<char*> this){
	return false;
}
/* @Override
        public */ char* orElseGet(struct Some<char*> this, Supplier<char*> supplier){
	return this.value;
}
/* @Override
        public <R> */ Option<struct R> flatMap(struct Some<char*> this, Option<struct R> (*)(char*) mapper){
	auto flatMap_local0 = mapper;
	return mapper.apply(flatMap_local0, this.value);
}
/* @Override
        public */ Option<char*> or(struct Some<char*> this, Supplier<Option<char*>> supplier){
	return this;
}
