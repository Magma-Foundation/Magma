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
/* private */ struct Joiner {
};
/* private static Option<String> compileClassSegment(String input, List_<List_<String>> typeParams, List_<String> typeArguments) {
        if (input.isBlank()) return new Some<>("");

        Option<String> maybeInterface = compileTypedBlock(input, " */ struct ", typeParams);
        if  {
	/* globals *//* = */ maybeAssignment.map(/* value *//* -> value + */ ";\n");
	/* return */new Some<>(/* "" */);
	/* }

 *//* return new */ Some<>(/* invalidate("class *//* segment", */ input);
};
/* public */ struct Main {
	/* private *//* static final Map<String, Function<List_<String>, Option<String>>> generators = new */ HashMap<>();
	/* private *//* static final List_<Tuple<String, List_<String>>> expanded = */ Lists.empty();
	/* private *//* static List_<String> imports = */ Lists.empty();
	/* private *//* static List_<String> structs = */ Lists.empty();
	/* private *//* static List_<String> functions = */ Lists.empty();
	/* private *//* static List_<Tuple<String, List_<String>>> toExpand = */ Lists.empty();
	/* private *//* static List_<String> globals = */ Lists.empty();
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
};
/* private */ struct Tuple__A_B__ {
};
/* private */ struct Tuple__String_/* List_<String> */__ {
};

	/* private *//* static int */ lambdaCounter = /*  0; */;;
