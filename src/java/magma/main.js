"use strict";
/* public  */ class Main {
}
/* private */ HeadedIterator( /* Head<T> head */);
/*

private static  */ class RangeHead {
}
/*


private static  */ class Lists {
}
( /*  */) => ; /* List<T> */ /* {
    return new JVMList<>();
} */ /*
*/
/*

private static  */ class State {
}
( /* char c */) => ;
( /*  */) => ;
( /*  */) => ;
( /*  */) => ;
( /*  */) => ; /* boolean */ /* {
    return this.depth == 1;
} */ /*
*/
/*

private static  */ class Joiner {
}
( /* Optional<String> current, String element */) => ; /* Optional<String> */ /* {
    return Optional.of(current.map(inner -> inner + element).orElse(element));
} */ /*
*/
/* private */ Definition( /* String beforeType, String type, String name, List<String> typeParams */);
/*

private static  */ class ListCollector {
}
( /* List<T> current, T element */) => ; /* List<T> */ /* {
    return current.add(element);
} */ /*
*/
/* public static */ main( /*  */);
/* private static */ compile( /* String input */);
/* private static */ compileStatements( /* String input, Function<String, String> mapper */);
/* private static */ divideStatements( /* String input */);
/* private static */ divideAll( /* String input, BiFunction<State, Character, State> folder */);
/* private static */ fold( /* State state, char c */);
/* ' */ append.isShallow( /*  */);
/*
    if  */ /* {
    return append.enter();
}
if (c == '} */
/* ') {
        */ append.exit( /*  */);
/* private static String compileRootSegment(String input) {
        var stripped = input.strip();
        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return "";
        }

        return compileClass(stripped, 0).orElseGet(() -> generatePlaceholder(stripped));
    } */ /* private static Optional<String> compileClass(String stripped, int depth) {
    return compileStructure(stripped, depth, "class ");
} */ /* private static Optional<String> compileStructure(String stripped, int depth, String infix) {
    return first(stripped, infix, (left, right) -> {
        return first(right, "{", (name, withEnd) -> {
            var strippedWithEnd = withEnd.strip();
            return suffix(strippedWithEnd, "}", content1 -> {
                var strippedName = name.strip();

                var beforeIndent = depth == 0 ? "" : "\n\t";
                var afterIndent = depth == 0 ? "\n" : "";

                var statements = compileStatements(content1, input -> compileClassSegment(input, depth + 1));
                return Optional.of(beforeIndent + generatePlaceholder(left) + infix + strippedName + " {" + statements + afterIndent + "}" + afterIndent);
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
} */ /* private static <T> Optional<T> suffix(String input, String suffix, Function<String, Optional<T>> mapper) {
    if (!input.endsWith(suffix)) {
        return Optional.empty();
    }

    var slice = input.substring(0, input.length() - suffix.length());
    return mapper.apply(slice);
} */ /* private static String compileClassSegment(String input, int depth) {
    return compileClass(input, depth)
            .or(() -> compileStructure(input, depth, "interface "))
            .or(() -> compileMethod(input, depth))
            .or(() -> compileDefinitionStatement(input, depth))
            .orElseGet(() -> generatePlaceholder(input));
} */ /* private static @NotNull Optional<? extends String> compileMethod(String input, int depth) {
    return first(input, "(", (definition, withParams) -> {
        return first(withParams, ")", (params, rawContent) -> {
            var content = rawContent.strip();
            var newContent = content.equals(";") ? ";" : generatePlaceholder(content);

            return Optional.of(createIndent(depth) + parseDefinition(definition)
                    .map(definition1 -> definition1.generateWithParams("(" + generatePlaceholder(params) + ")"))
                    .orElseGet(() -> generatePlaceholder(definition)) + newContent);
        });
    });
} */ /* private static String createIndent(int depth) {
    return "\n" + "\t".repeat(depth);
} */ /* private static Optional<String> compileDefinitionStatement(String input, int depth) {
    return suffix(input.strip(), ";", withoutEnd -> {
        return parseDefinition(withoutEnd).map(result -> createIndent(depth) + result.generate() + ";");
    });
} */ /* private static Optional<Definition> parseDefinition(String input) {
    return last(input.strip(), " ", (beforeName, name) -> {
        return last(beforeName, " ", (beforeType, type) -> {
            return suffix(beforeType.strip(), ">", withoutTypeParamStart -> {
                return first(withoutTypeParamStart, "<", (beforeTypeParams, typeParamsString) -> {
                    var typeParams = divideAll(typeParamsString, Main::foldValueChar)
                            .iterate()
                            .map(String::strip)
                            .collect(new ListCollector<>());

                    return assembleDefinition(beforeTypeParams, name, typeParams, type);
                });
            }).or(() -> {
                return assembleDefinition(beforeType, name, Lists.empty(), type);
            });
        });
    });
} */ /* private static Optional<Definition> assembleDefinition(String beforeTypeParams, String name, List<String> typeParams, String type) {
    return Optional.of(new Definition(beforeTypeParams, compileType(type), name.strip(), typeParams));
} */ /* private static State foldValueChar(State state, char c) {
    if (c == ',') {
        return state.advance();
    }
    return state.append(c);
} */ /* private static String compileType(String type) {
    return generatePlaceholder(type);
} */ /* private static <T> Optional<T> last(String input, String infix, BiFunction<String, String, Optional<T>> mapper) {
    return compileInfix(input, infix, Main::findLast, mapper);
} */ /* private static Optional<Integer> findLast(String input, String infix) {
    var index = input.lastIndexOf(infix);
    return index == -1 ? Optional.empty() : Optional.of(index);
} */ /* private static <T> Optional<T> first(String input, String infix, BiFunction<String, String, Optional<T>> mapper) {
    return compileInfix(input, infix, Main::findFirst, mapper);
} */ /* private static <T> Optional<T> compileInfix(
        String input,
        String infix,
        BiFunction<String, String, Optional<Integer>> locator,
        BiFunction<String, String, Optional<T>> mapper
) {
    return locator.apply(input, infix).flatMap(index -> {
        var left = input.substring(0, index);
        var right = input.substring(index + infix.length());
        return mapper.apply(left, right);
    });
} */ /* private static Optional<Integer> findFirst(String input, String infix) {
    var index = input.indexOf(infix);
    return index == -1 ? Optional.empty() : Optional.of(index);
} */ /* private static String generatePlaceholder(String input) {
    var replaced = input
            .replace("content-start", "content-start")
            .replace("content-end", "content-end");

    return "content-start " + replaced + " content-end";
} */ /* } */ 
