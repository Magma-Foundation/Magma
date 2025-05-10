/* private */struct Type {/*  */
};
char* Type_stringify(/*  */);
char* Type_generate(/*  */);
/* default */ char* Type_generateAsTemplate(/*  */);/*  {
            return this.generate();
        } */
int Type_equalsTo(struct Type other);
struct Type Type_strip(/*  */);
int Type_isParameterized(/*  */);
/* default */ char* Type_generateWithName(char* name);/*  {
            return this.generate() + " " + name;
        } */
// Type<>
/* private @ */struct Actual {/*  */
};
// Actual<>
/* private */struct Parameter {/*  */
};
char* Parameter_generate(/*  */);
struct Option_/* Definition */ Parameter_toDefinition(/*  */);
// Parameter<>
/* private static */struct StandardLibrary {/*  */
};
// StandardLibrary<>
/* private static */struct Lists {/*  */
};
// Lists<>
/* private sealed */struct Option_char_ref {/*  */
};
struct List_char_ref List_char_ref_addLast(char* element);
struct Iterator_char_ref Iterator_char_ref_concat(struct Iterator_char_ref other);
struct Option_char_ref Option_char_ref_or(struct Option_char_ref (*other)());
int Option_char_ref_isPresent(/*  */);
struct Option_char_ref Option_char_ref_filter(int (*predicate)(char*));
char* Option_char_ref_orElse(char* other);
char* Option_char_ref_orElseGet(char* (*other)());
int Option_char_ref_isEmpty(/*  */);
// Option<char*>
/* private */struct Iterator_char_ref {/*  */
};
struct Option_char_ref Iterator_char_ref_next(/*  */);
int Iterator_char_ref_anyMatch(int (*predicate)(char*));
int Iterator_char_ref_allMatch(int (*predicate)(char*));
struct Iterator_char_ref Iterator_char_ref_filter(int (*predicate)(char*));
// Iterator<char*>
/* private */struct Tuple2_List_char_ref_char_ref {/*  */
};
struct Iterator_char_ref List_char_ref_iterate(/*  */);
int List_char_ref_contains(char* element, int (*equator)(char*, char*));
int List_char_ref_equalsTo(struct List_char_ref others, int (*equator)(char*, char*));
int List_char_ref_size(/*  */);
struct List_char_ref Tuple2_List_char_ref_char_ref_left(/*  */);
char* Tuple2_List_char_ref_char_ref_right(/*  */);
// Tuple2<List<char*>, char*>
/* private sealed */struct Option_Tuple2_List_char_ref_char_ref {/*  */
};
struct Option_Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_or(struct Option_Tuple2_List_char_ref_char_ref (*other)());
int Option_Tuple2_List_char_ref_char_ref_isPresent(/*  */);
struct Option_Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_filter(int (*predicate)(struct Tuple2_List_char_ref_char_ref));
struct Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_orElse(struct Tuple2_List_char_ref_char_ref other);
struct Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_orElseGet(struct Tuple2_List_char_ref_char_ref (*other)());
int Option_Tuple2_List_char_ref_char_ref_isEmpty(/*  */);
// Option<Tuple2<List<char*>, char*>>
/* private sealed */struct Option_int {/*  */
};
struct Option_Tuple2_List_char_ref_char_ref List_char_ref_removeLast(/*  */);
int List_char_ref_isEmpty(/*  */);
char* List_char_ref_get(int index);
struct Option_int Option_int_or(struct Option_int (*other)());
int Option_int_isPresent(/*  */);
struct Option_int Option_int_filter(int (*predicate)(int));
int Option_int_orElse(int other);
int Option_int_orElseGet(int (*other)());
int Option_int_isEmpty(/*  */);
// Option<int>
/* private */struct List_char_ref {/*  */
};
struct Option_int List_char_ref_indexOf(char* element, int (*equator)(char*, char*));
struct List_char_ref List_char_ref_addAllLast(struct List_char_ref others);
struct Iterator_char_ref List_char_ref_iterateReversed(/*  */);
char* List_char_ref_last(/*  */);
struct List_char_ref List_char_ref_mapLast(char* (*mapper)(char*));
// List<char*>
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
/* private */ struct DivideState DivideState_advance(/*  */);/*  {
            this.segments = this.segments.addLast(this.buffer);
            this.buffer = "";
            return this;
        } */
/* private */ struct DivideState DivideState_append(char c);/*  {
            this.buffer = this.buffer + c;
            return this;
        } */
/* public */ int DivideState_isLevel(/*  */);/*  {
            return this.depth == 0;
        } */
/* public */ struct DivideState DivideState_enter(/*  */);/*  {
            this.depth++;
            return this;
        } */
/* public */ struct DivideState DivideState_exit(/*  */);/*  {
            this.depth--;
            return this;
        } */
/* public */ int DivideState_isShallow(/*  */);/*  {
            return this.depth == 1;
        } */
// DivideState<>
/* private sealed */struct Option_Type {/*  */
};
struct List_Type List_Type_addLast(struct Type element);
struct Iterator_Type Iterator_Type_concat(struct Iterator_Type other);
struct Option_Type Option_Type_or(struct Option_Type (*other)());
int Option_Type_isPresent(/*  */);
struct Option_Type Option_Type_filter(int (*predicate)(struct Type));
struct Type Option_Type_orElse(struct Type other);
struct Type Option_Type_orElseGet(struct Type (*other)());
int Option_Type_isEmpty(/*  */);
// Option<Type>
/* private */struct Iterator_Type {/*  */
};
struct Option_Type Iterator_Type_next(/*  */);
int Iterator_Type_anyMatch(int (*predicate)(struct Type));
int Iterator_Type_allMatch(int (*predicate)(struct Type));
struct Iterator_Type Iterator_Type_filter(int (*predicate)(struct Type));
// Iterator<Type>
/* private */struct Tuple2_List_Type_Type {/*  */
};
struct Iterator_Type List_Type_iterate(/*  */);
int List_Type_contains(struct Type element, int (*equator)(struct Type, struct Type));
int List_Type_equalsTo(struct List_Type others, int (*equator)(struct Type, struct Type));
int List_Type_size(/*  */);
struct List_Type Tuple2_List_Type_Type_left(/*  */);
struct Type Tuple2_List_Type_Type_right(/*  */);
// Tuple2<List<Type>, Type>
/* private sealed */struct Option_Tuple2_List_Type_Type {/*  */
};
struct Option_Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_or(struct Option_Tuple2_List_Type_Type (*other)());
int Option_Tuple2_List_Type_Type_isPresent(/*  */);
struct Option_Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_filter(int (*predicate)(struct Tuple2_List_Type_Type));
struct Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_orElse(struct Tuple2_List_Type_Type other);
struct Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_orElseGet(struct Tuple2_List_Type_Type (*other)());
int Option_Tuple2_List_Type_Type_isEmpty(/*  */);
// Option<Tuple2<List<Type>, Type>>
/* private */struct List_Type {/*  */
};
struct Option_Tuple2_List_Type_Type List_Type_removeLast(/*  */);
int List_Type_isEmpty(/*  */);
struct Type List_Type_get(int index);
struct Option_int List_Type_indexOf(struct Type element, int (*equator)(struct Type, struct Type));
struct List_Type List_Type_addAllLast(struct List_Type others);
struct Iterator_Type List_Type_iterateReversed(/*  */);
struct Type List_Type_last(/*  */);
struct List_Type List_Type_mapLast(struct Type (*mapper)(struct Type));
// List<Type>
/* private */struct Frame {/* public Frame() {
            this(new ArrayList<>(), new ArrayList<>(), new None<String>());
        } *//*  */
	struct List_char_ref typeParameters;
	struct List_Type typeArguments;
	struct Option_char_ref maybeStructName;
};
/* public */ struct Option_Type Frame_resolveTypeParam(char* name);/*  {
            return this.typeParameters.indexOf(name, String::equals).flatMap(index -> {
                if (index < this.typeArguments.size()) {
                    return new Some<>(this.typeArguments.get(index));
                }
                return new None<>();
            });
        } */
