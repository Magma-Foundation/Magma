/* private */struct Type {/*  */
};
/* Main: [
] */
/* Type: [
] */
char* Type_stringify(/*  */);
char* Type_generate(/*  */);
int Type_equalsTo(struct Type other);
struct Type Type_strip(/*  */);
int Type_isParameterized(/*  */);
/* default */ char* Type_generateWithName(char* name);/*  {
            return this.generate() + " " + name;
        } */
/* private @ */struct Actual {/*  */
};
/* Actual: [
	struct Type
] */
/* private */struct Parameter {/*  */
};
/* Parameter: [
	struct Type, 
	struct Actual
] */
char* Parameter_generate(/*  */);
struct Option_/* Definition */ Parameter_toDefinition(/*  */);
/* private static */struct StandardLibrary {/*  */
};
/* StandardLibrary: [
	struct Type, 
	struct Actual, 
	struct Parameter
] */
/* private static */struct Lists {/*  */
};
/* Lists: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary
] */
/* private sealed */struct Option_char_ref {/*  */
};
/* Main: [
] */
/* Type: [
] */
char* Type_stringify(/*  */);
char* Type_generate(/*  */);
int Type_equalsTo(struct Type other);
struct Type Type_strip(/*  */);
int Type_isParameterized(/*  */);
/* default */ char* Type_generateWithName(char* name);/*  {
            return this.generate() + " " + name;
        } */
/* List: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists
] */
struct List_char_ref List_addLast(char* element);
/* Iterator: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists
] */
struct Iterator_char_ref Iterator_concat(struct Iterator_char_ref other);
/* Option: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists
] */
struct Option_char_ref Option_or(struct Option_char_ref (*other)());
int Option_isPresent(/*  */);
struct Option_char_ref Option_filter(int (*predicate)(char*));
char* Option_orElse(char* other);
char* Option_orElseGet(char* (*other)());
int Option_isEmpty(/*  */);
/* private */struct Iterator_char_ref {/*  */
};
struct Option_char_ref Option_next(/*  */);
int Option_anyMatch(int (*predicate)(char*));
int Option_allMatch(int (*predicate)(char*));
struct Iterator_char_ref Option_filter(int (*predicate)(char*));
/* private sealed */struct Option_Tuple_List_char_ref_char_ref {/*  */
};
/* Main: [
] */
/* Type: [
] */
char* Type_stringify(/*  */);
char* Type_generate(/*  */);
int Type_equalsTo(struct Type other);
struct Type Type_strip(/*  */);
int Type_isParameterized(/*  */);
/* default */ char* Type_generateWithName(char* name);/*  {
            return this.generate() + " " + name;
        } */
/* List: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists
] */
struct List_char_ref List_addLast(char* element);
/* Iterator: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists
] */
struct Iterator_char_ref Iterator_concat(struct Iterator_char_ref other);
/* Option: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists
] */
struct Option_char_ref Option_or(struct Option_char_ref (*other)());
int Option_isPresent(/*  */);
struct Option_char_ref Option_filter(int (*predicate)(char*));
char* Option_orElse(char* other);
char* Option_orElseGet(char* (*other)());
int Option_isEmpty(/*  */);
/* Option: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists, 
	struct Option_char_ref, 
	struct Iterator_char_ref
] */
struct Option_char_ref Option_or(struct Option_char_ref (*other)());
int Option_isPresent(/*  */);
struct Option_char_ref Option_filter(int (*predicate)(char*));
char* Option_orElse(char* other);
char* Option_orElseGet(char* (*other)());
int Option_isEmpty(/*  */);
/* private sealed */struct Option_Tuple_char_ref_int {/*  */
};
/* Main: [
] */
/* Type: [
] */
char* Type_stringify(/*  */);
char* Type_generate(/*  */);
int Type_equalsTo(struct Type other);
struct Type Type_strip(/*  */);
int Type_isParameterized(/*  */);
/* default */ char* Type_generateWithName(char* name);/*  {
            return this.generate() + " " + name;
        } */
/* List: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists
] */
struct List_char_ref List_addLast(char* element);
/* Iterator: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists
] */
struct Iterator_char_ref Iterator_concat(struct Iterator_char_ref other);
/* Option: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists
] */
struct Option_char_ref Option_or(struct Option_char_ref (*other)());
int Option_isPresent(/*  */);
struct Option_char_ref Option_filter(int (*predicate)(char*));
char* Option_orElse(char* other);
char* Option_orElseGet(char* (*other)());
int Option_isEmpty(/*  */);
/* Option: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists, 
	struct Option_char_ref, 
	struct Iterator_char_ref
] */
struct Option_char_ref Option_or(struct Option_char_ref (*other)());
int Option_isPresent(/*  */);
struct Option_char_ref Option_filter(int (*predicate)(char*));
char* Option_orElse(char* other);
char* Option_orElseGet(char* (*other)());
int Option_isEmpty(/*  */);
/* Option: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists, 
	struct Option_char_ref, 
	struct Iterator_char_ref, 
	struct Option_Tuple_List_char_ref_char_ref
] */
struct Option_char_ref Option_or(struct Option_char_ref (*other)());
/* Option: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists, 
	struct Option_char_ref, 
	struct Iterator_char_ref, 
	struct Option_Tuple_List_char_ref_char_ref
] */
struct Option_char_ref Option_or(struct Option_char_ref (*other)());
int Option_isPresent(/*  */);
struct Option_char_ref Option_filter(int (*predicate)(char*));
char* Option_orElse(char* other);
char* Option_orElseGet(char* (*other)());
int Option_isEmpty(/*  */);
/* private sealed */struct Option_int {/*  */
};
int Option_isPresent(/*  */);
struct Option_char_ref Option_filter(int (*predicate)(char*));
char* Option_orElse(char* other);
char* Option_orElseGet(char* (*other)());
int Option_isEmpty(/*  */);
/* private */struct List_char_ref {/*  */
};
struct Option_int Option_indexOf(char* element, int (*equator)(char*, char*));
struct List_char_ref Option_addAllLast(struct List_char_ref others);
struct Iterator_char_ref Option_iterateReversed(/*  */);
char* Option_last(/*  */);
struct List_char_ref Option_mapLast(char* (*mapper)(char*));
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
/* public */ int Option_isLevel(/*  */);/*  {
            return this.depth == 0;
        } */
/* public */ int Option_isShallow(/*  */);/*  {
            return this.depth == 1;
        } */
/* private */struct List_Type {/*  */
};
/* Main: [
] */
/* Type: [
] */
char* Type_stringify(/*  */);
char* Type_generate(/*  */);
int Type_equalsTo(struct Type other);
struct Type Type_strip(/*  */);
int Type_isParameterized(/*  */);
/* default */ char* Type_generateWithName(char* name);/*  {
            return this.generate() + " " + name;
        } */
