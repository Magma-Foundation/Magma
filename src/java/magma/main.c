/* <R> R match(Function<T, R> whenOk, Function<X, R> whenErr); *//* 
     *//* void ifPresent(Consumer<T> ifPresent); *//* 

        <R> Option<R> flatMap(Function<T, Option<R>> mapper); *//* 

        <R> Option<R> map(Function<T, R> mapper); *//* 

        T orElse(T other); *//* 

        boolean isPresent(); *//* 

        Tuple<Boolean, T> toTuple(T other); *//* 

        T orElseGet(Supplier<T> other); *//* 

        Option<T> or(Supplier<Option<T>> other); *//* 
     *//* List_<T> add(T element); *//* 

        List_<T> addAll(List_<T> elements); *//* 

        void forEach(Consumer<T> consumer); *//* 

        Stream_<T> stream(); *//* 

        T popFirst(); *//* 

        boolean isEmpty(); *//* 
     *//* <R> Stream_<R> map(Function<T, R> mapper); *//* 

        <R> R foldWithInitial(R initial, BiFunction<R, T, R> folder); *//* 

        <C> C collect(Collector<T, C> collector); *//* 

        <R> Option<R> foldToOption(R initial, BiFunction<R, T, Option<R>> folder); *//* 
     *//* C createInitial(); *//* 

        C fold(C current, T element); *//* 
     *//* Option<T> next(); *//* 
     *//* 
 */#include <temp.h>
