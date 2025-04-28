/* private */struct State {
};
/* private */struct Joiner {
};
/* public */struct Main {
};
/* private */struct Tuple<char, struct State> {
};
/* public */struct Option<Tuple<char, struct State>> {
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
// Function<char*, char*>
// BiFunction<struct State, char, struct State>
// BiFunction<char*, char*, char*>
/* private static */ struct State fromInput(char* input){
	return struct State(input, listEmpty(), "", 0, 0);
}
/* private */ Option<Tuple<char, struct State>> popAndAppendToTuple(/*  */){
	return /* this.pop */(/* ).map */(/* tuple -> new Tuple<> */(/* tuple.left */, /* tuple.right.append(tuple.left */)));
}
/* private */ struct boolean isLevel(/*  */){
	return /* this.depth == 0 */;
}
/* private */ struct State enter(/*  */){
	return struct State(/* this.input */, /* this.segments */, /* this.buffer */, /* this.depth + 1 */, /* this.index */);
}
/* private */ struct State exit(/*  */){
	return struct State(/* this.input */, /* this.segments */, /* this.buffer */, /* this.depth - 1 */, /* this.index */);
}
/* private */ struct State advance(/*  */){
	return struct State(/* this.input */, /* this.segments.add */(/* this.buffer */), "", /* this.depth */, /* this.index */);
}
/* private */ struct boolean isShallow(/*  */){
	return /* this.depth == 1 */;
}
/* private */ Option<Tuple<char, struct State>> pop(/*  */){
	/* if (this.index >= this.input.length()) {
                return new None<>();
            } */
	/* var escaped = this.input.charAt(this.index); */
	return Some</*  */>(/* new Tuple<Character, State>(escaped */, /* new State(this.input */, /* this.segments */, /* this.buffer */, /* this.depth */, /* this.index + 1)) */);
}
/* private */ struct State append(struct char c){
	return struct State(/* this.input */, /* this.segments */, /* this.buffer + c */, /* this.depth */, /* this.index */);
}
/* public */ Option<struct State> popAndAppend(/*  */){
	return /* this.popAndAppendToTuple */(/* ).map(Tuple::right */);
}
struct private Joiner(/*  */){
	/* this(""); */
}
/* @Override
        public */ Option<char*> createInitial(/*  */){
	return None</*  */>();
}
/* @Override
        public */ Option<char*> fold(Option<char*> current, char* element){
	return Some</*  */>(/* current.map */(/* inner -> inner + this.delimiter + element).orElse(element */));
}
/* public static */ void main(/*  */){
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
/* private static */ char* compileRoot(char* input){
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
                } */
	/* )
                .collect(new Joiner())
                .orElse(""); */
	return /* compiled + join */(/* structs) + joinedExpansions + join(methods */);
}
/* private static */ char* join(List<char*> list){
	return join(list, "");
}
/* private static */ char* join(List<char*> list, char* delimiter){
	return /* list.iter */(/* ).collect(new Joiner(delimiter)).orElse("" */);
}
/* private static */ char* compileStatements(char* input, Function<char*, char*> compiler){
	return compileAll(input, /* Main::foldStatementChar */, compiler, /* Main::mergeStatements */);
}
/* private static */ char* compileAll(char* input, BiFunction<struct State, char, struct State> folder, Function<char*, char*> compiler, BiFunction<char*, char*, char*> merger){
	return generateAll(merger, /* parseAll(input */, folder, /* compiler) */);
}
/* private static */ char* generateAll(BiFunction<char*, char*, char*> merger, List<char*> parsed){
	return /* parsed.iter */(/* )
                .foldRight("" */, merger);
}
/* private static */ List<char*> parseAll(char* input, BiFunction<struct State, char, struct State> folder, Function<char*, char*> compiler){
	return divideAll(input, /* folder)
                .iter */(/* )
                .map(compiler)
                .collect(new ListCollector<>( */));
}
/* private static */ char* mergeStatements(char* buffer, char* element){
	return /* buffer + element */;
}
/* private static */ List<char*> divideAll(char* input, BiFunction<struct State, char, struct State> folder){
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
	return /* state.advance().segments */;
}
/* private static */ Option<struct State> foldSingleQuotes(struct State state, struct char next){
	/* if (next != '\'') {
            return new None<>();
        } */
	/* var appended = state.append(next); */
	return /* appended.popAndAppendToTuple */(/* )
                .flatMap(maybeSlash -> maybeSlash.left == '\\' ? maybeSlash.right.popAndAppend() : new Some<>(maybeSlash.right))
                .flatMap(State::popAndAppend */);
}
/* private static */ struct State foldStatementChar(struct State state, struct char c){
	/* var appended = state.append(c); */
	/* if (c == ';' && appended.isLevel()) {
            return appended.advance();
        } */
	/* if (c == '}' && appended.isShallow()) {
            return appended.advance().exit();
        } */
	/* if (c == '{') {
            return appended.enter();
        } */
	/* if (c == '}') {
            return appended.exit();
        } */
	return appended;
}
/* private static */ char* compileRootSegment(char* input){
	/* var stripped = input.strip(); */
	/* if (stripped.isEmpty()) {
            return "";
        } */
	/* if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return "";
        } */
	return compileClass(/* stripped).orElseGet */(/* () -> generatePlaceholder(stripped */));
}
/* private static */ Option<char*> compileClass(char* stripped){
	return compileStructure(stripped, "class ");
}
/* private static */ Option<char*> compileStructure(char* input, char* infix){
	/* var classIndex = input.indexOf(infix); */
	/* if (classIndex >= 0) {
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
        var content = withEnd.substring(0, withEnd.length() - "} */
	/* ".length()); */
	/* var strippedBeforeContent = beforeContent.strip(); */
	/* if (strippedBeforeContent.endsWith(">")) {
            var withoutEnd = strippedBeforeContent.substring(0, strippedBeforeContent.length() - ">".length());
            var typeParamStart = withoutEnd.indexOf("<");
            if (typeParamStart >= 0) {
                var name = withoutEnd.substring(0, typeParamStart).strip();
                var substring = withoutEnd.substring(typeParamStart + "<".length());
                var typeParameters = listFromArray(substring.split(Pattern.quote(",")));
                return assembleStructure(typeParameters, name, beforeClass, content);
            }
        } */
	return assembleStructure(listEmpty(), strippedBeforeContent, beforeClass, content);
}
/* private static */ Option<char*> assembleStructure(List<char*> typeParams, char* name, char* beforeClass, char* content){
	/* if (!typeParams.isEmpty()) {
            expandables.put(name, typeArgs -> {
                typeParameters = typeParams;
                typeArguments = typeArgs;

                var newName = name + "<" + join(typeArgs, ", ") + ">";
                return generateStructure(newName, beforeClass, content);
            });

            return new Some<>("");
        } */
	return generateStructure(name, beforeClass, content);
}
/* private static */ Option<char*> generateStructure(char* name, char* beforeClass, char* content){
	/* var generated = generatePlaceholder(beforeClass) + "struct " + name + " {" + compileStatements(content, Main::compileClassSegment) + "\n} */
	/* ; */
	/* \n"; */
	/* structs.add(generated); */
	return Some</*  */>("");
}
/* private static */ char* compileClassSegment(char* input){
	/* var stripped = input.strip(); */
	/* if (stripped.isEmpty()) {
            return "";
        } */
	return compileStructure(stripped, /* "record ")
                .or */(/* () -> compileStructure(stripped, "interface "))
                .or(() -> compileClass(stripped))
                .or(() -> compileMethod(stripped))
                .or(() -> compileDefinitionStatement(stripped))
                .orElseGet(() -> generatePlaceholder(stripped */));
}
/* private static */ Option<char*> compileDefinitionStatement(char* input){
	/* var stripped = input.strip(); */
	/* if (stripped.endsWith("; */
	/* ")) {
            var withoutEnd = stripped.substring(0, stripped.length() - ";".length());
            return new Some<>("\n\t" + compileDefinitionOrPlaceholder(withoutEnd) + ";");
        } */
	return None</*  */>();
}
/* private static */ Option<char*> compileMethod(char* stripped){
	/* var paramStart = stripped.indexOf("("); */
	/* if (paramStart >= 0) {
            var definition = stripped.substring(0, paramStart);
            var afterParams = stripped.substring(paramStart + "(".length());
            var paramEnd = afterParams.indexOf(")");
            if (paramEnd >= 0) {
                var params = afterParams.substring(0, paramEnd);
                var withoutParams = afterParams.substring(paramEnd + ")".length());
                var withBraces = withoutParams.strip();

                if (withBraces.startsWith("{") && withBraces.endsWith("}")) {
                    var content = withBraces.substring(1, withBraces.length() - 1);
                    var outputParams = generateValues(parseValues(params, Main::compileDefinitionOrPlaceholder));
                    var generated = compileDefinitionOrPlaceholder(definition) + "(" + outputParams + "){" + compileStatements(content, Main::compileFunctionSegment) + "\n}\n";
                    methods.add(generated);
                    return new Some<>("");
                }
                else {
                    return new Some<>("");
                }
            }
        } */
	return None</*  */>();
}
/* private static */ char* compileFunctionSegment(char* input){
	/* var stripped = input.strip(); */
	/* if (stripped.isEmpty()) {
            return "";
        } */
	/* if (stripped.endsWith("; */
	/* ")) {
            var withoutEnd = stripped.substring(0, stripped.length() - ";".length()).strip();
            if (withoutEnd.startsWith("return ")) {
                var value = withoutEnd.substring("return ".length());
                return "\n\treturn " + compileValue(value) + ";";
            }
        } */
	return /* "\n\t" + generatePlaceholder */(stripped);
}
/* private static */ char* compileValue(char* input){
	/* var stripped = input.strip(); */
	/* if (stripped.startsWith("\"") && stripped.endsWith("\"")) {
            return stripped;
        } */
	/* if (stripped.endsWith(")")) {
            var withoutEnd = stripped.substring(0, stripped.length() - ")".length()).strip();
            var argsStart = withoutEnd.indexOf("(");
            if (argsStart >= 0) {
                var caller = withoutEnd.substring(0, argsStart);
                String compiled;
                if (caller.startsWith("new ")) {
                    compiled = compileType(caller.substring("new ".length()));
                }
                else {
                    compiled = compileValue(caller);
                }

                var arguments = withoutEnd.substring(argsStart + "(".length());
                return compiled + "(" + compileValues(arguments) + ")";
            }
        } */
	/* if (isSymbol(stripped)) {
            return stripped;
        } */
	/* if (isNumber(stripped)) {
            return stripped;
        } */
	return generatePlaceholder(stripped);
}
/* private static */ struct boolean isNumber(char* input){
	/* for (var i = 0; */
	/* i < input.length(); */
	/* i++) {
            var c = input.charAt(i);
            if (Character.isDigit(c)) {
                continue;
            }
            return false;
        } */
	return true;
}
/* private static */ char* compileValues(char* input){
	return generateValues(/* parseValues(input */, /* Main::compileValue) */);
}
/* private static */ char* compileDefinitionOrPlaceholder(char* input){
	return compileDefinition(/* input).orElseGet */(/* () -> generatePlaceholder(input */));
}
/* private static */ Option<char*> compileDefinition(char* input){
	/* var stripped = input.strip(); */
	/* var nameSeparator = stripped.lastIndexOf(" "); */
	/* if (nameSeparator < 0) {
            return new None<>();
        } */
	/* var beforeName = stripped.substring(0, nameSeparator); */
	/* var name = stripped.substring(nameSeparator + " ".length()); */
	/* if (!isSymbol(name)) {
            return new None<>();
        } */
	/* var divisions = divideAll(beforeName, Main::foldByTypeSeparator); */
	/* if (divisions.size() == 1) {
            return new Some<>(compileType(beforeName) + " " + name);
        } */
	/* else {
            var beforeType = join(divisions.subList(0, divisions.size() - 1), " ");
            var type = divisions.last();

            return new Some<>(generatePlaceholder(beforeType) + " " + compileType(type) + " " + name);
        } */
}
/* private static */ struct State foldByTypeSeparator(struct State state, struct char c){
	/* if (c == ' ' && state.isLevel()) {
            return state.advance();
        } */
	/* var appended = state.append(c); */
	/* if (c == '<') {
            return appended.enter();
        } */
	/* if (c == '>') {
            return appended.exit();
        } */
	return appended;
}
/* private static */ char* compileType(char* input){
	/* var stripped = input.strip(); */
	/* var maybeTypeParamIndex = typeParameters.indexOf(stripped); */
	/* if (maybeTypeParamIndex.isPresent()) {
            var typeParamIndex = maybeTypeParamIndex.orElse(null);
            return typeArguments.get(typeParamIndex);
        } */
	/* switch (stripped) {
            case "int" -> {
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
        } */
	/* if (stripped.endsWith(">")) {
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
        } */
	/* if (isSymbol(stripped)) {
            return "struct " + stripped;
        } */
	return generatePlaceholder(stripped);
}
/* private static */ char* generateValues(List<char*> values){
	return generateAll(/* Main::mergeValues */, values);
}
/* private static */ List<char*> parseValues(char* input, Function<char*, char*> compiler){
	return parseAll(input, /* Main::foldValueChar */, compiler);
}
/* private static */ char* mergeValues(char* builder, char* element){
	/* if (builder.isEmpty()) {
            return builder + element;
        } */
	return /* builder + ", " + element */;
}
/* private static */ struct State foldValueChar(struct State state, struct char c){
	/* if (c == ',' && state.isLevel()) {
            return state.advance();
        } */
	/* var appended = state.append(c); */
	/* if (c == '<') {
            return appended.enter();
        } */
	/* if (c == '>') {
            return appended.exit();
        } */
	return appended;
}
/* private static */ struct boolean isSymbol(char* input){
	/* var stripped = input.strip(); */
	/* if (stripped.isEmpty()) {
            return false;
        } */
	/* for (var i = 0; */
	/* i < stripped.length(); */
	/* i++) {
            var c = stripped.charAt(i);
            if (Character.isLetter(c)) {
                continue;
            }
            return false;
        } */
	return true;
}
/* private static */ char* generatePlaceholder(char* input){
	return "/* " + input + " */";
}
/* @Override
        public <R> */ Option<struct R> map(Function</*  */, struct R> mapper){
	return Some</*  */>(/* mapper.apply */(/* this.value */));
}
/* @Override
        public */ struct boolean isPresent(/*  */){
	return true;
}
/* @Override
        public */ /*  */ orElse(/*  */ other){
	return /* this.value */;
}
/* @Override
        public */ struct boolean isEmpty(/*  */){
	return false;
}
/* @Override
        public */ /*  */ orElseGet(Supplier</*  */> supplier){
	return /* this.value */;
}
/* @Override
        public <R> */ Option<struct R> flatMap(Function</*  */, Option<struct R>> mapper){
	return /* mapper.apply */(/* this.value */);
}
/* @Override
        public */ Option</*  */> or(Supplier<Option</*  */>> supplier){
	return this;
}
/* @Override
        public <R> */ Option<struct R> map(Function</*  */, struct R> mapper){
	return None</*  */>();
}
/* @Override
        public */ struct boolean isPresent(/*  */){
	return false;
}
/* @Override
        public */ /*  */ orElse(/*  */ other){
	return other;
}
/* @Override
        public */ struct boolean isEmpty(/*  */){
	return true;
}
/* @Override
        public */ /*  */ orElseGet(Supplier</*  */> supplier){
	return /* supplier.get */();
}
/* @Override
        public <R> */ Option<struct R> flatMap(Function</*  */, Option<struct R>> mapper){
	return None</*  */>();
}
/* @Override
        public */ Option</*  */> or(Supplier<Option</*  */>> supplier){
	return /* supplier.get */();
}