/* public */ int Frame_isTypeParamDefined(char* value);/*  {
            return this.typeParameters.contains(value, String::equals);
        } */
/* public */ struct Frame Frame_defineTypeParameters(struct List_char_ref typeParameters);/*  {
            return new Frame(this.typeParameters.addAllLast(typeParameters), this.typeArguments, this.maybeStructName);
        } */
/* public */ struct Frame Frame_defineStruct(char* name);/*  {
            return new Frame(this.typeParameters, this.typeArguments, new Some<String>(name));
        } */
/* public */ struct Frame Frame_defineTypeArguments(struct List_Type typeArguments);/*  {
            return new Frame(this.typeParameters, this.typeArguments.addAllLast(typeArguments), this.maybeStructName);
        } */
/* public */ struct Option_/* ObjectType */ Frame_createObjectType(/*  */);/*  {
            return this.maybeStructName.map(name -> {
                return new ObjectType(name, this.typeArguments);
            });
        } */
// Frame<>
/* private sealed */struct Option_Frame {/*  */
};
struct List_Frame List_Frame_addLast(struct Frame element);
struct Iterator_Frame Iterator_Frame_concat(struct Iterator_Frame other);
struct Option_Frame Option_Frame_or(struct Option_Frame (*other)());
int Option_Frame_isPresent(/*  */);
struct Option_Frame Option_Frame_filter(int (*predicate)(struct Frame));
struct Frame Option_Frame_orElse(struct Frame other);
struct Frame Option_Frame_orElseGet(struct Frame (*other)());
int Option_Frame_isEmpty(/*  */);
// Option<Frame>
/* private */struct Iterator_Frame {/*  */
};
struct Option_Frame Iterator_Frame_next(/*  */);
int Iterator_Frame_anyMatch(int (*predicate)(struct Frame));
int Iterator_Frame_allMatch(int (*predicate)(struct Frame));
struct Iterator_Frame Iterator_Frame_filter(int (*predicate)(struct Frame));
// Iterator<Frame>
/* private */struct Tuple2_List_Frame_Frame {/*  */
};
struct Iterator_Frame List_Frame_iterate(/*  */);
int List_Frame_contains(struct Frame element, int (*equator)(struct Frame, struct Frame));
int List_Frame_equalsTo(struct List_Frame others, int (*equator)(struct Frame, struct Frame));
int List_Frame_size(/*  */);
struct List_Frame Tuple2_List_Frame_Frame_left(/*  */);
struct Frame Tuple2_List_Frame_Frame_right(/*  */);
// Tuple2<List<Frame>, Frame>
/* private sealed */struct Option_Tuple2_List_Frame_Frame {/*  */
};
struct Option_Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_or(struct Option_Tuple2_List_Frame_Frame (*other)());
int Option_Tuple2_List_Frame_Frame_isPresent(/*  */);
struct Option_Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_filter(int (*predicate)(struct Tuple2_List_Frame_Frame));
struct Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_orElse(struct Tuple2_List_Frame_Frame other);
struct Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_orElseGet(struct Tuple2_List_Frame_Frame (*other)());
int Option_Tuple2_List_Frame_Frame_isEmpty(/*  */);
// Option<Tuple2<List<Frame>, Frame>>
/* private */struct List_Frame {/*  */
};
struct Option_Tuple2_List_Frame_Frame List_Frame_removeLast(/*  */);
int List_Frame_isEmpty(/*  */);
struct Frame List_Frame_get(int index);
struct Option_int List_Frame_indexOf(struct Frame element, int (*equator)(struct Frame, struct Frame));
struct List_Frame List_Frame_addAllLast(struct List_Frame others);
struct Iterator_Frame List_Frame_iterateReversed(/*  */);
struct Frame List_Frame_last(/*  */);
struct List_Frame List_Frame_mapLast(struct Frame (*mapper)(struct Frame));
// List<Frame>
/* private */struct Stack {/* public Stack() {
            this(new ArrayList<Frame>().addLast(new Frame()));
        } *//*  */
	struct List_Frame frames;
};
/* public */ struct Stack Stack_defineTypeParameters(struct List_char_ref typeParameters);/*  {
            return this.mapLastFrame(last -> last.defineTypeParameters(typeParameters));
        } */
/* private */ struct Stack Stack_mapLastFrame(struct Frame (*mapper)(struct Frame));/*  {
            return new Stack(this.frames.mapLast(mapper));
        } */
/* public */ struct Stack Stack_defineStructPrototype(char* name);/*  {
            return this.mapLastFrame(last -> last.defineStruct(name));
        } */
/* public */ struct Stack Stack_defineTypeArguments(struct List_Type typeArguments);/*  {
            return this.mapLastFrame(last -> last.defineTypeArguments(typeArguments));
        } */
/* public */ struct Option_/* ObjectType */ Stack_findCurrentObjectType(/*  */);/*  {
            return this.frames
                    .iterateReversed()
                    .map(Frame::createObjectType)
                    .flatMap(Iterators::fromOptional)
                    .next();
        } */
/* public */ struct Option_Type Stack_resolveTypeArgument(char* value);/*  {
            return this.frames
                    .iterateReversed()
                    .map(frame -> frame.resolveTypeParam(value))
                    .flatMap(Iterators::fromOptional)
                    .next();
        } */
/* public */ int Stack_isTypeParamDefined(char* value);/*  {
            return this.frames
                    .iterateReversed()
                    .anyMatch(frame -> frame.isTypeParamDefined(value));
        } */
