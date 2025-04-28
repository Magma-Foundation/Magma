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
	/* var poppedChar = tuple.left; */
	/* var poppedState = tuple.right; */
	/* var appended = poppedState.append(poppedChar); */
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
	/* if (this.index >= this.input.length())  */{
	return None</*  */>();
	}
	/* var escaped = this.input.charAt(this.index); */
	return Some</*  */>(Tuple<char, struct State>(escaped, struct State(this.input, this.segments, this.buffer, this.depth, this.index + 1)));
}
/* private */ struct State append(struct State this, struct char c){
	return struct State(this.input, this.segments, this.buffer + c, this.depth, this.index);
}
/* public */ Option<struct State> popAndAppend(struct State this){
	return this.popAndAppendToTuple(this).map(this.popAndAppendToTuple(this), /* Tuple::right */);
}
/* public */ Option<char> peek(struct State this){
	/* if (this.index < this.input.length())  */{
	return Some</*  */>(this.input.charAt(this.input, this.index));
	}
	/* else  */{
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
	/* var joined = this.beforeType().map(Main::generatePlaceholder).map(inner -> inner + " ").orElse(""); */
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
	/* try  */{
	/* var source = Paths.get(".", "src", "java", "magma", "Main.java"); */
	/* var target = source.resolveSibling("main.c"); */
	/* var input = Files.readString(source); */
	/* Files.writeString(target, compileRoot(input)); */
	}
	/* catch (IOException e)  */{
	/* e.printStackTrace(); */
	}
}
/* private static */ char* compileRoot(struct Main this, char* input){
	/* var compiled = compileStatements(input, Main::compileRootSegment); */
	/* var joinedExpansions = expansions.iter()
                .map(tuple -> {
                    if (expandables.containsKey(tuple.left)) {
                        var expandable = expandables.get(tuple.left);
                        return expandable.apply(tuple.right).orElse("");
                    }
                    else {
                        return "// " + tuple.left + "<" + join(tuple.right, ", ") + ">\n";
                    }
                })
                .collect(new Joiner())
                .orElse(""); */
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
	/* State state = State.fromInput(input); */
	/* while (true)  */{
	/* var maybeNextTuple = state.pop(); */
	/* if (maybeNextTuple.isEmpty())  */{
	/* break; */
	}
	/* var nextTuple = maybeNextTuple.orElse(null); */
	/* var next = nextTuple.left; */
	/* var withoutNext = nextTuple.right; */
	/* state = foldSingleQuotes(withoutNext, next)
                    .or(() -> foldDoubleQuotes(withoutNext, next))
                    .orElseGet(() -> folder.apply(withoutNext, next)); */
	}
	return state.advance(state).segments;
}
/* private static */ Option<struct State> foldDoubleQuotes(struct Main this, struct State withoutNext, struct char c){
	/* if (c != '\"')  */{
	return None</*  */>();
	}
	/* var current = withoutNext.append(c); */
	/* while (true)  */{
	/* var maybeNext = current.popAndAppendToTuple(); */
	/* if (!(maybeNext instanceof Some(var next)))  */{
	/* break; */
	}
	/* current = next.right; */
	/* if (next.left == '"')  */{
	/* break; */
	}
	/* if (next.left == '\\')  */{
	/* current = current.popAndAppend().orElse(current); */
	}
	}
	return Some</*  */>(current);
}
/* private static */ Option<struct State> foldSingleQuotes(struct Main this, struct State state, struct char next){
	/* if (next != '\'')  */{
	return None</*  */>();
	}
	/* var appended = state.append(next); */
	return appended.popAndAppendToTuple(appended).flatMap(appended.popAndAppendToTuple(appended), /* maybeSlash -> maybeSlash */.left == '\\' ? maybeSlash.right.popAndAppend() : new Some<>(/* maybeSlash -> maybeSlash */.left == '\\' ? maybeSlash.right, maybeSlash.right)).flatMap(appended.popAndAppendToTuple(appended).flatMap(appended.popAndAppendToTuple(appended), /* maybeSlash -> maybeSlash */.left == '\\' ? maybeSlash.right.popAndAppend() : new Some<>(/* maybeSlash -> maybeSlash */.left == '\\' ? maybeSlash.right, maybeSlash.right)), /* State::popAndAppend */);
}
/* private static */ struct State foldStatementChar(struct Main this, struct State state, struct char c){
	/* var appended = state.append(c); */
	/* if (c == ';' && appended.isLevel())  */{
	return appended.advance(appended);
	}
	/* if (c == '}' && appended.isShallow())  */{
	return appended.advance(appended).exit(appended.advance(appended));
	}
	/* if (c == ' */{
	/* ' || c == '(') {
            return appended.enter(); */
	}
	/* if (c == '}' || c == ')')  */{
	return appended.exit(appended);
	}
	return appended;
}
/* private static */ char* compileRootSegment(struct Main this, char* input){
	/* var stripped = input.strip(); */
	/* if (stripped.isEmpty())  */{
	return "";
	}
	/* if (stripped.startsWith("package ") || stripped.startsWith("import "))  */{
	return "";
	}
	return compileClass(stripped).orElseGet(compileClass(stripped), /* () -> generatePlaceholder */(stripped));
}
/* private static */ Option<char*> compileClass(struct Main this, char* stripped){
	return compileStructure(stripped, "class ");
}
/* private static */ Option<char*> compileStructure(struct Main this, char* input, char* infix){
	/* var classIndex = input.indexOf(infix); */
	/* if (classIndex >= 0)  */{
	/* var beforeClass = input.substring(0, classIndex).strip(); */
	/* var afterClass = input.substring(classIndex + infix.length()); */
	/* var contentStart = afterClass.indexOf("{"); */
	/* if (contentStart >= 0)  */{
	/* var beforeContent = afterClass.substring(0, contentStart).strip(); */
	/* var permitsIndex = beforeContent.indexOf(" permits"); */
	/* var withoutPermits = permitsIndex >= 0
                        ? beforeContent.substring(0, permitsIndex).strip()
                        : beforeContent; */
	/* var paramStart = withoutPermits.indexOf("("); */
	/* var withEnd = afterClass.substring(contentStart + "{".length()).strip(); */
	/* if (paramStart >= 0)  */{
	/* String withoutParams = withoutPermits.substring(0, paramStart).strip(); */
	return getString(withoutParams, beforeClass, withEnd);
	}
	/* else  */{
	return getString(withoutPermits, beforeClass, withEnd);
	}
	}
	}
	return None</*  */>();
}
/* private static */ Option<char*> getString(struct Main this, char* beforeContent, char* beforeClass, char* withEnd){
	/* if (!withEnd.endsWith("}"))  */{
	return None</*  */>();
	}
	/* var content = withEnd.substring(0, withEnd.length() - "}".length()); */
	/* var strippedBeforeContent = beforeContent.strip(); */
	/* if (strippedBeforeContent.endsWith(">"))  */{
	/* var withoutEnd = strippedBeforeContent.substring(0, strippedBeforeContent.length() - ">".length()); */
	/* var typeParamStart = withoutEnd.indexOf("<"); */
	/* if (typeParamStart >= 0)  */{
	/* var name = withoutEnd.substring(0, typeParamStart).strip(); */
	/* var substring = withoutEnd.substring(typeParamStart + "<".length()); */
	/* var typeParameters = listFromArray(substring.split(Pattern.quote(","))); */
	return assembleStructure(typeParameters, name, beforeClass, content);
	}
	}
	return assembleStructure(listEmpty(), strippedBeforeContent, beforeClass, content);
}
/* private static */ Option<char*> assembleStructure(struct Main this, List<char*> typeParams, char* name, char* beforeClass, char* content){
	/* if (!typeParams.isEmpty())  */{
	/* expandables.put(name, typeArgs -> {
                typeParameters = typeParams;
                typeArguments = typeArgs;

                var newName = name + "<" + join(typeArgs, ", ") + ">";
                return generateStructure(newName, beforeClass, content);
            }); */
	return Some</*  */>("");
	}
	return generateStructure(name, beforeClass, content);
}
/* private static */ Option<char*> generateStructure(struct Main this, char* name, char* beforeClass, char* content){
	/* structNames = structNames.addLast(name); */
	/* var compiled = compileStatements(content, Main::compileClassSegment); */
	/* structNames = structNames.removeLast(); */
	/* var generated = generatePlaceholder(beforeClass) + "struct " + name + " {" + compiled + "\n};\n"; */
	/* structs.addLast(generated); */
	return Some</*  */>("");
}
/* private static */ char* compileClassSegment(struct Main this, char* input){
	/* var stripped = input.strip(); */
	/* if (stripped.isEmpty())  */{
	return "";
	}
	return compileStructure(stripped, "record ").or(compileStructure(stripped, "record "), /* () -> compileStructure */(stripped, "interface ")).or(compileStructure(stripped, "record ").or(compileStructure(stripped, "record "), /* () -> compileStructure */(stripped, "interface ")), /* () -> compileClass */(stripped)).or(compileStructure(stripped, "record ").or(compileStructure(stripped, "record "), /* () -> compileStructure */(stripped, "interface ")).or(compileStructure(stripped, "record ").or(compileStructure(stripped, "record "), /* () -> compileStructure */(stripped, "interface ")), /* () -> compileClass */(stripped)), /* () -> compileMethod */(stripped)).or(compileStructure(stripped, "record ").or(compileStructure(stripped, "record "), /* () -> compileStructure */(stripped, "interface ")).or(compileStructure(stripped, "record ").or(compileStructure(stripped, "record "), /* () -> compileStructure */(stripped, "interface ")), /* () -> compileClass */(stripped)).or(compileStructure(stripped, "record ").or(compileStructure(stripped, "record "), /* () -> compileStructure */(stripped, "interface ")).or(compileStructure(stripped, "record ").or(compileStructure(stripped, "record "), /* () -> compileStructure */(stripped, "interface ")), /* () -> compileClass */(stripped)), /* () -> compileMethod */(stripped)), /* () -> compileDefinitionStatement */(stripped)).orElseGet(compileStructure(stripped, "record ").or(compileStructure(stripped, "record "), /* () -> compileStructure */(stripped, "interface ")).or(compileStructure(stripped, "record ").or(compileStructure(stripped, "record "), /* () -> compileStructure */(stripped, "interface ")), /* () -> compileClass */(stripped)).or(compileStructure(stripped, "record ").or(compileStructure(stripped, "record "), /* () -> compileStructure */(stripped, "interface ")).or(compileStructure(stripped, "record ").or(compileStructure(stripped, "record "), /* () -> compileStructure */(stripped, "interface ")), /* () -> compileClass */(stripped)), /* () -> compileMethod */(stripped)).or(compileStructure(stripped, "record ").or(compileStructure(stripped, "record "), /* () -> compileStructure */(stripped, "interface ")).or(compileStructure(stripped, "record ").or(compileStructure(stripped, "record "), /* () -> compileStructure */(stripped, "interface ")), /* () -> compileClass */(stripped)).or(compileStructure(stripped, "record ").or(compileStructure(stripped, "record "), /* () -> compileStructure */(stripped, "interface ")).or(compileStructure(stripped, "record ").or(compileStructure(stripped, "record "), /* () -> compileStructure */(stripped, "interface ")), /* () -> compileClass */(stripped)), /* () -> compileMethod */(stripped)), /* () -> compileDefinitionStatement */(stripped)), /* () -> generatePlaceholder */(stripped));
}
/* private static */ Option<char*> compileDefinitionStatement(struct Main this, char* input){
	/* var stripped = input.strip(); */
	/* if (stripped.endsWith(";"))  */{
	/* var withoutEnd = stripped.substring(0, stripped.length() - ";".length()); */
	return Some</*  */>("\n\t" + compileDefinitionOrPlaceholder(withoutEnd) + ";");
	}
	return None</*  */>();
}
/* private static */ Option<char*> compileMethod(struct Main this, char* stripped){
	/* var paramStart = stripped.indexOf("("); */
	/* if (paramStart < 0)  */{
	return None</*  */>();
	}
	/* var inputDefinition = stripped.substring(0, paramStart); */
	/* var defined = parseDefinitionOrPlaceholder(inputDefinition); */
	/* if (defined instanceof Definition definition)  */{
	/* functionName = definition.name; */
	}
	/* var outputDefinition = defined.generate(); */
	/* var afterParams = stripped.substring(paramStart + "(".length()); */
	/* var paramEnd = afterParams.indexOf(")"); */
	/* if (paramEnd < 0)  */{
	return None</*  */>();
	}
	/* var params = afterParams.substring(0, paramEnd); */
	/* var withoutParams = afterParams.substring(paramEnd + ")".length()); */
	/* var withBraces = withoutParams.strip(); */
	/* if (!withBraces.startsWith(" */{
	/* ") || !withBraces.endsWith("}")) {
            return new Some<>(""); */
	}
	/* var content = withBraces.substring(1, withBraces.length() - 1); */
	/* var newParams = parseValues(params, Main::parseParameter)
                .iter()
                .filter(parameter -> !(parameter instanceof Whitespace))
                .collect(new ListCollector<>()); */
	/* var copy = Lists.<Defined>listEmpty()
                .addLast(new Definition("struct " + structNames.last(), "this"))
                .addAll(newParams); */
	/* var outputParams = generateValueList(copy); */
	return assembleMethod(outputDefinition, outputParams, content);
}
/* private static <T extends Node> */ char* generateValueList(struct Main this, List<struct T> copy){
	return copy.iter(copy).map(copy.iter(copy), /* Node::generate */).collect(copy.iter(copy).map(copy.iter(copy), /* Node::generate */), struct Joiner(", ")).orElse(copy.iter(copy).map(copy.iter(copy), /* Node::generate */).collect(copy.iter(copy).map(copy.iter(copy), /* Node::generate */), struct Joiner(", ")), "");
}
/* private static */ Some<char*> assembleMethod(struct Main this, char* definition, char* outputParams, char* content){
	/* var generated = definition + "(" + outputParams + "){" + compileStatements(content, Main::compileFunctionSegment) + "\n}\n"; */
	/* methods.addLast(generated); */
	return Some</*  */>("");
}
/* private static */ struct Defined parseParameter(struct Main this, char* input){
	return parseWhitespace(input).<Defined>map(parseWhitespace(input), /* value -> value */).or(parseWhitespace(input).<Defined>map(parseWhitespace(input), /* value -> value */), /* () -> parseDefinition */(input).map(/* () -> parseDefinition */(input), /* value -> value */)).orElseGet(parseWhitespace(input).<Defined>map(parseWhitespace(input), /* value -> value */).or(parseWhitespace(input).<Defined>map(parseWhitespace(input), /* value -> value */), /* () -> parseDefinition */(input).map(/* () -> parseDefinition */(input), /* value -> value */)), /* () -> new Content */(input));
}
/* private static */ Option<struct Whitespace> parseWhitespace(struct Main this, char* input){
	/* if (input.isBlank())  */{
	return Some</*  */>(struct Whitespace());
	}
	/* else  */{
	return None</*  */>();
	}
}
/* private static */ char* compileFunctionSegment(struct Main this, char* input){
	/* var stripped = input.strip(); */
	/* if (stripped.isEmpty())  */{
	return "";
	}
	/* if (stripped.endsWith(";"))  */{
	/* var withoutEnd = stripped.substring(0, stripped.length() - ";".length()).strip(); */
	/* if (withoutEnd.startsWith("return "))  */{
	/* var value = withoutEnd.substring("return ".length()); */
	return "\n\treturn " + compileValue(value) + ";";
	}
	}
	/* if (stripped.endsWith("}"))  */{
	/* var withoutEnd = stripped.substring(0, stripped.length() - "}".length()); */
	/* var contentStart = withoutEnd.indexOf("{"); */
	/* if (contentStart >= 0)  */{
	/* var beforeBlock = withoutEnd.substring(0, contentStart); */
	/* var content = withoutEnd.substring(contentStart + "{".length()); */
	/* var outputContent = compileStatements(content, Main::compileFunctionSegment); */
	return "\n\t" + generatePlaceholder(beforeBlock) + "{" + outputContent + "\n\t}";
	}
	}
	return /* "\n\t" + generatePlaceholder */(stripped);
}
/* private static */ char* compileValue(struct Main this, char* input){
	return parseValue(input).generate(parseValue(input));
}
/* private static */ struct Value parseValue(struct Main this, char* input){
	/* var stripped = input.strip(); */
	/* if (stripped.isEmpty())  */{
	return struct Whitespace();
	}
	/* if (stripped.startsWith("\"") && stripped.endsWith("\""))  */{
	return struct StringValue(stripped.substring(stripped, 1, stripped.length() - 1));
	}
	/* if (stripped.endsWith(")"))  */{
	/* var withoutEnd = stripped.substring(0, stripped.length() - ")".length()).strip(); */
	/* var divisions = divideAll(withoutEnd, Main::foldInvokableStart); */
	/* if (divisions.size() >= 2)  */{
	/* var joined = join(divisions.subList(0, divisions.size() - 1), ""); */
	/* var caller = joined.substring(0, joined.length() - ")".length()); */
	/* var arguments = divisions.last(); */
	/* Value parsedCaller; */
	/* if (caller.startsWith("new "))  */{
	/* parsedCaller = new Symbol(compileType(caller.substring("new ".length()))); */
	}
	/* else  */{
	/* parsedCaller = parseValue(caller); */
	}
	/* var parsedArgs = parseValues(arguments, Main::parseValue)
                        .iter()
                        .filter(value -> !(value instanceof Whitespace))
                        .collect(new ListCollector<>()); */
	/* List<Value> newArgs; */
	/* if (parsedCaller instanceof DataAccess(var parent, _))  */{
	/* newArgs = Lists.<Value>listEmpty()
                            .addLast(parent)
                            .addAll(parsedArgs); */
	}
	/* else  */{
	/* newArgs = parsedArgs; */
	}
	return struct Invocation(parsedCaller, newArgs);
	}
	}
	/* if (isSymbol(stripped))  */{
	return struct Symbol(stripped);
	}
	/* if (isNumber(stripped))  */{
	return struct Symbol(stripped);
	}
	/* var arrowIndex = stripped.indexOf("->"); */
	/* if (arrowIndex >= 0)  */{
	/* var beforeArrow = stripped.substring(0, arrowIndex).strip(); */
	/* var afterArrow = stripped.substring(arrowIndex + "->".length()).strip(); */
	/* if (afterArrow.startsWith(" */{
	/* ") && afterArrow.endsWith("}")) {
                var content = afterArrow.substring(1, afterArrow.length() - 1);

                var name = functionName + "_local" + functionLocalCounter;
                functionLocalCounter++;

                assembleMethod("auto " + name, "auto " + beforeArrow, content);
                return new Symbol(name); */
	}
	}
	/* var separator = stripped.lastIndexOf("."); */
	/* if (separator >= 0)  */{
	/* var value = stripped.substring(0, separator); */
	/* var property = stripped.substring(separator + ".".length()).strip(); */
	return struct DataAccess(parseValue(value), property);
	}
	return struct Content(stripped);
}
/* private static */ struct State foldInvokableStart(struct Main this, struct State state, char c){
	/* var appended = state.append(c); */
	/* if (c == '(')  */{
	/* var maybeAdvanced = appended.isLevel() ? appended.advance() : appended; */
	return maybeAdvanced.enter(maybeAdvanced);
	}
	/* if (c == ')')  */{
	return appended.exit(appended);
	}
	return appended;
}
/* private static */ int isNumber(struct Main this, char* input){
	/* if (input.isEmpty())  */{
	return false;
	}
	/* for (var i = 0; i < input.length(); i++)  */{
	/* var c = input.charAt(i); */
	/* if (Character.isDigit(c))  */{
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
	/* var stripped = input.strip(); */
	/* var nameSeparator = stripped.lastIndexOf(" "); */
	/* if (nameSeparator < 0)  */{
	return None</*  */>();
	}
	/* var beforeName = stripped.substring(0, nameSeparator); */
	/* var name = stripped.substring(nameSeparator + " ".length()); */
	/* if (!isSymbol(name))  */{
	return None</*  */>();
	}
	/* var divisions = divideAll(beforeName, Main::foldByTypeSeparator); */
	/* if (divisions.size() == 1)  */{
	return Some</*  */>(struct Definition(None</*  */>(), compileType(beforeName), name));
	}
	/* var beforeType = join(divisions.subList(0, divisions.size() - 1), " "); */
	/* var type = divisions.last(); */
	return Some</*  */>(struct Definition(Some</*  */>(beforeType), compileType(type), name));
}
/* private static */ struct State foldByTypeSeparator(struct Main this, struct State state, struct char c){
	/* if (c == ' ' && state.isLevel())  */{
	return state.advance(state);
	}
	/* var appended = state.append(c); */
	/* if (c == '<')  */{
	return appended.enter(appended);
	}
	/* if (c == '>')  */{
	return appended.exit(appended);
	}
	return appended;
}
/* private static */ char* compileType(struct Main this, char* input){
	/* var stripped = input.strip(); */
	/* var maybeTypeParamIndex = typeParameters.indexOf(stripped); */
	/* if (maybeTypeParamIndex.isPresent())  */{
	/* var typeParamIndex = maybeTypeParamIndex.orElse(null); */
	return typeArguments.get(typeArguments, typeParamIndex);
	}
	/* switch (stripped)  */{
	/* case "int", "boolean" ->  */{
	return "int";
	}
	/* case "Character" ->  */{
	return "char";
	}
	/* case "void" ->  */{
	return "void";
	}
	/* case "String" ->  */{
	return "char*";
	}
	}
	/* if (stripped.endsWith(">"))  */{
	/* var withoutEnd = stripped.substring(0, stripped.length() - ">".length()); */
	/* var index = withoutEnd.indexOf("<"); */
	/* if (index >= 0)  */{
	/* var base = withoutEnd.substring(0, index).strip(); */
	/* var substring = withoutEnd.substring(index + "<".length()); */
	/* var parsed = parseValues(substring, Main::compileType); */
	/* if (base.equals("Function"))  */{
	/* var arg0 = parsed.get(0); */
	/* var returns = parsed.get(1); */
	return /* returns + " (*)(" + arg0 + ")" */;
	}
	/* if (base.equals("BiFunction"))  */{
	/* var arg0 = parsed.get(0); */
	/* var arg1 = parsed.get(1); */
	/* var returns = parsed.get(2); */
	return /* returns + " (*)(" + arg0 + ", " + arg1 + ")" */;
	}
	/* if (!expansions.contains(new Tuple<>(base, parsed)))  */{
	/* expansions = expansions.addLast(new Tuple<>(base, parsed)); */
	}
	return /* base + "<" + generateValues(parsed) + ">" */;
	}
	}
	/* if (isSymbol(stripped))  */{
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
	/* if (builder.isEmpty())  */{
	return /* builder + element */;
	}
	return /* builder + ", " + element */;
}
/* private static */ struct State foldValueChar(struct Main this, struct State state, struct char c){
	/* if (c == ',' && state.isLevel())  */{
	return state.advance(state);
	}
	/* var appended = state.append(c); */
	/* if (c == '-')  */{
	/* if (appended.peek() instanceof Some(var maybeArrow))  */{
	/* if (maybeArrow == '>')  */{
	return appended.popAndAppend(appended).orElse(appended.popAndAppend(appended), appended);
	}
	}
	}
	/* if (c == '<' || c == '(')  */{
	return appended.enter(appended);
	}
	/* if (c == '>' || c == ')')  */{
	return appended.exit(appended);
	}
	return appended;
}
/* private static */ int isSymbol(struct Main this, char* input){
	/* var stripped = input.strip(); */
	/* if (stripped.isEmpty())  */{
	return false;
	}
	/* for (var i = 0; i < stripped.length(); i++)  */{
	/* var c = stripped.charAt(i); */
	/* if (Character.isLetter(c))  */{
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
