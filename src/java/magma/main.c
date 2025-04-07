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
#include <temp.h>
/* private static */ struct Lists {
};
/* private static */ struct State {/* private final List_<Character> queue; *//* 
        private List_<String> segments; *//* 
        private StringBuilder buffer; *//* 
        private int depth; */
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
/* private */ struct List___T__ {
	List___T__ add(T element);
	List___T__ addAll(List___T__ elements);
	void forEach(void (*)(T) consumer);
	Stream___T__ stream();
	T popFirst();
	int isEmpty();
	T get(int index);
	int size();
	T last();
	Stream___Tuple__Integer_T____ streamWithIndices();
	T first();
};
/* private sealed */ struct Option__Integer__ {
	void ifPresent(void (*)(Integer) ifPresent);
	/* <R> */Option__R__ flatMap(Option__R__ (*)(Integer) mapper);
	/* <R> */Option__R__ map(R (*)(Integer) mapper);
	Integer orElse(Integer other);
	int isPresent();
	Tuple__int_Integer__ toTuple(Integer other);
	Integer orElseGet(Integer (*)() other);
	Option__Integer__ or(Option__Integer__ (*)() other);
	int isEmpty();
	/* <R> */Option__Tuple__Integer_R____ and(Option__R__ (*)() other);
};
/* private */ struct List___Character__ {
	List___Character__ add(Character element);
	List___Character__ addAll(List___Character__ elements);
	void forEach(void (*)(Character) consumer);
	Stream___Character__ stream();
	Character popFirst();
	int isEmpty();
	Character get(int index);
	int size();
	Character last();
	Stream___Tuple__Integer_Character____ streamWithIndices();
	Character first();
};
/* private */ struct List___String__ {
	List___String__ add(String element);
	List___String__ addAll(List___String__ elements);
	void forEach(void (*)(String) consumer);
	Stream___String__ stream();
	String popFirst();
	int isEmpty();
	String get(int index);
	int size();
	String last();
	Stream___Tuple__Integer_String____ streamWithIndices();
	String first();
};
/* private */ struct Stream___Character__ {
	/* <R> */Stream___R__ map(R (*)(Character) mapper);
	/* <R> */R foldWithInitial(R initial, R (*)(R, Character) folder);
	/* <C> */C collect(Collector__Character_C__ collector);
	/* <R> */Option__R__ foldToOption(R initial, Option__R__ (*)(R, Character) folder);
	int anyMatch(Predicate__Character__ predicate);
	/* <R> */Stream___R__ flatMap(Stream___R__ (*)(Character) mapper);
	Stream___Character__ concat(Stream___Character__ other);
	Option__Character__ next();
	int allMatch(Predicate__Character__ predicate);
	Stream___Character__ filter(Predicate__Character__ predicate);
};
/* private */ struct Stream___T__ {
	/* <R> */Stream___R__ map(R (*)(T) mapper);
	/* <R> */R foldWithInitial(R initial, R (*)(R, T) folder);
	/* <C> */C collect(Collector__T_C__ collector);
	/* <R> */Option__R__ foldToOption(R initial, Option__R__ (*)(R, T) folder);
	int anyMatch(Predicate__T__ predicate);
	/* <R> */Stream___R__ flatMap(Stream___R__ (*)(T) mapper);
	Stream___T__ concat(Stream___T__ other);
	Option__T__ next();
	int allMatch(Predicate__T__ predicate);
	Stream___T__ filter(Predicate__T__ predicate);
};
/* private sealed */ struct Option__String__ {
	void ifPresent(void (*)(String) ifPresent);
	/* <R> */Option__R__ flatMap(Option__R__ (*)(String) mapper);
	/* <R> */Option__R__ map(R (*)(String) mapper);
	String orElse(String other);
	int isPresent();
	Tuple__int_String__ toTuple(String other);
	String orElseGet(String (*)() other);
	Option__String__ or(Option__String__ (*)() other);
	int isEmpty();
	/* <R> */Option__Tuple__String_R____ and(Option__R__ (*)() other);
};
/* private */ struct Tuple__A_B__ {
};
/* private sealed */ struct Option__IOException__ {
	void ifPresent(void (*)(IOException) ifPresent);
	/* <R> */Option__R__ flatMap(Option__R__ (*)(IOException) mapper);
	/* <R> */Option__R__ map(R (*)(IOException) mapper);
	IOException orElse(IOException other);
	int isPresent();
	Tuple__int_IOException__ toTuple(IOException other);
	IOException orElseGet(IOException (*)() other);
	Option__IOException__ or(Option__IOException__ (*)() other);
	int isEmpty();
	/* <R> */Option__Tuple__IOException_R____ and(Option__R__ (*)() other);
};
/* private sealed */ struct Result__String_IOException__ {
	/* <R> */R match(R (*)(String) whenOk, R (*)(IOException) whenErr);
};
/* private sealed */ struct Option__List___String____ {
	void ifPresent(void (*)(List___String__) ifPresent);
	/* <R> */Option__R__ flatMap(Option__R__ (*)(List___String__) mapper);
	/* <R> */Option__R__ map(R (*)(List___String__) mapper);
	List___String__ orElse(List___String__ other);
	int isPresent();
	Tuple__int_List___String____ toTuple(List___String__ other);
	List___String__ orElseGet(List___String__ (*)() other);
	Option__List___String____ or(Option__List___String____ (*)() other);
	int isEmpty();
	/* <R> */Option__Tuple__List___String___R____ and(Option__R__ (*)() other);
};
/* private sealed */ struct Option__State__ {
	void ifPresent(void (*)(State) ifPresent);
	/* <R> */Option__R__ flatMap(Option__R__ (*)(State) mapper);
	/* <R> */Option__R__ map(R (*)(State) mapper);
	State orElse(State other);
	int isPresent();
	Tuple__int_State__ toTuple(State other);
	State orElseGet(State (*)() other);
	Option__State__ or(Option__State__ (*)() other);
	int isEmpty();
	/* <R> */Option__Tuple__State_R____ and(Option__R__ (*)() other);
};
/* private */ struct List___List___String____ {
	List___List___String____ add(List___String__ element);
	List___List___String____ addAll(List___List___String____ elements);
	void forEach(void (*)(List___String__) consumer);
	Stream___List___String____ stream();
	List___String__ popFirst();
	int isEmpty();
	List___String__ get(int index);
	int size();
	List___String__ last();
	Stream___Tuple__Integer_List___String______ streamWithIndices();
	List___String__ first();
};
/* private */ struct Tuple__String_List___String____ {
};
/* private */ struct List___Tuple__String_List___String______ {
	List___Tuple__String_List___String______ add(Tuple__String_List___String____ element);
	List___Tuple__String_List___String______ addAll(List___Tuple__String_List___String______ elements);
	void forEach(void (*)(Tuple__String_List___String____) consumer);
	Stream___Tuple__String_List___String______ stream();
	Tuple__String_List___String____ popFirst();
	int isEmpty();
	Tuple__String_List___String____ get(int index);
	int size();
	Tuple__String_List___String____ last();
	Stream___Tuple__Integer_Tuple__String_List___String________ streamWithIndices();
	Tuple__String_List___String____ first();
};

	/* private static */int lambdaCounter = 0;;
/* public static <T> */List___T__ empty(){
	return Temp();
}
/* public static <T> */List___T__ of(/* T... */ elements){
	return Temp();
}
auto __lambda0__(auto child){
	return equator.apply(element, child);
}
/* public static <T> */int contains(List___T__ list, T element, int (*)(T, T) equator){
	return list.stream().anyMatch(__lambda0__);
}
/* public static <T> */int equalsTo(List___T__ elements, List___T__ other, int (*)(T, T) equator){if (1) {
}

	return Temp();
}
auto __lambda1__(auto tuple){
	return Tuple.left(tuple);
}
auto __lambda2__(auto tuple){
	return equator.apply(tuple.right, element);
}
/* public static <T> */Option__Integer__ indexOf(List___T__ list, T element, int (*)(T, T) equator){
	return list.streamWithIndices().filter(__lambda2__).next().map(__lambda1__);
}
private State(List___Character__ queue){
	this(queue, Lists.empty(), Temp()(), 0);
}
private State(List___Character__ queue, List___String__ segments, StringBuilder buffer, int depth){
	/* this.queue */ = queue;
	/* this.segments */ = segments;
	/* this.buffer */ = buffer;
	/* this.depth */ = depth;
}
/* private */State popAndAppend(){
	return append(pop());
}
/* private */int hasNext(){
	return !queue.isEmpty();
}
/* private */State enter(){
	/* this.depth */ = depth + 1;
	return this;
}
/* private */State exit(){
	/* this.depth */ = depth - 1;
	return this;
}
/* private */State append(char c){
	buffer.append(c);
	return this;
}
/* private */State advance(){
	/* segments */ = segments.add(buffer.toString());
	/* this.buffer */ = Temp()();
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
/* public */List___String__ segments(){
	return segments;
}
/* public */char peek(){
	return queue.first();
}
/* public static */Stream___Character__ from(String value){
	return Temp();
}
/* public static <T> */Stream___T__ empty(){
	return Temp();
}
/* @Override
        public */Option__String__ createInitial(){
	return Temp();
}
/* @Override
        public */Option__String__ fold(Option__String__ current, String element){
	return Temp();
}
/* public static <A, B> */int equalsTo(Tuple__A_B__ left, Tuple__A_B__ right, int (*)(A, A) leftEquator, int (*)(B, B) rightEquator){
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
	readString(source).match(__lambda4__, __lambda5__).ifPresent(__lambda3__);
}
/* private static */Option__IOException__ runWithInput(Path source, String input){
	String output = compile(input) + "int main(){\n\t__main__();\n\treturn 0;\n}\n";
	Path target = source.resolveSibling("main.c");
	return writeString(target, output);
}
/* private static */Option__IOException__ writeString(Path target, String output){/* 
        try {
            Files.writeString(target, output);
            return new None<>();
        } *//*  catch (IOException e) {
            return new Some<>(e);
        } */
}
/* private static */Result__String_IOException__ readString(Path source){/* 
        try {
            return new Ok<>(Files.readString(source));
        } *//*  catch (IOException e) {
            return new Err<>(e);
        } */
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
	List___String__ segments = divideAll(input, __lambda6__);
	return parseAll(segments, __lambda10__).map(__lambda9__).map(__lambda8__).orElse("");
}
/* private static */List___String__ generate(List___String__ compiled){/* 
        while (!toExpand.isEmpty()) {
            Tuple<String, List_<String>> tuple = toExpand.popFirst();
            if (isDefined(expanded, tuple)) continue;

            expanded.add(tuple);
            if (generators.containsKey(tuple.left)) {
                Function<List_<String>, Option<String>> generator = generators.get(tuple.left);
                generator.apply(tuple.right);
            } else {
                System.err.println(tuple.left + " is not a generic type");
            }
        } */
	return compiled.addAll(imports).addAll(structs).addAll(globals).addAll(functions);
}
auto __lambda11__(auto main){
	return Main.divideStatementChar(main);
}
auto __lambda12__(auto main){
	return Main.mergeStatements(main);
}
/* private static */Option__String__ compileStatements(String input, Option__String__ (*)(String) compiler){
	return compileAll(divideAll(input, __lambda11__), compiler, __lambda12__);
}
auto __lambda13__(auto compiled){
	return mergeAll(compiled, merger);
}
/* private static */Option__String__ compileAll(List___String__ segments, Option__String__ (*)(String) compiler, StringBuilder (*)(StringBuilder, String) merger){
	return parseAll(segments, compiler).map(__lambda13__);
}
/* private static */String mergeAll(List___String__ compiled, StringBuilder (*)(StringBuilder, String) merger){
	return compiled.stream().foldWithInitial(Temp(), merger).toString();
}
auto __lambda14__(auto compiled){
	return compiled.add(compiled);
}
/* private static */Option__List___String____ parseAll(List___String__ segments, Option__String__ (*)(String) compiler){
	return segments.stream().foldToOption(Lists.empty(), (compiled, segment) - /* > compiler */.apply(segment).map(__lambda14__));
}
/* private static */StringBuilder mergeStatements(StringBuilder output, String str){
	return output.append(str);
}
/* private static */List___String__ divideAll(String input, State (*)(State, Character) divider){
	List___Character__ queue = Streams.from(input).collect(Temp());
	State current = Temp()(queue);/* 
        while (current.hasNext()) {
            char c = current.pop();

            State finalCurrent = current;
            current = divideSingleQuotes(finalCurrent, c)
                    .or(() -> divideDoubleQuotes(finalCurrent, c))
                    .orElseGet(() -> divider.apply(finalCurrent, c));
        } */
	return current.advance().segments();
}
/* private static */Option__State__ divideDoubleQuotes(State state, char c){if (1) {
}

	State current = state.append(c);/* 
        while (current.hasNext()) {
            char popped = current.pop();
            current = current.append(popped);

            if (popped == '\\') current = current.popAndAppend();
            if (popped == '"') break;
        } */
	return Temp();
}
/* private static */Option__State__ divideSingleQuotes(State current, char c){if (1) {
}

	State appended = current.append(c);
	char maybeEscape = current.pop();
	State withNext = appended.append(maybeEscape);
	State appended1 = maybeEscape == /*  '\\' ? withNext */.popAndAppend() : withNext;
	return Temp();
}
/* private static */State divideStatementChar(State state, char c){
	State appended = state.append(c);if (1) {
}
if (1) {
}
if (1) {
}
if (1) {
}

	return appended;
}
/* private static */Option__String__ compileRootSegment(String input){
	String stripped = input.strip();if (1) {
}
if (1) {
}
if (1) {
}

	Option__String__ maybeClass = compileTypedBlock(input, "class ", Lists.of(Lists.empty()));if (1) {
}

	return Temp();
}
/* private static */Option__String__ compileTypedBlock(String input, String keyword, List___List___String____ typeParams){
	int classIndex = input.indexOf(keyword);if (1) {
}

	String modifiers = input.substring(0, classIndex).strip();
	String afterKeyword = input.substring(classIndex + keyword.length());
	int contentStart = afterKeyword.indexOf("{");if (1) {
}

	String beforeContent = afterKeyword.substring(0, contentStart).strip();
	int permitsIndex = beforeContent.indexOf("permits");
	String withoutPermits = /* permitsIndex >= 0
                ? beforeContent */.substring(0, permitsIndex).strip()
                : beforeContent;
	int paramStart = withoutPermits.indexOf("(");
	String withoutPermits1 = /* paramStart >= 0
                ? withoutPermits */.substring(0, paramStart)
                : withoutPermits;
	String body = afterKeyword.substring(contentStart + "{".length()).strip();
	return compileGenericTypedBlock(/* withoutPermits1 */, modifiers, body, typeParams).or(() - /* > {
            return compileToStruct(modifiers, withoutPermits1, body, typeParams, Lists */.empty(), Lists.empty());
        });
}
auto __lambda15__(auto string){
	return String.strip(string);
}
/* private static */Option__String__ compileGenericTypedBlock(String withoutPermits, String modifiers, String body, List___List___String____ typeParams){if (1) {
}

	String withoutEnd = withoutPermits.substring(0, withoutPermits.length() - ">".length());
	int genStart = withoutEnd.indexOf("<");if (1) {
}

	String name = withoutEnd.substring(0, genStart);
	String substring = withoutEnd.substring(genStart + "<".length());
	List___String__ finalClassTypeParams = Lists.of(substring.split(Pattern.quote(","))).stream().map(__lambda15__).collect(Temp());
	/* generators.put(name, typeArguments -> {
            */String joined = /*  generateGenericName(name, typeArguments);
            return compileToStruct(modifiers, joined, body, typeParams, finalClassTypeParams, typeArguments);
        }) */;
	return Temp();
}
auto __lambda16__(auto outputContent){
	return generateStruct(modifiers, name, outputContent);
}
/* private static */Option__String__ compileToStruct(String modifiers, String name, String body, List___List___String____ outerTypeParams, List___String__ innerTypeParams, List___String__ typeArguments){
	List___List___String____ merged = outerTypeParams.add(innerTypeParams);if (1) {
}

	String inputContent = body.substring(0, body.length() - "}".length());
	return compileStatements(inputContent, /* input1  */ - /* > compileClassSegment */(/* input1 */, merged, typeArguments)).map(__lambda16__);
}
/* private static */String generateStruct(String modifiers, String name, String content){
	String modifiersString = modifiers.isEmpty() ? "" : generatePlaceholder(modifiers) + " ";
	String generated = modifiersString + "struct " + name + " {" +
                content +
                "\n};\n";
	/* structs */ = structs.add(generated);
	return "";
}
/* private static */String invalidate(String type, String input){
	System.err.println("Invalid " + type + ": " + input);
	return generatePlaceholder(input);
}
/* private static */Option__String__ compileInitializationStatement(String input, List___List___String____ typeParams, List___String__ typeArguments){if (1) {
}
/*  else {
            return new None<>();
        } */
}
auto __lambda17__(auto outputParams){
	return /* {
            return compileDefinition */(header, typeParams, typeArguments).flatMap(definition -> {
                String string = generateInvokable(definition, outputParams);

                if (!withBody.startsWith("{") || !withBody.endsWith("}"))
                    return new Some<>(generateStatement(string));

                return compileStatements(withBody.substring(1, withBody.length() - 1), /* input1  */ - /* > compileStatement */(/* input1 */, typeParams, typeArguments)).map(statement -> {
                    return addFunction(statement, string);
                });
            });
        };
}
/* private static */Option__String__ compileMethod(String input, List___List___String____ typeParams, List___String__ typeArguments){
	int paramStart = input.indexOf("(");if (1) {
}

	String header = input.substring(0, paramStart).strip();
	String withParams = input.substring(paramStart + "(".length());
	int paramEnd = withParams.indexOf(")");if (1) {
}

	String paramString = withParams.substring(0, paramEnd);
	String withBody = withParams.substring(paramEnd + ")".length()).strip();
	return compileValues(paramString, /* input1  */ - /* > compileDefinition */(/* input1 */, typeParams, typeArguments)).flatMap(__lambda17__);
}
/* private static */String addFunction(String content, String string){
	String function = string + "{" + content + "\n}\n";
	/* functions */ = functions.add(function);
	return "";
}
/* private static */String generateInvokable(String definition, String params){
	return definition + "(" + params + ")";
}
auto __lambda18__(auto main){
	return Main.divideValueChar(main);
}
auto __lambda19__(auto main){
	return Main.mergeValues(main);
}
/* private static */Option__String__ compileValues(String input, Option__String__ (*)(String) compiler){
	return compileAll(divideAll(input, __lambda18__), compiler, __lambda19__);
}
/* private static */State divideValueChar(State state, Character c){if (1) {
}

	State appended = state.append(c);if (1) {
}
if (1) {
}
if (1) {
}

	return appended;
}
/* private static */StringBuilder mergeValues(StringBuilder buffer, String element){if (1) {
}

	return buffer.append(", ").append(element);
}
/* private static */Option__String__ compileStatement(String input, List___List___String____ typeParams, List___String__ typeArguments){
	String stripped = input.strip();if (1) {
}
if (1) {
}
if (1) {
}
if (1) {
}

	return Temp();
}
auto __lambda20__(auto outputDefinition){
	return /* {
            return compileValue */(inputValue).map(outputValue -> {
                return generateStatement(outputDefinition + " = " + outputValue);
            });
        };
}
/* private static */Option__String__ compileInitialization(String withoutEnd, List___List___String____ typeParams, List___String__ typeArguments){
	int separator = withoutEnd.indexOf("=");if (1) {
}

	String inputDefinition = withoutEnd.substring(0, separator);
	String inputValue = withoutEnd.substring(separator + "=".length());
	return compileDefinition(inputDefinition, typeParams, typeArguments).flatMap(__lambda20__);
}
/* private static */String generateStatement(String value){
	return "\n\t" + value + ";";
}
/* private static */Option__String__ compileValue(String input){
	String stripped = input.strip();if (1) {
}
if (1) {
}
if (1) {
}

	int arrowIndex = stripped.indexOf("->");if (1) {
}

	Option__String__ maybeInvocation = compileInvocation(stripped);if (1) {
}

	int dataSeparator = stripped.lastIndexOf(".");if (1) {
}

	int methodSeparator = stripped.lastIndexOf("::");if (1) {
}

	return compileOperator(input, "+").or(() - /* > compileOperator */(input, "-")).or(() - /* > compileOperator */(input, "==")).or(() - /* > isSymbol(stripped) ? new Some<>(stripped) : new None<> */()).or(() - /* > isNumber(stripped) ? new Some<>(stripped) : new None<> */()).or(() - /* > new Some<> */(invalidate("value", input)));
}
auto __lambda21__(auto tuple){
	return tuple.left + " " + operator + " " + tuple.right;
}
/* private static */Option__String__ compileOperator(String input, String operator){
	int operatorIndex = input.indexOf(operator);if (1) {
}

	String left = input.substring(0, operatorIndex);
	String right = input.substring(operatorIndex + operator.length());
	return compileValue(left).and(() - /* > compileValue */(right)).map(__lambda21__);
}
/* private static */int isNumber(String stripped){for (;;) {
}

	return true;
}
/* private static */String generateLambda(String paramName, String lambdaValue){
	String lambda = "__lambda" + lambdaCounter + "__";/* 
        lambdaCounter++; */
	String definition = generateDefinition("", "auto", lambda);
	String param = generateDefinition("", "auto", paramName);
	addFunction("\n\treturn " + lambdaValue + ";", generateInvokable(definition, param));
	return lambda;
}
auto __lambda22__(auto outputValues){
	return /* {
            return compileValue */(inputCaller).map(outputCaller -> {
                return generateInvocation(outputCaller, outputValues);
            });
        };
}
auto __lambda23__(auto main){
	return Main.compileValue(main);
}
/* private static */Option__String__ compileInvocation(String stripped){if (1) {
}

	String withoutEnd = stripped.substring(0, stripped.length() - ")".length());
	int argsStart =  - 1;
	int depth = 0;for (;;) {
}
if (1) {
}

	String inputCaller = withoutEnd.substring(0, argsStart);
	String inputArguments = withoutEnd.substring(argsStart + 1);
	return compileValues(inputArguments, __lambda23__).flatMap(__lambda22__);
}
/* private static */String generateInvocation(String caller, String arguments){
	return caller + "(" + arguments + ")";
}
/* private static */Option__String__ compileDefinition(String input, List___List___String____ typeParams, List___String__ typeArguments){
	String stripped = input.strip();if (1) {
}

	int nameSeparator = stripped.lastIndexOf(" ");if (1) {
}

	return Temp();
}
/* private static */String generateDefinition(String modifiers, String type, String name){
	return modifiers + type + " " + name;
}
/* private static */Option__String__ compileType(String type, List___List___String____ frames, List___String__ typeArguments){
	String stripped = type.strip();if (1) {
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
auto __lambda24__(auto string){
	return String.equals(string);
}
auto __lambda25__(auto string){
	return String.equals(string);
}
/* private static */int isDefined(List___Tuple__String_List___String______ toExpand, Tuple__String_List___String____ tuple){
	return Lists.contains(toExpand, tuple, (stringListTuple, /*  stringListTuple2 */) - /* > Tuples */.equalsTo(stringListTuple, /*  stringListTuple2 */, __lambda24__, (typeParams, /*  typeParams2 */) - /* > Lists */.equalsTo(typeParams, /*  typeParams2 */, __lambda25__)));
}
/* private static */String generateGenericName(String base, List___String__ newArguments){
	String joined = newArguments.stream().collect(Temp()).orElse("");
	return base + "__" + String.join("_", joined) + "__";
}
auto __lambda26__(auto list_){
	return List_.stream(list_);
}
/* private static */int hasNoTypeParams(List___List___String____ frames){
	Option__String__ next = frames.stream().flatMap(__lambda26__).next();
	return next.isEmpty();
}
auto __lambda27__(auto frame){
	return /* {
            return Lists */.contains(frame, stripped, String::equals);
        };
}
/* private static */int isTypeParam(List___List___String____ frames, String stripped){
	return frames.stream().anyMatch(__lambda27__);
}
/* private static */String generateFunctionalType(String returns, List___String__ newArguments){
	String joined = newArguments.stream().collect(Temp()).orElse("");
	return returns + " (*)(" + joined + ")";
}
/* private static */int isSymbol(String input){if (1) {
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
