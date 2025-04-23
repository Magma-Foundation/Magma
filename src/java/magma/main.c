/* package magma; */
/* import java.io.IOException; */
/* import java.nio.file.Files; */
/* import java.nio.file.Paths; */
/* import java.util.function.BiFunction; */
/* import java.util.function.Function; */
/* import java.util.function.Supplier; */
/*  */
/* public sealed */struct Option<T> permits Some, None {
};
/*  */struct Head<T> {
};
/* public */struct List<T> {
};
/* public */struct Collector<T, C> {
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
/* public */struct Tuple<A, B>(A left, B right) {
};
/* public static */struct RangeHead implements Head<Integer> {
	/* private final */ int length;
	/* private */ int counter;/* 

        public RangeHead(int length) {
            this.length = length;
        } */
};
/* public */struct Iterator<T>(Head<T> head) {
};
/* private static */struct State {
	/* private final */ List<char*> segments;
	/* private */ /* StringBuilder */ buffer;
	/* private */ int depth;/* 

        public State() {
            this(Lists.emptyList(), new StringBuilder(), 0);
        } */
};
/* private */struct Some<T>(T value) implements Option<T> {
};
/* private static final */struct None<T> implements Option<T> {
};
/* private */struct Joiner(String delimiter) implements Collector<String, Option<String>> {
};
/* private static */struct ListCollector<T> implements Collector<T, List<T>> {
};
/* private */struct Generic(String base, List<Type> args) implements Type {
};
/* private */struct Content(String input) implements Type {
};
/* private */struct Functional(List<Type> paramTypes, Type returnType) implements Type {
};
/* private */struct Definition(Option<String> beforeType, Type type, String name) implements Defined {
};
/* private */struct FunctionalDefinition(
            Option<String> beforeType,
            Type returns,
            String name,
            List<Type> args
    ) implements Defined {
};
/* private */struct Ref(Type type) implements Type {
};
/* private */struct OptionCollector<T, C>(Collector<T, C> collector) implements Collector<Option<T>, Option<C>> {
};
/*  */struct Main {
};
/* <R> */ Option</* R */> map(/*  R */(*mapper)(/* T */));
/* T */ orElseGet(/* T */(*other)());
Option</* T */> or(Option</* T */>(*other)());
/* T */ orElse(/* T */ other);
/* <R> */ Option</* R */> flatMap(Option</* R */>(*mapper)(/* T */));
/* <R> */ Option<Tuple</* T */, /*  R */>> and(Option</* R */>(*other)());
Option</* T */> next();
List</* T */> add(/* T */ element);
Iterator</* T */> iter();
int hasElements();
/* T */ removeFirst();
/* T */ get(int index);
/* C */ createInitial();
/* C */ fold(/* C */ current, /* T */ element);
char* generate();
char* generate();
/* @Override
        public */ char* generate(){
	return this.value;
}
/* @Override
        public */ Option<int> next(){
	if (this.counter < this.length){
		auto value = this.counter;
		this.counter++;
		return Some</*  */>(value);
	}
	else {
		return None</*  */>();
	}
}
/* public <R> */ Iterator</* R */> map(/*  R */(*mapper)(/* T */)){
	return Iterator</*  */>(/* () */ -> this.head.next().map(mapper));
}
/* public <R> */ /* R */ fold(/* R */ initial, BiFunction</* R */, /*  T */, /*  R */> folder){
	auto current = initial;
	/* while (true) */{
		/* switch (this.head.next()) */{
			case Some</* T>(var value) - */> current = folder.apply(current, value);
			/* case None<T> _ -> */{
				return current;
			}
		}
	}
}
/* public <C> */ /* C */ collect(Collector</* T */, /*  C */> collector){
	return this.fold(collector.createInitial(), collector::fold);
}
/* private */ State(List<char*> segments, /* StringBuilder */ buffer, int depth){
	/* this.segments = segments; */
	/* this.buffer = buffer; */
	/* this.depth = depth; */
}
/* public */ int isLevel(){
	return this.depth == 0;
}
/* public */ /* State */ enter(){
	this.depth++;
	return this;
}
/* public */ /* State */ exit(){
	/* this.depth-- */;
	return this;
}
/* private */ /* State */ append(/* char */ c){
	/* this.buffer.append(c) */;
	return this;
}
/* private */ /* State */ advance(){
	/* this.segments.add(this.buffer.toString()) */;
	/* this.buffer = new StringBuilder(); */
	return this;
}
/* public */ int isShallow(){
	return this.depth == 1;
}
/* @Override
        public <R> */ Option</* R */> map(/*  R */(*mapper)(/* T */)){
	return Some</*  */>(mapper.apply(this.value));
}
/* @Override
        public */ /* T */ orElseGet(/* T */(*other)()){
	return this.value;
}
/* @Override
        public */ Option</* T */> or(Option</* T */>(*other)()){
	return this;
}
/* @Override
        public */ /* T */ orElse(/* T */ other){
	return this.value;
}
/* @Override
        public <R> */ Option</* R */> flatMap(Option</* R */>(*mapper)(/* T */)){
	return mapper.apply(this.value);
}
/* @Override
        public <R> */ Option<Tuple</* T */, /*  R */>> and(Option</* R */>(*other)()){
	return /* other.get().map(otherValue */ -> Tuple</*  */>(this.value, /*  otherValue) */);
}
/* @Override
        public <R> */ Option</* R */> map(/*  R */(*mapper)(/* T */)){
	return None</*  */>();
}
/* @Override
        public */ /* T */ orElseGet(/* T */(*other)()){
	return other.get();
}
/* @Override
        public */ Option</* T */> or(Option</* T */>(*other)()){
	return other.get();
}
/* @Override
        public */ /* T */ orElse(/* T */ other){
	return other;
}
/* @Override
        public <R> */ Option</* R */> flatMap(Option</* R */>(*mapper)(/* T */)){
	return None</*  */>();
}
/* @Override
        public <R> */ Option<Tuple</* T */, /*  R */>> and(Option</* R */>(*other)()){
	return None</*  */>();
}
/* private */ Joiner(){
	/* this("") */;
}
/* @Override
        public */ Option<char*> createInitial(){
	return None</*  */>();
}
/* @Override
        public */ Option<char*> fold(Option<char*> maybeCurrent, char* element){
	/* return new Some<>(switch (maybeCurrent) */{
		/* case None<String> _ -> element */;
		/* case Some<String>(var current) -> current + this.delimiter + element */;
	}
	/* ) */;
}
/* @Override
        public */ List</* T */> createInitial(){
	return Lists.emptyList();
}
/* @Override
        public */ List</* T */> fold(List</* T */> current, /* T */ element){
	return current.add(element);
}
/* @Override
        public */ char* generate(){
	auto joined = /* generateValuesFromNodes(this */.args);
	return this.base + "<" + joined + ">";
}
/* @Override
        public */ char* generate(){
	return Main.generatePlaceholder(this.input);
}
/* @Override
        public */ char* generate(){
	return this.returnType.generate() + " (*)(" + generateValuesFromNodes(this.paramTypes) + ")";
}
/* @Override
        public */ char* generate(){
	auto beforeTypeString = /* this.beforeType
                    .map(Main::generatePlaceholder)
                    .map(inner */ -> /* inner + " ")
                     */.orElse("");
	return /* beforeTypeString + this */.type.generate() + " " + this.name;
}
/* @Override
        public */ char* generate(){
	auto beforeTypeString = /* this.beforeType.map(inner */ -> /* inner + " ") */.orElse("");
	return /* "%s%s(*%s)(%s)" */.formatted(beforeTypeString, this.returns.generate(), this.name, generateValuesFromNodes(this.args));
}
/* @Override
        public */ char* generate(){
	return this.type.generate() + "*";
}
/* @Override
        public */ Option</* C */> createInitial(){
	return Some</*  */>(this.collector.createInitial());
}
/* @Override
        public */ Option</* C */> fold(Option</* C */> current, Option</* T */> element){
	return /* current.and(() */ -> /* element).map(tuple */ -> this.collector.fold(tuple.left, tuple.right));
}
/* private static final List<String> structs */ /* = */ Lists.emptyList();
/* private static final List<String> methods */ /* = */ Lists.emptyList();
/* private static */ char* generateAll(BiFunction</* StringBuilder */, char*, /*  StringBuilder */> merger, List<char*> parsed){
	return parsed.iter().fold(new StringBuilder(), merger).toString();
}
/* private static <T> */ Option<List</* T */>> parseAll(char* input, BiFunction</* State */, /*  Character */, /*  State */> folder, Option</* T */>(*compiler)(char*)){
	return Main.divideAll(input, folder).iter().map(compiler).collect(new OptionCollector<>(new ListCollector<>()));
}
/* private static */ List<char*> divideAll(char* input, BiFunction</* State */, /*  Character */, /*  State */> folder){
	auto current = /* State */();
	auto queue = Iterator</*  */>(/* RangeHead */(input.length())).map(input::charAt).collect(new ListCollector<>());
	/* while (queue.hasElements()) */{
		auto c = queue.removeFirst();
		if (/* c == '\'' */){
			/* current.append(c) */;
			auto c1 = queue.removeFirst();
			/* current.append(c1) */;
			if (/* c1 == '\\' */){
				/* current.append(queue.removeFirst()) */;
			}
			/* current.append(queue.removeFirst()) */;
			/* continue */;
		}
		if (/* c == '"' */){
			/* current.append(c) */;
			/* while (queue.hasElements()) */{
				auto next = queue.removeFirst();
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
	return current.advance().segments;
}
/* private static */ char* generateValues(List<char*> parserd){
	return Main.generateAll(Main::mergeValues, parserd);
}
/* private static */ /* StringBuilder */ mergeValues(/* StringBuilder */ cache, char* element){
	if (cache.isEmpty()){
		return cache.append(element);
	}
	return cache.append(", ").append(element);
}
/* private static */ char* generatePlaceholder(char* input){
	return /* "/* " + input + " */" */;
}
/* private static */ char* generateValuesFromNodes(List</* Type */> list){
	return list.iter().map(Type::generate).collect(new Joiner(", ")).orElse("");
}
/* private static */ char* compileStatementOrBlock(char* input, int depth){
	return /* compileWhitespace(input)
                .or(() */ -> /* compileStatement(input, Main::compileStatementValue, depth))
                .or(() */ -> /* compileBlock(input, depth))
                .orElseGet(() */ -> /* createIndent(depth) + generatePlaceholder(input */.strip()));
}
/* private static */ char* createIndent(int depth){
	return /* "\n" + "\t" */.repeat(depth);
}
/* private static */ Option<char*> compileBlock(char* input, int depth){
	auto stripped = input.strip();
	if (stripped.endsWith("}")){
		auto withoutEnd = stripped.substring(0, stripped.length() - "}".length());
		auto contentStart = withoutEnd.indexOf("{");
		if (/* contentStart >= 0 */){
			auto beforeContent = withoutEnd.substring(0, contentStart);
			auto content = withoutEnd.substring(contentStart + "{".length());
			auto indent = /*  createIndent(depth) */;
			return Some</*  */>(/* indent + compileBeforeBlock(beforeContent) + "{" + compileStatementsOrBlocks(content */, /*  depth) + indent + "}" */);
		}
	}
	return None</*  */>();
}
/* private static */ char* compileBeforeBlock(char* input){
	auto stripped = input.strip();
	if (stripped.startsWith("if")){
		auto withoutKeyword = stripped.substring("if".length()).strip();
		if (withoutKeyword.startsWith("(") && withoutKeyword.endsWith(")")){
			auto condition = withoutKeyword.substring(1, withoutKeyword.length() - 1);
			return /* "if (" + compileValue(condition) + ")" */;
		}
	}
	if (stripped.equals("else")){
		return /* "else " */;
	}
	return /* generatePlaceholder(stripped) */;
}
/* private static */ Option<char*> compileStatementValue(char* input){
	auto stripped = input.strip();
	if (stripped.startsWith("return ")){
		auto value = stripped.substring("return ".length());
		return Some</*  */>(/* "return " + compileValue(value) */);
	}
	if (stripped.endsWith("++")){
		auto slice = stripped.substring(0, stripped.length() - "++".length());
		return Some</*  */>(/* compileValue(slice) + "++" */);
	}
	auto valueSeparator = stripped.indexOf("=");
	if (/* valueSeparator >= 0 */){
		auto definition = stripped.substring(0, valueSeparator);
		auto value = stripped.substring(valueSeparator + "=".length());
		/* return compileDefinitionToString(definition).map(outputDefinition -> */{
			return /* outputDefinition + " = " + compileValue(value) */;
		}
		/* ) */;
	}
	return Some</*  */>(/* generatePlaceholder(input) */);
}
/* private static */ char* compileValue(char* input){
	auto stripped = input.strip();
	if (stripped.startsWith("new ")){
		auto slice = stripped.substring("new ".length()).strip();
		if (slice.endsWith(")")){
			auto withoutEnd = slice.substring(0, slice.length() - ")".length());
			auto argsStart = withoutEnd.indexOf("(");
			if (/* argsStart >= 0 */){
				auto base = withoutEnd.substring(0, argsStart);
				auto args = withoutEnd.substring(argsStart + "(".length());
				if (/* parseAndModifyType(base) instanceof Some<Type>(Type type) */){
					auto newArgs = /* parseValues(args, value */ -> Some</*  */>(/* compileValue(value)))
                                 */.map(Main::generateValues).orElse("");
					return type.generate() + "(" + newArgs + ")";
				}
			}
		}
	}
	auto arrowIndex = /* input.indexOf(" */ -> /* ") */;
	if (/* arrowIndex >= 0 */){
		auto beforeArrow = input.substring(0, arrowIndex).strip();
		auto afterArrow = /* input.substring(arrowIndex + " */ -> /* " */.length());
		return /* generatePlaceholder(beforeArrow) + " */ -> /*  " + compileValue(afterArrow) */;
	}
	auto separator = stripped.lastIndexOf(".");
	if (/* separator >= 0 */){
		auto parent = stripped.substring(0, separator);
		auto property = stripped.substring(separator + ".".length());
		return /* compileValue(parent) + " */." + property;
	}
	if (/* isSymbol(stripped) */){
		return stripped;
	}
	return /* generatePlaceholder(input) */;
}
/* private static */ int isSymbol(char* input){
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
/* private static */ Option<char*> compileWhitespace(char* input){
	if (input.isBlank()){
		return Some</*  */>(/* "" */);
	}
	return None</*  */>();
}
/* private static */ Option</* Defined */> parseAndModifyDefinition(char* input){
	/* return Main.parseDefinition(input).map(definition -> */{
		if (definition.type instanceof Functional(var args, var base)){
			return /* FunctionalDefinition */(definition.beforeType, base, definition.name, args);
		}
		return definition;
	}
	/* ) */;
}
/* private static */ Option<char*> compileStatement(char* input, Option<char*>(*compiler)(char*), int depth){
	auto stripped = input.strip();
	if (/* !stripped */.endsWith(";")){
		return None</*  */>();
	}
	auto withoutEnd = stripped.substring(0, stripped.length() - ";".length());
	return /* compiler.apply(withoutEnd).map(definition */ -> /*  generateStatement(definition, depth)) */;
}
/* private static */ char* generateStatement(char* definition, int depth){
	return /* createIndent(depth) + definition + ";" */;
}
/* private static <T> */ Option<List</* T */>> parseValues(char* input, Option</* T */>(*compiler)(char*)){
	return Main.parseAll(input, Main::foldValueChar, compiler);
}
/* private static */ /* State */ foldValueChar(/* State */ state, /* char */ c){
	if (/* c == ',' && state */.isLevel()){
		return state.advance();
	}
	auto appended = state.append(c);
	if (/* c == '<' */){
		return appended.enter();
	}
	if (/* c == '>' */){
		return appended.exit();
	}
	return appended;
}
/* private static */ Option</* Definition */> parseDefinition(char* input){
	auto stripped = input.strip();
	auto nameSeparator = stripped.lastIndexOf(" ");
	if (/* nameSeparator < 0 */){
		return None</*  */>();
	}
	auto beforeName = stripped.substring(0, nameSeparator).strip();
	auto name = stripped.substring(nameSeparator + " ".length()).strip();
	/* return switch (Main.findTypeSeparator(beforeName)) */{
		/* case None<Integer> _ ->
                    Main.parseAndModifyType(beforeName).map(type -> new Definition(new None<>(), type, name)) */;
		/* case Some<Integer>(var typeSeparator) -> */{
			auto beforeType = beforeName.substring(0, typeSeparator).strip();
			auto inputType = beforeName.substring(typeSeparator + " ".length()).strip();
			/* yield Main.parseAndModifyType(inputType).map(outputType -> new Definition(new Some<>(beforeType), outputType, name)) */;
		}
	}
	/*  */;
}
/* private static */ Option<int> findTypeSeparator(char* input){
	auto depth = /*  0 */;
	/* for */ /* (var */ index = input.length() - 1;
	/* index */ > = /*  0 */;
	/* index--) */{
		auto c = input.charAt(index);
		if (/* c == ' ' && depth == 0 */){
			return Some</*  */>(index);
		}
		if (/* c == '>' */){
			depth++;
		}
		if (/* c == '<' */){
			/* depth-- */;
		}
	}
	return None</*  */>();
}
/* private static */ Option</* Type */> parseAndModifyType(char* input){
	/* return Main.parseType(input).map(parsed -> */{
		if (/* parsed instanceof Generic(var base, var arguments) */){
			if (base.equals("Function")){
				auto argType = arguments.get(0);
				auto returnType = arguments.get(1);
				return /* Functional */(Lists.of(argType), returnType);
			}
			if (base.equals("Supplier")){
				auto returns = arguments.get(0);
				return /* Functional */(Lists.emptyList(), returns);
			}
		}
		return parsed;
	}
	/* ) */;
}
/* private static */ Option</* Type */> parseType(char* input){
	auto stripped = input.strip();
	if (stripped.equals("public")){
		return None</*  */>();
	}
	if (stripped.equals("boolean")){
		return Some</*  */>(Primitive.Bit);
	}
	if (stripped.equals("String")){
		return Some</*  */>(/* Ref */(Primitive.I8));
	}
	if (stripped.equals("int") || stripped.equals("Integer")){
		return Some</*  */>(Primitive.I32);
	}
	if (stripped.equals("var")){
		return Some</*  */>(Primitive.Var);
	}
	if (stripped.endsWith(">")){
		auto slice = stripped.substring(0, stripped.length() - ">".length());
		auto argsStart = slice.indexOf("<");
		if (/* argsStart >= 0 */){
			auto base = slice.substring(0, argsStart).strip();
			auto inputArgs = slice.substring(argsStart + "<".length());
			return /* Main.parseValues(inputArgs, Main::parseAndModifyType).map(args */ -> /* Generic */(base, /*  args) */);
		}
	}
	return Some</*  */>(/* Content */(input));
}
/* private static */ /* StringBuilder */ mergeStatements(/* StringBuilder */ stringBuilder, char* str){
	return stringBuilder.append(str);
}
/* private static */ char* compileStatementsOrBlocks(char* body, int depth){
	return /* Main.compileStatements(body, segment */ -> Some</*  */>(/* compileStatementOrBlock(segment */, /*  depth + 1)) */);
}
/* private static */ char* compileStatements(char* input, Option<char*>(*compiler)(char*)){
	return Main.parseStatements(input, compiler).map(Main::generateStatements).orElse("");
}
/* private static */ Option<List<char*>> parseStatements(char* input, Option<char*>(*compiler)(char*)){
	return Main.parseAll(input, Main::foldStatementChar, compiler);
}
/* private static */ char* generateStatements(List<char*> inner){
	return /* generateAll(Main::mergeStatements, inner) */;
}
/* private static */ /* State */ foldStatementChar(/* State */ state, /* char */ c){
	auto appended = state.append(c);
	if (/* c == ';' && appended */.isLevel()){
		return appended.advance();
	}
	if (/* c == '}' && appended */.isShallow()){
		return appended.advance().exit();
	}
	/* if (c == ' */{
		/* ') {
            return appended.enter() */;
	}
	if (/* c == '}' */){
		return appended.exit();
	}
	else {
		return appended;
	}
}
/* private static */ Option<char*> compileDefinitionToString(char* input){
	return Main.parseAndModifyDefinition(input).map(Defined::generate);
}
/* void */ main(){
	/* try */{
		auto source = Paths.get(".", "src", "java", "magma", "Main.java");
		auto input = Files.readString(source);
		auto target = source.resolveSibling("main.c");
		/* Files.writeString(target, this.compileRoot(input)) */;
	}
	/* catch (IOException e) */{
		/* //noinspection CallToPrintStackTrace
            e.printStackTrace() */;
	}
}
/* private */ char* compileRoot(char* input){
	auto compiled = /* compileStatements(input, segment */ -> Some</*  */>(this.compileRootSegment(segment)));
	auto joinedStructs = structs.iter().collect(new Joiner()).orElse("");
	auto joinedMethods = methods.iter().collect(new Joiner()).orElse("");
	return /* compiled + joinedStructs + joinedMethods */;
}
/* private */ char* compileRootSegment(char* input){
	return /* this.compileClass(input)
                .orElseGet(() */ -> /* generatePlaceholder(input */.strip()) + "\n");
}
/* private */ Option<char*> compileClass(char* input){
	return this.compileStructured(input, "class ");
}
/* private */ Option<char*> compileStructured(char* input, char* infix){
	auto classIndex = input.indexOf(infix);
	if (/* classIndex < 0 */){
		return None</*  */>();
	}
	auto left = input.substring(0, classIndex).strip();
	auto right = input.substring(classIndex + infix.length());
	auto contentStart = right.indexOf("{");
	if (/* contentStart < 0 */){
		return None</*  */>();
	}
	auto name = right.substring(0, contentStart).strip();
	auto withEnd = right.substring(contentStart + "{".length()).strip();
	if (/* !withEnd */.endsWith("}")){
		return None</*  */>();
	}
	auto inputContent = withEnd.substring(0, withEnd.length() - 1);
	auto outputContent = /* compileStatements(inputContent, segment */ -> Some</*  */>(this.compileStructuredSegment(segment)));
	auto generated = /*  generatePlaceholder(left) + "struct " + name + " {" + outputContent + "\n};\n" */;
	/* structs.add(generated) */;
	return Some</*  */>(/* "" */);
}
/* private */ char* compileStructuredSegment(char* input){
	return /* compileWhitespace(input)
                .or(() */ -> /* this.compileStructured(input, "interface "))
                .or(() */ -> /* this.compileStructured(input, "enum "))
                .or(() */ -> /* this.compileStructured(input, "class "))
                .or(() */ -> /* this.compileStructured(input, "record "))
                .or(() */ -> /* this.compileMethod(input))
                .or(() */ -> /* this.compileDefinitionStatement(input))
                .orElseGet(() */ -> /*  generatePlaceholder(input)) */;
}
/* private */ Option<char*> compileMethod(char* input){
	auto paramStart = input.indexOf("(");
	if (/* paramStart >= 0 */){
		auto inputDefinition = input.substring(0, paramStart).strip();
		auto withParams = input.substring(paramStart + "(".length());
		/* return parseAndModifyDefinition(inputDefinition).map(Defined::generate).flatMap(outputDefinition -> */{
			auto paramEnd = withParams.indexOf(")");
			if (/* paramEnd >= 0 */){
				auto paramString = withParams.substring(0, paramEnd).strip();
				auto withBraces = withParams.substring(paramEnd + ")".length()).strip();
				auto outputParams = /* Main.parseValues(paramString, s */ -> Some</*  */>(this.compileParam(s))).map(Main::generateValues).orElse("");
				/* String newBody */;
				/* if (withBraces.startsWith(" */{
					/* ") && withBraces.endsWith("}")) { */ auto body = withBraces.substring(1, withBraces.length() - 1);
                        newBody = "{" + compileStatementsOrBlocks(body, 0) + "\n}";
				}
				/* else if (withBraces.equals(";")) */{
					/* newBody = ";"; */
				}
				else {
					return None</*  */>();
				}
				auto generated = /*  outputDefinition + "(" + outputParams + ")" + newBody + "\n" */;
				/* methods.add(generated) */;
				return Some</*  */>(/* "" */);
			}
			return None</*  */>();
		}
		/* ) */;
	}
	return None</*  */>();
}
/* private */ char* compileParam(char* param){
	return /* compileWhitespace(param)
                .or(() */ -> /* parseAndModifyDefinition(param).map(Defined::generate))
                .orElseGet(() */ -> /*  generatePlaceholder(param)) */;
}
/* private */ Option<char*> compileDefinitionStatement(char* input){
	return /* compileStatement(input, Main::compileDefinitionToString, 1) */;
}
