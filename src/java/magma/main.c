/* private static */struct State {
	/* private final */ char* input;
	/* private */ List<char*> segments;
	/* private */ int index;
	/* private */ /* StringBuilder */ buffer;
	/* private */ int depth;
};
/* private */struct Joiner {
};
/* private static Optional<String> compileStructure(String input, String infix) {
        var classIndex = input.indexOf(infix);
        if (classIndex >= 0) {
            var beforeClass = input.substring(0, classIndex).strip();
            var afterClass = input.substring(classIndex + infix.length());
            var contentStart = afterClass.indexOf("{");
            if (contentStart >= 0) {
                var beforeContent = afterClass.substring(0, contentStart).strip();

                var paramStart = beforeContent.indexOf("(");

                if (paramStart >= 0) {
                    String withoutParams = beforeContent.substring(0, paramStart).strip();
                    return getString(withoutParams, beforeClass, afterClass.substring(contentStart + "{".length()).strip());
                }
                else {
                    return getString(beforeContent, beforeClass, afterClass.substring(contentStart + "{".length()).strip());
                }
            }
        }
        return Optional.empty();
    }

    private static Optional<String> getString(String beforeContent, String beforeClass, String withEnd) {
        if (!withEnd.endsWith("}")) {
            return Optional.empty();
        }

        var content = withEnd.substring(0, withEnd.length() - "}".length());

        var strippedBeforeContent = beforeContent.strip();
        if (strippedBeforeContent.endsWith(">")) {
            var typeParamStart = strippedBeforeContent.indexOf("<");
            if (typeParamStart >= 0) {
                var name = strippedBeforeContent.substring(0, typeParamStart).strip();
                var substring = strippedBeforeContent.substring(typeParamStart + "<".length());
                var typeParameters = Lists.fromArray(substring.split(Pattern.quote(",")));
                return assemble(typeParameters, name, beforeClass, content);
            }
        }

        return assemble(Lists.empty(), strippedBeforeContent, beforeClass, content);
    }

    private static Optional<String> assemble(List<String> typeParams, String name, String beforeClass, String content) {
        if (!typeParams.isEmpty()) {
            return Optional.of("");
        }

        var generated = generatePlaceholder(beforeClass) + "struct " + name + " {" + compileStatements(content, Main::compileClassSegment) + "\n};\n";
        structs.add(generated);
        return Optional.of("");
    }

    private static String compileClassSegment(String input) {
        var stripped = input.strip();
        if (stripped.isEmpty()) {
            return "";
        }

        return compileStructure(stripped, " */struct ")
                .or {
	/* var stripped = input.strip() */;
	/* if (stripped.endsWith(" */;/* ")) {
            var withoutEnd = stripped.substring(0, stripped.length() - ";".length());
            return Optional.of("\n\t" + compileDefinition(withoutEnd) + ";");
        } */
	/* return Optional.empty() */;
	/* }

    private static Optional<String> compileMethod(String stripped) {
        var paramStart = stripped.indexOf("(") */;
	/* return Optional.empty() */;
	/* }

    private static String compileDefinition(String input) {
        var stripped = input.strip() */;
	/* var nameSeparator = stripped.lastIndexOf(" ") */;
	/* return generatePlaceholder(stripped) */;
	/* }

    private static String compileType(String input) {
        var stripped = input.strip() */;/* if (stripped.endsWith(">")) {
            var withoutEnd = stripped.substring(0, stripped.length() - ">".length());
            var index = withoutEnd.indexOf("<");
            if (index >= 0) {
                var base = withoutEnd.substring(0, index).strip();
                var arguments = compileValues(withoutEnd.substring(index + "<".length()), Main::compileType);
                return base + "<" + arguments + ">";
            }
        } */
	/* return generatePlaceholder(stripped) */;
	/* }

    private static String compileValues(String input, Function<String, String> compiler) {
        return compileAll(input, Main::foldValueChar, compiler, Main::mergeValues) */;
	/* return builder.append(", ").append(element) */;
	/* return state.append(c) */;
	/* }

    private static boolean isSymbol(String input) {
        var stripped = input.strip() */;
	/* for (var i = 0 */;
	/* i < stripped.length() */;/* i++) {
            var c = stripped.charAt(i);
            if (Character.isLetter(c)) {
                continue;
            }
            return false;
        } */
	/* return true */;
	/* }

    private static String generatePlaceholder(String input) {
        return "/* " + input + " */" */;
};
/* public */struct Main {
	/* private static final List<String> methods = Lists.empty() */;
	/* private static final List<String> structs = Lists.empty() */;
};
/* private State */(/* String input, List<String> segments, StringBuilder buffer, int depth, int index */){/* 
            this.input = input;
            this.index = index;
            this.buffer = buffer;
            this.depth = depth;
            this.segments = segments;
         */
}
/* public State */(/* String input */){/* 
            this(input, Lists.empty(), new StringBuilder(), 0, 0);
         */
}
/* private Optional<Tuple<Character, */ /* State>> */ popAndAppendToTuple(/*  */){/* 
            return this.pop().map(tuple -> new Tuple<>(tuple.left, tuple.right.append(tuple.left)));
         */
}
/* private */ /* boolean */ isLevel(/*  */){/* 
            return this.depth == 0;
         */
}
/* private */ /* State */ enter(/*  */){/* 
            this.depth = this.depth + 1;
            return this;
         */
}
/* private */ /* State */ exit(/*  */){/* 
            this.depth = this.depth - 1;
            return this;
         */
}
/* private */ /* State */ advance(/*  */){/* 
            this.segments = this.segments.add(this.buffer.toString());
            this.buffer = new StringBuilder();
            return this;
         */
}
/* private */ /* boolean */ isShallow(/*  */){/* 
            return this.depth == 1;
         */
}
/* private Optional<Tuple<Character, */ /* State>> */ pop(/*  */){/* 
            if (this.index >= this.input.length()) {
                return Optional.empty();
            }

            var escaped = this.input.charAt(this.index);
            this.index = this.index + 1;
            return Optional.of(new Tuple<>(escaped, this));
         */
}
/* private */ /* State */ append(/* char c */){/* 
            this.buffer.append(c);
            return this;
         */
}
/* public */ Optional</* State */> popAndAppend(/*  */){/* 
            return this.popAndAppendToTuple().map(Tuple::right);
         */
}
/* @Override
        public */ Optional<char*> createInitial(/*  */){/* 
            return Optional.empty();
         */
}
/* @Override
        public */ Optional<char*> fold(/* Optional<String> current, String element */){/* 
            return Optional.of(current.map(inner -> inner + element).orElse(element));
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
        return compileStatements(input, Main::compileRootSegment) + join(structs) + join(methods);
     */
}
/* private static */ char* join(/* List<String> list */){/* 
        return list.iter().collect(new Joiner()).orElse("");
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
            BiFunction<StringBuilder, String, StringBuilder> merger
     */){/* 
        return divideAll(input, folder)
                .iter()
                .foldRight(new StringBuilder(), (output, segment) -> merger.apply(output, compiler.apply(segment)))
                .toString();
     */
}
/* private static */ /* StringBuilder */ mergeStatements(/* StringBuilder output, String compiled */){/* 
        return output.append(compiled);
     */
}
/* private static */ List<char*> divideAll(/* String input, BiFunction<State, Character, State> folder */){/* 
        State state = new State(input);
        while (true) {
            var maybeNextTuple = state.pop();
            if (maybeNextTuple.isEmpty()) {
                break;
            }

            var nextTuple = maybeNextTuple.get();
            var next = nextTuple.left;
            var withoutNext = nextTuple.right;

            state = foldSingleQuotes(withoutNext, next)
                    .orElseGet(() -> folder.apply(withoutNext, next));
        }

        return state.advance().segments;
     */
}
/* private static */ Optional</* State */> foldSingleQuotes(/* State state, char next */){/* 
        if (next != '\'') {
            return Optional.empty();
        }

        var appended = state.append(next);
        return appended.popAndAppendToTuple()
                .flatMap(maybeSlash -> maybeSlash.left == '\\' ? maybeSlash.right.popAndAppend() : Optional.of(maybeSlash.right))
                .flatMap(State::popAndAppend);
     */
}
/* private static */ /* State */ foldStatementChar(/* State state, char c */){/* 
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
/* private static */ Optional<char*> compileClass(/* String stripped */){/* 
        return compileStructure(stripped, "class ");
     */
}
/* if */(/* paramStart >= 0 */){/* 
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
                    return Optional.of("");
                }
            }
         */
}
/* if */(/* nameSeparator >= 0 */){/* 
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
         */
}
/* switch */(/* stripped */){/* 
            case "int" -> {
                return "int";
            }
            case "void" -> {
                return "void";
            }
            case "String" -> {
                return "char*";
            }
         */
}
/* }

    private static */ /* StringBuilder */ mergeValues(/* StringBuilder builder, String element */){/* 
        if (builder.isEmpty()) {
            return builder.append(element);
         */
}
/* }

    private static */ /* State */ foldValueChar(/* State state, char c */){/* 
        if (c == ',') {
            return state.advance();
         */
}
