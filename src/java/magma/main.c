/* package magma; */
/* import java.io.IOException; */
/* import java.nio.file.Files; */
/* import java.nio.file.Paths; */
/* import java.util.function.BiFunction; */
/* import java.util.function.Function; */
/* import java.util.function.Supplier; */
/*  */
/* public sealed */struct Option {
};
/*  */struct Head {
};
/* public */struct List {
};
/* public */struct Collector {
};
/* private */struct Type {
};
/* private */struct Defined {
};
/* private */struct Primitive implements Type {
	/* Bit("int"),
        I8("char"), */ /* I32("int"), */ Var("auto");
	/* private final */ char* value;/* 

        Primitive(String value) {
            this.value = value;
        } */
};
/* public */struct Tuple {
};
/* public static */struct RangeHead implements Head {
	/* private final */ int length;
	/* private */ int counter;/* 

        public RangeHead(int length) {
            this.length = length;
        } */
};
/* public */struct Iterator {
};
/* private static */struct State {
	/* private final */ List<char*> segments;
	/* private */ struct StringBuilder buffer;
	/* private */ int depth;/* 

        public State() {
            this(Lists.emptyList(), new StringBuilder(), 0);
        } */
};
/* private */struct Some {
};
/* private */struct None {
};
/* private */struct Joiner {
};
/* private static */struct ListCollector {
};
/* private */struct Generic {
};
/* private */struct Content {
};
/* private */struct Functional {
};
/* private */struct Definition {
};
/* private */struct FunctionalDefinition {
};
/* private */struct Ref {
};
/* private */struct OptionCollector {
};
/* private */struct Struct {
};
/* private */struct Whitespace {
};
/* private */struct Invokable {
};
/*  */struct Main {
};
// Option<struct R>
// Option<struct T>
// Tuple<struct T, struct R>
// Option<Tuple<struct T, struct R>>
// List<struct T>
// Iterator<struct T>
// Option<int>
// Some<>
// None<>
// Iterator<struct R>
// Iterator<>
// Collector<struct T, struct C>
// List<char*>
// Option<char*>
// Option<struct C>
// Option<List<struct T>>
// List<struct Type>
// Option<struct Invokable>
// Option<struct Whitespace>
// Option<struct Defined>
// Option<struct Definition>
// Option<struct Type>
// Option<List<char*>>
/* <R> */ Option<struct R> map_Option(struct R (*mapper)(struct T));
struct T orElseGet_Option(struct T (*other)());
Option<struct T> or_Option(Option<struct T> (*other)());
struct T orElse_Option(struct T other);
/* <R> */ Option<struct R> flatMap_Option(Option<struct R> (*mapper)(struct T));
/* <R> */ Option<Tuple<struct T, struct R>> and_Option(Option<struct R> (*other)());
Option<struct T> next_Head();
List<struct T> add_List(struct T element);
Iterator<struct T> iter_List();
int hasElements_List();
struct T removeFirst_List();
struct T get_List(int index);
int contains_List(struct T element);
struct C createInitial_Collector();
struct C fold_Collector(struct C current, struct T element);
char* generate_Type();
char* generate_Defined();
struct Defined mapName_Defined(char* (*mapper)(char*));
/* @Override
        public */ char* generate_Primitive implements Type(){
	return this.value;
}
/* @Override
        public */ Option<int> next_RangeHead implements Head(){
	if (this.counter < this.length){
		auto value = this.counter;
		this.counter++;
		return Some<>(value);
	}
	else {
		return None<>(/*  */);
	}
}
/* public <R> */ Iterator<struct R> map_Iterator(struct R (*mapper)(struct T)){
	return Iterator<>(/*  */(/* ) */ -> this.head.next(/*  */).map(mapper));
}
/* public <R> */ struct R fold_Iterator(struct R initial, struct R (*folder)(struct R, struct T)){
	auto current = initial;
	while (true){
		/* switch (this.head.next()) */{
			/* case Some<T>(var value) -> */ current = folder.apply(current, value);
			/* case None<T> _ -> */{
				return current;
			}
		}
	}
}
/* public <C> */ struct C collect_Iterator(Collector<struct T, struct C> collector){
	return this.fold(collector.createInitial(/*  */), /*  collector::fold */);
}
struct private State_State(List<char*> segments, struct StringBuilder buffer, int depth){
	/* this.segments = segments; */
	/* this.buffer = buffer; */
	/* this.depth = depth; */
}
/* public */ int isLevel_State(){
	return this.depth == 0;
}
/* public */ struct State enter_State(){
	this.depth++;
	return this;
}
/* public */ struct State exit_State(){
	/* this.depth-- */;
	return this;
}
/* private */ struct State append_State(struct char c){
	/* this.buffer.append(c) */;
	return this;
}
/* private */ struct State advance_State(){
	/* this.segments.add(this.buffer.toString()) */;
	/* this.buffer = new StringBuilder(); */
	return this;
}
/* public */ int isShallow_State(){
	return this.depth == 1;
}
/* @Override
        public <R> */ Option<struct R> map_Some(struct R (*mapper)(struct T)){
	return Some<>(mapper.apply(this.value));
}
/* @Override
        public */ struct T orElseGet_Some(struct T (*other)()){
	return this.value;
}
/* @Override
        public */ Option<struct T> or_Some(Option<struct T> (*other)()){
	return this;
}
/* @Override
        public */ struct T orElse_Some(struct T other){
	return this.value;
}
/* @Override
        public <R> */ Option<struct R> flatMap_Some(Option<struct R> (*mapper)(struct T)){
	return mapper.apply(this.value);
}
/* @Override
        public <R> */ Option<Tuple<struct T, struct R>> and_Some(Option<struct R> (*other)()){
	return other.get(/* ) */.map(/* otherValue */ -> /* new Tuple<>(this */.value, otherValue));
}
/* @Override
        public <R> */ Option<struct R> map_None(struct R (*mapper)(struct T)){
	return None<>(/*  */);
}
/* @Override
        public */ struct T orElseGet_None(struct T (*other)()){
	return other.get(/*  */);
}
/* @Override
        public */ Option<struct T> or_None(Option<struct T> (*other)()){
	return other.get(/*  */);
}
/* @Override
        public */ struct T orElse_None(struct T other){
	return other;
}
/* @Override
        public <R> */ Option<struct R> flatMap_None(Option<struct R> (*mapper)(struct T)){
	return None<>(/*  */);
}
/* @Override
        public <R> */ Option<Tuple<struct T, struct R>> and_None(Option<struct R> (*other)()){
	return None<>(/*  */);
}
struct private Joiner_Joiner(){
	/* this("") */;
}
/* @Override
        public */ Option<char*> createInitial_Joiner(){
	return None<>(/*  */);
}
/* @Override
        public */ Option<char*> fold_Joiner(Option<char*> maybeCurrent, char* element){
	/* return new Some<>(switch (maybeCurrent) */{
		/* case None<String> _ -> element */;
		/* case Some<String>(var current) -> current + this.delimiter + element */;
	}
	/* ) */;
}
/* @Override
        public */ List<struct T> createInitial_ListCollector(){
	return Lists.emptyList(/*  */);
}
/* @Override
        public */ List<struct T> fold_ListCollector(List<struct T> current, struct T element){
	return current.add(element);
}
/* @Override
        public */ char* generate_Generic(){
	auto joined = generateValuesFromNodes(this.args);
	return this.base + "<" + joined + ">";
}
/* @Override
        public */ char* generate_Content(){
	return Main.generatePlaceholder(this.input);
}
/* @Override
        public */ char* generate_Functional(){
	return this.returnType.generate() + " (*)(" + generateValuesFromNodes(this.paramTypes) + ")";
}
/* @Override
        public */ char* generate_Definition(){
	auto beforeTypeString = this.beforeType.map(/* Main::generatePlaceholder)
                    .map(inner */ -> /* inner + " ")
                     */.orElse("");
	return /* beforeTypeString + this */.type.generate() + " " + this.name;
}
/* @Override
        public */ struct Defined mapName_Definition(char* (*mapper)(char*)){
	return struct Definition(this.beforeType, this.type, mapper.apply(this.name));
}
/* @Override
        public */ char* generate_FunctionalDefinition(){
	auto beforeTypeString = this.beforeType.map(/* inner */ -> /* inner + " ") */.orElse("");
	return /* "%s%s  */(/* *%s) */(/* %s)" */.formatted(beforeTypeString, this.returns.generate(), this.name, generateValuesFromNodes(this.args));
}
/* @Override
        public */ struct Defined mapName_FunctionalDefinition(char* (*mapper)(char*)){
	return struct FunctionalDefinition(this.beforeType, this.returns, mapper.apply(this.name), this.args);
}
/* @Override
        public */ char* generate_Ref(){
	return this.type.generate() + "*";
}
/* @Override
        public */ Option<struct C> createInitial_OptionCollector(){
	return Some<>(this.collector.createInitial(/*  */));
}
/* @Override
        public */ Option<struct C> fold_OptionCollector(Option<struct C> current, Option<struct T> element){
	return current.and(/*  */(/* ) */ -> /* element).map(tuple */ -> this.collector.fold(tuple.left, tuple.right));
}
/* @Override
        public */ char* generate_Struct(){
	return /* "struct " + this */.name;
}
/* @Override
        public */ char* generate_Whitespace(){
	return /* "" */;
}
/* private */ char* generate_Invokable(){
	auto joined = Main.generateValues(this.args);
	return this.beforeArgs + "(" + joined + ")";
}
/* public static final List<Generic> generics */ /* = */ Lists.emptyList_Invokable();
/* private static final List<String> structs */ /* = */ Lists.emptyList_Invokable();
/* private static final List<String> methods */ /* = */ Lists.emptyList_Invokable();
/* private static Option<String> currentStruct = */ struct new None<>_Invokable();
/* private static */ char* generateAll_Invokable(struct StringBuilder (*merger)(struct StringBuilder, char*), List<char*> parsed){
	return parsed.iter(/* )
                 */.fold(/* new StringBuilder( */), /* merger)
                 */.toString();
}
/* private static <T> */ Option<List<struct T>> parseAll_Invokable(char* input, struct State (*folder)(struct State, struct Character), Option<struct T> (*compiler)(char*)){
	return Main.divideAll(input, /* folder)
                 */.iter(/* )
                 */.map(/* compiler)
                 */.collect(new OptionCollector<>(new ListCollector<>()));
}
/* private static */ List<char*> divideAll_Invokable(char* input, struct State (*folder)(struct State, struct Character)){
	auto current = struct State(/*  */);
	auto queue = Iterator<>(struct RangeHead(input.length())).map(input::charAt).collect(new ListCollector<>());
	while (queue.hasElements(/*  */)){
		auto c = queue.removeFirst(/*  */);
		if (/* c == '\'' */){
			/* current.append(c) */;
			auto c1 = queue.removeFirst(/*  */);
			/* current.append(c1) */;
			if (/* c1 == '\\' */){
				/* current.append(queue.removeFirst()) */;
			}
			/* current.append(queue.removeFirst()) */;
			/* continue */;
		}
		if (/* c == '"' */){
			/* current.append(c) */;
			while (queue.hasElements(/*  */)){
				auto next = queue.removeFirst(/*  */);
				/* current.append(next) */;
				if (/* next == '\\' */){
					/* current.append(queue.removeFirst()) */;
				}
				if (/* next == '"' */){
					/* break */;
				}
			}
			/* continue */;
		}
		/* current = folder.apply(current, c); */
	}
	return current.advance(/*  */).segments;
}
/* private static */ char* generateValues_Invokable(List<char*> parserd){
	return Main.generateAll(/* Main::mergeValues */, parserd);
}
/* private static */ struct StringBuilder mergeValues_Invokable(struct StringBuilder cache, char* element){
	if (cache.isEmpty(/*  */)){
		return cache.append(element);
	}
	return cache.append(/* ", ") */.append(element);
}
/* private static */ char* generatePlaceholder_Invokable(char* input){
	return /* "/* " + input + " */" */;
}
/* private static */ char* generateValuesFromNodes_Invokable(List<struct Type> list){
	return list.iter(/* )
                 */.map(Type::generate).collect(new Joiner(", ")).orElse("");
}
/* private static */ char* compileStatementOrBlock_Invokable(char* input, int depth){
	return parseWhitespace(/* input) */.map(/* Whitespace::generate)
                 */.or(/* () */ -> /* compileStatement(input, Main::compileStatementValue, depth))
                .or(() */ -> /* compileBlock(input, depth))
                .orElseGet(() */ -> /* createIndent(depth) + generatePlaceholder(input */.strip()));
}
/* private static */ char* createIndent_Invokable(int depth){
	return /* "\n" + "\t" */.repeat(depth);
}
/* private static */ Option<char*> compileBlock_Invokable(char* input, int depth){
	auto stripped = input.strip(/*  */);
	if (stripped.endsWith(/* "}" */)){
		auto withoutEnd = stripped.substring(/* 0 */, stripped.length(/* ) - "}" */.length());
		auto contentStart = withoutEnd.indexOf(/* "{" */);
		if (/* contentStart >= 0 */){
			auto beforeContent = withoutEnd.substring(/* 0 */, contentStart);
			auto content = withoutEnd.substring(/* contentStart + "{" */.length(/*  */));
			auto indent = createIndent(depth);
			return Some<>(/* indent + compileBeforeBlock(beforeContent) + "{" + compileStatementsOrBlocks(content */, /*  depth) + indent + "}" */);
		}
	}
	return None<>(/*  */);
}
/* private static */ char* compileBeforeBlock_Invokable(char* input){
	if (input.strip(/* ) */.equals("else")){
		return /* "else " */;
	}
	return compileConditional(input, /* "if")
                 */.or(/*  */(/* ) */ -> /* compileConditional(input, "while"))
                .orElseGet(() */ -> /* generatePlaceholder(input */.strip()));
}
/* private static */ Option<char*> compileConditional_Invokable(char* input, char* prefix){
	auto stripped = input.strip(/*  */);
	if (stripped.startsWith(prefix)){
		auto withoutKeyword = stripped.substring(prefix.length(/* ) */).strip();
		if (withoutKeyword.startsWith(/* "(") && withoutKeyword */.endsWith(")")){
			auto condition = withoutKeyword.substring(/* 1 */, withoutKeyword.length() - 1);
			return Some<>(/* prefix + " (" + compileValue(condition) + ")" */);
		}
	}
	return None<>(/*  */);
}
/* private static */ Option<char*> compileStatementValue_Invokable(char* input){
	auto stripped = input.strip(/*  */);
	if (stripped.startsWith(/* "return " */)){
		auto value = stripped.substring(/* "return " */.length(/*  */));
		return Some<>(/* "return " + compileValue */(value));
	}
	if (stripped.endsWith(/* "++" */)){
		auto slice = stripped.substring(/* 0 */, stripped.length(/* ) - "++" */.length());
		return Some<>(/* compileValue(slice) + "++" */);
	}
	auto valueSeparator = stripped.indexOf(/* "=" */);
	if (/* valueSeparator >= 0 */){
		auto definition = stripped.substring(/* 0 */, valueSeparator);
		auto value = stripped.substring(/* valueSeparator + "=" */.length(/*  */));
		return compileDefinitionToString(/* definition)
                     */.map(/* outputDefinition */ -> /*  outputDefinition + " = " + compileValue(value */));
	}
	return Some<>(generatePlaceholder(input));
}
/* private static */ char* compileValue_Invokable(char* input){
	auto stripped = input.strip(/*  */);
	if (stripped.startsWith(/* "new " */)){
		auto slice = stripped.substring(/* "new " */.length(/* ) */).strip();
		if (compileInvokable(slice, /*  Main::compileConstructorCaller) instanceof Some(var value */)){
			return value;
		}
	}
	if (compileInvocation(/* input) instanceof Some(var value */)){
		return value;
	}
	auto arrowIndex = input.indexOf(/* " */ -> /* " */);
	if (/* arrowIndex >= 0 */){
		auto beforeArrow = input.substring(/* 0 */, /* arrowIndex) */.strip();
		auto afterArrow = input.substring(/* arrowIndex + " */ -> /* " */.length(/*  */));
		return generatePlaceholder(/* beforeArrow) + " */ -> /*  " + compileValue(afterArrow */);
	}
	auto separator = stripped.lastIndexOf(/* " */.");
	if (/* separator >= 0 */){
		auto parent = stripped.substring(/* 0 */, separator);
		auto property = stripped.substring(/* separator + " */.".length(/*  */));
		return /* compileValue(parent) + " */." + property;
	}
	if (isSymbol(stripped)){
		return stripped;
	}
	return generatePlaceholder(input);
}
/* private static */ Option<char*> compileInvocation_Invokable(char* input){
	return compileInvokable(input, /*  Main::compileValue */);
}
/* private static */ Option<char*> compileInvokable_Invokable(char* slice, char* (*beforeArgsCompiler)(char*)){
	return getRecord(slice, /* beforeArgsCompiler) */.map(Invokable::generate);
}
/* private static */ Option<struct Invokable> getRecord_Invokable(char* slice, char* (*beforeArgsCompiler)(char*)){
	if (/* !slice */.endsWith(/* ")" */)){
		return None<>(/*  */);
	}
	auto withoutEnd = slice.substring(/* 0 */, slice.length(/* ) - ")" */.length());
	auto argsStart = withoutEnd.indexOf(/* "(" */);
	if (/* argsStart < 0 */){
		return None<>(/*  */);
	}
	auto base = withoutEnd.substring(/* 0 */, argsStart);
	auto args = withoutEnd.substring(/* argsStart + " */(/* " */.length());
	/* return parseValues(args, value -> new Some<>(compileValue(value))).map(newArgs -> */{
		auto generate = beforeArgsCompiler.apply(base);
		return struct Invokable(generate, newArgs);
	}
	/* ) */;
}
/* private static */ char* compileConstructorCaller_Invokable(char* base){
	if (parseAndModifyType(/* base) instanceof Some<Type>(var type */)){
		return type.generate(/*  */);
	}
	return generatePlaceholder(base);
}
/* private static */ int isSymbol_Invokable(char* input){
	if (input.isEmpty(/*  */)){
		return false;
	}
	/* for */ /* (var */ i = /*  0 */;
	/* i < input.length() */;
	/* i++) */{
		auto c = input.charAt(i);
		if (Character.isLetter(c)){
			/* continue */;
		}
		return false;
	}
	return true;
}
/* private static */ Option<struct Whitespace> parseWhitespace_Invokable(char* input){
	if (input.isBlank(/*  */)){
		return Some<>(struct Whitespace(/*  */));
	}
	return None<>(/*  */);
}
/* private static */ Option<struct Defined> parseAndModifyDefinition_Invokable(char* input){
	/* return Main.parseDefinition(input).map(definition -> */{
		if (definition.type instanceof Functional(/* var args */, /*  var base */)){
			return struct FunctionalDefinition(definition.beforeType, base, definition.name, args);
		}
		return definition;
	}
	/* ) */;
}
/* private static */ Option<char*> compileStatement_Invokable(char* input, Option<char*> (*compiler)(char*), int depth){
	auto stripped = input.strip(/*  */);
	if (/* !stripped */.endsWith(/* ";" */)){
		return None<>(/*  */);
	}
	auto withoutEnd = stripped.substring(/* 0 */, stripped.length(/* ) - ";" */.length());
	return compiler.apply(/* withoutEnd) */.map(/* definition */ -> /*  generateStatement(definition, depth */));
}
/* private static */ char* generateStatement_Invokable(char* definition, int depth){
	return /* createIndent(depth) + definition + ";" */;
}
/* private static <T> */ Option<List<struct T>> parseValues_Invokable(char* input, Option<struct T> (*compiler)(char*)){
	return Main.parseAll(input, /*  Main::foldValueChar */, compiler);
}
/* private static */ struct State foldValueChar_Invokable(struct State state, struct char c){
	if (/* c == ',' && state */.isLevel(/*  */)){
		return state.advance(/*  */);
	}
	auto appended = state.append(c);
	if (/* c == '<' */){
		return appended.enter(/*  */);
	}
	if (/* c == '>' */){
		return appended.exit(/*  */);
	}
	return appended;
}
/* private static */ Option<struct Definition> parseDefinition_Invokable(char* input){
	auto stripped = input.strip(/*  */);
	auto nameSeparator = stripped.lastIndexOf(/* " " */);
	if (/* nameSeparator < 0 */){
		return None<>(/*  */);
	}
	auto beforeName = stripped.substring(/* 0 */, /* nameSeparator) */.strip();
	auto name = stripped.substring(/* nameSeparator + " " */.length(/* ) */).strip();
	/* return switch (Main.findTypeSeparator(beforeName)) */{
		/* case None<Integer> _ ->
                    Main.parseAndModifyType(beforeName).map(type -> new Definition(new None<>(), type, name)) */;
		/* case Some<Integer>(var typeSeparator) -> */{
			auto beforeType = beforeName.substring(/* 0 */, /* typeSeparator) */.strip();
			auto inputType = beforeName.substring(/* typeSeparator + " " */.length(/* ) */).strip();
			/* yield Main.parseAndModifyType(inputType).map(outputType -> new Definition(new Some<>(beforeType), outputType, name)) */;
		}
	}
	/*  */;
}
/* private static */ Option<int> findTypeSeparator_Invokable(char* input){
	auto depth = /*  0 */;
	/* for */ /* (var */ index = input.length() - 1;
	struct index > = /*  0 */;
	/* index--) */{
		auto c = input.charAt(index);
		if (/* c == ' ' && depth == 0 */){
			return Some<>(index);
		}
		if (/* c == '>' */){
			depth++;
		}
		if (/* c == '<' */){
			/* depth-- */;
		}
	}
	return None<>(/*  */);
}
/* private static */ Option<struct Type> parseAndModifyType_Invokable(char* input){
	/* return Main.parseType(input).map(parsed -> */{
		if (/* parsed instanceof Generic generic */){
			auto base = generic.base;
			auto arguments = generic.args;
			if (base.equals(/* "Function" */)){
				auto argType = arguments.get(/* 0 */);
				auto returnType = arguments.get(/* 1 */);
				return struct Functional(Lists.of(argType), returnType);
			}
			if (base.equals(/* "Supplier" */)){
				auto returns = arguments.get(/* 0 */);
				return struct Functional(Lists.emptyList(/*  */), returns);
			}
			if (base.equals(/* "BiFunction" */)){
				auto argType = arguments.get(/* 0 */);
				auto argType2 = arguments.get(/* 1 */);
				auto returnType = arguments.get(/* 2 */);
				return struct Functional(Lists.of(argType, /*  argType2) */, returnType);
			}
			else {
				if (/* !generics */.contains(generic)){
					/* generics.add(generic) */;
				}
			}
		}
		return parsed;
	}
	/* ) */;
}
/* private static */ Option<struct Type> parseType_Invokable(char* input){
	auto stripped = input.strip(/*  */);
	if (stripped.equals(/* "public" */)){
		return None<>(/*  */);
	}
	if (stripped.equals(/* "boolean" */)){
		return Some<>(Primitive.Bit);
	}
	if (stripped.equals(/* "String" */)){
		return Some<>(struct Ref(Primitive.I8));
	}
	if (stripped.equals(/* "int") || stripped */.equals("Integer")){
		return Some<>(Primitive.I32);
	}
	if (stripped.equals(/* "var" */)){
		return Some<>(Primitive.Var);
	}
	if (stripped.endsWith(/* ">" */)){
		auto slice = stripped.substring(/* 0 */, stripped.length(/* ) - ">" */.length());
		auto argsStart = slice.indexOf(/* "<" */);
		if (/* argsStart >= 0 */){
			auto base = slice.substring(/* 0 */, /* argsStart) */.strip();
			if (isSymbol(base)){
				auto inputArgs = slice.substring(/* argsStart + "<" */.length(/*  */));
				return Main.parseValues(inputArgs, /* Main::parseGenericArgument) */.map(/* args */ -> /*  new Generic(base, args */));
			}
		}
	}
	if (isSymbol(stripped)){
		return Some<>(struct Struct(stripped));
	}
	return Some<>(struct Content(input));
}
/* private static */ Option<struct Type> parseGenericArgument_Invokable(char* input1){
	return parseWhitespace(/* input1)
                 */.<Type>map(/* whitespace */ -> /* whitespace)
                .or(() */ -> /*  parseAndModifyType(input1 */));
}
/* private static */ struct StringBuilder mergeStatements_Invokable(struct StringBuilder stringBuilder, char* str){
	return stringBuilder.append(str);
}
/* private static */ char* compileStatementsOrBlocks_Invokable(char* body, int depth){
	return Main.compileStatements(body, /* segment */ -> /*  new Some<> */(/* compileStatementOrBlock(segment */, /*  depth + 1) */));
}
/* private static */ char* compileStatements_Invokable(char* input, Option<char*> (*compiler)(char*)){
	return Main.parseStatements(input, /* compiler) */.map(/* Main::generateStatements */).orElse("");
}
/* private static */ Option<List<char*>> parseStatements_Invokable(char* input, Option<char*> (*compiler)(char*)){
	return Main.parseAll(input, /*  Main::foldStatementChar */, compiler);
}
/* private static */ char* generateStatements_Invokable(List<char*> inner){
	return generateAll(/* Main::mergeStatements */, inner);
}
/* private static */ struct State foldStatementChar_Invokable(struct State state, struct char c){
	auto appended = state.append(c);
	if (/* c == ';' && appended */.isLevel(/*  */)){
		return appended.advance(/*  */);
	}
	if (/* c == '}' && appended */.isShallow(/*  */)){
		return appended.advance(/* ) */.exit();
	}
	/* if (c == ' */{
		/* ') {
            return appended.enter() */;
	}
	if (/* c == '}' */){
		return appended.exit(/*  */);
	}
	else {
		return appended;
	}
}
/* private static */ Option<char*> compileDefinitionToString_Invokable(char* input){
	return Main.parseAndModifyDefinition(/* input) */.map(Defined::generate);
}
struct void main_Invokable(){
	/* try */{
		auto source = Paths.get(/* " */.", /*  "src" */, /*  "java" */, /*  "magma" */, /* "Main */.java");
		auto input = Files.readString(source);
		auto target = source.resolveSibling(/* "main */.c");
		/* Files.writeString(target, this.compileRoot(input)) */;
	}
	/* catch (IOException e) */{
		/* //noinspection CallToPrintStackTrace
            e.printStackTrace() */;
	}
}
/* private */ char* compileRoot_Invokable(char* input){
	auto compiled = compileStatements(input, /* segment */ -> /*  new Some<> */(this.compileRootSegment(segment)));
	auto joinedStructs = structs.iter(/* ) */.collect(struct Joiner(/*  */)).orElse("");
	auto joinedGenerics = generics.iter(/* )
                .map(Generic::generate)
                .map(result */ -> /* "// " + result + "\n")
                 */.collect(struct Joiner(/*  */)).orElse("");
	auto joinedMethods = methods.iter(/* ) */.collect(struct Joiner(/*  */)).orElse("");
	return /* compiled + joinedStructs + joinedGenerics + joinedMethods */;
}
/* private */ char* compileRootSegment_Invokable(char* input){
	return this.compileClass(/* input)
                .orElseGet(() */ -> /* generatePlaceholder(input */.strip()) + "\n");
}
/* private */ Option<char*> compileClass_Invokable(char* input){
	return this.compileStructured(input, /*  "class " */);
}
/* private */ Option<char*> compileStructured_Invokable(char* input, char* infix){
	auto classIndex = input.indexOf(infix);
	if (/* classIndex < 0 */){
		return None<>(/*  */);
	}
	auto left = input.substring(/* 0 */, /* classIndex) */.strip();
	auto right = input.substring(/* classIndex + infix */.length(/*  */));
	auto contentStart = right.indexOf(/* "{" */);
	if (/* contentStart < 0 */){
		return None<>(/*  */);
	}
	auto beforeContent = right.substring(/* 0 */, /* contentStart) */.strip();
	auto paramStart = beforeContent.indexOf(/* "(" */);
	auto withoutParams = /* paramStart >= 0
                ? beforeContent */.substring(/* 0 */, paramStart).strip()
                : beforeContent;
	auto typeParamStart = withoutParams.indexOf(/* "<" */);
	auto name = /* typeParamStart >= 0
                ? withoutParams */.substring(/* 0 */, typeParamStart).strip()
                : withoutParams;
	auto withEnd = right.substring(/* contentStart + "{" */.length(/* ) */).strip();
	if (/* !withEnd */.endsWith(/* "}" */)){
		return None<>(/*  */);
	}
	auto inputContent = withEnd.substring(/* 0 */, withEnd.length() - 1);
	/* currentStruct = new Some<>(name); */
	auto outputContent = compileStatements(inputContent, /* segment */ -> /*  new Some<> */(this.compileStructuredSegment(segment)));
	auto generated = /*  generatePlaceholder(left) + "struct " + name + " {" + outputContent + "\n};\n" */;
	/* structs.add(generated) */;
	return Some<>(/* "" */);
}
/* private */ char* compileStructuredSegment_Invokable(char* input){
	return parseWhitespace(/* input) */.map(/* Whitespace::generate)
                .or(() */ -> /* this.compileStructured(input, "interface "))
                .or(() */ -> /* this.compileStructured(input, "enum "))
                .or(() */ -> /* this.compileStructured(input, "class "))
                .or(() */ -> /* this.compileStructured(input, "record "))
                .or(() */ -> /* this.compileMethod(input))
                .or(() */ -> /* this.compileDefinitionStatement(input))
                .orElseGet(() */ -> /*  generatePlaceholder(input */));
}
/* private */ Option<char*> compileMethod_Invokable(char* input){
	auto paramStart = input.indexOf(/* "(" */);
	if (/* paramStart >= 0 */){
		auto inputDefinition = input.substring(/* 0 */, /* paramStart) */.strip();
		auto withParams = input.substring(/* paramStart + " */(/* " */.length());
		/* return parseAndModifyDefinition(inputDefinition)
                    .map(definition -> */{
			/* return definition.mapName(name -> */{
				return /* name + currentStruct */.map(/* inner */ -> /* "_" + inner) */.orElse("");
			}
			/* ) */;
		}
		/* )
                    .map(Defined::generate).flatMap(outputDefinition -> */{
			auto paramEnd = withParams.indexOf(/* ")" */);
			if (/* paramEnd >= 0 */){
				auto paramString = withParams.substring(/* 0 */, /* paramEnd) */.strip();
				auto withBraces = withParams.substring(/* paramEnd + ")" */.length(/* ) */).strip();
				auto outputParams = Main.parseValues(paramString, /* s */ -> Some<>(this.compileParam(s))).map(Main::generateValues).orElse("");
				/* String newBody */;
				/* if (withBraces.startsWith(" */{
					/* ") && withBraces.endsWith("}")) { */ auto body = withBraces.substring(1, withBraces.length() - 1);
                                newBody = "{" + compileStatementsOrBlocks(body, 0) + "\n}";
				}
				/* else if (withBraces.equals(";")) */{
					/* newBody = ";"; */
				}
				else {
					return None<>(/*  */);
				}
				auto generated = /*  outputDefinition + "(" + outputParams + ")" + newBody + "\n" */;
				/* methods.add(generated) */;
				return Some<>(/* "" */);
			}
			return None<>(/*  */);
		}
		/* ) */;
	}
	return None<>(/*  */);
}
/* private */ char* compileParam_Invokable(char* param){
	return parseWhitespace(/* param) */.map(/* Whitespace::generate)
                .or(() */ -> /* parseAndModifyDefinition(param).map(Defined::generate))
                .orElseGet(() */ -> /*  generatePlaceholder(param */));
}
/* private */ Option<char*> compileDefinitionStatement_Invokable(char* input){
	return compileStatement(input, /*  Main::compileDefinitionToString */, /*  1 */);
}
