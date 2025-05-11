"use strict";
/* private */ class Some {
    /* @Override
        public  */ map(mapper) {
        return CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        (CompileState[structures = magma.Main$Lists$JVMList]);
        a8dfcc;
        (this.value);
        ;
    }
    /* @Override
        public */ isPresent() {
        return true;
    }
    /* @Override
        public */ orElse(other) {
        return this.value;
    }
    /* @Override
        public */ filter(predicate) {
        return CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        ( /* this.value) ? this : new None<>( */);
    }
    /* @Override
        public */ orElseGet(supplier) {
        return this.value;
    }
    /* @Override
        public */ or(other) {
        return this;
    }
    /* @Override
        public  */ flatMap(mapper) {
        return CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        (this.value);
    }
    /* @Override
        public */ isEmpty() {
        return false;
    }
}
/* private static */ class None {
    /* @Override
        public  */ map(mapper) {
        return CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        ();
    }
    /* @Override
        public */ isPresent() {
        return false;
    }
    /* @Override
        public */ orElse(other) {
        return other;
    }
    /* @Override
        public */ filter(predicate) {
        return CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        ();
    }
    /* @Override
        public */ orElseGet(supplier) {
        return CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        ();
    }
    /* @Override
        public */ or(other) {
        return CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        ();
    }
    /* @Override
        public  */ flatMap(mapper) {
        return CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        ();
    }
    /* @Override
        public */ isEmpty() {
        return true;
    }
}
/* private */ class HeadedIterator {
    /* @Override
        public  */ fold(initial, folder) {
        let current = initial; /* while (true) {
                R finalCurrent = current;
                var optional = this.head.next().map(inner -> folder.apply(finalCurrent, inner));
                if (optional.isPresent()) {
                    current = optional.orElse(null);
                }
                else {
                    return current;
                }
            } */
    }
    /* @Override
        public  */ map(mapper) {
        return CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        (CompileState[structures = magma.Main$Lists$JVMList]);
        a8dfcc;
        ( /* ) -> this.head.next().map(mapper */);
        ;
    }
    /* @Override
        public  */ collect(collector) {
        return CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        (CompileState[structures = magma.Main$Lists$JVMList]);
        a8dfcc;
        (), ; /*  collector::fold */
        ;
    }
}
/* private static final */ class JVMList {
    JVMList(elements) {
        let /* this.elements  */ = elements;
    }
    JVMList() {
        /* this(new ArrayList<>()) */ ;
    }
    /* @Override
            public */ add(element) {
        /* this.elements.add(element) */ ;
        return this;
    }
    /* @Override
            public */ iterate() {
        return CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        ( /* new RangeHead(this.elements.size())).map(this.elements::get */);
    }
    /* @Override
            public */ removeLast() {
        let slice = CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        ( /* 0 */,  /*  this.elements.size() - 1 */);
        let last = CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        ();
        return CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        (CompileState[structures = magma.Main$Lists$JVMList]);
        a8dfcc;
        ( /* new JVMList<>(slice */), ; /*  last) */
        ;
    }
    /* @Override
            public */ get(index) {
        return CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        (index);
    }
}
/* private static */ class Lists /*  */ {
    /* public static  */ empty() {
        return CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        ();
    }
    /* public static  */ of(elements) {
        return CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        (CompileState[structures = magma.Main$Lists$JVMList]);
        a8dfcc;
        (CompileState[structures = magma.Main$Lists$JVMList]);
        a8dfcc;
        (elements);
        ;
    }
}
/* private static */ class DivideState /*  */ {
    DivideState(input, index, segments, buffer, depth) {
        let /* this.segments  */ = segments;
        let /* this.buffer  */ = buffer;
        let /* this.depth  */ = depth;
        let /* this.input  */ = input;
        let /* this.index  */ = index;
    }
    DivideState(input) {
        /* this(input, 0, Lists.empty(), new StringBuilder(), 0) */ ;
    }
    /* private */ advance() {
        let /* this.segments  */ = CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        (CompileState[structures = magma.Main$Lists$JVMList]);
        a8dfcc;
        ();
        ;
        let /* this.buffer  */ = CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        ();
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
        return CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        ();
    }
    /* public */ popAndAppendToTuple() {
        return CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        (CompileState[structures = magma.Main$Lists$JVMList]);
        a8dfcc;
        (CompileState[structures = magma.Main$Lists$JVMList]);
        a8dfcc;
        (tuple.left, /* tuple.right.append(tuple */ .left);
        ;
    }
    /* public */ popAndAppendToOption() {
        return CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        ( /* ).map(Tuple::right */);
    }
}
/* private static */ class ListCollector {
    /* @Override
        public */ createInitial() {
        return CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        ();
    }
    /* @Override
        public */ fold(current, element) {
        return CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        (element);
    }
}
/* private */ class Tuple {
}
/* public */ class Main /*  */ {
    /* private static class RangeHead implements Head<Integer> {
        private final int length;
        private int counter;

        */ RangeHead(length) {
        let /* this.length  */ = length; /* }

        @Override
        public Option<Integer> next() {
            if (this.counter < this.length) {
                var value = this.counter;
                this.counter++;
                return new Some<>(value);
            } */
        return CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        (); /* } */
    }
    /* private */ CompileState(structures) {
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
    /* private */ Definition(maybeBefore, type, name, typeParams) {
    }
    /* public static */ main() {
    }
    /* private static */ compile(input) {
        let tuple = CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        (CompileState[structures = magma.Main$Lists$JVMList]);
        a8dfcc;
        (), input, ; /*  Main::compileRootSegment */
        ;
        let joined = CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        ( /* ).collect(new Joiner()).orElse("" */);
        return joined + tuple.right;
    }
    /* private static */ compileStatements(state, input, mapper) {
        return CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        (state, input,  /*  Main::foldStatementChar */, mapper,  /*  Main::mergeStatements */);
    }
    /* private static */ compileAll(state, input, folder, mapper, merger) {
        let parsed = CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        (state, input, folder, mapper);
        let generated = CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        (merger, parsed.right);
        return CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        (parsed.left, generated);
    }
    /* private static */ generateAll(merger, elements) {
        return CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        (CompileState[structures = magma.Main$Lists$JVMList]);
        a8dfcc;
        ( /* new StringBuilder( */),
        ; /*  merger)
                .toString( */
        ;
    }
    /* private static */ parseAll(state, input, folder, mapper) {
        return CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        (input,  /*  folder).iterate().fold(new Tuple<>(state */, CompileState[structures = magma.Main$Lists$JVMList]);
        a8dfcc;
        ( /* ) */),  /*  (tuple */,
        ; /*  element) -> {
            var state1 = tuple.left;
            var right = tuple.right;

            var applied = mapper.apply(state1, element);
            return new Tuple<>(applied.left, right.add(applied.right));
        } */
        ;
    }
    /* private static */ mergeStatements(stringBuilder, str) {
        return CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        (str);
    }
    /* private static */ divideAll(input, folder) {
        let current = CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        (input); /* while (true) {
            var maybePopped = current.pop().map(tuple -> {
                return foldSingleQuotes(tuple)
                        .or(() -> foldDoubleQuotes(tuple))
                        .orElseGet(() -> folder.apply(tuple.right, tuple.left));
            });

            if (maybePopped.isPresent()) {
                current = maybePopped.orElse(current);
            }
            else {
                break;
            }
        } */
        return CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        ().segments;
    }
    /* private static */ foldDoubleQuotes(tuple) {
        return CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        ();
    }
    /* private static */ foldSingleQuotes(tuple) {
        return CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        ();
    }
    /* private static */ foldStatementChar(state, c) {
        let append = CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        (c); /* if (c == ';' && append.isLevel()) {
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
    /* private static */ compileRootSegment(state, input) {
        let stripped = CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        (); /* if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return new Tuple<>(state, "");
        } */
        return CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        (stripped,  /*  0 */, CompileState[structures = magma.Main$Lists$JVMList]);
        a8dfcc;
        (CompileState[structures = magma.Main$Lists$JVMList]);
        a8dfcc;
        ( /* ) -> new Tuple<>(state, generatePlaceholder(stripped */);
        ;
    }
    /* private static */ compileClass(stripped, depth, state) {
        return CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        (stripped, "class ", "class ", state);
    }
    /* private static */ structure(stripped, sourceInfix, targetInfix, state) {
        return CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        (stripped, sourceInfix,  /*  (beforeInfix */,
        /*  right) -> {
            return first(right, "{", (beforeContent, withEnd) -> {
                var strippedWithEnd = withEnd.strip();
                return suffix(strippedWithEnd, "}", content1 -> {
                    return first(beforeContent, "<", new BiFunction<String, String, Option<Tuple<CompileState */
            ,
        /*  String>>>() {
            @Override
            public Option<Tuple<CompileState, String>> apply(String name, String withTypeParams) {
                return first(withTypeParams, ">", new BiFunction<String, String, Option<Tuple<CompileState */
            ,
        /*  String>>>() {
            @Override
            public Option<Tuple<CompileState, String>> apply(String typeParamsString, String afterTypeParams) {
                var typeParams = parseValues(state, typeParamsString, (state1, s) -> new Tuple<>(state1, s.strip()));
                return assemble(typeParams.left, targetInfix, beforeInfix, name, content1, typeParams.right, afterTypeParams);
            }
        });
    }
}).or(() -> {
    return assemble(state, targetInfix, beforeInfix, beforeContent, content1, Lists.empty(), "");
});
});
});
} */);
    }
    /* private static */ assemble(state, targetInfix, beforeInfix, rawName, content, typeParams, afterTypeParams) {
        let name = CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        (); /* if (!isSymbol(name)) {
            return new None<>();
        } */
        let joinedTypeParams = /*  typeParams.iterate().collect(new Joiner(", ")).map(inner -> "<"  */ +inner + CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        ("");
        let statements = CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        (state, content,  /*  (state0 */, CompileState[structures = magma.Main$Lists$JVMList]);
        a8dfcc;
        ( /* state0 */, input,  /*  1 */);
        ;
        let generated = CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        (CompileState[structures = magma.Main$Lists$JVMList]);
        a8dfcc;
        ();
        +targetInfix + name + joinedTypeParams + CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        (afterTypeParams) + " {" + statements.right + "\n}\n";
        return CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        (CompileState[structures = magma.Main$Lists$JVMList]);
        a8dfcc;
        ( /* statements.left.addStructure(generated */), ; /*  "") */
        ;
    }
    /* private static */ isSymbol(input) {
        return true;
    }
    /* private static  */ suffix(input, suffix, mapper) {
        let slice = CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        ( /* 0 */, CompileState[structures = magma.Main$Lists$JVMList]);
        a8dfcc;
        ( /* ) - suffix.length( */);
        ;
        return CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        (slice);
    }
    /* private static */ compileClassSegment(state, input, depth) {
        return CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        (input, CompileState[structures = magma.Main$Lists$JVMList]);
        a8dfcc;
        (CompileState[structures = magma.Main$Lists$JVMList]);
        a8dfcc;
        ( /* ) -> compileClass(input, depth, state))
                .or(() -> structure(input, "interface ", "interface ", state))
                .or(() -> structure(input, "record ", "class ", state))
                .or(() -> method(state, input, depth))
                .or(() -> compileDefinitionStatement(input, depth, state))
                .orElseGet(() -> new Tuple<>(state, generatePlaceholder(input */);
        ;
    }
    /* private static */ compileWhitespace(input, state) {
        return CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        ();
    }
    /* private static */ method(state, input, depth) {
        return; /* first(input, "(", (definition, withParams) -> {
            return first(withParams, ")", (params, rawContent) -> {
                var definitionTuple = parseDefinition(state, definition)
                        .map(definition1 -> {
                            var paramsTuple = compileValues(state, params, Main::compileParameter);
                            var generated = definition1.right.generateWithParams("("  */
        +paramsTuple.right + /*  ")");
        return new Tuple<>(paramsTuple.left, generated);
    })
    .orElseGet(() -> new Tuple<>(state, generatePlaceholder(definition)));

var content = rawContent.strip();
var indent = createIndent(depth);
if (content.equals(";")) {
var s = indent  */
            +definitionTuple.right + /*  ";";
        return new Some<>(new Tuple<>(definitionTuple.left, s));
    }

    if (content.startsWith("{") && content.endsWith("}")) {
        var statementsTuple = compileStatements(definitionTuple.left, content.substring(1, content.length() - 1), (state1, input1) -> compileFunctionalSegment(state1, input1, depth  */
            + +definitionTuple.right + " {" + statementsTuple.right + indent + CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        ( /* new Tuple<>(statementsTuple */.left,
        /*  generated));
    }

    return new None<>();
});
} */);
    }
    /* private static */ compileFunctionalSegment(state, input, depth) {
        let stripped = CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        (); /* if (stripped.isEmpty()) {
            return new Tuple<>(state, "");
        } */
        return CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        (stripped, ";",
        /*  s -> {
            var tuple = statementValue(state, s);
            return new Some<>(new Tuple<>(tuple.left, createIndent(depth */) + tuple.right + CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        ( /* () -> {
        return new Tuple<>(state, generatePlaceholder(stripped));
    } */);
    }
    /* private static */ statementValue(state, input) {
        let stripped = CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        (); /* if (stripped.startsWith("return ")) {
            var value = stripped.substring("return ".length());
            var tuple = value(state, value);
            return new Tuple<>(tuple.left, "return " + tuple.right);
        } */
        return; /* first(stripped, "=", (s, s2) -> {
            var definitionTuple = compileDefinition(state, s);
            var valueTuple = value(definitionTuple.left, s2);
            return new Some<>(new Tuple<>(valueTuple.left, "let "  */
        +definitionTuple.right + " = " + CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        ( /* () -> {
        return new Tuple<>(state, generatePlaceholder(stripped));
    } */);
    }
    /* private static */ value(state, input) {
        return CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        (state, CompileState[structures = magma.Main$Lists$JVMList]);
        a8dfcc;
        (CompileState[structures = magma.Main$Lists$JVMList]);
        a8dfcc;
        ( /* ) -> symbolValue(state, input))
                .or(() -> stringValue(state, input))
                .or(() -> dataAccess(state, input))
                .or(() -> invocation(state, input))
                .orElseGet(() -> new Tuple<CompileState, String>(state, generatePlaceholder(input */);
        ;
    }
    /* private static */ invocation(state, input) {
        return; /* suffix(input.strip(), ")", withoutEnd -> {
            return first(withoutEnd, "(", (callerString, argumentsString) -> {
                var callerTuple = value(state, callerString);
                var argumentsTuple = compileValues(callerTuple.left, argumentsString, Main::value);

                return new Some<>(new Tuple<>(argumentsTuple.left, callerTuple */
        left + "(" + argumentsTuple.right +
        /*  ")"));
    });
}) */;
    }
    /* private static */ dataAccess(state, input) {
        return; /* last(input.strip(), ".", (parent, property) -> {
            var value = value(state, parent);
            if (!isSymbol(property)) {
                return new None<>();
            }
            return new Some<>(new Tuple<>(value.left, value */
        right + "." +
        /*  property));
    }) */;
    }
    /* private static */ stringValue(state, input) {
        let stripped = CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        (); /* if (stripped.startsWith("\"") && stripped.endsWith("\"")) {
            return new Some<>(new Tuple<>(state, stripped));
        } */
        return CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        ();
    }
    /* private static */ symbolValue(state, value) {
        let stripped = CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        (); /* if (isSymbol(stripped)) {
            return new Some<>(new Tuple<>(state, stripped));
        } */
        return CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        ();
    }
    /* private static */ operation(state, value) {
        return /* first(value, " */ +
            .right + " + " +
        /*  rightTuple.right));
    }) */;
    }
    /* private static */ compileValues(state, params, mapper) {
        let parsed = CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        (state, params, mapper);
        let generated = CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        (parsed.right);
        return CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        (parsed.left, generated);
    }
    /* private static */ generateValues(elements) {
        return CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        ( /* Main::mergeValues */, elements);
    }
    /* private static */ parseValues(state, input, mapper) {
        return CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        (state, input,  /*  Main::foldValueChar */, mapper);
    }
    /* private static */ compileParameter(state, input) {
        return CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        (state, input);
    }
    /* private static */ compileDefinition(state, input) {
        return CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        (state, CompileState[structures = magma.Main$Lists$JVMList]);
        a8dfcc;
        (CompileState[structures = magma.Main$Lists$JVMList]);
        a8dfcc;
        ( /* Tuple<CompileState, Definition> tuple) -> new Tuple<>(tuple.left, tuple.right.generate()))
                .orElseGet(() -> new Tuple<>(state, generatePlaceholder(input */);
        ;
    }
    /* private static */ mergeValues(cache, element) {
        return CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        ( /* ", ").append(element */);
    }
    /* private static */ createIndent(depth) {
        return "\n" + CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        (depth);
    }
    /* private static */ compileDefinitionStatement(input, depth, state) {
        return CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        (CompileState[structures = magma.Main$Lists$JVMList]);
        a8dfcc;
        (), ";",
        ; /*  withoutEnd -> {
            return parseDefinition(state, withoutEnd).map(result -> {
                var generated = createIndent(depth */
        +CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        () + CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        (result.left,
        /*  generated);
    });
} */);
    }
    /* private static */ parseDefinition(state, input) {
        return CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        (CompileState[structures = magma.Main$Lists$JVMList]);
        a8dfcc;
        (), " ",  /*  (beforeName */,
        ; /*  name) -> {
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
        } */
        ;
    }
    /* private static */ getStringStringTuple(beforeName) {
        let divisions = CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        (beforeName,  /*  Main::foldTypeSeparator */);
        return CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        ( /* ).map(removed -> {
            var left = removed.left.iterate().collect(new Joiner(" ")).orElse("");
            var right = removed.right;

            return new Tuple<>(left, right);
        } */);
    }
    /* private static */ foldTypeSeparator(state, c) {
        let appended = CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        (c); /* if (c == '<') {
            return appended.enter();
        } */ /* if (c == '>') {
            return appended.exit();
        } */
        return appended;
    }
    /* private static */ assembleDefinition(state, beforeTypeParams, name, typeParams, type) {
        let type1 = CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        (state, type);
        let node = CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        (beforeTypeParams, /* type1 */ .right, CompileState[structures = magma.Main$Lists$JVMList]);
        a8dfcc;
        (), typeParams;
        ;
        return CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        ( /* new Tuple<>(type1 */.left,  /*  node) */);
    }
    /* private static */ foldValueChar(state, c) {
        let appended = CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        (c); /* if (c == '<') {
            return appended.enter();
        } */ /* if (c == '>') {
            return appended.exit();
        } */
        return appended;
    }
    /* private static */ type(state, input) {
        let stripped = CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        (); /* if (stripped.equals("int")) {
            return new Tuple<>(state, "number");
        } */ /* if (stripped.equals("String")) {
            return new Tuple<>(state, "string");
        } */ /* if (isSymbol(stripped)) {
            return new Tuple<>(state, stripped);
        } */
        return CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        (state, CompileState[structures = magma.Main$Lists$JVMList]);
        a8dfcc;
        (CompileState[structures = magma.Main$Lists$JVMList]);
        a8dfcc;
        ( /* ) -> varArgs(state, input))
                .orElseGet(() -> new Tuple<>(state, generatePlaceholder(stripped */);
        ;
    }
    /* private static */ varArgs(state, input) {
        return; /* suffix(input, "...", s -> {
            var inner = type(state, s);
            return new Some<>(new Tuple<>(inner.left, inner */
        right +
        /*  "[]"));
    }) */;
    }
    /* private static */ template(state, input) {
        return; /* suffix(input.strip(), ">", withoutEnd -> {
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
                    return new Some<>(new Tuple<>(argumentsState, "["  */
        +CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        ( /* 0 */) + ", " + CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        ( /* 1 */) + /*  "]"));
    }

    return new Some<>(new Tuple<>(argumentsState, strippedBase  */
            +"<" + CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        (arguments) +
        /*  ">"));
    });
}) */;
    }
    /* private static */ generate(arguments, returns) {
        let joined = CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        ( /* )
                .collect(new Joiner(", "))
                .orElse("" */);
        return "(" + joined + ") => " + returns;
    }
    /* private static  */ last(input, infix, mapper) {
        return CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        (input, infix,  /*  Main::findLast */, mapper);
    }
    /* private static */ findLast(input, infix) {
        let index = CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        (infix);
        return CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        ( /* ) : new Some<>(index */);
    }
    /* private static  */ first(input, infix, mapper) {
        return CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        (input, infix,  /*  Main::findFirst */, mapper);
    }
    /* private static  */ infix(input, infix, locator, mapper) {
        return; /* split(() -> locator.apply(input, infix).map(index -> {
            var left = input.substring(0, index);
            var right = input.substring(index  */
        +CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        ( /* ));
        return new Tuple<>(left */
            ,
        /*  right);
    }) */
            , mapper);
    }
    /* private static  */ split(splitter, mapper) {
        return CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        (CompileState[structures = magma.Main$Lists$JVMList]);
        a8dfcc;
        ( /* tuple -> mapper.apply(tuple.left, tuple */.right);
        ;
    }
    /* private static */ findFirst(input, infix) {
        let index = CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        (infix);
        return CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        ( /* ) : new Some<>(index */);
    }
    /* private static */ generatePlaceholder(input) {
        let replaced = CompileState[structures = magma.Main$Lists$JVMList];
        a8dfcc;
        ("/*", "content-start")
            .replace("*/", "content-end");
        return "/* " + replaced + " */";
    }
}
/*  */ 