/* List: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists
] */
struct List_char_ref List_addLast(char* element);
/* Iterator: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists
] */
struct Iterator_char_ref Iterator_concat(struct Iterator_char_ref other);
/* Option: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists
] */
struct Option_char_ref Option_or(struct Option_char_ref (*other)());
int Option_isPresent(/*  */);
struct Option_char_ref Option_filter(int (*predicate)(char*));
char* Option_orElse(char* other);
char* Option_orElseGet(char* (*other)());
int Option_isEmpty(/*  */);
/* Option: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists, 
	struct Option_char_ref, 
	struct Iterator_char_ref
] */
struct Option_char_ref Option_or(struct Option_char_ref (*other)());
int Option_isPresent(/*  */);
struct Option_char_ref Option_filter(int (*predicate)(char*));
char* Option_orElse(char* other);
char* Option_orElseGet(char* (*other)());
int Option_isEmpty(/*  */);
/* Option: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists, 
	struct Option_char_ref, 
	struct Iterator_char_ref, 
	struct Option_Tuple_List_char_ref_char_ref
] */
struct Option_char_ref Option_or(struct Option_char_ref (*other)());
/* Option: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists, 
	struct Option_char_ref, 
	struct Iterator_char_ref, 
	struct Option_Tuple_List_char_ref_char_ref
] */
struct Option_char_ref Option_or(struct Option_char_ref (*other)());
int Option_isPresent(/*  */);
struct Option_char_ref Option_filter(int (*predicate)(char*));
char* Option_orElse(char* other);
char* Option_orElseGet(char* (*other)());
int Option_isEmpty(/*  */);
/* List: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists, 
	struct Option_char_ref, 
	struct Iterator_char_ref, 
	struct Option_Tuple_List_char_ref_char_ref, 
	struct Option_Tuple_char_ref_int, 
	struct Option_int, 
	struct List_char_ref, 
	struct DivideState
] */
struct List_char_ref List_addLast(char* element);
struct Iterator_char_ref List_iterate(/*  */);
int List_contains(char* element, int (*equator)(char*, char*));
int List_equalsTo(struct List_char_ref others, int (*equator)(char*, char*));
int List_size(/*  */);
struct Option_Tuple_List_char_ref_char_ref List_removeLast(/*  */);
int List_isEmpty(/*  */);
char* List_get(int index);
struct Option_int List_indexOf(char* element, int (*equator)(char*, char*));
struct List_char_ref List_addAllLast(struct List_char_ref others);
struct Iterator_char_ref List_iterateReversed(/*  */);
char* List_last(/*  */);
struct List_char_ref List_mapLast(char* (*mapper)(char*));
/* private sealed */struct Option_Type {/*  */
};
/* Main: [
] */
/* Type: [
] */
char* Type_stringify(/*  */);
char* Type_generate(/*  */);
int Type_equalsTo(struct Type other);
struct Type Type_strip(/*  */);
int Type_isParameterized(/*  */);
/* default */ char* Type_generateWithName(char* name);/*  {
            return this.generate() + " " + name;
        } */
/* List: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists
] */
struct List_char_ref List_addLast(char* element);
/* Iterator: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists
] */
struct Iterator_char_ref Iterator_concat(struct Iterator_char_ref other);
/* Option: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists
] */
struct Option_char_ref Option_or(struct Option_char_ref (*other)());
int Option_isPresent(/*  */);
struct Option_char_ref Option_filter(int (*predicate)(char*));
char* Option_orElse(char* other);
char* Option_orElseGet(char* (*other)());
int Option_isEmpty(/*  */);
/* Option: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists, 
	struct Option_char_ref, 
	struct Iterator_char_ref
] */
struct Option_char_ref Option_or(struct Option_char_ref (*other)());
int Option_isPresent(/*  */);
struct Option_char_ref Option_filter(int (*predicate)(char*));
char* Option_orElse(char* other);
char* Option_orElseGet(char* (*other)());
int Option_isEmpty(/*  */);
/* Option: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists, 
	struct Option_char_ref, 
	struct Iterator_char_ref, 
	struct Option_Tuple_List_char_ref_char_ref
] */
struct Option_char_ref Option_or(struct Option_char_ref (*other)());
/* Option: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists, 
	struct Option_char_ref, 
	struct Iterator_char_ref, 
	struct Option_Tuple_List_char_ref_char_ref
] */
struct Option_char_ref Option_or(struct Option_char_ref (*other)());
int Option_isPresent(/*  */);
struct Option_char_ref Option_filter(int (*predicate)(char*));
char* Option_orElse(char* other);
char* Option_orElseGet(char* (*other)());
int Option_isEmpty(/*  */);
/* List: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists, 
	struct Option_char_ref, 
	struct Iterator_char_ref, 
	struct Option_Tuple_List_char_ref_char_ref, 
	struct Option_Tuple_char_ref_int, 
	struct Option_int, 
	struct List_char_ref, 
	struct DivideState
] */
struct List_char_ref List_addLast(char* element);
struct Iterator_char_ref List_iterate(/*  */);
int List_contains(char* element, int (*equator)(char*, char*));
int List_equalsTo(struct List_char_ref others, int (*equator)(char*, char*));
int List_size(/*  */);
struct Option_Tuple_List_char_ref_char_ref List_removeLast(/*  */);
int List_isEmpty(/*  */);
char* List_get(int index);
struct Option_int List_indexOf(char* element, int (*equator)(char*, char*));
struct List_char_ref List_addAllLast(struct List_char_ref others);
struct Iterator_char_ref List_iterateReversed(/*  */);
char* List_last(/*  */);
struct List_char_ref List_mapLast(char* (*mapper)(char*));
/* Option: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists, 
	struct Option_char_ref, 
	struct Iterator_char_ref, 
	struct Option_Tuple_List_char_ref_char_ref, 
	struct Option_Tuple_char_ref_int, 
	struct Option_int, 
	struct List_char_ref, 
	struct DivideState, 
	struct List_Type
] */
struct Option_char_ref Option_or(struct Option_char_ref (*other)());
int Option_isPresent(/*  */);
struct Option_char_ref Option_filter(int (*predicate)(char*));
char* Option_orElse(char* other);
char* Option_orElseGet(char* (*other)());
int Option_isEmpty(/*  */);
/* private */struct Frame {/* public Frame() {
            this(new ArrayList<>(), new ArrayList<>(), new None<String>());
        } *//* public Frame defineTypeParameters(List<String> typeParameters) {
            return new Frame(this.typeParameters.addAllLast(typeParameters), this.typeArguments, this.maybeStructName);
        } *//* public Frame defineStruct(String name) {
            return new Frame(this.typeParameters, this.typeArguments, new Some<String>(name));
        } *//* public Frame defineTypeArguments(List<Type> typeArguments) {
            return new Frame(this.typeParameters, this.typeArguments.addAllLast(typeArguments), this.maybeStructName);
        } *//*  */
	struct List_char_ref typeParameters;
	struct List_Type typeArguments;
	struct Option_char_ref maybeStructName;
};
/* public */ struct Option_Type Option_resolveTypeParam(char* name);/*  {
            return this.typeParameters.indexOf(name, String::equals).flatMap(index -> {
                if (index < this.typeArguments.size()) {
                    return new Some<>(this.typeArguments.get(index));
                }
                return new None<>();
            });
        } */
/* public */ int Option_isTypeParamDefined(char* value);/*  {
            return this.typeParameters.contains(value, String::equals);
        } */
/* private */struct List_Frame {/*  */
};
/* Main: [
] */
/* Type: [
] */
char* Type_stringify(/*  */);
char* Type_generate(/*  */);
int Type_equalsTo(struct Type other);
struct Type Type_strip(/*  */);
int Type_isParameterized(/*  */);
/* default */ char* Type_generateWithName(char* name);/*  {
            return this.generate() + " " + name;
        } */
