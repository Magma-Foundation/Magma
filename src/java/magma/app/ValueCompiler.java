package magma.app;

import jvm.api.collect.list.Lists;
import magma.api.Tuple2;
import magma.api.Tuple2Impl;
import magma.api.collect.Iters;
import magma.api.collect.head.HeadedIter;
import magma.api.collect.head.RangeHead;
import magma.api.collect.list.Iterable;
import magma.api.collect.list.List;
import magma.api.collect.list.ListCollector;
import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;
import magma.api.text.Characters;
import magma.api.text.Strings;
import magma.app.compile.CompileState;
import magma.app.compile.DivideState;
import magma.app.compile.Stack;
import magma.app.compile.compose.Composable;
import magma.app.compile.compose.PrefixComposable;
import magma.app.compile.compose.SplitComposable;
import magma.app.compile.compose.SuffixComposable;
import magma.app.compile.define.ConstructionCaller;
import magma.app.compile.define.Definition;
import magma.app.compile.define.Parameter;
import magma.app.compile.fold.ValueFolder;
import magma.app.compile.rule.OrRule;
import magma.app.compile.rule.Rule;
import magma.app.compile.select.FirstSelector;
import magma.app.compile.select.Selector;
import magma.app.compile.split.FoldingSplitter;
import magma.app.compile.split.Splitter;
import magma.app.compile.type.Type;
import magma.app.compile.value.AccessValue;
import magma.app.compile.value.Argument;
import magma.app.compile.value.Caller;
import magma.app.compile.value.Invokable;
import magma.app.compile.value.Lambda;
import magma.app.compile.value.Not;
import magma.app.compile.value.Operation;
import magma.app.compile.value.Placeholder;
import magma.app.compile.value.StringValue;
import magma.app.compile.value.Symbol;
import magma.app.compile.value.Value;
import magma.app.compile.fold.OperatorFolder;
import magma.app.compile.locate.FirstLocator;
import magma.app.compile.select.LastSelector;
import magma.app.compile.split.LocatingSplitter;

import java.util.function.Function;

public final class ValueCompiler {
    static Tuple2Impl<CompileState, String> generateValue(Tuple2<CompileState, Value> tuple) {
        var state = tuple.left();
        var right = tuple.right();
        var generated = right.generate();
        var s = Placeholder.generatePlaceholder(ValueCompiler.resolve(state, right).generate());
        return new Tuple2Impl<CompileState, String>(state, generated + s);
    }

    static Option<Tuple2<CompileState, Value>> parseInvokable(CompileState state, String input) {
        return new SuffixComposable<Tuple2<CompileState, Value>>(")", (String withoutEnd) -> {
            return new SplitComposable<Tuple2<CompileState, Value>>((String withoutEnd0) -> {
                    Selector selector = new LastSelector("");
                    return new FoldingSplitter((DivideState state1, char c) -> {
                        return ValueCompiler.foldInvocationStarts(state1, c);
                    }, selector).apply(withoutEnd0);
                }, Composable.toComposable((String callerWithArgStart, String args) -> {
                    return new SuffixComposable<Tuple2<CompileState, Value>>("(", (String callerString) -> {
                        return new PrefixComposable<Tuple2<CompileState, Value>>("new ", (String type) -> {
                            return TypeCompiler.compileType(state, type).flatMap((Tuple2<CompileState, String> callerTuple1) -> {
                                var callerState = callerTuple1.right();
                                var caller = callerTuple1.left();
                                return ValueCompiler.assembleInvokable(caller, new ConstructionCaller(callerState), args);
                            });
                        }).apply(Strings.strip(callerString)).or(() -> {
                            return parseValue(state, callerString).flatMap((Tuple2<CompileState, Value> callerTuple) -> {
                                return ValueCompiler.assembleInvokable(callerTuple.left(), callerTuple.right(), args);
                            });
                        });
                    }).apply(callerWithArgStart);
                })).apply(withoutEnd);
        }).apply(Strings.strip(input));
    }