/* public */ struct Stack Stack_enter(/*  */);/*  {
            return new Stack(this.frames.addLast(new Frame()));
        } */
/* public */ struct Stack Stack_exit(/*  */);/*  {
            return new Stack(this.frames.removeLast().map(listFramePair -> listFramePair.left()).orElse(this.frames));
        } */
// Stack<>
/* private */struct ObjectType {/*  */
	char* name;
	struct List_Type arguments;
};
/* public */ char* ObjectType_stringify(/*  */);/*  {
            var joined = this.arguments.iterate()
                    .map(Type::stringify)
                    .collect(new Joiner("_"))
                    .map(result -> "_" + result)
                    .orElse("");

            return this.name + joined;
        } */
/* public */ char* ObjectType_generate(/*  */);/*  {
            return "struct " + this.stringify();
        } */
/* public */ char* ObjectType_generateAsTemplate(/*  */);/*  {
            var joined = this.arguments.iterate()
                    .map(Type::generateAsTemplate)
                    .collect(new Joiner(", "))
                    .map(result -> "<" + result + ">")
                    .orElse("");

            return this.name + joined;
        } */
/* public */ int ObjectType_equalsTo(struct Type other);/*  {
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
// ObjectType<>
/* private */struct Tuple2_char_ref_List_char_ref {/*  */
};
char* Tuple2_char_ref_List_char_ref_left(/*  */);
struct List_char_ref Tuple2_char_ref_List_char_ref_right(/*  */);
// Tuple2<char*, List<char*>>
/* private sealed */struct Option_Tuple2_char_ref_List_char_ref {/*  */
};
struct List_Tuple2_char_ref_List_char_ref List_Tuple2_char_ref_List_char_ref_addLast(struct Tuple2_char_ref_List_char_ref element);
struct Iterator_Tuple2_char_ref_List_char_ref Iterator_Tuple2_char_ref_List_char_ref_concat(struct Iterator_Tuple2_char_ref_List_char_ref other);
struct Option_Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_or(struct Option_Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_char_ref_List_char_ref_isPresent(/*  */);
struct Option_Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_filter(int (*predicate)(struct Tuple2_char_ref_List_char_ref));
struct Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_orElse(struct Tuple2_char_ref_List_char_ref other);
struct Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_orElseGet(struct Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_char_ref_List_char_ref_isEmpty(/*  */);
// Option<Tuple2<char*, List<char*>>>
/* private */struct Iterator_Tuple2_char_ref_List_char_ref {/*  */
};
struct Option_Tuple2_char_ref_List_char_ref Iterator_Tuple2_char_ref_List_char_ref_next(/*  */);
int Iterator_Tuple2_char_ref_List_char_ref_anyMatch(int (*predicate)(struct Tuple2_char_ref_List_char_ref));
int Iterator_Tuple2_char_ref_List_char_ref_allMatch(int (*predicate)(struct Tuple2_char_ref_List_char_ref));
struct Iterator_Tuple2_char_ref_List_char_ref Iterator_Tuple2_char_ref_List_char_ref_filter(int (*predicate)(struct Tuple2_char_ref_List_char_ref));
// Iterator<Tuple2<char*, List<char*>>>
/* private */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref {/*  */
};
struct Iterator_Tuple2_char_ref_List_char_ref List_Tuple2_char_ref_List_char_ref_iterate(/*  */);
int List_Tuple2_char_ref_List_char_ref_contains(struct Tuple2_char_ref_List_char_ref element, int (*equator)(struct Tuple2_char_ref_List_char_ref, struct Tuple2_char_ref_List_char_ref));
int List_Tuple2_char_ref_List_char_ref_equalsTo(struct List_Tuple2_char_ref_List_char_ref others, int (*equator)(struct Tuple2_char_ref_List_char_ref, struct Tuple2_char_ref_List_char_ref));
int List_Tuple2_char_ref_List_char_ref_size(/*  */);
struct List_Tuple2_char_ref_List_char_ref Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_left(/*  */);
struct Tuple2_char_ref_List_char_ref Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_right(/*  */);
// Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>
/* private sealed */struct Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref {/*  */
};
struct Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_or(struct Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_isPresent(/*  */);
struct Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_filter(int (*predicate)(struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref));
struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_orElse(struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref other);
struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_orElseGet(struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_isEmpty(/*  */);
// Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>
/* private */struct List_Tuple2_char_ref_List_char_ref {/*  */
};
struct Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref List_Tuple2_char_ref_List_char_ref_removeLast(/*  */);
int List_Tuple2_char_ref_List_char_ref_isEmpty(/*  */);
struct Tuple2_char_ref_List_char_ref List_Tuple2_char_ref_List_char_ref_get(int index);
struct Option_int List_Tuple2_char_ref_List_char_ref_indexOf(struct Tuple2_char_ref_List_char_ref element, int (*equator)(struct Tuple2_char_ref_List_char_ref, struct Tuple2_char_ref_List_char_ref));
struct List_Tuple2_char_ref_List_char_ref List_Tuple2_char_ref_List_char_ref_addAllLast(struct List_Tuple2_char_ref_List_char_ref others);
struct Iterator_Tuple2_char_ref_List_char_ref List_Tuple2_char_ref_List_char_ref_iterateReversed(/*  */);
struct Tuple2_char_ref_List_char_ref List_Tuple2_char_ref_List_char_ref_last(/*  */);
struct List_Tuple2_char_ref_List_char_ref List_Tuple2_char_ref_List_char_ref_mapLast(struct Tuple2_char_ref_List_char_ref (*mapper)(struct Tuple2_char_ref_List_char_ref));
// List<Tuple2<char*, List<char*>>>
/* private sealed */struct Option_ObjectType {/*  */
};
struct List_ObjectType List_ObjectType_addLast(struct ObjectType element);
struct Iterator_ObjectType Iterator_ObjectType_concat(struct Iterator_ObjectType other);
struct Option_ObjectType Option_ObjectType_or(struct Option_ObjectType (*other)());
int Option_ObjectType_isPresent(/*  */);
struct Option_ObjectType Option_ObjectType_filter(int (*predicate)(struct ObjectType));
struct ObjectType Option_ObjectType_orElse(struct ObjectType other);
struct ObjectType Option_ObjectType_orElseGet(struct ObjectType (*other)());
int Option_ObjectType_isEmpty(/*  */);
// Option<ObjectType>
/* private */struct Iterator_ObjectType {/*  */
};
struct Option_ObjectType Iterator_ObjectType_next(/*  */);
int Iterator_ObjectType_anyMatch(int (*predicate)(struct ObjectType));
int Iterator_ObjectType_allMatch(int (*predicate)(struct ObjectType));
struct Iterator_ObjectType Iterator_ObjectType_filter(int (*predicate)(struct ObjectType));
// Iterator<ObjectType>
/* private */struct Tuple2_List_ObjectType_ObjectType {/*  */
};
struct Iterator_ObjectType List_ObjectType_iterate(/*  */);
int List_ObjectType_contains(struct ObjectType element, int (*equator)(struct ObjectType, struct ObjectType));
int List_ObjectType_equalsTo(struct List_ObjectType others, int (*equator)(struct ObjectType, struct ObjectType));
int List_ObjectType_size(/*  */);
struct List_ObjectType Tuple2_List_ObjectType_ObjectType_left(/*  */);
struct ObjectType Tuple2_List_ObjectType_ObjectType_right(/*  */);
// Tuple2<List<ObjectType>, ObjectType>
/* private sealed */struct Option_Tuple2_List_ObjectType_ObjectType {/*  */
};
struct Option_Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_or(struct Option_Tuple2_List_ObjectType_ObjectType (*other)());
int Option_Tuple2_List_ObjectType_ObjectType_isPresent(/*  */);
struct Option_Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_filter(int (*predicate)(struct Tuple2_List_ObjectType_ObjectType));
struct Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_orElse(struct Tuple2_List_ObjectType_ObjectType other);
struct Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_orElseGet(struct Tuple2_List_ObjectType_ObjectType (*other)());
int Option_Tuple2_List_ObjectType_ObjectType_isEmpty(/*  */);
// Option<Tuple2<List<ObjectType>, ObjectType>>
/* private */struct List_ObjectType {/*  */
};
struct Option_Tuple2_List_ObjectType_ObjectType List_ObjectType_removeLast(/*  */);
int List_ObjectType_isEmpty(/*  */);
struct ObjectType List_ObjectType_get(int index);
struct Option_int List_ObjectType_indexOf(struct ObjectType element, int (*equator)(struct ObjectType, struct ObjectType));
struct List_ObjectType List_ObjectType_addAllLast(struct List_ObjectType others);
struct Iterator_ObjectType List_ObjectType_iterateReversed(/*  */);
struct ObjectType List_ObjectType_last(/*  */);
struct List_ObjectType List_ObjectType_mapLast(struct ObjectType (*mapper)(struct ObjectType));
// List<ObjectType>
/* private sealed */struct Option_CompileState {/*  */
};
struct Option_CompileState Option_CompileState_or(struct Option_CompileState (*other)());
int Option_CompileState_isPresent(/*  */);
struct Option_CompileState Option_CompileState_filter(int (*predicate)(struct CompileState));
struct CompileState Option_CompileState_orElse(struct CompileState other);
struct CompileState Option_CompileState_orElseGet(struct CompileState (*other)());
int Option_CompileState_isEmpty(/*  */);
// Option<CompileState>
/* private sealed */struct Option_Func_CompileState_List_Type_Option_CompileState {/*  */
};
/* private */ struct Option_CompileState CompileState_expand(struct ObjectType expansion);/*  {
            if (expansion.isParameterized()) {
                return new None<>();
            }

            if (this.expansions.contains(expansion, ObjectType::equalsTo)) {
                return new None<>();
            }

            return this.addExpansion(expansion)
                    .findExpandable(expansion.name)
                    .flatMap(expandable -> expandable.apply(this, expansion.arguments));
        } */
/* private */ struct CompileState CompileState_addExpansion(struct ObjectType type);/*  {
            return new CompileState(this.generations, this.expandables, this.expansions.addLast(type), this.structures, this.methods, this.stack);
        } */
/* public */ struct CompileState CompileState_addStruct(char* structName);/*  {
            return new CompileState(this.generations.addLast(new Tuple2Impl<>(structName, this.methods)), this.expandables, this.expansions, this.structures, new ArrayList<>(), this.stack);
        } */
/* public */ struct CompileState CompileState_addExpandable(char* name, struct Option_CompileState (*expandable)(struct CompileState, struct List_Type));/*  {
            return new CompileState(this.generations, this.expandables.put(name, expandable), this.expansions, this.structures, this.methods, this.stack);
        } */
struct Option_Func_CompileState_List_Type_Option_CompileState Option_Func_CompileState_List_Type_Option_CompileState_or(struct Option_Func_CompileState_List_Type_Option_CompileState (*other)());
int Option_Func_CompileState_List_Type_Option_CompileState_isPresent(/*  */);
struct Option_Func_CompileState_List_Type_Option_CompileState Option_Func_CompileState_List_Type_Option_CompileState_filter(int (*predicate)(struct Option_CompileState (*)(struct CompileState, struct List_Type)));
struct Option_CompileState (*Option_Func_CompileState_List_Type_Option_CompileState_orElse)(struct CompileState, struct List_Type)(struct Option_CompileState (*other)(struct CompileState, struct List_Type));
struct Option_CompileState (*Option_Func_CompileState_List_Type_Option_CompileState_orElseGet)(struct CompileState, struct List_Type)(struct Option_CompileState (*(*other)())(struct CompileState, struct List_Type));
int Option_Func_CompileState_List_Type_Option_CompileState_isEmpty(/*  */);
// Option<struct Option_CompileState (*)(struct CompileState, struct List_Type)>
/* private */struct CompileState {/* public CompileState() {
            this(new ArrayList<>(), new ListMap<>(String::equals), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new Stack());
        } *//*  */
	struct List_Tuple2_char_ref_List_char_ref generations;
	struct Map_char_ref_Func_/* CompileState */_List_Type_Option_/* CompileState */ expandables;
	struct List_ObjectType expansions;
	struct List_ObjectType structures;
	struct List_char_ref methods;
	struct Stack stack;
};
/* public */ struct Option_Func_CompileState_List_Type_Option_CompileState CompileState_findExpandable(char* name);/*  {
            return this.expandables.find(name);
        } */
/* private */ struct CompileState CompileState_mapStack(struct Stack (*mapper)(struct Stack));/*  {
            return new CompileState(this.generations, this.expandables, this.expansions, this.structures, this.methods, mapper.apply(this.stack));
        } */
/* public */ int CompileState_isTypeDefined(char* base);/*  {
            return this.isCurrentStructName(base) || this.isStructureDefined(base);
        } */
/* private */ int CompileState_isStructureDefined(char* base);/*  {
            return this.structures.iterate().anyMatch(structure -> structure.name.equals(base));
        } */
/* private */ int CompileState_isCurrentStructName(char* base);/*  {
            return this.stack.findCurrentObjectType()
                    .filter(inner -> inner.name.equals(base))
                    .isPresent();
        } */
/* public */ struct CompileState CompileState_addMethod(char* method);/*  {
            return new CompileState(this.generations, this.expandables, this.expansions, this.structures, this.methods.addLast(method), this.stack);
        } */
/* public */ struct CompileState CompileState_addStructure(struct ObjectType type);/*  {
            return new CompileState(this.generations, this.expandables, this.expansions, this.structures.addLast(type), this.methods, this.stack);
        } */
// CompileState<>
/* private */struct Joiner {/* public Joiner() {
            this("");
        } *//*  */
	char* delimiter;
};
/* public */ struct Option_char_ref Joiner_createInitial(/*  */);/*  {
            return new None<>();
        } */
/* public */ struct Option_char_ref Joiner_fold(struct Option_char_ref maybeCurrent, char* element);/*  {
            return new Some<>(maybeCurrent.map(current -> current + this.delimiter + element).orElse(element));
        } */
// Joiner<>
/* private static */struct Iterators {/*  */
};
// Iterators<>
/* private */struct Ref {/*  */
	struct Type type;
};
/* public */ char* Ref_stringify(/*  */);/*  {
            return this.type.stringify() + "_ref";
        } */
/* public */ char* Ref_generate(/*  */);/*  {
            return this.type.generate() + "*";
        } */
/* public */ int Ref_equalsTo(struct Type other);/*  {
            return other instanceof Ref ref && this.type.equalsTo(ref.type);
        } */
/* public */ struct Type Ref_strip(/*  */);/*  {
            return new Ref(this.type.strip());
        } */
/* public */ int Ref_isParameterized(/*  */);/*  {
            return this.type.isParameterized();
        } */
// Ref<>
/* private */struct Placeholder {/*  */
	char* value;
};
/* public */ char* Placeholder_stringify(/*  */);/*  {
            return generatePlaceholder(this.value);
        } */
/* public */ char* Placeholder_generate(/*  */);/*  {
            return generatePlaceholder(this.value);
        } */
/* public */ struct Option_/* Definition */ Placeholder_toDefinition(/*  */);/*  {
            return new None<>();
        } */
/* public */ int Placeholder_equalsTo(struct Type other);/*  {
            return other instanceof Placeholder placeholder && this.value.equals(placeholder.value);
        } */
/* public */ struct Type Placeholder_strip(/*  */);/*  {
            return this;
        } */
/* public */ int Placeholder_isParameterized(/*  */);/*  {
            return true;
        } */
// Placeholder<>
/* private sealed */struct Option_Definition {/*  */
};
/* public */ char* Definition_generate(/*  */);/*  {
            return this.generateAfterAnnotations() + this.type.generateWithName(this.name);
        } */
struct Option_Definition Option_Definition_or(struct Option_Definition (*other)());
int Option_Definition_isPresent(/*  */);
struct Option_Definition Option_Definition_filter(int (*predicate)(struct Definition));
struct Definition Option_Definition_orElse(struct Definition other);
struct Definition Option_Definition_orElseGet(struct Definition (*other)());
int Option_Definition_isEmpty(/*  */);
// Option<Definition>
/* private */struct Definition {/*  */
	struct List_char_ref annotations;
	char* afterAnnotations;
	struct Type type;
	char* name;
	struct List_char_ref typeParams;
};
/* public */ struct Option_Definition Definition_toDefinition(/*  */);/*  {
            return new Some<>(this);
        } */
/* private */ char* Definition_generateAfterAnnotations(/*  */);/*  {
            return this.afterAnnotations.isEmpty() ? "" : (generatePlaceholder(this.afterAnnotations()) + " ");
        } */
/* public */ struct Definition Definition_mapType(struct Type (*mapper)(struct Type));/*  {
            return new Definition(this.annotations, this.afterAnnotations, mapper.apply(this.type), this.name, this.typeParams);
        } */
/* public */ struct Definition Definition_mapName(char* (*mapper)(char*));/*  {
            return new Definition(this.annotations, this.afterAnnotations, this.type, mapper.apply(this.name), this.typeParams);
        } */
// Definition<>
/* private */struct TypeParam {/*  */
	char* input;
};
/* public */ char* TypeParam_stringify(/*  */);/*  {
            return this.input;
        } */
/* public */ char* TypeParam_generate(/*  */);/*  {
            return "template " + this.input;
        } */
/* public */ int TypeParam_equalsTo(struct Type other);/*  {
            return other instanceof TypeParam param && this.input.equals(param.input);
        } */
/* public */ struct Type TypeParam_strip(/*  */);/*  {
            return Primitive.Void;
        } */
/* public */ int TypeParam_isParameterized(/*  */);/*  {
            return true;
        } */
// TypeParam<>
/* private */struct Functional {/*  */
	struct Type returnType;
	struct List_Type typeParameters;
};
/* public */ char* Functional_stringify(/*  */);/*  {
            var joinedParameters = this.typeParameters.iterate()
                    .map(Type::stringify)
                    .collect(new Joiner("_"))
                    .map(inner -> inner + "_")
                    .orElse("");

            return "Func_" + joinedParameters + this.returnType.stringify();
        } */
/* public */ char* Functional_generate(/*  */);/*  {
            return this.generateWithName("");
        } */
/* public */ int Functional_equalsTo(struct Type other);/*  {
            return other instanceof Functional functional
                    && this.returnType.equalsTo(functional.returnType)
                    && this.typeParameters.equalsTo(functional.typeParameters, Type::equalsTo);
        } */
/* public */ struct Type Functional_strip(/*  */);/*  {
            return new Functional(this.returnType.strip(), this.typeParameters.iterate()
                    .map(Type::strip)
                    .collect(new ListCollector<>()));
        } */
/* public */ int Functional_isParameterized(/*  */);/*  {
            return this.returnType.isParameterized() || this.typeParameters.iterate().anyMatch(Type::isParameterized);
        } */
/* public */ char* Functional_generateWithName(char* name);/*  {
            var joined = this.typeParameters.iterate()
                    .map(Type::generate)
                    .collect(new Joiner(", "))
                    .orElse("");

            var withoutReturns = "(*" + name + ")(" + joined + ")";
            if (this.returnType instanceof Functional functional) {
                return functional.generateWithName(withoutReturns);
            }

            return this.returnType.generate() + " " + withoutReturns;
        } */
// Functional<>
/* private */struct Tuple2_CompileState_char_ref {/*  */
};
/* public static */ void Main_main(/*  */);/*  {
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
/* private static */ char* Main_compile(char* input);/*  {
        var compiled = compileStatements(new CompileState(), input, Main::compileRootSegment);
        var compiledState = compiled.left();

        var joined = compiledState.generations
                .iterate()
                .map(tuple -> tuple.left() + joinWithDelimiter(tuple.right(), ""))
                .collect(new Joiner())
                .orElse("");

        return joined + compiled.right() + "\nint main(){\n\treturn 0;\n}\n";
    } */
/* private static */ char* Main_joinWithDelimiter(struct List_char_ref items, char* delimiter);/*  {
        return items.iterate()
                .collect(new Joiner(delimiter))
                .orElse("");
    } */
struct CompileState Tuple2_CompileState_char_ref_left(/*  */);
char* Tuple2_CompileState_char_ref_right(/*  */);
// Tuple2<CompileState, char*>
/* public */struct Main {/* ' && appended.isShallow()) {
            return appended.advance().exit();
        } *//* if (c == '{') {
            return appended.enter();
        }
        if (c == '} *//* ') {
            return appended.exit();
        } *//* return appended; *//*  */
};
/* private static */ struct Tuple2_CompileState_char_ref Main_compileStatements(struct CompileState initial, char* input, struct Tuple2_CompileState_char_ref (*mapper)(struct CompileState, char*));/*  {
        return compileAll(initial, input, Main::foldStatementChar, mapper, Main::merge);
    } */
/* private static */ struct Tuple2_CompileState_char_ref Main_compileAll(struct CompileState initial, char* input, struct DivideState (*folder)(struct DivideState, char), struct Tuple2_CompileState_char_ref (*mapper)(struct CompileState, char*), char* (*merger)(char*, char*));/*  {
        var tuple = parseAll(initial, input, folder, mapper);
        return new Tuple2Impl<>(tuple.left(), generateAll(tuple.right(), merger));
    } */
/* private static */ char* Main_generateAll(struct List_char_ref elements, char* (*merger)(char*, char*));/*  {
        return elements.iterate().fold("", merger);
    } */
/* private static */ char* Main_merge(char* buffer, char* element);/*  {
        return buffer + element;
    } */
/* private static */ struct List_char_ref Main_divide(char* input, struct DivideState (*folder)(struct DivideState, char));/*  {
        var current = new DivideState();
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            current = folder.apply(current, c);
        }

        return current.advance().segments;
    } */
/* private static */ struct DivideState Main_foldStatementChar(struct DivideState state, char c);/*  {
        var appended = state.append(c);
        if (c == ';' && appended.isLevel()) {
            return appended.advance();
        }
        if (c == '} */
// Main<>

	/* private static Tuple2<CompileState, String> compileRootSegment(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return new Tuple2Impl<>(state, "");
        }

        return createStructureRule("class ").apply(state, input)
                .orElseGet(() -> new Tuple2Impl<>(state, "\n\t" + generatePlaceholder(stripped.strip())));
    } */
	/* private static BiFunction<CompileState, String, Option<Tuple2<CompileState, String>>> createStructureRule(String infix) {
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
	/* private static BiFunction<CompileState, String, Option<Tuple2<CompileState, String>>> createStructWithoutParametersRule(String beforeKeyword, String content1) {
        return (state1, s) -> {
            return createStructureWithMaybeTypeParametersRule(state1, beforeKeyword, s, content1, new ArrayList<>());
        };
    } */
	/* private static BiFunction<CompileState, String, Option<Tuple2<CompileState, String>>> createStructWithParametersRule(String beforeKeyword, String content1) {
        return (state, input) -> {
            return compileInfix(input, ")", Main::findLast, (withParameters, afterParameters) -> {
                return compileFirst(withParameters, "(", (beforeParameters, parameters) -> {
                    var parametersTuple = parseParameters(state, parameters);
                    return createStructureWithMaybeTypeParametersRule(parametersTuple.left(), beforeKeyword, beforeParameters, content1, parametersTuple.right());
                });
            });
        };
    } */
	/* private static Tuple2<CompileState, List<Parameter>> parseParameters(CompileState state, String parameters) {
        return parseValues(state, parameters, Main::compileParameter);
    } */
	/* private static Tuple2<CompileState, Parameter> compileParameter(CompileState state, String input) {
        return parseDefinition(state, input)
                .map(value -> new Tuple2Impl<CompileState, Parameter>(value.left(), value.right()))
                .orElseGet(() -> new Tuple2Impl<>(state, new Placeholder(input)));
    } */
	/* private static Option<Tuple2<CompileState, String>> createStructureWithMaybeTypeParametersRule(
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
	/* private static BiFunction<CompileState, String, Option<Tuple2<CompileState, String>>> createStructureWithTypeParamsRule(String beforeKeyword, String content, List<Parameter> parameters) {
        return (state, input) -> {
            return compileSuffix(input.strip(), ">", withoutEnd -> {
                return compileFirst(withoutEnd, "<", (name, typeParameters) -> {
                    var typeParams = divide(typeParameters, Main::foldValueChar)
                            .iterate()
                            .map(String::strip)
                            .collect(new ListCollector<>());

                    return new Some<>(new Tuple2Impl<CompileState, String>(state.addExpandable(name, (state0, typeArguments) -> {
                        return assembleStructure(state0, beforeKeyword, name, typeParams, typeArguments, parameters, content);
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
	/* private static BiFunction<CompileState, String, Option<Tuple2<CompileState, String>>> createStructureWithoutTypeParamsRule(
            String beforeKeyword,
            String content,
            List<Parameter> parameters
    ) {
        return (state, name) -> {
            return assembleStructure(state, beforeKeyword, name, new ArrayList<String>(), new ArrayList<Type>(), parameters, content).map(newState -> {
                return new Tuple2Impl<>(newState, "");
            });
        };
    } */
	/* private static Option<CompileState> assembleStructure(
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
            return new None<>();
        }

        var defined = state.mapStack(stack -> stack
                .enter()
                .defineStructPrototype(name)
                .defineTypeParameters(typeParams)
                .defineTypeArguments(typeArguments)
        );

        var statementsTuple = parseStatements(content, defined);
        var type = new ObjectType(name, typeArguments);

        var definitions = parameters.iterate()
                .map(Parameter::toDefinition)
                .flatMap(Iterators::fromOptional)
                .map(Main::generateDefinitionStatement)
                .collect(new ListCollector<>());

        var statements = statementsTuple.right()
                .addAllLast(definitions);

        var generated = generatePlaceholder(beforeStruct.strip()) + type.generate() + " {" + generateAll(statements, Main::merge) + "\n};\n";
        var added = statementsTuple.left()
                .mapStack(Stack::exit)
                .addMethod(createDebug(typeArguments, name))
                .addStruct(generated)
                .addStructure(type);

        return new Some<>(added);
    } */
	/* private static String createDebug(List<Type> typeArguments, String name) {
        var joined = typeArguments.iterate()
                .map(Type::generateAsTemplate)
                .collect(new Joiner(", "))
                .orElse("");

        var asTemplate = name + "<" + joined + ">";
        return "// " + asTemplate + "\n";
    } */
	/* private static Tuple2<CompileState, List<String>> parseStatements(String content, CompileState defined) {
        return parseAll(defined, content, Main::foldStatementChar, Main::compileClassSegment);
    } */
	/* private static <T> Option<T> compileSymbol(String input, Function<String, Option<T>> mapper) {
        if (!isSymbol(input)) {
            return new None<>();
        }

        return mapper.apply(input);
    } */
	/* private static boolean isSymbol(String input) {
        if (input.equals("return") || input.equals("private")) {
            return false;
        }

        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (Character.isLetter(c) || (i != 0 && Character.isDigit(c))) {
                continue;
            }
            return false;
        }
        return true;
    } */
	/* private static Tuple2<CompileState, String> compileClassSegment(CompileState state, String input) {
        return compileOrPlaceholder(state, input, Lists.of(
                createStructureRule("class "),
                createStructureRule("interface "),
                createStructureRule("record "),
                Main::compileDefinitionStatement,
                Main::compileMethod
        ));
    } */
	/* private static Option<Tuple2<CompileState, String>> compileMethod(CompileState state, String input) {
        return compileFirst(input, "(", (definitionString, withParams) -> {
            return compileFirst(withParams, ")", (params, oldContent) -> {
                return parseDefinition(state, definitionString).flatMap(definitionPair -> {
                    var definitionState = definitionPair.left();
                    var definition = definitionPair.right();

                    var parametersTuple = parseParameters(definitionState, params);
                    return assembleMethod(parametersTuple.left(), definition, parametersTuple.right(), oldContent);
                });
            });
        });
    } */
	/* private static Option<Tuple2<CompileState, String>> assembleMethod(
            CompileState state,
            Definition definition,
            List<Parameter> parameters,
            String oldContent
    ) {
        if (!definition.typeParams.isEmpty()) {
            return new Some<>(new Tuple2Impl<CompileState, String>(state, ""));
        }

        Definition newDefinition;
        String newContent;
        if (definition.annotations.contains("Actual", String::equals)) {
            newDefinition = definition.mapType(Type::strip);
            newContent = ";";
        }
        else if (oldContent.equals(";")) {
            newDefinition = definition.mapType(Type::strip).mapName(name -> {
                return joinCurrentName(state, name);
            });

            newContent = ";";
        }
        else {
            newContent = ";" + generatePlaceholder(oldContent);
            newDefinition = definition.mapName(name -> {
                return joinCurrentName(state, name);
            });
        }

        var parametersString = parameters.iterate()
                .map(Parameter::generate)
                .collect(new Joiner(", "))
                .orElse("");

        var generatedHeader = newDefinition.generate() + "(" + parametersString + ")";
        var generated = generatedHeader + newContent + "\n";
        return new Some<>(new Tuple2Impl<CompileState, String>(state.addMethod(generated), ""));
    } */
	/* private static String joinCurrentName(CompileState state, String name) {
        return state.stack
                .findCurrentObjectType()
                .map(currentStructType -> currentStructType.stringify() + "_" + name)
                .orElse(name);
    } */
	/* private static Tuple2<CompileState, String> compileOrPlaceholder(
            CompileState state,
            String input,
            List<BiFunction<CompileState, String, Option<Tuple2<CompileState, String>>>> rules
    ) {
        return compileOr(state, input, rules).orElseGet(() -> new Tuple2Impl<>(state, generatePlaceholder(input.strip())));
    } */
	/* private static <T> Option<Tuple2<CompileState, T>> compileOr(
            CompileState state,
            String input,
            List<BiFunction<CompileState, String, Option<Tuple2<CompileState, T>>>> rules
    ) {
        return rules.iterate()
                .map(rule -> rule.apply(state, input))
                .flatMap(Iterators::fromOptional)
                .next();
    } */
	/* private static Option<Tuple2<CompileState, String>> compileDefinitionStatement(CompileState state, String input) {
        return compileSuffix(input.strip(), ";", withoutEnd -> {
            return parseDefinition(state, withoutEnd)
                    .map(pair -> new Tuple2Impl<>(pair.left(), generateDefinitionStatement(pair.right())));
        });
    } */
	/* private static String generateDefinitionStatement(Definition definition) {
        return "\n\t" + definition.generate() + ";";
    } */
	/* private static Option<Tuple2<CompileState, Definition>> parseDefinition(CompileState state, String input) {
        return compileInfix(input.strip(), " ", Main::findLast, (beforeName, rawName) -> {
            return compileOr(state, beforeName, Lists.of(
                    (state1, s) -> parseDefinitionWithTypeSeparator(beforeName, rawName, state1),
                    (state2, beforeName0) -> parseDefinitionWithoutTypeSeparator(state2, rawName, "", beforeName0)
            ));
        });
    } */
	/* private static Option<Tuple2<CompileState, Definition>> parseDefinitionWithTypeSeparator(String beforeName, String rawName, CompileState state1) {
        return compileInfix(
                () -> splitByTypeSeparator(beforeName),
                (beforeType, type) -> parseDefinitionWithoutTypeSeparator(state1, rawName, beforeType, type));
    } */
	/* private static Option<Tuple2<String, String>> splitByTypeSeparator(String beforeName) {
        var divisions = divide(beforeName, Main::foldTypeSeparator);

        if (divisions.size() >= 2) {
            var maybeRemoved = divisions.removeLast().<Tuple2<String, String>>map(removed -> {
                var joined = joinWithDelimiter(removed.left(), " ");
                return new Tuple2Impl<>(joined, removed.right());
            });

            if (maybeRemoved instanceof Some) {
                return maybeRemoved;
            }
        }

        return new None<>();
    } */
	/* private static Option<Tuple2<CompileState, Definition>> parseDefinitionWithoutTypeSeparator(
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
	/* private static Option<Tuple2<CompileState, Definition>> assembleDefinition(CompileState state, List<String> annotations, String afterAnnotations, String rawName, String type) {
        return compileSuffix(afterAnnotations.strip(), ">", withoutEnd -> {
            return compileFirst(withoutEnd, "<", (beforeTypeParams, typeParams) -> {
                var typeParamsTuple = Main.parseValues(state, typeParams, (state1, s1) -> new Tuple2Impl<>(state1, s1.strip()));
                return getCompileStateDefinitionTuple(typeParamsTuple.left(), annotations, beforeTypeParams.strip(), typeParamsTuple.right(), rawName, type);
            });
        }).or(() -> {
            return getCompileStateDefinitionTuple(state, annotations, afterAnnotations, new ArrayList<>(), rawName, type);
        });
    } */
	/* private static Option<Tuple2<CompileState, Definition>> getCompileStateDefinitionTuple(
            CompileState state,
            List<String> annotations,
            String afterAnnotations,
            List<String> typeParams,
            String rawName,
            String type
    ) {
        return compileSymbol(rawName.strip(), name -> {
            CompileState state1 = state.mapStack(stack -> stack.defineTypeParameters(typeParams));
            return parseType(state1, type).flatMap(typePair -> {
                return new Some<>(new Tuple2Impl<CompileState, Definition>(typePair.left(), new Definition(annotations, afterAnnotations, typePair.right(), name, typeParams)));
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
	/* private static Tuple2<CompileState, Type> parseTypeOrPlaceholder(CompileState state, String input) {
        return parseType(state, input).orElseGet(() -> new Tuple2Impl<>(state, new Placeholder(input.strip())));
    } */
	/* private static Option<Tuple2<CompileState, Type>> parseType(CompileState state, String input) {
        return compileOr(state, input, Lists.of(
                typed(Main::parsePrimitive),
                typed(Main::parseTemplate),
                typed(Main::parseArray),
                typed(Main::parseTypeParam),
                typed(Main::parseStructureType)
        ));
    } */
	/* private static Option<Tuple2<CompileState, Type>> parseStructureType(CompileState state, String input) {
        return compileSymbol(input.strip(), symbol -> {
            if (state.isTypeDefined(symbol)) {
                return new Some<>(new Tuple2Impl<CompileState, Type>(state, new ObjectType(symbol, new ArrayList<>())));
            }
            else {
                return new None<>();
            }
        });
    } */
	/* private static Option<Tuple2<CompileState, Type>> parseTypeParam(CompileState state, String input) {
        var stripped = input.strip();

        if (state.stack.isTypeParamDefined(stripped)) {
            return new Some<>(state.stack.resolveTypeArgument(stripped).map(tuple -> {
                return new Tuple2Impl<>(state, tuple);
            }).orElseGet(() -> new Tuple2Impl<>(state, new TypeParam(stripped))));
        }

        return new None<>();
    } */
	/* private static Option<Tuple2<CompileState, Type>> parseArray(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.endsWith("[]")) {
            var slice = input.substring(0, stripped.length() - "[]".length());
            var childTuple = parseTypeOrPlaceholder(state, slice);
            return new Some<>(new Tuple2Impl<CompileState, Type>(childTuple.left(), new Ref(childTuple.right())));
        }

        return new None<>();
    } */
	/* private static <T extends R, R> BiFunction<CompileState, String, Option<Tuple2<CompileState, R>>> typed(BiFunction<CompileState, String, Option<Tuple2<CompileState, T>>> rule) {
        return (state, input) -> rule.apply(state, input).map(pair -> new Tuple2Impl<>(pair.left(), pair.right()));
    } */
	/* private static Option<Tuple2<CompileState, Type>> parsePrimitive(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.equals("String")) {
            return new Some<>(new Tuple2Impl<CompileState, Type>(state, new Ref(Primitive.Char)));
        }

        if (stripped.equals("int")
                || stripped.equals("Integer")
                || stripped.equals("boolean")
                || stripped.equals("Boolean")) {
            return new Some<>(new Tuple2Impl<CompileState, Type>(state, Primitive.Int));
        }

        if (stripped.equals("char") || stripped.equals("Character")) {
            return new Some<>(new Tuple2Impl<CompileState, Type>(state, Primitive.Char));
        }

        if (stripped.equals("void")) {
            return new Some<>(new Tuple2Impl<CompileState, Type>(state, Primitive.Void));
        }

        return new None<>();
    } */
	/* private static Option<Tuple2<CompileState, Type>> parseTemplate(CompileState oldState, String input) {
        return compileSuffix(input.strip(), ">", withoutEnd -> {
            return Main.compileFirst(withoutEnd, "<", (base, argumentsString) -> {
                var argumentsTuple = parseValues(oldState, argumentsString, Main::parseTypeOrPlaceholder);

                var argumentsState = argumentsTuple.left();
                var arguments = argumentsTuple.right();

                if (base.equals("Supplier")) {
                    var functional = new Functional(arguments.get(0), new ArrayList<>());
                    return new Some<>(new Tuple2Impl<CompileState, Type>(argumentsState, functional));
                }

                if (base.equals("Predicate")) {
                    var functional = new Functional(Primitive.Int, Lists.of(arguments.get(0)));
                    return new Some<>(new Tuple2Impl<CompileState, Type>(argumentsState, functional));
                }

                if (base.equals("Function")) {
                    var functional = new Functional(arguments.get(1), Lists.of(arguments.get(0)));
                    return new Some<>(new Tuple2Impl<CompileState, Type>(argumentsState, functional));
                }

                if (base.equals("BiFunction")) {
                    var functional = new Functional(arguments.get(2), Lists.of(arguments.get(0), arguments.get(1)));
                    return new Some<>(new Tuple2Impl<CompileState, Type>(argumentsState, functional));
                }

                var expansion = new ObjectType(base, arguments);
                CompileState withExpansion;
                if (expansion.isParameterized()) {
                    withExpansion = argumentsState;
                }
                else {
                    withExpansion = argumentsState.expand(expansion).orElse(argumentsState);
                }

                return new Some<>(new Tuple2Impl<CompileState, Type>(withExpansion, expansion));
            });
        });
    } */
	/* private static <T> Tuple2<CompileState, List<T>> parseValues(CompileState oldState, String argumentsString, BiFunction<CompileState, String, Tuple2<CompileState, T>> mapper) {
        return parseAll(oldState, argumentsString, Main::foldValueChar, mapper);
    } */
	/* private static Option<Integer> findLast(String input, String infix) {
        var index = input.lastIndexOf(infix);
        return index == -1 ? new None<Integer>() : new Some<>(index);
    } */
	/* private static <T> Option<T> compileSuffix(String input, String suffix, Function<String, Option<T>> mapper) {
        if (input.endsWith(suffix)) {
            var content = input.substring(0, input.length() - suffix.length());
            return mapper.apply(content);
        }

        return new None<>();
    } */
	/* private static <T> Option<T> compileFirst(String stripped, String infix, BiFunction<String, String, Option<T>> mapper) {
        return compileInfix(stripped, infix, Main::findFirst, mapper);
    } */
	/* private static <T> Option<T> compileInfix(String input, String infix, BiFunction<String, String, Option<Integer>> locate, BiFunction<String, String, Option<T>> mapper) {
        return compileInfix(() -> split(input, infix, locate), mapper);
    } */
	/* private static <T> Option<T> compileInfix(Supplier<Option<Tuple2<String, String>>> supplier, BiFunction<String, String, Option<T>> mapper) {
        return supplier.get().flatMap(pair -> {
            return mapper.apply(pair.left(), pair.right());
        });
    } */
	/* private static Option<Tuple2<String, String>> split(String input, String infix, BiFunction<String, String, Option<Integer>> locate) {
        return locate.apply(input, infix).map(index -> {
            var left = input.substring(0, index);
            var right = input.substring(index + infix.length());
            return new Tuple2Impl<>(left, right);
        });
    } */
	/* private static Option<Integer> findFirst(String input, String infix) {
        var index = input.indexOf(infix);
        return index == -1 ? new None<Integer>() : new Some<>(index);
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
