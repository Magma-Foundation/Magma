/* if (isSymbol(stripped)) {
            return new Symbol(stripped);
        } *//* if (isNumber(stripped)) {
            return new Symbol(stripped);
        } *//* var arrowIndex = stripped.indexOf("->"); *//* if (arrowIndex >= 0) {
            var beforeArrow = stripped.substring(0, arrowIndex).strip();
            var afterArrow = stripped.substring(arrowIndex + "->".length()).strip();
            if (afterArrow.startsWith("{") && afterArrow.endsWith("}")) {
                var content = afterArrow.substring(1, afterArrow.length() - 1);

                var name = functionName + "_local" + functionLocalCounter;
                functionLocalCounter++;

                assembleMethod("auto " + name, "auto " + beforeArrow, content);
                return new Symbol(name);
            }
        } *//* var separator = stripped.lastIndexOf("."); *//* if (separator >= 0) {
            var value = stripped.substring(0, separator);
            var property = stripped.substring(separator + ".".length()).strip();
            return new DataAccess(parseValue(value), property);
        } *//* return new Content(stripped); *//* }

    private static State foldInvokableStart(State state, Character c) {
        var appended = state.append(c); *//* if (c == '(') {
            var maybeAdvanced = appended.isLevel() ? appended.advance() : appended;
            return maybeAdvanced.enter();
        } *//* if (c == ')') {
            return appended.exit();
        } *//* return appended; *//* }

    private static boolean isNumber(String input) {
        if (input.isEmpty()) {
            return false;
        } *//* for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (Character.isDigit(c)) {
                continue;
            }
            return false;
        } *//* return true; *//* }

    private static String compileDefinitionOrPlaceholder(String input) {
        return parseDefinitionOrPlaceholder(input).generate(); *//* }

    private static Defined parseDefinitionOrPlaceholder(String input) {
        return parseDefinition(input).<Defined>map(value -> value)
                .orElseGet(() -> new Content(input)); *//* }

    private static Option<Definition> parseDefinition(String input) {
        var stripped = input.strip(); *//* var nameSeparator = stripped.lastIndexOf(" "); *//* if (nameSeparator < 0) {
            return new None<>();
        } *//* var beforeName = stripped.substring(0, nameSeparator); *//* var name = stripped.substring(nameSeparator + " ".length()); *//* if (!isSymbol(name)) {
            return new None<>();
        } *//* var divisions = divideAll(beforeName, Main::foldByTypeSeparator); *//* if (divisions.size() == 1) {
            return new Some<>(new Definition(new None<>(), compileType(beforeName), name));
        } *//* var beforeType = join(divisions.subList(0, divisions.size() - 1), " "); *//* var type = divisions.last(); *//* return new Some<>(new Definition(new Some<>(beforeType), compileType(type), name)); *//* }

    private static State foldByTypeSeparator(State state, char c) {
        if (c == ' ' && state.isLevel()) {
            return state.advance();
        } *//* var appended = state.append(c); *//* if (c == '<') {
            return appended.enter();
        } *//* if (c == '>') {
            return appended.exit();
        } *//* return appended; *//* }

    private static String compileType(String input) {
        var stripped = input.strip(); *//* var maybeTypeParamIndex = typeParameters.indexOf(stripped); *//* if (maybeTypeParamIndex.isPresent()) {
            var typeParamIndex = maybeTypeParamIndex.orElse(null);
            return typeArguments.get(typeParamIndex);
        } *//* switch (stripped) {
            case "int", "boolean" -> {
                return "int";
            }
            case "Character" -> {
                return "char";
            }
            case "void" -> {
                return "void";
            }
            case "String" -> {
                return "char*";
            }
        } *//* if (stripped.endsWith(">")) {
            var withoutEnd = stripped.substring(0, stripped.length() - ">".length());
            var index = withoutEnd.indexOf("<");
            if (index >= 0) {
                var base = withoutEnd.substring(0, index).strip();
                var substring = withoutEnd.substring(index + "<".length());
                var parsed = parseValues(substring, Main::compileType);

                if (!expansions.contains(new Tuple<>(base, parsed))) {
                    expansions = expansions.add(new Tuple<>(base, parsed));
                }

                return base + "<" + generateValues(parsed) + ">";
            }
        } *//* if (isSymbol(stripped)) {
            return "struct " + stripped;
        } *//* return generatePlaceholder(stripped); *//* }

    private static String generateValues(List<String> values) {
        return generateAll(Main::mergeValues, values); *//* }

    private static <T> List<T> parseValues(String input, Function<String, T> compiler) {
        return parseAll(input, Main::foldValueChar, compiler); *//* }

    private static String mergeValues(String builder, String element) {
        if (builder.isEmpty()) {
            return builder + element;
        } *//* return builder + ", " + element; *//* }

    private static State foldValueChar(State state, char c) {
        if (c == ',' && state.isLevel()) {
            return state.advance();
        } *//* var appended = state.append(c); *//* if (c == '<') {
            return appended.enter();
        } *//* if (c == '>') {
            return appended.exit();
        } *//* return appended; *//* }

    private static boolean isSymbol(String input) {
        var stripped = input.strip(); *//* if (stripped.isEmpty()) {
            return false;
        } *//* for (var i = 0; i < stripped.length(); i++) {
            var c = stripped.charAt(i);
            if (Character.isLetter(c)) {
                continue;
            }
            return false;
        } *//* return true; *//* }

    private static String generatePlaceholder(String input) {
        return "/* " + input + " */"; *//* }
} *//* private */struct Defined extends Node {
};
/* private */struct Value extends Node {
};
/* private */struct Node {
};
/* private */struct Type extends Node {
};
/* private */struct State {
};
/* private */struct Joiner {
};
/* private */struct Definition {
};
/* private */struct Content {
};
/* private static */struct Whitespace implements Defined, Value {
};
/* private */struct StringValue {
};
/* private */struct Symbol {
};
/* private */struct Invocation {
};
/* private */struct DataAccess {
};
/* private static Option<String> compileStructure(String input, String infix) {
        var classIndex = input.indexOf(infix);
        if (classIndex >= 0) {
            var beforeClass = input.substring(0, classIndex).strip();
            var afterClass = input.substring(classIndex + infix.length());
            var contentStart = afterClass.indexOf("{");
            if (contentStart >= 0) {
                var beforeContent = afterClass.substring(0, contentStart).strip();

                var paramStart = beforeContent.indexOf("(");
                var withEnd = afterClass.substring(contentStart + "{".length()).strip();
                if (paramStart >= 0) {
                    String withoutParams = beforeContent.substring(0, paramStart).strip();
                    return getString(withoutParams, beforeClass, withEnd);
                }
                else {
                    return getString(beforeContent, beforeClass, withEnd);
                }
            }
        }
        return new None<>();
    }

    private static Option<String> getString(String beforeContent, String beforeClass, String withEnd) {
        if (!withEnd.endsWith("}")) {
            return new None<>();
        }
        var content = withEnd.substring(0, withEnd.length() - "}".length());

        var strippedBeforeContent = beforeContent.strip();
        if (strippedBeforeContent.endsWith(">")) {
            var withoutEnd = strippedBeforeContent.substring(0, strippedBeforeContent.length() - ">".length());
            var typeParamStart = withoutEnd.indexOf("<");
            if (typeParamStart >= 0) {
                var name = withoutEnd.substring(0, typeParamStart).strip();
                var substring = withoutEnd.substring(typeParamStart + "<".length());
                var typeParameters = listFromArray(substring.split(Pattern.quote(",")));
                return assembleStructure(typeParameters, name, beforeClass, content);
            }
        }

        return assembleStructure(listEmpty(), strippedBeforeContent, beforeClass, content);
    }

    private static Option<String> assembleStructure(List<String> typeParams, String name, String beforeClass, String content) {
        if (!typeParams.isEmpty()) {
            expandables.put(name, typeArgs -> {
                typeParameters = typeParams;
                typeArguments = typeArgs;

                var newName = name + "<" + join(typeArgs, ", ") + ">";
                return generateStructure(newName, beforeClass, content);
            });

            return new Some<>("");
        }

        return generateStructure(name, beforeClass, content);
    }

    private static Option<String> generateStructure(String name, String beforeClass, String content) {
        structName = name;

        var compiled = compileStatements(content, Main::compileClassSegment);
        var generated = generatePlaceholder(beforeClass) + "struct " + name + " {" + compiled + "\n};\n";
        structs.add(generated);
        return new Some<>("");
    }

    private static String compileClassSegment(String input) {
        var stripped = input.strip();
        if (stripped.isEmpty()) {
            return "";
        }

        return compileStructure(stripped, " */struct ")
                .or {
	/* \n" */;
	/* }
                else {
                    newArgs */ /* = */ parsedArgs;
};
/* public */struct Main {
	/* private static String structName = "" */;
	/* private static String functionName = "" */;
	/* private static int functionLocalCounter = 0 */;
};
/* private */struct Tuple<char, struct State> {
};
/* public */struct Option<Tuple<char, struct State>> {
};
/* private */struct Tuple</*  */> {
};
/* public */struct Some</*  */> {
};
/* public */struct Option<struct State> {
};
/* public */struct Option<char*> {
};
/* public */struct None</*  */> {
};
/* public */struct List<char*> {
};
/* public */struct List<struct T> {
};
/* private static */struct ListCollector</*  */> {
};
/* public */struct Some<char*> {
};
/* public */struct Option<struct Whitespace> {
};
// Function<char*, char*>
// BiFunction<struct State, char, struct State>
// BiFunction<char*, char*, char*>
// Function<char*, struct T>
/* private static */ struct State fromInput(struct State this, char* input){
	return struct State(input, listEmpty(), "", 0, 0);
}
auto popAndAppendToTuple_local0(auto tuple){
	/* var poppedChar = tuple.left; */
	/* var poppedState = tuple.right; */
	/* var appended = poppedState.append(poppedChar); */
	return Tuple</*  */>(poppedChar, appended);
}
/* private */ Option<Tuple<char, struct State>> popAndAppendToTuple(struct State this){
	return this.pop(this).map(this.pop(this), popAndAppendToTuple_local0);
}
/* private */ int isLevel(struct State this){
	return this.depth == 0;
}
/* private */ struct State enter(struct State this){
	return struct State(this.input, this.segments, this.buffer, this.depth + 1, this.index);
}
/* private */ struct State exit(struct State this){
	return struct State(this.input, this.segments, this.buffer, this.depth - 1, this.index);
}
/* private */ struct State advance(struct State this){
	return struct State(this.input, this.segments.add(this.segments, this.buffer), "", this.depth, this.index);
}
/* private */ int isShallow(struct State this){
	return this.depth == 1;
}
/* private */ Option<Tuple<char, struct State>> pop(struct State this){
	/* if (this.index >= this.input.length()) {
                return new None<>();
            } */
	/* var escaped = this.input.charAt(this.index); */
	return Some</*  */>(/* new Tuple<Character, State>(escaped */, /* new State(this */.input, this.segments, this.buffer, this.depth, this.index + 1)));
}
/* private */ struct State append(struct State this, struct char c){
	return struct State(this.input, this.segments, this.buffer + c, this.depth, this.index);
}
/* public */ Option<struct State> popAndAppend(struct State this){
	return this.popAndAppendToTuple(this).map(this.popAndAppendToTuple(this), /* Tuple::right */);
}
struct private Joiner(struct Joiner this){
	/* this(""); */
}
/* @Override
        public */ Option<char*> createInitial(struct Joiner this){
	return None</*  */>();
}
/* @Override
        public */ Option<char*> fold(struct Joiner this, Option<char*> current, char* element){
	return Some</*  */>(current.map(current, /* inner -> inner + this */.delimiter + element).orElse(current.map(current, /* inner -> inner + this */.delimiter + element), element));
}
struct public Definition(struct Definition this, char* type, char* name){
	/* this(new None<>(), type, name); */
}
/* @Override
        public */ char* generate(struct Definition this){
	/* var joined = this.beforeType().map(Main::generatePlaceholder).map(inner -> inner + " ").orElse(""); */
	return /* joined + this */.type() + " " + this.name(/* joined + this */.type() + " " + this);
}
/* @Override
        public */ char* generate(struct Content this){
	return generatePlaceholder(this.input);
}
/* @Override
        public */ char* generate(struct Whitespace implements Defined, Value this){
	return "";
}
/* @Override
        public */ char* generate(struct StringValue this){
	return "\"" + this.value + "\"";
}
/* @Override
        public */ char* generate(struct Symbol this){
	return this.value;
}
/* @Override
        public */ char* generate(struct Invocation this){
	return this.caller.generate() + "(" + generateValueList(this.args) + ")";
}
/* @Override
        public */ char* generate(struct DataAccess this){
	return this.parent.generate() + "." + this.property;
}
/* public static */ void main(struct DataAccess this){
	/* try {
            var source = Paths.get(".", "src", "java", "magma", "Main.java");
            var target = source.resolveSibling("main.c");

            var input = Files.readString(source);
            Files.writeString(target, compileRoot(input));
        } */
	/* catch (IOException e) {
            e.printStackTrace();
        } */
}
/* private static */ char* compileRoot(struct DataAccess this, char* input){
	/* var compiled = compileStatements(input, Main::compileRootSegment); */
	/* var joinedExpansions = expansions.iter()
                .map(tuple -> {
                    if (expandables.containsKey(tuple.left)) {
                        var expandable = expandables.get(tuple.left);
                        return expandable.apply(tuple.right).orElse("");
                    }
                    else {
                        return "// " + tuple.left + "<" + join(tuple.right, ", ") + ">\n";
                    }
                })
                .collect(new Joiner())
                .orElse(""); */
	return /* compiled + join(structs) + joinedExpansions + join */(methods);
}
/* private static */ char* join(struct DataAccess this, List<char*> list){
	return join(list, "");
}
/* private static */ char* join(struct DataAccess this, List<char*> list, char* delimiter){
	return list.iter(list).collect(list.iter(list), struct Joiner(delimiter)).orElse(list.iter(list).collect(list.iter(list), struct Joiner(delimiter)), "");
}
/* private static */ char* compileStatements(struct DataAccess this, char* input, Function<char*, char*> compiler){
	return compileAll(input, /* Main::foldStatementChar */, compiler, /* Main::mergeStatements */);
}
/* private static */ char* compileAll(struct DataAccess this, char* input, BiFunction<struct State, char, struct State> folder, Function<char*, char*> compiler, BiFunction<char*, char*, char*> merger){
	return generateAll(merger, /* parseAll(input */, folder, /* compiler) */);
}
/* private static */ char* generateAll(struct DataAccess this, BiFunction<char*, char*, char*> merger, List<char*> parsed){
	return parsed.iter(parsed).fold(parsed.iter(parsed), "", merger);
}
/* private static <T> */ List<struct T> parseAll(struct DataAccess this, char* input, BiFunction<struct State, char, struct State> folder, Function<char*, struct T> compiler){
	return divideAll(input, folder).iter(divideAll(input, folder)).map(divideAll(input, folder).iter(divideAll(input, folder)), compiler).collect(divideAll(input, folder).iter(divideAll(input, folder)).map(divideAll(input, folder).iter(divideAll(input, folder)), compiler), ListCollector</*  */>());
}
/* private static */ char* mergeStatements(struct DataAccess this, char* buffer, char* element){
	return /* buffer + element */;
}
/* private static */ List<char*> divideAll(struct DataAccess this, char* input, BiFunction<struct State, char, struct State> folder){
	/* State state = State.fromInput(input); */
	/* while (true) {
            var maybeNextTuple = state.pop();
            if (maybeNextTuple.isEmpty()) {
                break;
            }

            var nextTuple = maybeNextTuple.orElse(null);
            var next = nextTuple.left;
            var withoutNext = nextTuple.right;

            state = foldSingleQuotes(withoutNext, next)
                    .orElseGet(() -> folder.apply(withoutNext, next));
        } */
	return state.advance(state).segments;
}
/* private static */ Option<struct State> foldSingleQuotes(struct DataAccess this, struct State state, struct char next){
	/* if (next != '\'') {
            return new None<>();
        } */
	/* var appended = state.append(next); */
	return appended.popAndAppendToTuple(appended).flatMap(appended.popAndAppendToTuple(appended), /* maybeSlash -> maybeSlash */.left == '\\' ? maybeSlash.right.popAndAppend() : new Some<>(/* maybeSlash -> maybeSlash */.left == '\\' ? maybeSlash.right, maybeSlash.right)).flatMap(appended.popAndAppendToTuple(appended).flatMap(appended.popAndAppendToTuple(appended), /* maybeSlash -> maybeSlash */.left == '\\' ? maybeSlash.right.popAndAppend() : new Some<>(/* maybeSlash -> maybeSlash */.left == '\\' ? maybeSlash.right, maybeSlash.right)), /* State::popAndAppend */);
}
/* private static */ struct State foldStatementChar(struct DataAccess this, struct State state, struct char c){
	/* var appended = state.append(c); */
	/* if (c == ';' && appended.isLevel()) {
            return appended.advance();
        } */
	/* if (c == '}' && appended.isShallow()) {
            return appended.advance().exit();
        } */
	/* if (c == '{' || c == '(') {
            return appended.enter();
        } */
	/* if (c == '}' || c == ')') {
            return appended.exit();
        } */
	return appended;
}
/* private static */ char* compileRootSegment(struct DataAccess this, char* input){
	/* var stripped = input.strip(); */
	/* if (stripped.isEmpty()) {
            return "";
        } */
	/* if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return "";
        } */
	return compileClass(stripped).orElseGet(compileClass(stripped), /* () -> generatePlaceholder */(stripped));
}
/* private static */ Option<char*> compileClass(struct DataAccess this, char* stripped){
	return compileStructure(stripped, "class ");
}
/* }

    private static */ Some<char*> assembleMethod(struct ")
                .or this, char* definition, char* outputParams, char* content){
	/* var generated = definition + "(" + outputParams + "){" + compileStatements(content, Main::compileFunctionSegment) + "\n */
}
/* }

    private static */ Option<struct Whitespace> parseWhitespace(struct ")
                .or this, char* input){
	/* if (input.isBlank()) {
            return new Some<>(new Whitespace()); */
}
/* @Override
        public <R> */ Option<struct R> map(struct Some</*  */> this, Function</*  */, struct R> mapper){
	return Some</*  */>(mapper.apply(mapper, this.value));
}
/* @Override
        public */ int isPresent(struct Some</*  */> this){
	return true;
}
/* @Override
        public */ /*  */ orElse(struct Some</*  */> this, /*  */ other){
	return this.value;
}
/* @Override
        public */ int isEmpty(struct Some</*  */> this){
	return false;
}
/* @Override
        public */ /*  */ orElseGet(struct Some</*  */> this, Supplier</*  */> supplier){
	return this.value;
}
/* @Override
        public <R> */ Option<struct R> flatMap(struct Some</*  */> this, Function</*  */, Option<struct R>> mapper){
	return mapper.apply(mapper, this.value);
}
/* @Override
        public */ Option</*  */> or(struct Some</*  */> this, Supplier<Option</*  */>> supplier){
	return this;
}
/* @Override
        public <R> */ Option<struct R> map(struct None</*  */> this, Function</*  */, struct R> mapper){
	return None</*  */>();
}
/* @Override
        public */ int isPresent(struct None</*  */> this){
	return false;
}
/* @Override
        public */ /*  */ orElse(struct None</*  */> this, /*  */ other){
	return other;
}
/* @Override
        public */ int isEmpty(struct None</*  */> this){
	return true;
}
/* @Override
        public */ /*  */ orElseGet(struct None</*  */> this, Supplier</*  */> supplier){
	return supplier.get(supplier);
}
/* @Override
        public <R> */ Option<struct R> flatMap(struct None</*  */> this, Function</*  */, Option<struct R>> mapper){
	return None</*  */>();
}
/* @Override
        public */ Option</*  */> or(struct None</*  */> this, Supplier<Option</*  */>> supplier){
	return supplier.get(supplier);
}
/* @Override
        public */ List<struct T> createInitial(struct ListCollector</*  */> this){
	return listEmpty();
}
/* @Override
        public */ List<struct T> fold(struct ListCollector</*  */> this, List<struct T> current, struct T element){
	return current.add(current, element);
}
/* @Override
        public <R> */ Option<struct R> map(struct Some<char*> this, Function<char*, struct R> mapper){
	return Some</*  */>(mapper.apply(mapper, this.value));
}
/* @Override
        public */ int isPresent(struct Some<char*> this){
	return true;
}
/* @Override
        public */ char* orElse(struct Some<char*> this, char* other){
	return this.value;
}
/* @Override
        public */ int isEmpty(struct Some<char*> this){
	return false;
}
/* @Override
        public */ char* orElseGet(struct Some<char*> this, Supplier<char*> supplier){
	return this.value;
}
/* @Override
        public <R> */ Option<struct R> flatMap(struct Some<char*> this, Function<char*, Option<struct R>> mapper){
	return mapper.apply(mapper, this.value);
}
/* @Override
        public */ Option<char*> or(struct Some<char*> this, Supplier<Option<char*>> supplier){
	return this;
}
