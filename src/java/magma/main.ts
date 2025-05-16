/*private*/interface MethodHeader {
	generateWithAfterName(afterName : string): string;
}
/*private static*/class DivideState {
	/*private final*/segments : /*List*/<string>;
	/*private final*/input : string;
	/*private*/index : number;
	/*private*/buffer : /*StringBuilder*/;
	/*private*/depth : number;
	constructor (segments : /*List*/<string>, buffer : /*StringBuilder*/, depth : number, input : string, index : number) {
		this.segments = segments;
		this.buffer = buffer;
		this.depth = depth;
		this.input = input;
		this.index = index;
	}
	constructor (input : string) {
		this(new /*ArrayList*/<>(), new /*StringBuilder*/(), 0, input, 0);
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
	/*public*/pop(): /*Optional*/</*Tuple*/</*DivideState*/, string>> {
		if (/*this.index >= this*/.input.length()){
			return Optional.empty();
		}
		let c : unknown = this.input.charAt(this.index);
		this.index++;
		return Optional.of(new /*Tuple*/<>(this, c));
	}
	/*public*/popAndAppendToTuple(): /*Optional*/</*Tuple*/</*DivideState*/, string>> {
		return this.pop().map(inner => new /*Tuple*/<>(inner.left.append(inner.right), inner.right));
	}
	/*public*/popAndAppendToOption(): /*Optional*/</*DivideState*/> {
		return this.popAndAppendToTuple().map(Tuple.left);
	}
	/*public*/peek(): string {
		return this.input.charAt(this.index);
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
		return new /*CompileState*/(this.output, this.structureName, this.depth - 1);
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
		let current : unknown = new /*DivideState*/(input);
		/*while (true) */{
			let maybePopped : unknown = current.pop();
			if (maybePopped.isEmpty()){
				/*break*/;
			}
			let poppedTuple : unknown = maybePopped.get();
			let poppedState : unknown = poppedTuple.left;
			let popped : unknown = poppedTuple.right;
			current = foldSingleQuotes(poppedState, popped).or(() - /*> foldDoubleQuotes*/(poppedState, popped)).orElseGet(() - /*> folder*/.apply(poppedState, popped));
		}
		return current.advance().segments;
	}
	/*private static*/foldDoubleQuotes(state : /*DivideState*/, c : string): /*Optional*/</*DivideState*/> {
		if (/*c != '\"'*/){
			return Optional.empty();
		}
		let appended : unknown = state.append(c);
		/*while (true) */{
			let maybeTuple : unknown = appended.popAndAppendToTuple();
			if (maybeTuple.isEmpty()){
				/*break*/;
			}
			let tuple : unknown = maybeTuple.get();
			appended = tuple.left;
			if (tuple.right === "\\"){
				appended = appended.popAndAppendToOption().orElse(appended);
			}
			if (tuple.right === "\""){
				/*break*/;
			}
		}
		return Optional.of(appended);
	}
	/*private static*/foldSingleQuotes(state : /*DivideState*/, c : string): /*Optional*/</*DivideState*/> {
		if (/*c != '\''*/){
			return Optional.empty();
		}
		return state.append(c).popAndAppendToTuple().flatMap(Main.foldEscaped).flatMap(DivideState.popAndAppendToOption);
	}
	/*private static*/foldEscaped(tuple : /*Tuple*/</*DivideState*/, string>): /*Optional*/</*DivideState*/> {
		let state : unknown = tuple.left;
		let c : unknown = tuple.right;
		if (c === "\\"){
			return state.popAndAppendToOption();
		}
		return Optional.of(state);
	}
	/*private static*/foldStatements(state : /*DivideState*/, c : string): /*DivideState*/ {
		let appended : unknown = state.append(c);
		if (c === ";" && appended.isLevel()){
			return appended.advance();
		}
		if (c === "}" && appended.isShallow()){
			return appended.advance().exit();
		}
		/*if (c == '*/{
			/*' */ || c = /*= '(') {
            return appended.enter()*/;
		}
		if (c === "}" || c === ")"){
			return appended.exit();
		}
		return appended;
	}
	/*private static*/compileRootSegment(state : /*CompileState*/, input : string): /*Tuple*/</*CompileState*/, string> {
		return compileOrPlaceholder(state, input, List.of(Main.compileNamespaced, createStructureRule("class ", "class ")));
	}
	/*private static*/createStructureRule(sourceInfix : string, targetInfix : string): /*BiFunction*/</*CompileState*/, string, /*Optional*/</*Tuple*/</*CompileState*/, string>>> {
		return (/*state1*/, /* input1*/) - /*> compileFirst*/(/*input1*/, sourceInfix, (beforeKeyword, /* right1*/) - /*> {
            return compileFirst*/(/*right1*/, "{", /* (beforeContent, withEnd*/) - /*> {
                return compileSuffix(withEnd.strip(), "}", inputContent */ - /*> {
                    var strippedBeforeContent = beforeContent.strip();
                    return compileFirst*/(strippedBeforeContent, "(", /* (rawName, s2*/) - /*> {
                        var name = rawName.strip();
                        return assembleStructure(targetInfix, state1, beforeKeyword, inputContent, name);
                    }).or(*/() - /*> {
                        return assembleStructure(targetInfix, state1, beforeKeyword, inputContent, strippedBeforeContent);
                    });
                });
            });
        }*/);
	}
	/*private static*/assembleStructure(targetInfix : string, state1 : /*CompileState*/, beforeKeyword : string, inputContent : string, name : string): /*Optional*/</*Tuple*/</*CompileState*/, string>> {
		let outputContentTuple : unknown = compileStatements(/*state1*/.withStructureName(name), inputContent, Main.compileClassSegment);
		let outputContentState : unknown = outputContentTuple.left;
		let outputContent : unknown = outputContentTuple.right;
		let generated : unknown = generatePlaceholder(beforeKeyword.strip()) + targetInfix + name + " {" + outputContent + "\n}\n";
		return Optional.of(new /*Tuple*/<>(outputContentState.append(generated), ""));
	}
	/*private static*/compileNamespaced(state : /*CompileState*/, input : string): /*Optional*/</*Tuple*/</*CompileState*/, string>> {
		let stripped : unknown = input.strip();
		if (stripped.startsWith("package ") || stripped.startsWith("import ")){
			return Optional.of(new /*Tuple*/<>(state, ""));
		}
		/*else */{
			return Optional.empty();
		}
	}
	/*private static*/compileOrPlaceholder(state : /*CompileState*/, input : string, rules : /*List*/</*BiFunction*/</*CompileState*/, string, /*Optional*/</*Tuple*/</*CompileState*/, string>>>>): /*Tuple*/</*CompileState*/, string> {
		return compileOr(state, input, rules).orElseGet(() - /*> new Tuple*/ < /*>*/(state, generatePlaceholder(input)));
	}
	/*private static*/compileOr(state : /*CompileState*/, input : string, rules : /*List*/</*BiFunction*/</*CompileState*/, string, /*Optional*/</*Tuple*/</*CompileState*/, string>>>>): /*Optional*/</*Tuple*/</*CompileState*/, string>> {
		/*for (var rule : rules) */{
			let maybeTuple : unknown = rule.apply(state, input);
			if (maybeTuple.isPresent()){
				return maybeTuple;
			}
		}
		return Optional.empty();
	}
	/*private static*/compileClassSegment(state1 : /*CompileState*/, input1 : string): /*Tuple*/</*CompileState*/, string> {
		return compileOrPlaceholder(/*state1*/, /* input1*/, List.of(Main.compileWhitespace, createStructureRule("class ", "class "), createStructureRule("interface ", "interface "), createStructureRule("record ", "class "), Main.compileMethod, Main.compileFieldDefinition));
	}
	/*private static*/compileMethod(state : /*CompileState*/, input : string): /*Optional*/</*Tuple*/</*CompileState*/, string>> {
		return compileFirst(input, "(", (beforeParams, withParams) - /*> {
            return compileLast*/(beforeParams.strip(), " ", /* (_, name*/) - /*> {
                if (state.structureName.filter(name::equals).isPresent()) {
                    return compileMethodWithBeforeParams(state, new ConstructorHeader(), withParams);
                }

                return compileDefinition(state, beforeParams)
                        .flatMap(tuple */ - /*> compileMethodWithBeforeParams(tuple.left, tuple.right, withParams));
            });
        }*/);
	}
	/*private static*/compileMethodWithBeforeParams(state : /*CompileState*/, header : /*MethodHeader*/, withParams : string): /*Optional*/</*Tuple*/</*CompileState*/, string>> {
		return compileFirst(withParams, ")", (params, afterParams) - /*> {
            var parametersTuple = compileValues(state, params, Main::compileParameter);
            var parametersState = parametersTuple.left;
            var parameters = parametersTuple.right;

            var headerGenerated = header.generateWithAfterName("(" */ + parameters + ")");
            return compilePrefix(afterParams.strip(), "{", withoutContentStart -> {
                return compileSuffix(withoutContentStart.strip(), "}", withoutContentEnd -> {
                    var statementsTuple = compileFunctionStatements(parametersState.enterDepth().enterDepth(), withoutContentEnd);

                    return Optional.of(new Tuple<>(statementsTuple.left.exitDepth().exitDepth(), "\n\t" + headerGenerated + " {" + statementsTuple.right + "\n\t}"));
                });
            }).or(() -> {
                if (afterParams.strip().equals(";")) {
                    return Optional.of(new Tuple<>(parametersState, "\n\t" + headerGenerated + /* ";"));
                }

                return Optional.empty();
            });
        }*/);
	}
	/*private static*/compileFunctionStatements(state : /*CompileState*/, input : string): /*Tuple*/</*CompileState*/, string> {
		return compileStatements(state, input, Main.compileFunctionSegment);
	}
	/*private static*/compileFunctionSegment(state : /*CompileState*/, input : string): /*Tuple*/</*CompileState*/, string> {
		return compileOrPlaceholder(state, input, List.of(Main.compileWhitespace, Main.compileBlock, Main.compileFunctionStatement));
	}
	/*private static*/compileBlock(state : /*CompileState*/, input : string): /*Optional*/</*Tuple*/</*CompileState*/, string>> {
		return compileSuffix(input.strip(), "}", withoutEnd => {
			return compileFirst(withoutEnd, "{", (beforeContent, content) - /*> {
                var headerTuple = compileBlockHeader(state, beforeContent);
                var contentTuple = compileFunctionStatements(headerTuple.left.enterDepth(), content);

                var indent = generateIndent(state.depth());
                return Optional.of(new Tuple*/ < /*>(contentTuple.left.exitDepth(), indent */ + headerTuple.right + "{" + contentTuple.right + indent + /* "}"));
            }*/);
		});
	}
	/*private static*/compileBlockHeader(state : /*CompileState*/, input : string): /*Tuple*/</*CompileState*/, string> {
		return compileOrPlaceholder(state, input, List.of(Main.compileIf));
	}
	/*private static*/compileIf(state : /*CompileState*/, input : string): /*Optional*/</*Tuple*/</*CompileState*/, string>> {
		return compilePrefix(input.strip(), "if", withoutPrefix => {
			let strippedCondition : unknown = withoutPrefix.strip();
			return compilePrefix(strippedCondition, "(", withoutConditionStart => {
				return compileSuffix(withoutConditionStart, ")", withoutConditionEnd => {
					let tuple : unknown = compileValueOrPlaceholder(state, withoutConditionEnd);
					return Optional.of(new /*Tuple*/<>(tuple.left, "if (" + tuple.right + ")"));
				});
			});
		});
	}
	/*private static*/compileFunctionStatement(state : /*CompileState*/, input : string): /*Optional*/</*Tuple*/</*CompileState*/, string>> {
		return compileSuffix(input.strip(), ";", withoutEnd => {
			let valueTuple : unknown = compileFunctionStatementValue(state, withoutEnd);
			return Optional.of(new /*Tuple*/<>(valueTuple.left, generateIndent(state.depth()) + valueTuple.right + ";"));
		});
	}
	/*private static*/generateIndent(indent : number): string {
		return "\n" + "\t".repeat(indent);
	}
	/*private static*/compileFunctionStatementValue(state : /*CompileState*/, withoutEnd : string): /*Tuple*/</*CompileState*/, string> {
		return compileOrPlaceholder(state, withoutEnd, List.of(Main.compileReturn, Main.compileAssignment, Main.compileInvokable, createPostRule("++"), createPostRule("--")));
	}
	/*private static*/createPostRule(suffix : string): /*BiFunction*/</*CompileState*/, string, /*Optional*/</*Tuple*/</*CompileState*/, string>>> {
		return (/*state1*/, input) - /*> compileSuffix*/(input.strip(), suffix, child => {
			let tuple : unknown = compileValueOrPlaceholder(/*state1*/, child);
			return Optional.of(new /*Tuple*/<>(tuple.left, tuple.right + suffix));
		});
	}
	/*private static*/compileReturn(state : /*CompileState*/, input : string): /*Optional*/</*Tuple*/</*CompileState*/, string>> {
		return compilePrefix(input.strip(), "return ", value => {
			let tuple : unknown = compileValueOrPlaceholder(state, value);
			return Optional.of(new /*Tuple*/<>(tuple.left, "return " + tuple.right));
		});
	}
	/*private static*/compileInvokable(state : /*CompileState*/, input : string): /*Optional*/</*Tuple*/</*CompileState*/, string>> {
		return compileSuffix(input.strip(), ")", withoutEnd => {
			return compileSplit(splitFolded(withoutEnd, "", Main.foldInvocationStarts), (callerWithArgStart, arguments) - /*> {
                return compileSuffix(callerWithArgStart, "(", caller */ - /*> compilePrefix(caller.strip(), "new ", type */ - /*> {
                    var callerTuple = compileTypeOrPlaceholder(state, type);
                    return assembleInvokable(callerTuple.left, "new " */ + /* callerTuple.right, arguments);
                }).or(() */ - /*> {
                    var callerTuple = compileValueOrPlaceholder(state, caller);
                    return assembleInvokable(callerTuple.left, callerTuple.right, arguments);
                }));
            }*/);
		});
	}
	/*private static*/splitFolded(input : string, delimiter : string, folder : /*BiFunction*/</*DivideState*/, string, /* DivideState*/>): /*Optional*/</*Tuple*/<string, string>> {
		let divisions : unknown = divide(input, folder);
		if (divisions.size() < 2){
			return Optional.empty();
		}
		let beforeLast : unknown = divisions.subList(0, divisions.size() - 1);
		let last : unknown = divisions.getLast();
		let joined : unknown = String.join(delimiter, beforeLast);
		return Optional.of(new /*Tuple*/<>(joined, last));
	}
	/*private static*/foldInvocationStarts(state : /*DivideState*/, c : string): /*DivideState*/ {
		let appended : unknown = state.append(c);
		if (c === "("){
			let entered : unknown = appended.enter();
			if (entered.isShallow()){
				return entered.advance();
			}
			/*else */{
				return entered;
			}
		}
		if (c === ")"){
			return appended.exit();
		}
		return appended;
	}
	/*private static*/assembleInvokable(state : /*CompileState*/, caller : string, arguments : string): /*Optional*/</*Tuple*/</*CompileState*/, string>> {
		let argumentsTuple : unknown = compileValues(state, arguments, Main.compileValueOrPlaceholder);
		return Optional.of(new /*Tuple*/<>(argumentsTuple.left, caller + "(" + argumentsTuple.right + ")"));
	}
	/*private static*/compileAssignment(state : /*CompileState*/, input : string): /*Optional*/</*Tuple*/</*CompileState*/, string>> {
		return compileFirst(input, "=", (destination, source) - /*> {
            var sourceTuple = compileValueOrPlaceholder(state, source);

            var destinationTuple = compileValue*/(sourceTuple.left, destination).or(/*(*/) - /*> compileDefinition(sourceTuple.left, destination).map(tuple */ - /*> new Tuple*/ < /*>(tuple.left, "let " */ + tuple.right.generate(/*))))
                    .orElseGet((*/) - /*> new Tuple*/ < /*>(sourceTuple.left, generatePlaceholder(destination)));

            return Optional.of(new Tuple*/ < /*>(destinationTuple.left, destinationTuple*/.right + " = " + /* sourceTuple.right));
        }*/);
	}
	/*private static*/compileValueOrPlaceholder(state : /*CompileState*/, input : string): /*Tuple*/</*CompileState*/, string> {
		return compileValue(state, input).orElseGet(() - /*> new Tuple*/ < /*>*/(state, generatePlaceholder(input)));
	}
	/*private static*/compileValue(state : /*CompileState*/, input : string): /*Optional*/</*Tuple*/</*CompileState*/, string>> {
		return compileOr(state, input, List.of(createAccessRule("."), createAccessRule("::"), Main.compileSymbol, Main.compileLambda, Main.compileNot, Main.compileInvokable, Main.compileNumber, createOperatorRuleWithDifferentInfixes(/*"*/ === /*"*/, /* "*/ === /*="*/), createTextRule("\""), createTextRule("'"), createOperatorRule("+"), createOperatorRule("-"), createOperatorRule("<"), createOperatorRule("&&"), createOperatorRule("||")));
	}
	/*private static*/createTextRule(slice : string): /*BiFunction*/</*CompileState*/, string, /*Optional*/</*Tuple*/</*CompileState*/, string>>> {
		/*return (state1, input1) -> */{
			let stripped : unknown = /*input1*/.strip();
			if (!stripped.startsWith(slice) || !stripped.endsWith(slice) || stripped.length() < /*= slice*/.length()){
				return Optional.empty();
			}
			let value : unknown = stripped.substring(slice.length(), stripped.length() - slice.length());
			return Optional.of(new /*Tuple*/<>(/*state1*/, "\"" + value + "\""));
		}
		/**/;
	}
	/*private static*/compileNot(state : /*CompileState*/, input : string): /*Optional*/</*Tuple*/</*CompileState*/, string>> {
		return compilePrefix(input.strip(), "!", withoutPrefix => {
			let tuple : unknown = compileValueOrPlaceholder(state, withoutPrefix);
			return Optional.of(new /*Tuple*/<>(tuple.left, "!" + tuple.right));
		});
	}
	/*private static*/compileLambda(state : /*CompileState*/, input : string): /*Optional*/</*Tuple*/</*CompileState*/, string>> {
		return compileFirst(input, "->", (beforeArrow, afterArrow) - /*> {
            var strippedBeforeArrow = beforeArrow.strip();
            if (isSymbol(strippedBeforeArrow)) {
                var strippedAfterArrow = afterArrow.strip();
                return compilePrefix(strippedAfterArrow, "{", withoutContentStart */ - /*> {
                    return compileSuffix(withoutContentStart, "}", withoutContentEnd */ - /*> {
                        var statementsTuple = compileFunctionStatements(state.enterDepth(), withoutContentEnd);
                        var statementsState = statementsTuple.left;
                        var statements = statementsTuple.right;

                        var exited = statementsState.exitDepth();
                        return Optional.of(new Tuple*/ < /*>(exited, strippedBeforeArrow */ + " => {" + statements + generateIndent(exited.depth) + /* "}"));
                    });
                }).or(() */ - /*> {
                    var tuple = compileValueOrPlaceholder(state, strippedAfterArrow);
                    return Optional.of(new Tuple*/ < /*>(tuple.left, strippedBeforeArrow */ + " => " + /* tuple.right));
                });
            }
            else {
                return Optional.empty();
            }
        }*/);
	}
	/*private static*/createOperatorRule(infix : string): /*BiFunction*/</*CompileState*/, string, /*Optional*/</*Tuple*/</*CompileState*/, string>>> {
		return createOperatorRuleWithDifferentInfixes(infix, infix);
	}
	/*private static*/createAccessRule(infix : string): /*BiFunction*/</*CompileState*/, string, /*Optional*/</*Tuple*/</*CompileState*/, string>>> {
		return (state, input) - /*> compileLast*/(input, infix, (child, rawProperty) - /*> {
            var tuple = compileValueOrPlaceholder(state, child);
            var property = rawProperty.strip();
            if (isSymbol(property)) {
                return Optional.of(new Tuple*/ < /*>(tuple.left, tuple*/.right + "." + /* property));
            }
            else {
                return Optional.empty();
            }
        }*/);
	}
	/*private static*/createOperatorRuleWithDifferentInfixes(sourceInfix : string, targetInfix : string): /*BiFunction*/</*CompileState*/, string, /*Optional*/</*Tuple*/</*CompileState*/, string>>> {
		return (/*state1*/, /* input1*/) - /*> compileFirst*/(/*input1*/, sourceInfix, (left, right) - /*> {
            var leftTuple = compileValueOrPlaceholder(state1, left);
            var rightTuple = compileValueOrPlaceholder(leftTuple.left, right);
            return Optional.of(new Tuple*/ < /*>(rightTuple.left, leftTuple*/.right + " " + targetInfix + " " + /* rightTuple.right));
        }*/);
	}
	/*private static*/compileNumber(state : /*CompileState*/, input : string): /*Optional*/</*Tuple*/</*CompileState*/, string>> {
		let stripped : unknown = input.strip();
		if (isNumber(stripped)){
			return Optional.of(new /*Tuple*/<>(state, stripped));
		}
		/*else */{
			return Optional.empty();
		}
	}
	/*private static*/isNumber(input : string): boolean {
		/*for (var i = 0; i < input.length(); i++) */{
			let c : unknown = input.charAt(i);
			if (Character.isDigit(c)){
				/*continue*/;
			}
			return false;
		}
		return true;
	}
	/*private static*/compileSymbol(state : /*CompileState*/, input : string): /*Optional*/</*Tuple*/</*CompileState*/, string>> {
		let stripped : unknown = input.strip();
		if (isSymbol(stripped)){
			return Optional.of(new /*Tuple*/<>(state, stripped));
		}
		/*else */{
			return Optional.empty();
		}
	}
	/*private static*/isSymbol(input : string): boolean {
		/*for (var i = 0; i < input.length(); i++) */{
			let c : unknown = input.charAt(i);
			if (Character.isLetter(c)){
				/*continue*/;
			}
			return false;
		}
		return true;
	}
	/*private static*/compilePrefix(input : string, infix : string, mapper : /*Function*/<string, /*Optional*/</*Tuple*/</*CompileState*/, string>>>): /*Optional*/</*Tuple*/</*CompileState*/, string>> {
		if (!input.startsWith(infix)){
			return Optional.empty();
		}
		let slice : unknown = input.substring(infix.length());
		return mapper.apply(slice);
	}
	/*private static*/compileParameter(state : /*CompileState*/, input : string): /*Tuple*/</*CompileState*/, string> {
		return compileOrPlaceholder(state, input, List.of(Main.compileWhitespace, (/*state1*/, /* input1*/) - /*> {
                    return compileDefinition(state1, input1).map(tuple */ - /*> new Tuple*/ < /*>(tuple.left, tuple.right.generate()));
                }
        */));
	}
	/*private static*/compileWhitespace(state : /*CompileState*/, input : string): /*Optional*/</*Tuple*/</*CompileState*/, string>> {
		if (input.isBlank()){
			return Optional.of(new /*Tuple*/<>(state, ""));
		}
		return Optional.empty();
	}
	/*private static*/compileFieldDefinition(state : /*CompileState*/, input : string): /*Optional*/</*Tuple*/</*CompileState*/, string>> {
		return compileSuffix(input.strip(), ";", withoutEnd => {
			let definitionTuple : unknown = compileDefinitionOrPlaceholder(state, withoutEnd);
			return Optional.of(new /*Tuple*/<>(definitionTuple.left, "\n\t" + definitionTuple.right + ";"));
		});
	}
	/*private static*/compileDefinitionOrPlaceholder(state : /*CompileState*/, input : string): /*Tuple*/</*CompileState*/, string> {
		return compileDefinition(state, input).map(tuple => new /*Tuple*/<>(tuple.left, tuple.right.generate())).orElseGet(() - /*> new Tuple*/ < /*>*/(state, generatePlaceholder(input)));
	}
	/*private static*/compileDefinition(state : /*CompileState*/, input : string): /*Optional*/</*Tuple*/</*CompileState*/, /* Definition*/>> {
		return compileLast(input.strip(), " ", (beforeName, name) - /*> {
            var splits = splitFolded(beforeName.strip(), " ", Main::foldTypeSeparators);
            return compileSplit*/(splits, /* (beforeType, type*/) - /*> {
                return assembleDefinition(state, Optional.of(beforeType), name, type);
            }).or(*/() - /*> {
                return assembleDefinition(state, Optional.empty(), name, beforeName);
            });
        }*/);
	}
	/*private static*/foldTypeSeparators(state : /*DivideState*/, c : string): /*DivideState*/ {
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
	/*private static*/assembleDefinition(state : /*CompileState*/, maybeBeforeType : /*Optional*/<string>, name : string, type : string): /*Optional*/</*Tuple*/</*CompileState*/, /* Definition*/>> {
		let typeTuple : unknown = compileTypeOrPlaceholder(state, type);
		let generated : unknown = new /*Definition*/(maybeBeforeType, name, typeTuple.right);
		return Optional.of(new /*Tuple*/<>(typeTuple.left, generated));
	}
	/*private static*/compileTypeOrPlaceholder(state : /*CompileState*/, type : string): /*Tuple*/</*CompileState*/, string> {
		return compileType(state, type).orElseGet(() - /*> new Tuple*/ < /*>*/(state, generatePlaceholder(type)));
	}
	/*private static*/compileType(state : /*CompileState*/, type : string): /*Optional*/</*Tuple*/</*CompileState*/, string>> {
		return compileOr(state, type, List.of(Main.compileGeneric, Main.compilePrimitive));
	}
	/*private static*/compilePrimitive(state : /*CompileState*/, input : string): /*Optional*/</*Tuple*/</*CompileState*/, string>> {
		return findPrimitiveValue(input.strip()).map(result => new /*Tuple*/<>(state, result));
	}
	/*private static*/findPrimitiveValue(input : string): /*Optional*/<string> {
		/*return switch (input.strip()) */{
			/*case "char", "Character", "String" */ - /*> Optional*/.of("string");
			/*case "int", "Integer" */ - /*> Optional*/.of("number");
			/*case "boolean" */ - /*> Optional*/.of("boolean");
			/*case "var" */ - /*> Optional*/.of("unknown");
			/*case "void" */ - /*> Optional*/.of("void");
			default => Optional.empty();
		}
		/**/;
	}
	/*private static*/compileGeneric(state : /*CompileState*/, input : string): /*Optional*/</*Tuple*/</*CompileState*/, string>> {
		return compileSuffix(input.strip(), ">", withoutEnd => {
			return compileFirst(withoutEnd, "<", (baseString, argumentsString) - /*> {
                var argumentsTuple = compileValues(state, argumentsString, Main::compileTypeArgument);
                return Optional*/.of(/*new Tuple*/ < /*>(argumentsTuple.left, generatePlaceholder(baseString*/) + "<" + argumentsTuple.right + /* ">"));
            }*/);
		});
	}
	/*private static*/compileTypeArgument(state : /*CompileState*/, s : string): /*Tuple*/</*CompileState*/, string> {
		return compileOrPlaceholder(state, s, List.of(Main.compileWhitespace, Main.compileType));
	}
	/*private static*/compileValues(state : /*CompileState*/, input : string, mapper : /*BiFunction*/</*CompileState*/, string, /*Tuple*/</*CompileState*/, string>>): /*Tuple*/</*CompileState*/, string> {
		return compileAll(state, input, Main.foldValues, mapper, Main.mergeValues);
	}
	/*private static*/mergeValues(cache : /*StringBuilder*/, element : string): /*StringBuilder*/ {
		if (cache.isEmpty()){
			return cache.append(element);
		}
		return cache.append(", ").append(element);
	}
	/*private static*/foldValues(state : /*DivideState*/, c : string): /*DivideState*/ {
		if (c === "," && state.isLevel()){
			return state.advance();
		}
		let appended : unknown = state.append(c);
		if (c === "-"){
			let peeked : unknown = appended.peek();
			if (peeked === ">"){
				return appended.popAndAppendToOption().orElse(appended);
			}
			/*else */{
				return appended;
			}
		}
		if (c === /* '*/ < /*' */ || c === "("){
			return appended.enter();
		}
		if (c === ">" || c === ")"){
			return appended.exit();
		}
		return appended;
	}
	/*private static <T>*/compileLast(input : string, infix : string, mapper : /*BiFunction*/<string, string, /*Optional*/</*T*/>>): /*Optional*/</*T*/> {
		return compileInfix(input, infix, Main.findLast, mapper);
	}
	/*private static*/findLast(input : string, infix : string): number {
		return input.lastIndexOf(infix);
	}
	/*private static <T>*/compileSuffix(input : string, suffix : string, mapper : /*Function*/<string, /*Optional*/</*T*/>>): /*Optional*/</*T*/> {
		if (!input.endsWith(suffix)){
			return Optional.empty();
		}
		let content : unknown = input.substring(0, input.length() - suffix.length());
		return mapper.apply(content);
	}
	/*private static <T>*/compileFirst(input : string, infix : string, mapper : /*BiFunction*/<string, string, /*Optional*/</*T*/>>): /*Optional*/</*T*/> {
		return compileInfix(input, infix, Main.findFirst, mapper);
	}
	/*private static <T>*/compileInfix(input : string, infix : string, locator : /*BiFunction*/<string, string, number>, mapper : /*BiFunction*/<string, string, /*Optional*/</*T*/>>): /*Optional*/</*T*/> {
		return compileSplit(split(input, infix, locator), mapper);
	}
	/*private static <T>*/compileSplit(splitter : /*Optional*/</*Tuple*/<string, string>>, mapper : /*BiFunction*/<string, string, /*Optional*/</*T*/>>): /*Optional*/</*T*/> {
		return splitter.flatMap(tuple => mapper.apply(tuple.left, tuple.right));
	}
	/*private static*/split(input : string, infix : string, locator : /*BiFunction*/<string, string, number>): /*Optional*/</*Tuple*/<string, string>> {
		let index : unknown = locator.apply(input, infix);
		if (index < 0){
			return Optional.empty();
		}
		let left : unknown = input.substring(0, index);
		let right : unknown = input.substring(index + infix.length());
		return Optional.of(new /*Tuple*/<>(left, right));
	}
	/*private static*/findFirst(input : string, infix : string): number {
		return input.indexOf(infix);
	}
	/*private static*/generatePlaceholder(input : string): string {
		let replaced : unknown = input.replace("/*", "start").replace("*/", "end");
		return "/*" + replaced + "*/";
	}
}
/**/