/* List: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists
] */
struct List_char_ref List_addLast(char* element);
/* Iterator: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists
] */
struct Iterator_char_ref Iterator_concat(struct Iterator_char_ref other);
/* Option: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists
] */
struct Option_char_ref Option_or(struct Option_char_ref (*other)());
int Option_isPresent(/*  */);
struct Option_char_ref Option_filter(int (*predicate)(char*));
char* Option_orElse(char* other);
char* Option_orElseGet(char* (*other)());
int Option_isEmpty(/*  */);
/* Option: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists, 
	struct Option_char_ref, 
	struct Iterator_char_ref
] */
struct Option_char_ref Option_or(struct Option_char_ref (*other)());
int Option_isPresent(/*  */);
struct Option_char_ref Option_filter(int (*predicate)(char*));
char* Option_orElse(char* other);
char* Option_orElseGet(char* (*other)());
int Option_isEmpty(/*  */);
/* Option: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists, 
	struct Option_char_ref, 
	struct Iterator_char_ref, 
	struct Option_Tuple_List_char_ref_char_ref
] */
struct Option_char_ref Option_or(struct Option_char_ref (*other)());
/* Option: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists, 
	struct Option_char_ref, 
	struct Iterator_char_ref, 
	struct Option_Tuple_List_char_ref_char_ref
] */
struct Option_char_ref Option_or(struct Option_char_ref (*other)());
int Option_isPresent(/*  */);
struct Option_char_ref Option_filter(int (*predicate)(char*));
char* Option_orElse(char* other);
char* Option_orElseGet(char* (*other)());
int Option_isEmpty(/*  */);
/* List: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists, 
	struct Option_char_ref, 
	struct Iterator_char_ref, 
	struct Option_Tuple_List_char_ref_char_ref, 
	struct Option_Tuple_char_ref_int, 
	struct Option_int, 
	struct List_char_ref, 
	struct DivideState
] */
struct List_char_ref List_addLast(char* element);
struct Iterator_char_ref List_iterate(/*  */);
int List_contains(char* element, int (*equator)(char*, char*));
int List_equalsTo(struct List_char_ref others, int (*equator)(char*, char*));
int List_size(/*  */);
struct Option_Tuple_List_char_ref_char_ref List_removeLast(/*  */);
int List_isEmpty(/*  */);
char* List_get(int index);
struct Option_int List_indexOf(char* element, int (*equator)(char*, char*));
struct List_char_ref List_addAllLast(struct List_char_ref others);
struct Iterator_char_ref List_iterateReversed(/*  */);
char* List_last(/*  */);
struct List_char_ref List_mapLast(char* (*mapper)(char*));
/* Option: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists, 
	struct Option_char_ref, 
	struct Iterator_char_ref, 
	struct Option_Tuple_List_char_ref_char_ref, 
	struct Option_Tuple_char_ref_int, 
	struct Option_int, 
	struct List_char_ref, 
	struct DivideState, 
	struct List_Type
] */
struct Option_char_ref Option_or(struct Option_char_ref (*other)());
int Option_isPresent(/*  */);
struct Option_char_ref Option_filter(int (*predicate)(char*));
char* Option_orElse(char* other);
char* Option_orElseGet(char* (*other)());
int Option_isEmpty(/*  */);
/* List: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists, 
	struct Option_char_ref, 
	struct Iterator_char_ref, 
	struct Option_Tuple_List_char_ref_char_ref, 
	struct Option_Tuple_char_ref_int, 
	struct Option_int, 
	struct List_char_ref, 
	struct DivideState, 
	struct List_Type, 
	struct Option_Type, 
	struct Frame
] */
struct List_char_ref List_addLast(char* element);
struct Iterator_char_ref List_iterate(/*  */);
int List_contains(char* element, int (*equator)(char*, char*));
int List_equalsTo(struct List_char_ref others, int (*equator)(char*, char*));
int List_size(/*  */);
struct Option_Tuple_List_char_ref_char_ref List_removeLast(/*  */);
int List_isEmpty(/*  */);
char* List_get(int index);
struct Option_int List_indexOf(char* element, int (*equator)(char*, char*));
struct List_char_ref List_addAllLast(struct List_char_ref others);
struct Iterator_char_ref List_iterateReversed(/*  */);
char* List_last(/*  */);
struct List_char_ref List_mapLast(char* (*mapper)(char*));
/* private */struct Stack {/* public Stack() {
            this(new ArrayList<Frame>().addLast(new Frame()));
        } *//*  */
	struct List_Frame frames;
};
/* Stack: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists, 
	struct Option_char_ref, 
	struct Iterator_char_ref, 
	struct Option_Tuple_List_char_ref_char_ref, 
	struct Option_Tuple_char_ref_int, 
	struct Option_int, 
	struct List_char_ref, 
	struct DivideState, 
	struct List_Type, 
	struct Option_Type, 
	struct Frame, 
	struct List_Frame
] */
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
/* public */ struct Option_char_ref Stack_findThisName(/*  */);/*  {
            return this.frames
                    .iterateReversed()
                    .map(Frame::maybeStructName)
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
/* private */struct ObjectType {/*  */
	char* name;
	struct List_Type arguments;
};
/* ObjectType: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists, 
	struct Option_char_ref, 
	struct Iterator_char_ref, 
	struct Option_Tuple_List_char_ref_char_ref, 
	struct Option_Tuple_char_ref_int, 
	struct Option_int, 
	struct List_char_ref, 
	struct DivideState, 
	struct List_Type, 
	struct Option_Type, 
	struct Frame, 
	struct List_Frame, 
	struct Stack
] */
/* public */ char* ObjectType_stringify(/*  */);/*  {
            return this.name + this.joinArguments();
        } */
/* public */ char* ObjectType_generate(/*  */);/*  {
            return "struct " + this.stringify();
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
/* private */ char* ObjectType_joinArguments(/*  */);/*  {
            return this.arguments.iterate()
                    .map(Type::stringify)
                    .collect(new Joiner("_"))
                    .map(result -> "_" + result)
                    .orElse("");
        } */
/* private */struct List_ObjectType {/*  */
};
/* Main: [
] */
/* Type: [
] */
char* Type_stringify(/*  */);
char* Type_generate(/*  */);
int Type_equalsTo(struct Type other);
struct Type Type_strip(/*  */);
int Type_isParameterized(/*  */);
/* default */ char* Type_generateWithName(char* name);/*  {
            return this.generate() + " " + name;
        } */
/* List: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists
] */
struct List_char_ref List_addLast(char* element);
/* Iterator: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists
] */
struct Iterator_char_ref Iterator_concat(struct Iterator_char_ref other);
/* Option: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists
] */
struct Option_char_ref Option_or(struct Option_char_ref (*other)());
int Option_isPresent(/*  */);
struct Option_char_ref Option_filter(int (*predicate)(char*));
char* Option_orElse(char* other);
char* Option_orElseGet(char* (*other)());
int Option_isEmpty(/*  */);
/* Option: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists, 
	struct Option_char_ref, 
	struct Iterator_char_ref
] */
struct Option_char_ref Option_or(struct Option_char_ref (*other)());
int Option_isPresent(/*  */);
struct Option_char_ref Option_filter(int (*predicate)(char*));
char* Option_orElse(char* other);
char* Option_orElseGet(char* (*other)());
int Option_isEmpty(/*  */);
/* Option: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists, 
	struct Option_char_ref, 
	struct Iterator_char_ref, 
	struct Option_Tuple_List_char_ref_char_ref
] */
struct Option_char_ref Option_or(struct Option_char_ref (*other)());
/* Option: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists, 
	struct Option_char_ref, 
	struct Iterator_char_ref, 
	struct Option_Tuple_List_char_ref_char_ref
] */
struct Option_char_ref Option_or(struct Option_char_ref (*other)());
int Option_isPresent(/*  */);
struct Option_char_ref Option_filter(int (*predicate)(char*));
char* Option_orElse(char* other);
char* Option_orElseGet(char* (*other)());
int Option_isEmpty(/*  */);
/* List: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists, 
	struct Option_char_ref, 
	struct Iterator_char_ref, 
	struct Option_Tuple_List_char_ref_char_ref, 
	struct Option_Tuple_char_ref_int, 
	struct Option_int, 
	struct List_char_ref, 
	struct DivideState
] */
struct List_char_ref List_addLast(char* element);
struct Iterator_char_ref List_iterate(/*  */);
int List_contains(char* element, int (*equator)(char*, char*));
int List_equalsTo(struct List_char_ref others, int (*equator)(char*, char*));
int List_size(/*  */);
struct Option_Tuple_List_char_ref_char_ref List_removeLast(/*  */);
int List_isEmpty(/*  */);
char* List_get(int index);
struct Option_int List_indexOf(char* element, int (*equator)(char*, char*));
struct List_char_ref List_addAllLast(struct List_char_ref others);
struct Iterator_char_ref List_iterateReversed(/*  */);
char* List_last(/*  */);
struct List_char_ref List_mapLast(char* (*mapper)(char*));
/* Option: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists, 
	struct Option_char_ref, 
	struct Iterator_char_ref, 
	struct Option_Tuple_List_char_ref_char_ref, 
	struct Option_Tuple_char_ref_int, 
	struct Option_int, 
	struct List_char_ref, 
	struct DivideState, 
	struct List_Type
] */
struct Option_char_ref Option_or(struct Option_char_ref (*other)());
int Option_isPresent(/*  */);
struct Option_char_ref Option_filter(int (*predicate)(char*));
char* Option_orElse(char* other);
char* Option_orElseGet(char* (*other)());
int Option_isEmpty(/*  */);
/* List: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists, 
	struct Option_char_ref, 
	struct Iterator_char_ref, 
	struct Option_Tuple_List_char_ref_char_ref, 
	struct Option_Tuple_char_ref_int, 
	struct Option_int, 
	struct List_char_ref, 
	struct DivideState, 
	struct List_Type, 
	struct Option_Type, 
	struct Frame
] */
struct List_char_ref List_addLast(char* element);
struct Iterator_char_ref List_iterate(/*  */);
int List_contains(char* element, int (*equator)(char*, char*));
int List_equalsTo(struct List_char_ref others, int (*equator)(char*, char*));
int List_size(/*  */);
struct Option_Tuple_List_char_ref_char_ref List_removeLast(/*  */);
int List_isEmpty(/*  */);
char* List_get(int index);
struct Option_int List_indexOf(char* element, int (*equator)(char*, char*));
struct List_char_ref List_addAllLast(struct List_char_ref others);
struct Iterator_char_ref List_iterateReversed(/*  */);
char* List_last(/*  */);
struct List_char_ref List_mapLast(char* (*mapper)(char*));
/* List: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists, 
	struct Option_char_ref, 
	struct Iterator_char_ref, 
	struct Option_Tuple_List_char_ref_char_ref, 
	struct Option_Tuple_char_ref_int, 
	struct Option_int, 
	struct List_char_ref, 
	struct DivideState, 
	struct List_Type, 
	struct Option_Type, 
	struct Frame, 
	struct List_Frame, 
	struct Stack, 
	struct ObjectType
] */
struct List_char_ref List_addLast(char* element);
struct Iterator_char_ref List_iterate(/*  */);
int List_contains(char* element, int (*equator)(char*, char*));
int List_equalsTo(struct List_char_ref others, int (*equator)(char*, char*));
int List_size(/*  */);
struct Option_Tuple_List_char_ref_char_ref List_removeLast(/*  */);
int List_isEmpty(/*  */);
char* List_get(int index);
struct Option_int List_indexOf(char* element, int (*equator)(char*, char*));
struct List_char_ref List_addAllLast(struct List_char_ref others);
struct Iterator_char_ref List_iterateReversed(/*  */);
char* List_last(/*  */);
struct List_char_ref List_mapLast(char* (*mapper)(char*));
/* private sealed */struct Option_CompileState {/*  */
};
/* Main: [
] */
/* Type: [
] */
char* Type_stringify(/*  */);
char* Type_generate(/*  */);
int Type_equalsTo(struct Type other);
struct Type Type_strip(/*  */);
int Type_isParameterized(/*  */);
/* default */ char* Type_generateWithName(char* name);/*  {
            return this.generate() + " " + name;
        } */