    static Rule<Value> createTextRule(String slice) {
        return (CompileState state1, String input1) -> {
            var stripped = Strings.strip(input1);
            return new PrefixComposable<Tuple2<CompileState, Value>>(slice, (String s) -> {
                return new SuffixComposable<Tuple2<CompileState, Value>>(slice, (String s1) -> {
                                return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(state1, new StringValue(s1)));
                            }).apply(s);
            }).apply(stripped);
        };
    }

    static Option<Tuple2<CompileState, Value>> parseNot(CompileState state, String input) {
        return new PrefixComposable<Tuple2<CompileState, Value>>("!", (String withoutPrefix) -> {
            var childTuple = ValueCompiler.compileValueOrPlaceholder(state, withoutPrefix);
            var childState = childTuple.left();
            var child = "!" + childTuple.right();
            return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(childState, new Not(child)));
        }).apply(Strings.strip(input));
    }

    static Option<Tuple2<CompileState, Value>> parseLambda(CompileState state, String input) {
        Splitter splitter = new LocatingSplitter("->", new FirstLocator());
        return new SplitComposable<Tuple2<CompileState, Value>>(splitter, Composable.toComposable((String beforeArrow, String afterArrow) -> {
            var strippedBeforeArrow = Strings.strip(beforeArrow);
            return new PrefixComposable<Tuple2<CompileState, Value>>("(", (String withoutStart) -> {
                return new SuffixComposable<Tuple2<CompileState, Value>>(")", (String withoutEnd) -> {
                    return values((CompileState state1, String s) -> {
                        return DefiningCompiler.parseParameter(state1, s);
                    }).apply(state, withoutEnd).flatMap((Tuple2<CompileState, List<Parameter>> paramNames) -> {
                                    return ValueCompiler.compileLambdaWithParameterNames(paramNames.left(), DefiningCompiler.retainDefinitionsFromParameters(paramNames.right()), afterArrow);
                                });
                            }).apply(withoutStart);
            }).apply(strippedBeforeArrow);
        })).apply(input);
    }

    private static Option<Tuple2<CompileState, Value>> compileLambdaWithParameterNames(CompileState state, Iterable<Definition> paramNames, String afterArrow) {
        var strippedAfterArrow = Strings.strip(afterArrow);
        return new PrefixComposable<Tuple2<CompileState, Value>>("{", (String withoutContentStart) -> {
            return new SuffixComposable<Tuple2<CompileState, Value>>("}", (String withoutContentEnd) -> {
                    CompileState compileState = state.enterDepth();
                    var statementsTuple = FunctionSegmentCompiler.compileFunctionStatements(compileState.mapStack((Stack stack1) -> {
                        return stack1.defineAll(paramNames);
                    }), withoutContentEnd);
                    var statementsState = statementsTuple.left();
                    var statements = statementsTuple.right();

                    var exited = statementsState.exitDepth();
                    return ValueCompiler.assembleLambda(exited, paramNames, "{" + statements + exited.createIndent() + "}");
                }).apply(withoutContentStart);
        }).apply(strippedAfterArrow).or(() -> {
            return ValueCompiler.compileValue(state, strippedAfterArrow).flatMap((Tuple2<CompileState, String> tuple) -> {
                return ValueCompiler.assembleLambda(tuple.left(), paramNames, tuple.right());
            });
        });
    }

    private static Option<Tuple2<CompileState, Value>> assembleLambda(CompileState exited, Iterable<Definition> paramNames, String content) {
        return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(exited, new Lambda(paramNames, content)));
    }

    static Rule<Value> createOperatorRule(String infix) {
        return ValueCompiler.createOperatorRuleWithDifferentInfix(infix, infix);
    }

    static Rule<Value> createAccessRule(String infix) {
        return (CompileState state, String input) -> {
            return SplitComposable.compileLast(input, infix, (String childString, String rawProperty) -> {
                var property = Strings.strip(rawProperty);
                if (!ValueCompiler.isSymbol(property)) {
                    return new None<Tuple2<CompileState, Value>>();
                }

                return parseValue(state, childString).flatMap((Tuple2<CompileState, Value> childTuple) -> {
                    var childState = childTuple.left();
                    var child = childTuple.right();
                    return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(childState, new AccessValue(child, property)));
                });
            });
        };
    }

    static Rule<Value> createOperatorRuleWithDifferentInfix(String sourceInfix, String targetInfix) {
        return (CompileState state1, String input1) -> {
            return new SplitComposable<Tuple2<CompileState, Value>>((String slice) -> {
                    return new FoldingSplitter(new OperatorFolder(sourceInfix), (List<String> divisions) -> {
                            return new FirstSelector(sourceInfix).select(divisions);
                        }).apply(slice);
                }, Composable.toComposable((String leftString, String rightString) -> {
                    return parseValue(state1, leftString).flatMap((Tuple2<CompileState, Value> leftTuple) -> {
                        return parseValue(leftTuple.left(), rightString).flatMap((Tuple2<CompileState, Value> rightTuple) -> {
                            var left = leftTuple.right();
                            var right = rightTuple.right();
                            return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(rightTuple.left(), new Operation(left, targetInfix, right)));
                        });
                    });
                })).apply(input1);
        };
    }

    static Option<Tuple2<CompileState, Value>> parseSymbol(CompileState state, String input) {
        var stripped = Strings.strip(input);
        if (ValueCompiler.isSymbol(stripped)) {
            var withImport = TypeCompiler.addResolvedImportFromCache0(state, stripped);
            return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(withImport, new Symbol(stripped)));
        }
        else {
            return new None<Tuple2<CompileState, Value>>();
        }
    }

    static boolean isSymbol(String input) {
        var query = new HeadedIter<Integer>(new RangeHead(Strings.length(input)));
        return query.allMatch((Integer index) -> {
            return ValueCompiler.isSymbolChar(index, input.charAt(index));
        });
    }

    private static boolean isSymbolChar(int index, char c) {
        return '_' == c
                || Characters.isLetter(c)
                || (0 != index && Characters.isDigit(c));
    }

    private static boolean isNumber(String input) {
        var query = new HeadedIter<Integer>(new RangeHead(Strings.length(input)));
        return query.map(input::charAt).allMatch((Character c) -> {
            return Characters.isDigit(c);
        });
    }

    private static Type resolve(CompileState state, Value value) {
        return switch (value) {
            case AccessValue accessValue -> accessValue.resolve(state);
            case Invokable invokable -> invokable.resolve(state);
            case Lambda lambda -> lambda.resolve(state);
            case Not not -> not.resolve(state);
            case Operation operation -> operation.resolve(state);
            case Placeholder placeholder -> placeholder.resolve(state);
            case StringValue stringValue -> stringValue.resolve(state);
            case Symbol symbol -> symbol.resolve(state);
        };
    }

    static Option<Tuple2<CompileState, Value>> parseNumber(CompileState state, String input) {
        var stripped = Strings.strip(input);
        if (ValueCompiler.isNumber(stripped)) {
            return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(state, new Symbol(stripped)));
        }
        else {
            return new None<Tuple2<CompileState, Value>>();
        }
    }

    static Tuple2<CompileState, String> compileValueOrPlaceholder(CompileState state, String input) {
        return ValueCompiler.compileValue(state, input).orElseGet(() -> {
            return new Tuple2Impl<CompileState, String>(state, Placeholder.generatePlaceholder(input));
        });
    }

    static Option<Tuple2<CompileState, String>> compileValue(CompileState state, String input) {
        return parseValue(state, input).map((Tuple2<CompileState, Value> tuple) -> {
            return ValueCompiler.generateValue(tuple);
        });
    }

    private static Option<Tuple2<CompileState, Argument>> parseArgument(CompileState state1, String input) {
        return parseValue(state1, input)
                .map((Tuple2<CompileState, Value> tuple) -> {
                    return new Tuple2Impl<CompileState, Argument>(tuple.left(), tuple.right());
                });
    }

    private static Caller transformCaller(CompileState state, Caller oldCaller) {
        return oldCaller.findChild().flatMap((Value parent) -> {
            var parentType = ValueCompiler.resolve(state, parent);
            if (parentType.isFunctional()) {
                return new Some<Caller>(parent);
            }

            return new None<Caller>();
        }).orElse(oldCaller);
    }

    private static DivideState foldInvocationStarts(DivideState state, char c) {
        var appended = state.append(c);
        if ('(' == c) {
            var entered = appended.enter();
            if (entered.isShallow()) {
                return entered.advance();
            }
            else {
                return entered;
            }
        }

        if (')' == c) {
            return appended.exit();
        }

        return appended;
    }

    private static Option<Tuple2<CompileState, Value>> assembleInvokable(CompileState state, Caller oldCaller, String argsString) {
        return values((CompileState state1, String s) -> {
            return ValueCompiler.parseArgument(state1, s);
        }).apply(state, argsString).flatMap((Tuple2<CompileState, List<Argument>> argsTuple) -> {
            var argsState = argsTuple.left();
            var args = retain(argsTuple.right(), (Argument argument) -> {
                return argument.toValue();
            });

            var newCaller = ValueCompiler.transformCaller(argsState, oldCaller);
            return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(argsState, new Invokable(newCaller, args)));
        });
    }

    static <T, R> Iterable<R> retain(Iterable<T> args, Function<T, Option<R>> mapper) {
        return args.iter()
                .map(mapper)
                .flatMap(Iters::fromOption)
                .collect(new ListCollector<R>());
    }

    public static Option<Tuple2<CompileState, Value>> parseValue(CompileState state, String input) {
        return new OrRule<Value>(Lists.of(
                ValueCompiler::parseLambda,
                createOperatorRule("+"),
                createOperatorRule("-"),
                createOperatorRule("<="),
                createOperatorRule("<"),
                createOperatorRule("&&"),
                createOperatorRule("||"),
                createOperatorRule(">"),
                createOperatorRule(">="),
                ValueCompiler::parseInvokable,
                createAccessRule("."),
                createAccessRule("::"),
                ValueCompiler::parseSymbol,
                ValueCompiler::parseNot,
                ValueCompiler::parseNumber,
                createOperatorRuleWithDifferentInfix("==", "==="),
                createOperatorRuleWithDifferentInfix("!=", "!=="),
                createTextRule("\""),
                createTextRule("'")
        )).apply(state, input);
    }

    public static <T> Rule<List<T>> values(Rule<T> mapper) {
        return new DivideRule<>(new ValueFolder(), mapper);
    }
}