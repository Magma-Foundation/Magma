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
	auto local2 = this.map(this, mapper);
	return local2.fold(local2, /* new Iterator<> */(/* new EmptyHead<> */()), /*  Iterator::concat */);
}
/* public */ template Iterator<struct R> Iterator::map<R>(struct Iterator<T> this, struct R (*mapper)(T)){
	auto local3 = (/* ) -> this */.head.next();
	return /* new Iterator<> */(local3.map(local3, mapper));
}
/* private */ template Iterator<T> Iterator::concat(struct Iterator<T> this, template Iterator<T> other){
	auto local4 = (/* ) -> this */.head.next();
	return /* new Iterator<> */(local4.or(local4, /* other::next */));
}
/* public */ template Option<T> Iterator::next(struct Iterator<T> this){
	auto local5 = this.head;
	return local5.next(local5);
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
	auto local6 = current.map(current, /* inner -> inner + this */.delimiter + element);
	return /* new Some<> */(local6.orElse(local6, element));
}
struct public ImmutableCompileState::ImmutableCompileState(struct ImmutableCompileState this){
	this(Lists.empty(Lists), Lists.empty(Lists), Lists.empty(Lists), /* new None<> */(), /*  0 */);
}
/* public */ char* ImmutableCompileState::generate(struct ImmutableCompileState this){
	auto local7 = /* join(this */;
	return local7.structs) + join(local7, this.functions);
}
/* private static */ char* ImmutableCompileState::join(struct ImmutableCompileState this, template List<char*> lists){
	auto local8 = lists.iterate(lists);
	auto local9 = local8.collect(local8, /* new Joiner */());
	return local9.orElse(local9, "");
}
/* public */ struct CompileState ImmutableCompileState::addStruct(struct ImmutableCompileState this, char* struct){
	auto local10 = this.structs;
	return /* new ImmutableCompileState */(local10.addLast(local10, struct), this.functions, this.statements, this.maybeStructureType, this.counter);
}
/* public */ struct CompileState ImmutableCompileState::addFunction(struct ImmutableCompileState this, char* function){
	auto local11 = this.functions;
	return /* new ImmutableCompileState */(this.structs, local11.addLast(local11, function), this.statements, this.maybeStructureType, this.counter);
}
/* public */ struct CompileState ImmutableCompileState::withStructType(struct ImmutableCompileState this, struct StructurePrototype type){
	return /* new ImmutableCompileState */(this.structs, this.functions, this.statements, /* new Some<> */(type), this.counter);
}
/* public */ struct CompileState ImmutableCompileState::withoutStructType(struct ImmutableCompileState this){
	return /* new ImmutableCompileState */(this.structs, this.functions, this.statements, /* new None<> */(), this.counter);
}
/* public */ template Option<struct StructurePrototype> ImmutableCompileState::findStructureType(struct ImmutableCompileState this){
	return this.maybeStructureType;
}
/* public */ (char*, struct CompileState) ImmutableCompileState::createLocalName(struct ImmutableCompileState this){
	auto name = /*  "local" + this */.counter;
	return /* new Tuple<> */(name, /* new ImmutableCompileState */(this.structs, this.functions, this.statements, this.maybeStructureType, this.counter + 1));
}
/* public */ struct CompileState ImmutableCompileState::addStatement(struct ImmutableCompileState this, char* statement){
	auto local12 = this.statements;
	return /* new ImmutableCompileState */(this.structs, this.functions, local12.addLast(local12, statement), this.maybeStructureType, this.counter);
}
/* public */ (template List<char*>, struct CompileState) ImmutableCompileState::removeStatements(struct ImmutableCompileState this){
	return /* new Tuple<> */(this.statements, /* new ImmutableCompileState */(this.structs, this.functions, Lists.empty(Lists), this.maybeStructureType, this.counter));
}
struct public DivideState::DivideState(struct DivideState this, char* input){
	this(input, /* new JavaList<> */(), /* new StringBuilder */(), /*  0 */, /*  0 */);
}
/* private */ template Option<struct DivideState> DivideState::popAndAppend(struct DivideState this){
	auto local13 = this.popAndAppendToTuple(this);
	return local13.map(local13, /* Tuple::right */);
}
/* private */ template Option<(char, struct DivideState)> DivideState::popAndAppendToTuple(struct DivideState this){
	auto local14 = this.pop(this);
	return local14.map(local14, /* tuple -> {
                var c = tuple */.left;
                var state = tuple.right;
                return new Tuple<>(c, state.append(c));
            });
}
/* private */ struct DivideState DivideState::append(struct DivideState this, char c){
	auto local15 = this.buffer;
	return /* new DivideState */(this.input, this.segments, local15.append(local15, c), this.index, this.depth);
}
/* public */ template Option<(char, struct DivideState)> DivideState::pop(struct DivideState this){/* if (this.index < this.input.length()) {
                var c = this.input.charAt(this.index);
                return new Some<>(new Tuple<Character, DivideState>(c, new DivideState(this.input, this.segments, this.buffer, this.index + 1, this.depth)));
            } *//* else {
                return new None<>();
            } */
}
/* private */ struct DivideState DivideState::advance(struct DivideState this){
	auto local16 = this.buffer;
	auto local17 = this.buffer.isEmpty() ? this.segments : this.segments;
	auto withBuffer = local17.addLast(local17, local16.toString(local16));
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
	auto local18 = option.<Head<T>>map(option, /* SingleHead::new */);
	return /* new Iterator<> */(local18.orElseGet(local18, /* EmptyHead::new */));
}
struct public SingleHead::SingleHead(struct SingleHead<T> this, T value){/* this.value = value; *//* this.retrieved = false; */
}
/* public */ template Option<T> SingleHead::next(struct SingleHead<T> this){/* if (this.retrieved) {
                return new None<>();
            } *//* this.retrieved = true; */
	return /* new Some<> */(this.value);
}
/* public */ template Option<(char*, char*)> InfixSplitter::split(struct InfixSplitter this, char* input){
	auto local19 = this.apply(this, input);
	return local19.map(local19, /* classIndex -> {
                var beforeKeyword = input */.substring(0, classIndex);
                var afterKeyword = input.substring(classIndex + this.length());
                return new Tuple<>(beforeKeyword, afterKeyword);
            });
}
/* private */ int InfixSplitter::length(struct InfixSplitter this){
	auto local20 = this.infix;
	return local20.length(local20);
}
/* private */ template Option<int> InfixSplitter::apply(struct InfixSplitter this, char* input){
	auto local21 = this.locator(this);
	return local21.apply(local21, input, this.infix);
}
/* public */ template Option<(char*, char*)> TypeSeparatorSplitter::split(struct TypeSeparatorSplitter this, char* input){
	auto local22 = /* segments -> {
                var left = segments */.left;
                if (left;
	auto local23 = local22.isEmpty()) {
                    return new None<>(local22, /* );
                }

                var beforeType = left */.iterate();
	auto local24 = divide(input, /*  TypeSeparatorSplitter::fold) */.removeLast();
	return local24.flatMap(local24, local23.collect(local23, /* new Joiner */(" ")).orElse("");
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
	auto local25 = this.typeParams;
	auto local26 = local25.iterate(local25);
	auto local27 = local26.collect(local26, /* new Joiner */(", "));
	auto local28 = local27.map(local27, /* inner -> "<" + inner + ">" */);
	auto local29 = this.maybeBeforeType;
	auto local30 = local29.map(local29, /* beforeType -> generatePlaceholder(beforeType) + " " */);
	auto local31 = /* beforeTypeString + this */.type;
	auto typeParamString = local28.orElse(local28, "");
	auto beforeTypeString = local30.orElse(local30, "");
	return Strings.from(Strings, local31.generateWithName(local31, this.name).toSlice() + typeParamString);
}
/* public */ struct String_ Content::generate(struct Content this){
	return Strings.from(Strings, generatePlaceholder(this.input));
}
/* public */ struct String_ Functional::generate(struct Functional this){
	return this.generateWithName(this, "");
}
/* public */ struct String_ Functional::generateWithName(struct Functional this, char* name){
	auto local32 = /* type -> type */;
	auto local33 = this.arguments(this, /* ) */.iterate();
	auto local34 = local33.map(local33, local32.generate(local32, /* ) */.toSlice());
	auto local35 = local34.collect(local34, /* new Joiner */(", "));
	auto local36 = this.returns;
	auto local37 = local36.generate(local36);
	auto local38 = local37.appendSlice(local37, " (*");
	auto local39 = local38.appendSlice(local38, name);
	auto local40 = local39.appendSlice(local39, ")(");
	auto local41 = local40.appendSlice(local40, joinedArguments);
	auto joinedArguments = local35.orElse(local35, "");
	return local41.appendSlice(local41, ")");
}
/* public */ struct String_ Template::generate(struct Template this){
	return Strings.from(Strings, this.generate0(this));
}
/* private */ char* Template::generate0(struct Template this){
	auto local42 = /* type -> type */;
	auto local43 = this.arguments(this, /* ) */.iterate();
	auto local44 = local43.map(local43, local42.generate(local42, /* ) */.toSlice());
	auto local45 = local44.collect(local44, /* new Joiner */(", "));
	auto generatedTuple = local45.orElse(local45, "");
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
	auto local46 = this.type;
	return local46.generate(local46).toSlice() + "*";
}
/* public */ struct String_ TupleType::generate(struct TupleType this){
	return Strings.from(Strings, this.generate0(this));
}
/* private */ char* TupleType::generate0(struct TupleType this){
	return "(" + generateNodesAsValues(this.arguments) + ")";
}
/* public */ struct String_ StructRef::generate(struct StructRef this){
	auto local47 = this.typeParams;
	auto local48 = local47.iterate(local47);
	auto local49 = local48.collect(local48, /* new Joiner */(", "));
	auto local50 = local49.map(local49, /* inner -> "<" + inner + ">" */);
	auto local51 = Strings.from(Strings, "struct ");
	auto local52 = local51.appendSlice(local51, this.input);
	auto typeParamString = local50.orElse(local50, "");
	return local52.appendSlice(local52, typeParamString);
}
/* public */ template Option<(char*, char*)> DividingSplitter::split(struct DividingSplitter this, char* input){
	auto local53 = /* divisions -> {
                var left1 = divisions */.left;
                if (left1;
	auto local54 = local53.isEmpty()) {
                    return new Tuple<>(local53, divisions.right, /*  "");
                }

                var left = left1 */.iterate();
	auto local55 = divide(input, this.folder).removeLast();
	return local55.map(local55, local54.collect(local54, /* new Joiner */()).orElse("");
                var right = divisions.right;
                return new Tuple<>(left, right);
            });
}
/* public */ struct String_ Symbol::generate(struct Symbol this){
	return Strings.from(Strings, this.value);
}
/* public */ struct String_ StringNode::generate(struct StringNode this){
	auto local56 = Strings.from(Strings, "\"");
	auto local57 = local56.appendSlice(local56, this.value);
	return local57.appendSlice(local57, "\"");
}
/* public */ struct String_ Invocation::generate(struct Invocation this){
	auto local58 = this.arguments(this, /* ) */.iterate();
	auto local59 = local58.map(local58, /* Node::generate */);
	auto local60 = local59.map(local59, /* String_::toSlice */);
	auto local61 = local60.collect(local60, /* new Joiner */(", "));
	auto joined = local61.orElse(local61, "");
	return Strings.from(Strings, this.caller(this, /* ) */.generate().toSlice() + "(" + joined + ")");
}
/* public */ struct String_ DataAccess::generate(struct DataAccess this){
	auto local62 = this.parent(this, /* ) */.generate();
	return Strings.from(Strings, local62.toSlice(local62, /* ) + " */." + this.property());
}
auto Primitive::Primitive(struct Primitive this, char* value){/* this.value = value; */
}
/* public */ struct String_ Primitive::generate(struct Primitive this){
	return Strings.from(Strings, this.generate0(this));
}
/* private */ char* Primitive::generate0(struct Primitive this){
	return this.value;
}
/* public static */ void main(){
	auto local63 = run();
	local63.ifPresent(local63, /* Throwable::printStackTrace */);
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
	auto local64 = compileAll(state, input, /*  Main::compileRootSegment */);
	auto local65 = tuple.right + tuple.left;
	auto state = /* new ImmutableCompileState */();
	auto tuple = local64.orElse(local64, /* new Tuple<> */(state, ""));
	return local65.generate(local65);
}
/* private static */ template Option<(struct CompileState, char*)> compileAll(struct CompileState initial, char* input, template Option<(struct CompileState, char*)> (*mapper)(struct CompileState, char*)){
	return all(initial, input, /*  Main::foldStatementChar */, mapper, /*  Main::mergeStatements */);
}
/* private static */ template Option<(struct CompileState, char*)> all(struct CompileState initial, char* input, struct DivideState (*folder)(struct DivideState, char), template Option<(struct CompileState, char*)> (*mapper)(struct CompileState, char*), struct StringBuilder (*merger)(struct StringBuilder, char*)){
	auto local66 = parseAll(initial, input, folder, mapper);
	return local66.map(local66, /* tuple -> new Tuple<> */(tuple.left, generateAll(merger, tuple.right)));
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
	auto local67 = /* content -> content */;
	auto local68 = /* (state1, input1) -> content */(state1, input1);
	return or(state, input, Lists.of(Lists, /* 
                Main::whitespace */, /* 
                Main::compileNamespaced */, parseClass(), local68.map(local68, Tuple.mapRight(Tuple, local67.generate(local67, /* ) */.toSlice()))));
}
/* private static */ template Option<(struct CompileState, char*)> (*parseClass)(struct CompileState, char*)(){
	return structure("class", "class ");
}
/* private static */ template Option<(struct CompileState, char*)> (*structure)(struct CompileState, char*)(char* type, char* infix){
	auto local69 = /* (beforeKeyword, afterKeyword) -> {
            var slices = Arrays */;
	auto local70 = local69.stream(local69, beforeKeyword.split(beforeKeyword, " "));
	auto local71 = /* value -> !value */;
	auto local72 = local70.map(local70, /* String::strip */);
	return /* (state, input) -> first */(input, infix, local72.filter(local72, local71.isEmpty(local71)).toList();

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
	auto local73 = /* (Value node) -> node */;
	auto local74 = /* (state1, value) -> symbol */(state1, value);
	return first(beforeContent, " permits ", /* (beforePermits, variantsString) -> {
            return parseValues */(state, variantsString, local74.map(local74, Tuple.mapRight(Tuple, local73.generate(local73, /* ) */.toSlice()))).flatMap(params -> {
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
	auto local75 = /* (state1, value) -> symbol */(state1, value);
	return suffix(beforeParams0.strip(beforeParams0), ">", /* withoutEnd -> {
            return first */(withoutEnd, "<", /* (name, typeParamString) -> {
                return parseValues */(state, typeParamString, local75.map(local75, Tuple.mapRight(Tuple, (/* Value node) -> node */.generate().toSlice()))).flatMap(values -> {
                    return structureWithName(type, values.left, beforeKeyword, name, values.right, params, variants, withEnd);
                });
            });
        });
}
/* private static */ template Option<(struct CompileState, char*)> structureWithName(char* type, struct CompileState state, char* beforeKeyword, char* name, template List<char*> typeParams, template List<struct Parameter> params, template List<char*> variants, char* withEnd){
	auto local76 = /* content -> {
            return compileAll */(state.withStructType(state, /* new StructurePrototype */(type, name, typeParams, variants)), content, /*  Main::structSegment */);
	return suffix(withEnd.strip(withEnd), "}", local76.flatMap(local76, /* tuple -> {
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
	auto local77 = /* t -> t */;
	auto local78 = params.iterate(params);
	auto local79 = local78.map(local78, local77.generate(local77, /* ) */.toSlice());
	auto local80 = local79.map(local79, /* value -> "\n\t" + value + ";" */);
	auto local81 = local80.collect(local80, /* new Joiner */());
	auto paramsString = local81.orElse(local81, "");
	auto generatedStruct = /*  generatePlaceholder(beforeKeyword */.strip()) + "struct " + name + typeParamString + " {" + paramsString + content + "\n};\n";
	return /* new Tuple<CompileState, String> */(state.addStruct(state, generatedStruct), "");
}
/* private static */ char* generateTypeParams(template List<char*> typeParams){
	auto local82 = typeParams.isEmpty(typeParams, /* ) ? "" : "<" + typeParams */.iterate();
	return local82.collect(local82, /* new Joiner */(", ")).orElse("") + ">";
}
/* private static */ template Option<(struct CompileState, struct T)> or<T>(struct CompileState state, char* input, template List<template Option<(struct CompileState, struct T)> (*)(struct CompileState, char*)> actions){
	auto local83 = /* action -> action */;
	auto local84 = actions.iterate(actions);
	auto local85 = local84.map(local84, local83.apply(local83, state, input));
	return local85.flatMap(local85, /* Iterators::fromOptions)
                 */.next();
}
/* private static */ template Option<(struct CompileState, char*)> compileNamespaced(struct CompileState state, char* input){/* if (input.strip().startsWith("package ") || input.strip().startsWith("import ")) {
            return new Some<>(new Tuple<CompileState, String>(state, ""));
        } */
	return /* new None<> */();
}
/* private static */ template Option<(struct CompileState, char*)> structSegment(struct CompileState state, char* input){
	auto local86 = /* content -> content */;
	auto local87 = /* (state1, input1) -> content */(state1, input1);
	return or(state, input, Lists.of(Lists, /* 
                Main::whitespace */, /* 
                Main::annotation */, structure("enum", "enum "), parseClass(), structure("record", "record "), structure("interface", "interface "), /* 
                Main::method */, /* 
                Main::definitionStatement */, local87.map(local87, Tuple.mapRight(Tuple, local86.generate(local86, /* ) */.toSlice()))));
}
/* private static */ template Option<(struct CompileState, char*)> definitionStatement(struct CompileState state, char* input){
	auto local88 = /* definition -> definition */;
	auto local89 = /* withoutEnd -> definition */(state, withoutEnd);
	auto local90 = local89.map(local89, Tuple.mapRight(Tuple, local88.generate(local88, /* ) */.toSlice()));
	return suffix(input.strip(input), ";", local90.map(local90, /* value -> {
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
	auto local91 = /* outputDefinition -> {
                    return parseValues */(outputDefinition.left, paramsString, /*  Main::parameter */);
	auto local92 = /* (paramsString, withBraces) -> {
                return compileMethodHeader */(state, inputDefinition);
	return first(input, "(", /* (inputDefinition, withParams) -> {
            return first */(withParams, ")", local92.flatMap(local92, local91.flatMap(local91, /* outputParams -> {
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
/* private static */ template Option<(struct CompileState, template List<struct T>)> parseValues<T>(struct CompileState state, char* input, template Option<(struct CompileState, struct T)> (*compiler)(struct CompileState, char*)){
	return parseAll(state, input, /*  Main::foldValueChar */, compiler);
}
/* private static */ template Option<(struct CompileState, template List<struct T>)> parseAll<T>(struct CompileState initial, char* input, struct DivideState (*folder)(struct DivideState, char), template Option<(struct CompileState, struct T)> (*mapper)(struct CompileState, char*)){
	auto local93 = /* (maybeCurrent, segment) -> maybeCurrent */;
	auto local94 = divide(input, /*  folder)
                 */.iterate();
	return local94.<Option<Tuple<CompileState, List<T>>>>fold(local94, /* new Some<> */(/* new Tuple<CompileState, List<T>> */(initial, Lists.empty(Lists))), local93.flatMap(local93, /* state -> foldElement */(state, segment, mapper)));
}
/* private static */ template Option<(struct CompileState, template List<struct T>)> foldElement<T>((struct CompileState, template List<struct T>) state, char* segment, template Option<(struct CompileState, struct T)> (*mapper)(struct CompileState, char*)){
	auto local95 = mapper.apply(mapper, oldState, segment);
	auto oldState = state.left;
	auto oldCache = state.right;
	return local95.map(local95, /* result -> {
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
	auto local96 = /* maybeNextTuple -> {
            var nextChar = maybeNextTuple */.left;
            var nextState = maybeNextTuple.right;
	auto local97 = local96.append(local96, /* nextChar);

            var withEscaped = nextChar == '\\'
                    ? nextState */.popAndAppend();
	auto local98 = state.append(state, /* c) */.pop();/* if (c != '\'') {
            return new None<>();
        } */
	return local98.map(local98, local97.orElse(local97, /* nextState)
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
	auto local99 = /* t -> t */;
	auto local100 = params.iterate(params);
	auto local101 = local100.map(local100, local99.generate(local99, /* ) */.toSlice());
	auto local102 = local101.collect(local101, /* new Joiner */(", "));
	return local102.orElse(local102, "");
}
/* private static */ char* generateAll(struct StringBuilder (*merger)(struct StringBuilder, char*), template List<char*> right){
	auto local103 = right.iterate(right);
	return local103.fold(local103, /* new StringBuilder */(), /*  merger) */.toString();
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
	auto local104 = /* content -> {
                var newParameters = state */;
	auto local105 = /* structType -> params */;
	auto local106 = local104.findStructureType(local104);
	auto local107 = /* name -> state */;
	auto local108 = local107.findStructureType(local107);
	auto local109 = local108.map(local108, /* structureType -> structureType */.name + "::" + name);
	auto local110 = local106.map(local106, local105.addFirst(local105, /* new Definition */(/* new StructRef */(structType.name, structType.typeParams), "this"))).orElse(params);

                var paramStrings = generateNodesAsValues(newParameters);

                var newHeader = definition
                        ;
	auto local111 = local110.mapName(local110, local109.orElse(local109, /* name) */).generate().toSlice() + "(" + paramStrings + ")";

                if (definition.annotations.contains("Actual")) {
                    return new Some<>(new Tuple<>(state;
	auto local112 = /* tuple -> {
                    var removed = tuple */.left;
	auto local113 = local111.addFunction(newHeader + ";\n"), ""));
                }

                return compileAll(local111, state, content, /*  Main::functionSegment */);
	return prefix(withBraces.strip(withBraces), "{", /* withoutStart1 -> {
            return suffix */(withoutStart1, "}", local113.flatMap(local113, local112.removeStatements(local112, /* );
                    var joined = removed */.left.iterate().collect(new Joiner()).orElse("");

                    var generated = newHeader + "{" + joined + tuple.right + "\n}\n";
                    return new Some<>(new Tuple<>(removed.right.addFunction(generated), ""));
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
	auto local114 = (_, /*  name) -> state */.findStructureType();
	return split(input.strip(input), /* new InfixSplitter */(" ", /*  Main::lastIndexOfSlice */), local114.flatMap(local114, /* structureType -> {
            if (!structureType */.name.equals(name)) {
                return new None<>();
            }

            return new Some<>(new Tuple<>(state, new Definition(Primitive.Auto, name)));
        }));
}
/* private static */ template Option<(struct CompileState, char*)> functionSegment(struct CompileState state, char* input){
	auto local115 = /* slice -> statementValue */(state0, slice);
	auto local116 = /* content -> content */;
	auto local117 = /* (state0a, input1) -> content */(state0a, input1);
	return or(state, input.strip(input), Lists.of(Lists, /* 
                Main::whitespace */, /* (state0, input1) -> suffix */(input1.strip(input1), ";", local115.map(local115, Tuple.mapRight(Tuple, /* slice0 -> "\n\t" + slice0 + ";" */))), local117.map(local117, Tuple.mapRight(Tuple, local116.generate(local116, /* ) */.toSlice()))));
}
/* private static */ template Option<(struct CompileState, char*)> statementValue(struct CompileState state, char* input){
	auto local118 = /* right -> right */;
	auto local119 = /* (state1, s) -> invocation */(state1, s);
	return Main.or(Main, state, input, Lists.of(Lists, /* 
                Main::returns */, /* 
                Main::initialization */, local119.map(local119, Tuple.mapRight(Tuple, local118.generate(local118, /* ) */.toSlice()))));
}
/* private static */ template Option<(struct CompileState, char*)> initialization(struct CompileState state, char* s){
	auto local120 = /* result -> result */;
	auto local121 = /* result0 -> {
            return value */(result0.left, s2);
	auto local122 = local121.map(local121, Tuple.mapRight(Tuple, local120.generate(local120, /* ) */.toSlice()));
	auto local123 = /* (s1, s2) -> definition */(state, s1);
	return first(s, "=", local123.flatMap(local123, local122.map(local122, /* result1 -> {
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
	auto local124 = /* result1 -> result1 */;
	auto local125 = /* slice -> value */(state, slice);
	auto local126 = local125.map(local125, Tuple.mapRight(Tuple, local124.generate(local124, /* ) */.toSlice()));
	return prefix(input.strip(input), "return ", local126.map(local126, Tuple.mapRight(Tuple, /* result -> "return " + result */)));
}
/* private static */ template Option<(struct CompileState, struct Value)> value(struct CompileState state, char* input){
	auto local127 = /* (state1, input1) -> content */(state1, input1);
	return Main.or(Main, state, input, Lists.of(Lists, /* 
                Main::stringNode */, /* 
                Main::invocation */, /* 
                Main::dataAccess */, /* 
                Main::symbol */, local127.map(local127, Tuple.mapRight(Tuple, /* right -> right */))));
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
	auto local128 = /* (oldBeforeName, name) -> {
            if (!isSymbol(name)) {
                return new None<>();
            }

            List<String> annotations;
            String newBeforeName;
            var index = oldBeforeName */.indexOf("\n");
            if (index >= 0) {
                var stripped = oldBeforeName;
	auto local129 = /* (state1, c) -> {
                    if (c == '\'') {
                        return state1 */.advance();
                    }
                    return state1;
	auto local130 = local128.substring(local128, /* 0 */, index).strip();
                newBeforeName = oldBeforeName.substring(index + "\n";
	auto local131 = local130.length());

                annotations = divide(local130, stripped, local129.append(local129, /* c);
                } */).iterate();
	auto local132 = /* value -> value */;
	auto local133 = local131.map(local131, /* String::strip */);
	return split(input.strip(input), /* new InfixSplitter */(" ", /*  Main::lastIndexOfSlice */), local133.map(local133, local132.substring(local132, /* 1 */)).collect(new ListCollector<>());
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
	auto local134 = type(state, type);
	return local134.flatMap(local134, /* typeTuple -> {
            return assembleDefinition(typeTuple */.left, new None<String>(), typeTuple.right, name, annotations, Lists.empty());
        });
}
/* private static */ template Option<(struct CompileState, struct Definition)> assembleDefinition(struct CompileState state, template Option<char*> maybeBeforeType, struct Type type, char* name, template List<char*> annotations, template List<char*> typeParams){
	auto definition = /* new Definition */(annotations, maybeBeforeType, typeParams, type, name.strip(name));
	return /* new Some<> */(/* new Tuple<> */(state, definition));
}
/* private static */ template Option<(struct CompileState, struct Definition)> definitionWithTypeSeparator(struct CompileState state, char* beforeName, char* name, template List<char*> annotations){
	return split(beforeName, /* new TypeSeparatorSplitter */(), /* (beforeType, typeString) -> {
            return type */(state, typeString).flatMap(typeTuple -> {
                return or(typeTuple.left, beforeType, Lists.of(
                        (state2, s) -> definitionWithTypeParams(name, annotations, typeTuple, state2, s),
                        (state1, s) -> definitionWithoutTypeParams(name, annotations, typeTuple, state1, s)
                ));
            });
        });
}
/* private static */ template Option<(struct CompileState, struct Definition)> definitionWithTypeParams(char* name, template List<char*> annotations, (struct CompileState, struct Type) typeTuple, struct CompileState state2, char* input){
	return suffix(input.strip(input), ">", /* withoutEnd -> {
            return first */(withoutEnd, "<", /* (beforeTypeParams, typeParamStrings) -> {
                var typeParams = divide */(typeParamStrings, /*  Main::foldValueChar)
                         */.iterate().map(String::strip).collect(new ListCollector<>());

                return assembleDefinition(state2, new Some<>(beforeTypeParams.strip()), typeTuple.right, name, annotations, typeParams);
            });
        });
}
/* private static */ template Option<(struct CompileState, struct Definition)> definitionWithoutTypeParams(char* name, template List<char*> annotations, (struct CompileState, struct Type) typeTuple, struct CompileState state1, char* beforeType){
	return assembleDefinition(state1, /* new Some<> */(beforeType.strip(beforeType)), typeTuple.right, name, annotations, Lists.empty(Lists));
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
/* private static */ template Option<(struct CompileState, struct Type)> typeParameter(struct CompileState state, char* input){/* if (state.findStructureType() instanceof Some(var structureType)) {
            var stripped = input.strip();
            if (structureType.typeParams.contains(stripped)) {
                return new Some<>(new Tuple<>(state, new TypeParameter(stripped)));
            }
        } */
	return /* new None<> */();
}
/* private static */ template Option<(struct CompileState, struct S)> (*wrap)(struct CompileState, char*)<S, T extends S>(template Option<(struct CompileState, struct T)> (*content)(struct CompileState, char*)){
	auto local135 = /* (state, input) -> content */;
	auto local136 = local135.apply(local135, state, input);
	return local136.map(local136, Tuple.mapRight(Tuple, /* value -> value */));
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
/* private static */ template Option<struct T> suffix<T>(char* input, char* suffix, template Option<struct T> (*mapper)(char*)){/* if (!input.endsWith(suffix)) {
            return new None<>();
        } */
	auto slice = input.substring(input, /* 0 */, input.length(input, /* ) - suffix */.length());
	return mapper.apply(mapper, slice);
}
/* private static */ char* generatePlaceholder(char* input){
	return "/* " + input + " */";
}
/* private static */ template Option<struct T> first<T>(char* input, char* infix, template Option<struct T> (*mapper)(char*, char*)){
	return split(input, /* new InfixSplitter */(infix, /*  Main::firstIndexOfSlice */), mapper);
}
/* private static */ template Option<struct T> split<T>(char* input, struct Splitter splitter, template Option<struct T> (*mapper)(char*, char*)){
	auto local137 = splitter.split(splitter, input);
	return local137.flatMap(local137, /* tuple -> {
            return mapper */.apply(tuple.left, tuple.right);
        });
}
/* private static */ template Option<int> firstIndexOfSlice(char* input, char* infix){
	auto index = input.indexOf(input, infix);
	return /* index == -1 ? new None<Integer>() : new Some<> */(index);
}