/* List: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists
] */
struct List_char_ref List_addLast(char* element);
/* Iterator: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists
] */
struct Iterator_char_ref Iterator_concat(struct Iterator_char_ref other);
/* Option: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists
] */
struct Option_char_ref Option_or(struct Option_char_ref (*other)());
int Option_isPresent(/*  */);
struct Option_char_ref Option_filter(int (*predicate)(char*));
char* Option_orElse(char* other);
char* Option_orElseGet(char* (*other)());
int Option_isEmpty(/*  */);
/* Option: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists, 
	struct Option_char_ref, 
	struct Iterator_char_ref
] */
struct Option_char_ref Option_or(struct Option_char_ref (*other)());
int Option_isPresent(/*  */);
struct Option_char_ref Option_filter(int (*predicate)(char*));
char* Option_orElse(char* other);
char* Option_orElseGet(char* (*other)());
int Option_isEmpty(/*  */);
/* Option: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists, 
	struct Option_char_ref, 
	struct Iterator_char_ref, 
	struct Option_Tuple_List_char_ref_char_ref
] */
struct Option_char_ref Option_or(struct Option_char_ref (*other)());
/* Option: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists, 
	struct Option_char_ref, 
	struct Iterator_char_ref, 
	struct Option_Tuple_List_char_ref_char_ref
] */
struct Option_char_ref Option_or(struct Option_char_ref (*other)());
int Option_isPresent(/*  */);
struct Option_char_ref Option_filter(int (*predicate)(char*));
char* Option_orElse(char* other);
char* Option_orElseGet(char* (*other)());
int Option_isEmpty(/*  */);
/* List: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists, 
	struct Option_char_ref, 
	struct Iterator_char_ref, 
	struct Option_Tuple_List_char_ref_char_ref, 
	struct Option_Tuple_char_ref_int, 
	struct Option_int, 
	struct List_char_ref, 
	struct DivideState
] */
struct List_char_ref List_addLast(char* element);
struct Iterator_char_ref List_iterate(/*  */);
int List_contains(char* element, int (*equator)(char*, char*));
int List_equalsTo(struct List_char_ref others, int (*equator)(char*, char*));
int List_size(/*  */);
struct Option_Tuple_List_char_ref_char_ref List_removeLast(/*  */);
int List_isEmpty(/*  */);
char* List_get(int index);
struct Option_int List_indexOf(char* element, int (*equator)(char*, char*));
struct List_char_ref List_addAllLast(struct List_char_ref others);
struct Iterator_char_ref List_iterateReversed(/*  */);
char* List_last(/*  */);
struct List_char_ref List_mapLast(char* (*mapper)(char*));
/* Option: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists, 
	struct Option_char_ref, 
	struct Iterator_char_ref, 
	struct Option_Tuple_List_char_ref_char_ref, 
	struct Option_Tuple_char_ref_int, 
	struct Option_int, 
	struct List_char_ref, 
	struct DivideState, 
	struct List_Type
] */
struct Option_char_ref Option_or(struct Option_char_ref (*other)());
int Option_isPresent(/*  */);
struct Option_char_ref Option_filter(int (*predicate)(char*));
char* Option_orElse(char* other);
char* Option_orElseGet(char* (*other)());
int Option_isEmpty(/*  */);
/* List: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists, 
	struct Option_char_ref, 
	struct Iterator_char_ref, 
	struct Option_Tuple_List_char_ref_char_ref, 
	struct Option_Tuple_char_ref_int, 
	struct Option_int, 
	struct List_char_ref, 
	struct DivideState, 
	struct List_Type, 
	struct Option_Type, 
	struct Frame
] */
struct List_char_ref List_addLast(char* element);
struct Iterator_char_ref List_iterate(/*  */);
int List_contains(char* element, int (*equator)(char*, char*));
int List_equalsTo(struct List_char_ref others, int (*equator)(char*, char*));
int List_size(/*  */);
struct Option_Tuple_List_char_ref_char_ref List_removeLast(/*  */);
int List_isEmpty(/*  */);
char* List_get(int index);
struct Option_int List_indexOf(char* element, int (*equator)(char*, char*));
struct List_char_ref List_addAllLast(struct List_char_ref others);
struct Iterator_char_ref List_iterateReversed(/*  */);
char* List_last(/*  */);
struct List_char_ref List_mapLast(char* (*mapper)(char*));
/* List: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists, 
	struct Option_char_ref, 
	struct Iterator_char_ref, 
	struct Option_Tuple_List_char_ref_char_ref, 
	struct Option_Tuple_char_ref_int, 
	struct Option_int, 
	struct List_char_ref, 
	struct DivideState, 
	struct List_Type, 
	struct Option_Type, 
	struct Frame, 
	struct List_Frame, 
	struct Stack, 
	struct ObjectType
] */
struct List_char_ref List_addLast(char* element);
struct Iterator_char_ref List_iterate(/*  */);
int List_contains(char* element, int (*equator)(char*, char*));
int List_equalsTo(struct List_char_ref others, int (*equator)(char*, char*));
int List_size(/*  */);
struct Option_Tuple_List_char_ref_char_ref List_removeLast(/*  */);
int List_isEmpty(/*  */);
char* List_get(int index);
struct Option_int List_indexOf(char* element, int (*equator)(char*, char*));
struct List_char_ref List_addAllLast(struct List_char_ref others);
struct Iterator_char_ref List_iterateReversed(/*  */);
char* List_last(/*  */);
struct List_char_ref List_mapLast(char* (*mapper)(char*));
/* Option: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists, 
	struct Option_char_ref, 
	struct Iterator_char_ref, 
	struct Option_Tuple_List_char_ref_char_ref, 
	struct Option_Tuple_char_ref_int, 
	struct Option_int, 
	struct List_char_ref, 
	struct DivideState, 
	struct List_Type, 
	struct Option_Type, 
	struct Frame, 
	struct List_Frame, 
	struct Stack, 
	struct ObjectType, 
	struct List_ObjectType
] */
struct Option_char_ref Option_or(struct Option_char_ref (*other)());
int Option_isPresent(/*  */);
struct Option_char_ref Option_filter(int (*predicate)(char*));
char* Option_orElse(char* other);
char* Option_orElseGet(char* (*other)());
int Option_isEmpty(/*  */);
/* private */struct CompileState {/* public CompileState() {
            this(new ArrayList<>(), new ListMap<>(String::equals), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new Stack());
        } *//* private CompileState addExpansion(ObjectType type) {
            return new CompileState(this.generated, this.expandables, this.expansions.addLast(type), this.structures, this.methods, this.stack);
        } *//* public CompileState addStruct(String structName) {
            return new CompileState(this.generated.addLast(structName)
                    .addAllLast(this.methods), this.expandables, this.expansions, this.structures, new ArrayList<>(), this.stack);
        } *//* public CompileState addExpandable(String name, Function<List<Type>, Option<CompileState>> expandable) {
            return new CompileState(this.generated, this.expandables.put(name, expandable), this.expansions, this.structures, this.methods, this.stack);
        } *//* private CompileState mapStack(Function<Stack, Stack> mapper) {
            return new CompileState(this.generated, this.expandables, this.expansions, this.structures, this.methods, mapper.apply(this.stack));
        } *//* public CompileState addMethod(String method) {
            return new CompileState(this.generated, this.expandables, this.expansions, this.structures, this.methods.addLast(method), this.stack);
        } *//* public CompileState addStructure(ObjectType type) {
            return new CompileState(this.generated, this.expandables, this.expansions, this.structures.addLast(type), this.methods, this.stack);
        } *//*  */
	struct List_char_ref generated;
	struct Map_char_ref_Func_List_Type_Option_/* CompileState */ expandables;
	struct List_ObjectType expansions;
	struct List_ObjectType structures;
	struct List_char_ref methods;
	struct Stack stack;
};
/* private */ struct Option_CompileState Option_expand(struct ObjectType expansion);/*  {
            if (expansion.isParameterized()) {
                return new None<>();
            }

            if (this.expansions.contains(expansion, ObjectType::equalsTo)) {
                return new None<>();
            }

            System.err.println(expansion.generate());
            return this.addExpansion(expansion)
                    .findExpandable(expansion.name)
                    .flatMap(expandable -> expandable.apply(expansion.arguments));
        } */
