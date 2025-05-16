interface MethodHeader {
	generateWithAfterName(afterName : string): string;
}
class DivideState {
	segments : List<string>;
	input : string;
	index : number;
	buffer : StringBuilder;
	depth : number;
	constructor (segments : List<string>, buffer : StringBuilder, depth : number, input : string, index : number) {
		this.segments = segments;
		this.buffer = buffer;
		this.depth = depth;
		this.input = input;
		this.index = index;
	}
	constructor (input : string) {
		this(new ArrayList<>(), new StringBuilder(), 0, input, 0);
	}
	advance(): DivideState {
		this.segments.add(this.buffer.toString());
		this.buffer = new StringBuilder();
		return this;
	}
	append(c : string): DivideState {
		this.buffer.append(c);
		return this;
	}
	isLevel(): boolean {
		return this.depth === 0;
	}
	enter(): DivideState {
		this.depth++;
		return this;
	}
	exit(): DivideState {
		this.depth--;
		return this;
	}
	isShallow(): boolean {
		return this.depth === 1;
	}
	pop(): Optional<Tuple<DivideState, string>> {
		if (this.index >= this.input.length()){
			return Optional.empty();
		}
		let c : unknown = this.input.charAt(this.index);
		this.index++;
		return Optional.of(new Tuple<>(this, c));
	}
	popAndAppendToTuple(): Optional<Tuple<DivideState, string>> {
		return this.pop().map((inner) => new Tuple<>(inner.left.append(inner.right), inner.right));
	}
	popAndAppendToOption(): Optional<DivideState> {
		return this.popAndAppendToTuple().map(Tuple.left);
	}
	peek(): string {
		return this.input.charAt(this.index);
	}
	startsWith(slice : string): boolean {
		return this.input.substring(this.index).startsWith(slice);
	}
}
class Tuple<A, B> {
}
class CompileState {
	constructor () {
		this("", Optional.empty(), 0);
	}
	append(element : string): CompileState {
		return new CompileState(this.output + element, this.structureName, this.depth);
	}
	withStructureName(name : string): CompileState {
		return new CompileState(this.output, Optional.of(name), this.depth);
	}
	depth(): number {
		return this.depth;
	}
	enterDepth(): CompileState {
		return new CompileState(this.output, this.structureName, this.depth + 1);
	}
	exitDepth(): CompileState {
		return new CompileState(this.output, this.structureName, this.depth - 1);
	}
}
class Definition {
	generate(): string {
		return this.generateWithAfterName(" ");
	}
	generateWithAfterName(afterName : string): string {
		return this.name + afterName + ": " + this.type();
	}
}
class ConstructorHeader implements MethodHeader {
	generateWithAfterName(afterName : string): string {
		return "constructor " + afterName;
	}
}
class Main {
	main(): void {
		let source : unknown = Paths.get(".", "src", "java", "magma", "Main.java");
		let target : unknown = source.resolveSibling("main.ts");/*
        try {
            var input = Files.readString(source);
            Files.writeString(target, compileRoot(input));
        }*//* catch (IOException e) {
            throw new RuntimeException(e);
        }*/
	}
	compileRoot(input : string): string {
		let compiled : unknown = compileStatements(new CompileState(), input, Main.compileRootSegment);
		return compiled.left.output + compiled.right;
	}
	compileStatements(state : CompileState, input : string, mapper : BiFunction<CompileState, string, Tuple<CompileState, string>>): Tuple<CompileState, string> {
		return compileAll(state, input, Main.foldStatements, mapper, Main.mergeStatements);
	}
	compileAll(state : CompileState, input : string, folder : BiFunction<DivideState, string, DivideState>, mapper : BiFunction<CompileState, string, Tuple<CompileState, string>>, merger : BiFunction<StringBuilder, string, StringBuilder>): Tuple<CompileState, string> {
		let divisions : unknown = divide(input, folder);
		let folded : unknown = divisions.stream().reduce(new Tuple<CompileState, StringBuilder>(state, new StringBuilder()), (current, segment) => {
			let currentState : unknown = current.left;
			let currentElement : unknown = current.right;
			let mappedTuple : unknown = mapper.apply(currentState, segment);
			let mappedState : unknown = mappedTuple.left;
			let mappedElement : unknown = mappedTuple.right;
			return new Tuple<>(mappedState, merger.apply(currentElement, mappedElement));
		}, (_, next) => next);
		return new Tuple<>(folded.left, folded.right.toString());
	}
	mergeStatements(cache : StringBuilder, element : string): StringBuilder {
		return cache.append(element);
	}
	divide(input : string, folder : BiFunction<DivideState, string, DivideState>): List<string> {
		let current : unknown = new DivideState(input);
		while (true){
			let maybePopped : unknown = current.pop();
			if (maybePopped.isEmpty()){
				break;
			}
			let poppedTuple : unknown = maybePopped.get();
			let poppedState : unknown = poppedTuple.left;
			let popped : unknown = poppedTuple.right;
			current = foldSingleQuotes(poppedState, popped).or(() => foldDoubleQuotes(poppedState, popped)).orElseGet(() => folder.apply(poppedState, popped));
		}
		return current.advance().segments;
	}
	foldDoubleQuotes(state : DivideState, c : string): Optional<DivideState> {
		if (c !== "\""){
			return Optional.empty();
		}
		let appended : unknown = state.append(c);
		while (true){
			let maybeTuple : unknown = appended.popAndAppendToTuple();
			if (maybeTuple.isEmpty()){
				break;
			}
			let tuple : unknown = maybeTuple.get();
			appended = tuple.left;
			if (tuple.right === "\\"){
				appended = appended.popAndAppendToOption().orElse(appended);
			}
			if (tuple.right === "\""){
				break;
			}
		}
		return Optional.of(appended);
	}
	foldSingleQuotes(state : DivideState, c : string): Optional<DivideState> {
		if (c !== "\'"){
			return Optional.empty();
		}
		return state.append(c).popAndAppendToTuple().flatMap(Main.foldEscaped).flatMap(DivideState.popAndAppendToOption);
	}
	foldEscaped(tuple : Tuple<DivideState, string>): Optional<DivideState> {
		let state : unknown = tuple.left;
		let c : unknown = tuple.right;
		if (c === "\\"){
			return state.popAndAppendToOption();
		}
		return Optional.of(state);
	}
	foldStatements(state : DivideState, c : string): DivideState {
		let appended : unknown = state.append(c);
		if (c === ";" && appended.isLevel()){
			return appended.advance();
		}
		if (c === "}" && appended.isShallow()){
			return appended.advance().exit();
		}
		if (c === "{" || c === "("){
			return appended.enter();
		}
		if (c === "}" || c === ")"){
			return appended.exit();
		}
		return appended;
	}
	compileRootSegment(state : CompileState, input : string): Tuple<CompileState, string> {
		return compileOrPlaceholder(state, input, List.of(Main.compileWhitespace, Main.compileNamespaced, createStructureRule("class ", "class ")));
	}
	createStructureRule(sourceInfix : string, targetInfix : string): BiFunction<CompileState, string, Optional<Tuple<CompileState, string>>> {
		return (state1, input1) => compileFirst(input1, sourceInfix, (beforeKeyword, right1) => {
			return compileFirst(right1, "{", (beforeContent, withEnd) => {
				return compileSuffix(withEnd.strip(), "}", (inputContent) => {
					let strippedBeforeContent : unknown = beforeContent.strip();
					return compileFirst(strippedBeforeContent, "(", (rawName, s2) => {
						let name : unknown = rawName.strip();
						return assembleStructure(targetInfix, state1, beforeKeyword, inputContent, name);
					}).or(() => {
						return assembleStructure(targetInfix, state1, beforeKeyword, inputContent, strippedBeforeContent);
					});
				});
			});
		});
	}
	assembleStructure(targetInfix : string, state1 : CompileState, beforeKeyword : string, inputContent : string, name : string): Optional<Tuple<CompileState, string>> {
		let outputContentTuple : unknown = compileStatements(state1.withStructureName(name), inputContent, Main.compileClassSegment);
		let outputContentState : unknown = outputContentTuple.left;
		let outputContent : unknown = outputContentTuple.right;
		let generated : unknown = targetInfix + name + " {" + outputContent + "\n}\n";
		return Optional.of(new Tuple<>(outputContentState.append(generated), ""));
	}
	compileNamespaced(state : CompileState, input : string): Optional<Tuple<CompileState, string>> {
		let stripped : unknown = input.strip();
		if (stripped.startsWith("package ") || stripped.startsWith("import ")){
			return Optional.of(new Tuple<>(state, ""));
		}
		else {
			return Optional.empty();
		}
	}
	compileOrPlaceholder(state : CompileState, input : string, rules : List<BiFunction<CompileState, string, Optional<Tuple<CompileState, string>>>>): Tuple<CompileState, string> {
		return compileOr(state, input, rules).orElseGet(() => new Tuple<>(state, generatePlaceholder(input)));
	}
	compileOr(state : CompileState, input : string, rules : List<BiFunction<CompileState, string, Optional<Tuple<CompileState, string>>>>): Optional<Tuple<CompileState, string>> {
		return rules.stream().map((rule) => rule.apply(state, input)).flatMap(Optional.stream).findFirst();
	}
	compileClassSegment(state1 : CompileState, input1 : string): Tuple<CompileState, string> {
		return compileOrPlaceholder(state1, input1, List.of(Main.compileWhitespace, createStructureRule("class ", "class "), createStructureRule("interface ", "interface "), createStructureRule("record ", "class "), Main.compileMethod, Main.compileFieldDefinition));
	}
	compileMethod(state : CompileState, input : string): Optional<Tuple<CompileState, string>> {
		return compileFirst(input, "(", (beforeParams, withParams) => {
			return compileLast(beforeParams.strip(), " ", (_, name) => {
				if (state.structureName.filter(name.equals).isPresent()){
					return compileMethodWithBeforeParams(state, new ConstructorHeader(), withParams);
				}
				return compileDefinition(state, beforeParams).flatMap((tuple) => compileMethodWithBeforeParams(tuple.left, tuple.right, withParams));
			});
		});
	}
	compileMethodWithBeforeParams(state : CompileState, header : MethodHeader, withParams : string): Optional<Tuple<CompileState, string>> {
		return compileFirst(withParams, ")", (params, afterParams) => {
			let parametersTuple : unknown = compileValues(state, params, Main.compileParameter);
			let parametersState : unknown = parametersTuple.left;
			let parameters : unknown = parametersTuple.right;
			let headerGenerated : unknown = header.generateWithAfterName("(" + parameters + ")");
			return compilePrefix(afterParams.strip(), "{", (withoutContentStart) => {
				return compileSuffix(withoutContentStart.strip(), "}", (withoutContentEnd) => {
					let statementsTuple : unknown = compileFunctionStatements(parametersState.enterDepth().enterDepth(), withoutContentEnd);
					return Optional.of(new Tuple<>(statementsTuple.left.exitDepth().exitDepth(), "\n\t" + headerGenerated + " {" + statementsTuple.right + "\n\t}"));
				});
			}).or(() => {
				if (afterParams.strip().equals(";")){
					return Optional.of(new Tuple<>(parametersState, "\n\t" + headerGenerated + ";"));
				}
				return Optional.empty();
			});
		});
	}
	compileFunctionStatements(state : CompileState, input : string): Tuple<CompileState, string> {
		return compileStatements(state, input, Main.compileFunctionSegment);
	}
	compileFunctionSegment(state : CompileState, input : string): Tuple<CompileState, string> {
		return compileOrPlaceholder(state, input, List.of(Main.compileWhitespace, Main.compileEmptySegment, Main.compileBlock, Main.compileFunctionStatement, Main.compileReturnWithoutSuffix));
	}
	compileEmptySegment(state : CompileState, input : string): Optional<Tuple<CompileState, string>> {
		if (input.strip().equals(";")){
			return Optional.of(new Tuple<>(state, ";"));
		}
		else {
			return Optional.empty();
		}
	}
	compileReturnWithoutSuffix(state1 : CompileState, input1 : string): Optional<Tuple<CompileState, string>> {
		return compileReturn(input1, (withoutPrefix) => compileValue(state1, withoutPrefix)).map((tuple) => new Tuple<>(tuple.left, generateIndent(state1.depth) + tuple.right));
	}
	compileBlock(state : CompileState, input : string): Optional<Tuple<CompileState, string>> {
		return compileSuffix(input.strip(), "}", (withoutEnd) => {
			return compileSplit(splitFoldedLast(withoutEnd, "", Main.foldBlockStarts), (beforeContentWithEnd, content) => {
				return compileSuffix(beforeContentWithEnd, "{", (beforeContent) => {
					return compileBlockHeader(state, beforeContent).flatMap((headerTuple) => {
						let contentTuple : unknown = compileFunctionStatements(headerTuple.left.enterDepth(), content);
						let indent : unknown = generateIndent(state.depth());
						return Optional.of(new Tuple<>(contentTuple.left.exitDepth(), indent + headerTuple.right + "{" + contentTuple.right + indent + "}"));
					});
				});
			});
		});
	}
	foldBlockStarts(state : DivideState, c : string): DivideState {
		let appended : unknown = state.append(c);
		if (c === "{"){
			let entered : unknown = appended.enter();
			if (appended.isShallow()){
				return entered.advance();
			}
			else {
				return entered;
			}
		}
		if (c === "}"){
			return appended.exit();
		}
		return appended;
	}
	compileBlockHeader(state : CompileState, input : string): Optional<Tuple<CompileState, string>> {
		return compileOr(state, input, List.of(createConditionalRule("if"), createConditionalRule("while"), Main.compileElse));
	}
	createConditionalRule(prefix : string): BiFunction<CompileState, string, Optional<Tuple<CompileState, string>>> {
		return (state1, input1) => compilePrefix(input1.strip(), prefix, (withoutPrefix) => {
			let strippedCondition : unknown = withoutPrefix.strip();
			return compilePrefix(strippedCondition, "(", (withoutConditionStart) => {
				return compileSuffix(withoutConditionStart, ")", (withoutConditionEnd) => {
					let tuple : unknown = compileValueOrPlaceholder(state1, withoutConditionEnd);
					return Optional.of(new Tuple<>(tuple.left, prefix + " (" + tuple.right + ")"));
				});
			});
		});
	}
	compileElse(state : CompileState, input : string): Optional<Tuple<CompileState, string>> {
		if (input.strip().equals("else")){
			return Optional.of(new Tuple<>(state, "else "));
		}
		else {
			return Optional.empty();
		}
	}
	compileFunctionStatement(state : CompileState, input : string): Optional<Tuple<CompileState, string>> {
		return compileSuffix(input.strip(), ";", (withoutEnd) => {
			let valueTuple : unknown = compileFunctionStatementValue(state, withoutEnd);
			return Optional.of(new Tuple<>(valueTuple.left, generateIndent(state.depth()) + valueTuple.right + ";"));
		});
	}
	generateIndent(indent : number): string {
		return "\n" + "\t".repeat(indent);
	}
	compileFunctionStatementValue(state : CompileState, withoutEnd : string): Tuple<CompileState, string> {
		return compileOrPlaceholder(state, withoutEnd, List.of(Main.compileReturnWithValue, Main.compileAssignment, Main.compileInvokable, createPostRule("++"), createPostRule("--"), Main.compileBreak));
	}
	compileBreak(state : CompileState, input : string): Optional<Tuple<CompileState, string>> {
		if (input.strip().equals("break")){
			return Optional.of(new Tuple<>(state, "break"));
		}
		else {
			return Optional.empty();
		}
	}
	createPostRule(suffix : string): BiFunction<CompileState, string, Optional<Tuple<CompileState, string>>> {
		return (state1, input) => compileSuffix(input.strip(), suffix, (child) => {
			let tuple : unknown = compileValueOrPlaceholder(state1, child);
			return Optional.of(new Tuple<>(tuple.left, tuple.right + suffix));
		});
	}
	compileReturnWithValue(state : CompileState, input : string): Optional<Tuple<CompileState, string>> {
		return compileReturn(input, (value1) => compileValue(state, value1));
	}
	compileReturn(input : string, mapper : Function<string, Optional<Tuple<CompileState, string>>>): Optional<Tuple<CompileState, string>> {
		return compilePrefix(input.strip(), "return ", (value) => {
			return mapper.apply(value).flatMap((tuple) => {
				return Optional.of(new Tuple<>(tuple.left, "return " + tuple.right));
			});
		});
	}
	compileInvokable(state : CompileState, input : string): Optional<Tuple<CompileState, string>> {
		return compileSuffix(input.strip(), ")", (withoutEnd) => {
			return compileSplit(splitFoldedLast(withoutEnd, "", Main.foldInvocationStarts), (callerWithArgStart, arguments) => {
				return compileSuffix(callerWithArgStart, "(", (caller) => compilePrefix(caller.strip(), "new ", (type) => {
					let callerTuple : unknown = compileTypeOrPlaceholder(state, type);
					return assembleInvokable(callerTuple.left, "new " + callerTuple.right, arguments);
				}).or(() => {
					let callerTuple : unknown = compileValueOrPlaceholder(state, caller);
					return assembleInvokable(callerTuple.left, callerTuple.right, arguments);
				}));
			});
		});
	}
	splitFoldedLast(input : string, delimiter : string, folder : BiFunction<DivideState, string, DivideState>): Optional<Tuple<string, string>> {
		return splitFolded(input, folder, (divisions1) => selectLast(divisions1, delimiter));
	}
	splitFolded(input : string, folder : BiFunction<DivideState, string, DivideState>, selector : Function<List<string>, Optional<Tuple<string, string>>>): Optional<Tuple<string, string>> {
		let divisions : unknown = divide(input, folder);
		if (divisions.size() < 2){
			return Optional.empty();
		}
		return selector.apply(divisions);
	}
	selectLast(divisions : List<string>, delimiter : string): Optional<Tuple<string, string>> {
		let beforeLast : unknown = divisions.subList(0, divisions.size() - 1);
		let last : unknown = divisions.getLast();
		let joined : unknown = String.join(delimiter, beforeLast);
		return Optional.of(new Tuple<>(joined, last));
	}
	foldInvocationStarts(state : DivideState, c : string): DivideState {
		let appended : unknown = state.append(c);
		if (c === "("){
			let entered : unknown = appended.enter();
			if (entered.isShallow()){
				return entered.advance();
			}
			else {
				return entered;
			}
		}
		if (c === ")"){
			return appended.exit();
		}
		return appended;
	}
	assembleInvokable(state : CompileState, caller : string, arguments : string): Optional<Tuple<CompileState, string>> {
		let argumentsTuple : unknown = compileValues(state, arguments, Main.compileValueOrPlaceholder);
		return Optional.of(new Tuple<>(argumentsTuple.left, caller + "(" + argumentsTuple.right + ")"));
	}
	compileAssignment(state : CompileState, input : string): Optional<Tuple<CompileState, string>> {
		return compileFirst(input, "=", (destination, source) => {
			let sourceTuple : unknown = compileValueOrPlaceholder(state, source);
			let destinationTuple : unknown = compileValue(sourceTuple.left, destination).or(() => compileDefinition(sourceTuple.left, destination).map((tuple) => new Tuple<>(tuple.left, "let " + tuple.right.generate()))).orElseGet(() => new Tuple<>(sourceTuple.left, generatePlaceholder(destination)));
			return Optional.of(new Tuple<>(destinationTuple.left, destinationTuple.right + " = " + sourceTuple.right));
		});
	}
	compileValueOrPlaceholder(state : CompileState, input : string): Tuple<CompileState, string> {
		return compileValue(state, input).orElseGet(() => new Tuple<>(state, generatePlaceholder(input)));
	}
	compileValue(state : CompileState, input : string): Optional<Tuple<CompileState, string>> {
		return compileOr(state, input, List.of(createAccessRule("."), createAccessRule("::"), Main.compileSymbol, Main.compileLambda, Main.compileNot, Main.compileInvokable, Main.compileNumber, createOperatorRuleWithDifferentInfix("==", "==="), createOperatorRuleWithDifferentInfix("!=", "!=="), createOperatorRule("+"), createOperatorRule("-"), createOperatorRule("<="), createOperatorRule("<"), createOperatorRule("&&"), createOperatorRule("||"), createOperatorRule(">="), createTextRule("\""), createTextRule("'")));
	}
	createTextRule(slice : string): BiFunction<CompileState, string, Optional<Tuple<CompileState, string>>> {
		return (state1, input1) => {
			let stripped : unknown = input1.strip();
			if (!stripped.startsWith(slice) || !stripped.endsWith(slice) || stripped.length() <= slice.length()){
				return Optional.empty();
			}
			let value : unknown = stripped.substring(slice.length(), stripped.length() - slice.length());
			return Optional.of(new Tuple<>(state1, "\"" + value + "\""));
		};
	}
	compileNot(state : CompileState, input : string): Optional<Tuple<CompileState, string>> {
		return compilePrefix(input.strip(), "!", (withoutPrefix) => {
			let tuple : unknown = compileValueOrPlaceholder(state, withoutPrefix);
			return Optional.of(new Tuple<>(tuple.left, "!" + tuple.right));
		});
	}
	compileLambda(state : CompileState, input : string): Optional<Tuple<CompileState, string>> {
		return compileFirst(input, "->", (beforeArrow, afterArrow) => {
			let strippedBeforeArrow : unknown = beforeArrow.strip();
			if (isSymbol(strippedBeforeArrow)){
				return getCompileStateStringTuple(state, List.of(strippedBeforeArrow), afterArrow);
			}
			return compilePrefix(strippedBeforeArrow, "(", (withoutStart) => {
				return compileSuffix(withoutStart, ")", (withoutEnd) => {
					let paramNames : unknown = divide(withoutEnd, Main.foldValues).stream().map(String.strip).filter((value) => !value.isEmpty()).toList();
					if (paramNames.stream().allMatch(Main.isSymbol)){
						return getCompileStateStringTuple(state, paramNames, afterArrow);
					}
					else {
						return Optional.empty();
					}
				});
			});
		});
	}
	getCompileStateStringTuple(state : CompileState, paramNames : List<string>, afterArrow : string): Optional<Tuple<CompileState, string>> {
		let strippedAfterArrow : unknown = afterArrow.strip();
		return compilePrefix(strippedAfterArrow, "{", (withoutContentStart) => {
			return compileSuffix(withoutContentStart, "}", (withoutContentEnd) => {
				let statementsTuple : unknown = compileFunctionStatements(state.enterDepth(), withoutContentEnd);
				let statementsState : unknown = statementsTuple.left;
				let statements : unknown = statementsTuple.right;
				let exited : unknown = statementsState.exitDepth();
				return assembleLambda(exited, paramNames, "{" + statements + generateIndent(exited.depth) + "}");
			});
		}).or(() => {
			let tuple : unknown = compileValueOrPlaceholder(state, strippedAfterArrow);
			return assembleLambda(tuple.left, paramNames, tuple.right);
		});
	}
	assembleLambda(exited : CompileState, paramNames : List<string>, content : string): Optional<Tuple<CompileState, string>> {
		return Optional.of(new Tuple<>(exited, "(" + String.join(", ", paramNames) + ")" + " => " + content));
	}
	createOperatorRule(infix : string): BiFunction<CompileState, string, Optional<Tuple<CompileState, string>>> {
		return createOperatorRuleWithDifferentInfix(infix, infix);
	}
	createAccessRule(infix : string): BiFunction<CompileState, string, Optional<Tuple<CompileState, string>>> {
		return (state, input) => compileLast(input, infix, (child, rawProperty) => {
			let property : unknown = rawProperty.strip();
			if (isSymbol(property)){
				let tuple : unknown = compileValueOrPlaceholder(state, child);
				return Optional.of(new Tuple<>(tuple.left, tuple.right + "." + property));
			}
			else {
				return Optional.empty();
			}
		});
	}
	createOperatorRuleWithDifferentInfix(sourceInfix : string, targetInfix : string): BiFunction<CompileState, string, Optional<Tuple<CompileState, string>>> {
		return (state1, input1) => {
			return compileSplit(splitFolded(input1, foldOperator(sourceInfix), (divisions) => selectFirst(divisions, sourceInfix)), (left, right) => {
				let leftTuple : unknown = compileValueOrPlaceholder(state1, left);
				let rightTuple : unknown = compileValueOrPlaceholder(leftTuple.left, right);
				return Optional.of(new Tuple<>(rightTuple.left, leftTuple.right + " " + targetInfix + " " + rightTuple.right));
			});
		};
	}
	selectFirst(divisions : List<string>, delimiter : string): Optional<Tuple<string, string>> {
		let first : unknown = divisions.getFirst();
		let afterFirst : unknown = divisions.subList(1, divisions.size());
		let joined : unknown = String.join(delimiter, afterFirst);
		return Optional.of(new Tuple<>(first, joined));
	}
	foldOperator(infix : string): BiFunction<DivideState, string, DivideState> {
		return (state, c) => {
			if (c === infix.charAt(0) && state.startsWith(infix.substring(1))){
				let length : unknown = infix.length() - 1;
				let counter : unknown = 0;
				let current : unknown = state;
				while (counter < length){
					counter++;
					current = current.pop().map(Tuple.left).orElse(current);
				}
				return current.advance();
			}
			return state.append(c);
		};
	}
	compileNumber(state : CompileState, input : string): Optional<Tuple<CompileState, string>> {
		let stripped : unknown = input.strip();
		if (isNumber(stripped)){
			return Optional.of(new Tuple<>(state, stripped));
		}
		else {
			return Optional.empty();
		}
	}
	isNumber(input : string): boolean {
		return IntStream.range(0, input.length()).mapToObj(input.charAt).allMatch(Character.isDigit);
	}
	compileSymbol(state : CompileState, input : string): Optional<Tuple<CompileState, string>> {
		let stripped : unknown = input.strip();
		if (isSymbol(stripped)){
			return Optional.of(new Tuple<>(state, stripped));
		}
		else {
			return Optional.empty();
		}
	}
	isSymbol(input : string): boolean {
		return IntStream.range(0, input.length()).allMatch((index) => isSymbolChar(index, input.charAt(index)));
	}
	isSymbolChar(index : number, c : string): boolean {
		return c === "_" || Character.isLetter(c) || (index !== 0 && Character.isDigit(c));
	}
	compilePrefix(input : string, infix : string, mapper : Function<string, Optional<Tuple<CompileState, string>>>): Optional<Tuple<CompileState, string>> {
		if (!input.startsWith(infix)){
			return Optional.empty();
		}
		let slice : unknown = input.substring(infix.length());
		return mapper.apply(slice);
	}
	compileParameter(state : CompileState, input : string): Tuple<CompileState, string> {
		return compileOrPlaceholder(state, input, List.of(Main.compileWhitespace, (state1, input1) => {
			return compileDefinition(state1, input1).map((tuple) => new Tuple<>(tuple.left, tuple.right.generate()));
		}));
	}
	compileWhitespace(state : CompileState, input : string): Optional<Tuple<CompileState, string>> {
		if (input.isBlank()){
			return Optional.of(new Tuple<>(state, ""));
		}
		return Optional.empty();
	}
	compileFieldDefinition(state : CompileState, input : string): Optional<Tuple<CompileState, string>> {
		return compileSuffix(input.strip(), ";", (withoutEnd) => {
			let definitionTuple : unknown = compileDefinitionOrPlaceholder(state, withoutEnd);
			return Optional.of(new Tuple<>(definitionTuple.left, "\n\t" + definitionTuple.right + ";"));
		});
	}
	compileDefinitionOrPlaceholder(state : CompileState, input : string): Tuple<CompileState, string> {
		return compileDefinition(state, input).map((tuple) => new Tuple<>(tuple.left, tuple.right.generate())).orElseGet(() => new Tuple<>(state, generatePlaceholder(input)));
	}
	compileDefinition(state : CompileState, input : string): Optional<Tuple<CompileState, Definition>> {
		return compileLast(input.strip(), " ", (beforeName, name) => {
			let splits : unknown = splitFoldedLast(beforeName.strip(), " ", Main.foldTypeSeparators);
			return compileSplit(splits, (beforeType, type) => {
				return assembleDefinition(state, Optional.of(beforeType), name, type);
			}).or(() => {
				return assembleDefinition(state, Optional.empty(), name, beforeName);
			});
		});
	}
	foldTypeSeparators(state : DivideState, c : string): DivideState {
		if (c === " " && state.isLevel()){
			return state.advance();
		}
		let appended : unknown = state.append(c);
		if (c === "<"){
			return appended.enter();
		}
		if (c === ">"){
			return appended.exit();
		}
		return appended;
	}
	assembleDefinition(state : CompileState, maybeBeforeType : Optional<string>, name : string, type : string): Optional<Tuple<CompileState, Definition>> {
		let typeTuple : unknown = compileTypeOrPlaceholder(state, type);
		let generated : unknown = new Definition(maybeBeforeType, name, typeTuple.right);
		return Optional.of(new Tuple<>(typeTuple.left, generated));
	}
	compileTypeOrPlaceholder(state : CompileState, type : string): Tuple<CompileState, string> {
		return compileType(state, type).orElseGet(() => new Tuple<>(state, generatePlaceholder(type)));
	}
	compileType(state : CompileState, type : string): Optional<Tuple<CompileState, string>> {
		return compileOr(state, type, List.of(Main.compileGeneric, Main.compilePrimitive, Main.compileSymbolType));
	}
	compileSymbolType(state : CompileState, input : string): Optional<Tuple<CompileState, string>> {
		let stripped : unknown = input.strip();
		if (isSymbol(stripped)){
			return Optional.of(new Tuple<>(state, stripped));
		}
		return Optional.empty();
	}
	compilePrimitive(state : CompileState, input : string): Optional<Tuple<CompileState, string>> {
		return findPrimitiveValue(input.strip()).map((result) => new Tuple<>(state, result));
	}
	findPrimitiveValue(input : string): Optional<string> {
		let stripped : unknown = input.strip();
		if (stripped.equals("char") || stripped.equals("Character") || stripped.equals("String")){
			return Optional.of("string");
		}
		if (stripped.equals("int") || stripped.equals("Integer")){
			return Optional.of("number");
		}
		if (stripped.equals("boolean")){
			return Optional.of("boolean");
		}
		if (stripped.equals("var")){
			return Optional.of("unknown");
		}
		if (stripped.equals("void")){
			return Optional.of("void");
		}
		return Optional.empty();
	}
	compileGeneric(state : CompileState, input : string): Optional<Tuple<CompileState, string>> {
		return compileSuffix(input.strip(), ">", (withoutEnd) => {
			return compileFirst(withoutEnd, "<", (baseString, argumentsString) => {
				let argumentsTuple : unknown = compileValues(state, argumentsString, Main.compileTypeArgument);
				return Optional.of(new Tuple<>(argumentsTuple.left, baseString + "<" + argumentsTuple.right + ">"));
			});
		});
	}
	compileTypeArgument(state : CompileState, s : string): Tuple<CompileState, string> {
		return compileOrPlaceholder(state, s, List.of(Main.compileWhitespace, Main.compileType));
	}
	compileValues(state : CompileState, input : string, mapper : BiFunction<CompileState, string, Tuple<CompileState, string>>): Tuple<CompileState, string> {
		return compileAll(state, input, Main.foldValues, mapper, Main.mergeValues);
	}
	mergeValues(cache : StringBuilder, element : string): StringBuilder {
		if (cache.isEmpty()){
			return cache.append(element);
		}
		return cache.append(", ").append(element);
	}
	foldValues(state : DivideState, c : string): DivideState {
		if (c === "," && state.isLevel()){
			return state.advance();
		}
		let appended : unknown = state.append(c);
		if (c === "-"){
			let peeked : unknown = appended.peek();
			if (peeked === ">"){
				return appended.popAndAppendToOption().orElse(appended);
			}
			else {
				return appended;
			}
		}
		if (c === "<" || c === "("){
			return appended.enter();
		}
		if (c === ">" || c === ")"){
			return appended.exit();
		}
		return appended;
	}
	compileLast(input : string, infix : string, mapper : BiFunction<string, string, Optional<T>>): Optional<T> {
		return compileInfix(input, infix, Main.findLast, mapper);
	}
	findLast(input : string, infix : string): number {
		return input.lastIndexOf(infix);
	}
	compileSuffix(input : string, suffix : string, mapper : Function<string, Optional<T>>): Optional<T> {
		if (!input.endsWith(suffix)){
			return Optional.empty();
		}
		let content : unknown = input.substring(0, input.length() - suffix.length());
		return mapper.apply(content);
	}
	compileFirst(input : string, infix : string, mapper : BiFunction<string, string, Optional<T>>): Optional<T> {
		return compileInfix(input, infix, Main.findFirst, mapper);
	}
	compileInfix(input : string, infix : string, locator : BiFunction<string, string, number>, mapper : BiFunction<string, string, Optional<T>>): Optional<T> {
		return compileSplit(split(input, infix, locator), mapper);
	}
	compileSplit(splitter : Optional<Tuple<string, string>>, mapper : BiFunction<string, string, Optional<T>>): Optional<T> {
		return splitter.flatMap((tuple) => mapper.apply(tuple.left, tuple.right));
	}
	split(input : string, infix : string, locator : BiFunction<string, string, number>): Optional<Tuple<string, string>> {
		let index : unknown = locator.apply(input, infix);
		if (index < 0){
			return Optional.empty();
		}
		let left : unknown = input.substring(0, index);
		let right : unknown = input.substring(index + infix.length());
		return Optional.of(new Tuple<>(left, right));
	}
	findFirst(input : string, infix : string): number {
		return input.indexOf(infix);
	}
	generatePlaceholder(input : string): string {
		let replaced : unknown = input.replace("/*", "start").replace("*/", "end");
		return "/*" + replaced + "*/";
	}
}
