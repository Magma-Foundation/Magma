/* package magma; */
/* import java.io.IOException; */
/* import java.nio.file.Files; */
/* import java.nio.file.Paths; */
/* import java.util.Map; */
/* import java.util.function.BiFunction; */
/* import java.util.function.Function; */
/* import java.util.function.Predicate; */
/* import java.util.function.Supplier; */
/*  */
typedef /* private */struct {
} Type extends BeforeArgs;
typedef /* private */struct {
} Defined extends Parameter;
typedef /*  */struct {
} BeforeArgs;
typedef /*  */struct {
} Value;
typedef /*  */struct {
} Parameter;
typedef /* private */struct {
	/* Bit("int"),
        I8("char"), */ /* I32("int"), */ Var("auto");
	/* private final */ char* value;/* 

        Primitive(String value) {
            this.value = value;
        } */
} Primitive implements Type;
typedef /* private static */struct {
	/* private final */ List<char*> segments;
	/* private */ StringBuilder buffer;
	/* private */ int depth;/* 

        public State() {
            this(Lists.emptyList(), new StringBuilder(), 0);
        } */
} State;
typedef /* private */struct {
} Joiner;
typedef /* private */struct {
} Generic;
typedef /* private */struct {
} Content;
typedef /* private */struct {
} Functional;
typedef /* private */struct {/* public Definition(Type type, String name) {
            this(new None<>(), type, name);
        } */
} Definition;
typedef /* private */struct {
} FunctionalDefinition;
typedef /* private */struct {
} Ref;
typedef /* private */struct {
} Struct;
typedef /* private */struct {
} Whitespace;
typedef /* private */struct {
} Invokable;
typedef /* private */struct {
} Lambda;
typedef /* private */struct {
} DataAccess;
typedef /* private */struct {
} Symbol;
typedef /*  */struct {
} Main;
// List<char*>
// Option<char*>
// None</*  */>
// List<Type>
// Option<Type>
// Some</* /* this.members.get */ */>
// List<T>
// Option<List<T>>
// Option<T>
// Iterator</* RangeHead */>
// Some</* indent + compileBeforeBlock(beforeContent) + "{" + compileStatementsOrBlocks(content */, /*  depth) + indent + "}" */>
// Some</* prefix + " (" + compileValue(condition) + ")" */>
// Some</* /* /* "return " + compileValue */ */ */>
// Some</* compileValue(slice) + "++" */>
// Some</* /* generatePlaceholder */ */>
// Option<Invokable>
// Option<Whitespace>
// Some</* Whitespace */>
// Option<Defined>
// Option<Definition>
// Option<int>
// Some</* index */>
// Some</* Primitive */>
// Some</* Ref */>
// Some</* Symbol */>
// Some</* Content */>
// Option<List<char*>>
// Some</* "" */>
char* generate_Type extends BeforeArgs(Type extends BeforeArgs this);
char* generate_Defined extends Parameter(Defined extends Parameter this);
Defined mapName_Defined extends Parameter(Defined extends Parameter this, char* (*mapper)(char*));
char* generate_BeforeArgs(BeforeArgs this);
char* generate_Value(Value this);
Type resolveType_Value(Value this);
char* generate_Parameter(Parameter this);
/* @Override
        public */ char* generate_Primitive implements Type(Primitive implements Type this){
	return this.value;
}
/* private */ State_State(State this, List<char*> segments, StringBuilder buffer, int depth){
	/* this.segments = segments; */
	/* this.buffer = buffer; */
	/* this.depth = depth; */
}
/* public */ int isLevel_State(State this){
	return this.depth == 0;
}
/* public */ State enter_State(State this){
	this.depth++;
	return this;
}
/* public */ State exit_State(State this){
	/* this.depth-- */;
	return this;
}
/* private */ State append_State(State this, char c){
	/* this.buffer.append(c) */;
	return this;
}
/* private */ State advance_State(State this){
	/* this.segments.addLast(this.buffer.toString()) */;
	/* this.buffer = new StringBuilder(); */
	return this;
}
/* public */ int isShallow_State(State this){
	return this.depth == 1;
}
/* private */ Joiner_Joiner(Joiner this){
	/* this("") */;
}
/* @Override
        public */ Option<char*> createInitial_Joiner(Joiner this){
	return Generic[base=None, arguments=JavaList[list=[Content[input=]]]](/*  */);
}
/* @Override
        public */ Option<char*> fold_Joiner(Joiner this, Option<char*> maybeCurrent, char* element){
	/* return new Some<>(switch (maybeCurrent) */{
		/* case None<String> _ -> element */;
		/* case Some<String>(var current) -> current + this.delimiter + element */;
	}
	/* ) */;
}
/* @Override
        public */ char* generate_Generic(Generic this){
	auto joined = Content[input=generateValuesFromNodes](this.arguments);
	return this.base + "<" + joined + ">";
}
/* public */ Generic withArgs_Generic(Generic this, List<Type> arguments){
	return Symbol[value=Generic](this.base, arguments);
}
/* @Override
        public */ char* generate_Content(Content this){
	return Content[input=Main.generatePlaceholder](this.input);
}
/* @Override
        public */ Type resolveType_Content(Content this){
	return this;
}
/* @Override
        public */ char* generate_Functional(Functional this){
	return this.returnType.generate() + " (*)(" + generateValuesFromNodes(this.paramTypes) + ")";
}
/* @Override
        public */ char* generate_Definition(Definition this){
	auto beforeTypeString = Content[input=this.beforeType.map](/* Main::generatePlaceholder)
                    .map(inner */ -> /* inner + " ")
                     */.orElse("");
	return /* beforeTypeString + this */.type.generate() + " " + this.name;
}
/* @Override
        public */ Defined mapName_Definition(Definition this, char* (*mapper)(char*)){
	return Symbol[value=Definition](this.beforeType, this.type, Content[input=mapper.apply](this.name));
}
/* @Override
        public */ char* generate_FunctionalDefinition(FunctionalDefinition this){
	auto beforeTypeString = Content[input=this.beforeType.map](/* inner */ -> /* inner + " ") */.orElse("");
	return Content[input=/* "%s%s  */](Content[input=/* *%s) */](/* %s)" */.formatted(beforeTypeString, this.returns.generate(), this.name, generateValuesFromNodes(this.args));
}
/* @Override
        public */ Defined mapName_FunctionalDefinition(FunctionalDefinition this, char* (*mapper)(char*)){
	return Symbol[value=FunctionalDefinition](this.beforeType, this.returns, Content[input=mapper.apply](this.name), this.args);
}
/* @Override
        public */ char* generate_Ref(Ref this){
	return this.type.generate() + "*";
}
/* @Override
        public */ char* generate_Struct(Struct this){
	return /* "struct " + this */.name;
}
/* public */ Option<Type> find_Struct(Struct this, char* memberName){
	if (Content[input=this.members.containsKey](memberName)){
		return Generic[base=Some, arguments=JavaList[list=[Content[input=/* this.members.get */]]]](Content[input=this.members.get](memberName));
	}
	else {
		return Generic[base=None, arguments=JavaList[list=[Content[input=]]]](/*  */);
	}
}
/* @Override
        public */ char* generate_Whitespace(Whitespace this){
	return /* "" */;
}
/* @Override
        public */ char* generate_Invokable(Invokable this){
	auto joined = Content[input=this.arguments.iter](/* )
                     */.map(Value::generate).collect(new Joiner(", ")).orElse("");
	return this.beforeArgs + "(" + joined + ")";
}
/* @Override
        public */ Type resolveType_Invokable(Invokable this){
	if (this.beforeArgs instanceof Functional functional){
		return functional.returnType;
	}
	return Symbol[value=Content](Content[input=this.beforeArgs.generate](/*  */));
}
/* public */ Invokable withBeforeArgs_Invokable(Invokable this, Type type){
	return Symbol[value=Invokable](type, this.arguments);
}
/* @Override
        public */ char* generate_Lambda(Lambda this){
	return /* generatePlaceholder(this.beforeArrow) + " */ -> /* " + this */.value;
}
/* @Override
        public */ Type resolveType_Lambda(Lambda this){
	return Symbol[value=Content](/* "?" */);
}
/* @Override
        public */ char* generate_DataAccess(DataAccess this){
	return this.parent.generate() + "." + this.property;
}
/* @Override
        public */ Type resolveType_DataAccess(DataAccess this){
	if (this.parent.resolveType() instanceof Struct struct){
		if (Content[input=struct.find](this.property) instanceof Some(var value)){
			return value;
		}
	}
	return Symbol[value=Content](Content[input=this.parent.generate](/*  */));
}
/* @Override
        public */ char* generate_Symbol(Symbol this){
	return this.value;
}
/* @Override
        public */ Type resolveType_Symbol(Symbol this){
	return Symbol[value=Content](this.value);
}
/* public static final List<Generic> generics */ /* = */ Lists.emptyList_Symbol(Symbol this);
/* private static final List<String> structs */ /* = */ Lists.emptyList_Symbol(Symbol this);
/* private static final List<String> methods */ /* = */ Lists.emptyList_Symbol(Symbol this);
/* private static Option<String> currentStruct = */ new None<>_Symbol(Symbol this);
/* private static */ char* generateAll_Symbol(Symbol this, StringBuilder (*merger)(StringBuilder, char*), List<char*> parsed){
	return Content[input=parsed.iter](Content[input=/* )
                 */.fold](/* new StringBuilder( */), /* merger)
                 */.toString();
}
/* private static <T> */ Option<List<T>> parseAll_Symbol(Symbol this, char* input, State (*folder)(State, Character), Option<T> (*compiler)(char*)){
	return Content[input=Main.divideAll](input, Content[input=/* folder)
                 */.iter](Content[input=/* )
                 */.map](/* compiler)
                 */.collect(new OptionCollector<>(new ListCollector<>()));
}
/* private static */ List<char*> divideAll_Symbol(Symbol this, char* input, State (*folder)(State, Character)){
	auto current = Symbol[value=State](/*  */);
	auto queue = Generic[base=Iterator, arguments=JavaList[list=[Content[input=RangeHead]]]](Symbol[value=RangeHead](input.length())).map(input::charAt).collect(new ListCollector<>());
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
/* private static */ char* generateValues_Symbol(Symbol this, List<char*> parserd){
	return Content[input=Main.generateAll](/* Main::mergeValues */, parserd);
}
/* private static */ StringBuilder mergeValues_Symbol(Symbol this, StringBuilder cache, char* element){
	if (Content[input=cache.isEmpty](/*  */)){
		return Content[input=cache.append](element);
	}
	return Content[input=cache.append](/* ", ") */.append(element);
}
/* private static */ char* generatePlaceholder_Symbol(Symbol this, char* input){
	return /* "/* " + input + " */" */;
}
/* private static */ char* generateValuesFromNodes_Symbol(Symbol this, List<Type> list){
	return Content[input=list.iter](/* )
                 */.map(Type::generate).collect(new Joiner(", ")).orElse("");
}
/* private static */ char* compileStatementOrBlock_Symbol(Symbol this, char* input, int depth){
	return Content[input=parseWhitespace](Content[input=/* input) */.map](Content[input=/* Whitespace::generate)
                 */.or](/* () */ -> /* compileStatement(input, Main::compileStatementValue, depth))
                .or(() */ -> /* compileBlock(input, depth))
                .orElseGet(() */ -> /* createIndent(depth) + generatePlaceholder(input */.strip()));
}
/* private static */ char* createIndent_Symbol(Symbol this, int depth){
	return Content[input=/* "\n" + "\t" */.repeat](depth);
}
/* private static */ Option<char*> compileBlock_Symbol(Symbol this, char* input, int depth){
	auto stripped = Content[input=input.strip](/*  */);
	if (Content[input=stripped.endsWith](/* "}" */)){
		auto withoutEnd = Content[input=stripped.substring](/* 0 */, Content[input=stripped.length](/* ) - "}" */.length());
		auto contentStart = Content[input=withoutEnd.indexOf](/* "{" */);
		if (/* contentStart >= 0 */){
			auto beforeContent = Content[input=withoutEnd.substring](/* 0 */, contentStart);
			auto content = Content[input=withoutEnd.substring](Content[input=/* contentStart + "{" */.length](/*  */));
			auto indent = Content[input=createIndent](depth);
			return Generic[base=Some, arguments=JavaList[list=[Content[input=indent + compileBeforeBlock(beforeContent) + "{" + compileStatementsOrBlocks(content], Content[input= depth) + indent + "}"]]]](/* indent + compileBeforeBlock(beforeContent) + "{" + compileStatementsOrBlocks(content */, /*  depth) + indent + "}" */);
		}
	}
	return Generic[base=None, arguments=JavaList[list=[Content[input=]]]](/*  */);
}
/* private static */ char* compileBeforeBlock_Symbol(Symbol this, char* input){
	if (Content[input=input.strip](/* ) */.equals("else")){
		return /* "else " */;
	}
	return Content[input=compileConditional](input, Content[input=/* "if")
                 */.or](Content[input=/*  */](/* ) */ -> /* compileConditional(input, "while"))
                .orElseGet(() */ -> /* generatePlaceholder(input */.strip()));
}
/* private static */ Option<char*> compileConditional_Symbol(Symbol this, char* input, char* prefix){
	auto stripped = Content[input=input.strip](/*  */);
	if (Content[input=stripped.startsWith](prefix)){
		auto withoutKeyword = Content[input=stripped.substring](Content[input=prefix.length](/* ) */).strip();
		if (Content[input=withoutKeyword.startsWith](/* "(") && withoutKeyword */.endsWith(")")){
			auto condition = Content[input=withoutKeyword.substring](/* 1 */, withoutKeyword.length() - 1);
			return Generic[base=Some, arguments=JavaList[list=[Content[input=prefix + " (" + compileValue(condition) + ")"]]]](/* prefix + " (" + compileValue(condition) + ")" */);
		}
	}
	return Generic[base=None, arguments=JavaList[list=[Content[input=]]]](/*  */);
}
/* private static */ Option<char*> compileStatementValue_Symbol(Symbol this, char* input){
	auto stripped = Content[input=input.strip](/*  */);
	if (Content[input=stripped.startsWith](/* "return " */)){
		auto value = Content[input=stripped.substring](Content[input=/* "return " */.length](/*  */));
		return Generic[base=Some, arguments=JavaList[list=[Content[input=/* /* "return " + compileValue */ */]]]](Content[input=/* "return " + compileValue */](value));
	}
	if (Content[input=stripped.endsWith](/* "++" */)){
		auto slice = Content[input=stripped.substring](/* 0 */, Content[input=stripped.length](/* ) - "++" */.length());
		return Generic[base=Some, arguments=JavaList[list=[Content[input=compileValue(slice) + "++"]]]](/* compileValue(slice) + "++" */);
	}
	auto valueSeparator = Content[input=stripped.indexOf](/* "=" */);
	if (/* valueSeparator >= 0 */){
		auto definition = Content[input=stripped.substring](/* 0 */, valueSeparator);
		auto value = Content[input=stripped.substring](Content[input=/* valueSeparator + "=" */.length](/*  */));
		return Content[input=compileDefinitionToString](Content[input=/* definition)
                     */.map](/* outputDefinition */ -> /*  outputDefinition + " = " + compileValue(value */));
	}
	return Generic[base=Some, arguments=JavaList[list=[Content[input=/* generatePlaceholder */]]]](Content[input=generatePlaceholder](input));
}
/* private static */ char* compileValue_Symbol(Symbol this, char* input){
	return Content[input=parseValue](/* input) */.generate();
}
/* private static */ Value parseValue_Symbol(Symbol this, char* input){
	auto stripped = Content[input=input.strip](/*  */);
	if (Content[input=stripped.startsWith](/* "new " */)){
		auto slice = Content[input=stripped.substring](Content[input=/* "new " */.length](/* ) */).strip();
		auto construction = Content[input=parseInvokable](slice, /*  Main::compileConstructorCaller */);
		if (Content[input=/* construction instanceof Some */](/* var invokable */)){
			if (invokable.beforeArgs instanceof Type caller){
				/* Type withoutDiamond */;
				if (Content[input=/* caller instanceof Generic */](/* var base */, /*  var _ */)){
					auto actualTypes = Content[input=invokable.arguments.iter](Content[input=/* )
                                 */.map](/* Value::resolveType)
                                 */.collect(new ListCollector<>());
					auto withoutDiamond1 = Symbol[value=Generic](base, actualTypes);
					/* addGeneric(withoutDiamond1) */;
					/* withoutDiamond = withoutDiamond1; */
				}
				else {
					/* withoutDiamond = caller; */
				}
				return Content[input=invokable.withBeforeArgs](withoutDiamond);
			}
			return invokable;
		}
	}
	if (Content[input=parseInvocation](/* input) instanceof Some(var value */)){
		return value;
	}
	auto arrowIndex = Content[input=input.indexOf](/* " */ -> /* " */);
	if (/* arrowIndex >= 0 */){
		auto beforeArrow = Content[input=input.substring](/* 0 */, /* arrowIndex) */.strip();
		auto afterArrow = Content[input=input.substring](Content[input=/* arrowIndex + " */ -> /* " */.length](/*  */));
		return Symbol[value=Lambda](beforeArrow, Content[input=compileValue](afterArrow));
	}
	auto separator = Content[input=stripped.lastIndexOf](/* " */.");
	if (/* separator >= 0 */){
		auto parent = Content[input=stripped.substring](/* 0 */, separator);
		auto property = Content[input=stripped.substring](Content[input=/* separator + " */.".length](/*  */));
		return Symbol[value=DataAccess](Content[input=parseValue](parent), property);
	}
	if (Content[input=isSymbol](stripped)){
		return Symbol[value=Symbol](stripped);
	}
	return Symbol[value=Content](input);
}
/* private static */ Option<Invokable> parseInvocation_Symbol(Symbol this, char* input){
	return Content[input=parseInvokable](input, Content[input=/* input1 */ -> /*  new Content */](Content[input=compileValue](/* input1 */)));
}
/* private static */ Option<Invokable> parseInvokable_Symbol(Symbol this, char* slice, BeforeArgs (*beforeArgsCompiler)(char*)){
	if (Content[input=/* !slice */.endsWith](/* ")" */)){
		return Generic[base=None, arguments=JavaList[list=[Content[input=]]]](/*  */);
	}
	auto withoutEnd = Content[input=slice.substring](/* 0 */, Content[input=slice.length](/* ) - ")" */.length());
	auto argsStart = Content[input=withoutEnd.indexOf](/* "(" */);
	if (/* argsStart < 0 */){
		return Generic[base=None, arguments=JavaList[list=[Content[input=]]]](/*  */);
	}
	auto base = Content[input=withoutEnd.substring](/* 0 */, argsStart);
	auto args = Content[input=withoutEnd.substring](Content[input=/* argsStart + " */](/* " */.length());
	/* return parseValues(args, value -> new Some<>(parseValue(value))).map(newArgs -> */{
		auto generate = Content[input=beforeArgsCompiler.apply](base);
		return Symbol[value=Invokable](generate, newArgs);
	}
	/* ) */;
}
/* private static */ BeforeArgs compileConstructorCaller_Symbol(Symbol this, char* base){
	if (Content[input=parseAndModifyType](/* base) instanceof Some<Type>(var type */)){
		return type;
	}
	return Symbol[value=Content](base);
}
/* private static */ int isSymbol_Symbol(Symbol this, char* input){
	if (Content[input=input.isEmpty](/*  */)){
		return false;
	}
	if (Content[input=input.equals](/* "private" */)){
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
/* private static */ Option<Whitespace> parseWhitespace_Symbol(Symbol this, char* input){
	if (Content[input=input.isBlank](/*  */)){
		return Generic[base=Some, arguments=JavaList[list=[Content[input=Whitespace]]]](Symbol[value=Whitespace](/*  */));
	}
	return Generic[base=None, arguments=JavaList[list=[Content[input=]]]](/*  */);
}
/* private static */ Option<Defined> parseAndModifyDefinition_Symbol(Symbol this, char* input){
	/* return Main.parseDefinition(input).map(definition -> */{
		if (Content[input=definition.type instanceof Functional](/* var args */, /*  var base */)){
			return Symbol[value=FunctionalDefinition](definition.beforeType, base, definition.name, args);
		}
		return definition;
	}
	/* ) */;
}
/* private static */ Option<char*> compileStatement_Symbol(Symbol this, char* input, Option<char*> (*compiler)(char*), int depth){
	auto stripped = Content[input=input.strip](/*  */);
	if (Content[input=/* !stripped */.endsWith](/* ";" */)){
		return Generic[base=None, arguments=JavaList[list=[Content[input=]]]](/*  */);
	}
	auto withoutEnd = Content[input=stripped.substring](/* 0 */, Content[input=stripped.length](/* ) - ";" */.length());
	return Content[input=compiler.apply](Content[input=/* withoutEnd) */.map](/* definition */ -> /*  generateStatement(definition, depth */));
}
/* private static */ char* generateStatement_Symbol(Symbol this, char* definition, int depth){
	return /* createIndent(depth) + definition + ";" */;
}
/* private static <T> */ Option<List<T>> parseValues_Symbol(Symbol this, char* input, Option<T> (*compiler)(char*)){
	return Content[input=Main.parseAll](input, /*  Main::foldValueChar */, compiler);
}
/* private static */ State foldValueChar_Symbol(Symbol this, State state, char c){
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
/* private static */ Option<Definition> parseDefinition_Symbol(Symbol this, char* input){
	auto stripped = Content[input=input.strip](/*  */);
	auto nameSeparator = Content[input=stripped.lastIndexOf](/* " " */);
	if (/* nameSeparator < 0 */){
		return Generic[base=None, arguments=JavaList[list=[Content[input=]]]](/*  */);
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
/* private static */ Option<int> findTypeSeparator_Symbol(Symbol this, char* input){
	auto depth = /*  0 */;
	/* for */ /* (var */ index = input.length() - 1;
	index > = /*  0 */;
	/* index--) */{
		auto c = Content[input=input.charAt](index);
		if (/* c == ' ' && depth == 0 */){
			return Generic[base=Some, arguments=JavaList[list=[Content[input=index]]]](index);
		}
		if (/* c == '>' */){
			depth++;
		}
		if (/* c == '<' */){
			/* depth-- */;
		}
	}
	return Generic[base=None, arguments=JavaList[list=[Content[input=]]]](/*  */);
}
/* private static */ Option<Type> parseAndModifyType_Symbol(Symbol this, char* input){
	/* return Main.parseType(input).map(parsed -> */{
		if (/* parsed instanceof Generic generic */){
			auto withoutWhitespace = Content[input=generic.arguments.iter](Content[input=/* )
                         */.filter](/* arg */ -> /* !(arg instanceof Whitespace))
                         */.collect(new ListCollector<>());
			auto withoutWhitespaceGeneric = Content[input=generic.withArgs](withoutWhitespace);
			auto base = withoutWhitespaceGeneric.base;
			auto arguments1 = withoutWhitespaceGeneric.arguments;
			if (Content[input=base.equals](/* "Function" */)){
				auto argType = Content[input=/* arguments1 */.get](/* 0 */);
				auto returnType = Content[input=/* arguments1 */.get](/* 1 */);
				return Symbol[value=Functional](Content[input=Lists.of](argType), returnType);
			}
			if (Content[input=base.equals](/* "Supplier" */)){
				auto returns = Content[input=/* arguments1 */.get](/* 0 */);
				return Symbol[value=Functional](Content[input=Lists.emptyList](/*  */), returns);
			}
			if (Content[input=base.equals](/* "BiFunction" */)){
				auto argType = Content[input=/* arguments1 */.get](/* 0 */);
				auto argType2 = Content[input=/* arguments1 */.get](/* 1 */);
				auto returnType = Content[input=/* arguments1 */.get](/* 2 */);
				return Symbol[value=Functional](Lists.of(argType, /*  argType2) */, returnType);
			}
			else {
				/* addGeneric(withoutWhitespaceGeneric) */;
			}
		}
		return parsed;
	}
	/* ) */;
}
/* private static */ void addGeneric_Symbol(Symbol this, Generic generic){
	if (Content[input=/* !generics */.contains](/* generic) && generic */.arguments.hasElements()){
		/* generics.addLast(generic) */;
	}
}
/* private static */ Option<Type> parseType_Symbol(Symbol this, char* input){
	auto stripped = Content[input=input.strip](/*  */);
	if (Content[input=stripped.equals](/* "public" */)){
		return Generic[base=None, arguments=JavaList[list=[Content[input=]]]](/*  */);
	}
	if (Content[input=stripped.equals](/* "boolean" */)){
		return Generic[base=Some, arguments=JavaList[list=[Content[input=Primitive]]]](Primitive.Bit);
	}
	if (Content[input=stripped.equals](/* "String" */)){
		return Generic[base=Some, arguments=JavaList[list=[Content[input=Ref]]]](Symbol[value=Ref](Primitive.I8));
	}
	if (Content[input=stripped.equals](/* "int") || stripped */.equals("Integer")){
		return Generic[base=Some, arguments=JavaList[list=[Content[input=Primitive]]]](Primitive.I32);
	}
	if (Content[input=stripped.equals](/* "var" */)){
		return Generic[base=Some, arguments=JavaList[list=[Content[input=Primitive]]]](Primitive.Var);
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
	if (Content[input=isSymbol](stripped)){
		return Generic[base=Some, arguments=JavaList[list=[Content[input=Symbol]]]](Symbol[value=Symbol](stripped));
	}
	return Generic[base=Some, arguments=JavaList[list=[Content[input=Content]]]](Symbol[value=Content](input));
}
/* private static */ Option<Type> parseGenericArgument_Symbol(Symbol this, char* input1){
	return Content[input=parseWhitespace](Content[input=/* input1)
                 */.<Type>map](/* whitespace */ -> /* whitespace)
                .or(() */ -> /*  parseAndModifyType(input1 */));
}
/* private static */ StringBuilder mergeStatements_Symbol(Symbol this, StringBuilder stringBuilder, char* str){
	return Content[input=stringBuilder.append](str);
}
/* private static */ char* compileStatementsOrBlocks_Symbol(Symbol this, char* body, int depth){
	return Content[input=Main.compileStatements](body, Content[input=/* segment */ -> /*  new Some<> */](/* compileStatementOrBlock(segment */, /*  depth + 1) */));
}
/* private static */ char* compileStatements_Symbol(Symbol this, char* input, Option<char*> (*compiler)(char*)){
	return Content[input=Main.parseStatements](input, Content[input=/* compiler) */.map](/* Main::generateStatements */).orElse("");
}
/* private static */ Option<List<char*>> parseStatements_Symbol(Symbol this, char* input, Option<char*> (*compiler)(char*)){
	return Content[input=Main.parseAll](input, /*  Main::foldStatementChar */, compiler);
}
/* private static */ char* generateStatements_Symbol(Symbol this, List<char*> inner){
	return Content[input=generateAll](/* Main::mergeStatements */, inner);
}
/* private static */ State foldStatementChar_Symbol(Symbol this, State state, char c){
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
/* private static */ Option<char*> compileDefinitionToString_Symbol(Symbol this, char* input){
	return Content[input=Main.parseAndModifyDefinition](/* input) */.map(Defined::generate);
}
void main_Symbol(Symbol this){
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
/* private */ char* compileRoot_Symbol(Symbol this, char* input){
	auto compiled = Content[input=compileStatements](input, Content[input=/* segment */ -> /*  new Some<> */](Content[input=this.compileRootSegment](segment)));
	auto joinedStructs = Content[input=structs.iter](Content[input=/* ) */.collect](Symbol[value=Joiner](/*  */)).orElse("");
	auto joinedGenerics = Content[input=generics.iter](/* )
                .map(Generic::generate)
                .map(result */ -> Content[input=/* "// " + result + "\n")
                 */.collect](Symbol[value=Joiner](/*  */)).orElse("");
	auto joinedMethods = Content[input=methods.iter](Content[input=/* ) */.collect](Symbol[value=Joiner](/*  */)).orElse("");
	return /* compiled + joinedStructs + joinedGenerics + joinedMethods */;
}
/* private */ char* compileRootSegment_Symbol(Symbol this, char* input){
	return Content[input=this.compileClass](/* input)
                .orElseGet(() */ -> /* generatePlaceholder(input */.strip()) + "\n");
}
/* private */ Option<char*> compileClass_Symbol(Symbol this, char* input){
	return Content[input=this.compileStructured](input, /*  "class " */);
}
/* private */ Option<char*> compileStructured_Symbol(Symbol this, char* input, char* infix){
	auto classIndex = Content[input=input.indexOf](infix);
	if (/* classIndex < 0 */){
		return Generic[base=None, arguments=JavaList[list=[Content[input=]]]](/*  */);
	}
	auto left = Content[input=input.substring](/* 0 */, /* classIndex) */.strip();
	auto right = Content[input=input.substring](Content[input=/* classIndex + infix */.length](/*  */));
	auto contentStart = Content[input=right.indexOf](/* "{" */);
	if (/* contentStart < 0 */){
		return Generic[base=None, arguments=JavaList[list=[Content[input=]]]](/*  */);
	}
	auto beforeContent = Content[input=right.substring](/* 0 */, /* contentStart) */.strip();
	auto paramStart = Content[input=beforeContent.indexOf](/* "(" */);
	auto withoutParams = Content[input=/* paramStart >= 0
                ? beforeContent */.substring](/* 0 */, paramStart).strip()
                : beforeContent;
	auto typeParamStart = Content[input=withoutParams.indexOf](/* "<" */);
	/* String name */;
	if (/* typeParamStart >= 0 */){
		return Generic[base=Some, arguments=JavaList[list=[Content[input=""]]]](/* "" */);
	}
	else {
		/* name = withoutParams; */
	}
	auto withEnd = Content[input=right.substring](Content[input=/* contentStart + "{" */.length](/* ) */).strip();
	if (Content[input=/* !withEnd */.endsWith](/* "}" */)){
		return Generic[base=None, arguments=JavaList[list=[Content[input=]]]](/*  */);
	}
	auto inputContent = Content[input=withEnd.substring](/* 0 */, withEnd.length() - 1);
	/* currentStruct = new Some<>(name); */
	auto outputContent = Content[input=compileStatements](inputContent, Content[input=/* segment */ -> /*  new Some<> */](Content[input=this.compileStructuredSegment](segment)));
	auto generated = /*  "typedef " + generatePlaceholder(left) + "struct {" + outputContent + "\n} " +
                name +
                ";\n" */;
	/* structs.addLast(generated) */;
	return Generic[base=Some, arguments=JavaList[list=[Content[input=""]]]](/* "" */);
}
/* private */ char* compileStructuredSegment_Symbol(Symbol this, char* input){
	return Content[input=parseWhitespace](Content[input=/* input) */.map](/* Whitespace::generate)
                .or(() */ -> /* this.compileStructured(input, "interface "))
                .or(() */ -> /* this.compileStructured(input, "enum "))
                .or(() */ -> /* this.compileStructured(input, "class "))
                .or(() */ -> /* this.compileStructured(input, "record "))
                .or(() */ -> /* this.compileMethod(input))
                .or(() */ -> /* this.compileDefinitionStatement(input))
                .orElseGet(() */ -> /*  generatePlaceholder(input */));
}
/* private */ Option<char*> compileMethod_Symbol(Symbol this, char* input){
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
				auto inputParameters = Content[input=Main.parseValues](paramString, Content[input=/* s */ -> /*  new Some<> */](/* this.parseParameter(s)))
                                    .orElse(Lists.emptyList())
                                    .iter()
                                    .filter(param */ -> /* !(param instanceof Whitespace))
                                     */.collect(new ListCollector<>());
				auto aThis = Symbol[value=Definition](Symbol[value=Symbol](Content[input=currentStruct.orElse](/* "" */)), /*  "this" */);
				auto outputParams = Content[input=inputParameters.addFirst](/* aThis)
                                     */.iter().map(Parameter::generate).collect(new Joiner(", ")).orElse("");
				/* String newBody */;
				/* if (withBraces.startsWith(" */{
					/* ") && withBraces.endsWith("}")) { */ auto body = withBraces.substring(1, withBraces.length() - 1);
                                newBody = "{" + compileStatementsOrBlocks(body, 0) + "\n}";
				}
				/* else if (withBraces.equals(";")) */{
					/* newBody = ";"; */
				}
				else {
					return Generic[base=None, arguments=JavaList[list=[Content[input=]]]](/*  */);
				}
				auto generated = /*  outputDefinition + "(" + outputParams + ")" + newBody + "\n" */;
				/* methods.addLast(generated) */;
				return Generic[base=Some, arguments=JavaList[list=[Content[input=""]]]](/* "" */);
			}
			return Generic[base=None, arguments=JavaList[list=[Content[input=]]]](/*  */);
		}
		/* ) */;
	}
	return Generic[base=None, arguments=JavaList[list=[Content[input=]]]](/*  */);
}
/* private */ Parameter parseParameter_Symbol(Symbol this, char* param){
	return Content[input=parseWhitespace](Content[input=/* param) */.<Parameter>map](/* inner */ -> /* inner)
                .or(() */ -> /* parseAndModifyDefinition(param).map(inner */ -> /* inner))
                .orElseGet(() */ -> /*  new Content(param */));
}
/* private */ Option<char*> compileDefinitionStatement_Symbol(Symbol this, char* input){
	return Content[input=compileStatement](input, /*  Main::compileDefinitionToString */, /*  1 */);
}