/* public */ struct Option_Func_List_Type_Option_/* CompileState */ Option_findExpandable(char* name);/*  {
            return this.expandables.find(name);
        } */
/* public */ int Option_isTypeDefined(char* base);/*  {
            return this.isCurrentStructName(base) || this.isStructureDefined(base);
        } */
/* private */ int Option_isStructureDefined(char* base);/*  {
            return this.structures.iterate().anyMatch(structure -> structure.name.equals(base));
        } */
/* private */ int Option_isCurrentStructName(char* base);/*  {
            return this.stack.findThisName().filter(inner -> inner.equals(base)).isPresent();
        } */
/* private */struct Joiner {/* private Joiner() {
            this("");
        } *//*  */
	char* delimiter;
};
/* Joiner: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists, 
	struct Option_char_ref, 
	struct Iterator_char_ref, 
	struct Option_Tuple_List_char_ref_char_ref, 
	struct Option_Tuple_char_ref_int, 
	struct Option_int, 
	struct List_char_ref, 
	struct DivideState, 
	struct List_Type, 
	struct Option_Type, 
	struct Frame, 
	struct List_Frame, 
	struct Stack, 
	struct ObjectType, 
	struct List_ObjectType, 
	struct Option_CompileState, 
	struct CompileState
] */
/* public */ struct Option_char_ref Joiner_createInitial(/*  */);/*  {
            return new None<>();
        } */
/* public */ struct Option_char_ref Joiner_fold(struct Option_char_ref maybeCurrent, char* element);/*  {
            return new Some<>(maybeCurrent.map(current -> current + this.delimiter + element).orElse(element));
        } */
/* private static */struct Iterators {/*  */
};
/* Iterators: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists, 
	struct Option_char_ref, 
	struct Iterator_char_ref, 
	struct Option_Tuple_List_char_ref_char_ref, 
	struct Option_Tuple_char_ref_int, 
	struct Option_int, 
	struct List_char_ref, 
	struct DivideState, 
	struct List_Type, 
	struct Option_Type, 
	struct Frame, 
	struct List_Frame, 
	struct Stack, 
	struct ObjectType, 
	struct List_ObjectType, 
	struct Option_CompileState, 
	struct CompileState, 
	struct Joiner
] */
/* private */struct Ref {/*  */
	struct Type type;
};
/* Ref: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists, 
	struct Option_char_ref, 
	struct Iterator_char_ref, 
	struct Option_Tuple_List_char_ref_char_ref, 
	struct Option_Tuple_char_ref_int, 
	struct Option_int, 
	struct List_char_ref, 
	struct DivideState, 
	struct List_Type, 
	struct Option_Type, 
	struct Frame, 
	struct List_Frame, 
	struct Stack, 
	struct ObjectType, 
	struct List_ObjectType, 
	struct Option_CompileState, 
	struct CompileState, 
	struct Joiner, 
	struct Iterators
] */
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
/* private */struct Placeholder {/*  */
	char* value;
};
/* Placeholder: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists, 
	struct Option_char_ref, 
	struct Iterator_char_ref, 
	struct Option_Tuple_List_char_ref_char_ref, 
	struct Option_Tuple_char_ref_int, 
	struct Option_int, 
	struct List_char_ref, 
	struct DivideState, 
	struct List_Type, 
	struct Option_Type, 
	struct Frame, 
	struct List_Frame, 
	struct Stack, 
	struct ObjectType, 
	struct List_ObjectType, 
	struct Option_CompileState, 
	struct CompileState, 
	struct Joiner, 
	struct Iterators, 
	struct Ref
] */
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
/* private sealed */struct Option_Definition {/*  */
};
/* Main: [
] */
/* Type: [
] */
char* Type_stringify(/*  */);
char* Type_generate(/*  */);
int Type_equalsTo(struct Type other);
struct Type Type_strip(/*  */);
int Type_isParameterized(/*  */);
/* default */ char* Type_generateWithName(char* name);/*  {
            return this.generate() + " " + name;
        } */
