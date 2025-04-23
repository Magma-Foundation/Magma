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
/*  */struct Main {
};
/* <R> */ Option</* R */> map(/*  R */(*mapper)(/* T */))/* ; */
/* T */ orElseGet(/* T */(*other)())/* ; */
Option</* T */> or(Option</* T */>(*other)())/* ; */
/* T */ orElse(/* T */ other)/* ; */
/* <R> */ Option</* R */> flatMap(Option</* R */>(*mapper)(/* T */))/* ; */
Option</* T */> next(/*  */)/* ; */
List</* T */> add(/* T */ element)/* ; */
Iterator</* T */> iter(/*  */)/* ; */
int hasElements(/*  */)/* ; */
/* T */ removeFirst(/*  */)/* ; */
/* T */ get(/* int */ index)/* ; */
/* C */ createInitial(/*  */)/* ; */
/* C */ fold(/* C */ current, /* T */ element)/* ; */
char* generate(/*  */)/* ; */
char* generate(/*  */)/* ; */
/* @Override
        public */ char* generate(/*  */)/* {
            return this.value;
        } */
/* public static class RangeHead implements Head<Integer> {
        private final int length;
        private int counter; */ /* public */ RangeHead(/* int */ length)/* {
            this.length = length;
        }

        @Override
        public Option<Integer> next() {
            if (this.counter < this.length) {
                var value = this.counter;
                this.counter++;
                return new Some<>(value);
            }
            else {
                return new None<>();
            }
        }
    } */
/* public */ /* record */ Iterator<T>(Head</* T */> head)/* {
        public <R> Iterator<R> map(Function<T, R> mapper) {
            return new Iterator<>(() -> this.head.next().map(mapper));
        }

        public <R> R fold(R initial, BiFunction<R, T, R> folder) {
            var current = initial;
            while (true) {
                switch (this.head.next()) {
                    case Some<T>(var value) -> current = folder.apply(current, value);
                    case None<T> _ -> {
                        return current;
                    }
                }
            }
        }

        public <C> C collect(Collector<T, C> collector) {
            return this.fold(collector.createInitial(), collector::fold);
        }
    } */
/* private static class State {
        private final List<String> segments;
        private StringBuilder buffer;
        private int depth; */ /* private */ State(List<char*> segments, /* StringBuilder */ buffer, /* int */ depth)/* {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
        }

        public State() {
            this(Lists.emptyList(), new StringBuilder(), 0);
        }

        public boolean isLevel() {
            return this.depth == 0;
        }

        public State enter() {
            this.depth++;
            return this;
        }

        public State exit() {
            this.depth--;
            return this;
        }

        private State append(char c) {
            this.buffer.append(c);
            return this;
        }

        private State advance() {
            this.segments.add(this.buffer.toString());
            this.buffer = new StringBuilder();
            return this;
        }

        public boolean isShallow() {
            return this.depth == 1;
        }
    } */
/* private */ /* record */ Some<T>(/* T */ value)/* implements Option<T> {
        @Override
        public <R> Option<R> map(Function<T, R> mapper) {
            return new Some<>(mapper.apply(this.value));
        }

        @Override
        public T orElseGet(Supplier<T> other) {
            return this.value;
        }

        @Override
        public Option<T> or(Supplier<Option<T>> other) {
            return this;
        }

        @Override
        public T orElse(T other) {
            return this.value;
        }

        @Override
        public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
            return mapper.apply(this.value);
        }
    } */
/* private static final class None<T> implements Option<T> {
        @Override
        public <R> */ Option</* R */> map(/*  R */(*mapper)(/* T */))/* {
            return new None<>();
        }

        @Override
        public T orElseGet(Supplier<T> other) {
            return other.get();
        }

        @Override
        public Option<T> or(Supplier<Option<T>> other) {
            return other.get();
        }

        @Override
        public T orElse(T other) {
            return other;
        }

        @Override
        public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
            return new None<>();
        }
    } */
