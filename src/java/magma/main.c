/* private */struct Type {/* Type strip(); *//*  */
};
char* stringify(/*  */);
char* generate(/*  */);
int equalsTo(/* Type other */);
int isParameterized(/*  */);
/* private @ */struct Actual {/*  */
};
/* private */struct Parameter {/*  */
};
char* generate(/*  */);
/* private static */struct StandardLibrary {/*  */
};
/* private static */struct Lists {/*  */
};
/* private */struct List_char_ref {/* T get(int index); *//* T last(); *//*  */
};
char* stringify(/*  */);
char* generate(/*  */);
int equalsTo(/* Type other */);
int isParameterized(/*  */);
struct List_/* T */ addLast(/* T element */);
struct Iterator_/* T */ iterate(/*  */);
int contains(/* T element */, int (*)(/* T */, /* T */) equator);
int equalsTo(struct List_/* T */ others, int (*)(/* T */, /* T */) equator);
int size(/*  */);
struct Optional_Tuple_List_/* T */_/* T */ removeLast(/*  */);
int isEmpty(/*  */);
struct Optional_int indexOf(/* T element */, int (*)(/* T */, /* T */) equator);
struct List_/* T */ addAllLast(struct List_/* T */ others);
struct Iterator_/* T */ iterateReversed(/*  */);
/* private static */struct DivideState {
	/* private */ struct List_char_ref segments;
	/* private */ char* buffer;
	/* private */ int depth;/* private DivideState(List<String> segments, String buffer, int depth) {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
        } *//* public DivideState() {
            this(new ArrayList<String>(), "", 0);
        } *//* private DivideState advance() {
            this.segments = this.segments.addLast(this.buffer);
            this.buffer = "";
            return this;
        } *//* private DivideState append(char c) {
            this.buffer = this.buffer + c;
            return this;
        } *//* public DivideState enter() {
            this.depth++;
            return this;
        } *//* public DivideState exit() {
            this.depth--;
            return this;
        } *//*  */
};
/* public */ int isLevel(/*  */);/*  {
            return this.depth == 0;
        } */
/* public */ int isShallow(/*  */);/*  {
            return this.depth == 1;
        } */
/* private */struct List_Type {/* T get(int index); *//* T last(); *//*  */
};
char* stringify(/*  */);
char* generate(/*  */);
int equalsTo(/* Type other */);
int isParameterized(/*  */);
struct List_/* T */ addLast(/* T element */);
struct Iterator_/* T */ iterate(/*  */);
int contains(/* T element */, int (*)(/* T */, /* T */) equator);
int equalsTo(struct List_/* T */ others, int (*)(/* T */, /* T */) equator);
int size(/*  */);
struct Optional_Tuple_List_/* T */_/* T */ removeLast(/*  */);
int isEmpty(/*  */);
struct Optional_int indexOf(/* T element */, int (*)(/* T */, /* T */) equator);
struct List_/* T */ addAllLast(struct List_/* T */ others);
struct Iterator_/* T */ iterateReversed(/*  */);
struct List_/* T */ addLast(/* T element */);
struct Iterator_/* T */ iterate(/*  */);
int contains(/* T element */, int (*)(/* T */, /* T */) equator);
int equalsTo(struct List_/* T */ others, int (*)(/* T */, /* T */) equator);
int size(/*  */);
struct Optional_Tuple_List_/* T */_/* T */ removeLast(/*  */);
int isEmpty(/*  */);
struct Optional_int indexOf(/* T element */, int (*)(/* T */, /* T */) equator);
struct List_/* T */ addAllLast(struct List_/* T */ others);
struct Iterator_/* T */ iterateReversed(/*  */);
/* private */struct Frame {/*  */
};
/* public */ struct Optional_Type resolveTypeParam(char* name);/*  {
            return this.typeParams.indexOf(name, String::equals).flatMap(index -> {
                if (index < this.typeArguments.size()) {
                    return Optional.of(this.typeArguments.get(index));
                }
                return Optional.empty();
            });
        } */
