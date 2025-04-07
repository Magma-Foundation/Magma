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
struct Temp {
};
struct Temp {
};
struct Temp {
};
struct Temp {
};
struct Temp {
};
struct Temp {
};
struct Temp {
};
/* private static Option<String> compileClassSegment(String input, List_<List_<String>> typeParams, List_<String> typeArguments) {
        if (input.isBlank()) return new Some<>("");

        Option<String> maybeInterface = compileTypedBlock(input, " */ struct ", typeParams);
        if (maybeInterface.isPresent()) return maybeInterface;

        int recordIndex = input.indexOf("record ");
        if (recordIndex >= 0) {
	/* return */struct new Some<>(/* generateStruct("", *//* "Temp", */ "");
	/* }

 *//* Option<String> maybeMethod = */ compileMethod(/* input */, /* typeParams */, /* typeArguments */);
	/* return */struct new Some<>(/* "" */);
	/* }

 *//* return new */ Some<>(/* invalidate("class *//* segment", */ input);
};
/* public */ struct Main {
	/* private *//* static final Map<String, Function<List_<String>, Option<String>>> generators = new */ HashMap<>(/*  */);
	/* private *//* static final List_<Tuple<String, List_<String>>> expanded = */ Lists.empty(/*  */);
	/* private *//* static List_<String> imports = */ Lists.empty(/*  */);
	/* private *//* static List_<String> structs = */ Lists.empty(/*  */);
	/* private *//* static List_<String> functions = */ Lists.empty(/*  */);
	/* private *//* static List_<Tuple<String, List_<String>>> toExpand = */ Lists.empty(/*  */);
	/* private *//* static List_<String> globals = */ Lists.empty(/*  */);
};
/* private sealed */ struct Option__struct String__ {
	void ifPresent(void (*)(struct String) ifPresent);
	/* <R> */Option__struct R__ flatMap(Option__struct R__ (*)(struct String) mapper);
	/* <R> */Option__struct R__ map(struct R (*)(struct String) mapper);
	struct String orElse(struct String other);
	int isPresent(/*  */);
	Tuple__int_struct String__ toTuple(struct String other);
	struct String orElseGet(struct String (*)() other);
	Option__struct String__ or(Option__struct String__ (*)() other);
	int isEmpty(/*  */);
};

	/* private *//* static int */ lambdaCounter = /*  0; */;;
