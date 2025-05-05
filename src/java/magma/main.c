struct Result {
};
struct Option {
};
struct Error {
};
struct Actual {
};
struct Value {
};
struct Parameter {
};
struct None {
};
struct Some {
};
struct Ok {
};
struct Err {
};
struct DivideState {
	char* input;
	template List<char*> segments;
	int index;
	struct StringBuilder buffer;
	int depth;
};
struct Frame {
};
struct CompileState {
};
struct Tuple {
};
struct Definition {
};
struct StringValue {
};
struct Symbol {
};
struct Invocation {
};
struct DataAccess {
};
struct MethodAccess {
};
struct Operation {
};
struct Whitespace {
};
struct TupleNode {
};
struct NumberValue {
};
struct CharValue {
};
struct Not {
};
struct CompileError {
};
struct OrState {
};
struct ApplicationError {
};
struct FunctionProto {
};
struct StructProto {
};
struct Operator {
	ADD("+"), SUBTRACT("-"), LESS_THAN("<"), AND("&&"), OR("||"), GREATER_THAN_OR_EQUALS(">="), EQUALS("=="), NOT_EQUALS("!=");
	char* representation;
};
struct Main {
};
struct R Result::match(struct R (*)(struct T) whenOk, struct R (*)(struct X) whenErr) {
}
template Result<struct R, struct X> Result::mapValue(struct R (*)(struct T) mapper) {
}
template Result<struct T, struct R> Result::mapErr(struct R (*)(struct X) mapper) {
}
template Result<struct R, struct X> Result::flatMapValue(template Result<struct R, struct X> (*)(struct T) mapper) {
}
void Option::ifPresent(void (*)(struct T) consumer) {
}
struct T Option::orElseGet(struct T (*)() supplier) {
}
template Option<struct T> Option::or(template Option<struct T> (*)() other) {
}
int Option::isPresent() {
}
template Option<struct R> Option::map(struct R (*)(struct T) mapper) {
}
template Option<struct R> Option::flatMap(template Option<struct R> (*)(struct T) mapper) {
}
struct T Option::orElse(struct T other) {
}
struct R Option::match(struct R (*)(struct T) whenSome, struct R (*)() whenNone) {
}
char* Error::display() {
}
char* Value::generate() {
}
void None::ifPresent(void (*)(struct T) consumer){
}
struct T None::orElseGet(struct T (*)() supplier){
		return get(supplier);
}
template Option<struct T> None::or(template Option<struct T> (*)() other){
		return get(other);
}
int None::isPresent(){
		return 0;
}
template Option<struct R> None::map(struct R (*)(struct T) mapper){
		return template None<>::new();
}
template Option<struct R> None::flatMap(template Option<struct R> (*)(struct T) mapper){
		return template None<>::new();
}
struct T None::orElse(struct T other){
		return other;
}
struct R None::match(struct R (*)(struct T) whenSome, struct R (*)() whenNone){
		return get(whenNone);
}
void Some::ifPresent(void (*)(struct T) consumer){
		accept(consumer, this.value);
}
struct T Some::orElseGet(struct T (*)() supplier){
		return this.value;
}
template Option<struct T> Some::or(template Option<struct T> (*)() other){
		return this;
}
int Some::isPresent(){
		return 1;
}
template Option<struct R> Some::map(struct R (*)(struct T) mapper){
		return template Some<>::new(apply(mapper, this.value));
}
template Option<struct R> Some::flatMap(template Option<struct R> (*)(struct T) mapper){
		return apply(mapper, this.value);
}
struct T Some::orElse(struct T other){
		return this.value;
}
struct R Some::match(struct R (*)(struct T) whenSome, struct R (*)() whenNone){
		return apply(whenSome, this.value);
}
struct R Ok::match(struct R (*)(struct T) whenOk, struct R (*)(struct X) whenErr){
		return apply(whenOk, this.value);
}
template Result<struct R, struct X> Ok::mapValue(struct R (*)(struct T) mapper){
		return template Ok<>::new(apply(mapper, this.value));
}
template Result<struct T, struct R> Ok::mapErr(struct R (*)(struct X) mapper){
		return template Ok<>::new(this.value);
}
template Result<struct R, struct X> Ok::flatMapValue(template Result<struct R, struct X> (*)(struct T) mapper){
		return apply(mapper, this.value);
}
struct R Err::match(struct R (*)(struct T) whenOk, struct R (*)(struct X) whenErr){
		return apply(whenErr, this.error);
}
template Result<struct R, struct X> Err::mapValue(struct R (*)(struct T) mapper){
		return template Err<>::new(this.error);
}
template Result<struct T, struct R> Err::mapErr(struct R (*)(struct X) mapper){
		return template Err<>::new(apply(mapper, this.error));
}
template Result<struct R, struct X> Err::flatMapValue(template Result<struct R, struct X> (*)(struct T) mapper){
		return template Err<>::new(this.error);
}
struct public DivideState::DivideState(char* input){
		this(input, template ArrayList<>::new(), struct StringBuilder::new(), 0, 0);
}
struct public DivideState::DivideState(char* input, template List<char*> segments, struct StringBuilder buffer, int index, int depth){
		this.input = input;
		this.segments = segments;
		this.index = index;
		this.depth = depth;
		this.buffer = buffer;
}
struct DivideState DivideState::enter(){
		this.depth = this.depth + 1;
		return this;
}
struct DivideState DivideState::exit(){
		this.depth = this.depth - 1;
		return this;
}
struct DivideState DivideState::advance(){
		add(segments(this), toString(this.buffer));
		this.buffer = struct StringBuilder::new();
		return this;
}
template List<char*> DivideState::segments(){
		return this.segments;
}
int DivideState::isShallow(){
		return this.depth == 1;
}
int DivideState::isLevel(){
		return this.depth == 0;
}
template Option<struct DivideState> DivideState::popAndAppendToOption(){
		return map(popAndAppendToTuple(this), struct Tuple::right);
}
auto lambda0(auto tuple){
	return (tuple.left, append(tuple.right, tuple.left));
}
template Option<(struct Character, struct DivideState)> DivideState::popAndAppendToTuple(){
		return map(pop(this), lambda0);
}
struct DivideState DivideState::append(char c){
		append(this.buffer, c);
		return this;
}
template Option<(struct Character, struct DivideState)> DivideState::pop(){
		if (this.index < length(this.input)){
			auto c = charAt(this.input, this.index);
			return template Some<>::new((c, struct DivideState::new(this.input, this.segments, this.buffer, this.index + 1, this.depth)));
		}
		else {
			return template None<>::new();
		}
}
char DivideState::peek(){
		return charAt(this.input, this.index);
}
struct public Frame::Frame(){
		this(template HashMap<>::new(), template ArrayList<>::new(), template None<>::new(), template None<>::new());
}
(char*, struct Frame) Frame::createName(char* category){
		if (!containsKey(this.counters, category)){
			put(this.counters, category, 0);
		}
		auto oldCounter = get(this.counters, category);
		auto name = category + oldCounter;
		auto newCounter = oldCounter + 1;
		put(this.counters, category, newCounter);
		return (name, this);
}
struct Frame Frame::withFunctionProto(struct FunctionProto proto){
		return struct Frame::new(this.counters, this.statements, template Some<>::new(proto), this.structProto);
}
struct Frame Frame::withStructProto(struct StructProto proto){
		return struct Frame::new(this.counters, this.statements, this.functionProto, template Some<>::new(proto));
}
struct public CompileState::CompileState(){
		this(template ArrayList<>::new(), template ArrayList<>::new(), template ArrayList<>::new(singletonList(Collections, struct Frame::new())));
}
struct CompileState CompileState::addFunction(char* generated){
		add(this.functions, generated);
		return this;
}
(char*, struct CompileState) CompileState::createName(char* category){
		auto frame = getLast(this.frames);
		auto nameTuple = createName(frame, category);
		set(this.frames, size(this.frames) - 1, nameTuple.right);
		return (nameTuple.left, this);
}
struct CompileState CompileState::enter(){
		add(this.frames, struct Frame::new());
		return this;
}
struct CompileState CompileState::exit(){
		removeLast(this.frames);
		return this;
}
struct CompileState CompileState::addStruct(char* generated){
		add(this.structs, generated);
		return this;
}
int CompileState::depth(){
		return size(this.frames);
}
struct CompileState CompileState::mapLast(struct Frame (*)(struct Frame) mapper){
		auto last = getLast(this.frames);
		auto newLast = apply(mapper, last);
		set(this.frames, size(this.frames) - 1, newLast);
		return this;
}
struct Frame CompileState::last(){
		return getLast(this.frames);
}
struct public Definition::Definition(char* type, char* name){
		this(emptyList(Collections), emptyList(Collections), type, name);
}
char* Definition::generate(){
		auto modifiersString = generateModifiers(this);
		return modifiersString + this.type + " " + this.name;
}
char* Definition::generateModifiers(){
		if (isEmpty(this.modifiers)){
			return "";
		}
		return join(String, " ", this.modifiers) + " ";
}
struct Definition Definition::mapName(char* (*)(char*) mapper){
		return struct Definition::new(this.annotations, this.modifiers, this.type, apply(mapper, this.name));
}
char* StringValue::generate(){
		return "\"" + this.value + "\"";
}
char* Symbol::generate(){
		return this.value;
}
char* Invocation::generate(){
		auto joined = collect(map(stream(this.arguments), struct Value::generate), joining(Collectors, ", "));
		return generate(this.caller) + "(" + joined + ")";
}
char* DataAccess::generate(){
		return generate(this.parent) + "." + this.child;
}
char* MethodAccess::generate(){
		return parent(this) + "::" + child(this);
}
char* Operation::generate(){
		return generate(this.left) + " " + this.operator.representation + " " + generate(this.right);
}
char* Whitespace::generate(){
		return "";
}
char* TupleNode::generate(){
		return "(" + this.first.generate() + ", " + this.second.generate() + ")";
}
char* NumberValue::generate(){
		return this.value;
}
char* CharValue::generate(){
		return "'" + this.value + "'";
}
char* Not::generate(){
		return "!" + generate(this.value);
}
struct public CompileError::CompileError(char* message, char* context){
		this(message, context, emptyList(Collections));
}
char* CompileError::display(){
		return format(this, 0);
}
auto lambda0(auto statement){
	return "\n" + repeat("\t", depth) + statement;
}
auto lambda1(auto error){
	return format(error, depth + 1);
}
char* CompileError::format(int depth){
		auto copy = template ArrayList<>::new(this.errors);
		sort(copy, comparingInt(Comparator, struct CompileError::depth));
		return this.message + ": " + this.context + collect(map(map(stream(copy), lambda1), lambda0), joining(Collectors));
}
int CompileError::depth(){
		return 1 + orElse(max(mapToInt(stream(this.errors), struct CompileError::depth)), 0);
}
struct public OrState::OrState(){
		this(template None<>::new(), template ArrayList<>::new());
}
template OrState<struct T> OrState::withValue((struct CompileState, struct T) pair){
		return template OrState<>::new(template Some<>::new(pair), this.errors);
}
template OrState<struct T> OrState::withError(struct CompileError error){
		add(this.errors, error);
		return this;
}
auto lambda0(){
	return template Err<>::new(this.errors);
}
template Result<(struct CompileState, struct T), template List<struct CompileError>> OrState::toResult(){
		return match(this.option, struct Ok::new, lambda0);
}
char* ApplicationError::display(){
		return display(childError(this));
}
auto Operator::Operator(char* representation){
		this.representation = representation;
}
auto lambda0(auto error){
	return println(System.err, display(error));
}
void Main::main(){
	ifPresent(match(mapErr(readSource(), struct ApplicationError::new), struct Main::compileAndWrite, struct Some::new), lambda0);
}
auto lambda0(auto output){
	return map(writeTarget(output), struct ApplicationError::new);
}
template Option<struct ApplicationError> Main::compileAndWrite(char* input){
	return match(mapErr(compile(input), struct ApplicationError::new), lambda0, struct Some::new);
}
template Option<struct IOError> Main::writeTarget(char* output);
template Result<char*, struct IOError> Main::readSource();
auto lambda0(auto tuple){
	auto joinedStructs = join(String, "", tuple.left.structs);
	auto joinedFunctions = join(String, "", tuple.left.functions);
	return joinedStructs + joinedFunctions + tuple.right;
}
template Result<char*, struct CompileError> Main::compile(char* input){
	auto state = struct CompileState::new();
	return mapValue(compileStatements(state, input, struct Main::compileRootSegment), lambda0);
}
auto lambda0(auto state0, auto input0){
	return structure(state0, input0, "class");
}
template Result<(struct CompileState, char*), struct CompileError> Main::compileRootSegment(struct CompileState state, char* input){
	return or(state, input, of(List, typed("whitespace", struct Main::whitespace), typed("namespaced", struct Main::namespaced), typed("class", lambda0)));
}
template Result<(struct CompileState, char*), struct CompileError> Main::namespaced(struct CompileState state, char* input){
	auto stripped = strip(input);
	if (startsWith(stripped, "package ") || startsWith(stripped, "import ")){
		return template Ok<>::new((state, ""));
	}
	return template Err<>::new(struct CompileError::new("Not namespaced", input));
}
auto lambda0(auto tuple){
	return (tuple.left, generateStatements(tuple.right));
}
template Result<(struct CompileState, char*), struct CompileError> Main::compileStatements(struct CompileState state, char* input, template BiFunction<struct CompileState, char*, template Result<(struct CompileState, char*), struct CompileError>> mapper){
	return mapValue(parseStatements(state, input, mapper), lambda0);
}
char* Main::generateStatements(template List<char*> elements){
	return generateAll(elements, struct Main::mergeStatements);
}
template Result<(struct CompileState, template List<char*>), struct CompileError> Main::parseStatements(struct CompileState state, char* input, template BiFunction<struct CompileState, char*, template Result<(struct CompileState, char*), struct CompileError>> mapper){
	return parseAll(state, input, struct Main::foldStatementChar, mapper);
}
auto lambda0(auto current0){
	return foldElement(current0, segment, mapper);
}
auto lambda1(auto result, auto segment){
	return flatMapValue(result, lambda0);
}
auto lambda2(auto _, auto next){
	return next;
}
template Result<(struct CompileState, template List<struct T>), struct CompileError> Main::parseAll(struct CompileState initial, char* input, template BiFunction<struct DivideState, struct Character, struct DivideState> folder, template BiFunction<struct CompileState, char*, template Result<(struct CompileState, struct T), struct CompileError>> mapper){
	return reduce(stream(divideAll(input, folder)), createInitial(initial), lambda1, lambda2);
}
template Result<(struct CompileState, template List<struct T>), struct CompileError> Main::createInitial(struct CompileState initial){
	return template Ok<>::new((struct CompileState, template List<struct T>)::new(initial, template ArrayList<struct T>::new()));
}
auto lambda0(auto mapped){
	auto elements = current.right;
	auto newElement = mapped.right;
	add(elements, newElement);
	return (mapped.left, elements);
}
template Result<(struct CompileState, template List<struct T>), struct CompileError> Main::foldElement((struct CompileState, template List<struct T>) current, char* segment, template BiFunction<struct CompileState, char*, template Result<(struct CompileState, struct T), struct CompileError>> mapper){
	return mapValue(apply(mapper, current.left, segment), lambda0);
}
auto lambda0(){
	return apply(folder, popped.right, popped.left);
}
auto lambda1(){
	return foldDoubleQuotes(popped.right, popped.left);
}
auto lambda2(){
	return apply(folder, popped.right, popped.left);
}
auto lambda3(){
	return foldDoubleQuotes(popped.right, popped.left);
}
template List<char*> Main::divideAll(char* input, template BiFunction<struct DivideState, struct Character, struct DivideState> folder){
	auto current = struct DivideState::new(input);
	while (1){
		auto maybePopped = pop(current);
		if (!(maybePopped == 0)){
			break;
		}
		current = orElseGet(or(foldSingleQuotes(popped.right, popped.left), lambda3), lambda2);
	}
	return advance(current).segments;
}
template Option<struct DivideState> Main::foldDoubleQuotes(struct DivideState state, char maybeDoubleQuotes){
	if (maybeDoubleQuotes != '\"'){
		return template None<>::new();
	}
	auto current = append(state, maybeDoubleQuotes);
	while (1){
		if (!(popAndAppendToTuple(current) == 0)){
			break;
		}
		auto next = popped.left;
		current = popped.right;
		if (next == '\\'){
			current = orElse(popAndAppendToOption(current), current);
		}
		if (next == '\"'){
			break;
		}
	}
	return template Some<>::new(current);
}
auto lambda0(auto popped){
	return foldEscape(popped.right, popped.left);
}
template Option<struct DivideState> Main::foldSingleQuotes(struct DivideState state, char c){
	if (c != '\''){
		return template None<>::new();
	}
	auto appended = append(state, c);
	return flatMap(flatMap(popAndAppendToTuple(appended), lambda0), struct DivideState::popAndAppendToOption);
}
template Option<struct DivideState> Main::foldEscape(struct DivideState state, struct Character c){
	if (c == '\\'){
		return popAndAppendToOption(state);
	}
	return template Some<>::new(state);
}
auto lambda0(auto _, auto next){
	return next;
}
char* Main::generateAll(template List<char*> elements, template BiFunction<struct StringBuilder, char*, struct StringBuilder> merger){
	return toString(reduce(stream(elements), struct StringBuilder::new(), merger, lambda0));
}
struct StringBuilder Main::mergeStatements(struct StringBuilder output, char* mapped){
	return append(output, mapped);
}
struct DivideState Main::foldStatementChar(struct DivideState state, char c){
	auto appended = append(state, c);
	if (c == ';' && isLevel(appended)){
		return advance(appended);
	}
	if (c == '}' && isShallow(appended)){
		return exit(advance(appended));
	}
	if (c == '{' || c == '('){
		return enter(appended);
	}
	if (c == '}' || c == ')'){
		return exit(appended);
	}
	return appended;
}
auto lambda0(auto state3, auto input3){
	return structure(state3, input3, "enum ");
}
auto lambda1(auto state2, auto input2){
	return structure(state2, input2, "class ");
}
auto lambda2(auto state1, auto input1){
	return structure(state1, input1, "record ");
}
auto lambda3(auto state0, auto input0){
	return structure(state0, input0, "interface ");
}
template Result<(struct CompileState, char*), struct CompileError> Main::compileStructSegment(struct CompileState state, char* input){
	return or(state, input, of(List, typed("whitespace", struct Main::whitespace), typed("enum", lambda0), typed("class", lambda1), typed("record", lambda2), typed("interface", lambda3), typed("method", struct Main::method), typed("definition", struct Main::definitionStatement), typed("enum-values", struct Main::enumValues)));
}
auto lambda0(auto tuple){
	return (tuple.left, generate(tuple.right));
}
auto lambda1(auto state1, auto input1){
	return mapValue(compileInvokable(state1, input1), lambda0);
}
auto lambda2(auto state0, auto input0){
	return compileValues(state0, input0, lambda1);
}
template Result<(struct CompileState, char*), struct CompileError> Main::enumValues(struct CompileState state, char* input){
	return statement(state, input, lambda2);
}
template Result<(struct CompileState, char*), struct CompileError> Main::definitionStatement(struct CompileState state, char* input){
	return statement(state, input, struct Main::definition);
}
auto lambda0(auto nameTuple){
	return assembleStructure(nameTuple, right);
}
template Result<(struct CompileState, char*), struct CompileError> Main::structure(struct CompileState state, char* input, char* infix){
	auto stripped = strip(input);
	if (!endsWith(stripped, "}")){
		return createSuffixErr(stripped, "}");
	}
	auto withoutEnd = substring(stripped, 0, length(stripped) - length("}"));
	auto contentStart = indexOf(withoutEnd, "{");
	if (contentStart < 0){
		return createInfixErr(withoutEnd, "{");
	}
	auto left = substring(withoutEnd, 0, contentStart);
	auto right = substring(withoutEnd, contentStart + length("{"));
	auto infixIndex = indexOf(left, infix);
	if (infixIndex < 0){
		return createInfixErr(withoutEnd, infix);
	}
	auto beforeInfix = strip(substring(left, 0, infixIndex));
	if (contains(beforeInfix, "\n")){
		return template Ok<>::new((state, ""));
	}
	auto afterInfix = strip(substring(left, infixIndex + length(infix)));
	return flatMapValue(flatMapValue(flatMapValue(removeImplements(state, afterInfix), struct Main::removeParams), struct Main::removeTypeParams), lambda0);
}
auto lambda0(auto s1, auto _){
	return template Ok<>::new((state, s1));
}
auto lambda1(auto state, auto s){
	return infix(s, "(", lambda0);
}
auto lambda2(auto state, auto s){
	return template Ok<>::new((state, s));
}
template Result<(struct CompileState, char*), struct CompileError> Main::removeParams((struct CompileState, char*) state1){
	return or(Main, state1.left, state1.right, of(List, lambda1, lambda2));
}
auto lambda0(auto s1, auto _){
	return template Ok<>::new((state, s1));
}
auto lambda1(auto state, auto s){
	return infix(s, " implements ", lambda0);
}
auto lambda2(auto state, auto s){
	return template Ok<>::new((state, s));
}
template Result<(struct CompileState, char*), struct CompileError> Main::removeImplements(struct CompileState state0, char* afterInfix){
	return or(Main, state0, afterInfix, of(List, lambda1, lambda2));
}
auto lambda0(auto left1, auto _){
	return template Ok<>::new((state, left1));
}
auto lambda1(auto state, auto s){
	return infix(s, "<", lambda0);
}
auto lambda2(auto state, auto s){
	return template Ok<>::new((state, s));
}
template Result<(struct CompileState, char*), struct CompileError> Main::removeTypeParams((struct CompileState, char*) withoutParams){
	return or(Main, withoutParams.left, withoutParams.right, of(List, lambda1, lambda2));
}
auto lambda0(auto result){
	auto generated = "struct " + name + " {" + result.right + "\n};\n";
	return (addStruct(exit(result.left), generated), "");
}
auto lambda1(auto last){
	return withStructProto(last, struct StructProto::new(name));
}
template Result<(struct CompileState, char*), struct CompileError> Main::assembleStructure((struct CompileState, char*) nameTuple, char* right){
	auto nameState = nameTuple.left;
	auto name = nameTuple.right;
	if (!isSymbol(name)){
		return template Err<>::new(struct CompileError::new("Not a symbol", name));
	}
	return mapValue(compileStatements(mapLast(enter(nameState), lambda1), right, struct Main::compileStructSegment), lambda0);
}
template Result<(struct CompileState, struct T), struct CompileError> Main::infix(char* input, char* infix, template BiFunction<char*, char*, template Result<(struct CompileState, struct T), struct CompileError>> mapper){
	auto typeParamStart = indexOf(input, infix);
	if (typeParamStart >= 0){
		auto left = substring(input, 0, typeParamStart);
		auto right = substring(input, typeParamStart + length(infix));
		return apply(mapper, left, right);
	}
	return createInfixErr(input, infix);
}
template Result<(struct CompileState, struct T), struct CompileError> Main::createInfixErr(char* withoutEnd, char* infix){
	return template Err<>::new(struct CompileError::new("Infix '" + infix + "' not present", withoutEnd));
}
template Result<(struct CompileState, struct T), struct CompileError> Main::createSuffixErr(char* stripped, char* suffix){
	return template Err<>::new(struct CompileError::new("Suffix '" + suffix + "' not present", stripped));
}
auto lambda0(auto name){
	return structName + "::" + name;
}
auto lambda1(auto name){
	return structName + "::" + name;
}
auto lambda2(auto state1, auto s){
	return methodWithBraces(state1, s, generatedHeader, header);
}
auto lambda3(auto state2, auto s){
	return methodWithoutBraces(state2, s, generatedHeader);
}
auto lambda4(auto methodHeaderTuple){
	auto afterParams = strip(substring(input, paramEnd + length(")")));
	auto header = methodHeaderTuple.right;
	auto joinedParams = collect(map(stream(header.params), struct Definition::generate), joining(Collectors, ", "));
	auto structName = orElse(last(state).structProto, struct StructProto::new("?")).name;
	auto withStructName = generate(mapName(header.definition, lambda1));
	auto generatedHeader = withStructName + "(" + joinedParams + ")";
	if (contains(header.definition.annotations, "Actual")){
		auto generated = generatedHeader + ";\n";
		return template Ok<>::new((addFunction(methodHeaderTuple.left, generated), ""));
	}
	return or(methodHeaderTuple.left, afterParams, of(List, lambda2, lambda3));
}
template Result<(struct CompileState, char*), struct CompileError> Main::method(struct CompileState state, char* input){
	auto paramEnd = indexOf(input, ")");
	if (paramEnd < 0){
		return template Err<>::new(struct CompileError::new("Not a method", input));
	}
	auto withParams = substring(input, 0, paramEnd);
	return flatMapValue(methodHeader(state, withParams), lambda4);
}
template Result<(struct CompileState, char*), struct CompileError> Main::methodWithoutBraces(struct CompileState state, char* content, char* header){
	if (equals(content, ";")){
		auto generated = header + " {\n}\n";
		return template Ok<>::new((addFunction(state, generated), ""));
	}
	return template Err<>::new(struct CompileError::new("Content ';' not present", content));
}
auto lambda0(auto statementsTuple){
	auto statementsState = statementsTuple.left;
	auto statements = statementsTuple.right;
	auto oldStatements = template ArrayList<char*>::new();
	addAll(oldStatements, getLast(frames(statementsState)).statements);
	addAll(oldStatements, statements);
	auto generated = header + "{" + generateStatements(oldStatements) + "\n}\n";
	return template Ok<>::new((addFunction(exit(statementsState), generated), ""));
}
auto lambda1(auto last){
	return withFunctionProto(last, proto);
}
template Result<(struct CompileState, char*), struct CompileError> Main::methodWithBraces(struct CompileState state, char* withBraces, char* header, struct FunctionProto proto){
	if (!startsWith(withBraces, "{") || !endsWith(withBraces, "}")){
		return template Err<>::new(struct CompileError::new("No braces present", withBraces));
	}
	auto content = strip(substring(withBraces, 1, length(withBraces) - 1));
	return flatMapValue(parseStatements(mapLast(enter(state), lambda1), content, struct Main::compileFunctionSegment), lambda0);
}
auto lambda0(auto paramsTuple){
	auto paramsState = paramsTuple.left;
	auto params = toList(flatMap(stream(paramsTuple.right), struct Main::retainDefinition));
	if (isSymbol(definitionString)){
		auto definition = struct Definition::new("auto", definitionString);
		return template Ok<>::new((paramsState, struct FunctionProto::new(definition, params)));
	}
	if (parseDefinition(state, definitionString) == 0){
		auto definition = definitionTuple.right;
		return template Ok<>::new((paramsState, struct FunctionProto::new(definition, params)));
	}
	return template Err<>::new(struct CompileError::new("Not a method header", input));
}
template Result<(struct CompileState, struct FunctionProto), struct CompileError> Main::methodHeader(struct CompileState state, char* input){
	auto paramStart = indexOf(input, "(");
	if (paramStart < 0){
		return createInfixErr(input, "(");
	}
	auto definitionString = strip(substring(input, 0, paramStart));
	auto inputParams = substring(input, paramStart + length("("));
	return flatMapValue(parseValues(state, inputParams, struct Main::compileParameter), lambda0);
}
template Stream<struct Definition> Main::retainDefinition(struct Parameter parameter){
	if (parameter == 0){
		return of(Stream, definition1);
	}
	else {
		return empty(Stream);
	}
}
template Result<(struct CompileState, struct Parameter), struct CompileError> Main::compileParameter(struct CompileState state2, char* input){
	return or(state2, input, of(List, typed("?", struct Main::parseWhitespace), typed("?", struct Main::parseDefinition)));
}
template Result<(struct CompileState, char*), struct CompileError> Main::whitespace(struct CompileState state, char* input){
	if (isBlank(input)){
		return template Ok<>::new((state, ""));
	}
	return template Err<>::new(struct CompileError::new("Not whitespace", input));
}
template Result<(struct CompileState, char*), struct CompileError> Main::compileFunctionSegment(struct CompileState state, char* input){
	return or(state, input, of(List, typed("whitespace", struct Main::whitespace), typed("statement", struct Main::functionStatement), typed("block", struct Main::compileBlock)));
}
auto lambda0(auto result){
	return (result.left, indent + result.right + "{" + generateStatements(oldStatements) + indent + "}");
}
auto lambda1(auto statementsTuple){
		auto oldStatements = template ArrayList<char*>::new();
		addAll(oldStatements, getLast(statementsTuple.left.frames).statements);
		addAll(oldStatements, statementsTuple.right);
		return mapValue(compileBlockHeader(exit(statementsTuple.left), beforeContent), lambda0);
}
template Result<(struct CompileState, char*), struct CompileError> Main::compileBlock(struct CompileState state, char* input){
	char* indent = "\n" + repeat("\t", depth(state) - 2);
	if (!endsWith(input, "}")){
		return template Err<>::new(struct CompileError::new("Not a block", input));
	}
	auto withoutEnd = substring(input, 0, length(input) - length("}"));
	auto contentStart1 = findContentStart(withoutEnd);
	if (contentStart1 == 0){
		auto beforeContent = substring(contentStart.left, 0, length(contentStart.left) - length("{"));
		auto content = contentStart.right;
		auto entered = enter(state);
		return flatMapValue(parseStatements(entered, content, struct Main::compileFunctionSegment), lambda1);
	}
	return template Err<>::new(struct CompileError::new("No content start present", withoutEnd));
}
template Option<(char*, char*)> Main::findContentStart(char* input){
	auto divisions = divideAll(input, struct Main::foldContentStart);
	if (size(divisions) < 2){
		return template None<>::new();
	}
	auto first = getFirst(divisions);
	auto after = subList(divisions, 1, size(divisions));
	auto joined = join(String, "", after);
	return template Some<>::new((first, joined));
}
struct DivideState Main::foldContentStart(struct DivideState state, char c){
	auto appended = append(state, c);
	if (c == '{'){
		return advance(appended);
	}
	return appended;
}
template Result<(struct CompileState, char*), struct CompileError> Main::functionStatement(struct CompileState state, char* input){
	return statement(state, input, struct Main::functionStatementValue);
}
auto lambda0(auto result){
	return template Ok<>::new((result.left, "\n" + "\t".repeat(state.depth() - 2) + result.right + ";"));
}
template Result<(struct CompileState, char*), struct CompileError> Main::statement(struct CompileState state, char* input, template BiFunction<struct CompileState, char*, template Result<(struct CompileState, char*), struct CompileError>> mapper){
	auto stripped = strip(input);
	if (!endsWith(stripped, ";")){
		return createSuffixErr(input, ";");
	}
	auto slice = substring(stripped, 0, length(stripped) - length(";"));
	return flatMapValue(apply(mapper, state, slice), lambda0);
}
auto lambda0(auto state1, auto input1){
	return compileConditional(state1, input1, "if");
}
auto lambda1(auto state1, auto input1){
	return compileConditional(state1, input1, "while");
}
template Result<(struct CompileState, char*), struct CompileError> Main::compileBlockHeader(struct CompileState state, char* input){
	return or(Main, state, input, of(List, struct Main::compileElse, lambda0, lambda1));
}
auto lambda0(auto tuple0){
	return template Ok<>::new((tuple0.left, prefix + " (" + tuple0.right + ")"));
}
template Result<(struct CompileState, char*), struct CompileError> Main::compileConditional(struct CompileState state, char* input, char* prefix){
	auto stripped = strip(input);
	if (!startsWith(stripped, prefix)){
		return createPrefixErr(stripped, prefix);
	}
	auto withoutPrefix = strip(substring(stripped, length(prefix)));
	if (!startsWith(withoutPrefix, "(") || !endsWith(withoutPrefix, ")")){
		return template Err<>::new(struct CompileError::new("No condition present", input));
	}
	auto value = substring(withoutPrefix, 1, length(withoutPrefix) - 1);
	return flatMapValue(compileValue(state, value), lambda0);
}
template Result<(struct CompileState, char*), struct CompileError> Main::compileElse(struct CompileState state, char* input){
	if (equals(strip(input), "else")){
		return template Ok<>::new((state, "else "));
	}
	return template Err<>::new(struct CompileError::new("Not an else statement", input));
}
auto lambda0(auto state1, auto input1){
	return compileKeyword(state1, input1, "break");
}
auto lambda1(auto state1, auto input1){
	return compileKeyword(state1, input1, "continue");
}
auto lambda2(auto tuple){
	return (tuple.left, generate(tuple.right));
}
auto lambda3(auto state0, auto input0){
	return mapValue(compileInvokable(state0, input0), lambda2);
}
template Result<(struct CompileState, char*), struct CompileError> Main::functionStatementValue(struct CompileState state, char* input){
	return or(state, input, of(List, lambda0, lambda1, struct Main::compileReturn, lambda3, struct Main::compileAssignment));
}
template Result<(struct CompileState, char*), struct CompileError> Main::compileKeyword(struct CompileState state, char* input, char* equals){
	if (equals(input, equals)){
		return template Ok<>::new((state, equals));
	}
	return template Err<>::new(struct CompileError::new("Not break", input));
}
auto lambda0(auto tuple0){
	return template Ok<>::new((tuple0.left, "return " + tuple0.right));
}
template Result<(struct CompileState, char*), struct CompileError> Main::compileReturn(struct CompileState state, char* input){
	auto stripped = strip(input);
	if (!startsWith(stripped, "return ")){
		return createPrefixErr(stripped, "return ");
	}
	auto right = substring(stripped, length("return "));
	return flatMapValue(compileValue(state, right), lambda0);
}
template Result<(struct CompileState, struct T), struct CompileError> Main::createPrefixErr(char* input, char* prefix){
	return template Err<>::new(struct CompileError::new("Prefix '" + prefix + "' not present", input));
}
auto lambda0(auto valueTuple){
	return (valueTuple.left, definitionTuple.right + " = " + valueTuple.right);
}
auto lambda1(auto definitionTuple){
	return mapValue(compileValue(definitionTuple.left, right), lambda0);
}
template Result<(struct CompileState, char*), struct CompileError> Main::compileAssignment(struct CompileState state, char* input){
	auto valueSeparator = indexOf(input, "=");
	if (valueSeparator < 0){
		return createInfixErr(input, "=");
	}
	auto left = substring(input, 0, valueSeparator);
	auto right = substring(input, valueSeparator + length("="));
	return flatMapValue(compileAssignable(state, left), lambda1);
}
template Result<(struct CompileState, char*), struct CompileError> Main::compileAssignable(struct CompileState state, char* left){
	return or(Main, state, left, of(List, struct Main::definition, struct Main::compileValue));
}
auto lambda0(auto tuple){
	return (left(tuple), generate(right(tuple)));
}
template Result<(struct CompileState, char*), struct CompileError> Main::definition(struct CompileState state, char* input){
	return mapValue(parseDefinition(state, input), lambda0);
}
template Result<(struct CompileState, struct Definition), struct CompileError> Main::parseDefinition(struct CompileState state, char* input){
	auto stripped = strip(input);
	auto valueSeparator = lastIndexOf(stripped, " ");
	if (valueSeparator >= 0){
		auto beforeName = substring(stripped, 0, valueSeparator);
		auto name = strip(substring(stripped, valueSeparator + length(" ")));
		auto annotationSeparator = lastIndexOf(beforeName, "\n");
		if (annotationSeparator < 0){
			return definitionWithAnnotations(state, emptyList(Collections), beforeName, name);
		}
		auto annotations = parseAnnotations(substring(beforeName, 0, annotationSeparator));
		auto beforeName0 = substring(beforeName, annotationSeparator + length("\n"));
		return definitionWithAnnotations(state, annotations, beforeName0, name);
	}
	return template Err<>::new(struct CompileError::new("Invalid definition", input));
}
template List<char*> Main::parseAnnotations(char* annotationsString){
	auto annotationsArray = split(strip(annotationsString), quote(Pattern, "\n"));
	return toList(map(map(stream(Arrays, annotationsArray), char*::strip), struct Main::truncateAnnotationValue));
}
char* Main::truncateAnnotationValue(char* slice){
	if (isEmpty(slice)){
		return "";
	}
	return substring(slice, 1);
}
template Result<(struct CompileState, struct Definition), struct CompileError> Main::definitionWithAnnotations(struct CompileState state, template List<char*> annotations, char* withoutAnnotations, char* name){
	auto stripped = strip(withoutAnnotations);
	if (findTypeSeparator(stripped) == 0){
		auto type = slices.right;
		return definitionWithBeforeType(state, annotations, type, name);
	}
	return definitionWithBeforeType(state, annotations, stripped, name);
}
template Option<(char*, char*)> Main::findTypeSeparator(char* input){
	auto divisions = divideAll(strip(input), struct Main::foldTypeSeparator);
	if (size(divisions) >= 2){
		auto left = subList(divisions, 0, size(divisions) - 1);
		auto joinedLeft = join(String, " ", left);
		return template Some<>::new((joinedLeft, getLast(divisions)));
	}
	return template None<>::new();
}
struct DivideState Main::foldTypeSeparator(struct DivideState state, struct Character c){
	if (c == ' ' && isLevel(state)){
		return advance(state);
	}
	auto appended = append(state, c);
	if (c == '<'){
		return enter(appended);
	}
	if (c == '>'){
		return exit(appended);
	}
	return appended;
}
auto lambda0(auto typeResult){
	return (typeResult.left, struct Definition::new(annotations, emptyList(Collections), typeResult.right, name));
}
template Result<(struct CompileState, struct Definition), struct CompileError> Main::definitionWithBeforeType(struct CompileState state, template List<char*> annotations, char* type, char* name){
	return mapValue(type(state, type), lambda0);
}
template Result<(struct CompileState, char*), struct CompileError> Main::type(struct CompileState state, char* input){
	return or(state, input, of(List, struct Main::primitive, struct Main::symbolType, struct Main::template));
}
auto lambda0(auto argsTuple){
	auto args = argsTuple.right;
	if (equals(base, "Tuple") && size(args) >= 2){
		auto first = get(args, 0);
		auto second = get(args, 1);
		return template Ok<>::new((argsTuple.left, "(" + first + ", " + second + ")"));
	}
	if (equals(base, "Consumer")){
		return template Ok<>::new((argsTuple.left, generateFunctional("void", singletonList(Collections, getFirst(args)))));
	}
	if (equals(base, "Supplier")){
		return template Ok<>::new((argsTuple.left, generateFunctional(getFirst(args), emptyList(Collections))));
	}
	if (equals(base, "Function")){
		return template Ok<>::new((argsTuple.left, generateFunctional(get(args, 1), singletonList(Collections, getFirst(args)))));
	}
	return template Ok<>::new((argsTuple.left, "template " + base + "<" + generateValues(args) + ">"));
}
template Result<(struct CompileState, char*), struct CompileError> Main::template(struct CompileState state, char* input){
	auto stripped = strip(input);
	if (!endsWith(stripped, ">")){
		return createSuffixErr(stripped, ">");
	}
	auto withoutEnd = substring(stripped, 0, length(stripped) - length(">"));
	auto argsStart = indexOf(withoutEnd, "<");
	if (argsStart < 0){
		return createInfixErr(withoutEnd, "<");
	}
	auto base = strip(substring(withoutEnd, 0, argsStart));
	auto argsString = substring(withoutEnd, argsStart + length("<"));
	return flatMapValue(parseValues(state, argsString, struct Main::argumentType), lambda0);
}
char* Main::generateFunctional(char* returnType, template List<char*> arguments){
	return returnType + " (*)(" + String.join(", ", arguments) + ")";
}
template Result<(struct CompileState, char*), struct CompileError> Main::argumentType(struct CompileState state1, char* input1){
	return or(state1, input1, of(List, struct Main::whitespace, struct Main::type));
}
template Result<(struct CompileState, char*), struct CompileError> Main::symbolType(struct CompileState state, char* input){
	auto stripped = strip(input);
	if (isSymbol(stripped)){
		return template Ok<>::new((state, "struct " + stripped));
	}
	return template Err<>::new(struct CompileError::new("Not a symbol", stripped));
}
template Result<(struct CompileState, char*), struct CompileError> Main::primitive(struct CompileState state, char* input){
	auto stripped = strip(input);
	if (equals(stripped, "var")){
		return template Ok<>::new((state, "auto"));
	}
	if (equals(stripped, "char")){
		return template Ok<>::new((state, "char"));
	}
	if (equals(stripped, "void")){
		return template Ok<>::new((state, "void"));
	}
	if (equals(stripped, "String")){
		return template Ok<>::new((state, "char*"));
	}
	if (equals(stripped, "boolean") || equals(stripped, "int")){
		return template Ok<>::new((state, "int"));
	}
	return template Err<>::new(struct CompileError::new("Not a primitive", input));
}
auto lambda0(auto arg){
	return !(arg == 0);
}
auto lambda1(auto arg){
	return !(arg == 0);
}
auto lambda2(auto state2, auto callerString1){
	return constructorCaller(state2, callerString1, oldArguments);
}
auto lambda3(auto state1, auto s){
	return invocationCaller(state1, s, oldArguments);
}
auto lambda4(auto argumentsTuple){
	auto argumentState = argumentsTuple.left;
	auto oldArguments = toList(filter(stream(argumentsTuple.right), lambda1));
	return or(argumentState, callerString, of(List, lambda2, lambda3));
}
template Result<(struct CompileState, struct Value), struct CompileError> Main::compileInvokable(struct CompileState state, char* input){
	auto stripped = strip(input);
	if (!endsWith(stripped, ")")){
		return createSuffixErr(stripped, ")");
	}
	auto withoutEnd = substring(stripped, 0, length(stripped) - length(")"));
	auto divisions = divideAll(withoutEnd, struct Main::foldInvocationStart);
	if (size(divisions) < 2){
		return template Err<>::new(struct CompileError::new("Insufficient divisions", withoutEnd));
	}
	auto joined = join(String, "", subList(divisions, 0, size(divisions) - 1));
	auto callerString = substring(joined, 0, length(joined) - length(")"));
	auto inputArguments = getLast(divisions);
	auto tupleCompileErrorResult = parseValues(state, inputArguments, struct Main::parseArgument);
	return flatMapValue(tupleCompileErrorResult, lambda4);
}
auto lambda0(auto callerTuple){
	auto invocation = struct Invocation::new(struct MethodAccess::new(callerTuple.right, "new"), oldArguments);
	return template Ok<>::new((callerTuple.left, invocation));
}
template Result<(struct CompileState, struct Value), struct CompileError> Main::constructorCaller(struct CompileState state, char* callerString, template List<struct Value> oldArguments){
	auto stripped = strip(callerString);
	if (!startsWith(stripped, "new ")){
		return createPrefixErr(stripped, "new ");
	}
	auto withoutPrefix = substring(stripped, length("new "));
	if (equals(withoutPrefix, "Tuple<>") && size(oldArguments) >= 2){
		return template Ok<>::new((state, struct TupleNode::new(get(oldArguments, 0), get(oldArguments, 1))));
	}
	return flatMapValue(type(state, withoutPrefix), lambda0);
}
template Result<(struct CompileState, struct Value), struct CompileError> Main::invocationCaller(struct CompileState state, char* input, template List<struct Value> arguments){
	if (value(state, input) == 0){
		auto callerState = callerTuple.left;
		auto oldCaller = callerTuple.right;
		struct Value newCaller = oldCaller;
		auto newArguments = template ArrayList<struct Value>::new();
		if (oldCaller == 0){
			add(newArguments, parent);
			newCaller = struct Symbol::new(property);
		}
		addAll(newArguments, arguments);
		return template Ok<>::new((callerState, struct Invocation::new(newCaller, newArguments)));
	}
	return template Err<>::new(struct CompileError::new("Not an invocation", input));
}
template Result<(struct CompileState, struct Value), struct CompileError> Main::parseArgument(struct CompileState state1, char* input1){
	return or(state1, input1, of(List, typed("?", struct Main::parseWhitespace), typed("?", struct Main::value)));
}
template Result<(struct CompileState, struct Whitespace), struct CompileError> Main::parseWhitespace(struct CompileState state, char* input){
	if (isBlank(input)){
		return template Ok<>::new((state, struct Whitespace::new()));
	}
	else {
		return template Err<>::new(struct CompileError::new("Not whitespace", input));
	}
}
auto lambda0(auto tuple){
	return (tuple.left, generateValues(tuple.right));
}
template Result<(struct CompileState, char*), struct CompileError> Main::compileValues(struct CompileState state, char* input, template BiFunction<struct CompileState, char*, template Result<(struct CompileState, char*), struct CompileError>> compiler){
	return mapValue(parseValues(state, input, compiler), lambda0);
}
char* Main::generateValues(template List<char*> elements){
	return generateAll(elements, struct Main::mergeValues);
}
template Result<(struct CompileState, template List<struct T>), struct CompileError> Main::parseValues(struct CompileState state, char* input, template BiFunction<struct CompileState, char*, template Result<(struct CompileState, struct T), struct CompileError>> compiler){
	return parseAll(state, input, struct Main::foldValueChar, compiler);
}
struct StringBuilder Main::mergeValues(struct StringBuilder cache, char* element){
	if (isEmpty(cache)){
		return append(cache, element);
	}
	return append(append(cache, ", "), element);
}
struct DivideState Main::foldValueChar(struct DivideState state, char c){
	if (c == ',' && isLevel(state)){
		return advance(state);
	}
	auto appended = append(state, c);
	if (c == '-' && peek(appended) == '>'){
		return orElse(popAndAppendToOption(appended), appended);
	}
	if (c == '(' || c == '<'){
		return enter(appended);
	}
	if (c == ')' || c == '>'){
		return exit(appended);
	}
	return appended;
}
auto lambda0(auto tuple){
	return (tuple.left, generate(tuple.right));
}
template Result<(struct CompileState, char*), struct CompileError> Main::compileValue(struct CompileState state, char* input){
	return mapValue(value(state, input), lambda0);
}
template Result<(struct CompileState, struct Value), struct CompileError> Main::value(struct CompileState state, char* input){
	template List<template BiFunction<struct CompileState, char*, template Result<(struct CompileState, struct Value), struct CompileError>>> beforeOperators = of(List, typed("?", struct Main::compileNot), typed("?", struct Main::compileString), typed("?", struct Main::compileChar), typed("lambda", struct Main::compileLambda));
	template List<template BiFunction<struct CompileState, char*, template Result<(struct CompileState, struct Value), struct CompileError>>> afterOperators = of(List, typed("invokable", struct Main::compileInvokable), typed("?", struct Main::compileAccess), typed("?", struct Main::parseBooleanValue), typed("?", struct Main::compileSymbolValue), typed("?", struct Main::compileMethodReference), typed("?", struct Main::parseNumber), typed("instanceof", struct Main::instanceOfNode));
	auto rules = template ArrayList<template BiFunction<struct CompileState, char*, template Result<(struct CompileState, struct Value), struct CompileError>>>::new(beforeOperators);
	addAll(rules, toList(map(stream(Arrays, values(Operator)), struct Main::createOperatorRule)));
	addAll(rules, afterOperators);
	return or(state, input, rules);
}
auto lambda0(auto state1, auto input1){
	return operator(state1, input1, value);
}
template BiFunction<struct CompileState, char*, template Result<(struct CompileState, struct Value), struct CompileError>> Main::createOperatorRule(struct Operator value){
	return typed(name(value), lambda0);
}
auto lambda0(auto valueResult){
	return (valueResult.left, struct Operation::new(valueResult.right, Operator.EQUALS, struct NumberValue::new("0")));
}
auto lambda1(auto beforeKeyword, auto _){
	return mapValue(value(state, beforeKeyword), lambda0);
}
template Result<(struct CompileState, struct Value), struct CompileError> Main::instanceOfNode(struct CompileState state, char* input){
	return infix(input, "instanceof", lambda1);
}
auto lambda0(auto inner){
	return (inner.left, struct Not::new(inner.right));
}
template Result<(struct CompileState, struct Not), struct CompileError> Main::compileNot(struct CompileState state, char* input){
	auto stripped = strip(input);
	if (!startsWith(stripped, "!")){
		return template Err<>::new(struct CompileError::new("Not not", input));
	}
	return mapValue(value(state, substring(stripped, 1)), lambda0);
}
template Result<(struct CompileState, struct NumberValue), struct CompileError> Main::parseBooleanValue(struct CompileState state, char* input){
	auto stripped = strip(input);
	if (equals(stripped, "false")){
		return template Ok<>::new((state, struct NumberValue::new("0")));
	}
	if (equals(stripped, "true")){
		return template Ok<>::new((state, struct NumberValue::new("1")));
	}
	return template Err<>::new(struct CompileError::new("Not a valid boolean value", input));
}
template Result<(struct CompileState, struct CharValue), struct CompileError> Main::compileChar(struct CompileState state, char* input){
	auto stripped = strip(input);
	if (startsWith(stripped, "'") && endsWith(stripped, "'") && length(stripped) >= 3){
		return template Ok<>::new((state, struct CharValue::new(substring(stripped, 1, length(stripped) - 1))));
	}
	else {
		return template Err<>::new(struct CompileError::new("Not a char", input));
	}
}
template Result<(struct CompileState, struct Value), struct CompileError> Main::parseNumber(struct CompileState state, char* input){
	auto stripped = strip(input);
	if (isNumber(stripped)){
		return template Ok<>::new((state, struct NumberValue::new(stripped)));
	}
	else {
		return template Err<>::new(struct CompileError::new("Not a valid number", stripped));
	}
}
int Main::isNumber(char* input){
	return allMatch(mapToObj(range(IntStream, 0, length(input)), struct input::charAt), struct Character::isDigit);
}
auto lambda0(auto rightTuple){
	auto operation = struct Operation::new(leftTuple.right, operator, rightTuple.right);
	return (rightTuple.left, operation);
}
auto lambda1(auto leftTuple){
	return mapValue(value(leftTuple.left, right), lambda0);
}
template Result<(struct CompileState, struct Operation), struct CompileError> Main::operator(struct CompileState state, char* input, struct Operator operator){
	auto index = indexOf(input, operator.representation);
	if (index < 0){
		return createInfixErr(input, operator.representation);
	}
	auto left = substring(input, 0, index);
	auto right = substring(input, index + length(operator.representation));
	return flatMapValue(value(state, left), lambda1);
}
auto lambda0(auto errs){
	return struct CompileError::new("No valid rule present", input, errs);
}
auto lambda1(auto tOrState, auto mapper){
	if (isPresent(tOrState.option)){
		return tOrState;
	}
	return match(apply(mapper, state, input), struct tOrState::withValue, struct tOrState::withError);
}
auto lambda2(auto _, auto next){
	return next;
}
template Result<(struct CompileState, struct T), struct CompileError> Main::or(struct CompileState state, char* input, template List<template BiFunction<struct CompileState, char*, template Result<(struct CompileState, struct T), struct CompileError>>> rules){
	return mapErr(toResult(reduce(stream(rules), template OrState<struct T>::new(), lambda1, lambda2)), lambda0);
}
auto lambda0(auto err){
	return struct CompileError::new("Invalid type '" + type + "'", input, singletonList(Collections, err));
}
auto lambda1(auto value){
	return (struct CompileState, struct S)::new(value.left, value.right);
}
auto lambda2(auto state, auto input){
	return mapErr(mapValue(apply(mapper, state, input), lambda1), lambda0);
}
template BiFunction<struct CompileState, char*, template Result<(struct CompileState, struct S), struct CompileError>> Main::typed(char* type, template BiFunction<struct CompileState, char*, template Result<(struct CompileState, struct T), struct CompileError>> mapper){
	return lambda2;
}
template Result<(struct CompileState, struct MethodAccess), struct CompileError> Main::compileMethodReference(struct CompileState state, char* input){
	auto functionSeparator = indexOf(strip(input), "::");
	if (functionSeparator >= 0){
		auto left = substring(strip(input), 0, functionSeparator);
		auto right = strip(substring(strip(input), functionSeparator + length("::")));
		auto maybeLeftTuple = type(state, left);
		if (maybeLeftTuple == 0){
			if (isSymbol(right)){
				return template Ok<>::new((leftTuple.left, struct MethodAccess::new(leftTuple.right, right)));
			}
		}
	}
	return template Err<>::new(struct CompileError::new("Not a method reference", input));
}
template Result<(struct CompileState, struct Symbol), struct CompileError> Main::compileSymbolValue(struct CompileState state, char* input){
	auto stripped = strip(input);
	if (isSymbol(stripped)){
		return template Ok<>::new((struct CompileState, struct Symbol)::new(state, struct Symbol::new(stripped)));
	}
	return template Err<>::new(struct CompileError::new("Not a symbol", input));
}
template Result<(struct CompileState, struct DataAccess), struct CompileError> Main::compileAccess(struct CompileState state, char* input){
	auto separator = lastIndexOf(strip(input), ".");
	if (separator >= 0){
		auto parent = substring(strip(input), 0, separator);
		auto child = substring(strip(input), separator + length("."));
		if (isSymbol(child) && value(state, parent) == 0){
			return template Ok<>::new((struct CompileState, struct DataAccess)::new(tuple.left, struct DataAccess::new(tuple.right, child)));
		}
	}
	return template Err<>::new(struct CompileError::new("Not data access", input));
}
template Result<(struct CompileState, struct StringValue), struct CompileError> Main::compileString(struct CompileState state, char* input){
	auto stripped = strip(input);
	if (startsWith(stripped, "\"") && endsWith(stripped, "\"") && length(stripped) >= 2){
		return template Ok<>::new((state, struct StringValue::new(substring(stripped, 1, length(stripped) - 1))));
	}
	return template Err<>::new(struct CompileError::new("Not a string", input));
}
auto lambda0(auto result){
	return assembleLambda(result.left, paramNames, result.right);
}
auto lambda0(auto valueTuple){
	return assembleLambda(valueTuple.left, paramNames, "\n\treturn " + valueTuple.right + ";");
}
template Result<(struct CompileState, struct Symbol), struct CompileError> Main::compileLambda(struct CompileState state, char* input){
	auto arrowIndex = indexOf(input, "->");
	if (arrowIndex < 0){
		return createInfixErr(input, "->");
	}
	auto beforeArrow = strip(substring(input, 0, arrowIndex));
	auto afterArrow = substring(input, arrowIndex + length("->"));
	if (!(findLambdaParamNames(beforeArrow) == 0)){
		return template Err<>::new(struct CompileError::new("No valid lambda parameter names found", beforeArrow));
	}
	auto withBraces = strip(afterArrow);
	if (startsWith(withBraces, "{") && endsWith(withBraces, "}")){
		auto content = substring(withBraces, 1, length(withBraces) - 1);
		return flatMapValue(compileStatements(state, content, struct Main::compileFunctionSegment), lambda0);
	}
	return flatMapValue(compileValue(state, afterArrow), lambda0);
}
auto lambda0(auto value){
	return !isEmpty(value);
}
auto lambda1(auto value){
	return !isEmpty(value);
}
template Option<template List<char*>> Main::findLambdaParamNames(char* beforeArrow){
	if (isSymbol(beforeArrow)){
		return template Some<>::new(singletonList(Collections, beforeArrow));
	}
	if (startsWith(beforeArrow, "(") && endsWith(beforeArrow, ")")){
		auto paramNames = toList(filter(map(stream(Arrays, split(substring(beforeArrow, 1, length(beforeArrow) - 1), quote(Pattern, ","))), char*::strip), lambda1));
		return template Some<>::new(paramNames);
	}
	return template None<>::new();
}
auto lambda0(auto name){
	return "auto " + name;
}
auto lambda1(auto name){
	return "auto " + name;
}
template Result<(struct CompileState, struct Symbol), struct CompileError> Main::assembleLambda(struct CompileState state, template List<char*> paramNames, char* content){
	auto nameTuple = createName(state, "lambda");
	auto generatedName = nameTuple.left;
	auto joinedParams = collect(map(stream(paramNames), lambda1), joining(Collectors, ", "));
	return template Ok<>::new((addFunction(nameTuple.right, "auto " + generatedName + "(" + joinedParams + "){" + content + "\n}\n"), struct Symbol::new(generatedName)));
}
auto lambda0(auto index){
	auto c = charAt(input, index);
	return isLetter(Character, c) || c == '_' || (index != 0 && isDigit(Character, c));
}
int Main::isSymbol(char* input){
	auto stripped = strip(input);
	if (isEmpty(stripped)){
		return 0;
	}
	return allMatch(range(IntStream, 0, length(stripped)), lambda0);
}
struct DivideState Main::foldInvocationStart(struct DivideState state, char c){
	auto appended = append(state, c);
	if (c == '('){
		auto entered = enter(appended);
		if (isShallow(appended)){
			return advance(entered);
		}
		else {
			return entered;
		}
	}
	if (c == ')'){
		return exit(appended);
	}
	return appended;
}