/* List: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists
] */
struct List_char_ref List_addLast(char* element);
/* Iterator: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists
] */
struct Iterator_char_ref Iterator_concat(struct Iterator_char_ref other);
/* Option: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists
] */
struct Option_char_ref Option_or(struct Option_char_ref (*other)());
int Option_isPresent(/*  */);
struct Option_char_ref Option_filter(int (*predicate)(char*));
char* Option_orElse(char* other);
char* Option_orElseGet(char* (*other)());
int Option_isEmpty(/*  */);
/* Option: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists, 
	struct Option_char_ref, 
	struct Iterator_char_ref
] */
struct Option_char_ref Option_or(struct Option_char_ref (*other)());
int Option_isPresent(/*  */);
struct Option_char_ref Option_filter(int (*predicate)(char*));
char* Option_orElse(char* other);
char* Option_orElseGet(char* (*other)());
int Option_isEmpty(/*  */);
/* Option: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists, 
	struct Option_char_ref, 
	struct Iterator_char_ref, 
	struct Option_Tuple_List_char_ref_char_ref
] */
struct Option_char_ref Option_or(struct Option_char_ref (*other)());
/* Option: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists, 
	struct Option_char_ref, 
	struct Iterator_char_ref, 
	struct Option_Tuple_List_char_ref_char_ref
] */
struct Option_char_ref Option_or(struct Option_char_ref (*other)());
int Option_isPresent(/*  */);
struct Option_char_ref Option_filter(int (*predicate)(char*));
char* Option_orElse(char* other);
char* Option_orElseGet(char* (*other)());
int Option_isEmpty(/*  */);
/* List: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists, 
	struct Option_char_ref, 
	struct Iterator_char_ref, 
	struct Option_Tuple_List_char_ref_char_ref, 
	struct Option_Tuple_char_ref_int, 
	struct Option_int, 
	struct List_char_ref, 
	struct DivideState
] */
struct List_char_ref List_addLast(char* element);
struct Iterator_char_ref List_iterate(/*  */);
int List_contains(char* element, int (*equator)(char*, char*));
int List_equalsTo(struct List_char_ref others, int (*equator)(char*, char*));
int List_size(/*  */);
struct Option_Tuple_List_char_ref_char_ref List_removeLast(/*  */);
int List_isEmpty(/*  */);
char* List_get(int index);
struct Option_int List_indexOf(char* element, int (*equator)(char*, char*));
struct List_char_ref List_addAllLast(struct List_char_ref others);
struct Iterator_char_ref List_iterateReversed(/*  */);
char* List_last(/*  */);
struct List_char_ref List_mapLast(char* (*mapper)(char*));
/* Option: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists, 
	struct Option_char_ref, 
	struct Iterator_char_ref, 
	struct Option_Tuple_List_char_ref_char_ref, 
	struct Option_Tuple_char_ref_int, 
	struct Option_int, 
	struct List_char_ref, 
	struct DivideState, 
	struct List_Type
] */
struct Option_char_ref Option_or(struct Option_char_ref (*other)());
int Option_isPresent(/*  */);
struct Option_char_ref Option_filter(int (*predicate)(char*));
char* Option_orElse(char* other);
char* Option_orElseGet(char* (*other)());
int Option_isEmpty(/*  */);
/* List: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists, 
	struct Option_char_ref, 
	struct Iterator_char_ref, 
	struct Option_Tuple_List_char_ref_char_ref, 
	struct Option_Tuple_char_ref_int, 
	struct Option_int, 
	struct List_char_ref, 
	struct DivideState, 
	struct List_Type, 
	struct Option_Type, 
	struct Frame
] */
struct List_char_ref List_addLast(char* element);
struct Iterator_char_ref List_iterate(/*  */);
int List_contains(char* element, int (*equator)(char*, char*));
int List_equalsTo(struct List_char_ref others, int (*equator)(char*, char*));
int List_size(/*  */);
struct Option_Tuple_List_char_ref_char_ref List_removeLast(/*  */);
int List_isEmpty(/*  */);
char* List_get(int index);
struct Option_int List_indexOf(char* element, int (*equator)(char*, char*));
struct List_char_ref List_addAllLast(struct List_char_ref others);
struct Iterator_char_ref List_iterateReversed(/*  */);
char* List_last(/*  */);
struct List_char_ref List_mapLast(char* (*mapper)(char*));
/* List: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists, 
	struct Option_char_ref, 
	struct Iterator_char_ref, 
	struct Option_Tuple_List_char_ref_char_ref, 
	struct Option_Tuple_char_ref_int, 
	struct Option_int, 
	struct List_char_ref, 
	struct DivideState, 
	struct List_Type, 
	struct Option_Type, 
	struct Frame, 
	struct List_Frame, 
	struct Stack, 
	struct ObjectType
] */
struct List_char_ref List_addLast(char* element);
struct Iterator_char_ref List_iterate(/*  */);
int List_contains(char* element, int (*equator)(char*, char*));
int List_equalsTo(struct List_char_ref others, int (*equator)(char*, char*));
int List_size(/*  */);
struct Option_Tuple_List_char_ref_char_ref List_removeLast(/*  */);
int List_isEmpty(/*  */);
char* List_get(int index);
struct Option_int List_indexOf(char* element, int (*equator)(char*, char*));
struct List_char_ref List_addAllLast(struct List_char_ref others);
struct Iterator_char_ref List_iterateReversed(/*  */);
char* List_last(/*  */);
struct List_char_ref List_mapLast(char* (*mapper)(char*));
/* Option: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists, 
	struct Option_char_ref, 
	struct Iterator_char_ref, 
	struct Option_Tuple_List_char_ref_char_ref, 
	struct Option_Tuple_char_ref_int, 
	struct Option_int, 
	struct List_char_ref, 
	struct DivideState, 
	struct List_Type, 
	struct Option_Type, 
	struct Frame, 
	struct List_Frame, 
	struct Stack, 
	struct ObjectType, 
	struct List_ObjectType
] */
struct Option_char_ref Option_or(struct Option_char_ref (*other)());
int Option_isPresent(/*  */);
struct Option_char_ref Option_filter(int (*predicate)(char*));
char* Option_orElse(char* other);
char* Option_orElseGet(char* (*other)());
int Option_isEmpty(/*  */);
/* Option: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists, 
	struct Option_char_ref, 
	struct Iterator_char_ref, 
	struct Option_Tuple_List_char_ref_char_ref, 
	struct Option_Tuple_char_ref_int, 
	struct Option_int, 
	struct List_char_ref, 
	struct DivideState, 
	struct List_Type, 
	struct Option_Type, 
	struct Frame, 
	struct List_Frame, 
	struct Stack, 
	struct ObjectType, 
	struct List_ObjectType, 
	struct Option_CompileState, 
	struct CompileState, 
	struct Joiner, 
	struct Iterators, 
	struct Ref, 
	struct Placeholder
] */
struct Option_char_ref Option_or(struct Option_char_ref (*other)());
int Option_isPresent(/*  */);
struct Option_char_ref Option_filter(int (*predicate)(char*));
char* Option_orElse(char* other);
char* Option_orElseGet(char* (*other)());
int Option_isEmpty(/*  */);
/* private */struct Definition {/* public Definition mapType(Function<Type, Type> mapper) {
            return new Definition(this.annotations, this.afterAnnotations, mapper.apply(this.type), this.name, this.typeParams);
        } *//* public Definition mapName(Function<String, String> mapper) {
            return new Definition(this.annotations, this.afterAnnotations, this.type, mapper.apply(this.name), this.typeParams);
        } *//*  */
	struct List_char_ref annotations;
	char* afterAnnotations;
	struct Type type;
	char* name;
	struct List_char_ref typeParams;
};
/* public */ struct Option_Definition Option_toDefinition(/*  */);/*  {
            return new Some<>(this);
        } */
/* private */ char* Option_generateAfterAnnotations(/*  */);/*  {
            return this.afterAnnotations.isEmpty() ? "" : (generatePlaceholder(this.afterAnnotations()) + " ");
        } */
/* private */struct TypeParam {/*  */
	char* input;
};
/* TypeParam: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists, 
	struct Option_char_ref, 
	struct Iterator_char_ref, 
	struct Option_Tuple_List_char_ref_char_ref, 
	struct Option_Tuple_char_ref_int, 
	struct Option_int, 
	struct List_char_ref, 
	struct DivideState, 
	struct List_Type, 
	struct Option_Type, 
	struct Frame, 
	struct List_Frame, 
	struct Stack, 
	struct ObjectType, 
	struct List_ObjectType, 
	struct Option_CompileState, 
	struct CompileState, 
	struct Joiner, 
	struct Iterators, 
	struct Ref, 
	struct Placeholder, 
	struct Option_Definition, 
	struct Definition
] */
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
/* private */struct Functional {/*  */
	struct Type returnType;
	struct List_Type typeParameters;
};
/* Functional: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists, 
	struct Option_char_ref, 
	struct Iterator_char_ref, 
	struct Option_Tuple_List_char_ref_char_ref, 
	struct Option_Tuple_char_ref_int, 
	struct Option_int, 
	struct List_char_ref, 
	struct DivideState, 
	struct List_Type, 
	struct Option_Type, 
	struct Frame, 
	struct List_Frame, 
	struct Stack, 
	struct ObjectType, 
	struct List_ObjectType, 
	struct Option_CompileState, 
	struct CompileState, 
	struct Joiner, 
	struct Iterators, 
	struct Ref, 
	struct Placeholder, 
	struct Option_Definition, 
	struct Definition, 
	struct TypeParam
] */
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

            return this.returnType.generate() + " (*" +
                    name +
                    ")(" + joined + ")";
        } */
/* private */struct Tuple_CompileState_char_ref {/*  */
};
/* Joiner: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists, 
	struct Option_char_ref, 
	struct Iterator_char_ref, 
	struct Option_Tuple_List_char_ref_char_ref, 
	struct Option_Tuple_char_ref_int, 
	struct Option_int, 
	struct List_char_ref, 
	struct DivideState, 
	struct List_Type, 
	struct Option_Type, 
	struct Frame, 
	struct List_Frame, 
	struct Stack, 
	struct ObjectType, 
	struct List_ObjectType, 
	struct Option_CompileState, 
	struct CompileState
] */
/* public */ struct Option_char_ref Joiner_createInitial(/*  */);/*  {
            return new None<>();
        } */
