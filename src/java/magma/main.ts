/* private */interface Option<T>/*   */ {
	map<R>(mapper : (T) => R) : Option<R>;
	isPresent() : boolean;
	orElse(other : T) : T;
	filter(predicate : (T) => boolean) : Option<T>;
	orElseGet(supplier : () => T) : T;
	or(other : () => Option<T>) : Option<T>;
	flatMap<R>(mapper : (T) => Option<R>) : Option<R>;
	isEmpty() : boolean;
}
/* private */interface Collector<T, C>/*   */ {
	createInitial() : C;
	fold(current : C, element : T) : C;
}
/* private */interface Iterator<T>/*   */ {
	fold<R>(initial : R, folder : (R, T) => R) : R;
	map<R>(mapper : (T) => R) : Iterator<R>;
	collect<R>(collector : Collector<T, R>) : R;
}
/* private */interface List<T>/*   */ {
	add(element : T) : List<T>;
	iterate() : Iterator<T>;
	removeLast() : Option<[List<T>, T]>;
	get(index : number) : T;
}
/* private */interface Head<T>/*   */ {
	next() : Option<T>;
}
/* private */class Some<T>/* (T value) implements Option<T>  */ {
	/* @Override
        public  */ map<R>(mapper : (T) => R) : Option<R> {
		return /* new Some<> */(mapper.apply(this.value));
	}
	/* @Override
        public */ isPresent() : boolean {
		return true;
	}
	/* @Override
        public */ orElse(other : T) : T {
		return this.value;
	}
	/* @Override
        public */ filter(predicate : (T) => boolean) : Option<T> {
		/* if (predicate.test(this.value))  */{
			return this;
		}
		return /* new None<> */();
	}
	/* @Override
        public */ orElseGet(supplier : () => T) : T {
		return this.value;
	}
	/* @Override
        public */ or(other : () => Option<T>) : Option<T> {
		return this;
	}
	/* @Override
        public  */ flatMap<R>(mapper : (T) => Option<R>) : Option<R> {
		return mapper.apply(this.value);
	}
	/* @Override
        public */ isEmpty() : boolean {
		return false;
	}
}
/* private static */class None<T>/*  implements Option<T>  */ {
	/* @Override
        public  */ map<R>(mapper : (T) => R) : Option<R> {
		return /* new None<> */();
	}
	/* @Override
        public */ isPresent() : boolean {
		return false;
	}
	/* @Override
        public */ orElse(other : T) : T {
		return other;
	}
	/* @Override
        public */ filter(predicate : (T) => boolean) : Option<T> {
		return /* new None<> */();
	}
	/* @Override
        public */ orElseGet(supplier : () => T) : T {
		return supplier.get();
	}
	/* @Override
        public */ or(other : () => Option<T>) : Option<T> {
		return other.get();
	}
	/* @Override
        public  */ flatMap<R>(mapper : (T) => Option<R>) : Option<R> {
		return /* new None<> */();
	}
	/* @Override
        public */ isEmpty() : boolean {
		return true;
	}
}
/* private */class HeadedIterator<T>/* (Head<T> head) implements Iterator<T>  */ {
	/* @Override
        public  */ fold<R>(initial : R, folder : (R, T) => R) : R {
		let current : var = initial;
		/* while (true)  */{
			let finalCurrent : R = current;
			let optional : var = this.head.next().map((inner) => folder.apply(finalCurrent, inner));
			/* if (optional.isPresent())  */{
				let /* current  */ = optional.orElse(null);
			}
			/* else  */{
				return current;
			}
		}
	}
	/* @Override
        public  */ map<R>(mapper : (T) => R) : Iterator<R> {
		return /* new HeadedIterator<> */(() => this.head.next().map(mapper));
	}
	/* @Override
        public  */ collect<R>(collector : Collector<T, R>) : R {
		return this.fold(collector.createInitial(), /*  collector::fold */);
	}
}
/* private static final */class JVMList<T>/*  implements List<T>  */ {
	/* private final */ elements : java.util.List<T>;
	JVMList(elements : java.util.List<T>) : private {
		let /* this.elements  */ = elements;
	}
	JVMList() : public {
		/* this(new ArrayList<>()) */;
	}
	/* @Override
            public */ add(element : T) : List<T> {
		/* this.elements.add(element) */;
		return this;
	}
	/* @Override
            public */ iterate() : Iterator<T> {
		return /* new HeadedIterator<> */(/* new RangeHead */(this.elements.size())).map(/* this.elements::get */);
	}
	/* @Override
            public */ removeLast() : Option<[List<T>, T]> {
		/* if (this.elements.isEmpty())  */{
			return /* new None<> */();
		}
		let slice : var = this.elements.subList(0, /*  this.elements.size() - 1 */);
		let last : var = this.elements.getLast();
		return /* new Some<> */(/* new Tuple<List<T>, T> */(/* new JVMList<> */(slice), last));
	}
	/* @Override
            public */ get(index : number) : T {
		return this.elements.get(index);
	}
}
/* private static */class Lists/*  */ {
	/* public static  */ empty<T>() : List<T> {
		return /* new JVMList<> */();
	}
	/* public static  */ of<T>(elements : T[]) : List<T> {
		return /* new JVMList<> */(/* new ArrayList<> */(Arrays.asList(elements)));
	}
}
/* private static */class DivideState/*  */ {
	/* private final */ input : string;
	/* private final */ index : number;
	/* private */ depth : number;
	/* private */ segments : List<string>;
	/* private */ buffer : StringBuilder;
	DivideState(input : string, index : number, segments : List<string>, buffer : StringBuilder, depth : number) : public {
		let /* this.segments  */ = segments;
		let /* this.buffer  */ = buffer;
		let /* this.depth  */ = depth;
		let /* this.input  */ = input;
		let /* this.index  */ = index;
	}
	DivideState(input : string) : public {
		/* this(input, 0, Lists.empty(), new StringBuilder(), 0) */;
	}
	/* private */ advance() : DivideState {
		let /* this.segments  */ = this.segments.add(this.buffer.toString());
		let /* this.buffer  */ = /* new StringBuilder */();
		return this;
	}
	/* private */ append(c : char) : DivideState {
		/* this.buffer.append(c) */;
		return this;
	}
	/* public */ enter() : DivideState {
		/* this.depth++ */;
		return this;
	}
	/* public */ isLevel() : boolean {
		return /* this.depth == 0 */;
	}
	/* public */ exit() : DivideState {
		/* this.depth-- */;
		return this;
	}
	/* public */ isShallow() : boolean {
		return /* this.depth == 1 */;
	}
	/* public */ pop() : Option<[Character, DivideState]> {
		/* if (this.index < this.input.length())  */{
			let c : var = this.input.charAt(this.index);
			return /* new Some<>(new Tuple<>(c, new DivideState(this.input, this */.index + /*  1, this.segments, this.buffer, this.depth))) */;
		}
		return /* new None<> */();
	}
	/* public */ popAndAppendToTuple() : Option<[Character, DivideState]> {
		return this.pop().map((tuple) => /* new Tuple<> */(tuple.left, tuple.right.append(tuple.left)));
	}
	/* public */ popAndAppendToOption() : Option<DivideState> {
		return this.popAndAppendToTuple().map(/* Tuple::right */);
	}
	/* public */ peek() : char {
		return this.input.charAt(this.index);
	}
}
/* private static */class ListCollector<T>/*  implements Collector<T, List<T>>  */ {
	/* @Override
        public */ createInitial() : List<T> {
		return Lists.empty();
	}
	/* @Override
        public */ fold(current : List<T>, element : T) : List<T> {
		return current.add(element);
	}
}
/* private */class Tuple<A, B>/* (A left, B right)  */ {
}
/* public */class Main/*  */ {
	/* private static class RangeHead implements Head<Integer> {
        private final int length;
        private int counter;

        */ RangeHead(length : number) : public {
		let /* this.length  */ = length;
		/* }

        @Override
        public Option<Integer> next()  */{
			let /* if (this.counter */ value : /* < this.length) {
                var */ = /* this.counter;
                this */.counter +  + /* ;
                return new Some<> */(value);
		}
		return /* new None<> */();/* } */
	}
	/* private */ CompileState(structures : List<string>) : record {
		/* public CompileState()  */{
			/* this(Lists.empty()) */;
		}
		/* public CompileState addStructure(String structure)  */{
			return /* new CompileState */(this.structures.add(structure));
		}
	}/* 

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
	/* private */ Definition(maybeBefore : Option<string>, type : string, name : string, typeParams : List<string>) : record {
		/* private String generate()  */{
			return this.generateWithParams("");
		}
		/* public String generateWithParams(String params)  */{
			let joined : var = /*  this.typeParams.iterate()
                    .collect(new Joiner())
                    .map(inner -> "<"  */ + inner + /*  ">")
                    .orElse("") */;
			let before : var = /*  this.maybeBefore
                    .filter(value -> !value.isEmpty())
                    .map(Main::generatePlaceholder)
                    .map(inner -> inner  */ + /*  " ")
                    .orElse("") */;
			return before + this.name + joined + params + " : " + this.type;
		}
	}
	/* public static */ main() : void {
		/* try  */{
			let parent : var = Paths.get(".", "src", "java", "magma");
			let source : var = parent.resolve("Main.java");
			let target : var = parent.resolve("main.ts");
			let input : var = Files.readString(source);
			/* Files.writeString(target, compile(input)) */;
			/* new ProcessBuilder("cmd", "/c", "npm", "exec", "tsc")
                    .inheritIO()
                    .start()
                    .waitFor() */;
		}
		/* catch (IOException | InterruptedException e)  */{
			/* throw new RuntimeException(e) */;
		}
	}
	/* private static */ compile(input : string) : string {
		let tuple : var = compileStatements(/* new CompileState */(), input, /*  Main::compileRootSegment */);
		let joined : var = tuple.left.structures.iterate().collect(/* new Joiner */()).orElse("");
		return joined + tuple.right;
	}
	/* private static */ compileStatements(state : CompileState, input : string, mapper : (CompileState, string) => [CompileState, string]) : [CompileState, string] {
		return compileAll(state, input, /*  Main::foldStatementChar */, mapper, /*  Main::mergeStatements */);
	}
	/* private static */ compileAll(state : CompileState, input : string, folder : (DivideState, Character) => DivideState, mapper : (CompileState, string) => [CompileState, string], merger : (StringBuilder, string) => StringBuilder) : [CompileState, string] {
		let parsed : var = parseAll(state, input, folder, mapper);
		let generated : var = generateAll(merger, parsed.right);
		return /* new Tuple<> */(parsed.left, generated);
	}
	/* private static */ generateAll(merger : (StringBuilder, string) => StringBuilder, elements : List<string>) : string {
		return elements.iterate().fold(/* new StringBuilder */(), merger).toString();
	}
	/* private static */ parseAll(state : CompileState, input : string, folder : (DivideState, Character) => DivideState, mapper : (CompileState, string) => [CompileState, string]) : [CompileState, List<string>] {
		return divideAll(input, folder).iterate().fold(/* new Tuple<> */(state, Lists.empty()), (tuple,  element) => /*  {
            var state1 = tuple.left;
            var right = tuple.right;

            var applied = mapper.apply(state1, element);
            return new Tuple<>(applied.left, right.add(applied.right));
        } */);
	}
	/* private static */ mergeStatements(stringBuilder : StringBuilder, str : string) : StringBuilder {
		return stringBuilder.append(str);
	}
	/* private static */ divideAll(input : string, folder : (DivideState, Character) => DivideState) : List<string> {
		let current : var = /* new DivideState */(input);
		/* while (true)  */{
			let maybePopped : var = current.pop().map((tuple) => /*  {
                return foldSingleQuotes(tuple)
                        .or(() -> foldDoubleQuotes(tuple))
                        .orElseGet(() -> folder.apply(tuple.right, tuple.left));
            } */);
			/* if (maybePopped.isPresent())  */{
				let /* current  */ = maybePopped.orElse(current);
			}
			/* else  */{
				/* break */;
			}
		}
		return current.advance().segments;
	}
	/* private static */ foldDoubleQuotes(tuple : [Character, DivideState]) : Option<DivideState> {
		/* if (tuple.left == '\"')  */{
			let current : var = tuple.right.append(tuple.left);
			/* while (true)  */{
				let maybePopped : var = current.popAndAppendToTuple();
				/* if (maybePopped.isEmpty())  */{
					/* break */;
				}
				let popped : var = maybePopped.orElse(null);
				let /* current  */ = popped.right;
				/* if (popped.left == '\\')  */{
					let /* current  */ = current.popAndAppendToOption().orElse(current);
				}
				/* if (popped.left == '\"')  */{
					/* break */;
				}
			}
			return /* new Some<> */(current);
		}
		return /* new None<> */();
	}
	/* private static */ foldSingleQuotes(tuple : [Character, DivideState]) : Option<DivideState> {
		/* if (tuple.left != '\'')  */{
			return /* new None<> */();
		}
		let appended : var = tuple.right.append(tuple.left);
		return appended.popAndAppendToTuple().map(/* Main::foldEscaped */).flatMap(/* DivideState::popAndAppendToOption */);
	}
	/* private static */ foldEscaped(escaped : [Character, DivideState]) : DivideState {
		/* if (escaped.left == '\\')  */{
			return escaped.right.popAndAppendToOption().orElse(escaped.right);
		}
		return escaped.right;
	}
	/* private static */ foldStatementChar(state : DivideState, c : char) : DivideState {
		let append : var = state.append(c);
		/* if (c == ';' && append.isLevel())  */{
			return append.advance();
		}
		/* if (c == '}' && append.isShallow())  */{
			return append.advance().exit();
		}
		/* if (c == ' */{
			let c : /* ' || */ = /* = '(') {
            return append.enter() */;
		}
		/* if (c == '}' || c == ')')  */{
			return append.exit();
		}
		return append;
	}
	/* private static */ compileRootSegment(state : CompileState, input : string) : [CompileState, string] {
		let stripped : var = input.strip();
		/* if (stripped.startsWith("package ") || stripped.startsWith("import "))  */{
			return /* new Tuple<> */(state, "");
		}
		return compileClass(stripped, 0, state).orElseGet(() => /* new Tuple<> */(state, generatePlaceholder(stripped)));
	}
	/* private static */ compileClass(stripped : string, depth : number, state : CompileState) : Option<[CompileState, string]> {
		return structure(stripped, "class ", "class ", state);
	}
	/* private static */ structure(stripped : string, sourceInfix : string, targetInfix : string, state : CompileState) : Option<[CompileState, string]> {
		return first(stripped, sourceInfix, (beforeInfix,  right) => /*  {
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
        } */);
	}
	/* private static */ assemble(state : CompileState, targetInfix : string, beforeInfix : string, rawName : string, content : string, typeParams : List<string>, afterTypeParams : string) : Option<[CompileState, string]> {
		let name : var = rawName.strip();
		/* if (!isSymbol(name))  */{
			return /* new None<> */();
		}
		let joinedTypeParams : var = /*  typeParams.iterate().collect(new Joiner(", ")).map(inner -> "<"  */ + inner + /*  ">").orElse("") */;
		let statements : var = compileStatements(state, content, (state0,  input) => compileClassSegment(/* state0 */, input, 1));
		let generated : var = generatePlaceholder(beforeInfix.strip()) + targetInfix + name + joinedTypeParams + generatePlaceholder(afterTypeParams) + " {" + statements.right + "\n}\n";
		return /* new Some<> */(/* new Tuple<> */(statements.left.addStructure(generated), ""));
	}
	/* private static */ isSymbol(input : string) : boolean {
		/* for (var i = 0; i < input.length(); i++)  */{
			let c : var = input.charAt(i);
			/* if (Character.isLetter(c))  */{
				/* continue */;
			}
			return false;
		}
		return true;
	}
	/* private static  */ suffix<T>(input : string, suffix : string, mapper : (string) => Option<T>) : Option<T> {
		/* if (!input.endsWith(suffix))  */{
			return /* new None<> */();
		}
		let slice : var = input.substring(0, /* input.length() - suffix */.length());
		return mapper.apply(slice);
	}
	/* private static */ compileClassSegment(state : CompileState, input : string, depth : number) : [CompileState, string] {
		return compileWhitespace(input, state).or(() => compileClass(input, depth, state)).or(() => structure(input, "interface ", "interface ", state)).or(() => structure(input, "record ", "class ", state)).or(() => method(state, input, depth)).or(() => compileDefinitionStatement(input, depth, state)).orElseGet(() => /* new Tuple<> */(state, generatePlaceholder(input)));
	}
	/* private static */ compileWhitespace(input : string, state : CompileState) : Option<[CompileState, string]> {
		/* if (input.isBlank())  */{
			return /* new Some<> */(/* new Tuple<> */(state, ""));
		}
		return /* new None<> */();
	}
	/* private static */ method(state : CompileState, input : string, depth : number) : Option<[CompileState, string]> {
		return /* first(input, "(", (definition, withParams) -> {
            return first(withParams, ")", (params, rawContent) -> {
                var definitionTuple = parseDefinition(state, definition)
                        .map(definition1 -> {
                            var paramsTuple = compileValues(state, params, Main::compileParameter);
                            var generated = definition1.right.generateWithParams("("  */ + paramsTuple.right + /*  ")");
                            return new Tuple<>(paramsTuple.left, generated);
                        })
                        .orElseGet(() -> new Tuple<>(state, generatePlaceholder(definition)));

                var content = rawContent.strip();
                var indent = createIndent(depth);
                if (content.equals(";")) {
                    var s = indent  */ + definitionTuple.right + /*  ";";
                    return new Some<>(new Tuple<>(definitionTuple.left, s));
                }

                if (content.startsWith("{") && content.endsWith("}")) {
                    var substring = content.substring(1, content.length() - 1);
                    var statementsTuple = compileFunctionSegments(definitionTuple.left, substring, depth);
                    var generated = indent  */ + definitionTuple.right + " {" + statementsTuple.right + indent + /* "}";
                    return new Some<>(new Tuple<>(statementsTuple.left, generated));
                }

                return new None<> */(/* );
            });
        } */);
	}
	/* private static */ compileFunctionSegments(state : CompileState, input : string, depth : number) : [CompileState, string] {
		return /* compileStatements(state, input, (state1, input1) -> compileFunctionSegment(state1, input1, depth  */ + /*  1)) */;
	}
	/* private static */ compileFunctionSegment(state : CompileState, input : string, depth : number) : [CompileState, string] {
		let stripped : var = input.strip();
		/* if (stripped.isEmpty())  */{
			return /* new Tuple<> */(state, "");
		}
		return suffix(stripped, ";", (s) => /*  {
            var tuple = statementValue(state, s);
            return new Some<>(new Tuple<>(tuple.left, createIndent(depth */) + tuple.right + /*  ";"));
        }).or(() -> {
            return suffix(stripped, "}", withoutEnd -> {
                return first(withoutEnd, "{", (beforeContent, content) -> {
                    var compiled = compileFunctionSegments(state, content, depth);
                    var indent = createIndent(depth);
                    return new Some<>(new Tuple<>(compiled.left, indent  */ + generatePlaceholder(beforeContent) + "{" + compiled.right + indent + /*  "}"));
                });
            });
        }).orElseGet(() -> {
            return new Tuple<>(state, generatePlaceholder(stripped));
        }) */;
	}
	/* private static */ statementValue(state : CompileState, input : string) : [CompileState, string] {
		let stripped : var = input.strip();
		/* if (stripped.startsWith("return "))  */{
			let value : var = stripped.substring("return ".length());
			let tuple : var = value(state, value);
			return /* new Tuple<>(tuple.left, "return "  */ + /*  tuple.right) */;
		}
		return /* first(stripped, "=", (s, s2) -> {
            var definitionTuple = compileDefinition(state, s);
            var valueTuple = value(definitionTuple.left, s2);
            return new Some<>(new Tuple<>(valueTuple.left, "let "  */ + definitionTuple.right + " = " + /*  valueTuple.right));
        }).orElseGet(() -> {
            return new Tuple<>(state, generatePlaceholder(stripped));
        }) */;
	}
	/* private static */ value(state : CompileState, input : string) : [CompileState, string] {
		return lambda(state, input).or(() => stringValue(state, input)).or(() => dataAccess(state, input)).or(() => symbolValue(state, input)).or(() => operation(state, input)).or(() => invocation(state, input)).or(() => digits(state, input)).orElseGet(() => /* new Tuple<CompileState, String> */(state, generatePlaceholder(input)));
	}
	/* private static */ lambda(state : CompileState, input : string) : Option<[CompileState, string]> {
		return first(input, "->", (beforeArrow,  valueString) => /*  {
            var strippedBeforeArrow = beforeArrow.strip();
            if (isSymbol(strippedBeforeArrow)) {
                return assembleLambda(state, Lists.of(strippedBeforeArrow), valueString);
            }

            if (strippedBeforeArrow.startsWith("(") && strippedBeforeArrow.endsWith(")")) {
                var parameterNames = divideAll(strippedBeforeArrow.substring(1, strippedBeforeArrow.length() - 1), Main::foldValueChar);
                return assembleLambda(state, parameterNames, valueString);
            }

            return new None<>();
        } */);
	}
	/* private static */ assembleLambda(state : CompileState, paramNames : List<string>, valueString : string) : Some<[CompileState, string]> {
		let value : var = value(state, valueString);
		let joined : var = paramNames.iterate().collect(/* new Joiner */(", ")).orElse("");
		return /* new Some<>(new Tuple<>(value.left, "("  */ + joined + ")" + " => " + /*  value.right)) */;
	}
	/* private static */ digits(state : CompileState, input : string) : Option<[CompileState, string]> {
		let stripped : var = input.strip();
		/* if (isNumber(stripped))  */{
			return /* new Some<> */(/* new Tuple<> */(state, stripped));
		}
		return /* new None<> */();
	}
	/* private static */ isNumber(input : string) : boolean {
		/* for (var i = 0; i < input.length(); i++)  */{
			let c : var = input.charAt(i);
			/* if (Character.isDigit(c))  */{
				/* continue */;
			}
			return false;
		}
		return true;
	}
	/* private static */ invocation(state : CompileState, input : string) : Option<[CompileState, string]> {
		return /* suffix(input.strip(), ")", withoutEnd -> {
            return split(() -> toLast(withoutEnd, "", Main::foldInvocationStart), (callerWithEnd, argumentsString) -> {
                return suffix(callerWithEnd, "(", callerString -> {
                    var callerTuple = value(state, callerString);
                    var argumentsTuple = compileValues(callerTuple.left, argumentsString, Main::value);

                    return new Some<>(new Tuple<>(argumentsTuple.left, callerTuple */.right + "(" + argumentsTuple.right + /*  ")"));
                });
            });
        }) */;
	}
	/* private static */ foldInvocationStart(state : DivideState, c : char) : DivideState {
		let appended : var = state.append(c);
		/* if (c == '(')  */{
			let enter : var = appended.enter();
			/* if (enter.isShallow())  */{
				return enter.advance();
			}
			return enter;
		}
		/* if (c == ')')  */{
			return appended.exit();
		}
		return appended;
	}
	/* private static */ dataAccess(state : CompileState, input : string) : Option<[CompileState, string]> {
		return /* last(input.strip(), ".", (parent, property) -> {
            var value = value(state, parent);
            if (!isSymbol(property)) {
                return new None<>();
            }
            return new Some<>(new Tuple<>(value.left, value */.right + "." + /*  property));
        }) */;
	}
	/* private static */ stringValue(state : CompileState, input : string) : Option<[CompileState, string]> {
		let stripped : var = input.strip();
		/* if (stripped.startsWith("\"") && stripped.endsWith("\""))  */{
			return /* new Some<> */(/* new Tuple<> */(state, stripped));
		}
		return /* new None<> */();
	}
	/* private static */ symbolValue(state : CompileState, value : string) : Option<[CompileState, string]> {
		let stripped : var = value.strip();
		/* if (isSymbol(stripped))  */{
			return /* new Some<> */(/* new Tuple<> */(state, stripped));
		}
		return /* new None<> */();
	}
	/* private static */ operation(state : CompileState, value : string) : Option<[CompileState, string]> {
		return /* first(value, " */ + /* ", (s, s2) -> {
            var leftTuple = value(state, s);
            var rightTuple = value(leftTuple.left, s2);
            return new Some<>(new Tuple<>(rightTuple.left, leftTuple */.right + " + " + /*  rightTuple.right));
        }) */;
	}
	/* private static */ compileValues(state : CompileState, params : string, mapper : (CompileState, string) => [CompileState, string]) : [CompileState, string] {
		let parsed : var = parseValues(state, params, mapper);
		let generated : var = generateValues(parsed.right);
		return /* new Tuple<> */(parsed.left, generated);
	}
	/* private static */ generateValues(elements : List<string>) : string {
		return generateAll(/* Main::mergeValues */, elements);
	}
	/* private static */ parseValues(state : CompileState, input : string, mapper : (CompileState, string) => [CompileState, string]) : [CompileState, List<string>] {
		return parseAll(state, input, /*  Main::foldValueChar */, mapper);
	}
	/* private static */ compileParameter(state : CompileState, input : string) : [CompileState, string] {
		/* if (input.isBlank())  */{
			return /* new Tuple<> */(state, "");
		}
		return compileDefinition(state, input);
	}
	/* private static */ compileDefinition(state : CompileState, input : string) : [CompileState, string] {
		return parseDefinition(state, input).map((Tuple<CompileState, Definition> tuple) => /* new Tuple<> */(tuple.left, tuple.right.generate())).orElseGet(() => /* new Tuple<> */(state, generatePlaceholder(input)));
	}
	/* private static */ mergeValues(cache : StringBuilder, element : string) : StringBuilder {
		/* if (cache.isEmpty())  */{
			return cache.append(element);
		}
		return cache.append(", ").append(element);
	}
	/* private static */ createIndent(depth : number) : string {
		return "\n" + "\t".repeat(depth);
	}
	/* private static */ compileDefinitionStatement(input : string, depth : number, state : CompileState) : Option<[CompileState, string]> {
		return suffix(input.strip(), ";", (withoutEnd) => /*  {
            return parseDefinition(state, withoutEnd).map(result -> {
                var generated = createIndent(depth */) + result.right.generate() + /* ";";
                return new Tuple<> */(result.left, /*  generated);
            });
        } */);
	}
	/* private static */ parseDefinition(state : CompileState, input : string) : Option<[CompileState, Definition]> {
		return last(input.strip(), " ", (beforeName,  name) => /*  {
            return split(() -> toLast(beforeName, " ", Main::foldTypeSeparator), (beforeType, type) -> {
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
        } */);
	}
	/* private static */ toLast(input : string, separator : string, folder : (DivideState, Character) => DivideState) : Option<[string, string]> {
		let divisions : var = divideAll(input, folder);
		return divisions.removeLast().map((removed) => /*  {
            var left = removed.left.iterate().collect(new Joiner(separator)).orElse("");
            var right = removed.right;

            return new Tuple<>(left, right);
        } */);
	}
	/* private static */ foldTypeSeparator(state : DivideState, c : Character) : DivideState {
		/* if (c == ' ' && state.isLevel())  */{
			return state.advance();
		}
		let appended : var = state.append(c);
		/* if (c == '<')  */{
			return appended.enter();
		}
		/* if (c == '>')  */{
			return appended.exit();
		}
		return appended;
	}
	/* private static */ assembleDefinition(state : CompileState, beforeTypeParams : Option<string>, name : string, typeParams : List<string>, type : string) : Option<[CompileState, Definition]> {
		let type1 : var = type(state, type);
		let node : var = /* new Definition */(beforeTypeParams, /* type1 */.right, name.strip(), typeParams);
		return /* new Some<> */(/* new Tuple<> */(/* type1 */.left, node));
	}
	/* private static */ foldValueChar(state : DivideState, c : char) : DivideState {
		/* if (c == ',' && state.isLevel())  */{
			return state.advance();
		}
		let appended : var = state.append(c);
		/* if (c == '-')  */{
			let peeked : var = appended.peek();
			/* if (peeked == '>')  */{
				return appended.popAndAppendToOption().orElse(appended);
			}
			/* else  */{
				return appended;
			}
		}
		/* if (c == '<' || c == '(' || c == ' */{
			/* ') {
            return appended.enter() */;
		}
		/* if (c == '>' || c == ')' || c == '}')  */{
			return appended.exit();
		}
		return appended;
	}
	/* private static */ type(state : CompileState, input : string) : [CompileState, string] {
		let stripped : var = input.strip();
		/* if (stripped.equals("int"))  */{
			return /* new Tuple<> */(state, "number");
		}
		/* if (stripped.equals("String"))  */{
			return /* new Tuple<> */(state, "string");
		}
		/* if (isSymbol(stripped))  */{
			return /* new Tuple<> */(state, stripped);
		}
		return template(state, input).or(() => varArgs(state, input)).orElseGet(() => /* new Tuple<> */(state, generatePlaceholder(stripped)));
	}
	/* private static */ varArgs(state : CompileState, input : string) : Option<[CompileState, string]> {
		return /* suffix(input, "...", s -> {
            var inner = type(state, s);
            return new Some<>(new Tuple<>(inner.left, inner */.right + /*  "[]"));
        }) */;
	}
	/* private static */ template(state : CompileState, input : string) : Option<[CompileState, string]> {
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
                    return new Some<>(new Tuple<>(argumentsState, "["  */ + arguments.get(0) + ", " + arguments.get(1) + /*  "]"));
                }

                return new Some<>(new Tuple<>(argumentsState, strippedBase  */ + "<" + generateValues(arguments) + /*  ">"));
            });
        }) */;
	}
	/* private static */ generate(arguments : List<string>, returns : string) : string {
		let joined : var = arguments.iterate().collect(/* new Joiner */(", ")).orElse("");
		return "(" + joined + ") => " + returns;
	}
	/* private static  */ last<T>(input : string, infix : string, mapper : (string, string) => Option<T>) : Option<T> {
		return infix(input, infix, /*  Main::findLast */, mapper);
	}
	/* private static */ findLast(input : string, infix : string) : Option<Integer> {
		let index : var = input.lastIndexOf(infix);
		/* if (index == -1)  */{
			return /* new None<Integer> */();
		}
		return /* new Some<> */(index);
	}
	/* private static  */ first<T>(input : string, infix : string, mapper : (string, string) => Option<T>) : Option<T> {
		return infix(input, infix, /*  Main::findFirst */, mapper);
	}
	/* private static  */ infix<T>(input : string, infix : string, locator : (string, string) => Option<Integer>, mapper : (string, string) => Option<T>) : Option<T> {
		return /* split(() -> locator.apply(input, infix).map(index -> {
            var left = input.substring(0, index);
            var right = input.substring(index  */ + infix.length(/* ));
            return new Tuple<>(left, right);
        }), mapper */);
	}
	/* private static  */ split<T>(splitter : () => Option<[string, string]>, mapper : (string, string) => Option<T>) : Option<T> {
		return splitter.get().flatMap((tuple) => mapper.apply(tuple.left, tuple.right));
	}
	/* private static */ findFirst(input : string, infix : string) : Option<Integer> {
		let index : var = input.indexOf(infix);
		/* if (index == -1)  */{
			return /* new None<Integer> */();
		}
		return /* new Some<> */(index);
	}
	/* private static */ generatePlaceholder(input : string) : string {
		let replaced : var = input.replace("/*", "content-start").replace("*/", "content-end");
		return "/* " + replaced + " */";
	}
}
/*  */