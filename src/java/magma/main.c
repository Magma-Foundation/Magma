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
/* private static List_<String> assemble(List_<String> compiled) {
        assembleGenerics(compiled);
        compiled.addAll(structs);
        compiled.addAll(methods);
        return compiled;
    } */
/* private static void assembleGenerics(List_<String> compiled) {
        while (!toExpand.isEmpty()) {
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
        }
    } */
/* private static Option<String> assembleEntry(Tuple<String, List_<String>> expansion) {
        if (expandables.containsKey(expansion.left)) {
            return expandables.get(expansion.left).apply(expansion.right);
        }
        else {
            return new None<>();
        }
    } */
/* private static Option<String> compileAndMergeAll(List_<String> segments, Function<String, Option<String>> compiler, BiFunction<String, String, String> merger) {
        return compileAll(segments, compiler).map(compiled -> mergeAll(compiled, merger));
    } */
/* private static String mergeAll(List_<String> list, BiFunction<String, String, String> merger) {
        return list.iter().fold("", merger);
    } */
/* private static Option<List_<String>> compileAll(List_<String> segments, Function<String, Option<String>> compiler) {
        Option<List_<String>> maybeCompiled = new Some<>(Lists.empty());
        return segments.iter().fold(maybeCompiled, (current, element) -> current.flatMap(output -> {
            return compiler.apply(element).map(compiled -> {
                output.add(compiled);
                return output;
            });
        }));
    } */
/* private static List_<String> divideAll(String input, BiFunction<State, Character, State> folder) {
        LinkedList<Character> queue = IntStream.range(0, input.length())
                .mapToObj(input::charAt)
                .collect(Collectors.toCollection(LinkedList::new));

        State current = new State(queue);
        while (current.hasNext()) {
            char c = current.pop();

            State finalCurrent = current;
            current = foldSingleQuotes(current, c)
                    .or(() -> foldDoubleQuotes(finalCurrent, c))
                    .orElseGet(() -> folder.apply(finalCurrent, c));
        }

        return current.advance().segments;
    } */
/* private static Option<String> assembleStruct(String name, String inputContent, List_<String> typeParams, List_<String> typeArguments) {
        String realName = typeArguments.isEmpty() ? name : stringify(name, typeArguments);

        String outputContent = compileStatements(inputContent, definition -> new Some<>(compileClassSegment(definition, realName, typeParams, typeArguments))).orElse("");
        String struct = "typedef struct {" +
                outputContent +
                "\n} " +
                realName +
                ";\n";

        structs.add(struct);
        return new Some<>("");
    } */
/* private static String compileClassSegment(String input, String structName, List_<String> typeParams, List_<String> typeArguments) {
        return compileClass(input)
                .or(() -> compileToStruct(input, "interface "))
                .or(() -> compileToStruct(input, "record "))
                .or(() -> compileMethod(input, structName, typeParams, typeArguments))
                .orElseGet(() -> generatePlaceholder(input) + "\n");
    } */
/* private static Option<String> compileMethod(String input, String structName, List_<String> typeParams, List_<String> typeArguments) {
        int paramStart = input.indexOf("(");
        if (paramStart < 0) {
            return new None<>();
        }

        String inputDefinition = input.substring(0, paramStart).strip();
        String withParams = input.substring(paramStart + 1);

        return compileDefinition(inputDefinition, Lists.of(structName), typeParams, typeArguments).flatMap(outputDefinition -> {
            int paramEnd = withParams.indexOf(")");
            if (paramEnd >= 0) {
                String inputParams = withParams.substring(0, paramEnd).strip();
                Option<String> maybeOutputParams;
                maybeOutputParams = inputParams.isEmpty() ? new Some<>("") : compileValues(inputParams, definition -> compileDefinition(definition, Lists.empty(), typeParams, typeArguments));

                return maybeOutputParams.flatMap(outputParams -> {
                    String value = outputDefinition + "(" +
                            outputParams +
                            "){" + "\n}\n";

                    methods.add(value);
                    return new Some<>("");
                });
            }
            else {
                return new None<>();
            }
        });
    } */
/* private static List_<String> divideValues(String input) {
        return divideAll(input, Main::foldValueChar);
    } */
/* private static Option<String> compileDefinition(String definition, List_<String> stack, List_<String> typeParams, List_<String> typeArguments) {
        int nameSeparator = definition.lastIndexOf(" ");
        if (nameSeparator < 0) {
            return new None<>();
        }

        String beforeName = definition.substring(0, nameSeparator).strip();
        String oldName = definition.substring(nameSeparator + " ".length()).strip();
        if (!isSymbol(oldName)) {
            return new None<>();
        }

        String newName;
        if (oldName.equals("main")) {
            newName = "__main__";
        }
        else {
            String joined = stack.iter()
                    .collect(new Joiner("_"))
                    .map(value -> value + "_")
                    .orElse("");

            newName = joined + oldName;
        }

        int typeSeparator = findTypeSeparator(beforeName);
        String inputType;
        if (typeSeparator >= 0) {
            inputType = beforeName.substring(typeSeparator + " ".length()).strip();
        }
        else {
            inputType = beforeName;
        }

        return compileType(inputType, new Some<>(newName), typeParams, typeArguments);
    } */
