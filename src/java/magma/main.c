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
	auto local2 = this.map(this, mapper);
	return local2.fold(local2, /* new Iterator<> */(/* new EmptyHead<> */()), /*  Iterator::concat */);
}
/* public <R> */ template Iterator<struct R> Iterator::map(struct Iterator<T> this, struct R (*mapper)(T)){
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
	auto local6 = current.map(current, /* inner -> inner + this */.delimiter + element);
	return /* new Some<> */(local6.orElse(local6, element));
}
struct public ImmutableCompileState::ImmutableCompileState(struct ImmutableCompileState this){
	this(Lists.empty(Lists), Lists.empty(Lists), Lists.empty(Lists), /* new None<> */(), /*  0 */);
}
/* @Override
 public */ char* ImmutableCompileState::generate(struct ImmutableCompileState this){
	auto local7 = /* join(this */;
	return local7.structs) + join(local7, this.functions);
}
/* private static */ char* ImmutableCompileState::join(struct ImmutableCompileState this, template List<char*> lists){
	auto local8 = lists.iterate(lists);
	auto local9 = local8.collect(local8, /* new Joiner */());
	return local9.orElse(local9, "");
}
/* @Override
 public */ struct CompileState ImmutableCompileState::addStruct(struct ImmutableCompileState this, char* struct){
	auto local10 = this.structs;
	return /* new ImmutableCompileState */(local10.addLast(local10, struct), this.functions, this.statements, this.maybeStructureType, this.counter);
}
/* @Override
 public */ struct CompileState ImmutableCompileState::addFunction(struct ImmutableCompileState this, char* function){
	auto local11 = this.functions;
	return /* new ImmutableCompileState */(this.structs, local11.addLast(local11, function), this.statements, this.maybeStructureType, this.counter);
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
	auto local12 = this.statements;
	return /* new ImmutableCompileState */(this.structs, this.functions, local12.addLast(local12, statement), this.maybeStructureType, this.counter);
}
/* @Override
 public */ (template List<char*>, struct CompileState) ImmutableCompileState::removeStatements(struct ImmutableCompileState this){
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
/* public static <A, B, C> */ (A, struct C) (*Tuple::mapRight)((A, B))(struct Tuple<A, B> this, struct C (*mapper)(B)){
	return /* tuple -> new Tuple<> */(tuple.left, mapper.apply(mapper, tuple.right));
}
/* public static <T> */ template Iterator<struct T> Iterators::fromOptions(struct Iterators this, template Option<struct T> option){
	auto local18 = option.<Head<T>>map(option, /* SingleHead::new */);
	return /* new Iterator<> */(local18.orElseGet(local18, /* EmptyHead::new */));
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
/* @Override
 public */ template Option<(char*, char*)> TypeSeparatorSplitter::split(struct TypeSeparatorSplitter this, char* input){
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
	auto local25 = this.maybeBeforeType;
	auto local26 = local25.map(local25, /* beforeType -> generatePlaceholder(beforeType) + " " */);
	auto local27 = /* beforeTypeString + this */.type;
	auto beforeTypeString = local26.orElse(local26, "");
	return local27.generateWithName(local27, this.name).toSlice();
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
	auto local28 = /* type -> type */;
	auto local29 = this.arguments(this, /* ) */.iterate();
	auto local30 = local29.map(local29, local28.generate(local28, /* ) */.toSlice());
	auto local31 = local30.collect(local30, /* new Joiner */(", "));
	auto local32 = this.returns;
	auto local33 = local32.generate(local32);
	auto local34 = local33.appendSlice(local33, " (*");
	auto local35 = local34.appendSlice(local34, name);
	auto local36 = local35.appendSlice(local35, ")(");
	auto local37 = local36.appendSlice(local36, joinedArguments);
	auto joinedArguments = local31.orElse(local31, "");
	return local37.appendSlice(local37, ")");
}
/* @Override
 public */ struct String_ Template::generate(struct Template this){
	return Strings.from(Strings, this.generate0(this));
}
/* private */ char* Template::generate0(struct Template this){
	auto local38 = /* type -> type */;
	auto local39 = this.arguments(this, /* ) */.iterate();
	auto local40 = local39.map(local39, local38.generate(local38, /* ) */.toSlice());
	auto local41 = local40.collect(local40, /* new Joiner */(", "));
	auto generatedTuple = local41.orElse(local41, "");
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
	auto local42 = this.type;
	return local42.generate(local42).toSlice() + "*";
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
	auto local43 = this.typeParams;
	auto local44 = local43.iterate(local43);
	auto local45 = local44.collect(local44, /* new Joiner */(", "));
	auto local46 = local45.map(local45, /* inner -> "<" + inner + ">" */);
	auto local47 = Strings.from(Strings, "struct ");
	auto local48 = local47.appendSlice(local47, this.input);
	auto typeParamString = local46.orElse(local46, "");
	return local48.appendSlice(local48, typeParamString);
}
/* @Override
 public */ template Option<(char*, char*)> DividingSplitter::split(struct DividingSplitter this, char* input){
	auto local49 = /* divisions -> {
                var left1 = divisions */.left;
                if (left1;
	auto local50 = local49.isEmpty()) {
                    return new Tuple<>(local49, divisions.right, /*  "");
                }

                var left = left1 */.iterate();
	auto local51 = divide(input, this.folder).removeLast();
	return local51.map(local51, local50.collect(local50, /* new Joiner */()).orElse("");
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
	auto local52 = Strings.from(Strings, "\"");
	auto local53 = local52.appendSlice(local52, this.value);
	return local53.appendSlice(local53, "\"");
}
/* @Override
 public */ struct String_ Invocation::generate(struct Invocation this){
	auto local54 = this.arguments(this, /* ) */.iterate();
	auto local55 = local54.map(local54, /* Node::generate */);
	auto local56 = local55.map(local55, /* String_::toSlice */);
	auto local57 = local56.collect(local56, /* new Joiner */(", "));
	auto joined = local57.orElse(local57, "");
	return Strings.from(Strings, this.caller(this, /* ) */.generate().toSlice() + "(" + joined + ")");
}
/* @Override
 public */ struct String_ DataAccess::generate(struct DataAccess this){
	auto local58 = this.parent(this, /* ) */.generate();
	return Strings.from(Strings, local58.toSlice(local58, /* ) + " */." + this.property());
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
	auto local59 = run();
	local59.ifPresent(local59, /* Throwable::printStackTrace */);
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
	auto local60 = compileAll(state, input, /*  Main::compileRootSegment */);
	auto local61 = tuple.right + tuple.left;
	auto state = /* new ImmutableCompileState */();
	auto tuple = local60.orElse(local60, /* new Tuple<> */(state, ""));
	return local61.generate(local61);
}
/* private static */ template Option<(struct CompileState, char*)> compileAll(struct CompileState initial, char* input, template Option<(struct CompileState, char*)> (*mapper)(struct CompileState, char*)){
	return all(initial, input, /*  Main::foldStatementChar */, mapper, /*  Main::mergeStatements */);
}
/* private static */ template Option<(struct CompileState, char*)> all(struct CompileState initial, char* input, struct DivideState (*folder)(struct DivideState, char), template Option<(struct CompileState, char*)> (*mapper)(struct CompileState, char*), struct StringBuilder (*merger)(struct StringBuilder, char*)){
	auto local62 = parseAll(initial, input, folder, mapper);
	return local62.map(local62, /* tuple -> new Tuple<> */(tuple.left, generateAll(merger, tuple.right)));
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
	auto local63 = /* content -> content */;
	auto local64 = /* (state1, input1) -> content */(state1, input1);
	return or(state, input, Lists.of(Lists, /* 
                Main::whitespace */, /* 
                Main::compileNamespaced */, parseClass(), local64.map(local64, Tuple.mapRight(Tuple, local63.generate(local63, /* ) */.toSlice()))));
}
/* private static */ template Option<(struct CompileState, char*)> (*parseClass)(struct CompileState, char*)(){
	return structure("class", "class ");
}
/* private static */ template Option<(struct CompileState, char*)> (*structure)(struct CompileState, char*)(char* type, char* infix){
	auto local65 = /* (beforeKeyword, afterKeyword) -> {
            var slices = Arrays */;
	auto local66 = local65.stream(local65, beforeKeyword.split(beforeKeyword, " "));
	auto local67 = /* value -> !value */;
	auto local68 = local66.map(local66, /* String::strip */);
	return /* (state, input) -> first */(input, infix, local68.filter(local68, local67.isEmpty(local67)).toList();

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
	auto local69 = /* (Value node) -> node */;
	auto local70 = /* (state1, value) -> symbol */(state1, value);
	return first(beforeContent, " permits ", /* (beforePermits, variantsString) -> {
            return parseValues */(state, variantsString, local70.map(local70, Tuple.mapRight(Tuple, local69.generate(local69, /* ) */.toSlice()))).flatMap(params -> {
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
	auto local71 = /* (state1, value) -> symbol */(state1, value);
	return suffix(beforeParams0.strip(beforeParams0), ">", /* withoutEnd -> {
            return first */(withoutEnd, "<", /* (name, typeParamString) -> {
                return parseValues */(state, typeParamString, local71.map(local71, Tuple.mapRight(Tuple, (/* Value node) -> node */.generate().toSlice()))).flatMap(values -> {
                    return structureWithName(type, values.left, beforeKeyword, name, values.right, params, variants, withEnd);
                });
            });
        });
}
/* private static */ template Option<(struct CompileState, char*)> structureWithName(char* type, struct CompileState state, char* beforeKeyword, char* name, template List<char*> typeParams, template List<struct Parameter> params, template List<char*> variants, char* withEnd){
	auto local72 = /* content -> {
            return compileAll */(state.withStructType(state, /* new StructurePrototype */(type, name, typeParams, variants)), content, /*  Main::structSegment */);
	return suffix(withEnd.strip(withEnd), "}", local72.flatMap(local72, /* tuple -> {
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
	auto local73 = /* t -> t */;
	auto local74 = params.iterate(params);
	auto local75 = local74.map(local74, local73.generate(local73, /* ) */.toSlice());
	auto local76 = local75.map(local75, /* value -> "\n\t" + value + ";" */);
	auto local77 = local76.collect(local76, /* new Joiner */());
	auto paramsString = local77.orElse(local77, "");
	auto generatedStruct = /*  generatePlaceholder(beforeKeyword */.strip()) + "struct " + name + typeParamString + " {" + paramsString + content + "\n};\n";
	return /* new Tuple<CompileState, String> */(state.addStruct(state, generatedStruct), "");
}
/* private static */ char* generateTypeParams(template List<char*> typeParams){
	auto local78 = typeParams.isEmpty(typeParams, /* ) ? "" : "<" + typeParams */.iterate();
	return local78.collect(local78, /* new Joiner */(", ")).orElse("") + ">";
}
/* private static <T> */ template Option<(struct CompileState, struct T)> or(struct CompileState state, char* input, template List<template Option<(struct CompileState, struct T)> (*)(struct CompileState, char*)> actions){
	auto local79 = /* action -> action */;
	auto local80 = actions.iterate(actions);
	auto local81 = local80.map(local80, local79.apply(local79, state, input));
	return local81.flatMap(local81, /* Iterators::fromOptions)
                 */.next();
}
/* private static */ template Option<(struct CompileState, char*)> compileNamespaced(struct CompileState state, char* input){/* if (input.strip().startsWith("package ") || input.strip().startsWith("import ")) {
            return new Some<>(new Tuple<CompileState, String>(state, ""));
        } */
	return /* new None<> */();
}
/* private static */ template Option<(struct CompileState, char*)> structSegment(struct CompileState state, char* input){
	auto local82 = /* content -> content */;
	auto local83 = /* (state1, input1) -> content */(state1, input1);
	return or(state, input, Lists.of(Lists, /* 
                Main::whitespace */, /* 
                Main::annotation */, structure("enum", "enum "), parseClass(), structure("record", "record "), structure("interface", "interface "), /* 
                Main::method */, /* 
                Main::definitionStatement */, local83.map(local83, Tuple.mapRight(Tuple, local82.generate(local82, /* ) */.toSlice()))));
}
/* private static */ template Option<(struct CompileState, char*)> definitionStatement(struct CompileState state, char* input){
	auto local84 = /* definition -> definition */;
	auto local85 = /* withoutEnd -> definition */(state, withoutEnd);
	auto local86 = local85.map(local85, Tuple.mapRight(Tuple, local84.generate(local84, /* ) */.toSlice()));
	return suffix(input.strip(input), ";", local86.map(local86, /* value -> {
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
	auto local87 = /* outputDefinition -> {
                    return parseValues */(outputDefinition.left, paramsString, /*  Main::parameter */);
	auto local88 = /* (paramsString, withBraces) -> {
                return compileMethodHeader */(state, inputDefinition);
	return first(input, "(", /* (inputDefinition, withParams) -> {
            return first */(withParams, ")", local88.flatMap(local88, local87.flatMap(local87, /* outputParams -> {
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
	auto local89 = /* (maybeCurrent, segment) -> maybeCurrent */;
	auto local90 = divide(input, /*  folder)
                 */.iterate();
	return local90.<Option<Tuple<CompileState, List<T>>>>fold(local90, /* new Some<> */(/* new Tuple<CompileState, List<T>> */(initial, Lists.empty(Lists))), local89.flatMap(local89, /* state -> foldElement */(state, segment, mapper)));
}
/* private static <T> */ template Option<(struct CompileState, template List<struct T>)> foldElement((struct CompileState, template List<struct T>) state, char* segment, template Option<(struct CompileState, struct T)> (*mapper)(struct CompileState, char*)){
	auto local91 = mapper.apply(mapper, oldState, segment);
	auto oldState = state.left;
	auto oldCache = state.right;
	return local91.map(local91, /* result -> {
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
	auto local92 = /* maybeNextTuple -> {
            var nextChar = maybeNextTuple */.left;
            var nextState = maybeNextTuple.right;
	auto local93 = local92.append(local92, /* nextChar);

            var withEscaped = nextChar == '\\'
                    ? nextState */.popAndAppend();
	auto local94 = state.append(state, /* c) */.pop();/* if (c != '\'') {
            return new None<>();
        } */
	return local94.map(local94, local93.orElse(local93, /* nextState)
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
	auto local95 = /* t -> t */;
	auto local96 = params.iterate(params);
	auto local97 = local96.map(local96, local95.generate(local95, /* ) */.toSlice());
	auto local98 = local97.collect(local97, /* new Joiner */(", "));
	return local98.orElse(local98, "");
}
/* private static */ char* generateAll(struct StringBuilder (*merger)(struct StringBuilder, char*), template List<char*> right){
	auto local99 = right.iterate(right);
	return local99.fold(local99, /* new StringBuilder */(), /*  merger) */.toString();
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
	auto local100 = /* tuple -> {
                    var newParameters = tuple */.left;
	auto local101 = /* structType -> params */;
	auto local102 = local100.findStructureType(local100);
	auto local103 = local102.map(local102, local101.addFirst(local101, /* new Definition */(/* new StructRef */(structType.name, structType.typeParams), "this"))).orElse(params);

                    var paramStrings = generateNodesAsValues(newParameters);

                    var removed = tuple.left;
	auto local104 = local103.removeStatements(local103, /* );
                    var joined = removed */.left.iterate();
	auto local105 = /* name -> removed */.right;
	auto local106 = local105.findStructureType(local105);
	auto local107 = local104.collect(local104, /* new Joiner */()).orElse("");

                    var generated = definition
                            ;
	auto local108 = /* content -> {
                return compileAll */(state, content, /*  Main::functionSegment */);
	return prefix(withBraces.strip(withBraces), "{", /* withoutStart1 -> {
            return suffix */(withoutStart1, "}", local108.flatMap(local108, local107.mapName(local107, local106.map(local106, /* structureType -> structureType */.name + "::" + name).orElse(name)).generate().toSlice() + "(" + paramStrings + "){" + joined + tuple.right + "\n}\n";

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
	auto local109 = (_, /*  name) -> state */.findStructureType();
	return split(input.strip(input), /* new InfixSplitter */(" ", /*  Main::lastIndexOfSlice */), local109.flatMap(local109, /* structureType -> {
            if (!structureType */.name.equals(name)) {
                return new None<>();
            }

            return new Some<>(new Tuple<>(state, new Definition(Primitive.Auto, name)));
        }));
}
/* private static */ template Option<(struct CompileState, char*)> functionSegment(struct CompileState state, char* input){
	auto local110 = /* slice -> statementValue */(state0, slice);
	auto local111 = /* content -> content */;
	auto local112 = /* (state0a, input1) -> content */(state0a, input1);
	return or(state, input.strip(input), Lists.of(Lists, /* 
                Main::whitespace */, /* (state0, input1) -> suffix */(input1.strip(input1), ";", local110.map(local110, Tuple.mapRight(Tuple, /* slice0 -> "\n\t" + slice0 + ";" */))), local112.map(local112, Tuple.mapRight(Tuple, local111.generate(local111, /* ) */.toSlice()))));
}
/* private static */ template Option<(struct CompileState, char*)> statementValue(struct CompileState state, char* input){
	auto local113 = /* right -> right */;
	auto local114 = /* (state1, s) -> invocation */(state1, s);
	return Main.or(Main, state, input, Lists.of(Lists, /* 
                Main::returns */, /* 
                Main::initialization */, local114.map(local114, Tuple.mapRight(Tuple, local113.generate(local113, /* ) */.toSlice()))));
}
/* private static */ template Option<(struct CompileState, char*)> initialization(struct CompileState state, char* s){
	auto local115 = /* result -> result */;
	auto local116 = /* result0 -> {
            return value */(result0.left, s2);
	auto local117 = /* (s1, s2) -> definition */(state, s1);
	return first(s, "=", local117.flatMap(local117, local116.map(local116, Tuple.mapRight(Tuple, local115.generate(local115, /* ) */.toSlice())).map(result1 -> {
                return new Tuple<>(result1.left, result0.right.generate0() + " = " + result1.right());
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
                        if(parent instanceof Symbol) {
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
	auto local118 = /* result1 -> result1 */;
	auto local119 = /* slice -> value */(state, slice);
	auto local120 = local119.map(local119, Tuple.mapRight(Tuple, local118.generate(local118, /* ) */.toSlice()));
	return prefix(input.strip(input), "return ", local120.map(local120, Tuple.mapRight(Tuple, /* result -> "return " + result */)));
}
/* private static */ template Option<(struct CompileState, struct Value)> value(struct CompileState state, char* input){
	auto local121 = /* (state1, input1) -> content */(state1, input1);
	return Main.or(Main, state, input, Lists.of(Lists, /* 
                Main::stringNode */, /* 
                Main::invocation */, /* 
                Main::dataAccess */, /* 
                Main::symbol */, local121.map(local121, Tuple.mapRight(Tuple, /* right -> right */))));
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
	auto local122 = type(state, type);
	return local122.flatMap(local122, /* typeTuple -> {
            return assembleDefinition(typeTuple */.left, new None<String>(), typeTuple.right, name);
        });
}
/* private static */ template Option<(struct CompileState, struct Definition)> assembleDefinition(struct CompileState state, template Option<char*> maybeBeforeType, struct Type type, char* name){
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
/* private static <S, T extends S> */ template Option<(struct CompileState, struct S)> (*wrap)(struct CompileState, char*)(template Option<(struct CompileState, struct T)> (*content)(struct CompileState, char*)){
	auto local123 = /* (state, input) -> content */;
	auto local124 = local123.apply(local123, state, input);
	return local124.map(local124, Tuple.mapRight(Tuple, /* value -> value */));
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
	auto local125 = splitter.split(splitter, input);
	return local125.flatMap(local125, /* tuple -> {
            return mapper */.apply(tuple.left, tuple.right);
        });
}
/* private static */ template Option<int> firstIndexOfSlice(char* input, char* infix){
	auto index = input.indexOf(input, infix);
	return /* index == -1 ? new None<Integer>() : new Some<> */(index);
}
