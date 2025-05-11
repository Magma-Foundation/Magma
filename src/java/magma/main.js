"use strict";
/* private */ class Some {
    /* @Override
        public  */ map(mapper) {
        return new Some(mapper.apply(this.value));
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
        return mapper.apply(this.value);
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
        return supplier.get();
    }
    /* @Override
        public */ or(other) {
        return other.get();
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
/* private */ class HeadedIterator {
    /* @Override
        public  */ fold(initial, folder) {
        let current = initial;
        /* while (true) {{
                R finalCurrent = current;
                var optional = this.head.next().map(inner -> folder.apply(finalCurrent, inner));
                if (optional.isPresent()) {{
                    current = optional.orElse(null);
                }
                else  */ {
            return current; /* } */
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
            public */ add(element) {
        /* this.elements.add(element) */ ;
        return this;
    }
    /* @Override
            public */ iterate() {
        return new HeadedIterator(new RangeHead(this.elements.size())).map(this.elements.get);
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
        let /* this.segments  */ = this.segments.add(this.buffer.toString());
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
/* private static */ class ListCollector {
    /* @Override
        public */ createInitial() {
        return Lists.empty();
    }
    /* @Override
        public */ fold(current, element) {
        return current.add(element);
    }
}
/* private */ class Tuple {
}
/* public */ class Main /*  */ {
    /* private */ CompileState(structures) {
        /* public CompileState()  */ {
            /* this(Lists.empty()) */ ;
        }
        /* public CompileState addStructure(String structure)  */ {
            return new CompileState(this.structures.add(structure));
        }
    }
    /* private */ Definition(maybeBefore, type, name, typeParams) {
        /* private String generate()  */ {
            return this.generateWithParams("");
        }
        /* public String generateWithParams(String params)  */ {
            let joined = this.typeParams.iterate().collect(new Joiner()).map((inner) => "<" + inner + ">").orElse("");
            let before = this.maybeBefore.filter((value) => !value.isEmpty()).map(Main.generatePlaceholder).map((inner) => inner + " ").orElse("");
            return before + this.name + joined + params + " : " + this.type;
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
        return compileAll(state, input, Main.foldStatementChar, mapper, Main.mergeStatements);
    }
    /* private static */ compileAll(state, input, folder, mapper, merger) {
        let parsed = parseAll(state, input, folder, mapper);
        let generated = generateAll(merger, parsed.right);
        return new Tuple(parsed.left, generated);
    }
    /* private static */ generateAll(merger, elements) {
        return elements.iterate().fold(new StringBuilder(), merger).toString();
    }
    /* private static */ parseAll(state, input, folder, mapper) {
        return divideAll(input, folder).iterate().fold(new Tuple(state, Lists.empty()), (tuple, element) => {
            let state1 = tuple.left;
            let right = tuple.right;
            let applied = mapper.apply(state1, element);
            return new Tuple(applied.left, right.add(applied.right));
        });
    }
    /* private static */ mergeStatements(stringBuilder, str) {
        return stringBuilder.append(str);
    }
    /* private static */ divideAll(input, folder) {
        let current = new DivideState(input);
        /* while (true) {{
            var maybePopped = current.pop().map(tuple -> {{
                return foldSingleQuotes(tuple)
                        .or(() -> foldDoubleQuotes(tuple))
                        .orElseGet(() -> folder.apply(tuple.right, tuple.left));
            });

            if (maybePopped.isPresent()) {{
                current = maybePopped.orElse(current);
            }
            else  */ {
            /* break */ ; /* } */
        }
        return current.advance().segments;
    }
    /* private static */ foldDoubleQuotes(tuple) {
        /* if (tuple.left == '\"') {{
            var current = tuple.right.append(tuple.left);
            while (true) {{
                var maybePopped = current.popAndAppendToTuple();
                if (maybePopped.isEmpty()) {{
                    break;
                }

                var popped = maybePopped.orElse(null);
                current = popped.right;

                if (popped.left == '\\') {{
                    current = current.popAndAppendToOption().orElse(current);
                }
                if (popped.left == '\"')  */ {
            /* break */ ;
            /* }
            }

            return new Some<>(current) */ ;
        }
        return new None();
    }
    /* private static */ foldSingleQuotes(tuple) {
        /* if (tuple.left != '\'')  */ {
            return new None();
        }
        let appended = tuple.right.append(tuple.left);
        return appended.popAndAppendToTuple().map(Main.foldEscaped).flatMap(DivideState.popAndAppendToOption);
    }
    /* private static */ foldEscaped(escaped) {
        /* if (escaped.left == '\\')  */ {
            return escaped.right.popAndAppendToOption().orElse(escaped.right);
        }
        return escaped.right;
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
                        return getOr(targetInfix, state, beforeInfix, s, content1);
                    }).or(() => {
                        return getOr(targetInfix, state, beforeInfix, beforeContent, content1);
                    });
                });
            });
        });
    }
    /* private static */ getOr(targetInfix, state, beforeInfix, beforeContent, content1) {
        return suffix(beforeContent, ")", (s) => {
            return first(s, "(", (s1, s2) => {
                return getOred(targetInfix, state, beforeInfix, s1, content1);
            });
        }).or(() => {
            return getOred(targetInfix, state, beforeInfix, beforeContent, content1);
        });
    }
    /* private static */ getOred(targetInfix, state, beforeInfix, beforeContent, content1) {
        return first(beforeContent, "<", (name, withTypeParams) => {
            return first(withTypeParams, ">", (typeParamsString, afterTypeParams) => {
                let typeParams = parseValues(state, typeParamsString, (state1, s) => new Tuple(state1, s.strip()));
                return assemble(typeParams.left, targetInfix, beforeInfix, name, content1, typeParams.right, afterTypeParams);
            });
        }).or(() => {
            return assemble(state, targetInfix, beforeInfix, beforeContent, content1, Lists.empty(), "");
        });
    }
    /* private static */ assemble(state, targetInfix, beforeInfix, rawName, content, typeParams, afterTypeParams) {
        let name = rawName.strip();
        /* if (!isSymbol(name))  */ {
            return new None();
        }
        let joinedTypeParams = typeParams.iterate().collect(new Joiner(", ")).map((inner) => "<" + inner + ">").orElse("");
        let statements = compileStatements(state, content, (state0, input) => compileClassSegment(state0, input, 1));
        let generated = generatePlaceholder(beforeInfix.strip()) + targetInfix + name + joinedTypeParams + generatePlaceholder(afterTypeParams) + " {" + statements.right + "\n}\n";
        return new Some(new Tuple(statements.left.addStructure(generated), ""));
    }
    /* private static */ isSymbol(input) {
        /* for (var i = 0; i < input.length(); i++) {{
            var c = input.charAt(i);
            if (Character.isLetter(c) || (i != 0 && Character.isDigit(c)))  */ {
            /* continue */ ;
            /* }
            return false */ ;
        }
        return true;
    }
    /* private static  */ suffix(input, suffix, mapper) {
        /* if (!input.endsWith(suffix))  */ {
            return new None();
        }
        let slice = input.substring(0, input.length() - suffix.length());
        return mapper.apply(slice);
    }
    /* private static */ compileClassSegment(state, input, depth) {
        return compileWhitespace(input, state).or(() => compileClass(input, depth, state)).or(() => structure(input, "interface ", "interface ", state)).or(() => structure(input, "record ", "class ", state)).or(() => method(state, input, depth)).or(() => compileDefinitionStatement(input, depth, state)).orElseGet(() => new Tuple(state, generatePlaceholder(input)));
    }
    /* private static */ compileWhitespace(input, state) {
        /* if (input.isBlank())  */ {
            return new Some(new Tuple(state, ""));
        }
        return new None();
    }
    /* private static */ method(state, input, depth) {
        return first(input, "(", (definition, withParams) => {
            return first(withParams, ")", (params, rawContent) => {
                let definitionTuple = parseDefinition(state, definition).map((definition1) => {
                    let paramsTuple = compileValues(state, params, Main.compileParameter);
                    let generated = definition1.right.generateWithParams("(" + paramsTuple.right + ")");
                    return new Tuple(paramsTuple.left, generated);
                }).orElseGet(() => new Tuple(state, generatePlaceholder(definition)));
                let content = rawContent.strip();
                let indent = createIndent(depth);
                /* if (content.equals(";"))  */ {
                    let s = indent + definitionTuple.right + ";";
                    return new Some(new Tuple(definitionTuple.left, s));
                }
                /* if (content.startsWith("{") && content.endsWith("}"))  */ {
                    let substring = content.substring(1, content.length() - 1);
                    let statementsTuple = compileFunctionSegments(definitionTuple.left, substring, depth);
                    let generated = indent + definitionTuple.right + " {" + statementsTuple.right + indent + "}";
                    return new Some(new Tuple(statementsTuple.left, generated));
                }
                return new None();
            });
        });
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
                return toLast(withoutEnd, "{", Main.foldBlockStart);
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
        /* if (c == '{')  */ {
            return appended.advance();
        }
        return appended;
    }
    /* private static */ statementValue(state, input, depth) {
        let stripped = input.strip();
        /* if (stripped.startsWith("return "))  */ {
            let value = stripped.substring("return ".length());
            let tuple = value(state, value, depth);
            return new Tuple(tuple.left, "return " + tuple.right);
        }
        return first(stripped, "=", (s, s2) => {
            let definitionTuple = compileDefinition(state, s);
            let valueTuple = value(definitionTuple.left, s2, depth);
            return new Some(new Tuple(valueTuple.left, "let " + definitionTuple.right + " = " + valueTuple.right));
        }).orElseGet(() => {
            return new Tuple(state, generatePlaceholder(stripped));
        });
    }
    /* private static */ value(state, input, depth) {
        return lambda(state, input, depth).or(() => stringValue(state, input)).or(() => dataAccess(state, input, depth)).or(() => symbolValue(state, input)).or(() => invocation(state, input, depth)).or(() => operation(state, input, depth, "+")).or(() => operation(state, input, depth, "-")).or(() => digits(state, input)).or(() => not(state, input, depth)).or(() => methodReference(state, input, depth)).orElseGet(() => new [CompileState, string](state, generatePlaceholder(input)));
    }
    /* private static */ methodReference(state, input, depth) {
        return last(input, "::", (s, s2) => {
            let value = value(state, s, depth);
            return new Some(new Tuple(value.left, value.right + "." + s2));
        });
    }
    /* private static */ not(state, input, depth) {
        let stripped = input.strip();
        /* if (stripped.startsWith("!"))  */ {
            let slice = stripped.substring(1);
            let value = value(state, slice, depth);
            return new Some(new Tuple(value.left, "!" + value.right));
        }
        return new None();
    }
    /* private static */ lambda(state, input, depth) {
        return first(input, "->", (beforeArrow, valueString) => {
            let strippedBeforeArrow = beforeArrow.strip();
            /* if (isSymbol(strippedBeforeArrow))  */ {
                return assembleLambda(state, Lists.of(strippedBeforeArrow), valueString, depth);
            }
            /* if (strippedBeforeArrow.startsWith("(") && strippedBeforeArrow.endsWith(")"))  */ {
                let parameterNames = divideAll(strippedBeforeArrow.substring(1, strippedBeforeArrow.length() - 1), Main.foldValueChar);
                return assembleLambda(state, parameterNames, valueString, depth);
            }
            return new None();
        });
    }
    /* private static */ assembleLambda(state, paramNames, valueString, depth) {
        /* Tuple<CompileState, String> value */ ;
        let strippedValueString = valueString.strip();
        /* String s */ ;
        /* if (strippedValueString.startsWith("{") && strippedValueString.endsWith("}"))  */ {
            let /* value  */ = compileFunctionSegments(state, strippedValueString.substring(1, strippedValueString.length() - 1), depth);
            let /* s  */ = "{" + value.right + createIndent(depth) + "}";
        }
        /* else  */ {
            let /* value  */ = value(state, strippedValueString, depth);
            let /* s  */ = value.right;
        }
        let joined = paramNames.iterate().collect(new Joiner(", ")).orElse("");
        return new Some(new Tuple(value.left, "(" + joined + ") => " + s));
    }
    /* private static */ digits(state, input) {
        let stripped = input.strip();
        /* if (isNumber(stripped))  */ {
            return new Some(new Tuple(state, stripped));
        }
        return new None();
    }
    /* private static */ isNumber(input) {
        /* for (var i = 0; i < input.length(); i++) {{
            var c = input.charAt(i);
            if (Character.isDigit(c))  */ {
            /* continue */ ;
            /* }
            return false */ ;
        }
        return true;
    }
    /* private static */ invocation(state, input, depth) {
        return suffix(input.strip(), ")", (withoutEnd) => {
            return split(() => toLast(withoutEnd, "", Main.foldInvocationStart), (callerWithEnd, argumentsString) => {
                return suffix(callerWithEnd, "(", (callerString) => {
                    let callerString1 = callerString.strip();
                    let callerTuple = invocationHeader(state, depth, callerString1);
                    let argumentsTuple = compileValues(callerTuple.left, argumentsString, (state1, input1) => value(state1, input1, depth));
                    return new Some(new Tuple(argumentsTuple.left, callerTuple.right + "(" + argumentsTuple.right + ")"));
                });
            });
        });
    }
    /* private static */ invocationHeader(state, depth, callerString1) {
        /* if (callerString1.startsWith("new ")) {{
            String input1 = callerString1.substring("new ".length());
            var map = type(state, input1).map(type -> {{
                return new Tuple<>(type.left, "new " + type.right);
            });

            if (map.isPresent())  */ {
            return map.orElse(null); /* } */
        }
        return value(state, callerString1, depth);
    }
    /* private static */ foldInvocationStart(state, c) {
        let appended = state.append(c);
        /* if (c == '(') {{
            var enter = appended.enter();
            if (enter.isShallow())  */ {
            return enter.advance();
            /* }
            return enter */ ;
        }
        /* if (c == ')')  */ {
            return appended.exit();
        }
        return appended;
    }
    /* private static */ dataAccess(state, input, depth) {
        return last(input.strip(), ".", (parent, property) => {
            /* if (!isSymbol(property))  */ {
                return new None();
            }
            let value = value(state, parent, depth);
            return new Some(new Tuple(value.left, value.right + "." + property));
        });
    }
    /* private static */ stringValue(state, input) {
        let stripped = input.strip();
        /* if (stripped.startsWith("\"") && stripped.endsWith("\""))  */ {
            return new Some(new Tuple(state, stripped));
        }
        return new None();
    }
    /* private static */ symbolValue(state, value) {
        let stripped = value.strip();
        /* if (isSymbol(stripped))  */ {
            return new Some(new Tuple(state, stripped));
        }
        return new None();
    }
    /* private static */ operation(state, value, depth, infix) {
        return first(value, infix, (s, s2) => {
            let leftTuple = value(state, s, depth);
            let rightTuple = value(leftTuple.left, s2, depth);
            return new Some(new Tuple(rightTuple.left, leftTuple.right + " " + infix + " " + rightTuple.right));
        });
    }
    /* private static */ compileValues(state, params, mapper) {
        let parsed = parseValues(state, params, mapper);
        let generated = generateValues(parsed.right);
        return new Tuple(parsed.left, generated);
    }
    /* private static */ generateValues(elements) {
        return generateAll(Main.mergeValues, elements);
    }
    /* private static */ parseValues(state, input, mapper) {
        return parseAll(state, input, Main.foldValueChar, mapper);
    }
    /* private static */ compileParameter(state, input) {
        /* if (input.isBlank())  */ {
            return new Tuple(state, "");
        }
        return compileDefinition(state, input);
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
                        let typeParams = parseValues(state, typeParamsString, (state1, s) => new Tuple(state1, s.strip()));
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
        let type1 = typeOrPlaceholder(state, type);
        let node = new Definition(beforeTypeParams, type1.right, name.strip(), typeParams);
        return new Some(new Tuple(type1.left, node));
    }
    /* private static */ foldValueChar(state, c) {
        /* if (c == ',' && state.isLevel())  */ {
            return state.advance();
        }
        let appended = state.append(c);
        /* if (c == '-') {{
            var peeked = appended.peek();
            if (peeked == '>') {{
                return appended.popAndAppendToOption().orElse(appended);
            }
            else  */ {
            return appended; /* } */
        }
        /* if (c == '<' || c == '(' || c == '{')  */ {
            return appended.enter();
        }
        /* if (c == '>' || c == ')' || c == '}')  */ {
            return appended.exit();
        }
        return appended;
    }
    /* private static */ typeOrPlaceholder(state, input) {
        return type(state, input).orElseGet(() => new Tuple(state, generatePlaceholder(input)));
    }
    /* private static */ type(state, input) {
        let stripped = input.strip();
        /* if (stripped.equals("int"))  */ {
            return new Some(new Tuple(state, "number"));
        }
        /* if (stripped.equals("String"))  */ {
            return new Some(new Tuple(state, "string"));
        }
        /* if (isSymbol(stripped))  */ {
            return new Some(new Tuple(state, stripped));
        }
        return template(state, input).or(() => varArgs(state, input));
    }
    /* private static */ varArgs(state, input) {
        return suffix(input, "...", (s) => {
            let inner = typeOrPlaceholder(state, s);
            return new Some(new Tuple(inner.left, inner.right + "[]"));
        });
    }
    /* private static */ template(state, input) {
        return suffix(input.strip(), ">", (withoutEnd) => {
            return first(withoutEnd, "<", (base, argumentsString) => {
                let strippedBase = base.strip();
                let argumentsTuple = parseValues(state, argumentsString, Main.typeOrPlaceholder);
                let argumentsState = argumentsTuple.left;
                let arguments = argumentsTuple.right;
                /* if (base.equals("BiFunction"))  */ {
                    return new Some(new Tuple(argumentsState, generate(Lists.of(arguments.get(0), arguments.get(1)), arguments.get(2))));
                }
                /* if (base.equals("Function"))  */ {
                    return new Some(new Tuple(argumentsState, generate(Lists.of(arguments.get(0)), arguments.get(1))));
                }
                /* if (base.equals("Predicate"))  */ {
                    return new Some(new Tuple(argumentsState, generate(Lists.of(arguments.get(0)), "boolean")));
                }
                /* if (base.equals("Supplier"))  */ {
                    return new Some(new Tuple(argumentsState, generate(Lists.empty(), arguments.get(0))));
                }
                /* if (base.equals("Tuple") && arguments.size() >= 2)  */ {
                    return new Some(new Tuple(argumentsState, "[" + arguments.get(0) + ", " + arguments.get(1) + "]"));
                }
                return new Some(new Tuple(argumentsState, strippedBase + "<" + generateValues(arguments) + ">"));
            });
        });
    }
    /* private static */ generate(arguments, returns) {
        let joined = arguments.iterate().collect(new Joiner(", ")).orElse("");
        return "(" + joined + ") => " + returns;
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
        return split(() => locator.apply(input, infix).map((index) => {
            let left = input.substring(0, index);
            let right = input.substring(index + infix.length());
            return new Tuple(left, right);
        }), mapper);
    }
    /* private static  */ split(splitter, mapper) {
        return splitter.get().flatMap((tuple) => mapper.apply(tuple.left, tuple.right));
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
    }
}
/*  */ 
