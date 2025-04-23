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
	/* Bit("int"), */ I8("char");
	/* private final */ char* value;/* 

        Primitive(String value) {
            this.value = value;
        } */
};
/* public static */struct RangeHead implements Head<Integer> {
	/* private final */ /* int */ length;
	/* private */ /* int */ counter;
};
/* public */struct Iterator<T>(Head<T> head) {
};
/* private static */struct State {
	/* private final */ List<char*> segments;
	/* private */ /* StringBuilder */ buffer;
	/* private */ /* int */ depth;
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
/*  */struct Main {
};
/* <R> */ Option</* R */> map(/*  R */(*mapper)(/* T */));
/* T */ orElseGet(/* T */(*other)());
Option</* T */> or(Option</* T */>(*other)());
/* T */ orElse(/* T */ other);
/* <R> */ Option</* R */> flatMap(Option</* R */>(*mapper)(/* T */));
Option</* T */> next();
List</* T */> add(/* T */ element);
Iterator</* T */> iter();
int hasElements();
/* T */ removeFirst();
/* T */ get(/* int */ index);
/* C */ createInitial();
/* C */ fold(/* C */ current, /* T */ element);
char* generate();
char* generate();
/* @Override
        public */ char* generate(){
	return this.value;
}
/* public */ RangeHead(/* int */ length){
	/* this.length = length; */
}
/* @Override
        public */ Option</* Integer */> next(){
	/* if (this.counter < this.length) {
                var value = this.counter;
                this.counter++;
                return new Some<>(value);
            } */
	/* else {
                return new None<>();
            } */
}
/* public <R> */ Iterator</* R */> map(/*  R */(*mapper)(/* T */)){
	return /* new Iterator<>(() -> this */.head.next().map(mapper));
}
/* public <R> */ /* R */ fold(/* R */ initial, BiFunction</* R */, /*  T */, /*  R */> folder){
	/* var current = initial; */
	/* while (true) {
                switch (this.head.next()) {
                    case Some<T>(var value) -> current = folder.apply(current, value);
                    case None<T> _ -> {
                        return current;
                    }
                }
            } */
}
/* public <C> */ /* C */ collect(Collector</* T */, /*  C */> collector){
	return this.fold(collector.createInitial(), collector::fold);
}
/* private */ State(List<char*> segments, /* StringBuilder */ buffer, /* int */ depth){
	/* this.segments = segments; */
	/* this.buffer = buffer; */
	/* this.depth = depth; */
}
/* public */ State(){
	/* this(Lists.emptyList(), new StringBuilder(), 0); */
}
/* public */ int isLevel(){
	return this.depth == 0;
}
/* public */ /* State */ enter(){
	/* this.depth++; */
	return this;
}
/* public */ /* State */ exit(){
	/* this.depth--; */
	return this;
}
/* private */ /* State */ append(/* char */ c){
	/* this.buffer.append(c); */
	return this;
}
/* private */ /* State */ advance(){
	/* this.segments.add(this.buffer.toString()); */
	/* this.buffer = new StringBuilder(); */
	return this;
}
/* public */ int isShallow(){
	return this.depth == 1;
}
/* @Override
        public <R> */ Option</* R */> map(/*  R */(*mapper)(/* T */)){
	return /* new Some<>(mapper */.apply(this.value));
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
        public <R> */ Option</* R */> map(/*  R */(*mapper)(/* T */)){
	return /* new None<>() */;
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
	return /* new None<>() */;
}
/* private */ Joiner(){
	/* this(""); */
}
/* @Override
        public */ Option<char*> createInitial(){
	return /* new None<>() */;
}
/* @Override
        public */ Option<char*> fold(Option<char*> maybeCurrent, char* element){
	/* return new Some<>(switch (maybeCurrent) {
                case None<String> _ -> element;
                case Some<String>(var current) -> current + this.delimiter + element;
            } */
	/* ); */
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
	/* var joined = generateValuesFromNodes(this.args); */
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
	/* var beforeTypeString = this.beforeType
                    .map(Main::generatePlaceholder)
                    .map(inner -> inner + " ")
                    .orElse(""); */
	return /* beforeTypeString + this */.type.generate() + " " + this.name;
}
/* @Override
        public */ char* generate(){
	/* var beforeTypeString = this.beforeType.map(inner -> inner + " ").orElse(""); */
	return /* "%s%s(*%s)(%s)" */.formatted(beforeTypeString, this.returns.generate(), this.name, generateValuesFromNodes(this.args));
}
/* @Override
        public */ char* generate(){
	return this.type.generate() + "*";
}
/* private static final List<String> structs */ /* = */ Lists.emptyList();
/* private static final List<String> methods */ /* = */ Lists.emptyList();
/* private static */ char* generateAll(BiFunction</* StringBuilder */, char*, /*  StringBuilder */> merger, List<char*> parsed){
	return parsed.iter().fold(new StringBuilder(), merger).toString();
}
/* private static <T> */ List</* T */> parseAll(char* input, BiFunction</* State */, /*  Character */, /*  State */> folder, /*  T */(*compiler)(char*)){
	return Main.divideAll(input, folder).iter().map(compiler).collect(new ListCollector<>());
}
/* private static */ List<char*> divideAll(char* input, BiFunction</* State */, /*  Character */, /*  State */> folder){
	/* var current = new State(); */
	/* var queue = new Iterator<>(new RangeHead(input.length()))
                .map(input::charAt)
                .collect(new ListCollector<>()); */
	/* while (queue.hasElements()) {
            var c = queue.removeFirst();

            if (c == '\'') {
                current.append(c);

                var c1 = queue.removeFirst();
                current.append(c1);
                if (c1 == '\\') {
                    current.append(queue.removeFirst());
                }
                current.append(queue.removeFirst());
                continue;
            }
            if (c == '"') {
                current.append(c);
                while (queue.hasElements()) {
                    var next = queue.removeFirst();
                    current.append(next);

                    if (next == '\\') {
                        current.append(queue.removeFirst());
                    }
                    if (next == '"') {
                        break;
                    }
                }

                continue;
            }
            current = folder.apply(current, c);
        } */
	return current.advance().segments;
}
/* private static */ char* generateValues(List<char*> parserd){
	return Main.generateAll(Main::mergeValues, parserd);
}
/* private static */ /* StringBuilder */ mergeValues(/* StringBuilder */ cache, char* element){
	/* if (cache.isEmpty()) {
            return cache.append(element);
        } */
	return cache.append(", ").append(element);
}
/* private static */ char* generatePlaceholder(char* input){
	return /* "/* " + input + " */" */;
}
/* private static */ char* generateValuesFromNodes(List</* Type */> list){
	return list.iter().map(Type::generate).collect(new Joiner(", ")).orElse("");
}
/* private static */ char* compileStatementOrBlock(char* input){
	return /* compileWhitespace(input)
                 */.or(() -> compileStatement(input, Main::compileStatementValue)).orElseGet(() -> "\n\t" + generatePlaceholder(input.strip()));
}
/* private static */ Option<char*> compileStatementValue(char* input){
	/* var stripped = input.strip(); */
	/* if (stripped.startsWith("return ")) {
            var value = stripped.substring("return ".length());
            return new Some<>("return " + compileValue(value));
        } */
	return /* new None<>() */;
}
/* private static */ char* compileValue(char* input){
	/* var stripped = input.strip(); */
	/* var separator = stripped.lastIndexOf("."); */
	/* if (separator >= 0) {
            var parent = stripped.substring(0, separator);
            var property = stripped.substring(separator + ".".length());
            return compileValue(parent) + "." + property;
        } */
	/* if (isSymbol(stripped)) {
            return stripped;
        } */
	return /* generatePlaceholder(input) */;
}
/* private static */ int isSymbol(char* input){
	/* for (var i = 0; */
	/* i < input.length(); */
	/* i++) {
            var c = input.charAt(i);
            if (Character.isLetter(c)) {
                continue;
            }
            return false;
        } */
	return true;
}
/* private static */ Option<char*> compileWhitespace(char* input){
	/* if (input.isBlank()) {
            return new Some<>("");
        } */
	return /* new None<>() */;
}
/* private static */ Option</* Defined */> parseAndModifyDefinition(char* input){
	/* return Main.parseDefinition(input).map(definition -> {
            if (definition.type instanceof Functional(var args, var base)) {
                return new FunctionalDefinition(definition.beforeType, base, definition.name, args);
            }

            return definition;
        } */
	/* ); */
}
/* private static */ Option<char*> compileStatement(char* input, Option<char*>(*compiler)(char*)){
	/* var stripped = input.strip(); */
	/* if (!stripped.endsWith(";")) {
            return new None<>();
        } */
	/* var withoutEnd = stripped.substring(0, stripped.length() - ";".length()); */
	return compiler.apply(withoutEnd).map(definition -> "\n\t" + definition + ";");
}
/* private static <T> */ List</* T */> parseValues(char* input, /*  T */(*compiler)(char*)){
	return Main.parseAll(input, Main::foldValueChar, compiler);
}
/* private static */ /* State */ foldValueChar(/* State */ state, /* char */ c){
	/* if (c == ',' && state.isLevel()) {
            return state.advance();
        } */
	/* var appended = state.append(c); */
	/* if (c == '<') {
            return appended.enter();
        } */
	/* if (c == '>') {
            return appended.exit();
        } */
	return appended;
}
/* private static */ Option</* Definition */> parseDefinition(char* input){
	/* var nameSeparator = input.lastIndexOf(" "); */
	/* if (nameSeparator < 0) {
            return new None<>();
        } */
	/* var beforeName = input.substring(0, nameSeparator).strip(); */
	/* var name = input.substring(nameSeparator + " ".length()).strip(); */
	/* return new Some<>(switch (Main.findTypeSeparator(beforeName)) {
            case None<Integer> _ -> new Definition(new None<>(), Main.parseAndModifyType(beforeName), name);
            case Some<Integer>(var typeSeparator) -> {
                var beforeType = beforeName.substring(0, typeSeparator).strip();
                var type = beforeName.substring(typeSeparator + " ".length()).strip();
                var newType = Main.parseAndModifyType(type);
                yield new Definition(new Some<>(beforeType), newType, name);
            }
        } */
	/* ); */
}
/* private static */ Option</* Integer */> findTypeSeparator(char* input){
	/* var depth = 0; */
	/* for (var index = input.length() - 1; */
	/* index >= 0; */
	/* index--) {
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
        } */
	return /* new None<>() */;
}
/* private static */ /* Type */ parseAndModifyType(char* input){
	/* var parsed = Main.parseType(input); */
	/* if (parsed instanceof Generic(var base, var arguments)) {
            if (base.equals("Function")) {
                var argType = arguments.get(0);
                var returnType = arguments.get(1);

                return new Functional(Lists.of(argType), returnType);
            }

            if (base.equals("Supplier")) {
                var returns = arguments.get(0);
                return new Functional(Lists.emptyList(), returns);
            }
        } */
	return parsed;
}
/* private static */ /* Type */ parseType(char* input){
	/* var stripped = input.strip(); */
	/* if (stripped.equals("boolean")) {
            return Primitive.Bit;
        } */
	/* if (stripped.equals("String")) {
            return new Ref(Primitive.I8);
        } */
	/* if (stripped.endsWith(">")) {
            var slice = stripped.substring(0, stripped.length() - ">".length());
            var argsStart = slice.indexOf("<");
            if (argsStart >= 0) {
                var base = slice.substring(0, argsStart).strip();
                var inputArgs = slice.substring(argsStart + "<".length());
                var args = Main.parseValues(inputArgs, input1 -> parseAndModifyType(input1));
                return new Generic(base, args);
            }
        } */
	return /* new Content(input) */;
}
/* void */ main(){
	/* try {
            var source = Paths.get(".", "src", "java", "magma", "Main.java");
            var input = Files.readString(source);

            var target = source.resolveSibling("main.c");
            Files.writeString(target, this.compileRoot(input));
        } */
	/* catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        } */
}
/* private */ char* compileRoot(char* input){
	/* var compiled = this.compileStatements(input, this::compileRootSegment); */
	/* var joinedStructs = structs.iter().collect(new Joiner()).orElse(""); */
	/* var joinedMethods = methods.iter().collect(new Joiner()).orElse(""); */
	return /* compiled + joinedStructs + joinedMethods */;
}
/* private */ char* compileStatements(char* input, char*(*segment)(char*)){
	return Main.generateAll(this::mergeStatements, Main.parseAll(input, this::foldStatementChar, segment));
}
/* private */ /* StringBuilder */ mergeStatements(/* StringBuilder */ stringBuilder, char* str){
	return stringBuilder.append(str);
}
/* private */ char* compileRootSegment(char* input){
	return this.compileClass(input).orElseGet(() -> generatePlaceholder(input.strip()) + "\n");
}
/* private */ Option<char*> compileClass(char* input){
	return this.compileStructured(input, "class ");
}
/* private */ Option<char*> compileStructured(char* input, char* infix){
	/* var classIndex = input.indexOf(infix); */
	/* if (classIndex < 0) {
            return new None<>();
        } */
	/* var left = input.substring(0, classIndex).strip(); */
	/* var right = input.substring(classIndex + infix.length()); */
	/* var contentStart = right.indexOf("{"); */
	/* if (contentStart < 0) {
            return new None<>();
        } */
	/* var name = right.substring(0, contentStart).strip(); */
	/* var withEnd = right.substring(contentStart + "{".length()).strip(); */
	/* if (!withEnd.endsWith("}")) {
            return new None<>();
        } */
	/* var inputContent = withEnd.substring(0, withEnd.length() - 1); */
	/* var outputContent = this.compileStatements(inputContent, this::compileStructuredSegment); */
	/* var generated = generatePlaceholder(left) + "struct " + name + " {" + outputContent + "\n};\n"; */
	/* structs.add(generated); */
	return /* new Some<>("") */;
}
/* private */ char* compileStructuredSegment(char* input){
	return /* compileWhitespace(input)
                 */.or(() -> this.compileStructured(input, "interface ")).or(() -> this.compileStructured(input, "enum ")).or(() -> this.compileStructured(input, "class ")).or(() -> this.compileStructured(input, "record ")).or(() -> this.compileMethod(input)).or(() -> this.compileDefinitionStatement(input)).orElseGet(() -> generatePlaceholder(input));
}
/* private */ Option<char*> compileMethod(char* input){
	/* var paramStart = input.indexOf("("); */
	/* if (paramStart >= 0) {
            var inputDefinition = input.substring(0, paramStart).strip();
            var withParams = input.substring(paramStart + "(".length());

            return parseAndModifyDefinition(inputDefinition).map(Defined::generate).flatMap(outputDefinition -> {
                var paramEnd = withParams.indexOf(")");
                if (paramEnd >= 0) {
                    var inputParams = withParams.substring(0, paramEnd).strip();
                    var withBraces = withParams.substring(paramEnd + ")".length()).strip();
                    var outputParams = this.compileValues(inputParams, this::compileParam);
                    String newBody;
                    if (withBraces.startsWith("{") && withBraces.endsWith("}")) {
                        var body = withBraces.substring(1, withBraces.length() - 1);
                        newBody = "{" + this.compileStatements(body, Main::compileStatementOrBlock) + "\n}";
                    }
                    else if (withBraces.equals(";")) {
                        newBody = ";";
                    }
                    else {
                        return new None<>();
                    }

                    var generated = outputDefinition + "(" + outputParams + ")" + newBody + "\n";
                    methods.add(generated);
                    return new Some<>("");
                }

                return new None<>();
            });
        } */
	return /* new None<>() */;
}
/* private */ char* compileValues(char* input, char*(*compiler)(char*)){
	return Main.generateValues(Main.parseValues(input, compiler));
}
/* private */ char* compileParam(char* param){
	return /* compileWhitespace(param)
                 */.or(() -> parseAndModifyDefinition(param).map(Defined::generate)).orElseGet(() -> generatePlaceholder(param));
}
/* private */ Option<char*> compileDefinitionStatement(char* input){
	return /* compileStatement(input, withoutEnd -> Main */.parseAndModifyDefinition(withoutEnd).map(Defined::generate));
}
/* private */ /* State */ foldStatementChar(/* State */ state, /* char */ c){
	/* var appended = state.append(c); */
	/* if (c == ';' && appended.isLevel()) {
            return appended.advance();
        } */
	/* if (c == '}' && appended.isShallow()) {
            return appended.advance().exit();
        } */
	/* if (c == '{') {
            return appended.enter();
        } */
	/* if (c == '}') {
            return appended.exit();
        } */
	/* else {
            return appended;
        } */
}
