"use strict";
/* private static */ class Lists {
    /* private static final class JVMList<T> implements List<T> {
            private final java.util.List<T> elements;

            */ JVMList(elements) {
        /* this.elements = elements */ ;
        /* }

            public JVMList() {
                this(new ArrayList<>()) */ ;
        /* }

            @Override
            public List<T> add(T element) {
                this.elements.add(element) */ ;
        return this;
        /* }

            @Override
            public Iterator<T> iterate() {
                return new HeadedIterator<>(new RangeHead(this.elements.size())).map(this.elements::get) */ ; /* }

    @Override
    public Option<Tuple<List<T>, T>> removeLast() {
        if (this.elements.isEmpty()) {
            return new None<>();
        } */
        /* var slice = this.elements.subList(0, this.elements.size() - 1) */ ;
        /* var last = this.elements.getLast() */ ;
        return /* new Some<>(new Tuple<List<T>, T>(new JVMList<>(slice), last)) */;
        /* }

            @Override
            public T get(int index) {
                return this.elements.get(index) */ ; /* } */
    }
    /* public static  */ empty() {
        return /* new JVMList<>() */;
    }
    /* public static  */ of(elements /* T... */) {
        return /* new JVMList<>(new ArrayList<>(Arrays.asList(elements))) */;
    }
}
/* private static */ class DivideState {
    DivideState(input, index, segments, buffer, depth) {
        /* this.segments = segments */ ;
        /* this.buffer = buffer */ ;
        /* this.depth = depth */ ;
        /* this.input = input */ ;
        /* this.index = index */ ;
    }
    DivideState(input) {
        /* this(input, 0, Lists.empty(), new StringBuilder(), 0) */ ;
    }
    /* private */ advance() {
        /* this.segments = this.segments.add(this.buffer.toString()) */ ;
        /* this.buffer = new StringBuilder() */ ;
        return this;
    }
    /* private */ append(c) {
        /* this.buffer.append(c) */ ;
        return this;
    }
    /* public */ enter() {
        /* this.depth++ */ ;
        return this;
    }
    /* public */ isLevel() {
        return /* this.depth == 0 */;
    }
    /* public */ exit() {
        /* this.depth-- */ ;
        return this;
    }
    /* public */ isShallow() {
        return /* this.depth == 1 */;
    }
    /* public */ pop() {
        return /* new None<>() */;
    }
    /* public */ popAndAppendToTuple() {
        return /* this.pop().map(tuple -> new Tuple<>(tuple.left, tuple.right.append(tuple.left))) */;
    }
    /* public */ popAndAppendToOption() {
        return /* this.popAndAppendToTuple().map(Tuple::right) */;
    }
}
/* public */ class Main {
}
implements;
Option < T > {};
public(mapper, (T) => R);
Option < R > {
    return:  /* new None<>() */
}; /*

private record HeadedIterator<T>(Head<T> head) implements Iterator<T> {
    @Override
    public <R> R fold(R initial, BiFunction<R, T, R> folder) {
        var current = initial;
        while (true) {
            R finalCurrent = current;
            var optional = this.head.next().map(inner -> folder.apply(finalCurrent, inner));
            if (optional.isPresent()) {
                current = optional.orElse(null);
            }
            else {
                return current;
            }
        }
    }

    @Override
    public <R> Iterator<R> map(Function<T, R> mapper) {
        return new HeadedIterator<>(() -> this.head.next().map(mapper));
    }

    @Override
    public <R> R collect(Collector<T, R> collector) {
        return this.fold(collector.createInitial(), collector::fold);
    }
} */
/* private static class RangeHead implements Head<Integer> {
    private final int length;
    private int counter;

    */ RangeHead(length, number);
