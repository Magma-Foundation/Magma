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
	template List<T> (*mapLast)(S, T (*)(T));
	template Iterator<T> (*iterateReversed)(S);
	template Option<T> (*last)(S);
	template List<T> (*setLast)(S, T);
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
	template Option<struct StructurePrototype> (*findStructureType)(S);
	(char*, struct CompileState) (*createLocalName)(S);
	struct CompileState (*addStatement)(S, char*);
	(template List<char*>, struct CompileState) (*removeStatements)(S);
	struct CompileState (*enter)(S);
	struct CompileState (*exit)(S);
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
/* private */struct Frame {
	template Option<struct StructurePrototype> prototype;
	int counter;
};
/* private */struct ImmutableCompileState {
	template List<char*> structs;
	template List<char*> functions;
	template List<char*> statements;
	template List<struct Frame> frames;
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
	template List<char*> annotations;
	template Option<char*> maybeBeforeType;
	template List<char*> typeParams;
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
	struct Value parent;
	char* property;
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
	auto local0 = this.generate(this);
	auto local1 = local0.appendSlice(local0, " ");
	return local1.appendSlice(local1, name);
}
/* private static */ struct String_ Strings::from(struct Strings this, char* value);
/* public */ template Option<struct R> Some::map<R>(struct Some<T> this, struct R (*mapper)(T)){
	return /* new Some<> */(mapper.apply(mapper, this.value));
}
/* public */ int Some::isEmpty(struct Some<T> this){
	return false;
}
/* public */ T Some::orElse(struct Some<T> this, T other){
	return this.value;
}
/* public */ template Option<T> Some::or(struct Some<T> this, template Option<T> (*other)()){
	return this;
}
/* public */ T Some::orElseGet(struct Some<T> this, T (*other)()){
	return this.value;
}
/* public */ template Option<struct R> Some::flatMap<R>(struct Some<T> this, template Option<struct R> (*mapper)(T)){
	return mapper.apply(mapper, this.value);
}
/* public */ template Option<T> Some::filter(struct Some<T> this, template Predicate<T> predicate){
	return predicate.test(predicate, this.value) ? this : new None<>();
}
/* public */ int Some::isPresent(struct Some<T> this){
	return true;
}
/* public */ void Some::ifPresent(struct Some<T> this, template Consumer<T> consumer){
	consumer.accept(consumer, this.value);
}
/* public */ template Option<struct R> None::map<R>(struct None<T> this, struct R (*mapper)(T)){
	return /* new None<> */();
}
/* public */ int None::isEmpty(struct None<T> this){
	return true;
}
/* public */ T None::orElse(struct None<T> this, T other){
	return other;
}
/* public */ template Option<T> None::or(struct None<T> this, template Option<T> (*other)()){
	return other.get(other);
}
/* public */ T None::orElseGet(struct None<T> this, T (*other)()){
	return other.get(other);
}
/* public */ template Option<struct R> None::flatMap<R>(struct None<T> this, template Option<struct R> (*mapper)(T)){
	return /* new None<> */();
}
/* public */ template Option<T> None::filter(struct None<T> this, template Predicate<T> predicate){
	return /* new None<> */();
}
/* public */ int None::isPresent(struct None<T> this){
	return false;
}
/* public */ void None::ifPresent(struct None<T> this, template Consumer<T> consumer){
}
/* public */ struct C Iterator::collect<C>(struct Iterator<T> this, template Collector<T, struct C> collector){
	return this.fold(this, collector.createInitial(collector), /*  collector::fold */);
}
/* private */ struct C Iterator::fold<C>(struct Iterator<T> this, struct C initial, struct C (*folder)(struct C, T)){
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
/* public */ template Iterator<struct R> Iterator::flatMap<R>(struct Iterator<T> this, template Iterator<struct R> (*mapper)(T)){
	auto local0 = this.map(this, mapper);
	return local0.fold(local0, /* new Iterator<> */(/* new EmptyHead<> */()), /*  Iterator::concat */);
}
/* public */ template Iterator<struct R> Iterator::map<R>(struct Iterator<T> this, struct R (*mapper)(T)){
	auto local1 = (/* ) -> this */.head.next();
	return /* new Iterator<> */(local1.map(local1, mapper));
}
/* private */ template Iterator<T> Iterator::concat(struct Iterator<T> this, template Iterator<T> other){
	auto local2 = (/* ) -> this */.head.next();
	return /* new Iterator<> */(local2.or(local2, /* other::next */));
}
/* public */ template Option<T> Iterator::next(struct Iterator<T> this){
	auto local3 = this.head;
	return local3.next(local3);
}
struct private RangeHead::RangeHead(struct RangeHead this, int length){/* this.length = length; *//* this.counter = 0; */
}
/* public */ template Option<int> RangeHead::next(struct RangeHead this){/* if (this.counter >= this.length) {
                return new None<>();
            } */
	auto value = this.counter;/* this.counter++; */
	return /* new Some<> */(value);
}
/* public static */ template List<struct T> Lists::of<T>(struct Lists this, /* T... */ elements){
	return /* new JavaList<> */(Arrays.asList(Arrays, elements));
}
/* public static */ template List<struct T> Lists::empty<T>(struct Lists this){
	return /* new JavaList<> */(/* new ArrayList<> */());
}
/* public */ template Option<T> EmptyHead::next(struct EmptyHead<T> this){
	return /* new None<> */();
}
struct public Joiner::Joiner(struct Joiner this){
	this("");
}
/* public */ template Option<char*> Joiner::createInitial(struct Joiner this){
	return /* new None<> */();
}
/* public */ template Option<char*> Joiner::fold(struct Joiner this, template Option<char*> current, char* element){
	auto local0 = current.map(current, /* inner -> inner + this */.delimiter + element);
	return /* new Some<> */(local0.orElse(local0, element));
}
struct public Frame::Frame(struct Frame this){
	this(/* new None<> */(), /*  0 */);
}
/* public */ struct Frame Frame::withProto(struct Frame this, struct StructurePrototype type){
	return /* new Frame */(/* new Some<> */(type), this.counter);
}
/* public */ (char*, struct Frame) Frame::createName(struct Frame this){
	return /* new Tuple<> */(/* "local" + this */.counter, /* new Frame */(this.prototype, this.counter + 1));
}
struct public ImmutableCompileState::ImmutableCompileState(struct ImmutableCompileState this){
	this(Lists.empty(Lists), Lists.empty(Lists), Lists.empty(Lists), Lists.of(Lists, /* new Frame */()));
}
/* public */ char* ImmutableCompileState::generate(struct ImmutableCompileState this){
	auto local0 = /* join(this */;
	return local0.structs) + join(local0, this.functions);
}
/* private static */ char* ImmutableCompileState::join(struct ImmutableCompileState this, template List<char*> lists){
	auto local1 = lists.iterate(lists);
	auto local2 = local1.collect(local1, /* new Joiner */());
	return local2.orElse(local2, "");
}
/* public */ struct CompileState ImmutableCompileState::addStruct(struct ImmutableCompileState this, char* struct){
	auto local3 = this.structs;
	return /* new ImmutableCompileState */(local3.addLast(local3, struct), this.functions, this.statements, this.frames);
}
/* public */ struct CompileState ImmutableCompileState::addFunction(struct ImmutableCompileState this, char* function){
	auto local4 = this.functions;
	return /* new ImmutableCompileState */(this.structs, local4.addLast(local4, function), this.statements, this.frames);
}
/* public */ struct CompileState ImmutableCompileState::withStructType(struct ImmutableCompileState this, struct StructurePrototype type){
	auto local5 = /* last -> last */;
	auto local6 = this.frames;
	return /* new ImmutableCompileState */(this.structs, this.functions, this.statements, local6.mapLast(local6, local5.withProto(local5, type)));
}
/* public */ template Option<struct StructurePrototype> ImmutableCompileState::findStructureType(struct ImmutableCompileState this){
	auto local7 = this.frames;
	auto local8 = local7.iterateReversed(local7);
	auto local9 = local8.map(local8, /* frame -> frame */.prototype);
	return local9.flatMap(local9, /* Iterators::fromOptions)
                     */.next();
}
/* public */ (char*, struct CompileState) ImmutableCompileState::createLocalName(struct ImmutableCompileState this){
	auto local10 = this.frames;
	auto local11 = local10.last(local10);
	auto local12 = local11.<Tuple<String, CompileState>>map(local11, /* frame -> {
                var name = frame */.createName();
                return new Tuple<>(name.left, new ImmutableCompileState(this.structs, this.functions, this.statements, this.frames.setLast(name.right)));
            });
	return local12.orElseGet(local12, /* () -> new Tuple<> */("", this));
}
/* public */ struct CompileState ImmutableCompileState::addStatement(struct ImmutableCompileState this, char* statement){
	auto local13 = this.statements;
	return /* new ImmutableCompileState */(this.structs, this.functions, local13.addLast(local13, statement), this.frames);
}
/* public */ (template List<char*>, struct CompileState) ImmutableCompileState::removeStatements(struct ImmutableCompileState this){
	return /* new Tuple<> */(this.statements, /* new ImmutableCompileState */(this.structs, this.functions, Lists.empty(Lists), this.frames));
}
/* public */ struct CompileState ImmutableCompileState::enter(struct ImmutableCompileState this){
	auto local14 = this.frames;
	return /* new ImmutableCompileState */(this.structs, this.functions, this.statements, local14.addLast(local14, /* new Frame */()));
}
/* public */ struct CompileState ImmutableCompileState::exit(struct ImmutableCompileState this){
	auto local15 = this.frames;
	auto local16 = local15.removeLast(local15);
	auto local17 = local16.map(local16, /* Tuple::left */);
	return /* new ImmutableCompileState */(this.structs, this.functions, this.statements, local17.orElse(local17, this.frames));
}
struct public DivideState::DivideState(struct DivideState this, char* input){
	this(input, /* new JavaList<> */(), /* new StringBuilder */(), /*  0 */, /*  0 */);
}
/* private */ template Option<struct DivideState> DivideState::popAndAppend(struct DivideState this){
	auto local0 = this.popAndAppendToTuple(this);
	return local0.map(local0, /* Tuple::right */);
}
/* private */ template Option<(char, struct DivideState)> DivideState::popAndAppendToTuple(struct DivideState this){
	auto local1 = this.pop(this);
	return local1.map(local1, /* tuple -> {
                var c = tuple */.left;
                var state = tuple.right;
                return new Tuple<>(c, state.append(c));
            });
}
/* private */ struct DivideState DivideState::append(struct DivideState this, char c){
	auto local2 = this.buffer;
	return /* new DivideState */(this.input, this.segments, local2.append(local2, c), this.index, this.depth);
}
/* public */ template Option<(char, struct DivideState)> DivideState::pop(struct DivideState this){/* if (this.index < this.input.length()) {
                var c = this.input.charAt(this.index);
                return new Some<>(new Tuple<Character, DivideState>(c, new DivideState(this.input, this.segments, this.buffer, this.index + 1, this.depth)));
            } *//* else {
                return new None<>();
            } */
}
/* private */ struct DivideState DivideState::advance(struct DivideState this){
	auto local3 = this.buffer;
	auto local4 = this.buffer.isEmpty() ? this.segments : this.segments;
	auto withBuffer = local4.addLast(local4, local3.toString(local3));
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
/* public static */ (A, struct C) (*Tuple::mapRight)((A, B))<A, B, C>(struct Tuple<A, B> this, struct C (*mapper)(B)){
	return /* tuple -> new Tuple<> */(tuple.left, mapper.apply(mapper, tuple.right));
}
/* public static */ template Iterator<struct T> Iterators::fromOptions<T>(struct Iterators this, template Option<struct T> option){
	auto local0 = option.<Head<T>>map(option, /* SingleHead::new */);
	return /* new Iterator<> */(local0.orElseGet(local0, /* EmptyHead::new */));
}
struct public SingleHead::SingleHead(struct SingleHead<T> this, T value){/* this.value = value; *//* this.retrieved = false; */
}
/* public */ template Option<T> SingleHead::next(struct SingleHead<T> this){/* if (this.retrieved) {
                return new None<>();
            } *//* this.retrieved = true; */
	return /* new Some<> */(this.value);
}
/* public */ template Option<(char*, char*)> InfixSplitter::split(struct InfixSplitter this, char* input){
	auto local0 = this.apply(this, input);
	return local0.map(local0, /* classIndex -> {
                var beforeKeyword = input */.substring(0, classIndex);
                var afterKeyword = input.substring(classIndex + this.length());
                return new Tuple<>(beforeKeyword, afterKeyword);
            });
}
/* private */ int InfixSplitter::length(struct InfixSplitter this){
	auto local1 = this.infix;
	return local1.length(local1);
}
/* private */ template Option<int> InfixSplitter::apply(struct InfixSplitter this, char* input){
	auto local2 = this.locator(this);
	return local2.apply(local2, input, this.infix);
}
/* public */ template Option<(char*, char*)> TypeSeparatorSplitter::split(struct TypeSeparatorSplitter this, char* input){
	auto local0 = /* segments -> {
                var left = segments */.left;
                if (left;
	auto local1 = local0.isEmpty()) {
                    return new None<>(local0, /* );
                }

                var beforeType = left */.iterate();
	auto local2 = divide(input, /*  TypeSeparatorSplitter::fold) */.removeLast();
	return local2.flatMap(local2, local1.collect(local1, /* new Joiner */(" ")).orElse("");
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
	this(Lists.empty(Lists), /* new None<> */(), Lists.empty(Lists), type, name);
}
/* public */ struct Definition Definition::mapName(struct Definition this, char* (*mapper)(char*)){
	return /* new Definition */(this.annotations, this.maybeBeforeType, this.typeParams, this.type, mapper.apply(mapper, this.name));
}
/* public */ struct String_ Definition::generate(struct Definition this){
	auto local0 = this.typeParams;
	auto local1 = local0.iterate(local0);
	auto local2 = local1.collect(local1, /* new Joiner */(", "));
	auto local3 = local2.map(local2, /* inner -> "<" + inner + ">" */);
	auto local4 = this.maybeBeforeType;
	auto local5 = local4.map(local4, /* beforeType -> generatePlaceholder(beforeType) + " " */);
	auto local6 = /* beforeTypeString + this */.type;
	auto typeParamString = local3.orElse(local3, "");
	auto beforeTypeString = local5.orElse(local5, "");
	return Strings.from(Strings, local6.generateWithName(local6, this.name).toSlice() + typeParamString);
}
/* public */ struct String_ Content::generate(struct Content this){
	return Strings.from(Strings, generatePlaceholder(this.input));
}
/* public */ struct String_ Functional::generate(struct Functional this){
	return this.generateWithName(this, "");
}
/* public */ struct String_ Functional::generateWithName(struct Functional this, char* name){
	auto local0 = /* type -> type */;
	auto local1 = this.arguments(this, /* ) */.iterate();
	auto local2 = local1.map(local1, local0.generate(local0, /* ) */.toSlice());
	auto local3 = local2.collect(local2, /* new Joiner */(", "));
	auto local4 = this.returns;
	auto local5 = local4.generate(local4);
	auto local6 = local5.appendSlice(local5, " (*");
	auto local7 = local6.appendSlice(local6, name);
	auto local8 = local7.appendSlice(local7, ")(");
	auto local9 = local8.appendSlice(local8, joinedArguments);
	auto joinedArguments = local3.orElse(local3, "");
	return local9.appendSlice(local9, ")");
}
/* public */ struct String_ Template::generate(struct Template this){
	return Strings.from(Strings, this.generate0(this));
}
/* private */ char* Template::generate0(struct Template this){
	auto local0 = /* type -> type */;
	auto local1 = this.arguments(this, /* ) */.iterate();
	auto local2 = local1.map(local1, local0.generate(local0, /* ) */.toSlice());
	auto local3 = local2.collect(local2, /* new Joiner */(", "));
	auto generatedTuple = local3.orElse(local3, "");
	return "template " + this.base() + "<" + generatedTuple + ">";
}
/* public */ template List<T> ListCollector::createInitial(struct ListCollector<T> this){
	return Lists.empty(Lists);
}
/* public */ template List<T> ListCollector::fold(struct ListCollector<T> this, template List<T> current, T element){
	return current.addLast(current, element);
}
/* public */ struct String_ TypeParameter::generate(struct TypeParameter this){
	return Strings.from(Strings, this.generate0(this));
}
/* private */ char* TypeParameter::generate0(struct TypeParameter this){
	return this.value;
}
/* public */ struct String_ Ref::generate(struct Ref this){
	return Strings.from(Strings, this.generate0(this));
}
/* private */ char* Ref::generate0(struct Ref this){
	auto local0 = this.type;
	return local0.generate(local0).toSlice() + "*";
}
/* public */ struct String_ TupleType::generate(struct TupleType this){
	return Strings.from(Strings, this.generate0(this));
}
/* private */ char* TupleType::generate0(struct TupleType this){
	return "(" + generateNodesAsValues(this.arguments) + ")";
}
/* public */ struct String_ StructRef::generate(struct StructRef this){
	auto local0 = this.typeParams;
	auto local1 = local0.iterate(local0);
	auto local2 = local1.collect(local1, /* new Joiner */(", "));
	auto local3 = local2.map(local2, /* inner -> "<" + inner + ">" */);
	auto local4 = Strings.from(Strings, "struct ");
	auto local5 = local4.appendSlice(local4, this.input);
	auto typeParamString = local3.orElse(local3, "");
	return local5.appendSlice(local5, typeParamString);
}
/* public */ template Option<(char*, char*)> DividingSplitter::split(struct DividingSplitter this, char* input){
	auto local0 = /* divisions -> {
                var left1 = divisions */.left;
                if (left1;
	auto local1 = local0.isEmpty()) {
                    return new Tuple<>(local0, divisions.right, /*  "");
                }

                var left = left1 */.iterate();
	auto local2 = divide(input, this.folder).removeLast();
	return local2.map(local2, local1.collect(local1, /* new Joiner */()).orElse("");
                var right = divisions.right;
                return new Tuple<>(left, right);
            });
}
/* public */ struct String_ Symbol::generate(struct Symbol this){
	return Strings.from(Strings, this.value);
}
/* public */ struct String_ StringNode::generate(struct StringNode this){
	auto local0 = Strings.from(Strings, "\"");
	auto local1 = local0.appendSlice(local0, this.value);
	return local1.appendSlice(local1, "\"");
}
/* public */ struct String_ Invocation::generate(struct Invocation this){
	auto local0 = this.arguments(this, /* ) */.iterate();
	auto local1 = local0.map(local0, /* Node::generate */);
	auto local2 = local1.map(local1, /* String_::toSlice */);
	auto local3 = local2.collect(local2, /* new Joiner */(", "));
	auto joined = local3.orElse(local3, "");
	return Strings.from(Strings, this.caller(this, /* ) */.generate().toSlice() + "(" + joined + ")");
}
/* public */ struct String_ DataAccess::generate(struct DataAccess this){
	auto local0 = this.parent(this, /* ) */.generate();
	return Strings.from(Strings, local0.toSlice(local0, /* ) + " */." + this.property());
}
auto Primitive::Primitive(struct Primitive this, char* value){/* this.value = value; */
}
/* public */ struct String_ Primitive::generate(struct Primitive this){
	return Strings.from(Strings, this.generate0(this));
}
/* private */ char* Primitive::generate0(struct Primitive this){
	return this.value;
}
/* public static */ void Main::main(struct Main this){
	auto local0 = run();
	local0.ifPresent(local0, /* Throwable::printStackTrace */);
}
/* private static */ template Option<struct IOException> Main::run(struct Main this){
	return /* switch (readInput()) {
            case Err<String, IOException>(var error) -> new Some<>(error);
            case Ok<String, IOException>(var input) -> {
                var output = compileRoot(input);
                yield writeOutput(output);
            }
        } */;
}
/* private static */ template Option<struct IOException> Main::writeOutput(struct Main this, char* output);
/* private static */ template Result<char*, struct IOException> Main::readInput(struct Main this);
/* private static */ char* Main::compileRoot(struct Main this, char* input){
	auto local1 = compileAll(state, input, /*  Main::compileRootSegment */);
	auto local2 = tuple.right + tuple.left;
	auto state = /* new ImmutableCompileState */();
	auto tuple = local1.orElse(local1, /* new Tuple<> */(state, ""));
	return local2.generate(local2);
}
/* private static */ template Option<(struct CompileState, char*)> Main::compileAll(struct Main this, struct CompileState initial, char* input, template Option<(struct CompileState, char*)> (*mapper)(struct CompileState, char*)){
	return all(initial, input, /*  Main::foldStatementChar */, mapper, /*  Main::mergeStatements */);
}
/* private static */ template Option<(struct CompileState, char*)> Main::all(struct Main this, struct CompileState initial, char* input, struct DivideState (*folder)(struct DivideState, char), template Option<(struct CompileState, char*)> (*mapper)(struct CompileState, char*), struct StringBuilder (*merger)(struct StringBuilder, char*)){
	auto local3 = parseAll(initial, input, folder, mapper);
	return local3.map(local3, /* tuple -> new Tuple<> */(tuple.left, generateAll(merger, tuple.right)));
}
/* private static */ struct StringBuilder Main::mergeStatements(struct Main this, struct StringBuilder output, char* right){
	return output.append(output, right);
}
/* private static */ struct DivideState Main::foldStatementChar(struct Main this, struct DivideState state, char c){
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
/* private static */ template Option<(struct CompileState, char*)> Main::compileRootSegment(struct Main this, struct CompileState state, char* input){
	auto local4 = /* content -> content */;
	auto local5 = /* (state1, input1) -> content */(state1, input1);
	return or(state, input, Lists.of(Lists, /* 
                Main::whitespace */, /* 
                Main::compileNamespaced */, parseClass(), local5.map(local5, Tuple.mapRight(Tuple, local4.generate(local4, /* ) */.toSlice()))));
}
/* private static */ template Option<(struct CompileState, char*)> (*Main::parseClass)(struct CompileState, char*)(struct Main this){
	return structure("class", "class ");
}
/* private static */ template Option<(struct CompileState, char*)> (*Main::structure)(struct CompileState, char*)(struct Main this, char* type, char* infix){
	auto local6 = /* (beforeKeyword, afterKeyword) -> {
            var slices = Arrays */;
	auto local7 = local6.stream(local6, beforeKeyword.split(beforeKeyword, " "));
	auto local8 = /* value -> !value */;
	auto local9 = local7.map(local7, /* String::strip */);
	return /* (state, input) -> first */(input, infix, local9.filter(local9, local8.isEmpty(local8)).toList();

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
/* private static */ template Option<(struct CompileState, char*)> Main::structureWithVariants(struct Main this, char* type, struct CompileState state, char* beforeKeyword, char* beforeContent, char* withEnd){
	auto local10 = /* (Value node) -> node */;
	auto local11 = /* (state1, value) -> symbol */(state1, value);
	return first(beforeContent, " permits ", /* (beforePermits, variantsString) -> {
            return parseValues */(state, variantsString, local11.map(local11, Tuple.mapRight(Tuple, local10.generate(local10, /* ) */.toSlice()))).flatMap(params -> {
                return structureWithoutVariants(type, params.left, beforeKeyword, beforePermits, params.right, withEnd);
            });
        });
}
/* private static */ template Option<(struct CompileState, struct Value)> Main::symbol(struct Main this, struct CompileState state, char* value){
	auto stripped = value.strip(value);/* if (isSymbol(stripped)) {
            return new Some<>(new Tuple<>(state, new Symbol(stripped)));
        } *//* else {
            return new None<>();
        } */
}
/* private static */ template Option<(struct CompileState, char*)> Main::structureWithoutVariants(struct Main this, char* type, struct CompileState state, char* beforeKeyword, char* beforeContent, template List<char*> variants, char* withEnd){
	return or(state, beforeContent, Lists.of(Lists, /* (state0, s) -> structureWithImplements */(type, state0, beforeKeyword, s, variants, withEnd), /* (state0, s) -> structureWithoutImplements */(type, state0, beforeKeyword, s, variants, withEnd)));
}
/* private static */ template Option<(struct CompileState, char*)> Main::structureWithImplements(struct Main this, char* type, struct CompileState state0, char* beforeKeyword, char* s, template List<char*> variants, char* withEnd){
	return first(s, " implements ", /* (s1, s2) -> structureWithoutImplements */(type, state0, beforeKeyword, s1, variants, withEnd));
}
/* private static */ template Option<(struct CompileState, char*)> Main::structureWithoutImplements(struct Main this, char* type, struct CompileState state, char* beforeKeyword, char* beforeContent, template List<char*> variants, char* withEnd){
	return or(state, beforeContent, Lists.of(Lists, /* (state1, s) -> structureWithExtends */(type, beforeKeyword, beforeContent, variants, withEnd, state1), /* (state2, s) -> structureWithoutExtends */(type, state2, beforeKeyword, s, variants, withEnd)));
}
/* private static */ template Option<(struct CompileState, char*)> Main::structureWithExtends(struct Main this, char* type, char* beforeKeyword, char* beforeContent, template List<char*> variants, char* withEnd, struct CompileState state1){
	return first(beforeContent, " extends ", /* (s1, s2) -> structureWithoutExtends */(type, state1, beforeKeyword, s1, variants, withEnd));
}
/* private static */ template Option<(struct CompileState, char*)> Main::structureWithoutExtends(struct Main this, char* type, struct CompileState state, char* beforeKeyword, char* beforeContent, template List<char*> variants, char* withEnd){
	return or(state, beforeContent, Lists.of(Lists, /* (instance, before) -> structureWithParams */(type, instance, beforeKeyword, before, variants, withEnd), /* (instance, before) -> structureWithoutParams */(type, instance, beforeKeyword, before.strip(before), Lists.empty(Lists), variants, withEnd)));
}
/* private static */ template Option<(struct CompileState, char*)> Main::structureWithParams(struct Main this, char* type, struct CompileState instance, char* beforeKeyword, char* beforeContent, template List<char*> variants, char* withEnd){
	return suffix(beforeContent.strip(beforeContent), ")", /* withoutEnd -> first */(withoutEnd, "(", /* (name, paramString) -> {
            return parseAll */(instance, paramString, /*  Main::foldValueChar */, /*  Main::parameter */).flatMap(params -> {
                        return structureWithoutParams(type, params.left, beforeKeyword, name, params.right, variants, withEnd);
                    });
        }));
}
/* private static */ template Option<(struct CompileState, struct Parameter)> Main::parameter(struct Main this, struct CompileState instance, char* paramString){
	return Main.or(Main, instance, paramString, Lists.of(Lists, wrap(/* Main::definition */), wrap(/* Main::content */)));
}
/* private static */ template Option<(struct CompileState, char*)> Main::structureWithoutParams(struct Main this, char* type, struct CompileState state, char* beforeKeyword, char* beforeParams, template List<struct Parameter> params, template List<char*> variants, char* withEnd){
	return or(state, beforeParams, Lists.of(Lists, /* (state0, beforeParams0) -> structureWithTypeParams */(type, state0, beforeParams0, beforeKeyword, params, variants, withEnd), /* (state0, name) -> structureWithName */(type, state0, beforeKeyword, name, Lists.empty(Lists), params, variants, withEnd)));
}
/* private static */ template Option<(struct CompileState, char*)> Main::structureWithTypeParams(struct Main this, char* type, struct CompileState state, char* beforeParams0, char* beforeKeyword, template List<struct Parameter> params, template List<char*> variants, char* withEnd){
	auto local12 = /* (state1, value) -> symbol */(state1, value);
	return suffix(beforeParams0.strip(beforeParams0), ">", /* withoutEnd -> {
            return first */(withoutEnd, "<", /* (name, typeParamString) -> {
                return parseValues */(state, typeParamString, local12.map(local12, Tuple.mapRight(Tuple, (/* Value node) -> node */.generate().toSlice()))).flatMap(values -> {
                    return structureWithName(type, values.left, beforeKeyword, name, values.right, params, variants, withEnd);
                });
            });
        });
}
/* private static */ template Option<(struct CompileState, char*)> Main::structureWithName(struct Main this, char* type, struct CompileState state, char* beforeKeyword, char* name, template List<char*> typeParams, template List<struct Parameter> params, template List<char*> variants, char* withEnd){
	auto local13 = state.enter(state);
	auto local14 = /* content -> {
            return compileAll */(local13.withStructType(local13, /* new StructurePrototype */(type, name, typeParams, variants)), content, /*  Main::structSegment */);
	return suffix(withEnd.strip(withEnd), "}", local14.flatMap(local14, /* tuple -> {
                if (!isSymbol(name)) {
                    return new None<>();
                }
                return new Some<>(assembleStruct(type, tuple */.left, beforeKeyword, name, typeParams, params, variants, tuple.right));
            }).map(tuple -> new Tuple<>(tuple.left.exit(), tuple.right));
        });
}
/* private static */ (struct CompileState, char*) Main::assembleStruct(struct Main this, char* type, struct CompileState state, char* beforeKeyword, char* name, template List<char*> typeParams, template List<struct Parameter> params, template List<char*> variants, char* oldContent){/* if (!variants.isEmpty()) {
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
/* private static */ (struct CompileState, char*) Main::generateStruct(struct Main this, struct CompileState state, char* beforeKeyword, char* name, char* typeParamString, template List<struct Parameter> params, char* content){
	auto local15 = /* t -> t */;
	auto local16 = params.iterate(params);
	auto local17 = local16.map(local16, local15.generate(local15, /* ) */.toSlice());
	auto local18 = local17.map(local17, /* value -> "\n\t" + value + ";" */);
	auto local19 = local18.collect(local18, /* new Joiner */());
	auto paramsString = local19.orElse(local19, "");
	auto generatedStruct = /*  generatePlaceholder(beforeKeyword */.strip()) + "struct " + name + typeParamString + " {" + paramsString + content + "\n};\n";
	return /* new Tuple<CompileState, String> */(state.addStruct(state, generatedStruct), "");
}
/* private static */ char* Main::generateTypeParams(struct Main this, template List<char*> typeParams){
	auto local20 = typeParams.isEmpty(typeParams, /* ) ? "" : "<" + typeParams */.iterate();
	return local20.collect(local20, /* new Joiner */(", ")).orElse("") + ">";
}
/* private static */ template Option<(struct CompileState, struct T)> Main::or<T>(struct Main this, struct CompileState state, char* input, template List<template Option<(struct CompileState, struct T)> (*)(struct CompileState, char*)> actions){
	auto local21 = /* action -> action */;
	auto local22 = actions.iterate(actions);
	auto local23 = local22.map(local22, local21.apply(local21, state, input));
	return local23.flatMap(local23, /* Iterators::fromOptions)
                 */.next();
}
/* private static */ template Option<(struct CompileState, char*)> Main::compileNamespaced(struct Main this, struct CompileState state, char* input){/* if (input.strip().startsWith("package ") || input.strip().startsWith("import ")) {
            return new Some<>(new Tuple<CompileState, String>(state, ""));
        } */
	return /* new None<> */();
}
/* private static */ template Option<(struct CompileState, char*)> Main::structSegment(struct Main this, struct CompileState state, char* input){
	auto local24 = /* content -> content */;
	auto local25 = /* (state1, input1) -> content */(state1, input1);
	return or(state, input, Lists.of(Lists, /* 
                Main::whitespace */, /* 
                Main::annotation */, structure("enum", "enum "), parseClass(), structure("record", "record "), structure("interface", "interface "), /* 
                Main::method */, /* 
                Main::definitionStatement */, local25.map(local25, Tuple.mapRight(Tuple, local24.generate(local24, /* ) */.toSlice()))));
}
/* private static */ template Option<(struct CompileState, char*)> Main::definitionStatement(struct Main this, struct CompileState state, char* input){
	auto local26 = /* definition -> definition */;
	auto local27 = /* withoutEnd -> definition */(state, withoutEnd);
	auto local28 = local27.map(local27, Tuple.mapRight(Tuple, local26.generate(local26, /* ) */.toSlice()));
	return suffix(input.strip(input), ";", local28.map(local28, /* value -> {
            var generated = "\n\t" + value */.right + ";";
            return new Tuple<>(value.left, generated);
        }));
}
/* private static */ template Option<(struct CompileState, struct Content)> Main::content(struct Main this, struct CompileState state, char* input){
	return /* new Some<> */(/* new Tuple<> */(state, /* new Content */(input)));
}
/* private static */ template Option<(struct CompileState, char*)> Main::whitespace(struct Main this, struct CompileState state, char* input){/* if (input.isBlank()) {
            return new Some<>(new Tuple<>(state, ""));
        } */
	return /* new None<> */();
}
/* private static */ template Option<(struct CompileState, char*)> Main::method(struct Main this, struct CompileState state, char* input){
	auto local29 = /* outputDefinition -> {
                    return parseValues */(outputDefinition.left, paramsString, /*  Main::parameter */);
	auto local30 = /* (paramsString, withBraces) -> {
                return compileMethodHeader */(state, inputDefinition);
	return first(input, "(", /* (inputDefinition, withParams) -> {
            return first */(withParams, ")", local30.flatMap(local30, local29.flatMap(local29, /* outputParams -> {
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
/* private static */ template Option<struct Definition> Main::retainDefinition(struct Main this, struct Parameter param){/* if (param instanceof Definition definition) {
            return new Some<>(definition);
        } *//* else {
            return new None<>();
        } */
}
/* private static */ template Option<(struct CompileState, template List<struct T>)> Main::parseValues<T>(struct Main this, struct CompileState state, char* input, template Option<(struct CompileState, struct T)> (*compiler)(struct CompileState, char*)){
	return parseAll(state, input, /*  Main::foldValueChar */, compiler);
}
/* private static */ template Option<(struct CompileState, template List<struct T>)> Main::parseAll<T>(struct Main this, struct CompileState initial, char* input, struct DivideState (*folder)(struct DivideState, char), template Option<(struct CompileState, struct T)> (*mapper)(struct CompileState, char*)){
	auto local31 = /* (maybeCurrent, segment) -> maybeCurrent */;
	auto local32 = divide(input, /*  folder)
                 */.iterate();
	return local32.<Option<Tuple<CompileState, List<T>>>>fold(local32, /* new Some<> */(/* new Tuple<CompileState, List<T>> */(initial, Lists.empty(Lists))), local31.flatMap(local31, /* state -> foldElement */(state, segment, mapper)));
}
/* private static */ template Option<(struct CompileState, template List<struct T>)> Main::foldElement<T>(struct Main this, (struct CompileState, template List<struct T>) state, char* segment, template Option<(struct CompileState, struct T)> (*mapper)(struct CompileState, char*)){
	auto local33 = mapper.apply(mapper, oldState, segment);
	auto oldState = state.left;
	auto oldCache = state.right;
	return local33.map(local33, /* result -> {
            var newState = result */.left;
            var newElement = result.right;
            return new Tuple<>(newState, oldCache.addLast(newElement));
        });
}
/* private static */ template List<char*> Main::divide(struct Main this, char* input, struct DivideState (*folder)(struct DivideState, char)){
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
/* private static */ template Option<struct DivideState> Main::foldDoubleQuotes(struct Main this, struct DivideState state, char c){/* if (c != '\"') {
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
/* private static */ template Option<struct DivideState> Main::foldSingleQuotes(struct Main this, struct DivideState state, char c){
	auto local34 = /* maybeNextTuple -> {
            var nextChar = maybeNextTuple */.left;
            var nextState = maybeNextTuple.right;
	auto local35 = local34.append(local34, /* nextChar);

            var withEscaped = nextChar == '\\'
                    ? nextState */.popAndAppend();
	auto local36 = state.append(state, /* c) */.pop();/* if (c != '\'') {
            return new None<>();
        } */
	return local36.map(local36, local35.orElse(local35, /* nextState)
                    : nextState;

            return withEscaped */.popAndAppend().orElse(withEscaped);
        });
}
/* private static */ struct DivideState Main::foldValueChar(struct Main this, struct DivideState state, char c){/* if (c == ',' && state.isLevel()) {
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
/* private static */ char* Main::generateNodesAsValues<T extends Node>(struct Main this, template List<struct T> params){
	auto local37 = /* t -> t */;
	auto local38 = params.iterate(params);
	auto local39 = local38.map(local38, local37.generate(local37, /* ) */.toSlice());
	auto local40 = local39.collect(local39, /* new Joiner */(", "));
	return local40.orElse(local40, "");
}
/* private static */ char* Main::generateAll(struct Main this, struct StringBuilder (*merger)(struct StringBuilder, char*), template List<char*> right){
	auto local41 = right.iterate(right);
	return local41.fold(local41, /* new StringBuilder */(), /*  merger) */.toString();
}
/* private static */ template Option<(struct CompileState, char*)> Main::methodWithoutContent(struct Main this, struct CompileState state, struct Definition definition, template List<struct Definition> params, char* content){/* if (!content.equals(";")) {
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
/* private static */ template Option<(struct CompileState, char*)> Main::methodWithContent(struct Main this, struct CompileState state, struct Definition definition, template List<struct Definition> params, char* withBraces){
	auto local42 = /* content -> {
                var newParameters = state */;
	auto local43 = /* structType -> params */;
	auto local44 = local42.findStructureType(local42);
	auto local45 = /* name -> state */;
	auto local46 = local45.findStructureType(local45);
	auto local47 = local46.map(local46, /* structureType -> structureType */.name + "::" + name);
	auto local48 = local44.map(local44, local43.addFirst(local43, /* new Definition */(/* new StructRef */(structType.name, structType.typeParams), "this"))).orElse(params);

                var paramStrings = generateNodesAsValues(newParameters);

                var newHeader = definition
                        ;
	auto local49 = local48.mapName(local48, local47.orElse(local47, /* name) */).generate().toSlice() + "(" + paramStrings + ")";

                if (definition.annotations.contains("Actual")) {
                    return new Some<>(new Tuple<>(state;
	auto local50 = /* tuple -> {
                    var removed = tuple */.left;
	auto local51 = local49.addFunction(newHeader + ";\n"), ""));
                }

                return compileAll(local49, state, content, /*  Main::functionSegment */);
	return prefix(withBraces.strip(withBraces), "{", /* withoutStart1 -> {
            return suffix */(withoutStart1, "}", local51.flatMap(local51, local50.removeStatements(local50, /* );
                    var joined = removed */.left.iterate().collect(new Joiner()).orElse("");

                    var generated = newHeader + "{" + joined + tuple.right + "\n}\n";
                    return new Some<>(new Tuple<>(removed.right.addFunction(generated), ""));
                });
            });
        });
}
/* private static */ template Option<(struct CompileState, struct Definition)> Main::compileMethodHeader(struct Main this, struct CompileState state, char* definition){
	return or(state, definition, Lists.of(Lists, /* 
                Main::definition */, /* 
                Main::constructor
         */));
}
/* private static */ template Option<(struct CompileState, struct Definition)> Main::constructor(struct Main this, struct CompileState state, char* input){
	return or(state, input, Lists.of(Lists, /* 
                Main::constructorWithType */, /* 
                Main::constructorWithoutType
         */));
}
/* private static */ template Option<(struct CompileState, struct Definition)> Main::constructorWithoutType(struct Main this, struct CompileState state, char* s){
	auto stripped = s.strip(s);/* if (isSymbol(stripped)) {
            return new Some<>(new Tuple<>(state, new Definition(Primitive.Auto, stripped)));
        } */
	return /* new None<> */();
}
/* private static */ template Option<(struct CompileState, struct Definition)> Main::constructorWithType(struct Main this, struct CompileState state, char* input){
	auto local52 = (_, /*  name) -> state */.findStructureType();
	return split(input.strip(input), /* new InfixSplitter */(" ", /*  Main::lastIndexOfSlice */), local52.flatMap(local52, /* structureType -> {
            if (!structureType */.name.equals(name)) {
                return new None<>();
            }

            return new Some<>(new Tuple<>(state, new Definition(Primitive.Auto, name)));
        }));
}
/* private static */ template Option<(struct CompileState, char*)> Main::functionSegment(struct Main this, struct CompileState state, char* input){
	auto local53 = /* slice -> statementValue */(state0, slice);
	auto local54 = /* content -> content */;
	auto local55 = /* (state0a, input1) -> content */(state0a, input1);
	return or(state, input.strip(input), Lists.of(Lists, /* 
                Main::whitespace */, /* (state0, input1) -> suffix */(input1.strip(input1), ";", local53.map(local53, Tuple.mapRight(Tuple, /* slice0 -> "\n\t" + slice0 + ";" */))), local55.map(local55, Tuple.mapRight(Tuple, local54.generate(local54, /* ) */.toSlice()))));
}
/* private static */ template Option<(struct CompileState, char*)> Main::statementValue(struct Main this, struct CompileState state, char* input){
	auto local56 = /* right -> right */;
	auto local57 = /* (state1, s) -> invocation */(state1, s);
	return Main.or(Main, state, input, Lists.of(Lists, /* 
                Main::returns */, /* 
                Main::initialization */, local57.map(local57, Tuple.mapRight(Tuple, local56.generate(local56, /* ) */.toSlice()))));
}
/* private static */ template Option<(struct CompileState, char*)> Main::initialization(struct Main this, struct CompileState state, char* s){
	auto local58 = /* result -> result */;
	auto local59 = /* result0 -> {
            return value */(result0.left, s2);
	auto local60 = local59.map(local59, Tuple.mapRight(Tuple, local58.generate(local58, /* ) */.toSlice()));
	auto local61 = /* (s1, s2) -> definition */(state, s1);
	return first(s, "=", local61.flatMap(local61, local60.map(local60, /* result1 -> {
                return new Tuple<>(result1 */.left, result0.right.generate().toSlice() + " = " + result1.right());
            });
        }));
}
/* private static */ template Option<(struct CompileState, struct Value)> Main::invocation(struct Main this, struct CompileState state0, char* input){
	return suffix(input.strip(input), ")", /* withoutEnd -> {
            return split */(withoutEnd, /* new DividingSplitter */(/* Main::foldInvocationStart */), /* (withEnd, argumentsString) -> {
                return suffix */(withEnd.strip(withEnd), "(", /* callerString -> value */(state0, callerString).flatMap(callerTuple -> {
                    return Main.parseValues(callerTuple.left, argumentsString, Main::value).map(argumentsTuple -> {
                        var oldCaller = callerTuple.right;

                        var oldState = argumentsTuple.left;
                        var oldArguments = argumentsTuple.right;
                        if (!(oldCaller instanceof DataAccess access)) {
                            return new Tuple<>(oldState, new Invocation(oldCaller, oldArguments));
                        }

                        var parent = access.parent;
                        if (parent instanceof Symbol) {
                            return new Tuple<>(oldState, new Invocation(oldCaller, oldArguments.addFirst(parent)));
                        }

                        var localName = oldState.createLocalName();
                        var name = localName.left;
                        var right = localName.right;
                        var newState = right.addStatement("\n\tauto " + name + " = " + parent.generate().toSlice() + ";");
                        var element = new Symbol(name);
                        var newArguments = oldArguments.addFirst(element);
                        var newCaller = new DataAccess(element, access.property);
                        return new Tuple<>(newState, new Invocation(newCaller, newArguments));
                    });
                }));
            });
        });
}
/* private static */ struct DivideState Main::foldInvocationStart(struct Main this, struct DivideState state, char c){
	auto appended = state.append(state, c);/* if (c == '(') {
            var enter = appended.enter();
            return appended.isLevel() ? enter.advance() : enter;
        } *//* if (c == ')') {
            return appended.exit();
        } */
	return appended;
}
/* private static */ template Option<(struct CompileState, char*)> Main::returns(struct Main this, struct CompileState state, char* input){
	auto local62 = /* result1 -> result1 */;
	auto local63 = /* slice -> value */(state, slice);
	auto local64 = local63.map(local63, Tuple.mapRight(Tuple, local62.generate(local62, /* ) */.toSlice()));
	return prefix(input.strip(input), "return ", local64.map(local64, Tuple.mapRight(Tuple, /* result -> "return " + result */)));
}
/* private static */ template Option<(struct CompileState, struct Value)> Main::value(struct Main this, struct CompileState state, char* input){
	auto local65 = /* (state1, input1) -> content */(state1, input1);
	return Main.or(Main, state, input, Lists.of(Lists, /* 
                Main::stringNode */, /* 
                Main::invocation */, /* 
                Main::dataAccess */, /* 
                Main::symbol */, local65.map(local65, Tuple.mapRight(Tuple, /* right -> right */))));
}
/* private static */ template Option<(struct CompileState, struct Value)> Main::stringNode(struct Main this, struct CompileState state, char* input){
	auto stripped = input.strip(input);/* if (stripped.length() >= 2 && stripped.startsWith("\"") && stripped.endsWith("\"")) {
            return new Some<>(new Tuple<>(state, new StringNode(stripped.substring(1, stripped.length() - 1))));
        } *//* else {
            return new None<>();
        } */
}
/* private static */ template Option<(struct CompileState, struct Value)> Main::dataAccess(struct Main this, struct CompileState state, char* input){
	return split(input, /* new InfixSplitter */(".", /*  Main::lastIndexOfSlice */), /* (parent, property) -> {
            return value */(state, parent).map(tuple -> {
                return new Tuple<>(tuple.left, new DataAccess(tuple.right, property));
            });
        });
}
/* private static */ template Option<(struct CompileState, struct Definition)> Main::definition(struct Main this, struct CompileState state, char* input){
	auto local66 = /* (oldBeforeName, name) -> {
            if (!isSymbol(name)) {
                return new None<>();
            }

            List<String> annotations;
            String newBeforeName;
            var index = oldBeforeName */.indexOf("\n");
            if (index >= 0) {
                var stripped = oldBeforeName;
	auto local67 = /* (state1, c) -> {
                    if (c == '\'') {
                        return state1 */.advance();
                    }
                    return state1;
	auto local68 = local66.substring(local66, /* 0 */, index).strip();
                newBeforeName = oldBeforeName.substring(index + "\n";
	auto local69 = local68.length());

                annotations = divide(local68, stripped, local67.append(local67, /* c);
                } */).iterate();
	auto local70 = /* value -> value */;
	auto local71 = local69.map(local69, /* String::strip */);
	return split(input.strip(input), /* new InfixSplitter */(" ", /*  Main::lastIndexOfSlice */), local71.map(local71, local70.substring(local70, /* 1 */)).collect(new ListCollector<>());
            }
            else {
                annotations = Lists.empty();
                newBeforeName = oldBeforeName;
            }

            return Main.or(state, newBeforeName.strip(), Lists.of(
                    (instance, beforeName0) -> definitionWithTypeSeparator(instance, beforeName0, name, annotations),
                    (instance, beforeName0) -> definitionWithoutTypeSeparator(instance, beforeName0, name, annotations)
            ));
        });
}
/* private static */ int Main::isSymbol(struct Main this, char* value){/* for (var i = 0; i < value.length(); i++) {
            var c = value.charAt(i);
            if (Character.isLetter(c) || c == '_' || (i != 0 && Character.isDigit(c))) {
                continue;
            }
            return false;
        } */
	return true;
}
/* private static */ template Option<(struct CompileState, struct Definition)> Main::definitionWithoutTypeSeparator(struct Main this, struct CompileState state, char* type, char* name, template List<char*> annotations){
	return assembleDefinition(state, /* new None<String> */(), type, name, annotations, Lists.empty(Lists));
}
/* private static */ template Option<(struct CompileState, struct Definition)> Main::assembleDefinition(struct Main this, struct CompileState state, template Option<char*> maybeBeforeType, char* type, char* name, template List<char*> annotations, template List<char*> typeParams){
	auto local72 = type(state.enter(state), type);
	return local72.map(local72, /* newType -> {
            var definition = new Definition(annotations, maybeBeforeType, typeParams, newType */.right, name.strip());
            return new Tuple<>(newType.left.exit(), definition);
        });
}
/* private static */ template Option<(struct CompileState, struct Definition)> Main::definitionWithTypeSeparator(struct Main this, struct CompileState state, char* beforeName, char* name, template List<char*> annotations){
	return split(beforeName, /* new TypeSeparatorSplitter */(), /*  (beforeType, typeString) -> {
            return or(state, beforeType, Lists */.of(
                    (state2, s) -> definitionWithTypeParams(state2, annotations, s, typeString, name),
                    (state1, s) -> definitionWithoutTypeParams(name, annotations, typeString, state1, s)));
        });
}
/* private static */ template Option<(struct CompileState, struct Definition)> Main::definitionWithTypeParams(struct Main this, struct CompileState state, template List<char*> annotations, char* beforeType, char* typeString, char* name){
	return suffix(beforeType.strip(beforeType), ">", /* withoutEnd -> {
            return first */(withoutEnd, "<", /* (beforeTypeParams, typeParamStrings) -> {
                var typeParams = divide */(typeParamStrings, /*  Main::foldValueChar)
                         */.iterate().map(String::strip).collect(new ListCollector<>());

                return assembleDefinition(state, new Some<>(beforeTypeParams.strip()), typeString, name, annotations, typeParams);
            });
        });
}
/* private static */ template Option<(struct CompileState, struct Definition)> Main::definitionWithoutTypeParams(struct Main this, char* name, template List<char*> annotations, char* typeTuple, struct CompileState state1, char* beforeType){
	return assembleDefinition(state1, /* new Some<> */(beforeType.strip(beforeType)), typeTuple, name, annotations, Lists.empty(Lists));
}
/* private static */ template Option<(struct CompileState, struct Type)> Main::type(struct Main this, struct CompileState state, char* input){
	return Main.or(Main, state, input, Lists.of(Lists, /* 
                Main::primitive */, /* 
                Main::template */, /* 
                Main::typeParameter */, /* 
                Main::string */, /* 
                Main::structureType */, wrap(/* Main::content */)));
}
/* private static */ template Option<(struct CompileState, struct Type)> Main::structureType(struct Main this, struct CompileState state, char* input){
	auto stripped = input.strip(input);/* if (isSymbol(stripped)) {
            return new Some<>(new Tuple<>(state, new StructRef(stripped, Lists.empty())));
        } *//* else {
            return new None<>();
        } */
}
/* private static */ template Option<(struct CompileState, struct Type)> Main::string(struct Main this, struct CompileState state, char* input){/* if (input.strip().equals("String")) {
            return new Some<>(new Tuple<>(state, new Ref(Primitive.I8)));
        } *//* else {
            return new None<>();
        } */
}
/* private static */ template Option<(struct CompileState, struct Type)> Main::typeParameter(struct Main this, struct CompileState state, char* input){/* if (state.findStructureType() instanceof Some(var structureType)) {
            var stripped = input.strip();
            if (structureType.typeParams.contains(stripped)) {
                return new Some<>(new Tuple<>(state, new TypeParameter(stripped)));
            }
        } */
	return /* new None<> */();
}
/* private static */ template Option<(struct CompileState, struct S)> (*Main::wrap)(struct CompileState, char*)<S, T extends S>(struct Main this, template Option<(struct CompileState, struct T)> (*content)(struct CompileState, char*)){
	auto local73 = /* (state, input) -> content */;
	auto local74 = local73.apply(local73, state, input);
	return local74.map(local74, Tuple.mapRight(Tuple, /* value -> value */));
}
/* private static */ template Option<(struct CompileState, struct Type)> Main::primitive(struct Main this, struct CompileState state, char* input){
	auto stripped = input.strip(input);
	return /* switch (stripped) {
            case "boolean", "Boolean", "int", "Integer" -> new Some<>(new Tuple<>(state, Primitive */.I32));
            case "char", "Character" -> new Some<>(new Tuple<>(state, Primitive.I8));
            case "var" -> new Some<>(new Tuple<>(state, Primitive.Auto));
            case "void" -> new Some<>(new Tuple<>(state, Primitive.Void));
            default -> new None<>();
        };
}
/* private static */ template Option<(struct CompileState, struct Type)> Main::template(struct Main this, struct CompileState state, char* input){
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
/* private static */ template Option<int> Main::lastIndexOfSlice(struct Main this, char* input, char* infix){
	auto index = input.lastIndexOf(input, infix);
	return /* index == -1 ? new None<Integer>() : new Some<> */(index);
}
/* private static */ template Option<(struct CompileState, char*)> Main::prefix(struct Main this, char* input, char* prefix, template Option<(struct CompileState, char*)> (*mapper)(char*)){/* if (!input.startsWith(prefix)) {
            return new None<>();
        } */
	auto slice = input.substring(input, prefix.length(prefix));
	return mapper.apply(mapper, slice);
}
/* private static */ template Option<struct T> Main::suffix<T>(struct Main this, char* input, char* suffix, template Option<struct T> (*mapper)(char*)){/* if (!input.endsWith(suffix)) {
            return new None<>();
        } */
	auto slice = input.substring(input, /* 0 */, input.length(input, /* ) - suffix */.length());
	return mapper.apply(mapper, slice);
}
/* private static */ char* Main::generatePlaceholder(struct Main this, char* input){
	return "/* " + input + " */";
}
/* private static */ template Option<struct T> Main::first<T>(struct Main this, char* input, char* infix, template Option<struct T> (*mapper)(char*, char*)){
	return split(input, /* new InfixSplitter */(infix, /*  Main::firstIndexOfSlice */), mapper);
}
/* private static */ template Option<struct T> Main::split<T>(struct Main this, char* input, struct Splitter splitter, template Option<struct T> (*mapper)(char*, char*)){
	auto local75 = splitter.split(splitter, input);
	return local75.flatMap(local75, /* tuple -> {
            return mapper */.apply(tuple.left, tuple.right);
        });
}
/* private static */ template Option<int> Main::firstIndexOfSlice(struct Main this, char* input, char* infix){
	auto index = input.indexOf(input, infix);
	return /* index == -1 ? new None<Integer>() : new Some<> */(index);
}