/* public */ int isTypeParamDefined(char* value);/*  {
            return this.typeParams.contains(value, String::equals);
        } */
/* private */struct List_Frame {/* T get(int index); *//* T last(); *//*  */
};
char* stringify(/*  */);
char* generate(/*  */);
int equalsTo(/* Type other */);
int isParameterized(/*  */);
struct List_/* T */ addLast(/* T element */);
struct Iterator_/* T */ iterate(/*  */);
int contains(/* T element */, int (*)(/* T */, /* T */) equator);
int equalsTo(struct List_/* T */ others, int (*)(/* T */, /* T */) equator);
int size(/*  */);
struct Optional_Tuple_List_/* T */_/* T */ removeLast(/*  */);
int isEmpty(/*  */);
struct Optional_int indexOf(/* T element */, int (*)(/* T */, /* T */) equator);
struct List_/* T */ addAllLast(struct List_/* T */ others);
struct Iterator_/* T */ iterateReversed(/*  */);
struct List_/* T */ addLast(/* T element */);
struct Iterator_/* T */ iterate(/*  */);
int contains(/* T element */, int (*)(/* T */, /* T */) equator);
int equalsTo(struct List_/* T */ others, int (*)(/* T */, /* T */) equator);
int size(/*  */);
struct Optional_Tuple_List_/* T */_/* T */ removeLast(/*  */);
int isEmpty(/*  */);
struct Optional_int indexOf(/* T element */, int (*)(/* T */, /* T */) equator);
struct List_/* T */ addAllLast(struct List_/* T */ others);
struct Iterator_/* T */ iterateReversed(/*  */);
struct List_/* T */ addLast(/* T element */);
struct Iterator_/* T */ iterate(/*  */);
int contains(/* T element */, int (*)(/* T */, /* T */) equator);
int equalsTo(struct List_/* T */ others, int (*)(/* T */, /* T */) equator);
int size(/*  */);
struct Optional_Tuple_List_/* T */_/* T */ removeLast(/*  */);
int isEmpty(/*  */);
struct Optional_int indexOf(/* T element */, int (*)(/* T */, /* T */) equator);
struct List_/* T */ addAllLast(struct List_/* T */ others);
struct Iterator_/* T */ iterateReversed(/*  */);
/* private */struct Stack {/* public Stack() {
            this(new ArrayList<>());
        } *//*  */
};
/* private */struct CompileState {/* public CompileState() {
            this(new ArrayList<>(), new HashMap<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new Stack());
        } *//* private CompileState addExpansion(ObjectType type) {
            return new CompileState(this.generated, this.expandables, this.expansions.addLast(type), this.structures, this.methods, this.stack);
        } *//* public CompileState addStruct(String struct) {
            return new CompileState(this.generated.addLast(struct)
                    .addAllLast(this.methods), this.expandables, this.expansions, this.structures, new ArrayList<>(), this.stack);
        } *//* public CompileState addExpandable(String name, Function<List<Type>, Optional<CompileState>> expandable) {
            this.expandables.put(name, expandable);
            return this;
        } *//* public CompileState addTypeParameters(List<String> typeParams) {
            return new CompileState(this.generated, this.expandables, this.expansions, this.structures, this.methods, this.stack);
        } *//* public CompileState withStructureName(String name) {
            return new CompileState(this.generated, this.expandables, this.expansions, this.structures, this.methods, this.stack);
        } *//* public CompileState addTypeArguments(List<Type> typeArguments) {
            return new CompileState(this.generated, this.expandables, this.expansions, this.structures, this.methods, this.stack);
        } *//* public CompileState addMethod(String method) {
            return new CompileState(this.generated, this.expandables, this.expansions, this.structures, this.methods.addLast(method), this.stack);
        } *//* public CompileState addStructure(ObjectType type) {
            return new CompileState(this.generated, this.expandables, this.expansions, this.structures.addLast(type), this.methods, this.stack);
        } *//*  */
};
/* private */ struct Optional_/* CompileState */ expand(/* ObjectType expansion */);/*  {
            if (expansion.isParameterized()) {
                return Optional.empty();
            }

            if (this.expansions.contains(expansion, ObjectType::equalsTo)) {
                return Optional.empty();
            }

            System.err.println(expansion.generate());
            return this.addExpansion(expansion)
                    .findExpandable(expansion.name)
                    .flatMap(expandable -> expandable.apply(expansion.arguments));
        } */
