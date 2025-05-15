/*private static*/class DivideState {
	/*private final*/segments : /*List*/<string>;
	/*private*/buffer : /*StringBuilder*/;
	/*private*/depth : number;
	constructor (segments : /*List*/<string>, buffer : /*StringBuilder*/, depth : number) {
		this.segments = segments;
		this.buffer = buffer;
		this.depth = depth;
	}
	constructor () {
		this(new /*ArrayList*/<>(), new /*StringBuilder*/(), 0);
	}
	/*private*/advance(): /*DivideState*/ {
		this.segments.add(this.buffer.toString());
		this.buffer = new /*StringBuilder*/();
		return this;
	}
	/*private*/append(c : string): /*DivideState*/ {
		this.buffer.append(c);
		return this;
	}
	/*public*/isLevel(): boolean {
		return this.depth === 0;
	}
	/*public*/enter(): /*DivideState*/ {
		this.depth++;
		return this;
	}
	/*public*/exit(): /*DivideState*/ {
		this.depth--;
		return this;
	}
	/*public*/isShallow(): boolean {
		return this.depth === 1;
	}
}
/*private static*/class ConstructorHeader implements MethodHeader {
	/*@Override
        public*/generateWithAfterName(afterName : string): string {
		return /*"constructor " + afterName*/;
	}
}
/*public*/class Main {/*private interface MethodHeader {
        String generateWithAfterName(String afterName);
    }*/
	/*private record*/B>(left : /*A*/, right : /*B*/): /*Tuple<A,*/ {
	}
	/*private*/CompileState(output : string, structureName : /*Optional*/<string>): /*record*/ {/*public CompileState() {
            this("", Optional.empty());
        }*//*

        public CompileState append(String element) {
            return new CompileState(this.output + element, this.structureName);
        }*//*

        public CompileState withStructureName(String name) {
            return new CompileState(this.output, Optional.of(name));
        }*/
	}/*

    private record Definition(Optional<String> maybeBeforeType, String name, String type) implements MethodHeader {
        private String generate() {
            return this.generateWithAfterName(" ");
        }

        @Override
        public String generateWithAfterName(String afterName) {
            var beforeTypeString = this.maybeBeforeType().map(Main::generatePlaceholder).orElse("");
            return beforeTypeString + this.name + afterName + ": " + this.type();
        }
    }*/
	/*public static*/main(): /*void*/ {
		/*var source */ = Paths.get(/*"."*/, /* "src"*/, /* "java"*/, /* "magma"*/, /* "Main.java"*/);
		/*var target */ = source.resolveSibling(/*"main.ts"*/);/*
        try {
            var input = Files.readString(source);
            Files.writeString(target, compileRoot(input));
        }*//* catch (IOException e) {
            throw new RuntimeException(e);
        }*/
	}
	/*private static*/compileRoot(input : string): string {
		/*var compiled */ = compileSegments(new /*CompileState*/(), input, /* Main::compileRootSegment*/);
		return /*compiled.left.output + compiled*/.right;
	}
	/*private static Tuple<CompileState,*/compileSegments(state : /*CompileState*/, input : string, /* BiFunction<CompileState*/, /* String*/, /* Tuple<CompileState*/, mapper : /*String>>*/): /*String>*/ {
		return compileAll(state, input, /* Main::foldStatements*/, mapper, /* Main::mergeStatements*/);
	}
	/*private static Tuple<CompileState,*/compileAll(state : /*CompileState*/, input : string, /* BiFunction<DivideState*/, /* Character*/, folder : /*DivideState>*/, /* BiFunction<CompileState*/, /* String*/, /* Tuple<CompileState*/, mapper : /*String>>*/, /* BiFunction<StringBuilder*/, /* String*/, merger : /*StringBuilder>*/): /*String>*/ {
		/*var divisions */ = divide(input, folder);
		/*var current */ = new /*Tuple*/<>(state, new /*StringBuilder*/());/*
        for (var segment : divisions) {
            var currentState = current.left;
            var currentElement = current.right;

            var mappedTuple = mapper.apply(currentState, segment);
            var mappedState = mappedTuple.left;
            var mappedElement = mappedTuple.right;

            current = new Tuple<>(mappedState, merger.apply(currentElement, mappedElement));
        }*/
		return new /*Tuple*/<>(current.left, current.right.toString());
	}
	/*private static*/mergeStatements(cache : /*StringBuilder*/, element : string): /*StringBuilder*/ {
		return cache.append(element);
	}
	/*private static*/divide(input : string, /* BiFunction<DivideState*/, /* Character*/, folder : /*DivideState>*/): /*List*/<string> {
		/*var current */ = new /*DivideState*/();
		/*for (var i */ = 0;
		/*i < input*/.length();/* i++) {
            var c = input.charAt(i);
            current = folder.apply(current, c);
        }*/
		return current.advance().segments;
	}
	/*private static*/foldStatements(state : /*DivideState*/, c : string): /*DivideState*/ {
		/*var appended */ = state.append(c);
		/*if (c */ = /*= '*/;/*' && appended.isLevel()) {
            return appended.advance();
        }*//*

        if (c == '*/
	}/*' && appended.isShallow()) {
            return appended.advance().exit();
        }*//*

        if (c == '{') {
            return appended.enter();
        }

        if (c == '}*//*') {
            return appended.exit();
        }*/
	appended : /*return*/;
}
/*return compileFirst(right1, "{", (rawName, withEnd) -> {
                return compileSuffix(withEnd.strip(), "}", inputContent -> {
                    var name = rawName.strip();
                    var outputContentTuple = compileSegments(state.withStructureName(name), inputContent, Main::compileClassSegment);
                    var outputContentState = outputContentTuple.left;
                    var outputContent = outputContentTuple.right;

                    var generated = generatePlaceholder(beforeKeyword.strip()) + "*/class " + name + " {
	/*" + outputContent + "\n}\n";
                    return Optional.of(new Tuple<>(outputContentState.append(generated),*/}) : /*""));*/;
}
/*private static Optional<Tuple<CompileState, String>> compileClass(CompileState state, String input) {
        return compileFirst(input, "*/class ", (beforeKeyword, right1) -> {
	/*)*/;
	/*})*/;
}
/*

    private static Tuple<CompileState, String> compileRootSegment(CompileState state, String input) {
        return compileOrPlaceholder(state, input, List.of(
                Main::compileNamespaced,
                Main::compileClass
        ));
    }*//*

    private static Optional<Tuple<CompileState, String>> compileNamespaced(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return Optional.of(new Tuple<>(state, ""));
        }
        else {
            return Optional.empty();
        }
    }*//*

    private static Tuple<CompileState, String> compileOrPlaceholder(
            CompileState state,
            String input,
            List<BiFunction<CompileState, String, Optional<Tuple<CompileState, String>>>> rules
    ) {
        return compileOr(state, input, rules).orElseGet(() -> new Tuple<>(state, generatePlaceholder(input)));
    }*//*

    private static Optional<Tuple<CompileState, String>> compileOr(CompileState state, String input, List<BiFunction<CompileState, String, Optional<Tuple<CompileState, String>>>> rules) {
        for (var rule : rules) {
            var maybeTuple = rule.apply(state, input);
            if (maybeTuple.isPresent()) {
                return maybeTuple;
            }
        }

        return Optional.empty();
    }*//*

    private static Tuple<CompileState, String> compileClassSegment(CompileState state1, String input1) {
        return compileOrPlaceholder(state1, input1, List.of(
                Main::compileWhitespace,
                Main::compileClass,
                Main::compileFieldDefinition,
                Main::compileMethod
        ));
    }*//*

    private static Optional<Tuple<CompileState, String>> compileMethod(CompileState state, String input) {
        return compileFirst(input, "(", (beforeParams, withParams) -> {
            return compileLast(beforeParams.strip(), " ", (_, name) -> {
                if (state.structureName.filter(name::equals).isPresent()) {
                    return compileMethodWithBeforeParams(state, new ConstructorHeader(), withParams);
                }

                return compileDefinition(state, beforeParams)
                        .flatMap(tuple -> compileMethodWithBeforeParams(tuple.left, tuple.right, withParams));
            });
        });
    }*//*

    private static Optional<Tuple<CompileState, String>> compileMethodWithBeforeParams(CompileState state, MethodHeader header, String withParams) {
        return compileFirst(withParams, ")", (params, afterParams) -> {
            return compilePrefix(afterParams.strip(), "{", withoutContentStart -> {
                return compileSuffix(withoutContentStart.strip(), "}", withoutContentEnd -> {
                    var parametersTuple = compileValues(state, params, Main::compileParameter);
                    var statementsTuple = compileSegments(parametersTuple.left, withoutContentEnd, Main::compileFunctionSegment);

                    var headerGenerated = header.generateWithAfterName("(" + parametersTuple.right + ")");
                    return Optional.of(new Tuple<>(statementsTuple.left, "\n\t" + headerGenerated + " {" + statementsTuple.right + "\n\t}"));
                });
            });
        });
    }*//*

    private static Tuple<CompileState, String> compileFunctionSegment(CompileState state, String input) {
        return compileOrPlaceholder(state, input, List.of(
                Main::compileWhitespace,
                Main::compileFunctionStatement
        ));
    }*//*

    private static Optional<Tuple<CompileState, String>> compileFunctionStatement(CompileState state, String input) {
        return compileSuffix(input.strip(), ";", withoutEnd -> {
            var valueTuple = compileFunctionStatementValue(state, withoutEnd);
            return Optional.of(new Tuple<>(valueTuple.left, "\n\t\t" + valueTuple.right + ";"));
        });
    }*//*

    private static Tuple<CompileState, String> compileFunctionStatementValue(CompileState state, String withoutEnd) {
        return compileOrPlaceholder(state, withoutEnd, List.of(
                Main::compileReturn,
                Main::compileAssignment,
                Main::compileInvokable,
                createPostRule("++"),
                createPostRule("--")
        ));
    }*//*

    private static BiFunction<CompileState, String, Optional<Tuple<CompileState, String>>> createPostRule(String suffix) {
        return (state1, input) -> compileSuffix(input.strip(), suffix, child -> {
            var tuple = compileValue(state1, child);
            return Optional.of(new Tuple<>(tuple.left, tuple.right + suffix));
        });
    }*//*

    private static Optional<Tuple<CompileState, String>> compileReturn(CompileState state, String input) {
        return compilePrefix(input.strip(), "return ", value -> {
            var tuple = compileValue(state, value);
            return Optional.of(new Tuple<>(tuple.left, "return " + tuple.right));
        });
    }*//*

    private static Optional<Tuple<CompileState, String>> compileInvokable(CompileState state, String input) {
        return compileSuffix(input.strip(), ")", withoutEnd -> {
            return compileFirst(withoutEnd, "(", (caller, arguments) -> {
                return compilePrefix(caller.strip(), "new ", type -> {
                    var callerTuple = compileTypeOrPlaceholder(state, type);
                    return assembleInvokable(callerTuple.left, "new " + callerTuple.right, arguments);
                }).or(() -> {
                    var callerTuple = compileValue(state, caller);
                    return assembleInvokable(callerTuple.left, callerTuple.right, arguments);
                });
            });
        });
    }*//*

    private static Optional<Tuple<CompileState, String>> assembleInvokable(CompileState state, String caller, String arguments) {
        var argumentsTuple = compileValues(state, arguments, Main::compileValue);
        return Optional.of(new Tuple<>(argumentsTuple.left, caller + "(" + argumentsTuple.right + ")"));
    }*//*

    private static Optional<Tuple<CompileState, String>> compileAssignment(CompileState state, String input) {
        return compileFirst(input, "=", (destination, source) -> {
            var sourceTuple = compileValue(state, source);
            var destinationTuple = compileValue(sourceTuple.left, destination);
            return Optional.of(new Tuple<>(destinationTuple.left, destinationTuple.right + " = " + sourceTuple.right));
        });
    }*//*

    private static Tuple<CompileState, String> compileValue(CompileState state, String input) {
        return compileOr(state, input, List.of(
                Main::compileAccess,
                Main::compileSymbol,
                Main::compileInvokable,
                Main::compileNumber,
                Main::compileEquals
        )).orElseGet(() -> new Tuple<>(state, generatePlaceholder(input)));
    }*//*

    private static Optional<Tuple<CompileState, String>> compileEquals(CompileState state, String input) {
        return compileFirst(input, "==", (left, right) -> {
            var leftTuple = compileValue(state, left);
            var rightTuple = compileValue(leftTuple.left, right);
            return Optional.of(new Tuple<>(rightTuple.left, leftTuple.right + " === " + rightTuple.right));
        });
    }*//*

    private static Optional<Tuple<CompileState, String>> compileNumber(CompileState state, String input) {
        var stripped = input.strip();
        if (isNumber(stripped)) {
            return Optional.of(new Tuple<>(state, stripped));
        }
        else {
            return Optional.empty();
        }
    }*//*

    private static boolean isNumber(String input) {
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (Character.isDigit(c)) {
                continue;
            }
            return false;
        }
        return true;
    }*//*

    private static Optional<Tuple<CompileState, String>> compileAccess(CompileState state, String input) {
        return compileLast(input, ".", (child, rawProperty) -> {
            var tuple = compileValue(state, child);
            var property = rawProperty.strip();
            if (isSymbol(property)) {
                return Optional.of(new Tuple<>(tuple.left, tuple.right + "." + property));
            }
            else {
                return Optional.empty();
            }
        });
    }*//*

    private static Optional<Tuple<CompileState, String>> compileSymbol(CompileState state, String input) {
        var stripped = input.strip();
        if (isSymbol(stripped)) {
            return Optional.of(new Tuple<>(state, stripped));
        }
        else {
            return Optional.empty();
        }
    }*//*

    private static boolean isSymbol(String input) {
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (Character.isLetter(c)) {
                continue;
            }
            return false;
        }
        return true;
    }*//*

    private static Optional<Tuple<CompileState, String>> compilePrefix(String input, String infix, Function<String, Optional<Tuple<CompileState, String>>> mapper) {
        if (!input.startsWith(infix)) {
            return Optional.empty();
        }

        var slice = input.substring(infix.length());
        return mapper.apply(slice);
    }*//*

    private static Tuple<CompileState, String> compileParameter(CompileState state, String input) {
        return compileOrPlaceholder(state, input, List.of(
                Main::compileWhitespace,
                (state1, input1) -> {
                    return compileDefinition(state1, input1).map(tuple -> new Tuple<>(tuple.left, tuple.right.generate()));
                }
        ));
    }*//*

    private static Optional<Tuple<CompileState, String>> compileWhitespace(CompileState state, String input) {
        if (input.isBlank()) {
            return Optional.of(new Tuple<>(state, ""));
        }
        return Optional.empty();
    }*//*

    private static Optional<Tuple<CompileState, String>> compileFieldDefinition(CompileState state, String input) {
        return compileSuffix(input.strip(), ";", withoutEnd -> {
            var definitionTuple = compileDefinitionOrPlaceholder(state, withoutEnd);
            return Optional.of(new Tuple<>(definitionTuple.left, "\n\t" + definitionTuple.right + ";"));
        });
    }*//*

    private static Tuple<CompileState, String> compileDefinitionOrPlaceholder(CompileState state, String input) {
        return compileDefinition(state, input)
                .map(tuple -> new Tuple<>(tuple.left, tuple.right.generate()))
                .orElseGet(() -> new Tuple<>(state, generatePlaceholder(input)));
    }*//*

    private static Optional<Tuple<CompileState, Definition>> compileDefinition(CompileState state, String input) {
        return compileLast(input.strip(), " ", (beforeName, name) -> {
            return compileLast(beforeName.strip(), " ", (beforeType, type) -> {
                return assembleDefinition(state, Optional.of(beforeType), name, type);
            }).or(() -> {
                return assembleDefinition(state, Optional.empty(), name, beforeName);
            });
        });
    }*//*

    private static Optional<Tuple<CompileState, Definition>> assembleDefinition(CompileState state, Optional<String> maybeBeforeType, String name, String type) {
        var typeTuple = compileTypeOrPlaceholder(state, type);

        var generated = new Definition(maybeBeforeType, name, typeTuple.right);
        return Optional.of(new Tuple<>(typeTuple.left, generated));
    }*//*

    private static Tuple<CompileState, String> compileTypeOrPlaceholder(CompileState state, String type) {
        return compileType(state, type).orElseGet(() -> new Tuple<>(state, generatePlaceholder(type)));
    }*//*

    private static Optional<Tuple<CompileState, String>> compileType(CompileState state, String type) {
        return compileOr(state, type, List.of(
                Main::compileGeneric,
                Main::compilePrimitive
        ));
    }*//*

    private static Optional<Tuple<CompileState, String>> compilePrimitive(CompileState state, String input) {
        return findPrimitiveValue(input.strip()).map(result -> new Tuple<>(state, result));
    }*//*

    private static Optional<String> findPrimitiveValue(String input) {
        var stripped = input.strip();
        if (stripped.equals("String") || stripped.equals("char")) {
            return Optional.of("string");
        }

        if (stripped.equals("int")) {
            return Optional.of("number");
        }

        if (stripped.equals("boolean")) {
            return Optional.of("boolean");
        }

        return Optional.empty();
    }*//*

    private static Optional<Tuple<CompileState, String>> compileGeneric(CompileState state, String input) {
        return compileSuffix(input.strip(), ">", withoutEnd -> {
            return compileFirst(withoutEnd, "<", (baseString, argumentsString) -> {
                var argumentsTuple = compileValues(state, argumentsString, Main::compileTypeArgument);
                return Optional.of(new Tuple<>(argumentsTuple.left, generatePlaceholder(baseString) + "<" + argumentsTuple.right + ">"));
            });
        });
    }*//*

    private static Tuple<CompileState, String> compileTypeArgument(CompileState state, String s) {
        return compileOrPlaceholder(state, s, List.of(
                Main::compileWhitespace,
                Main::compileType
        ));
    }*//*

    private static Tuple<CompileState, String> compileValues(CompileState state, String input, BiFunction<CompileState, String, Tuple<CompileState, String>> mapper) {
        return compileAll(state, input, Main::foldValues, mapper, Main::mergeValues);
    }*//*

    private static StringBuilder mergeValues(StringBuilder cache, String element) {
        if (cache.isEmpty()) {
            return cache.append(element);
        }
        return cache.append(", ").append(element);
    }*//*

    private static DivideState foldValues(DivideState state, char c) {
        if (c == ',') {
            return state.advance();
        }
        return state.append(c);
    }*//*

    private static <T> Optional<T> compileLast(String input, String infix, BiFunction<String, String, Optional<T>> mapper) {
        return compileInfix(input, infix, Main::findLast, mapper);
    }*//*

    private static int findLast(String input, String infix) {
        return input.lastIndexOf(infix);
    }*//*

    private static <T> Optional<T> compileSuffix(String input, String suffix, Function<String, Optional<T>> mapper) {
        if (!input.endsWith(suffix)) {
            return Optional.empty();
        }

        var content = input.substring(0, input.length() - suffix.length());
        return mapper.apply(content);
    }*//*

    private static <T> Optional<T> compileFirst(String input, String infix, BiFunction<String, String, Optional<T>> mapper) {
        return compileInfix(input, infix, Main::findFirst, mapper);
    }*//*

    private static <T> Optional<T> compileInfix(String input, String infix, BiFunction<String, String, Integer> locator, BiFunction<String, String, Optional<T>> mapper) {
        var index = locator.apply(input, infix);
        if (index < 0) {
            return Optional.empty();
        }

        var left = input.substring(0, index);
        var right = input.substring(index + infix.length());
        return mapper.apply(left, right);
    }*//*

    private static int findFirst(String input, String infix) {
        return input.indexOf(infix);
    }*//*

    private static String generatePlaceholder(String input) {
        var replaced = input
                .replace("start", "start")
                .replace("end", "end");

        return "start" + replaced + "end";
    }*//*
}*/