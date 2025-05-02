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
	template Option<struct T> (*next)(S);
};
/* private */struct List<S, T> {
	S _super;
	template List<struct T> (*addLast)(S, struct T);
	template Iterator<struct T> (*iterate)(S);
	template Option<(template List<struct T>, struct T)> (*removeLast)(S);
	int (*isEmpty)(S);
	struct T (*get)(S, int);
	template List<struct T> (*addFirst)(S, struct T);
	int (*contains)(S, struct T);
	template List<struct T> (*mapLast)(S, struct T (*)(struct T));
	template Iterator<struct T> (*iterateReversed)(S);
	template Option<struct T> (*last)(S);
	template List<struct T> (*setLast)(S, struct T);
	template List<struct T> (*addAddLast)(S, template List<struct T>);
};
/* private */struct Collector<S, T, C> {
	S _super;
	struct C (*createInitial)(S);
	struct C (*fold)(S, struct C, struct T);
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
	struct CompileState (*mapLastFrame)(S, struct Frame (*)(struct Frame));
	template Option<struct StructurePrototype> (*findStructureType)(S);
	(char*, struct CompileState) (*createLocalName)(S);
	struct CompileState (*addStatement)(S, char*);
	(template List<char*>, struct CompileState) (*removeStatements)(S);
	struct CompileState (*enter)(S);
	struct CompileState (*exit)(S);
	int (*hasTypeParam)(S, char*);
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
	template List<char*> typeParams;
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
	/* private final */ struct T value;
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
/* public */ template Option<R> Some::map<R>(struct Some<T> this, R (*mapper)(struct T)){
	return /* new Some<> */(mapper.apply(mapper, this.value));
}
/* public */ int isEmpty(){
	return false;
}
/* public */ struct T orElse(struct T other){
	return this.value;
}
/* public */ template Option<struct T> or(template Option<struct T> (*other)()){
	return this;
}
/* public */ struct T orElseGet(struct T (*other)()){
	return this.value;
}
/* public */ template Option<R> flatMap<R>(template Option<R> (*mapper)(struct T)){
	return mapper.apply(mapper, this.value);
}
/* public */ template Option<struct T> filter(template Predicate<struct T> predicate){
	return predicate.test(predicate, this.value) ? this : new None<>();
}
/* public */ int isPresent(){
	return true;
}
/* public */ void ifPresent(template Consumer<struct T> consumer){
	consumer.accept(consumer, this.value);
}
/* public */ template Option<R> None::map<R>(struct None<T> this, R (*mapper)(struct T)){
	return /* new None<> */();
}
/* public */ int isEmpty(){
	return true;
}
/* public */ struct T orElse(struct T other){
	return other;
}
/* public */ template Option<struct T> or(template Option<struct T> (*other)()){
	return other.get(other);
}
/* public */ struct T orElseGet(struct T (*other)()){
	return other.get(other);
}
/* public */ template Option<R> flatMap<R>(template Option<R> (*mapper)(struct T)){
	return /* new None<> */();
}
/* public */ template Option<struct T> filter(template Predicate<struct T> predicate){
	return /* new None<> */();
}
/* public */ int isPresent(){
	return false;
}
/* public */ void ifPresent(template Consumer<struct T> consumer){
}
/* public */ C Iterator::collect<C>(struct Iterator<T> this, template Collector<struct T, C> collector){
	return this.fold(this, collector.createInitial(collector), /*  collector::fold */);
}
/* private */ C fold<C>(C initial, C (*folder)(C, struct T)){
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
/* public */ template Iterator<R> flatMap<R>(template Iterator<R> (*mapper)(struct T)){
	auto local0 = this.map(this, mapper);
	return local0.fold(local0, /* new Iterator<> */(/* new EmptyHead<> */()), /*  Iterator::concat */);
}
/* public */ template Iterator<R> map<R>(R (*mapper)(struct T)){
	auto local0 = (/* ) -> this */.head.next();
	return /* new Iterator<> */(local0.map(local0, mapper));
}
/* private */ template Iterator<struct T> concat(template Iterator<struct T> other){
	auto local0 = (/* ) -> this */.head.next();
	return /* new Iterator<> */(local0.or(local0, /* other::next */));
}
/* public */ template Option<struct T> next(){
	auto local0 = this.head;
	return local0.next(local0);
}
/* public */ int anyMatch(template Predicate<struct T> predicate){
	auto local0 = /* (aBoolean, t) -> aBoolean || predicate */;
	return this.fold(this, false, local0.test(local0, t));
}
struct private RangeHead::RangeHead(struct RangeHead this, int length){/* this.length = length; *//* this.counter = 0; */
}
/* public */ template Option<int> next(){/* if (this.counter >= this.length) {
                return new None<>();
            } */
	auto value = this.counter;/* this.counter++; */
	return /* new Some<> */(value);
}
/* public static */ template List<T> Lists::of<T>(struct Lists this, /* T... */ elements){
	return /* new JavaList<> */(Arrays.asList(Arrays, elements));
}
/* public static */ template List<T> empty<T>(){
	return /* new JavaList<> */(/* new ArrayList<> */());
}
/* public */ template Option<struct T> EmptyHead::next(struct EmptyHead<T> this){
	return /* new None<> */();
}
struct public Joiner::Joiner(struct Joiner this){
	this("");
}
/* public */ template Option<char*> createInitial(){
	return /* new None<> */();
}
/* public */ template Option<char*> fold(template Option<char*> current, char* element){
	auto local0 = current.map(current, /* inner -> inner + this */.delimiter + element);
	return /* new Some<> */(local0.orElse(local0, element));
}
struct public Frame::Frame(struct Frame this){
	this(/* new None<> */(), /*  0 */, Lists.empty(Lists));
}
/* public */ struct Frame withProto(struct StructurePrototype type){
	return /* new Frame */(/* new Some<> */(type), this.counter, this.typeParams);
}
/* public */ (char*, struct Frame) createName(){
	return /* new Tuple<> */(/* "local" + this */.counter, /* new Frame */(this.prototype, this.counter + 1, this.typeParams));
}
/* public */ struct Frame defineTypeParams(template List<char*> typeParams){
	auto local0 = this.typeParams;
	return /* new Frame */(this.prototype, this.counter, local0.addAddLast(local0, typeParams));
}
/* public */ int hasTypeParam(char* typeParam){
	auto local0 = this.typeParams;
	return local0.contains(local0, typeParam);
}
struct public ImmutableCompileState::ImmutableCompileState(struct ImmutableCompileState this){
	this(Lists.empty(Lists), Lists.empty(Lists), Lists.empty(Lists), Lists.of(Lists, /* new Frame */()));
}
/* public */ char* generate(){
	auto local0 = /* join(this */;
	return local0.structs) + join(local0, this.functions);
}
/* private static */ char* join(template List<char*> lists){
	auto local0 = lists.iterate(lists);
	auto local1 = local0.collect(local0, /* new Joiner */());
	return local1.orElse(local1, "");
}
/* public */ struct CompileState addStruct(char* struct){
	auto local0 = this.structs;
	return /* new ImmutableCompileState */(local0.addLast(local0, struct), this.functions, this.statements, this.frames);
}
/* public */ struct CompileState addFunction(char* function){
	auto local0 = this.functions;
	return /* new ImmutableCompileState */(this.structs, local0.addLast(local0, function), this.statements, this.frames);
}
/* public */ struct CompileState mapLastFrame(struct Frame (*mapper)(struct Frame)){
	auto local0 = this.frames;
	return /* new ImmutableCompileState */(this.structs, this.functions, this.statements, local0.mapLast(local0, mapper));
}
/* public */ template Option<struct StructurePrototype> findStructureType(){
	auto local0 = this.frames;
	auto local1 = local0.iterateReversed(local0);
	auto local2 = local1.map(local1, /* frame -> frame */.prototype);
	return local2.flatMap(local2, /* Iterators::fromOptions)
                     */.next();
}
/* public */ (char*, struct CompileState) createLocalName(){
	auto local0 = this.frames;
	auto local1 = local0.last(local0);
	auto local2 = local1.<Tuple<String, CompileState>>map(local1, /* frame -> {
                var name = frame */.createName();
                return new Tuple<>(name.left, new ImmutableCompileState(this.structs, this.functions, this.statements, this.frames.setLast(name.right)));
            });
	return local2.orElseGet(local2, /* () -> new Tuple<> */("", this));
}
/* public */ struct CompileState addStatement(char* statement){
	auto local0 = this.statements;
	return /* new ImmutableCompileState */(this.structs, this.functions, local0.addLast(local0, statement), this.frames);
}
/* public */ (template List<char*>, struct CompileState) removeStatements(){
	return /* new Tuple<> */(this.statements, /* new ImmutableCompileState */(this.structs, this.functions, Lists.empty(Lists), this.frames));
}
/* public */ struct CompileState enter(){
	auto local0 = this.frames;
	return /* new ImmutableCompileState */(this.structs, this.functions, this.statements, local0.addLast(local0, /* new Frame */()));
}
/* public */ struct CompileState exit(){
	auto local0 = this.frames;
	auto local1 = local0.removeLast(local0);
	auto local2 = local1.map(local1, /* Tuple::left */);
	return /* new ImmutableCompileState */(this.structs, this.functions, this.statements, local2.orElse(local2, this.frames));
}
/* public */ int hasTypeParam(char* typeParam){
	auto local0 = this.frames;
	auto local1 = /* frame -> frame */;
	auto local2 = local0.iterateReversed(local0);
	return local2.anyMatch(local2, local1.hasTypeParam(local1, typeParam));
}
struct public DivideState::DivideState(struct DivideState this, char* input){
	this(input, /* new JavaList<> */(), /* new StringBuilder */(), /*  0 */, /*  0 */);
}
/* private */ template Option<struct DivideState> popAndAppend(){
	auto local0 = this.popAndAppendToTuple(this);
	return local0.map(local0, /* Tuple::right */);
}
/* private */ template Option<(char, struct DivideState)> popAndAppendToTuple(){
	auto local0 = this.pop(this);
	return local0.map(local0, /* tuple -> {
                var c = tuple */.left;
                var state = tuple.right;
                return new Tuple<>(c, state.append(c));
            });
}
/* private */ struct DivideState append(char c){
	auto local0 = this.buffer;
	return /* new DivideState */(this.input, this.segments, local0.append(local0, c), this.index, this.depth);
}
/* public */ template Option<(char, struct DivideState)> pop(){/* if (this.index < this.input.length()) {
                var c = this.input.charAt(this.index);
                return new Some<>(new Tuple<Character, DivideState>(c, new DivideState(this.input, this.segments, this.buffer, this.index + 1, this.depth)));
            } *//* else {
                return new None<>();
            } */
}
/* private */ struct DivideState advance(){
	auto local0 = this.buffer;
	auto local1 = this.buffer.isEmpty() ? this.segments : this.segments;
	auto withBuffer = local1.addLast(local1, local0.toString(local0));
	return /* new DivideState */(this.input, withBuffer, /* new StringBuilder */(), this.index, this.depth);
}
/* public */ struct DivideState exit(){
	return /* new DivideState */(this.input, this.segments, this.buffer, this.index, this.depth - 1);
}
/* public */ int isLevel(){
	return this.depth == 0;
}
/* public */ struct DivideState enter(){
	return /* new DivideState */(this.input, this.segments, this.buffer, this.index, this.depth + 1);
}
/* public */ int isShallow(){
	return this.depth == 1;
}
/* public */ template Option<char> peek(){/* if (this.index < this.input.length()) {
                return new Some<>(this.input.charAt(this.index));
            } *//* else {
                return new None<>();
            } */
}
/* public static */ (A, C) (*Tuple::mapRight)((A, B))<A, B, C>(struct Tuple<A, B> this, C (*mapper)(B)){
	return /* tuple -> new Tuple<> */(tuple.left, mapper.apply(mapper, tuple.right));
}
/* public static */ template Iterator<T> Iterators::fromOptions<T>(struct Iterators this, template Option<T> option){
	auto local0 = option.<Head<T>>map(option, /* SingleHead::new */);
	return /* new Iterator<> */(local0.orElseGet(local0, /* EmptyHead::new */));
}
struct public SingleHead::SingleHead(struct SingleHead<T> this, struct T value){/* this.value = value; *//* this.retrieved = false; */
}
/* public */ template Option<struct T> next(){/* if (this.retrieved) {
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
/* private */ int length(){
	auto local0 = this.infix;
	return local0.length(local0);
}
/* private */ template Option<int> apply(char* input){
	auto local0 = this.locator(this);
	return local0.apply(local0, input, this.infix);
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
/* private static */ struct DivideState fold(struct DivideState state, char c){/* if (c == ' ' && state.isLevel()) {
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
/* public */ struct Definition mapName(char* (*mapper)(char*)){
	return /* new Definition */(this.annotations, this.maybeBeforeType, this.typeParams, this.type, mapper.apply(mapper, this.name));
}
/* public */ struct String_ generate(){
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
/* public */ struct String_ generateWithName(char* name){
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
/* private */ char* generate0(){
	auto local0 = /* type -> type */;
	auto local1 = this.arguments(this, /* ) */.iterate();
	auto local2 = local1.map(local1, local0.generate(local0, /* ) */.toSlice());
	auto local3 = local2.collect(local2, /* new Joiner */(", "));
	auto generatedTuple = local3.orElse(local3, "");
	return "template " + this.base() + "<" + generatedTuple + ">";
}
/* public */ template List<struct T> ListCollector::createInitial(struct ListCollector<T> this){
	return Lists.empty(Lists);
}
/* public */ template List<struct T> fold(template List<struct T> current, struct T element){
	return current.addLast(current, element);
}
/* public */ struct String_ TypeParameter::generate(struct TypeParameter this){
	return Strings.from(Strings, this.generate0(this));
}
/* private */ char* generate0(){
	return this.value;
}
/* public */ struct String_ Ref::generate(struct Ref this){
	return Strings.from(Strings, this.generate0(this));
}
/* private */ char* generate0(){
	auto local0 = this.type;
	return local0.generate(local0).toSlice() + "*";
}
/* public */ struct String_ TupleType::generate(struct TupleType this){
	return Strings.from(Strings, this.generate0(this));
}
/* private */ char* generate0(){
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
/* public */ struct String_ generate(){
	return Strings.from(Strings, this.generate0(this));
}
/* private */ char* generate0(){
	return this.value;
}
/* public static */ void main(){
	auto local0 = run();
	local0.ifPresent(local0, /* Throwable::printStackTrace */);
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
/* private static */ template Option<struct IOException> writeOutput(char* output);
/* private static */ template Result<char*, struct IOException> readInput();
/* private static */ char* compileRoot(char* input){
	auto local0 = compileAll(state, input, /*  Main::compileRootSegment */);
	auto local1 = tuple.right + tuple.left;
	auto state = /* new ImmutableCompileState */();
	auto tuple = local0.orElse(local0, /* new Tuple<> */(state, ""));
	return local1.generate(local1);
}
/* private static */ template Option<(struct CompileState, char*)> compileAll(struct CompileState initial, char* input, template Option<(struct CompileState, char*)> (*mapper)(struct CompileState, char*)){
	return all(initial, input, /*  Main::foldStatementChar */, mapper, /*  Main::mergeStatements */);
}
/* private static */ template Option<(struct CompileState, char*)> all(struct CompileState initial, char* input, struct DivideState (*folder)(struct DivideState, char), template Option<(struct CompileState, char*)> (*mapper)(struct CompileState, char*), struct StringBuilder (*merger)(struct StringBuilder, char*)){
	auto local0 = parseAll(initial, input, folder, mapper);
	return local0.map(local0, /* tuple -> new Tuple<> */(tuple.left, generateAll(merger, tuple.right)));
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
	auto local0 = /* content -> content */;
	auto local1 = /* (state1, input1) -> content */(state1, input1);
	return or(state, input, Lists.of(Lists, /* 
                Main::whitespace */, /* 
                Main::compileNamespaced */, parseClass(), local1.map(local1, Tuple.mapRight(Tuple, local0.generate(local0, /* ) */.toSlice()))));
}
/* private static */ template Option<(struct CompileState, char*)> (*parseClass)(struct CompileState, char*)(){
	return structure("class", "class ");
}
/* private static */ template Option<(struct CompileState, char*)> (*structure)(struct CompileState, char*)(char* type, char* infix){
	auto local0 = /* (beforeKeyword, afterKeyword) -> {
            var slices = Arrays */;
	auto local1 = local0.stream(local0, beforeKeyword.split(beforeKeyword, " "));
	auto local2 = /* value -> !value */;
	auto local3 = local1.map(local1, /* String::strip */);
	return /* (state, input) -> first */(input, infix, local3.filter(local3, local2.isEmpty(local2)).toList();

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
	auto local0 = /* (Value node) -> node */;
	auto local1 = /* (state1, value) -> symbol */(state1, value);
	return first(beforeContent, " permits ", /* (beforePermits, variantsString) -> {
            return parseValues */(state, variantsString, local1.map(local1, Tuple.mapRight(Tuple, local0.generate(local0, /* ) */.toSlice()))).flatMap(params -> {
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
	auto local0 = /* (state1, value) -> symbol */(state1, value);
	return suffix(beforeParams0.strip(beforeParams0), ">", /* withoutEnd -> {
            return first */(withoutEnd, "<", /* (name, typeParamString) -> {
                return parseValues */(state, typeParamString, local0.map(local0, Tuple.mapRight(Tuple, (/* Value node) -> node */.generate().toSlice()))).flatMap(values -> {
                    return structureWithName(type, values.left, beforeKeyword, name, values.right, params, variants, withEnd);
                });
            });
        });
}
/* private static */ template Option<(struct CompileState, char*)> structureWithName(char* type, struct CompileState state, char* beforeKeyword, char* name, template List<char*> typeParams, template List<struct Parameter> params, template List<char*> variants, char* withEnd){
	auto local0 = /* last -> last */;
	auto local1 = state.enter(state);
	auto local2 = /* content -> {
            final StructurePrototype type1 = new StructurePrototype(type, name, typeParams, variants);
            return compileAll */(local1.mapLastFrame(local1, local0.withProto(local0, type1)), content, /*  Main::structSegment */);
	return suffix(withEnd.strip(withEnd), "}", local2.flatMap(local2, /* tuple -> {
                if (!isSymbol(name)) {
                    return new None<>();
                }
                return new Some<>(assembleStruct(type, tuple */.left, beforeKeyword, name, typeParams, params, variants, tuple.right));
            }).map(tuple -> new Tuple<>(tuple.left.exit(), tuple.right));
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
	auto local0 = /* t -> t */;
	auto local1 = params.iterate(params);
	auto local2 = local1.map(local1, local0.generate(local0, /* ) */.toSlice());
	auto local3 = local2.map(local2, /* value -> "\n\t" + value + ";" */);
	auto local4 = local3.collect(local3, /* new Joiner */());
	auto paramsString = local4.orElse(local4, "");
	auto generatedStruct = /*  generatePlaceholder(beforeKeyword */.strip()) + "struct " + name + typeParamString + " {" + paramsString + content + "\n};\n";
	return /* new Tuple<CompileState, String> */(state.addStruct(state, generatedStruct), "");
}
/* private static */ char* generateTypeParams(template List<char*> typeParams){
	auto local0 = typeParams.isEmpty(typeParams, /* ) ? "" : "<" + typeParams */.iterate();
	return local0.collect(local0, /* new Joiner */(", ")).orElse("") + ">";
}
/* private static */ template Option<(struct CompileState, T)> or<T>(struct CompileState state, char* input, template List<template Option<(struct CompileState, T)> (*)(struct CompileState, char*)> actions){
	auto local0 = /* action -> action */;
	auto local1 = actions.iterate(actions);
	auto local2 = local1.map(local1, local0.apply(local0, state, input));
	return local2.flatMap(local2, /* Iterators::fromOptions)
                 */.next();
}
/* private static */ template Option<(struct CompileState, char*)> compileNamespaced(struct CompileState state, char* input){/* if (input.strip().startsWith("package ") || input.strip().startsWith("import ")) {
            return new Some<>(new Tuple<CompileState, String>(state, ""));
        } */
	return /* new None<> */();
}
/* private static */ template Option<(struct CompileState, char*)> structSegment(struct CompileState state, char* input){
	auto local0 = /* content -> content */;
	auto local1 = /* (state1, input1) -> content */(state1, input1);
	return or(state, input, Lists.of(Lists, /* 
                Main::whitespace */, /* 
                Main::annotation */, structure("enum", "enum "), parseClass(), structure("record", "record "), structure("interface", "interface "), /* 
                Main::method */, /* 
                Main::definitionStatement */, local1.map(local1, Tuple.mapRight(Tuple, local0.generate(local0, /* ) */.toSlice()))));
}
/* private static */ template Option<(struct CompileState, char*)> definitionStatement(struct CompileState state, char* input){
	auto local0 = /* definition -> definition */;
	auto local1 = /* withoutEnd -> definition */(state, withoutEnd);
	auto local2 = local1.map(local1, Tuple.mapRight(Tuple, local0.generate(local0, /* ) */.toSlice()));
	return suffix(input.strip(input), ";", local2.map(local2, /* value -> {
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
	auto local0 = /* (paramsString, withBraces) -> {
                return compileMethodHeader */(state, inputDefinition);
	return first(input, "(", /* (inputDefinition, withParams) -> {
            return first */(withParams, ")", local0.flatMap(local0, /* definitionTuple -> {
                    var left = definitionTuple */.left;
                    var right = definitionTuple.right;
                    var entered = left.enter().mapLastFrame(last -> right.typeParams.isEmpty() ? last : last.defineTypeParams(right.typeParams));
                    return methodWithParameters(paramsString, withBraces, definitionTuple, entered).map(tuple -> new Tuple<>(tuple.left.exit(), tuple.right));
                });
            });
        });
}
/* private static */ template Option<(struct CompileState, char*)> methodWithParameters(char* paramsString, char* withBraces, (struct CompileState, struct Definition) definitionTuple, struct CompileState state){
	auto local0 = /* outputParams -> {
            var params = outputParams */.right
                    ;
	auto local1 = local0.iterate(local0);
	auto local2 = local1.map(local1, /* Main::retainDefinition */);
	auto local3 = parseValues(state, paramsString, /*  Main::parameter */);
	return local3.flatMap(local3, local2.flatMap(local2, /* Iterators::fromOptions */).collect(new ListCollector<>());

            return Main.or(outputParams.left, withBraces, Lists.of(
                    (state0, element) -> methodWithoutContent(state0, definitionTuple.right, params, element),
                    (state0, element) -> methodWithContent(state0, definitionTuple.right, params, element)));
        });
}
/* private static */ template Option<struct Definition> retainDefinition(struct Parameter param){/* if (param instanceof Definition definition) {
            return new Some<>(definition);
        } *//* else {
            return new None<>();
        } */
}
/* private static */ template Option<(struct CompileState, template List<T>)> parseValues<T>(struct CompileState state, char* input, template Option<(struct CompileState, T)> (*compiler)(struct CompileState, char*)){
	return parseAll(state, input, /*  Main::foldValueChar */, compiler);
}
/* private static */ template Option<(struct CompileState, template List<T>)> parseAll<T>(struct CompileState initial, char* input, struct DivideState (*folder)(struct DivideState, char), template Option<(struct CompileState, T)> (*mapper)(struct CompileState, char*)){
	auto local0 = /* (maybeCurrent, segment) -> maybeCurrent */;
	auto local1 = divide(input, /*  folder)
                 */.iterate();
	return local1.<Option<Tuple<CompileState, List<T>>>>fold(local1, /* new Some<> */(/* new Tuple<CompileState, List<T>> */(initial, Lists.empty(Lists))), local0.flatMap(local0, /* state -> foldElement */(state, segment, mapper)));
}
/* private static */ template Option<(struct CompileState, template List<T>)> foldElement<T>((struct CompileState, template List<T>) state, char* segment, template Option<(struct CompileState, T)> (*mapper)(struct CompileState, char*)){
	auto local0 = mapper.apply(mapper, oldState, segment);
	auto oldState = state.left;
	auto oldCache = state.right;
	return local0.map(local0, /* result -> {
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
/* private static */ template Option<struct DivideState> foldSingleQuotes(struct DivideState state, char c){
	auto local0 = /* maybeNextTuple -> {
            var nextChar = maybeNextTuple */.left;
            var nextState = maybeNextTuple.right;
	auto local1 = local0.append(local0, /* nextChar);

            var withEscaped = nextChar == '\\'
                    ? nextState */.popAndAppend();
	auto local2 = state.append(state, /* c) */.pop();/* if (c != '\'') {
            return new None<>();
        } */
	return local2.map(local2, local1.orElse(local1, /* nextState)
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
/* private static */ char* generateNodesAsValues<T extends Node>(template List<struct T> params){
	auto local0 = /* t -> t */;
	auto local1 = params.iterate(params);
	auto local2 = local1.map(local1, local0.generate(local0, /* ) */.toSlice());
	auto local3 = local2.collect(local2, /* new Joiner */(", "));
	return local3.orElse(local3, "");
}
/* private static */ char* generateAll(struct StringBuilder (*merger)(struct StringBuilder, char*), template List<char*> right){
	auto local0 = right.iterate(right);
	return local0.fold(local0, /* new StringBuilder */(), /*  merger) */.toString();
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
	auto local0 = /* content -> {
                var newParameters = state */;
	auto local1 = /* structType -> params */;
	auto local2 = local0.findStructureType(local0);
	auto local3 = /* name -> state */;
	auto local4 = local3.findStructureType(local3);
	auto local5 = local4.map(local4, /* structureType -> structureType */.name + "::" + name);
	auto local6 = local2.map(local2, local1.addFirst(local1, /* new Definition */(/* new StructRef */(structType.name, structType.typeParams), "this"))).orElse(params);

                var paramStrings = generateNodesAsValues(newParameters);

                var newHeader = definition
                        ;
	auto local7 = local6.mapName(local6, local5.orElse(local5, /* name) */).generate().toSlice() + "(" + paramStrings + ")";

                if (definition.annotations.contains("Actual")) {
                    return new Some<>(new Tuple<>(state;
	auto local8 = /* tuple -> {
                    var removed = tuple */.left;
	auto local9 = local8.removeStatements(local8, /* );
                    var joined = removed */.left.iterate();
	auto local10 = local7.addFunction(newHeader + ";\n"), ""));
                }

                return compileAll(local7, state, content, /*  Main::functionSegment */);
	return prefix(withBraces.strip(withBraces), "{", /* withoutStart1 -> {
            return suffix */(withoutStart1, "}", local10.flatMap(local10, local9.collect(local9, /* new Joiner( */)).orElse("");

                    var generated = newHeader + "{" + joined + tuple.right + "\n}\n";
                    return new Some<>(new Tuple<>(removed.right.exit().addFunction(generated), ""));
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
	auto local0 = (_, /*  name) -> state */.findStructureType();
	return split(input.strip(input), /* new InfixSplitter */(" ", /*  Main::lastIndexOfSlice */), local0.flatMap(local0, /* structureType -> {
            if (!structureType */.name.equals(name)) {
                return new None<>();
            }

            return new Some<>(new Tuple<>(state, new Definition(Primitive.Auto, name)));
        }));
}
/* private static */ template Option<(struct CompileState, char*)> functionSegment(struct CompileState state, char* input){
	auto local0 = /* slice -> statementValue */(state0, slice);
	auto local1 = /* content -> content */;
	auto local2 = /* (state0a, input1) -> content */(state0a, input1);
	return or(state, input.strip(input), Lists.of(Lists, /* 
                Main::whitespace */, /* (state0, input1) -> suffix */(input1.strip(input1), ";", local0.map(local0, Tuple.mapRight(Tuple, /* slice0 -> "\n\t" + slice0 + ";" */))), local2.map(local2, Tuple.mapRight(Tuple, local1.generate(local1, /* ) */.toSlice()))));
}
/* private static */ template Option<(struct CompileState, char*)> statementValue(struct CompileState state, char* input){
	auto local0 = /* right -> right */;
	auto local1 = /* (state1, s) -> invocation */(state1, s);
	return Main.or(Main, state, input, Lists.of(Lists, /* 
                Main::returns */, /* 
                Main::initialization */, local1.map(local1, Tuple.mapRight(Tuple, local0.generate(local0, /* ) */.toSlice()))));
}
/* private static */ template Option<(struct CompileState, char*)> initialization(struct CompileState state, char* s){
	auto local0 = /* result -> result */;
	auto local1 = /* result0 -> {
            return value */(result0.left, s2);
	auto local2 = local1.map(local1, Tuple.mapRight(Tuple, local0.generate(local0, /* ) */.toSlice()));
	auto local3 = /* (s1, s2) -> definition */(state, s1);
	return first(s, "=", local3.flatMap(local3, local2.map(local2, /* result1 -> {
                return new Tuple<>(result1 */.left, result0.right.generate().toSlice() + " = " + result1.right());
            });
        }));
}
/* private static */ template Option<(struct CompileState, struct Value)> invocation(struct CompileState state0, char* input){
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
	auto local0 = /* result1 -> result1 */;
	auto local1 = /* slice -> value */(state, slice);
	auto local2 = local1.map(local1, Tuple.mapRight(Tuple, local0.generate(local0, /* ) */.toSlice()));
	return prefix(input.strip(input), "return ", local2.map(local2, Tuple.mapRight(Tuple, /* result -> "return " + result */)));
}
/* private static */ template Option<(struct CompileState, struct Value)> value(struct CompileState state, char* input){
	auto local0 = /* (state1, input1) -> content */(state1, input1);
	return Main.or(Main, state, input, Lists.of(Lists, /* 
                Main::stringNode */, /* 
                Main::invocation */, /* 
                Main::dataAccess */, /* 
                Main::symbol */, local0.map(local0, Tuple.mapRight(Tuple, /* right -> right */))));
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
                return new Tuple<>(tuple.left, new DataAccess(tuple.right, property));
            });
        });
}
/* private static */ template Option<(struct CompileState, struct Definition)> definition(struct CompileState state, char* input){
	auto local0 = /* (oldBeforeName, name) -> {
            if (!isSymbol(name)) {
                return new None<>();
            }

            List<String> annotations;
            String newBeforeName;
            var index = oldBeforeName */.indexOf("\n");
            if (index >= 0) {
                var stripped = oldBeforeName;
	auto local1 = /* (state1, c) -> {
                    if (c == '\'') {
                        return state1 */.advance();
                    }
                    return state1;
	auto local2 = local0.substring(local0, /* 0 */, index).strip();
                newBeforeName = oldBeforeName.substring(index + "\n";
	auto local3 = local2.length());

                annotations = divide(local2, stripped, local1.append(local1, /* c);
                } */).iterate();
	auto local4 = /* value -> value */;
	auto local5 = local3.map(local3, /* String::strip */);
	return split(input.strip(input), /* new InfixSplitter */(" ", /*  Main::lastIndexOfSlice */), local5.map(local5, local4.substring(local4, /* 1 */)).collect(new ListCollector<>());
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
/* private static */ int isSymbol(char* value){/* for (var i = 0; i < value.length(); i++) {
            var c = value.charAt(i);
            if (Character.isLetter(c) || c == '_' || (i != 0 && Character.isDigit(c))) {
                continue;
            }
            return false;
        } */
	return true;
}
/* private static */ template Option<(struct CompileState, struct Definition)> definitionWithoutTypeSeparator(struct CompileState state, char* type, char* name, template List<char*> annotations){
	return assembleDefinition(state, /* new None<String> */(), type, name, annotations, Lists.empty(Lists));
}
/* private static */ template Option<(struct CompileState, struct Definition)> assembleDefinition(struct CompileState state, template Option<char*> maybeBeforeType, char* type, char* name, template List<char*> annotations, template List<char*> typeParams){
	auto local0 = /* last -> last */;
	auto local1 = state.enter(state);
	auto local2 = type(local1.mapLastFrame(local1, local0.defineTypeParams(local0, typeParams)), type);
	return local2.map(local2, /* newType -> {
            var definition = new Definition(annotations, maybeBeforeType, typeParams, newType */.right, name.strip());
            return new Tuple<>(newType.left.exit(), definition);
        });
}
/* private static */ template Option<(struct CompileState, struct Definition)> definitionWithTypeSeparator(struct CompileState state, char* beforeName, char* name, template List<char*> annotations){
	return split(beforeName, /* new TypeSeparatorSplitter */(), /*  (beforeType, typeString) -> {
            return or(state, beforeType, Lists */.of(
                    (state2, s) -> definitionWithTypeParams(state2, annotations, s, typeString, name),
                    (state1, s) -> definitionWithoutTypeParams(name, annotations, typeString, state1, s)));
        });
}
/* private static */ template Option<(struct CompileState, struct Definition)> definitionWithTypeParams(struct CompileState state, template List<char*> annotations, char* beforeType, char* typeString, char* name){
	return suffix(beforeType.strip(beforeType), ">", /* withoutEnd -> {
            return first */(withoutEnd, "<", /* (beforeTypeParams, typeParamStrings) -> {
                var typeParams = divide */(typeParamStrings, /*  Main::foldValueChar)
                         */.iterate().map(String::strip).collect(new ListCollector<>());

                return assembleDefinition(state, new Some<>(beforeTypeParams.strip()), typeString, name, annotations, typeParams);
            });
        });
}
/* private static */ template Option<(struct CompileState, struct Definition)> definitionWithoutTypeParams(char* name, template List<char*> annotations, char* typeTuple, struct CompileState state1, char* beforeType){
	return assembleDefinition(state1, /* new Some<> */(beforeType.strip(beforeType)), typeTuple, name, annotations, Lists.empty(Lists));
}
/* private static */ template Option<(struct CompileState, struct Type)> type(struct CompileState state, char* input){
	return Main.or(Main, state, input, Lists.of(Lists, /* 
                Main::primitive */, /* 
                Main::template */, /* 
                Main::typeParam */, /* 
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
/* private static */ template Option<(struct CompileState, struct Type)> typeParam(struct CompileState state, char* input){
	auto stripped = input.strip(input);/* if (state.hasTypeParam(stripped)) {
            return new Some<>(new Tuple<>(state, new TypeParameter(stripped)));
        } */
	return /* new None<> */();
}
/* private static */ template Option<(struct CompileState, S)> (*wrap)(struct CompileState, char*)<S, T extends S>(template Option<(struct CompileState, struct T)> (*content)(struct CompileState, char*)){
	auto local0 = /* (state, input) -> content */;
	auto local1 = local0.apply(local0, state, input);
	return local1.map(local1, Tuple.mapRight(Tuple, /* value -> value */));
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
/* private static */ template Option<T> suffix<T>(char* input, char* suffix, template Option<T> (*mapper)(char*)){/* if (!input.endsWith(suffix)) {
            return new None<>();
        } */
	auto slice = input.substring(input, /* 0 */, input.length(input, /* ) - suffix */.length());
	return mapper.apply(mapper, slice);
}
/* private static */ char* generatePlaceholder(char* input){
	return "/* " + input + " */";
}
/* private static */ template Option<T> first<T>(char* input, char* infix, template Option<T> (*mapper)(char*, char*)){
	return split(input, /* new InfixSplitter */(infix, /*  Main::firstIndexOfSlice */), mapper);
}
/* private static */ template Option<T> split<T>(char* input, struct Splitter splitter, template Option<T> (*mapper)(char*, char*)){
	auto local0 = splitter.split(splitter, input);
	return local0.flatMap(local0, /* tuple -> {
            return mapper */.apply(tuple.left, tuple.right);
        });
}
/* private static */ template Option<int> firstIndexOfSlice(char* input, char* infix){
	auto index = input.indexOf(input, infix);
	return /* index == -1 ? new None<Integer>() : new Some<> */(index);
}
