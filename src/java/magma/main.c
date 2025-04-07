#include <temp.h>
#include <temp.h>
#include <temp.h>
#include <temp.h>
#include <temp.h>
#include <temp.h>
#include <temp.h>
#include <temp.h>
#include <temp.h>
#include <temp.h>
#include <temp.h>
#include <temp.h>
#include <temp.h>
/* private static */ struct State {
};
/* private static */ struct Streams {
};
/* private */ struct Joiner {
};
/* private static */ struct Tuples {
};
/* private */ struct CompileError {
};
/* private static Option<String> compileTypedBlock(String input, String keyword, List_<List_<String>> typeParams) {
        int */ struct Index = input.indexOf {
	/* ");
       *//*  */ if(/* contentStart < */ 0);
};
/* public */ struct Main {
	/* private static final Map<String, Function<List_<String>, Option<String>>> generators = */new HashMap<>();
	/* private static final List_<Tuple<String, List_<String>>> expanded *//* = */ Lists.empty();
	/* private static List_<String> imports *//* = */ Lists.empty();
	/* private static List_<String> structs *//* = */ Lists.empty();
	/* private static List_<String> functions *//* = */ Lists.empty();
	/* private static List_<Tuple<String, List_<String>>> toExpand *//* = */ Lists.empty();
	/* private static List_<String> globals *//* = */ Lists.empty();
};
/* public */ struct List__Character {
	List__Character add(Character element);
	List__Character addAll(List__Character elements);
	Stream__Character stream();
	Character popFirst();
	int hasElements();
	Option_Character apply(int index);
	int size();
	Character last();
	Stream__Tuple_Integer_Character streamWithIndices();
	Character first();
};
/* public */ struct List__String {
	List__String add(String element);
	List__String addAll(List__String elements);
	Stream__String stream();
	String popFirst();
	int hasElements();
	Option_String apply(int index);
	int size();
	String last();
	Stream__Tuple_Integer_String streamWithIndices();
	String first();
};
/* public */ struct Stream__Character {
	/* <R> */Stream__R map(R (*)(Character) mapper);
	/* <R> */R foldWithInitial(R initial, R (*)(R, Character) folder);
	/* <C> */C collect(Collector_Character_C collector);
	/* <R> */Option_R foldToOption(R initial, Option_R (*)(R, Character) folder);
	int anyMatch(Predicate_Character predicate);
	/* <R> */Stream__R flatMap(Stream__R (*)(Character) mapper);
	Stream__Character concat(Stream__Character other);
	Option_Character next();
	int allMatch(Predicate_Character predicate);
	Stream__Character filter(Predicate_Character predicate);
};
/* public */ struct Stream__T {
	/* <R> */Stream__R map(R (*)(T) mapper);
	/* <R> */R foldWithInitial(R initial, R (*)(R, T) folder);
	/* <C> */C collect(Collector_T_C collector);
	/* <R> */Option_R foldToOption(R initial, Option_R (*)(R, T) folder);
	int anyMatch(Predicate_T predicate);
	/* <R> */Stream__R flatMap(Stream__R (*)(T) mapper);
	Stream__T concat(Stream__T other);
	Option_T next();
	int allMatch(Predicate_T predicate);
	Stream__T filter(Predicate_T predicate);
};
/* sealed public */ struct Option_String {
	void ifPresent(void (*)(String) ifPresent);
	/* <R> */Option_R flatMap(Option_R (*)(String) mapper);
	/* <R> */Option_R map(R (*)(String) mapper);
	String orElse(String other);
	int isPresent();
	Tuple_int_String toTuple(String other);
	String orElseGet(String (*)() other);
	Option_String or(Option_String (*)() other);
	int isEmpty();
	/* <R> */Option_Tuple_String_R and(Option_R (*)() other);
	/* <R> */R match(R (*)(String) whenPresent, R (*)() whenEmpty);
};
/* public */ struct Tuple_A_B {
};
/* sealed public */ struct Option_IOException {
	void ifPresent(void (*)(IOException) ifPresent);
	/* <R> */Option_R flatMap(Option_R (*)(IOException) mapper);
	/* <R> */Option_R map(R (*)(IOException) mapper);
	IOException orElse(IOException other);
	int isPresent();
	Tuple_int_IOException toTuple(IOException other);
	IOException orElseGet(IOException (*)() other);
	Option_IOException or(Option_IOException (*)() other);
	int isEmpty();
	/* <R> */Option_Tuple_IOException_R and(Option_R (*)() other);
	/* <R> */R match(R (*)(IOException) whenPresent, R (*)() whenEmpty);
};
/* sealed public */ struct Option_List__String {
	void ifPresent(void (*)(List__String) ifPresent);
	/* <R> */Option_R flatMap(Option_R (*)(List__String) mapper);
	/* <R> */Option_R map(R (*)(List__String) mapper);
	List__String orElse(List__String other);
	int isPresent();
	Tuple_int_List__String toTuple(List__String other);
	List__String orElseGet(List__String (*)() other);
	Option_List__String or(Option_List__String (*)() other);
	int isEmpty();
	/* <R> */Option_Tuple_List__String_R and(Option_R (*)() other);
	/* <R> */R match(R (*)(List__String) whenPresent, R (*)() whenEmpty);
};
/* sealed public */ struct Option_State {
	void ifPresent(void (*)(State) ifPresent);
	/* <R> */Option_R flatMap(Option_R (*)(State) mapper);
	/* <R> */Option_R map(R (*)(State) mapper);
	State orElse(State other);
	int isPresent();
	Tuple_int_State toTuple(State other);
	State orElseGet(State (*)() other);
	Option_State or(Option_State (*)() other);
	int isEmpty();
	/* <R> */Option_Tuple_State_R and(Option_R (*)() other);
	/* <R> */R match(R (*)(State) whenPresent, R (*)() whenEmpty);
};
/* sealed public */ struct Result_String_CompileError {
	/* <R> */R match(R (*)(String) whenOk, R (*)(CompileError) whenErr);
	Option_String findValue();
};
/* public */ struct List__List__String {
	List__List__String add(List__String element);
	List__List__String addAll(List__List__String elements);
	Stream__List__String stream();
	List__String popFirst();
	int hasElements();
	Option_List__String apply(int index);
	int size();
	List__String last();
	Stream__Tuple_Integer_List__String streamWithIndices();
	List__String first();
};
/* public */ struct Tuple_String_List__String {
};
/* public */ struct List__Tuple_String_List__String {
	List__Tuple_String_List__String add(Tuple_String_List__String element);
	List__Tuple_String_List__String addAll(List__Tuple_String_List__String elements);
	Stream__Tuple_String_List__String stream();
	Tuple_String_List__String popFirst();
	int hasElements();
	Option_Tuple_String_List__String apply(int index);
	int size();
	Tuple_String_List__String last();
	Stream__Tuple_Integer_Tuple_String_List__String streamWithIndices();
	Tuple_String_List__String first();
};

	/* private static */int lambdaCounter = 0;;
