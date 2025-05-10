/* private */struct Type {/* String stringify(); *//* String generate(); *//* boolean equalsTo(Type other); *//* Type strip(); *//* boolean isParameterized(); *//*  */
};
 /* private @ */struct Actual {/*  */
};
 /* private */struct Parameter {/*  */
};
 /* private static */struct StandardLibrary {/*  */
};
 /* private static */struct Lists {/*  */
};
 /* private */struct List_char_ref {/* List<T> addLast(T element); *//* Iterator<T> iterate(); *//* boolean contains(T element, BiFunction<T, T, Boolean> equator); *//* boolean equalsTo(List<T> others, BiFunction<T, T, Boolean> equator); *//* int size(); *//* Optional<Tuple<List<T>, T>> removeLast(); *//* boolean isEmpty(); *//*  */
};
 
	/* private */ struct DivideState List_advance(/*  */);/*  {
            this.segments = this.segments.addLast(this.buffer);
            this.buffer = "";
            return this;
        } */ 
	/* private */ struct DivideState List_append(/* char c */);/*  {
            this.buffer = this.buffer + c;
            return this;
        } */ 
	/* public */ int List_isLevel(/*  */);/*  {
            return this.depth == 0;
        } */ 
	/* public */ struct DivideState List_enter(/*  */);/*  {
            this.depth++;
            return this;
        } */ 
	/* public */ struct DivideState List_exit(/*  */);/*  {
            this.depth--;
            return this;
        } */ 
	/* public */ int List_isShallow(/*  */);/*  {
            return this.depth == 1;
        } */ /* private static */struct DivideState {
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
 
	/* private */ struct Optional_CompileState CompileState_expand(/* ObjectType expansion */);/*  {
            if (this.expansions.contains(expansion, ObjectType::equalsTo)) {
                return Optional.empty();
            }

            return this.addExpansion(expansion)
                    .findExpandable(expansion.name)
                    .flatMap(expandable -> expandable.apply(expansion.arguments));
        } */ 
	/* private */ struct CompileState CompileState_addExpansion(/* ObjectType type */);/*  {
            return new CompileState(this.generated, this.expandables, this.expansions.addLast(type), this.typeParams, this.maybeCurrentStructName);
        } */ 
	/* public */ struct CompileState CompileState_addGenerative(/* String struct */);/*  {
            return new CompileState(this.generated.addLast(struct), this.expandables, this.expansions, this.typeParams, this.maybeCurrentStructName);
        } */ 
	/* public */ struct CompileState CompileState_addExpandable(/* String name, Function<List<Type>, Optional<CompileState>> expandable */);/*  {
            this.expandables.put(name, expandable);
            return this;
        } */ /* private */struct List_Type {/* List<T> addLast(T element); *//* Iterator<T> iterate(); *//* boolean contains(T element, BiFunction<T, T, Boolean> equator); *//* boolean equalsTo(List<T> others, BiFunction<T, T, Boolean> equator); *//* int size(); *//* Optional<Tuple<List<T>, T>> removeLast(); *//* boolean isEmpty(); *//*  */
};
 
	/* public */ struct Optional_Function_List_Type_Optional_CompileState CompileState_findExpandable(/* String name */);/*  {
            if (this.expandables.containsKey(name)) {
                return Optional.of(this.expandables.get(name));
            }
            return Optional.empty();
        } */ 
	/* public */ struct CompileState List_addTypeParameters(/* List<String> typeParams */);/*  {
            return new CompileState(this.generated, this.expandables, this.expansions, typeParams, this.maybeCurrentStructName);
        } */ 
	/* public */ struct CompileState List_withStructureName(/* String name */);/*  {
            return new CompileState(this.generated, this.expandables, this.expansions, this.typeParams, Optional.of(name));
        } */ /* private */struct CompileState {/* public CompileState() {
            this(new ArrayList<>(), new HashMap<>(), new ArrayList<>(), new ArrayList<>(), Optional.empty());
        } *//*  */
};
 
	/* public */ struct Optional_char_ref Joiner_createInitial(/*  */);/*  {
            return Optional.empty();
        } */ 
	/* public */ struct Optional_char_ref Joiner_fold(/* Optional<String> maybeCurrent, String element */);/*  {
            return Optional.of(maybeCurrent.map(current -> current + this.delimiter + element).orElse(element));
        } */ /* private */struct Joiner {/* private Joiner() {
            this("");
        } *//*  */
};
 /* private static */struct Iterators {/*  */
};
 
	/* public */ char* Ref_stringify(/*  */);/*  {
            return this.type.stringify() + "_ref";
        } */ 
	/* public */ char* Ref_generate(/*  */);/*  {
            return this.type.generate() + "*";
        } */ 
	/* public */ int Ref_equalsTo(/* Type other */);/*  {
            return other instanceof Ref ref && this.type.equalsTo(ref.type);
        } */ 
	/* public */ struct Type Ref_strip(/*  */);/*  {
            return new Ref(this.type.strip());
        } */ 
	/* public */ int Ref_isParameterized(/*  */);/*  {
            return this.type.isParameterized();
        } */ /* private */struct Ref {/*  */
};
 
	/* public */ char* ObjectType_stringify(/*  */);/*  {
            return this.name + this.joinArguments();
        } */ 
	/* public */ char* ObjectType_generate(/*  */);/*  {
            return "struct " + this.stringify();
        } */ 
	/* public */ int ObjectType_equalsTo(/* Type other */);/*  {
            return other instanceof ObjectType objectType
                    && this.name.equals(objectType.name)
                    && this.arguments.equalsTo(objectType.arguments, Type::equalsTo);
        } */ 
	/* public */ struct Type ObjectType_strip(/*  */);/*  {
            var newArguments = this.arguments.iterate()
                    .map(Type::strip)
                    .collect(new ListCollector<>());

            return new ObjectType(this.name, newArguments);
        } */ 
	/* public */ int ObjectType_isParameterized(/*  */);/*  {
            return this.arguments.iterate().anyMatch(Type::isParameterized);
        } */ 
	/* private */ char* ObjectType_joinArguments(/*  */);/*  {
            return this.arguments.iterate()
                    .map(Type::stringify)
                    .collect(new Joiner("_"))
                    .map(result -> "_" + result)
                    .orElse("");
        } */ /* private */struct ObjectType {/*  */
};
 
	/* public */ char* Placeholder_stringify(/*  */);/*  {
            return generatePlaceholder(this.value);
        } */ 
	/* public */ char* Placeholder_generate(/*  */);/*  {
            return generatePlaceholder(this.value);
        } */ 
	/* public */ int Placeholder_equalsTo(/* Type other */);/*  {
            return other instanceof Placeholder placeholder && this.value.equals(placeholder.value);
        } */ 
	/* public */ struct Type Placeholder_strip(/*  */);/*  {
            return this;
        } */ 
	/* public */ int Placeholder_isParameterized(/*  */);/*  {
            return false;
        } */ /* private */struct Placeholder {/*  */
};
 
	/* private */ char* Definition_generate(/*  */);/*  {
            return generatePlaceholder(this.afterAnnotations()) + " " + this.type().generate() + " " + this.name();
        } */ 
	/* public */ struct Definition Definition_mapType(/* Function<Type, Type> mapper */);/*  {
            return new Definition(this.annotations, this.afterAnnotations, mapper.apply(this.type), this.name, this.typeParams);
        } */ 
	/* public */ struct Definition Definition_mapName(/* Function<String, String> mapper */);/*  {
            return new Definition(this.annotations, this.afterAnnotations, this.type, mapper.apply(this.name), this.typeParams);
        } */ /* private */struct Definition {/*  */
};
 
	/* public */ char* TypeParam_stringify(/*  */);/*  {
            return this.input;
        } */ 
	/* public */ char* TypeParam_generate(/*  */);/*  {
            return "template " + this.input;
        } */ 
	/* public */ int TypeParam_equalsTo(/* Type other */);/*  {
            return other instanceof TypeParam param && this.input.equals(param.input);
        } */ 
	/* public */ struct Type TypeParam_strip(/*  */);/*  {
            return Primitive.Void;
        } */ 
	/* public */ int TypeParam_isParameterized(/*  */);/*  {
            return true;
        } */ /* private */struct TypeParam {/*  */
};
 
	/* public static */ void TypeParam_main(/*  */);/*  {
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
	/* private static */ char* TypeParam_compile(/* String input */);/*  {
        var compiled = compileStatements(new CompileState(), input, Main::compileRootSegment);
        var compiledState = compiled.left;

        var joined = join(compiledState.generated);
        return joined + compiled.right + "\nint main(){\n\treturn 0;\n}\n";
    } */ 
	/* private static */ char* TypeParam_join(/* List<String> items */);/*  {
        return joinWithDelimiter(items, " ");
    } */ 
	/* private static */ char* TypeParam_joinWithDelimiter(/* List<String> items, String delimiter */);/*  {
        return items.iterate()
                .collect(new Joiner(delimiter))
                .orElse("");
    } */ /* private */struct Tuple_CompileState_char_ref {/*  */
};
 
	/* private static */ struct Tuple_CompileState_char_ref TypeParam_compileStatements(/* CompileState initial, String input, BiFunction<CompileState, String, Tuple<CompileState, String>> mapper */);/*  {
        return compileAll(initial, input, Main::foldStatementChar, mapper, Main::merge);
    } */ 
	/* private static */ struct Tuple_CompileState_char_ref Tuple_compileAll(/* CompileState initial, String input, BiFunction<DivideState, Character, DivideState> folder, BiFunction<CompileState, String, Tuple<CompileState, String>> mapper, BiFunction<String, String, String> merger */);/*  {
        var tuple = parseAll(initial, input, folder, mapper);
        return new Tuple<>(tuple.left, generateAll(tuple.right, merger));
    } */ 
	/* private static */ char* Tuple_generateAll(/* List<String> elements, BiFunction<String, String, String> merger */);/*  {
        return elements.iterate().fold("", merger);
    } */ 
	/* private static */ char* Tuple_merge(/* String buffer, String element */);/*  {
        return buffer + element;
    } */ 
	/* private static */ struct List_char_ref Tuple_divide(/* String input, BiFunction<DivideState, Character, DivideState> folder */);/*  {
        var current = new DivideState();
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            current = folder.apply(current, c);
        }

        return current.advance().segments;
    } */ 
	/* private static */ struct DivideState Tuple_foldStatementChar(/* DivideState state, char c */);/*  {
        var appended = state.append(c);
        if (c == ';' && appended.isLevel()) {
            return appended.advance();
        }
        if (c == '} */ /* public */struct Main {/* ' && appended.isShallow()) {
            return appended.advance().exit();
        } *//* if (c == '{') {
            return appended.enter();
        }
        if (c == '} *//* ') {
            return appended.exit();
        } *//* return appended; *//*  */
};

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
                            createStructWithParametersRule(beforeKeyword, content1),
                            createStructWithoutParametersRule(beforeKeyword, content1)
                    ));
                });
            });
        });
    } */
	/* private static BiFunction<CompileState, String, Optional<Tuple<CompileState, String>>> createStructWithoutParametersRule(String beforeKeyword, String content1) {
        return (state1, s) -> {
            return createStructureWithMaybeTypeParametersRule(state1, beforeKeyword, s, content1, new ArrayList<>());
        };
    } */
	/* private static BiFunction<CompileState, String, Optional<Tuple<CompileState, String>>> createStructWithParametersRule(String beforeKeyword, String content1) {
        return (state, input) -> {
            return compileInfix(input, ")", Main::findLast, (withParameters, afterParameters) -> {
                return compileFirst(withParameters, "(", (beforeParameters, parameters) -> {
                    var parametersTuple = parseValues(state, parameters, Main::compileParameter);
                    return createStructureWithMaybeTypeParametersRule(parametersTuple.left, beforeKeyword, beforeParameters, content1, parametersTuple.right);
                });
            });
        };
    } */
	/* private static Tuple<CompileState, Parameter> compileParameter(CompileState state, String input) {
        return parseDefinition(state, input)
                .map(value -> new Tuple<CompileState, Parameter>(value.left, value.right))
                .orElseGet(() -> new Tuple<>(state, new Placeholder(input)));
    } */
	/* private static Optional<Tuple<CompileState, String>> createStructureWithMaybeTypeParametersRule(
            CompileState state,
            String beforeKeyword,
            String beforeContent,
            String content1,
            List<Parameter> parameters
    ) {
        return compileOr(state, beforeContent, Lists.of(
                createStructureWithTypeParamsRule(beforeKeyword, content1, parameters),
                createStructureWithoutTypeParamsRule(beforeKeyword, content1, parameters)
        ));
    } */
	/* private static BiFunction<CompileState, String, Optional<Tuple<CompileState, String>>> createStructureWithTypeParamsRule(String beforeKeyword, String content, List<Parameter> parameters) {
        return (state, input) -> {
            return compileSuffix(input.strip(), ">", withoutEnd -> {
                return compileFirst(withoutEnd, "<", (name, typeParameters) -> {
                    var typeParams = divide(typeParameters, Main::foldValueChar)
                            .iterate()
                            .map(String::strip)
                            .collect(new ListCollector<>());

                    return Optional.of(new Tuple<>(state.addExpandable(name, (typeArguments) -> {
                        return assembleStructure(state, beforeKeyword, name, typeParams, typeArguments, parameters, content);
                    }), ""));
                });
            });
        };
    } */
	/* private static DivideState foldValueChar(DivideState state, char c) {
        if (c == ',' && state.isLevel()) {
            return state.advance();
        }
        var appended = state.append(c);
        if (c == '<') {
            return appended.enter();
        }
        if (c == '>') {
            return appended.exit();
        }
        return appended;
    } */
	/* private static BiFunction<CompileState, String, Optional<Tuple<CompileState, String>>> createStructureWithoutTypeParamsRule(
            String beforeKeyword,
            String content,
            List<Parameter> parameters
    ) {
        return (state, name) -> {
            return assembleStructure(state, beforeKeyword, name, new ArrayList<String>(), new ArrayList<Type>(), parameters, content).map(newState -> {
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
            List<Parameter> parameters,
            String content
    ) {
        var statementsTuple = compileStatements(state.withStructureName(name).addTypeParameters(typeParams), content, Main::compileClassSegment);
        var generated = generatePlaceholder(beforeStruct.strip()) + new ObjectType(name.strip(), typeArguments).generate() + " {" + statementsTuple.right + "\n};\n";
        var added = statementsTuple.left.addGenerative(generated);
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

                    if (!definition.typeParams.isEmpty()) {
                        return Optional.of(new Tuple<>(definitionTuple.left, ""));
                    }

                    Definition newDefinition;
                    String newContent;
                    if (definition.annotations.contains("Actual", String::equals) || oldContent.equals(";")) {
                        newDefinition = definition.mapType(Type::strip);
                        newContent = ";";
                    }
                    else {
                        newContent = ";" + generatePlaceholder(oldContent);
                        newDefinition = definition.mapName(name -> {
                            return state.maybeCurrentStructName.map(currentStructName -> currentStructName + "_" + name).orElse(name);
                        });
                    }

                    var generatedHeader = "\n\t" + newDefinition.generate() + "(" + generatePlaceholder(params) + ")";
                    var generated = generatedHeader + newContent;
                    return Optional.of(new Tuple<>(definitionState.addGenerative(generated), ""));
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
            return compileInfix(() -> {
                var divisions = divide(beforeName, Main::foldTypeSeparator);

                if (divisions.size() >= 2) {
                    var maybeRemoved = divisions.removeLast();
                    if (maybeRemoved.isPresent()) {
                        var removed = maybeRemoved.get();
                        var joined = joinWithDelimiter(removed.left, " ");
                        return Optional.of(new Tuple<>(joined, removed.right));
                    }
                }

                return Optional.empty();
            }, (beforeType, type) -> {
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
	/* private static DivideState foldTypeSeparator(DivideState state, Character c) {
        if (c == ' ' && state.isLevel()) {
            return state.advance();
        }

        var appended = state.append(c);
        if (c == '<') {
            return appended.enter();
        }
        if (c == '>') {
            return appended.exit();
        }
        return appended;
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
                typed(Main::parseTypeParam),
                typed(Main::parseStructureType)
        )).orElseGet(() -> new Tuple<>(state, new Placeholder(input.strip())));
    } */
	/* private static Optional<Tuple<CompileState, Type>> parseStructureType(CompileState state, String input) {
        return compileSymbol(input, input1 -> Optional.of(new Tuple<>(state, new ObjectType(input1, new ArrayList<>()))));
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

        if (stripped.equals("int") || stripped.equals("boolean")) {
            return Optional.of(new Tuple<>(state, Primitive.Int));
        }

        if (stripped.equals("void")) {
            return Optional.of(new Tuple<>(state, Primitive.Void));
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
        return compileInfix(() -> split(input, infix, locate), mapper);
    } */
	/* private static <T> Optional<T> compileInfix(Supplier<Optional<Tuple<String, String>>> supplier, BiFunction<String, String, Optional<T>> mapper) {
        return supplier.get().flatMap(tuple -> {
            return mapper.apply(tuple.left, tuple.right);
        });
    } */
	/* private static Optional<Tuple<String, String>> split(String input, String infix, BiFunction<String, String, Optional<Integer>> locate) {
        return locate.apply(input, infix).map(index -> {
            var left = input.substring(0, index);
            var right = input.substring(index + infix.length());
            return new Tuple<>(left, right);
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
