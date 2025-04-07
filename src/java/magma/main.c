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
#include <temp.h>
/* private static */ struct Lists {
};
/* private static */ struct State {
	/* private final */List__Character queue;
	/* private */List__String segments;
	/* private */StringBuilder buffer;
	/* private */int depth;
};
/* private static */ struct Streams {
};
/* private */ struct Joiner {
};
/* private static */ struct Tuples {
};
/* private static Option<String> compileClassSegment(String input, List_<List_<String>> typeParams, List_<String> typeArguments) {
        if (input.isBlank()) return new Some<>("");

        Option<String> maybeClass = compileTypedBlock(input, " */ struct ", typeParams);
        if  {
	/* globals *//* = */ maybeInitialization.map(/* value -> value *//* + */ ";\n");
	/* return */new Some<>(/* "" */);
	/* }

       */ if(/* input.endsWith(";" */);
	return compileDefinition(/* sliced */, /*  typeParams */, /*  typeArguments */);
	/* }

        return */new Some<>(/* invalidate("class *//* segment", */ input);
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
/* private */ struct List__T {
	List__T add(T element);
	List__T addAll(List__T elements);
	void forEach(void (*)(T) consumer);
	Stream__T stream();
	T popFirst();
	int isEmpty();
	T get(int index);
	int size();
	T last();
	Stream__Tuple_Integer_T streamWithIndices();
	T first();
};
/* sealed public */ struct Option_Integer {
	void ifPresent(void (*)(Integer) ifPresent);
	/* <R> */Option_R flatMap(Option_R (*)(Integer) mapper);
	/* <R> */Option_R map(R (*)(Integer) mapper);
	Integer orElse(Integer other);
	int isPresent();
	Tuple_int_Integer toTuple(Integer other);
	Integer orElseGet(Integer (*)() other);
	Option_Integer or(Option_Integer (*)() other);
	int isEmpty();
	/* <R> */Option_Tuple_Integer_R and(Option_R (*)() other);
};
/* private */ struct List__Character {
	List__Character add(Character element);
	List__Character addAll(List__Character elements);
	void forEach(void (*)(Character) consumer);
	Stream__Character stream();
	Character popFirst();
	int isEmpty();
	Character get(int index);
	int size();
	Character last();
	Stream__Tuple_Integer_Character streamWithIndices();
	Character first();
};
/* private */ struct List__String {
	List__String add(String element);
	List__String addAll(List__String elements);
	void forEach(void (*)(String) consumer);
	Stream__String stream();
	String popFirst();
	int isEmpty();
	String get(int index);
	int size();
	String last();
	Stream__Tuple_Integer_String streamWithIndices();
	String first();
};
/* private */ struct Stream__Character {
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
/* private */ struct Stream__T {
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
};
/* private */ struct Tuple_A_B {
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
};
/* private */ struct List__List__String {
	List__List__String add(List__String element);
	List__List__String addAll(List__List__String elements);
	void forEach(void (*)(List__String) consumer);
	Stream__List__String stream();
	List__String popFirst();
	int isEmpty();
	List__String get(int index);
	int size();
	List__String last();
	Stream__Tuple_Integer_List__String streamWithIndices();
	List__String first();
};
/* private */ struct Tuple_String_List__String {
};
/* private */ struct List__Tuple_String_List__String {
	List__Tuple_String_List__String add(Tuple_String_List__String element);
	List__Tuple_String_List__String addAll(List__Tuple_String_List__String elements);
	void forEach(void (*)(Tuple_String_List__String) consumer);
	Stream__Tuple_String_List__String stream();
	Tuple_String_List__String popFirst();
	int isEmpty();
	Tuple_String_List__String get(int index);
	int size();
	Tuple_String_List__String last();
	Stream__Tuple_Integer_Tuple_String_List__String streamWithIndices();
	Tuple_String_List__String first();
};

	/* private static */int lambdaCounter = 0;;
/* public static <T> */List__T empty(){
	return Temp();
}
auto __lambda0__(auto child){
	return equator.apply(element, child);
}
/* public static <T> */int contains(List__T list, T element, int (*)(T, T) equator){
	return list.stream().anyMatch(__lambda0__);
}
/* public static <T> */int equalsTo(List__T elements, List__T other, int (*)(T, T) equator){
	if (1) {
	}
	return Temp();
}
auto __lambda1__(auto tuple){
	return Tuple.left(tuple);
}
auto __lambda2__(auto tuple){
	return equator.apply(tuple.right, element);
}
/* public static <T> */Option_Integer indexOf(List__T list, T element, int (*)(T, T) equator){
	return list.streamWithIndices().filter(__lambda2__).next().map(__lambda1__);
}
private State(List__Character queue){
	this(queue, Lists.empty(), Temp()(), 0);
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
	return !queue.isEmpty();
}
/* private */State enter(){
	/* this.depth  */ = depth + 1;
	return this;
}
/* private */State exit(){
	/* this.depth  */ = depth - 1;
	return this;
}
/* private */State append(char c){
	buffer.append(c);
	return this;
}
/* private */State advance(){
	/* segments  */ = segments.add(buffer.toString());
	/* this.buffer  */ = Temp()();
	return this;
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
	return Temp();
}
/* public static <T> */Stream__T empty(){
	return Temp();
}
/* @Override
        public */Option_String createInitial(){
	return Temp();
}
/* @Override
        public */Option_String fold(Option_String current, String element){
	return Temp();
}
/* public static <A, B> */int equalsTo(Tuple_A_B left, Tuple_A_B right, int (*)(A, A) leftEquator, int (*)(B, B) rightEquator){
	return leftEquator.apply(left.left, right.left) &&
                    rightEquator.apply(left.right, right.right);
}
auto __lambda3__(auto throwable){
	return Throwable.printStackTrace(throwable);
}
auto __lambda4__(auto input){
	return runWithInput(source, input);
}
auto __lambda5__(auto some){
	return Some.new(some);
}
/* public static */void main(String* args){
	Path source = Paths.get(".", "src", "java", "magma", "Main.java");
	magma.Files.readString(source).match(__lambda4__, __lambda5__).ifPresent(__lambda3__);
}
/* private static */Option_IOException runWithInput(Path source, String input){
	String output = compile(input) + "int main(){\n\t__main__();\n\treturn 0;\n}\n";
	Path target = source.resolveSibling("main.c");
	return magma.Files.writeString(target, output);
}
auto __lambda6__(auto main){
	return Main.divideStatementChar(main);
}
auto __lambda7__(auto main){
	return Main.mergeStatements(main);
}
auto __lambda8__(auto compiled){
	return mergeAll(compiled, __lambda7__);
}
auto __lambda9__(auto main){
	return Main.generate(main);
}
auto __lambda10__(auto main){
	return Main.compileRootSegment(main);
}
/* private static */String compile(String input){
	List__String segments = divideAll(input, __lambda6__);
	return parseAll(segments, __lambda10__).map(__lambda9__).map(__lambda8__).orElse("");
}
/* private static */List__String generate(List__String compiled){
	while (1) {
	}
	return compiled.addAll(imports).addAll(structs).addAll(globals).addAll(functions);
}
auto __lambda11__(auto main){
	return Main.divideStatementChar(main);
}
auto __lambda12__(auto main){
	return Main.mergeStatements(main);
}
/* private static */Option_String compileStatements(String input, Option_String (*)(String) compiler){
	return compileAll(divideAll(input, __lambda11__), compiler, __lambda12__);
}
auto __lambda13__(auto compiled){
	return mergeAll(compiled, merger);
}
/* private static */Option_String compileAll(List__String segments, Option_String (*)(String) compiler, StringBuilder (*)(StringBuilder, String) merger){
	return parseAll(segments, compiler).map(__lambda13__);
}
/* private static */String mergeAll(List__String compiled, StringBuilder (*)(StringBuilder, String) merger){
	return compiled.stream().foldWithInitial(Temp(), merger).toString();
}
auto __lambda14__(auto compiled){
	return compiled.add(compiled);
}
auto __lambda15__(auto compiled, auto segment){
	return compiler.apply(segment).map(__lambda14__);
}
/* private static */Option_List__String parseAll(List__String segments, Option_String (*)(String) compiler){
	return segments.stream().foldToOption(Lists.empty(), __lambda15__);
}
/* private static */StringBuilder mergeStatements(StringBuilder output, String str){
	return output.append(str);
}
/* private static */List__String divideAll(String input, State (*)(State, Character) divider){
	List__Character queue = Streams.from(input).collect(Temp());
	State current = Temp()(queue);
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
	return Temp();
}
/* private static */Option_State divideSingleQuotes(State current, char c){
	if (1) {
	}
	State appended = current.append(c);
	char maybeEscape = current.pop();
	State withNext = appended.append(maybeEscape);
	State appended1 = maybeEscape == '\\' ? withNext.popAndAppend() : withNext;
	return Temp();
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
	String stripped = input.strip();
	if (1) {
	}
	if (1) {
	}
	if (1) {
	}
	List__List__String frame = Temp()(Lists.empty());
	Option_String maybeClass = compileTypedBlock(input, "class ", frame);
	if (1) {
	}
	return Temp();
}
auto __lambda16__(auto ){
	return compileToStruct(modifiers, withoutPermits1, body, typeParams, Lists.empty(), Lists.empty());
}
/* private static */Option_String compileTypedBlock(String input, String keyword, List__List__String typeParams){
	int classIndex = input.indexOf(keyword);
	if (1) {
	}
	String modifiers = input.substring(0, classIndex).strip();
	String afterKeyword = input.substring(classIndex + keyword.length());
	int contentStart = afterKeyword.indexOf("{");
	if (1) {
	}
	String beforeContent = afterKeyword.substring(0, contentStart).strip();
	int permitsIndex = beforeContent.indexOf("permits");
	String withoutPermits = permitsIndex >= 0 ? beforeContent.substring(0, permitsIndex).strip() : beforeContent;
	int paramStart = withoutPermits.indexOf("(");
	String withoutPermits1 = paramStart >= 0 ? withoutPermits.substring(0, paramStart) : withoutPermits;
	String body = afterKeyword.substring(contentStart + "{".length()).strip();
	return compileGenericTypedBlock(withoutPermits1, modifiers, body, typeParams).or(__lambda16__);
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
	List__String finalClassTypeParams = Temp()(Temp());
	/* generators.put(name, typeArguments -> {
            */String joined = /*  generateGenericName(name, typeArguments);
            return compileToStruct(modifiers, joined, body, typeParams, finalClassTypeParams, typeArguments);
        }) */;
	return Temp();
}
auto __lambda17__(auto outputContent){
	return generateStruct(modifiers, name, outputContent);
}
auto __lambda18__(auto input1){
	return compileClassSegment(input1, merged, typeArguments);
}
/* private static */Option_String compileToStruct(String modifiers, String name, String body, List__List__String outerTypeParams, List__String innerTypeParams, List__String typeArguments){
	List__List__String merged = outerTypeParams.add(innerTypeParams);
	if (1) {
	}
	String inputContent = body.substring(0, body.length() - "}".length());
	return compileStatements(inputContent, __lambda18__).map(__lambda17__);
}
/* private static */String generateStruct(String modifiers, String name, String content){
	String modifiersString = modifiers.isEmpty() ? "" : generatePlaceholder(modifiers) + " ";
	String generated = modifiersString + "struct " + name + " {" +
                content +
                "\n};\n";
	/* structs  */ = structs.add(generated);
	return "";
}
/* private static */String invalidate(String type, String input){
	System.err.println("Invalid " + type + ": " + input);
	return generatePlaceholder(input);
}
/* private static */Option_String compileInitializationStatement(String input, List__List__String typeParams, List__String typeArguments){
	if (1) {
	}
	else {}
}
auto __lambda19__(auto statement){
	return 
	return addFunction(statement, string);;
}
auto __lambda20__(auto input1){
	return compileStatement(input1, typeParams, typeArguments);
}
auto __lambda21__(auto definition){
	return 
	String string = generateInvokable(definition, outputParams);
	if (1) {
	}
	return compileStatements(withBody.substring(1, withBody.length() - 1), __lambda20__).map(__lambda19__);;
}
auto __lambda22__(auto outputParams){
	return 
	return compileOrInvalidateDefinition(header, typeParams, typeArguments).flatMap(__lambda21__);;
}
auto __lambda23__(auto input1){
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
	return compileValues(paramString, __lambda23__).flatMap(__lambda22__);
}
/* private static */String addFunction(String content, String string){
	String function = string + "{" + content + "\n}\n";
	/* functions  */ = functions.add(function);
	return "";
}
/* private static */String generateInvokable(String definition, String params){
	return definition + "(" + params + ")";
}
auto __lambda24__(auto main){
	return Main.divideValueChar(main);
}
auto __lambda25__(auto main){
	return Main.mergeValues(main);
}
/* private static */Option_String compileValues(String input, Option_String (*)(String) compiler){
	return compileAll(divideAll(input, __lambda24__), compiler, __lambda25__);
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
/* private static */Option_String compileStatement(String input, List__List__String typeParams, List__String typeArguments){
	String stripped = input.strip();
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
	if (1) {
	}
	return Temp();
}
auto __lambda26__(auto outputValue){
	return generateStatement(outputDefinition + " = " + outputValue);
}
auto __lambda27__(auto outputDefinition){
	return compileValue(inputValue, typeParams, typeArguments).map(__lambda26__);
}
/* private static */Option_String compileInitialization(String withoutEnd, List__List__String typeParams, List__String typeArguments){
	int separator = withoutEnd.indexOf("=");
	if (1) {
	}
	String inputDefinition = withoutEnd.substring(0, separator);
	String inputValue = withoutEnd.substring(separator + "=".length());
	return compileOrInvalidateDefinition(inputDefinition, typeParams, typeArguments).flatMap(__lambda27__);
}
/* private static */String generateStatement(String value){
	return "\n\t" + value + ";";
}
auto __lambda28__(auto ){
	return Temp();
}
auto __lambda29__(auto ){
	return compileOperator(input, ">=", typeParams, typeArguments);
}
auto __lambda30__(auto ){
	return compileOperator(input, "==", typeParams, typeArguments);
}
auto __lambda31__(auto ){
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
	return compileOperator(input, "+", typeParams, typeArguments).or(__lambda31__).or(__lambda30__).or(__lambda29__).or(() -> isSymbol(stripped) ? Temp() : Temp()(__lambda28__);
}
auto __lambda32__(auto outputValue){
	return generateLambda(paramNames, outputValue);
}
auto __lambda33__(auto paramNames){
	return 
	String inputValue = input.substring(arrowIndex + "->".length()).strip();
	return compileLambdaBody(inputValue, typeParams, typeArguments).map(__lambda32__);;
}
/* private static */Option_String compileLambda(String input, List__List__String typeParams, List__String typeArguments){
	int arrowIndex = input.indexOf("->");
	if (1) {
	}
	String beforeArrow = input.substring(0, arrowIndex).strip();
	return findLambdaParams(beforeArrow).flatMap(__lambda33__);
}
/* private static */Option_List__String findLambdaParams(String beforeArrow){
	if (1) {
	}
	if (1) {
	}
	return Temp();
}
/* private static */Option_String compileLambdaBody(String inputValue, List__List__String typeParams, List__String typeArguments){
	if (1) {
	}
	else {}
}
auto __lambda34__(auto tuple){
	return tuple.left + " " + operator + " " + tuple.right;
}
auto __lambda35__(auto ){
	return compileValue(right, typeParams, typeArguments);
}
/* private static */Option_String compileOperator(String input, String operator, List__List__String typeParams, List__String typeArguments){
	int operatorIndex = input.indexOf(operator);
	if (1) {
	}
	String left = input.substring(0, operatorIndex);
	String right = input.substring(operatorIndex + operator.length());
	return compileValue(left, typeParams, typeArguments).and(__lambda35__).map(__lambda34__);
}
/* private static */int isNumber(String stripped){
	for (;;) {
	}
	return true;
}
auto __lambda36__(auto name){
	return generateDefinition("", "auto", name);
}
/* private static */String generateLambda(List__String paramNames, String lambdaValue){
	String lambda = "__lambda" + lambdaCounter + "__";
	temp++;
	String definition = generateDefinition("", "auto", lambda);
	String params = paramNames.stream().map(__lambda36__).collect(Temp()).orElse("");
	addFunction("\n\treturn " + lambdaValue + ";", generateInvokable(definition, params));
	return lambda;
}
auto __lambda37__(auto outputCaller){
	return 
	return generateInvocation(outputCaller, outputValues);;
}
auto __lambda38__(auto outputValues){
	return 
	return compileValue(inputCaller, typeParams, typeArguments).map(__lambda37__);;
}
auto __lambda39__(auto input){
	return compileValue(input, typeParams, typeArguments);
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
	return compileValues(inputArguments, __lambda39__).flatMap(__lambda38__);
}
/* private static */String generateInvocation(String caller, String arguments){
	return caller + "(" + arguments + ")";
}
auto __lambda40__(auto ){
	return Temp();
}
/* private static */Option_String compileOrInvalidateDefinition(String input, List__List__String typeParams, List__String typeArguments){
	return compileDefinition(input, typeParams, typeArguments).or(__lambda40__);
}
/* private static */Option_String compileDefinition(String input, List__List__String typeParams, List__String typeArguments){
	String stripped = input.strip();
	if (1) {
	}
	int nameSeparator = stripped.lastIndexOf(" ");
	if (1) {
	}
	return Temp();
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
	return Temp();
}
auto __lambda41__(auto string){
	return String.equals(string);
}
auto __lambda42__(auto string){
	return String.equals(string);
}
auto __lambda43__(auto typeParams, auto typeParams2){
	return Lists.equalsTo(typeParams, typeParams2, __lambda42__);
}
auto __lambda44__(auto stringListTuple, auto stringListTuple2){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, __lambda41__, __lambda43__);
}
/* private static */int isDefined(List__Tuple_String_List__String toExpand, Tuple_String_List__String tuple){
	return Lists.contains(toExpand, tuple, __lambda44__);
}
/* private static */String generateGenericName(String base, List__String newArguments){
	String joined = newArguments.stream().collect(Temp()).orElse("");
	return base + "_" + String.join("_", joined);
}
auto __lambda45__(auto list_){
	return List_.stream(list_);
}
/* private static */int hasNoTypeParams(List__List__String frames){
	Option_String next = frames.stream().flatMap(__lambda45__).next();
	return next.isEmpty();
}
auto __lambda46__(auto string){
	return String.equals(string);
}
auto __lambda47__(auto frame){
	return 
	return Lists.contains(frame, stripped, __lambda46__);;
}
/* private static */int isTypeParam(List__List__String frames, String stripped){
	return frames.stream().anyMatch(__lambda47__);
}
/* private static */String generateFunctionalType(String returns, List__String newArguments){
	String joined = newArguments.stream().collect(Temp()).orElse("");
	return returns + " (*)(" + joined + ")";
}
/* private static */int isSymbol(String input){
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