/* private *//* static final class RangeHead implements Head<Integer> {
        private final int size;
        private int counter = 0;

        private */ RangeHead(struct int size){
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
        public static <T> List_<T> */ empty(/*  */){
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

 *//* private State enter() { */ this.depth = /*  depth + 1 */;/* 
            return this; */
	/* }

 *//* private State exit() { */ this.depth = /*  depth - 1 */;/* 
            return this; */
	/* }

        private State append(char c) {
            buffer */.append(c);/* 
            return this; */
	/* }

 *//* private State advance() { */ segments = segments.add(buffer.toString());
	/* this.buffer */ = /* new StringBuilder */();/* 
            return this; */
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
        public void */ ifPresent(void (*)(struct T) ifPresent){
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
        public Option<T> */ next(/*  */){
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
        public static Stream_<Character> */ from(struct String value){
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
        public List_<T> */ createInitial(/*  */){
	/* return Lists */.empty();
	/* }

        @Override
        public List_<T> fold(List_<T> current, T element) {
            return current */.add(element);/* 
        }
     */
}
/* private *//* static class Tuples {
        public static <A, B> boolean */ equalsTo(Tuple__struct A_struct B__ left, Tuple__struct A_struct B__ right, int (*)(struct A, struct A) leftEquator, int (*)(struct B, struct B) rightEquator){
	/* return leftEquator */.apply(left.left, right.left) &&
                    rightEquator.apply(left.right, right.right);/* 
        }
     */
}
/* private *//* static class SingleHead<T> implements Head<T> {
        private final T value;
        private boolean retrieved = false;

        public */ SingleHead(struct T value){
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
auto __lambda7__(auto throwable){
	return Throwable.printStackTrace(throwable);
}
auto __lambda8__(auto input){
	return /*  runWithInput(source */;
}
auto __lambda9__(auto /* input), some */){
	return /* input), Some */.new(/* input), some */);
}
/* public *//* static void */ main(struct String* args){
	struct Path source = Paths.get(".", "src", "java", "magma", "Main.java");
	readString(source).match(__lambda8__, __lambda9__).ifPresent(__lambda7__);
}
/* private *//* static Option<IOException> */ runWithInput(struct Path source, struct String input){
	struct String output = /*  compile(input) + "int main(){\n\t__main__();\n\treturn 0;\n}\n" */;
	struct Path target = source.resolveSibling("main.c");
	/* return writeString */(target, output);
}
/* private *//* static Option<IOException> */ writeString(struct Path target, struct String output){/* 
        try {
            Files.writeString(target, output);
            return new None<>();
        } *//*  catch (IOException e) {
            return new Some<>(e);
        } */
}
/* private *//* static Result<String, IOException> */ readString(struct Path source){/* 
        try {
            return new Ok<>(Files.readString(source));
        } *//*  catch (IOException e) {
            return new Err<>(e);
        } */
}
auto __lambda10__(auto main){
	return Main.divideStatementChar(main);
}
auto __lambda11__(auto compiled){
	return /*  mergeAll(compiled */;
}
auto __lambda12__(auto main){
	return Main.mergeStatements)(main);
}
auto __lambda13__(auto main){
	return Main.generate(main);
}
auto __lambda14__(auto main){
	return Main.compileRootSegment(main);
}
/* private *//* static String */ compile(struct String input){
	/* List_<String> */ segments = divideAll(input, __lambda10__);
	/* return parseAll */(segments, __lambda14__).map(__lambda13__).map(__lambda11__, __lambda12__).orElse("");
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
auto __lambda15__(auto string){
	return String.equals(string);
}
auto __lambda16__(auto string){
	return String.equals))(string);
}
/* private *//* static boolean */ hasAlreadyBeenSeen(Tuple__struct String_/* List_<String> */__ tuple){
	/* return Lists */.contains(expanded, tuple, /* (entry, entry0) -> Tuples */.equalsTo(entry, /*  entry0 */, __lambda15__, /* (list, list0) -> Lists */.equalsTo(list, /*  list0 */, __lambda16__);
}
auto __lambda17__(auto main){
	return Main.divideStatementChar(main);
}
auto __lambda18__(auto main){
	return Main.mergeStatements(main);
}
/* private *//* static Option<String> */ compileStatements(struct String input, Option__struct String__ (*)(struct String) compiler){
	/* return compileAll */(divideAll(input, __lambda17__), compiler, __lambda18__);
}
auto __lambda19__(auto compiled){
	return /*  mergeAll(compiled */;
}
/* private *//* static Option<String> */ compileAll(/* List_<String> */ segments, Option__struct String__ (*)(struct String) compiler, struct StringBuilder (*)(struct StringBuilder, struct String) merger){
	/* return parseAll */(segments, compiler).map(__lambda19__, /*  merger) */);
}
/* private *//* static String */ mergeAll(/* List_<String> */ compiled, struct StringBuilder (*)(struct StringBuilder, struct String) merger){
	/* return compiled */.stream().foldWithInitial(/* new StringBuilder */(), merger).toString();
}
auto __lambda20__(auto compiled){
	return compiled.add(compiled);
}
/* private *//* static Option<List_<String>> */ parseAll(/* List_<String> */ segments, Option__struct String__ (*)(struct String) compiler){
	/* return segments */.stream().foldToOption(Lists.empty(), /* (compiled, segment) -> compiler */.apply(segment).map(__lambda20__));
}
/* private *//* static StringBuilder */ mergeStatements(struct StringBuilder output, struct String str){
	/* return output */.append(str);
}
/* private *//* static List_<String> */ divideAll(struct String input, struct State (*)(struct State, struct Character) divider){
	/* List_<Character> */ queue = Streams.from(input).collect(/* new ListCollector<> */());
	struct State current = /* new State */(queue);/* 
        while (current.hasNext()) {
            char c = current.pop();

            State finalCurrent = current;
            current = divideSingleQuotes(finalCurrent, c)
                    .or(() -> divideDoubleQuotes(finalCurrent, c))
                    .orElseGet(() -> divider.apply(finalCurrent, c));
        } */
	/* return current */.advance().segments();
}
/* private *//* static Option<State> */ divideDoubleQuotes(struct State state, struct char c){
	/* if *//* (c */ ! = /* '"') return new None<> */();
	struct State current = state.append(c);/* 
        while (current.hasNext()) {
            char popped = current.pop();
            current = current.append(popped);

            if (popped == '\\') current = current.popAndAppend();
            if (popped == '"') break;
        } */
	/* return new Some<> */(current);
}
/* private *//* static Option<State> */ divideSingleQuotes(struct State current, struct char c){
	/* if *//* (c */ ! = /* '\'') return new None<> */();
	struct State appended = current.append(c);
	struct char maybeEscape = current.pop();
	struct State withNext = appended.append(maybeEscape);
	struct State appended1 = /* maybeEscape == '\\' ? withNext */.popAndAppend() : withNext;
	/* return new Some<> */(/* appended1 */.popAndAppend());
}
/* private *//* static State */ divideStatementChar(struct State state, struct char c){
	struct State appended = state.append(c);
	struct if (c = /* = ';' && appended */.isLevel()) return appended.advance();
	struct if (c = /* = '}' && appended */.isShallow()) return appended.advance().exit();
	struct if (c = /* = '{') return appended */.enter();
	struct if (c = /* = '}') return appended */.exit();/* 
        return appended; */
}
/* private *//* static Option<String> */ compileRootSegment(struct String input){
	struct String stripped = input.strip();
	/* if (stripped */.isEmpty()) return new Some<>("");
	/* if (input */.startsWith("package ")) return new Some<>("");/* 

        if (stripped.startsWith("import ")) {
            String value = "#include <temp.h>\n";
            imports = imports.add(value);
            return new Some<>("");
        } */
	Option__struct String__ maybeClass = compileTypedBlock(input, "class ", Lists.of(Lists.empty()));/* 
        if (maybeClass.isPresent()) return maybeClass; */
	/* return new Some<> */(invalidate("root segment", input));
}
/* private *//* static Option<String> */ compileTypedBlock(struct String input, struct String keyword, /* List_<List_<String>> */ typeParams){
	struct int classIndex = input.indexOf(keyword);
	/* if (classIndex < 0) return new None<> */();
	struct String modifiers = input.substring(/* 0 */, classIndex).strip();
	struct String right = input.substring(/* classIndex + keyword */.length());
	struct int contentStart = right.indexOf("{");
	/* if (contentStart < 0) return new None<> */();
	struct String beforeContent = right.substring(/* 0 */, contentStart).strip();
	struct int permitsIndex = beforeContent.indexOf("permits");
	struct String withoutPermits = /* permitsIndex >= 0
                ? beforeContent */.substring(/* 0 */, permitsIndex).strip()
                : beforeContent;
	struct String body = right.substring(/* contentStart + "{" */.length()).strip();/* 

        return compileGenericTypedBlock(withoutPermits, modifiers, body, typeParams).or(() -> {
            return compileToStruct(modifiers, withoutPermits, body, typeParams, Lists.empty(), Lists.empty());
        } *//* ); */
}
auto __lambda21__(auto string){
	return String.strip(string);
}
/* private *//* static Option<String> */ compileGenericTypedBlock(struct String withoutPermits, struct String modifiers, struct String body, /* List_<List_<String>> */ typeParams){
	/* if (!withoutPermits */.endsWith(">")) return new None<>();
	struct String withoutEnd = withoutPermits.substring(/* 0 */, withoutPermits.length() - ">".length());
	struct int genStart = withoutEnd.indexOf("<");
	/* if (genStart < 0) return new None<> */();
	struct String name = withoutEnd.substring(/* 0 */, genStart);
	struct String substring = withoutEnd.substring(/* genStart + "<" */.length());
	/* List_<String> */ finalClassTypeParams = Lists.of(substring.split(Pattern.quote(","))).stream().map(__lambda21__).collect(/* new ListCollector<> */());/* 

        generators.put(name, typeArguments -> {
            String joined = generateGenericName(name, typeArguments);
            return compileToStruct(modifiers, joined, body, typeParams, finalClassTypeParams, typeArguments);
        } *//* ); */
	/* return new Some<> */("");
}
auto __lambda22__(auto outputContent){
	return /*  generateStruct(modifiers */;
}
/* private *//* static Option<String> */ compileToStruct(struct String modifiers, struct String name, struct String body, /* List_<List_<String>> */ outerTypeParams, /* List_<String> */ innerTypeParams, /* List_<String> */ typeArguments){
	/* List_<List_<String>> */ merged = outerTypeParams.add(innerTypeParams);
	/* if (!body */.endsWith("}")) return new None<>();
	struct String inputContent = body.substring(/* 0 */, body.length() - "}".length());
	/* return compileStatements */(inputContent, /*  input1 -> compileClassSegment(input1 */, merged, /*  typeArguments) */).map(__lambda22__, name, /*  outputContent) */);
}
/* private *//* static String */ generateStruct(struct String modifiers, struct String name, struct String content){
	struct String modifiersString = modifiers.isEmpty() ? "" : generatePlaceholder(modifiers) + " ";
	struct String generated = /*  modifiersString + "struct " + name + " {" +
                content +
                "\n};\n" */;
	/* structs */ = structs.add(generated);/* 
        return ""; */
}
/* private *//* static String */ invalidate(struct String type, struct String input){
	System.err.println(/* "Invalid " + type + ": " + input */);
	/* return generatePlaceholder */(input);
}
/* private *//* static Option<String> */ compileMethod(struct String input, /* List_<List_<String>> */ typeParams, /* List_<String> */ typeArguments){
	struct int paramStart = input.indexOf("(");
	/* if (paramStart < 0) return new None<> */();
	struct String header = input.substring(/* 0 */, paramStart).strip();
	struct String withParams = input.substring(paramStart + "(".length());
	struct int paramEnd = withParams.indexOf(")");
	/* if (paramEnd < 0) return new None<> */();
	struct String paramString = withParams.substring(/* 0 */, paramEnd);
	struct String withBody = withParams.substring(paramEnd + ")".length()).strip();/* 

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
/* private *//* static String */ addFunction(struct String content, struct String string){
	struct String function = /*  string + "{" + content + "\n}\n" */;
	/* functions */ = functions.add(function);/* 
        return ""; */
}
/* private *//* static String */ generateInvokable(struct String definition, struct String params){/* 
        return definition + "(" + params + ")"; */
}
auto __lambda23__(auto main){
	return Main.divideValueChar(main);
}
auto __lambda24__(auto main){
	return Main.mergeValues(main);
}
/* private *//* static Option<String> */ compileValues(struct String input, Option__struct String__ (*)(struct String) compiler){
	/* return compileAll */(divideAll(input, __lambda23__), compiler, __lambda24__);
}
/* private *//* static State */ divideValueChar(struct State state, struct Character c){
	struct if (c = /* = ',' && state */.isLevel()) return state.advance();
	struct State appended = state.append(c);
	struct if (c = /* = '(' || c == '<') return appended */.enter();
	struct if (c = /* = ')' || c == '>') return appended */.exit();/* 
        return appended; */
}
/* private *//* static StringBuilder */ mergeValues(struct StringBuilder buffer, struct String element){
	/* if (buffer */.isEmpty()) return buffer.append(element);
	/* return buffer */.append(", ").append(element);
}
/* private *//* static Option<String> */ compileStatement(struct String input, /* List_<List_<String>> */ typeParams, /* List_<String> */ typeArguments){
	struct String stripped = input.strip();
	/* if (stripped */.isEmpty()) return new Some<>("");/* 

        if (stripped.endsWith(";")) {
            String withoutEnd = stripped.substring(0, stripped.length() - ";".length());

            Option<String> maybeAssignment = compileAssignment(withoutEnd, typeParams, typeArguments);
            if (maybeAssignment.isPresent()) return maybeAssignment;

            Option<String> maybeInvocation = compileInvocation(withoutEnd);
            if (maybeInvocation.isPresent()) return maybeInvocation.map(Main::generateStatement);
        } */
	/* return new Some<> */(invalidate("statement", input));
}
/* private *//* static Option<String> */ compileAssignment(struct String input, /* List_<List_<String>> */ typeParams, /* List_<String> */ typeArguments){
	struct int separator = input.indexOf("=");
	/* if (separator < 0) return new None<> */();
	struct String inputDefinition = input.substring(/* 0 */, separator);
	struct String inputValue = input.substring(/* separator + "=" */.length());/* 
        return compileDefinition(inputDefinition, typeParams, typeArguments).flatMap(outputDefinition -> {
            return compileValue(inputValue).map(outputValue -> {
                return generateStatement(outputDefinition + " = " + outputValue);
            });
        } *//* ); */
}
/* private *//* static String */ generateStatement(struct String value){/* 
        return "\n\t" + value + ";"; */
}
/* private *//* static Option<String> */ compileValue(struct String input){
	struct String stripped = input.strip();
	/* if (stripped */.startsWith("\"") && stripped.endsWith("\"")) return new Some<>(stripped);
	struct int arrowIndex = stripped.indexOf("->");/* 
        if (arrowIndex >= 0) {
            String paramName = stripped.substring(0, arrowIndex).strip();
            String inputValue = stripped.substring(arrowIndex + "->".length());
            if (isSymbol(paramName)) {
                return compileValue(inputValue).map(outputValue -> generateLambda(paramName, outputValue));
            }
        } */
	Option__struct String__ maybeInvocation = compileInvocation(stripped);/* 
        if (maybeInvocation.isPresent()) return maybeInvocation; */
	struct int dataSeparator = stripped.lastIndexOf(".");/* 
        if (dataSeparator >= 0) {
            String object = stripped.substring(0, dataSeparator);
            String property = stripped.substring(dataSeparator + ".".length());

            return compileValue(object).map(newObject -> {
                return newObject + "." + property;
            });
        } */
	struct int methodSeparator = stripped.lastIndexOf("::");/* 
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
/* private *//* static String */ generateLambda(struct String paramName, struct String lambdaValue){
	struct String lambda = "__lambda" + lambdaCounter + "__";/* 
        lambdaCounter++; */
	struct String definition = generateDefinition("", "auto", lambda);
	struct String param = generateDefinition("", "auto", paramName);
	addFunction("\n\treturn " + lambdaValue + ";", generateInvokable(definition, param));/* 

        return lambda; */
}
/* private *//* static Option<String> */ compileInvocation(struct String stripped){
	/* if (!stripped */.endsWith(")")) return new None<>();
	struct String withoutEnd = stripped.substring(0, stripped.length() - ")".length());
	struct int argsStart = /*  -1 */;
	struct int depth = /*  0 */;
	/* for *//* (int */ i = withoutEnd.length() - 1;
	struct i > = /*  0 */;/*  i--) {
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
	struct String inputCaller = withoutEnd.substring(/* 0 */, argsStart);
	struct String inputArguments = withoutEnd.substring(/* argsStart + 1 */);/* 
        return compileValues(inputArguments, Main::compileValue).flatMap(outputValues -> {
            return compileValue(inputCaller).map(outputCaller -> {
                return generateInvocation(outputCaller, outputValues);
            });
        } *//* ); */
}
/* private *//* static String */ generateInvocation(struct String caller, struct String arguments){/* 
        return caller + "(" + arguments + ")"; */
}
/* private *//* static Option<String> */ compileDefinition(struct String input, /* List_<List_<String>> */ typeParams, /* List_<String> */ typeArguments){
	struct String stripped = input.strip();
	struct int nameSeparator = stripped.lastIndexOf(" ");/* 
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
/* private *//* static String */ generateDefinition(struct String modifiers, struct String type, struct String name){/* 
        return modifiers + type + " " + name; */
}
auto __lambda25__(auto value){
	return /*  value + "*" */;
}
/* private *//* static Option<String> */ compileType(struct String type, /* List_<List_<String>> */ frames, /* List_<String> */ typeArguments){
	struct String stripped = type.strip();/* 

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
            return compileType(stripped.substring(/* 0 */, stripped.length() - "[]".length()), frames, typeArguments).map(__lambda25__);
	/* if (isSymbol(stripped)) return new Some<> */(/* "struct " + stripped */);/* 
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
/* private *//* static String */ generateGenericName(struct String base, /* List_<String> */ newArguments){
	struct String joined = newArguments.stream().collect(/* new Joiner */("_")).orElse("");/* 
        return base + "__" + String.join("_", joined) + "__"; */
}
auto __lambda26__(auto /* list_ */){
	return /* List_ */.stream(/* list_ */);
}
/* private *//* static boolean */ hasNoTypeParams(/* List_<List_<String>> */ frames){
	Option__struct String__ next = frames.stream().flatMap(__lambda26__).next();
	/* return next */.isEmpty();
}
/* private *//* static boolean */ isTypeParam(/* List_<List_<String>> */ frames, struct String stripped){/* 
        return frames.stream().anyMatch(frame -> {
            return Lists.contains(frame, stripped, String::equals);
        } *//* ); */
}
/* private *//* static String */ generateGeneric(struct String base, /* List_<String> */ newArguments){/* 
        return base + "<" + mergeAll(newArguments, Main::mergeValues) + ">"; */
}
/* private *//* static String */ generateFunctionalType(struct String returns, /* List_<String> */ newArguments){
	struct String joined = newArguments.stream().collect(/* new Joiner */(", ")).orElse("");/* 
        return returns + " (*)(" + joined + ")"; */
}
/* private *//* static boolean */ isSymbol(struct String input){
	/* for *//* (int */ i = /*  0 */;
	/* i < input */.length();/*  i++) {
            char c = input.charAt(i);
            if (!Character.isLetter(c)) {
                return false;
            }
        } *//* 
        return true; */
}
/* private *//* static String */ generatePlaceholder(struct String input){/* 
        return "/* " + input + " */"; */
}
int main(){
	__main__();
	return 0;
}