/* public */ struct Optional_Func_List_Type_Optional_/* CompileState */ findExpandable(char* name);/*  {
            if (this.expandables.containsKey(name)) {
                return Optional.of(this.expandables.get(name));
            }
            return Optional.empty();
        } */
/* public */ int isTypeDefined(char* base);/*  {
            return this.isCurrentStructName(base) || this.isStructureDefined(base);
        } */
/* private */ int isStructureDefined(char* base);/*  {
            return this.structures.iterate().anyMatch(structure -> structure.name.equals(base));
        } */
/* private */ int isCurrentStructName(char* base);/*  {
            return this.findThisName().filter(inner -> inner.equals(base)).isPresent();
        } */
/* public */ struct Optional_char_ref findThisName(/*  */);/*  {
            return this.stack.frames
                    .iterateReversed()
                    .map(Frame::maybeStructName)
                    .flatMap(Iterators::fromOptional)
                    .next();
        } */
/* public */ struct Optional_Type resolveTypeArgument(char* value);/*  {
            return this.stack.frames
                    .iterateReversed()
                    .map(frame -> frame.resolveTypeParam(value))
                    .flatMap(Iterators::fromOptional)
                    .next();
        } */
/* public */ int isTypeParamDefined(char* value);/*  {
            return this.stack.frames
                    .iterateReversed()
                    .anyMatch(frame -> frame.isTypeParamDefined(value));
        } */
/* private */struct Joiner {/* private Joiner() {
            this("");
        } *//*  */
};
/* public */ struct Optional_char_ref createInitial(/*  */);/*  {
            return Optional.empty();
        } */
/* public */ struct Optional_char_ref fold(struct Optional_char_ref maybeCurrent, char* element);/*  {
            return Optional.of(maybeCurrent.map(current -> current + this.delimiter + element).orElse(element));
        } */
/* private static */struct Iterators {/*  */
};
/* private */struct Ref {/*  */
};
/* public */ char* stringify(/*  */);/*  {
            return this.type.stringify() + "_ref";
        } */
/* public */ char* generate(/*  */);/*  {
            return this.type.generate() + "*";
        } */
/* public */ int equalsTo(struct Type other);/*  {
            return other instanceof Ref ref && this.type.equalsTo(ref.type);
        } */
/* public */ struct Type strip(/*  */);/*  {
            return new Ref(this.type.strip());
        } */
/* public */ int isParameterized(/*  */);/*  {
            return this.type.isParameterized();
        } */
/* private */struct ObjectType {/*  */
};
/* public */ char* stringify(/*  */);/*  {
            return this.name + this.joinArguments();
        } */
/* public */ char* generate(/*  */);/*  {
            return "struct " + this.stringify();
        } */
/* public */ int equalsTo(struct Type other);/*  {
            return other instanceof ObjectType objectType
                    && this.name.equals(objectType.name)
                    && this.arguments.equalsTo(objectType.arguments, Type::equalsTo);
        } */
/* public */ struct Type strip(/*  */);/*  {
            var newArguments = this.arguments.iterate()
                    .map(Type::strip)
                    .collect(new ListCollector<>());

            return new ObjectType(this.name, newArguments);
        } */
/* public */ int isParameterized(/*  */);/*  {
            return this.arguments.iterate().anyMatch(Type::isParameterized);
        } */
/* private */ char* joinArguments(/*  */);/*  {
            return this.arguments.iterate()
                    .map(Type::stringify)
                    .collect(new Joiner("_"))
                    .map(result -> "_" + result)
                    .orElse("");
        } */
