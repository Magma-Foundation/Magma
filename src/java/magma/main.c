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
	return this.generate().appendSlice(/* " " */).appendSlice(name);
}
/* @Actual
 private static */ struct String_ Strings::from(struct Strings this, char* value){
	return /* new JavaString */(value);
}
/* @Override
 public <R> */ template Option<struct R> Some::map(struct Some<T> this, struct R (*mapper)(T)){
	return /* new Some<> */(mapper.apply(this.value));
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
	return mapper.apply(this.value);
}
/* @Override
 public */ template Option<T> Some::filter(struct Some<T> this, template Predicate<T> predicate){
	return predicate.test(this.value) ? this : new None<>();
}
/* @Override
 public */ int Some::isPresent(struct Some<T> this){
	return true;
}
/* @Override
 public */ void Some::ifPresent(struct Some<T> this, template Consumer<T> consumer){
	consumer.accept(this.value);
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
	return other.get();
}
/* @Override
 public */ T None::orElseGet(struct None<T> this, T (*other)()){
	return other.get();
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
	return this.fold(collector.createInitial(), /*  collector::fold */);
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
	return this.map(mapper).fold(/* new Iterator<> */(/* new EmptyHead<> */()), /*  Iterator::concat */);
}
/* public <R> */ template Iterator<struct R> Iterator::map(struct Iterator<T> this, struct R (*mapper)(T)){
	return /* new Iterator<> */((/* ) -> this */.head.next().map(mapper));
}
/* private */ template Iterator<T> Iterator::concat(struct Iterator<T> this, template Iterator<T> other){
	return /* new Iterator<> */((/* ) -> this */.head.next().or(/* other::next */));
}
/* public */ template Option<T> Iterator::next(struct Iterator<T> this){
	return this.head.next();
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
	return /* new JavaList<> */(Arrays.asList(elements));
}
/* public static <T> */ template List<struct T> Lists::empty(struct Lists this){
	return /* new JavaList<> */(/* new ArrayList<> */());
}
/* @Override
 public */ template Option<T> EmptyHead::next(struct EmptyHead<T> this){
	return /* new None<> */();
}
struct public Joiner::Joiner(struct Joiner this){
	this(/* "" */);
}
/* @Override
 public */ template Option<char*> Joiner::createInitial(struct Joiner this){
	return /* new None<> */();
}
/* @Override
 public */ template Option<char*> Joiner::fold(struct Joiner this, template Option<char*> current, char* element){
	return /* new Some<> */(current.map(/* inner -> inner + this */.delimiter + element).orElse(element));
}
struct public CompileState::CompileState(struct CompileState this){
	this(Lists.empty(), Lists.empty(), /* new None<> */());
}
/* private */ char* CompileState::generate(struct CompileState this){
	return this.getJoin(this.structs) + this.getJoin(this.functions);
}
/* private */ char* CompileState::getJoin(struct CompileState this, template List<char*> lists){
	return lists.iterate().collect(/* new Joiner */()).orElse(/* "" */);
}
/* public */ struct CompileState CompileState::addStruct(struct CompileState this, char* struct){
	return /* new CompileState */(this.structs.addLast(struct), this.functions, this.maybeStructureType);
}
/* public */ struct CompileState CompileState::addFunction(struct CompileState this, char* function){
	return /* new CompileState */(this.structs, this.functions.addLast(function), this.maybeStructureType);
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
	return this.popAndAppendToTuple().map(/* Tuple::right */);
}
/* private */ template Option<(char, struct DivideState)> DivideState::popAndAppendToTuple(struct DivideState this){
	return this.pop().map(/* tuple -> {
                var c = tuple */.left;
                var state = tuple.right;
                return new Tuple<>(c, state.append(c));
            });
}
/* private */ struct DivideState DivideState::append(struct DivideState this, char c){
	return /* new DivideState */(this.input, this.segments, this.buffer.append(c), this.index, this.depth);
}
/* public */ template Option<(char, struct DivideState)> DivideState::pop(struct DivideState this){/* if (this.index < this.input.length()) {
                var c = this.input.charAt(this.index);
                return new Some<>(new Tuple<Character, DivideState>(c, new DivideState(this.input, this.segments, this.buffer, this.index + 1, this.depth)));
            } *//* else {
                return new None<>();
            } */
}
/* private */ struct DivideState DivideState::advance(struct DivideState this){
	auto withBuffer = this.buffer.isEmpty() ? this.segments : this.segments.addLast(this.buffer.toString());
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
	return /* tuple -> new Tuple<> */(tuple.left, mapper.apply(tuple.right));
}
/* public static <T> */ template Iterator<struct T> Iterators::fromOptions(struct Iterators this, template Option<struct T> option){
	return /* new Iterator<> */(option.<Head<T>>map(/* SingleHead::new */).orElseGet(/* EmptyHead::new */));
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
	return this.apply(input).map(/* classIndex -> {
                var beforeKeyword = input */.substring(0, classIndex);
                var afterKeyword = input.substring(classIndex + this.length());
                return new Tuple<>(beforeKeyword, afterKeyword);
            });
}
/* private */ int InfixSplitter::length(struct InfixSplitter this){
	return this.infix.length();
}
/* private */ template Option<int> InfixSplitter::apply(struct InfixSplitter this, char* input){
	return this.locator().apply(input, this.infix);
}
/* @Override
 public */ template Option<(char*, char*)> TypeSeparatorSplitter::split(struct TypeSeparatorSplitter this, char* input){
	return divide(input, /*  TypeSeparatorSplitter::fold) */.removeLast().flatMap(/* segments -> {
                var left = segments */.left;
                if (left.isEmpty()) {
                    return new None<>(/* );
                }

                var beforeType = left */.iterate().collect(/* new Joiner */(/* " " */)).orElse("");
                var type = segments.right;
                return new Some<>(new Tuple<>(beforeType, type));
            });
}
/* private static */ struct DivideState TypeSeparatorSplitter::fold(struct TypeSeparatorSplitter this, struct DivideState state, char c){/* if (c == ' ' && state.isLevel()) {
                return state.advance();
            } */
	auto appended = state.append(c);/* if (c == '<') {
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
	return /* new Definition */(this.maybeBeforeType, this.type, mapper.apply(this.name));
}
/* @Override
 public */ struct String_ Definition::generate(struct Definition this){
	return Strings.from(this.generate0());
}
/* private */ char* Definition::generate0(struct Definition this){
	auto beforeTypeString = this.maybeBeforeType.map(/* beforeType -> generatePlaceholder(beforeType) + " " */).orElse(/* "" */);
	return /* beforeTypeString + this */.type.generateWithName(this.name).toSlice();
}
/* @Override
 public */ struct String_ Content::generate(struct Content this){
	return Strings.from(this.generate0());
}
/* private */ char* Content::generate0(struct Content this){
	return generatePlaceholder(this.input);
}
/* @Override
 public */ struct String_ Functional::generate(struct Functional this){
	return this.generateWithName(/* "" */);
}
/* @Override
 public */ struct String_ Functional::generateWithName(struct Functional this, char* name){
	auto joinedArguments = this.arguments(/* ) */.iterate().map(/* type -> type */.generate(/* ) */.toSlice()).collect(/* new Joiner */(/* ", " */)).orElse(/* "" */);
	return this.returns.generate().appendSlice(/* " (*" */).appendSlice(name).appendSlice(/* ")(" */).appendSlice(joinedArguments).appendSlice(/* ")" */);
}
/* @Override
 public */ struct String_ Template::generate(struct Template this){
	return Strings.from(this.generate0());
}
/* private */ char* Template::generate0(struct Template this){
	auto generatedTuple = this.arguments(/* ) */.iterate().map(/* type -> type */.generate(/* ) */.toSlice()).collect(/* new Joiner */(/* ", " */)).orElse(/* "" */);
	return /* "template " + this */.base() + "<" + generatedTuple + ">";
}
/* @Override
 public */ template List<T> ListCollector::createInitial(struct ListCollector<T> this){
	return Lists.empty();
}
/* @Override
 public */ template List<T> ListCollector::fold(struct ListCollector<T> this, template List<T> current, T element){
	return current.addLast(element);
}
/* @Override
 public */ struct String_ TypeParameter::generate(struct TypeParameter this){
	return Strings.from(this.generate0());
}
/* private */ char* TypeParameter::generate0(struct TypeParameter this){
	return this.value;
}
/* @Override
 public */ struct String_ Ref::generate(struct Ref this){
	return Strings.from(this.generate0());
}
/* private */ char* Ref::generate0(struct Ref this){
	return this.type.generate().toSlice() + "*";
}
/* @Override
 public */ struct String_ TupleType::generate(struct TupleType this){
	return Strings.from(this.generate0());
}
/* private */ char* TupleType::generate0(struct TupleType this){
	return /* "(" + generateNodesAsValues(this */.arguments) + ")";
}
/* @Override
 public */ struct String_ StructRef::generate(struct StructRef this){
	auto typeParamString = this.typeParams.iterate().collect(/* new Joiner */(/* ", " */)).map(/* inner -> "<" + inner + ">" */).orElse(/* "" */);
	return Strings.from(/* "struct " */).appendSlice(this.input).appendSlice(typeParamString);
}
/* @Override
 public */ template Option<(char*, char*)> DividingSplitter::split(struct DividingSplitter this, char* input){
	return divide(input, this.folder).removeLast().map(/* divisions -> {
                var left1 = divisions */.left;
                if (left1.isEmpty()) {
                    return new Tuple<>(divisions.right, /*  "");
                }

                var left = left1 */.iterate().collect(/* new Joiner */()).orElse("");
                var right = divisions.right;
                return new Tuple<>(left, right);
            });
}
auto Primitive::Primitive(struct Primitive this, char* value){/* this.value = value; */
}
/* @Override
 public */ struct String_ Primitive::generate(struct Primitive this){
	return Strings.from(this.generate0());
}
/* private */ char* Primitive::generate0(struct Primitive this){
	return this.value;
}
/* public static */ void main(){
	run().ifPresent(/* Throwable::printStackTrace */);
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
	auto tuple = compileAll(state, input, /*  Main::compileRootSegment */).orElse(/* new Tuple<> */(state, /*  "" */));
	return tuple.right + tuple.left.generate();
}
/* private static */ template Option<(struct CompileState, char*)> compileAll(struct CompileState initial, char* input, template Option<(struct CompileState, char*)> (*mapper)(struct CompileState, char*)){
	return all(initial, input, /*  Main::foldStatementChar */, mapper, /*  Main::mergeStatements */);
}
/* private static */ template Option<(struct CompileState, char*)> all(struct CompileState initial, char* input, struct DivideState (*folder)(struct DivideState, char), template Option<(struct CompileState, char*)> (*mapper)(struct CompileState, char*), struct StringBuilder (*merger)(struct StringBuilder, char*)){
	return parseAll(initial, input, folder, mapper).map(/* tuple -> new Tuple<> */(tuple.left, generateAll(merger, tuple.right)));
}
/* private static */ struct StringBuilder mergeStatements(struct StringBuilder output, char* right){
	return output.append(right);
}
/* private static */ struct DivideState foldStatementChar(struct DivideState state, char c){
	auto appended = state.append(c);/* if (c == ';' && appended.isLevel()) {
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
	return or(state, input, Lists.of(/* 
                Main::whitespace */, /* 
                Main::compileNamespaced */, parseClass(), /* (state1, input1) -> content */(state1, input1).map(Tuple.mapRight(/* content -> content */.generate(/* ) */.toSlice()))));
}
/* private static */ template Option<(struct CompileState, char*)> (*parseClass)(struct CompileState, char*)(){
	return structure(/* "class" */, /*  "class " */);
}
/* private static */ template Option<(struct CompileState, char*)> (*structure)(struct CompileState, char*)(char* type, char* infix){
	return /* (state, input) -> first */(input, infix, /* (beforeKeyword, afterKeyword) -> {
            var slices = Arrays */.stream(beforeKeyword.split(/* " " */)).map(/* String::strip */).filter(/* value -> !value */.isEmpty()).toList();

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
	return first(beforeContent, /*  " permits " */, /* (beforePermits, variantsString) -> {
            return parseValues */(state, variantsString, /*  Main::symbol */).flatMap(params -> {
                return structureWithoutVariants(type, params.left, beforeKeyword, beforePermits, params.right, withEnd);
            });
        });
}
/* private static */ template Option<(struct CompileState, char*)> symbol(struct CompileState state, char* value){
	auto stripped = value.strip();/* if (isSymbol(stripped)) {
            return new Some<>(new Tuple<>(state, stripped));
        } *//* else {
            return new None<>();
        } */
}
/* private static */ template Option<(struct CompileState, char*)> structureWithoutVariants(char* type, struct CompileState state, char* beforeKeyword, char* beforeContent, template List<char*> variants, char* withEnd){
	return or(state, beforeContent, Lists.of(/* (state0, s) -> structureWithImplements */(type, state0, beforeKeyword, s, variants, withEnd), /* (state0, s) -> structureWithoutImplements */(type, state0, beforeKeyword, s, variants, withEnd)));
}
/* private static */ template Option<(struct CompileState, char*)> structureWithImplements(char* type, struct CompileState state0, char* beforeKeyword, char* s, template List<char*> variants, char* withEnd){
	return first(s, /*  " implements " */, /* (s1, s2) -> structureWithoutImplements */(type, state0, beforeKeyword, s1, variants, withEnd));
}
/* private static */ template Option<(struct CompileState, char*)> structureWithoutImplements(char* type, struct CompileState state, char* beforeKeyword, char* beforeContent, template List<char*> variants, char* withEnd){
	return or(state, beforeContent, Lists.of(/* (state1, s) -> structureWithExtends */(type, beforeKeyword, beforeContent, variants, withEnd, state1), /* (state2, s) -> structureWithoutExtends */(type, state2, beforeKeyword, s, variants, withEnd)));
}
/* private static */ template Option<(struct CompileState, char*)> structureWithExtends(char* type, char* beforeKeyword, char* beforeContent, template List<char*> variants, char* withEnd, struct CompileState state1){
	return first(beforeContent, /*  " extends " */, /* (s1, s2) -> structureWithoutExtends */(type, state1, beforeKeyword, s1, variants, withEnd));
}
/* private static */ template Option<(struct CompileState, char*)> structureWithoutExtends(char* type, struct CompileState state, char* beforeKeyword, char* beforeContent, template List<char*> variants, char* withEnd){
	return or(state, beforeContent, Lists.of(/* (instance, before) -> structureWithParams */(type, instance, beforeKeyword, before, variants, withEnd), /* (instance, before) -> structureWithoutParams */(type, instance, beforeKeyword, before.strip(), Lists.empty(), variants, withEnd)));
}
/* private static */ template Option<(struct CompileState, char*)> structureWithParams(char* type, struct CompileState instance, char* beforeKeyword, char* beforeContent, template List<char*> variants, char* withEnd){
	return suffix(beforeContent.strip(), /*  ")" */, /* withoutEnd -> first */(withoutEnd, /*  "(" */, /* (name, paramString) -> {
            return parseAll */(instance, paramString, /*  Main::foldValueChar */, /*  Main::parameter */).flatMap(params -> {
                        return structureWithoutParams(type, params.left, beforeKeyword, name, params.right, variants, withEnd);
                    });
        }));
}
/* private static */ template Option<(struct CompileState, struct Parameter)> parameter(struct CompileState instance, char* paramString){
	return Main.or(instance, paramString, Lists.of(wrap(/* Main::definition */), wrap(/* Main::content */)));
}
/* private static */ template Option<(struct CompileState, char*)> structureWithoutParams(char* type, struct CompileState state, char* beforeKeyword, char* beforeParams, template List<struct Parameter> params, template List<char*> variants, char* withEnd){
	return or(state, beforeParams, Lists.of(/* (state0, beforeParams0) -> structureWithTypeParams */(type, state0, beforeParams0, beforeKeyword, params, variants, withEnd), /* (state0, name) -> structureWithName */(type, state0, beforeKeyword, name, Lists.empty(), params, variants, withEnd)));
}
/* private static */ template Option<(struct CompileState, char*)> structureWithTypeParams(char* type, struct CompileState state, char* beforeParams0, char* beforeKeyword, template List<struct Parameter> params, template List<char*> variants, char* withEnd){
	return suffix(beforeParams0.strip(), /*  ">" */, /* withoutEnd -> {
            return first */(withoutEnd, /*  "<" */, /*  (name, typeParamString) -> {
                return parseValues(state, typeParamString, Main::symbol */).flatMap(values -> {
                    return structureWithName(type, values.left, beforeKeyword, name, values.right, params, variants, withEnd);
                });
            });
        });
}
/* private static */ template Option<(struct CompileState, char*)> structureWithName(char* type, struct CompileState state, char* beforeKeyword, char* name, template List<char*> typeParams, template List<struct Parameter> params, template List<char*> variants, char* withEnd){
	return suffix(withEnd.strip(), /*  "}" */, /* content -> {
            return compileAll */(state.withStructType(/* new StructurePrototype */(type, name, typeParams, variants)), content, /*  Main::structSegment */).flatMap(/* tuple -> {
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
	auto paramsString = params.iterate().map(/* t -> t */.generate(/* ) */.toSlice()).map(/* value -> "\n\t" + value + ";" */).collect(/* new Joiner */()).orElse(/* "" */);
	auto generatedStruct = /*  generatePlaceholder(beforeKeyword */.strip()) + "struct " + name + typeParamString + " {" + paramsString + content + "\n};\n";
	return /* new Tuple<CompileState, String> */(state.addStruct(generatedStruct), /*  "" */);
}
/* private static */ char* generateTypeParams(template List<char*> typeParams){
	return typeParams.isEmpty(/* ) ? "" : "<" + typeParams */.iterate().collect(/* new Joiner */(/* ", " */)).orElse("") + ">";
}
/* private static <T> */ template Option<(struct CompileState, struct T)> or(struct CompileState state, char* input, template List<template Option<(struct CompileState, struct T)> (*)(struct CompileState, char*)> actions){
	return actions.iterate().map(/* action -> action */.apply(state, input)).flatMap(/* Iterators::fromOptions)
                 */.next();
}
/* private static */ template Option<(struct CompileState, char*)> compileNamespaced(struct CompileState state, char* input){/* if (input.strip().startsWith("package ") || input.strip().startsWith("import ")) {
            return new Some<>(new Tuple<CompileState, String>(state, ""));
        } */
	return /* new None<> */();
}
/* private static */ template Option<(struct CompileState, char*)> structSegment(struct CompileState state, char* input){
	return or(state, input, Lists.of(/* 
                Main::whitespace */, /* 
                Main::annotation */, structure(/* "enum" */, /*  "enum " */), parseClass(), structure(/* "record" */, /*  "record " */), structure(/* "interface" */, /*  "interface " */), /* 
                Main::method */, /* 
                Main::definitionStatement */, /* (state1, input1) -> content */(state1, input1).map(Tuple.mapRight(/* content -> content */.generate(/* ) */.toSlice()))));
}
/* private static */ template Option<(struct CompileState, char*)> definitionStatement(struct CompileState state, char* input){
	return suffix(input.strip(), /*  ";" */, /* withoutEnd -> definition */(state, withoutEnd).map(Tuple.mapRight(/* definition -> definition */.generate(/* ) */.toSlice())).map(/* value -> {
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
	return first(input, /*  "(" */, /* (inputDefinition, withParams) -> {
            return first */(withParams, /*  ")" */, /* (paramsString, withBraces) -> {
                return compileMethodHeader */(state, inputDefinition).flatMap(/* outputDefinition -> {
                    return parseValues */(outputDefinition.left, paramsString, /*  Main::parameter */).flatMap(/* outputParams -> {
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
                 */.iterate().<Option<Tuple<CompileState, List<T>>>>fold(/* new Some<> */(/* new Tuple<CompileState, List<T>> */(initial, Lists.empty())), /* (maybeCurrent, segment) -> maybeCurrent */.flatMap(/* state -> foldElement */(state, segment, mapper)));
}
/* private static <T> */ template Option<(struct CompileState, template List<struct T>)> foldElement((struct CompileState, template List<struct T>) state, char* segment, template Option<(struct CompileState, struct T)> (*mapper)(struct CompileState, char*)){
	auto oldState = state.left;
	auto oldCache = state.right;
	return mapper.apply(oldState, segment).map(/* result -> {
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
	return current.advance().segments;
}
/* private static */ template Option<struct DivideState> foldDoubleQuotes(struct DivideState state, char c){/* if (c != '\"') {
            return new None<>();
        } */
	auto appended = state.append(c);/* while (true) {
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
	return state.append(/* c) */.pop().map(/* maybeNextTuple -> {
            var nextChar = maybeNextTuple */.left;
            var nextState = maybeNextTuple.right.append(/* nextChar);

            var withEscaped = nextChar == '\\'
                    ? nextState */.popAndAppend().orElse(/* nextState)
                    : nextState;

            return withEscaped */.popAndAppend().orElse(withEscaped);
        });
}
/* private static */ struct DivideState foldValueChar(struct DivideState state, char c){/* if (c == ',' && state.isLevel()) {
            return state.advance();
        } */
	auto appended = state.append(c);/* if (c == '-' && appended.peek().filter(value -> value == '>').isPresent()) {
            return appended.popAndAppend().orElse(appended);
        } *//* if (c == '<' || c == '(' || c == '{') {
            return appended.enter();
        } *//* if (c == '>' || c == ')' || c == '}') {
            return appended.exit();
        } */
	return appended;
}
/* private static <T extends Node> */ char* generateNodesAsValues(template List<struct T> params){
	return params.iterate().map(/* t -> t */.generate(/* ) */.toSlice()).collect(/* new Joiner */(/* ", " */)).orElse(/* "" */);
}
/* private static */ char* generateAll(struct StringBuilder (*merger)(struct StringBuilder, char*), template List<char*> right){
	return right.iterate().fold(/* new StringBuilder */(), /*  merger) */.toString();
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
	return prefix(withBraces.strip(), /*  "{" */, /* withoutStart1 -> {
            return suffix */(withoutStart1, /*  "}" */, /* content -> {
                return compileAll */(state, content, /*  Main::functionSegment */).flatMap(/* tuple -> {
                    var newParameters = state */.maybeStructureType
                            .map(/* structType -> params */.addFirst(/* new Definition */(/* new StructRef */(structType.name, structType.typeParams), /*  "this" */))).orElse(params);
                    var paramStrings = generateNodesAsValues(newParameters);

                    var generated = definition
                            .mapName(/* name -> state */.maybeStructureType.map(/* structureType -> structureType */.name + "::" + name).orElse(name)).generate().toSlice() + "(" + paramStrings + "){" + tuple.right + "\n}\n";
                    return new Some<>(new Tuple<>(state.addFunction(generated), ""));
                });
            });
        });
}
/* private static */ template Option<(struct CompileState, struct Definition)> compileMethodHeader(struct CompileState state, char* definition){
	return or(state, definition, Lists.of(/* 
                Main::definition */, /* 
                Main::constructor
         */));
}
/* private static */ template Option<(struct CompileState, struct Definition)> constructor(struct CompileState state, char* input){
	return or(state, input, Lists.of(/* 
                Main::constructorWithType */, /* 
                Main::constructorWithoutType
         */));
}
/* private static */ template Option<(struct CompileState, struct Definition)> constructorWithoutType(struct CompileState state, char* s){
	auto stripped = s.strip();/* if (isSymbol(stripped)) {
            return new Some<>(new Tuple<>(state, new Definition(Primitive.Auto, stripped)));
        } */
	return /* new None<> */();
}
/* private static */ template Option<(struct CompileState, struct Definition)> constructorWithType(struct CompileState state, char* input){
	return split(input.strip(), /* new InfixSplitter */(/* " " */, /*  Main::lastIndexOfSlice */), /* (_, name) -> state */.maybeStructureType.flatMap(/* structureType -> {
            if (!structureType */.name.equals(name)) {
                return new None<>();
            }

            return new Some<>(new Tuple<>(state, new Definition(Primitive.Auto, name)));
        }));
}
/* private static */ template Option<(struct CompileState, char*)> functionSegment(struct CompileState state, char* input){
	return or(state, input.strip(), Lists.of(/* 
                Main::whitespace */, /* (state0, input1) -> suffix */(input1.strip(), /*  ";" */, /* slice -> statementValue */(state0, slice).map(Tuple.mapRight(/* slice0 -> "\n\t" + slice0 + ";" */))), /* (state0a, input1) -> content */(state0a, input1).map(Tuple.mapRight(/* content -> content */.generate(/* ) */.toSlice()))));
}
/* private static */ template Option<(struct CompileState, char*)> statementValue(struct CompileState state, char* input){
	return Main.or(state, input, Lists.of(/* 
                Main::returns */, /* 
                Main::initialization */, /* 
                Main::invocation
         */));
}
/* private static */ template Option<(struct CompileState, char*)> initialization(struct CompileState state, char* s){
	return first(s, /*  "=" */, /* (s1, s2) -> definition */(state, s1).flatMap(/* result0 -> {
            return value */(result0.left, s2).map(result1 -> {
                return new Tuple<>(result1.left, result0.right.generate0() + " = " + result1.right());
            });
        }));
}
/* private static */ template Option<(struct CompileState, char*)> invocation(struct CompileState state0, char* input){
	return suffix(input.strip(), /*  ")" */, /* withoutEnd -> {
            return split */(withoutEnd, /* new DividingSplitter */(/* Main::foldInvocationStart */), /* (withEnd, argumentsString) -> {
                return suffix */(withEnd.strip(), /*  "(" */, /* callerString -> value */(state0, callerString).flatMap(/* callerTuple -> {
                    return Main */.parseValues(callerTuple.left, argumentsString, /*  Main::value */).map(argumentsTuple -> {
                        var joined = argumentsTuple.right.iterate().collect(new Joiner(", ")).orElse("");

                        return new Tuple<>(argumentsTuple.left, callerTuple.right + "(" + joined + ")");
                    });
                }));
            });
        });
}
/* private static */ struct DivideState foldInvocationStart(struct DivideState state, char c){
	auto appended = state.append(c);/* if (c == '(') {
            var enter = appended.enter();
            return appended.isLevel() ? enter.advance() : enter;
        } *//* if (c == ')') {
            return appended.exit();
        } */
	return appended;
}
/* private static */ template Option<(struct CompileState, char*)> returns(struct CompileState state, char* input){
	return prefix(input.strip(), /*  "return " */, /* slice -> value */(state, slice).map(Tuple.mapRight(/* result -> "return " + result */)));
}
/* private static */ template Option<(struct CompileState, char*)> value(struct CompileState state, char* input){
	return or(state, input, Lists.of(/* 
                Main::invocation */, /* 
                Main::dataAccess */, /* 
                Main::symbol */, /* (state1, input1) -> content */(state1, input1).map(Tuple.mapRight(/* content -> content */.generate(/* ) */.toSlice()))));
}
/* private static */ template Option<(struct CompileState, char*)> dataAccess(struct CompileState state, char* input){
	return split(input, /* new InfixSplitter */(/* " */.", /*  Main::lastIndexOfSlice */), /* (parent, property) -> {
            return value */(state, parent).map(tuple -> {
                return new Tuple<>(tuple.left, tuple.right + "." + property);
            });
        });
}
/* private static */ template Option<(struct CompileState, struct Definition)> definition(struct CompileState state, char* input){
	return split(input.strip(), /* new InfixSplitter */(/* " " */, /*  Main::lastIndexOfSlice */), /*  (beforeName, name) -> {
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
	return type(state, type).flatMap(/* typeTuple -> {
            return assemble(typeTuple */.left, new None<String>(), typeTuple.right, name);
        });
}
/* private static */ template Option<(struct CompileState, struct Definition)> assemble(struct CompileState state, template Option<char*> maybeBeforeType, struct Type type, char* name){
	auto definition = /* new Definition */(maybeBeforeType, type, name.strip());
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
	return Main.or(state, input, Lists.of(/* 
                Main::primitive */, /* 
                Main::template */, /* 
                Main::typeParameter */, /* 
                Main::string */, /* 
                Main::structureType */, wrap(/* Main::content */)));
}
/* private static */ template Option<(struct CompileState, struct Type)> structureType(struct CompileState state, char* input){
	auto stripped = input.strip();/* if (isSymbol(stripped)) {
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
	return /* (state, input) -> content */.apply(state, input).map(Tuple.mapRight(/* value -> value */));
}
/* private static */ template Option<(struct CompileState, struct Type)> primitive(struct CompileState state, char* input){
	auto stripped = input.strip();
	return /* switch (stripped) {
            case "boolean", "Boolean", "int", "Integer" -> new Some<>(new Tuple<>(state, Primitive */.I32));
            case "char", "Character" -> new Some<>(new Tuple<>(state, Primitive.I8));
            case "var" -> new Some<>(new Tuple<>(state, Primitive.Auto));
            case "void" -> new Some<>(new Tuple<>(state, Primitive.Void));
            default -> new None<>();
        };
}
/* private static */ template Option<(struct CompileState, struct Type)> template(struct CompileState state, char* input){
	return suffix(input.strip(), /*  ">" */, /* withoutEnd -> {
            return first */(withoutEnd, /*  "<" */, /*  (base, argumentsString) -> {
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
	auto index = input.lastIndexOf(infix);
	return /* index == -1 ? new None<Integer>() : new Some<> */(index);
}
/* private static */ template Option<(struct CompileState, char*)> prefix(char* input, char* prefix, template Option<(struct CompileState, char*)> (*mapper)(char*)){/* if (!input.startsWith(prefix)) {
            return new None<>();
        } */
	auto slice = input.substring(prefix.length());
	return mapper.apply(slice);
}
/* private static <T> */ template Option<struct T> suffix(char* input, char* suffix, template Option<struct T> (*mapper)(char*)){/* if (!input.endsWith(suffix)) {
            return new None<>();
        } */
	auto slice = input.substring(/* 0 */, input.length(/* ) - suffix */.length());
	return mapper.apply(slice);
}
/* private static */ char* generatePlaceholder(char* input){
	return /* "/* " + input + " */" */;
}
/* private static <T> */ template Option<struct T> first(char* input, char* infix, template Option<struct T> (*mapper)(char*, char*)){
	return split(input, /* new InfixSplitter */(infix, /*  Main::firstIndexOfSlice */), mapper);
}
/* private static <T> */ template Option<struct T> split(char* input, struct Splitter splitter, template Option<struct T> (*mapper)(char*, char*)){
	return splitter.split(input).flatMap(/* tuple -> {
            return mapper */.apply(tuple.left, tuple.right);
        });
}
/* private static */ template Option<int> firstIndexOfSlice(char* input, char* infix){
	auto index = input.indexOf(infix);
	return /* index == -1 ? new None<Integer>() : new Some<> */(index);
}