/* private */ /* record */ Joiner(char* delimiter)/* implements Collector<String, Option<String>> {
        private Joiner() {
            this("");
        }

        @Override
        public Option<String> createInitial() {
            return new None<>();
        }

        @Override
        public Option<String> fold(Option<String> maybeCurrent, String element) {
            return new Some<>(switch (maybeCurrent) {
                case None<String> _ -> element;
                case Some<String>(var current) -> current + this.delimiter + element;
            });
        }
    } */
/* private static class ListCollector<T> implements Collector<T, List<T>> {
        @Override
        public */ List</* T */> createInitial(/*  */)/* {
            return Lists.emptyList();
        }

        @Override
        public List<T> fold(List<T> current, T element) {
            return current.add(element);
        }
    } */
/* private */ /* record */ Generic(char* base, List</* Type */> args)/* implements Type {
        @Override
        public String generate() {
            var joined = generateValuesFromNodes(this.args);
            return this.base + "<" + joined + ">";
        }
    } */
/* private */ /* record */ Content(char* input)/* implements Type {
        @Override
        public String generate() {
            return Main.generatePlaceholder(this.input);
        }
    } */
/* private */ /* record */ Functional(List</* Type */> paramTypes, /* Type */ returnType)/* implements Type {
        @Override
        public String generate() {
            return this.returnType.generate() + " (*)(" + generateValuesFromNodes(this.paramTypes) + ")";
        }
    } */
/* private */ /* record */ Definition(Option<char*> beforeType, /* Type */ type, char* name)/* implements Defined {
        @Override
        public String generate() {
            var beforeTypeString = this.beforeType
                    .map(Main::generatePlaceholder)
                    .map(inner -> inner + " ")
                    .orElse("");

            return beforeTypeString + this.type.generate() + " " + this.name;
        }
    } */
/* private */ /* record */ FunctionalDefinition(Option<char*> beforeType, /* Type */ returns, char* name, List</* Type */> args)/* implements Defined {
        @Override
        public String generate() {
            var beforeTypeString = this.beforeType.map(inner -> inner + " ").orElse("");
            return "%s%s(*%s)(%s)".formatted(beforeTypeString, this.returns.generate(), this.name, generateValuesFromNodes(this.args));
        }
    } */
/* private */ /* record */ Ref(/* Type */ type)/* implements Type {
        @Override
        public String generate() {
            return this.type.generate() + "*";
        }
    } */
/* private static final List<String> structs */ /* = */ Lists.emptyList(/*  */)/* ; */
/* private static final List<String> methods */ /* = */ Lists.emptyList(/*  */)/* ; */
/* private static */ char* generateAll(BiFunction</* StringBuilder */, char*, /*  StringBuilder */> merger, List<char*> parsed)/* {
        return parsed.iter()
                .fold(new StringBuilder(), merger)
                .toString();
    } */
/* private static <T> */ List</* T */> parseAll(char* input, BiFunction</* State */, /*  Character */, /*  State */> folder, /*  T */(*compiler)(char*))/* {
        return Main.divideAll(input, folder)
                .iter()
                .map(compiler)
                .collect(new ListCollector<>());
    } */
/* private static */ List<char*> divideAll(char* input, BiFunction</* State */, /*  Character */, /*  State */> folder)/* {
        var current = new State();
        var queue = new Iterator<>(new RangeHead(input.length()))
                .map(input::charAt)
                .collect(new ListCollector<>());

        while (queue.hasElements()) {
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
        }
        return current.advance().segments;
    } */
/* private static */ char* generateValues(List<char*> parserd)/* {
        return Main.generateAll(Main::mergeValues, parserd);
    } */
/* private static */ /* StringBuilder */ mergeValues(/* StringBuilder */ cache, char* element)/* {
        if (cache.isEmpty()) {
            return cache.append(element);
        }
        return cache.append(", ").append(element);
    } */
/* private static */ char* generatePlaceholder(char* input)/* {
        return "/* " + input + " */";
    } */
