/* sealed */struct Result<T, X> permits Ok, Err {/*  */
}
/* public sealed */struct Option<T> permits Some, None {
	/* T */ (*orElse)(/* T */);/* 
     */
}
/* public */struct List<T> {
	Iterator</* T */> (*iter)();
	List</* T */> (*add)(/* T */);
	/* T */ (*get)(int);/* 
     */
}
/* public */struct Iterator<T> {
	</* R */> (*map)(/*  R */ (*)(/* T */));
	</* R */> (*fold)(/* R */, /*  R */ (*)(/* R */, /*  T */));
	</* R */> (*flatMap)(Iterator</* R */> (*)(/* T */));
	Iterator</* T */> (*concat)(Iterator</* T */>);
	Option</* T */> (*next)();
	</* C */> (*collect)(Collector</* T */, /*  C */>);/* 
     */
}
/* public */struct Collector<T, C> {
	/* C */ (*createInitial)();
	/* C */ (*fold)(/* C */, /* T */);/* 
     */
}
/*  */struct Head<T> {
	Option</* T */> (*next)();/* 
     */
}
/*  */struct Type extends Node {/* default  */ flattenType(/*  */){/* 
            return this; *//* 
         */
}
/* 
     */
}
/*  */struct Node {
	/* String */ (*generate)();/* 
     */
}
/* sealed */struct Definable extends Node {/* default  */ flattenDefinable(/*  */){/* 
            return this; *//* 
         */
}

	Option</* Type */> (*findType)();
	/* Definable */ (*withParams)(List</* Type */>);/* 
     */
}
/*  */struct Ok<T, X>(T value) implements Result<T, X> {/*  */
}
/*  */struct Err<T, X>(X error) implements Result<T, X> {/*  */
}
/*  */struct Some<T>(T value) implements Option<T> {/* @Override
  */ orElse(/* T */ other){/* 
            return this.value; *//* 
         */
}
/* 
     */
}
/* static final */struct None<T> implements Option<T> {/* @Override
  */ orElse(/* T */ other){/* 
            return other; *//* 
         */
}
/* 
     */
}
/* private static */struct State {
	/* private  */ segments;
	/* private  */ buffer;
	/* private  */ depth;/* private */ State(List</* String */> segments, /* StringBuilder */ buffer, int depth){
	/* this.segments */ = segments;
	/* this.buffer */ = buffer;
	/* this.depth */ = depth;/* 
         */
}
/* public */ State(/*  */){/* 
            this(Lists.empty(), new StringBuilder(), 0); *//* 
         */
}
/* private  */ append(/* char */ c){/* 
            this.buffer.append(c); *//* 
            return this; *//* 
         */
}
/* private  */ isLevel(/*  */){
	/* return */ this.depth = /* = 0 */;/* 
         */
}
/* private  */ isShallow(/*  */){
	/* return */ this.depth = /* = 1 */;/* 
         */
}
/* private  */ enter(/*  */){
	/* this.depth */ = this.depth + 1;/* 
            return this; *//* 
         */
}
/* private  */ exit(/*  */){
	/* this.depth */ = this.depth - 1;/* 
            return this; *//* 
         */
}
/* private  */ advance(/*  */){/* 
            this.segments.add(this.buffer.toString()); */
	/* this.buffer */ = /* new StringBuilder */();/* 
            return this; *//* 
         */
}
/* 
     */
}
/* private static */struct EmptyHead<T> implements Head<T> {/* @Override
  */ next(/*  */){/* 
            return new None<>(); *//* 
         */
}
/* 
     */
}
/* public */struct HeadedIterator<T>(Head<T> head) implements Iterator<T> {/* @Override
  */ map(/*  R */ (*mapper)(/* T */)){/* 
            return new HeadedIterator<>(() -> switch (this.head.next()) {
                case None<T> _ -> new None<>();
                case Some<T>(T value) -> new Some<>(mapper.apply(value));
            } *//* ); *//* 
         */
}
/* @Override
  */ fold(/* R */ initial, /*  R */ (*folder)(/* R */, /*  T */)){
	/* var */ current = initial;/* 
            while (true) {
                switch (this.head.next()) {
                    case None<T> _ -> {
                        return current;
                    }
                    case Some(var value) -> current = folder.apply(current, value);
                }
            } *//* 
         */
}
/* @Override
  */ flatMap(Iterator</* R */> (*mapper)(/* T */)){/* 
            return this.map(mapper).fold(Iterators.empty(), Iterator::concat); *//* 
         */
}
/* @Override
  */ concat(Iterator</* T */> other){/* 
            return new HeadedIterator<>(() -> switch (this.head.next()) {
                case Some<T> option -> option;
                case None<T> _ -> other.next();
            } *//* ); *//* 
         */
}
/* @Override
  */ next(/*  */){/* 
            return this.head.next(); *//* 
         */
}
/* @Override
  */ collect(Collector</* T */, /*  C */> collector){/* 
            return this.fold(collector.createInitial(), collector::fold); *//* 
         */
}
/* 
     */
}
/* public static */struct RangeHead implements Head<Integer> {
	/* private  */ length;
	/* private  */ 0;/* public */ RangeHead(int length){
	/* this.length */ = length;/* 
         */
}
/* @Override
  */ next(/*  */){/* 
            if (this.counter < this.length) {
                var value = this.counter;
                this.counter++;
                return new Some<>(value);
            } *//* 
            else {
                return new None<>();
            } *//* 
         */
}
/* 
     */
}
/* private static final */struct SingleHead<T> implements Head<T> {
	/* private  */ value;
	/* private  */ false;/* private */ SingleHead(/* T */ value){
	/* this.value */ = value;/* 
         */
}
/* @Override
  */ next(/*  */){/* 
            if (this.retrieved) {
                return new None<>();
            } */
	/* this.retrieved */ = true;/* 
            return new Some<>(this.value); *//* 
         */
}
/* 
     */
}
/* static */struct Iterators {/* public  */ empty(/*  */){/* 
            return new HeadedIterator<>(new EmptyHead<>()); *//* 
         */
}
/* public  */ fromOption(Option</* T */> option){/* 
            return new HeadedIterator<>(switch (option) {
                case None<T> _ -> new EmptyHead<T>();
                case Some<T>(var value) -> new SingleHead<T>(value);
            } *//* ); *//* 
         */
}
/* 
     */
}
/* private */struct Joiner(String delimiter) implements Collector<String, Option<String>> {/* private */ Joiner(/*  */){/* 
            this(""); *//* 
         */
}
/* @Override
  */ createInitial(/*  */){/* 
            return new None<>(); *//* 
         */
}
/* @Override
  */ fold(Option</* String */> current, /* String */ element){/* 
            return switch (current) {
                case None<String> _ -> new Some<>(element);
                case Some<String>(var value) -> new Some<>(value + this.delimiter + element);
            } *//* ; *//* 
         */
}
/* 
     */
}
/* private */struct Generic(String base, List<Type> arguments) implements Type {/* @Override
  */ generate(/*  */){
	/* var */ joined = this.arguments.iter(/* )
                     */.map(/* Type::generate */).collect(new Joiner(", "))
                    .orElse("");/* 

            return this.base + "<" + joined + ">"; *//* 
         */
}
/* @Override
  */ flattenType(/*  */){/* 
            if (this.base.equals("Function")) {
                return new Functional(Lists.of(this.arguments.get(0)), this.arguments.get(1));
            } *//* 

            if (this.base.equals("BiFunction")) {
                return new Functional(Lists.of(this.arguments.get(0), this.arguments.get(1)), this.arguments.get(2));
            } *//* 

            return this; *//* 
         */
}
/* 
     */
}
/* private */struct Content(String input) implements Type, Definable {/* private  */ generatePlaceholder(/* String */ input){/* 
            return "/* " + input + " */"; *//* 
         */
}
/* @Override
  */ generate(/*  */){/* 
            return generatePlaceholder(this.input); *//* 
         */
}
/* @Override
  */ findType(/*  */){/* 
            return new None<>(); *//* 
         */
}
/* @Override
  */ withParams(List</* Type */> paramTypes){/* 
            return this; *//* 
         */
}
/* 
     */
}
/* private */struct Functional(List<Type> typeParams, Type returns) implements Type {/* @Override
  */ generate(/*  */){
	/* var */ joined = this.typeParams.iter(/* )
                     */.map(/* Type::generate */).collect(new Joiner(", "))
                    .orElse("");/* 

            return this.returns.generate() + " (*)(" + joined + ")"; *//* 
         */
}
/* 

     */
}
/* private static */struct ListCollector<T> implements Collector<T, List<T>> {/* @Override
  */ createInitial(/*  */){/* 
            return Lists.empty(); *//* 
         */
}
/* @Override
  */ fold(List</* T */> current, /* T */ element){/* 
            return current.add(element); *//* 
         */
}
/* 
     */
}
/* private */struct FunctionalDefinition(Functional functional, String name) implements Definable {/* @Override
  */ generate(/*  */){
	/* var */ joined = this.functional.typeParams.iter(/* )
                     */.map(/* Node::generate */).collect(new Joiner(", "))
                    .orElse("");/* 

            return this.functional.returns.generate() + " (*" + this.name + ")(" + joined + ")"; *//* 
         */
}
/* @Override
  */ findType(/*  */){/* 
            return new Some<>(this.functional); *//* 
         */
}
/* @Override
  */ withParams(List</* Type */> paramTypes){/* 
            return new FunctionalDefinition(new Functional(paramTypes, this.functional), this.name); *//* 
         */
}
/* 
     */
}
/* private */struct Definition(Type parsed, String name) implements Definable {/* @Override
  */ generate(/*  */){/* 
            return this.parsed().generate() + " " + this.name(); *//* 
         */
}
/* @Override
  */ flattenDefinable(/*  */){/* 
            if (this.parsed instanceof Functional functional) {
                return new FunctionalDefinition(functional, this.name);
            } *//* 
            return this; *//* 
         */
}
/* @Override
  */ findType(/*  */){/* 
            return new Some<>(this.parsed); *//* 
         */
}
/* @Override
  */ withParams(List</* Type */> paramTypes){/* 
            return new FunctionalDefinition(new Functional(paramTypes, this.parsed), this.name); *//* 
         */
}
/* 
     */
}
/*  */struct Main {
	/* enum  */ (*Void)();
	/* public  */ (*Lists.empty)();void main(/*  */){
	/* var */ source = Paths.get(".", "src", "java", "magma", "Main.java");
	/* var */ target = source.resolveSibling("main.c");/* 

        if (this.run(source, target) instanceof Some(var error)) {
            //noinspection CallToPrintStackTrace
            error.printStackTrace();
        } *//* 
     */
}
/* private  */ run(/* Path */ source, /* Path */ target){/* 
        return switch (this.readString(source)) {
            case Err(var error) -> new Some<>(error);
            case Ok(var input) -> this.writeString(target, this.compile(input));
        } *//* ; *//* 
     */
}
/* private  */ compile(/* String */ input){
	/* var */ output = this.generateAll(/* this::mergeStatements */, this.parseStatements(input));/* 
        return structs.iter().collect(new Joiner()).orElse("") + output; *//* 
     */
}
/* private  */ parseStatements(/* String */ input){/* 
        return this.parseAll(input, this::foldStatementChar, this::compileRootSegment); *//* 
     */
}
/* private  */ generateAll(/*  StringBuilder */ (*merger)(/* StringBuilder */, /*  String */), List</* String */> parsed){/* 
        return parsed.iter()
                .fold(new StringBuilder(), merger)
                .toString(); *//* 
     */
}
/* private  */ parseAll(/* String */ input, /*  State */ (*folder)(/* State */, /*  Character */), /*  T */ (*compiler)(/* String */)){/* 
        return this.divide(input, new State(), folder)
                .iter()
                .map(compiler)
                .collect(new ListCollector<>()); *//* 
     */
}
/* private  */ mergeStatements(/* StringBuilder */ output, /* String */ compiled){/* 
        return output.append(compiled); *//* 
     */
}
/* private  */ divide(/* String */ input, /* State */ state, /*  State */ (*folder)(/* State */, /*  Character */)){
	/* var */ current = state;
	/* for  */ i = /*  0 */;/*  i < input.length(); *//*  i++) {
            var c = input.charAt(i);
            current = folder.apply(current, c);
        } *//* 
        current.advance(); *//* 
        return current.segments; *//* 
     */
}
/* private  */ foldStatementChar(/* State */ state, /* char */ c){/* 
        state.append(c); */
	/* if */ (c = /* = ' */;/* ' && state.isLevel()) {
            state.advance();
        } *//* 
        else if (c == ' */
}

	/* '  */ (*state.isShallow)();/* else */ if(/* c  */ '{'){/* 
            state.enter(); *//* 
        }
        else if (c == ' */
}

	/* ')  */ (*state.exit)();
	/* return */ state;/* 
     */
}
/* package magma; *//* 

import java.io.IOException; *//* 
import java.nio.file.Files; *//* 
import java.nio.file.Path; *//* 
import java.nio.file.Paths; *//* 
import java.util.Arrays; *//* 
import java.util.function.BiFunction; *//* 
import java.util.function.Function; *//* private  */ compileRootSegment(/* String */ input0){/* 
        return this.compileOr(input0, Lists.of(
                input -> this.compileStructured(input, "interface "),
                input -> this.compileStructured(input, "record "),
                input -> this.compileStructured(input, "class "),
                this::compileMethod
        )); *//* 
     */
}
/* private  */ compileOr(/* String */ input0, List<Option</* String */> (*)(/* String */)> rules){
	/* var */ result = rules.iter(/* )
                 */.map(/* rule -> rule */.apply(/* input0) */).flatMap(Iterators::fromOption).next();/* 

        return switch (result) {
            case None<String> _ -> Content.generatePlaceholder(input0);
            case Some<String>(var value) -> value;
        } *//* ; *//* 
     */
}
/* private  */ compileDefinitionStatement(/* String */ input){
	/* var */ stripped = input.strip();/* 
        if (stripped.endsWith("; *//* ")) {
            var content = stripped.substring(0, stripped.length() - ";".length());
            return new Some<>("\n\t" + this.compileDefinition(content) + ";");
        } *//* 
        else {
            return new None<>();
        } *//* 
     */
}
/* private  */ compileMethod(/* String */ input){
	/* var */ paramStart = input.indexOf("(");/* 
        if (paramStart < 0) {
            return new None<>();
        } */
	/* var */ definition = input.substring(/* 0 */, /* paramStart) */.strip();
	/* var */ withParams = input.substring(/* paramStart + " */(".length());
	/* var */ paramEnd = withParams.indexOf(")");/* 
        if (paramEnd < 0) {
            return new None<>();
        } */
	/* var */ paramsString = withParams.substring(/* 0 */, /* paramEnd) */.strip();
	/* var */ withBraces = withParams.substring(/* paramEnd + ")" */.length(/* ) */).strip();
	List</* Definable */> params = this.parseValues(paramsString, /* input1 -> this */.parseDefinition(/* input1) */.flattenDefinable());/* 

        if (withBraces.startsWith("{") && withBraces.endsWith("} *//* ")) {
            var inputContent = withBraces.substring(1, withBraces.length() - 1);
            var outputContent = this.generateAll(this::mergeStatements, this.parseAll(inputContent, this::foldStatementChar, this::compileStatementOrBlock));
            var joinedParams = this.joinNodes(params, ", ");
            return new Some<>(this.compileDefinition(definition) + "(" + joinedParams + ")" + "{" + outputContent + "\n}\n");
        } *//* 
        else {
            var paramTypes = params.iter()
                    .map(Definable::findType)
                    .flatMap(Iterators::fromOption)
                    .collect(new ListCollector<>());

            var definable = this.parseDefinition(definition).flattenDefinable();
            var definable1 = definable.withParams(paramTypes);
            return new Some<>("\n\t" + definable1.generate() + ";");
        } *//* 

     */
}
/* private  */ joinNodes(List</* T */> nodes, /* String */ delimiter){/* 
        return nodes.iter()
                .map(Node::generate)
                .collect(new Joiner(delimiter))
                .orElse(""); *//* 
     */
}
/* private  */ compileDefinition(/* String */ input){/* 
        return this.parseDefinition(input)
                .flattenDefinable()
                .generate(); *//* 
     */
}
/* private  */ parseDefinition(/* String */ input){
	/* var */ stripped = input.strip();
	/* var */ space = stripped.lastIndexOf(" ");/* 
        if (space < 0) {
            return new Content(stripped);
        } */
	/* var */ beforeName = stripped.substring(/* 0 */, space);/* 
        var type = switch (this.findTypeSeparator(beforeName)) {
            case None<Integer> _ -> beforeName;
            case Some<Integer>(var typeSeparator) -> beforeName.substring(0, typeSeparator + " ".length());
        } *//* ; */
	/* var */ name = stripped.substring(/* space + " " */.length());
	/* var */ parsed = this.parseType(/* type) */.flattenType();/* 
        return new Definition(parsed, name); *//* 
     */
}
/* private  */ findTypeSeparator(/* String */ input){
	/* var */ depth = /*  0 */;
	/* for  */ index = /*  0 */;/*  index < input.length(); *//*  index++) {
            var c = input.charAt(index);
            if (c == ' ' && depth == 0) {
                return new Some<>(index);
            }
            if (c == '>') {
                depth++;
            }
            if (c == '<') {
                depth--;
            }
        } *//* 
        return new None<>(); *//* 
     */
}
/* private  */ parseType(/* String */ input){
	/* var */ stripped = input.strip();/* 
        if (stripped.equals("void")) {
            return Primitive.Void;
        } *//* 

        if (stripped.equals("int")) {
            return Primitive.I32;
        } *//* 

        if (stripped.endsWith(">")) {
            var withoutEnd = stripped.substring(0, stripped.length() - ">".length());
            var argumentStart = withoutEnd.indexOf("<");
            if (argumentStart >= 0) {
                var base = withoutEnd.substring(0, argumentStart).strip();
                var parsed = this.parseValues(withoutEnd.substring(argumentStart + "<".length()), input1 -> this.parseType(input1).flattenType());
                return new Generic(base, parsed);
            }
        } *//* 

        return new Content(input); *//* 
     */
}
/* private  */ parseValues(/* String */ input, /*  T */ (*compileType)(/* String */)){/* 
        return this.parseAll(input, this::foldValueChar, compileType); *//* 
     */
}
/* private  */ compileStatementOrBlock(/* String */ input){
	/* var */ stripped = input.strip();/* 
        if (stripped.endsWith("; *//* ")) {
            var statement = stripped.substring(0, stripped.length() - ";".length());
            var valueSeparator = statement.indexOf("=");
            if (valueSeparator >= 0) {
                var definition = statement.substring(0, valueSeparator).strip();
                var value = statement.substring(valueSeparator + "=".length());
                return "\n\t" + this.compileDefinition(definition) + " = " + this.compileValue(value) + ";";
            }
        } *//* 

        return Content.generatePlaceholder(input); *//* 
     */
}
/* private  */ compileValue(/* String */ input){
	/* var */ stripped = input.strip();/* 
        if (stripped.startsWith("\"") && stripped.endsWith("\"")) {
            return stripped;
        } *//* 

        if (stripped.endsWith(")")) {
            var withoutEnd = stripped.substring(0, stripped.length() - ")".length());
            var paramStart = withoutEnd.indexOf("(");
            if (paramStart >= 0) {
                var caller = withoutEnd.substring(0, paramStart).strip();
                var arguments = withoutEnd.substring(paramStart + "(".length());
                var tList = this.parseValues(arguments, this::compileValue);
                return this.compileValue(caller) + "(" + this.generateAll(this::mergeValues, tList) + ")";
            }
        } */
	/* var */ separator = stripped.lastIndexOf(".");/* 
        if (separator >= 0) {
            var parent = stripped.substring(0, separator);
            var child = stripped.substring(separator + ".".length());

            return this.compileValue(parent) + "." + child;
        } *//* 

        if (this.isSymbol(stripped)) {
            return stripped;
        } *//* 

        return Content.generatePlaceholder(input); *//* 
     */
}
/* private  */ foldValueChar(/* State */ state, /* char */ c){/* 
        if (c == ',' && state.isLevel()) {
            return state.advance();
        } */
	/* var */ appended = state.append(c);/* 
        if (c == '<') {
            return appended.enter();
        } *//* 
        if (c == '>') {
            return appended.exit();
        } *//* 
        return appended; *//* 
     */
}
/* private  */ mergeValues(/* StringBuilder */ cache, /* String */ element){/* 
        if (cache.isEmpty()) {
            return cache.append(element);
        } *//* 
        return cache.append(", ").append(element); *//* 
     */
}
/* private  */ isSymbol(/* String */ input){
	/* for  */ i = /*  0 */;/*  i < input.length(); *//*  i++) {
            var c = input.charAt(i);
            if (Character.isLetter(c)) {
                continue;
            }
            return false;
        } *//* 
        return true; *//* 
     */
}
/* private  */ compileStructured(/* String */ input, /* String */ infix){
	/* var */ interfaceIndex = input.indexOf(infix);/* 
        if (interfaceIndex < 0) {
            return new None<>();
        } */
	/* var */ beforeKeyword = input.substring(/* 0 */, /* interfaceIndex) */.strip();
	/* var */ allSymbols = Arrays.stream(beforeKeyword.split(/* " "))
                 */.map(/* String::strip)
                 */.filter(value -> !value.isEmpty()).allMatch(this::isSymbol);/* 

        if (!allSymbols) {
            return new None<>();
        } */
	/* var */ afterKeyword = input.substring(/* interfaceIndex + infix */.length());/* 
        var contentStart = afterKeyword.indexOf("{");
        if (contentStart < 0) {
            return new None<>();
        }

        var beforeContent = afterKeyword.substring(0, contentStart).strip();
        var withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
        if (!withEnd.endsWith("}")) {
            return new None<>();
        }

        var content = withEnd.substring(0, withEnd.length() - "} *//* ".length()); *//* 
        var generated = Content.generatePlaceholder(beforeKeyword.strip()) + "struct " +
                beforeContent + " {" + this.generateAll(this::mergeStatements, this.parseAll(content, this::foldStatementChar, this::compileClassMember)) + "\n} *//* \n"; *//* 

        structs.add(generated); *//* 
        return new Some<>(""); *//* 
     */
}
/* private  */ compileClassMember(/* String */ input0){/* 
        return this.compileOr(input0, Lists.of(
                input -> this.compileStructured(input, "class "),
                input -> this.compileStructured(input, "interface "),
                input -> this.compileStructured(input, "record "),
                this::compileMethod,
                this::compileDefinitionStatement
        )); *//* 
     */
}
/* private  */ writeString(/* Path */ target, /* String */ output){/* 
        try {
            Files.writeString(target, output);
            return new None<>();
        } *//*  catch (IOException e) {
            return new Some<>(e);
        } *//* 
     */
}
/* private  */ readString(/* Path */ source){/* 
        try {
            return new Ok<>(Files.readString(source));
        } *//*  catch (IOException e) {
            return new Err<>(e);
        } *//* 
     */
}
/* 
} */