/* private */struct Placeholder {/*  */
};
/* public */ char* stringify(/*  */);/*  {
            return generatePlaceholder(this.value);
        } */
/* public */ char* generate(/*  */);/*  {
            return generatePlaceholder(this.value);
        } */
/* public */ int equalsTo(struct Type other);/*  {
            return other instanceof Placeholder placeholder && this.value.equals(placeholder.value);
        } */
/* public */ struct Type strip(/*  */);/*  {
            return this;
        } */
/* public */ int isParameterized(/*  */);/*  {
            return true;
        } */
/* private */struct Definition {/* public Definition mapType(Function<Type, Type> mapper) {
            return new Definition(this.annotations, this.afterAnnotations, mapper.apply(this.type), this.name, this.typeParams);
        } *//* public Definition mapName(Function<String, String> mapper) {
            return new Definition(this.annotations, this.afterAnnotations, this.type, mapper.apply(this.name), this.typeParams);
        } *//*  */
};
/* public */ char* generate(/*  */);/*  {
            return this.generateAfterAnnotations() + this.type().generate() + " " + this.name();
        } */
/* private */ char* generateAfterAnnotations(/*  */);/*  {
            return this.afterAnnotations.isEmpty() ? "" : (generatePlaceholder(this.afterAnnotations()) + " ");
        } */
/* private */struct TypeParam {/*  */
};
/* public */ char* stringify(/*  */);/*  {
            return this.input;
        } */
/* public */ char* generate(/*  */);/*  {
            return "template " + this.input;
        } */
/* public */ int equalsTo(struct Type other);/*  {
            return other instanceof TypeParam param && this.input.equals(param.input);
        } */
/* public */ struct Type strip(/*  */);/*  {
            return Primitive.Void;
        } */
/* public */ int isParameterized(/*  */);/*  {
            return true;
        } */
/* private */struct Functional {/*  */
};
/* public */ char* stringify(/*  */);/*  {
            var joinedParameters = this.typeParameters.iterate()
                    .map(Type::stringify)
                    .collect(new Joiner("_"))
                    .map(inner -> inner + "_")
                    .orElse("");

            return "Func_" + joinedParameters + this.returnType.stringify();
        } */
/* public */ char* generate(/*  */);/*  {
            var joined = this.typeParameters.iterate()
                    .map(Type::generate)
                    .collect(new Joiner(", "))
                    .orElse("");

            return this.returnType.generate() + " (*)(" + joined + ")";
        } */
/* public */ int equalsTo(struct Type other);/*  {
            return other instanceof Functional functional
                    && this.returnType.equalsTo(functional.returnType)
                    && this.typeParameters.equalsTo(functional.typeParameters, Type::equalsTo);
        } */
/* public */ struct Type strip(/*  */);/*  {
            return new Functional(this.returnType.strip(), this.typeParameters.iterate()
                    .map(Type::strip)
                    .collect(new ListCollector<>()));
        } */
/* public */ int isParameterized(/*  */);/*  {
            return this.returnType.isParameterized() || this.typeParameters.iterate().anyMatch(Type::isParameterized);
        } */
/* private */struct Tuple_CompileState_char_ref {/*  */
};
/* public */ struct Optional_char_ref createInitial(/*  */);/*  {
            return Optional.empty();
        } */
/* public */ struct Optional_char_ref fold(struct Optional_char_ref maybeCurrent, char* element);/*  {
            return Optional.of(maybeCurrent.map(current -> current + this.delimiter + element).orElse(element));
        } */
/* public */struct Main {/* ' && appended.isShallow()) {
            return appended.advance().exit();
        } *//* if (c == '{') {
            return appended.enter();
        }
        if (c == '} *//* ') {
            return appended.exit();
        } *//* return appended; *//*  */
};
/* private static */ struct Tuple_CompileState_char_ref compileStatements(struct CompileState initial, char* input, struct Tuple_CompileState_char_ref (*)(struct CompileState, char*) mapper);/*  {
        return compileAll(initial, input, Main::foldStatementChar, mapper, Main::merge);
    } */
