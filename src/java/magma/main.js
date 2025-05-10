"use strict";
/* public */ class Main {
}
/* private */ record;
Some(T, value);
implements;
Option < T > {};
/* private static */ class None {
}
/* private */ record;
HeadedIterator(Head < T > head);
implements;
Iterator < T > {};
/* private static */ class RangeHead {
}
/* private static */ class Lists {
}
 > elements;
implements;
List < T > {};
/* public static  */ empty();
List; /* {
    return new JVMList<>();
} */
/* public static  */ of(elements);
List; /* {
    return new JVMList<>(new ArrayList<>(Arrays.asList(elements)));
} */
/* private static */ class State {
}
/* private */ record;
Joiner(String, delimiter);
implements;
Collector < String, Option < String >> {};
/* private */ record;
Definition(Option < String > maybeBefore, String, type, String, name, List < String > typeParams);
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
/* private */ record;
Tuple(A, left, B, right);
{
}
/* public static */ main();
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
/* private static */ compile(input, String);
String; /* {
    return compileStatements(input, Main::compileRootSegment);
} */
/* private static */ compileStatements(input, String, mapper, (String) => String);
String; /* {
    return compileAll(input, Main::foldStatementChar, mapper, Main::mergeStatements);
} */
/* private static */ compileAll(input, String, folder, (State, Character) => State, mapper, (String) => String, merger, (StringBuilder, String) => StringBuilder);
String; /* {
    return generateAll(merger, parseAll(input, folder, mapper));
} */
/* private static */ generateAll(merger, (StringBuilder, String) => StringBuilder, elements, (List));
String; /* {
    return elements
            .iterate()
            .fold(new StringBuilder(), merger)
            .toString();
} */
/* private static */ parseAll(input, String, folder, (State, Character) => State, mapper, (String) => String);
List; /* {
    return divideAll(input, folder)
            .iterate()
            .map(mapper)
            .collect(new ListCollector<>());
} */
/* private static */ mergeStatements(stringBuilder, StringBuilder, str, String);
StringBuilder; /* {
    return stringBuilder.append(str);
} */
/* private static */ divideAll(input, String, folder, (State, Character) => State);
List; /* {
    var current = new State();
    for (var i = 0; i < input.length(); i++) {
        var c = input.charAt(i);
        current = folder.apply(current, c);
    }

    return current.advance().segments;
} */
/* private static */ foldStatementChar(state, State, c, char);
State; /* {
    var append = state.append(c);
    if (c == ';' && append.isLevel()) {
        return append.advance();
    }
    if (c == '} */
/* ' */ append.isShallow();
/*
    if  */ /* {
    return append.enter();
}
if (c == '} */
/* ') {
        */ append.exit();
