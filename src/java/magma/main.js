"use strict";
/* public  */ class Main {
}
/* <R> R fold(R initial, BiFunction<R, T, */ folder;
;
/* <R> Iterator<R> map(Function<T, */ mapper;
; /*
*/
/* List<T> */ element;
; /*

Iterator<T> iterate(); */ /*
*/
/*

private static  */ class RangeHead {
}
/*


private static  */ class Lists {
}
/*

private static  */ class State {
} /*

public static void main() {
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
} */ /*

private static String compile(String input) {
    return compileStatements(input, Main::compileRootSegment);
} */ /*

private static String compileStatements(String input, Function<String, String> mapper) {
    return divide(input)
            .iterate()
            .map(mapper)
            .fold(new StringBuilder(), StringBuilder::append)
            .toString();
} */ /*

private static List<String> divide(String input) {
    var current = new State();
    for (var i = 0; i < input.length(); i++) {
        var c = input.charAt(i);
        current = fold(current, c);
    }

    return current.advance().segments;
} */ /*

private static State fold(State state, char c) {
    var append = state.append(c);
    if (c == ';' && append.isLevel()) {
        return append.advance();
    }
    if (c == '} */ /* ' && append.isShallow()) {
    return append.advance().exit();
} */ /*
if (c == '{') {
    return append.enter();
}
if (c == '} */ /* ') {
    return append.exit();
} */ /*
return append; */ /*
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
    return compileFirst(stripped, infix, (left, right) -> {
        return compileFirst(right, "{", (name, withEnd) -> {
            var strippedWithEnd = withEnd.strip();
            return compileSuffix(strippedWithEnd, "}", content1 -> {
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
} */ /* private static Optional<String> compileSuffix(String input, String suffix, Function<String, Optional<String>> mapper) {
    if (!input.endsWith(suffix)) {
        return Optional.empty();
    }

    var slice = input.substring(0, input.length() - suffix.length());
    return mapper.apply(slice);
} */ /* private static String compileClassSegment(String input, int depth) {
    return compileClass(input, depth)
            .or(() -> compileStructure(input, depth, "interface "))
            .or(() -> compileDefinitionStatement(input, depth))
            .orElseGet(() -> generatePlaceholder(input));
} */ /* private static Optional<String> compileDefinitionStatement(String input, int depth) {
    return compileSuffix(input.strip(), ";", withoutEnd -> {
        return compileLast(withoutEnd, " ", (s, name) -> {
            return compileLast(s, " ", (beforeType, type) -> {
                return Optional.of("\n" + "\t".repeat(depth) + generatePlaceholder(beforeType) + " " + name.strip() + " : " + compileType(type) + ";");
            });
        });
    });
} */ /* private static String compileType(String type) {
    return generatePlaceholder(type);
} */ /* private static Optional<String> compileLast(String input, String infix, BiFunction<String, String, Optional<String>> mapper) {
    return compileInfix(input, infix, Main::findLast, mapper);
} */ /* private static Optional<Integer> findLast(String input, String infix) {
    var index = input.lastIndexOf(infix);
    return index == -1 ? Optional.empty() : Optional.of(index);
} */ /* private static Optional<String> compileFirst(String input, String infix, BiFunction<String, String, Optional<String>> mapper) {
    return compileInfix(input, infix, Main::findFirst, mapper);
} */ /* private static Optional<String> compileInfix(String input, String infix, BiFunction<String, String, Optional<Integer>> locator, BiFunction<String, String, Optional<String>> mapper) {
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