private State(List__Character queue){
	this(queue, Lists.empty(), /* new StringBuilder */(), 0);
}
private State(List__Character queue, List__String segments, StringBuilder buffer, int depth){
	/* this.queue  */ = queue;
	/* this.segments  */ = segments;
	/* this.buffer  */ = buffer;
	/* this.depth  */ = depth;
}
/* private */State popAndAppend(){
	return append(pop());
}
/* private */int hasNext(){
	return queue.hasElements();
}
/* private */State enter(){
	return State(queue, segments, buffer, depth + 1);
}
/* private */State exit(){
	return State(queue, segments, buffer, depth - 1);
}
/* private */State append(char c){
	return State(queue, segments, buffer.append(c), depth);
}
/* private */State advance(){
	return State(queue, segments.add(buffer.toString()), /* new StringBuilder */(), depth);
}
/* private */int isLevel(){
	return depth == 0;
}
/* private */char pop(){
	return queue.popFirst();
}
/* private */int isShallow(){
	return depth == 1;
}
/* public */List__String segments(){
	return segments;
}
/* public */char peek(){
	return queue.first();
}
/* public static */Stream__Character from(String value){
	return HeadedStream(RangeHead(value.length())).map(value::charAt);
}
/* public static <T> */Stream__T empty(){
	return HeadedStream(EmptyHead());
}
/* @Override
        public */Option_String createInitial(){
	return None();
}
auto __lambda0__(auto inner){
	return inner + delimiter + element;
}
/* @Override
        public */Option_String fold(Option_String current, String element){
	return Some(current.map(__lambda0__).orElse(element));
}
/* public static <A, B> */int equalsTo(Tuple_A_B left, Tuple_A_B right, int (*)(A, A) leftEquator, int (*)(B, B) rightEquator){
	return leftEquator.apply(left.left, right.left) &&
                    rightEquator.apply(left.right, right.right);
}
/* public */String display(){
	return message + ": " + context;
}
auto __lambda1__(auto throwable){
	return Throwable.printStackTrace(throwable);
}
auto __lambda2__(auto input){
	return runWithInput(source, input);
}
auto __lambda3__(auto some){
	return Some.new(some);
}
/* public static */void main(String* args){
	Path source = Paths.get(".", "src", "java", "magma", "Main.java");
	magma.Files.readString(source).match(__lambda2__, __lambda3__).ifPresent(__lambda1__);
}
/* private static */Option_IOException runWithInput(Path source, String input){
	String output = compile(input) + "int main(){\n\t__main__();\n\treturn 0;\n}\n";
	Path target = source.resolveSibling("main.c");
	return magma.Files.writeString(target, output);
}
auto __lambda4__(auto main){
	return Main.divideStatementChar(main);
}
auto __lambda5__(auto main){
	return Main.mergeStatements(main);
}
auto __lambda6__(auto compiled){
	return mergeAll(compiled, __lambda5__);
}
auto __lambda7__(auto main){
	return Main.generate(main);
}
auto __lambda8__(auto main){
	return Main.compileRootSegment(main);
}
/* private static */String compile(String input){
	List__String segments = divideAll(input, __lambda4__);
	return parseAll(segments, __lambda8__).map(__lambda7__).map(__lambda6__).orElse("");
}
/* private static */List__String generate(List__String compiled){
	while (1) {
	}
	return compiled.addAll(imports).addAll(structs).addAll(globals).addAll(functions);
}
auto __lambda9__(auto main){
	return Main.divideStatementChar(main);
}
auto __lambda10__(auto main){
	return Main.mergeStatements(main);
}
/* private static */Option_String compileStatements(String input, Option_String (*)(String) compiler){
	return compileAll(divideAll(input, __lambda9__), compiler, __lambda10__);
}
auto __lambda11__(auto compiled){
	return mergeAll(compiled, merger);
}
/* private static */Option_String compileAll(List__String segments, Option_String (*)(String) compiler, StringBuilder (*)(StringBuilder, String) merger){
	return parseAll(segments, compiler).map(__lambda11__);
}
/* private static */String mergeAll(List__String compiled, StringBuilder (*)(StringBuilder, String) merger){
	return compiled.stream().foldWithInitial(StringBuilder(), merger).toString();
}
auto __lambda12__(auto compiled){
	return compiled.add(compiled);
}
auto __lambda13__(auto compiled, auto segment){
	return compiler.apply(segment).map(__lambda12__);
}
/* private static */Option_List__String parseAll(List__String segments, Option_String (*)(String) compiler){
	return segments.stream().foldToOption(Lists.empty(), __lambda13__);
}
/* private static */StringBuilder mergeStatements(StringBuilder output, String str){
	return output.append(str);
}
/* private static */List__String divideAll(String input, State (*)(State, Character) divider){
	List__Character queue = Streams.from(input).collect(ListCollector());
	State current = /* new State */(queue);
	while (1) {
	}
	return current.advance().segments();
}
/* private static */Option_State divideDoubleQuotes(State state, char c){
	if (1) {
	}
	State current = state.append(c);
	while (1) {
	}
	return Some(current);
}
/* private static */Option_State divideSingleQuotes(State current, char c){
	if (1) {
	}
	State appended = current.append(c);
	char maybeEscape = current.pop();
	State withNext = appended.append(maybeEscape);
	State appended1 = maybeEscape == '\\' ? withNext.popAndAppend() : withNext;
	return Some(appended1.popAndAppend());
}
/* private static */State divideStatementChar(State state, char c){
	State appended = state.append(c);
	if (1) {
	}
	if (1) {
	}
	if (1) {
	}
	if (1) {
	}
	return appended;
}
/* private static */Option_String compileRootSegment(String input){
	return compileRootStatement(input).findValue();
}
auto __lambda14__(auto /* (function<string, result<string, compileerror>>) ok */){
	return /* (Function<String, Result<String, CompileError>>) Ok */.new(/* (function<string, result<string, compileerror>>) ok */);
}
auto __lambda15__(auto ){
	return Err(CompileError("Invalid " + "root segment", input));
}
auto __lambda16__(auto ){
	return compileClass(input);
}
auto __lambda17__(auto ){
	return compileImport(input);
}
auto __lambda18__(auto ){
	return compilePackage(input.startsWith("package "));
}
/* private static */Result_String_CompileError compileRootStatement(String input){
	return compileWhitespace(input).or(__lambda18__).or(__lambda17__).or(__lambda16__).match(__lambda14__, __lambda15__);
}
/* private static */Option_String compileClass(String input){
	List__List__String frame = Lists.<List_<String>>empty().add(Lists.empty());
	return compileTypedBlock(input, "class ", frame);
}
/* private static */Option_String compileImport(String input){
	if (1) {
	}
	return None();
}
/* private static */Option_String compilePackage(int input){
	if (1) {
	}
	return None();
}
/* private static */Option_String compileWhitespace(String input){
	if (1) {
	}
	return None();
}
auto __lambda19__(auto string){
	return String.strip(string);
}
/* private static */Option_String compileGenericTypedBlock(String withoutPermits, String modifiers, String body, List__List__String typeParams){
	if (1) {
	}
	String withoutEnd = withoutPermits.substring(0, withoutPermits.length() - ">".length());
	int genStart = withoutEnd.indexOf("<");
	if (1) {
	}
	String name = withoutEnd.substring(0, genStart);
	String substring = withoutEnd.substring(genStart + "<".length());
	List__String finalClassTypeParams = Lists.fromNative(Arrays.asList(substring.split(Pattern.quote(",")))).stream().map(__lambda19__).collect(ListCollector());
	/* generators.put(name, typeArguments -> {
            */String joined = /*  generateGenericName(name, typeArguments);
            return compileToStruct(modifiers, joined, body, typeParams, finalClassTypeParams, typeArguments);
        }) */;
	return Some("");
}
auto __lambda20__(auto outputContent){
	return generateStruct(modifiers, name, outputContent);
}
auto __lambda21__(auto input1){
	return compileClassSegment(input1, merged, typeArguments).findValue();
}
/* private static */Option_String compileToStruct(String modifiers, String name, String body, List__List__String outerTypeParams, List__String innerTypeParams, List__String typeArguments){
	List__List__String merged = outerTypeParams.add(innerTypeParams);
	if (1) {
	}
	String inputContent = body.substring(0, body.length() - "}".length());
	return compileStatements(inputContent, __lambda21__).map(__lambda20__);
}
/* private static */String generateStruct(String modifiers, String name, String content){
	String modifiersString = modifiers.isEmpty() ? "" : generatePlaceholder(modifiers) + " ";
	String generated = modifiersString + "struct " + name + " {" +
                content +
                "\n};\n";
	/* structs  */ = structs.add(generated);
	return "";
}
auto __lambda22__(auto ok){
	return Ok.new(ok);
}
auto __lambda23__(auto ){
	return Err(CompileError("Invalid class segment", input));
}
auto __lambda24__(auto ){
	return compileDefinitionStatement(input, typeParams, typeArguments);
}
auto __lambda25__(auto ){
	return compileGlobal(input, typeParams, typeArguments);
}
auto __lambda26__(auto ){
	return compileMethod(input, typeParams, typeArguments);
}
auto __lambda27__(auto ){
	return compileTypedBlock(input, "record ", typeParams);
}
auto __lambda28__(auto ){
	return compileTypedBlock(input, "interface ", typeParams);
}
auto __lambda29__(auto ){
	return compileTypedBlock(input, "class", typeParams);
}
/* private static */Result_String_CompileError compileClassSegment(String input, List__List__String typeParams, List__String typeArguments){
	return compileWhitespace(input).or(__lambda29__).or(__lambda28__).or(__lambda27__).or(__lambda26__).or(__lambda25__).or(__lambda24__).<Result<String, CompileError>>match(__lambda22__, __lambda23__);
}
auto __lambda30__(auto main){
	return Main.generateStatement(main);
}
/* private static */Option_String compileDefinitionStatement(String input, List__List__String typeParams, List__String typeArguments){
	if (1) {
	}
	String sliced = input.substring(0, input.length() - ";".length());
	return compileDefinition(sliced, typeParams, typeArguments).map(__lambda30__);
}
auto __lambda31__(auto globals){
	return globals.add(globals);
}
auto __lambda32__(auto value){
	return value + ";\n";
}
/* private static */Option_String compileGlobal(String input, List__List__String typeParams, List__String typeArguments){
	if (1) {
	}
	String substring = input.substring(0, input.length() - ";".length());
	Option_String maybeInitialization = compileInitialization(substring, typeParams, typeArguments);
	/* globals  */ = maybeInitialization.map(__lambda32__).map(__lambda31__).orElse(globals);
	return Some("");
}
auto __lambda33__(auto outputParams){
	return getStringOption(typeParams, typeArguments, outputParams, header, withBody);
}
auto __lambda34__(auto input1){
	return compileOrInvalidateDefinition(input1, typeParams, typeArguments);
}
/* private static */Option_String compileMethod(String input, List__List__String typeParams, List__String typeArguments){
	int paramStart = input.indexOf("(");
	if (1) {
	}
	String header = input.substring(0, paramStart).strip();
	String withParams = input.substring(paramStart + "(".length());
	int paramEnd = withParams.indexOf(")");
	if (1) {
	}
	String paramString = withParams.substring(0, paramEnd);
	String withBody = withParams.substring(paramEnd + ")".length()).strip();
	return compileValues(paramString, __lambda34__).flatMap(__lambda33__);
}
auto __lambda35__(auto definition){
	return getOption(typeParams, typeArguments, outputParams, withBody, definition);
}
/* private static */Option_String getStringOption(List__List__String typeParams, List__String typeArguments, String outputParams, String header, String withBody){
	return compileOrInvalidateDefinition(header, typeParams, typeArguments).flatMap(__lambda35__);
}
auto __lambda36__(auto statement){
	return addFunction(statement, string);
}
auto __lambda37__(auto input1){
	return compileStatementOrBlock(input1, typeParams, typeArguments).findValue();
}
/* private static */Option_String getOption(List__List__String typeParams, List__String typeArguments, String outputParams, String withBody, String definition){
	String string = generateInvokable(definition, outputParams);
	if (1) {
	}
	return compileStatements(withBody.substring(1, withBody.length() - 1), __lambda37__).map(__lambda36__);
}
/* private static */String addFunction(String content, String string){
	String function = string + "{" + content + "\n}\n";
	/* functions  */ = functions.add(function);
	return "";
}
/* private static */String generateInvokable(String definition, String params){
	return definition + "(" + params + ")";
}
auto __lambda38__(auto main){
	return Main.divideValueChar(main);
}
auto __lambda39__(auto main){
	return Main.mergeValues(main);
}
/* private static */Option_String compileValues(String input, Option_String (*)(String) compiler){
	return compileAll(divideAll(input, __lambda38__), compiler, __lambda39__);
}
/* private static */State divideValueChar(State state, Character c){
	if (1) {
	}
	State appended = state.append(c);
	if (1) {
	}
	if (1) {
	}
	if (1) {
	}
	return appended;
}
/* private static */StringBuilder mergeValues(StringBuilder buffer, String element){
	if (1) {
	}
	return buffer.append(", ").append(element);
}
auto __lambda40__(auto ok){
	return Ok.new(ok);
}
auto __lambda41__(auto ){
	return Err(CompileError("Invalid statement or block", input));
}
auto __lambda42__(auto ){
	return compileStatement(input, typeParams, typeArguments);
}
auto __lambda43__(auto ){
	return compilePostFix(input);
}
auto __lambda44__(auto ){
	return compileElse(input);
}
auto __lambda45__(auto ){
	return compileFor(input);
}
auto __lambda46__(auto ){
	return compileeWhile(input);
}
auto __lambda47__(auto ){
	return compileIf(input);
}
/* private static */Result_String_CompileError compileStatementOrBlock(String input, List__List__String typeParams, List__String typeArguments){
	return compileWhitespace(input).or(__lambda47__).or(__lambda46__).or(__lambda45__).or(__lambda44__).or(__lambda43__).or(__lambda42__).<Result<String, CompileError>>match(__lambda40__, __lambda41__);
}
/* private static */Option_String compilePostFix(String input){
	if (1) {
	}
	return None();
}
/* private static */Option_String compileElse(String input){
	if (1) {
	}
	return None();
}
/* private static */Option_String compileFor(String input){
	if (1) {
	}
	return None();
}
/* private static */Option_String compileeWhile(String input){
	if (1) {
	}
	return None();
}
/* private static */Option_String compileIf(String input){
	if (1) {
	}
	return None();
}
auto __lambda48__(auto ){
	return compileInvocationStatement(withoutEnd, typeParams, typeArguments);
}
auto __lambda49__(auto ){
	return compileAssignment(withoutEnd, typeParams, typeArguments);
}
auto __lambda50__(auto ){
	return compileInitialization(withoutEnd, typeParams, typeArguments);
}
/* private static */Option_String compileStatement(String input, List__List__String typeParams, List__String typeArguments){
	if (1) {
	}
	String withoutEnd = input.strip().substring(0, input.strip().length() - ";".length());
	return compileReturn(withoutEnd, typeParams, typeArguments).or(__lambda50__).or(__lambda49__).or(__lambda48__);
}
/* private static */Option_String compileReturn(String withoutEnd, List__List__String typeParams, List__String typeArguments){
	if (1) {
	}
	return None();
}
auto __lambda51__(auto main){
	return Main.generateStatement(main);
}
/* private static */Option_String compileInvocationStatement(String withoutEnd, List__List__String typeParams, List__String typeArguments){
	return compileInvocation(withoutEnd, typeParams, typeArguments).map(__lambda51__);
}
/* private static */Option_String compileAssignment(String withoutEnd, List__List__String typeParams, List__String typeArguments){
	int valueSeparator = withoutEnd.indexOf("=");
	if (1) {
	}
	return None();
}
auto __lambda52__(auto outputValue){
	return generateStatement(outputDefinition + " = " + outputValue);
}
auto __lambda53__(auto outputDefinition){
	return compileValue(inputValue, typeParams, typeArguments).map(__lambda52__);
}
/* private static */Option_String compileInitialization(String withoutEnd, List__List__String typeParams, List__String typeArguments){
	int separator = withoutEnd.indexOf("=");
	if (1) {
	}
	String inputDefinition = withoutEnd.substring(0, separator);
	String inputValue = withoutEnd.substring(separator + "=".length());
	return compileOrInvalidateDefinition(inputDefinition, typeParams, typeArguments).flatMap(__lambda53__);
}
/* private static */String generateStatement(String value){
	return "\n\t" + value + ";";
}
auto __lambda54__(auto ){
	return 
	System.err.println(Err(/* new CompileError("Invalid "  */ + /*  "value", input))
                             */.error.display());
	return Some(generatePlaceholder(input));;
}
auto __lambda55__(auto ){
	return compileOperator(input, ">=", typeParams, typeArguments);
}
auto __lambda56__(auto ){
	return compileOperator(input, "==", typeParams, typeArguments);
}
auto __lambda57__(auto ){
	return compileOperator(input, "-", typeParams, typeArguments);
}
/* private static */Option_String compileValue(String input, List__List__String typeParams, List__String typeArguments){
	String stripped = input.strip();
	if (1) {
	}
	if (1) {
	}
	if (1) {
	}
	if (1) {
	}
	Option_String maybeLambda = compileLambda(input, typeParams, typeArguments);
	if (1) {
	}
	Option_String maybeInvocation = compileInvocation(stripped, typeParams, typeArguments);
	if (1) {
	}
	int ternaryIndex = stripped.indexOf("?");
	if (1) {
	}
	int dataSeparator = stripped.lastIndexOf(".");
	if (1) {
	}
	int methodSeparator = stripped.lastIndexOf("::");
	if (1) {
	}
	return compileOperator(input, "+", typeParams, typeArguments).or(__lambda57__).or(__lambda56__).or(__lambda55__).or(() -> isSymbol(stripped) ? Some(stripped) : None(/* ))
                 */.or(() -> isNumber(stripped) ? Some(stripped) : /* new None<>())
                 */.or(__lambda54__);
}
auto __lambda58__(auto outputValue){
	return generateLambda(paramNames, outputValue);
}
auto __lambda59__(auto paramNames){
	return 
	String inputValue = input.substring(arrowIndex + "->".length()).strip();
	return compileLambdaBody(inputValue, typeParams, typeArguments).map(__lambda58__);;
}
/* private static */Option_String compileLambda(String input, List__List__String typeParams, List__String typeArguments){
	int arrowIndex = input.indexOf("->");
	if (1) {
	}
	String beforeArrow = input.substring(0, arrowIndex).strip();
	return findLambdaParams(beforeArrow).flatMap(__lambda59__);
}
/* private static */Option_List__String findLambdaParams(String beforeArrow){
	if (1) {
	}
	if (1) {
	}
	return None();
}
/* private static */Option_String compileLambdaBody(String inputValue, List__List__String typeParams, List__String typeArguments){
	if (1) {
	}
	else {}
}
auto __lambda60__(auto tuple){
	return tuple.left + " " + operator + " " + tuple.right;
}
auto __lambda61__(auto ){
	return compileValue(right, typeParams, typeArguments);
}
/* private static */Option_String compileOperator(String input, String operator, List__List__String typeParams, List__String typeArguments){
	int operatorIndex = input.indexOf(operator);
	if (1) {
	}
	String left = input.substring(0, operatorIndex);
	String right = input.substring(operatorIndex + operator.length());
	return compileValue(left, typeParams, typeArguments).and(__lambda61__).map(__lambda60__);
}
/* private static */int isNumber(String stripped){
	for (;;) {
	}
	return true;
}
auto __lambda62__(auto name){
	return generateDefinition("", "auto", name);
}
/* private static */String generateLambda(List__String paramNames, String lambdaValue){
	String lambda = "__lambda" + lambdaCounter + "__";
	temp++;
	String definition = generateDefinition("", "auto", lambda);
	String params = paramNames.stream().map(__lambda62__).collect(Joiner(", ")).orElse("");
	addFunction("\n\treturn " + lambdaValue + ";", generateInvokable(definition, params));
	return lambda;
}
auto __lambda63__(auto outputCaller){
	return generateInvocation(outputCaller, outputValues);
}
auto __lambda64__(auto outputValues){
	return compileValue(inputCaller, typeParams, typeArguments).map(__lambda63__);
}
/* private static */Option_String compileInvocation(String stripped, List__List__String typeParams, List__String typeArguments){
	if (1) {
	}
	String withoutEnd = stripped.substring(0, stripped.length() - ")".length());
	int argsStart =  - 1;
	int depth = 0;
	for (;;) {
	}
	if (1) {
	}
	String inputCaller = withoutEnd.substring(0, argsStart);
	String inputArguments = withoutEnd.substring(argsStart + 1);
	return compileAllValues(inputArguments, typeParams, typeArguments).flatMap(__lambda64__);
}
auto __lambda65__(auto input){
	return compileValue(input, typeParams, typeArguments);
}
/* private static */Option_String compileAllValues(String arguments, List__List__String typeParams, List__String typeArguments){
	return compileValues(arguments, __lambda65__);
}
/* private static */String generateInvocation(String caller, String arguments){
	return caller + "(" + arguments + ")";
}
auto __lambda66__(auto ){
	return 
	System.err.println(Err(/* new CompileError("Invalid "  */ + /*  "definition", input))
                     */.error.display());
	return Some(generatePlaceholder(input));;
}
/* private static */Option_String compileOrInvalidateDefinition(String input, List__List__String typeParams, List__String typeArguments){
	return compileDefinition(input, typeParams, typeArguments).or(__lambda66__);
}
/* private static */Option_String compileDefinition(String input, List__List__String typeParams, List__String typeArguments){
	String stripped = input.strip();
	if (1) {
	}
	int nameSeparator = stripped.lastIndexOf(" ");
	if (1) {
	}
	return None();
}
/* private static */String generateDefinition(String modifiers, String type, String name){
	return modifiers + type + " " + name;
}
/* private static */Option_String compileType(String type, List__List__String frames, List__String typeArguments){
	String stripped = type.strip();
	if (1) {
	}
	if (1) {
	}
	if (1) {
	}
	if (1) {
	}
	if (1) {
	}
	if (1) {
	}
	System.err.println(Err(/* new CompileError("Invalid "  */ + /*  "type", stripped))
                 */.error.display());
	return Some(generatePlaceholder(stripped));
}
auto __lambda67__(auto string){
	return String.equals(string);
}
auto __lambda68__(auto string){
	return String.equals(string);
}
auto __lambda69__(auto typeParams, auto typeParams2){
	return Lists.equalsTo(typeParams, typeParams2, __lambda68__);
}
auto __lambda70__(auto stringListTuple, auto stringListTuple2){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, __lambda67__, __lambda69__);
}
/* private static */int isDefined(List__Tuple_String_List__String toExpand, Tuple_String_List__String tuple){
	return Lists.contains(toExpand, tuple, __lambda70__);
}
/* private static */String generateGenericName(String base, List__String newArguments){
	String joined = newArguments.stream().collect(Joiner("_")).orElse("");
	return base + "_" + String.join("_", joined);
}
auto __lambda71__(auto list_){
	return List_.stream(list_);
}
/* private static */int hasNoTypeParams(List__List__String frames){
	Option_String next = frames.stream().flatMap(__lambda71__).next();
	return next.isEmpty();
}
auto __lambda72__(auto string){
	return String.equals(string);
}
auto __lambda73__(auto frame){
	return Lists.contains(frame, stripped, __lambda72__);
}
/* private static */int isTypeParam(List__List__String frames, String stripped){
	return frames.stream().anyMatch(__lambda73__);
}
/* private static */String generateFunctionalType(String returns, List__String newArguments){
	String joined = newArguments.stream().collect(Joiner(", ")).orElse("");
	return returns + " (*)(" + joined + ")";
}
/* private static */int isSymbol(String input){
	if (1) {
	}
	if (1) {
	}
	for (;;) {
	}
	return true;
}
/* private static */String generatePlaceholder(String input){
	return "/* " + input + " */";
}
int main(){
	__main__();
	return 0;
}
