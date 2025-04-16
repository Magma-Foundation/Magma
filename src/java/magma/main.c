/* import java.io.IOException; */
/* import java.nio.file.Files; */
/* import java.nio.file.Path; */
/* import java.nio.file.Paths; */
/* import java.util.Arrays; */
/* import java.util.Deque; */
/* import java.util.HashMap; */
/* import java.util.LinkedList; */
/* import java.util.Map; */
/* import java.util.function.BiFunction; */
/* import java.util.function.Consumer; */
/* import java.util.function.Function; */
/* import java.util.function.Supplier; */
/* import java.util.stream.Collectors; */
/* import java.util.stream.IntStream; */
/*  */
typedef struct {/* private final Deque<Character> queue; */
/* private final List_<String> segments; */
/* private String buffer; */
/* private int depth; */
/* private State(Deque<Character> queue, List_<String> segments, String buffer, int depth) {
            this.queue = queue;
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
        } */
/* public State(Deque<Character> queue) {
            this(queue, Lists.empty(), "", 0);
        } */
/*  */

} State;
typedef struct {/*  */

} Joiner;
typedef struct {/* private static final Map<String, Function<List_<String>, Option<String>>> expandables = new HashMap<>(); */
/* private static final List_<Tuple<String, List_<String>>> visited = Lists.empty(); */
/* private static final List_<String> structs = Lists.empty(); */
/* private static final List_<String> methods = Lists.empty(); */
/* private static List_<Tuple<String, List_<String>>> toExpand = Lists.empty(); */
/*  */

} Main;
typedef struct {/* T orElse(T other); */
/* <R> Option<R> flatMap(Function<T, Option<R>> mapper); */
/* <R> Option<R> map(Function<T, R> mapper); */
/* Option<T> or(Supplier<Option<T>> other); */
/* T orElseGet(Supplier<T> other); */
/* void ifPresent(Consumer<T> consumer); */
/* boolean isPresent(); */
/*  */

} Option_char_ref;
typedef struct {/* List_<T> add(T element); */
/* List_<T> addAll(List_<T> elements); */
/* boolean isEmpty(); */
/* List_<T> copy(); */
/* boolean contains(T element); */
/* Iterator<T> iter(); */
/* int indexOf(T element); */
/* T get(int index); */
/*  */

} List__char_ref;
typedef struct {/*  */

} Tuple_char_ref_List__char_ref;
typedef struct {/* T orElse(T other); */
/* <R> Option<R> flatMap(Function<T, Option<R>> mapper); */
/* <R> Option<R> map(Function<T, R> mapper); */
/* Option<T> or(Supplier<Option<T>> other); */
/* T orElseGet(Supplier<T> other); */
/* void ifPresent(Consumer<T> consumer); */
/* boolean isPresent(); */
/*  */

} Option_List__char_ref;
typedef struct {/* T orElse(T other); */
/* <R> Option<R> flatMap(Function<T, Option<R>> mapper); */
/* <R> Option<R> map(Function<T, R> mapper); */
/* Option<T> or(Supplier<Option<T>> other); */
/* T orElseGet(Supplier<T> other); */
/* void ifPresent(Consumer<T> consumer); */
/* boolean isPresent(); */
/*  */

} Option_State;
typedef struct {/* <R> R fold(R initial, BiFunction<R, T, R> folder) {
            R current = initial;
            while (true) {
                R finalCurrent = current;
                Option<R> option = this.head.next().map(next -> folder.apply(finalCurrent, next));
                if (option.isPresent()) {
                    current = option.orElse(null);
                }
                else {
                    return current;
                }
            }
        } */
/* <R> Iterator<R> map(Function<T, R> mapper) {
            return new Iterator<>(() -> this.head.next().map(mapper));
        } */
/* <C> C collect(Collector<T, C> collector) {
            return this.fold(collector.createInitial(), collector::fold);
        } */
/*  */

} Iterator_char_ref;
int State_isShallow(){/* return this.depth == 1; *//*  */
}
State State_exit(){/* this.depth = this.depth - 1; *//* return this; *//*  */
}
State State_enter(){/* this.depth = this.depth + 1; *//* return this; *//*  */
}
State State_advance(){/* this.segments.add(this.buffer); *//* this.buffer = ""; *//* return this; *//*  */
}
int State_isLevel(){/* return this.depth == 0; *//*  */
}
State State_append(char c){/* this.buffer = this.buffer + c; *//* return this; *//*  */
}
int State_hasNext(){/* return !this.queue.isEmpty(); *//*  */
}
char State_pop(){/* return this.queue.pop(); *//*  */
}
Option_char_ref Joiner_createInitial(){/* return new None<>(); *//*  */
}
Option_char_ref Joiner_fold(Option_char_ref current, char* element){/* return new Some<>(current.map(inner -> inner + this.delimiter + element).orElse(element)); *//*  */
}
void __main__(char** args){/* try {
            Path source = Paths.get(".", "src", "java", "magma", "Main.java");
            String input = Files.readString(source);

            Path target = source.resolveSibling("main.c");
            Files.writeString(target, compile(input) + "int main(int argc, char **argv){\n\t__main__(argv);\n\treturn 0;\n}");

            Process process = new ProcessBuilder("cmd.exe", "/c", "build.bat")
                    .directory(Paths.get(".").toFile())
                    .inheritIO()
                    .start();

            process.waitFor();
        } *//* catch (IOException | InterruptedException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        } *//*  */
}
char* Main_compile(char* input){/* List_<String> segments = divideAll(input, Main::foldStatementChar); *//* return compileAll(segments, s -> new Some<>(compileRootSegment(s)))
                .map(Main::assemble)
                .map(compiled -> mergeAll(compiled, Main::mergeStatements)).orElse(""); *//*  */
}
List__char_ref Main_assemble(List__char_ref compiled){/* assembleGenerics(compiled); *//* compiled.addAll(structs); *//* compiled.addAll(methods); *//* return compiled; *//*  */
}
void Main_assembleGenerics(List__char_ref compiled){/* while (!toExpand.isEmpty()) {
            List_<Tuple<String, List_<String>>> copy = toExpand.copy();
            toExpand = Lists.empty();

            boolean anyGenerated = false;

            for (Tuple<String, List_<String>> tuple : Lists.toNativeList(copy)) {
                if (!visited.contains(tuple)) {
                    visited.add(tuple);
                    assembleEntry(tuple).ifPresent(compiled::add);
                    anyGenerated = true;
                }
            }

            if (!anyGenerated) {
                break;
            }
        } *//*  */
}
Option_char_ref Main_assembleEntry(Tuple_char_ref_List__char_ref expansion){/* if (expandables.containsKey(expansion.left)) {
            return expandables.get(expansion.left).apply(expansion.right);
        } *//* else {
            return new None<>();
        } *//*  */
}
Option_char_ref Main_compileStatements(char* input, Option_char_ref (*compiler)(char*)){/* return compileAndMergeAll(divideAll(input, Main::foldStatementChar), compiler, Main::mergeStatements); *//*  */
}
Option_char_ref Main_compileAndMergeAll(List__char_ref segments, Option_char_ref (*compiler)(char*), char* (*merger)(char*, char*)){/* return compileAll(segments, compiler).map(compiled -> mergeAll(compiled, merger)); *//*  */
}
char* Main_mergeAll(List__char_ref list, char* (*merger)(char*, char*)){/* return list.iter().fold("", merger); *//*  */
}
Option_List__char_ref Main_compileAll(List__char_ref segments, Option_char_ref (*compiler)(char*)){/* Option<List_<String>> maybeCompiled = new Some<>(Lists.empty()); *//* return segments.iter().fold(maybeCompiled, (current, element) -> current.flatMap(output -> {
            return compiler.apply(element).map(compiled -> {
                output.add(compiled);
                return output;
            });
        } *//* )); *//*  */
}
char* Main_mergeStatements(char* output, char* element){/* return output + element; *//*  */
}
List__char_ref Main_divideAll(char* input, State (*folder)(State, char)){/* LinkedList<Character> queue = IntStream.range(0, input.length())
                .mapToObj(input::charAt)
                .collect(Collectors.toCollection(LinkedList::new)); *//* State current = new State(queue); *//* while (current.hasNext()) {
            char c = current.pop();

            State finalCurrent = current;
            current = foldSingleQuotes(current, c)
                    .or(() -> foldDoubleQuotes(finalCurrent, c))
                    .orElseGet(() -> folder.apply(finalCurrent, c));
        } *//* return current.advance().segments; *//*  */
}
Option_State Main_foldDoubleQuotes(State current, char c){/* return new None<>(); *//*  */
}
Option_State Main_foldSingleQuotes(State current, char c){/* if (c != '\'') {
            return new None<>();
        } *//* State current1 = current.append(c); *//* char popped = current1.pop(); *//* State appended = current1.append(popped); *//* State state = popped == '\\' ? appended.append(appended.pop()) : appended; *//* return new Some<>(state.append(state.pop())); *//*  */
}
State Main_foldStatementChar(State state, char c){/* State appended = state.append(c); *//* if (c == ';' && state.isLevel()) {
            return appended.advance();
        } *//* if (c == '}' && state.isShallow()) {
            return state.advance().exit();
        } *//* if (c == '{') {
            return appended.enter();
        } *//* if (c == '}') {
            return appended.exit();
        } *//* return appended; *//*  */
}
char* Main_compileRootSegment(char* input){/* if (input.startsWith("package ")) {
            return "";
        } *//* return compileClass(input).orElseGet(() -> generatePlaceholder(input) + "\n"); *//*  */
}
Option_char_ref Main_compileClass(char* input){/* return compileToStruct(input, "class "); *//*  */
}
Option_char_ref Main_compileToStruct(char* input, char* infix){/* int classIndex = input.indexOf(infix); *//* if (classIndex < 0) {
            return new None<>();
        } *//* boolean beforeKeyword = Arrays.stream(input.substring(0, classIndex).split(" "))
                .map(String::strip)
                .filter(modifier -> !modifier.isEmpty())
                .allMatch(Main::isSymbol); *//* if (!beforeKeyword) {
            return new None<>();
        } *//* String afterKeyword = input.substring(classIndex + infix.length()); *//* int contentStart = afterKeyword.indexOf("{");
        if (contentStart < 0) {
            return new None<>();
        }
        String beforeContent = afterKeyword.substring(0, contentStart).strip();

        String withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
        if (!withEnd.endsWith("}")) {
            return new None<>();
        }

        String inputContent = withEnd.substring(0, withEnd.length() - "} *//* ".length()); *//* int permitsIndex = beforeContent.lastIndexOf(" permits "); *//* String withoutPermits = permitsIndex >= 0
                ? beforeContent.substring(0, permitsIndex).strip()
                : beforeContent; *//* int paramStart = withoutPermits.indexOf("("); *//* String withoutParams = paramStart >= 0
                ? withoutPermits.substring(0, paramStart).strip()
                : withoutPermits; *//* int typeParamStart = withoutParams.indexOf("<"); *//* if (typeParamStart >= 0) {
            var name = withoutParams.substring(0, typeParamStart).strip();
            String withTypeEnd = withoutParams.substring(typeParamStart + "<".length()).strip();
            if (withTypeEnd.endsWith(">")) {
                String inputTypeParams = withTypeEnd.substring(0, withTypeEnd.length() - ">".length());
                List_<String> outputTypeParams = divideValues(inputTypeParams)
                        .iter()
                        .map(String::strip)
                        .collect(new ListCollector<>());

                expandables.put(name, typeArguments -> assembleStruct(name, inputContent, outputTypeParams, typeArguments));
                return new Some<>("");
            }
        } *//* if (isSymbol(withoutParams)) {
            return assembleStruct(withoutParams, inputContent, Lists.empty(), Lists.empty());
        } *//* return new None<>(); *//*  */
}
Option_char_ref Main_assembleStruct(char* name, char* inputContent, List__char_ref typeParams, List__char_ref typeArguments){/* String realName = typeArguments.isEmpty() ? name : stringify(name, typeArguments); *//* String outputContent = compileStatements(inputContent, definition -> new Some<>(compileClassSegment(definition, realName, typeParams, typeArguments))).orElse(""); *//* String struct = "typedef struct {" +
                outputContent +
                "\n} *//* " +
                realName +
                "; *//* \n"; *//* structs.add(struct); *//* return new Some<>(""); *//*  */
}
int Main_isSymbol(char* input){/* for (int i = 0; *//* i < input.length(); *//* i++) {
            char c = input.charAt(i);
            if (c == '_' || Character.isLetter(c)) {
                continue;
            }
            return false;
        } *//* return true; *//*  */
}
char* Main_compileClassSegment(char* input, char* structName, List__char_ref typeParams, List__char_ref typeArguments){/* return compileClass(input)
                .or(() -> compileToStruct(input, "interface "))
                .or(() -> compileToStruct(input, "record "))
                .or(() -> compileMethod(input, structName, typeParams, typeArguments))
                .orElseGet(() -> generatePlaceholder(input) + "\n"); *//*  */
}
Option_char_ref Main_compileMethod(char* input, char* structName, List__char_ref typeParams, List__char_ref typeArguments){/* int paramStart = input.indexOf("("); *//* if (paramStart < 0) {
            return new None<>();
        } *//* String inputDefinition = input.substring(0, paramStart).strip(); *//* String withParams = input.substring(paramStart + 1); *//* return compileDefinition(inputDefinition, Lists.of(structName), typeParams, typeArguments).flatMap(outputDefinition -> {
            int paramEnd = withParams.indexOf(")");
            if (paramEnd >= 0) {
                String inputParams = withParams.substring(0, paramEnd).strip();
                String withEnd = withParams.substring(paramEnd + ")".length()).strip();

                Option<String> maybeOutputParams;
                maybeOutputParams = inputParams.isEmpty() ? new Some<>("") : compileValues(inputParams, definition -> compileDefinition(definition, Lists.empty(), typeParams, typeArguments));

                return maybeOutputParams.flatMap(outputParams -> {
                    if (withEnd.startsWith("{") && withEnd.endsWith("}")) {
                        String inputContent = withEnd.substring("{".length(), withEnd.length() - "}".length());
                        return compileStatements(inputContent, Main::compileStatement).map(outputContent -> {
                            String value = outputDefinition + "(" +
                                    outputParams +
                                    "){" + outputContent + "\n}\n";

                            methods.add(value);
                            return "";
                        });
                    }
                    else {
                        return new None<>();
                    }
                });
            }
            else {
                return new None<>();
            }
        } *//* ); *//*  */
}
Option_char_ref Main_compileStatement(char* input){/* return new Some<>(generatePlaceholder(input)); *//*  */
}
Option_char_ref Main_compileValues(char* input, Option_char_ref (*compileDefinition)(char*)){/* return compileAndMergeAll(divideValues(input), compileDefinition, Main::mergeValues); *//*  */
}
List__char_ref Main_divideValues(char* input){/* return divideAll(input, Main::foldValueChar); *//*  */
}
State Main_foldValueChar(State state, char c){/* if (c == ',' && state.isLevel()) {
            return state.advance();
        } *//* State appended = state.append(c); *//* if (c == '<') {
            return appended.enter();
        } *//* if (c == '>') {
            return appended.exit();
        } *//* return appended; *//*  */
}
char* Main_mergeValues(char* builder, char* element){/* return mergeDelimited(builder, element, ", "); *//*  */
}
char* Main_mergeDelimited(char* buffer, char* element, char* delimiter){/* if (buffer.isEmpty()) {
            return element;
        } *//* return buffer + delimiter + element; *//*  */
}
Option_char_ref Main_compileDefinition(char* definition, List__char_ref stack, List__char_ref typeParams, List__char_ref typeArguments){/* int nameSeparator = definition.lastIndexOf(" "); *//* if (nameSeparator < 0) {
            return new None<>();
        } *//* String beforeName = definition.substring(0, nameSeparator).strip(); *//* String oldName = definition.substring(nameSeparator + " ".length()).strip(); *//* if (!isSymbol(oldName)) {
            return new None<>();
        } *//* String newName; *//* if (oldName.equals("main")) {
            newName = "__main__";
        } *//* else {
            String joined = stack.iter()
                    .collect(new Joiner("_"))
                    .map(value -> value + "_")
                    .orElse("");

            newName = joined + oldName;
        } *//* int typeSeparator = findTypeSeparator(beforeName); *//* String inputType; *//* if (typeSeparator >= 0) {
            String beforeType = beforeName.substring(0, typeSeparator).strip();
            if (beforeType.endsWith(">")) {
                return new None<>();
            }

            inputType = beforeName.substring(typeSeparator + " ".length()).strip();
        } *//* else {
            inputType = beforeName;
        } *//* return compileType(inputType, new Some<>(newName), typeParams, typeArguments); *//*  */
}
int Main_findTypeSeparator(char* input){/* int depth = 0; *//* for (int i = input.length() - 1; *//* i >= 0; *//* i--) {
            char c = input.charAt(i);
            if (c == ' ' && depth == 0) {
                return i;
            }

            if (c == '>') {
                depth++;
            }
            if (c == '<') {
                depth--;
            }
        } *//* return -1; *//*  */
}
Option_char_ref Main_compileType(char* input, Option_char_ref maybeName, List__char_ref typeParams, List__char_ref typeArguments){/* String stripped = input.strip(); *//* int index = typeParams.indexOf(stripped); *//* if (index >= 0) {
            return new Some<>(generateSimpleDefinition(typeArguments.get(index), maybeName));
        } *//* if (stripped.equals("new") || stripped.equals("private") || stripped.equals("public")) {
            return new None<>();
        } *//* if (stripped.equals("void")) {
            return new Some<>(generateSimpleDefinition("void", maybeName));
        } *//* if (stripped.equals("char") || stripped.equals("Character")) {
            return new Some<>(generateSimpleDefinition("char", maybeName));
        } *//* if (stripped.equals("int") || stripped.equals("Integer") || stripped.equals("boolean") || stripped.equals("Boolean")) {
            return new Some<>(generateSimpleDefinition("int", maybeName));
        } *//* if (stripped.equals("String")) {
            return new Some<>(generateSimpleDefinition("char*", maybeName));
        } *//* if (stripped.endsWith("[]")) {
            return compileType(stripped.substring(0, stripped.length() - "[]".length()), new None<>(), typeParams, typeArguments)
                    .map(value -> generateSimpleDefinition(value + "*", maybeName));
        } *//* if (stripped.endsWith(">")) {
            String withoutEnd = stripped.substring(0, stripped.length() - ">".length());
            int argsStart = withoutEnd.indexOf("<");
            if (argsStart >= 0) {
                String base = withoutEnd.substring(0, argsStart).strip();
                if (isSymbol(base)) {
                    String inputArgs = withoutEnd.substring(argsStart + "<".length());
                    List_<String> segments = divideValues(inputArgs);
                    return compileAll(segments, arg -> compileType(arg, new None<>(), typeParams, typeArguments)).map(arguments -> {
                        if (base.equals("Supplier")) {
                            return generateFunctionalDefinition(maybeName, Lists.of(), arguments.get(0));
                        }

                        if (base.equals("Function")) {
                            return generateFunctionalDefinition(maybeName, Lists.of(arguments.get(0)), arguments.get(1));
                        }

                        if (base.equals("BiFunction")) {
                            return generateFunctionalDefinition(maybeName, Lists.of(arguments.get(0), arguments.get(1)), arguments.get(2));
                        }

                        if (base.equals("Consumer")) {
                            return generateFunctionalDefinition(maybeName, Lists.of(arguments.get(0)), "void");
                        }

                        Tuple<String, List_<String>> entry = new Tuple<>(base, arguments);
                        if (!toExpand.contains(entry)) {
                            toExpand.add(entry);
                        }

                        String type = stringify(base, arguments);
                        return generateSimpleDefinition(type, maybeName);
                    });
                }
            }
        } *//* if (isSymbol(stripped)) {
            return new Some<>(generateSimpleDefinition(stripped, maybeName));
        } *//* else {
            return new None<>();
        } *//*  */
}
char* Main_stringify(char* base, List__char_ref arguments){/* String merged = mergeAll(arguments, (builder, element) -> mergeDelimited(builder, element, "_"))
                .replace("*", "_ref"); *//* return base + "_" + merged; *//*  */
}
char* Main_generateFunctionalDefinition(Option_char_ref name, List__char_ref paramTypes, char* returnType){/* String joined = paramTypes.iter().collect(new Joiner(", ")).orElse(""); *//* return returnType + " (*" + name.orElse("") + ")(" +
                joined +
                ")"; *//*  */
}
char* Main_generateSimpleDefinition(char* type, Option_char_ref maybeName){/* return type + maybeName.map(name -> " " + name).orElse(""); *//*  */
}
char* Main_generatePlaceholder(char* input){/* String replaced = input.strip()
                .replace("<cmt-start>", "<cmt-start>")
                .replace("<cmt-end>", "<cmt-end>"); *//* return "<cmt-start>" + replaced + "<cmt-end>"; *//*  */
}
int main(int argc, char **argv){
	__main__(argv);
	return 0;
}