/* public */ struct Option_char_ref Joiner_fold(struct Option_char_ref maybeCurrent, char* element);/*  {
            return new Some<>(maybeCurrent.map(current -> current + this.delimiter + element).orElse(element));
        } */
/* Tuple: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists, 
	struct Option_char_ref, 
	struct Iterator_char_ref, 
	struct Option_Tuple_List_char_ref_char_ref, 
	struct Option_Tuple_char_ref_int, 
	struct Option_int, 
	struct List_char_ref, 
	struct DivideState, 
	struct List_Type, 
	struct Option_Type, 
	struct Frame, 
	struct List_Frame, 
	struct Stack, 
	struct ObjectType, 
	struct List_ObjectType, 
	struct Option_CompileState, 
	struct CompileState, 
	struct Joiner, 
	struct Iterators, 
	struct Ref, 
	struct Placeholder, 
	struct Option_Definition, 
	struct Definition, 
	struct TypeParam, 
	struct Functional
] */
/* private */struct Tuple_CompileState_List_char_ref {/*  */
};
/* Joiner: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists, 
	struct Option_char_ref, 
	struct Iterator_char_ref, 
	struct Option_Tuple_List_char_ref_char_ref, 
	struct Option_Tuple_char_ref_int, 
	struct Option_int, 
	struct List_char_ref, 
	struct DivideState, 
	struct List_Type, 
	struct Option_Type, 
	struct Frame, 
	struct List_Frame, 
	struct Stack, 
	struct ObjectType, 
	struct List_ObjectType, 
	struct Option_CompileState, 
	struct CompileState
] */
/* public */ struct Option_char_ref Joiner_createInitial(/*  */);/*  {
            return new None<>();
        } */
/* public */ struct Option_char_ref Joiner_fold(struct Option_char_ref maybeCurrent, char* element);/*  {
            return new Some<>(maybeCurrent.map(current -> current + this.delimiter + element).orElse(element));
        } */
/* Tuple: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists, 
	struct Option_char_ref, 
	struct Iterator_char_ref, 
	struct Option_Tuple_List_char_ref_char_ref, 
	struct Option_Tuple_char_ref_int, 
	struct Option_int, 
	struct List_char_ref, 
	struct DivideState, 
	struct List_Type, 
	struct Option_Type, 
	struct Frame, 
	struct List_Frame, 
	struct Stack, 
	struct ObjectType, 
	struct List_ObjectType, 
	struct Option_CompileState, 
	struct CompileState, 
	struct Joiner, 
	struct Iterators, 
	struct Ref, 
	struct Placeholder, 
	struct Option_Definition, 
	struct Definition, 
	struct TypeParam, 
	struct Functional
] */
/* Tuple: [
	struct Type, 
	struct Actual, 
	struct Parameter, 
	struct StandardLibrary, 
	struct Lists, 
	struct Option_char_ref, 
	struct Iterator_char_ref, 
	struct Option_Tuple_List_char_ref_char_ref, 
	struct Option_Tuple_char_ref_int, 
	struct Option_int, 
	struct List_char_ref, 
	struct DivideState, 
	struct List_Type, 
	struct Option_Type, 
	struct Frame, 
	struct List_Frame, 
	struct Stack, 
	struct ObjectType, 
	struct List_ObjectType, 
	struct Option_CompileState, 
	struct CompileState, 
	struct Joiner, 
	struct Iterators, 
	struct Ref, 
	struct Placeholder, 
	struct Option_Definition, 
	struct Definition, 
	struct TypeParam, 
	struct Functional, 
	struct Tuple_CompileState_char_ref
] */
/* public */struct Main {/* ' && appended.isShallow()) {
            return appended.advance().exit();
        } *//* if (c == '{') {
            return appended.enter();
        }
        if (c == '} *//* ') {
            return appended.exit();
        } *//* return appended; *//*  */
};
/* private static */ char* Tuple_generateAll(struct List_char_ref elements, char* (*merger)(char*, char*));/*  {
        return elements.iterate().fold("", merger);
    } */
/* private static */ char* Tuple_merge(char* buffer, char* element);/*  {
        return buffer + element;
    } */
/* private static */ struct List_char_ref Tuple_divide(char* input, struct DivideState (*folder)(struct DivideState, char));/*  {
        var current = new DivideState();
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            current = folder.apply(current, c);
        }

        return current.advance().segments;
    } */