/* private *//* static final class RangeHead implements Head<Integer> {
        private final int size;
        private int counter = 0;

        private */ RangeHead(int size){
	/* this.size */ = size;/* 
        }

        @Override
        public Option<Integer> next() {
            if (counter < size) {
                int value = counter;
                counter++;
                return new Some<>(value);
            } *//*  else {
                return new None<>();
            } *//* 
        }
     */
}
auto __lambda0__(auto child){
	return equator.apply(element;
}
auto __lambda1__(auto index){
	return equator.apply(elements.get(index);
}
auto __lambda2__(auto tuple){
	return Tuple.left(tuple);
}
auto __lambda3__(auto tuple){
	return equator.apply(tuple.right;
}
/* private *//* static class Lists {
        public static <T> List_<T> */ empty(){
	/* return new JavaList<> */();
	/* }

        public static <T> List_<T> of(T */... elements) {
            return new JavaList<>(Arrays.asList(elements));
	/* }

        public static <T> boolean contains(List_<T> list, T element, BiFunction<T, T, Boolean> equator) {
            return list */.stream().anyMatch(__lambda0__, /*  child) */);
	/* }

 *//* public static <T> boolean equalsTo(List_<T> elements, List_<T> other, BiFunction<T, T, Boolean> equator) {
            if (elements.size() */ ! = other.size()) return false;
	/* return new HeadedStream<> */(/* new RangeHead */(elements.size())).allMatch(__lambda1__, other.get(index)));
	/* }

        public static <T> Option<Integer> indexOf(List_<T> list, T element, BiFunction<T, T, Boolean> equator) {
            return list */.streamWithIndices().filter(__lambda3__, /*  element) */).next().map(__lambda2__);/* 
        }
     */
}
/* private *//* static class State {
        private final List_<Character> queue;
        private List_<String> segments;
        private StringBuilder buffer;
        private int depth;

        private */ State(/* List_<Character> */ queue){
	this(queue, Lists.empty(), /* new StringBuilder */(), /*  0 */);
	/* }

 *//* private State(List_<Character> queue, List_<String> segments, StringBuilder buffer, int depth) { */ this.queue = queue;
	/* this.segments */ = segments;
	/* this.buffer */ = buffer;
	/* this.depth */ = depth;
	/* }

        private State popAndAppend() {
            return append */(pop());
	/* }

        private boolean hasNext() {
            return !queue */.isEmpty();
	/* }

 *//* private State enter() { */ this.depth = /*  depth + 1 */;
	return this;
	/* }

 *//* private State exit() { */ this.depth = /*  depth - 1 */;
	return this;
	/* }

        private State append(char c) {
            buffer */.append(c);
	return this;
	/* }

 *//* private State advance() { */ segments = segments.add(buffer.toString());
	/* this.buffer */ = /* new StringBuilder */();
	return this;
	/* }

 *//* private boolean isLevel() {
            return */ depth = /* = 0 */;
	/* }

        private char pop() {
            return queue */.popFirst();
	/* }

 *//* private boolean isShallow() {
            return */ depth = /* = 1 */;/* 
        }

        public List_<String> segments() {
            return segments; *//* 
        }
     */
}
/* private *//* static final class None<T> implements Option<T> {
        @Override
        public void */ ifPresent(void (*)(T) ifPresent){
	/* }

        @Override
        public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
            return new None<> */();
	/* }

        @Override
        public <R> Option<R> map(Function<T, R> mapper) {
            return new None<> */();/* 
        }

        @Override
        public T orElse(T other) {
            return other; *//* 
        }

        @Override
        public boolean isPresent() {
            return false; */
	/* }

        @Override
        public Tuple<Boolean, T> toTuple(T other) {
            return new Tuple<> */(false, other);
	/* }

        @Override
        public T orElseGet(Supplier<T> other) {
            return other */.get();
	/* }

        @Override
        public Option<T> or(Supplier<Option<T>> other) {
            return other */.get();/* 
        }

        @Override
        public boolean isEmpty() {
            return true; *//* 
        }
     */
}
/* private *//* static class EmptyHead<T> implements Head<T> {
        @Override
        public Option<T> */ next(){
	/* return new None<> */();/* 
        }
     */
}
auto __lambda4__(auto value){
	return value.charAt(value);
}
auto __lambda5__(auto emptyhead){
	return EmptyHead.new(emptyhead);
}
auto __lambda6__(auto singlehead){
	return SingleHead.new(singlehead);
}
/* private *//* static class Streams {
        public static Stream_<Character> */ from(String value){
	/* return new HeadedStream<> */(/* new RangeHead */(value.length())).map(__lambda4__);
	/* }

        public static <T> Stream_<T> empty() {
            return new HeadedStream<> */(/* new EmptyHead<> */());
	/* }

        public static <T> Stream_<T> fromOption(Option<T> option) {
            return new HeadedStream<> */(option.<Head<T>>map(__lambda6__).orElseGet(__lambda5__));/* 
        }
     */
}
/* private *//* static class ListCollector<T> implements Collector<T, List_<T>> {
        @Override
        public List_<T> */ createInitial(){
	/* return Lists */.empty();
	/* }

        @Override
        public List_<T> fold(List_<T> current, T element) {
            return current */.add(element);/* 
        }
     */
}
/* @Override
 *//* public Option<String> */ createInitial(){
	/* return new None<> */();
}
auto __lambda7__(auto inner){
	return /*  inner + delimiter + element */;
}
/* @Override
 *//* public Option<String> */ fold(Option__String__ current, String element){
	/* return new Some<> */(current.map(__lambda7__).orElse(element));
}
/* private *//* static class Tuples {
        public static <A, B> boolean */ equalsTo(Tuple__A_B__ left, Tuple__A_B__ right, int (*)(A, A) leftEquator, int (*)(B, B) rightEquator){
	/* return leftEquator */.apply(left.left, right.left) &&
                    rightEquator.apply(left.right, right.right);/* 
        }
     */
}
/* private *//* static class SingleHead<T> implements Head<T> {
        private final T value;
        private boolean retrieved = false;

        public */ SingleHead(T value){
	/* this.value */ = value;
	/* }

        @Override
        public Option<T> next() {
            if (retrieved) return new None<> */();
	/* retrieved */ = true;
	/* return new Some<> */(value);/* 
        }
     */
}
auto __lambda8__(auto throwable){
	return Throwable.printStackTrace(throwable);
}
auto __lambda9__(auto input){
	return /*  runWithInput(source */;
}
auto __lambda10__(auto /* input), some */){
	return /* input), Some */.new(/* input), some */);
}
/* public *//* static void */ main(String* args){
	Path source = Paths.get(".", "src", "java", "magma", "Main.java");
	readString(source).match(__lambda9__, __lambda10__).ifPresent(__lambda8__);
}
/* private *//* static Option<IOException> */ runWithInput(Path source, String input){
	String output = /*  compile(input) + "int main(){\n\t__main__();\n\treturn 0;\n}\n" */;
	Path target = source.resolveSibling("main.c");
	/* return writeString */(target, output);
}
/* private *//* static Option<IOException> */ writeString(Path target, String output){/* 
        try {
            Files.writeString(target, output);
            return new None<>();
        } *//*  catch (IOException e) {
            return new Some<>(e);
        } */
}
/* private *//* static Result<String, IOException> */ readString(Path source){/* 
        try {
            return new Ok<>(Files.readString(source));
        } *//*  catch (IOException e) {
            return new Err<>(e);
        } */
}
auto __lambda11__(auto main){
	return Main.divideStatementChar(main);
}
auto __lambda12__(auto compiled){
	return /*  mergeAll(compiled */;
}
auto __lambda13__(auto main){
	return Main.mergeStatements)(main);
}
auto __lambda14__(auto main){
	return Main.generate(main);
}
auto __lambda15__(auto main){
	return Main.compileRootSegment(main);
}
/* private *//* static String */ compile(String input){
	/* List_<String> */ segments = divideAll(input, __lambda11__);
	/* return parseAll */(segments, __lambda15__).map(__lambda14__).map(__lambda12__, __lambda13__).orElse("");
}
/* private *//* static List_<String> */ generate(/* List_<String> */ compiled){/* 
        while (!toExpand.isEmpty()) {
            Tuple<String, List_<String>> tuple = toExpand.popFirst();
            if (hasAlreadyBeenSeen(tuple)) continue;

            expanded.add(tuple);
            if (generators.containsKey(tuple.left)) {
                Function<List_<String>, Option<String>> generator = generators.get(tuple.left);
                generator.apply(tuple.right);
            } else {
                System.err.println(tuple.left + " is not a generic type");
            }
        } */
	/* return compiled */.addAll(imports).addAll(structs).addAll(globals).addAll(functions);
}
auto __lambda16__(auto string){
	return String.equals(string);
}
auto __lambda17__(auto string){
	return String.equals))(string);
}
/* private *//* static boolean */ hasAlreadyBeenSeen(Tuple__String_/* List_<String> */__ tuple){
	/* return Lists */.contains(expanded, tuple, /* (entry, entry0) -> Tuples */.equalsTo(entry, /*  entry0 */, __lambda16__, /* (list, list0) -> Lists */.equalsTo(list, /*  list0 */, __lambda17__);
}
auto __lambda18__(auto main){
	return Main.divideStatementChar(main);
}
auto __lambda19__(auto main){
	return Main.mergeStatements(main);
}
/* private *//* static Option<String> */ compileStatements(String input, Option__String__ (*)(String) compiler){
	/* return compileAll */(divideAll(input, __lambda18__), compiler, __lambda19__);
}
auto __lambda20__(auto compiled){
	return /*  mergeAll(compiled */;
}
/* private *//* static Option<String> */ compileAll(/* List_<String> */ segments, Option__String__ (*)(String) compiler, StringBuilder (*)(StringBuilder, String) merger){
	/* return parseAll */(segments, compiler).map(__lambda20__, /*  merger) */);
}
/* private *//* static String */ mergeAll(/* List_<String> */ compiled, StringBuilder (*)(StringBuilder, String) merger){
	/* return compiled */.stream().foldWithInitial(/* new StringBuilder */(), merger).toString();
}
auto __lambda21__(auto compiled){
	return compiled.add(compiled);
}
/* private *//* static Option<List_<String>> */ parseAll(/* List_<String> */ segments, Option__String__ (*)(String) compiler){
	/* return segments */.stream().foldToOption(Lists.empty(), /* (compiled, segment) -> compiler */.apply(segment).map(__lambda21__));
}
/* private *//* static StringBuilder */ mergeStatements(StringBuilder output, String str){
	/* return output */.append(str);
}
/* private *//* static List_<String> */ divideAll(String input, State (*)(State, Character) divider){
	/* List_<Character> */ queue = Streams.from(input).collect(/* new ListCollector<> */());
	State current = /* new State */(queue);/* 
        while (current.hasNext()) {
            char c = current.pop();

            State finalCurrent = current;
            current = divideSingleQuotes(finalCurrent, c)
                    .or(() -> divideDoubleQuotes(finalCurrent, c))
                    .orElseGet(() -> divider.apply(finalCurrent, c));
        } */
	/* return current */.advance().segments();
}
/* private *//* static Option<State> */ divideDoubleQuotes(State state, char c){
	/* if *//* (c */ ! = /* '"') return new None<> */();
	State current = state.append(c);/* 
        while (current.hasNext()) {
            char popped = current.pop();
            current = current.append(popped);

            if (popped == '\\') current = current.popAndAppend();
            if (popped == '"') break;
        } */
	/* return new Some<> */(current);
}
/* private *//* static Option<State> */ divideSingleQuotes(State current, char c){
	/* if *//* (c */ ! = /* '\'') return new None<> */();
	State appended = current.append(c);
	char maybeEscape = current.pop();
	State withNext = appended.append(maybeEscape);
	State appended1 = /* maybeEscape == '\\' ? withNext */.popAndAppend() : withNext;
	/* return new Some<> */(/* appended1 */.popAndAppend());
}
/* private *//* static State */ divideStatementChar(State state, char c){
	State appended = state.append(c);
	if (c = /* = ';' && appended */.isLevel()) return appended.advance();
	if (c = /* = '}' && appended */.isShallow()) return appended.advance().exit();
	if (c = /* = '{') return appended */.enter();
	if (c = /* = '}') return appended */.exit();
	return appended;
}
/* private *//* static Option<String> */ compileRootSegment(String input){
	String stripped = input.strip();
	/* if (stripped */.isEmpty()) return new Some<>("");
	/* if (input */.startsWith("package ")) return new Some<>("");/* 

        if (stripped.startsWith("import ")) {
            String value = "#include <temp.h>\n";
            imports = imports.add(value);
            return new Some<>("");
        } */
	Option__String__ maybeClass = compileTypedBlock(input, "class ", Lists.of(Lists.empty()));/* 
        if (maybeClass.isPresent()) return maybeClass; */
	/* return new Some<> */(invalidate("root segment", input));
}
/* private *//* static Option<String> */ compileTypedBlock(String input, String keyword, /* List_<List_<String>> */ typeParams){
	int classIndex = input.indexOf(keyword);
	/* if (classIndex < 0) return new None<> */();
	String modifiers = input.substring(/* 0 */, classIndex).strip();
	String afterKeyword = input.substring(/* classIndex + keyword */.length());
	int contentStart = afterKeyword.indexOf("{");
	/* if (contentStart < 0) return new None<> */();
	String beforeContent = afterKeyword.substring(/* 0 */, contentStart).strip();
	int permitsIndex = beforeContent.indexOf("permits");
	String withoutPermits = /* permitsIndex >= 0
                ? beforeContent */.substring(/* 0 */, permitsIndex).strip()
                : beforeContent;
	int paramStart = withoutPermits.indexOf("(");
	String withoutPermits1 = /* paramStart >= 0
                ? withoutPermits */.substring(0, paramStart)
                : withoutPermits;
	String body = afterKeyword.substring(/* contentStart + "{" */.length()).strip();/* 

        return compileGenericTypedBlock(withoutPermits1, modifiers, body, typeParams).or(() -> {
            return compileToStruct(modifiers, withoutPermits1, body, typeParams, Lists.empty(), Lists.empty());
        } *//* ); */
}
auto __lambda22__(auto string){
	return String.strip(string);
}
/* private *//* static Option<String> */ compileGenericTypedBlock(String withoutPermits, String modifiers, String body, /* List_<List_<String>> */ typeParams){
	/* if (!withoutPermits */.endsWith(">")) return new None<>();
	String withoutEnd = withoutPermits.substring(/* 0 */, withoutPermits.length() - ">".length());
	int genStart = withoutEnd.indexOf("<");
	/* if (genStart < 0) return new None<> */();
	String name = withoutEnd.substring(/* 0 */, genStart);
	String substring = withoutEnd.substring(/* genStart + "<" */.length());
	/* List_<String> */ finalClassTypeParams = Lists.of(substring.split(Pattern.quote(","))).stream().map(__lambda22__).collect(/* new ListCollector<> */());/* 

        generators.put(name, typeArguments -> {
            String joined = generateGenericName(name, typeArguments);
            return compileToStruct(modifiers, joined, body, typeParams, finalClassTypeParams, typeArguments);
        } *//* ); */
	/* return new Some<> */("");
}
auto __lambda23__(auto outputContent){
	return /*  generateStruct(modifiers */;
}
/* private *//* static Option<String> */ compileToStruct(String modifiers, String name, String body, /* List_<List_<String>> */ outerTypeParams, /* List_<String> */ innerTypeParams, /* List_<String> */ typeArguments){
	/* List_<List_<String>> */ merged = outerTypeParams.add(innerTypeParams);
	/* if (!body */.endsWith("}")) return new None<>();
	String inputContent = body.substring(/* 0 */, body.length() - "}".length());
	/* return compileStatements */(inputContent, /*  input1 -> compileClassSegment(input1 */, merged, /*  typeArguments) */).map(__lambda23__, name, /*  outputContent) */);
}
/* private *//* static String */ generateStruct(String modifiers, String name, String content){
	String modifiersString = modifiers.isEmpty() ? "" : generatePlaceholder(modifiers) + " ";
	String generated = /*  modifiersString + "struct " + name + " {" +
                content +
                "\n};\n" */;
	/* structs */ = structs.add(generated);
	return "";
}
/* private *//* static String */ invalidate(String type, String input){
	System.err.println(/* "Invalid " + type + ": " + input */);
	/* return generatePlaceholder */(input);
}
/* private *//* static Option<String> */ compileMethod(String input, /* List_<List_<String>> */ typeParams, /* List_<String> */ typeArguments){
	int paramStart = input.indexOf("(");
	/* if (paramStart < 0) return new None<> */();
	String header = input.substring(/* 0 */, paramStart).strip();
	String withParams = input.substring(paramStart + "(".length());
	int paramEnd = withParams.indexOf(")");
	/* if (paramEnd < 0) return new None<> */();
	String paramString = withParams.substring(/* 0 */, paramEnd);
	String withBody = withParams.substring(paramEnd + ")".length()).strip();/* 

        return compileValues(paramString, input1 -> compileDefinition(input1, typeParams, typeArguments)).flatMap(outputParams -> {
            return compileDefinition(header, typeParams, typeArguments).flatMap(definition -> {
                String string = generateInvokable(definition, outputParams);

                if (!withBody.startsWith("{") || !withBody.endsWith("}"))
                    return new Some<>(generateStatement(string));

                return compileStatements(withBody.substring(1, withBody.length() - 1), input1 -> compileStatement(input1, typeParams, typeArguments)).map(statement -> {
                    return addFunction(statement, string);
                });
            });
        } *//* ); */
}
/* private *//* static String */ addFunction(String content, String string){
	String function = /*  string + "{" + content + "\n}\n" */;
	/* functions */ = functions.add(function);
	return "";
}
/* private *//* static String */ generateInvokable(String definition, String params){
	return /* definition + "(" + params + ")" */;
}
auto __lambda24__(auto main){
	return Main.divideValueChar(main);
}
auto __lambda25__(auto main){
	return Main.mergeValues(main);
}
/* private *//* static Option<String> */ compileValues(String input, Option__String__ (*)(String) compiler){
	/* return compileAll */(divideAll(input, __lambda24__), compiler, __lambda25__);
}
/* private *//* static State */ divideValueChar(State state, Character c){
	if (c = /* = ',' && state */.isLevel()) return state.advance();
	State appended = state.append(c);
	if (c = /* = '(' || c == '<') return appended */.enter();
	if (c = /* = ')' || c == '>') return appended */.exit();
	return appended;
}
/* private *//* static StringBuilder */ mergeValues(StringBuilder buffer, String element){
	/* if (buffer */.isEmpty()) return buffer.append(element);
	/* return buffer */.append(", ").append(element);
}
/* private *//* static Option<String> */ compileStatement(String input, /* List_<List_<String>> */ typeParams, /* List_<String> */ typeArguments){
	String stripped = input.strip();
	/* if (stripped */.isEmpty()) return new Some<>("");/* 

        if (stripped.endsWith(";")) {
            String withoutEnd = stripped.substring(0, stripped.length() - ";".length());

            Option<String> maybeAssignment = compileAssignment(withoutEnd, typeParams, typeArguments);
            if (maybeAssignment.isPresent()) return maybeAssignment;

            Option<String> maybeInvocation = compileInvocation(withoutEnd);
            if (maybeInvocation.isPresent()) return maybeInvocation.map(Main::generateStatement);

            if(withoutEnd.startsWith("return ")) {
                String value = withoutEnd.substring("return ".length());
                return compileValue(value).map(inner -> "return " + inner).map(Main::generateStatement);
            }
        } */
	/* return new Some<> */(invalidate("statement", input));
}
/* private *//* static Option<String> */ compileAssignment(String input, /* List_<List_<String>> */ typeParams, /* List_<String> */ typeArguments){
	int separator = input.indexOf("=");
	/* if (separator < 0) return new None<> */();
	String inputDefinition = input.substring(/* 0 */, separator);
	String inputValue = input.substring(/* separator + "=" */.length());/* 
        return compileDefinition(inputDefinition, typeParams, typeArguments).flatMap(outputDefinition -> {
            return compileValue(inputValue).map(outputValue -> {
                return generateStatement(outputDefinition + " = " + outputValue);
            });
        } *//* ); */
}
/* private *//* static String */ generateStatement(String value){
	return "\n\t" + value + ";";
}
/* private *//* static Option<String> */ compileValue(String input){
	String stripped = input.strip();
	/* if (stripped */.startsWith("\"") && stripped.endsWith("\"")) return new Some<>(stripped);
	int arrowIndex = stripped.indexOf("->");/* 
        if (arrowIndex >= 0) {
            String paramName = stripped.substring(0, arrowIndex).strip();
            String inputValue = stripped.substring(arrowIndex + "->".length());
            if (isSymbol(paramName)) {
                return compileValue(inputValue).map(outputValue -> generateLambda(paramName, outputValue));
            }
        } */
	Option__String__ maybeInvocation = compileInvocation(stripped);/* 
        if (maybeInvocation.isPresent()) return maybeInvocation; */
	int dataSeparator = stripped.lastIndexOf(".");/* 
        if (dataSeparator >= 0) {
            String object = stripped.substring(0, dataSeparator);
            String property = stripped.substring(dataSeparator + ".".length());

            return compileValue(object).map(newObject -> {
                return newObject + "." + property;
            });
        } */
	int methodSeparator = stripped.lastIndexOf("::");/* 
        if (methodSeparator >= 0) {
            String object = stripped.substring(0, methodSeparator);
            String property = stripped.substring(methodSeparator + "::".length());

            return compileValue(object).map(newObject -> {
                String caller = newObject + "." + property;
                String paramName = newObject.toLowerCase();
                return generateLambda(paramName, generateInvocation(caller, paramName));
            });
        } *//* 

        if (isSymbol(stripped)) {
            return new Some<>(stripped);
        } */
	/* return new Some<> */(invalidate("value", input));
}
/* private *//* static String */ generateLambda(String paramName, String lambdaValue){
	String lambda = "__lambda" + lambdaCounter + "__";/* 
        lambdaCounter++; */
	String definition = generateDefinition("", "auto", lambda);
	String param = generateDefinition("", "auto", paramName);
	addFunction("\n\treturn " + lambdaValue + ";", generateInvokable(definition, param));
	return lambda;
}
/* private *//* static Option<String> */ compileInvocation(String stripped){
	/* if (!stripped */.endsWith(")")) return new None<>();
	String withoutEnd = stripped.substring(0, stripped.length() - ")".length());
	int argsStart = /*  -1 */;
	int depth = /*  0 */;
	/* for *//* (int */ i = withoutEnd.length() - 1;
	i > = /*  0 */;/*  i--) {
            char c = withoutEnd.charAt(i);
            if (c == '(' && depth == 0) {
                argsStart = i;
                break;
            } else {
                if (c == ')') depth++;
                if (c == '(') depth--;
            }
        } */
	/* if (argsStart < 0) return new None<> */();
	String inputCaller = withoutEnd.substring(/* 0 */, argsStart);
	String inputArguments = withoutEnd.substring(/* argsStart + 1 */);/* 
        return compileValues(inputArguments, Main::compileValue).flatMap(outputValues -> {
            return compileValue(inputCaller).map(outputCaller -> {
                return generateInvocation(outputCaller, outputValues);
            });
        } *//* ); */
}
/* private *//* static String */ generateInvocation(String caller, String arguments){
	return /* caller + "(" + arguments + ")" */;
}
/* private *//* static Option<String> */ compileDefinition(String input, /* List_<List_<String>> */ typeParams, /* List_<String> */ typeArguments){
	String stripped = input.strip();
	/* if (stripped */.isEmpty()) return new Some<>("");
	int nameSeparator = stripped.lastIndexOf(" ");/* 
        if (nameSeparator >= 0) {
            String beforeName = stripped.substring(0, nameSeparator);

            int typeSeparator = -1;
            int depth = 0;
            for (int i = 0; i < beforeName.length(); i++) {
                char c = beforeName.charAt(i);
                if (c == ' ' && depth == 0) {
                    typeSeparator = i;
                    break;
                } else {
                    if (c == '>') depth++;
                    if (c == '<') depth--;
                }
            }

            String modifiers;
            String inputType;
            if (typeSeparator >= 0) {
                modifiers = generatePlaceholder(beforeName.substring(0, typeSeparator));
                inputType = beforeName.substring(typeSeparator + 1);
            } else {
                modifiers = "";
                inputType = beforeName;
            }

            String name = stripped.substring(nameSeparator + " ".length());

            return compileType(inputType, typeParams, typeArguments).map(outputType -> {
                return generateDefinition(modifiers, outputType, name);
            });
        } */
	/* return new Some<> */(invalidate("definition", stripped));
}
/* private *//* static String */ generateDefinition(String modifiers, String type, String name){
	return /* modifiers + type + " " + name */;
}
auto __lambda26__(auto value){
	return /*  value + "*" */;
}
/* private *//* static Option<String> */ compileType(String type, /* List_<List_<String>> */ frames, /* List_<String> */ typeArguments){
	String stripped = type.strip();/* 

        if (isTypeParam(frames, stripped)) {
            List_<String> last = frames.last();
            return Lists.indexOf(last, stripped, String::equals).map(index -> {
                String argument = typeArguments.get(index);
                return new Some<>(argument);
            }).orElseGet(() -> {
                return new Some<>(stripped);
            });
        } */
	/* if (stripped */.equals("void")) return new Some<>("void");
	/* if (stripped */.equals("boolean") || stripped.equals("Boolean")) return new Some<>("int");
	/* if (stripped */.endsWith("[]"))
            return compileType(stripped.substring(/* 0 */, stripped.length() - "[]".length()), frames, typeArguments).map(__lambda26__);
	/* if (isSymbol(stripped)) return new Some<> */(stripped);/* 

        if (stripped.endsWith(">")) {
            String withoutEnd = stripped.substring(0, stripped.length() - ">".length());
            int genStart = withoutEnd.indexOf("<");
            if (genStart >= 0) {
                String base = withoutEnd.substring(0, genStart).strip();
                if (isSymbol(base)) {
                    String oldArguments = withoutEnd.substring(genStart + "<".length());
                    List_<String> segments = divideAll(oldArguments, Main::divideValueChar);
                    return parseAll(segments, type1 -> compileType(type1, frames, typeArguments)).map(newArguments -> {
                        if (base.equals("Function")) {
                            return generateFunctionalType(newArguments.get(1), Lists.of(newArguments.get(0)));
                        }
                        if (base.equals("BiFunction")) {
                            return generateFunctionalType(newArguments.get(2), Lists.of(newArguments.get(0), newArguments.get(1)));
                        }

                        if (base.equals("Consumer")) {
                            return generateFunctionalType("void", Lists.of(newArguments.get(0)));
                        }

                        if (base.equals("Supplier")) {
                            return generateFunctionalType(newArguments.get(0), Lists.empty());
                        }

                        if (hasNoTypeParams(frames)) {
                            Tuple<String, List_<String>> tuple = new Tuple<>(base, newArguments);
                            if (!Lists.contains(toExpand, tuple, new BiFunction<>() {
                                @Override
                                public Boolean apply(Tuple<String, List_<String>> stringListTuple, Tuple<String, List_<String>> stringListTuple2) {
                                    return Tuples.equalsTo(stringListTuple, stringListTuple2, String::equals, new BiFunction<List_<String>, List_<String>, Boolean>() {
                                        @Override
                                        public Boolean apply(List_<String> typeParams, List_<String> typeParams2) {
                                            return Lists.equalsTo(typeParams, typeParams2, String::equals);
                                        }
                                    });
                                }
                            })) {
                                toExpand = toExpand.add(tuple);
                            }
                        }

                        return generateGenericName(base, newArguments);
                    });
                }
            }
        } */
	/* return new Some<> */(invalidate("type", stripped));
}
/* private *//* static String */ generateGenericName(String base, /* List_<String> */ newArguments){
	String joined = newArguments.stream().collect(/* new Joiner */("_")).orElse("");
	return /* base + "__" + String */.join("_", joined) + "__";
}
auto __lambda27__(auto /* list_ */){
	return /* List_ */.stream(/* list_ */);
}
/* private *//* static boolean */ hasNoTypeParams(/* List_<List_<String>> */ frames){
	Option__String__ next = frames.stream().flatMap(__lambda27__).next();
	/* return next */.isEmpty();
}
/* private *//* static boolean */ isTypeParam(/* List_<List_<String>> */ frames, String stripped){/* 
        return frames.stream().anyMatch(frame -> {
            return Lists.contains(frame, stripped, String::equals);
        } *//* ); */
}
auto __lambda28__(auto /* base + "<" + mergeall(newarguments, main */){
	return /* base + "<" + mergeAll(newArguments, Main */.mergeValues) + ">"(/* base + "<" + mergeall(newarguments, main */);
}
/* private *//* static String */ generateGeneric(String base, /* List_<String> */ newArguments){
	return __lambda28__;
}
/* private *//* static String */ generateFunctionalType(String returns, /* List_<String> */ newArguments){
	String joined = newArguments.stream().collect(/* new Joiner */(", ")).orElse("");
	return /* returns + " (*)(" + joined + ")" */;
}
/* private *//* static boolean */ isSymbol(String input){
	/* for *//* (int */ i = /*  0 */;
	/* i < input */.length();/*  i++) {
            char c = input.charAt(i);
            if (!Character.isLetter(c)) {
                return false;
            }
        } */
	return true;
}
/* private *//* static String */ generatePlaceholder(String input){
	return "/* " + input + " */";
}
int main(){
	__main__();
	return 0;
}
