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
struct Some_Invocation {
};
struct List_Type {
};
struct Option_Definition {
};
struct State fromInput(struct State this, char* input){
	return new_State(input, listEmpty(), "", 0, 0);
}
Option<Tuple<char, struct State>> pop(struct State this){
	if (this.index >= this.input.length(this.index >= this.input)){
		return new_None_/*  */();
	}
	struct State escaped = this.input.charAt(this.input, this.index);
	return new_Some_/*  */(new_Tuple_i8_State(escaped, new_State(this.input, this.segments, this.buffer, this.depth, this.index + 1)));
}
struct State append(struct State this, struct char c){
	return new_State(this.input, this.segments, this.buffer + c, this.depth, this.index);
}
auto popAndAppendToTuple_local1(auto tuple){
	/* tuple */ poppedChar = tuple.left;
	/* tuple */ poppedState = tuple.right;
	/* poppedState */ appended = poppedState.append(poppedState, poppedChar);
	return new_Tuple_/*  */(poppedChar, appended);
}
Option<Tuple<char, struct State>> popAndAppendToTuple(struct State this){
	Option<Tuple<char, struct State>> popAndAppendToTuple_local3 = this.pop(this);
	return popAndAppendToTuple_local3.map(popAndAppendToTuple_local3, popAndAppendToTuple_local1);
}
int isLevel(struct State this){
	return this.depth == 0;
}
struct State enter(struct State this){
	return new_State(this.input, this.segments, this.buffer, this.depth + 1, this.index);
}
struct State exit(struct State this){
	return new_State(this.input, this.segments, this.buffer, /* this.depth - 1 */, this.index);
}
struct State advance(struct State this){
	return new_State(this.input, this.segments.addLast(this.segments, this.buffer), "", this.depth, this.index);
}
int isShallow(struct State this){
	return this.depth == 1;
}
Option<struct State> popAndAppend(struct State this){
	Option<Tuple<char, struct State>> popAndAppend_local1 = this.popAndAppendToTuple(this);
	return popAndAppend_local1.map(popAndAppend_local1, /* Tuple::right */);
}
Option<char> peek(struct State this){
	if (this.index < this.input.length(this.index < this.input)){
		return new_Some_/*  */(this.input.charAt(this.input, this.index));
	}
	else {
		return new_None_/*  */();
	}
}
struct private Joiner(struct Joiner this){
	this("");
}
Option<char*> createInitial(struct Joiner this){
	return new_None_/*  */();
}
auto fold_local0(auto inner){
	return inner + this.delimiter + element;
}
Option<char*> fold(struct Joiner this, Option<char*> current, char* element){
	/* current */ fold_local2 = current.map(current, fold_local0);
	return new_Some_/*  */(fold_local2.orElse(fold_local2, element));
}
char* generate(struct Definition this){
	struct Definition generate_local1 = this.type.generate(this.type) + " " + this;
	return generate_local1.name(generate_local1);
}
Option<struct Type> findType(struct Definition this){
	return new_Some_/*  */(this.type);
}
Option<char*> findName(struct Definition this){
	return new_Some_/*  */(this.name);
}
char* generate(struct Content this){
	return generatePlaceholder(this.input);
}
Option<struct Type> findType(struct Content this){
	return new_None_/*  */();
}
Option<char*> findName(struct Content this){
	return new_None_/*  */();
}
char* stringify(struct Content this){
	return generatePlaceholder(this.input);
}
char* generate(struct Whitespace implements Defined, Value this){
	return "";
}
Option<struct Type> findType(struct Whitespace implements Defined, Value this){
	return new_None_/*  */();
}
Option<char*> findName(struct Whitespace implements Defined, Value this){
	return new_None_/*  */();
}
char* generate(struct StringValue this){
	return "\"" + this.value + "\"";
}
char* generate(struct Symbol this){
	return this.value;
}
char* generate(struct Invocation this){
	return this.caller.generate(this.caller) + "(" + generateValueList(this.args) + ")";
}
char* generate(struct DataAccess this){
	return this.parent.generate(this.parent) + "." + this.property;
}
char* generate(struct Operation this){
	return this.left.generate(this.left) + " " + this.operator.representation + " " + this.right.generate(this.left.generate(this.left) + " " + this.operator.representation + " " + this.right);
}
auto fromArray_local0(auto index){
	return /* /* array[index] */ */;
}
Iterator<struct T> fromArray(struct Iterators this, /* T[] */ array){
	return new_/* Iterator<>(new RangeHead(array.length)).map */(fromArray_local0);
}
auto fromOption_local0(auto _){
	return /* /* new EmptyHead */ */;
}
auto fromOption_local1(auto var value){
	return /* /* new SingleHead */ */;
}
Iterator<struct T> fromOption(struct Iterators this, Option<struct T> option){
	return new_Iterator_/*  */(/* switch (option) {
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
Option<struct Type> find(struct StructType this, char* name){
	struct StructType find_local4 = this.properties.iter(this.properties);
	/* find_local4 */ find_local5 = find_local4.filter(find_local4, find_local2);
	/* find_local5 */ find_local6 = find_local5.map(find_local5, /* Definition::findType */);
	/* find_local6 */ find_local7 = find_local6.flatMap(find_local6, /* Iterators::fromOption */);
	return find_local7.next(find_local7);
}
struct StructType define(struct StructType this, struct Definition definition){
	return new_StructType(this.name, this.properties.addLast(this.properties, definition));
}
char* generate(struct Generic this){
	return this.base + "<" + generateValueList(this.args) + ">";
}
char* stringify(struct Generic this){
	struct Generic stringify_local1 = this.base + "_" + this.args.iter(this.base + "_" + this.args);
	/* stringify_local1 */ stringify_local2 = stringify_local1.map(stringify_local1, /* Type::stringify */);
	/* stringify_local2 */ stringify_local3 = stringify_local2.collect(stringify_local2, new_Joiner("_"));
	return stringify_local3.orElse(stringify_local3, "");
}
char* stringify(struct Ref this){
	return this.type.stringify(this.type) + "_star";
}
char* generate(struct Ref this){
	return this.type.generate(this.type) + "*";
}
char* generate(struct Functional this){
	/* generateValueList */ generated = generateValueList(this.paramTypes(this));
	return this.returns.generate(this.returns) + " (*)(" + generated + ")";
}
char* stringify(struct Functional this){
	return "_Func_" + generateValueList(this.paramTypes) + "_" + this.returns.stringify() + "_";
}
auto run_local0(auto var error){
	return /* /* new Some */ */;
}
Option<struct IOException> run(struct Main this){
	/* return switch (readString(SOURCE)) */{
		/* case Err */ < /* String, IOException */ > run_local0 <  > (error);
		/* case Ok<String, IOException>(var input) -> */{
			/* compileRoot */ output = compileRoot(input);
			/* yield writeTarget */(TARGET, output);
		}
	}
	/* ; */
}
void main(struct Main this){
	/* run */ main_local0 = run();
	main_local0.ifPresent(main_local0, /* Throwable::printStackTrace */);
}
Option<struct IOException> writeTarget(struct Main this, struct Path target, char* csq){
	/* try */{
		Files.writeString(Files, target, csq);
		return new_None_/*  */();
	}
	/* catch (IOException e) */{
		return new_Some_/*  */(e);
	}
}
Result<char*, struct IOException> readString(struct Main this, struct Path source){
	/* try */{
		return new_Ok_/*  */(Files.readString(Files, source));
	}
	/* catch (IOException e) */{
		return new_Err_/*  */(e);
	}
}
auto compileRoot_local1(auto tuple){
	if (expandables.containsKey(expandables, tuple.left)){
	/* expandable */ compileRoot_local4 = expandable.apply(expandable, tuple.right);
		/* expandables */ expandable = expandables.get(expandables, tuple.left);
		return compileRoot_local4.orElse(compileRoot_local4, "");
	}
	else {
		/* new_/* Generic(tuple.left, tuple.right).generate */ */ generated = new_/* Generic(tuple.left, tuple.right).generate */();
		return "// " + generated + ">\n";
	}
}
char* compileRoot(struct Main this, char* input){
	/* expansions */ compileRoot_local6 = expansions.iter(expansions);
	/* compileRoot_local6 */ compileRoot_local7 = compileRoot_local6.map(compileRoot_local6, compileRoot_local1);
	/* compileRoot_local7 */ compileRoot_local8 = compileRoot_local7.collect(compileRoot_local7, new_Joiner());
	/* compileStatements */ compiled = compileStatements(input, /* Main::compileRootSegment */);
	/* compileRoot_local8 */ joinedExpansions = compileRoot_local8.orElse(compileRoot_local8, "");
	return compiled + join(structs) + joinedExpansions + join(methods);
}
char* join(struct Main this, List<char*> list){
	return join(list, "");
}
char* join(struct Main this, List<char*> list, char* delimiter){
	/* list */ join_local1 = list.iter(list);
	/* join_local1 */ join_local2 = join_local1.collect(join_local1, new_Joiner(delimiter));
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
	/* parsed */ generateAll_local1 = parsed.iter(parsed);
	return generateAll_local1.fold(generateAll_local1, "", merger);
}
List<struct T> parseAll(struct Main this, char* input, struct State (*)(struct State, char) folder, struct T (*)(char*) compiler){
	/* divideAll */ parseAll_local0 = divideAll(input, folder);
	/* parseAll_local0 */ parseAll_local1 = parseAll_local0.iter(parseAll_local0);
	/* parseAll_local1 */ parseAll_local2 = parseAll_local1.map(parseAll_local1, compiler);
	return parseAll_local2.collect(parseAll_local2, new_ListCollector_/*  */());
}
char* mergeStatements(struct Main this, char* buffer, char* element){
	return buffer + element;
}
auto divideAll_local2(){
	return foldDoubleQuotes(withoutNext, next);
}
auto divideAll_local5(){
	return folder.apply(folder, folder, withoutNext, next);
}
List<char*> divideAll(struct Main this, char* input, struct State (*)(struct State, char) folder){
	/* State */ state = State.fromInput(State, input);
	while (1){
		/* state */ maybeNextTuple = state.pop(state);
		if (/* maybeNextTuple instanceof None */ < Tuple < /* Character, State */ >  > ){
			break;
		}
		if (/* maybeNextTuple instanceof Some */ < Tuple < /* Character, State */ >  > (/* var nextTuple */)){
	/* foldSingleQuotes */ divideAll_local3 = foldSingleQuotes(withoutNext, next);
	/* divideAll_local3 */ divideAll_local7 = divideAll_local3.or(divideAll_local3, divideAll_local2);
			/* nextTuple */ next = nextTuple.left;
			/* nextTuple */ withoutNext = nextTuple.right;
			state = divideAll_local7.orElseGet(divideAll_local7, divideAll_local5);
		}
	}
	return state.advance(state).segments;
}
Option<struct State> foldDoubleQuotes(struct Main this, struct State withoutNext, struct char c){
	if (c != '\"'){
		return new_None_/*  */();
	}
	/* withoutNext */ current = withoutNext.append(withoutNext, c);
	while (1){
		/* current */ maybeNext = current.popAndAppendToTuple(current);
		if (!(/* maybeNext instanceof Some */(/* var next */))){
			break;
		}
		current = next.right;
		if (next.left == '"'){
			break;
		}
		if (next.left == '\\'){
	/* current */ foldDoubleQuotes_local3 = current.popAndAppend(current);
			current = foldDoubleQuotes_local3.orElse(foldDoubleQuotes_local3, current);
		}
	}
	return new_Some_/*  */(current);
}
auto foldSingleQuotes_local2(auto maybeSlash){
	return maybeSlash.left == /* /* '\\' ? maybeSlash.right.popAndAppend() : new Some */ */ <  > (maybeSlash.right);
}
Option<struct State> foldSingleQuotes(struct Main this, struct State state, struct char next){
	/* appended */ foldSingleQuotes_local3 = appended.popAndAppendToTuple(appended);
	/* foldSingleQuotes_local3 */ foldSingleQuotes_local4 = foldSingleQuotes_local3.flatMap(foldSingleQuotes_local3, foldSingleQuotes_local2);
	if (next != '\''){
		return new_None_/*  */();
	}
	/* state */ appended = state.append(state, next);
	return foldSingleQuotes_local4.flatMap(foldSingleQuotes_local4, /* State::popAndAppend */);
}
struct State foldStatementChar(struct Main this, struct State state, struct char c){
	/* c */ foldStatementChar_local2 = c == ';' && appended;
	/* c */ foldStatementChar_local5 = c == '}' && appended;
	/* state */ appended = state.append(state, c);
	if (foldStatementChar_local2.isLevel(foldStatementChar_local2)){
		return appended.advance(appended);
	}
	if (foldStatementChar_local5.isShallow(foldStatementChar_local5)){
	/* appended */ foldStatementChar_local4 = appended.advance(appended);
		return foldStatementChar_local4.exit(foldStatementChar_local4);
	}
	/* if (c == ' */{
		/* = '(') {
            return appended.enter() */ c = /* = '(') {
            return appended.enter() */;
	}
	if (c == '}' || c == ')'){
		return appended.exit(appended);
	}
	return appended;
}
auto compileRootSegment_local4(){
	return generatePlaceholder(stripped);
}
char* compileRootSegment(struct Main this, char* input){
	/* stripped */ compileRootSegment_local3 = stripped.startsWith(stripped, "package ") || stripped;
	/* compileClass */ compileRootSegment_local5 = compileClass(stripped);
	/* input */ stripped = input.strip(input);
	if (stripped.isEmpty(stripped)){
		return "";
	}
	if (compileRootSegment_local3.startsWith(compileRootSegment_local3, "import ")){
		return "";
	}
	return compileRootSegment_local5.orElseGet(compileRootSegment_local5, compileRootSegment_local4);
}
Option<char*> compileClass(struct Main this, char* stripped){
	return compileStructure(stripped, "class ");
}
Option<char*> compileStructure(struct Main this, char* input, char* infix){
	/* input */ classIndex = input.indexOf(input, infix);
	if (classIndex >= 0){
	/* classIndex */ compileStructure_local1 = classIndex + infix;
		/* input */ afterClass = input.substring(input, compileStructure_local1.length(compileStructure_local1));
		/* afterClass */ contentStart = afterClass.indexOf(afterClass, "{");
		if (contentStart >= 0){
	/* afterClass */ compileStructure_local5 = afterClass.substring(afterClass, 0, contentStart);
	/* contentStart */ compileStructure_local8 = contentStart + "{";
	/* afterClass */ compileStructure_local10 = afterClass.substring(afterClass, compileStructure_local8.length(compileStructure_local8));
			/* compileStructure_local5 */ beforeContent = compileStructure_local5.strip(compileStructure_local5);
			/* beforeContent */ permitsIndex = beforeContent.indexOf(beforeContent, " permits");
			/* permitsIndex */ withoutPermits = permitsIndex >= /* 0
                        ? beforeContent.substring(0, permitsIndex).strip()
                        : beforeContent */;
			/* withoutPermits */ paramStart = withoutPermits.indexOf(withoutPermits, "(");
			/* compileStructure_local10 */ withEnd = compileStructure_local10.strip(compileStructure_local10);
			if (paramStart >= 0){
	/* withoutPermits */ compileStructure_local12 = withoutPermits.substring(withoutPermits, 0, paramStart);
				/* compileStructure_local12 */ withoutParams = compileStructure_local12.strip(compileStructure_local12);
				return getString(withoutParams, withEnd);
			}
			else {
				return getString(withoutPermits, withEnd);
			}
		}
	}
	return new_None_/*  */();
}
Option<char*> getString(struct Main this, char* beforeContent, char* withEnd){
	int getString_local0 = !withEnd;
	/* withEnd.length() - "}" */ getString_local1 = /* withEnd.length() - "}" */;
	if (getString_local0.endsWith(getString_local0, "}")){
		return new_None_/*  */();
	}
	/* withEnd */ content = withEnd.substring(withEnd, 0, getString_local1.length(getString_local1));
	/* beforeContent */ strippedBeforeContent = beforeContent.strip(beforeContent);
	if (strippedBeforeContent.endsWith(strippedBeforeContent, ">")){
	/* strippedBeforeContent.length() - " */ getString_local4 = /* strippedBeforeContent.length() - " */ > /* " */;
		/* strippedBeforeContent */ withoutEnd = strippedBeforeContent.substring(strippedBeforeContent, 0, getString_local4.length(getString_local4));
		/* withoutEnd */ typeParamStart = withoutEnd.indexOf(withoutEnd, "<");
		if (typeParamStart >= 0){
	/* withoutEnd */ getString_local8 = withoutEnd.substring(withoutEnd, 0, typeParamStart);
	/* typeParamStart */ getString_local9 = typeParamStart + "<";
			/* getString_local8 */ name = getString_local8.strip(getString_local8);
			/* withoutEnd */ substring = withoutEnd.substring(withoutEnd, getString_local9.length(getString_local9));
			/* listFromArray */ typeParameters = listFromArray(substring.split(substring, Pattern.quote(Pattern, ",")));
			return assembleStructure(typeParameters, name, content);
		}
	}
	return assembleStructure(listEmpty(), strippedBeforeContent, content);
}
Option<char*> assembleStructure(struct Main this, List<char*> typeParams, char* name, char* content){
	int assembleStructure_local0 = !typeParams;
	if (assembleStructure_local0.isEmpty(assembleStructure_local0)){
		/* typeParams;
                typeArguments = typeArgs;

                var newName = new Generic(name, typeArgs).stringify();
                return generateStructure */ typeParameters = /* typeParams;
                typeArguments = typeArgs;

                var newName = new Generic(name, typeArgs).stringify();
                return generateStructure */(newName, /* content);
            } */);
		return new_Some_/*  */("");
	}
	return generateStructure(name, content);
}
Option<char*> generateStructure(struct Main this, char* name, char* content){
	structStack = structStack.addLast(structStack, new_StructType(name));
	/* compileStatements */ compiled = compileStatements(content, /* Main::compileClassSegment */);
	structStack = structStack.removeLast(structStack);
	char* generated = "struct " + name + " {" + compiled + "\n};\n";
	structs.addLast(structs, generated);
	return new_Some_/*  */("");
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
	/* compileStructure */ compileClassSegment_local3 = compileStructure(stripped, "record ");
	/* compileClassSegment_local3 */ compileClassSegment_local5 = compileClassSegment_local3.or(compileClassSegment_local3, compileClassSegment_local2);
	/* compileClassSegment_local5 */ compileClassSegment_local7 = compileClassSegment_local5.or(compileClassSegment_local5, compileClassSegment_local4);
	/* compileClassSegment_local7 */ compileClassSegment_local9 = compileClassSegment_local7.or(compileClassSegment_local7, compileClassSegment_local6);
	/* compileClassSegment_local9 */ compileClassSegment_local11 = compileClassSegment_local9.or(compileClassSegment_local9, compileClassSegment_local8);
	/* input */ stripped = input.strip(input);
	if (stripped.isEmpty(stripped)){
		return "";
	}
	return compileClassSegment_local11.orElseGet(compileClassSegment_local11, compileClassSegment_local10);
}
Option<char*> compileDefinitionStatement(struct Main this, char* input){
	/* input */ stripped = input.strip(input);
	if (stripped.endsWith(stripped, ";")){
	/* stripped.length() - ";" */ compileDefinitionStatement_local1 = /* stripped.length() - ";" */;
		/* stripped */ withoutEnd = stripped.substring(stripped, 0, compileDefinitionStatement_local1.length(compileDefinitionStatement_local1));
		return new_Some_/*  */("\n\t" + compileDefinitionOrPlaceholder(withoutEnd) + ";");
	}
	return new_None_/*  */();
}
auto compileMethod_local11(auto parameter){
	return !(/* /* parameter instanceof Whitespace */ */);
}
auto compileMethod_local18(auto last){
	/* newParams */ compileMethod_local20 = newParams.iter(newParams);
	/* compileMethod_local20 */ compileMethod_local21 = compileMethod_local20.map(compileMethod_local20, /* Defined::findType */);
	/* compileMethod_local21 */ compileMethod_local22 = compileMethod_local21.flatMap(compileMethod_local21, /* Iterators::fromOption */);
	/* defined */ compileMethod_local24 = defined.findName(defined);
	/* defined */ compileMethod_local26 = defined.findType(defined);
	/* compileMethod_local22 */ paramTypes = compileMethod_local22.collect(compileMethod_local22, new_ListCollector_/*  */());
	/* compileMethod_local24 */ name = compileMethod_local24.orElse(compileMethod_local24, "?");
	/* compileMethod_local26 */ type = compileMethod_local26.orElse(compileMethod_local26, Primitive.Auto);
	return last.define(last, new_Definition(new_Functional(paramTypes, type), name));
}
auto compileMethod_local17(auto method){
	structStack = structStack.mapLast(structStack, compileMethod_local18);
	return method;
}
Option<char*> compileMethod(struct Main this, char* stripped){
	/* paramStart */ compileMethod_local2 = paramStart + "(";
	/* paramEnd */ compileMethod_local6 = paramEnd + ")";
	/* parseValues */ compileMethod_local10 = parseValues(params, /* Main::parseParameter */);
	/* compileMethod_local10 */ compileMethod_local12 = compileMethod_local10.iter(compileMethod_local10);
	/* compileMethod_local12 */ compileMethod_local13 = compileMethod_local12.filter(compileMethod_local12, compileMethod_local11);
	/* Lists. */ compileMethod_local15 = /* Lists. */ < Defined > listEmpty();
	/* compileMethod_local15 */ compileMethod_local16 = compileMethod_local15.addLast(compileMethod_local15, new_Definition(structStack.last(structStack), "this"));
	/* assembleMethod */ compileMethod_local29 = assembleMethod(defined, outputParams, content);
	/* stripped */ paramStart = stripped.indexOf(stripped, "(");
	if (paramStart < 0){
		return new_None_/*  */();
	}
	/* stripped */ inputDefinition = stripped.substring(stripped, 0, paramStart);
	/* parseDefinitionOrPlaceholder */ defined = parseDefinitionOrPlaceholder(inputDefinition);
	if (/* defined instanceof Definition definition */){
		functionName = definition.name;
		functionLocalCounter = 0;
	}
	/* stripped */ afterParams = stripped.substring(stripped, compileMethod_local2.length(compileMethod_local2));
	/* afterParams */ paramEnd = afterParams.indexOf(afterParams, ")");
	if (paramEnd < 0){
		return new_None_/*  */();
	}
	/* afterParams */ params = afterParams.substring(afterParams, 0, paramEnd);
	/* afterParams */ withoutParams = afterParams.substring(afterParams, compileMethod_local6.length(compileMethod_local6));
	/* withoutParams */ withBraces = withoutParams.strip(withoutParams);
	/* if (!withBraces.startsWith(" */{
		/* ") || !withBraces.endsWith("}")) {
            return new Some<>(""); */
	}
	/* withBraces */ content = withBraces.substring(withBraces, 1, /* withBraces.length() - 1 */);
	/* compileMethod_local13 */ oldParams = compileMethod_local13.collect(compileMethod_local13, new_ListCollector_/*  */());
	/* compileMethod_local16 */ newParams = compileMethod_local16.addAll(compileMethod_local16, oldParams);
	/* generateValueList */ outputParams = generateValueList(newParams);
	return compileMethod_local29.map(compileMethod_local29, compileMethod_local17);
}
char* generateValueList(struct Main this, List<struct T> copy){
	return generateValueList(copy, /* Node::generate */);
}
char* generateValueList(struct Main this, List<struct T> copy, char* (*)(struct T) generate){
	/* copy */ generateValueList_local1 = copy.iter(copy);
	/* generateValueList_local1 */ generateValueList_local2 = generateValueList_local1.map(generateValueList_local1, generate);
	/* generateValueList_local2 */ generateValueList_local3 = generateValueList_local2.collect(generateValueList_local2, new_Joiner(", "));
	return generateValueList_local3.orElse(generateValueList_local3, "");
}
auto assembleMethod_local0(auto input){
	return compileFunctionSegment(input, 1);
}
Option<char*> assembleMethod(struct Main this, struct Defined definition, char* outputParams, char* content){
	/* parseStatementsWithLocals */ parsed = parseStatementsWithLocals(content, assembleMethod_local0);
	/* definition */ generated = definition.generate(definition) + "(" + outputParams + "){" + generateStatements(parsed) + "\n}\n";
	methods.addLast(methods, generated);
	return new_Some_/*  */("");
}
List<char*> parseStatementsWithLocals(struct Main this, char* content, char* (*)(char*) compiler){
	/* Lists. */ parseStatementsWithLocals_local2 = /* Lists. */ < String > listEmpty();
	/* parseStatementsWithLocals_local2 */ parseStatementsWithLocals_local3 = parseStatementsWithLocals_local2.addAll(parseStatementsWithLocals_local2, elements);
	statements = statements.addLast(statements, listEmpty());
	/* parseStatements */ parsed1 = parseStatements(content, compiler);
	/* statements */ elements = statements.removeAndGetLast(statements);
	return parseStatementsWithLocals_local3.addAll(parseStatementsWithLocals_local3, parsed1);
}
auto parseParameter_local0(auto value){
	return value;
}
auto parseParameter_local1(auto value){
	return value;
}
auto parseParameter_local3(){
	return parseParameter_local2.map(parseParameter_local2, parseParameter_local2, parseParameter_local1);
}
auto parseParameter_local6(){
	return new_Content(input);
}
struct Defined parseParameter(struct Main this, char* input){
	/* parseDefinition */ parseParameter_local2 = parseDefinition(input);
	/* parseWhitespace(input). */ parseParameter_local5 = /* parseWhitespace(input). */ < Defined > map(parseParameter_local0);
	/* parseParameter_local5 */ parseParameter_local7 = parseParameter_local5.or(parseParameter_local5, parseParameter_local3);
	return parseParameter_local7.orElseGet(parseParameter_local7, parseParameter_local6);
}
Option<struct Whitespace> parseWhitespace(struct Main this, char* input){
	if (input.isBlank(input)){
		return new_Some_/*  */(new_Whitespace());
	}
	else {
		return new_None_/*  */();
	}
}
auto compileFunctionSegment_local13(auto input1){
	return compileFunctionSegment(input1, depth + 1);
}
char* compileFunctionSegment(struct Main this, char* input, int depth){
	char* compileFunctionSegment_local2 = "\n" + "\t";
	/* input */ stripped = input.strip(input);
	if (stripped.isEmpty(stripped)){
		return "";
	}
	/* compileFunctionSegment_local2 */ indent = compileFunctionSegment_local2.repeat(compileFunctionSegment_local2, depth);
	if (stripped.endsWith(stripped, ";")){
	/* stripped.length() - ";" */ compileFunctionSegment_local3 = /* stripped.length() - ";" */;
	/* stripped */ compileFunctionSegment_local5 = stripped.substring(stripped, 0, compileFunctionSegment_local3.length(compileFunctionSegment_local3));
		/* compileFunctionSegment_local5 */ withoutEnd = compileFunctionSegment_local5.strip(compileFunctionSegment_local5);
		/* compileStatementValue */ maybeStatementValue = compileStatementValue(withoutEnd);
		if (/* maybeStatementValue instanceof Some */(/* var statementValue */)){
			return indent + statementValue + ";";
		}
	}
	if (stripped.endsWith(stripped, "}")){
	/* stripped.length() - "}" */ compileFunctionSegment_local7 = /* stripped.length() - "}" */;
		/* stripped */ withoutEnd = stripped.substring(stripped, 0, compileFunctionSegment_local7.length(compileFunctionSegment_local7));
		/* withoutEnd */ contentStart = withoutEnd.indexOf(withoutEnd, "{");
		if (contentStart >= 0){
	/* contentStart */ compileFunctionSegment_local11 = contentStart + "{";
			/* withoutEnd */ beforeBlock = withoutEnd.substring(withoutEnd, 0, contentStart);
			/* withoutEnd */ content = withoutEnd.substring(withoutEnd, compileFunctionSegment_local11.length(compileFunctionSegment_local11));
			/* parseStatementsWithLocals */ outputContent = parseStatementsWithLocals(content, compileFunctionSegment_local13);
			return indent + compileBeforeBlock(beforeBlock) + "{" + join(outputContent) + indent + "}";
		}
	}
	return indent + generatePlaceholder(stripped);
}
Option<char*> compileStatementValue(struct Main this, char* input){
	/* input */ stripped = input.strip(input);
	if (stripped.equals(stripped, "break")){
		return new_Some_/*  */("break");
	}
	if (stripped.startsWith(stripped, "return ")){
	char* compileStatementValue_local2 = "return ";
		/* stripped */ value = stripped.substring(stripped, compileStatementValue_local2.length(compileStatementValue_local2));
		return new_Some_/*  */("return " + compileValue(value));
	}
	/* stripped */ valueSeparator = stripped.indexOf(stripped, "=");
	if (valueSeparator >= 0){
	/* valueSeparator */ compileStatementValue_local7 = valueSeparator + "=";
	/* newAssignable */ compileStatementValue_local10 = newAssignable.generate(newAssignable) + " = " + value;
		/* stripped */ assignableString = stripped.substring(stripped, 0, valueSeparator);
		/* stripped */ valueString = stripped.substring(stripped, compileStatementValue_local7.length(compileStatementValue_local7));
		/* parseAssignable */ assignable = parseAssignable(assignableString);
		/* parseValue */ value = parseValue(valueString);
		/* resolve */ type = resolve(value);
		/* Assignable newAssignable; */
		if (/* assignable instanceof Definition definition */){
			newAssignable = new_Definition(type, definition.name);
		}
		else {
			newAssignable = assignable;
		}
		return new_Some_/*  */(compileStatementValue_local10.generate(compileStatementValue_local10));
	}
	if (/* compileInvokable(input) instanceof Some */(/* var invokable */)){
		return new_Some_/*  */(invokable.generate(invokable));
	}
	return new_None_/*  */();
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
	return new_Content(symbol.value);
}
struct Type resolveInvocation(struct Main this, struct Invocation invocation){
	/* invocation */ caller = invocation.caller;
	/* resolve */ resolvedCaller = resolve(caller);
	if (/* resolvedCaller instanceof Functional functional */){
		return functional.returns;
	}
	return resolvedCaller;
}
struct Type resolveDataAccess(struct Main this, struct DataAccess dataAccess){
	/* dataAccess */ parent = dataAccess.parent;
	/* resolve */ resolved = resolve(parent);
	if (/* resolved instanceof StructType structType */){
		/* structType */ typeOption = structType.find(structType, dataAccess.property);
		if (/* typeOption instanceof Some */ < Type > (/* var propertyType */)){
			return propertyType;
		}
	}
	return resolved;
}
auto parseAssignable_local0(auto value1){
	return value1;
}
auto parseAssignable_local1(){
	return parseValue(definition);
}
struct Assignable parseAssignable(struct Main this, char* definition){
	/* parseDefinition(definition)
                . */ parseAssignable_local2 = /* parseDefinition(definition)
                . */ < Assignable > map(parseAssignable_local0);
	return parseAssignable_local2.orElseGet(parseAssignable_local2, parseAssignable_local1);
}
auto compileBeforeBlock_local2(){
	return compileConditional(stripped, "while");
}
auto compileBeforeBlock_local4(){
	return generatePlaceholder(stripped);
}
char* compileBeforeBlock(struct Main this, char* input){
	/* compileConditional */ compileBeforeBlock_local3 = compileConditional(stripped, "if");
	/* compileBeforeBlock_local3 */ compileBeforeBlock_local5 = compileBeforeBlock_local3.or(compileBeforeBlock_local3, compileBeforeBlock_local2);
	/* input */ stripped = input.strip(input);
	if (stripped.equals(stripped, "else")){
		return "else ";
	}
	return compileBeforeBlock_local5.orElseGet(compileBeforeBlock_local5, compileBeforeBlock_local4);
}
Option<char*> compileConditional(struct Main this, char* stripped, char* prefix){
	if (stripped.startsWith(stripped, prefix)){
	/* stripped */ compileConditional_local2 = stripped.substring(stripped, prefix.length(prefix));
	/* withoutPrefix */ compileConditional_local5 = withoutPrefix.startsWith(withoutPrefix, "(") && withoutPrefix;
		/* compileConditional_local2 */ withoutPrefix = compileConditional_local2.strip(compileConditional_local2);
		if (compileConditional_local5.endsWith(compileConditional_local5, ")")){
			/* withoutPrefix */ condition = withoutPrefix.substring(withoutPrefix, 1, /* withoutPrefix.length() - 1 */);
			return new_Some_/*  */(prefix + " (" + compileValue(condition) + ")");
		}
	}
	return new_None_/*  */();
}
char* compileValue(struct Main this, char* input){
	/* parseValue */ compileValue_local0 = parseValue(input);
	return compileValue_local0.generate(compileValue_local0);
}
auto parseValue_local17(auto value){
	return parseValue_local16.isEmpty(parseValue_local16, parseValue_local16);
}
struct Value parseValue(struct Main this, char* input){
	/* stripped */ parseValue_local31 = stripped.length(stripped) >= 2 && stripped.startsWith(stripped, "\"") && stripped;
	/* stripped */ parseValue_local35 = stripped.length(stripped) >= 2 && stripped.startsWith(stripped, "'") && stripped;
	/* input */ stripped = input.strip(input);
	if (stripped.isEmpty(stripped)){
		return new_Whitespace();
	}
	if (stripped.equals(stripped, "false")){
		return BooleanValue.False;
	}
	if (stripped.equals(stripped, "true")){
		return BooleanValue.True;
	}
	/* stripped */ arrowIndex = stripped.indexOf(stripped, "->");
	if (arrowIndex >= 0){
	/* stripped */ parseValue_local6 = stripped.substring(stripped, 0, arrowIndex);
	/* arrowIndex */ parseValue_local7 = arrowIndex + "->";
	/* stripped */ parseValue_local9 = stripped.substring(stripped, parseValue_local7.length(parseValue_local7));
	/* beforeArrow */ parseValue_local22 = beforeArrow.startsWith(beforeArrow, "(") && beforeArrow;
		/* parseValue_local6 */ beforeArrow = parseValue_local6.strip(parseValue_local6);
		/* parseValue_local9 */ afterArrow = parseValue_local9.strip(parseValue_local9);
		if (isSymbol(beforeArrow)){
			return assembleLambda(afterArrow, Lists.listFrom(Lists, beforeArrow));
		}
		if (parseValue_local22.endsWith(parseValue_local22, ")")){
	/* beforeArrow */ parseValue_local13 = beforeArrow.substring(beforeArrow, 1, /* beforeArrow.length() - 1 */);
	/* Iterators */ parseValue_local15 = Iterators.fromArray(Iterators, parseValue_local13.split(parseValue_local13, Pattern.quote(Pattern, ",")));
	int parseValue_local16 = !value;
	/* parseValue_local15 */ parseValue_local19 = parseValue_local15.map(parseValue_local15, /* String::strip */);
	/* parseValue_local19 */ parseValue_local20 = parseValue_local19.filter(parseValue_local19, parseValue_local17);
			/* parseValue_local20 */ args = parseValue_local20.collect(parseValue_local20, new_ListCollector_/*  */());
			return assembleLambda(afterArrow, args);
		}
	}
	if (/* compileInvokable(stripped) instanceof Some */(/* var invokable */)){
		return invokable;
	}
	if (isSymbol(stripped)){
		return new_Symbol(stripped);
	}
	if (isNumber(stripped)){
		return new_Symbol(stripped);
	}
	/* stripped */ separator = stripped.lastIndexOf(stripped, ".");
	if (separator >= 0){
	/* separator */ parseValue_local25 = separator + ".";
	/* stripped */ parseValue_local27 = stripped.substring(stripped, parseValue_local25.length(parseValue_local25));
		/* stripped */ value = stripped.substring(stripped, 0, separator);
		/* parseValue_local27 */ property = parseValue_local27.strip(parseValue_local27);
		if (isSymbol(property)){
			return new_DataAccess(parseValue(value), property);
		}
	}
	if (parseValue_local31.endsWith(parseValue_local31, "\"")){
		return new_StringValue(stripped.substring(stripped, 1, /* stripped.length() - 1 */));
	}
	if (parseValue_local35.endsWith(parseValue_local35, "'")){
		return new_CharValue(stripped.substring(stripped, 1, /* stripped.length() - 1 */));
	}
	if (stripped.startsWith(stripped, "!")){
		return new_Not(parseValue(input.substring(input, 1)));
	}
	/* for (var operator : Operator.values()) */{
		/* stripped */ operatorIndex = stripped.indexOf(stripped, operator.representation);
		if (operatorIndex >= 0){
			/* stripped */ left = stripped.substring(stripped, 0, operatorIndex);
			/* stripped */ right = stripped.substring(stripped, operatorIndex + operator.representation.length(operatorIndex + operator.representation));
			return new_Operation(parseValue(left), operator, parseValue(right));
		}
	}
	return new_Content(stripped);
}
Option<struct Invocation> compileInvokable(struct Main this, char* stripped){
	int compileInvokable_local0 = !stripped;
	/* stripped.length() - ")" */ compileInvokable_local1 = /* stripped.length() - ")" */;
	/* stripped */ compileInvokable_local3 = stripped.substring(stripped, 0, compileInvokable_local1.length(compileInvokable_local1));
	/* joined.length() - ")" */ compileInvokable_local6 = /* joined.length() - ")" */;
	if (compileInvokable_local0.endsWith(compileInvokable_local0, ")")){
		return new_None_/*  */();
	}
	/* compileInvokable_local3 */ withoutEnd = compileInvokable_local3.strip(compileInvokable_local3);
	/* divideAll */ divisions = divideAll(withoutEnd, /* Main::foldInvokableStart */);
	if (divisions.size(divisions) < 2){
		return new_None_/*  */();
	}
	/* join */ joined = join(divisions.subList(divisions, 0, /* divisions.size() - 1 */), "");
	/* joined */ caller = joined.substring(joined, 0, compileInvokable_local6.length(compileInvokable_local6));
	/* divisions */ arguments = divisions.last(divisions);
	if (caller.startsWith(caller, "new ")){
	char* compileInvokable_local9 = "new ";
	char* compileInvokable_local11 = "new_" + type;
		/* parseType */ type = parseType(caller.substring(caller, compileInvokable_local9.length(compileInvokable_local9)));
		/* new_Symbol */ parsedCaller = new_Symbol(compileInvokable_local11.stringify(compileInvokable_local11));
		return assembleInvokable(parsedCaller, arguments, Lists.listEmpty(Lists));
	}
	/* parseValue */ parsedCaller = parseValue(caller);
	if (/* resolve(parsedCaller) instanceof Functional functional */){
		return assembleInvokable(parsedCaller, arguments, functional.paramTypes);
	}
	else {
		return assembleInvokable(parsedCaller, arguments, Lists.listEmpty(Lists));
	}
}
auto assembleInvokable_local1(auto Tuple<Integer, auto String> input){
	/* expectedArgumentsType */ maybeFound = expectedArgumentsType.find(expectedArgumentsType, input.left);
	/* Value parsed; */
	if (/* maybeFound instanceof Some */(/* var found */)){
		typeStack = typeStack.addLast(typeStack, found);
		parsed = parseValue(input.right);
		typeStack = typeStack.removeLast(typeStack);
	}
	else {
		parsed = parseValue(input.right);
	}
	return parsed;
}
auto assembleInvokable_local8(auto value){
	return !(/* /* value instanceof Whitespace */ */);
}
Some<struct Invocation> assembleInvokable(struct Main this, struct Value caller, char* arguments, List<struct Type> expectedArgumentsType){
	/* divideAll */ assembleInvokable_local0 = divideAll(arguments, /* Main::foldValueChar */);
	/* assembleInvokable_local0 */ assembleInvokable_local5 = assembleInvokable_local0.iterWithIndices(assembleInvokable_local0);
	/* assembleInvokable_local5 */ assembleInvokable_local6 = assembleInvokable_local5.map(assembleInvokable_local5, assembleInvokable_local1);
	/* assembleInvokable_local6 */ assembleInvokable_local7 = assembleInvokable_local6.collect(assembleInvokable_local6, new_ListCollector_/*  */());
	/* assembleInvokable_local7 */ assembleInvokable_local9 = assembleInvokable_local7.iter(assembleInvokable_local7);
	/* assembleInvokable_local9 */ assembleInvokable_local10 = assembleInvokable_local9.filter(assembleInvokable_local9, assembleInvokable_local8);
	/* Lists. */ assembleInvokable_local13 = /* Lists. */ < Value > listEmpty();
	/* assembleInvokable_local13 */ assembleInvokable_local14 = assembleInvokable_local13.addLast(assembleInvokable_local13, symbol);
	/* assembleInvokable_local10 */ parsedArgs = assembleInvokable_local10.collect(assembleInvokable_local10, new_ListCollector_/*  */());
	if (!(/* caller instanceof DataAccess */(/* var parent */, /* var property */))){
		return new_Some_/*  */(new_Invocation(caller, parsedArgs));
	}
	/* generateName */ name = generateName();
	/* Value symbol; */
	if (/* parent instanceof Symbol */ || /* parent instanceof DataAccess */){
		symbol = parent;
	}
	else {
	/* statements */ assembleInvokable_local12 = statements.last(statements);
		/* resolve */ type = resolve(parent);
		char* statement = "\n\t" + type.generate() + " " + name + " = " + parent.generate() + ";";
		assembleInvokable_local12.addLast(assembleInvokable_local12, statement);
		symbol = new_Symbol(name);
	}
	/* assembleInvokable_local14 */ newArgs = assembleInvokable_local14.addAll(assembleInvokable_local14, parsedArgs);
	return new_Some_/*  */(new_Invocation(new_DataAccess(symbol, property), newArgs));
}
auto assembleLambda_local1(auto name){
	return "auto " + name;
}
struct Symbol assembleLambda(struct Main this, char* afterArrow, List<char*> names){
	/* names */ assembleLambda_local2 = names.iter(names);
	/* assembleLambda_local2 */ assembleLambda_local3 = assembleLambda_local2.map(assembleLambda_local2, assembleLambda_local1);
	/* assembleLambda_local3 */ assembleLambda_local4 = assembleLambda_local3.collect(assembleLambda_local3, new_Joiner(", "));
	/* assembleLambda_local4 */ params = assembleLambda_local4.orElse(assembleLambda_local4, "");
	/* if (afterArrow.startsWith(" */{
		/* afterArrow.substring(1, afterArrow.length() - 1);
            var name = generateName();
            assembleMethod(new Definition(Primitive.Auto, name), params, content);
            return new Symbol */ content = /* afterArrow.substring(1, afterArrow.length() - 1);
            var name = generateName();
            assembleMethod(new Definition(Primitive.Auto, name), params, content);
            return new Symbol */(name);
	}
	/* compileValue */ newValue = compileValue(afterArrow);
	/* generateName */ name = generateName();
	assembleMethod(new_Definition(Primitive.Auto, name), params, "\n\treturn " + newValue + ";");
	return new_Symbol(name);
}
char* generateName(struct Main this){
	/* functionName */ name = functionName + "_local" + functionLocalCounter;
	/* functionLocalCounter++; */
	return name;
}
struct State foldInvokableStart(struct Main this, struct State state, char c){
	/* state */ appended = state.append(state, c);
	if (c == '('){
		/* appended.isLevel() ? appended.advance() : appended */ maybeAdvanced = /* appended.isLevel() ? appended.advance() : appended */;
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
		/* input */ c = input.charAt(input, i);
		if (Character.isDigit(Character, c)){
			/* continue; */
		}
		return 0;
	}
	return 1;
}
char* compileDefinitionOrPlaceholder(struct Main this, char* input){
	/* parseDefinitionOrPlaceholder */ compileDefinitionOrPlaceholder_local0 = parseDefinitionOrPlaceholder(input);
	return compileDefinitionOrPlaceholder_local0.generate(compileDefinitionOrPlaceholder_local0);
}
auto parseDefinitionOrPlaceholder_local0(auto value){
	return value;
}
auto parseDefinitionOrPlaceholder_local1(){
	return new_Content(input);
}
struct Defined parseDefinitionOrPlaceholder(struct Main this, char* input){
	/* parseDefinition(input). */ parseDefinitionOrPlaceholder_local2 = /* parseDefinition(input). */ < Defined > map(parseDefinitionOrPlaceholder_local0);
	return parseDefinitionOrPlaceholder_local2.orElseGet(parseDefinitionOrPlaceholder_local2, parseDefinitionOrPlaceholder_local1);
}
Option<struct Definition> parseDefinition(struct Main this, char* input){
	/* nameSeparator */ parseDefinition_local3 = nameSeparator + " ";
	/* input */ stripped = input.strip(input);
	/* stripped */ nameSeparator = stripped.lastIndexOf(stripped, " ");
	if (nameSeparator < 0){
		return new_None_/*  */();
	}
	/* stripped */ beforeName = stripped.substring(stripped, 0, nameSeparator);
	/* stripped */ name = stripped.substring(stripped, parseDefinition_local3.length(parseDefinition_local3));
	if (!isSymbol(name)){
		return new_None_/*  */();
	}
	/* divideAll */ divisions = divideAll(beforeName, /* Main::foldByTypeSeparator */);
	if (divisions.size(divisions) == 1){
		return new_Some_/*  */(new_Definition(parseType(beforeName), name));
	}
	/* divisions */ type = divisions.last(divisions);
	return new_Some_/*  */(new_Definition(parseType(type), name));
}
struct State foldByTypeSeparator(struct Main this, struct State state, struct char c){
	/* c */ foldByTypeSeparator_local1 = c == ' ' && state;
	if (foldByTypeSeparator_local1.isLevel(foldByTypeSeparator_local1)){
		return state.advance(state);
	}
	/* state */ appended = state.append(state, c);
	if (c == '<'){
		return appended.enter(appended);
	}
	if (c == '>'){
		return appended.exit(appended);
	}
	return appended;
}
struct Type parseType(struct Main this, char* input){
	/* typeParameters */ parseType_local2 = typeParameters.indexOf(typeParameters, stripped);
	/* input */ stripped = input.strip(input);
	/* parseType_local2 */ maybeTypeArgument = parseType_local2.flatMap(parseType_local2, /* typeArguments::find */);
	if (/* maybeTypeArgument instanceof Some */(/* var found */)){
		return found;
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
		return new_Ref(Primitive.I8);
	}
	if (stripped.endsWith(stripped, ">")){
	/* stripped.length() - " */ parseType_local4 = /* stripped.length() - " */ > /* " */;
		/* stripped */ withoutEnd = stripped.substring(stripped, 0, parseType_local4.length(parseType_local4));
		/* withoutEnd */ index = withoutEnd.indexOf(withoutEnd, "<");
		if (index >= 0){
	/* withoutEnd */ parseType_local8 = withoutEnd.substring(withoutEnd, 0, index);
	/* index */ parseType_local9 = index + "<";
	int parseType_local26 = !expansions;
			/* parseType_local8 */ base = parseType_local8.strip(parseType_local8);
			/* withoutEnd */ substring = withoutEnd.substring(withoutEnd, parseType_local9.length(parseType_local9));
			/* parseValues */ parsed = parseValues(substring, /* Main::parseType */);
			if (base.equals(base, "Function")){
	/* parsed */ parseType_local12 = parsed.find(parsed, 0);
	/* parsed */ parseType_local14 = parsed.find(parsed, 1);
				/* parseType_local12 */ arg0 = parseType_local12.orElse(parseType_local12, null);
				/* parseType_local14 */ returns = parseType_local14.orElse(parseType_local14, null);
				return new_Functional(Lists.listFrom(Lists, arg0), returns);
			}
			if (base.equals(base, "BiFunction")){
	/* parsed */ parseType_local18 = parsed.find(parsed, 0);
	/* parsed */ parseType_local20 = parsed.find(parsed, 1);
	/* parsed */ parseType_local22 = parsed.find(parsed, 2);
				/* parseType_local18 */ arg0 = parseType_local18.orElse(parseType_local18, null);
				/* parseType_local20 */ arg1 = parseType_local20.orElse(parseType_local20, null);
				/* parseType_local22 */ returns = parseType_local22.orElse(parseType_local22, null);
				return new_Functional(Lists.listFrom(Lists, arg0, arg1), returns);
			}
			if (parseType_local26.contains(parseType_local26, new_Tuple_/*  */(base, parsed))){
				expansions = expansions.addLast(expansions, new_Tuple_/*  */(base, parsed));
			}
			return new_Generic(base, parsed);
		}
	}
	if (isSymbol(stripped)){
		return new_StructType(stripped);
	}
	return new_Content(stripped);
}
List<struct T> parseValues(struct Main this, char* input, struct T (*)(char*) compiler){
	return parseAll(input, /* Main::foldValueChar */, compiler);
}
struct State foldValueChar(struct Main this, struct State state, struct char c){
	/* c */ foldValueChar_local1 = c == ',' && state;
	if (foldValueChar_local1.isLevel(foldValueChar_local1)){
		return state.advance(state);
	}
	/* state */ appended = state.append(state, c);
	if (c == '-'){
		if (/* appended.peek() instanceof Some */(/* var maybeArrow */)){
			if (maybeArrow == '>'){
	/* appended */ foldValueChar_local4 = appended.popAndAppend(appended);
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
	/* input */ stripped = input.strip(input);
	if (stripped.isEmpty(stripped)){
		return 0;
	}
	/* for (var i = 0; i < stripped.length(); i++) */{
		/* stripped */ c = stripped.charAt(stripped, i);
		if (Character.isLetter(Character, c) || /* (i */ != 0 && Character.isDigit(Character, /* c) */) || c == '_'){
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
	return new_None_/*  */();
}
/*  */ orElse(struct None_/*  */ this, /*  */ other){
	return other;
}
/*  */ orElseGet(struct None_/*  */ this, Supplier</*  */> supplier){
	return supplier.get(supplier);
}
Option<struct R> flatMap(struct None_/*  */ this, Option<struct R> (*)(/*  */) mapper){
	return new_None_/*  */();
}
Option</*  */> or(struct None_/*  */ this, Supplier<Option</*  */>> supplier){
	return supplier.get(supplier);
}
void ifPresent(struct None_/*  */ this, Consumer</*  */> consumer){
}
Option<struct R> map(struct Some_/*  */ this, struct R (*)(/*  */) mapper){
	return new_Some_/*  */(mapper.apply(mapper, this.value));
}
/*  */ orElse(struct Some_/*  */ this, /*  */ other){
	return this.value;
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
	struct Iterator_T map_local1 = this.head.next(this.head);
	return new_Iterator_/*  */(map_local2);
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
	struct Iterator_T fold_local4 = this.head.next(this.head);
		/* current */ finalCurrent = current;
		/* fold_local4 */ optional = fold_local4.map(fold_local4, fold_local2);
		/* switch (optional) */{
			/* case None<R> _ -> */{
				return current;
			}
			/* nextState */ current = nextState;
		}
	}
}
auto filter_local0(auto element){
	return /* new_Iterator_/*  */ */(/* /* predicate.test(element) ? new SingleHead */ */ <  > /* /* (element) : new EmptyHead */ */ <  > ());
}
Iterator<struct T> filter(struct Iterator_T this, Predicate<struct T> predicate){
	return this.flatMap(this, filter_local0);
}
Iterator<struct R> flatMap(struct Iterator_T this, Iterator<struct R> (*)(struct T) mapper){
	Iterator<struct R> flatMap_local1 = this.map(this, mapper);
	return flatMap_local1.fold(flatMap_local1, new_Iterator_/*  */(new_EmptyHead_/*  */()), /* Iterator::concat */);
}
auto concat_local2(){
	return concat_local1.or(concat_local1, concat_local1, /* /* other::next */ */);
}
Iterator<struct T> concat(struct Iterator_T this, Iterator<struct T> other){
	struct Iterator_T concat_local1 = this.head.next(this.head);
	return new_Iterator_/*  */(concat_local2);
}
Option<struct T> next(struct Iterator_T this){
	return this.head.next(this.head);
}
auto map_local2(){
	return map_local1.map(map_local1, map_local1, mapper);
}
Iterator<struct R> map(struct Iterator_/*  */ this, struct R (*)(/*  */) mapper){
	struct Iterator_/*  */ map_local1 = this.head.next(this.head);
	return new_Iterator_/*  */(map_local2);
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
	struct Iterator_/*  */ fold_local4 = this.head.next(this.head);
		/* current */ finalCurrent = current;
		/* fold_local4 */ optional = fold_local4.map(fold_local4, fold_local2);
		/* switch (optional) */{
			/* case None<R> _ -> */{
				return current;
			}
			/* nextState */ current = nextState;
		}
	}
}
auto filter_local0(auto element){
	return /* new_Iterator_/*  */ */(/* /* predicate.test(element) ? new SingleHead */ */ <  > /* /* (element) : new EmptyHead */ */ <  > ());
}
Iterator</*  */> filter(struct Iterator_/*  */ this, Predicate</*  */> predicate){
	return this.flatMap(this, filter_local0);
}
Iterator<struct R> flatMap(struct Iterator_/*  */ this, Iterator<struct R> (*)(/*  */) mapper){
	Iterator<struct R> flatMap_local1 = this.map(this, mapper);
	return flatMap_local1.fold(flatMap_local1, new_Iterator_/*  */(new_EmptyHead_/*  */()), /* Iterator::concat */);
}
auto concat_local2(){
	return concat_local1.or(concat_local1, concat_local1, /* /* other::next */ */);
}
Iterator</*  */> concat(struct Iterator_/*  */ this, Iterator</*  */> other){
	struct Iterator_/*  */ concat_local1 = this.head.next(this.head);
	return new_Iterator_/*  */(concat_local2);
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
Option<struct R> map(struct Some_Invocation this, struct R (*)(struct Invocation) mapper){
	return new_Some_/*  */(mapper.apply(mapper, this.value));
}
struct Invocation orElse(struct Some_Invocation this, struct Invocation other){
	return this.value;
}
struct Invocation orElseGet(struct Some_Invocation this, Supplier<struct Invocation> supplier){
	return this.value;
}
Option<struct R> flatMap(struct Some_Invocation this, Option<struct R> (*)(struct Invocation) mapper){
	return mapper.apply(mapper, this.value);
}
Option<struct Invocation> or(struct Some_Invocation this, Supplier<Option<struct Invocation>> supplier){
	return this;
}
void ifPresent(struct Some_Invocation this, Consumer<struct Invocation> consumer){
	consumer.accept(consumer, this.value);
}