public;
{
    /* this.length = length */ ; /* }

    @Override
    public Option<Integer> next() {
        if (this.counter < this.length) {
            var value = this.counter;
            this.counter++;
            return new Some<>(value);
        } */
    return /* new None<>() */; /* } */
}
/* private */ CompileState(structures, (List));
record;
{ /* public CompileState() {
        this(Lists.empty());
    } */ /* public CompileState addStructure(String structure) {
    return new CompileState(this.structures.add(structure));
} */
} /*

private record Joiner(String delimiter) implements Collector<String, Option<String>> {
    private Joiner() {
        this("");
    }

    @Override
    public Option<String> createInitial() {
        return new None<>();
    }

    @Override
    public Option<String> fold(Option<String> current, String element) {
        return new Some<>(current.map(inner -> inner + this.delimiter + element).orElse(element));
    }
} */
/* private */ Definition(maybeBefore, (Option), type, string, name, string, typeParams, (List));
record;
{ /* private String generate() {
        return this.generateWithParams("");
    } */ /* public String generateWithParams(String params) {
    var joined = this.typeParams.iterate()
            .collect(new Joiner())
            .map(inner -> "<" + inner + ">")
            .orElse("");

    var before = this.maybeBefore
            .filter(value -> !value.isEmpty())
            .map(Main::generatePlaceholder)
            .map(inner -> inner + " ")
            .orElse("");

    return before + this.name + joined + params + " : " + this.type;
} */
}
/* private static class ListCollector<T> implements Collector<T, List<T>> {
    @Override
    public */ createInitial();
