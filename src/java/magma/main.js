"use strict";
/* private */ class Some {
}
(T);
value;
implements;
Option < T > {};
/* private static */ class None {
}
/* private */ class HeadedIterator {
}
(Head < T > head);
implements;
Iterator < T > {};
/* private static */ class RangeHead {
}
/* private */ class JVMList {
}
(java.util.List < T > elements);
implements;
List < T > {};
/* private static */ class Lists {
}
/* private */ class CompileState {
}
(List < String > structures);
{
    CompileState();
    public; /* {
            this(Lists.empty());
        } */
    /* public */ addStructure(structure, String);
    CompileState; /* {
            return new CompileState(this.structures.add(structure));
        } */
}
/* private static */ class DivideState {
}
/* private */ class Joiner {
}
(String);
delimiter;
implements;
Collector < String, Option < String >> {};
/* private */ class Definition {
}
(Option < String > maybeBefore, String);
type, String;
name, List < String > typeParams;
{
    /* private */ generate();
    String; /* {
            return this.generateWithParams("");
        } */
    /* public */ generateWithParams(params, String);
    String; /* {
            var joined = this.typeParams.iterate()
                    .collect(new Joiner())
                    .map(inner -> "<" + inner + ">")
                    .orElse("");

            var before = this.maybeBefore
                    .filter(value -> !value.isEmpty())
                    .map(Main::generatePlaceholder)
                    .map(inner -> inner + " ")
                    .orElse("");

            return before + this.name + joined + params + " : " + this.type;
        } */
}
/* private static */ class ListCollector {
}
/* private */ class Tuple {
}
(A);
left, B;
right;
{
}
/* public */ class Main {
}
() => append;
return;
/* private static Tuple<CompileState, String> compileRootSegment(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return new Tuple<>(state, "");
        }

        return compileClass(stripped, 0, state)
                .orElseGet(() -> new Tuple<>(state, generatePlaceholder(stripped)));
    } */ /* private static Option<Tuple<CompileState, String>> compileClass(String stripped, int depth, CompileState state) {
    return compileStructure(stripped, "class ", "class ", state);
} */ /* private static Option<Tuple<CompileState, String>> compileStructure(String stripped, String sourceInfix, String targetInfix, CompileState state) {
    return first(stripped, sourceInfix, (beforeInfix, right) -> {
        return first(right, "{", (name, withEnd) -> {
            var strippedWithEnd = withEnd.strip();
            return suffix(strippedWithEnd, "}", content1 -> {
                var strippedName = name.strip();
                var statements = compileStatements(state, content1, (state0, input) -> compileClassSegment(state0, input, 1));
                var generated = generatePlaceholder(beforeInfix.strip()) + targetInfix + strippedName + " {" + statements.right + "\n}\n";
                return new Some<>(new Tuple<>(statements.left.addStructure(generated), ""));
            });
        });
    });
} */ /* private static boolean isSymbol(String input) {
    for (var i = 0; i < input.length(); i++) {
        var c = input.charAt(i);
        if (Character.isLetter(c)) {
            continue;
        }
        return false;
    }
    return true;
} */ /* private static <T> Option<T> suffix(String input, String suffix, Function<String, Option<T>> mapper) {
    if (!input.endsWith(suffix)) {
        return new None<>();
    }

    var slice = input.substring(0, input.length() - suffix.length());
    return mapper.apply(slice);
} */ /* private static Tuple<CompileState, String> compileClassSegment(CompileState state, String input, int depth) {
    return compileWhitespace(input, state)
            .or(() -> compileClass(input, depth, state))
            .or(() -> compileStructure(input, "interface ", "interface ", state))
            .or(() -> compileStructure(input, "record ", "class ", state))
            .or(() -> compileMethod(input, depth, state))
            .or(() -> compileDefinitionStatement(input, depth, state))
            .orElseGet(() -> new Tuple<>(state, generatePlaceholder(input)));
} */ /* private static Option<Tuple<CompileState, String>> compileWhitespace(String input, CompileState state) {
    if (input.isBlank()) {
        return new Some<>(new Tuple<>(state, ""));
    }
    return new None<>();
} */ /* private static Option<Tuple<CompileState, String>> compileMethod(String input, int depth, CompileState state) {
    return first(input, "(", (definition, withParams) -> {
        return first(withParams, ")", (params, rawContent) -> {
            var content = rawContent.strip();
            var newContent = content.equals(";") ? ";" : generatePlaceholder(content);

            var tuple = parseDefinition(state, definition)
                    .map(definition1 -> {
                        var paramsTuple = compileValues(state, params, Main::compileParameter);
                        var generated = definition1.right.generateWithParams("(" + paramsTuple.right + ")");
                        return new Tuple<>(paramsTuple.left, generated);
                    })
                    .orElseGet(() -> new Tuple<>(state, generatePlaceholder(definition)));

            var s = createIndent(depth) + tuple.right + newContent;
            return new Some<>(new Tuple<>(tuple.left, s));
        });
    });
} */ /* private static Tuple<CompileState, String> compileValues(CompileState state, String params, BiFunction<CompileState, String, Tuple<CompileState, String>> mapper) {
    var parsed = parseValues(state, params, mapper);
    var generated = generateValues(parsed.right);
    return new Tuple<>(parsed.left, generated);
} */ /* private static String generateValues(List<String> elements) {
    return generateAll(Main::mergeValues, elements);
} */ /* private static Tuple<CompileState, List<String>> parseValues(CompileState state, String input, BiFunction<CompileState, String, Tuple<CompileState, String>> mapper) {
    return parseAll(state, input, Main::foldValueChar, mapper);
} */ /* private static Tuple<CompileState, String> compileParameter(CompileState state, String input) {
    if (input.isBlank()) {
        return new Tuple<>(state, "");
    }

    return parseDefinition(state, input)
            .map((Tuple<CompileState, Definition> tuple) -> new Tuple<>(tuple.left, tuple.right.generate()))
            .orElseGet(() -> new Tuple<>(state, generatePlaceholder(input)));
} */ /* private static StringBuilder mergeValues(StringBuilder cache, String element) {
    if (cache.isEmpty()) {
        return cache.append(element);
    }
    return cache.append(", ").append(element);
} */ /* private static String createIndent(int depth) {
    return "\n" + "\t".repeat(depth);
} */ /* private static Option<Tuple<CompileState, String>> compileDefinitionStatement(String input, int depth, CompileState state) {
    return suffix(input.strip(), ";", withoutEnd -> {
        return parseDefinition(state, withoutEnd).map(result -> {
            var generated = createIndent(depth) + result.right.generate() + ";";
            return new Tuple<>(result.left, generated);
        });
    });
} */ /* private static Option<Tuple<CompileState, Definition>> parseDefinition(CompileState state, String input) {
    return last(input.strip(), " ", (beforeName, name) -> {
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
    });
} */ /* private static Option<Tuple<String, String>> getStringStringTuple(String beforeName) {
    var divisions = divideAll(beforeName, Main::foldTypeSeparator);
    return divisions.removeLast().map(removed -> {
        var left = removed.left.iterate().collect(new Joiner(" ")).orElse("");
        var right = removed.right;

        return new Tuple<>(left, right);
    });
} */ /* private static DivideState foldTypeSeparator(DivideState state, Character c) {
    if (c == ' ' && state.isLevel()) {
        return state.advance();
    }

    var appended = state.append(c);
    if (c == '<') {
        return appended.enter();
    }
    if (c == '>') {
        return appended.exit();
    }
    return appended;
} */ /* private static Option<Tuple<CompileState, Definition>> assembleDefinition(CompileState state, Option<String> beforeTypeParams, String name, List<String> typeParams, String type) {
    var type1 = type(state, type);
    var node = new Definition(beforeTypeParams, type1.right, name.strip(), typeParams);
    return new Some<>(new Tuple<>(type1.left, node));
} */ /* private static DivideState foldValueChar(DivideState state, char c) {
    if (c == ',' && state.isLevel()) {
        return state.advance();
    }

    var appended = state.append(c);
    if (c == '<') {
        return appended.enter();
    }
    if (c == '>') {
        return appended.exit();
    }
    return appended;
} */ /* private static Tuple<CompileState, String> type(CompileState state, String input) {
    var stripped = input.strip();
    if (stripped.equals("int")) {
        return new Tuple<>(state, "number");
    }

    if (isSymbol(stripped)) {
        return new Tuple<>(state, stripped);
    }

    return template(state, input).orElseGet(() -> new Tuple<>(state, generatePlaceholder(stripped)));
} */ /* private static Option<Tuple<CompileState, String>> template(CompileState state, String input) {
    return suffix(input.strip(), ">", withoutEnd -> {
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
                return new Some<>(new Tuple<>(argumentsState, "[" + arguments.get(0) + ", " + arguments.get(1) + "]"));
            }

            return new Some<>(new Tuple<>(argumentsState, strippedBase + "<" + generateValues(arguments) + ">"));
        });
    });
} */ /* private static String generate(List<String> arguments, String returns) {
    var joined = arguments.iterate()
            .collect(new Joiner(", "))
            .orElse("");

    return "(" + joined + ") => " + returns;
} */ /* private static <T> Option<T> last(String input, String infix, BiFunction<String, String, Option<T>> mapper) {
    return infix(input, infix, Main::findLast, mapper);
} */ /* private static Option<Integer> findLast(String input, String infix) {
    var index = input.lastIndexOf(infix);
    return index == -1 ? new None<Integer>() : new Some<>(index);
} */ /* private static <T> Option<T> first(String input, String infix, BiFunction<String, String, Option<T>> mapper) {
    return infix(input, infix, Main::findFirst, mapper);
} */ /* private static <T> Option<T> infix(
        String input,
        String infix,
        BiFunction<String, String, Option<Integer>> locator,
        BiFunction<String, String, Option<T>> mapper
) {
    return split(() -> locator.apply(input, infix).map(index -> {
        var left = input.substring(0, index);
        var right = input.substring(index + infix.length());
        return new Tuple<>(left, right);
    }), mapper);
} */ /* private static <T> Option<T> split(Supplier<Option<Tuple<String, String>>> splitter, BiFunction<String, String, Option<T>> mapper) {
    return splitter.get().flatMap(tuple -> mapper.apply(tuple.left, tuple.right));
} */ /* private static Option<Integer> findFirst(String input, String infix) {
    var index = input.indexOf(infix);
    return index == -1 ? new None<Integer>() : new Some<>(index);
} */ /* private static String generatePlaceholder(String input) {
    var replaced = input
            .replace("content-start", "content-start")
            .replace("content-end", "content-end");

    return "content-start " + replaced + " content-end";
} */ /* } */ 
