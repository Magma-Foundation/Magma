/* private */struct Type {/* String stringify(); *//* String generate(); *//* boolean equalsTo(Type other); *//* Type strip(); *//* boolean isParameterized(); *//*  */
};
/* private @ */struct Actual {/*  */
};
/* private static */struct StandardLibrary {/*  */
};
/* private static */struct Lists {/*  */
};
/* private */struct List_char_ref {/* List<T> addLast(T element); *//* Iterator<T> iterate(); *//* boolean contains(T element, BiFunction<T, T, Boolean> equator); *//* boolean equalsTo(List<T> others, BiFunction<T, T, Boolean> equator); *//*  */
};
/* private static */struct DivideState {
	/* private */ struct List_char_ref segments;
	/* private */ char* buffer;
	/* private */ int depth;/* private DivideState(List<String> segments, String buffer, int depth) {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
        } *//* public DivideState() {
            this(new ArrayList<String>(), "", 0);
        } *//*  */
};
/* private */struct CompileState(
            List<String> structs,
            List<String> functions, Map<String, Function<List<Type>, Optional<CompileState>>> expandables,
            List<ObjectType> expansions, List<String> typeParams) {/* public CompileState() {
            this(new ArrayList<>(), new ArrayList<>(), new HashMap<>(), new ArrayList<>(), new ArrayList<>());
        } *//*  */
};
/* private */struct Tuple<A, B>(A left, B right) {/*  */
};
/* private static */struct Iterators {/*  */
};
/* private */struct Ref(Type type) implements Type {/*  */
};
/* private */struct ObjectType(String name, List<Type> arguments) implements Type {/*  */
};
/* private */struct Placeholder(String value) implements Type {/*  */
};
/* private */struct Definition(List<String> annotations, String afterAnnotations, Type type, String name,
                              List<String> typeParams) {/*  */
};
/* private */struct TypeParam(String input) implements Type {/*  */
};
/* private */struct List_/* T> */ {/* List<T> addLast(T element); *//* Iterator<T> iterate(); *//* boolean contains(T element, BiFunction<T, T, Boolean> equator); *//* boolean equalsTo(List<T> others, BiFunction<T, T, Boolean> equator); *//*  */
};
/* public */struct Main {/* ' && appended.isShallow()) {
            return appended.advance().exit();
        } *//* if (c == '{') {
            return appended.enter();
        }
        if (c == '} *//* ') {
            return appended.exit();
        } *//* return appended; *//*  */
};

	/* private static */ void* allocate(/* int length */);
	/* public static */ struct List_T of(/* T... elements */);/*  {
            return new ArrayList<>(elements, elements.length);
        } */
	/* private */ /* DivideState */ advance(/*  */);/*  {
            this.segments = this.segments.addLast(this.buffer);
            this.buffer = "";
            return this;
        } */
	/* private */ /* DivideState */ append(/* char c */);/*  {
            this.buffer = this.buffer + c;
            return this;
        } */
	/* public */ /* boolean */ isLevel(/*  */);/*  {
            return this.depth == 0;
        } */
	/* public */ /* DivideState */ enter(/*  */);/*  {
            this.depth++;
            return this;
        } */
	/* public */ /* DivideState */ exit(/*  */);/*  {
            this.depth--;
            return this;
        } */
	/* public */ /* boolean */ isShallow(/*  */);/*  {
            return this.depth == 1;
        } */
	/* private */ struct Optional_/* CompileState */ expand(/* ObjectType expansion */);/*  {
            if (this.expansions.contains(expansion, ObjectType::equalsTo)) {
                return Optional.empty();
            }

            return this.addExpansion(expansion)
                    .findExpandable(expansion.name)
                    .flatMap(expandable -> expandable.apply(expansion.arguments));
        } */
	/* private */ /* CompileState */ addExpansion(/* ObjectType type */);/*  {
            return new CompileState(this.structs, this.functions, this.expandables, this.expansions.addLast(type), this.typeParams);
        } */
	/* public */ /* CompileState */ addStruct(/* String struct */);/*  {
            return new CompileState(this.structs.addLast(struct), this.functions, this.expandables, this.expansions, this.typeParams);
        } */
	/* public */ /* CompileState */ addExpandable(/* String name, Function<List<Type>, Optional<CompileState>> expandable */);/*  {
            this.expandables.put(name, expandable);
            return this;
        } */
	/* public Optional<Function<List<Type>, */ struct Optional_/* CompileState>> */ findExpandable(/* String name */);/*  {
            if (this.expandables.containsKey(name)) {
                return Optional.of(this.expandables.get(name));
            }
            return Optional.empty();
        } */
	/* public */ /* CompileState */ addTypeParameters(/* List<String> typeParams */);/*  {
            return new CompileState(this.structs, this.functions, this.expandables, this.expansions, typeParams);
        } */
	/* public */ /* CompileState */ addFunction(/* String function */);/*  {
            return new CompileState(this.structs, this.functions.addLast(function), this.expandables, this.expansions, this.typeParams);
        } */
	/* public static */ struct Iterator_T fromOptional(/* Optional<T> optional */);/*  {
            return new HeadedIterator<>(optional.<Head<T>>map(SingleHead::new).orElseGet(EmptyHead::new));
        } */
	/* public */ char* stringify(/*  */);/*  {
            return this.type.stringify() + "_ref";
        } */
	/* public */ char* generate(/*  */);/*  {
            return this.type.generate() + "*";
        } */
	/* public */ /* boolean */ equalsTo(/* Type other */);/*  {
            return other instanceof Ref ref && this.type.equalsTo(ref.type);
        } */
	/* public */ /* Type */ strip(/*  */);/*  {
            return new Ref(this.type.strip());
        } */
	/* public */ /* boolean */ isParameterized(/*  */);/*  {
            return this.type.isParameterized();
        } */
	/* public */ char* stringify(/*  */);/*  {
            return this.generate();
        } */
	/* public */ char* generate(/*  */);/*  {
            return "struct " + this.name + this.joinArguments();
        } */
	/* public */ /* boolean */ equalsTo(/* Type other */);/*  {
            return other instanceof ObjectType objectType
                    && this.name.equals(objectType.name)
                    && this.arguments.equalsTo(objectType.arguments, Type::equalsTo);
        } */
	/* public */ /* Type */ strip(/*  */);/*  {
            var newArguments = this.arguments.iterate()
                    .map(Type::strip)
                    .collect(new ListCollector<>());

            return new ObjectType(this.name, newArguments);
        } */
	/* public */ /* boolean */ isParameterized(/*  */);/*  {
            return this.arguments.iterate().anyMatch(Type::isParameterized);
        } */
	/* private */ char* joinArguments(/*  */);/*  {
            return this.arguments.iterate()
                    .map(Type::stringify)
                    .collect(new Joiner("_"))
                    .map(result -> "_" + result)
                    .orElse("");
        } */
	/* public */ char* stringify(/*  */);/*  {
            return generatePlaceholder(this.value);
        } */
	/* public */ char* generate(/*  */);/*  {
            return generatePlaceholder(this.value);
        } */
	/* public */ /* boolean */ equalsTo(/* Type other */);/*  {
            return other instanceof Placeholder placeholder && this.value.equals(placeholder.value);
        } */
	/* public */ /* Type */ strip(/*  */);/*  {
            return this;
        } */
	/* public */ /* boolean */ isParameterized(/*  */);/*  {
            return false;
        } */
	/* private */ char* generate(/*  */);/*  {
            return generatePlaceholder(this.afterAnnotations()) + " " + this.type().generate() + " " + this.name();
        } */
	/* public */ /* Definition */ mapType(/* Function<Type, Type> mapper */);/*  {
            return new Definition(this.annotations, this.afterAnnotations, mapper.apply(this.type), this.name, this.typeParams);
        } */
	/* public */ char* stringify(/*  */);/*  {
            return this.input;
        } */
	/* public */ char* generate(/*  */);/*  {
            return "template " + this.input;
        } */
	/* public */ /* boolean */ equalsTo(/* Type other */);/*  {
            return other instanceof TypeParam param && this.input.equals(param.input);
        } */
	/* public */ /* Type */ strip(/*  */);/*  {
            return Primitive.Void;
        } */
	/* public */ /* boolean */ isParameterized(/*  */);/*  {
            return true;
        } */
	/* public static */ /* void */ main(/*  */);/*  {
        try {
            var root = Paths.get(".", "src", "java", "magma");
            var source = root.resolve("main.java");
            var target = root.resolve("main.c");

            var input = Files.readString(source);
            Files.writeString(target, compile(input));

            new ProcessBuilder("clang.exe", target.toAbsolutePath().toString(), "-o", "main.exe")
                    .inheritIO()
                    .start()
                    .waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    } */
	/* private static */ char* compile(/* String input */);/*  {
        var compiled = compileStatements(new CompileState(), input, Main::compileRootSegment);
        var compiledState = compiled.left;
        
        var joinedStructs = join(compiledState.structs);
        var joinedMethods = join(compiledState.functions);

        return joinedStructs + joinedMethods + compiled.right + "\nint main(){\n\treturn 0;\n}\n";
    } */
	/* private static */ char* join(/* List<String> items */);/*  {
        return items.iterate()
                .collect(new Joiner())
                .orElse("");
    } */
	/* private static Tuple<CompileState, */ /* String> */ compileStatements(/* CompileState initial, String input, BiFunction<CompileState, String, Tuple<CompileState, String>> mapper */);/*  {
        return compileAll(initial, input, Main::foldStatementChar, mapper, Main::merge);
    } */
	/* private static Tuple<CompileState, */ /* String> */ compileAll(/* CompileState initial, String input, BiFunction<DivideState, Character, DivideState> folder, BiFunction<CompileState, String, Tuple<CompileState, String>> mapper, BiFunction<String, String, String> merger */);/*  {
        var tuple = parseAll(initial, input, folder, mapper);
        return new Tuple<>(tuple.left, generateAll(tuple.right, merger));
    } */
	/* private static <T> Tuple<CompileState, */ struct List_/* T> */ parseAll(/* 
            CompileState initial,
            String input,
            BiFunction<DivideState, Character, DivideState> folder,
            BiFunction<CompileState, String, Tuple<CompileState, T>> mapper
     */);/*  {
        var segments = divide(input, folder);

        var tuple = new Tuple<>(initial, (List<T>) new ArrayList<T>());
        var folded = segments.iterate().fold(tuple, (tuple0, element) -> {
            var mapped = mapper.apply(tuple0.left, element);
            return new Tuple<>(mapped.left, tuple0.right.addLast(mapped.right));
        });

        return new Tuple<CompileState, List<T>>(folded.left, tuple.right);
    } */
	/* private static */ char* generateAll(/* List<String> elements, BiFunction<String, String, String> merger */);/*  {
        return elements.iterate().fold("", merger);
    } */
	/* private static */ char* merge(/* String buffer, String element */);/*  {
        return buffer + element;
    } */
	/* private static */ struct List_char_ref divide(/* String input, BiFunction<DivideState, Character, DivideState> folder */);/*  {
        var current = new DivideState();
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            current = folder.apply(current, c);
        }

        return current.advance().segments;
    } */
	/* private static */ /* DivideState */ foldStatementChar(/* DivideState state, char c */);/*  {
        var appended = state.append(c);
        if (c == ';' && appended.isLevel()) {
            return appended.advance();
        }
        if (c == '} */
	/* private static Tuple<CompileState, String> compileRootSegment(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return new Tuple<>(state, "");
        }

        return createStructureRule("class ").apply(state, input)
                .orElseGet(() -> new Tuple<>(state, "\n\t" + generatePlaceholder(stripped.strip())));
    } */
	/* private static BiFunction<CompileState, String, Optional<Tuple<CompileState, String>>> createStructureRule(String infix) {
        return (state, input) -> compileFirst(input, infix, (beforeKeyword, afterKeyword) -> {
            return compileFirst(afterKeyword, "{", (beforeContent, withEnd) -> {
                return compileSuffix(withEnd.strip(), "}", content1 -> {
                    return compileOr(state, beforeContent, Lists.of(
                            createStructureWithTypeParamsRule(beforeKeyword, content1),
                            createStructureWithoutTypeParamsRule(beforeKeyword, content1)
                    ));
                });
            });
        });
    } */
	/* private static BiFunction<CompileState, String, Optional<Tuple<CompileState, String>>> createStructureWithTypeParamsRule(String beforeKeyword, String content) {
        return (state, input) -> {
            return compileSuffix(input.strip(), ">", withoutEnd -> {
                return compileFirst(withoutEnd, "<", (name, typeParameters) -> {
                    var typeParams = divide(typeParameters, Main::foldValueChar)
                            .iterate()
                            .map(String::strip)
                            .collect(new ListCollector<>());

                    return Optional.of(new Tuple<>(state.addExpandable(name, (typeArguments) -> {
                        return assembleStructure(state, beforeKeyword, name, typeParams, typeArguments, content);
                    }), ""));
                });
            });
        };
    } */
	/* private static DivideState foldValueChar(DivideState state, char c) {
        if (c == ',') {
            return state.advance();
        }
        return state.append(c);
    } */
	/* private static BiFunction<CompileState, String, Optional<Tuple<CompileState, String>>> createStructureWithoutTypeParamsRule(String beforeKeyword, String content) {
        return (state, name) -> {
            return assembleStructure(state, beforeKeyword, name, new ArrayList<String>(), new ArrayList<Type>(), content).map(newState -> {
                return new Tuple<>(newState, "");
            });
        };
    } */
	/* private static Optional<CompileState> assembleStructure(
            CompileState state,
            String beforeStruct,
            String name,
            List<String> typeParams,
            List<Type> typeArguments,
            String content
    ) {
        var statementsTuple = compileStatements(state.addTypeParameters(typeParams), content, Main::compileClassSegment);
        var generated = generatePlaceholder(beforeStruct.strip()) + new ObjectType(name.strip(), typeArguments).generate() + " {" + statementsTuple.right + "\n};\n";
        var added = statementsTuple.left.addStruct(generated);
        return Optional.of(added);
    } */
	/* private static <T> Optional<T> compileSymbol(String input, Function<String, Optional<T>> mapper) {
        if (!isSymbol(input)) {
            return Optional.empty();
        }

        return mapper.apply(input);
    } */
	/* private static boolean isSymbol(String input) {
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (Character.isLetter(c)) {
                continue;
            }
            return false;
        }
        return true;
    } */
	/* private static Tuple<CompileState, String> compileClassSegment(CompileState state, String input) {
        return compileOrPlaceholder(state, input, Lists.of(
                createStructureRule("class "),
                createStructureRule("interface "),
                createStructureRule("record "),
                Main::compileDefinitionStatement,
                Main::compileMethod
        ));
    } */
	/* private static Optional<Tuple<CompileState, String>> compileMethod(CompileState state, String input) {
        return compileFirst(input, "(", (definitionString, withParams) -> {
            return compileFirst(withParams, ")", (params, oldContent) -> {
                return parseDefinition(state, definitionString).flatMap(definitionTuple -> {
                    var definitionState = definitionTuple.left;
                    var definition = definitionTuple.right;

                    Definition newDefinition;
                    String newContent;
                    if (definition.annotations.contains("Actual", String::equals) || oldContent.equals(";")) {
                        newDefinition = definition.mapType(Type::strip);
                        newContent = ";";
                    }
                    else {
                        newContent = ";" + generatePlaceholder(oldContent);
                        newDefinition = definition;
                    }

                    var generatedHeader = "\n\t" + newDefinition.generate() + "(" + generatePlaceholder(params) + ")";
                    var generated = generatedHeader + newContent;
                    return Optional.of(new Tuple<>(definitionState.addFunction(generated), ""));
                });
            });
        });
    } */
	/* private static Tuple<CompileState, String> compileOrPlaceholder(
            CompileState state,
            String input,
            List<BiFunction<CompileState, String, Optional<Tuple<CompileState, String>>>> rules
    ) {
        return compileOr(state, input, rules).orElseGet(() -> new Tuple<>(state, generatePlaceholder(input.strip())));
    } */
	/* private static <T> Optional<Tuple<CompileState, T>> compileOr(
            CompileState state,
            String input,
            List<BiFunction<CompileState, String, Optional<Tuple<CompileState, T>>>> rules
    ) {
        return rules.iterate()
                .map(rule -> rule.apply(state, input))
                .flatMap(Iterators::fromOptional)
                .next();
    } */
	/* private static Optional<Tuple<CompileState, String>> compileDefinitionStatement(CompileState state, String input) {
        return compileSuffix(input.strip(), ";", withoutEnd -> {
            return parseDefinition(state, withoutEnd)
                    .map(tuple -> new Tuple<>(tuple.left, tuple.right.generate()))
                    .map(tuple -> new Tuple<>(tuple.left, "\n\t" + tuple.right + ";"));
        });
    } */
	/* private static Optional<Tuple<CompileState, Definition>> parseDefinition(CompileState state, String input) {
        return compileInfix(input.strip(), " ", Main::findLast, (beforeName, rawName) -> {
            return compileInfix(beforeName.strip(), " ", Main::findLast, (beforeType, type) -> {
                var strippedBeforeType = beforeType.strip();
                return compileInfix(strippedBeforeType, "\n", Main::findLast, (annotationsString, afterAnnotations) -> {
                    var annotations = divide(annotationsString, foldWithDelimiter('\n'))
                            .iterate()
                            .map(slice -> slice.substring(1))
                            .map(String::strip)
                            .collect(new ListCollector<>());

                    return assembleDefinition(state, annotations, afterAnnotations.strip(), rawName, type);
                }).or(() -> {
                    return assembleDefinition(state, new ArrayList<>(), strippedBeforeType, rawName, type);
                });
            });
        });
    } */
	/* private static Optional<Tuple<CompileState, Definition>> assembleDefinition(CompileState state, List<String> annotations, String afterAnnotations, String rawName, String type) {
        return compileSuffix(afterAnnotations.strip(), ">", withoutEnd -> {
            return compileFirst(withoutEnd, "<", (beforeTypeParams, typeParams) -> {
                var typeParamsTuple = Main.parseValues(state, typeParams, (state1, s1) -> new Tuple<>(state1, s1.strip()));
                return getCompileStateDefinitionTuple(typeParamsTuple.left, annotations, beforeTypeParams.strip(), typeParamsTuple.right, rawName, type);
            });
        }).or(() -> {
            return getCompileStateDefinitionTuple(state, annotations, afterAnnotations, new ArrayList<>(), rawName, type);
        });
    } */
	/* private static Optional<Tuple<CompileState, Definition>> getCompileStateDefinitionTuple(CompileState state, List<String> annotations, String afterAnnotations, List<String> typeParams, String rawName, String type) {
        return compileSymbol(rawName.strip(), name -> {
            var typeTuple = parseType(state.addTypeParameters(typeParams), type);
            return Optional.of(new Tuple<>(typeTuple.left, new Definition(annotations, afterAnnotations, typeTuple.right, name, typeParams)));
        });
    } */
	/* private static BiFunction<DivideState, Character, DivideState> foldWithDelimiter(char delimiter) {
        return (state, c) -> {
            if (c == delimiter) {
                return state.advance();
            }
            return state.append(c);
        };
    } */
	/* private static Tuple<CompileState, Type> parseType(CompileState state, String input) {
        return compileOr(state, input, Lists.of(
                typed(Main::parsePrimitive),
                typed(Main::parseTemplate),
                typed(Main::parseArray),
                typed(Main::parseTypeParam)
        )).orElseGet(() -> new Tuple<>(state, new Placeholder(input.strip())));
    } */
	/* private static Optional<Tuple<CompileState, Type>> parseTypeParam(CompileState state, String input) {
        var stripped = input.strip();
        if (state.typeParams.contains(stripped, String::equals)) {
            return Optional.of(new Tuple<>(state, new TypeParam(stripped)));
        }

        return Optional.empty();
    } */
	/* private static Optional<Tuple<CompileState, Type>> parseArray(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.endsWith("[]")) {
            var slice = input.substring(0, stripped.length() - "[]".length());
            var childTuple = parseType(state, slice);
            return Optional.of(new Tuple<>(childTuple.left, new Ref(childTuple.right)));
        }

        return Optional.empty();
    } */
	/* private static <T extends R, R> BiFunction<CompileState, String, Optional<Tuple<CompileState, R>>> typed(BiFunction<CompileState, String, Optional<Tuple<CompileState, T>>> rule) {
        return (state, input) -> rule.apply(state, input).map(tuple -> new Tuple<>(tuple.left, tuple.right));
    } */
	/* private static Optional<Tuple<CompileState, Type>> parsePrimitive(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.equals("String")) {
            return Optional.of(new Tuple<>(state, new Ref(Primitive.Char)));
        }
        if (stripped.equals("int")) {
            return Optional.of(new Tuple<>(state, Primitive.Int));
        }
        return Optional.empty();
    } */
	/* private static Optional<Tuple<CompileState, ObjectType>> parseTemplate(CompileState oldState, String input) {
        return compileSuffix(input.strip(), ">", withoutEnd -> {
            return compileFirst(withoutEnd, "<", (base, argumentsString) -> {
                var argumentsTuple = parseValues(oldState, argumentsString, Main::parseType);

                var argumentsState = argumentsTuple.left;
                var arguments = argumentsTuple.right;

                var expansion = new ObjectType(base, arguments);
                CompileState withExpansion;
                if (expansion.isParameterized()) {
                    withExpansion = argumentsState;
                }
                else {
                    withExpansion = argumentsState.expand(expansion).orElse(argumentsState);
                }

                return Optional.of(new Tuple<>(withExpansion, expansion));
            });
        });
    } */
	/* private static <T> Tuple<CompileState, List<T>> parseValues(CompileState oldState, String argumentsString, BiFunction<CompileState, String, Tuple<CompileState, T>> mapper) {
        return parseAll(oldState, argumentsString, Main::foldValueChar, mapper);
    } */
	/* private static Optional<Integer> findLast(String input, String infix) {
        var index = input.lastIndexOf(infix);
        return index == -1 ? Optional.empty() : Optional.of(index);
    } */
	/* private static <T> Optional<T> compileSuffix(String input, String suffix, Function<String, Optional<T>> mapper) {
        if (input.endsWith(suffix)) {
            var content = input.substring(0, input.length() - suffix.length());
            return mapper.apply(content);
        }

        return Optional.empty();
    } */
	/* private static <T> Optional<T> compileFirst(String stripped, String infix, BiFunction<String, String, Optional<T>> mapper) {
        return compileInfix(stripped, infix, Main::findFirst, mapper);
    } */
	/* private static <T> Optional<T> compileInfix(String input, String infix, BiFunction<String, String, Optional<Integer>> locate, BiFunction<String, String, Optional<T>> mapper) {
        return locate.apply(input, infix).flatMap(index -> {
            var left = input.substring(0, index);
            var right = input.substring(index + infix.length());
            return mapper.apply(left, right);
        });
    } */
	/* private static Optional<Integer> findFirst(String input, String infix) {
        var index = input.indexOf(infix);
        return index == -1 ? Optional.empty() : Optional.of(index);
    } */
	/* private static String generatePlaceholder(String input) {
        var replaced = input
                .replace("content-start", "content-start")
                .replace("content-end", "content-end");

        return "content-start" + replaced + " content-end";
    } */
	/* private enum Primitive implements Type {
        Char("char"),
        Int("int"),
        Void("void");

        private final String value;

        Primitive(String value) {
            this.value = value;
        }

        @Override
        public String stringify() {
            return this.value;
        }

        @Override
        public String generate() {
            return this.value;
        }

        @Override
        public boolean equalsTo(Type other) {
            return this == other;
        }

        @Override
        public Type strip() {
            return this;
        }

        @Override
        public boolean isParameterized() {
            return false;
        }
    } */
	/* } */
int main(){
	return 0;
}
