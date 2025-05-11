"use strict";
/* private */ class Some {
    constructor(value) {
    }
    /* @Override
        public  */ map(mapper) {
        return new Some(mapper(this.value));
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
        /* if (predicate.test(this.value))  */ {
            return this;
        }
        return new None();
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
        return mapper(this.value);
    }
    /* @Override
        public */ isEmpty() {
        return false;
    }
}
/* private static */ class None {
    /* @Override
        public  */ map(mapper) {
        return new None();
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
        return new None();
    }
    /* @Override
        public */ orElseGet(supplier) {
        return supplier();
    }
    /* @Override
        public */ or(other) {
        return other();
    }
    /* @Override
        public  */ flatMap(mapper) {
        return new None();
    }
    /* @Override
        public */ isEmpty() {
        return true;
    }
}
/* private static */ class SingleHead {
    SingleHead(value) {
        let /* this.value  */ = value;
        let /* this.retrieved  */ = false;
    }
    /* @Override
        public */ next() {
        /* if (this.retrieved)  */ {
            return new None();
        }
        let /* this.retrieved  */ = true;
        return new Some(this.value);
    }
}
/* private static */ class EmptyHead {
    /* @Override
        public */ next() {
        return new None();
    }
}
/* private */ class HeadedIterator {
    constructor(head) {
    }
    /* @Override
        public  */ fold(initial, folder) {
        let current = initial;
        /* while (true)  */ {
            let finalCurrent = current;
            let optional = this.head.next().map((inner) => folder(finalCurrent, inner));
            /* if (optional.isPresent())  */ {
                let /* current  */ = optional.orElse(null);
            }
            /* else  */ {
                return current;
            }
        }
    }
    /* @Override
        public  */ map(mapper) {
        return new HeadedIterator(() => this.head.next().map(mapper));
    }
    /* @Override
        public  */ collect(collector) {
        return this.fold(collector.createInitial(), collector.fold);
    }
    /* @Override
        public */ filter(predicate) {
        return this.flatMap((element) => {
            /* if (predicate.test(element))  */ {
                return new HeadedIterator(new SingleHead(element));
            }
            return new HeadedIterator(new EmptyHead());
        });
    }
    /* @Override
        public */ next() {
        return this.head.next();
    }
    /* @Override
        public  */ flatMap(f) {
        return new HeadedIterator(new FlatMapHead(this.head, f));
    }
}
/* private static */ class RangeHead /*  */ {
    RangeHead(length) {
        let /* this.length  */ = length;
    }
    /* @Override
        public */ next() {
        /* if (this.counter < this.length)  */ {
            let value = this.counter;
            /* this.counter++ */ ;
            return new Some(value);
        }
        return new None();
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
            public */ addLast(element) {
        /* this.elements.add(element) */ ;
        return this;
    }
    /* @Override
            public */ iterate() {
        return this.iterateWithIndices().map(Tuple.right);
    }
    /* @Override
            public */ removeLast() {
        /* if (this.elements.isEmpty())  */ {
            return new None();
        }
        let slice = this.elements.subList(0, this.elements.size() - 1);
        let last = this.elements.getLast();
        return new Some(new [(List), T](new JVMList(slice), last));
    }
    /* @Override
            public */ get(index) {
        return this.elements.get(index);
    }
    /* @Override
            public */ size() {
        return this.elements.size();
    }
    /* @Override
            public */ isEmpty() {
        return this.elements.isEmpty();
    }
    /* @Override
            public */ addFirst(element) {
        /* this.elements.addFirst(element) */ ;
        return this;
    }
    /* @Override
            public */ iterateWithIndices() {
        return new HeadedIterator(new RangeHead(this.elements.size())).map((index) => new Tuple(index, this.elements.get(index)));
    }
    /* @Override
            public */ removeFirst() {
        /* if (this.elements.isEmpty())  */ {
            return new None();
        }
        let first = this.elements.getFirst();
        let slice = this.elements.subList(1, this.elements.size());
        return new Some(new [T, (List)](first, new JVMList(slice)));
    }
}
/* private static */ class Lists /*  */ {
    /* public static  */ empty() {
        return new JVMList();
    }
    /* public static  */ of(elements) {
        return new JVMList(new ArrayList(Arrays.asList(elements)));
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
        let /* this.segments  */ = this.segments.addLast(this.buffer.toString());
        let /* this.buffer  */ = new StringBuilder();
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
        return this.depth == 0;
    }
    /* public */ exit() {
        /* this.depth-- */ ;
        return this;
    }
    /* public */ isShallow() {
        return this.depth == 1;
    }
    /* public */ pop() {
        /* if (this.index < this.input.length())  */ {
            let c = this.input.charAt(this.index);
            return new Some(new Tuple(c, new DivideState(this.input, this.index + 1, this.segments, this.buffer, this.depth)));
        }
        return new None();
    }
    /* public */ popAndAppendToTuple() {
        return this.pop().map((tuple) => new Tuple(tuple.left, tuple.right.append(tuple.left)));
    }
    /* public */ popAndAppendToOption() {
        return this.popAndAppendToTuple().map(Tuple.right);
    }
    /* public */ peek() {
        return this.input.charAt(this.index);
    }
}
/* private */ class Joiner /*  */ {
    constructor(delimiter) {
    }
    Joiner() {
        /* this("") */ ;
    }
    /* @Override
        public */ createInitial() {
        return new None();
    }
    /* @Override
        public */ fold(current, element) {
        return new Some(current.map((inner) => inner + this.delimiter + element).orElse(element));
    }
}
/* private */ class Definition /*  */ {
    constructor(maybeBefore, type, name, typeParams) {
    }
    /* private */ generate() {
        return this.generateWithParams("");
    }
    /* public */ generateWithParams(params) {
        let joined = this.joinTypeParams();
        let before = this.joinBefore();
        let typeString = this.generateType();
        return before + this.name + joined + params + typeString;
    }
    /* private */ generateType() {
        /* if (this.type.equals(Primitive.Var))  */ {
            return "";
        }
        return " : " + this.type.generate();
    }
    /* private */ joinBefore() {
        return this.maybeBefore.filter((value) => !value.isEmpty()).map(Main.generatePlaceholder).map((inner) => inner + " ").orElse("");
    }
    /* private */ joinTypeParams() {
        return this.typeParams.iterate().collect(new Joiner()).map((inner) => "<" + inner + ">").orElse("");
    }
}
/* private static */ class ListCollector {
    /* @Override
        public */ createInitial() {
        return Lists.empty();
    }
    /* @Override
        public */ fold(current, element) {
        return current.addLast(element);
    }
}
/* private */ class Tuple {
}
/* private static */ class FlatMapHead {
    FlatMapHead(head, mapper) {
        let /* this.mapper  */ = mapper;
        let /* this.current  */ = new None();
        let /* this.head  */ = head;
    }
    /* @Override
        public */ next() {
        /* while (true)  */ {
            /* if (this.current.isPresent())  */ {
                let inner = this.current.orElse(null);
                let maybe = inner.next();
                /* if (maybe.isPresent())  */ {
                    return maybe;
                }
                /* else  */ {
                    let /* this.current  */ = new None();
                }
            }
            let outer = this.head.next();
            /* if (outer.isPresent())  */ {
                let /* this.current  */ = outer.map(this.mapper);
            }
            /* else  */ {
                return new None();
            }
        }
    }
}
/* private */ class Symbol /*  */ {
    constructor(input) {
    }
    /* @Override
        public */ generate() {
        return this.input;
    }
}
/* private static */ class ArrayType /*  */ {
    ArrayType(right) {
        let /* this.right  */ = right;
    }
    /* @Override
        public */ generate() {
        return this.right.generate() + "[]";
    }
}
/* private static */ class Whitespace /*  */ {
}
/* private static */ class Iterators /*  */ {
    /* public static  */ fromOption(option) {
        let single = option.map(SingleHead.new);
        return new HeadedIterator(single.orElseGet(EmptyHead.new));
    }
}
/* private */ class FunctionType /*  */ {
    constructor(arguments, returns) {
    }
    /* @Override
        public */ generate() {
        let joined = this.arguments().iterateWithIndices().map((pair) => "arg" + pair.left + " : " + pair.right.generate()).collect(new Joiner(", ")).orElse("");
        return "(" + joined + ") => " + this.returns.generate();
    }
}
/* private */ class TupleType /*  */ {
    constructor(arguments) {
    }
    /* @Override
        public */ generate() {
        let joinedArguments = this.arguments.iterate().map(Type.generate).collect(new Joiner(", ")).orElse("");
        return "[" + joinedArguments + "]";
    }
}
/* private */ class Template /*  */ {
    constructor(base, arguments) {
    }
    /* @Override
        public */ generate() {
        let joinedArguments = this.arguments.iterate().map(Type.generate).collect(new Joiner(", ")).map((inner) => "<" + inner + ">").orElse("");
        return this.base + joinedArguments;
    }
}
/* private */ class Placeholder /*  */ {
    constructor(input) {
    }
    /* @Override
        public */ generate() {
        return this.input;
    }
}
/* private */ class StringValue /*  */ {
    constructor(stripped) {
    }
    /* @Override
        public */ generate() {
        return this.stripped;
    }
}
/* private */ class DataAccess /*  */ {
    constructor(parent, property) {
    }
    /* @Override
        public */ generate() {
        return this.parent.generate() + "." + this.property;
    }
}
/* private */ class SymbolValue /*  */ {
    constructor(value) {
    }
    /* @Override
        public */ generate() {
        return this.value;
    }
}
/* private */ class ConstructionCaller /*  */ {
    constructor(right) {
    }
    /* @Override
        public */ generate() {
        return "new " + this.right.generate();
    }
}
/* private */ class Operation /*  */ {
    constructor(left, infix, right) {
    }
    /* @Override
        public */ generate() {
        return this.left.generate() + " " + this.infix + " " + this.right.generate();
    }
}
/* private */ class Not /*  */ {
    constructor(value) {
    }
    /* @Override
        public */ generate() {
        return "!" + this.value.generate();
    }
}
/* private */ class BlockLambdaValue /*  */ {
    constructor(right, depth) {
    }
    /* @Override
        public */ generate() {
        return "{" + this.right + createIndent(this.depth) + "}";
    }
}
/* private */ class Lambda /*  */ {
    constructor(parameterNames, body) {
    }
    /* @Override
        public */ generate() {
        let joined = this.parameterNames.iterate().map((inner) => inner + " : unknown").collect(new Joiner(", ")).orElse("");
        return "(" + joined + ") => " + this.body.generate();
    }
}
/* private */ class Invokable /*  */ {
    constructor(caller, arguments) {
    }
    /* @Override
        public */ generate() {
        let joined = this.arguments.iterate().map(Value.generate).collect(new Joiner(", ")).orElse("");
        return this.caller.generate() + "(" + joined + ")";
    }
}
/* private */ class IndexValue /*  */ {
    constructor(parent, child) {
    }
    /* @Override
        public */ generate() {
        return this.parent.generate() + "[" + this.child.generate() + "]";
    }
}
/* public */ class Main /*  */ {
    /* private */ CompileState(structures, definitions) {
        /* public CompileState()  */ {
            /* this(Lists.empty(), Lists.empty()) */ ;
        }
        /* private Option<Type> resolve(String name)  */ {
            return this.definitions.iterate().filter((definition) => definition.name.equals(name)).next().map(Definition.type);
        }
        /* public CompileState addStructure(String structure)  */ {
            return new CompileState(this.structures.addLast(structure), this.definitions);
        }
        /* public CompileState withDefinitions(List<Definition> definitions)  */ {
            return new CompileState(this.structures, definitions);
        }
    }
    /* public static */ main() {
        /* try  */ {
            let parent = Paths.get(".", "src", "java", "magma");
            let source = parent.resolve("Main.java");
            let target = parent.resolve("main.ts");
            let input = Files.readString(source);
            /* Files.writeString(target, compile(input)) */ ;
            /* new ProcessBuilder("cmd", "/c", "npm", "exec", "tsc")
                    .inheritIO()
                    .start()
                    .waitFor() */ ;
        }
        /* catch (IOException | InterruptedException e)  */ {
            /* throw new RuntimeException(e) */ ;
        }
    }
    /* private static */ compile(input) {
        let tuple = compileStatements(new CompileState(), input, Main.compileRootSegment);
        let joined = tuple.left.structures.iterate().collect(new Joiner()).orElse("");
        return joined + tuple.right;
    }
    /* private static */ compileStatements(state, input, mapper) {
        let parsed = parseStatements(state, input, mapper);
        return new Tuple(parsed.left, generateStatements(parsed.right));
    }
    /* private static */ generateStatements(statements) {
        return generateAll(Main.mergeStatements, statements);
    }
    /* private static */ parseStatements(state, input, mapper) {
        return parseAll(state, input, Main.foldStatementChar, mapper);
    }
    /* private static */ generateAll(merger, elements) {
        return elements.iterate().fold(new StringBuilder(), merger).toString();
    }
    /* private static  */ parseAll(state, input, folder, mapper) {
        return getCompileStateListTuple(state, input, folder, (state1, s) => new Some(mapper(state1, s))).orElseGet(() => new Tuple(state, Lists.empty()));
    }
    /* private static  */ getCompileStateListTuple(state, input, folder, mapper) {
        let initial = new Some(new Tuple(state, Lists.empty()));
        return divideAll(input, folder).iterate().fold(initial, (tuple, element) => {
            return tuple.flatMap((inner) => {
                let state1 = inner.left;
                let right = inner.right;
                return mapper(state1, element).map((applied) => {
                    return new Tuple(applied.left, right.addLast(applied.right));
                });
            });
        });
    }
    /* private static */ mergeStatements(stringBuilder, str) {
        return stringBuilder.append(str);
    }
    /* private static */ divideAll(input, folder) {
        let current = new DivideState(input);
        /* while (true)  */ {
            let maybePopped = current.pop().map((tuple) => {
                return foldSingleQuotes(tuple).or(() => foldDoubleQuotes(tuple)).orElseGet(() => folder(tuple.right, tuple.left));
            });
            /* if (maybePopped.isPresent())  */ {
                let /* current  */ = maybePopped.orElse(current);
            }
            /* else  */ {
                /* break */ ;
            }
        }
        return current.advance().segments;
    }
    /* private static */ foldDoubleQuotes(tuple) {
        /* if (tuple.left == '\"')  */ {
            let current = tuple[1].append(tuple[0]);
            /* while (true)  */ {
                let maybePopped = current.popAndAppendToTuple();
                /* if (maybePopped.isEmpty())  */ {
                    /* break */ ;
                }
                let popped = maybePopped.orElse(null);
                let /* current  */ = popped.right;
                /* if (popped.left == '\\')  */ {
                    let /* current  */ = current.popAndAppendToOption().orElse(current);
                }
                /* if (popped.left == '\"')  */ {
                    /* break */ ;
                }
            }
            return new Some(current);
        }
        return new None();
    }
    /* private static */ foldSingleQuotes(tuple) {
        /* if (tuple.left != '\'')  */ {
            return new None();
        }
        let appended = tuple[1].append(tuple[0]);
        return appended.popAndAppendToTuple().map(Main.foldEscaped).flatMap(DivideState.popAndAppendToOption);
    }
    /* private static */ foldEscaped(escaped) {
        /* if (escaped.left == '\\')  */ {
            return escaped[1].popAndAppendToOption().orElse(escaped[1]);
        }
        return escaped[1];
    }
    /* private static */ foldStatementChar(state, c) {
        let append = state.append(c);
        /* if (c == ';' && append.isLevel())  */ {
            return append.advance();
        }
        /* if (c == '}' && append.isShallow())  */ {
            return append.advance().exit();
        }
        /* if (c == '{' || c == '(')  */ {
            return append.enter();
        }
        /* if (c == '}' || c == ')')  */ {
            return append.exit();
        }
        return append;
    }
    /* private static */ compileRootSegment(state, input) {
        let stripped = input.strip();
        /* if (stripped.startsWith("package ") || stripped.startsWith("import "))  */ {
            return new Tuple(state, "");
        }
        return compileClass(stripped, 0, state).orElseGet(() => new Tuple(state, generatePlaceholder(stripped)));
    }
    /* private static */ compileClass(stripped, depth, state) {
        return structure(stripped, "class ", "class ", state);
    }
    /* private static */ structure(stripped, sourceInfix, targetInfix, state) {
        return first(stripped, sourceInfix, (beforeInfix, right) => {
            return first(right, "{", (beforeContent, withEnd) => {
                let strippedWithEnd = withEnd.strip();
                return suffix(strippedWithEnd, "}", (content1) => {
                    return first(beforeContent, " implements ", (s, s2) => {
                        return structureWithMaybeParams(targetInfix, state, beforeInfix, s, content1);
                    }).or(() => {
                        return structureWithMaybeParams(targetInfix, state, beforeInfix, beforeContent, content1);
                    });
                });
            });
        });
    }
    /* private static */ structureWithMaybeParams(targetInfix, state, beforeInfix, beforeContent, content1) {
        return suffix(beforeContent, ")", (s) => {
            return first(s, "(", (s1, s2) => {
                let parsed = parseParameters(state, s2);
                return getOred(targetInfix, parsed.left, beforeInfix, s1, content1, parsed.right);
            });
        }).or(() => {
            return getOred(targetInfix, state, beforeInfix, beforeContent, content1, Lists.empty());
        });
    }
    /* private static */ getOred(targetInfix, state, beforeInfix, beforeContent, content1, params) {
        return first(beforeContent, "<", (name, withTypeParams) => {
            return first(withTypeParams, ">", (typeParamsString, afterTypeParams) => {
                let /* final */ compileStateStringTupleBiFunction = (state1, s) => new Tuple(state1, s.strip());
                let typeParams = parseValuesOrEmpty(state, typeParamsString, (state1, s) => new Some(compileStateStringTupleBiFunction.apply(state1, s)));
                return assemble(typeParams.left, targetInfix, beforeInfix, name, content1, typeParams.right, afterTypeParams, params);
            });
        }).or(() => {
            return assemble(state, targetInfix, beforeInfix, beforeContent, content1, Lists.empty(), "", params);
        });
    }
    /* private static */ assemble(state, targetInfix, beforeInfix, rawName, content, typeParams, afterTypeParams, params) {
        let name = rawName.strip();
        /* if (!isSymbol(name))  */ {
            return new None();
        }
        let joinedTypeParams = typeParams.iterate().collect(new Joiner(", ")).map((inner) => "<" + inner + ">").orElse("");
        let parsed = parseStatements(state, content, (state0, input) => compileClassSegment(state0, input, 1));
        /* List<String> parsed1 */ ;
        /* if (params.isEmpty())  */ {
            let /* parsed1  */ = parsed.right;
        }
        /* else  */ {
            let joined = joinValues(retainDefinitions(params));
            let constructorIndent = createIndent(1);
            let /* parsed1  */ = parsed.right.addFirst(constructorIndent + "constructor (" + joined + ") {" + constructorIndent + "}\n");
        }
        let parsed2 = parsed1.iterate().collect(new Joiner()).orElse("");
        let generated = generatePlaceholder(beforeInfix.strip()) + targetInfix + name + joinedTypeParams + generatePlaceholder(afterTypeParams) + " {" + parsed2 + "\n}\n";
        return new Some(new Tuple(parsed.left.addStructure(generated), ""));
    }
    /* private static */ retainDefinition(parameter) {
        /* if (parameter instanceof Definition definition)  */ {
            return new Some(definition);
        }
        return new None();
    }
    /* private static */ isSymbol(input) {
        /* for (var i = 0; i < input.length(); i++)  */ {
            let c = input.charAt(i);
            /* if (Character.isLetter(c) || (i != 0 && Character.isDigit(c)))  */ {
                /* continue */ ;
            }
            return false;
        }
        return true;
    }
    /* private static  */ suffix(input, suffix, mapper) {
        /* if (!input.endsWith(suffix))  */ {
            return new None();
        }
        let slice = input.substring(0, input.length() - suffix.length());
        return mapper(slice);
    }
    /* private static */ compileClassSegment(state, input, depth) {
        return compileWhitespace(input, state).or(() => compileClass(input, depth, state)).or(() => structure(input, "interface ", "interface ", state)).or(() => structure(input, "record ", "class ", state)).or(() => compileMethod(state, input, depth)).or(() => compileDefinitionStatement(input, depth, state)).orElseGet(() => new Tuple(state, generatePlaceholder(input)));
    }
    /* private static */ compileWhitespace(input, state) {
        /* if (input.isBlank())  */ {
            return new Some(new Tuple(state, ""));
        }
        return new None();
    }
    /* private static */ compileMethod(state, input, depth) {
        return first(input, "(", (definitionString, withParams) => {
            return first(withParams, ")", (parametersString, rawContent) => {
                return parseDefinition(state, definitionString).flatMap((definitionTuple) => {
                    let definitionState = definitionTuple.left;
                    let definition = definitionTuple.right;
                    let parametersTuple = parseParameters(definitionState, parametersString);
                    let parameters = parametersTuple.right;
                    let definitions = retainDefinitions(parameters);
                    let joinedParameters = joinValues(definitions);
                    let content = rawContent.strip();
                    let indent = createIndent(depth);
                    let generatedHeader = definition.generateWithParams("(" + joinedParameters + ")");
                    /* if (content.equals(";"))  */ {
                        return new Some(new Tuple(parametersTuple.left, indent + generatedHeader + ";"));
                    }
                    /* if (content.startsWith("{") && content.endsWith("}"))  */ {
                        let substring = content.substring(1, content.length() - 1);
                        let statementsTuple = compileFunctionSegments(parametersTuple.left.withDefinitions(definitions), substring, depth);
                        let generated = indent + generatedHeader + " {" + statementsTuple.right + indent + "}";
                        return new Some(new Tuple(statementsTuple.left, generated));
                    }
                    return new None();
                });
            });
        });
    }
    /* private static */ joinValues(retainParameters) {
        return retainParameters.iterate().map(Definition.generate).collect(new Joiner(", ")).orElse("");
    }
    /* private static */ retainDefinitions(right) {
        return right.iterate().map(Main.retainDefinition).flatMap(Iterators.fromOption).collect(new ListCollector());
    }
    /* private static */ parseParameters(state, params) {
        return parseValuesOrEmpty(state, params, (state1, s) => new Some(compileParameter(state1, s)));
    }
    /* private static */ compileFunctionSegments(state, input, depth) {
        return compileStatements(state, input, (state1, input1) => compileFunctionSegment(state1, input1, depth + 1));
    }
    /* private static */ compileFunctionSegment(state, input, depth) {
        let stripped = input.strip();
        /* if (stripped.isEmpty())  */ {
            return new Tuple(state, "");
        }
        return suffix(stripped, ";", (s) => {
            let tuple = statementValue(state, s, depth);
            return new Some(new Tuple(tuple.left, createIndent(depth) + tuple.right + ";"));
        }).or(() => {
            return block(state, depth, stripped);
        }).orElseGet(() => {
            return new Tuple(state, generatePlaceholder(stripped));
        });
    }
    /* private static */ block(state, depth, stripped) {
        return suffix(stripped, "}", (withoutEnd) => {
            return split(() => {
                let divisions = divideAll(withoutEnd, Main.foldBlockStart);
                return divisions.removeFirst().map((removed) => {
                    let right = removed.left;
                    let left = removed.right.iterate().collect(new Joiner("")).orElse("");
                    return new Tuple(right, left);
                });
            }, (beforeContent, content) => {
                return suffix(beforeContent, "{", (s) => {
                    let compiled = compileFunctionSegments(state, content, depth);
                    let indent = createIndent(depth);
                    return new Some(new Tuple(compiled.left, indent + generatePlaceholder(s) + "{" + compiled.right + indent + "}"));
                });
            });
        });
    }
    /* private static */ foldBlockStart(state, c) {
        let appended = state.append(c);
        /* if (c == '{' && state.isLevel())  */ {
            return appended.advance();
        }
        /* if (c == '{')  */ {
            return appended.enter();
        }
        /* if (c == '}')  */ {
            return appended.exit();
        }
        return appended;
    }
    /* private static */ statementValue(state, input, depth) {
        let stripped = input.strip();
        /* if (stripped.startsWith("return "))  */ {
            let value = stripped.substring("return ".length());
            let tuple = compileValue(state, value, depth);
            return new Tuple(tuple.left, "return " + tuple.right);
        }
        return first(stripped, "=", (s, s2) => {
            let definitionTuple = compileDefinition(state, s);
            let valueTuple = compileValue(definitionTuple.left, s2, depth);
            return new Some(new Tuple(valueTuple.left, "let " + definitionTuple.right + " = " + valueTuple.right));
        }).orElseGet(() => {
            return new Tuple(state, generatePlaceholder(stripped));
        });
    }
    /* private static */ compileValue(state, input, depth) {
        let tuple = parseValue(state, input, depth);
        return new Tuple(tuple.left, tuple.right.generate());
    }
    /* private static */ parseValue(state, input, depth) {
        return parseLambda(state, input, depth).or(() => parseString(state, input)).or(() => parseDataAccess(state, input, depth)).or(() => parseSymbolValue(state, input)).or(() => parseInvocation(state, input, depth)).or(() => parseOperation(state, input, depth, "+")).or(() => parseOperation(state, input, depth, "-")).or(() => parseDigits(state, input)).or(() => parseNot(state, input, depth)).or(() => parseMethodReference(state, input, depth)).orElseGet(() => new [CompileState, Value](state, new Placeholder(input)));
    }
    /* private static */ parseMethodReference(state, input, depth) {
        return last(input, "::", (s, s2) => {
            let tuple = parseValue(state, s, depth);
            return new Some(new Tuple(tuple.left, new DataAccess(tuple.right, s2)));
        });
    }
    /* private static */ parseNot(state, input, depth) {
        let stripped = input.strip();
        /* if (stripped.startsWith("!"))  */ {
            let slice = stripped.substring(1);
            let tuple = parseValue(state, slice, depth);
            let value = tuple.right;
            return new Some(new Tuple(tuple.left, new Not(value)));
        }
        return new None();
    }
    /* private static */ parseLambda(state, input, depth) {
        return first(input, "->", (beforeArrow, valueString) => {
            let strippedBeforeArrow = beforeArrow.strip();
            /* if (isSymbol(strippedBeforeArrow))  */ {
                return assembleLambda(state, Lists.of(strippedBeforeArrow), valueString, depth);
            }
            /* if (strippedBeforeArrow.startsWith("(") && strippedBeforeArrow.endsWith(")"))  */ {
                let parameterNames = divideAll(strippedBeforeArrow.substring(1, strippedBeforeArrow.length() - 1), Main.foldValueChar).iterate().map(String.strip).filter((value) => !value.isEmpty()).collect(new ListCollector());
                return assembleLambda(state, parameterNames, valueString, depth);
            }
            return new None();
        });
    }
    /* private static */ assembleLambda(state, paramNames, valueString, depth) {
        let strippedValueString = valueString.strip();
        /* Tuple<CompileState, LambdaValue> value */ ;
        /* if (strippedValueString.startsWith("{") && strippedValueString.endsWith("}"))  */ {
            let value1 = compileStatements(state, strippedValueString.substring(1, strippedValueString.length() - 1), (state1, input1) => compileFunctionSegment(state1, input1, depth + 1));
            let right = value1.right;
            let /* value  */ = new Tuple(value1.left, new BlockLambdaValue(right, depth));
        }
        /* else  */ {
            let value1 = parseValue(state, strippedValueString, depth);
            let /* value  */ = new Tuple(value1.left, value1.right);
        }
        let right = value.right;
        return new Some(new Tuple(value.left, new Lambda(paramNames, right)));
    }
    /* private static */ parseDigits(state, input) {
        let stripped = input.strip();
        /* if (isNumber(stripped))  */ {
            return new Some(new [CompileState, Value](state, new SymbolValue(stripped)));
        }
        return new None();
    }
    /* private static */ isNumber(input) {
        /* for (var i = 0; i < input.length(); i++)  */ {
            let c = input.charAt(i);
            /* if (Character.isDigit(c))  */ {
                /* continue */ ;
            }
            return false;
        }
        return true;
    }
    /* private static */ parseInvocation(state, input, depth) {
        return suffix(input.strip(), ")", (withoutEnd) => {
            return split(() => toLast(withoutEnd, "", Main.foldInvocationStart), (callerWithEnd, argumentsString) => {
                return suffix(callerWithEnd, "(", (callerString) => {
                    let callerString1 = callerString.strip();
                    let callerTuple = invocationHeader(state, depth, callerString1);
                    let parsed = parseValues(callerTuple.left, argumentsString, (state3, s) => new Some(parseValue(state3, s, depth))).orElseGet(() => new Tuple(callerTuple.left, Lists.empty()));
                    let oldCaller = callerTuple.right;
                    let arguments = parsed.right;
                    let newCaller = modifyCaller(parsed.left, oldCaller);
                    let invokable = new Invokable(newCaller, arguments);
                    return new Some(new Tuple(parsed.left, invokable));
                });
            });
        });
    }
    /* private static */ modifyCaller(state, oldCaller) {
        /* if (oldCaller instanceof DataAccess access)  */ {
            let type = resolveType(access.parent, state);
            /* if (type instanceof FunctionType)  */ {
                return access.parent;
            }
        }
        return oldCaller;
    }
    /* private static */ resolveType(value, state) {
        /* return switch (value)  */ {
            /* case DataAccess dataAccess -> Primitive.Var */ ;
            /* case Invokable invokable -> Primitive.Var */ ;
            /* case Lambda lambda -> Primitive.Var */ ;
            /* case Not not -> Primitive.Var */ ;
            /* case Operation operation -> Primitive.Var */ ;
            /* case Placeholder placeholder -> Primitive.Var */ ;
            /* case StringValue stringValue -> Primitive.Var */ ;
            /* case SymbolValue symbolValue -> state.resolve(symbolValue.value).orElse(Primitive.Var) */ ;
            /* case IndexValue indexValue -> Primitive.Var */ ;
        }
        /*  */ ;
    }
    /* private static */ invocationHeader(state, depth, callerString1) {
        /* if (callerString1.startsWith("new "))  */ {
            let input1 = callerString1.substring("new ".length());
            let map = parseType(state, input1).map((type) => {
                let right = type.right;
                return new [CompileState, Caller](type.left, new ConstructionCaller(right));
            });
            /* if (map.isPresent())  */ {
                return map.orElse(null);
            }
        }
        let tuple = parseValue(state, callerString1, depth);
        return new Tuple(tuple.left, tuple.right);
    }
    /* private static */ foldInvocationStart(state, c) {
        let appended = state.append(c);
        /* if (c == '(')  */ {
            let enter = appended.enter();
            /* if (enter.isShallow())  */ {
                return enter.advance();
            }
            return enter;
        }
        /* if (c == ')')  */ {
            return appended.exit();
        }
        return appended;
    }
    /* private static */ parseDataAccess(state, input, depth) {
        return last(input.strip(), ".", (parentString, rawProperty) => {
            let property = rawProperty.strip();
            /* if (!isSymbol(property))  */ {
                return new None();
            }
            let tuple = parseValue(state, parentString, depth);
            let parent = tuple.right;
            let type = resolveType(parent, state);
            /* if (type instanceof TupleType)  */ {
                /* if (property.equals("left"))  */ {
                    return new Some(new Tuple(state, new IndexValue(parent, new SymbolValue("0"))));
                }
                /* if (property.equals("right"))  */ {
                    return new Some(new Tuple(state, new IndexValue(parent, new SymbolValue("1"))));
                }
            }
            return new Some(new Tuple(tuple.left, new DataAccess(parent, property)));
        });
    }
    /* private static */ parseString(state, input) {
        let stripped = input.strip();
        /* if (stripped.startsWith("\"") && stripped.endsWith("\""))  */ {
            return new Some(new Tuple(state, new StringValue(stripped)));
        }
        return new None();
    }
    /* private static */ parseSymbolValue(state, value) {
        let stripped = value.strip();
        /* if (isSymbol(stripped))  */ {
            return new Some(new Tuple(state, new SymbolValue(stripped)));
        }
        return new None();
    }
    /* private static */ parseOperation(state, value, depth, infix) {
        return first(value, infix, (s, s2) => {
            let tuple = parseValue(state, s, depth);
            let tuple1 = parseValue(tuple.left, s2, depth);
            let left = tuple.right;
            let right = tuple1.right;
            return new Some(new Tuple(tuple1.left, new Operation(left, infix, right)));
        });
    }
    /* private static */ compileValues(state, params, mapper) {
        let parsed = parseValuesOrEmpty(state, params, (state1, s) => new Some(mapper(state1, s)));
        let generated = generateValues(parsed.right);
        return new Tuple(parsed.left, generated);
    }
    /* private static */ generateValues(elements) {
        return generateAll(Main.mergeValues, elements);
    }
    /* private static  */ parseValuesOrEmpty(state, input, mapper) {
        return parseValues(state, input, mapper).orElseGet(() => new Tuple(state, Lists.empty()));
    }
    /* private static  */ parseValues(state, input, mapper) {
        return getCompileStateListTuple(state, input, Main.foldValueChar, mapper);
    }
    /* private static */ compileParameter(state, input) {
        /* if (input.isBlank())  */ {
            return new Tuple(state, new Whitespace());
        }
        return parseDefinition(state, input).map((tuple) => new [CompileState, Parameter](tuple.left, tuple.right)).orElseGet(() => new Tuple(state, new Placeholder(input)));
    }
    /* private static */ compileDefinition(state, input) {
        return parseDefinition(state, input).map((tuple) => new Tuple(tuple.left, tuple.right.generate())).orElseGet(() => new Tuple(state, generatePlaceholder(input)));
    }
    /* private static */ mergeValues(cache, element) {
        /* if (cache.isEmpty())  */ {
            return cache.append(element);
        }
        return cache.append(", ").append(element);
    }
    /* private static */ createIndent(depth) {
        return "\n" + "\t".repeat(depth);
    }
    /* private static */ compileDefinitionStatement(input, depth, state) {
        return suffix(input.strip(), ";", (withoutEnd) => {
            return parseDefinition(state, withoutEnd).map((result) => {
                let generated = createIndent(depth) + result.right.generate() + ";";
                return new Tuple(result.left, generated);
            });
        });
    }
    /* private static */ parseDefinition(state, input) {
        return last(input.strip(), " ", (beforeName, name) => {
            return split(() => toLast(beforeName, " ", Main.foldTypeSeparator), (beforeType, type) => {
                return suffix(beforeType.strip(), ">", (withoutTypeParamStart) => {
                    return first(withoutTypeParamStart, "<", (beforeTypeParams, typeParamsString) => {
                        let /* final */ compileStateStringTupleBiFunction = (state1, s) => new Tuple(state1, s.strip());
                        let typeParams = parseValuesOrEmpty(state, typeParamsString, (state1, s) => new Some(compileStateStringTupleBiFunction.apply(state1, s)));
                        return assembleDefinition(typeParams.left, new Some(beforeTypeParams), name, typeParams.right, type);
                    });
                }).or(() => {
                    return assembleDefinition(state, new Some(beforeType), name, Lists.empty(), type);
                });
            }).or(() => {
                return assembleDefinition(state, new None(), name, Lists.empty(), beforeName);
            });
        });
    }
    /* private static */ toLast(input, separator, folder) {
        let divisions = divideAll(input, folder);
        return divisions.removeLast().map((removed) => {
            let left = removed.left.iterate().collect(new Joiner(separator)).orElse("");
            let right = removed.right;
            return new Tuple(left, right);
        });
    }
    /* private static */ foldTypeSeparator(state, c) {
        /* if (c == ' ' && state.isLevel())  */ {
            return state.advance();
        }
        let appended = state.append(c);
        /* if (c == '<')  */ {
            return appended.enter();
        }
        /* if (c == '>')  */ {
            return appended.exit();
        }
        return appended;
    }
    /* private static */ assembleDefinition(state, beforeTypeParams, name, typeParams, type) {
        return parseType(state, type).map((type1) => {
            let node = new Definition(beforeTypeParams, type1.right, name.strip(), typeParams);
            return new Tuple(type1.left, node);
        });
    }
    /* private static */ foldValueChar(state, c) {
        /* if (c == ',' && state.isLevel())  */ {
            return state.advance();
        }
        let appended = state.append(c);
        /* if (c == '-')  */ {
            let peeked = appended.peek();
            /* if (peeked == '>')  */ {
                return appended.popAndAppendToOption().orElse(appended);
            }
            /* else  */ {
                return appended;
            }
        }
        /* if (c == '<' || c == '(' || c == '{')  */ {
            return appended.enter();
        }
        /* if (c == '>' || c == ')' || c == '}')  */ {
            return appended.exit();
        }
        return appended;
    }
    /* private static */ compileType(state, input) {
        return parseType(state, input).map((tuple) => new Tuple(tuple.left, tuple.right.generate()));
    }
    /* private static */ parseType(state, input) {
        let stripped = input.strip();
        /* if (stripped.equals("int") || stripped.equals("Integer"))  */ {
            return new Some(new Tuple(state, Primitive.Int));
        }
        /* if (stripped.equals("String"))  */ {
            return new Some(new Tuple(state, Primitive.String));
        }
        /* if (stripped.equals("var"))  */ {
            return new Some(new Tuple(state, Primitive.Var));
        }
        /* if (isSymbol(stripped))  */ {
            return new Some(new Tuple(state, new Symbol(stripped)));
        }
        return template(state, input).or(() => varArgs(state, input));
    }
    /* private static */ varArgs(state, input) {
        return suffix(input, "...", (s) => {
            return parseType(state, s).map((inner) => {
                let newState = inner.left;
                let child = inner.right;
                return new Tuple(newState, new ArrayType(child));
            });
        });
    }
    /* private static */ template(state, input) {
        return suffix(input.strip(), ">", (withoutEnd) => {
            return first(withoutEnd, "<", (base, argumentsString) => {
                let strippedBase = base.strip();
                return parseValues(state, argumentsString, Main.argument).map((argumentsTuple) => {
                    return assembleTemplate(base, strippedBase, argumentsTuple.left, argumentsTuple.right);
                });
            });
        });
    }
    /* private static */ assembleTemplate(base, strippedBase, state, arguments) {
        let children = arguments.iterate().map(Main.retainType).flatMap(Iterators.fromOption).collect(new ListCollector());
        /* if (base.equals("BiFunction"))  */ {
            return new Tuple(state, new FunctionType(Lists.of(children.get(0), children.get(1)), children.get(2)));
        }
        /* if (base.equals("Function"))  */ {
            return new Tuple(state, new FunctionType(Lists.of(children.get(0)), children.get(1)));
        }
        /* if (base.equals("Predicate"))  */ {
            return new Tuple(state, new FunctionType(Lists.of(children.get(0)), Primitive.Boolean));
        }
        /* if (base.equals("Supplier"))  */ {
            return new Tuple(state, new FunctionType(Lists.empty(), children.get(0)));
        }
        /* if (base.equals("Tuple") && children.size() >= 2)  */ {
            return new Tuple(state, new TupleType(children));
        }
        return new Tuple(state, new Template(strippedBase, children));
    }
    /* private static */ retainType(argument) {
        /* if (argument instanceof Type type)  */ {
            return new Some(type);
        }
        /* else  */ {
            return new None();
        }
    }
    /* private static */ argument(state, input) {
        /* if (input.isBlank())  */ {
            return new Some(new Tuple(state, new Whitespace()));
        }
        return parseType(state, input).map((tuple) => new Tuple(tuple.left, tuple.right));
    }
    /* private static  */ last(input, infix, mapper) {
        return infix(input, infix, Main.findLast, mapper);
    }
    /* private static */ findLast(input, infix) {
        let index = input.lastIndexOf(infix);
        /* if (index == -1)  */ {
            return new None();
        }
        return new Some(index);
    }
    /* private static  */ first(input, infix, mapper) {
        return infix(input, infix, Main.findFirst, mapper);
    }
    /* private static  */ infix(input, infix, locator, mapper) {
        return split(() => locator(input, infix).map((index) => {
            let left = input.substring(0, index);
            let right = input.substring(index + infix.length());
            return new Tuple(left, right);
        }), mapper);
    }
    /* private static  */ split(splitter, mapper) {
        return splitter().flatMap((tuple) => mapper(tuple.left, tuple.right));
    }
    /* private static */ findFirst(input, infix) {
        let index = input.indexOf(infix);
        /* if (index == -1)  */ {
            return new None();
        }
        return new Some(index);
    }
    /* private static */ generatePlaceholder(input) {
        let replaced = input.replace("/*", "content-start").replace("*/", "content-end");
        return "/* " + replaced + " */";
    } /*

    private enum Primitive implements Type {
        Int("number"),
        String("string"),
        Boolean("boolean"),
        Var("var");

        private final String value;

        Primitive(String value) {
            this.value = value;
        }

        @Override
        public String generate() {
            return this.value;
        }
    } */
}
/*  */ 
