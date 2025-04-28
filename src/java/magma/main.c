/* private */struct Defined extends Node {
};
/* private */struct Value extends Node {
};
/* private */struct Node {
};
/* private */struct State {
};
/* private */struct Joiner {
};
/* private */struct Definition {
};
/* private */struct Content {
};
/* private static */struct Whitespace implements Defined, Value {
};
/* private */struct StringValue {
};
/* private */struct Symbol {
};
/* private */struct Invocation {
};
/* private */struct DataAccess {
};
/* public */struct Main {
	/* private static String functionName = "" */;
	/* private static int functionLocalCounter = 0 */;
};
/* private */struct Tuple<char, struct State> {
};
/* public sealed */struct Option<Tuple<char, struct State>> {
};
/* private */struct Tuple</*  */> {
};
/* public */struct None</*  */> {
};
/* public */struct Some</*  */> {
};
/* public sealed */struct Option<struct State> {
};
/* public sealed */struct Option<char> {
};
/* public sealed */struct Option<char*> {
};
/* public */struct List<char*> {
};
/* public */struct List<struct T> {
};
/* private static */struct ListCollector</*  */> {
};
/* public */struct Some<char*> {
};
/* public sealed */struct Option<struct Whitespace> {
};
/* public sealed */struct Option<struct Definition> {
};
/* private static */ struct State fromInput(struct State this, char* input){
	return struct State(input, listEmpty(), "", 0, 0);
}
auto popAndAppendToTuple_local0(auto tuple){
	struct var poppedChar = tuple.left;
	struct var poppedState = tuple.right;
	struct var appended = poppedState.append(poppedState, poppedChar);
	return Tuple</*  */>(poppedChar, appended);
}
/* private */ Option<Tuple<char, struct State>> popAndAppendToTuple(struct State this){
	return this.pop(this).map(this.pop(this), popAndAppendToTuple_local0);
}
/* private */ int isLevel(struct State this){
	return this.depth == 0;
}
/* private */ struct State enter(struct State this){
	return struct State(this.input, this.segments, this.buffer, this.depth + 1, this.index);
}
/* private */ struct State exit(struct State this){
	return struct State(this.input, this.segments, this.buffer, this.depth - 1, this.index);
}
/* private */ struct State advance(struct State this){
	return struct State(this.input, this.segments.addLast(this.segments, this.buffer), "", this.depth, this.index);
}
/* private */ int isShallow(struct State this){
	return this.depth == 1;
}
/* private */ Option<Tuple<char, struct State>> pop(struct State this){
	if (this.index >= this.input.length(this.index >= this.input)){
		return None</*  */>();
	}
	struct var escaped = this.input.charAt(this.input, this.index);
	return Some</*  */>(Tuple<char, struct State>(escaped, struct State(this.input, this.segments, this.buffer, this.depth, this.index + 1)));
}
/* private */ struct State append(struct State this, struct char c){
	return struct State(this.input, this.segments, this.buffer + c, this.depth, this.index);
}
/* public */ Option<struct State> popAndAppend(struct State this){
	return this.popAndAppendToTuple(this).map(this.popAndAppendToTuple(this), /* Tuple::right */);
}
/* public */ Option<char> peek(struct State this){
	if (this.index < this.input.length(this.index < this.input)){
		return Some</*  */>(this.input.charAt(this.input, this.index));
	}
	/* else */{
		return None</*  */>();
	}
}
struct private Joiner(struct Joiner this){
	/* this(""); */
}
/* @Override
        public */ Option<char*> createInitial(struct Joiner this){
	return None</*  */>();
}
/* @Override
        public */ Option<char*> fold(struct Joiner this, Option<char*> current, char* element){
	return Some</*  */>(current.map(current, /* inner -> inner + this */.delimiter + element).orElse(current.map(current, /* inner -> inner + this */.delimiter + element), element));
}
struct public Definition(struct Definition this, char* type, char* name){
	/* this(new None<>(), type, name); */
}
/* @Override
        public */ char* generate(struct Definition this){
	struct var joined = this.beforeType(this).map(this.beforeType(this), /* Main::generatePlaceholder */).map(this.beforeType(this).map(this.beforeType(this), /* Main::generatePlaceholder */), /* inner -> inner + " " */).orElse(this.beforeType(this).map(this.beforeType(this), /* Main::generatePlaceholder */).map(this.beforeType(this).map(this.beforeType(this), /* Main::generatePlaceholder */), /* inner -> inner + " " */), "");
	return /* joined + this */.type() + " " + this.name(/* joined + this */.type() + " " + this);
}
/* @Override
        public */ char* generate(struct Content this){
	return generatePlaceholder(this.input);
}
/* @Override
        public */ char* generate(struct Whitespace implements Defined, Value this){
	return "";
}
/* @Override
        public */ char* generate(struct StringValue this){
	return "\"" + this.value + "\"";
}
/* @Override
        public */ char* generate(struct Symbol this){
	return this.value;
}
/* @Override
        public */ char* generate(struct Invocation this){
	return this.caller.generate() + "(" + generateValueList(this.args) + ")";
}
/* @Override
        public */ char* generate(struct DataAccess this){
	return this.parent.generate() + "." + this.property;
}
/* public static */ void main(struct Main this){
	/* try */{
		struct var source = Paths.get(Paths, ".", "src", "java", "magma", "Main.java");
		struct var target = source.resolveSibling(source, "main.c");
		struct var input = Files.readString(Files, source);
		/* Files.writeString(target, compileRoot(input)); */
	}
	/* catch (IOException e) */{
		/* e.printStackTrace(); */
	}
}
auto compileRoot_local1(auto tuple){
	if (expandables.containsKey(expandables, tuple.left)){
		struct var expandable = expandables.get(expandables, tuple.left);
		return expandable.apply(expandable, tuple.right).orElse(expandable.apply(expandable, tuple.right), "");
	}
	/* else */{
		return "// " + tuple.left + "<" + join(tuple.right, ", ") + ">\n";
	}
}
/* private static */ char* compileRoot(struct Main this, char* input){
	struct var compiled = compileStatements(input, /* Main::compileRootSegment */);
	struct var joinedExpansions = expansions.iter(expansions).map(expansions.iter(expansions), compileRoot_local1).collect(expansions.iter(expansions).map(expansions.iter(expansions), compileRoot_local1), struct Joiner()).orElse(expansions.iter(expansions).map(expansions.iter(expansions), compileRoot_local1).collect(expansions.iter(expansions).map(expansions.iter(expansions), compileRoot_local1), struct Joiner()), "");
	return /* compiled + join(structs) + joinedExpansions + join */(methods);
}
/* private static */ char* join(struct Main this, List<char*> list){
	return join(list, "");
}
/* private static */ char* join(struct Main this, List<char*> list, char* delimiter){
	return list.iter(list).collect(list.iter(list), struct Joiner(delimiter)).orElse(list.iter(list).collect(list.iter(list), struct Joiner(delimiter)), "");
}
/* private static */ char* compileStatements(struct Main this, char* input, char* (*)(char*) compiler){
	return compileAll(input, /* Main::foldStatementChar */, compiler, /* Main::mergeStatements */);
}
/* private static */ char* compileAll(struct Main this, char* input, struct State (*)(struct State, char) folder, char* (*)(char*) compiler, char* (*)(char*, char*) merger){
	return generateAll(merger, parseAll(input, folder, compiler));
}
/* private static */ char* generateAll(struct Main this, char* (*)(char*, char*) merger, List<char*> parsed){
	return parsed.iter(parsed).fold(parsed.iter(parsed), "", merger);
}
/* private static <T> */ List<struct T> parseAll(struct Main this, char* input, struct State (*)(struct State, char) folder, struct T (*)(char*) compiler){
	return divideAll(input, folder).iter(divideAll(input, folder)).map(divideAll(input, folder).iter(divideAll(input, folder)), compiler).collect(divideAll(input, folder).iter(divideAll(input, folder)).map(divideAll(input, folder).iter(divideAll(input, folder)), compiler), ListCollector</*  */>());
}
/* private static */ char* mergeStatements(struct Main this, char* buffer, char* element){
	return /* buffer + element */;
}
/* private static */ List<char*> divideAll(struct Main this, char* input, struct State (*)(struct State, char) folder){
	struct State state = State.fromInput(State, input);
	/* while (true) */{
		struct var maybeNextTuple = state.pop(state);
		if (maybeNextTuple.isEmpty(maybeNextTuple)){
			/* break; */
		}
		struct var nextTuple = maybeNextTuple.orElse(maybeNextTuple, null);
		struct var next = nextTuple.left;
		struct var withoutNext = nextTuple.right;
		/* state  */ = foldSingleQuotes(withoutNext, next).or(foldSingleQuotes(withoutNext, next), /* () -> foldDoubleQuotes */(withoutNext, next)).orElseGet(foldSingleQuotes(withoutNext, next).or(foldSingleQuotes(withoutNext, next), /* () -> foldDoubleQuotes */(withoutNext, next)), /* () -> folder */.apply(/* () -> folder */, withoutNext, next));
	}
	return state.advance(state).segments;
}
/* private static */ Option<struct State> foldDoubleQuotes(struct Main this, struct State withoutNext, struct char c){
	if (/* c != '\"' */){
		return None</*  */>();
	}
	struct var current = withoutNext.append(withoutNext, c);
	/* while (true) */{
		struct var maybeNext = current.popAndAppendToTuple(current);
		if (/* ! */(/* maybeNext instanceof Some */(/* var next */))){
			/* break; */
		}
		/* current  */ = next.right;
		if (next.left == '"'){
			/* break; */
		}
		if (next.left == '\\'){
			/* current  */ = current.popAndAppend(current).orElse(current.popAndAppend(current), current);
		}
	}
	return Some</*  */>(current);
}
/* private static */ Option<struct State> foldSingleQuotes(struct Main this, struct State state, struct char next){
	if (/* next != '\'' */){
		return None</*  */>();
	}
	struct var appended = state.append(state, next);
	return appended.popAndAppendToTuple(appended).flatMap(appended.popAndAppendToTuple(appended), /* maybeSlash -> maybeSlash */.left == '\\' ? maybeSlash.right.popAndAppend() : new Some<>(/* maybeSlash -> maybeSlash */.left == '\\' ? maybeSlash.right, maybeSlash.right)).flatMap(appended.popAndAppendToTuple(appended).flatMap(appended.popAndAppendToTuple(appended), /* maybeSlash -> maybeSlash */.left == '\\' ? maybeSlash.right.popAndAppend() : new Some<>(/* maybeSlash -> maybeSlash */.left == '\\' ? maybeSlash.right, maybeSlash.right)), /* State::popAndAppend */);
}
/* private static */ struct State foldStatementChar(struct Main this, struct State state, struct char c){
	struct var appended = state.append(state, c);
	if (/* c == ';' && appended */.isLevel(/* c == ';' && appended */)){
		return appended.advance(appended);
	}
	if (/* c == '}' && appended */.isShallow(/* c == '}' && appended */)){
		return appended.advance(appended).exit(appended.advance(appended));
	}
	/* if (c == ' */{
		/* ' || */ c = /* = '(') {
            return appended */.enter();
	}
	if (/* c == '}' || c == ')' */){
		return appended.exit(appended);
	}
	return appended;
}
/* private static */ char* compileRootSegment(struct Main this, char* input){
	struct var stripped = input.strip(input);
	if (stripped.isEmpty(stripped)){
		return "";
	}
	if (stripped.startsWith("package ") || stripped.startsWith(stripped.startsWith("package ") || stripped, "import ")){
		return "";
	}
	return compileClass(stripped).orElseGet(compileClass(stripped), /* () -> generatePlaceholder */(stripped));
}
/* private static */ Option<char*> compileClass(struct Main this, char* stripped){
	return compileStructure(stripped, "class ");
}
/* private static */ Option<char*> compileStructure(struct Main this, char* input, char* infix){
	struct var classIndex = input.indexOf(input, infix);
	if (/* classIndex >= 0 */){
		struct var beforeClass = input.substring(input, 0, classIndex).strip(input.substring(input, 0, classIndex));
		struct var afterClass = input.substring(input, /* classIndex + infix */.length(/* classIndex + infix */));
		struct var contentStart = afterClass.indexOf(afterClass, "{");
		if (/* contentStart >= 0 */){
			struct var beforeContent = afterClass.substring(afterClass, 0, contentStart).strip(afterClass.substring(afterClass, 0, contentStart));
			struct var permitsIndex = beforeContent.indexOf(beforeContent, " permits");
			struct var withoutPermits = /* permitsIndex >= 0
                        ? beforeContent */.substring(/* permitsIndex >= 0
                        ? beforeContent */, 0, permitsIndex).strip()
                        : beforeContent;
			struct var paramStart = withoutPermits.indexOf(withoutPermits, "(");
			struct var withEnd = afterClass.substring(afterClass, /* contentStart + "{" */.length(/* contentStart + "{" */)).strip(afterClass.substring(afterClass, /* contentStart + "{" */.length(/* contentStart + "{" */)));
			if (/* paramStart >= 0 */){
				char* withoutParams = withoutPermits.substring(withoutPermits, 0, paramStart).strip(withoutPermits.substring(withoutPermits, 0, paramStart));
				return getString(withoutParams, beforeClass, withEnd);
			}
			/* else */{
				return getString(withoutPermits, beforeClass, withEnd);
			}
		}
	}
	return None</*  */>();
}
/* private static */ Option<char*> getString(struct Main this, char* beforeContent, char* beforeClass, char* withEnd){
	if (/* !withEnd */.endsWith(/* !withEnd */, "}")){
		return None</*  */>();
	}
	struct var content = withEnd.substring(withEnd, 0, withEnd.length() - "}".length(withEnd.length() - "}"));
	struct var strippedBeforeContent = beforeContent.strip(beforeContent);
	if (strippedBeforeContent.endsWith(strippedBeforeContent, ">")){
		struct var withoutEnd = strippedBeforeContent.substring(strippedBeforeContent, 0, strippedBeforeContent.length() - ">".length(strippedBeforeContent.length() - ">"));
		struct var typeParamStart = withoutEnd.indexOf(withoutEnd, "<");
		if (/* typeParamStart >= 0 */){
			struct var name = withoutEnd.substring(withoutEnd, 0, typeParamStart).strip(withoutEnd.substring(withoutEnd, 0, typeParamStart));
			struct var substring = withoutEnd.substring(withoutEnd, /* typeParamStart + "<" */.length(/* typeParamStart + "<" */));
			struct var typeParameters = listFromArray(substring.split(substring, Pattern.quote(Pattern, ",")));
			return assembleStructure(typeParameters, name, beforeClass, content);
		}
	}
	return assembleStructure(listEmpty(), strippedBeforeContent, beforeClass, content);
}
/* private static */ Option<char*> assembleStructure(struct Main this, List<char*> typeParams, char* name, char* beforeClass, char* content){
	if (/* !typeParams */.isEmpty(/* !typeParams */)){
		/* expandables.put(name, typeArgs */ /* -> { */ typeParameters = /* typeParams;
                typeArguments = typeArgs;

                var newName = name + "<" + join(typeArgs, ", ") + ">";
                return generateStructure */(newName, beforeClass, /* content);
            } */);
		return Some</*  */>("");
	}
	return generateStructure(name, beforeClass, content);
}
/* private static */ Option<char*> generateStructure(struct Main this, char* name, char* beforeClass, char* content){
	/* structNames  */ = structNames.addLast(structNames, name);
	struct var compiled = compileStatements(content, /* Main::compileClassSegment */);
	/* structNames  */ = structNames.removeLast(structNames);
	struct var generated = /* generatePlaceholder(beforeClass) + "struct " + name + " {" + compiled + "\n};\n" */;
	/* structs.addLast(generated); */
	return Some</*  */>("");
}
/* private static */ char* compileClassSegment(struct Main this, char* input){
	struct var stripped = input.strip(input);
	if (stripped.isEmpty(stripped)){
		return "";
	}
	return compileStructure(stripped, "record ").or(compileStructure(stripped, "record "), /* () -> compileStructure */(stripped, "interface ")).or(compileStructure(stripped, "record ").or(compileStructure(stripped, "record "), /* () -> compileStructure */(stripped, "interface ")), /* () -> compileClass */(stripped)).or(compileStructure(stripped, "record ").or(compileStructure(stripped, "record "), /* () -> compileStructure */(stripped, "interface ")).or(compileStructure(stripped, "record ").or(compileStructure(stripped, "record "), /* () -> compileStructure */(stripped, "interface ")), /* () -> compileClass */(stripped)), /* () -> compileMethod */(stripped)).or(compileStructure(stripped, "record ").or(compileStructure(stripped, "record "), /* () -> compileStructure */(stripped, "interface ")).or(compileStructure(stripped, "record ").or(compileStructure(stripped, "record "), /* () -> compileStructure */(stripped, "interface ")), /* () -> compileClass */(stripped)).or(compileStructure(stripped, "record ").or(compileStructure(stripped, "record "), /* () -> compileStructure */(stripped, "interface ")).or(compileStructure(stripped, "record ").or(compileStructure(stripped, "record "), /* () -> compileStructure */(stripped, "interface ")), /* () -> compileClass */(stripped)), /* () -> compileMethod */(stripped)), /* () -> compileDefinitionStatement */(stripped)).orElseGet(compileStructure(stripped, "record ").or(compileStructure(stripped, "record "), /* () -> compileStructure */(stripped, "interface ")).or(compileStructure(stripped, "record ").or(compileStructure(stripped, "record "), /* () -> compileStructure */(stripped, "interface ")), /* () -> compileClass */(stripped)).or(compileStructure(stripped, "record ").or(compileStructure(stripped, "record "), /* () -> compileStructure */(stripped, "interface ")).or(compileStructure(stripped, "record ").or(compileStructure(stripped, "record "), /* () -> compileStructure */(stripped, "interface ")), /* () -> compileClass */(stripped)), /* () -> compileMethod */(stripped)).or(compileStructure(stripped, "record ").or(compileStructure(stripped, "record "), /* () -> compileStructure */(stripped, "interface ")).or(compileStructure(stripped, "record ").or(compileStructure(stripped, "record "), /* () -> compileStructure */(stripped, "interface ")), /* () -> compileClass */(stripped)).or(compileStructure(stripped, "record ").or(compileStructure(stripped, "record "), /* () -> compileStructure */(stripped, "interface ")).or(compileStructure(stripped, "record ").or(compileStructure(stripped, "record "), /* () -> compileStructure */(stripped, "interface ")), /* () -> compileClass */(stripped)), /* () -> compileMethod */(stripped)), /* () -> compileDefinitionStatement */(stripped)), /* () -> generatePlaceholder */(stripped));
}
/* private static */ Option<char*> compileDefinitionStatement(struct Main this, char* input){
	struct var stripped = input.strip(input);
	if (stripped.endsWith(stripped, ";")){
		struct var withoutEnd = stripped.substring(stripped, 0, stripped.length() - ";".length(stripped.length() - ";"));
		return Some</*  */>("\n\t" + compileDefinitionOrPlaceholder(withoutEnd) + ";");
	}
	return None</*  */>();
}
/* private static */ Option<char*> compileMethod(struct Main this, char* stripped){
	struct var paramStart = stripped.indexOf(stripped, "(");
	if (/* paramStart < 0 */){
		return None</*  */>();
	}
	struct var inputDefinition = stripped.substring(stripped, 0, paramStart);
	struct var defined = parseDefinitionOrPlaceholder(inputDefinition);
	if (/* defined instanceof Definition definition */){
		/* functionName  */ = definition.name;
	}
	struct var outputDefinition = defined.generate(defined);
	struct var afterParams = stripped.substring(stripped, /* paramStart + "(" */.length(/* paramStart + "(" */));
	struct var paramEnd = afterParams.indexOf(afterParams, ")");
	if (/* paramEnd < 0 */){
		return None</*  */>();
	}
	struct var params = afterParams.substring(afterParams, 0, paramEnd);
	struct var withoutParams = afterParams.substring(afterParams, /* paramEnd + ")" */.length(/* paramEnd + ")" */));
	struct var withBraces = withoutParams.strip(withoutParams);
	/* if (!withBraces.startsWith(" */{
		/* ") || !withBraces.endsWith("}")) {
            return new Some<>(""); */
	}
	struct var content = withBraces.substring(withBraces, 1, withBraces.length() - 1);
	struct var newParams = parseValues(params, /* Main::parseParameter */).iter(parseValues(params, /* Main::parseParameter */)).filter(parseValues(params, /* Main::parseParameter */).iter(parseValues(params, /* Main::parseParameter */)), /* parameter -> ! */(/* parameter instanceof Whitespace */)).collect(parseValues(params, /* Main::parseParameter */).iter(parseValues(params, /* Main::parseParameter */)).filter(parseValues(params, /* Main::parseParameter */).iter(parseValues(params, /* Main::parseParameter */)), /* parameter -> ! */(/* parameter instanceof Whitespace */)), ListCollector</*  */>());
	struct var copy = Lists.<Defined>listEmpty(Lists).addLast(Lists.<Defined>listEmpty(Lists), struct Definition(/* "struct " + structNames */.last(/* "struct " + structNames */), "this")).addAll(Lists.<Defined>listEmpty(Lists).addLast(Lists.<Defined>listEmpty(Lists), struct Definition(/* "struct " + structNames */.last(/* "struct " + structNames */), "this")), newParams);
	struct var outputParams = generateValueList(copy);
	return assembleMethod(outputDefinition, outputParams, content);
}
/* private static <T extends Node> */ char* generateValueList(struct Main this, List<struct T> copy){
	return copy.iter(copy).map(copy.iter(copy), /* Node::generate */).collect(copy.iter(copy).map(copy.iter(copy), /* Node::generate */), struct Joiner(", ")).orElse(copy.iter(copy).map(copy.iter(copy), /* Node::generate */).collect(copy.iter(copy).map(copy.iter(copy), /* Node::generate */), struct Joiner(", ")), "");
}
/* private static */ Some<char*> assembleMethod(struct Main this, char* definition, char* outputParams, char* content){
	struct var generated = /* definition + "(" + outputParams + "){" + compileStatements(content, input -> compileFunctionSegment(input, 1)) + "\n}\n" */;
	/* methods.addLast(generated); */
	return Some</*  */>("");
}
/* private static */ struct Defined parseParameter(struct Main this, char* input){
	return parseWhitespace(input).<Defined>map(parseWhitespace(input), /* value -> value */).or(parseWhitespace(input).<Defined>map(parseWhitespace(input), /* value -> value */), /* () -> parseDefinition */(input).map(/* () -> parseDefinition */(input), /* value -> value */)).orElseGet(parseWhitespace(input).<Defined>map(parseWhitespace(input), /* value -> value */).or(parseWhitespace(input).<Defined>map(parseWhitespace(input), /* value -> value */), /* () -> parseDefinition */(input).map(/* () -> parseDefinition */(input), /* value -> value */)), /* () -> new Content */(input));
}
/* private static */ Option<struct Whitespace> parseWhitespace(struct Main this, char* input){
	if (input.isBlank(input)){
		return Some</*  */>(struct Whitespace());
	}
	/* else */{
		return None</*  */>();
	}
}
/* private static */ char* compileFunctionSegment(struct Main this, char* input, int depth){
	struct var stripped = input.strip(input);
	if (stripped.isEmpty(stripped)){
		return "";
	}
	struct var indent = "\n" + "\t".repeat("\n" + "\t", depth);
	if (stripped.endsWith(stripped, ";")){
		struct var withoutEnd = stripped.substring(stripped, 0, stripped.length() - ";".length(stripped.length() - ";")).strip(stripped.substring(stripped, 0, stripped.length() - ";".length(stripped.length() - ";")));
		struct var maybeStatementValue = compileStatementValue(withoutEnd);
		if (/* maybeStatementValue instanceof Some */(/* var statementValue */)){
			return /* indent + statementValue + ";" */;
		}
	}
	if (stripped.endsWith(stripped, "}")){
		struct var withoutEnd = stripped.substring(stripped, 0, stripped.length() - "}".length(stripped.length() - "}"));
		struct var contentStart = withoutEnd.indexOf(withoutEnd, "{");
		if (/* contentStart >= 0 */){
			struct var beforeBlock = withoutEnd.substring(withoutEnd, 0, contentStart);
			struct var content = withoutEnd.substring(withoutEnd, /* contentStart + "{" */.length(/* contentStart + "{" */));
			struct var outputContent = compileStatements(content, /* input1 -> compileFunctionSegment */(/* input1 */, /* depth + 1 */));
			return /* indent + compileBeforeBlock(beforeBlock) + "{" + outputContent + indent + "}" */;
		}
	}
	return /* indent + generatePlaceholder */(stripped);
}
/* private static */ Option<char*> compileStatementValue(struct Main this, char* input){
	if (input.startsWith(input, "return ")){
		struct var value = input.substring(input, "return ".length("return "));
		return Some</*  */>(/* "return " + compileValue */(value));
	}
	struct var valueSeparator = input.indexOf(input, "=");
	if (/* valueSeparator >= 0 */){
		struct var definition = input.substring(input, 0, valueSeparator);
		struct var value = input.substring(input, /* valueSeparator + "=" */.length(/* valueSeparator + "=" */));
		return Some</*  */>(parseDefinitionOrPlaceholder(definition).generate() + " = " + compileValue(parseDefinitionOrPlaceholder(definition), value));
	}
	return None</*  */>();
}
/* private static */ char* compileBeforeBlock(struct Main this, char* input){
	struct var stripped = input.strip(input);
	if (stripped.startsWith(stripped, "if")){
		struct var withoutPrefix = stripped.substring(stripped, "if".length("if")).strip(stripped.substring(stripped, "if".length("if")));
		if (withoutPrefix.startsWith("(") && withoutPrefix.endsWith(withoutPrefix.startsWith("(") && withoutPrefix, ")")){
			struct var condition = withoutPrefix.substring(withoutPrefix, 1, withoutPrefix.length() - 1);
			return "if (" + compileValue(condition) + ")";
		}
	}
	return generatePlaceholder(stripped);
}
/* private static */ char* compileValue(struct Main this, char* input){
	return parseValue(input).generate(parseValue(input));
}
/* private static */ struct Value parseValue(struct Main this, char* input){
	struct var stripped = input.strip(input);
	if (stripped.isEmpty(stripped)){
		return struct Whitespace();
	}
	if (stripped.startsWith("\"") && stripped.endsWith(stripped.startsWith("\"") && stripped, "\"")){
		return struct StringValue(stripped.substring(stripped, 1, stripped.length() - 1));
	}
	if (stripped.endsWith(stripped, ")")){
		struct var withoutEnd = stripped.substring(stripped, 0, stripped.length() - ")".length(stripped.length() - ")")).strip(stripped.substring(stripped, 0, stripped.length() - ")".length(stripped.length() - ")")));
		struct var divisions = divideAll(withoutEnd, /* Main::foldInvokableStart */);
		if (divisions.size() >= 2){
			struct var joined = join(divisions.subList(divisions, 0, divisions.size() - 1), "");
			struct var caller = joined.substring(joined, 0, joined.length() - ")".length(joined.length() - ")"));
			struct var arguments = divisions.last(divisions);
			/* Value parsedCaller; */
			if (caller.startsWith(caller, "new ")){
				/* parsedCaller  */ = struct Symbol(compileType(caller.substring(caller, "new ".length("new "))));
			}
			/* else */{
				/* parsedCaller  */ = parseValue(caller);
			}
			struct var parsedArgs = parseValues(arguments, /* Main::parseValue */).iter(parseValues(arguments, /* Main::parseValue */)).filter(parseValues(arguments, /* Main::parseValue */).iter(parseValues(arguments, /* Main::parseValue */)), /* value -> ! */(/* value instanceof Whitespace */)).collect(parseValues(arguments, /* Main::parseValue */).iter(parseValues(arguments, /* Main::parseValue */)).filter(parseValues(arguments, /* Main::parseValue */).iter(parseValues(arguments, /* Main::parseValue */)), /* value -> ! */(/* value instanceof Whitespace */)), ListCollector</*  */>());
			/* List<Value> newArgs; */
			if (/* parsedCaller instanceof DataAccess */(/* var parent */, /* _ */)){
				/* newArgs  */ = Lists.<Value>listEmpty(Lists).addLast(Lists.<Value>listEmpty(Lists), parent).addAll(Lists.<Value>listEmpty(Lists).addLast(Lists.<Value>listEmpty(Lists), parent), parsedArgs);
			}
			/* else */{
				/* newArgs  */ = parsedArgs;
			}
			return struct Invocation(parsedCaller, newArgs);
		}
	}
	if (isSymbol(stripped)){
		return struct Symbol(stripped);
	}
	if (isNumber(stripped)){
		return struct Symbol(stripped);
	}
	struct var arrowIndex = stripped.indexOf(stripped, "->");
	if (/* arrowIndex >= 0 */){
		struct var beforeArrow = stripped.substring(stripped, 0, arrowIndex).strip(stripped.substring(stripped, 0, arrowIndex));
		struct var afterArrow = stripped.substring(stripped, /* arrowIndex + "->" */.length(/* arrowIndex + "->" */)).strip(stripped.substring(stripped, /* arrowIndex + "->" */.length(/* arrowIndex + "->" */)));
		/* if (afterArrow.startsWith(" */{
			/* ") && afterArrow.endsWith("}")) {
                var */ content = afterArrow.substring(1, afterArrow.length() - 1);

                var name = functionName + "_local" + functionLocalCounter;
                functionLocalCounter++;

                assembleMethod("auto " + name, "auto " + beforeArrow, content);
                return new Symbol(afterArrow.substring(1, afterArrow, name);
		}
	}
	struct var separator = stripped.lastIndexOf(stripped, ".");
	if (/* separator >= 0 */){
		struct var value = stripped.substring(stripped, 0, separator);
		struct var property = stripped.substring(stripped, /* separator + " */.".length(/* separator + " */.")).strip(stripped.substring(stripped, /* separator + " */.".length(/* separator + " */.")));
		return struct DataAccess(parseValue(value), property);
	}
	return struct Content(stripped);
}
/* private static */ struct State foldInvokableStart(struct Main this, struct State state, char c){
	struct var appended = state.append(state, c);
	if (/* c == '(' */){
		struct var maybeAdvanced = appended.isLevel() ? appended.advance() : appended;
		return maybeAdvanced.enter(maybeAdvanced);
	}
	if (/* c == ')' */){
		return appended.exit(appended);
	}
	return appended;
}
/* private static */ int isNumber(struct Main this, char* input){
	if (input.isEmpty(input)){
		return false;
	}
	/* for (var i = 0; i < input.length(); i++) */{
		struct var c = input.charAt(input, i);
		if (Character.isDigit(Character, c)){
			/* continue; */
		}
		return false;
	}
	return true;
}
/* private static */ char* compileDefinitionOrPlaceholder(struct Main this, char* input){
	return parseDefinitionOrPlaceholder(input).generate(parseDefinitionOrPlaceholder(input));
}
/* private static */ struct Defined parseDefinitionOrPlaceholder(struct Main this, char* input){
	return parseDefinition(input).<Defined>map(parseDefinition(input), /* value -> value */).orElseGet(parseDefinition(input).<Defined>map(parseDefinition(input), /* value -> value */), /* () -> new Content */(input));
}
/* private static */ Option<struct Definition> parseDefinition(struct Main this, char* input){
	struct var stripped = input.strip(input);
	struct var nameSeparator = stripped.lastIndexOf(stripped, " ");
	if (/* nameSeparator < 0 */){
		return None</*  */>();
	}
	struct var beforeName = stripped.substring(stripped, 0, nameSeparator);
	struct var name = stripped.substring(stripped, /* nameSeparator + " " */.length(/* nameSeparator + " " */));
	if (/* !isSymbol */(name)){
		return None</*  */>();
	}
	struct var divisions = divideAll(beforeName, /* Main::foldByTypeSeparator */);
	if (divisions.size() == 1){
		return Some</*  */>(struct Definition(None</*  */>(), compileType(beforeName), name));
	}
	struct var beforeType = join(divisions.subList(divisions, 0, divisions.size() - 1), " ");
	struct var type = divisions.last(divisions);
	return Some</*  */>(struct Definition(Some</*  */>(beforeType), compileType(type), name));
}
/* private static */ struct State foldByTypeSeparator(struct Main this, struct State state, struct char c){
	if (/* c == ' ' && state */.isLevel(/* c == ' ' && state */)){
		return state.advance(state);
	}
	struct var appended = state.append(state, c);
	if (/* c == '<' */){
		return appended.enter(appended);
	}
	if (/* c == '>' */){
		return appended.exit(appended);
	}
	return appended;
}
/* private static */ char* compileType(struct Main this, char* input){
	struct var stripped = input.strip(input);
	struct var maybeTypeParamIndex = typeParameters.indexOf(typeParameters, stripped);
	if (maybeTypeParamIndex.isPresent(maybeTypeParamIndex)){
		struct var typeParamIndex = maybeTypeParamIndex.orElse(maybeTypeParamIndex, null);
		return typeArguments.get(typeArguments, typeParamIndex);
	}
	/* switch (stripped) */{
		/* case "int", "boolean" -> */{
			return "int";
		}
		/* case "Character" -> */{
			return "char";
		}
		/* case "void" -> */{
			return "void";
		}
		/* case "String" -> */{
			return "char*";
		}
	}
	if (stripped.endsWith(stripped, ">")){
		struct var withoutEnd = stripped.substring(stripped, 0, stripped.length() - ">".length(stripped.length() - ">"));
		struct var index = withoutEnd.indexOf(withoutEnd, "<");
		if (/* index >= 0 */){
			struct var base = withoutEnd.substring(withoutEnd, 0, index).strip(withoutEnd.substring(withoutEnd, 0, index));
			struct var substring = withoutEnd.substring(withoutEnd, /* index + "<" */.length(/* index + "<" */));
			struct var parsed = parseValues(substring, /* Main::compileType */);
			if (base.equals(base, "Function")){
				/* var arg0  */ = parsed.get(parsed, 0);
				struct var returns = parsed.get(parsed, 1);
				return /* returns + " (*)(" + arg0 + ")" */;
			}
			if (base.equals(base, "BiFunction")){
				/* var arg0  */ = parsed.get(parsed, 0);
				/* var arg1  */ = parsed.get(parsed, 1);
				struct var returns = parsed.get(parsed, 2);
				return /* returns + " (*)(" + arg0 + ", " + arg1 + ")" */;
			}
			if (/* !expansions */.contains(/* !expansions */, Tuple</*  */>(base, parsed))){
				/* expansions  */ = expansions.addLast(expansions, Tuple</*  */>(base, parsed));
			}
			return /* base + "<" + generateValues(parsed) + ">" */;
		}
	}
	if (isSymbol(stripped)){
		return /* "struct " + stripped */;
	}
	return generatePlaceholder(stripped);
}
/* private static */ char* generateValues(struct Main this, List<char*> values){
	return generateAll(/* Main::mergeValues */, values);
}
/* private static <T> */ List<struct T> parseValues(struct Main this, char* input, struct T (*)(char*) compiler){
	return parseAll(input, /* Main::foldValueChar */, compiler);
}
/* private static */ char* mergeValues(struct Main this, char* builder, char* element){
	if (builder.isEmpty(builder)){
		return /* builder + element */;
	}
	return /* builder + ", " + element */;
}
/* private static */ struct State foldValueChar(struct Main this, struct State state, struct char c){
	if (/* c == ',' && state */.isLevel(/* c == ',' && state */)){
		return state.advance(state);
	}
	struct var appended = state.append(state, c);
	if (/* c == '-' */){
		if (appended.peek() instanceof Some(appended, /* var maybeArrow */)){
			if (/* maybeArrow == '>' */){
				return appended.popAndAppend(appended).orElse(appended.popAndAppend(appended), appended);
			}
		}
	}
	if (/* c == '<' || c == '(' */){
		return appended.enter(appended);
	}
	if (/* c == '>' || c == ')' */){
		return appended.exit(appended);
	}
	return appended;
}
/* private static */ int isSymbol(struct Main this, char* input){
	struct var stripped = input.strip(input);
	if (stripped.isEmpty(stripped)){
		return false;
	}
	/* for (var i = 0; i < stripped.length(); i++) */{
		struct var c = stripped.charAt(stripped, i);
		if (Character.isLetter(Character, c)){
			/* continue; */
		}
		return false;
	}
	return true;
}
/* private static */ char* generatePlaceholder(struct Main this, char* input){
	return "/* " + input + " */";
}
/* @Override
        public <R> */ Option<struct R> map(struct None</*  */> this, struct R (*)(/*  */) mapper){
	return None</*  */>();
}
/* @Override
        public */ int isPresent(struct None</*  */> this){
	return false;
}
/* @Override
        public */ /*  */ orElse(struct None</*  */> this, /*  */ other){
	return other;
}
/* @Override
        public */ int isEmpty(struct None</*  */> this){
	return true;
}
/* @Override
        public */ /*  */ orElseGet(struct None</*  */> this, Supplier</*  */> supplier){
	return supplier.get(supplier);
}
/* @Override
        public <R> */ Option<struct R> flatMap(struct None</*  */> this, Option<struct R> (*)(/*  */) mapper){
	return None</*  */>();
}
/* @Override
        public */ Option</*  */> or(struct None</*  */> this, Supplier<Option</*  */>> supplier){
	return supplier.get(supplier);
}
/* @Override
        public <R> */ Option<struct R> map(struct Some</*  */> this, struct R (*)(/*  */) mapper){
	return Some</*  */>(mapper.apply(mapper, this.value));
}
/* @Override
        public */ int isPresent(struct Some</*  */> this){
	return true;
}
/* @Override
        public */ /*  */ orElse(struct Some</*  */> this, /*  */ other){
	return this.value;
}
/* @Override
        public */ int isEmpty(struct Some</*  */> this){
	return false;
}
/* @Override
        public */ /*  */ orElseGet(struct Some</*  */> this, Supplier</*  */> supplier){
	return this.value;
}
/* @Override
        public <R> */ Option<struct R> flatMap(struct Some</*  */> this, Option<struct R> (*)(/*  */) mapper){
	return mapper.apply(mapper, this.value);
}
/* @Override
        public */ Option</*  */> or(struct Some</*  */> this, Supplier<Option</*  */>> supplier){
	return this;
}
/* @Override
        public */ List<struct T> createInitial(struct ListCollector</*  */> this){
	return listEmpty();
}
/* @Override
        public */ List<struct T> fold(struct ListCollector</*  */> this, List<struct T> current, struct T element){
	return current.addLast(current, element);
}
/* @Override
        public <R> */ Option<struct R> map(struct Some<char*> this, struct R (*)(char*) mapper){
	return Some</*  */>(mapper.apply(mapper, this.value));
}
/* @Override
        public */ int isPresent(struct Some<char*> this){
	return true;
}
/* @Override
        public */ char* orElse(struct Some<char*> this, char* other){
	return this.value;
}
/* @Override
        public */ int isEmpty(struct Some<char*> this){
	return false;
}
/* @Override
        public */ char* orElseGet(struct Some<char*> this, Supplier<char*> supplier){
	return this.value;
}
/* @Override
        public <R> */ Option<struct R> flatMap(struct Some<char*> this, Option<struct R> (*)(char*) mapper){
	return mapper.apply(mapper, this.value);
}
/* @Override
        public */ Option<char*> or(struct Some<char*> this, Supplier<Option<char*>> supplier){
	return this;
}
