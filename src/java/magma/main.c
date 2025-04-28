/* private static */struct State {
	/* private final */ char* input;
	/* private */ List<char*> segments;
	/* private */ int index;
	/* private */ char* buffer;
	/* private */ int depth;
};
/* private */struct Joiner {
};
/* public */struct Main {
};
/* public */struct List<char*> {
};
/* public */struct Option<struct State> {
};
/* public */struct Option<char*> {
};
/* private State */(/* String input, List<String> segments, String buffer, int depth, int index */){/* 
            this.input = input;
            this.index = index;
            this.buffer = buffer;
            this.depth = depth;
            this.segments = segments;
         */
}
/* public State */(/* String input */){/* 
            this(input, Lists.empty(), "", 0, 0);
         */
}
/* private Option<Tuple<Character, */ /* State>> */ popAndAppendToTuple(/*  */){/* 
            return this.pop().map(tuple -> new Tuple<>(tuple.left, tuple.right.append(tuple.left)));
         */
}
/* private */ struct boolean isLevel(/*  */){/* 
            return this.depth == 0;
         */
}
/* private */ struct State enter(/*  */){/* 
            this.depth = this.depth + 1;
            return this;
         */
}
/* private */ struct State exit(/*  */){/* 
            this.depth = this.depth - 1;
            return this;
         */
}
/* private */ struct State advance(/*  */){/* 
            this.segments = this.segments.add(this.buffer);
            this.buffer = "";
            return this;
         */
}
/* private */ struct boolean isShallow(/*  */){/* 
            return this.depth == 1;
         */
}
/* private Option<Tuple<Character, */ /* State>> */ pop(/*  */){/* 
            if (this.index >= this.input.length()) {
                return new None<>();
            }

            var escaped = this.input.charAt(this.index);
            this.index = this.index + 1;
            return new Some<>(new Tuple<Character, State>(escaped, this));
         */
}
/* private */ struct State append(/* char c */){/* 
            this.buffer = this.buffer + c;
            return this;
         */
}
/* public */ Option<struct State> popAndAppend(/*  */){/* 
            return this.popAndAppendToTuple().map(Tuple::right);
         */
}
/* private Joiner */(/*  */){/* 
            this("");
         */
}
/* @Override
        public */ Option<char*> createInitial(/*  */){/* 
            return new None<>();
         */
}
/* @Override
        public */ Option<char*> fold(/* Option<String> current, String element */){/* 
            return new Some<>(current.map(inner -> inner + element).orElse(element));
         */
}
/* public static */ void main(/*  */){/* 
        try {
            var source = Paths.get(".", "src", "java", "magma", "Main.java");
            var target = source.resolveSibling("main.c");

            var input = Files.readString(source);
            Files.writeString(target, compileRoot(input));
        } catch (IOException e) {
            e.printStackTrace();
        }
     */
}
/* private static */ char* compileRoot(/* String input */){/* 
        var compiled = compileStatements(input, Main::compileRootSegment);
        var joinedExpansions = expansions.iter()
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
                .orElse("");

        return compiled + join(structs) + joinedExpansions + join(methods);
     */
}
/* private static */ char* join(/* List<String> list */){/* 
        return join(list, "");
     */
}
/* private static */ char* join(/* List<String> list, String delimiter */){/* 
        return list.iter().collect(new Joiner(delimiter)).orElse("");
     */
}
/* private static */ char* compileStatements(/* String input, Function<String, String> compiler */){/* 
        return compileAll(input, Main::foldStatementChar, compiler, Main::mergeStatements);
     */
}
/* private static */ char* compileAll(/* 
            String input,
            BiFunction<State, Character, State> folder,
            Function<String, String> compiler,
            BiFunction<String, String, String> merger
     */){/* 
        return generateAll(merger, parseAll(input, folder, compiler));
     */
}
/* private static */ char* generateAll(/* BiFunction<String, String, String> merger, List<String> parsed */){/* 
        return parsed.iter()
                .foldRight("", merger);
     */
}
/* private static */ List<char*> parseAll(/* String input, BiFunction<State, Character, State> folder, Function<String, String> compiler */){/* 
        return divideAll(input, folder)
                .iter()
                .map(compiler)
                .collect(new ListCollector<>());
     */
}
/* private static */ char* mergeStatements(/* String buffer, String element */){/* 
        return buffer + element;
     */
}
/* private static */ List<char*> divideAll(/* String input, BiFunction<State, Character, State> folder */){/* 
        State state = new State(input);
        while (true) {
            var maybeNextTuple = state.pop();
            if (maybeNextTuple.isEmpty()) {
                break;
            }

            var nextTuple = maybeNextTuple.orElse(null);
            var next = nextTuple.left;
            var withoutNext = nextTuple.right;

            state = foldSingleQuotes(withoutNext, next)
                    .orElseGet(() -> folder.apply(withoutNext, next));
        }

        return state.advance().segments;
     */
}
/* private static */ Option<struct State> foldSingleQuotes(/* State state, char next */){/* 
        if (next != '\'') {
            return new None<>();
        }

        var appended = state.append(next);
        return appended.popAndAppendToTuple()
                .flatMap(maybeSlash -> maybeSlash.left == '\\' ? maybeSlash.right.popAndAppend() : new Some<>(maybeSlash.right))
                .flatMap(State::popAndAppend);
     */
}
/* private static */ struct State foldStatementChar(/* State state, char c */){/* 
        var appended = state.append(c);

        if (c == ';' && appended.isLevel()) {
            return appended.advance();
        }
        if (c == '}' && appended.isShallow()) {
            return appended.advance().exit();
        }
        if (c == '{') {
            return appended.enter();
        }
        if (c == '}') {
            return appended.exit();
        }
        return appended;
     */
}
/* private static */ char* compileRootSegment(/* String input */){/* 
        var stripped = input.strip();
        if (stripped.isEmpty()) {
            return "";
        }

        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return "";
        }

        return compileClass(stripped).orElseGet(() -> generatePlaceholder(stripped));
     */
}
/* private static */ Option<char*> compileClass(/* String stripped */){/* 
        return compileStructure(stripped, "class ");
     */
}
/* private static */ Option<char*> compileStructure(/* String input, String infix */){/* 
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
                var typeParameters = Lists.fromArray(substring.split(Pattern.quote(",")));
                return assembleStructure(typeParameters, name, beforeClass, content);
            }
        }

        return assembleStructure(Lists.empty(), strippedBeforeContent, beforeClass, content);
     */
}
/* private static */ Option<char*> assembleStructure(/* List<String> typeParams, String name, String beforeClass, String content */){/* 
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
     */
}
/* private static */ Option<char*> generateStructure(/* String name, String beforeClass, String content */){/* 
        var generated = generatePlaceholder(beforeClass) + "struct " + name + " {" + compileStatements(content, Main::compileClassSegment) + "\n};\n";
        structs.add(generated);
        return new Some<>("");
     */
}
/* private static */ char* compileClassSegment(/* String input */){/* 
        var stripped = input.strip();
        if (stripped.isEmpty()) {
            return "";
        }

        return compileStructure(stripped, "record ")
                .or(() -> compileStructure(stripped, "interface "))
                .or(() -> compileClass(stripped))
                .or(() -> compileMethod(stripped))
                .or(() -> compileDefinitionStatement(stripped))
                .orElseGet(() -> generatePlaceholder(stripped));
     */
}
/* private static */ Option<char*> compileDefinitionStatement(/* String input */){/* 
        var stripped = input.strip();
        if (stripped.endsWith(";")) {
            var withoutEnd = stripped.substring(0, stripped.length() - ";".length());
            return new Some<>("\n\t" + compileDefinition(withoutEnd) + ";");
        }

        return new None<>();
     */
}
/* private static */ Option<char*> compileMethod(/* String stripped */){/* 
        var paramStart = stripped.indexOf("(");
        if (paramStart >= 0) {
            var definition = stripped.substring(0, paramStart);
            var afterParams = stripped.substring(paramStart + "(".length());
            var paramEnd = afterParams.indexOf(")");
            if (paramEnd >= 0) {
                var params = afterParams.substring(0, paramEnd);
                var withoutParams = afterParams.substring(paramEnd + ")".length());
                var withBraces = withoutParams.strip();

                if (withBraces.startsWith("{") && withBraces.endsWith("}")) {
                    var content = withBraces.substring(1, withBraces.length() - 1);
                    var generated = compileDefinition(definition) + "(" + generatePlaceholder(params) + "){" + generatePlaceholder(content) + "\n}\n";
                    methods.add(generated);
                    return new Some<>("");
                }
                else {
                    return new Some<>("");
                }
            }
        }
        return new None<>();
     */
}
/* private static */ char* compileDefinition(/* String input */){/* 
        var stripped = input.strip();
        var nameSeparator = stripped.lastIndexOf(" ");
        if (nameSeparator >= 0) {
            var beforeName = stripped.substring(0, nameSeparator);
            var name = stripped.substring(nameSeparator + " ".length());
            if (isSymbol(name)) {
                var typeSeparator = beforeName.lastIndexOf(" ");
                if (typeSeparator >= 0) {
                    var beforeType = beforeName.substring(0, typeSeparator).strip();
                    var type = beforeName.substring(typeSeparator + " ".length());
                    return generatePlaceholder(beforeType) + " " + compileType(type) + " " + name;
                }
            }
        }

        return generatePlaceholder(stripped);
     */
}
/* private static */ char* compileType(/* String input */){/* 
        var stripped = input.strip();
        var maybeTypeParamIndex = typeParameters.indexOf(stripped);
        if (maybeTypeParamIndex.isPresent()) {
            var typeParamIndex = maybeTypeParamIndex.orElse(null);
            return typeArguments.get(typeParamIndex);
        }

        switch (stripped) {
            case "int" -> {
                return "int";
            }
            case "void" -> {
                return "void";
            }
            case "String" -> {
                return "char*";
            }
        }

        if (stripped.endsWith(">")) {
            var withoutEnd = stripped.substring(0, stripped.length() - ">".length());
            var index = withoutEnd.indexOf("<");
            if (index >= 0) {
                var base = withoutEnd.substring(0, index).strip();
                var parsed = parseValues(withoutEnd.substring(index + "<".length()), Main::compileType);

                if (!expansions.contains(new Tuple<>(base, parsed))) {
                    expansions = expansions.add(new Tuple<>(base, parsed));
                }

                return base + "<" + generateValues(parsed) + ">";
            }
        }

        if (isSymbol(stripped)) {
            return "struct " + stripped;
        }

        return generatePlaceholder(stripped);
     */
}
/* private static */ char* generateValues(/* List<String> values */){/* 
        return generateAll(Main::mergeValues, values);
     */
}
/* private static */ List<char*> parseValues(/* String input, Function<String, String> compiler */){/* 
        return parseAll(input, Main::foldValueChar, compiler);
     */
}
/* private static */ char* mergeValues(/* String builder, String element */){/* 
        if (builder.isEmpty()) {
            return builder + element;
        }

        return builder + ", " + element;
     */
}
/* private static */ struct State foldValueChar(/* State state, char c */){/* 
        if (c == ',') {
            return state.advance();
        }
        return state.append(c);
     */
}
/* private static */ struct boolean isSymbol(/* String input */){/* 
        var stripped = input.strip();
        for (var i = 0; i < stripped.length(); i++) {
            var c = stripped.charAt(i);
            if (Character.isLetter(c)) {
                continue;
            }
            return false;
        }
        return true;
     */
}
/* private static */ char* generatePlaceholder(/* String input */){/* 
        return "/* " + input + " */";
     */
}