List < T > {
    return:  /* Lists.empty() */
};
/* private record */ B > (left);
A, right;
B;
{
}
/* public static */ main();
void { /* try {
        var parent = Paths.get(".", "src", "java", "magma");
        var source = parent.resolve("Main.java");
        var target = parent.resolve("main.ts");

        var input = Files.readString(source);
        Files.writeString(target, compile(input));

        new ProcessBuilder("cmd", "/c", "npm", "exec", "tsc")
                .inheritIO()
                .start()
                .waitFor();
    } */ /* catch (IOException | InterruptedException e) {
    throw new RuntimeException(e);
} */};
/* private static */ compile(input, string);
string;
{
    /* var tuple = compileStatements(new CompileState(), input, Main::compileRootSegment) */ ;
    /* var joined = tuple.left.structures.iterate().collect(new Joiner()).orElse("") */ ;
    return joined +  /*  tuple.right */;
}
/* private static */ compileStatements(state, CompileState, input, string, mapper, (CompileState, string) => [CompileState, string]);
[CompileState, string];
{
    return /* compileAll(state, input, Main::foldStatementChar, mapper, Main::mergeStatements) */;
}
/* private static */ compileAll(state, CompileState, input, string, folder, (DivideState, Character) => DivideState, mapper, (CompileState, string) => [CompileState, string], merger, (StringBuilder, string) => StringBuilder);
[CompileState, string];
{
    /* var parsed = parseAll(state, input, folder, mapper) */ ;
    /* var generated = generateAll(merger, parsed.right) */ ;
    return /* new Tuple<>(parsed.left, generated) */;
}
/* private static */ generateAll(merger, (StringBuilder, string) => StringBuilder, elements, (List));
string;
{
    return /* elements
            .iterate()
            .fold(new StringBuilder(), merger)
            .toString() */;
}
/* private static */ parseAll(state, CompileState, input, string, folder, (DivideState, Character) => DivideState, mapper, (CompileState, string) => [CompileState, string]);
[CompileState, (List)];
{
    return /* divideAll(input, folder).iterate().fold(new Tuple<>(state, Lists.empty()), (tuple, element) -> {
        var state1 = tuple.left;
        var right = tuple.right;

        var applied = mapper.apply(state1, element);
        return new Tuple<>(applied.left, right.add(applied.right));
    }) */;
}
/* private static */ mergeStatements(stringBuilder, StringBuilder, str, string);
StringBuilder;
{
    return /* stringBuilder.append(str) */;
}
/* private static */ divideAll(input, string, folder, (DivideState, Character) => DivideState);
List < string > {
    return:  /* current.advance().segments */
};
/* private static */ foldDoubleQuotes(tuple, [Character, DivideState]);
Option < DivideState > {
    return:  /* new None<>() */
};
/* private static */ foldSingleQuotes(tuple, [Character, DivideState]);
Option < DivideState > {
    return:  /* new None<>() */
};
/* private static */ foldStatementChar(state, DivideState, c, char);
DivideState;
{
    /* var append = state.append(c) */ ; /* if (c == ';' && append.isLevel()) {
        return append.advance();
    } */ /* if (c == '}' && append.isShallow()) {
        return append.advance().exit();
    } */ /* if (c == '{' || c == '(') {
        return append.enter();
    } */ /* if (c == '}' || c == ')') {
        return append.exit();
    } */
    return append;
}
/* private static */ compileRootSegment(state, CompileState, input, string);
[CompileState, string];
{
    /* var stripped = input.strip() */ ; /* if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
        return new Tuple<>(state, "");
    } */
    return /* compileClass(stripped, 0, state)
            .orElseGet(() -> new Tuple<>(state, generatePlaceholder(stripped))) */;
}
/* private static */ compileClass(stripped, string, depth, number, state, CompileState);
Option < [CompileState, string] > {
    return:  /* structure(stripped, "class ", "class ", state) */
};
/* private static */ structure(stripped, string, sourceInfix, string, targetInfix, string, state, CompileState);
Option < [CompileState, string] > {
    return /* first(stripped, sourceInfix, (beforeInfix, right) -> {
        return first(right, "{", (name, withEnd) -> {
            var strippedWithEnd = withEnd.strip();
            return suffix(strippedWithEnd, "}", content1 -> {
                var strippedName = name.strip();
                if (!isSymbol(strippedName)) {
                    return new None<>();
                }

                var statements = compileStatements(state, content1, (state0, input) -> compileClassSegment(state0, input, 1));
                var generated = generatePlaceholder(beforeInfix.strip())  */: /* first(stripped, sourceInfix, (beforeInfix, right) -> {
        return first(right, "{", (name, withEnd) -> {
            var strippedWithEnd = withEnd.strip();
            return suffix(strippedWithEnd, "}", content1 -> {
                var strippedName = name.strip();
                if (!isSymbol(strippedName)) {
                    return new None<>();
                }

                var statements = compileStatements(state, content1, (state0, input) -> compileClassSegment(state0, input, 1));
                var generated = generatePlaceholder(beforeInfix.strip())  */ +targetInfix + strippedName + " {" + /*  statements.right  */ + /*  "\n}\n";
    return new Some<>(new Tuple<>(statements.left.addStructure(generated), ""));
});
});
}) */
};
/* private static */ isSymbol(input, string);
boolean;
{ /* for (var i = 0; i < input.length(); i++) {
        var c = input.charAt(i);
        if (Character.isLetter(c)) {
            continue;
        }
        return false;
    } */
    return true;
}
/* private static  */ suffix(input, string, suffix, string, mapper, (string) => Option);
Option < T > {
    return:  /* mapper.apply(slice) */
};
/* private static */ compileClassSegment(state, CompileState, input, string, depth, number);
[CompileState, string];
{
    return /* compileWhitespace(input, state)
            .or(() -> compileClass(input, depth, state))
            .or(() -> structure(input, "interface ", "interface ", state))
            .or(() -> structure(input, "record ", "class ", state))
            .or(() -> method(state, input, depth))
            .or(() -> compileDefinitionStatement(input, depth, state))
            .orElseGet(() -> new Tuple<>(state, generatePlaceholder(input))) */;
}
/* private static */ compileWhitespace(input, string, state, CompileState);
Option < [CompileState, string] > {
    return:  /* new None<>() */
};
/* private static */ method(state, CompileState, input, string, depth, number);
Option < [CompileState, string] > {
    return /* first(input, "(", (definition, withParams) -> {
        return first(withParams, ")", (params, rawContent) -> {
            var definitionTuple = parseDefinition(state, definition)
                    .map(definition1 -> {
                        var paramsTuple = compileValues(state, params, Main::compileParameter);
                        var generated = definition1.right.generateWithParams("("  */: /* first(input, "(", (definition, withParams) -> {
        return first(withParams, ")", (params, rawContent) -> {
            var definitionTuple = parseDefinition(state, definition)
                    .map(definition1 -> {
                        var paramsTuple = compileValues(state, params, Main::compileParameter);
                        var generated = definition1.right.generateWithParams("("  */ + + + + + + +" {" + /*  statementsTuple.right  */ +indent +
    /*  "}";
    return new Some<>(new Tuple<>(statementsTuple.left, generated));
}

return new None<>();
});
}) */
};
/* private static */ compileFunctionalSegment(state, CompileState, input, string, depth, number);
[CompileState, string];
{
    /* var stripped = input.strip() */ ; /* if (stripped.isEmpty()) {
        return new Tuple<>(state, "");
    } */
    return; /* suffix(stripped, ";", s -> {
        var tuple = statementValue(state, s);
        return new Some<>(new Tuple<>(tuple.left, createIndent(depth)  */
    + + /*  ";"));
}).orElseGet(() -> {
    return new Tuple<>(state, generatePlaceholder(stripped));
}) */;
}
/* private static */ statementValue(state, CompileState, input, string);
[CompileState, string];
{
    /* var stripped = input.strip() */ ; /* if (stripped.startsWith("return ")) {
        var value = stripped.substring("return ".length());
        var tuple = value(state, value);
        return new Tuple<>(tuple.left, "return " + tuple.right);
    } */
    return /* new Tuple<>(state, generatePlaceholder(stripped)) */;
}
/* private static */ value(state, CompileState, input, string);
[CompileState, string];
{
    return /* operation(state, input)
            .or(() -> symbolValue(state, input))
            .or(() -> stringValue(state, input))
            .orElseGet(() -> new Tuple<CompileState, String>(state, generatePlaceholder(input))) */;
}
/* private static */ stringValue(state, CompileState, input, string);
Option < [CompileState, string] > {
    return:  /* new None<>() */
};
/* private static */ symbolValue(state, CompileState, value, string);
Option < [CompileState, string] > {
    return:  /* new None<>() */
};
/* private static */ operation(state, CompileState, value, string);
Option < [CompileState, string] > {
    return /* first(value, " */: /* first(value, " */ + +" + " +
    /*  rightTuple.right));
}) */
};
/* private static */ compileValues(state, CompileState, params, string, mapper, (CompileState, string) => [CompileState, string]);
[CompileState, string];
{
    /* var parsed = parseValues(state, params, mapper) */ ;
    /* var generated = generateValues(parsed.right) */ ;
    return /* new Tuple<>(parsed.left, generated) */;
}
/* private static */ generateValues(elements, (List));
string;
{
    return /* generateAll(Main::mergeValues, elements) */;
}
/* private static */ parseValues(state, CompileState, input, string, mapper, (CompileState, string) => [CompileState, string]);
[CompileState, (List)];
{
    return /* parseAll(state, input, Main::foldValueChar, mapper) */;
}
/* private static */ compileParameter(state, CompileState, input, string);
[CompileState, string];
{ /* if (input.isBlank()) {
        return new Tuple<>(state, "");
    } */
    return /* parseDefinition(state, input)
            .map((Tuple<CompileState, Definition> tuple) -> new Tuple<>(tuple.left, tuple.right.generate()))
            .orElseGet(() -> new Tuple<>(state, generatePlaceholder(input))) */;
}
/* private static */ mergeValues(cache, StringBuilder, element, string);
StringBuilder;
{ /* if (cache.isEmpty()) {
        return cache.append(element);
    } */
    return /* cache.append(", ").append(element) */;
}
/* private static */ createIndent(depth, number);
string;
{
    return "\n" +  /*  "\t".repeat(depth) */;
}
/* private static */ compileDefinitionStatement(input, string, depth, number, state, CompileState);
Option < [CompileState, string] > {
    return /* suffix(input.strip(), ";", withoutEnd -> {
        return parseDefinition(state, withoutEnd).map(result -> {
            var generated = createIndent(depth)  */: /* suffix(input.strip(), ";", withoutEnd -> {
        return parseDefinition(state, withoutEnd).map(result -> {
            var generated = createIndent(depth)  */ + + /*  ";";
    return new Tuple<>(result.left, generated);
});
}) */
};
/* private static */ parseDefinition(state, CompileState, input, string);
Option < [CompileState, Definition] > {
    return:  /* last(input.strip(), " ", (beforeName, name) -> {
        return split(() -> getStringStringTuple(beforeName), (beforeType, type) -> {
            return suffix(beforeType.strip(), ">", withoutTypeParamStart -> {
                return first(withoutTypeParamStart, "<", (beforeTypeParams, typeParamsString) -> {
                    var typeParams = parseValues(state, typeParamsString, (state1, s) -> new Tuple<>(state1, s.strip()));
                    return assembleDefinition(typeParams.left, new Some<String>(beforeTypeParams), name, typeParams.right, type);
                });
            }).or(() -> {
                return assembleDefinition(state, new Some<String>(beforeType), name, Lists.empty(), type);
            });
        }).or(() -> {
            return assembleDefinition(state, new None<String>(), name, Lists.empty(), beforeName);
        });
    }) */
};
/* private static */ getStringStringTuple(beforeName, string);
Option < [string, string] > {
    return:  /* divisions.removeLast().map(removed -> {
        var left = removed.left.iterate().collect(new Joiner(" ")).orElse("");
        var right = removed.right;

        return new Tuple<>(left, right);
    }) */
};
/* private static */ foldTypeSeparator(state, DivideState, c, Character);
DivideState;
{ /* if (c == ' ' && state.isLevel()) {
        return state.advance();
    } */
    /* var appended = state.append(c) */ ; /* if (c == '<') {
        return appended.enter();
    } */ /* if (c == '>') {
        return appended.exit();
    } */
    return appended;
}
/* private static */ assembleDefinition(state, CompileState, beforeTypeParams, (Option), name, string, typeParams, (List), type, string);
Option < [CompileState, Definition] > {
    return:  /* new Some<>(new Tuple<>(type1.left, node)) */
};
/* private static */ foldValueChar(state, DivideState, c, char);
DivideState;
{ /* if (c == ',' && state.isLevel()) {
        return state.advance();
    } */
    /* var appended = state.append(c) */ ; /* if (c == '<') {
        return appended.enter();
    } */ /* if (c == '>') {
        return appended.exit();
    } */
    return appended;
}
/* private static */ type(state, CompileState, input, string);
[CompileState, string];
{
    /* var stripped = input.strip() */ ; /* if (stripped.equals("int")) {
        return new Tuple<>(state, "number");
    } */ /* if (stripped.equals("String")) {
        return new Tuple<>(state, "string");
    } */ /* if (isSymbol(stripped)) {
        return new Tuple<>(state, stripped);
    } */
    return /* template(state, input).orElseGet(() -> new Tuple<>(state, generatePlaceholder(stripped))) */;
}
/* private static */ template(state, CompileState, input, string);
Option < [CompileState, string] > {
    return /* suffix(input.strip(), ">", withoutEnd -> {
        return first(withoutEnd, "<", (base, argumentsString) -> {
            var strippedBase = base.strip();
            var argumentsTuple = parseValues(state, argumentsString, Main::type);
            var argumentsState = argumentsTuple.left;
            var arguments = argumentsTuple.right;

            if (base.equals("BiFunction")) {
                return new Some<>(new Tuple<>(argumentsState, generate(Lists.of(arguments.get(0), arguments.get(1)), arguments.get(2))));
            }

            if (base.equals("Function")) {
                return new Some<>(new Tuple<>(argumentsState, generate(Lists.of(arguments.get(0)), arguments.get(1))));
            }

            if (base.equals("Predicate")) {
                return new Some<>(new Tuple<>(argumentsState, generate(Lists.of(arguments.get(0)), "boolean")));
            }

            if (base.equals("Supplier")) {
                return new Some<>(new Tuple<>(argumentsState, generate(Lists.empty(), arguments.get(0))));
            }

            if (base.equals("Tuple")) {
                return new Some<>(new Tuple<>(argumentsState, "["  */: /* suffix(input.strip(), ">", withoutEnd -> {
        return first(withoutEnd, "<", (base, argumentsString) -> {
            var strippedBase = base.strip();
            var argumentsTuple = parseValues(state, argumentsString, Main::type);
            var argumentsState = argumentsTuple.left;
            var arguments = argumentsTuple.right;

            if (base.equals("BiFunction")) {
                return new Some<>(new Tuple<>(argumentsState, generate(Lists.of(arguments.get(0), arguments.get(1)), arguments.get(2))));
            }

            if (base.equals("Function")) {
                return new Some<>(new Tuple<>(argumentsState, generate(Lists.of(arguments.get(0)), arguments.get(1))));
            }

            if (base.equals("Predicate")) {
                return new Some<>(new Tuple<>(argumentsState, generate(Lists.of(arguments.get(0)), "boolean")));
            }

            if (base.equals("Supplier")) {
                return new Some<>(new Tuple<>(argumentsState, generate(Lists.empty(), arguments.get(0))));
            }

            if (base.equals("Tuple")) {
                return new Some<>(new Tuple<>(argumentsState, "["  */ + +", " + /*  arguments.get(1)  */ + +"<" + /*  generateValues(arguments)  */ + /*  ">"));
});
}) */
};
/* private static */ generate(arguments, (List), returns, string);
string;
{
    /* var joined = arguments.iterate()
            .collect(new Joiner(", "))
            .orElse("") */ ;
    return "(" + joined + ") => " + returns;
}
/* private static  */ last(input, string, infix, string, mapper, (string, string) => Option);
Option < T > {
    return:  /* infix(input, infix, Main::findLast, mapper) */
};
/* private static */ findLast(input, string, infix, string);
Option < Integer > {
    return:  /* index == -1 ? new None<Integer>() : new Some<>(index) */
};
/* private static  */ first(input, string, infix, string, mapper, (string, string) => Option);
Option < T > {
    return:  /* infix(input, infix, Main::findFirst, mapper) */
};
/* private static  */ infix(input, string, infix, string, locator, (string, string) => Option, mapper, (string, string) => Option);
Option < T > {
    return /* split(() -> locator.apply(input, infix).map(index -> {
        var left = input.substring(0, index);
        var right = input.substring(index  */: /* split(() -> locator.apply(input, infix).map(index -> {
        var left = input.substring(0, index);
        var right = input.substring(index  */ + /*  infix.length());
    return new Tuple<>(left, right);
}), mapper) */
};
/* private static  */ split(splitter, () => Option, mapper, (string, string) => Option);
Option < T > {
    return:  /* splitter.get().flatMap(tuple -> mapper.apply(tuple.left, tuple.right)) */
};
/* private static */ findFirst(input, string, infix, string);
Option < Integer > {
    return:  /* index == -1 ? new None<Integer>() : new Some<>(index) */
};
/* private static */ generatePlaceholder(input, string);
string;
{
    /* var replaced = input
            .replace("content-start", "content-start")
            .replace("content-end", "content-end") */ ;
    return "/* " + replaced + " */";
}
/*  */ 