#include <temp.h>
#include <temp.h>
#include <temp.h>
#include <temp.h>
#include <temp.h>
#include <temp.h>
#include <temp.h>
#include <temp.h>
#include <temp.h>
/* private sealed */ struct Result<T, X> permits Ok, Err {
};
/* private sealed */ struct Option<T> permits Some, None {
};
/* private */ struct List_<T> {
};
/* private */ struct Stream_<T> {
};
/* private */ struct Collector<T, C> {
};
/* private */ struct Head<T> {
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
/* public */ struct Main {
};

	/* private static final *//* List_<String> */ imports = Lists.empty();;;

	/* private static final *//* List_<String> */ structs = Lists.empty();;;

	/* private static final *//* List_<String> */ functions = Lists.empty();;;

	/* private static *//* List_<String> */ globals = Lists.empty();;;

	/* private static */struct int lambdaCounter = /*  0; */;;
/* private record *//* Tuple<A, */ B>(struct A left, struct B right){
}
/* private static final class RangeHead implements Head<Integer> {
        private final int size;
        private int counter = 0;

        */struct private RangeHead(struct int size){
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
/* private static class Lists {
        public static <T> *//* List_<T> */ empty(/*  */){
	/* return new JavaList<> */();/* 
        }
     */
}
/* private static class State {
        private final List_<Character> queue;
        private final List_<String> segments;
        private StringBuilder buffer;
        private int depth;

        */struct private State(/* List_<Character> */ queue){
	this(queue, Lists.empty(), /* new StringBuilder */(), /*  0 */);
	/* }

        private State(List_<Character> queue, List_<String> segments, StringBuilder buffer, int depth) {
           */struct  this.queue = queue;
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

        private State enter() {
           */struct  this.depth = /*  depth + 1 */;/* 
            return this; */
	/* }

        private State exit() {
           */struct  this.depth = /*  depth - 1 */;/* 
            return this; */
	/* }

        private State append(char c) {
            buffer */.append(c);/* 
            return this; */
	/* }

        private State advance() {
            segments */.add(buffer.toString());
	/* this.buffer */ = /* new StringBuilder */();/* 
            return this; */
	/* }

        private boolean isLevel() {
            */struct return depth = /* = 0 */;
	/* }

        private char pop() {
            return queue */.popFirst();
	/* }

        private boolean isShallow() {
            */struct return depth = /* = 1 */;/* 
        }

        public List_<String> segments() {
            return segments; *//* 
        }
     */
}
/* private static final class None<T> implements Option<T> {
        @Override
        public */void ifPresent(/* Consumer<T> */ ifPresent){
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
     */
}
auto __lambda0__(auto value){
	return value.charAt(value);
}
/* private static class Streams {
        public static *//* Stream_<Character> */ from(struct String value){
	/* return new HeadedStream<> */(/* new RangeHead */(value.length())).map(__lambda0__);/* 
        }
     */
}
/* private static class ListCollector<T> implements Collector<T, List_<T>> {
        @Override
        public *//* List_<T> */ createInitial(/*  */){
	/* return Lists */.empty();
	/* }

        @Override
        public List_<T> fold(List_<T> current, T element) {
            return current */.add(element);/* 
        }
     */
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
/* public static */void main(struct String* args){
	struct Path source = Paths.get(".", "src", "java", "magma", "Main.java");
	readString(source).match(__lambda2__, __lambda3__).ifPresent(__lambda1__);
}
/* private static *//* Option<IOException> */ runWithInput(struct Path source, struct String input){
	struct String output = /*  compile(input) + "int main(){\n\t__main__();\n\treturn 0;\n}\n" */;
	struct Path target = source.resolveSibling("main.c");
	/* return writeString */(target, output);
}
/* private static *//* Option<IOException> */ writeString(struct Path target, struct String output){/* 
        try {
            Files.writeString(target, output);
            return new None<>();
        } *//*  catch (IOException e) {
            return new Some<>(e);
        } */
}
/* private static Result<String, *//* IOException> */ readString(struct Path source){/* 
        try {
            return new Ok<>(Files.readString(source));
        } *//*  catch (IOException e) {
            return new Err<>(e);
        } */
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
/* private static */struct String compile(struct String input){
	/* List_<String> */ segments = divideAll(input, __lambda4__);/* 
        return parseAll(segments, Main::compileRootSegment)
                .map(compiled -> {
                    compiled.addAll(imports);
                    compiled.addAll(structs);
                    compiled.addAll(globals);
                    compiled.addAll(functions);
                    return compiled;
                } */
	/* )
                 */.map(__lambda6__).orElse("");
}
auto __lambda7__(auto main){
	return Main.divideStatementChar(main);
}
auto __lambda8__(auto main){
	return Main.mergeStatements(main);
}
/* private static *//* Option<String> */ compileStatements(struct String input, /* Function<String */, /* Option<String>> */ compiler){
	/* return compileAll */(divideAll(input, __lambda7__), compiler, __lambda8__);
}
auto __lambda9__(auto compiled){
	return mergeAll(compiled, merger);
}
/* private static *//* Option<String> */ compileAll(/* List_<String> */ segments, /* Function<String */, /* Option<String>> */ compiler, /* BiFunction<StringBuilder */, /* String */, /* StringBuilder> */ merger){
	/* return parseAll */(segments, compiler).map(__lambda9__);
}
/* private static */struct String mergeAll(/* List_<String> */ compiled, /* BiFunction<StringBuilder */, /* String */, /* StringBuilder> */ merger){
	/* return compiled */.stream().foldWithInitial(/* new StringBuilder */(), merger).toString();
}
auto __lambda10__(auto compiled){
	return compiled.add(compiled);
}
/* private static *//* Option<List_<String>> */ parseAll(/* List_<String> */ segments, /* Function<String */, /* Option<String>> */ compiler){
	/* return segments */.stream().foldToOption(Lists.empty(), /* (compiled, segment) -> compiler */.apply(segment).map(__lambda10__));
}
/* private static */struct StringBuilder mergeStatements(struct StringBuilder output, struct String str){
	/* return output */.append(str);
}
/* private static *//* List_<String> */ divideAll(struct String input, /* BiFunction<State */, /* Character */, /* State> */ divider){
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
/* private static *//* Option<State> */ divideDoubleQuotes(struct State state, struct char c){
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
/* private static *//* Option<State> */ divideSingleQuotes(struct State current, struct char c){
	/* if *//* (c */ ! = /* '\'') return new None<> */();
	struct State appended = current.append(c);
	struct char maybeEscape = current.pop();
	struct State withNext = appended.append(maybeEscape);
	struct State appended1 = /* maybeEscape == '\\' ? withNext */.popAndAppend() : withNext;
	/* return new Some<> */(/* appended1 */.popAndAppend());
}
/* private static */struct State divideStatementChar(struct State state, struct char c){
	struct State appended = state.append(c);
	struct if (c = /* = ';' && appended */.isLevel()) return appended.advance();
	struct if (c = /* = '}' && appended */.isShallow()) return appended.advance().exit();
	struct if (c = /* = '{') return appended */.enter();
	struct if (c = /* = '}') return appended */.exit();/* 
        return appended; */
}
/* private static *//* Option<String> */ compileRootSegment(struct String input){
	struct String stripped = input.strip();
	/* if (stripped */.isEmpty()) return new Some<>("");
	/* if (input */.startsWith("package ")) return new Some<>("");/* 

        if (stripped.startsWith("import ")) {
            String value = "#include <temp.h>\n";
            imports.add(value);
            return new Some<>("");
        } */
	/* Option<String> */ maybeClass = compileTypedBlock(input, "class ");/* 
        if (maybeClass.isPresent()) return maybeClass; */
	/* return new Some<> */(invalidate("root segment", input));
}
auto __lambda11__(auto outputContent){
	return /*  generateStruct(modifiers, name) + outputContent */;
}
auto __lambda12__(auto main){
	return Main.compileClassSegment(main);
}
/* private static *//* Option<String> */ compileTypedBlock(struct String input, struct String keyword){
	struct int classIndex = input.indexOf(keyword);
	/* if (classIndex < 0) return new None<> */();
	struct String modifiers = input.substring(/* 0 */, classIndex).strip();
	struct String right = input.substring(/* classIndex + keyword */.length());
	struct int contentStart = right.indexOf("{");
	/* if (contentStart < 0) return new None<> */();
	struct String name = right.substring(/* 0 */, contentStart).strip();
	struct String body = right.substring(/* contentStart + "{" */.length()).strip();
	/* if (!body */.endsWith("}")) return new None<>();
	struct String inputContent = body.substring(/* 0 */, body.length() - "}".length());
	/* return compileStatements */(inputContent, __lambda12__).map(__lambda11__);
}
/* private static */struct String generateStruct(struct String modifiers, struct String name){
	struct String modifiersString = modifiers.isEmpty() ? "" : generatePlaceholder(modifiers) + " ";
	struct String generated = /*  modifiersString + "struct " + name + " {\n};\n" */;
	structs.add(generated);/* 
        return ""; */
}
/* private static */struct String invalidate(struct String type, struct String input){
	System.err.println(/* "Invalid " + type + ": " + input */);
	/* return generatePlaceholder */(input);
}
/* private static *//* Option<String> */ compileClassSegment(struct String input){
	/* Option<String> */ maybeMethod = compileMethod(input);/* 
        if (maybeMethod.isPresent()) return maybeMethod; */
	/* Option<String> */ maybeInterface = compileTypedBlock(input, "interface ");/* 
        if (maybeInterface.isPresent()) return maybeInterface; */
	struct int recordIndex = input.indexOf("record ");/* 
        if (recordIndex >= 0) {
            return new Some<>(generateStruct("", "Temp"));
        } */
	/* Option<String> */ maybeAssignment = compileAssignment(input);/* 
        if (maybeAssignment.isPresent()) {
            globals = maybeAssignment.map(value -> value + ";\n")
                    .map(globals::add)
                    .orElse(globals);

            return new Some<>("");
        } */
	/* return new Some<> */(invalidate("class segment", input));
}
/* private static *//* Option<String> */ compileMethod(struct String input){
	struct int paramStart = input.indexOf("(");
	/* if (paramStart < 0) return new None<> */();
	struct String header = input.substring(/* 0 */, paramStart).strip();
	struct String withParams = input.substring(paramStart + "(".length());
	struct int paramEnd = withParams.indexOf(")");
	/* if (paramEnd < 0) return new None<> */();
	struct String paramString = withParams.substring(/* 0 */, paramEnd);
	struct String withBody = withParams.substring(paramEnd + ")".length()).strip();
	/* if (!withBody */.startsWith("{") || !withBody.endsWith("}")) return new None<>();/* 

        return compileValues(paramString, Main::compileDefinition).flatMap(outputParams -> {
            return compileDefinition(header).flatMap(definition -> {
                return compileStatements(withBody.substring(1, withBody.length() - 1), Main::compileStatement).map(statement -> {
                    return addFunction(definition, outputParams, statement);
                });
            });
        } *//* ); */
}
/* private static */struct String addFunction(struct String definition, struct String params, struct String content){
	struct String function = /*  definition + "(" + params + "){" + content + "\n}\n" */;
	functions.add(function);/* 
        return ""; */
}
auto __lambda13__(auto main){
	return Main.divideValueChar(main);
}
auto __lambda14__(auto main){
	return Main.mergeValues(main);
}
/* private static *//* Option<String> */ compileValues(struct String input, /* Function<String */, /* Option<String>> */ compiler){
	/* return compileAll */(divideAll(input, __lambda13__), compiler, __lambda14__);
}
/* private static */struct State divideValueChar(struct State state, struct Character c){
	struct if (c = /* = ',' && state */.isLevel()) return state.advance();
	struct State appended = state.append(c);
	struct if (c = /* = '(') return appended */.enter();
	struct if (c = /* = ')') return appended */.exit();/* 
        return appended; */
}
/* private static */struct StringBuilder mergeValues(struct StringBuilder buffer, struct String element){
	/* if (buffer */.isEmpty()) return buffer.append(element);
	/* return buffer */.append(", ").append(element);
}
/* private static *//* Option<String> */ compileStatement(struct String input){
	struct String stripped = input.strip();
	/* if (stripped */.isEmpty()) return new Some<>("");/* 

        if (stripped.endsWith(";")) {
            String withoutEnd = stripped.substring(0, stripped.length() - ";".length());

            Option<String> maybeAssignment = compileAssignment(withoutEnd);
            if (maybeAssignment.isPresent()) return maybeAssignment;

            Option<String> maybeInvocation = compileInvocation(withoutEnd);
            if (maybeInvocation.isPresent()) return maybeInvocation.map(Main::generateStatement);
        } */
	/* return new Some<> */(invalidate("statement", input));
}
/* private static *//* Option<String> */ compileAssignment(struct String input){
	struct int separator = input.indexOf("=");
	/* if (separator < 0) return new None<> */();
	struct String inputDefinition = input.substring(/* 0 */, separator);
	struct String inputValue = input.substring(/* separator + "=" */.length());/* 
        return compileDefinition(inputDefinition).flatMap(outputDefinition -> {
            return compileValue(inputValue).map(outputValue -> {
                return generateStatement(outputDefinition + " = " + outputValue);
            });
        } *//* ); */
}
/* private static */struct String generateStatement(struct String value){/* 
        return "\n\t" + value + ";"; */
}
/* private static *//* Option<String> */ compileValue(struct String input){
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
	/* Option<String> */ maybeInvocation = compileInvocation(stripped);/* 
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
/* private static */struct String generateLambda(struct String paramName, struct String lambdaValue){
	struct String lambda = "__lambda" + lambdaCounter + "__";/* 
        lambdaCounter++; */
	struct String definition = generateDefinition("", "auto", lambda);
	struct String param = generateDefinition("", "auto", paramName);
	addFunction(definition, param, "\n\treturn " + lambdaValue + ";");/* 

        return lambda; */
}
/* private static *//* Option<String> */ compileInvocation(struct String stripped){
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
/* private static */struct String generateInvocation(struct String caller, struct String arguments){/* 
        return caller + "(" + arguments + ")"; */
}
/* private static *//* Option<String> */ compileDefinition(struct String input){
	struct String stripped = input.strip();
	struct int nameSeparator = stripped.lastIndexOf(" ");/* 
        if (nameSeparator >= 0) {
            String beforeName = stripped.substring(0, nameSeparator);

            int space = beforeName.lastIndexOf(" ");

            String modifiers;
            String type;
            if (space >= 0) {
                modifiers = generatePlaceholder(beforeName.substring(0, space));
                type = beforeName.substring(space + 1);
            } else {
                modifiers = "";
                type = beforeName;
            }

            String name = stripped.substring(nameSeparator + " ".length());
            return new Some<>(generateDefinition(modifiers, compileType(type), name));
        } */
	/* return new Some<> */(invalidate("definition", stripped));
}
/* private static */struct String generateDefinition(struct String modifiers, struct String type, struct String name){/* 
        return modifiers + type + " " + name; */
}
/* private static */struct String compileType(struct String type){
	struct String stripped = type.strip();/* 
        if (stripped.equals("void")) return "void"; *//* 
        if (stripped.endsWith("[]")) return compileType(stripped.substring(0, stripped.length() - "[]".length())) + "*"; *//* 

        if (isSymbol(stripped)) {
            return "struct " + stripped;
        } */
	/* return invalidate */("type", stripped);
}
/* private static */struct boolean isSymbol(struct String input){
	/* for *//* (int */ i = /*  0 */;
	/* i < input */.length();/*  i++) {
            char c = input.charAt(i);
            if (!Character.isLetter(c)) {
                return false;
            }
        } *//* 
        return true; */
}
/* private static */struct String generatePlaceholder(struct String input){/* 
        return "/* " + input + " */"; */
}
int main(){
	__main__();
	return 0;
}
