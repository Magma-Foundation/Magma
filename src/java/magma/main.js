"use strict";
/* private */ class Some {
    /* @Override
        public  */ map(mapper) {
        return /* new Some<>(mapper.apply(this.value)) */;
    }
    /* @Override
        public */ isPresent() {
        return true;
    }
    /* @Override
        public */ orElse(other) {
        return /* this.value */;
    }
    /* @Override
        public */ filter(predicate) {
        return /* predicate.test(this.value) ? this : new None<>() */;
    }
    /* @Override
        public */ orElseGet(supplier) {
        return /* this.value */;
    }
    /* @Override
        public */ or(other) {
        return this;
    }
    /* @Override
        public  */ flatMap(mapper) {
        return /* mapper.apply(this.value) */;
    }
    /* @Override
        public */ isEmpty() {
        return false;
    }
}
/* private static */ class None {
    /* @Override
        public  */ map(mapper) {
        return /* new None<>() */;
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
        return /* new None<>() */;
    }
    /* @Override
        public */ orElseGet(supplier) {
        return /* supplier.get() */;
    }
    /* @Override
        public */ or(other) {
        return /* other.get() */;
    }
    /* @Override
        public  */ flatMap(mapper) {
        return /* new None<>() */;
    }
    /* @Override
        public */ isEmpty() {
        return true;
    }
}
/* private */ class HeadedIterator {
    /* @Override
        public  */ fold(initial, folder) {
        /* var current = initial */ ; /* while (true) {
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
        return /* new HeadedIterator<>(() -> this.head.next().map(mapper)) */;
    }
    /* @Override
        public  */ collect(collector) {
        return /* this.fold(collector.createInitial(), collector::fold) */;
    }
}
/* private static final */ class JVMList {
    JVMList(elements) {
        /* this.elements = elements */ ;
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
        return /* new HeadedIterator<>(new RangeHead(this.elements.size())).map(this.elements::get) */;
    }
    /* @Override
            public */ removeLast() {
        /* var slice = this.elements.subList(0, this.elements.size() - 1) */ ;
        /* var last = this.elements.getLast() */ ;
        return /* new Some<>(new Tuple<List<T>, T>(new JVMList<>(slice), last)) */;
    }
    /* @Override
            public */ get(index) {
        return /* this.elements.get(index) */;
    }
}
/* private static */ class Lists /*  */ {
    /* public static  */ empty() {
        return /* new JVMList<>() */;
    }
    /* public static  */ of(elements) {
        return /* new JVMList<>(new ArrayList<>(Arrays.asList(elements))) */;
    }
}
/* private static */ class DivideState /*  */ {
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
/* private static */ class ListCollector {
    /* @Override
        public */ createInitial() {
        return /* Lists.empty() */;
    }
    /* @Override
        public */ fold(current, element) {
        return /* current.add(element) */;
    }
}
/* private */ class Tuple {
}
/* public */ class Main /*  */ {
    /* private static class RangeHead implements Head<Integer> {
        private final int length;
        private int counter;

        */ RangeHead(length) {
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
        /* var tuple = compileStatements(new CompileState(), input, Main::compileRootSegment) */ ;
        /* var joined = tuple.left.structures.iterate().collect(new Joiner()).orElse("") */ ;
        return joined +  /*  tuple.right */;
    }
    /* private static */ compileStatements(state, input, mapper) {
        return /* compileAll(state, input, Main::foldStatementChar, mapper, Main::mergeStatements) */;
    }
    /* private static */ compileAll(state, input, folder, mapper, merger) {
        /* var parsed = parseAll(state, input, folder, mapper) */ ;
        /* var generated = generateAll(merger, parsed.right) */ ;
        return /* new Tuple<>(parsed.left, generated) */;
    }
    /* private static */ generateAll(merger, elements) {
        return /* elements
                .iterate()
                .fold(new StringBuilder(), merger)
                .toString() */;
    }
    /* private static */ parseAll(state, input, folder, mapper) {
        return /* divideAll(input, folder).iterate().fold(new Tuple<>(state, Lists.empty()), (tuple, element) -> {
            var state1 = tuple.left;
            var right = tuple.right;

            var applied = mapper.apply(state1, element);
            return new Tuple<>(applied.left, right.add(applied.right));
        }) */;
    }
    /* private static */ mergeStatements(stringBuilder, str) {
        return /* stringBuilder.append(str) */;
    }
    /* private static */ divideAll(input, folder) {
        /* var current = new DivideState(input) */ ; /* while (true) {
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
        return /* current.advance().segments */;
    }
    /* private static */ foldDoubleQuotes(tuple) {
        return /* new None<>() */;
    }
    /* private static */ foldSingleQuotes(tuple) {
        return /* new None<>() */;
    }
    /* private static */ foldStatementChar(state, c) {
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
    /* private static */ compileRootSegment(state, input) {
        /* var stripped = input.strip() */ ; /* if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return new Tuple<>(state, "");
        } */
        return /* compileClass(stripped, 0, state)
                .orElseGet(() -> new Tuple<>(state, generatePlaceholder(stripped))) */;
    }
    /* private static */ compileClass(stripped, depth, state) {
        return /* structure(stripped, "class ", "class ", state) */;
    }
    /* private static */ structure(stripped, sourceInfix, targetInfix, state) {
        return /* first(stripped, sourceInfix, (beforeInfix, right) -> {
            return first(right, "{", (beforeContent, withEnd) -> {
                var strippedWithEnd = withEnd.strip();
                return suffix(strippedWithEnd, "}", content1 -> {
                    return first(beforeContent, "<", new BiFunction<String, String, Option<Tuple<CompileState, String>>>() {
                        @Override
                        public Option<Tuple<CompileState, String>> apply(String name, String withTypeParams) {
                            return first(withTypeParams, ">", new BiFunction<String, String, Option<Tuple<CompileState, String>>>() {
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
        }) */;
    }
    /* private static */ assemble(state, targetInfix, beforeInfix, rawName, content, typeParams, afterTypeParams) {
        /* var name = rawName.strip() */ ; /* if (!isSymbol(name)) {
            return new None<>();
        } */
        /* var joinedTypeParams = typeParams.iterate().collect(new Joiner(", ")).map(inner -> "<" + inner + ">").orElse("") */ ;
        /* var statements = compileStatements(state, content, (state0, input) -> compileClassSegment(state0, input, 1)) */ ;
        /* var generated = generatePlaceholder(beforeInfix.strip()) + targetInfix + name + joinedTypeParams + generatePlaceholder(afterTypeParams) + " {" + statements.right + "\n}\n" */ ;
        return /* new Some<>(new Tuple<>(statements.left.addStructure(generated), "")) */;
    }
    /* private static */ isSymbol(input) {
        return true;
    }
    /* private static  */ suffix(input, suffix, mapper) {
        /* var slice = input.substring(0, input.length() - suffix.length()) */ ;
        return /* mapper.apply(slice) */;
    }
    /* private static */ compileClassSegment(state, input, depth) {
        return /* compileWhitespace(input, state)
                .or(() -> compileClass(input, depth, state))
                .or(() -> structure(input, "interface ", "interface ", state))
                .or(() -> structure(input, "record ", "class ", state))
                .or(() -> method(state, input, depth))
                .or(() -> compileDefinitionStatement(input, depth, state))
                .orElseGet(() -> new Tuple<>(state, generatePlaceholder(input))) */;
    }
    /* private static */ compileWhitespace(input, state) {
        return /* new None<>() */;
    }
    /* private static */ method(state, input, depth) {
        return; /* first(input, "(", (definition, withParams) -> {
            return first(withParams, ")", (params, rawContent) -> {
                var definitionTuple = parseDefinition(state, definition)
                        .map(definition1 -> {
                            var paramsTuple = compileValues(state, params, Main::compileParameter);
                            var generated = definition1.right.generateWithParams("("  */
        + + + + + + +" {" + /*  statementsTuple.right  */ +indent +
        /*  "}";
        return new Some<>(new Tuple<>(statementsTuple.left, generated));
    }

    return new None<>();
});
}) */;
    }
    /* private static */ compileFunctionalSegment(state, input, depth) {
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
    /* private static */ statementValue(state, input) {
        /* var stripped = input.strip() */ ; /* if (stripped.startsWith("return ")) {
            var value = stripped.substring("return ".length());
            var tuple = value(state, value);
            return new Tuple<>(tuple.left, "return " + tuple.right);
        } */
        return /* new Tuple<>(state, generatePlaceholder(stripped)) */;
    }
    /* private static */ value(state, input) {
        return /* operation(state, input)
                .or(() -> symbolValue(state, input))
                .or(() -> stringValue(state, input))
                .orElseGet(() -> new Tuple<CompileState, String>(state, generatePlaceholder(input))) */;
    }
    /* private static */ stringValue(state, input) {
        /* var stripped = input.strip() */ ; /* if (stripped.startsWith("\"") && stripped.endsWith("\"")) {
            return new Some<>(new Tuple<>(state, stripped));
        } */
        return /* new None<>() */;
    }
    /* private static */ symbolValue(state, value) {
        /* var stripped = value.strip() */ ; /* if (isSymbol(stripped)) {
            return new Some<>(new Tuple<>(state, stripped));
        } */
        return /* new None<>() */;
    }
    /* private static */ operation(state, value) {
        return /* first(value, " */ + +" + " +
        /*  rightTuple.right));
    }) */;
    }
    /* private static */ compileValues(state, params, mapper) {
        /* var parsed = parseValues(state, params, mapper) */ ;
        /* var generated = generateValues(parsed.right) */ ;
        return /* new Tuple<>(parsed.left, generated) */;
    }
    /* private static */ generateValues(elements) {
        return /* generateAll(Main::mergeValues, elements) */;
    }
    /* private static */ parseValues(state, input, mapper) {
        return /* parseAll(state, input, Main::foldValueChar, mapper) */;
    }
    /* private static */ compileParameter(state, input) {
        return /* parseDefinition(state, input)
                .map((Tuple<CompileState, Definition> tuple) -> new Tuple<>(tuple.left, tuple.right.generate()))
                .orElseGet(() -> new Tuple<>(state, generatePlaceholder(input))) */;
    }
    /* private static */ mergeValues(cache, element) {
        return /* cache.append(", ").append(element) */;
    }
    /* private static */ createIndent(depth) {
        return "\n" +  /*  "\t".repeat(depth) */;
    }
    /* private static */ compileDefinitionStatement(input, depth, state) {
        return; /* suffix(input.strip(), ";", withoutEnd -> {
            return parseDefinition(state, withoutEnd).map(result -> {
                var generated = createIndent(depth)  */
        + + /*  ";";
        return new Tuple<>(result.left, generated);
    });
}) */;
    }
    /* private static */ parseDefinition(state, input) {
        return /* last(input.strip(), " ", (beforeName, name) -> {
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
        }) */;
    }
    /* private static */ getStringStringTuple(beforeName) {
        /* var divisions = divideAll(beforeName, Main::foldTypeSeparator) */ ;
        return /* divisions.removeLast().map(removed -> {
            var left = removed.left.iterate().collect(new Joiner(" ")).orElse("");
            var right = removed.right;

            return new Tuple<>(left, right);
        }) */;
    }
    /* private static */ foldTypeSeparator(state, c) {
        /* var appended = state.append(c) */ ; /* if (c == '<') {
            return appended.enter();
        } */ /* if (c == '>') {
            return appended.exit();
        } */
        return appended;
    }
    /* private static */ assembleDefinition(state, beforeTypeParams, name, typeParams, type) {
        /* var type1 = type(state, type) */ ;
        /* var node = new Definition(beforeTypeParams, type1.right, name.strip(), typeParams) */ ;
        return /* new Some<>(new Tuple<>(type1.left, node)) */;
    }
    /* private static */ foldValueChar(state, c) {
        /* var appended = state.append(c) */ ; /* if (c == '<') {
            return appended.enter();
        } */ /* if (c == '>') {
            return appended.exit();
        } */
        return appended;
    }
    /* private static */ type(state, input) {
        /* var stripped = input.strip() */ ; /* if (stripped.equals("int")) {
            return new Tuple<>(state, "number");
        } */ /* if (stripped.equals("String")) {
            return new Tuple<>(state, "string");
        } */ /* if (isSymbol(stripped)) {
            return new Tuple<>(state, stripped);
        } */
        return /* template(state, input)
                .or(() -> varArgs(state, input))
                .orElseGet(() -> new Tuple<>(state, generatePlaceholder(stripped))) */;
    }
    /* private static */ varArgs(state, input) {
        return; /* suffix(input, "...", new Function<String, Option<Tuple<CompileState, String>>>() {
            @Override
            public Option<Tuple<CompileState, String>> apply(String s) {
                var inner = type(state, s);
                return new Some<>(new Tuple<>(inner.left, inner.right  */
        + /*  "[]"));
    }
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
        + +", " + /*  arguments.get(1)  */ + +"<" + /*  generateValues(arguments)  */ + /*  ">"));
    });
}) */;
    }
    /* private static */ generate(arguments, returns) {
        /* var joined = arguments.iterate()
                .collect(new Joiner(", "))
                .orElse("") */ ;
        return "(" + joined + ") => " + returns;
    }
    /* private static  */ last(input, infix, mapper) {
        return /* infix(input, infix, Main::findLast, mapper) */;
    }
    /* private static */ findLast(input, infix) {
        /* var index = input.lastIndexOf(infix) */ ;
        return /* index == -1 ? new None<Integer>() : new Some<>(index) */;
    }
    /* private static  */ first(input, infix, mapper) {
        return /* infix(input, infix, Main::findFirst, mapper) */;
    }
    /* private static  */ infix(input, infix, locator, mapper) {
        return; /* split(() -> locator.apply(input, infix).map(index -> {
            var left = input.substring(0, index);
            var right = input.substring(index  */
        + /*  infix.length());
        return new Tuple<>(left, right);
    }), mapper) */;
    }
    /* private static  */ split(splitter, mapper) {
        return /* splitter.get().flatMap(tuple -> mapper.apply(tuple.left, tuple.right)) */;
    }
    /* private static */ findFirst(input, infix) {
        /* var index = input.indexOf(infix) */ ;
        return /* index == -1 ? new None<Integer>() : new Some<>(index) */;
    }
    /* private static */ generatePlaceholder(input) {
        /* var replaced = input
                .replace("content-start", "content-start")
                .replace("content-end", "content-end") */ ;
        return "/* " + replaced + " */";
    }
}
/*  */ 
