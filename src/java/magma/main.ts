/*private*/interface MethodHeader {
	generateWithAfterName(afterName : string): string;
}
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
/*private*/class Tuple<A, B> {
}
/*private*/class CompileState {
	constructor () {
		this("", Optional.empty(), 0);
	}
	/*public*/append(element : string): /*CompileState*/ {
		return new /*CompileState*/(this.output + element, this.structureName, this.depth);
	}
	/*public*/withStructureName(name : string): /*CompileState*/ {
		return new /*CompileState*/(this.output, Optional.of(name), this.depth);
	}
	/*public*/depth(): number {
		return this.depth;
	}
	/*public*/enterDepth(): /*CompileState*/ {
		return new /*CompileState*/(this.output, this.structureName, this.depth + 1);
	}
	/*public*/exitDepth(): /*CompileState*/ {
		return new /*CompileState*/(this.output, this.structureName, /* this.depth - 1*/);
	}
}
/*private*/class Definition {
	/*private*/generate(): string {
		return this.generateWithAfterName(" ");
	}
	/*@Override
        public*/generateWithAfterName(afterName : string): string {
		let beforeTypeString : unknown = this.maybeBeforeType().map(Main.generatePlaceholder).orElse("");
		return beforeTypeString + this.name + afterName + ": " + this.type();
	}
}
/*private static*/class ConstructorHeader implements MethodHeader {
	/*@Override
        public*/generateWithAfterName(afterName : string): string {
		return "constructor " + afterName;
	}
}
/*public*/class Main {
	/*public static*/main(): void {
		let source : unknown = Paths.get(".", "src", "java", "magma", "Main.java");
		let target : unknown = source.resolveSibling("main.ts");
		/*try */{
			let input : unknown = Files.readString(source);
			Files.writeString(target, compileRoot(input));
		}
		/*catch (IOException e) */{
			/*throw new RuntimeException*/(e);
		}
	}
	/*private static*/compileRoot(input : string): string {
		let compiled : unknown = compileStatements(new /*CompileState*/(), input, Main.compileRootSegment);
		return compiled.left.output + compiled.right;
	}
	/*private static*/compileStatements(state : /*CompileState*/, input : string, mapper : /*BiFunction*/</*CompileState*/, string, /*Tuple*/</*CompileState*/, string>>): /*Tuple*/</*CompileState*/, string> {
		return compileAll(state, input, Main.foldStatements, mapper, Main.mergeStatements);
	}
	/*private static*/compileAll(state : /*CompileState*/, input : string, folder : /*BiFunction*/</*DivideState*/, string, /* DivideState*/>, mapper : /*BiFunction*/</*CompileState*/, string, /*Tuple*/</*CompileState*/, string>>, merger : /*BiFunction*/</*StringBuilder*/, string, /* StringBuilder*/>): /*Tuple*/</*CompileState*/, string> {
		let divisions : unknown = divide(input, folder);
		let current : unknown = new /*Tuple*/<>(state, new /*StringBuilder*/());
		/*for (var segment : divisions) */{
			let currentState : unknown = current.left;
			let currentElement : unknown = current.right;
			let mappedTuple : unknown = mapper.apply(currentState, segment);
			let mappedState : unknown = mappedTuple.left;
			let mappedElement : unknown = mappedTuple.right;
			current = new /*Tuple*/<>(mappedState, merger.apply(currentElement, mappedElement));
		}
		return new /*Tuple*/<>(current.left, current.right.toString());
	}
	/*private static*/mergeStatements(cache : /*StringBuilder*/, element : string): /*StringBuilder*/ {
		return cache.append(element);
	}
	/*private static*/divide(input : string, folder : /*BiFunction*/</*DivideState*/, string, /* DivideState*/>): /*List*/<string> {
		let current : unknown = new /*DivideState*/();
		/*for (var i = 0; i < input.length(); i++) */{
			let c : unknown = input.charAt(i);
			current = folder.apply(current, c);
		}
		return current.advance().segments;
	}
	/*private static*/foldStatements(state : /*DivideState*/, c : string): /*DivideState*/ {
		let appended : unknown = state.append(c);
		/*if (c == ';' && appended.isLevel()) */{
			return appended.advance();
		}/*

        if (c == '}*/
		/*' && appended*/.isShallow(/*)) {
            return appended.advance().exit(*/);
	}/*

        if (c == '{' || c == '(') {
            return appended.enter();
        }

        if (c == '}' || c == ')') {
            return appended.exit();
        }*/
	appended : /*return*/;
}
/*private static BiFunction<CompileState, String, Optional<Tuple<CompileState, String>>> createStructureRule(String sourceInfix, String targetInfix) {
        return (state1, input1) -> compileFirst(input1, sourceInfix, (beforeKeyword, right1) -> {
            return compileFirst(right1, "{", (beforeContent, withEnd) -> {
                return compileSuffix(withEnd.strip(), "}", inputContent -> {
                    var strippedBeforeContent = beforeContent.strip();
                    return compileFirst(strippedBeforeContent, "(", (rawName, s2) -> {
                        var name = rawName.strip();
                        return assembleStructure(targetInfix, state1, beforeKeyword, inputContent, name);
                    }).or(() -> {
                        return assembleStructure(targetInfix, state1, beforeKeyword, inputContent, strippedBeforeContent);
                    });
                });
            });
        });
    }

    private static Optional<Tuple<CompileState, String>> assembleStructure(String targetInfix, CompileState state1, String beforeKeyword, String inputContent, String name) {
        var outputContentTuple = compileStatements(state1.withStructureName(name), inputContent, Main::compileClassSegment);
        var outputContentState = outputContentTuple.left;
        var outputContent = outputContentTuple.right;

        var generated = generatePlaceholder(beforeKeyword.strip()) + targetInfix + name + " {" + outputContent + "\n}\n";
        return Optional.of(new Tuple<>(outputContentState.append(generated), ""));
    }

    private static Optional<Tuple<CompileState, String>> compileNamespaced(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return Optional.of(new Tuple<>(state, ""));
        }
        else {
            return Optional.empty();
        }
    }

    private static Tuple<CompileState, String> compileOrPlaceholder(
            CompileState state,
            String input,
            List<BiFunction<CompileState, String, Optional<Tuple<CompileState, String>>>> rules
    ) {
        return compileOr(state, input, rules).orElseGet(() -> new Tuple<>(state, generatePlaceholder(input)));
    }

    private static Optional<Tuple<CompileState, String>> compileOr(CompileState state, String input, List<BiFunction<CompileState, String, Optional<Tuple<CompileState, String>>>> rules) {
        for (var rule : rules) {
            var maybeTuple = rule.apply(state, input);
            if (maybeTuple.isPresent()) {
                return maybeTuple;
            }
        }

        return Optional.empty();
    }

    private static Tuple<CompileState, String> compileClassSegment(CompileState state1, String input1) {
        return compileOrPlaceholder(state1, input1, List.of(
                Main::compileWhitespace,
                createStructureRule("*/class ", "class "),
                createStructureRule {/*return compileFirst(input, "(", (beforeParams, withParams) -> {
            return compileLast(beforeParams.strip(), " ", (_, name) -> {
                if (state.structureName.filter(name::equals).isPresent()) {
                    return compileMethodWithBeforeParams(state, new ConstructorHeader(), withParams);
                }

                return compileDefinition(state, beforeParams)
                        .flatMap(tuple -> compileMethodWithBeforeParams(tuple.left, tuple.right, withParams));
            });
        });
    }*/
	/*private static Optional<Tuple<CompileState, String>> compileMethodWithBeforeParams(CompileState state, MethodHeader header, String withParams) {
        return compileFirst(withParams, ")", (params, afterParams)*/}) : /*-> {
            var parametersTuple = compileValues(state, params, Main::compileParameter);
            var parametersState = parametersTuple.left;
            var parameters = parametersTuple.right;

            var headerGenerated = header.generateWithAfterName("(" + parameters + ")");
            return compilePrefix(afterParams.strip(), "{", withoutContentStart -> {
                return compileSuffix(withoutContentStart.strip(), "}", withoutContentEnd -> {
                    var statementsTuple = compileFunctionStatements(parametersState.enterDepth().enterDepth(), withoutContentEnd);

                    return Optional.of(new Tuple<>(statementsTuple.left.exitDepth().exitDepth(), "\n\t" + headerGenerated + " {" + statementsTuple.right + "\n\t}"));
                });
            }).or(() -> {
                if (afterParams.strip().equals(";")) {
                    return Optional.of(new Tuple<>(parametersState, "\n\t" + headerGenerated + ";"));
                }

                return Optional.empty();
            });*/;
	/*}

    private static Tuple<CompileState, String> compileFunctionStatements(CompileState state, String input) {
        return compileStatements(state,*/Main::compileFunctionSegment) : /*input,*/;
	/*}

    private static Tuple<CompileState, String> compileFunctionSegment(CompileState state, String input) {
        return compileOrPlaceholder(state, input, List.of(
                Main::compileWhitespace,
                Main::compileBlock,
               */)) : /*Main::compileFunctionStatement*/;
	/*}

    private static*/compileBlock(state : /*CompileState*/, input : string): /*Optional*/</*Tuple*/</*CompileState*/, string>> {/*return compileSuffix(input.strip(), "*/
	}
	/*", withoutEnd*/}) : /*-> {
            return compileFirst(withoutEnd, "{", (beforeContent, content) -> {
                var tuple = compileFunctionStatements(state.enterDepth(), content);
                var indent = generateIndent(state.depth());
                return Optional.of(new Tuple<>(tuple.left.exitDepth(), indent + generatePlaceholder(beforeContent) + "{" + tuple.right + indent + "}"));
            });*/;
	/*}

    private static Optional<Tuple<CompileState, String>> compileFunctionStatement(CompileState state, String input) {
        return compileSuffix(input.strip(), ";", withoutEnd*/}) : /*-> {
            var valueTuple = compileFunctionStatementValue(state, withoutEnd);
            return Optional.of(new Tuple<>(valueTuple.left, generateIndent(state.depth()) + valueTuple.right + ";"));*/;
	/*}

    private static String generateIndent(int indent) {
        return "\n"*/"\t".repeat(indent) : /*+*/;
	/*}

    private static Tuple<CompileState, String> compileFunctionStatementValue(CompileState state, String withoutEnd) {
        return compileOrPlaceholder(state, withoutEnd, List.of(
                Main::compileReturn,
                Main::compileAssignment,
                Main::compileInvokable,
                createPostRule("++"),
               */)) : /*createPostRule("--")*/;
	/*}

    private static BiFunction<CompileState, String, Optional<Tuple<CompileState, String>>> createPostRule(String suffix) {
        return (state1, input)*/}) : /*-> compileSuffix(input.strip(), suffix, child -> {
            var tuple = compileValueOrPlaceholder(state1, child);
            return Optional.of(new Tuple<>(tuple.left, tuple.right + suffix));*/;
	/*}

    private static Optional<Tuple<CompileState, String>> compileReturn(CompileState state, String input) {
        return compilePrefix(input.strip(), "return ", value*/}) : /*-> {
            var tuple = compileValueOrPlaceholder(state, value);
            return Optional.of(new Tuple<>(tuple.left, "return " + tuple.right));*/;
	/*}

    private static Optional<Tuple<CompileState, String>> compileInvokable(CompileState state, String input) {
        return compileSuffix(input.strip(), ")", withoutEnd*/}) : /*-> {
            return compileSplit(splitFolded(withoutEnd, "", Main::foldInvocationStarts), (callerWithArgStart, arguments) -> {
                return compileSuffix(callerWithArgStart, "(", caller -> compilePrefix(caller.strip(), "new ", type -> {
                    var callerTuple = compileTypeOrPlaceholder(state, type);
                    return assembleInvokable(callerTuple.left, "new " + callerTuple.right, arguments);
                }).or(() -> {
                    var callerTuple = compileValueOrPlaceholder(state, caller);
                    return assembleInvokable(callerTuple.left, callerTuple.right, arguments);
                }));
            });*/;
	/*}

    private static Optional<Tuple<String, String>> splitFolded(String input, String delimiter, BiFunction<DivideState, Character, DivideState> folder) {
        var divisions =*/folder) : /*divide(input,*/;/*
        if (divisions.size() < 2) {
            return Optional.empty();
        }*/
	/*var beforeLast = divisions.subList(0, divisions.size()*/1) : /*-*/;
	/*var last*/divisions.getLast(): /*=*/;
	/*var joined*/String.join(/*delimiter*/, /* beforeLast*/): /*=*/;
	/*return Optional.of(new*/last)) : /*Tuple<>(joined,*/;
	/*}

    private static DivideState foldInvocationStarts(DivideState state, char c) {
        var appended*/state.append(c) : /*=*/;/*
        if (c == '(') {
            var entered = appended.enter();
            if (entered.isShallow()) {
                return entered.advance();
            }
            else {
                return entered;
            }
        }

        if (c == ')') {
            return appended.exit();
        }*/
	appended : /*return*/;
	/*}

    private static Optional<Tuple<CompileState, String>> assembleInvokable(CompileState state, String caller, String arguments) {
        var argumentsTuple = compileValues(state,*/Main::compileValueOrPlaceholder) : /*arguments,*/;
	/*return Optional.of(new Tuple<>(argumentsTuple.left, caller + "(" + argumentsTuple.right*/")")) : /*+*/;
	/*}

    private static Optional<Tuple<CompileState, String>> compileAssignment(CompileState state, String input) {
        return compileFirst(input, "=", (destination, source)*/}) : /*-> {
            var sourceTuple = compileValueOrPlaceholder(state, source);

            var destinationTuple = compileValue(sourceTuple.left, destination)
                    .or(() -> compileDefinition(sourceTuple.left, destination).map(tuple -> new Tuple<>(tuple.left, "let " + tuple.right.generate())))
                    .orElseGet(() -> new Tuple<>(sourceTuple.left, generatePlaceholder(destination)));

            return Optional.of(new Tuple<>(destinationTuple.left, destinationTuple.right + " = " + sourceTuple.right));*/;
	/*}

    private static Tuple<CompileState, String> compileValueOrPlaceholder(CompileState state, String input) {
        return compileValue(state, input).orElseGet(()*/generatePlaceholder(input))) : /*-> new Tuple<>(state,*/;
	/*}

    private static Optional<Tuple<CompileState, String>> compileValue(CompileState state, String input) {
        return compileOr(state, input, List.of(
                createAccessRule("."),
                createAccessRule("::"),
                Main::compileSymbol,
                Main::compileInvokable,
                Main::compileNumber,
                createOperatorRule("==", "==="),
                createOperatorRule("+", "+"),
               */)) : /*Main::compileString*/;
	/*}

    private static BiFunction<CompileState, String, Optional<Tuple<CompileState, String>>> createAccessRule(String infix) {
        return (state, input)*/}) : /*-> compileLast(input, infix, (child, rawProperty) -> {
            var tuple = compileValueOrPlaceholder(state, child);
            var property = rawProperty.strip();
            if (isSymbol(property)) {
                return Optional.of(new Tuple<>(tuple.left, tuple.right + "." + property));
            }
            else {
                return Optional.empty();
            }*/;
	/*}

    private static Optional<Tuple<CompileState, String>> compileString(CompileState state, String input) {
        var stripped*/input.strip() : /*=*/;/*
        if (stripped.startsWith("\"") && stripped.endsWith("\"")) {
            return Optional.of(new Tuple<>(state, stripped));
        }*//*
        else {
            return Optional.empty();
        }*/
	/*}

    private static BiFunction<CompileState, String, Optional<Tuple<CompileState, String>>> createOperatorRule(String sourceInfix, String targetInfix) {
        return (state1, input1)*/}) : /*-> compileFirst(input1, sourceInfix, (left, right) -> {
            var leftTuple = compileValueOrPlaceholder(state1, left);
            var rightTuple = compileValueOrPlaceholder(leftTuple.left, right);
            return Optional.of(new Tuple<>(rightTuple.left, leftTuple.right + " " + targetInfix + " " + rightTuple.right));*/;
	/*}

    private static Optional<Tuple<CompileState, String>> compileNumber(CompileState state, String input) {
        var stripped*/input.strip() : /*=*/;/*
        if (isNumber(stripped)) {
            return Optional.of(new Tuple<>(state, stripped));
        }*//*
        else {
            return Optional.empty();
        }*/
	/*}

    private static*/isNumber(input : string): boolean {
		let /*for*/i : /*(var*/ = /* 0; i < input.length(); i*/ +  + /*) {
            var c = input.charAt(i);
            if (Character.isDigit(c)) {
                continue;
            }
            return false*/;
	}
	true : /*return*/;
	/*}

    private static Optional<Tuple<CompileState, String>> compileSymbol(CompileState state, String input) {
        var stripped*/input.strip() : /*=*/;/*
        if (isSymbol(stripped)) {
            return Optional.of(new Tuple<>(state, stripped));
        }*//*
        else {
            return Optional.empty();
        }*/
	/*}

    private static*/isSymbol(input : string): boolean {
		let /*for*/i : /*(var*/ = /* 0; i < input.length(); i*/ +  + /*) {
            var c = input.charAt(i);
            if (Character.isLetter(c)) {
                continue;
            }
            return false*/;
	}
	true : /*return*/;
	/*}

    private static*/compilePrefix(input : string, infix : string, mapper : /*Function*/<string, /*Optional*/</*Tuple*/</*CompileState*/, string>>>): /*Optional*/</*Tuple*/</*CompileState*/, string>> {
		/*if (!input.startsWith(infix)) {
            return Optional*/.empty();
	}
	/*var slice*/input.substring(infix.length()) : /*=*/;
	mapper.apply(/*slice*/): /*return*/;
	/*}

    private static Tuple<CompileState, String> compileParameter(CompileState state, String input) {
        return compileOrPlaceholder(state, input, List.of(
                Main::compileWhitespace,
                (state1, input1)*/)) : /*-> {
                    return compileDefinition(state1, input1).map(tuple -> new Tuple<>(tuple.left, tuple.right.generate()));
                }*/;
	/*}

    private static*/compileWhitespace(state : /*CompileState*/, input : string): /*Optional*/</*Tuple*/</*CompileState*/, string>> {
		/*if (input.isBlank()) {
            return Optional*/.of(new /*Tuple*/<>(state, ""));
	}
	Optional.empty(): /*return*/;
	/*}

    private static Optional<Tuple<CompileState, String>> compileFieldDefinition(CompileState state, String input) {
        return compileSuffix(input.strip(), ";", withoutEnd*/}) : /*-> {
            var definitionTuple = compileDefinitionOrPlaceholder(state, withoutEnd);
            return Optional.of(new Tuple<>(definitionTuple.left, "\n\t" + definitionTuple.right + ";"));*/;
	/*}

    private static Tuple<CompileState, String> compileDefinitionOrPlaceholder(CompileState state, String input) {
        return compileDefinition(state, input)
                .map(tuple*/generatePlaceholder(input))) : /*-> new Tuple<>(tuple.left, tuple.right.generate()))
                .orElseGet(() -> new Tuple<>(state,*/;
	/*}

    private static Optional<Tuple<CompileState, Definition>> compileDefinition(CompileState state, String input) {
        return compileLast(input.strip(), " ", (beforeName, name)*/}) : /*-> {
            var splits = splitFolded(beforeName.strip(), " ", Main::foldTypeSeparators);
            return compileSplit(splits, (beforeType, type) -> {
                return assembleDefinition(state, Optional.of(beforeType), name, type);
            }).or(() -> {
                return assembleDefinition(state, Optional.empty(), name, beforeName);
            });*/;
	/*}

    private static*/foldTypeSeparators(state : /*DivideState*/, c : string): /*DivideState*/ {
		let (c : /*if*/ = /*= ' ' && state*/.isLevel(/*)) {
            return state.advance(*/);
	}
	/*var appended*/state.append(/*c*/): /*=*/;/*
        if (c == '<') {
            return appended.enter();
        }*//*
        if (c == '>') {
            return appended.exit();
        }*/
	appended : /*return*/;
	/*}

    private static Optional<Tuple<CompileState, Definition>> assembleDefinition(CompileState state, Optional<String> maybeBeforeType, String name, String type) {
        var typeTuple =*/type) : /*compileTypeOrPlaceholder(state,*/;
	/*var generated =*/Definition(/*maybeBeforeType*/, /* name*/, /* typeTuple.right*/): /*new*/;
	/*return Optional.of(new*/generated)) : /*Tuple<>(typeTuple.left,*/;
	/*}

    private static Tuple<CompileState, String> compileTypeOrPlaceholder(CompileState state, String type) {
        return compileType(state, type).orElseGet(()*/generatePlaceholder(type))) : /*-> new Tuple<>(state,*/;
	/*}

    private static Optional<Tuple<CompileState, String>> compileType(CompileState state, String type) {
        return compileOr(state, type, List.of(
                Main::compileGeneric,
               */)) : /*Main::compilePrimitive*/;
	/*}

    private static Optional<Tuple<CompileState, String>> compilePrimitive(CompileState state, String input) {
        return findPrimitiveValue(input.strip()).map(result*/result)) : /*-> new Tuple<>(state,*/;
	/*}

    private static*/findPrimitiveValue(input : string): /*Optional*/<string> {
		return /*switch (input.strip()) {
            case "char", "Character", "String" -> Optional.of("string");
            case "int" -> Optional.of("number");
            case "boolean" -> Optional.of("boolean");
            case "var" -> Optional.of("unknown");
            case "void" -> Optional.of("void");
            default -> Optional*/.empty();
	}
	/**/;
	/*}

    private static Optional<Tuple<CompileState, String>> compileGeneric(CompileState state, String input) {
        return compileSuffix(input.strip(),*/}) : /*">", withoutEnd -> {
            return compileFirst(withoutEnd, "<", (baseString, argumentsString) -> {
                var argumentsTuple = compileValues(state, argumentsString, Main::compileTypeArgument);
                return Optional.of(new Tuple<>(argumentsTuple.left, generatePlaceholder(baseString) + "<" + argumentsTuple.right + ">"));
            });*/;
	/*}

    private static Tuple<CompileState, String> compileTypeArgument(CompileState state, String s) {
        return compileOrPlaceholder(state, s, List.of(
                Main::compileWhitespace,
               */)) : /*Main::compileType*/;
	/*}

    private static Tuple<CompileState, String> compileValues(CompileState state, String input, BiFunction<CompileState, String, Tuple<CompileState, String>> mapper) {
        return compileAll(state, input, Main::foldValues,*/Main::mergeValues) : /*mapper,*/;
	/*}

    private static*/mergeValues(cache : /*StringBuilder*/, element : string): /*StringBuilder*/ {
		/*if (cache.isEmpty()) {
            return cache*/.append(element);
	}
	/*return*/").append(element) : /*cache.append(",*/;
	/*}

    private static*/foldValues(state : /*DivideState*/, c : string): /*DivideState*/ {
		let (c : /*if*/ = /*= ',' && state*/.isLevel(/*)) {
            return state.advance(*/);
	}
	/*var appended*/state.append(/*c*/): /*=*/;/*
        if (c == '<' || c == '(') {
            return appended.enter();
        }
        if (c == '>' || c == ')') {
            return appended.exit();
        }*/
	appended : /*return*/;
	/*}

    private static <T> Optional<T> compileLast(String input, String infix, BiFunction<String, String, Optional<T>> mapper) {
        return compileInfix(input, infix,*/mapper) : /*Main::findLast,*/;
	/*}

    private static int findLast(String input, String infix) {
       */input.lastIndexOf(infix) : /*return*/;
	/*}

    private static <T>*/compileSuffix(input : string, suffix : string, mapper : /*Function*/<string, /*Optional*/</*T*/>>): /*Optional*/</*T*/> {
		/*if (!input.endsWith(suffix)) {
            return Optional*/.empty();
	}
	/*var content = input.substring(0, input.length()*/suffix.length()) : /*-*/;
	mapper.apply(/*content*/): /*return*/;
	/*}

    private static <T> Optional<T> compileFirst(String input, String infix, BiFunction<String, String, Optional<T>> mapper) {
        return compileInfix(input, infix,*/mapper) : /*Main::findFirst,*/;
	/*}

    private static <T> Optional<T> compileInfix(String input, String infix, BiFunction<String, String, Integer> locator, BiFunction<String, String, Optional<T>> mapper) {
        return compileSplit(split(input, infix,*/mapper) : /*locator),*/;
	/*}

    private static <T> Optional<T> compileSplit(Optional<Tuple<String, String>> splitter, BiFunction<String, String, Optional<T>> mapper) {
        return splitter.flatMap(tuple*/tuple.right)) : /*-> mapper.apply(tuple.left,*/;
	/*}

    private static Optional<Tuple<String, String>> split(String input, String infix, BiFunction<String, String, Integer> locator) {
        var index =*/infix) : /*locator.apply(input,*/;/*
        if (index < 0) {
            return Optional.empty();
        }*/
	/*var left*/input.substring(/*0*/, /* index*/): /*=*/;
	/*var right = input.substring(index*/infix.length()) : /*+*/;
	/*return Optional.of(new*/right)) : /*Tuple<>(left,*/;
	/*}

    private static int findFirst(String input, String infix) {
       */input.indexOf(infix) : /*return*/;
	/*}

    private static String generatePlaceholder(String input) {
        var replaced = input
                .replace("start", "start")
               */"end") : /*.replace("end",*/;
	/*return "start" + replaced*/"*/" : /*+*/;/*
    }
*/
}
/*

    private static Tuple<CompileState, String> compileRootSegment(CompileState state, String input) {
        return compileOrPlaceholder(state, input, List.of(
                Main::compileNamespaced,
                createStructureRule("class ", "class ")
        ));
    }*//**/