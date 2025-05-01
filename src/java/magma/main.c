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
/* private */struct CompileState {
	template List<char*> structs;
	template List<char*> functions;
	template Option<struct StructurePrototype> maybeStructureType;
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
/* public */struct DataAccess {
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
	return this.generate(this).appendSlice(this.generate(this), " ").appendSlice(this.generate(this).appendSlice(this.generate(this), " "), name);
}
/* @Actual
 private static */ struct String_ Strings::from(struct Strings this, char* value){
	return /* new JavaString */(value);
}
/* @Override
 public <R> */ template Option<struct R> Some::map(struct Some<T> this, struct R (*mapper)(T)){
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
	return mapper.apply(mapper, this.value);
}
/* @Override
 public */ template Option<T> Some::filter(struct Some<T> this, template Predicate<T> predicate){
	return predicate.test(predicate, this.value) ? this : new None<>();
}
/* @Override
 public */ int Some::isPresent(struct Some<T> this){
	return true;
}
/* @Override
 public */ void Some::ifPresent(struct Some<T> this, template Consumer<T> consumer){
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
	return other.get(other);
}
/* @Override
 public */ T None::orElseGet(struct None<T> this, T (*other)()){
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
	return this.map(this, mapper).fold(this.map(this, mapper), /* new Iterator<> */(/* new EmptyHead<> */()), /*  Iterator::concat */);
}
/* public <R> */ template Iterator<struct R> Iterator::map(struct Iterator<T> this, struct R (*mapper)(T)){
	return /* new Iterator<> */((/* ) -> this */.head.next().map((/* ) -> this */.head.next(), mapper));
}
/* private */ template Iterator<T> Iterator::concat(struct Iterator<T> this, template Iterator<T> other){
	return /* new Iterator<> */((/* ) -> this */.head.next().or((/* ) -> this */.head.next(), /* other::next */));
}
/* public */ template Option<T> Iterator::next(struct Iterator<T> this){
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
	return /* new Some<> */(current.map(current, /* inner -> inner + this */.delimiter + element).orElse(current.map(current, /* inner -> inner + this */.delimiter + element), element));
}
struct public CompileState::CompileState(struct CompileState this){
	this(Lists.empty(Lists), Lists.empty(Lists), /* new None<> */());
}
/* private */ char* CompileState::generate(struct CompileState this){
	return this.getJoin(this.structs) + this.getJoin(this.getJoin(this.structs) + this, this.functions);
}
/* private */ char* CompileState::getJoin(struct CompileState this, template List<char*> lists){
	return lists.iterate(lists).collect(lists.iterate(lists), /* new Joiner */()).orElse(lists.iterate(lists).collect(lists.iterate(lists), /* new Joiner */()), "");
}
/* public */ struct CompileState CompileState::addStruct(struct CompileState this, char* struct){
	return /* new CompileState */(this.structs.addLast(this.structs, struct), this.functions, this.maybeStructureType);
}
/* public */ struct CompileState CompileState::addFunction(struct CompileState this, char* function){
	return /* new CompileState */(this.structs, this.functions.addLast(this.functions, function), this.maybeStructureType);
}
/* public */ struct CompileState CompileState::withStructType(struct CompileState this, struct StructurePrototype type){
	return /* new CompileState */(this.structs, this.functions, /* new Some<> */(type));
}
/* public */ struct CompileState CompileState::withoutStructType(struct CompileState this){
	return /* new CompileState */(this.structs, this.functions, /* new None<> */());
}
struct public DivideState::DivideState(struct DivideState this, char* input){
	this(input, /* new JavaList<> */(), /* new StringBuilder */(), /*  0 */, /*  0 */);
}
/* private */ template Option<struct DivideState> DivideState::popAndAppend(struct DivideState this){
	return this.popAndAppendToTuple(this).map(this.popAndAppendToTuple(this), /* Tuple::right */);
}
/* private */ template Option<(char, struct DivideState)> DivideState::popAndAppendToTuple(struct DivideState this){
	return this.pop(this).map(this.pop(this), /* tuple -> {
                var c = tuple */.left;
                var state = tuple.right;
                return new Tuple<>(c, state.append(c));
            });
}
/* private */ struct DivideState DivideState::append(struct DivideState this, char c){
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
	return /* tuple -> new Tuple<> */(tuple.left, mapper.apply(mapper, tuple.right));
}
/* public static <T> */ template Iterator<struct T> Iterators::fromOptions(struct Iterators this, template Option<struct T> option){
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
	return this.apply(this, input).map(this.apply(this, input), /* classIndex -> {
                var beforeKeyword = input */.substring(0, classIndex);
                var afterKeyword = input.substring(classIndex + this.length());
                return new Tuple<>(beforeKeyword, afterKeyword);
            });
}
/* private */ int InfixSplitter::length(struct InfixSplitter this){
	return this.infix.length(this.infix);
}
/* private */ template Option<int> InfixSplitter::apply(struct InfixSplitter this, char* input){
	return this.locator(this).apply(this.locator(this), input, this.infix);
}
/* @Override
 public */ template Option<(char*, char*)> TypeSeparatorSplitter::split(struct TypeSeparatorSplitter this, char* input){
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
/* private static */ struct DivideState TypeSeparatorSplitter::fold(struct TypeSeparatorSplitter this, struct DivideState state, char c){/* if (c == ' ' && state.isLevel()) {
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
	return /* new Definition */(this.maybeBeforeType, this.type, mapper.apply(mapper, this.name));
}
/* @Override
 public */ struct String_ Definition::generate(struct Definition this){
	return Strings.from(Strings, this.generate0(this));
}
/* private */ char* Definition::generate0(struct Definition this){
	auto beforeTypeString = this.maybeBeforeType.map(this.maybeBeforeType, /* beforeType -> generatePlaceholder(beforeType) + " " */).orElse(this.maybeBeforeType.map(this.maybeBeforeType, /* beforeType -> generatePlaceholder(beforeType) + " " */), "");
	return /* beforeTypeString + this */.type.generateWithName(/* beforeTypeString + this */.type, this.name).toSlice();
}
/* @Override
 public */ struct String_ Content::generate(struct Content this){
	return Strings.from(Strings, generatePlaceholder(this.input));
}
/* @Override
 public */ struct String_ Functional::generate(struct Functional this){
	return this.generateWithName(this, "");
}
/* @Override
 public */ struct String_ Functional::generateWithName(struct Functional this, char* name){
	auto joinedArguments = this.arguments(this, /* ) */.iterate().map(this.arguments(this, /* ) */.iterate(), /* type -> type */.generate(/* type -> type */, /* ) */.toSlice()).collect(this.arguments(this, /* ) */.iterate().map(this.arguments(this, /* ) */.iterate(), /* type -> type */.generate(/* type -> type */, /* ) */.toSlice()), /* new Joiner */(", ")).orElse(this.arguments(this, /* ) */.iterate().map(this.arguments(this, /* ) */.iterate(), /* type -> type */.generate(/* type -> type */, /* ) */.toSlice()).collect(this.arguments(this, /* ) */.iterate().map(this.arguments(this, /* ) */.iterate(), /* type -> type */.generate(/* type -> type */, /* ) */.toSlice()), /* new Joiner */(", ")), "");
	return this.returns.generate(this.returns).appendSlice(this.returns.generate(this.returns), " (*").appendSlice(this.returns.generate(this.returns).appendSlice(this.returns.generate(this.returns), " (*"), name).appendSlice(this.returns.generate(this.returns).appendSlice(this.returns.generate(this.returns), " (*").appendSlice(this.returns.generate(this.returns).appendSlice(this.returns.generate(this.returns), " (*"), name), ")(").appendSlice(this.returns.generate(this.returns).appendSlice(this.returns.generate(this.returns), " (*").appendSlice(this.returns.generate(this.returns).appendSlice(this.returns.generate(this.returns), " (*"), name).appendSlice(this.returns.generate(this.returns).appendSlice(this.returns.generate(this.returns), " (*").appendSlice(this.returns.generate(this.returns).appendSlice(this.returns.generate(this.returns), " (*"), name), ")("), joinedArguments).appendSlice(this.returns.generate(this.returns).appendSlice(this.returns.generate(this.returns), " (*").appendSlice(this.returns.generate(this.returns).appendSlice(this.returns.generate(this.returns), " (*"), name).appendSlice(this.returns.generate(this.returns).appendSlice(this.returns.generate(this.returns), " (*").appendSlice(this.returns.generate(this.returns).appendSlice(this.returns.generate(this.returns), " (*"), name), ")(").appendSlice(this.returns.generate(this.returns).appendSlice(this.returns.generate(this.returns), " (*").appendSlice(this.returns.generate(this.returns).appendSlice(this.returns.generate(this.returns), " (*"), name).appendSlice(this.returns.generate(this.returns).appendSlice(this.returns.generate(this.returns), " (*").appendSlice(this.returns.generate(this.returns).appendSlice(this.returns.generate(this.returns), " (*"), name), ")("), joinedArguments), ")");
}
/* @Override
 public */ struct String_ Template::generate(struct Template this){
	return Strings.from(Strings, this.generate0(this));
}
/* private */ char* Template::generate0(struct Template this){
	auto generatedTuple = this.arguments(this, /* ) */.iterate().map(this.arguments(this, /* ) */.iterate(), /* type -> type */.generate(/* type -> type */, /* ) */.toSlice()).collect(this.arguments(this, /* ) */.iterate().map(this.arguments(this, /* ) */.iterate(), /* type -> type */.generate(/* type -> type */, /* ) */.toSlice()), /* new Joiner */(", ")).orElse(this.arguments(this, /* ) */.iterate().map(this.arguments(this, /* ) */.iterate(), /* type -> type */.generate(/* type -> type */, /* ) */.toSlice()).collect(this.arguments(this, /* ) */.iterate().map(this.arguments(this, /* ) */.iterate(), /* type -> type */.generate(/* type -> type */, /* ) */.toSlice()), /* new Joiner */(", ")), "");
	return "template " + this.base() + "<" + generatedTuple + ">";
}
/* @Override
 public */ template List<T> ListCollector::createInitial(struct ListCollector<T> this){
	return Lists.empty(Lists);
}
/* @Override
 public */ template List<T> ListCollector::fold(struct ListCollector<T> this, template List<T> current, T element){
	return current.addLast(current, element);
}
/* @Override
 public */ struct String_ TypeParameter::generate(struct TypeParameter this){
	return Strings.from(Strings, this.generate0(this));
}
/* private */ char* TypeParameter::generate0(struct TypeParameter this){
	return this.value;
}
/* @Override
 public */ struct String_ Ref::generate(struct Ref this){
	return Strings.from(Strings, this.generate0(this));
}
/* private */ char* Ref::generate0(struct Ref this){
	return this.type.generate(this.type).toSlice() + "*";
}
/* @Override
 public */ struct String_ TupleType::generate(struct TupleType this){
	return Strings.from(Strings, this.generate0(this));
}
/* private */ char* TupleType::generate0(struct TupleType this){
	return "(" + generateNodesAsValues(this.arguments) + ")";
}
/* @Override
 public */ struct String_ StructRef::generate(struct StructRef this){
	auto typeParamString = this.typeParams.iterate(this.typeParams).collect(this.typeParams.iterate(this.typeParams), /* new Joiner */(", ")).map(this.typeParams.iterate(this.typeParams).collect(this.typeParams.iterate(this.typeParams), /* new Joiner */(", ")), /* inner -> "<" + inner + ">" */).orElse(this.typeParams.iterate(this.typeParams).collect(this.typeParams.iterate(this.typeParams), /* new Joiner */(", ")).map(this.typeParams.iterate(this.typeParams).collect(this.typeParams.iterate(this.typeParams), /* new Joiner */(", ")), /* inner -> "<" + inner + ">" */), "");
	return Strings.from(Strings, "struct ").appendSlice(Strings.from(Strings, "struct "), this.input).appendSlice(Strings.from(Strings, "struct ").appendSlice(Strings.from(Strings, "struct "), this.input), typeParamString);
}
/* @Override
 public */ template Option<(char*, char*)> DividingSplitter::split(struct DividingSplitter this, char* input){
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
	return Strings.from(Strings, this.value);
}
/* @Override
 public */ struct String_ StringNode::generate(struct StringNode this){
	return Strings.from(Strings, "\"").appendSlice(Strings.from(Strings, "\""), this.value).appendSlice(Strings.from(Strings, "\"").appendSlice(Strings.from(Strings, "\""), this.value), "\"");
}
/* @Override
 public */ struct String_ Invocation::generate(struct Invocation this){
	auto joined = this.arguments(this, /* ) */.iterate().map(this.arguments(this, /* ) */.iterate(), /* Node::generate */).map(this.arguments(this, /* ) */.iterate().map(this.arguments(this, /* ) */.iterate(), /* Node::generate */), /* String_::toSlice */).collect(this.arguments(this, /* ) */.iterate().map(this.arguments(this, /* ) */.iterate(), /* Node::generate */).map(this.arguments(this, /* ) */.iterate().map(this.arguments(this, /* ) */.iterate(), /* Node::generate */), /* String_::toSlice */), /* new Joiner */(", ")).orElse(this.arguments(this, /* ) */.iterate().map(this.arguments(this, /* ) */.iterate(), /* Node::generate */).map(this.arguments(this, /* ) */.iterate().map(this.arguments(this, /* ) */.iterate(), /* Node::generate */), /* String_::toSlice */).collect(this.arguments(this, /* ) */.iterate().map(this.arguments(this, /* ) */.iterate(), /* Node::generate */).map(this.arguments(this, /* ) */.iterate().map(this.arguments(this, /* ) */.iterate(), /* Node::generate */), /* String_::toSlice */), /* new Joiner */(", ")), "");
	return Strings.from(Strings, this.caller(this, /* ) */.generate().toSlice() + "(" + joined + ")");
}
/* @Override
 public */ struct String_ DataAccess::generate(struct DataAccess this){
	return Strings.from(Strings, this.parent(this, /* ) */.generate().toSlice(this.parent(this, /* ) */.generate(), /* ) + " */." + this.property());
}
auto Primitive::Primitive(struct Primitive this, char* value){/* this.value = value; */
}
/* @Override
 public */ struct String_ Primitive::generate(struct Primitive this){
	return Strings.from(Strings, this.generate0(this));
}
/* private */ char* Primitive::generate0(struct Primitive this){
	return this.value;
}
/* public static */ void main(){
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
	auto state = /* new CompileState */();
	auto tuple = compileAll(state, input, /*  Main::compileRootSegment */).orElse(compileAll(state, input, /*  Main::compileRootSegment */), /* new Tuple<> */(state, ""));
	return tuple.right + tuple.left.generate(tuple.right + tuple.left);
}
/* private static */ template Option<(struct CompileState, char*)> compileAll(struct CompileState initial, char* input, template Option<(struct CompileState, char*)> (*mapper)(struct CompileState, char*)){
	return all(initial, input, /*  Main::foldStatementChar */, mapper, /*  Main::mergeStatements */);
}
/* private static */ template Option<(struct CompileState, char*)> all(struct CompileState initial, char* input, struct DivideState (*folder)(struct DivideState, char), template Option<(struct CompileState, char*)> (*mapper)(struct CompileState, char*), struct StringBuilder (*merger)(struct StringBuilder, char*)){
	return parseAll(initial, input, folder, mapper).map(parseAll(initial, input, folder, mapper), /* tuple -> new Tuple<> */(tuple.left, generateAll(merger, tuple.right)));
}
/* private static */ struct StringBuilder mergeStatements(struct StringBuilder output, char* right){
	return output.append(output, right);
}
/* private static */ struct DivideState foldStatementChar(struct DivideState state, char c){
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
	return or(state, input, Lists.of(Lists, /* 
                Main::whitespace */, /* 
                Main::compileNamespaced */, parseClass(), /* (state1, input1) -> content */(state1, input1).map(/* (state1, input1) -> content */(state1, input1), Tuple.mapRight(Tuple, /* content -> content */.generate(/* content -> content */, /* ) */.toSlice()))));
}
/* private static */ template Option<(struct CompileState, char*)> (*parseClass)(struct CompileState, char*)(){
	return structure("class", "class ");
}
/* private static */ template Option<(struct CompileState, char*)> (*structure)(struct CompileState, char*)(char* type, char* infix){
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
	return first(beforeContent, " permits ", /* (beforePermits, variantsString) -> {
            return parseValues */(state, variantsString, /* (state1, value) -> symbol */(state1, value).map(/* (state1, value) -> symbol */(state1, value), Tuple.mapRight(Tuple, /* (Value node) -> node */.generate(/* (Value node) -> node */, /* ) */.toSlice()))).flatMap(params -> {
                return structureWithoutVariants(type, params.left, beforeKeyword, beforePermits, params.right, withEnd);
            });
        });
}
/* private static */ template Option<(struct CompileState, struct Value)> symbol(struct CompileState state, char* value){
	auto stripped = value.strip(value);/* if (isSymbol(stripped)) {
            return new Some<>(new Tuple<>(state, new Symbol(stripped)));
        } *//* else {
            return new None<>();
        } */
}
/* private static */ template Option<(struct CompileState, char*)> structureWithoutVariants(char* type, struct CompileState state, char* beforeKeyword, char* beforeContent, template List<char*> variants, char* withEnd){
	return or(state, beforeContent, Lists.of(Lists, /* (state0, s) -> structureWithImplements */(type, state0, beforeKeyword, s, variants, withEnd), /* (state0, s) -> structureWithoutImplements */(type, state0, beforeKeyword, s, variants, withEnd)));
}
/* private static */ template Option<(struct CompileState, char*)> structureWithImplements(char* type, struct CompileState state0, char* beforeKeyword, char* s, template List<char*> variants, char* withEnd){
	return first(s, " implements ", /* (s1, s2) -> structureWithoutImplements */(type, state0, beforeKeyword, s1, variants, withEnd));
}
/* private static */ template Option<(struct CompileState, char*)> structureWithoutImplements(char* type, struct CompileState state, char* beforeKeyword, char* beforeContent, template List<char*> variants, char* withEnd){
	return or(state, beforeContent, Lists.of(Lists, /* (state1, s) -> structureWithExtends */(type, beforeKeyword, beforeContent, variants, withEnd, state1), /* (state2, s) -> structureWithoutExtends */(type, state2, beforeKeyword, s, variants, withEnd)));
}
/* private static */ template Option<(struct CompileState, char*)> structureWithExtends(char* type, char* beforeKeyword, char* beforeContent, template List<char*> variants, char* withEnd, struct CompileState state1){
	return first(beforeContent, " extends ", /* (s1, s2) -> structureWithoutExtends */(type, state1, beforeKeyword, s1, variants, withEnd));
}
/* private static */ template Option<(struct CompileState, char*)> structureWithoutExtends(char* type, struct CompileState state, char* beforeKeyword, char* beforeContent, template List<char*> variants, char* withEnd){
	return or(state, beforeContent, Lists.of(Lists, /* (instance, before) -> structureWithParams */(type, instance, beforeKeyword, before, variants, withEnd), /* (instance, before) -> structureWithoutParams */(type, instance, beforeKeyword, before.strip(before), Lists.empty(Lists), variants, withEnd)));
}
/* private static */ template Option<(struct CompileState, char*)> structureWithParams(char* type, struct CompileState instance, char* beforeKeyword, char* beforeContent, template List<char*> variants, char* withEnd){
	return suffix(beforeContent.strip(beforeContent), ")", /* withoutEnd -> first */(withoutEnd, "(", /* (name, paramString) -> {
            return parseAll */(instance, paramString, /*  Main::foldValueChar */, /*  Main::parameter */).flatMap(params -> {
                        return structureWithoutParams(type, params.left, beforeKeyword, name, params.right, variants, withEnd);
                    });
        }));
}
/* private static */ template Option<(struct CompileState, struct Parameter)> parameter(struct CompileState instance, char* paramString){
	return Main.or(Main, instance, paramString, Lists.of(Lists, wrap(/* Main::definition */), wrap(/* Main::content */)));
}
/* private static */ template Option<(struct CompileState, char*)> structureWithoutParams(char* type, struct CompileState state, char* beforeKeyword, char* beforeParams, template List<struct Parameter> params, template List<char*> variants, char* withEnd){
	return or(state, beforeParams, Lists.of(Lists, /* (state0, beforeParams0) -> structureWithTypeParams */(type, state0, beforeParams0, beforeKeyword, params, variants, withEnd), /* (state0, name) -> structureWithName */(type, state0, beforeKeyword, name, Lists.empty(Lists), params, variants, withEnd)));
}
/* private static */ template Option<(struct CompileState, char*)> structureWithTypeParams(char* type, struct CompileState state, char* beforeParams0, char* beforeKeyword, template List<struct Parameter> params, template List<char*> variants, char* withEnd){
	return suffix(beforeParams0.strip(beforeParams0), ">", /* withoutEnd -> {
            return first */(withoutEnd, "<", /* (name, typeParamString) -> {
                return parseValues */(state, typeParamString, /* (state1, value) -> symbol */(state1, value).map(/* (state1, value) -> symbol */(state1, value), Tuple.mapRight(Tuple, (/* Value node) -> node */.generate().toSlice()))).flatMap(values -> {
                    return structureWithName(type, values.left, beforeKeyword, name, values.right, params, variants, withEnd);
                });
            });
        });
}
/* private static */ template Option<(struct CompileState, char*)> structureWithName(char* type, struct CompileState state, char* beforeKeyword, char* name, template List<char*> typeParams, template List<struct Parameter> params, template List<char*> variants, char* withEnd){
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
	auto paramsString = params.iterate(params).map(params.iterate(params), /* t -> t */.generate(/* t -> t */, /* ) */.toSlice()).map(params.iterate(params).map(params.iterate(params), /* t -> t */.generate(/* t -> t */, /* ) */.toSlice()), /* value -> "\n\t" + value + ";" */).collect(params.iterate(params).map(params.iterate(params), /* t -> t */.generate(/* t -> t */, /* ) */.toSlice()).map(params.iterate(params).map(params.iterate(params), /* t -> t */.generate(/* t -> t */, /* ) */.toSlice()), /* value -> "\n\t" + value + ";" */), /* new Joiner */()).orElse(params.iterate(params).map(params.iterate(params), /* t -> t */.generate(/* t -> t */, /* ) */.toSlice()).map(params.iterate(params).map(params.iterate(params), /* t -> t */.generate(/* t -> t */, /* ) */.toSlice()), /* value -> "\n\t" + value + ";" */).collect(params.iterate(params).map(params.iterate(params), /* t -> t */.generate(/* t -> t */, /* ) */.toSlice()).map(params.iterate(params).map(params.iterate(params), /* t -> t */.generate(/* t -> t */, /* ) */.toSlice()), /* value -> "\n\t" + value + ";" */), /* new Joiner */()), "");
	auto generatedStruct = /*  generatePlaceholder(beforeKeyword */.strip()) + "struct " + name + typeParamString + " {" + paramsString + content + "\n};\n";
	return /* new Tuple<CompileState, String> */(state.addStruct(state, generatedStruct), "");
}
/* private static */ char* generateTypeParams(template List<char*> typeParams){
	return typeParams.isEmpty(typeParams, /* ) ? "" : "<" + typeParams */.iterate().collect(typeParams.isEmpty(typeParams, /* ) ? "" : "<" + typeParams */.iterate(), /* new Joiner */(", ")).orElse("") + ">";
}
/* private static <T> */ template Option<(struct CompileState, struct T)> or(struct CompileState state, char* input, template List<template Option<(struct CompileState, struct T)> (*)(struct CompileState, char*)> actions){
	return actions.iterate(actions).map(actions.iterate(actions), /* action -> action */.apply(/* action -> action */, state, input)).flatMap(actions.iterate(actions).map(actions.iterate(actions), /* action -> action */.apply(/* action -> action */, state, input)), /* Iterators::fromOptions)
                 */.next();
}
/* private static */ template Option<(struct CompileState, char*)> compileNamespaced(struct CompileState state, char* input){/* if (input.strip().startsWith("package ") || input.strip().startsWith("import ")) {
            return new Some<>(new Tuple<CompileState, String>(state, ""));
        } */
	return /* new None<> */();
}
/* private static */ template Option<(struct CompileState, char*)> structSegment(struct CompileState state, char* input){
	return or(state, input, Lists.of(Lists, /* 
                Main::whitespace */, /* 
                Main::annotation */, structure("enum", "enum "), parseClass(), structure("record", "record "), structure("interface", "interface "), /* 
                Main::method */, /* 
                Main::definitionStatement */, /* (state1, input1) -> content */(state1, input1).map(/* (state1, input1) -> content */(state1, input1), Tuple.mapRight(Tuple, /* content -> content */.generate(/* content -> content */, /* ) */.toSlice()))));
}
/* private static */ template Option<(struct CompileState, char*)> definitionStatement(struct CompileState state, char* input){
	return suffix(input.strip(input), ";", /* withoutEnd -> definition */(state, withoutEnd).map(/* withoutEnd -> definition */(state, withoutEnd), Tuple.mapRight(Tuple, /* definition -> definition */.generate(/* definition -> definition */, /* ) */.toSlice())).map(/* withoutEnd -> definition */(state, withoutEnd).map(/* withoutEnd -> definition */(state, withoutEnd), Tuple.mapRight(Tuple, /* definition -> definition */.generate(/* definition -> definition */, /* ) */.toSlice())), /* value -> {
            var generated = "\n\t" + value */.right + ";";
            return new Tuple<>(value.left, generated);
        }));
}
/* private static */ template Option<(struct CompileState, struct Content)> content(struct CompileState state, char* input){
	return /* new Some<> */(/* new Tuple<CompileState, Content> */(state, /* new Content */(input)));
}
/* private static */ template Option<(struct CompileState, char*)> whitespace(struct CompileState state, char* input){/* if (input.isBlank()) {
            return new Some<>(new Tuple<CompileState, String>(state, ""));
        } */
	return /* new None<> */();
}
/* private static */ template Option<(struct CompileState, char*)> method(struct CompileState state, char* input){
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
	return divide(input, /*  folder)
                 */.iterate().<Option<Tuple<CompileState, List<T>>>>fold(divide(input, /*  folder)
                 */.iterate(), /* new Some<> */(/* new Tuple<CompileState, List<T>> */(initial, Lists.empty(Lists))), /* (maybeCurrent, segment) -> maybeCurrent */.flatMap(/* (maybeCurrent, segment) -> maybeCurrent */, /* state -> foldElement */(state, segment, mapper)));
}
/* private static <T> */ template Option<(struct CompileState, template List<struct T>)> foldElement((struct CompileState, template List<struct T>) state, char* segment, template Option<(struct CompileState, struct T)> (*mapper)(struct CompileState, char*)){
	auto oldState = state.left;
	auto oldCache = state.right;
	return mapper.apply(mapper, oldState, segment).map(mapper.apply(mapper, oldState, segment), /* result -> {
            var newState = result */.left;
            var newElement = result.right;
            return new Tuple<>(newState, oldCache.addLast(newElement));
        });
}
/* private static */ template List<char*> divide(char* input, struct DivideState (*folder)(struct DivideState, char)){
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
/* private static */ template Option<struct DivideState> foldDoubleQuotes(struct DivideState state, char c){/* if (c != '\"') {
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
/* private static */ template Option<struct DivideState> foldSingleQuotes(struct DivideState state, char c){/* if (c != '\'') {
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
/* private static */ struct DivideState foldValueChar(struct DivideState state, char c){/* if (c == ',' && state.isLevel()) {
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
	return params.iterate(params).map(params.iterate(params), /* t -> t */.generate(/* t -> t */, /* ) */.toSlice()).collect(params.iterate(params).map(params.iterate(params), /* t -> t */.generate(/* t -> t */, /* ) */.toSlice()), /* new Joiner */(", ")).orElse(params.iterate(params).map(params.iterate(params), /* t -> t */.generate(/* t -> t */, /* ) */.toSlice()).collect(params.iterate(params).map(params.iterate(params), /* t -> t */.generate(/* t -> t */, /* ) */.toSlice()), /* new Joiner */(", ")), "");
}
/* private static */ char* generateAll(struct StringBuilder (*merger)(struct StringBuilder, char*), template List<char*> right){
	return right.iterate(right).fold(right.iterate(right), /* new StringBuilder */(), /*  merger) */.toString();
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
	return /* new Some<> */(/* new Tuple<CompileState, String> */(state, generated));
}
/* private static */ template Option<(struct CompileState, char*)> methodWithContent(struct CompileState state, struct Definition definition, template List<struct Definition> params, char* withBraces){
	return prefix(withBraces.strip(withBraces), "{", /* withoutStart1 -> {
            return suffix */(withoutStart1, "}", /* content -> {
                return compileAll */(state, content, /*  Main::functionSegment */).flatMap(/* content -> {
                return compileAll */(state, content, /*  Main::functionSegment */), /* tuple -> {
                    var newParameters = state */.maybeStructureType
                            .map(/* tuple -> {
                    var newParameters = state */.maybeStructureType
                            , /* structType -> params */.addFirst(/* structType -> params */, /* new Definition */(/* new StructRef */(structType.name, structType.typeParams), "this"))).orElse(params);
                    var paramStrings = generateNodesAsValues(newParameters);

                    var generated = definition
                            .mapName(/* tuple -> {
                    var newParameters = state */.maybeStructureType
                            .map(/* tuple -> {
                    var newParameters = state */.maybeStructureType
                            , /* structType -> params */.addFirst(/* structType -> params */, /* new Definition */(/* new StructRef */(structType.name, structType.typeParams), "this"))).orElse(params);
                    var paramStrings = generateNodesAsValues(newParameters);

                    var generated = definition
                            , /* name -> state */.maybeStructureType.map(/* name -> state */.maybeStructureType, /* structureType -> structureType */.name + "::" + name).orElse(name)).generate().toSlice() + "(" + paramStrings + "){" + tuple.right + "\n}\n";
                    return new Some<>(new Tuple<>(state.addFunction(generated), ""));
                });
            });
        });
}
/* private static */ template Option<(struct CompileState, struct Definition)> compileMethodHeader(struct CompileState state, char* definition){
	return or(state, definition, Lists.of(Lists, /* 
                Main::definition */, /* 
                Main::constructor
         */));
}
/* private static */ template Option<(struct CompileState, struct Definition)> constructor(struct CompileState state, char* input){
	return or(state, input, Lists.of(Lists, /* 
                Main::constructorWithType */, /* 
                Main::constructorWithoutType
         */));
}
/* private static */ template Option<(struct CompileState, struct Definition)> constructorWithoutType(struct CompileState state, char* s){
	auto stripped = s.strip(s);/* if (isSymbol(stripped)) {
            return new Some<>(new Tuple<>(state, new Definition(Primitive.Auto, stripped)));
        } */
	return /* new None<> */();
}
/* private static */ template Option<(struct CompileState, struct Definition)> constructorWithType(struct CompileState state, char* input){
	return split(input.strip(input), /* new InfixSplitter */(" ", /*  Main::lastIndexOfSlice */), /* (_, name) -> state */.maybeStructureType.flatMap(/* (_, name) -> state */.maybeStructureType, /* structureType -> {
            if (!structureType */.name.equals(name)) {
                return new None<>();
            }

            return new Some<>(new Tuple<>(state, new Definition(Primitive.Auto, name)));
        }));
}
/* private static */ template Option<(struct CompileState, char*)> functionSegment(struct CompileState state, char* input){
	return or(state, input.strip(input), Lists.of(Lists, /* 
                Main::whitespace */, /* (state0, input1) -> suffix */(input1.strip(input1), ";", /* slice -> statementValue */(state0, slice).map(/* slice -> statementValue */(state0, slice), Tuple.mapRight(Tuple, /* slice0 -> "\n\t" + slice0 + ";" */))), /* (state0a, input1) -> content */(state0a, input1).map(/* (state0a, input1) -> content */(state0a, input1), Tuple.mapRight(Tuple, /* content -> content */.generate(/* content -> content */, /* ) */.toSlice()))));
}
/* private static */ template Option<(struct CompileState, char*)> statementValue(struct CompileState state, char* input){
	return Main.or(Main, state, input, Lists.of(Lists, /* 
                Main::returns */, /* 
                Main::initialization */, /* (state1, s) -> invocation */(state1, s).map(/* (state1, s) -> invocation */(state1, s), Tuple.mapRight(Tuple, /* right -> right */.generate(/* right -> right */, /* ) */.toSlice()))));
}
/* private static */ template Option<(struct CompileState, char*)> initialization(struct CompileState state, char* s){
	return first(s, "=", /* (s1, s2) -> definition */(state, s1).flatMap(/* (s1, s2) -> definition */(state, s1), /* result0 -> {
            return value */(result0.left, s2).map(/* result0 -> {
            return value */(result0.left, s2), Tuple.mapRight(Tuple, /* result -> result */.generate(/* result -> result */, /* ) */.toSlice())).map(result1 -> {
                return new Tuple<>(result1.left, result0.right.generate0() + " = " + result1.right());
            });
        }));
}
/* private static */ template Option<(struct CompileState, struct Value)> invocation(struct CompileState state0, char* input){
	return suffix(input.strip(input), ")", /* withoutEnd -> {
            return split */(withoutEnd, /* new DividingSplitter */(/* Main::foldInvocationStart */), /* (withEnd, argumentsString) -> {
                return suffix */(withEnd.strip(withEnd), "(", /*  callerString -> value(state0, callerString */).flatMap(callerTuple -> {
                    return Main.parseValues(callerTuple.left, argumentsString, Main::value).map(argumentsTuple -> {
                        var caller = callerTuple.right;
                        List<Value> newArguments;
                        if (caller instanceof DataAccess access) {
                            newArguments = argumentsTuple.right.addFirst(access.parent);
                        }
                        else {
                            newArguments = argumentsTuple.right;
                        }

                        return new Tuple<>(argumentsTuple.left, new Invocation(caller, newArguments));
                    });
                }));
            });
        });
}
/* private static */ struct DivideState foldInvocationStart(struct DivideState state, char c){
	auto appended = state.append(state, c);/* if (c == '(') {
            var enter = appended.enter();
            return appended.isLevel() ? enter.advance() : enter;
        } *//* if (c == ')') {
            return appended.exit();
        } */
	return appended;
}
/* private static */ template Option<(struct CompileState, char*)> returns(struct CompileState state, char* input){
	return prefix(input.strip(input), "return ", /* slice -> value */(state, slice).map(/* slice -> value */(state, slice), Tuple.mapRight(Tuple, /* result1 -> result1 */.generate(/* result1 -> result1 */, /* ) */.toSlice())).map(/* slice -> value */(state, slice).map(/* slice -> value */(state, slice), Tuple.mapRight(Tuple, /* result1 -> result1 */.generate(/* result1 -> result1 */, /* ) */.toSlice())), Tuple.mapRight(Tuple, /* result -> "return " + result */)));
}
/* private static */ template Option<(struct CompileState, struct Value)> value(struct CompileState state, char* input){
	return Main.or(Main, state, input, Lists.of(Lists, /* 
                Main::stringNode */, /* 
                Main::invocation */, /* 
                Main::dataAccess */, /* 
                Main::symbol */, /* (state1, input1) -> content */(state1, input1).map(/* (state1, input1) -> content */(state1, input1), Tuple.mapRight(Tuple, /* right -> right */))));
}
/* private static */ template Option<(struct CompileState, struct Value)> stringNode(struct CompileState state, char* input){
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
	return type(state, type).flatMap(type(state, type), /* typeTuple -> {
            return assemble(typeTuple */.left, new None<String>(), typeTuple.right, name);
        });
}
/* private static */ template Option<(struct CompileState, struct Definition)> assemble(struct CompileState state, template Option<char*> maybeBeforeType, struct Type type, char* name){
	auto definition = /* new Definition */(maybeBeforeType, type, name.strip(name));
	return /* new Some<> */(/* new Tuple<> */(state, definition));
}
/* private static */ template Option<(struct CompileState, struct Definition)> definitionWithTypeSeparator(struct CompileState state, char* beforeName, char* name){
	return split(beforeName, /* new TypeSeparatorSplitter */(), /* (beforeType, typeString) -> {
            return type */(state, typeString).flatMap(typeTuple -> {
                return assemble(typeTuple.left, new Some<>(beforeType), typeTuple.right, name);
            });
        });
}
/* private static */ template Option<(struct CompileState, struct Type)> type(struct CompileState state, char* input){
	return Main.or(Main, state, input, Lists.of(Lists, /* 
                Main::primitive */, /* 
                Main::template */, /* 
                Main::typeParameter */, /* 
                Main::string */, /* 
                Main::structureType */, wrap(/* Main::content */)));
}
/* private static */ template Option<(struct CompileState, struct Type)> structureType(struct CompileState state, char* input){
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
/* private static */ template Option<(struct CompileState, struct Type)> typeParameter(struct CompileState state, char* input){/* if (state.maybeStructureType instanceof Some(var structureType)) {
            var stripped = input.strip();
            if (structureType.typeParams.contains(stripped)) {
                return new Some<>(new Tuple<>(state, new TypeParameter(stripped)));
            }
        } */
	return /* new None<> */();
}
/* private static <S, T extends S> */ template Option<(struct CompileState, struct S)> (*wrap)(struct CompileState, char*)(template Option<(struct CompileState, struct T)> (*content)(struct CompileState, char*)){
	return /* (state, input) -> content */.apply(/* (state, input) -> content */, state, input).map(/* (state, input) -> content */.apply(/* (state, input) -> content */, state, input), Tuple.mapRight(Tuple, /* value -> value */));
}
/* private static */ template Option<(struct CompileState, struct Type)> primitive(struct CompileState state, char* input){
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
	auto index = input.lastIndexOf(input, infix);
	return /* index == -1 ? new None<Integer>() : new Some<> */(index);
}
/* private static */ template Option<(struct CompileState, char*)> prefix(char* input, char* prefix, template Option<(struct CompileState, char*)> (*mapper)(char*)){/* if (!input.startsWith(prefix)) {
            return new None<>();
        } */
	auto slice = input.substring(input, prefix.length(prefix));
	return mapper.apply(mapper, slice);
}
/* private static <T> */ template Option<struct T> suffix(char* input, char* suffix, template Option<struct T> (*mapper)(char*)){/* if (!input.endsWith(suffix)) {
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
	return splitter.split(splitter, input).flatMap(splitter.split(splitter, input), /* tuple -> {
            return mapper */.apply(tuple.left, tuple.right);
        });
}
/* private static */ template Option<int> firstIndexOfSlice(char* input, char* infix){
	auto index = input.indexOf(input, infix);
	return /* index == -1 ? new None<Integer>() : new Some<> */(index);
}
