"use strict";
/* public  */ class Main {
}
/* private */ HeadedIterator( /* Head<T> head */);
record; /* implements Iterator<T> {
    @Override
    public <R> R fold(R initial, BiFunction<R, T, R> folder) {
        var current = initial;
        while (true) {
            R finalCurrent = current;
            var optional = this.head.next().map(inner -> folder.apply(finalCurrent, inner));
            if (optional.isPresent()) {
                current = optional.get();
            }
            else {
                return current;
            }
        }
    }

    @Override
    public <R> Iterator<R> map(Function<T, R> mapper) {
        return new HeadedIterator<>(() -> this.head.next().map(mapper));
    }

    @Override
    public <R> R collect(Collector<T, R> collector) {
        return this.fold(collector.createInitial(), collector::fold);
    }
} */
/*

private static  */ class RangeHead {
}
/*


private static  */ class Lists {
}
/*

private static  */ class State {
}
/*

private static  */ class Joiner {
}
( /* Optional<String> current, String element */) => ; /* Optional<String> */ /* {
    return Optional.of(current.map(inner -> inner + element).orElse(element));
} */ /*
*/
/* private */ Definition( /* Optional<String> maybeBefore, String type, String name, List<String> typeParams */);
record; /* {
    private String generate() {
        return this.generateWithParams("");
    }

    public String generateWithParams(String params) {
        var joined = this.typeParams.iterate()
                .collect(new Joiner())
                .map(inner -> "<" + inner + ">")
                .orElse("");

        var before = this.maybeBefore.map(Main::generatePlaceholder)
                .map(inner -> inner + " ")
                .orElse("");

        return before + this.name + joined + params + " : " + this.type;
    }
} */
/*

private static  */ class ListCollector {
}
( /* List<T> current, T element */) => ; /* List<T> */ /* {
    return current.add(element);
} */ /*
*/
/* public static */ main( /*  */);
void /* {
    try {
        var parent = Paths.get(".", "src", "java", "magma");
        var source = parent.resolve("Main.java");
        var target = parent.resolve("main.ts");

        var input = Files.readString(source);
        Files.writeString(target, compile(input));

        new ProcessBuilder("cmd", "/c", "npm", "exec", "tsc")
                .inheritIO()
                .start()
                .waitFor();
    } catch (IOException | InterruptedException e) {
        throw new RuntimeException(e);
    }
} */ 
/* private static */ compile( /* String input */);
String; /* {
    return compileStatements(input, Main::compileRootSegment);
} */
/* private static */ compileStatements( /* String input, Function<String, String> mapper */);
String; /* {
    return divideStatements(input)
            .iterate()
            .map(mapper)
            .fold(new StringBuilder(), StringBuilder::append)
            .toString();
} */
/* private static */ divideStatements( /* String input */);
/* private static */ divideAll( /* String input, BiFunction<State, Character, State> folder */);
/* private static */ fold( /* State state, char c */);
State; /* {
    var append = state.append(c);
    if (c == ';' && append.isLevel()) {
        return append.advance();
    }
    if (c == '} */
/* ' */ append.isShallow( /*  */);
/*
    if  */ /* {
    return append.enter();
}
if (c == '} */
/* ') {
        */ append.exit( /*  */);
return; /* ;
} */
append: return; /*
 */
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

                    return assembleDefinition(Optional.of(beforeTypeParams), name, typeParams, type);
                });
            }).or(() -> {
                return assembleDefinition(Optional.of(beforeType), name, Lists.empty(), type);
            });
        }).or(() -> {
            return assembleDefinition(Optional.empty(), name, Lists.empty(), beforeName);
        });
    });
} */ /* private static Optional<Definition> assembleDefinition(Optional<String> beforeTypeParams, String name, List<String> typeParams, String type) {
    return Optional.of(new Definition(beforeTypeParams, compileType(type), name.strip(), typeParams));
} */ /* private static State foldValueChar(State state, char c) {
    if (c == ',') {
        return state.advance();
    }
    return state.append(c);
} */ /* private static String compileType(String input) {
    var stripped = input.strip();
    if(isSymbol(stripped)) {
        return stripped;
    }

    return generatePlaceholder(stripped);
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