/* private static */ char* generateValuesFromNodes(List</* Type */> list)/* {
        return list.iter()
                .map(Type::generate)
                .collect(new Joiner(", "))
                .orElse("");
    } */
/* void */ main(/*  */)/* {
        try {
            var source = Paths.get(".", "src", "java", "magma", "Main.java");
            var input = Files.readString(source);

            var target = source.resolveSibling("main.c");
            Files.writeString(target, this.compileRoot(input));
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    } */
/* private */ char* compileRoot(char* input)/* {
        var compiled = this.compileStatements(input, this::compileRootSegment);
        var joinedStructs = structs.iter().collect(new Joiner()).orElse("");
        var joinedMethods = methods.iter().collect(new Joiner()).orElse("");
        return compiled + joinedStructs + joinedMethods;
    } */
/* private */ char* compileStatements(char* input, char*(*segment)(char*))/* {
        return Main.generateAll(this::mergeStatements, Main.parseAll(input, this::foldStatementChar, segment));
    } */
/* private */ /* StringBuilder */ mergeStatements(/* StringBuilder */ stringBuilder, char* str)/* {
        return stringBuilder.append(str);
    } */
/* private */ char* compileRootSegment(char* input)/* {
        return this.compileClass(input)
                .orElseGet(() -> generatePlaceholder(input.strip()) + "\n");
    } */
/* private */ Option<char*> compileClass(char* input)/* {
        return this.compileStructured(input, "class ");
    } */
/* private */ Option<char*> compileStructured(char* input, char* infix)/* {
        var classIndex = input.indexOf(infix);
        if (classIndex < 0) {
            return new None<>();
        }
        var left = input.substring(0, classIndex).strip();
        var right = input.substring(classIndex + infix.length());
        var contentStart = right.indexOf("{");
        if (contentStart < 0) {
            return new None<>();
        }
        var name = right.substring(0, contentStart).strip();
        var withEnd = right.substring(contentStart + "{".length()).strip();
        if (!withEnd.endsWith("}")) {
            return new None<>();
        }
        var inputContent = withEnd.substring(0, withEnd.length() - 1);
        var outputContent = this.compileStatements(inputContent, this::compileStructuredSegment);

        var generated = generatePlaceholder(left) + "struct " + name + " {" + outputContent + "\n};\n";
        structs.add(generated);
        return new Some<>("");
    } */
/* private */ char* compileStructuredSegment(char* input)/* {
        return this.compileWhitespace(input)
                .or(() -> this.compileStructured(input, "interface "))
                .or(() -> this.compileStructured(input, "enum "))
                .or(() -> this.compileMethod(input))
                .or(() -> this.compileDefinitionStatement(input))
                .orElseGet(() -> generatePlaceholder(input));
    } */
/* private */ Option<char*> compileWhitespace(char* input)/* {
        if (input.isBlank()) {
            return new Some<>("");
        }
        return new None<>();
    } */
/* private */ Option<char*> compileMethod(char* input)/* {
        var paramStart = input.indexOf("(");
        if (paramStart >= 0) {
            var inputDefinition = input.substring(0, paramStart).strip();
            var withParams = input.substring(paramStart + "(".length());

            return this.parseAndModifyDefinition(inputDefinition).map(Defined::generate).flatMap(outputDefinition -> {
                var paramEnd = withParams.indexOf(")");
                if (paramEnd >= 0) {
                    var inputParams = withParams.substring(0, paramEnd).strip();
                    var body = withParams.substring(paramEnd + ")".length()).strip();
                    var outputParams = this.compileValues(inputParams, this::compileParam);
                    var generated = outputDefinition + "(" + outputParams + ")" + generatePlaceholder(body) + "\n";
                    methods.add(generated);
                    return new Some<>("");
                }

                return new None<>();
            });
        }

        return new None<>();
    } */
/* private */ char* compileValues(char* input, char*(*compiler)(char*))/* {
        return Main.generateValues(this.parseValues(input, compiler));
    } */
/* private <T> */ List</* T */> parseValues(char* input, /*  T */(*compiler)(char*))/* {
        return Main.parseAll(input, this::foldValueChar, compiler);
    } */
