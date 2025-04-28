struct Defined extends Assignable {
};
struct Value extends Assignable {
};
struct Node {
};
struct Assignable extends Node {
};
struct Type extends Node {
};
struct State {
};
struct Joiner {
};
struct Definition {
};
struct Content {
};
struct Whitespace implements Defined, Value {
};
struct StringValue {
};
struct Symbol {
};
struct Invocation {
};
struct DataAccess {
};
struct Operation {
};
struct Iterators {
};
struct CharValue {
};
struct Not {
};
struct StructType {
};
struct Generic {
};
struct Ref {
};
struct Functional {
};
struct Main {
	/* private static String functionName = "" */;
	/* private static int functionLocalCounter = 0 */;
};
struct Option_Type {
};
struct Option_i8_star {
};
struct Tuple_i8_State {
};
struct Option_Tuple_i8_State {
};
struct None_/*  */ {
};
struct Some_/*  */ {
};
struct Tuple_/*  */ {
};
struct Option_State {
};
struct Option_i8 {
};
struct Iterator_T {
};
struct Option_T {
};
struct Iterator_/*  */ {
};
struct Option_IOException {
};
struct Result_i8_star_IOException {
};
struct Ok_/*  */ {
};
struct Err_/*  */ {
};
struct List_i8_star {
};
struct List_T {
};
struct ListCollector_/*  */ {
};
struct Option_Whitespace {
};
struct Option_Invocation {
};
struct Option_Definition {
};
struct State fromInput(struct State this, char* input){
	return State(input, listEmpty(), "", 0, 0);
}
Option<Tuple<char, struct State>> pop(struct State this){
	if (this.index >= this.input.length(this.index >= this.input)){
		return None_/*  */();
	}
	/* this.input.charAt(this.input, this.index) */ escaped = this.input.charAt(this.input, this.index);
	return Some_/*  */(Tuple_i8_State(escaped, State(this.input, this.segments, this.buffer, this.depth, this.index + 1)));
}
auto popAndAppendToTuple_local1(auto tuple){
	/* tuple.left */ poppedChar = tuple.left;
	/* tuple.right */ poppedState = tuple.right;
	/* poppedState.append(poppedState, poppedChar) */ appended = poppedState.append(poppedState, poppedChar);
	return Tuple_/*  */(poppedChar, appended);
}
Option<Tuple<char, struct State>> popAndAppendToTuple(struct State this){
	Option<Tuple<char, struct State>> popAndAppendToTuple_local3 = this.pop(this);
	return popAndAppendToTuple_local3.map(popAndAppendToTuple_local3, popAndAppendToTuple_local1);
}
int isLevel(struct State this){
	return this.depth == 0;
}
struct State enter(struct State this){
	return State(this.input, this.segments, this.buffer, this.depth + 1, this.index);
}
struct State exit(struct State this){
	return State(this.input, this.segments, this.buffer, this.depth - 1, this.index);
}
struct State advance(struct State this){
	return State(this.input, this.segments.addLast(this.segments, this.buffer), "", this.depth, this.index);
}
int isShallow(struct State this){
	return this.depth == 1;
}
struct State append(struct State this, struct char c){
	return State(this.input, this.segments, this.buffer + c, this.depth, this.index);
}
Option<struct State> popAndAppend(struct State this){
	Option<Tuple<char, struct State>> popAndAppend_local1 = this.popAndAppendToTuple(this);
	return popAndAppend_local1.map(popAndAppend_local1, /* Tuple::right */);
}
Option<char> peek(struct State this){
	if (this.index < this.input.length(this.index < this.input)){
		return Some_/*  */(this.input.charAt(this.input, this.index));
	}
	else {
		return None_/*  */();
	}
}
struct private Joiner(struct Joiner this){
	this("");
}
Option<char*> createInitial(struct Joiner this){
	return None_/*  */();
}
auto fold_local0(auto inner){
	return inner + this.delimiter + element;
}
Option<char*> fold(struct Joiner this, Option<char*> current, char* element){
	/* current.map(current, fold_local0) */ fold_local2 = current.map(current, fold_local0);
	return Some_/*  */(fold_local2.orElse(fold_local2, element));
}
char* generate(struct Definition this){
	return this.type.generate() + " " + this.name(this.type.generate() + " " + this);
}
Option<struct Type> findType(struct Definition this){
	return Some_/*  */(this.type);
}
Option<char*> findName(struct Definition this){
	return Some_/*  */(this.name);
}
char* generate(struct Content this){
	return generatePlaceholder(this.input);
}
Option<struct Type> findType(struct Content this){
	return None_/*  */();
}
Option<char*> findName(struct Content this){
	return None_/*  */();
}
char* stringify(struct Content this){
	return generatePlaceholder(this.input);
}
char* generate(struct Whitespace implements Defined, Value this){
	return "";
}
Option<struct Type> findType(struct Whitespace implements Defined, Value this){
	return None_/*  */();
}
Option<char*> findName(struct Whitespace implements Defined, Value this){
	return None_/*  */();
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
auto fromOption_local0(auto _){
	return /* /* new EmptyHead */ */;
}
auto fromOption_local1(auto var value){
	return /* /* new SingleHead */ */;
}
Iterator<struct T> fromOption(struct Iterators this, Option<struct T> option){
	return Iterator_/*  */(/* switch (option) {
                case None */ < T > fromOption_local0 < T > /* ();
                case Some */ < T > fromOption_local1 <  > /* (value);
            } */);
}
char* generate(struct CharValue this){
	return "'" + this.slice + "'";
}
char* generate(struct Not this){
	return "!" + this.value.generate("!" + this.value);
}
struct public StructType(struct StructType this, char* name){
	this(name, Lists.listEmpty(Lists));
}
char* generate(struct StructType this){
	return "struct " + this.name;
}
char* stringify(struct StructType this){
	return this.name;
}
auto find_local2(auto definition){
	return definition.name.equals(definition.name, definition.name, name);
}
auto find_local6(auto definition){
	return definition.findType(definition, definition);
}
Option<struct Type> find(struct StructType this, char* name){
	/* this.properties.iter(this.properties) */ find_local4 = this.properties.iter(this.properties);
	/* find_local4.filter(find_local4, find_local2) */ find_local8 = find_local4.filter(find_local4, find_local2);
	/* find_local8.map(find_local8, find_local6) */ find_local9 = find_local8.map(find_local8, find_local6);
	/* find_local9.flatMap(find_local9, /* Iterators::fromOption */) */ find_local10 = find_local9.flatMap(find_local9, /* Iterators::fromOption */);
	return find_local10.next(find_local10);
}
struct StructType define(struct StructType this, struct Definition definition){
	return StructType(this.name, this.properties.addLast(this.properties, definition));
}
char* generate(struct Generic this){
	return this.base + "<" + generateValueList(this.args) + ">";
}
char* stringify(struct Generic this){
	/* this.base + "_" + this.args.iter(this.base + "_" + this.args) */ stringify_local1 = this.base + "_" + this.args.iter(this.base + "_" + this.args);
	/* stringify_local1.map(stringify_local1, /* Type::stringify */) */ stringify_local2 = stringify_local1.map(stringify_local1, /* Type::stringify */);
	/* stringify_local2.collect(stringify_local2, Joiner("_")) */ stringify_local3 = stringify_local2.collect(stringify_local2, Joiner("_"));
	return stringify_local3.orElse(stringify_local3, "");
}
char* stringify(struct Ref this){
	return this.type.stringify() + "_star";
}
char* generate(struct Ref this){
	return this.type.generate() + "*";
}
char* generate(struct Functional this){
	/* generateValueList(this.paramTypes(this)) */ generated = generateValueList(this.paramTypes(this));
	return this.returns.generate() + " (*)(" + generated + ")";
}
char* stringify(struct Functional this){
	return "_Func_" + /* generateValueList(this */.paramTypes) + "_" + this.returns.stringify() + "_";
}
auto run_local0(auto var error){
	return /* /* new Some */ */;
}
Option<struct IOException> run(struct Main this){
	/* return switch (readString(SOURCE)) */{
		/* case Err */ < /* String, IOException */ > run_local0 <  > (error);
		/* case Ok<String, IOException>(var input) -> */{
			/* compileRoot(input) */ output = compileRoot(input);
			/* yield writeTarget */(TARGET, output);
		}
	}
	/* ; */
}
void main(struct Main this){
	/* run() */ main_local0 = run();
	main_local0.ifPresent(main_local0, /* Throwable::printStackTrace */);
}
Option<struct IOException> writeTarget(struct Main this, struct Path target, char* csq){
	/* try */{
		Files.writeString(Files, target, csq);
		return None_/*  */();
	}
	/* catch (IOException e) */{
		return Some_/*  */(e);
	}
}
Result<char*, struct IOException> readString(struct Main this, struct Path source){
	/* try */{
		return Ok_/*  */(Files.readString(Files, source));
	}
	/* catch (IOException e) */{
		return Err_/*  */(e);
	}
}
auto compileRoot_local1(auto tuple){
	if (expandables.containsKey(expandables, tuple.left)){
	/* expandable.apply(expandable, tuple.right) */ compileRoot_local4 = expandable.apply(expandable, tuple.right);
		/* expandables.get(expandables, tuple.left) */ expandable = expandables.get(expandables, tuple.left);
		return compileRoot_local4.orElse(compileRoot_local4, "");
	}
	else {
		/* /* Generic(tuple.left, tuple.right).generate */() */ generated = /* Generic(tuple.left, tuple.right).generate */();
		return "// " + generated + ">\n";
	}
}
char* compileRoot(struct Main this, char* input){
	/* expansions.iter(expansions) */ compileRoot_local6 = expansions.iter(expansions);
	/* compileRoot_local6.map(compileRoot_local6, compileRoot_local1) */ compileRoot_local7 = compileRoot_local6.map(compileRoot_local6, compileRoot_local1);
	/* compileRoot_local7.collect(compileRoot_local7, Joiner()) */ compileRoot_local8 = compileRoot_local7.collect(compileRoot_local7, Joiner());
	/* compileStatements(input, /* Main::compileRootSegment */) */ compiled = compileStatements(input, /* Main::compileRootSegment */);
	/* compileRoot_local8.orElse(compileRoot_local8, "") */ joinedExpansions = compileRoot_local8.orElse(compileRoot_local8, "");
	return compiled + join(structs) + joinedExpansions + join(methods);
}
char* join(struct Main this, List<char*> list){
	return join(list, "");
}
char* join(struct Main this, List<char*> list, char* delimiter){
	/* list.iter(list) */ join_local1 = list.iter(list);
	/* join_local1.collect(join_local1, Joiner(delimiter)) */ join_local2 = join_local1.collect(join_local1, Joiner(delimiter));
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
	/* parsed.iter(parsed) */ generateAll_local1 = parsed.iter(parsed);
	return generateAll_local1.fold(generateAll_local1, "", merger);
}
List<struct T> parseAll(struct Main this, char* input, struct State (*)(struct State, char) folder, struct T (*)(char*) compiler){
	/* divideAll(input, folder) */ parseAll_local0 = divideAll(input, folder);
	/* parseAll_local0.iter(parseAll_local0) */ parseAll_local1 = parseAll_local0.iter(parseAll_local0);
	/* parseAll_local1.map(parseAll_local1, compiler) */ parseAll_local2 = parseAll_local1.map(parseAll_local1, compiler);
	return parseAll_local2.collect(parseAll_local2, ListCollector_/*  */());
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
	/* State.fromInput(State, input) */ state = State.fromInput(State, input);
	while (1){
	/* foldSingleQuotes(withoutNext, next) */ divideAll_local5 = foldSingleQuotes(withoutNext, next);
	/* divideAll_local5.or(divideAll_local5, divideAll_local4) */ divideAll_local9 = divideAll_local5.or(divideAll_local5, divideAll_local4);
		/* state.pop(state) */ maybeNextTuple = state.pop(state);
		if (maybeNextTuple.isEmpty(maybeNextTuple)){
			break;
		}
		/* maybeNextTuple.orElse(maybeNextTuple, null) */ nextTuple = maybeNextTuple.orElse(maybeNextTuple, null);
		/* nextTuple.left */ next = nextTuple.left;
		/* nextTuple.right */ withoutNext = nextTuple.right;
		state = divideAll_local9.orElseGet(divideAll_local9, divideAll_local7);
	}
	return state.advance(state).segments;
}
Option<struct State> foldDoubleQuotes(struct Main this, struct State withoutNext, struct char c){
	if (c != '\"'){
		return None_/*  */();
	}
	/* withoutNext.append(withoutNext, c) */ current = withoutNext.append(withoutNext, c);
	while (1){
		/* current.popAndAppendToTuple(current) */ maybeNext = current.popAndAppendToTuple(current);
		if (!(/* maybeNext instanceof Some */(/* var next */))){
			break;
		}
		current = next.right;
		if (next.left == '"'){
			break;
		}
		if (next.left == '\\'){
	/* current.popAndAppend(current) */ foldDoubleQuotes_local3 = current.popAndAppend(current);
			current = foldDoubleQuotes_local3.orElse(foldDoubleQuotes_local3, current);
		}
	}
	return Some_/*  */(current);
}
auto foldSingleQuotes_local3(auto maybeSlash){
	return maybeSlash.left == '\\' ? maybeSlash.right.popAndAppend() : new Some<>(maybeSlash.left == '\\' ? maybeSlash.right, maybeSlash.left == '\\' ? maybeSlash.right, maybeSlash.right);
}
Option<struct State> foldSingleQuotes(struct Main this, struct State state, struct char next){
	/* appended.popAndAppendToTuple(appended) */ foldSingleQuotes_local5 = appended.popAndAppendToTuple(appended);
	/* foldSingleQuotes_local5.flatMap(foldSingleQuotes_local5, foldSingleQuotes_local3) */ foldSingleQuotes_local6 = foldSingleQuotes_local5.flatMap(foldSingleQuotes_local5, foldSingleQuotes_local3);
	if (next != '\''){
		return None_/*  */();
	}
	/* state.append(state, next) */ appended = state.append(state, next);
	return foldSingleQuotes_local6.flatMap(foldSingleQuotes_local6, /* State::popAndAppend */);
}
struct State foldStatementChar(struct Main this, struct State state, struct char c){
	/* c */ foldStatementChar_local2 = c == ';' && appended;
	/* c */ foldStatementChar_local5 = c == '}' && appended;
	/* state.append(state, c) */ appended = state.append(state, c);
	if (foldStatementChar_local2.isLevel(foldStatementChar_local2)){
		return appended.advance(appended);
	}
	if (foldStatementChar_local5.isShallow(foldStatementChar_local5)){
	/* appended.advance(appended) */ foldStatementChar_local4 = appended.advance(appended);
		return foldStatementChar_local4.exit(foldStatementChar_local4);
	}
	/* if (c == ' */{
		/* /* = '(') {
            return appended */.enter() */ c = /* = '(') {
            return appended */.enter();
	}
	if (c == '}' || c == ')'){
		return appended.exit(appended);
	}
	return appended;
}
auto compileRootSegment_local3(){
	return generatePlaceholder(stripped);
}
char* compileRootSegment(struct Main this, char* input){
	/* compileClass(stripped) */ compileRootSegment_local4 = compileClass(stripped);
	/* input.strip(input) */ stripped = input.strip(input);
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
	/* input.indexOf(input, infix) */ classIndex = input.indexOf(input, infix);
	if (classIndex >= 0){
	/* classIndex */ compileStructure_local1 = classIndex + infix;
		/* input.substring(input, compileStructure_local1.length(compileStructure_local1)) */ afterClass = input.substring(input, compileStructure_local1.length(compileStructure_local1));
		/* afterClass.indexOf(afterClass, "{") */ contentStart = afterClass.indexOf(afterClass, "{");
		if (contentStart >= 0){
	/* afterClass.substring(afterClass, 0, contentStart) */ compileStructure_local5 = afterClass.substring(afterClass, 0, contentStart);
	/* permitsIndex */ compileStructure_local7 = permitsIndex >= /* 0
                        ? beforeContent */;
	/* contentStart */ compileStructure_local9 = contentStart + "{";
	/* afterClass.substring(afterClass, compileStructure_local9.length(compileStructure_local9)) */ compileStructure_local11 = afterClass.substring(afterClass, compileStructure_local9.length(compileStructure_local9));
			/* compileStructure_local5.strip(compileStructure_local5) */ beforeContent = compileStructure_local5.strip(compileStructure_local5);
			/* beforeContent.indexOf(beforeContent, " permits") */ permitsIndex = beforeContent.indexOf(beforeContent, " permits");
			/* compileStructure_local7.substring(compileStructure_local7, 0, permitsIndex).strip()
                        : beforeContent */ withoutPermits = compileStructure_local7.substring(compileStructure_local7, 0, permitsIndex).strip()
                        : beforeContent;
			/* withoutPermits.indexOf(withoutPermits, "(") */ paramStart = withoutPermits.indexOf(withoutPermits, "(");
			/* compileStructure_local11.strip(compileStructure_local11) */ withEnd = compileStructure_local11.strip(compileStructure_local11);
			if (paramStart >= 0){
	/* withoutPermits.substring(withoutPermits, 0, paramStart) */ compileStructure_local13 = withoutPermits.substring(withoutPermits, 0, paramStart);
				/* compileStructure_local13.strip(compileStructure_local13) */ withoutParams = compileStructure_local13.strip(compileStructure_local13);
				return getString(withoutParams, withEnd);
			}
			else {
				return getString(withoutPermits, withEnd);
			}
		}
	}
	return None_/*  */();
}
Option<char*> getString(struct Main this, char* beforeContent, char* withEnd){
	int getString_local0 = !withEnd;
	if (getString_local0.endsWith(getString_local0, "}")){
		return None_/*  */();
	}
	/* withEnd.substring(withEnd, 0, withEnd.length() - "}".length(withEnd.length() - "}")) */ content = withEnd.substring(withEnd, 0, withEnd.length() - "}".length(withEnd.length() - "}"));
	/* beforeContent.strip(beforeContent) */ strippedBeforeContent = beforeContent.strip(beforeContent);
	if (strippedBeforeContent.endsWith(strippedBeforeContent, ">")){
		/* strippedBeforeContent.substring(strippedBeforeContent, 0, strippedBeforeContent.length() - ">".length(strippedBeforeContent.length() - ">")) */ withoutEnd = strippedBeforeContent.substring(strippedBeforeContent, 0, strippedBeforeContent.length() - ">".length(strippedBeforeContent.length() - ">"));
		/* withoutEnd.indexOf(withoutEnd, "<") */ typeParamStart = withoutEnd.indexOf(withoutEnd, "<");
		if (typeParamStart >= 0){
	/* withoutEnd.substring(withoutEnd, 0, typeParamStart) */ getString_local8 = withoutEnd.substring(withoutEnd, 0, typeParamStart);
	/* typeParamStart */ getString_local9 = typeParamStart + "<";
			/* getString_local8.strip(getString_local8) */ name = getString_local8.strip(getString_local8);
			/* withoutEnd.substring(withoutEnd, getString_local9.length(getString_local9)) */ substring = withoutEnd.substring(withoutEnd, getString_local9.length(getString_local9));
			/* listFromArray(substring.split(substring, Pattern.quote(Pattern, ","))) */ typeParameters = listFromArray(substring.split(substring, Pattern.quote(Pattern, ",")));
			return assembleStructure(typeParameters, name, content);
		}
	}
	return assembleStructure(listEmpty(), strippedBeforeContent, content);
}
Option<char*> assembleStructure(struct Main this, List<char*> typeParams, char* name, char* content){
	int assembleStructure_local1 = !typeParams;
	if (assembleStructure_local1.isEmpty(assembleStructure_local1)){
	/* /* typeParams;
                typeArguments = typeArgs;

                var newName = new Generic */(name, typeArgs) */ assembleStructure_local0 = /* typeParams;
                typeArguments = typeArgs;

                var newName = new Generic */(name, typeArgs);
		/* assembleStructure_local0.stringify();
                return generateStructure(assembleStructure_local0, newName, /* content);
            } */) */ typeParameters = assembleStructure_local0.stringify();
                return generateStructure(assembleStructure_local0, newName, /* content);
            } */);
		return Some_/*  */("");
	}
	return generateStructure(name, content);
}
Option<char*> generateStructure(struct Main this, char* name, char* content){
	structStack = structStack.addLast(structStack, StructType(name));
	/* compileStatements(content, /* Main::compileClassSegment */) */ compiled = compileStatements(content, /* Main::compileClassSegment */);
	structStack = structStack.removeLast(structStack);
	char* generated = "struct " + name + " {" + compiled + "\n};\n";
	structs.addLast(structs, generated);
	return Some_/*  */("");
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
	/* compileStructure(stripped, "record ") */ compileClassSegment_local3 = compileStructure(stripped, "record ");
	/* compileClassSegment_local3.or(compileClassSegment_local3, compileClassSegment_local2) */ compileClassSegment_local5 = compileClassSegment_local3.or(compileClassSegment_local3, compileClassSegment_local2);
	/* compileClassSegment_local5.or(compileClassSegment_local5, compileClassSegment_local4) */ compileClassSegment_local7 = compileClassSegment_local5.or(compileClassSegment_local5, compileClassSegment_local4);
	/* compileClassSegment_local7.or(compileClassSegment_local7, compileClassSegment_local6) */ compileClassSegment_local9 = compileClassSegment_local7.or(compileClassSegment_local7, compileClassSegment_local6);
	/* compileClassSegment_local9.or(compileClassSegment_local9, compileClassSegment_local8) */ compileClassSegment_local11 = compileClassSegment_local9.or(compileClassSegment_local9, compileClassSegment_local8);
	/* input.strip(input) */ stripped = input.strip(input);
	if (stripped.isEmpty(stripped)){
		return "";
	}
	return compileClassSegment_local11.orElseGet(compileClassSegment_local11, compileClassSegment_local10);
}
Option<char*> compileDefinitionStatement(struct Main this, char* input){
	/* input.strip(input) */ stripped = input.strip(input);
	if (stripped.endsWith(stripped, ";")){
		/* stripped.substring(stripped, 0, stripped.length() - ";".length(stripped.length() - ";")) */ withoutEnd = stripped.substring(stripped, 0, stripped.length() - ";".length(stripped.length() - ";"));
		return Some_/*  */("\n\t" + compileDefinitionOrPlaceholder(withoutEnd) + ";");
	}
	return None_/*  */();
}
auto compileMethod_local11(auto parameter){
	return !(/* /* parameter instanceof Whitespace */ */);
}
auto compileMethod_local19(auto last){
	/* newParams.iter(newParams) */ compileMethod_local21 = newParams.iter(newParams);
	/* compileMethod_local21.map(compileMethod_local21, /* Defined::findType */) */ compileMethod_local22 = compileMethod_local21.map(compileMethod_local21, /* Defined::findType */);
	/* compileMethod_local22.flatMap(compileMethod_local22, /* Iterators::fromOption */) */ compileMethod_local23 = compileMethod_local22.flatMap(compileMethod_local22, /* Iterators::fromOption */);
	/* defined.findName(defined) */ compileMethod_local25 = defined.findName(defined);
	/* defined.findType(defined) */ compileMethod_local27 = defined.findType(defined);
	/* compileMethod_local23.collect(compileMethod_local23, ListCollector_/*  */()) */ paramTypes = compileMethod_local23.collect(compileMethod_local23, ListCollector_/*  */());
	/* compileMethod_local25.orElse(compileMethod_local25, "?") */ name = compileMethod_local25.orElse(compileMethod_local25, "?");
	/* compileMethod_local27.orElse(compileMethod_local27, Primitive.Auto) */ type = compileMethod_local27.orElse(compileMethod_local27, Primitive.Auto);
	return last.define(last, Definition(Functional(paramTypes, type), name));
}
auto compileMethod_local18(auto method){
	structStack = structStack.mapLast(structStack, compileMethod_local19);
	return method;
}
Option<char*> compileMethod(struct Main this, char* stripped){
	/* paramStart */ compileMethod_local2 = paramStart + "(";
	/* paramEnd */ compileMethod_local6 = paramEnd + ")";
	/* parseValues(params, /* Main::parseParameter */) */ compileMethod_local10 = parseValues(params, /* Main::parseParameter */);
	/* compileMethod_local10.iter(compileMethod_local10) */ compileMethod_local12 = compileMethod_local10.iter(compileMethod_local10);
	/* compileMethod_local12.filter(compileMethod_local12, compileMethod_local11) */ compileMethod_local13 = compileMethod_local12.filter(compileMethod_local12, compileMethod_local11);
	/* Lists.<Defined>listEmpty(Lists) */ compileMethod_local16 = Lists.<Defined>listEmpty(Lists);
	/* compileMethod_local16.addLast(compileMethod_local16, Definition(structStack.last(structStack), "this")) */ compileMethod_local17 = compileMethod_local16.addLast(compileMethod_local16, Definition(structStack.last(structStack), "this"));
	/* assembleMethod(defined, outputParams, content) */ compileMethod_local30 = assembleMethod(defined, outputParams, content);
	/* stripped.indexOf(stripped, "(") */ paramStart = stripped.indexOf(stripped, "(");
	if (paramStart < 0){
		return None_/*  */();
	}
	/* stripped.substring(stripped, 0, paramStart) */ inputDefinition = stripped.substring(stripped, 0, paramStart);
	/* parseDefinitionOrPlaceholder(inputDefinition) */ defined = parseDefinitionOrPlaceholder(inputDefinition);
	if (/* defined instanceof Definition definition */){
		functionName = definition.name;
		functionLocalCounter = 0;
	}
	/* stripped.substring(stripped, compileMethod_local2.length(compileMethod_local2)) */ afterParams = stripped.substring(stripped, compileMethod_local2.length(compileMethod_local2));
	/* afterParams.indexOf(afterParams, ")") */ paramEnd = afterParams.indexOf(afterParams, ")");
	if (paramEnd < 0){
		return None_/*  */();
	}
	/* afterParams.substring(afterParams, 0, paramEnd) */ params = afterParams.substring(afterParams, 0, paramEnd);
	/* afterParams.substring(afterParams, compileMethod_local6.length(compileMethod_local6)) */ withoutParams = afterParams.substring(afterParams, compileMethod_local6.length(compileMethod_local6));
	/* withoutParams.strip(withoutParams) */ withBraces = withoutParams.strip(withoutParams);
	/* if (!withBraces.startsWith(" */{
		/* ") || !withBraces.endsWith("}")) {
            return new Some<>(""); */
	}
	/* withBraces.substring(withBraces, 1, withBraces.length() - 1) */ content = withBraces.substring(withBraces, 1, withBraces.length() - 1);
	/* compileMethod_local13.collect(compileMethod_local13, ListCollector_/*  */()) */ oldParams = compileMethod_local13.collect(compileMethod_local13, ListCollector_/*  */());
	/* compileMethod_local17.addAll(compileMethod_local17, oldParams) */ newParams = compileMethod_local17.addAll(compileMethod_local17, oldParams);
	/* generateValueList(newParams) */ outputParams = generateValueList(newParams);
	return compileMethod_local30.map(compileMethod_local30, compileMethod_local18);
}
char* generateValueList(struct Main this, List<struct T> copy){
	return generateValueList(copy, /* Node::generate */);
}
char* generateValueList(struct Main this, List<struct T> copy, char* (*)(struct T) generate){
	/* copy.iter(copy) */ generateValueList_local1 = copy.iter(copy);
	/* generateValueList_local1.map(generateValueList_local1, generate) */ generateValueList_local2 = generateValueList_local1.map(generateValueList_local1, generate);
	/* generateValueList_local2.collect(generateValueList_local2, Joiner(", ")) */ generateValueList_local3 = generateValueList_local2.collect(generateValueList_local2, Joiner(", "));
	return generateValueList_local3.orElse(generateValueList_local3, "");
}
auto assembleMethod_local0(auto input){
	return compileFunctionSegment(input, 1);
}
Option<char*> assembleMethod(struct Main this, struct Defined definition, char* outputParams, char* content){
	/* parseStatementsWithLocals(content, assembleMethod_local0) */ parsed = parseStatementsWithLocals(content, assembleMethod_local0);
	/* definition.generate() + "(" + outputParams + "){" + generateStatements(parsed) + "\n}\n" */ generated = definition.generate() + "(" + outputParams + "){" + generateStatements(parsed) + "\n}\n";
	methods.addLast(methods, generated);
	return Some_/*  */("");
}
List<char*> parseStatementsWithLocals(struct Main this, char* content, char* (*)(char*) compiler){
	/* Lists.<String>listEmpty(Lists) */ parseStatementsWithLocals_local3 = Lists.<String>listEmpty(Lists);
	/* parseStatementsWithLocals_local3.addAll(parseStatementsWithLocals_local3, elements) */ parseStatementsWithLocals_local4 = parseStatementsWithLocals_local3.addAll(parseStatementsWithLocals_local3, elements);
	statements = statements.addLast(statements, listEmpty());
	/* parseStatements(content, compiler) */ parsed1 = parseStatements(content, compiler);
	/* statements.removeAndGetLast(statements) */ elements = statements.removeAndGetLast(statements);
	return parseStatementsWithLocals_local4.addAll(parseStatementsWithLocals_local4, parsed1);
}
auto parseParameter_local0(auto value){
	return value;
}
auto parseParameter_local2(auto value){
	return value;
}
auto parseParameter_local4(){
	return parseParameter_local3.map(parseParameter_local3, parseParameter_local3, parseParameter_local2);
}
auto parseParameter_local7(){
	return Content(input);
}
struct Defined parseParameter(struct Main this, char* input){
	/* parseWhitespace(input) */ parseParameter_local1 = parseWhitespace(input);
	/* parseDefinition(input) */ parseParameter_local3 = parseDefinition(input);
	/* parseParameter_local1.<Defined>map(parseParameter_local1, parseParameter_local0) */ parseParameter_local6 = parseParameter_local1.<Defined>map(parseParameter_local1, parseParameter_local0);
	/* parseParameter_local6.or(parseParameter_local6, parseParameter_local4) */ parseParameter_local8 = parseParameter_local6.or(parseParameter_local6, parseParameter_local4);
	return parseParameter_local8.orElseGet(parseParameter_local8, parseParameter_local7);
}
Option<struct Whitespace> parseWhitespace(struct Main this, char* input){
	if (input.isBlank(input)){
		return Some_/*  */(Whitespace());
	}
	else {
		return None_/*  */();
	}
}
auto compileFunctionSegment_local13(auto input1){
	return compileFunctionSegment(input1, depth + 1);
}
char* compileFunctionSegment(struct Main this, char* input, int depth){
	char* compileFunctionSegment_local2 = "\n" + "\t";
	/* input.strip(input) */ stripped = input.strip(input);
	if (stripped.isEmpty(stripped)){
		return "";
	}
	/* compileFunctionSegment_local2.repeat(compileFunctionSegment_local2, depth) */ indent = compileFunctionSegment_local2.repeat(compileFunctionSegment_local2, depth);
	if (stripped.endsWith(stripped, ";")){
	/* stripped.substring(stripped, 0, stripped.length() - ";".length(stripped.length() - ";")) */ compileFunctionSegment_local5 = stripped.substring(stripped, 0, stripped.length() - ";".length(stripped.length() - ";"));
		/* compileFunctionSegment_local5.strip(compileFunctionSegment_local5) */ withoutEnd = compileFunctionSegment_local5.strip(compileFunctionSegment_local5);
		/* compileStatementValue(withoutEnd) */ maybeStatementValue = compileStatementValue(withoutEnd);
		if (/* maybeStatementValue instanceof Some */(/* var statementValue */)){
			return indent + statementValue + ";";
		}
	}
	if (stripped.endsWith(stripped, "}")){
		/* stripped.substring(stripped, 0, stripped.length() - "}".length(stripped.length() - "}")) */ withoutEnd = stripped.substring(stripped, 0, stripped.length() - "}".length(stripped.length() - "}"));
		/* withoutEnd.indexOf(withoutEnd, "{") */ contentStart = withoutEnd.indexOf(withoutEnd, "{");
		if (contentStart >= 0){
	/* contentStart */ compileFunctionSegment_local11 = contentStart + "{";
			/* withoutEnd.substring(withoutEnd, 0, contentStart) */ beforeBlock = withoutEnd.substring(withoutEnd, 0, contentStart);
			/* withoutEnd.substring(withoutEnd, compileFunctionSegment_local11.length(compileFunctionSegment_local11)) */ content = withoutEnd.substring(withoutEnd, compileFunctionSegment_local11.length(compileFunctionSegment_local11));
			/* parseStatementsWithLocals(content, compileFunctionSegment_local13) */ outputContent = parseStatementsWithLocals(content, compileFunctionSegment_local13);
			return indent + compileBeforeBlock(beforeBlock) + "{" + join(outputContent) + indent + "}";
		}
	}
	return indent + generatePlaceholder(stripped);
}
Option<char*> compileStatementValue(struct Main this, char* input){
	/* input.strip(input) */ stripped = input.strip(input);
	if (stripped.equals(stripped, "break")){
		return Some_/*  */("break");
	}
	if (stripped.startsWith(stripped, "return ")){
	char* compileStatementValue_local2 = "return ";
		/* stripped.substring(stripped, compileStatementValue_local2.length(compileStatementValue_local2)) */ value = stripped.substring(stripped, compileStatementValue_local2.length(compileStatementValue_local2));
		return Some_/*  */("return " + compileValue(value));
	}
	/* stripped.indexOf(stripped, "=") */ valueSeparator = stripped.indexOf(stripped, "=");
	if (valueSeparator >= 0){
	/* valueSeparator */ compileStatementValue_local7 = valueSeparator + "=";
		/* stripped.substring(stripped, 0, valueSeparator) */ assignableString = stripped.substring(stripped, 0, valueSeparator);
		/* stripped.substring(stripped, compileStatementValue_local7.length(compileStatementValue_local7)) */ valueString = stripped.substring(stripped, compileStatementValue_local7.length(compileStatementValue_local7));
		/* parseAssignable(assignableString) */ assignable = parseAssignable(assignableString);
		/* parseValue(valueString) */ value = parseValue(valueString);
		/* resolve(value) */ type = resolve(value);
		/* Assignable newAssignable; */
		if (/* assignable instanceof Definition definition */){
			newAssignable = Definition(type, definition.name);
		}
		else {
			newAssignable = assignable;
		}
		return Some_/*  */(newAssignable.generate() + " = " + value.generate(newAssignable.generate() + " = " + value));
	}
	if (/* compileInvokable(input) instanceof Some */(/* var invokable */)){
		return Some_/*  */(invokable.generate(invokable));
	}
	return None_/*  */();
}
struct Type resolve(struct Main this, struct Value value){
	/* return switch (value) */{
		/* case BooleanValue _ -> Primitive.Bool; */
		/* case CharValue _ -> Primitive.I8; */
		/* case Content content -> content; */
		/* case DataAccess dataAccess - */ > resolveDataAccess(dataAccess);
		/* case Invocation invocation - */ > resolveInvocation(invocation);
		/* case Not _ -> Primitive.Bool; */
		/* case Operation operation - */ > resolve(operation.left);
		/* case StringValue _ - */ > /* new Ref */(Primitive.I8);
		/* case Symbol symbol - */ > resolveSymbol(symbol);
		/* case Whitespace _ -> Primitive.Void; */
	}
	/* ; */
}
struct Type resolveSymbol(struct Main this, struct Symbol symbol){
	if (symbol.value.equals(symbol.value, "this")){
		return structStack.last(structStack);
	}
	return Content(symbol.value);
}
struct Type resolveInvocation(struct Main this, struct Invocation invocation){
	/* invocation.caller */ caller = invocation.caller;
	/* resolve(caller) */ resolvedCaller = resolve(caller);
	if (/* resolvedCaller instanceof Functional functional */){
		return functional.returns;
	}
	else {
		return Content(invocation.generate(invocation));
	}
}
struct Type resolveDataAccess(struct Main this, struct DataAccess dataAccess){
	/* dataAccess.parent */ parent = dataAccess.parent;
	/* resolve(parent) */ resolved = resolve(parent);
	if (/* resolved instanceof StructType structType */){
		/* structType.find(structType, dataAccess.property) */ typeOption = structType.find(structType, dataAccess.property);
		if (/* typeOption instanceof Some */ < Type > (/* var propertyType */)){
			return propertyType;
		}
	}
	return Content(dataAccess.generate(dataAccess));
}
auto parseAssignable_local0(auto value1){
	return value1;
}
auto parseAssignable_local2(){
	return parseValue(definition);
}
struct Assignable parseAssignable(struct Main this, char* definition){
	/* parseDefinition(definition) */ parseAssignable_local1 = parseDefinition(definition);
	/* parseAssignable_local1.<Assignable>map(parseAssignable_local1, parseAssignable_local0) */ parseAssignable_local3 = parseAssignable_local1.<Assignable>map(parseAssignable_local1, parseAssignable_local0);
	return parseAssignable_local3.orElseGet(parseAssignable_local3, parseAssignable_local2);
}
auto compileBeforeBlock_local2(){
	return compileConditional(stripped, "while");
}
auto compileBeforeBlock_local4(){
	return generatePlaceholder(stripped);
}
char* compileBeforeBlock(struct Main this, char* input){
	/* compileConditional(stripped, "if") */ compileBeforeBlock_local3 = compileConditional(stripped, "if");
	/* compileBeforeBlock_local3.or(compileBeforeBlock_local3, compileBeforeBlock_local2) */ compileBeforeBlock_local5 = compileBeforeBlock_local3.or(compileBeforeBlock_local3, compileBeforeBlock_local2);
	/* input.strip(input) */ stripped = input.strip(input);
	if (stripped.equals(stripped, "else")){
		return "else ";
	}
	return compileBeforeBlock_local5.orElseGet(compileBeforeBlock_local5, compileBeforeBlock_local4);
}
Option<char*> compileConditional(struct Main this, char* stripped, char* prefix){
	if (stripped.startsWith(stripped, prefix)){
	/* stripped.substring(stripped, prefix.length(prefix)) */ compileConditional_local2 = stripped.substring(stripped, prefix.length(prefix));
		/* compileConditional_local2.strip(compileConditional_local2) */ withoutPrefix = compileConditional_local2.strip(compileConditional_local2);
		if (withoutPrefix.startsWith("(") && withoutPrefix.endsWith(withoutPrefix.startsWith("(") && withoutPrefix, ")")){
			/* withoutPrefix.substring(withoutPrefix, 1, withoutPrefix.length() - 1) */ condition = withoutPrefix.substring(withoutPrefix, 1, withoutPrefix.length() - 1);
			return Some_/*  */(prefix + " (" + compileValue(condition) + ")");
		}
	}
	return None_/*  */();
}
char* compileValue(struct Main this, char* input){
	/* parseValue(input) */ compileValue_local0 = parseValue(input);
	return compileValue_local0.generate(compileValue_local0);
}
auto parseValue_local17(auto value){
	return parseValue_local16.isEmpty(parseValue_local16, parseValue_local16);
}
struct Value parseValue(struct Main this, char* input){
	/* input.strip(input) */ stripped = input.strip(input);
	if (stripped.isEmpty(stripped)){
		return Whitespace();
	}
	if (stripped.equals(stripped, "false")){
		return BooleanValue.False;
	}
	if (stripped.equals(stripped, "true")){
		return BooleanValue.True;
	}
	/* stripped.indexOf(stripped, "->") */ arrowIndex = stripped.indexOf(stripped, "->");
	if (arrowIndex >= 0){
	/* stripped.substring(stripped, 0, arrowIndex) */ parseValue_local6 = stripped.substring(stripped, 0, arrowIndex);
	/* arrowIndex */ parseValue_local7 = arrowIndex + "->";
	/* stripped.substring(stripped, parseValue_local7.length(parseValue_local7)) */ parseValue_local9 = stripped.substring(stripped, parseValue_local7.length(parseValue_local7));
		/* parseValue_local6.strip(parseValue_local6) */ beforeArrow = parseValue_local6.strip(parseValue_local6);
		/* parseValue_local9.strip(parseValue_local9) */ afterArrow = parseValue_local9.strip(parseValue_local9);
		if (isSymbol(beforeArrow)){
			return assembleLambda(afterArrow, Lists.listFrom(Lists, beforeArrow));
		}
		if (beforeArrow.startsWith("(") && beforeArrow.endsWith(beforeArrow.startsWith("(") && beforeArrow, ")")){
	/* beforeArrow.substring(beforeArrow, 1, beforeArrow.length() - 1) */ parseValue_local13 = beforeArrow.substring(beforeArrow, 1, beforeArrow.length() - 1);
	/* Iterators.fromArray(Iterators, parseValue_local13.split(parseValue_local13, Pattern.quote(Pattern, ","))) */ parseValue_local15 = Iterators.fromArray(Iterators, parseValue_local13.split(parseValue_local13, Pattern.quote(Pattern, ",")));
	int parseValue_local16 = !value;
	/* parseValue_local15.map(parseValue_local15, /* String::strip */) */ parseValue_local19 = parseValue_local15.map(parseValue_local15, /* String::strip */);
	/* parseValue_local19.filter(parseValue_local19, parseValue_local17) */ parseValue_local20 = parseValue_local19.filter(parseValue_local19, parseValue_local17);
			/* parseValue_local20.collect(parseValue_local20, ListCollector_/*  */()) */ args = parseValue_local20.collect(parseValue_local20, ListCollector_/*  */());
			return assembleLambda(afterArrow, args);
		}
	}
	if (/* compileInvokable(stripped) instanceof Some */(/* var invokable */)){
		return invokable;
	}
	if (isSymbol(stripped)){
		return Symbol(stripped);
	}
	if (isNumber(stripped)){
		return Symbol(stripped);
	}
	/* stripped.lastIndexOf(stripped, /* " */.") */ separator = stripped.lastIndexOf(stripped, /* " */.");
	if (separator >= 0){
	/* stripped.substring(stripped, separator + /* " */.".length(separator + /* " */.")) */ parseValue_local26 = stripped.substring(stripped, separator + /* " */.".length(separator + /* " */."));
		/* stripped.substring(stripped, 0, separator) */ value = stripped.substring(stripped, 0, separator);
		/* parseValue_local26.strip(parseValue_local26) */ property = parseValue_local26.strip(parseValue_local26);
		return DataAccess(parseValue(value), property);
	}
	if (stripped.length() >= 2 && stripped.startsWith("\"") && stripped.endsWith(stripped.length() >= 2 && stripped.startsWith("\"") && stripped, "\"")){
		return StringValue(stripped.substring(stripped, 1, stripped.length() - 1));
	}
	if (stripped.length() >= 2 && stripped.startsWith("'") && stripped.endsWith(stripped.length() >= 2 && stripped.startsWith("'") && stripped, "'")){
		return CharValue(stripped.substring(stripped, 1, stripped.length() - 1));
	}
	if (stripped.startsWith(stripped, "!")){
		return Not(parseValue(input.substring(input, 1)));
	}
	/* for (var operator : Operator.values()) */{
		/* stripped.indexOf(stripped, operator.representation) */ operatorIndex = stripped.indexOf(stripped, operator.representation);
		if (operatorIndex >= 0){
			/* stripped.substring(stripped, 0, operatorIndex) */ left = stripped.substring(stripped, 0, operatorIndex);
			/* stripped.substring(stripped, operatorIndex + operator.representation.length(operatorIndex + operator.representation)) */ right = stripped.substring(stripped, operatorIndex + operator.representation.length(operatorIndex + operator.representation));
			return Operation(parseValue(left), operator, parseValue(right));
		}
	}
	return Content(stripped);
}
auto compileInvokable_local13(auto value){
	return !(/* /* value instanceof Whitespace */ */);
}
Option<struct Invocation> compileInvokable(struct Main this, char* stripped){
	int compileInvokable_local0 = !stripped;
	/* stripped.substring(stripped, 0, stripped.length() - ")".length(stripped.length() - ")")) */ compileInvokable_local3 = stripped.substring(stripped, 0, stripped.length() - ")".length(stripped.length() - ")"));
	/* parseValues(arguments, /* Main::parseValue */) */ compileInvokable_local12 = parseValues(arguments, /* Main::parseValue */);
	/* compileInvokable_local12.iter(compileInvokable_local12) */ compileInvokable_local14 = compileInvokable_local12.iter(compileInvokable_local12);
	/* compileInvokable_local14.filter(compileInvokable_local14, compileInvokable_local13) */ compileInvokable_local15 = compileInvokable_local14.filter(compileInvokable_local14, compileInvokable_local13);
	/* Lists.<Value>listEmpty(Lists) */ compileInvokable_local19 = Lists.<Value>listEmpty(Lists);
	/* compileInvokable_local19.addLast(compileInvokable_local19, symbol) */ compileInvokable_local20 = compileInvokable_local19.addLast(compileInvokable_local19, symbol);
	if (compileInvokable_local0.endsWith(compileInvokable_local0, ")")){
		return None_/*  */();
	}
	/* compileInvokable_local3.strip(compileInvokable_local3) */ withoutEnd = compileInvokable_local3.strip(compileInvokable_local3);
	/* divideAll(withoutEnd, /* Main::foldInvokableStart */) */ divisions = divideAll(withoutEnd, /* Main::foldInvokableStart */);
	if (divisions.size() < 2){
		return None_/*  */();
	}
	/* join(divisions.subList(divisions, 0, divisions.size() - 1), "") */ joined = join(divisions.subList(divisions, 0, divisions.size() - 1), "");
	/* joined.substring(joined, 0, joined.length() - ")".length(joined.length() - ")")) */ caller = joined.substring(joined, 0, joined.length() - ")".length(joined.length() - ")"));
	/* divisions.last(divisions) */ arguments = divisions.last(divisions);
	/* Value parsedCaller; */
	if (caller.startsWith(caller, "new ")){
	char* compileInvokable_local8 = "new ";
	/* parseType(caller.substring(caller, compileInvokable_local8.length(compileInvokable_local8))) */ compileInvokable_local10 = parseType(caller.substring(caller, compileInvokable_local8.length(compileInvokable_local8)));
		parsedCaller = Symbol(compileInvokable_local10.stringify(compileInvokable_local10));
	}
	else {
		parsedCaller = parseValue(caller);
	}
	/* compileInvokable_local15.collect(compileInvokable_local15, ListCollector_/*  */()) */ parsedArgs = compileInvokable_local15.collect(compileInvokable_local15, ListCollector_/*  */());
	if (!(/* parsedCaller instanceof DataAccess */(/* var parent */, /* var property */))){
		return Some_/*  */(Invocation(parsedCaller, parsedArgs));
	}
	/* generateName() */ name = generateName();
	/* Value symbol; */
	if (/* parent instanceof Symbol */ || /* parent instanceof DataAccess */){
		symbol = parent;
	}
	else {
	/* statements.last(statements) */ compileInvokable_local17 = statements.last(statements);
		/* resolve(parent) */ type = resolve(parent);
		/* "\n\t" + type.generate() + " " + name + " = " + parent.generate() + ";" */ statement = "\n\t" + type.generate() + " " + name + " = " + parent.generate() + ";";
		compileInvokable_local17.addLast(compileInvokable_local17, statement);
		symbol = Symbol(name);
	}
	/* compileInvokable_local20.addAll(compileInvokable_local20, parsedArgs) */ newArgs = compileInvokable_local20.addAll(compileInvokable_local20, parsedArgs);
	return Some_/*  */(Invocation(DataAccess(symbol, property), newArgs));
}
auto assembleLambda_local1(auto name){
	return "auto " + name;
}
struct Symbol assembleLambda(struct Main this, char* afterArrow, List<char*> names){
	/* names.iter(names) */ assembleLambda_local2 = names.iter(names);
	/* assembleLambda_local2.map(assembleLambda_local2, assembleLambda_local1) */ assembleLambda_local3 = assembleLambda_local2.map(assembleLambda_local2, assembleLambda_local1);
	/* assembleLambda_local3.collect(assembleLambda_local3, Joiner(", ")) */ assembleLambda_local4 = assembleLambda_local3.collect(assembleLambda_local3, Joiner(", "));
	/* assembleLambda_local4.orElse(assembleLambda_local4, "") */ params = assembleLambda_local4.orElse(assembleLambda_local4, "");
	/* if (afterArrow.startsWith(" */{
		/* afterArrow.substring(1, afterArrow.length() - 1);
            var name = generateName();
            assembleMethod(new Definition(Primitive.Auto, name), params, content);
            return new Symbol(afterArrow.substring(1, afterArrow.length() - 1);
            var name = generateName();
            assembleMethod(new Definition(Primitive, name) */ content = afterArrow.substring(1, afterArrow.length() - 1);
            var name = generateName();
            assembleMethod(new Definition(Primitive.Auto, name), params, content);
            return new Symbol(afterArrow.substring(1, afterArrow.length() - 1);
            var name = generateName();
            assembleMethod(new Definition(Primitive, name);
	}
	/* compileValue(afterArrow) */ newValue = compileValue(afterArrow);
	/* generateName() */ name = generateName();
	assembleMethod(Definition(Primitive.Auto, name), params, "\n\treturn " + newValue + ";");
	return Symbol(name);
}
char* generateName(struct Main this){
	/* functionName */ name = functionName + "_local" + functionLocalCounter;
	/* functionLocalCounter++; */
	return name;
}
struct State foldInvokableStart(struct Main this, struct State state, char c){
	/* state.append(state, c) */ appended = state.append(state, c);
	if (c == '('){
		/* appended.isLevel() ? appended.advance() : appended */ maybeAdvanced = appended.isLevel() ? appended.advance() : appended;
		return maybeAdvanced.enter(maybeAdvanced);
	}
	if (c == ')'){
		return appended.exit(appended);
	}
	return appended;
}
int isNumber(struct Main this, char* input){
	if (input.isEmpty(input)){
		return 0;
	}
	/* for (var i = 0; i < input.length(); i++) */{
		/* input.charAt(input, i) */ c = input.charAt(input, i);
		if (Character.isDigit(Character, c)){
			/* continue; */
		}
		return 0;
	}
	return 1;
}
char* compileDefinitionOrPlaceholder(struct Main this, char* input){
	/* parseDefinitionOrPlaceholder(input) */ compileDefinitionOrPlaceholder_local0 = parseDefinitionOrPlaceholder(input);
	return compileDefinitionOrPlaceholder_local0.generate(compileDefinitionOrPlaceholder_local0);
}
auto parseDefinitionOrPlaceholder_local0(auto value){
	return value;
}
auto parseDefinitionOrPlaceholder_local2(){
	return Content(input);
}
struct Defined parseDefinitionOrPlaceholder(struct Main this, char* input){
	/* parseDefinition(input) */ parseDefinitionOrPlaceholder_local1 = parseDefinition(input);
	/* parseDefinitionOrPlaceholder_local1.<Defined>map(parseDefinitionOrPlaceholder_local1, parseDefinitionOrPlaceholder_local0) */ parseDefinitionOrPlaceholder_local3 = parseDefinitionOrPlaceholder_local1.<Defined>map(parseDefinitionOrPlaceholder_local1, parseDefinitionOrPlaceholder_local0);
	return parseDefinitionOrPlaceholder_local3.orElseGet(parseDefinitionOrPlaceholder_local3, parseDefinitionOrPlaceholder_local2);
}
Option<struct Definition> parseDefinition(struct Main this, char* input){
	/* nameSeparator */ parseDefinition_local3 = nameSeparator + " ";
	/* input.strip(input) */ stripped = input.strip(input);
	/* stripped.lastIndexOf(stripped, " ") */ nameSeparator = stripped.lastIndexOf(stripped, " ");
	if (nameSeparator < 0){
		return None_/*  */();
	}
	/* stripped.substring(stripped, 0, nameSeparator) */ beforeName = stripped.substring(stripped, 0, nameSeparator);
	/* stripped.substring(stripped, parseDefinition_local3.length(parseDefinition_local3)) */ name = stripped.substring(stripped, parseDefinition_local3.length(parseDefinition_local3));
	if (!isSymbol(name)){
		return None_/*  */();
	}
	/* divideAll(beforeName, /* Main::foldByTypeSeparator */) */ divisions = divideAll(beforeName, /* Main::foldByTypeSeparator */);
	if (divisions.size() == 1){
		return Some_/*  */(Definition(parseType(beforeName), name));
	}
	/* divisions.last(divisions) */ type = divisions.last(divisions);
	return Some_/*  */(Definition(parseType(type), name));
}
struct State foldByTypeSeparator(struct Main this, struct State state, struct char c){
	/* c */ foldByTypeSeparator_local1 = c == ' ' && state;
	if (foldByTypeSeparator_local1.isLevel(foldByTypeSeparator_local1)){
		return state.advance(state);
	}
	/* state.append(state, c) */ appended = state.append(state, c);
	if (c == '<'){
		return appended.enter(appended);
	}
	if (c == '>'){
		return appended.exit(appended);
	}
	return appended;
}
struct Type parseType(struct Main this, char* input){
	/* input.strip(input) */ stripped = input.strip(input);
	/* typeParameters.indexOf(typeParameters, stripped) */ maybeTypeParamIndex = typeParameters.indexOf(typeParameters, stripped);
	if (maybeTypeParamIndex.isPresent(maybeTypeParamIndex)){
		/* maybeTypeParamIndex.orElse(maybeTypeParamIndex, null) */ typeParamIndex = maybeTypeParamIndex.orElse(maybeTypeParamIndex, null);
		return typeArguments.get(typeArguments, typeParamIndex);
	}
	/* switch (stripped) */{
		/* case "int", "boolean" -> */{
			return Primitive.I32;
		}
		/* case "Character" -> */{
			return Primitive.I8;
		}
		/* case "void" -> */{
			return Primitive.Void;
		}
		/* case "var" -> */{
			return Primitive.Auto;
		}
	}
	if (stripped.equals(stripped, "String")){
		return Ref(Primitive.I8);
	}
	if (stripped.endsWith(stripped, ">")){
		/* stripped.substring(stripped, 0, stripped.length() - ">".length(stripped.length() - ">")) */ withoutEnd = stripped.substring(stripped, 0, stripped.length() - ">".length(stripped.length() - ">"));
		/* withoutEnd.indexOf(withoutEnd, "<") */ index = withoutEnd.indexOf(withoutEnd, "<");
		if (index >= 0){
	/* withoutEnd.substring(withoutEnd, 0, index) */ parseType_local10 = withoutEnd.substring(withoutEnd, 0, index);
	/* index */ parseType_local11 = index + "<";
	int parseType_local23 = !expansions;
			/* parseType_local10.strip(parseType_local10) */ base = parseType_local10.strip(parseType_local10);
			/* withoutEnd.substring(withoutEnd, parseType_local11.length(parseType_local11)) */ substring = withoutEnd.substring(withoutEnd, parseType_local11.length(parseType_local11));
			/* parseValues(substring, /* Main::parseType */) */ parsed = parseValues(substring, /* Main::parseType */);
			if (base.equals(base, "Function")){
				/* parsed.get(parsed, 0) */ arg0 = parsed.get(parsed, 0);
				/* parsed.get(parsed, 1) */ returns = parsed.get(parsed, 1);
				return Functional(Lists.listFrom(Lists, arg0), returns);
			}
			if (base.equals(base, "BiFunction")){
				/* parsed.get(parsed, 0) */ arg0 = parsed.get(parsed, 0);
				/* parsed.get(parsed, 1) */ arg1 = parsed.get(parsed, 1);
				/* parsed.get(parsed, 2) */ returns = parsed.get(parsed, 2);
				return Functional(Lists.listFrom(Lists, arg0, arg1), returns);
			}
			if (parseType_local23.contains(parseType_local23, Tuple_/*  */(base, parsed))){
				expansions = expansions.addLast(expansions, Tuple_/*  */(base, parsed));
			}
			return Generic(base, parsed);
		}
	}
	if (isSymbol(stripped)){
		return StructType(stripped);
	}
	return Content(stripped);
}
List<struct T> parseValues(struct Main this, char* input, struct T (*)(char*) compiler){
	return parseAll(input, /* Main::foldValueChar */, compiler);
}
struct State foldValueChar(struct Main this, struct State state, struct char c){
	/* c */ foldValueChar_local1 = c == ',' && state;
	if (foldValueChar_local1.isLevel(foldValueChar_local1)){
		return state.advance(state);
	}
	/* state.append(state, c) */ appended = state.append(state, c);
	if (c == '-'){
		if (appended.peek() instanceof Some(appended, /* var maybeArrow */)){
			if (maybeArrow == '>'){
	/* appended.popAndAppend(appended) */ foldValueChar_local4 = appended.popAndAppend(appended);
				return foldValueChar_local4.orElse(foldValueChar_local4, appended);
			}
		}
	}
	if (c == '<' || c == '('){
		return appended.enter(appended);
	}
	if (c == '>' || c == ')'){
		return appended.exit(appended);
	}
	return appended;
}
int isSymbol(struct Main this, char* input){
	/* input.strip(input) */ stripped = input.strip(input);
	if (stripped.isEmpty(stripped)){
		return 0;
	}
	/* for (var i = 0; i < stripped.length(); i++) */{
		/* stripped.charAt(stripped, i) */ c = stripped.charAt(stripped, i);
		if (Character.isLetter(c) || (i != 0 && Character.isDigit(c)) || c == '_'){
			/* continue; */
		}
		return 0;
	}
	return 1;
}
char* generatePlaceholder(struct Main this, char* input){
	return "/* " + input + " */";
}
Option<struct R> map(struct None_/*  */ this, struct R (*)(/*  */) mapper){
	return None_/*  */();
}
int isPresent(struct None_/*  */ this){
	return 0;
}
/*  */ orElse(struct None_/*  */ this, /*  */ other){
	return other;
}
int isEmpty(struct None_/*  */ this){
	return 1;
}
/*  */ orElseGet(struct None_/*  */ this, Supplier</*  */> supplier){
	return supplier.get(supplier);
}
Option<struct R> flatMap(struct None_/*  */ this, Option<struct R> (*)(/*  */) mapper){
	return None_/*  */();
}
Option</*  */> or(struct None_/*  */ this, Supplier<Option</*  */>> supplier){
	return supplier.get(supplier);
}
void ifPresent(struct None_/*  */ this, Consumer</*  */> consumer){
}
Option<struct R> map(struct Some_/*  */ this, struct R (*)(/*  */) mapper){
	return Some_/*  */(mapper.apply(mapper, this.value));
}
int isPresent(struct Some_/*  */ this){
	return 1;
}
/*  */ orElse(struct Some_/*  */ this, /*  */ other){
	return this.value;
}
int isEmpty(struct Some_/*  */ this){
	return 0;
}
/*  */ orElseGet(struct Some_/*  */ this, Supplier</*  */> supplier){
	return this.value;
}
Option<struct R> flatMap(struct Some_/*  */ this, Option<struct R> (*)(/*  */) mapper){
	return mapper.apply(mapper, this.value);
}
Option</*  */> or(struct Some_/*  */ this, Supplier<Option</*  */>> supplier){
	return this;
}
void ifPresent(struct Some_/*  */ this, Consumer</*  */> consumer){
	consumer.accept(consumer, this.value);
}
auto map_local2(){
	return map_local1.map(map_local1, map_local1, mapper);
}
Iterator<struct R> map(struct Iterator_T this, struct R (*)(struct T) mapper){
	/* this.head.next(this.head) */ map_local1 = this.head.next(this.head);
	return Iterator_/*  */(map_local2);
}
struct C collect(struct Iterator_T this, Collector<struct T, struct C> collector){
	return this.fold(this, collector.createInitial(collector), /* collector::fold */);
}
auto fold_local2(auto next){
	return folder.apply(folder, folder, finalCurrent, next);
}
struct R fold(struct Iterator_T this, struct R initial, struct R (*)(struct R, struct T) folder){
	/* initial */ current = initial;
	while (1){
	/* this.head.next(this.head) */ fold_local4 = this.head.next(this.head);
		/* current */ finalCurrent = current;
		/* fold_local4.map(fold_local4, fold_local2) */ optional = fold_local4.map(fold_local4, fold_local2);
		if (optional.isPresent(optional)){
			current = optional.orElse(optional, null);
		}
		else {
			return current;
		}
	}
}
auto filter_local1(auto element){
	return /* Iterator_/*  */ */(predicate.test(element) ? new SingleHead<>(element) : new EmptyHead<>(predicate, predicate));
}
Iterator<struct T> filter(struct Iterator_T this, Predicate<struct T> predicate){
	return this.flatMap(this, filter_local1);
}
Iterator<struct R> flatMap(struct Iterator_T this, Iterator<struct R> (*)(struct T) mapper){
	Iterator<struct R> flatMap_local1 = this.map(this, mapper);
	return flatMap_local1.fold(flatMap_local1, Iterator_/*  */(EmptyHead_/*  */()), /* Iterator::concat */);
}
auto concat_local2(){
	return concat_local1.or(concat_local1, concat_local1, /* /* other::next */ */);
}
Iterator<struct T> concat(struct Iterator_T this, Iterator<struct T> other){
	/* this.head.next(this.head) */ concat_local1 = this.head.next(this.head);
	return Iterator_/*  */(concat_local2);
}
Option<struct T> next(struct Iterator_T this){
	return this.head.next(this.head);
}
auto map_local2(){
	return map_local1.map(map_local1, map_local1, mapper);
}
Iterator<struct R> map(struct Iterator_/*  */ this, struct R (*)(/*  */) mapper){
	/* this.head.next(this.head) */ map_local1 = this.head.next(this.head);
	return Iterator_/*  */(map_local2);
}
struct C collect(struct Iterator_/*  */ this, Collector</*  */, struct C> collector){
	return this.fold(this, collector.createInitial(collector), /* collector::fold */);
}
auto fold_local2(auto next){
	return folder.apply(folder, folder, finalCurrent, next);
}
struct R fold(struct Iterator_/*  */ this, struct R initial, struct R (*)(struct R, /*  */) folder){
	/* initial */ current = initial;
	while (1){
	/* this.head.next(this.head) */ fold_local4 = this.head.next(this.head);
		/* current */ finalCurrent = current;
		/* fold_local4.map(fold_local4, fold_local2) */ optional = fold_local4.map(fold_local4, fold_local2);
		if (optional.isPresent(optional)){
			current = optional.orElse(optional, null);
		}
		else {
			return current;
		}
	}
}
auto filter_local1(auto element){
	return /* Iterator_/*  */ */(predicate.test(element) ? new SingleHead<>(element) : new EmptyHead<>(predicate, predicate));
}
Iterator</*  */> filter(struct Iterator_/*  */ this, Predicate</*  */> predicate){
	return this.flatMap(this, filter_local1);
}
Iterator<struct R> flatMap(struct Iterator_/*  */ this, Iterator<struct R> (*)(/*  */) mapper){
	Iterator<struct R> flatMap_local1 = this.map(this, mapper);
	return flatMap_local1.fold(flatMap_local1, Iterator_/*  */(EmptyHead_/*  */()), /* Iterator::concat */);
}
auto concat_local2(){
	return concat_local1.or(concat_local1, concat_local1, /* /* other::next */ */);
}
Iterator</*  */> concat(struct Iterator_/*  */ this, Iterator</*  */> other){
	/* this.head.next(this.head) */ concat_local1 = this.head.next(this.head);
	return Iterator_/*  */(concat_local2);
}
Option</*  */> next(struct Iterator_/*  */ this){
	return this.head.next(this.head);
}
List<struct T> createInitial(struct ListCollector_/*  */ this){
	return listEmpty();
}
List<struct T> fold(struct ListCollector_/*  */ this, List<struct T> current, struct T element){
	return current.addLast(current, element);
}