return; /* ;
} */
append: return;
/* private static String compileRootSegment(String input) {
        var stripped = input.strip();
        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return "";
        }

        return compileClass(stripped, 0).orElseGet(() -> generatePlaceholder(stripped));
    } */ /* private static Option<String> compileClass(String stripped, int depth) {
    return compileStructure(stripped, depth, "class ");
} */ /* private static Option<String> compileStructure(String stripped, int depth, String infix) {
    return first(stripped, infix, (beforeInfix, right) -> {
        return first(right, "{", (name, withEnd) -> {
            var strippedWithEnd = withEnd.strip();
            return suffix(strippedWithEnd, "}", content1 -> {
                var strippedName = name.strip();

                var indent = createIndent(depth);
                var beforeIndent = depth == 0 ? "" : indent;
                var afterBlock = depth == 0 ? "\n" : "";

                var statements = compileStatements(content1, input -> compileClassSegment(input, depth + 1));
                return new Some<>(beforeIndent + generatePlaceholder(beforeInfix.strip()) + infix + strippedName + " {" + statements + indent + "}" + afterBlock);
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
} */ /* private static String compileClassSegment(String input, int depth) {
    return compileWhitespace(input)
            .or(() -> compileClass(input, depth))
            .or(() -> compileStructure(input, depth, "interface "))
            .or(() -> compileStructure(input, depth, "record "))
            .or(() -> compileMethod(input, depth))
            .or(() -> compileDefinitionStatement(input, depth))
            .orElseGet(() -> generatePlaceholder(input));
} */ /* private static Option<String> compileWhitespace(String input) {
    if (input.isBlank()) {
        return new Some<>("");
    }
    return new None<>();
} */ /* private static Option<String> compileMethod(String input, int depth) {
    return first(input, "(", (definition, withParams) -> {
        return first(withParams, ")", (params, rawContent) -> {
            var content = rawContent.strip();
            var newContent = content.equals(";") ? ";" : generatePlaceholder(content);

            return new Some<>(createIndent(depth) + parseDefinition(definition)
                    .map(definition1 -> definition1.generateWithParams("(" + compileValues(params, Main::compileParameter) + ")"))
                    .orElseGet(() -> generatePlaceholder(definition)) + newContent);
        });
    });
} */ /* private static String compileValues(String params, Function<String, String> mapper) {
    return generateValues(parseValues(params, mapper));
} */ /* private static String generateValues(List<String> elements) {
    return generateAll(Main::mergeValues, elements);
} */ /* private static List<String> parseValues(String params, Function<String, String> mapper) {
    return parseAll(params, Main::foldValueChar, mapper);
} */ /* private static String compileParameter(String input) {
    if (input.isBlank()) {
        return "";
    }
    return parseDefinition(input).map(Definition::generate).orElseGet(() -> generatePlaceholder(input));
} */ /* private static StringBuilder mergeValues(StringBuilder cache, String element) {
    if (cache.isEmpty()) {
        return cache.append(element);
    }
    return cache.append(", ").append(element);
} */ /* private static String createIndent(int depth) {
    return "\n" + "\t".repeat(depth);
} */ /* private static Option<String> compileDefinitionStatement(String input, int depth) {
    return suffix(input.strip(), ";", withoutEnd -> {
        return parseDefinition(withoutEnd).map(result -> createIndent(depth) + result.generate() + ";");
    });
} */ /* private static Option<Definition> parseDefinition(String input) {
    return last(input.strip(), " ", (beforeName, name) -> {
        return split(() -> getStringStringTuple(beforeName), (beforeType, type) -> {
            return suffix(beforeType.strip(), ">", withoutTypeParamStart -> {
                return first(withoutTypeParamStart, "<", (beforeTypeParams, typeParamsString) -> {
                    var typeParams = parseValues(typeParamsString, String::strip);

                    return assembleDefinition(new Some<String>(beforeTypeParams), name, typeParams, type);
                });
            }).or(() -> {
                return assembleDefinition(new Some<String>(beforeType), name, Lists.empty(), type);
            });
        }).or(() -> {
            return assembleDefinition(new None<String>(), name, Lists.empty(), beforeName);
        });
    });
} */ /* private static Option<Tuple<String, String>> getStringStringTuple(String beforeName) {
    var divisions = divideAll(beforeName, Main::foldTypeSeparator);
    return divisions.removeLast().map(removed -> {
        var left = removed.left.iterate().collect(new Joiner(" ")).orElse("");
        var right = removed.right;

        return new Tuple<>(left, right);
    });
} */ /* private static State foldTypeSeparator(State state, Character c) {
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
} */ /* private static Option<Definition> assembleDefinition(Option<String> beforeTypeParams, String name, List<String> typeParams, String type) {
    return new Some<>(new Definition(beforeTypeParams, type(type), name.strip(), typeParams));
} */ /* private static State foldValueChar(State state, char c) {
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
} */ /* private static String type(String input) {
    var stripped = input.strip();
    if (stripped.equals("int")) {
        return "number";
    }

    if (isSymbol(stripped)) {
        return stripped;
    }

    return template(input).orElseGet(() -> generatePlaceholder(stripped));
} */ /* private static Option<String> template(String input) {
    return suffix(input.strip(), ">", withoutEnd -> {
        return first(withoutEnd, "<", (base, argumentsString) -> {
            var strippedBase = base.strip();
            var arguments = parseValues(argumentsString, Main::type);

            if (base.equals("BiFunction")) {
                return new Some<>(generate(Lists.of(arguments.get(0), arguments.get(1)), arguments.get(2)));
            }

            if (base.equals("Function")) {
                return new Some<>(generate(Lists.of(arguments.get(0)), arguments.get(1)));
            }

            if (base.equals("Predicate")) {
                return new Some<>(generate(Lists.of(arguments.get(0)), "boolean"));
            }

            if (base.equals("Supplier")) {
                return new Some<>(generate(Lists.empty(), arguments.get(0)));
            }

            return new Some<>(strippedBase + "<" + generateValues(arguments) + ">");
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