/* private */ /* State */ foldValueChar(/* State */ state, /* char */ c)/* {
        if (c == ',' && state.isLevel()) {
            return state.advance();
        }

        var appended = state.append(c);
        if (c == '<') {
            return appended.enter();
        }
        if (c == '>') {
            return appended.exit();
        }
        return appended;
    } */
/* private */ char* compileParam(char* param)/* {
        return this.parseAndModifyDefinition(param).map(Defined::generate).orElseGet(() -> generatePlaceholder(param));
    } */
/* private */ Option<char*> compileDefinitionStatement(char* input)/* {
        var stripped = input.strip();
        if (!stripped.endsWith(";")) {
            return new None<>();
        }
        var withoutEnd = stripped.substring(0, stripped.length() - ";".length());
        return this.parseAndModifyDefinition(withoutEnd).map(Defined::generate).map(definition -> "\n\t" + definition + ";");
    } */
/* private */ Option</* Defined */> parseAndModifyDefinition(char* input)/* {
        return this.parseDefinition(input).map(definition -> {
            if (definition.type instanceof Functional(var args, var base)) {
                return new FunctionalDefinition(definition.beforeType, base, definition.name, args);
            }

            return definition;
        });
    } */
/* private */ Option</* Definition */> parseDefinition(char* input)/* {
        var nameSeparator = input.lastIndexOf(" ");
        if (nameSeparator < 0) {
            return new None<>();
        }
        var beforeName = input.substring(0, nameSeparator).strip();
        var name = input.substring(nameSeparator + " ".length()).strip();

        return new Some<>(switch (this.findTypeSeparator(beforeName)) {
            case None<Integer> _ -> new Definition(new None<>(), this.parseAndModifyType(beforeName), name);
            case Some<Integer>(var typeSeparator) -> {
                var beforeType = beforeName.substring(0, typeSeparator).strip();
                var type = beforeName.substring(typeSeparator + " ".length()).strip();
                var newType = this.parseAndModifyType(type);
                yield new Definition(new Some<>(beforeType), newType, name);
            }
        });
    } */
/* private */ Option</* Integer */> findTypeSeparator(char* input)/* {
        var depth = 0;
        for (var index = input.length() - 1; index >= 0; index--) {
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
        }

        return new None<>();
    } */
/* private */ /* Type */ parseAndModifyType(char* input)/* {
        var parsed = this.parseType(input);
        if (parsed instanceof Generic(var base, var arguments)) {
            if (base.equals("Function")) {
                var argType = arguments.get(0);
                var returnType = arguments.get(1);

                return new Functional(Lists.of(argType), returnType);
            }

            if (base.equals("Supplier")) {
                var returns = arguments.get(0);
                return new Functional(Lists.emptyList(), returns);
            }
        }
        return parsed;
    } */
/* private */ /* Type */ parseType(char* input)/* {
        var stripped = input.strip();
        if (stripped.equals("boolean")) {
            return Primitive.Bit;
        }

        if (stripped.equals("String")) {
            return new Ref(Primitive.I8);
        }

        if (stripped.endsWith(">")) {
            var slice = stripped.substring(0, stripped.length() - ">".length());
            var argsStart = slice.indexOf("<");
            if (argsStart >= 0) {
                var base = slice.substring(0, argsStart).strip();
                var inputArgs = slice.substring(argsStart + "<".length());
                var args = this.parseValues(inputArgs, this::parseAndModifyType);
                return new Generic(base, args);
            }
        }

        return new Content(input);
    } */
/* private */ /* State */ foldStatementChar(/* State */ state, /* char */ c)/* {
        var appended = state.append(c);
        if (c == ';' && appended.isLevel()) {
            return appended.advance();
        }
        if (c == '}' && appended.isShallow()) {
            return appended.advance().exit();
        }
        if (c == '{') {
            return appended.enter();
        }
        if (c == '}') {
            return appended.exit();
        }
        else {
            return appended;
        }
    } */