/* private static */ struct Tuple_CompileState_char_ref compileAll(struct CompileState initial, char* input, struct DivideState (*)(struct DivideState, /* Character */) folder, struct Tuple_CompileState_char_ref (*)(struct CompileState, char*) mapper, char* (*)(char*, char*) merger);/*  {
        var tuple = parseAll(initial, input, folder, mapper);
        return new Tuple<>(tuple.left, generateAll(tuple.right, merger));
    } */
/* private static */ char* generateAll(struct List_char_ref elements, char* (*)(char*, char*) merger);/*  {
        return elements.iterate().fold("", merger);
    } */
/* private static */ char* merge(char* buffer, char* element);/*  {
        return buffer + element;
    } */
/* private static */ struct List_char_ref divide(char* input, struct DivideState (*)(struct DivideState, /* Character */) folder);/*  {
        var current = new DivideState();
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            current = folder.apply(current, c);
        }

        return current.advance().segments;
    } */
/* private static */ struct DivideState foldStatementChar(struct DivideState state, /*  char c */);/*  {
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
                    var parametersTuple = parseParameters(state, parameters);
                    return createStructureWithMaybeTypeParametersRule(parametersTuple.left, beforeKeyword, beforeParameters, content1, parametersTuple.right);
                });
            });
        };
    } */
	/* private static Tuple<CompileState, List<Parameter>> parseParameters(CompileState state, String parameters) {
        return parseValues(state, parameters, Main::compileParameter);
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
            String rawName,
            List<String> typeParams,
            List<Type> typeArguments,
            List<Parameter> parameters,
            String content
    ) {
        var name = rawName.strip();
        if (!isSymbol(name)) {
            return Optional.empty();
        }

        var statementsTuple = compileStatements(state.withStructureName(name)
                .addTypeParameters(typeParams)
                .addTypeArguments(typeArguments), content, Main::compileClassSegment);

        var type = new ObjectType(name, typeArguments);
        var generated = generatePlaceholder(beforeStruct.strip()) + type.generate() + " {" + statementsTuple.right + "\n};\n";
        var added = statementsTuple.left.addStruct(generated).addStructure(type);
        return Optional.of(added);
    } */
	/* private static <T> Optional<T> compileSymbol(String input, Function<String, Optional<T>> mapper) {
        if (!isSymbol(input)) {
            return Optional.empty();
        }

        return mapper.apply(input);
    } */
	/* private static boolean isSymbol(String input) {
        if (input.equals("return") || input.equals("private")) {
            return false;
        }

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

                    var parametersTuple = parseParameters(definitionState, params);
                    return assembleMethod(parametersTuple.left, definition, parametersTuple.right, oldContent);
                });
            });
        });
    } */
	/* private static Optional<Tuple<CompileState, String>> assembleMethod(
            CompileState state,
            Definition definition,
            List<Parameter> parameters,
            String oldContent
    ) {
        if (!definition.typeParams.isEmpty()) {
            return Optional.of(new Tuple<>(state, ""));
        }

        Definition newDefinition;
        String newContent;
        if (definition.annotations.contains("Actual", String::equals)) {
            newDefinition = definition.mapType(Type::strip);
            newContent = ";";
        }
        else if (oldContent.equals(";")) {
            newDefinition = definition.mapType(Type::strip).mapName(name -> {
                return state.findThisName().map(currentStructName -> currentStructName + "_" + name).orElse(name);
            });

            newContent = ";";
        }
        else {
            newContent = ";" + generatePlaceholder(oldContent);
            newDefinition = definition.mapName(name -> {
                return state.findThisName().map(currentStructName -> currentStructName + "_" + name).orElse(name);
            });
        }

        var parametersString = parameters.iterate()
                .map(Parameter::generate)
                .collect(new Joiner(", "))
                .orElse("");

        var generatedHeader = newDefinition.generate() + "(" + parametersString + ")";
        var generated = generatedHeader + newContent + "\n";
        return Optional.of(new Tuple<>(state.addMethod(generated), ""));
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
            return compileOr(state, beforeName, Lists.of(
                    (state1, s) -> compileInfix(() -> {
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
                        return getCompileStateDefinitionTuple(state1, rawName, beforeType, type);
                    }),
                    (state2, s) -> getCompileStateDefinitionTuple(state2, rawName, "", s)
            ));
        });
    } */
	/* private static Optional<Tuple<CompileState, Definition>> getCompileStateDefinitionTuple(
            CompileState state,
            String rawName,
            String beforeType,
            String type
    ) {
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
	/* private static Optional<Tuple<CompileState, Definition>> getCompileStateDefinitionTuple(
            CompileState state,
            List<String> annotations,
            String afterAnnotations,
            List<String> typeParams,
            String rawName,
            String type
    ) {
        return compileSymbol(rawName.strip(), name -> {
            CompileState state1 = state.addTypeParameters(typeParams);
            return parseType(state1, type).flatMap(typeTuple -> {
                return Optional.of(new Tuple<>(typeTuple.left, new Definition(annotations, afterAnnotations, typeTuple.right, name, typeParams)));
            });
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
	/* private static Tuple<CompileState, Type> parseTypeOrPlaceholder(CompileState state, String input) {
        return parseType(state, input).orElseGet(() -> new Tuple<>(state, new Placeholder(input.strip())));
    } */
	/* private static Optional<Tuple<CompileState, Type>> parseType(CompileState state, String input) {
        return compileOr(state, input, Lists.of(
                typed(Main::parsePrimitive),
                typed(Main::parseTemplate),
                typed(Main::parseArray),
                typed(Main::parseTypeParam),
                typed(Main::parseStructureType)
        ));
    } */
	/* private static Optional<Tuple<CompileState, Type>> parseStructureType(CompileState state, String input) {
        return compileSymbol(input.strip(), symbol -> {
            if (state.isTypeDefined(symbol)) {
                return Optional.of(new Tuple<>(state, new ObjectType(symbol, new ArrayList<>())));
            }
            else {
                return Optional.empty();
            }
        });
    } */
	/* private static Optional<Tuple<CompileState, Type>> parseTypeParam(CompileState state, String input) {
        var stripped = input.strip();

        if (state.isTypeParamDefined(stripped)) {
            return Optional.of(state.resolveTypeArgument(stripped).map(tuple -> {
                return new Tuple<>(state, tuple);
            }).orElseGet(() -> new Tuple<>(state, new TypeParam(stripped))));
        }

        return Optional.empty();
    } */
	/* private static Optional<Tuple<CompileState, Type>> parseArray(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.endsWith("[]")) {
            var slice = input.substring(0, stripped.length() - "[]".length());
            var childTuple = parseTypeOrPlaceholder(state, slice);
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

        if (stripped.equals("int")
                || stripped.equals("Integer")
                || stripped.equals("boolean")
                || stripped.equals("Boolean")) {
            return Optional.of(new Tuple<>(state, Primitive.Int));
        }

        if (stripped.equals("void")) {
            return Optional.of(new Tuple<>(state, Primitive.Void));
        }

        return Optional.empty();
    } */
	/* private static Optional<Tuple<CompileState, Type>> parseTemplate(CompileState oldState, String input) {
        return compileSuffix(input.strip(), ">", withoutEnd -> {
            return Main.compileFirst(withoutEnd, "<", (base, argumentsString) -> {
                var argumentsTuple = parseValues(oldState, argumentsString, Main::parseTypeOrPlaceholder);

                var argumentsState = argumentsTuple.left;
                var arguments = argumentsTuple.right;

                if (base.equals("Function")) {
                    var functional = new Functional(arguments.get(1), Lists.of(arguments.get(0)));
                    return Optional.of(new Tuple<>(argumentsState, functional));
                }

                if (base.equals("BiFunction")) {
                    var functional = new Functional(arguments.get(2), Lists.of(arguments.get(0), arguments.get(1)));
                    return Optional.of(new Tuple<>(argumentsState, functional));
                }

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