/* private static Option<String> compileType(String input, Option<String> maybeName, List_<String> typeParams, List_<String> typeArguments) {
        String stripped = input.strip();
        int index = typeParams.indexOf(stripped);
        if (index >= 0) {
            return new Some<>(generateSimpleDefinition(typeArguments.get(index), maybeName));
        }

        if (stripped.equals("new") || stripped.equals("private") || stripped.equals("public")) {
            return new None<>();
        }

        if (stripped.equals("void")) {
            return new Some<>(generateSimpleDefinition("void", maybeName));
        }

        if (stripped.equals("char") || stripped.equals("Character")) {
            return new Some<>(generateSimpleDefinition("char", maybeName));
        }

        if (stripped.equals("int") || stripped.equals("Integer") || stripped.equals("boolean") || stripped.equals("Boolean")) {
            return new Some<>(generateSimpleDefinition("int", maybeName));
        }

        if (stripped.equals("String")) {
            return new Some<>(generateSimpleDefinition("char*", maybeName));
        }

        if (stripped.endsWith("[]")) {
            return compileType(stripped.substring(0, stripped.length() - "[]".length()), new None<>(), typeParams, typeArguments)
                    .map(value -> generateSimpleDefinition(value + "*", maybeName));
        }

        if (stripped.endsWith(">")) {
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

                        Tuple<String, List_<String>> entry = new Tuple<>(base, arguments);
                        if (!toExpand.contains(entry)) {
                            toExpand.add(entry);
                        }

                        String type = stringify(base, arguments);
                        return generateSimpleDefinition(type, maybeName);
                    });
                }
            }
        }

        if (isSymbol(stripped)) {
            return new Some<>(generateSimpleDefinition(stripped, maybeName));
        }
        else {
            return new None<>();
        }
    } */
/* private static String stringify(String base, List_<String> arguments) {
        String merged = mergeAll(arguments, (builder, element) -> mergeDelimited(builder, element, "_"))
                .replace("*", "_ref");

        return base + "_" + merged;
    } */
/* private static String generateFunctionalDefinition(Option<String> name, List_<String> paramTypes, String returnType) {
        String joined = paramTypes.iter().collect(new Joiner(", ")).orElse("");

        return returnType + " (*" + name.orElse("") + ")(" +
                joined +
                ")";
    } */
/*  */

} Main;
typedef struct {/*  */

} Option_char_ref;
typedef struct {/*  */

} Option_State;
typedef struct {/*  */

} Option_R;
int State_isShallow(){
}
State State_exit(){
}
State State_enter(){
}
State State_advance(){
}
int State_isLevel(){
}
State State_append(char c){
}
int State_hasNext(){
}
char State_pop(){
}
Option_char_ref Joiner_createInitial(){
}
Option_char_ref Joiner_fold(Option_char_ref current, char* element){
}
void __main__(char** args){
}
char* Main_compile(char* input){
}
Option_char_ref Main_compileStatements(char* input, Option_char_ref (*compiler)(char*)){
}
char* Main_mergeStatements(char* output, char* element){
}
Option_State Main_foldDoubleQuotes(State current, char c){
}
Option_State Main_foldSingleQuotes(State current, char c){
}
State Main_foldStatementChar(State state, char c){
}
char* Main_compileRootSegment(char* input){
}
Option_char_ref Main_compileClass(char* input){
}
Option_char_ref Main_compileToStruct(char* input, char* infix){
}
int Main_isSymbol(char* input){
}
Option_char_ref Main_compileValues(char* input, Option_char_ref (*compileDefinition)(char*)){
}
State Main_foldValueChar(State state, char c){
}
char* Main_mergeValues(char* builder, char* element){
}
char* Main_mergeDelimited(char* buffer, char* element, char* delimiter){
}
int Main_findTypeSeparator(char* input){
}
char* Main_generateSimpleDefinition(char* type, Option_char_ref maybeName){
}
char* Main_generatePlaceholder(char* input){
}
char* Option_char_ref_orElse(char* other){
}
Option_R Option_char_ref_flatMap(Option_R (*mapper)(char*)){
}
Option_R Option_char_ref_map(R (*mapper)(char*)){
}
Option_char_ref Option_char_ref_or(Option_char_ref (*other)()){
}
char* Option_char_ref_orElseGet(char* (*other)()){
}
void Option_char_ref_ifPresent(Consumer_char_ref consumer){
}
int Option_char_ref_isPresent(){
}
State Option_State_orElse(State other){
}
Option_R Option_State_flatMap(Option_R (*mapper)(State)){
}
Option_R Option_State_map(R (*mapper)(State)){
}
Option_State Option_State_or(Option_State (*other)()){
}
State Option_State_orElseGet(State (*other)()){
}
void Option_State_ifPresent(Consumer_State consumer){
}
int Option_State_isPresent(){
}
R Option_R_orElse(R other){
}
Option_R Option_R_flatMap(Option_R (*mapper)(R)){
}
Option_R Option_R_map(R (*mapper)(R)){
}
Option_R Option_R_or(Option_R (*other)()){
}
R Option_R_orElseGet(R (*other)()){
}
void Option_R_ifPresent(Consumer_R consumer){
}
int Option_R_isPresent(){
}
int main(int argc, char **argv){
	__main__(argv);
	return 0;
}