/* private static */ struct DivideState Tuple_foldStatementChar(struct DivideState state, char c);/*  {
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
	/* private static BiFunction<CompileState, String, Option<Tuple<CompileState, String>>> createStructureRule(String infix) {
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
	/* private static BiFunction<CompileState, String, Option<Tuple<CompileState, String>>> createStructWithoutParametersRule(String beforeKeyword, String content1) {
        return (state1, s) -> {
            return createStructureWithMaybeTypeParametersRule(state1, beforeKeyword, s, content1, new ArrayList<>());
        };
    } */
	/* private static BiFunction<CompileState, String, Option<Tuple<CompileState, String>>> createStructWithParametersRule(String beforeKeyword, String content1) {
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
	/* private static Option<Tuple<CompileState, String>> createStructureWithMaybeTypeParametersRule(
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
	/* private static BiFunction<CompileState, String, Option<Tuple<CompileState, String>>> createStructureWithTypeParamsRule(String beforeKeyword, String content, List<Parameter> parameters) {
        return (state, input) -> {
            return compileSuffix(input.strip(), ">", withoutEnd -> {
                return compileFirst(withoutEnd, "<", (name, typeParameters) -> {
                    var typeParams = divide(typeParameters, Main::foldValueChar)
                            .iterate()
                            .map(String::strip)
                            .collect(new ListCollector<>());

                    return new Some<>(new Tuple<CompileState, String>(state.addExpandable(name, (typeArguments) -> {
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
	/* private static BiFunction<CompileState, String, Option<Tuple<CompileState, String>>> createStructureWithoutTypeParamsRule(
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

        var joinedSymbols = state.structures
                .iterate()
                .map(ObjectType::generate)
                .map(inner -> "\n\t" + inner)
                .collect(new Joiner(", "))
                .orElse("");

        var defined = state.addMethod(generatePlaceholder(name + ": [" + joinedSymbols + "\n]") + "\n").mapStack(stack -> stack
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

        var statements = statementsTuple.right
                .addAllLast(definitions);

        var generated = generatePlaceholder(beforeStruct.strip()) + type.generate() + " {" + generateAll(statements, Main::merge) + "\n};\n";
        var added = statementsTuple.left.addStruct(generated).addStructure(type);
        return new Some<>(added);
    } */
	/* private static Tuple<CompileState, List<String>> parseStatements(String content, CompileState defined) {
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
	/* private static Option<Tuple<CompileState, String>> compileMethod(CompileState state, String input) {
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
	/* private static Option<Tuple<CompileState, String>> assembleMethod(
            CompileState state,
            Definition definition,
            List<Parameter> parameters,
            String oldContent
    ) {
        if (!definition.typeParams.isEmpty()) {
            return new Some<>(new Tuple<CompileState, String>(state, ""));
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
        return new Some<>(new Tuple<CompileState, String>(state.addMethod(generated), ""));
    } */
	/* private static String joinCurrentName(CompileState state, String name) {
        return state.stack
                .findThisName()
                .map(currentStructName -> currentStructName + "_" + name)
                .orElse(name);
    } */
	/* private static Tuple<CompileState, String> compileOrPlaceholder(
            CompileState state,
            String input,
            List<BiFunction<CompileState, String, Option<Tuple<CompileState, String>>>> rules
    ) {
        return compileOr(state, input, rules).orElseGet(() -> new Tuple<>(state, generatePlaceholder(input.strip())));
    } */
	/* private static <T> Option<Tuple<CompileState, T>> compileOr(
            CompileState state,
            String input,
            List<BiFunction<CompileState, String, Option<Tuple<CompileState, T>>>> rules
    ) {
        return rules.iterate()
                .map(rule -> rule.apply(state, input))
                .flatMap(Iterators::fromOptional)
                .next();
    } */
	/* private static Option<Tuple<CompileState, String>> compileDefinitionStatement(CompileState state, String input) {
        return compileSuffix(input.strip(), ";", withoutEnd -> {
            return parseDefinition(state, withoutEnd)
                    .map(tuple -> new Tuple<>(tuple.left, generateDefinitionStatement(tuple.right)));
        });
    } */
	/* private static String generateDefinitionStatement(Definition definition) {
        return "\n\t" + definition.generate() + ";";
    } */
	/* private static Option<Tuple<CompileState, Definition>> parseDefinition(CompileState state, String input) {
        return compileInfix(input.strip(), " ", Main::findLast, (beforeName, rawName) -> {
            return compileOr(state, beforeName, Lists.of(
                    (state1, s) -> compileInfix(() -> {
                        var divisions = divide(beforeName, Main::foldTypeSeparator);

                        if (divisions.size() >= 2) {
                            var maybeRemoved = divisions.removeLast().map(removed -> {
                                var joined = joinWithDelimiter(removed.left, " ");
                                return new Tuple<>(joined, removed.right);
                            });

                            if (maybeRemoved instanceof Some) {
                                return maybeRemoved;
                            }
                        }

                        return new None<>();
                    }, (beforeType, type) -> {
                        return getCompileStateDefinitionTuple(state1, rawName, beforeType, type);
                    }),
                    (state2, s) -> getCompileStateDefinitionTuple(state2, rawName, "", s)
            ));
        });
    } */
	/* private static Option<Tuple<CompileState, Definition>> getCompileStateDefinitionTuple(
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
	/* private static Option<Tuple<CompileState, Definition>> assembleDefinition(CompileState state, List<String> annotations, String afterAnnotations, String rawName, String type) {
        return compileSuffix(afterAnnotations.strip(), ">", withoutEnd -> {
            return compileFirst(withoutEnd, "<", (beforeTypeParams, typeParams) -> {
                var typeParamsTuple = Main.parseValues(state, typeParams, (state1, s1) -> new Tuple<>(state1, s1.strip()));
                return getCompileStateDefinitionTuple(typeParamsTuple.left, annotations, beforeTypeParams.strip(), typeParamsTuple.right, rawName, type);
            });
        }).or(() -> {
            return getCompileStateDefinitionTuple(state, annotations, afterAnnotations, new ArrayList<>(), rawName, type);
        });
    } */
	/* private static Option<Tuple<CompileState, Definition>> getCompileStateDefinitionTuple(
            CompileState state,
            List<String> annotations,
            String afterAnnotations,
            List<String> typeParams,
            String rawName,
            String type
    ) {
        return compileSymbol(rawName.strip(), name -> {
            CompileState state1 = state.mapStack(stack -> stack.defineTypeParameters(typeParams));
            return parseType(state1, type).flatMap(typeTuple -> {
                return new Some<>(new Tuple<CompileState, Definition>(typeTuple.left, new Definition(annotations, afterAnnotations, typeTuple.right, name, typeParams)));
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
	/* private static Option<Tuple<CompileState, Type>> parseType(CompileState state, String input) {
        return compileOr(state, input, Lists.of(
                typed(Main::parsePrimitive),
                typed(Main::parseTemplate),
                typed(Main::parseArray),
                typed(Main::parseTypeParam),
                typed(Main::parseStructureType)
        ));
    } */
	/* private static Option<Tuple<CompileState, Type>> parseStructureType(CompileState state, String input) {
        return compileSymbol(input.strip(), symbol -> {
            if (state.isTypeDefined(symbol)) {
                return new Some<>(new Tuple<CompileState, Type>(state, new ObjectType(symbol, new ArrayList<>())));
            }
            else {
                return new None<>();
            }
        });
    } */
	/* private static Option<Tuple<CompileState, Type>> parseTypeParam(CompileState state, String input) {
        var stripped = input.strip();

        if (state.stack.isTypeParamDefined(stripped)) {
            return new Some<>(state.stack.resolveTypeArgument(stripped).map(tuple -> {
                return new Tuple<>(state, tuple);
            }).orElseGet(() -> new Tuple<>(state, new TypeParam(stripped))));
        }

        return new None<>();
    } */
	/* private static Option<Tuple<CompileState, Type>> parseArray(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.endsWith("[]")) {
            var slice = input.substring(0, stripped.length() - "[]".length());
            var childTuple = parseTypeOrPlaceholder(state, slice);
            return new Some<>(new Tuple<CompileState, Type>(childTuple.left, new Ref(childTuple.right)));
        }

        return new None<>();
    } */
	/* private static <T extends R, R> BiFunction<CompileState, String, Option<Tuple<CompileState, R>>> typed(BiFunction<CompileState, String, Option<Tuple<CompileState, T>>> rule) {
        return (state, input) -> rule.apply(state, input).map(tuple -> new Tuple<>(tuple.left, tuple.right));
    } */
	/* private static Option<Tuple<CompileState, Type>> parsePrimitive(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.equals("String")) {
            return new Some<>(new Tuple<CompileState, Type>(state, new Ref(Primitive.Char)));
        }

        if (stripped.equals("int")
                || stripped.equals("Integer")
                || stripped.equals("boolean")
                || stripped.equals("Boolean")) {
            return new Some<>(new Tuple<CompileState, Type>(state, Primitive.Int));
        }

        if (stripped.equals("char") || stripped.equals("Character")) {
            return new Some<>(new Tuple<CompileState, Type>(state, Primitive.Char));
        }

        if (stripped.equals("void")) {
            return new Some<>(new Tuple<CompileState, Type>(state, Primitive.Void));
        }

        return new None<>();
    } */
	/* private static Option<Tuple<CompileState, Type>> parseTemplate(CompileState oldState, String input) {
        return compileSuffix(input.strip(), ">", withoutEnd -> {
            return Main.compileFirst(withoutEnd, "<", (base, argumentsString) -> {
                var argumentsTuple = parseValues(oldState, argumentsString, Main::parseTypeOrPlaceholder);

                var argumentsState = argumentsTuple.left;
                var arguments = argumentsTuple.right;

                if (base.equals("Supplier")) {
                    var functional = new Functional(arguments.get(0), new ArrayList<>());
                    return new Some<>(new Tuple<CompileState, Type>(argumentsState, functional));
                }

                if (base.equals("Predicate")) {
                    var functional = new Functional(Primitive.Int, Lists.of(arguments.get(0)));
                    return new Some<>(new Tuple<CompileState, Type>(argumentsState, functional));
                }

                if (base.equals("Function")) {
                    var functional = new Functional(arguments.get(1), Lists.of(arguments.get(0)));
                    return new Some<>(new Tuple<CompileState, Type>(argumentsState, functional));
                }

                if (base.equals("BiFunction")) {
                    var functional = new Functional(arguments.get(2), Lists.of(arguments.get(0), arguments.get(1)));
                    return new Some<>(new Tuple<CompileState, Type>(argumentsState, functional));
                }

                var expansion = new ObjectType(base, arguments);
                CompileState withExpansion;
                if (expansion.isParameterized()) {
                    withExpansion = argumentsState;
                }
                else {
                    withExpansion = argumentsState.expand(expansion).orElse(argumentsState);
                }

                return new Some<>(new Tuple<CompileState, Type>(withExpansion, expansion));
            });
        });
    } */
	/* private static <T> Tuple<CompileState, List<T>> parseValues(CompileState oldState, String argumentsString, BiFunction<CompileState, String, Tuple<CompileState, T>> mapper) {
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
	/* private static <T> Option<T> compileInfix(Supplier<Option<Tuple<String, String>>> supplier, BiFunction<String, String, Option<T>> mapper) {
        return supplier.get().flatMap(tuple -> {
            return mapper.apply(tuple.left, tuple.right);
        });
    } */
	/* private static Option<Tuple<String, String>> split(String input, String infix, BiFunction<String, String, Option<Integer>> locate) {
        return locate.apply(input, infix).map(index -> {
            var left = input.substring(0, index);
            var right = input.substring(index + infix.length());
            return new Tuple<>(left, right);
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
