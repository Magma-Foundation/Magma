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
/* private */struct Type<S> {
	S _super;
};
/* private */struct Parameter<S> {
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
enum ResultVariant {
	Ok,
	Err
};
union ResultValue<T, X> {
	Ok<T, X> ok;
	Err<T, X> err;
};
/* private sealed */struct Result<T, X> {
	ResultVariant _variant;
	ResultValue<T, X> _value;
};
/* private */struct Value<S> {
	S _super;
};
/* private */struct CompileState<S> {
	S _super;
	char* (*generate)(S);
	struct CompileState (*addStruct)(S, char*);
	struct CompileState (*addFunction)(S, char*);
	struct CompileState (*withStructType)(S, struct StructurePrototype);
	struct CompileState (*withoutStructType)(S);
	template Option<struct StructurePrototype> (*findStructureType)(S);
	(char*, struct CompileState) (*createLocalName)(S);
	struct CompileState (*addStatement)(S, char*);
	(template List<char*>, struct CompileState) (*removeStatements)(S);
};
/* private static */struct Strings {
};
/* private */struct Some<T> {
	struct T value;
};
/* private */struct None<T> {
};
/* private */struct Iterator<T> {
	template Head<struct T> head;
};
/* private static final */struct RangeHead {
	/* private final */ int length;
	/* private */ int counter;
};
/* private static */struct Lists {
};
/* private static */struct EmptyHead<T> {
};
/* private */struct Joiner {
	char* delimiter;
};
/* private */struct StructurePrototype {
	char* type;
	char* name;
	template List<char*> typeParams;
	template List<char*> variants;
};
/* private */struct ImmutableCompileState {
	template List<char*> structs;
	template List<char*> functions;
	template List<char*> statements;
	template Option<struct StructurePrototype> maybeStructureType;
	int counter;
};
/* private */struct DivideState {
	char* input;
	template List<char*> segments;
	struct StringBuilder buffer;
	int index;
	int depth;
};
/* private */struct Tuple<A, B> {
	struct A left;
	struct B right;
};
/* private static */struct Iterators {
};
/* private static */struct SingleHead<T> {
	/* private final */ T value;
	/* private */ int retrieved;
};
/* private */struct InfixSplitter {
	char* infix;
	template Option<int> (*locator)(char*, char*);
};
/* private static */struct TypeSeparatorSplitter {
};
/* private */struct Definition {
	template Option<char*> maybeBeforeType;
	struct Type type;
	char* name;
};
/* private */struct Content {
	char* input;
};
/* private */struct Functional {
	template List<struct Type> arguments;
	struct Type returns;
};
/* private */struct Template {
	char* base;
	template List<struct Type> arguments;
};
/* private static */struct ListCollector<T> {
};
/* private */struct TypeParameter {
	char* value;
};
/* private */struct Ref {
	struct Type type;
};
/* private */struct TupleType {
	template List<struct Type> arguments;
};
/* private */struct StructRef {
	char* input;
	template List<char*> typeParams;
};
/* private */struct Ok<T, X> {
	struct T value;
};
/* private */struct Err<T, X> {
	struct X error;
};
/* private */struct DividingSplitter {
	struct DivideState (*folder)(struct DivideState, char);
};
/* private */struct Symbol {
	char* value;
};
/* private */struct StringNode {
	char* value;
};
/* private */struct Invocation {
	struct Value caller;
	template List<struct Value> arguments;
};
/* private */struct DataAccess {
	char* property;
	struct Value parent;
};
/* private */struct Primitive {/* Auto("auto"),
        Void("void"),
        I8("char"),
        I32("int"); */
	/* private final */ char* value;
};
/* public */struct Main {/* 

    public static final Path SOURCE = Paths.get(".", "src", "java", "magma", "Main.java"); *//* 
    public static final Path TARGET = SOURCE.resolveSibling("main.c"); */
};
/* default */ struct String_ Type::generateWithName(struct Type this, char* name){
	auto local0 = this;
	auto local1 = this.generate(this);
	auto local2 = this.generate(this).appendSlice(this.generate(this), " ");
	return this.generate(this).appendSlice(this.generate(this), " ").appendSlice(this.generate(this).appendSlice(this.generate(this), " "), name);
}
/* @Actual
 private static */ struct String_ Strings::from(struct Strings this, char* value){
	return /* new JavaString */(value);
}
/* @Override
 public <R> */ template Option<struct R> Some::map(struct Some<T> this, struct R (*mapper)(T)){
	auto local3 = mapper;
	return /* new Some<> */(mapper.apply(mapper, this.value));
}
/* @Override
 public */ int Some::isEmpty(struct Some<T> this){
	return false;
}
/* @Override
 public */ T Some::orElse(struct Some<T> this, T other){
	return this.value;
}
/* @Override
 public */ template Option<T> Some::or(struct Some<T> this, template Option<T> (*other)()){
	return this;
}
/* @Override
 public */ T Some::orElseGet(struct Some<T> this, T (*other)()){
	return this.value;
}
/* @Override
 public <R> */ template Option<struct R> Some::flatMap(struct Some<T> this, template Option<struct R> (*mapper)(T)){
	auto local4 = mapper;
	return mapper.apply(mapper, this.value);
}
/* @Override
 public */ template Option<T> Some::filter(struct Some<T> this, template Predicate<T> predicate){
	auto local5 = predicate;
	return predicate.test(predicate, this.value) ? this : new None<>();
}
/* @Override
 public */ int Some::isPresent(struct Some<T> this){
	return true;
}
/* @Override
 public */ void Some::ifPresent(struct Some<T> this, template Consumer<T> consumer){
	auto local6 = consumer;
	consumer.accept(consumer, this.value);
}
/* @Override
 public <R> */ template Option<struct R> None::map(struct None<T> this, struct R (*mapper)(T)){
	return /* new None<> */();
}
/* @Override
 public */ int None::isEmpty(struct None<T> this){
	return true;
}
/* @Override
 public */ T None::orElse(struct None<T> this, T other){
	return other;
}
/* @Override
 public */ template Option<T> None::or(struct None<T> this, template Option<T> (*other)()){
	auto local7 = other;
	return other.get(other);
}
/* @Override
 public */ T None::orElseGet(struct None<T> this, T (*other)()){
	auto local8 = other;
	return other.get(other);
}
/* @Override
 public <R> */ template Option<struct R> None::flatMap(struct None<T> this, template Option<struct R> (*mapper)(T)){
	return /* new None<> */();
}
/* @Override
 public */ template Option<T> None::filter(struct None<T> this, template Predicate<T> predicate){
	return /* new None<> */();
}
/* @Override
 public */ int None::isPresent(struct None<T> this){
	return false;
}
/* @Override
 public */ void None::ifPresent(struct None<T> this, template Consumer<T> consumer){
}
/* public <C> */ struct C Iterator::collect(struct Iterator<T> this, template Collector<T, struct C> collector){
	auto local9 = collector;
	auto local10 = this;
	return this.fold(this, collector.createInitial(collector), /*  collector::fold */);
}
/* private <C> */ struct C Iterator::fold(struct Iterator<T> this, struct C initial, struct C (*folder)(struct C, T)){
	auto current = initial;/* while (true) {
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
/* public <R> */ template Iterator<struct R> Iterator::flatMap(struct Iterator<T> this, template Iterator<struct R> (*mapper)(T)){
	auto local11 = this;
	auto local12 = this.map(this, mapper);
	return this.map(this, mapper).fold(this.map(this, mapper), /* new Iterator<> */(/* new EmptyHead<> */()), /*  Iterator::concat */);
}
/* public <R> */ template Iterator<struct R> Iterator::map(struct Iterator<T> this, struct R (*mapper)(T)){
	auto local13 = (/* ) -> this */.head.next();
	return /* new Iterator<> */((/* ) -> this */.head.next().map((/* ) -> this */.head.next(), mapper));
}
/* private */ template Iterator<T> Iterator::concat(struct Iterator<T> this, template Iterator<T> other){
	auto local14 = (/* ) -> this */.head.next();
	return /* new Iterator<> */((/* ) -> this */.head.next().or((/* ) -> this */.head.next(), /* other::next */));
}
/* public */ template Option<T> Iterator::next(struct Iterator<T> this){
	auto local15 = this.head;
	return this.head.next(this.head);
}
struct private RangeHead::RangeHead(struct RangeHead this, int length){/* this.length = length; *//* this.counter = 0; */
}
/* @Override
 public */ template Option<int> RangeHead::next(struct RangeHead this){/* if (this.counter >= this.length) {
                return new None<>();
            } */
	auto value = this.counter;/* this.counter++; */
	return /* new Some<> */(value);
}
/* public static <T> */ template List<struct T> Lists::of(struct Lists this, /* T... */ elements){
	auto local16 = Arrays;
	return /* new JavaList<> */(Arrays.asList(Arrays, elements));
}
/* public static <T> */ template List<struct T> Lists::empty(struct Lists this){
	return /* new JavaList<> */(/* new ArrayList<> */());
}
/* @Override
 public */ template Option<T> EmptyHead::next(struct EmptyHead<T> this){
	return /* new None<> */();
}
struct public Joiner::Joiner(struct Joiner this){
	this("");
}
/* @Override
 public */ template Option<char*> Joiner::createInitial(struct Joiner this){
	return /* new None<> */();
}
/* @Override
 public */ template Option<char*> Joiner::fold(struct Joiner this, template Option<char*> current, char* element){
	auto local17 = current;
	auto local18 = current.map(current, /* inner -> inner + this */.delimiter + element);
	return /* new Some<> */(current.map(current, /* inner -> inner + this */.delimiter + element).orElse(current.map(current, /* inner -> inner + this */.delimiter + element), element));
}
struct public ImmutableCompileState::ImmutableCompileState(struct ImmutableCompileState this){
	auto local19 = Lists;
	auto local20 = Lists;
	auto local21 = Lists;
	this(Lists.empty(Lists), Lists.empty(Lists), Lists.empty(Lists), /* new None<> */(), /*  0 */);
}
/* @Override
 public */ char* ImmutableCompileState::generate(struct ImmutableCompileState this){
	auto local22 = /* join(this */;
	return /* join(this */.structs) + join(/* join(this */, this.functions);
}
/* private static */ char* ImmutableCompileState::join(struct ImmutableCompileState this, template List<char*> lists){
	auto local23 = lists;
	auto local24 = lists.iterate(lists);
	auto local25 = lists.iterate(lists).collect(lists.iterate(lists), /* new Joiner */());
	return lists.iterate(lists).collect(lists.iterate(lists), /* new Joiner */()).orElse(lists.iterate(lists).collect(lists.iterate(lists), /* new Joiner */()), "");
}
/* @Override
 public */ struct CompileState ImmutableCompileState::addStruct(struct ImmutableCompileState this, char* struct){
	auto local26 = this.structs;
	return /* new ImmutableCompileState */(this.structs.addLast(this.structs, struct), this.functions, this.statements, this.maybeStructureType, this.counter);
}
/* @Override
 public */ struct CompileState ImmutableCompileState::addFunction(struct ImmutableCompileState this, char* function){
	auto local27 = this.functions;
	return /* new ImmutableCompileState */(this.structs, this.functions.addLast(this.functions, function), this.statements, this.maybeStructureType, this.counter);
}
/* @Override
 public */ struct CompileState ImmutableCompileState::withStructType(struct ImmutableCompileState this, struct StructurePrototype type){
	return /* new ImmutableCompileState */(this.structs, this.functions, this.statements, /* new Some<> */(type), this.counter);
}
/* @Override
 public */ struct CompileState ImmutableCompileState::withoutStructType(struct ImmutableCompileState this){
	return /* new ImmutableCompileState */(this.structs, this.functions, this.statements, /* new None<> */(), this.counter);
}
/* @Override
 public */ template Option<struct StructurePrototype> ImmutableCompileState::findStructureType(struct ImmutableCompileState this){
	return this.maybeStructureType;
}
/* @Override
 public */ (char*, struct CompileState) ImmutableCompileState::createLocalName(struct ImmutableCompileState this){
	auto name = /*  "local" + this */.counter;
	return /* new Tuple<> */(name, /* new ImmutableCompileState */(this.structs, this.functions, this.statements, this.maybeStructureType, this.counter + 1));
}
/* @Override
 public */ struct CompileState ImmutableCompileState::addStatement(struct ImmutableCompileState this, char* statement){
	auto local28 = this.statements;
	return /* new ImmutableCompileState */(this.structs, this.functions, this.statements.addLast(this.statements, statement), this.maybeStructureType, this.counter);
}
/* @Override
 public */ (template List<char*>, struct CompileState) ImmutableCompileState::removeStatements(struct ImmutableCompileState this){
	auto local29 = Lists;
	return /* new Tuple<> */(this.statements, /* new ImmutableCompileState */(this.structs, this.functions, Lists.empty(Lists), this.maybeStructureType, this.counter));
}
struct public DivideState::DivideState(struct DivideState this, char* input){
	this(input, /* new JavaList<> */(), /* new StringBuilder */(), /*  0 */, /*  0 */);
}
/* private */ template Option<struct DivideState> DivideState::popAndAppend(struct DivideState this){
	auto local30 = this;
	auto local31 = this.popAndAppendToTuple(this);
	return this.popAndAppendToTuple(this).map(this.popAndAppendToTuple(this), /* Tuple::right */);
}
/* private */ template Option<(char, struct DivideState)> DivideState::popAndAppendToTuple(struct DivideState this){
	auto local32 = this;
	auto local33 = this.pop(this);
	return this.pop(this).map(this.pop(this), /* tuple -> {
                var c = tuple */.left;
                var state = tuple.right;
                return new Tuple<>(c, state.append(c));
            });
}
/* private */ struct DivideState DivideState::append(struct DivideState this, char c){
	auto local34 = this.buffer;
	return /* new DivideState */(this.input, this.segments, this.buffer.append(this.buffer, c), this.index, this.depth);
}
/* public */ template Option<(char, struct DivideState)> DivideState::pop(struct DivideState this){/* if (this.index < this.input.length()) {
                var c = this.input.charAt(this.index);
                return new Some<>(new Tuple<Character, DivideState>(c, new DivideState(this.input, this.segments, this.buffer, this.index + 1, this.depth)));
            } *//* else {
                return new None<>();
            } */
}
/* private */ struct DivideState DivideState::advance(struct DivideState this){
	auto local35 = this.buffer;
	auto local36 = this.buffer.isEmpty() ? this.segments : this.segments;
	auto withBuffer = this.buffer.isEmpty() ? this.segments : this.segments.addLast(this.buffer.isEmpty() ? this.segments : this.segments, this.buffer.toString(this.buffer));
	return /* new DivideState */(this.input, withBuffer, /* new StringBuilder */(), this.index, this.depth);
}
/* public */ struct DivideState DivideState::exit(struct DivideState this){
	return /* new DivideState */(this.input, this.segments, this.buffer, this.index, this.depth - 1);
}
/* public */ int DivideState::isLevel(struct DivideState this){
	return this.depth == 0;
}
/* public */ struct DivideState DivideState::enter(struct DivideState this){
	return /* new DivideState */(this.input, this.segments, this.buffer, this.index, this.depth + 1);
}
/* public */ int DivideState::isShallow(struct DivideState this){
	return this.depth == 1;
}
/* public */ template Option<char> DivideState::peek(struct DivideState this){/* if (this.index < this.input.length()) {
                return new Some<>(this.input.charAt(this.index));
            } *//* else {
                return new None<>();
            } */
}
/* public static <A, B, C> */ (A, struct C) (*Tuple::mapRight)((A, B))(struct Tuple<A, B> this, struct C (*mapper)(B)){
	auto local37 = mapper;
	return /* tuple -> new Tuple<> */(tuple.left, mapper.apply(mapper, tuple.right));
}
/* public static <T> */ template Iterator<struct T> Iterators::fromOptions(struct Iterators this, template Option<struct T> option){
	auto local38 = option;
	auto local39 = option.<Head<T>>map(option, /* SingleHead::new */);
	return /* new Iterator<> */(option.<Head<T>>map(option, /* SingleHead::new */).orElseGet(option.<Head<T>>map(option, /* SingleHead::new */), /* EmptyHead::new */));
}
struct public SingleHead::SingleHead(struct SingleHead<T> this, T value){/* this.value = value; *//* this.retrieved = false; */
}
/* @Override
 public */ template Option<T> SingleHead::next(struct SingleHead<T> this){/* if (this.retrieved) {
                return new None<>();
            } *//* this.retrieved = true; */
	return /* new Some<> */(this.value);
}
/* @Override
 public */ template Option<(char*, char*)> InfixSplitter::split(struct InfixSplitter this, char* input){
	auto local40 = this;
	auto local41 = this.apply(this, input);
	return this.apply(this, input).map(this.apply(this, input), /* classIndex -> {
                var beforeKeyword = input */.substring(0, classIndex);
                var afterKeyword = input.substring(classIndex + this.length());
                return new Tuple<>(beforeKeyword, afterKeyword);
            });
}
/* private */ int InfixSplitter::length(struct InfixSplitter this){
	auto local42 = this.infix;
	return this.infix.length(this.infix);
}
/* private */ template Option<int> InfixSplitter::apply(struct InfixSplitter this, char* input){
	auto local43 = this;
	auto local44 = this.locator(this);
	return this.locator(this).apply(this.locator(this), input, this.infix);
}
/* @Override
 public */ template Option<(char*, char*)> TypeSeparatorSplitter::split(struct TypeSeparatorSplitter this, char* input){
	auto local45 = /* segments -> {
                var left = segments */.left;
                if (left;
	auto local46 = /* segments -> {
                var left = segments */.left;
                if (left.isEmpty()) {
                    return new None<>(/* segments -> {
                var left = segments */.left;
                if (left, /* );
                }

                var beforeType = left */.iterate();
	auto local47 = divide(input, /*  TypeSeparatorSplitter::fold) */.removeLast();
	return divide(input, /*  TypeSeparatorSplitter::fold) */.removeLast().flatMap(divide(input, /*  TypeSeparatorSplitter::fold) */.removeLast(), /* segments -> {
                var left = segments */.left;
                if (left.isEmpty()) {
                    return new None<>(/* segments -> {
                var left = segments */.left;
                if (left, /* );
                }

                var beforeType = left */.iterate().collect(/* segments -> {
                var left = segments */.left;
                if (left.isEmpty()) {
                    return new None<>(/* segments -> {
                var left = segments */.left;
                if (left, /* );
                }

                var beforeType = left */.iterate(), /* new Joiner */(" ")).orElse("");
                var type = segments.right;
                return new Some<>(new Tuple<>(beforeType, type));
            });
}
/* private static */ struct DivideState TypeSeparatorSplitter::fold(struct TypeSeparatorSplitter this, struct DivideState state, char c){
	auto local48 = state;/* if (c == ' ' && state.isLevel()) {
                return state.advance();
            } */
	auto appended = state.append(state, c);/* if (c == '<') {
                return appended.enter();
            } *//* if (c == '>') {
                return appended.exit();
            } */
	return appended;
}
struct public Definition::Definition(struct Definition this, struct Type type, char* name){
	this(/* new None<> */(), type, name);
}
/* public */ struct Definition Definition::mapName(struct Definition this, char* (*mapper)(char*)){
	auto local49 = mapper;
	return /* new Definition */(this.maybeBeforeType, this.type, mapper.apply(mapper, this.name));
}
/* @Override
 public */ struct String_ Definition::generate(struct Definition this){
	auto local50 = this;
	auto local51 = Strings;
	return Strings.from(Strings, this.generate0(this));
}
/* private */ char* Definition::generate0(struct Definition this){
	auto local52 = this.maybeBeforeType;
	auto local53 = this.maybeBeforeType.map(this.maybeBeforeType, /* beforeType -> generatePlaceholder(beforeType) + " " */);
	auto local54 = /* beforeTypeString + this */.type;
	auto beforeTypeString = this.maybeBeforeType.map(this.maybeBeforeType, /* beforeType -> generatePlaceholder(beforeType) + " " */).orElse(this.maybeBeforeType.map(this.maybeBeforeType, /* beforeType -> generatePlaceholder(beforeType) + " " */), "");
	return /* beforeTypeString + this */.type.generateWithName(/* beforeTypeString + this */.type, this.name).toSlice();
}
/* @Override
 public */ struct String_ Content::generate(struct Content this){
	auto local55 = Strings;
	return Strings.from(Strings, generatePlaceholder(this.input));
}
/* @Override
 public */ struct String_ Functional::generate(struct Functional this){
	auto local56 = this;
	return this.generateWithName(this, "");
}
/* @Override
 public */ struct String_ Functional::generateWithName(struct Functional this, char* name){
	auto local57 = this;
	auto local58 = /* type -> type */;
	auto local59 = this.arguments(this, /* ) */.iterate();
	auto local60 = this.arguments(this, /* ) */.iterate().map(this.arguments(this, /* ) */.iterate(), /* type -> type */.generate(/* type -> type */, /* ) */.toSlice());
	auto local61 = this.arguments(this, /* ) */.iterate().map(this.arguments(this, /* ) */.iterate(), /* type -> type */.generate(/* type -> type */, /* ) */.toSlice()).collect(this.arguments(this, /* ) */.iterate().map(this.arguments(this, /* ) */.iterate(), /* type -> type */.generate(/* type -> type */, /* ) */.toSlice()), /* new Joiner */(", "));
	auto local62 = this.returns;
	auto local63 = this.returns.generate(this.returns);
	auto local64 = this.returns.generate(this.returns).appendSlice(this.returns.generate(this.returns), " (*");
	auto local65 = this.returns.generate(this.returns).appendSlice(this.returns.generate(this.returns), " (*").appendSlice(this.returns.generate(this.returns).appendSlice(this.returns.generate(this.returns), " (*"), name);
	auto local66 = this.returns.generate(this.returns).appendSlice(this.returns.generate(this.returns), " (*").appendSlice(this.returns.generate(this.returns).appendSlice(this.returns.generate(this.returns), " (*"), name).appendSlice(this.returns.generate(this.returns).appendSlice(this.returns.generate(this.returns), " (*").appendSlice(this.returns.generate(this.returns).appendSlice(this.returns.generate(this.returns), " (*"), name), ")(");
	auto local67 = this.returns.generate(this.returns).appendSlice(this.returns.generate(this.returns), " (*").appendSlice(this.returns.generate(this.returns).appendSlice(this.returns.generate(this.returns), " (*"), name).appendSlice(this.returns.generate(this.returns).appendSlice(this.returns.generate(this.returns), " (*").appendSlice(this.returns.generate(this.returns).appendSlice(this.returns.generate(this.returns), " (*"), name), ")(").appendSlice(this.returns.generate(this.returns).appendSlice(this.returns.generate(this.returns), " (*").appendSlice(this.returns.generate(this.returns).appendSlice(this.returns.generate(this.returns), " (*"), name).appendSlice(this.returns.generate(this.returns).appendSlice(this.returns.generate(this.returns), " (*").appendSlice(this.returns.generate(this.returns).appendSlice(this.returns.generate(this.returns), " (*"), name), ")("), joinedArguments);
	auto joinedArguments = this.arguments(this, /* ) */.iterate().map(this.arguments(this, /* ) */.iterate(), /* type -> type */.generate(/* type -> type */, /* ) */.toSlice()).collect(this.arguments(this, /* ) */.iterate().map(this.arguments(this, /* ) */.iterate(), /* type -> type */.generate(/* type -> type */, /* ) */.toSlice()), /* new Joiner */(", ")).orElse(this.arguments(this, /* ) */.iterate().map(this.arguments(this, /* ) */.iterate(), /* type -> type */.generate(/* type -> type */, /* ) */.toSlice()).collect(this.arguments(this, /* ) */.iterate().map(this.arguments(this, /* ) */.iterate(), /* type -> type */.generate(/* type -> type */, /* ) */.toSlice()), /* new Joiner */(", ")), "");
	return this.returns.generate(this.returns).appendSlice(this.returns.generate(this.returns), " (*").appendSlice(this.returns.generate(this.returns).appendSlice(this.returns.generate(this.returns), " (*"), name).appendSlice(this.returns.generate(this.returns).appendSlice(this.returns.generate(this.returns), " (*").appendSlice(this.returns.generate(this.returns).appendSlice(this.returns.generate(this.returns), " (*"), name), ")(").appendSlice(this.returns.generate(this.returns).appendSlice(this.returns.generate(this.returns), " (*").appendSlice(this.returns.generate(this.returns).appendSlice(this.returns.generate(this.returns), " (*"), name).appendSlice(this.returns.generate(this.returns).appendSlice(this.returns.generate(this.returns), " (*").appendSlice(this.returns.generate(this.returns).appendSlice(this.returns.generate(this.returns), " (*"), name), ")("), joinedArguments).appendSlice(this.returns.generate(this.returns).appendSlice(this.returns.generate(this.returns), " (*").appendSlice(this.returns.generate(this.returns).appendSlice(this.returns.generate(this.returns), " (*"), name).appendSlice(this.returns.generate(this.returns).appendSlice(this.returns.generate(this.returns), " (*").appendSlice(this.returns.generate(this.returns).appendSlice(this.returns.generate(this.returns), " (*"), name), ")(").appendSlice(this.returns.generate(this.returns).appendSlice(this.returns.generate(this.returns), " (*").appendSlice(this.returns.generate(this.returns).appendSlice(this.returns.generate(this.returns), " (*"), name).appendSlice(this.returns.generate(this.returns).appendSlice(this.returns.generate(this.returns), " (*").appendSlice(this.returns.generate(this.returns).appendSlice(this.returns.generate(this.returns), " (*"), name), ")("), joinedArguments), ")");
}
/* @Override
 public */ struct String_ Template::generate(struct Template this){
	auto local68 = this;
	auto local69 = Strings;
	return Strings.from(Strings, this.generate0(this));
}
/* private */ char* Template::generate0(struct Template this){
	auto local70 = this;
	auto local71 = /* type -> type */;
	auto local72 = this.arguments(this, /* ) */.iterate();
	auto local73 = this.arguments(this, /* ) */.iterate().map(this.arguments(this, /* ) */.iterate(), /* type -> type */.generate(/* type -> type */, /* ) */.toSlice());
	auto local74 = this.arguments(this, /* ) */.iterate().map(this.arguments(this, /* ) */.iterate(), /* type -> type */.generate(/* type -> type */, /* ) */.toSlice()).collect(this.arguments(this, /* ) */.iterate().map(this.arguments(this, /* ) */.iterate(), /* type -> type */.generate(/* type -> type */, /* ) */.toSlice()), /* new Joiner */(", "));
	auto generatedTuple = this.arguments(this, /* ) */.iterate().map(this.arguments(this, /* ) */.iterate(), /* type -> type */.generate(/* type -> type */, /* ) */.toSlice()).collect(this.arguments(this, /* ) */.iterate().map(this.arguments(this, /* ) */.iterate(), /* type -> type */.generate(/* type -> type */, /* ) */.toSlice()), /* new Joiner */(", ")).orElse(this.arguments(this, /* ) */.iterate().map(this.arguments(this, /* ) */.iterate(), /* type -> type */.generate(/* type -> type */, /* ) */.toSlice()).collect(this.arguments(this, /* ) */.iterate().map(this.arguments(this, /* ) */.iterate(), /* type -> type */.generate(/* type -> type */, /* ) */.toSlice()), /* new Joiner */(", ")), "");
	return "template " + this.base() + "<" + generatedTuple + ">";
}
/* @Override
 public */ template List<T> ListCollector::createInitial(struct ListCollector<T> this){
	auto local75 = Lists;
	return Lists.empty(Lists);
}
/* @Override
 public */ template List<T> ListCollector::fold(struct ListCollector<T> this, template List<T> current, T element){
	auto local76 = current;
	return current.addLast(current, element);
}
/* @Override
 public */ struct String_ TypeParameter::generate(struct TypeParameter this){
	auto local77 = this;
	auto local78 = Strings;
	return Strings.from(Strings, this.generate0(this));
}
/* private */ char* TypeParameter::generate0(struct TypeParameter this){
	return this.value;
}
/* @Override
 public */ struct String_ Ref::generate(struct Ref this){
	auto local79 = this;
	auto local80 = Strings;
	return Strings.from(Strings, this.generate0(this));
}
/* private */ char* Ref::generate0(struct Ref this){
	auto local81 = this.type;
	return this.type.generate(this.type).toSlice() + "*";
}
/* @Override
 public */ struct String_ TupleType::generate(struct TupleType this){
	auto local82 = this;
	auto local83 = Strings;
	return Strings.from(Strings, this.generate0(this));
}
/* private */ char* TupleType::generate0(struct TupleType this){
	return "(" + generateNodesAsValues(this.arguments) + ")";
}
/* @Override
 public */ struct String_ StructRef::generate(struct StructRef this){
	auto local84 = this.typeParams;
	auto local85 = this.typeParams.iterate(this.typeParams);
	auto local86 = this.typeParams.iterate(this.typeParams).collect(this.typeParams.iterate(this.typeParams), /* new Joiner */(", "));
	auto local87 = this.typeParams.iterate(this.typeParams).collect(this.typeParams.iterate(this.typeParams), /* new Joiner */(", ")).map(this.typeParams.iterate(this.typeParams).collect(this.typeParams.iterate(this.typeParams), /* new Joiner */(", ")), /* inner -> "<" + inner + ">" */);
	auto local88 = Strings;
	auto local89 = Strings.from(Strings, "struct ");
	auto local90 = Strings.from(Strings, "struct ").appendSlice(Strings.from(Strings, "struct "), this.input);
	auto typeParamString = this.typeParams.iterate(this.typeParams).collect(this.typeParams.iterate(this.typeParams), /* new Joiner */(", ")).map(this.typeParams.iterate(this.typeParams).collect(this.typeParams.iterate(this.typeParams), /* new Joiner */(", ")), /* inner -> "<" + inner + ">" */).orElse(this.typeParams.iterate(this.typeParams).collect(this.typeParams.iterate(this.typeParams), /* new Joiner */(", ")).map(this.typeParams.iterate(this.typeParams).collect(this.typeParams.iterate(this.typeParams), /* new Joiner */(", ")), /* inner -> "<" + inner + ">" */), "");
	return Strings.from(Strings, "struct ").appendSlice(Strings.from(Strings, "struct "), this.input).appendSlice(Strings.from(Strings, "struct ").appendSlice(Strings.from(Strings, "struct "), this.input), typeParamString);
}
/* @Override
 public */ template Option<(char*, char*)> DividingSplitter::split(struct DividingSplitter this, char* input){
	auto local91 = /* divisions -> {
                var left1 = divisions */.left;
                if (left1;
	auto local92 = /* divisions -> {
                var left1 = divisions */.left;
                if (left1.isEmpty()) {
                    return new Tuple<>(/* divisions -> {
                var left1 = divisions */.left;
                if (left1, divisions.right, /*  "");
                }

                var left = left1 */.iterate();
	auto local93 = divide(input, this.folder).removeLast();
	return divide(input, this.folder).removeLast().map(divide(input, this.folder).removeLast(), /* divisions -> {
                var left1 = divisions */.left;
                if (left1.isEmpty()) {
                    return new Tuple<>(/* divisions -> {
                var left1 = divisions */.left;
                if (left1, divisions.right, /*  "");
                }

                var left = left1 */.iterate().collect(/* divisions -> {
                var left1 = divisions */.left;
                if (left1.isEmpty()) {
                    return new Tuple<>(/* divisions -> {
                var left1 = divisions */.left;
                if (left1, divisions.right, /*  "");
                }

                var left = left1 */.iterate(), /* new Joiner */()).orElse("");
                var right = divisions.right;
                return new Tuple<>(left, right);
            });
}
/* @Override
 public */ struct String_ Symbol::generate(struct Symbol this){
	auto local94 = Strings;
	return Strings.from(Strings, this.value);
}
/* @Override
 public */ struct String_ StringNode::generate(struct StringNode this){
	auto local95 = Strings;
	auto local96 = Strings.from(Strings, "\"");
	auto local97 = Strings.from(Strings, "\"").appendSlice(Strings.from(Strings, "\""), this.value);
	return Strings.from(Strings, "\"").appendSlice(Strings.from(Strings, "\""), this.value).appendSlice(Strings.from(Strings, "\"").appendSlice(Strings.from(Strings, "\""), this.value), "\"");
}
/* @Override
 public */ struct String_ Invocation::generate(struct Invocation this){
	auto local98 = this;
	auto local99 = this.arguments(this, /* ) */.iterate();
	auto local100 = this.arguments(this, /* ) */.iterate().map(this.arguments(this, /* ) */.iterate(), /* Node::generate */);
	auto local101 = this.arguments(this, /* ) */.iterate().map(this.arguments(this, /* ) */.iterate(), /* Node::generate */).map(this.arguments(this, /* ) */.iterate().map(this.arguments(this, /* ) */.iterate(), /* Node::generate */), /* String_::toSlice */);
	auto local102 = this.arguments(this, /* ) */.iterate().map(this.arguments(this, /* ) */.iterate(), /* Node::generate */).map(this.arguments(this, /* ) */.iterate().map(this.arguments(this, /* ) */.iterate(), /* Node::generate */), /* String_::toSlice */).collect(this.arguments(this, /* ) */.iterate().map(this.arguments(this, /* ) */.iterate(), /* Node::generate */).map(this.arguments(this, /* ) */.iterate().map(this.arguments(this, /* ) */.iterate(), /* Node::generate */), /* String_::toSlice */), /* new Joiner */(", "));
	auto local103 = this;
	auto local104 = Strings;
	auto joined = this.arguments(this, /* ) */.iterate().map(this.arguments(this, /* ) */.iterate(), /* Node::generate */).map(this.arguments(this, /* ) */.iterate().map(this.arguments(this, /* ) */.iterate(), /* Node::generate */), /* String_::toSlice */).collect(this.arguments(this, /* ) */.iterate().map(this.arguments(this, /* ) */.iterate(), /* Node::generate */).map(this.arguments(this, /* ) */.iterate().map(this.arguments(this, /* ) */.iterate(), /* Node::generate */), /* String_::toSlice */), /* new Joiner */(", ")).orElse(this.arguments(this, /* ) */.iterate().map(this.arguments(this, /* ) */.iterate(), /* Node::generate */).map(this.arguments(this, /* ) */.iterate().map(this.arguments(this, /* ) */.iterate(), /* Node::generate */), /* String_::toSlice */).collect(this.arguments(this, /* ) */.iterate().map(this.arguments(this, /* ) */.iterate(), /* Node::generate */).map(this.arguments(this, /* ) */.iterate().map(this.arguments(this, /* ) */.iterate(), /* Node::generate */), /* String_::toSlice */), /* new Joiner */(", ")), "");
	return Strings.from(Strings, this.caller(this, /* ) */.generate().toSlice() + "(" + joined + ")");
}
/* @Override
 public */ struct String_ DataAccess::generate(struct DataAccess this){
	auto local105 = this;
	auto local106 = this.parent(this, /* ) */.generate();
	auto local107 = Strings;
	return Strings.from(Strings, this.parent(this, /* ) */.generate().toSlice(this.parent(this, /* ) */.generate(), /* ) + " */." + this.property());
}
auto Primitive::Primitive(struct Primitive this, char* value){/* this.value = value; */
}
/* @Override
 public */ struct String_ Primitive::generate(struct Primitive this){
	auto local108 = this;
	auto local109 = Strings;
	return Strings.from(Strings, this.generate0(this));
}
/* private */ char* Primitive::generate0(struct Primitive this){
	return this.value;
}
/* public static */ void main(){
	auto local110 = run();
	run().ifPresent(run(), /* Throwable::printStackTrace */);
}
/* private static */ template Option<struct IOException> run(){
	return /* switch (readInput()) {
            case Err<String, IOException>(var error) -> new Some<>(error);
            case Ok<String, IOException>(var input) -> {
                var output = compileRoot(input);
                yield writeOutput(output);
            }
        } */;
}
/* @Actual
 private static */ template Option<struct IOException> writeOutput(char* output){/* try {
            Files.writeString(TARGET, output);
            return new None<>();
        } *//* catch (IOException e) {
            return new Some<>(e);
        } */
}
/* @Actual
 private static */ template Result<char*, struct IOException> readInput(){/* try {
            return new Ok<>(Files.readString(SOURCE));
        } *//* catch (IOException e) {
            return new Err<>(e);
        } */
}
/* private static */ char* compileRoot(char* input){
	auto local111 = compileAll(state, input, /*  Main::compileRootSegment */);
	auto local112 = tuple.right + tuple.left;
	auto state = /* new ImmutableCompileState */();
	auto tuple = compileAll(state, input, /*  Main::compileRootSegment */).orElse(compileAll(state, input, /*  Main::compileRootSegment */), /* new Tuple<> */(state, ""));
	return tuple.right + tuple.left.generate(tuple.right + tuple.left);
}
/* private static */ template Option<(struct CompileState, char*)> compileAll(struct CompileState initial, char* input, template Option<(struct CompileState, char*)> (*mapper)(struct CompileState, char*)){
	return all(initial, input, /*  Main::foldStatementChar */, mapper, /*  Main::mergeStatements */);
}
/* private static */ template Option<(struct CompileState, char*)> all(struct CompileState initial, char* input, struct DivideState (*folder)(struct DivideState, char), template Option<(struct CompileState, char*)> (*mapper)(struct CompileState, char*), struct StringBuilder (*merger)(struct StringBuilder, char*)){
	auto local113 = parseAll(initial, input, folder, mapper);
	return parseAll(initial, input, folder, mapper).map(parseAll(initial, input, folder, mapper), /* tuple -> new Tuple<> */(tuple.left, generateAll(merger, tuple.right)));
}
/* private static */ struct StringBuilder mergeStatements(struct StringBuilder output, char* right){
	auto local114 = output;
	return output.append(output, right);
}
/* private static */ struct DivideState foldStatementChar(struct DivideState state, char c){
	auto local115 = state;
	auto appended = state.append(state, c);/* if (c == ';' && appended.isLevel()) {
            return appended.advance();
        } *//* if (c == '}' && appended.isShallow()) {
            var exit = appended.exit();
            if (exit.peek() instanceof Some(var temp) && temp == ';') {
                return exit.popAndAppend().orElse(exit).advance();
            }
            else {
                return exit.advance();
            }
        } *//* if (c == '{' || c == '(') {
            return appended.enter();
        } *//* if (c == '}' || c == ')') {
            return appended.exit();
        } */
	return appended;
}
/* private static */ template Option<(struct CompileState, char*)> compileRootSegment(struct CompileState state, char* input){
	auto local116 = /* content -> content */;
	auto local117 = Tuple;
	auto local118 = /* (state1, input1) -> content */(state1, input1);
	auto local119 = Lists;
	return or(state, input, Lists.of(Lists, /* 
                Main::whitespace */, /* 
                Main::compileNamespaced */, parseClass(), /* (state1, input1) -> content */(state1, input1).map(/* (state1, input1) -> content */(state1, input1), Tuple.mapRight(Tuple, /* content -> content */.generate(/* content -> content */, /* ) */.toSlice()))));
}
/* private static */ template Option<(struct CompileState, char*)> (*parseClass)(struct CompileState, char*)(){
	return structure("class", "class ");
}
/* private static */ template Option<(struct CompileState, char*)> (*structure)(struct CompileState, char*)(char* type, char* infix){
	auto local120 = beforeKeyword;
	auto local121 = /* (beforeKeyword, afterKeyword) -> {
            var slices = Arrays */;
	auto local122 = /* (beforeKeyword, afterKeyword) -> {
            var slices = Arrays */.stream(/* (beforeKeyword, afterKeyword) -> {
            var slices = Arrays */, beforeKeyword.split(beforeKeyword, " "));
	auto local123 = /* value -> !value */;
	auto local124 = /* (beforeKeyword, afterKeyword) -> {
            var slices = Arrays */.stream(/* (beforeKeyword, afterKeyword) -> {
            var slices = Arrays */, beforeKeyword.split(beforeKeyword, " ")).map(/* (beforeKeyword, afterKeyword) -> {
            var slices = Arrays */.stream(/* (beforeKeyword, afterKeyword) -> {
            var slices = Arrays */, beforeKeyword.split(beforeKeyword, " ")), /* String::strip */);
	return /* (state, input) -> first */(input, infix, /* (beforeKeyword, afterKeyword) -> {
            var slices = Arrays */.stream(/* (beforeKeyword, afterKeyword) -> {
            var slices = Arrays */, beforeKeyword.split(beforeKeyword, " ")).map(/* (beforeKeyword, afterKeyword) -> {
            var slices = Arrays */.stream(/* (beforeKeyword, afterKeyword) -> {
            var slices = Arrays */, beforeKeyword.split(beforeKeyword, " ")), /* String::strip */).filter(/* (beforeKeyword, afterKeyword) -> {
            var slices = Arrays */.stream(/* (beforeKeyword, afterKeyword) -> {
            var slices = Arrays */, beforeKeyword.split(beforeKeyword, " ")).map(/* (beforeKeyword, afterKeyword) -> {
            var slices = Arrays */.stream(/* (beforeKeyword, afterKeyword) -> {
            var slices = Arrays */, beforeKeyword.split(beforeKeyword, " ")), /* String::strip */), /* value -> !value */.isEmpty(/* value -> !value */)).toList();

            if (slices.contains("@Actual")) {
                return new Some<>(new Tuple<>(state, ""));
            }

            return first(afterKeyword, "{", (beforeContent, withEnd) -> {
                return or(state, beforeContent, Lists.of(
                        (state0, beforeContent0) -> structureWithVariants(type, state0, beforeKeyword, beforeContent0, withEnd),
                        (state0, beforeContent0) -> structureWithoutVariants(type, state0, beforeKeyword, beforeContent0, Lists.empty(), withEnd)
                ));
            });
        });
}
/* private static */ template Option<(struct CompileState, char*)> structureWithVariants(char* type, struct CompileState state, char* beforeKeyword, char* beforeContent, char* withEnd){
	auto local125 = /* (Value node) -> node */;
	auto local126 = Tuple;
	auto local127 = /* (state1, value) -> symbol */(state1, value);
	return first(beforeContent, " permits ", /* (beforePermits, variantsString) -> {
            return parseValues */(state, variantsString, /* (state1, value) -> symbol */(state1, value).map(/* (state1, value) -> symbol */(state1, value), Tuple.mapRight(Tuple, /* (Value node) -> node */.generate(/* (Value node) -> node */, /* ) */.toSlice()))).flatMap(params -> {
                return structureWithoutVariants(type, params.left, beforeKeyword, beforePermits, params.right, withEnd);
            });
        });
}
/* private static */ template Option<(struct CompileState, struct Value)> symbol(struct CompileState state, char* value){
	auto local128 = value;
	auto stripped = value.strip(value);/* if (isSymbol(stripped)) {
            return new Some<>(new Tuple<>(state, new Symbol(stripped)));
        } *//* else {
            return new None<>();
        } */
}
/* private static */ template Option<(struct CompileState, char*)> structureWithoutVariants(char* type, struct CompileState state, char* beforeKeyword, char* beforeContent, template List<char*> variants, char* withEnd){
	auto local129 = Lists;
	return or(state, beforeContent, Lists.of(Lists, /* (state0, s) -> structureWithImplements */(type, state0, beforeKeyword, s, variants, withEnd), /* (state0, s) -> structureWithoutImplements */(type, state0, beforeKeyword, s, variants, withEnd)));
}
/* private static */ template Option<(struct CompileState, char*)> structureWithImplements(char* type, struct CompileState state0, char* beforeKeyword, char* s, template List<char*> variants, char* withEnd){
	return first(s, " implements ", /* (s1, s2) -> structureWithoutImplements */(type, state0, beforeKeyword, s1, variants, withEnd));
}
/* private static */ template Option<(struct CompileState, char*)> structureWithoutImplements(char* type, struct CompileState state, char* beforeKeyword, char* beforeContent, template List<char*> variants, char* withEnd){
	auto local130 = Lists;
	return or(state, beforeContent, Lists.of(Lists, /* (state1, s) -> structureWithExtends */(type, beforeKeyword, beforeContent, variants, withEnd, state1), /* (state2, s) -> structureWithoutExtends */(type, state2, beforeKeyword, s, variants, withEnd)));
}
/* private static */ template Option<(struct CompileState, char*)> structureWithExtends(char* type, char* beforeKeyword, char* beforeContent, template List<char*> variants, char* withEnd, struct CompileState state1){
	return first(beforeContent, " extends ", /* (s1, s2) -> structureWithoutExtends */(type, state1, beforeKeyword, s1, variants, withEnd));
}
/* private static */ template Option<(struct CompileState, char*)> structureWithoutExtends(char* type, struct CompileState state, char* beforeKeyword, char* beforeContent, template List<char*> variants, char* withEnd){
	auto local131 = before;
	auto local132 = Lists;
	auto local133 = Lists;
	return or(state, beforeContent, Lists.of(Lists, /* (instance, before) -> structureWithParams */(type, instance, beforeKeyword, before, variants, withEnd), /* (instance, before) -> structureWithoutParams */(type, instance, beforeKeyword, before.strip(before), Lists.empty(Lists), variants, withEnd)));
}
/* private static */ template Option<(struct CompileState, char*)> structureWithParams(char* type, struct CompileState instance, char* beforeKeyword, char* beforeContent, template List<char*> variants, char* withEnd){
	auto local134 = beforeContent;
	return suffix(beforeContent.strip(beforeContent), ")", /* withoutEnd -> first */(withoutEnd, "(", /* (name, paramString) -> {
            return parseAll */(instance, paramString, /*  Main::foldValueChar */, /*  Main::parameter */).flatMap(params -> {
                        return structureWithoutParams(type, params.left, beforeKeyword, name, params.right, variants, withEnd);
                    });
        }));
}
/* private static */ template Option<(struct CompileState, struct Parameter)> parameter(struct CompileState instance, char* paramString){
	auto local135 = Lists;
	auto local136 = Main;
	return Main.or(Main, instance, paramString, Lists.of(Lists, wrap(/* Main::definition */), wrap(/* Main::content */)));
}
/* private static */ template Option<(struct CompileState, char*)> structureWithoutParams(char* type, struct CompileState state, char* beforeKeyword, char* beforeParams, template List<struct Parameter> params, template List<char*> variants, char* withEnd){
	auto local137 = Lists;
	auto local138 = Lists;
	return or(state, beforeParams, Lists.of(Lists, /* (state0, beforeParams0) -> structureWithTypeParams */(type, state0, beforeParams0, beforeKeyword, params, variants, withEnd), /* (state0, name) -> structureWithName */(type, state0, beforeKeyword, name, Lists.empty(Lists), params, variants, withEnd)));
}
/* private static */ template Option<(struct CompileState, char*)> structureWithTypeParams(char* type, struct CompileState state, char* beforeParams0, char* beforeKeyword, template List<struct Parameter> params, template List<char*> variants, char* withEnd){
	auto local139 = beforeParams0;
	auto local140 = Tuple;
	auto local141 = /* (state1, value) -> symbol */(state1, value);
	return suffix(beforeParams0.strip(beforeParams0), ">", /* withoutEnd -> {
            return first */(withoutEnd, "<", /* (name, typeParamString) -> {
                return parseValues */(state, typeParamString, /* (state1, value) -> symbol */(state1, value).map(/* (state1, value) -> symbol */(state1, value), Tuple.mapRight(Tuple, (/* Value node) -> node */.generate().toSlice()))).flatMap(values -> {
                    return structureWithName(type, values.left, beforeKeyword, name, values.right, params, variants, withEnd);
                });
            });
        });
}
/* private static */ template Option<(struct CompileState, char*)> structureWithName(char* type, struct CompileState state, char* beforeKeyword, char* name, template List<char*> typeParams, template List<struct Parameter> params, template List<char*> variants, char* withEnd){
	auto local142 = withEnd;
	auto local143 = state;
	auto local144 = /* content -> {
            return compileAll */(state.withStructType(state, /* new StructurePrototype */(type, name, typeParams, variants)), content, /*  Main::structSegment */);
	return suffix(withEnd.strip(withEnd), "}", /* content -> {
            return compileAll */(state.withStructType(state, /* new StructurePrototype */(type, name, typeParams, variants)), content, /*  Main::structSegment */).flatMap(/* content -> {
            return compileAll */(state.withStructType(state, /* new StructurePrototype */(type, name, typeParams, variants)), content, /*  Main::structSegment */), /* tuple -> {
                if (!isSymbol(name)) {
                    return new None<>();
                }
                return new Some<>(assembleStruct(type, tuple */.left, beforeKeyword, name, typeParams, params, variants, tuple.right));
            }).map(tuple -> new Tuple<>(tuple.left.withoutStructType(), tuple.right));
        });
}
/* private static */ (struct CompileState, char*) assembleStruct(char* type, struct CompileState state, char* beforeKeyword, char* name, template List<char*> typeParams, template List<struct Parameter> params, template List<char*> variants, char* oldContent){/* if (!variants.isEmpty()) {
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
	return generateStruct(state, beforeKeyword, name, generateTypeParams(typeParams), params, oldContent);
}
/* private static */ (struct CompileState, char*) generateStruct(struct CompileState state, char* beforeKeyword, char* name, char* typeParamString, template List<struct Parameter> params, char* content){
	auto local145 = params;
	auto local146 = /* t -> t */;
	auto local147 = params.iterate(params);
	auto local148 = params.iterate(params).map(params.iterate(params), /* t -> t */.generate(/* t -> t */, /* ) */.toSlice());
	auto local149 = params.iterate(params).map(params.iterate(params), /* t -> t */.generate(/* t -> t */, /* ) */.toSlice()).map(params.iterate(params).map(params.iterate(params), /* t -> t */.generate(/* t -> t */, /* ) */.toSlice()), /* value -> "\n\t" + value + ";" */);
	auto local150 = params.iterate(params).map(params.iterate(params), /* t -> t */.generate(/* t -> t */, /* ) */.toSlice()).map(params.iterate(params).map(params.iterate(params), /* t -> t */.generate(/* t -> t */, /* ) */.toSlice()), /* value -> "\n\t" + value + ";" */).collect(params.iterate(params).map(params.iterate(params), /* t -> t */.generate(/* t -> t */, /* ) */.toSlice()).map(params.iterate(params).map(params.iterate(params), /* t -> t */.generate(/* t -> t */, /* ) */.toSlice()), /* value -> "\n\t" + value + ";" */), /* new Joiner */());
	auto local151 = state;
	auto paramsString = params.iterate(params).map(params.iterate(params), /* t -> t */.generate(/* t -> t */, /* ) */.toSlice()).map(params.iterate(params).map(params.iterate(params), /* t -> t */.generate(/* t -> t */, /* ) */.toSlice()), /* value -> "\n\t" + value + ";" */).collect(params.iterate(params).map(params.iterate(params), /* t -> t */.generate(/* t -> t */, /* ) */.toSlice()).map(params.iterate(params).map(params.iterate(params), /* t -> t */.generate(/* t -> t */, /* ) */.toSlice()), /* value -> "\n\t" + value + ";" */), /* new Joiner */()).orElse(params.iterate(params).map(params.iterate(params), /* t -> t */.generate(/* t -> t */, /* ) */.toSlice()).map(params.iterate(params).map(params.iterate(params), /* t -> t */.generate(/* t -> t */, /* ) */.toSlice()), /* value -> "\n\t" + value + ";" */).collect(params.iterate(params).map(params.iterate(params), /* t -> t */.generate(/* t -> t */, /* ) */.toSlice()).map(params.iterate(params).map(params.iterate(params), /* t -> t */.generate(/* t -> t */, /* ) */.toSlice()), /* value -> "\n\t" + value + ";" */), /* new Joiner */()), "");
	auto generatedStruct = /*  generatePlaceholder(beforeKeyword */.strip()) + "struct " + name + typeParamString + " {" + paramsString + content + "\n};\n";
	return /* new Tuple<CompileState, String> */(state.addStruct(state, generatedStruct), "");
}
/* private static */ char* generateTypeParams(template List<char*> typeParams){
	auto local152 = typeParams;
	auto local153 = typeParams.isEmpty(typeParams, /* ) ? "" : "<" + typeParams */.iterate();
	return typeParams.isEmpty(typeParams, /* ) ? "" : "<" + typeParams */.iterate().collect(typeParams.isEmpty(typeParams, /* ) ? "" : "<" + typeParams */.iterate(), /* new Joiner */(", ")).orElse("") + ">";
}
/* private static <T> */ template Option<(struct CompileState, struct T)> or(struct CompileState state, char* input, template List<template Option<(struct CompileState, struct T)> (*)(struct CompileState, char*)> actions){
	auto local154 = actions;
	auto local155 = /* action -> action */;
	auto local156 = actions.iterate(actions);
	auto local157 = actions.iterate(actions).map(actions.iterate(actions), /* action -> action */.apply(/* action -> action */, state, input));
	return actions.iterate(actions).map(actions.iterate(actions), /* action -> action */.apply(/* action -> action */, state, input)).flatMap(actions.iterate(actions).map(actions.iterate(actions), /* action -> action */.apply(/* action -> action */, state, input)), /* Iterators::fromOptions)
                 */.next();
}
/* private static */ template Option<(struct CompileState, char*)> compileNamespaced(struct CompileState state, char* input){/* if (input.strip().startsWith("package ") || input.strip().startsWith("import ")) {
            return new Some<>(new Tuple<CompileState, String>(state, ""));
        } */
	return /* new None<> */();
}
/* private static */ template Option<(struct CompileState, char*)> structSegment(struct CompileState state, char* input){
	auto local158 = /* content -> content */;
	auto local159 = Tuple;
	auto local160 = /* (state1, input1) -> content */(state1, input1);
	auto local161 = Lists;
	return or(state, input, Lists.of(Lists, /* 
                Main::whitespace */, /* 
                Main::annotation */, structure("enum", "enum "), parseClass(), structure("record", "record "), structure("interface", "interface "), /* 
                Main::method */, /* 
                Main::definitionStatement */, /* (state1, input1) -> content */(state1, input1).map(/* (state1, input1) -> content */(state1, input1), Tuple.mapRight(Tuple, /* content -> content */.generate(/* content -> content */, /* ) */.toSlice()))));
}
/* private static */ template Option<(struct CompileState, char*)> definitionStatement(struct CompileState state, char* input){
	auto local162 = input;
	auto local163 = /* definition -> definition */;
	auto local164 = Tuple;
	auto local165 = /* withoutEnd -> definition */(state, withoutEnd);
	auto local166 = /* withoutEnd -> definition */(state, withoutEnd).map(/* withoutEnd -> definition */(state, withoutEnd), Tuple.mapRight(Tuple, /* definition -> definition */.generate(/* definition -> definition */, /* ) */.toSlice()));
	return suffix(input.strip(input), ";", /* withoutEnd -> definition */(state, withoutEnd).map(/* withoutEnd -> definition */(state, withoutEnd), Tuple.mapRight(Tuple, /* definition -> definition */.generate(/* definition -> definition */, /* ) */.toSlice())).map(/* withoutEnd -> definition */(state, withoutEnd).map(/* withoutEnd -> definition */(state, withoutEnd), Tuple.mapRight(Tuple, /* definition -> definition */.generate(/* definition -> definition */, /* ) */.toSlice())), /* value -> {
            var generated = "\n\t" + value */.right + ";";
            return new Tuple<>(value.left, generated);
        }));
}
/* private static */ template Option<(struct CompileState, struct Content)> content(struct CompileState state, char* input){
	return /* new Some<> */(/* new Tuple<> */(state, /* new Content */(input)));
}
/* private static */ template Option<(struct CompileState, char*)> whitespace(struct CompileState state, char* input){/* if (input.isBlank()) {
            return new Some<>(new Tuple<>(state, ""));
        } */
	return /* new None<> */();
}
/* private static */ template Option<(struct CompileState, char*)> method(struct CompileState state, char* input){
	auto local167 = /* outputDefinition -> {
                    return parseValues */(outputDefinition.left, paramsString, /*  Main::parameter */);
	auto local168 = /* (paramsString, withBraces) -> {
                return compileMethodHeader */(state, inputDefinition);
	return first(input, "(", /* (inputDefinition, withParams) -> {
            return first */(withParams, ")", /* (paramsString, withBraces) -> {
                return compileMethodHeader */(state, inputDefinition).flatMap(/* (paramsString, withBraces) -> {
                return compileMethodHeader */(state, inputDefinition), /* outputDefinition -> {
                    return parseValues */(outputDefinition.left, paramsString, /*  Main::parameter */).flatMap(/* outputDefinition -> {
                    return parseValues */(outputDefinition.left, paramsString, /*  Main::parameter */), /* outputParams -> {
                        var params = outputParams */.right
                                .iterate().map(Main::retainDefinition).flatMap(Iterators::fromOptions).collect(new ListCollector<>());

                        return or(outputParams.left, withBraces, Lists.of(
                                (state0, element) -> methodWithoutContent(state0, outputDefinition.right, params, element),
                                (state0, element) -> methodWithContent(state0, outputDefinition.right, params, element)));
                    });
                });
            });
        });
}
/* private static */ template Option<struct Definition> retainDefinition(struct Parameter param){/* if (param instanceof Definition definition) {
            return new Some<>(definition);
        } *//* else {
            return new None<>();
        } */
}
/* private static <T> */ template Option<(struct CompileState, template List<struct T>)> parseValues(struct CompileState state, char* input, template Option<(struct CompileState, struct T)> (*compiler)(struct CompileState, char*)){
	return parseAll(state, input, /*  Main::foldValueChar */, compiler);
}
/* private static <T> */ template Option<(struct CompileState, template List<struct T>)> parseAll(struct CompileState initial, char* input, struct DivideState (*folder)(struct DivideState, char), template Option<(struct CompileState, struct T)> (*mapper)(struct CompileState, char*)){
	auto local169 = Lists;
	auto local170 = /* (maybeCurrent, segment) -> maybeCurrent */;
	auto local171 = divide(input, /*  folder)
                 */.iterate();
	return divide(input, /*  folder)
                 */.iterate().<Option<Tuple<CompileState, List<T>>>>fold(divide(input, /*  folder)
                 */.iterate(), /* new Some<> */(/* new Tuple<CompileState, List<T>> */(initial, Lists.empty(Lists))), /* (maybeCurrent, segment) -> maybeCurrent */.flatMap(/* (maybeCurrent, segment) -> maybeCurrent */, /* state -> foldElement */(state, segment, mapper)));
}
/* private static <T> */ template Option<(struct CompileState, template List<struct T>)> foldElement((struct CompileState, template List<struct T>) state, char* segment, template Option<(struct CompileState, struct T)> (*mapper)(struct CompileState, char*)){
	auto local172 = mapper;
	auto local173 = mapper.apply(mapper, oldState, segment);
	auto oldState = state.left;
	auto oldCache = state.right;
	return mapper.apply(mapper, oldState, segment).map(mapper.apply(mapper, oldState, segment), /* result -> {
            var newState = result */.left;
            var newElement = result.right;
            return new Tuple<>(newState, oldCache.addLast(newElement));
        });
}
/* private static */ template List<char*> divide(char* input, struct DivideState (*folder)(struct DivideState, char)){
	auto local174 = current;
	struct DivideState current = /* new DivideState */(input);/* while (true) {
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
	return current.advance(current).segments;
}
/* private static */ template Option<struct DivideState> foldDoubleQuotes(struct DivideState state, char c){
	auto local175 = state;/* if (c != '\"') {
            return new None<>();
        } */
	auto appended = state.append(state, c);/* while (true) {
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
	return /* new Some<> */(appended);
}
/* private static */ template Option<struct DivideState> foldSingleQuotes(struct DivideState state, char c){
	auto local176 = state;
	auto local177 = /* maybeNextTuple -> {
            var nextChar = maybeNextTuple */.left;
            var nextState = maybeNextTuple.right;
	auto local178 = /* maybeNextTuple -> {
            var nextChar = maybeNextTuple */.left;
            var nextState = maybeNextTuple.right.append(/* maybeNextTuple -> {
            var nextChar = maybeNextTuple */.left;
            var nextState = maybeNextTuple.right, /* nextChar);

            var withEscaped = nextChar == '\\'
                    ? nextState */.popAndAppend();
	auto local179 = state.append(state, /* c) */.pop();/* if (c != '\'') {
            return new None<>();
        } */
	return state.append(state, /* c) */.pop().map(state.append(state, /* c) */.pop(), /* maybeNextTuple -> {
            var nextChar = maybeNextTuple */.left;
            var nextState = maybeNextTuple.right.append(/* maybeNextTuple -> {
            var nextChar = maybeNextTuple */.left;
            var nextState = maybeNextTuple.right, /* nextChar);

            var withEscaped = nextChar == '\\'
                    ? nextState */.popAndAppend().orElse(/* maybeNextTuple -> {
            var nextChar = maybeNextTuple */.left;
            var nextState = maybeNextTuple.right.append(/* maybeNextTuple -> {
            var nextChar = maybeNextTuple */.left;
            var nextState = maybeNextTuple.right, /* nextChar);

            var withEscaped = nextChar == '\\'
                    ? nextState */.popAndAppend(), /* nextState)
                    : nextState;

            return withEscaped */.popAndAppend().orElse(withEscaped);
        });
}
/* private static */ struct DivideState foldValueChar(struct DivideState state, char c){
	auto local180 = state;/* if (c == ',' && state.isLevel()) {
            return state.advance();
        } */
	auto appended = state.append(state, c);/* if (c == '-' && appended.peek().filter(value -> value == '>').isPresent()) {
            return appended.popAndAppend().orElse(appended);
        } *//* if (c == '<' || c == '(' || c == '{') {
            return appended.enter();
        } *//* if (c == '>' || c == ')' || c == '}') {
            return appended.exit();
        } */
	return appended;
}
/* private static <T extends Node> */ char* generateNodesAsValues(template List<struct T> params){
	auto local181 = params;
	auto local182 = /* t -> t */;
	auto local183 = params.iterate(params);
	auto local184 = params.iterate(params).map(params.iterate(params), /* t -> t */.generate(/* t -> t */, /* ) */.toSlice());
	auto local185 = params.iterate(params).map(params.iterate(params), /* t -> t */.generate(/* t -> t */, /* ) */.toSlice()).collect(params.iterate(params).map(params.iterate(params), /* t -> t */.generate(/* t -> t */, /* ) */.toSlice()), /* new Joiner */(", "));
	return params.iterate(params).map(params.iterate(params), /* t -> t */.generate(/* t -> t */, /* ) */.toSlice()).collect(params.iterate(params).map(params.iterate(params), /* t -> t */.generate(/* t -> t */, /* ) */.toSlice()), /* new Joiner */(", ")).orElse(params.iterate(params).map(params.iterate(params), /* t -> t */.generate(/* t -> t */, /* ) */.toSlice()).collect(params.iterate(params).map(params.iterate(params), /* t -> t */.generate(/* t -> t */, /* ) */.toSlice()), /* new Joiner */(", ")), "");
}
/* private static */ char* generateAll(struct StringBuilder (*merger)(struct StringBuilder, char*), template List<char*> right){
	auto local186 = right;
	auto local187 = right.iterate(right);
	return right.iterate(right).fold(right.iterate(right), /* new StringBuilder */(), /*  merger) */.toString();
}
/* private static */ template Option<(struct CompileState, char*)> methodWithoutContent(struct CompileState state, struct Definition definition, template List<struct Definition> params, char* content){/* if (!content.equals(";")) {
            return new None<>();
        } *//* String generated; *//* if (state.findStructureType().filter(value -> value.type.equals("interface") && value.variants.isEmpty()).isPresent()) {
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
	return /* new Some<> */(/* new Tuple<CompileState, String> */(state, generated));
}
/* private static */ template Option<(struct CompileState, char*)> methodWithContent(struct CompileState state, struct Definition definition, template List<struct Definition> params, char* withBraces){
	auto local188 = withBraces;
	auto local189 = /* tuple -> {
                    var newParameters = tuple */.left;
	auto local190 = /* structType -> params */;
	auto local191 = /* tuple -> {
                    var newParameters = tuple */.left.findStructureType(/* tuple -> {
                    var newParameters = tuple */.left);
	auto local192 = /* tuple -> {
                    var newParameters = tuple */.left.findStructureType(/* tuple -> {
                    var newParameters = tuple */.left).map(/* tuple -> {
                    var newParameters = tuple */.left.findStructureType(/* tuple -> {
                    var newParameters = tuple */.left), /* structType -> params */.addFirst(/* structType -> params */, /* new Definition */(/* new StructRef */(structType.name, structType.typeParams), "this"))).orElse(params);

                    var paramStrings = generateNodesAsValues(newParameters);

                    var removed = tuple.left;
	auto local193 = /* tuple -> {
                    var newParameters = tuple */.left.findStructureType(/* tuple -> {
                    var newParameters = tuple */.left).map(/* tuple -> {
                    var newParameters = tuple */.left.findStructureType(/* tuple -> {
                    var newParameters = tuple */.left), /* structType -> params */.addFirst(/* structType -> params */, /* new Definition */(/* new StructRef */(structType.name, structType.typeParams), "this"))).orElse(params);

                    var paramStrings = generateNodesAsValues(newParameters);

                    var removed = tuple.left.removeStatements(/* tuple -> {
                    var newParameters = tuple */.left.findStructureType(/* tuple -> {
                    var newParameters = tuple */.left).map(/* tuple -> {
                    var newParameters = tuple */.left.findStructureType(/* tuple -> {
                    var newParameters = tuple */.left), /* structType -> params */.addFirst(/* structType -> params */, /* new Definition */(/* new StructRef */(structType.name, structType.typeParams), "this"))).orElse(params);

                    var paramStrings = generateNodesAsValues(newParameters);

                    var removed = tuple.left, /* );
                    var joined = removed */.left.iterate();
	auto local194 = /* name -> removed */.right;
	auto local195 = /* name -> removed */.right.findStructureType(/* name -> removed */.right);
	auto local196 = /* tuple -> {
                    var newParameters = tuple */.left.findStructureType(/* tuple -> {
                    var newParameters = tuple */.left).map(/* tuple -> {
                    var newParameters = tuple */.left.findStructureType(/* tuple -> {
                    var newParameters = tuple */.left), /* structType -> params */.addFirst(/* structType -> params */, /* new Definition */(/* new StructRef */(structType.name, structType.typeParams), "this"))).orElse(params);

                    var paramStrings = generateNodesAsValues(newParameters);

                    var removed = tuple.left.removeStatements(/* tuple -> {
                    var newParameters = tuple */.left.findStructureType(/* tuple -> {
                    var newParameters = tuple */.left).map(/* tuple -> {
                    var newParameters = tuple */.left.findStructureType(/* tuple -> {
                    var newParameters = tuple */.left), /* structType -> params */.addFirst(/* structType -> params */, /* new Definition */(/* new StructRef */(structType.name, structType.typeParams), "this"))).orElse(params);

                    var paramStrings = generateNodesAsValues(newParameters);

                    var removed = tuple.left, /* );
                    var joined = removed */.left.iterate().collect(/* tuple -> {
                    var newParameters = tuple */.left.findStructureType(/* tuple -> {
                    var newParameters = tuple */.left).map(/* tuple -> {
                    var newParameters = tuple */.left.findStructureType(/* tuple -> {
                    var newParameters = tuple */.left), /* structType -> params */.addFirst(/* structType -> params */, /* new Definition */(/* new StructRef */(structType.name, structType.typeParams), "this"))).orElse(params);

                    var paramStrings = generateNodesAsValues(newParameters);

                    var removed = tuple.left.removeStatements(/* tuple -> {
                    var newParameters = tuple */.left.findStructureType(/* tuple -> {
                    var newParameters = tuple */.left).map(/* tuple -> {
                    var newParameters = tuple */.left.findStructureType(/* tuple -> {
                    var newParameters = tuple */.left), /* structType -> params */.addFirst(/* structType -> params */, /* new Definition */(/* new StructRef */(structType.name, structType.typeParams), "this"))).orElse(params);

                    var paramStrings = generateNodesAsValues(newParameters);

                    var removed = tuple.left, /* );
                    var joined = removed */.left.iterate(), /* new Joiner */()).orElse("");

                    var generated = definition
                            ;
	auto local197 = /* content -> {
                return compileAll */(state, content, /*  Main::functionSegment */);
	return prefix(withBraces.strip(withBraces), "{", /* withoutStart1 -> {
            return suffix */(withoutStart1, "}", /* content -> {
                return compileAll */(state, content, /*  Main::functionSegment */).flatMap(/* content -> {
                return compileAll */(state, content, /*  Main::functionSegment */), /* tuple -> {
                    var newParameters = tuple */.left.findStructureType(/* tuple -> {
                    var newParameters = tuple */.left).map(/* tuple -> {
                    var newParameters = tuple */.left.findStructureType(/* tuple -> {
                    var newParameters = tuple */.left), /* structType -> params */.addFirst(/* structType -> params */, /* new Definition */(/* new StructRef */(structType.name, structType.typeParams), "this"))).orElse(params);

                    var paramStrings = generateNodesAsValues(newParameters);

                    var removed = tuple.left.removeStatements(/* tuple -> {
                    var newParameters = tuple */.left.findStructureType(/* tuple -> {
                    var newParameters = tuple */.left).map(/* tuple -> {
                    var newParameters = tuple */.left.findStructureType(/* tuple -> {
                    var newParameters = tuple */.left), /* structType -> params */.addFirst(/* structType -> params */, /* new Definition */(/* new StructRef */(structType.name, structType.typeParams), "this"))).orElse(params);

                    var paramStrings = generateNodesAsValues(newParameters);

                    var removed = tuple.left, /* );
                    var joined = removed */.left.iterate().collect(/* tuple -> {
                    var newParameters = tuple */.left.findStructureType(/* tuple -> {
                    var newParameters = tuple */.left).map(/* tuple -> {
                    var newParameters = tuple */.left.findStructureType(/* tuple -> {
                    var newParameters = tuple */.left), /* structType -> params */.addFirst(/* structType -> params */, /* new Definition */(/* new StructRef */(structType.name, structType.typeParams), "this"))).orElse(params);

                    var paramStrings = generateNodesAsValues(newParameters);

                    var removed = tuple.left.removeStatements(/* tuple -> {
                    var newParameters = tuple */.left.findStructureType(/* tuple -> {
                    var newParameters = tuple */.left).map(/* tuple -> {
                    var newParameters = tuple */.left.findStructureType(/* tuple -> {
                    var newParameters = tuple */.left), /* structType -> params */.addFirst(/* structType -> params */, /* new Definition */(/* new StructRef */(structType.name, structType.typeParams), "this"))).orElse(params);

                    var paramStrings = generateNodesAsValues(newParameters);

                    var removed = tuple.left, /* );
                    var joined = removed */.left.iterate(), /* new Joiner */()).orElse("");

                    var generated = definition
                            .mapName(/* tuple -> {
                    var newParameters = tuple */.left.findStructureType(/* tuple -> {
                    var newParameters = tuple */.left).map(/* tuple -> {
                    var newParameters = tuple */.left.findStructureType(/* tuple -> {
                    var newParameters = tuple */.left), /* structType -> params */.addFirst(/* structType -> params */, /* new Definition */(/* new StructRef */(structType.name, structType.typeParams), "this"))).orElse(params);

                    var paramStrings = generateNodesAsValues(newParameters);

                    var removed = tuple.left.removeStatements(/* tuple -> {
                    var newParameters = tuple */.left.findStructureType(/* tuple -> {
                    var newParameters = tuple */.left).map(/* tuple -> {
                    var newParameters = tuple */.left.findStructureType(/* tuple -> {
                    var newParameters = tuple */.left), /* structType -> params */.addFirst(/* structType -> params */, /* new Definition */(/* new StructRef */(structType.name, structType.typeParams), "this"))).orElse(params);

                    var paramStrings = generateNodesAsValues(newParameters);

                    var removed = tuple.left, /* );
                    var joined = removed */.left.iterate().collect(/* tuple -> {
                    var newParameters = tuple */.left.findStructureType(/* tuple -> {
                    var newParameters = tuple */.left).map(/* tuple -> {
                    var newParameters = tuple */.left.findStructureType(/* tuple -> {
                    var newParameters = tuple */.left), /* structType -> params */.addFirst(/* structType -> params */, /* new Definition */(/* new StructRef */(structType.name, structType.typeParams), "this"))).orElse(params);

                    var paramStrings = generateNodesAsValues(newParameters);

                    var removed = tuple.left.removeStatements(/* tuple -> {
                    var newParameters = tuple */.left.findStructureType(/* tuple -> {
                    var newParameters = tuple */.left).map(/* tuple -> {
                    var newParameters = tuple */.left.findStructureType(/* tuple -> {
                    var newParameters = tuple */.left), /* structType -> params */.addFirst(/* structType -> params */, /* new Definition */(/* new StructRef */(structType.name, structType.typeParams), "this"))).orElse(params);

                    var paramStrings = generateNodesAsValues(newParameters);

                    var removed = tuple.left, /* );
                    var joined = removed */.left.iterate(), /* new Joiner */()).orElse("");

                    var generated = definition
                            , /* name -> removed */.right.findStructureType(/* name -> removed */.right).map(/* name -> removed */.right.findStructureType(/* name -> removed */.right), /* structureType -> structureType */.name + "::" + name).orElse(name)).generate().toSlice() + "(" + paramStrings + "){" + joined + tuple.right + "\n}\n";

                    return new Some<>(new Tuple<>(removed.right.addFunction(generated), ""));
                });
            });
        });
}
/* private static */ template Option<(struct CompileState, struct Definition)> compileMethodHeader(struct CompileState state, char* definition){
	auto local198 = Lists;
	return or(state, definition, Lists.of(Lists, /* 
                Main::definition */, /* 
                Main::constructor
         */));
}
/* private static */ template Option<(struct CompileState, struct Definition)> constructor(struct CompileState state, char* input){
	auto local199 = Lists;
	return or(state, input, Lists.of(Lists, /* 
                Main::constructorWithType */, /* 
                Main::constructorWithoutType
         */));
}
/* private static */ template Option<(struct CompileState, struct Definition)> constructorWithoutType(struct CompileState state, char* s){
	auto local200 = s;
	auto stripped = s.strip(s);/* if (isSymbol(stripped)) {
            return new Some<>(new Tuple<>(state, new Definition(Primitive.Auto, stripped)));
        } */
	return /* new None<> */();
}
/* private static */ template Option<(struct CompileState, struct Definition)> constructorWithType(struct CompileState state, char* input){
	auto local201 = input;
	auto local202 = (_, /*  name) -> state */.findStructureType();
	return split(input.strip(input), /* new InfixSplitter */(" ", /*  Main::lastIndexOfSlice */), (_, /*  name) -> state */.findStructureType().flatMap((_, /*  name) -> state */.findStructureType(), /* structureType -> {
            if (!structureType */.name.equals(name)) {
                return new None<>();
            }

            return new Some<>(new Tuple<>(state, new Definition(Primitive.Auto, name)));
        }));
}
/* private static */ template Option<(struct CompileState, char*)> functionSegment(struct CompileState state, char* input){
	auto local203 = input;
	auto local204 = input1;
	auto local205 = Tuple;
	auto local206 = /* slice -> statementValue */(state0, slice);
	auto local207 = /* content -> content */;
	auto local208 = Tuple;
	auto local209 = /* (state0a, input1) -> content */(state0a, input1);
	auto local210 = Lists;
	return or(state, input.strip(input), Lists.of(Lists, /* 
                Main::whitespace */, /* (state0, input1) -> suffix */(input1.strip(input1), ";", /* slice -> statementValue */(state0, slice).map(/* slice -> statementValue */(state0, slice), Tuple.mapRight(Tuple, /* slice0 -> "\n\t" + slice0 + ";" */))), /* (state0a, input1) -> content */(state0a, input1).map(/* (state0a, input1) -> content */(state0a, input1), Tuple.mapRight(Tuple, /* content -> content */.generate(/* content -> content */, /* ) */.toSlice()))));
}
/* private static */ template Option<(struct CompileState, char*)> statementValue(struct CompileState state, char* input){
	auto local211 = /* right -> right */;
	auto local212 = Tuple;
	auto local213 = /* (state1, s) -> invocation */(state1, s);
	auto local214 = Lists;
	auto local215 = Main;
	return Main.or(Main, state, input, Lists.of(Lists, /* 
                Main::returns */, /* 
                Main::initialization */, /* (state1, s) -> invocation */(state1, s).map(/* (state1, s) -> invocation */(state1, s), Tuple.mapRight(Tuple, /* right -> right */.generate(/* right -> right */, /* ) */.toSlice()))));
}
/* private static */ template Option<(struct CompileState, char*)> initialization(struct CompileState state, char* s){
	auto local216 = /* result -> result */;
	auto local217 = Tuple;
	auto local218 = /* result0 -> {
            return value */(result0.left, s2);
	auto local219 = /* (s1, s2) -> definition */(state, s1);
	return first(s, "=", /* (s1, s2) -> definition */(state, s1).flatMap(/* (s1, s2) -> definition */(state, s1), /* result0 -> {
            return value */(result0.left, s2).map(/* result0 -> {
            return value */(result0.left, s2), Tuple.mapRight(Tuple, /* result -> result */.generate(/* result -> result */, /* ) */.toSlice())).map(result1 -> {
                return new Tuple<>(result1.left, result0.right.generate0() + " = " + result1.right());
            });
        }));
}
/* private static */ template Option<(struct CompileState, struct Value)> invocation(struct CompileState state0, char* input){
	auto local220 = input;
	auto local221 = withEnd;
	return suffix(input.strip(input), ")", /* withoutEnd -> {
            return split */(withoutEnd, /* new DividingSplitter */(/* Main::foldInvocationStart */), /* (withEnd, argumentsString) -> {
                return suffix */(withEnd.strip(withEnd), "(", /* callerString -> value */(state0, callerString).flatMap(callerTuple -> {
                    return Main.parseValues(callerTuple.left, argumentsString, Main::value).map(argumentsTuple -> {
                        var caller = callerTuple.right;

                        CompileState oldState;
                        var oldArguments = argumentsTuple.right;
                        List<Value> newArguments;
                        if (caller instanceof DataAccess access) {
                            var localName = argumentsTuple.left.createLocalName();
                            var name = localName.left;
                            oldState = localName.right.addStatement("\n\tauto " + name + " = " + access.parent.generate().toSlice() + ";");
                            newArguments = oldArguments.addFirst(access.parent);
                        }
                        else {
                            oldState = argumentsTuple.left;
                            newArguments = oldArguments;
                        }

                        return new Tuple<>(oldState, new Invocation(caller, newArguments));
                    });
                }));
            });
        });
}
/* private static */ struct DivideState foldInvocationStart(struct DivideState state, char c){
	auto local222 = state;
	auto appended = state.append(state, c);/* if (c == '(') {
            var enter = appended.enter();
            return appended.isLevel() ? enter.advance() : enter;
        } *//* if (c == ')') {
            return appended.exit();
        } */
	return appended;
}
/* private static */ template Option<(struct CompileState, char*)> returns(struct CompileState state, char* input){
	auto local223 = input;
	auto local224 = /* result1 -> result1 */;
	auto local225 = Tuple;
	auto local226 = /* slice -> value */(state, slice);
	auto local227 = Tuple;
	auto local228 = /* slice -> value */(state, slice).map(/* slice -> value */(state, slice), Tuple.mapRight(Tuple, /* result1 -> result1 */.generate(/* result1 -> result1 */, /* ) */.toSlice()));
	return prefix(input.strip(input), "return ", /* slice -> value */(state, slice).map(/* slice -> value */(state, slice), Tuple.mapRight(Tuple, /* result1 -> result1 */.generate(/* result1 -> result1 */, /* ) */.toSlice())).map(/* slice -> value */(state, slice).map(/* slice -> value */(state, slice), Tuple.mapRight(Tuple, /* result1 -> result1 */.generate(/* result1 -> result1 */, /* ) */.toSlice())), Tuple.mapRight(Tuple, /* result -> "return " + result */)));
}
/* private static */ template Option<(struct CompileState, struct Value)> value(struct CompileState state, char* input){
	auto local229 = Tuple;
	auto local230 = /* (state1, input1) -> content */(state1, input1);
	auto local231 = Lists;
	auto local232 = Main;
	return Main.or(Main, state, input, Lists.of(Lists, /* 
                Main::stringNode */, /* 
                Main::invocation */, /* 
                Main::dataAccess */, /* 
                Main::symbol */, /* (state1, input1) -> content */(state1, input1).map(/* (state1, input1) -> content */(state1, input1), Tuple.mapRight(Tuple, /* right -> right */))));
}
/* private static */ template Option<(struct CompileState, struct Value)> stringNode(struct CompileState state, char* input){
	auto local233 = input;
	auto stripped = input.strip(input);/* if (stripped.length() >= 2 && stripped.startsWith("\"") && stripped.endsWith("\"")) {
            return new Some<>(new Tuple<>(state, new StringNode(stripped.substring(1, stripped.length() - 1))));
        } *//* else {
            return new None<>();
        } */
}
/* private static */ template Option<(struct CompileState, struct Value)> dataAccess(struct CompileState state, char* input){
	return split(input, /* new InfixSplitter */(".", /*  Main::lastIndexOfSlice */), /* (parent, property) -> {
            return value */(state, parent).map(tuple -> {
                return new Tuple<>(tuple.left, new DataAccess(property, tuple.right));
            });
        });
}
/* private static */ template Option<(struct CompileState, struct Definition)> definition(struct CompileState state, char* input){
	auto local234 = input;
	return split(input.strip(input), /* new InfixSplitter */(" ", /*  Main::lastIndexOfSlice */), /*  (beforeName, name) -> {
            if (!isSymbol(name)) {
                return new None<>();
            }

            return Main */.or(state, beforeName.strip(), Lists.of(
                    (instance, beforeName0) -> definitionWithTypeSeparator(instance, beforeName0, name),
                    (instance, beforeName0) -> definitionWithoutTypeSeparator(instance, beforeName0, name)
            ));
        });
}
/* private static */ int isSymbol(char* value){/* for (var i = 0; i < value.length(); i++) {
            var c = value.charAt(i);
            if (Character.isLetter(c) || c == '_' || (i != 0 && Character.isDigit(c))) {
                continue;
            }
            return false;
        } */
	return true;
}
/* private static */ template Option<(struct CompileState, struct Definition)> definitionWithoutTypeSeparator(struct CompileState state, char* type, char* name){
	auto local235 = type(state, type);
	return type(state, type).flatMap(type(state, type), /* typeTuple -> {
            return assembleDefinition(typeTuple */.left, new None<String>(), typeTuple.right, name);
        });
}
/* private static */ template Option<(struct CompileState, struct Definition)> assembleDefinition(struct CompileState state, template Option<char*> maybeBeforeType, struct Type type, char* name){
	auto local236 = name;
	auto definition = /* new Definition */(maybeBeforeType, type, name.strip(name));
	return /* new Some<> */(/* new Tuple<> */(state, definition));
}
/* private static */ template Option<(struct CompileState, struct Definition)> definitionWithTypeSeparator(struct CompileState state, char* beforeName, char* name){
	return split(beforeName, /* new TypeSeparatorSplitter */(), /* (beforeType, typeString) -> {
            return type */(state, typeString).flatMap(typeTuple -> {
                return assembleDefinition(typeTuple.left, new Some<>(beforeType), typeTuple.right, name);
            });
        });
}
/* private static */ template Option<(struct CompileState, struct Type)> type(struct CompileState state, char* input){
	auto local237 = Lists;
	auto local238 = Main;
	return Main.or(Main, state, input, Lists.of(Lists, /* 
                Main::primitive */, /* 
                Main::template */, /* 
                Main::typeParameter */, /* 
                Main::string */, /* 
                Main::structureType */, wrap(/* Main::content */)));
}
/* private static */ template Option<(struct CompileState, struct Type)> structureType(struct CompileState state, char* input){
	auto local239 = input;
	auto stripped = input.strip(input);/* if (isSymbol(stripped)) {
            return new Some<>(new Tuple<>(state, new StructRef(stripped, Lists.empty())));
        } *//* else {
            return new None<>();
        } */
}
/* private static */ template Option<(struct CompileState, struct Type)> string(struct CompileState state, char* input){/* if (input.strip().equals("String")) {
            return new Some<>(new Tuple<>(state, new Ref(Primitive.I8)));
        } *//* else {
            return new None<>();
        } */
}
/* private static */ template Option<(struct CompileState, struct Type)> typeParameter(struct CompileState state, char* input){/* if (state.findStructureType() instanceof Some(var structureType)) {
            var stripped = input.strip();
            if (structureType.typeParams.contains(stripped)) {
                return new Some<>(new Tuple<>(state, new TypeParameter(stripped)));
            }
        } */
	return /* new None<> */();
}
/* private static <S, T extends S> */ template Option<(struct CompileState, struct S)> (*wrap)(struct CompileState, char*)(template Option<(struct CompileState, struct T)> (*content)(struct CompileState, char*)){
	auto local240 = /* (state, input) -> content */;
	auto local241 = Tuple;
	auto local242 = /* (state, input) -> content */.apply(/* (state, input) -> content */, state, input);
	return /* (state, input) -> content */.apply(/* (state, input) -> content */, state, input).map(/* (state, input) -> content */.apply(/* (state, input) -> content */, state, input), Tuple.mapRight(Tuple, /* value -> value */));
}
/* private static */ template Option<(struct CompileState, struct Type)> primitive(struct CompileState state, char* input){
	auto local243 = input;
	auto stripped = input.strip(input);
	return /* switch (stripped) {
            case "boolean", "Boolean", "int", "Integer" -> new Some<>(new Tuple<>(state, Primitive */.I32));
            case "char", "Character" -> new Some<>(new Tuple<>(state, Primitive.I8));
            case "var" -> new Some<>(new Tuple<>(state, Primitive.Auto));
            case "void" -> new Some<>(new Tuple<>(state, Primitive.Void));
            default -> new None<>();
        };
}
/* private static */ template Option<(struct CompileState, struct Type)> template(struct CompileState state, char* input){
	auto local244 = input;
	return suffix(input.strip(input), ">", /* withoutEnd -> {
            return first */(withoutEnd, "<", /*  (base, argumentsString) -> {
                return parseValues(state, argumentsString, Main::type */).flatMap(argumentsTuple -> {
                    var arguments = argumentsTuple.right;

                    Type generated = switch (base) {
                        case "Function" -> new Functional(Lists.of(arguments.get(0)), arguments.get(1));
                        case "BiFunction" ->
                                new Functional(Lists.of(arguments.get(0), arguments.get(1)), arguments.get(2));
                        case "Supplier" -> new Functional(Lists.empty(), arguments.get(0));
                        case "Tuple" -> new TupleType(arguments);
                        default -> new Template(base, arguments);
                    };

                    return new Some<>(new Tuple<>(argumentsTuple.left, generated));
                });
            });
        });
}
/* private static */ template Option<int> lastIndexOfSlice(char* input, char* infix){
	auto local245 = input;
	auto index = input.lastIndexOf(input, infix);
	return /* index == -1 ? new None<Integer>() : new Some<> */(index);
}
/* private static */ template Option<(struct CompileState, char*)> prefix(char* input, char* prefix, template Option<(struct CompileState, char*)> (*mapper)(char*)){
	auto local246 = prefix;
	auto local247 = input;
	auto local248 = mapper;/* if (!input.startsWith(prefix)) {
            return new None<>();
        } */
	auto slice = input.substring(input, prefix.length(prefix));
	return mapper.apply(mapper, slice);
}
/* private static <T> */ template Option<struct T> suffix(char* input, char* suffix, template Option<struct T> (*mapper)(char*)){
	auto local249 = input;
	auto local250 = input;
	auto local251 = mapper;/* if (!input.endsWith(suffix)) {
            return new None<>();
        } */
	auto slice = input.substring(input, /* 0 */, input.length(input, /* ) - suffix */.length());
	return mapper.apply(mapper, slice);
}
/* private static */ char* generatePlaceholder(char* input){
	return "/* " + input + " */";
}
/* private static <T> */ template Option<struct T> first(char* input, char* infix, template Option<struct T> (*mapper)(char*, char*)){
	return split(input, /* new InfixSplitter */(infix, /*  Main::firstIndexOfSlice */), mapper);
}
/* private static <T> */ template Option<struct T> split(char* input, struct Splitter splitter, template Option<struct T> (*mapper)(char*, char*)){
	auto local252 = splitter;
	auto local253 = splitter.split(splitter, input);
	return splitter.split(splitter, input).flatMap(splitter.split(splitter, input), /* tuple -> {
            return mapper */.apply(tuple.left, tuple.right);
        });
}
/* private static */ template Option<int> firstIndexOfSlice(char* input, char* infix){
	auto local254 = input;
	auto index = input.indexOf(input, infix);
	return /* index == -1 ? new None<Integer>() : new Some<> */(index);
}
