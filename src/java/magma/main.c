/* package magma; */
/* import java.io.IOException; */
/* import java.nio.file.Files; */
/* import java.nio.file.Paths; */
/* import java.util.Map; */
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
/* private */struct Type extends BeforeArgs {
};
/* private */struct Defined {
};
/*  */struct BeforeArgs {
};
/*  */struct Value {
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
	/* private */ /* StringBuilder */ buffer;
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
/* private */struct Lambda {
};
/* private */struct DataAccess {
};
/* private */struct Symbol {
};
/*  */struct Main {
};
// Option</* R */>
// Option</* T */>
// Tuple</* T */, /*  R */>
// Option<Tuple</* T */, /*  R */>>
// List</* T */>
// Iterator</* T */>
// Option<int>
// Some<>
// None<>
// Iterator</* R */>
// Iterator<>
// Collector</* T */, /*  C */>
// List<char*>
// Option<char*>
// Option</* C */>
// Option</* Type */>
// Option<List</* T */>>
// List</* Type */>
// Option</* Invokable */>
// Option</* Whitespace */>
// Option</* Defined */>
// Option</* Definition */>
// Option<List<char*>>
/* <R> */ Option</* R */> map_Option(/*  R */ (*mapper)(/* T */));
/* T */ orElseGet_Option(/* T */ (*other)());
Option</* T */> or_Option(Option</* T */> (*other)());
/* T */ orElse_Option(/* T */ other);
/* <R> */ Option</* R */> flatMap_Option(Option</* R */> (*mapper)(/* T */));
/* <R> */ Option<Tuple</* T */, /*  R */>> and_Option(Option</* R */> (*other)());
Option</* T */> next_Head();
List</* T */> add_List(/* T */ element);
Iterator</* T */> iter_List();
int hasElements_List();
/* T */ removeFirst_List();
/* T */ get_List(int index);
int contains_List(/* T */ element);
/* C */ createInitial_Collector();
/* C */ fold_Collector(/* C */ current, /* T */ element);
char* generate_Type extends BeforeArgs();
char* generate_Defined();
/* Defined */ mapName_Defined(char* (*mapper)(char*));
char* generate_BeforeArgs();
char* generate_Value();
/* Type */ resolveType_Value();
/* @Override
        public */ char* generate_Primitive implements Type(){
	return this.value;
}
/* @Override
        public */ Option<int> next_RangeHead implements Head(){
	if (this.counter < this.length){
		auto value = this.counter;
		this.counter++;
		return Generic[base=Some, args=JavaList[list=[Whitespace[]]]](value);
	}
	else {
		return Generic[base=None, args=JavaList[list=[Whitespace[]]]](/*  */);
	}
}
/* public <R> */ Iterator</* R */> map_Iterator(/*  R */ (*mapper)(/* T */)){
	return Generic[base=Iterator, args=JavaList[list=[Whitespace[]]]](Content[input=/*  */](/* ) */ -> Content[input=this.head.next](/*  */).map(mapper));
}
/* public <R> */ /* R */ fold_Iterator(/* R */ initial, /*  R */ (*folder)(/* R */, /*  T */)){
	auto current = initial;
	while (true){
		/* switch (this.head.next()) */{
			/* case Some<T>(var value) -> */ current = Content[input=folder.apply](current, value);
			/* case None<T> _ -> */{
				return current;
			}
		}
	}
}
/* public <C> */ /* C */ collect_Iterator(Collector</* T */, /*  C */> collector){
	return Content[input=this.fold](Content[input=collector.createInitial](/*  */), /*  collector::fold */);
}
/* private */ State_State(List<char*> segments, /* StringBuilder */ buffer, int depth){
	/* this.segments = segments; */
	/* this.buffer = buffer; */
	/* this.depth = depth; */
}
/* public */ int isLevel_State(){
	return this.depth == 0;
}
/* public */ /* State */ enter_State(){
	this.depth++;
	return this;
}
/* public */ /* State */ exit_State(){
	/* this.depth-- */;
	return this;
}
/* private */ /* State */ append_State(/* char */ c){
	/* this.buffer.append(c) */;
	return this;
}
/* private */ /* State */ advance_State(){
	/* this.segments.add(this.buffer.toString()) */;
	/* this.buffer = new StringBuilder(); */
	return this;
}
/* public */ int isShallow_State(){
	return this.depth == 1;
}
/* @Override
        public <R> */ Option</* R */> map_Some(/*  R */ (*mapper)(/* T */)){
	return Generic[base=Some, args=JavaList[list=[Whitespace[]]]](Content[input=mapper.apply](this.value));
}
/* @Override
        public */ /* T */ orElseGet_Some(/* T */ (*other)()){
	return this.value;
}
/* @Override
        public */ Option</* T */> or_Some(Option</* T */> (*other)()){
	return this;
}
/* @Override
        public */ /* T */ orElse_Some(/* T */ other){
	return this.value;
}
/* @Override
        public <R> */ Option</* R */> flatMap_Some(Option</* R */> (*mapper)(/* T */)){
	return Content[input=mapper.apply](this.value);
}
/* @Override
        public <R> */ Option<Tuple</* T */, /*  R */>> and_Some(Option</* R */> (*other)()){
	return Content[input=other.get](Content[input=/* ) */.map](/* otherValue */ -> /* new Tuple<>(this */.value, otherValue));
}
/* @Override
        public <R> */ Option</* R */> map_None(/*  R */ (*mapper)(/* T */)){
	return Generic[base=None, args=JavaList[list=[Whitespace[]]]](/*  */);
}
/* @Override
        public */ /* T */ orElseGet_None(/* T */ (*other)()){
	return Content[input=other.get](/*  */);
}
/* @Override
        public */ Option</* T */> or_None(Option</* T */> (*other)()){
	return Content[input=other.get](/*  */);
}
/* @Override
        public */ /* T */ orElse_None(/* T */ other){
	return other;
}
/* @Override
        public <R> */ Option</* R */> flatMap_None(Option</* R */> (*mapper)(/* T */)){
	return Generic[base=None, args=JavaList[list=[Whitespace[]]]](/*  */);
}
/* @Override
        public <R> */ Option<Tuple</* T */, /*  R */>> and_None(Option</* R */> (*other)()){
	return Generic[base=None, args=JavaList[list=[Whitespace[]]]](/*  */);
}
/* private */ Joiner_Joiner(){
	/* this("") */;
}
/* @Override
        public */ Option<char*> createInitial_Joiner(){
	return Generic[base=None, args=JavaList[list=[Whitespace[]]]](/*  */);
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
        public */ List</* T */> createInitial_ListCollector(){
	return Content[input=Lists.emptyList](/*  */);
}
/* @Override
        public */ List</* T */> fold_ListCollector(List</* T */> current, /* T */ element){
	return Content[input=current.add](element);
}
/* @Override
        public */ char* generate_Generic(){
	auto joined = Content[input=generateValuesFromNodes](this.args);
	return this.base + "<" + joined + ">";
}
/* @Override
        public */ char* generate_Content(){
	return Content[input=Main.generatePlaceholder](this.input);
}
/* @Override
        public */ /* Type */ resolveType_Content(){
	return this;
}
/* @Override
        public */ char* generate_Functional(){
	return this.returnType.generate() + " (*)(" + generateValuesFromNodes(this.paramTypes) + ")";
}
/* @Override
        public */ char* generate_Definition(){
	auto beforeTypeString = Content[input=this.beforeType.map](/* Main::generatePlaceholder)
                    .map(inner */ -> /* inner + " ")
                     */.orElse("");
	return /* beforeTypeString + this */.type.generate() + " " + this.name;
}
/* @Override
        public */ /* Defined */ mapName_Definition(char* (*mapper)(char*)){
	return Content[input=Definition](this.beforeType, this.type, Content[input=mapper.apply](this.name));
}
/* @Override
        public */ char* generate_FunctionalDefinition(){
	auto beforeTypeString = Content[input=this.beforeType.map](/* inner */ -> /* inner + " ") */.orElse("");
	return Content[input=/* "%s%s  */](Content[input=/* *%s) */](/* %s)" */.formatted(beforeTypeString, this.returns.generate(), this.name, generateValuesFromNodes(this.args));
}
/* @Override
        public */ /* Defined */ mapName_FunctionalDefinition(char* (*mapper)(char*)){
	return Content[input=FunctionalDefinition](this.beforeType, this.returns, Content[input=mapper.apply](this.name), this.args);
}
/* @Override
        public */ char* generate_Ref(){
	return this.type.generate() + "*";
}
/* @Override
        public */ Option</* C */> createInitial_OptionCollector(){
	return Generic[base=Some, args=JavaList[list=[Whitespace[]]]](Content[input=this.collector.createInitial](/*  */));
}
/* @Override
        public */ Option</* C */> fold_OptionCollector(Option</* C */> current, Option</* T */> element){
	return Content[input=current.and](Content[input=/*  */](/* ) */ -> /* element).map(tuple */ -> this.collector.fold(tuple.left, tuple.right));
}
/* @Override
        public */ char* generate_Struct(){
	return /* "struct " + this */.name;
}
/* public */ Option</* Type */> find_Struct(char* memberName){
	if (Content[input=this.members.containsKey](memberName)){
		return Generic[base=Some, args=JavaList[list=[Whitespace[]]]](Content[input=this.members.get](memberName));
	}
	else {
		return Generic[base=None, args=JavaList[list=[Whitespace[]]]](/*  */);
	}
}
/* @Override
        public */ char* generate_Whitespace(){
	return /* "" */;
}
/* @Override
        public */ char* generate_Invokable(){
	auto joined = Content[input=this.arguments.iter](/* )
                     */.map(Value::generate).collect(new Joiner(", ")).orElse("");
	return this.beforeArgs + "(" + joined + ")";
}
/* @Override
        public */ /* Type */ resolveType_Invokable(){
	if (this.beforeArgs instanceof Functional functional){
		return functional.returnType;
	}
	return Content[input=Content](Content[input=this.beforeArgs.generate](/*  */));
}
/* @Override
        public */ char* generate_Lambda(){
	return /* generatePlaceholder(this.beforeArrow) + " */ -> /* " + this */.value;
}
/* @Override
        public */ /* Type */ resolveType_Lambda(){
	return Content[input=Content](/* "?" */);
}
/* @Override
        public */ char* generate_DataAccess(){
	return this.parent.generate() + "." + this.property;
}
/* @Override
        public */ /* Type */ resolveType_DataAccess(){
	if (this.parent.resolveType() instanceof Struct struct){
		if (Content[input=struct.find](this.property) instanceof Some(var value)){
			return value;
		}
	}
	return Content[input=Content](Content[input=this.parent.generate](/*  */));
}
/* @Override
        public */ char* generate_Symbol(){
	return this.value;
}
/* @Override
        public */ /* Type */ resolveType_Symbol(){
	return Content[input=Content](this.value);
}
/* public static final List<Generic> generics */ /* = */ Lists.emptyList_Symbol();
/* private static final List<String> structs */ /* = */ Lists.emptyList_Symbol();
/* private static final List<String> methods */ /* = */ Lists.emptyList_Symbol();
/* private static Option<String> currentStruct = */ /* new */ None<>_Symbol();
/* private static */ char* generateAll_Symbol(/*  StringBuilder */ (*merger)(/* StringBuilder */, char*), List<char*> parsed){
	return Content[input=parsed.iter](Content[input=/* )
                 */.fold](/* new StringBuilder( */), /* merger)
                 */.toString();
}
/* private static <T> */ Option<List</* T */>> parseAll_Symbol(char* input, /*  State */ (*folder)(/* State */, /*  Character */), Option</* T */> (*compiler)(char*)){
	return Content[input=Main.divideAll](input, Content[input=/* folder)
                 */.iter](Content[input=/* )
                 */.map](/* compiler)
                 */.collect(new OptionCollector<>(new ListCollector<>()));
}
/* private static */ List<char*> divideAll_Symbol(char* input, /*  State */ (*folder)(/* State */, /*  Character */)){
	auto current = Content[input=State](/*  */);
	auto queue = Generic[base=Iterator, args=JavaList[list=[Whitespace[]]]](Content[input=RangeHead](input.length())).map(input::charAt).collect(new ListCollector<>());
	while (Content[input=queue.hasElements](/*  */)){
		auto c = Content[input=queue.removeFirst](/*  */);
		if (/* c == '\'' */){
			/* current.append(c) */;
			auto c1 = Content[input=queue.removeFirst](/*  */);
			/* current.append(c1) */;
			if (/* c1 == '\\' */){
				/* current.append(queue.removeFirst()) */;
			}
			/* current.append(queue.removeFirst()) */;
			/* continue */;
		}
		if (/* c == '"' */){
			/* current.append(c) */;
			while (Content[input=queue.hasElements](/*  */)){
				auto next = Content[input=queue.removeFirst](/*  */);
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
	return Content[input=current.advance](/*  */).segments;
}
/* private static */ char* generateValues_Symbol(List<char*> parserd){
	return Content[input=Main.generateAll](/* Main::mergeValues */, parserd);
}
/* private static */ /* StringBuilder */ mergeValues_Symbol(/* StringBuilder */ cache, char* element){
	if (Content[input=cache.isEmpty](/*  */)){
		return Content[input=cache.append](element);
	}
	return Content[input=cache.append](/* ", ") */.append(element);
}
/* private static */ char* generatePlaceholder_Symbol(char* input){
	return /* "/* " + input + " */" */;
}
/* private static */ char* generateValuesFromNodes_Symbol(List</* Type */> list){
	return Content[input=list.iter](/* )
                 */.map(Type::generate).collect(new Joiner(", ")).orElse("");
}
/* private static */ char* compileStatementOrBlock_Symbol(char* input, int depth){
	return Content[input=parseWhitespace](Content[input=/* input) */.map](Content[input=/* Whitespace::generate)
                 */.or](/* () */ -> /* compileStatement(input, Main::compileStatementValue, depth))
                .or(() */ -> /* compileBlock(input, depth))
                .orElseGet(() */ -> /* createIndent(depth) + generatePlaceholder(input */.strip()));
}
/* private static */ char* createIndent_Symbol(int depth){
	return Content[input=/* "\n" + "\t" */.repeat](depth);
}
/* private static */ Option<char*> compileBlock_Symbol(char* input, int depth){
	auto stripped = Content[input=input.strip](/*  */);
	if (Content[input=stripped.endsWith](/* "}" */)){
		auto withoutEnd = Content[input=stripped.substring](/* 0 */, Content[input=stripped.length](/* ) - "}" */.length());
		auto contentStart = Content[input=withoutEnd.indexOf](/* "{" */);
		if (/* contentStart >= 0 */){
			auto beforeContent = Content[input=withoutEnd.substring](/* 0 */, contentStart);
			auto content = Content[input=withoutEnd.substring](Content[input=/* contentStart + "{" */.length](/*  */));
			auto indent = Content[input=createIndent](depth);
			return Generic[base=Some, args=JavaList[list=[Whitespace[]]]](/* indent + compileBeforeBlock(beforeContent) + "{" + compileStatementsOrBlocks(content */, /*  depth) + indent + "}" */);
		}
	}
	return Generic[base=None, args=JavaList[list=[Whitespace[]]]](/*  */);
}
/* private static */ char* compileBeforeBlock_Symbol(char* input){
	if (Content[input=input.strip](/* ) */.equals("else")){
		return /* "else " */;
	}
	return Content[input=compileConditional](input, Content[input=/* "if")
                 */.or](Content[input=/*  */](/* ) */ -> /* compileConditional(input, "while"))
                .orElseGet(() */ -> /* generatePlaceholder(input */.strip()));
}
/* private static */ Option<char*> compileConditional_Symbol(char* input, char* prefix){
	auto stripped = Content[input=input.strip](/*  */);
	if (Content[input=stripped.startsWith](prefix)){
		auto withoutKeyword = Content[input=stripped.substring](Content[input=prefix.length](/* ) */).strip();
		if (Content[input=withoutKeyword.startsWith](/* "(") && withoutKeyword */.endsWith(")")){
			auto condition = Content[input=withoutKeyword.substring](/* 1 */, withoutKeyword.length() - 1);
			return Generic[base=Some, args=JavaList[list=[Whitespace[]]]](/* prefix + " (" + compileValue(condition) + ")" */);
		}
	}
	return Generic[base=None, args=JavaList[list=[Whitespace[]]]](/*  */);
}
/* private static */ Option<char*> compileStatementValue_Symbol(char* input){
	auto stripped = Content[input=input.strip](/*  */);
	if (Content[input=stripped.startsWith](/* "return " */)){
		auto value = Content[input=stripped.substring](Content[input=/* "return " */.length](/*  */));
		return Generic[base=Some, args=JavaList[list=[Whitespace[]]]](Content[input=/* "return " + compileValue */](value));
	}
	if (Content[input=stripped.endsWith](/* "++" */)){
		auto slice = Content[input=stripped.substring](/* 0 */, Content[input=stripped.length](/* ) - "++" */.length());
		return Generic[base=Some, args=JavaList[list=[Whitespace[]]]](/* compileValue(slice) + "++" */);
	}
	auto valueSeparator = Content[input=stripped.indexOf](/* "=" */);
	if (/* valueSeparator >= 0 */){
		auto definition = Content[input=stripped.substring](/* 0 */, valueSeparator);
		auto value = Content[input=stripped.substring](Content[input=/* valueSeparator + "=" */.length](/*  */));
		return Content[input=compileDefinitionToString](Content[input=/* definition)
                     */.map](/* outputDefinition */ -> /*  outputDefinition + " = " + compileValue(value */));
	}
	return Generic[base=Some, args=JavaList[list=[Whitespace[]]]](Content[input=generatePlaceholder](input));
}
/* private static */ char* compileValue_Symbol(char* input){
	return Content[input=parseValue](/* input) */.generate();
}
/* private static */ /* Value */ parseValue_Symbol(char* input){
	auto stripped = Content[input=input.strip](/*  */);
	if (Content[input=stripped.startsWith](/* "new " */)){
		auto slice = Content[input=stripped.substring](Content[input=/* "new " */.length](/* ) */).strip();
		auto construction = Content[input=parseInvokable](slice, /*  Main::compileConstructorCaller */);
		if (Content[input=/* construction instanceof Some */](/* var invokable */)){
			auto invokable1 = invokable;
			if (invokable.beforeArgs instanceof Type caller){
				if (/* caller instanceof Generic */){
					/* invokable.arguments
                                .iter()
                                .map(value -> value.resolveType()) */;
				}
			}
			return /* invokable1 */;
		}
	}
	if (Content[input=parseInvocation](/* input) instanceof Some(var value */)){
		return value;
	}
	auto arrowIndex = Content[input=input.indexOf](/* " */ -> /* " */);
	if (/* arrowIndex >= 0 */){
		auto beforeArrow = Content[input=input.substring](/* 0 */, /* arrowIndex) */.strip();
		auto afterArrow = Content[input=input.substring](Content[input=/* arrowIndex + " */ -> /* " */.length](/*  */));
		return Content[input=Lambda](beforeArrow, Content[input=compileValue](afterArrow));
	}
	auto separator = Content[input=stripped.lastIndexOf](/* " */.");
	if (/* separator >= 0 */){
		auto parent = Content[input=stripped.substring](/* 0 */, separator);
		auto property = Content[input=stripped.substring](Content[input=/* separator + " */.".length](/*  */));
		return Content[input=DataAccess](Content[input=parseValue](parent), property);
	}
	if (Content[input=isSymbol](stripped)){
		return Content[input=Symbol](stripped);
	}
	return Content[input=Content](input);
}
/* private static */ Option</* Invokable */> parseInvocation_Symbol(char* input){
	return Content[input=parseInvokable](input, Content[input=/* input1 */ -> /*  new Content */](Content[input=compileValue](/* input1 */)));
}
/* private static */ Option</* Invokable */> parseInvokable_Symbol(char* slice, /*  BeforeArgs */ (*beforeArgsCompiler)(char*)){
	if (Content[input=/* !slice */.endsWith](/* ")" */)){
		return Generic[base=None, args=JavaList[list=[Whitespace[]]]](/*  */);
	}
	auto withoutEnd = Content[input=slice.substring](/* 0 */, Content[input=slice.length](/* ) - ")" */.length());
	auto argsStart = Content[input=withoutEnd.indexOf](/* "(" */);
	if (/* argsStart < 0 */){
		return Generic[base=None, args=JavaList[list=[Whitespace[]]]](/*  */);
	}
	auto base = Content[input=withoutEnd.substring](/* 0 */, argsStart);
	auto args = Content[input=withoutEnd.substring](Content[input=/* argsStart + " */](/* " */.length());
	/* return parseValues(args, value -> new Some<>(parseValue(value))).map(newArgs -> */{
		auto generate = Content[input=beforeArgsCompiler.apply](base);
		return Content[input=Invokable](generate, newArgs);
	}
	/* ) */;
}
/* private static */ /* BeforeArgs */ compileConstructorCaller_Symbol(char* base){
	if (Content[input=parseAndModifyType](/* base) instanceof Some<Type>(var type */)){
		return type;
	}
	return Content[input=Content](base);
}
/* private static */ int isSymbol_Symbol(char* input){
	if (Content[input=input.isEmpty](/*  */)){
		return false;
	}
	/* for */ /* (var */ i = /*  0 */;
	/* i < input.length() */;
	/* i++) */{
		auto c = Content[input=input.charAt](i);
		if (Content[input=Character.isLetter](c)){
			/* continue */;
		}
		return false;
	}
	return true;
}
/* private static */ Option</* Whitespace */> parseWhitespace_Symbol(char* input){
	if (Content[input=input.isBlank](/*  */)){
		return Generic[base=Some, args=JavaList[list=[Whitespace[]]]](Content[input=Whitespace](/*  */));
	}
	return Generic[base=None, args=JavaList[list=[Whitespace[]]]](/*  */);
}
/* private static */ Option</* Defined */> parseAndModifyDefinition_Symbol(char* input){
	/* return Main.parseDefinition(input).map(definition -> */{
		if (Content[input=definition.type instanceof Functional](/* var args */, /*  var base */)){
			return Content[input=FunctionalDefinition](definition.beforeType, base, definition.name, args);
		}
		return definition;
	}
	/* ) */;
}
/* private static */ Option<char*> compileStatement_Symbol(char* input, Option<char*> (*compiler)(char*), int depth){
	auto stripped = Content[input=input.strip](/*  */);
	if (Content[input=/* !stripped */.endsWith](/* ";" */)){
		return Generic[base=None, args=JavaList[list=[Whitespace[]]]](/*  */);
	}
	auto withoutEnd = Content[input=stripped.substring](/* 0 */, Content[input=stripped.length](/* ) - ";" */.length());
	return Content[input=compiler.apply](Content[input=/* withoutEnd) */.map](/* definition */ -> /*  generateStatement(definition, depth */));
}
/* private static */ char* generateStatement_Symbol(char* definition, int depth){
	return /* createIndent(depth) + definition + ";" */;
}
/* private static <T> */ Option<List</* T */>> parseValues_Symbol(char* input, Option</* T */> (*compiler)(char*)){
	return Content[input=Main.parseAll](input, /*  Main::foldValueChar */, compiler);
}
/* private static */ /* State */ foldValueChar_Symbol(/* State */ state, /* char */ c){
	if (Content[input=/* c == ',' && state */.isLevel](/*  */)){
		return Content[input=state.advance](/*  */);
	}
	auto appended = Content[input=state.append](c);
	if (/* c == '<' */){
		return Content[input=appended.enter](/*  */);
	}
	if (/* c == '>' */){
		return Content[input=appended.exit](/*  */);
	}
	return appended;
}
/* private static */ Option</* Definition */> parseDefinition_Symbol(char* input){
	auto stripped = Content[input=input.strip](/*  */);
	auto nameSeparator = Content[input=stripped.lastIndexOf](/* " " */);
	if (/* nameSeparator < 0 */){
		return Generic[base=None, args=JavaList[list=[Whitespace[]]]](/*  */);
	}
	auto beforeName = Content[input=stripped.substring](/* 0 */, /* nameSeparator) */.strip();
	auto name = Content[input=stripped.substring](Content[input=/* nameSeparator + " " */.length](/* ) */).strip();
	/* return switch (Main.findTypeSeparator(beforeName)) */{
		/* case None<Integer> _ ->
                    Main.parseAndModifyType(beforeName).map(type -> new Definition(new None<>(), type, name)) */;
		/* case Some<Integer>(var typeSeparator) -> */{
			auto beforeType = Content[input=beforeName.substring](/* 0 */, /* typeSeparator) */.strip();
			auto inputType = Content[input=beforeName.substring](Content[input=/* typeSeparator + " " */.length](/* ) */).strip();
			/* yield Main.parseAndModifyType(inputType).map(outputType -> new Definition(new Some<>(beforeType), outputType, name)) */;
		}
	}
	/*  */;
}
/* private static */ Option<int> findTypeSeparator_Symbol(char* input){
	auto depth = /*  0 */;
	/* for */ /* (var */ index = input.length() - 1;
	/* index */ > = /*  0 */;
	/* index--) */{
		auto c = Content[input=input.charAt](index);
		if (/* c == ' ' && depth == 0 */){
			return Generic[base=Some, args=JavaList[list=[Whitespace[]]]](index);
		}
		if (/* c == '>' */){
			depth++;
		}
		if (/* c == '<' */){
			/* depth-- */;
		}
	}
	return Generic[base=None, args=JavaList[list=[Whitespace[]]]](/*  */);
}
/* private static */ Option</* Type */> parseAndModifyType_Symbol(char* input){
	/* return Main.parseType(input).map(parsed -> */{
		if (/* parsed instanceof Generic generic */){
			auto base = generic.base;
			auto arguments = generic.args;
			if (Content[input=base.equals](/* "Function" */)){
				auto argType = Content[input=arguments.get](/* 0 */);
				auto returnType = Content[input=arguments.get](/* 1 */);
				return Content[input=Functional](Content[input=Lists.of](argType), returnType);
			}
			if (Content[input=base.equals](/* "Supplier" */)){
				auto returns = Content[input=arguments.get](/* 0 */);
				return Content[input=Functional](Content[input=Lists.emptyList](/*  */), returns);
			}
			if (Content[input=base.equals](/* "BiFunction" */)){
				auto argType = Content[input=arguments.get](/* 0 */);
				auto argType2 = Content[input=arguments.get](/* 1 */);
				auto returnType = Content[input=arguments.get](/* 2 */);
				return Content[input=Functional](Lists.of(argType, /*  argType2) */, returnType);
			}
			else {
				if (Content[input=/* !generics */.contains](generic)){
					/* generics.add(generic) */;
				}
			}
		}
		return parsed;
	}
	/* ) */;
}
/* private static */ Option</* Type */> parseType_Symbol(char* input){
	auto stripped = Content[input=input.strip](/*  */);
	if (Content[input=stripped.equals](/* "public" */)){
		return Generic[base=None, args=JavaList[list=[Whitespace[]]]](/*  */);
	}
	if (Content[input=stripped.equals](/* "boolean" */)){
		return Generic[base=Some, args=JavaList[list=[Whitespace[]]]](Primitive.Bit);
	}
	if (Content[input=stripped.equals](/* "String" */)){
		return Generic[base=Some, args=JavaList[list=[Whitespace[]]]](Content[input=Ref](Primitive.I8));
	}
	if (Content[input=stripped.equals](/* "int") || stripped */.equals("Integer")){
		return Generic[base=Some, args=JavaList[list=[Whitespace[]]]](Primitive.I32);
	}
	if (Content[input=stripped.equals](/* "var" */)){
		return Generic[base=Some, args=JavaList[list=[Whitespace[]]]](Primitive.Var);
	}
	if (Content[input=stripped.endsWith](/* ">" */)){
		auto slice = Content[input=stripped.substring](/* 0 */, Content[input=stripped.length](/* ) - ">" */.length());
		auto argsStart = Content[input=slice.indexOf](/* "<" */);
		if (/* argsStart >= 0 */){
			auto base = Content[input=slice.substring](/* 0 */, /* argsStart) */.strip();
			if (Content[input=isSymbol](base)){
				auto inputArgs = Content[input=slice.substring](Content[input=/* argsStart + "<" */.length](/*  */));
				return Content[input=Main.parseValues](inputArgs, Content[input=/* Main::parseGenericArgument) */.map](/* args */ -> /*  new Generic(base, args */));
			}
		}
	}
	return Generic[base=Some, args=JavaList[list=[Whitespace[]]]](Content[input=Content](input));
}
/* private static */ Option</* Type */> parseGenericArgument_Symbol(char* input1){
	return Content[input=parseWhitespace](Content[input=/* input1)
                 */.<Type>map](/* whitespace */ -> /* whitespace)
                .or(() */ -> /*  parseAndModifyType(input1 */));
}
/* private static */ /* StringBuilder */ mergeStatements_Symbol(/* StringBuilder */ stringBuilder, char* str){
	return Content[input=stringBuilder.append](str);
}
/* private static */ char* compileStatementsOrBlocks_Symbol(char* body, int depth){
	return Content[input=Main.compileStatements](body, Content[input=/* segment */ -> /*  new Some<> */](/* compileStatementOrBlock(segment */, /*  depth + 1) */));
}
/* private static */ char* compileStatements_Symbol(char* input, Option<char*> (*compiler)(char*)){
	return Content[input=Main.parseStatements](input, Content[input=/* compiler) */.map](/* Main::generateStatements */).orElse("");
}
/* private static */ Option<List<char*>> parseStatements_Symbol(char* input, Option<char*> (*compiler)(char*)){
	return Content[input=Main.parseAll](input, /*  Main::foldStatementChar */, compiler);
}
/* private static */ char* generateStatements_Symbol(List<char*> inner){
	return Content[input=generateAll](/* Main::mergeStatements */, inner);
}
/* private static */ /* State */ foldStatementChar_Symbol(/* State */ state, /* char */ c){
	auto appended = Content[input=state.append](c);
	if (Content[input=/* c == ';' && appended */.isLevel](/*  */)){
		return Content[input=appended.advance](/*  */);
	}
	if (Content[input=/* c == '}' && appended */.isShallow](/*  */)){
		return Content[input=appended.advance](/* ) */.exit();
	}
	/* if (c == ' */{
		/* ') {
            return appended.enter() */;
	}
	if (/* c == '}' */){
		return Content[input=appended.exit](/*  */);
	}
	else {
		return appended;
	}
}
/* private static */ Option<char*> compileDefinitionToString_Symbol(char* input){
	return Content[input=Main.parseAndModifyDefinition](/* input) */.map(Defined::generate);
}
/* void */ main_Symbol(){
	/* try */{
		auto source = Content[input=Paths.get](/* " */.", /*  "src" */, /*  "java" */, /*  "magma" */, /* "Main */.java");
		auto input = Content[input=Files.readString](source);
		auto target = Content[input=source.resolveSibling](/* "main */.c");
		/* Files.writeString(target, this.compileRoot(input)) */;
	}
	/* catch (IOException e) */{
		/* //noinspection CallToPrintStackTrace
            e.printStackTrace() */;
	}
}
/* private */ char* compileRoot_Symbol(char* input){
	auto compiled = Content[input=compileStatements](input, Content[input=/* segment */ -> /*  new Some<> */](Content[input=this.compileRootSegment](segment)));
	auto joinedStructs = Content[input=structs.iter](Content[input=/* ) */.collect](Content[input=Joiner](/*  */)).orElse("");
	auto joinedGenerics = Content[input=generics.iter](/* )
                .map(Generic::generate)
                .map(result */ -> Content[input=/* "// " + result + "\n")
                 */.collect](Content[input=Joiner](/*  */)).orElse("");
	auto joinedMethods = Content[input=methods.iter](Content[input=/* ) */.collect](Content[input=Joiner](/*  */)).orElse("");
	return /* compiled + joinedStructs + joinedGenerics + joinedMethods */;
}
/* private */ char* compileRootSegment_Symbol(char* input){
	return Content[input=this.compileClass](/* input)
                .orElseGet(() */ -> /* generatePlaceholder(input */.strip()) + "\n");
}
/* private */ Option<char*> compileClass_Symbol(char* input){
	return Content[input=this.compileStructured](input, /*  "class " */);
}
/* private */ Option<char*> compileStructured_Symbol(char* input, char* infix){
	auto classIndex = Content[input=input.indexOf](infix);
	if (/* classIndex < 0 */){
		return Generic[base=None, args=JavaList[list=[Whitespace[]]]](/*  */);
	}
	auto left = Content[input=input.substring](/* 0 */, /* classIndex) */.strip();
	auto right = Content[input=input.substring](Content[input=/* classIndex + infix */.length](/*  */));
	auto contentStart = Content[input=right.indexOf](/* "{" */);
	if (/* contentStart < 0 */){
		return Generic[base=None, args=JavaList[list=[Whitespace[]]]](/*  */);
	}
	auto beforeContent = Content[input=right.substring](/* 0 */, /* contentStart) */.strip();
	auto paramStart = Content[input=beforeContent.indexOf](/* "(" */);
	auto withoutParams = Content[input=/* paramStart >= 0
                ? beforeContent */.substring](/* 0 */, paramStart).strip()
                : beforeContent;
	auto typeParamStart = Content[input=withoutParams.indexOf](/* "<" */);
	auto name = Content[input=/* typeParamStart >= 0
                ? withoutParams */.substring](/* 0 */, typeParamStart).strip()
                : withoutParams;
	auto withEnd = Content[input=right.substring](Content[input=/* contentStart + "{" */.length](/* ) */).strip();
	if (Content[input=/* !withEnd */.endsWith](/* "}" */)){
		return Generic[base=None, args=JavaList[list=[Whitespace[]]]](/*  */);
	}
	auto inputContent = Content[input=withEnd.substring](/* 0 */, withEnd.length() - 1);
	/* currentStruct = new Some<>(name); */
	auto outputContent = Content[input=compileStatements](inputContent, Content[input=/* segment */ -> /*  new Some<> */](Content[input=this.compileStructuredSegment](segment)));
	auto generated = /*  generatePlaceholder(left) + "struct " + name + " {" + outputContent + "\n};\n" */;
	/* structs.add(generated) */;
	return Generic[base=Some, args=JavaList[list=[Whitespace[]]]](/* "" */);
}
/* private */ char* compileStructuredSegment_Symbol(char* input){
	return Content[input=parseWhitespace](Content[input=/* input) */.map](/* Whitespace::generate)
                .or(() */ -> /* this.compileStructured(input, "interface "))
                .or(() */ -> /* this.compileStructured(input, "enum "))
                .or(() */ -> /* this.compileStructured(input, "class "))
                .or(() */ -> /* this.compileStructured(input, "record "))
                .or(() */ -> /* this.compileMethod(input))
                .or(() */ -> /* this.compileDefinitionStatement(input))
                .orElseGet(() */ -> /*  generatePlaceholder(input */));
}
/* private */ Option<char*> compileMethod_Symbol(char* input){
	auto paramStart = Content[input=input.indexOf](/* "(" */);
	if (/* paramStart >= 0 */){
		auto inputDefinition = Content[input=input.substring](/* 0 */, /* paramStart) */.strip();
		auto withParams = Content[input=input.substring](Content[input=/* paramStart + " */](/* " */.length());
		/* return parseAndModifyDefinition(inputDefinition)
                    .map(definition -> */{
			/* return definition.mapName(name -> */{
				return Content[input=/* name + currentStruct */.map](/* inner */ -> /* "_" + inner) */.orElse("");
			}
			/* ) */;
		}
		/* )
                    .map(Defined::generate).flatMap(outputDefinition -> */{
			auto paramEnd = Content[input=withParams.indexOf](/* ")" */);
			if (/* paramEnd >= 0 */){
				auto paramString = Content[input=withParams.substring](/* 0 */, /* paramEnd) */.strip();
				auto withBraces = Content[input=withParams.substring](Content[input=/* paramEnd + ")" */.length](/* ) */).strip();
				auto outputParams = Content[input=Main.parseValues](paramString, /* s */ -> Generic[base=Some, args=JavaList[list=[Whitespace[]]]](this.compileParam(s))).map(Main::generateValues).orElse("");
				/* String newBody */;
				/* if (withBraces.startsWith(" */{
					/* ") && withBraces.endsWith("}")) { */ auto body = withBraces.substring(1, withBraces.length() - 1);
                                newBody = "{" + compileStatementsOrBlocks(body, 0) + "\n}";
				}
				/* else if (withBraces.equals(";")) */{
					/* newBody = ";"; */
				}
				else {
					return Generic[base=None, args=JavaList[list=[Whitespace[]]]](/*  */);
				}
				auto generated = /*  outputDefinition + "(" + outputParams + ")" + newBody + "\n" */;
				/* methods.add(generated) */;
				return Generic[base=Some, args=JavaList[list=[Whitespace[]]]](/* "" */);
			}
			return Generic[base=None, args=JavaList[list=[Whitespace[]]]](/*  */);
		}
		/* ) */;
	}
	return Generic[base=None, args=JavaList[list=[Whitespace[]]]](/*  */);
}
/* private */ char* compileParam_Symbol(char* param){
	return Content[input=parseWhitespace](Content[input=/* param) */.map](/* Whitespace::generate)
                .or(() */ -> /* parseAndModifyDefinition(param).map(Defined::generate))
                .orElseGet(() */ -> /*  generatePlaceholder(param */));
}
/* private */ Option<char*> compileDefinitionStatement_Symbol(char* input){
	return Content[input=compileStatement](input, /*  Main::compileDefinitionToString */, /*  1 */);
}
