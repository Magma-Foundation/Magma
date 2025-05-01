enum OptionVariant {
	Some,
	None
};
union OptionValue<T> {
	Some<T> some;
	None<T> none;
};
/* private sealed */struct Option<T> {
	OptionVariant _variant;
	OptionValue<T> _value;
};
/* private */struct Head<S, T> {
	S _super;
	template Option<T> (*next)(S);
};
/* private */struct List<S, T> {
	S _super;
	template List<T> (*addLast)(S, T);
	template Iterator<T> (*iterate)(S);
	template Option<(template List<T>, T)> (*removeLast)(S);
	int (*isEmpty)(S);
	T (*get)(S, int);
	template List<T> (*addFirst)(S, T);
	int (*contains)(S, T);
};
/* private */struct Collector<S, T, C> {
	S _super;
	C (*createInitial)(S);
	C (*fold)(S, C, T);
};
/* private */struct Splitter<S> {
	S _super;
	template Option<(char*, char*)> (*split)(S, char*);
};
/* private */struct Type extends Node<S> {
	S _super;
};
/* private */struct Parameter extends Node<S> {
	S _super;
};
/* private */struct Node<S> {
	S _super;
	struct String_ (*generate)(S);
};
/* private */struct String_<S> {
	S _super;
	char* (*toSlice)(S);
	struct String_ (*appendSlice)(S, char*);
};
/* private */struct Some<T>(T value) implements Option<T> {
};
/* private */struct None<T>() implements Option<T> {
};
/* private */struct Iterator<T> {template Head<struct T> head
};
/* private */struct Joiner(String delimiter) implements Collector<String, Option<String>> {
};
/* private */struct StructurePrototype {char* typechar* nametemplate List<char*> typeParamstemplate List<char*> variants
};
/* private */struct CompileState {template List<char*> structstemplate List<char*> functionstemplate Option<struct StructurePrototype> maybeStructureType
};
/* private */struct DivideState {char* inputtemplate List<char*> segmentsstruct StringBuilder bufferint indexint depth
};
/* private */struct Tuple<A, B> {struct A leftstruct B right
};
/* private */struct InfixSplitter(String infix,
                                 BiFunction<String, String, Option<Integer>> locator) implements Splitter {
};
/* private */struct Definition(Option<String> maybeBeforeType, Type type, String name) implements Parameter {
};
/* private */struct Content(String input) implements Type, Parameter {
};
/* private */struct Functional(List<Type> arguments, Type returns) implements Type {
};
/* private */struct Template(String base, List<Type> arguments) implements Type {
};
/* private */struct TypeParameter(String value) implements Type {
};
/* private */struct Ref(Type type) implements Type {
};
/* private */struct TupleType(List<Type> arguments) implements Type {
};
/* private */struct StructRef(String input) implements Type {
};
/* public */struct Main {/* 

    @External
    private record JavaString(String value) implements String_ {
        @Override
        public String toSlice() {
            return this.value;
        }

        @Override
        public String_ appendSlice(String slice) {
            return Strings.from(this.value + slice);
        }
    } *//* 

    @External
    private record JavaList<T>(java.util.List<T> elements) implements List<T> {
        public JavaList() {
            this(new ArrayList<>());
        }

        @Override
        public List<T> addLast(T element) {
            var copy = new ArrayList<>(this.elements);
            copy.add(element);
            return new JavaList<>(copy);
        }

        @Override
        public Iterator<T> iterate() {
            return new Iterator<>(new RangeHead(this.elements.size())).map(this.elements::get);
        }

        @Override
        public Option<Tuple<List<T>, T>> removeLast() {
            if (this.elements.isEmpty()) {
                return new None<>();
            }

            var slice = this.elements.subList(0, this.elements.size() - 1);
            var last = this.elements.getLast();
            return new Some<>(new Tuple<>(new JavaList<>(new ArrayList<>(slice)), last));
        }

        @Override
        public boolean isEmpty() {
            return this.elements.isEmpty();
        }

        @Override
        public T get(int index) {
            return this.elements.get(index);
        }

        @Override
        public List<T> addFirst(T element) {
            var copy = this.copy();
            copy.addFirst(element);
            return new JavaList<>(copy);
        }

        @Override
        public boolean contains(T element) {
            return this.elements.contains(element);
        }

        private java.util.List<T> copy() {
            return new ArrayList<T>(this.elements);
        }
    } *//* 

    private enum Primitive implements Type {
        Auto("auto"),
        I8("char"),
        I32("int");
        private final String value;

        Primitive(String value) {
            this.value = value;
        }

        @Override
        public String_ generate() {
            return Strings.from(this.generate0());
        }

        private String generate0() {
            return this.value;
        }
    } */
};
/* default */ struct String_ generateWithName_Type extends Node(struct Type extends Node this, char* name){
	return /* this.generate().appendSlice(" ").appendSlice(name) */;
}
/* private static class Strings {
 private static */ struct String_ from(char* value){
	return /* new JavaString(value) */;/* } */
}
/* @Override
 public <R> */ template Option<struct R> map_Some(struct Some this, /*  R */ (*mapper)(struct T)){
	return /* new Some<>(mapper.apply(this.value)) */;
}
/* @Override
 public */ int isEmpty_Some(struct Some this){
	return /* false */;
}
/* @Override
 public */ struct T orElse_Some(struct Some this, struct T other){
	return /* this.value */;
}
/* @Override
 public */ template Option<struct T> or_Some(struct Some this, template Option<struct T> (*other)()){
	return /* this */;
}
/* @Override
 public */ struct T orElseGet_Some(struct Some this, struct T (*other)()){
	return /* this.value */;
}
/* @Override
 public <R> */ template Option<struct R> flatMap_Some(struct Some this, template Option<struct R> (*mapper)(struct T)){
	return /* mapper.apply(this.value) */;
}
/* @Override
 public */ template Option<struct T> filter_Some(struct Some this, template Predicate<struct T> predicate){
	return /* predicate.test(this.value) ? this : new None<>() */;
}
/* @Override
 public */ int isPresent_Some(struct Some this){
	return /* true */;
}
/* @Override
 public <R> */ template Option<struct R> map_None(struct None this, /*  R */ (*mapper)(struct T)){
	return /* new None<>() */;
}
/* @Override
 public */ int isEmpty_None(struct None this){
	return /* true */;
}
/* @Override
 public */ struct T orElse_None(struct None this, struct T other){
	return /* other */;
}
/* @Override
 public */ template Option<struct T> or_None(struct None this, template Option<struct T> (*other)()){
	return /* other.get() */;
}
/* @Override
 public */ struct T orElseGet_None(struct None this, struct T (*other)()){
	return /* other.get() */;
}
/* @Override
 public <R> */ template Option<struct R> flatMap_None(struct None this, template Option<struct R> (*mapper)(struct T)){
	return /* new None<>() */;
}
/* @Override
 public */ template Option<struct T> filter_None(struct None this, template Predicate<struct T> predicate){
	return /* new None<>() */;
}
/* @Override
 public */ int isPresent_None(struct None this){
	return /* false */;
}
/* public <C> */ struct C collect_Iterator(struct Iterator this, template Collector<T, /*  C */> collector){
	return /* this.fold(collector.createInitial(), collector::fold) */;
}
/* private <C> */ struct C fold_Iterator(struct Iterator this, struct C initial, template BiFunction<struct C, T, /*  C */> folder){/* var current = initial; *//* while (true) {
                C finalCurrent = current;
                var maybeNext = this.head.next().map(next -> folder.apply(finalCurrent, next));
                if (maybeNext.isEmpty()) {
                    return current;
                }
                else {
                    current = maybeNext.orElse(null);
                }
            } */
}
/* public <R> */ template Iterator<struct R> flatMap_Iterator(struct Iterator this, template Iterator<struct R> (*mapper)(T)){
	return /* this.map(mapper).fold(new Iterator<>(new EmptyHead<>()), Iterator::concat) */;
}
/* public <R> */ template Iterator<struct R> map_Iterator(struct Iterator this, /*  R */ (*mapper)(T)){
	return /* new Iterator<>(() -> this.head.next().map(mapper)) */;
}
/* private */ template Iterator<T> concat_Iterator(struct Iterator this, template Iterator<T> other){
	return /* new Iterator<>(() -> this.head.next().or(other::next)) */;
}
/* public */ template Option<T> next_Iterator(struct Iterator this){
	return /* this.head.next() */;
}
/* private static final class RangeHead implements Head<Integer> {
 private final int length;
 private int counter = 0;

 */ struct private RangeHead(int length){/* this.length = length; *//* }

        @Override
        public Option<Integer> next() {
            if (this.counter >= this.length) {
                return new None<>();
            } *//* var value = this.counter; *//* this.counter++; */
	return /* new Some<>(value) */;/* } */
}
/* private static class Lists {
 public static <T> */ template List<struct T> of(/* T... */ elements){
	return /* new JavaList<>(Arrays.asList(elements)) */;/* }

        public static <T> List<T> empty() {
            return new JavaList<>(new ArrayList<>()); *//* } */
}
/* private static class EmptyHead<T> implements Head<T> {
 @Override
 public */ template Option<struct T> next(){
	return /* new None<>() */;/* } */
}
struct public Joiner_Joiner(String delimiter) implements Collector(struct Joiner(String delimiter) implements Collector this){/* this(""); */
}
/* @Override
 public */ template Option<String> createInitial_Joiner(String delimiter) implements Collector(struct Joiner(String delimiter) implements Collector this){
	return /* new None<>() */;
}
/* @Override
 public */ template Option<String> fold_Joiner(String delimiter) implements Collector(struct Joiner(String delimiter) implements Collector this, template Option<String> current, String element){
	return /* new Some<>(current.map(inner -> inner + this.delimiter + element).orElse(element)) */;
}
struct public CompileState_CompileState(struct CompileState this){/* this(Lists.empty(), Lists.empty(), new None<>()); */
}
/* private */ char* generate_CompileState(struct CompileState this){
	return /* this.getJoin(this.structs) + this.getJoin(this.functions) */;
}
/* private */ char* getJoin_CompileState(struct CompileState this, template List<char*> lists){
	return /* lists.iterate().collect(new Joiner()).orElse("") */;
}
/* public */ struct CompileState addStruct_CompileState(struct CompileState this, char* struct){
	return /* new CompileState(this.structs.addLast(struct), this.functions, this.maybeStructureType) */;
}
/* public */ struct CompileState addFunction_CompileState(struct CompileState this, char* function){
	return /* new CompileState(this.structs, this.functions.addLast(function), this.maybeStructureType) */;
}
/* public */ struct CompileState withStructType_CompileState(struct CompileState this, struct StructurePrototype type){
	return /* new CompileState(this.structs, this.functions, new Some<>(type)) */;
}
/* public */ struct CompileState withoutStructType_CompileState(struct CompileState this){
	return /* new CompileState(this.structs, this.functions, new None<>()) */;
}
struct public DivideState_DivideState(struct DivideState this, char* input){/* this(input, new JavaList<>(), new StringBuilder(), 0, 0); */
}
/* private */ template Option<struct DivideState> popAndAppend_DivideState(struct DivideState this){
	return /* this.popAndAppendToTuple().map(Tuple::right) */;
}
/* private */ template Option<(struct Character, /*  DivideState */)> popAndAppendToTuple_DivideState(struct DivideState this){/* return this.pop().map(tuple -> {
                var c = tuple.left;
                var state = tuple.right;
                return new Tuple<>(c, state.append(c));
            } *//* ); */
}
/* private */ struct DivideState append_DivideState(struct DivideState this, struct char c){
	return /* new DivideState(this.input, this.segments, this.buffer.append(c), this.index, this.depth) */;
}
/* public */ template Option<(struct Character, /*  DivideState */)> pop_DivideState(struct DivideState this){/* if (this.index < this.input.length()) {
                var c = this.input.charAt(this.index);
                return new Some<>(new Tuple<Character, DivideState>(c, new DivideState(this.input, this.segments, this.buffer, this.index + 1, this.depth)));
            } *//* else {
                return new None<>();
            } */
}
/* private */ struct DivideState advance_DivideState(struct DivideState this){/* var withBuffer = this.buffer.isEmpty() ? this.segments : this.segments.addLast(this.buffer.toString()); */
	return /* new DivideState(this.input, withBuffer, new StringBuilder(), this.index, this.depth) */;
}
/* public */ struct DivideState exit_DivideState(struct DivideState this){
	return /* new DivideState(this.input, this.segments, this.buffer, this.index, this.depth - 1) */;
}
/* public */ int isLevel_DivideState(struct DivideState this){
	return /* this.depth == 0 */;
}
/* public */ struct DivideState enter_DivideState(struct DivideState this){
	return /* new DivideState(this.input, this.segments, this.buffer, this.index, this.depth + 1) */;
}
/* public */ int isShallow_DivideState(struct DivideState this){
	return /* this.depth == 1 */;
}
/* public static <A, B, C> */ (A, /*  C */) (*mapRight_Tuple)((A, B))(struct Tuple this, /*  C */ (*mapper)(B)){
	return /* tuple -> new Tuple<>(tuple.left, mapper.apply(tuple.right)) */;
}
/* private static class Iterators {
 public static <T> */ template Iterator<struct T> fromOptions(template Option<struct T> option){
	return /* new Iterator<>(option.<Head<T>>map(SingleHead::new).orElseGet(EmptyHead::new)) */;/* } */
}
/* private static class SingleHead<T> implements Head<T> {
 private final T value;
 private boolean retrieved = false;

 */ struct public SingleHead(struct T value){/* this.value = value; *//* }

        @Override
        public Option<T> next() {
            if (this.retrieved) {
                return new None<>();
            } *//* this.retrieved = true; */
	return /* new Some<>(this.value) */;/* } */
}
/* @Override
 public */ template Option<(char*, char*)> split_InfixSplitter(String infix,
                                 BiFunction<String, String, Option<Integer>> locator) implements Splitter(struct InfixSplitter(String infix,
                                 BiFunction<String, String, Option<Integer>> locator) implements Splitter this, char* input){/* return this.apply(input).map(classIndex -> {
                var beforeKeyword = input.substring(0, classIndex);
                var afterKeyword = input.substring(classIndex + this.length());
                return new Tuple<>(beforeKeyword, afterKeyword);
            } *//* ); */
}
/* private */ int length_InfixSplitter(String infix,
                                 BiFunction<String, String, Option<Integer>> locator) implements Splitter(struct InfixSplitter(String infix,
                                 BiFunction<String, String, Option<Integer>> locator) implements Splitter this){
	return /* this.infix.length() */;
}
/* private */ template Option<struct Integer> apply_InfixSplitter(String infix,
                                 BiFunction<String, String, Option<Integer>> locator) implements Splitter(struct InfixSplitter(String infix,
                                 BiFunction<String, String, Option<Integer>> locator) implements Splitter this, char* input){
	return /* this.locator().apply(input, this.infix) */;
}
/* private static class TypeSeparatorSplitter implements Splitter {
 @Override
 public */ template Option<(char*, char*)> split(char* input){/* return divide(input, TypeSeparatorSplitter::fold).removeLast().flatMap(segments -> {
                var left = segments.left;
                if (left.isEmpty()) {
                    return new None<>();
                }

                var beforeType = left.iterate().collect(new Joiner(" ")).orElse("");
                var type = segments.right;
                return new Some<>(new Tuple<>(beforeType, type));
            } *//* ); *//* }

        private static DivideState fold(DivideState state, char c) {
            if (c == ' ' && state.isLevel()) {
                return state.advance();
            } *//* var appended = state.append(c); *//* if (c == '<') {
                return appended.enter();
            } *//* if (c == '>') {
                return appended.exit();
            } */
	return /* appended */;/* } */
}
struct public Definition_Definition(Option<String> maybeBeforeType, Type type, String name) implements Parameter(struct Definition(Option<String> maybeBeforeType, Type type, String name) implements Parameter this, struct Type type, char* name){/* this(new None<>(), type, name); */
}
/* public */ struct Definition mapName_Definition(Option<String> maybeBeforeType, Type type, String name) implements Parameter(struct Definition(Option<String> maybeBeforeType, Type type, String name) implements Parameter this, char* (*mapper)(char*)){
	return /* new Definition(this.maybeBeforeType, this.type, mapper.apply(this.name)) */;
}
/* @Override
 public */ struct String_ generate_Definition(Option<String> maybeBeforeType, Type type, String name) implements Parameter(struct Definition(Option<String> maybeBeforeType, Type type, String name) implements Parameter this){
	return /* Strings.from(this.generate0()) */;
}
/* private */ char* generate0_Definition(Option<String> maybeBeforeType, Type type, String name) implements Parameter(struct Definition(Option<String> maybeBeforeType, Type type, String name) implements Parameter this){/* var beforeTypeString = this.maybeBeforeType.map(beforeType -> generatePlaceholder(beforeType) + " ").orElse(""); */
	return /* beforeTypeString + this.type.generateWithName(this.name).toSlice() */;
}
/* @Override
 public */ struct String_ generate_Content(String input) implements Type, Parameter(struct Content(String input) implements Type, Parameter this){
	return /* Strings.from(this.generate0()) */;
}
/* private */ char* generate0_Content(String input) implements Type, Parameter(struct Content(String input) implements Type, Parameter this){
	return /* generatePlaceholder(this.input) */;
}
/* @Override
 public */ struct String_ generate_Functional(List<Type> arguments, Type returns) implements Type(struct Functional(List<Type> arguments, Type returns) implements Type this){
	return /* this.generateWithName("") */;
}
/* @Override
 public */ struct String_ generateWithName_Functional(List<Type> arguments, Type returns) implements Type(struct Functional(List<Type> arguments, Type returns) implements Type this, char* name){/* var joinedArguments = this.arguments().iterate()
                    .map(type -> type.generate().toSlice())
                    .collect(new Joiner(", "))
                    .orElse(""); */
	return /* this.returns.generate()
                    .appendSlice(" (*")
                    .appendSlice(name)
                    .appendSlice(")(")
                    .appendSlice(joinedArguments)
                    .appendSlice(")") */;
}
/* @Override
 public */ struct String_ generate_Template(String base, List<Type> arguments) implements Type(struct Template(String base, List<Type> arguments) implements Type this){
	return /* Strings.from(this.generate0()) */;
}
/* private */ char* generate0_Template(String base, List<Type> arguments) implements Type(struct Template(String base, List<Type> arguments) implements Type this){/* var generatedTuple = this.arguments().iterate()
                    .map(type -> type.generate().toSlice())
                    .collect(new Joiner(", "))
                    .orElse(""); */
	return /* "template " + this.base() + "<" + generatedTuple + ">" */;
}
/* private static class ListCollector<T> implements Collector<T, List<T>> {
 @Override
 public */ template List<struct T> createInitial(){
	return /* Lists.empty() */;/* }

        @Override
        public List<T> fold(List<T> current, T element) {
            return current.addLast(element); *//* } */
}
/* @Override
 public */ struct String_ generate_TypeParameter(String value) implements Type(struct TypeParameter(String value) implements Type this){
	return /* Strings.from(this.generate0()) */;
}
/* private */ char* generate0_TypeParameter(String value) implements Type(struct TypeParameter(String value) implements Type this){
	return /* this.value */;
}
/* @Override
 public */ struct String_ generate_Ref(Type type) implements Type(struct Ref(Type type) implements Type this){
	return /* Strings.from(this.generate0()) */;
}
/* private */ char* generate0_Ref(Type type) implements Type(struct Ref(Type type) implements Type this){
	return /* this.type.generate().toSlice() + "*" */;
}
/* @Override
 public */ struct String_ generate_TupleType(List<Type> arguments) implements Type(struct TupleType(List<Type> arguments) implements Type this){
	return /* Strings.from(this.generate0()) */;
}
/* private */ char* generate0_TupleType(List<Type> arguments) implements Type(struct TupleType(List<Type> arguments) implements Type this){
	return /* "(" + generateNodesAsValues(this.arguments) + ")" */;
}
/* @Override
 public */ struct String_ generate_StructRef(String input) implements Type(struct StructRef(String input) implements Type this){
	return /* Strings.from("struct ").appendSlice(this.input) */;
}
/* public static */ struct void main(){/* try {
            var source = Paths.get(".", "src", "java", "magma", "Main.java");
            var target = source.resolveSibling("main.c");

            var input = Files.readString(source);
            Files.writeString(target, compileRoot(input));
        } *//* catch (IOException e) {
            e.printStackTrace();
        } */
}
/* private static */ char* compileRoot(char* input){/* var state = new CompileState(); *//* var tuple = compileAll(state, input, Main::compileRootSegment)
                .orElse(new Tuple<>(state, "")); */
	return /* tuple.right + tuple.left.generate() */;
}
/* private static */ template Option<(struct CompileState, char*)> compileAll(struct CompileState initial, char* input, template BiFunction<struct CompileState, char*, template Option<(struct CompileState, char*)>> mapper){
	return /* all(initial, input, Main::foldStatementChar, mapper, Main::mergeStatements) */;
}
/* private static */ template Option<(struct CompileState, char*)> all(struct CompileState initial, char* input, template BiFunction<struct DivideState, /*  Character */, /*  DivideState */> folder, template BiFunction<struct CompileState, char*, template Option<(struct CompileState, char*)>> mapper, template BiFunction<struct StringBuilder, char*, /*  StringBuilder */> merger){
	return /* parseAll(initial, input, folder, mapper).map(tuple -> new Tuple<>(tuple.left, generateAll(merger, tuple.right))) */;
}
/* private static */ struct StringBuilder mergeStatements(struct StringBuilder output, char* right){
	return /* output.append(right) */;
}
/* private static */ struct DivideState foldStatementChar(struct DivideState state, struct char c){/* var appended = state.append(c); *//* if (c == ';' && appended.isLevel()) {
            return appended.advance();
        } *//* if (c == '}' && appended.isShallow()) {
            return appended.advance().exit();
        } *//* if (c == '{') {
            return appended.enter();
        } *//* if (c == '}') {
            return appended.exit();
        } */
	return /* appended */;
}
/* private static */ template Option<(struct CompileState, char*)> compileRootSegment(struct CompileState state, char* input){
	return /* or(state, input, Lists.of(
                Main::whitespace,
                Main::compileNamespaced,
                structure("class", "class "),
                (state1, input1) -> content(state1, input1).map(Tuple.mapRight(content -> content.generate().toSlice()))
        )) */;
}
/* private static */ template BiFunction<struct CompileState, char*, template Option<(struct CompileState, char*)>> structure(char* type, char* infix){/* return (state, input) -> first(input, infix, (beforeKeyword, afterKeyword) -> {
            var slices = Arrays.stream(beforeKeyword.split(" "))
                    .map(String::strip)
                    .filter(value -> !value.isEmpty())
                    .toList();

            if (slices.contains("@External")) {
                return new None<>();
            }

            return first(afterKeyword, "{", (beforeContent, withEnd) -> {
                return or(state, beforeContent, Lists.of(
                        (state0, beforeContent0) -> structureWithVariants(type, state0, beforeKeyword, beforeContent0, withEnd),
                        (state0, beforeContent0) -> structureWithoutVariants(type, state0, beforeKeyword, beforeContent0, Lists.empty(), withEnd)
                ));
            });
        } *//* ); */
}
/* private static */ template Option<(struct CompileState, char*)> structureWithVariants(char* type, struct CompileState state, char* beforeKeyword, char* beforeContent, char* withEnd){/* return first(beforeContent, " permits ", (beforePermits, variantsString) -> {
            return parseValues(state, variantsString, Main::symbol).flatMap(params -> {
                return structureWithoutVariants(type, params.left, beforeKeyword, beforePermits, params.right, withEnd);
            });
        } *//* ); */
}
/* private static */ template Option<(struct CompileState, char*)> symbol(struct CompileState state, char* value){
	return /* new Some<>(new Tuple<>(state, value.strip())) */;
}
/* private static */ template Option<(struct CompileState, char*)> structureWithoutVariants(char* type, struct CompileState state, char* beforeKeyword, char* beforeContent, template List<char*> variants, char* withEnd){
	return /* or(state, beforeContent, Lists.of(
                (instance, before) -> structureWithParams(type, instance, beforeKeyword, before, variants, withEnd),
                (instance, before) -> structureWithoutParams(type, instance, beforeKeyword, before.strip(), "", variants, withEnd)
        )) */;
}
/* private static */ template Option<(struct CompileState, char*)> structureWithParams(char* type, struct CompileState instance, char* beforeKeyword, char* beforeContent, template List<char*> variants, char* withEnd){/* return suffix(beforeContent.strip(), ")", withoutEnd -> first(withoutEnd, "(", (name, paramString) -> {
            return all(instance, paramString, Main::foldValueChar, (instance1, paramString1) -> parameter(instance1, paramString1).map(Tuple.mapRight(parameter -> parameter.generate().toSlice())), Main::mergeStatements).flatMap(params -> {
                return structureWithoutParams(type, params.left, beforeKeyword, name, params.right, variants, withEnd);
            });
        } *//* )); */
}
/* private static */ template Option<(struct CompileState, /*  Parameter */)> parameter(struct CompileState instance, char* paramString){
	return /* Main.or(instance, paramString, Lists.of(
                wrap(Main::definition),
                wrap(Main::content)
        )) */;
}
/* private static */ template Option<(struct CompileState, char*)> structureWithoutParams(char* type, struct CompileState state, char* beforeKeyword, char* beforeParams, char* params, template List<char*> variants, char* withEnd){
	return /* or(state, beforeParams, Lists.of(
                (state0, beforeParams0) -> structureWithTypeParams(type, state0, beforeParams0, beforeKeyword, params, variants, withEnd),
                (state0, name) -> structureWithName(type, state0, beforeKeyword, name, Lists.empty(), params, variants, withEnd)
        )) */;
}
/* private static */ template Option<(struct CompileState, char*)> structureWithTypeParams(char* type, struct CompileState state, char* beforeParams0, char* beforeKeyword, char* params, template List<char*> variants, char* withEnd){/* return suffix(beforeParams0.strip(), ">", withoutEnd -> {
            return first(withoutEnd, "<", (name, typeParamString) -> {
                return parseValues(state, typeParamString, Main::symbol).flatMap(values -> {
                    return structureWithName(type, values.left, beforeKeyword, name, values.right, params, variants, withEnd);
                });
            });
        } *//* ); */
}
/* private static */ template Option<(struct CompileState, char*)> structureWithName(char* type, struct CompileState state, char* beforeKeyword, char* name, template List<char*> typeParams, char* params, template List<char*> variants, char* withEnd){/* return suffix(withEnd.strip(), "}", content -> {
            return compileAll(state.withStructType(new StructurePrototype(type, name, typeParams, variants)), content, Main::structSegment).flatMap(tuple -> {
                return new Some<>(assembleStruct(type, tuple.left, beforeKeyword, name, typeParams, params, variants, tuple.right));
            }).map(tuple -> new Tuple<>(tuple.left.withoutStructType(), tuple.right));
        } *//* ); */
}
/* private static */ (struct CompileState, char*) assembleStruct(char* type, struct CompileState state, char* beforeKeyword, char* name, template List<char*> typeParams, char* params, template List<char*> variants, char* oldContent){/* if (!variants.isEmpty()) {
            var enumName = name + "Variant";
            var enumFields = variants.iterate()
                    .map(variant -> "\n\t" + variant)
                    .collect(new Joiner(","))
                    .orElse("");
            var generatedEnum = "enum " + enumName + " {" + enumFields + "\n};\n";

            var typeParamString = generateTypeParams(typeParams);
            var unionName = name + "Value" + typeParamString;
            var unionFields = variants.iterate()
                    .map(variant -> "\n\t" + variant + typeParamString + " " + variant.toLowerCase() + ";")
                    .collect(new Joiner(""))
                    .orElse("");
            var generateUnion = "union " + unionName + " {" + unionFields + "\n};\n";

            var compileState = state.addStruct(generatedEnum).addStruct(generateUnion);
            var newContent = "\n\t" + enumName + " _variant;"
                    + "\n\t" + unionName + " _value;"
                    + oldContent;

            return generateStruct(compileState, beforeKeyword, name, typeParamString, params, newContent);
        } *//* if (type.equals("interface")) {
            var typeParamString = generateTypeParams(typeParams.addFirst("S"));
            var newContent = "\n\tS _super;" + oldContent;
            return generateStruct(state, beforeKeyword, name, typeParamString, params, newContent);
        } */
	return /* generateStruct(state, beforeKeyword, name, generateTypeParams(typeParams), params, oldContent) */;
}
/* private static */ (struct CompileState, char*) generateStruct(struct CompileState state, char* beforeKeyword, char* name, char* typeParamString, char* params, char* content){/* var generatedStruct = generatePlaceholder(beforeKeyword.strip()) + "struct " + name + typeParamString + " {" + params + content + "\n};\n"; */
	return /* new Tuple<CompileState, String>(state.addStruct(generatedStruct), "") */;
}
/* private static */ char* generateTypeParams(template List<char*> typeParams){
	return /* typeParams.isEmpty() ? "" : "<" + typeParams.iterate().collect(new Joiner(", ")).orElse("") + ">" */;
}
/* private static <T> */ template Option<(struct CompileState, /*  T */)> or(struct CompileState state, char* input, template List<template BiFunction<struct CompileState, char*, template Option<(struct CompileState, /*  T */)>>> actions){
	return /* actions.iterate()
                .map(action -> action.apply(state, input))
                .flatMap(Iterators::fromOptions)
                .next() */;
}
/* private static */ template Option<(struct CompileState, char*)> compileNamespaced(struct CompileState state, char* input){/* if (input.strip().startsWith("package ") || input.strip().startsWith("import ")) {
            return new Some<>(new Tuple<CompileState, String>(state, ""));
        } */
	return /* new None<>() */;
}
/* private static */ template Option<(struct CompileState, char*)> structSegment(struct CompileState state, char* input){
	return /* or(state, input, Lists.of(
                Main::whitespace,
                Main::annotation,
                structure("record", "record "),
                structure("interface", "interface "),
                Main::method,
                Main::definitionStatement,
                (state1, input1) -> content(state1, input1).map(Tuple.mapRight(content -> content.generate().toSlice()))
        )) */;
}
/* private static */ template Option<(struct CompileState, char*)> definitionStatement(struct CompileState state, char* input){/* return suffix(input.strip(), ";", withoutEnd -> definition(state, withoutEnd).map(Tuple.mapRight(definition -> definition.generate().toSlice())).map(value -> {
            var generated = "\n\t" + value.right + ";";
            return new Tuple<>(value.left, generated);
        } *//* )); */
}
/* private static */ template Option<(struct CompileState, /*  Content */)> content(struct CompileState state, char* input){
	return /* new Some<>(new Tuple<CompileState, Content>(state, new Content(input))) */;
}
/* private static */ template Option<(struct CompileState, char*)> whitespace(struct CompileState state, char* input){/* if (input.isBlank()) {
            return new Some<>(new Tuple<CompileState, String>(state, ""));
        } */
	return /* new None<>() */;
}
/* private static */ template Option<(struct CompileState, char*)> method(struct CompileState state, char* input){/* return first(input, "(", (inputDefinition, withParams) -> {
            return first(withParams, ")", (paramsString, withBraces) -> {
                return compileMethodHeader(state, inputDefinition).flatMap(outputDefinition -> {
                    return parseValues(outputDefinition.left, paramsString, Main::parameter).flatMap(outputParams -> {
                        var params = outputParams.right
                                .iterate()
                                .map(Main::retainDefinition)
                                .flatMap(Iterators::fromOptions)
                                .collect(new ListCollector<>());

                        return or(outputParams.left, withBraces, Lists.of(
                                (state0, element) -> methodWithoutContent(state0, outputDefinition.right, params, element),
                                (state0, element) -> methodWithContent(state0, outputDefinition.right, params, element)));
                    });
                });
            });
        } *//* ); */
}
/* private static */ template Option<struct Definition> retainDefinition(struct Parameter param){/* if (param instanceof Definition definition) {
            return new Some<>(definition);
        } *//* else {
            return new None<>();
        } */
}
/* private static <T> */ template Option<(struct CompileState, template List<struct T>)> parseValues(struct CompileState state, char* input, template BiFunction<struct CompileState, char*, template Option<(struct CompileState, /*  T */)>> compiler){
	return /* parseAll(state, input, Main::foldValueChar, compiler) */;
}
/* private static <T> */ template Option<(struct CompileState, template List<struct T>)> parseAll(struct CompileState initial, char* input, template BiFunction<struct DivideState, /*  Character */, /*  DivideState */> folder, template BiFunction<struct CompileState, char*, template Option<(struct CompileState, /*  T */)>> mapper){
	return /* divide(input, folder)
                .iterate()
                .<Option<Tuple<CompileState, List<T>>>>fold(new Some<>(new Tuple<CompileState, List<T>>(initial, Lists.empty())),
                        (maybeCurrent, segment) -> maybeCurrent.flatMap(
                                state -> foldElement(state, segment, mapper))) */;
}
/* private static <T> */ template Option<(struct CompileState, template List<struct T>)> foldElement((struct CompileState, template List<struct T>) state, char* segment, template BiFunction<struct CompileState, char*, template Option<(struct CompileState, /*  T */)>> mapper){/* var oldState = state.left; *//* var oldCache = state.right; *//* return mapper.apply(oldState, segment).map(result -> {
            var newState = result.left;
            var newElement = result.right;
            return new Tuple<>(newState, oldCache.addLast(newElement));
        } *//* ); */
}
/* private static */ template List<char*> divide(char* input, template BiFunction<struct DivideState, /*  Character */, /*  DivideState */> folder){/* DivideState current = new DivideState(input); *//* while (true) {
            var maybePopped = current.pop();
            if (maybePopped.isEmpty()) {
                break;
            }

            var popped = maybePopped.orElse(null);
            var c = popped.left;
            var state = popped.right;
            current = foldSingleQuotes(state, c)
                    .or(() -> foldDoubleQuotes(state, c))
                    .orElseGet(() -> folder.apply(state, c));
        } */
	return /* current.advance().segments */;
}
/* private static */ template Option<struct DivideState> foldDoubleQuotes(struct DivideState state, struct char c){/* if (c != '\"') {
            return new None<>();
        } *//* var appended = state.append(c); *//* while (true) {
            var maybeTuple = appended.popAndAppendToTuple();
            if (maybeTuple.isEmpty()) {
                break;
            }

            var nextTuple = maybeTuple.orElse(null);
            var next = nextTuple.left;
            appended = nextTuple.right;

            if (next == '\\') {
                appended = appended.popAndAppend().orElse(appended);
            }
            if (next == '\"') {
                break;
            }
        } */
	return /* new Some<>(appended) */;
}
/* private static */ template Option<struct DivideState> foldSingleQuotes(struct DivideState state, struct char c){/* if (c != '\'') {
            return new None<>();
        } *//* return state.append(c).pop().map(maybeNextTuple -> {
            var nextChar = maybeNextTuple.left;
            var nextState = maybeNextTuple.right.append(nextChar);

            var withEscaped = nextChar == '\\'
                    ? nextState.popAndAppend().orElse(nextState)
                    : nextState;

            return withEscaped.popAndAppend().orElse(withEscaped);
        } *//* ); */
}
/* private static */ struct DivideState foldValueChar(struct DivideState state, struct char c){/* if (c == ',' && state.isLevel()) {
            return state.advance();
        } *//* var appended = state.append(c); *//* if (c == '<') {
            return appended.enter();
        } *//* if (c == '>') {
            return appended.exit();
        } */
	return /* appended */;
}
/* private static <T extends Node> */ char* generateNodesAsValues(template List<struct T> params){
	return /* params.iterate()
                .map(t -> t.generate().toSlice())
                .collect(new Joiner(", "))
                .orElse("") */;
}
/* private static */ char* generateAll(template BiFunction<struct StringBuilder, char*, /*  StringBuilder */> merger, template List<char*> right){
	return /* right.iterate().fold(new StringBuilder(), merger).toString() */;
}
/* private static */ template Option<(struct CompileState, char*)> methodWithoutContent(struct CompileState state, struct Definition definition, template List<struct Definition> params, char* content){/* if (!content.equals(";")) {
            return new None<>();
        } *//* String generated; *//* if (state.maybeStructureType.filter(value -> value.type.equals("interface") && value.variants.isEmpty()).isPresent()) {
            var returnType = definition.type;
            var name = definition.name;
            var argumentTypes = params.iterate()
                    .map(Definition::type)
                    .collect(new ListCollector<>())
                    .addFirst(new TypeParameter("S"));

            var functionalType = new Functional(argumentTypes, returnType);
            var definition0 = new Definition(functionalType, name);
            generated = "\n\t" + definition0.generate().toSlice() + ";";
        } *//* else {
            generated = "";
        } */
	return /* new Some<>(new Tuple<CompileState, String>(state, generated)) */;
}
/* private static */ template Option<(struct CompileState, char*)> methodWithContent(struct CompileState state, struct Definition definition, template List<struct Definition> params, char* withBraces){/* return prefix(withBraces.strip(), "{", withoutStart1 -> {
            return suffix(withoutStart1, "}", content -> {
                return compileAll(state, content, Main::functionSegment).flatMap(tuple -> {
                    var newParameters = state.maybeStructureType
                            .map(structType -> params.addFirst(new Definition(new StructRef(structType.name), "this")))
                            .orElse(params);
                    var paramStrings = generateNodesAsValues(newParameters);

                    var generated = definition
                            .mapName(name -> state.maybeStructureType.map(structureType -> name + "_" + structureType.name).orElse(name)).generate().toSlice() + "(" + paramStrings + "){" + tuple.right + "\n}\n";
                    return new Some<>(new Tuple<>(state.addFunction(generated), ""));
                });
            });
        } *//* ); */
}
/* private static */ template Option<(struct CompileState, /*  Definition */)> compileMethodHeader(struct CompileState state, char* definition){
	return /* or(state, definition, Lists.of(
                Main::definition,
                Main::constructor
        )) */;
}
/* private static */ template Option<(struct CompileState, /*  Definition */)> constructor(struct CompileState state, char* input){/* return split(input.strip(), new InfixSplitter(" ", Main::lastIndexOfSlice), (_, name) -> state.maybeStructureType.flatMap(structureType -> {
            if (!structureType.name.equals(name)) {
                return new None<>();
            }

            return new Some<>(new Tuple<>(state, new Definition(Primitive.Auto, name)));
        } *//* )); */
}
/* private static */ template Option<(struct CompileState, char*)> functionSegment(struct CompileState state, char* input){
	return /* or(state, input.strip(), Lists.of(
                Main::whitespace,
                (state0, input1) -> suffix(input1.strip(), ";", slice -> statementValue(state0, slice).map(Tuple.mapRight(slice0 -> "\n\t" + slice0 + ";"))),
                (state0a, input1) -> content(state0a, input1).map(Tuple.mapRight(content -> content.generate().toSlice()))
        )) */;
}
/* private static */ template Option<(struct CompileState, char*)> statementValue(struct CompileState state, char* input){
	return /* or(state, input, Lists.of(Main::returns)) */;
}
/* private static */ template Option<(struct CompileState, char*)> returns(struct CompileState state, char* input){
	return /* prefix(input.strip(), "return ", slice -> compileValue(state, slice).map(Tuple.mapRight(result -> "return " + result))) */;
}
/* private static */ template Option<(struct CompileState, char*)> compileValue(struct CompileState state, char* input){
	return /* or(state, input, Lists.of(
                (state1, input1) -> content(state1, input1).map(Tuple.mapRight(content -> content.generate().toSlice()))
        )) */;
}
/* private static */ template Option<(struct CompileState, /*  Definition */)> definition(struct CompileState state, char* input){/* return split(input.strip(), new InfixSplitter(" ", Main::lastIndexOfSlice), (beforeName, name) -> {
            if (!isSymbol(name)) {
                return new None<>();
            }

            return Main.or(state, beforeName.strip(), Lists.of(
                    (instance, beforeName0) -> definitionWithTypeSeparator(instance, beforeName0, name),
                    (instance, beforeName0) -> definitionWithoutTypeSeparator(instance, beforeName0, name)
            ));
        } *//* ); */
}
/* private static */ int isSymbol(char* value){/* for (var i = 0; *//* i < value.length(); *//* i++) {
            var c = value.charAt(i);
            if (Character.isLetter(c) || c == '_' || (i != 0 && Character.isDigit(c))) {
                continue;
            }
            return false;
        } */
	return /* true */;
}
/* private static */ template Option<(struct CompileState, /*  Definition */)> definitionWithoutTypeSeparator(struct CompileState state, char* type, char* name){/* return type(state, type).flatMap(typeTuple -> {
            var definition = new Definition(new None<>(), typeTuple.right, name.strip());
            return new Some<>(new Tuple<>(typeTuple.left, definition));
        } *//* ); */
}
/* private static */ template Option<(struct CompileState, /*  Definition */)> definitionWithTypeSeparator(struct CompileState state, char* beforeName, char* name){/* return split(beforeName, new TypeSeparatorSplitter(), (beforeType, typeString) -> {
            return type(state, typeString).flatMap(typeTuple -> {
                return new Some<>(new Tuple<>(typeTuple.left, new Definition(new Some<>(beforeType), typeTuple.right, name.strip())));
            });
        } *//* ); */
}
/* private static */ template Option<(struct CompileState, /*  Type */)> type(struct CompileState state, char* input){
	return /* Main.or(state, input, Lists.of(
                Main::primitive,
                Main::template,
                Main::typeParameter,
                Main::string,
                Main::structureType,
                wrap(Main::content)
        )) */;
}
/* private static */ template Option<(struct CompileState, /*  Type */)> structureType(struct CompileState state, char* input){/* if (isSymbol(input)) {
            return new Some<>(new Tuple<>(state, new StructRef(input)));
        } *//* else {
            return new None<>();
        } */
}
/* private static */ template Option<(struct CompileState, /*  Type */)> string(struct CompileState state, char* input){/* if (input.strip().equals("String")) {
            return new Some<>(new Tuple<>(state, new Ref(Primitive.I8)));
        } *//* else {
            return new None<>();
        } */
}
/* private static */ template Option<(struct CompileState, /*  Type */)> typeParameter(struct CompileState state, char* input){/* if (state.maybeStructureType instanceof Some(var structureType)) {
            var stripped = input.strip();
            if (structureType.typeParams.contains(stripped)) {
                return new Some<>(new Tuple<>(state, new TypeParameter(stripped)));
            }
        } */
	return /* new None<>() */;
}
/* private static <S, T extends S> */ template BiFunction<struct CompileState, char*, template Option<(struct CompileState, /*  S */)>> wrap(template BiFunction<struct CompileState, char*, template Option<(struct CompileState, /*  T */)>> content){
	return /* (state, input) -> content.apply(state, input).map(Tuple.mapRight(value -> value)) */;
}
/* private static */ template Option<(struct CompileState, /*  Type */)> primitive(struct CompileState state, char* input){/* var stripped = input.strip(); *//* if (stripped.equals("boolean") || stripped.equals("int")) {
            return new Some<>(new Tuple<>(state, Primitive.I32));
        } */
	return /* new None<>() */;
}
/* private static */ template Option<(struct CompileState, /*  Type */)> template(struct CompileState state, char* input){/* return suffix(input.strip(), ">", withoutEnd -> {
            return first(withoutEnd, "<", (base, argumentsString) -> {
                return parseValues(state, argumentsString, Main::type).flatMap(argumentsTuple -> {
                    var arguments = argumentsTuple.right;

                    Type generated;
                    if (base.equals("Function")) {
                        generated = new Functional(Lists.of(arguments.get(0)), arguments.get(1));
                    }
                    else if (base.equals("Supplier")) {
                        generated = new Functional(Lists.empty(), arguments.get(0));
                    }
                    else if (base.equals("Tuple")) {
                        generated = new TupleType(arguments);
                    }
                    else {
                        generated = new Template(base, arguments);
                    }

                    return new Some<>(new Tuple<>(argumentsTuple.left, generated));
                });
            });
        } *//* ); */
}
/* private static */ template Option<struct Integer> lastIndexOfSlice(char* input, char* infix){/* var index = input.lastIndexOf(infix); */
	return /* index == -1 ? new None<Integer>() : new Some<>(index) */;
}
/* private static */ template Option<(struct CompileState, char*)> prefix(char* input, char* prefix, template Option<(struct CompileState, char*)> (*mapper)(char*)){/* if (!input.startsWith(prefix)) {
            return new None<>();
        } *//* var slice = input.substring(prefix.length()); */
	return /* mapper.apply(slice) */;
}
/* private static <T> */ template Option<struct T> suffix(char* input, char* suffix, template Option<struct T> (*mapper)(char*)){/* if (!input.endsWith(suffix)) {
            return new None<>();
        } *//* var slice = input.substring(0, input.length() - suffix.length()); */
	return /* mapper.apply(slice) */;
}
/* private static */ char* generatePlaceholder(char* input){
	return /* "/* " + input + " */" */;
}
/* private static <T> */ template Option<struct T> first(char* input, char* infix, template BiFunction<char*, char*, template Option<struct T>> mapper){
	return /* split(input, new InfixSplitter(infix, Main::firstIndexOfSlice), mapper) */;
}
/* private static <T> */ template Option<struct T> split(char* input, struct Splitter splitter, template BiFunction<char*, char*, template Option<struct T>> mapper){/* return splitter.split(input).flatMap(tuple -> {
            return mapper.apply(tuple.left, tuple.right);
        } *//* ); */
}
/* private static */ template Option<struct Integer> firstIndexOfSlice(char* input, char* infix){/* var index = input.indexOf(infix); */
	return /* index == -1 ? new None<Integer>() : new Some<>(index) */;
}
