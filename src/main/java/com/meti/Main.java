package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
    private static int depth = 0;

    public static List<String> split(String line) {
        var lines = new ArrayList<String>();
        var builder = new StringBuilder();
        var depth = 0;
        var isWithinString = false;

        for (int i = 0; i < line.length(); i++) {
            var c = line.charAt(i);
            if (c == '}' && depth == 1 && !isWithinString) {
                builder.append("}");
                depth = 0;
                lines.add(builder.toString());
                builder = new StringBuilder();
            } else if (c == ';' && depth == 0 && !isWithinString) {
                lines.add(builder.toString());
                builder = new StringBuilder();
            } else {
                if (!isWithinString) {
                    if (c == '{' || c == '(') depth++;
                    if (c == '}' || c == ')') depth--;
                }
                if (c == '\'' || c == '\"') {
                    isWithinString = !isWithinString;
                }

                builder.append(c);
            }
        }

        lines.add(builder.toString());

        var output = lines.stream()
                .map(String::strip)
                .filter(value -> !value.isEmpty())
                .collect(Collectors.toList());
        return output;
    }

    public static void main(String[] args) {
        var input = readStringImpl(Paths.get(".", "Main.java")).match(value -> value, err -> {
            err.printStackTrace();
            return "";
        });

        var lines = split(input);
        var output = new StringBuilder();
        for (var line : lines) {
            var stripped = line.strip();
            String output1;
            try {
                output1 = compileNode(stripped);
            } catch (CompileException e) {
                throw new RuntimeException(e);
            }

            output.append(output1);
        }

        var path = Paths.get(".", "Main.mgs");
        writeString(path, output.toString()).match(unused -> null, e -> {
            e.printStackTrace();
            return null;
        });
    }

    private static String compileNode(String stripped) throws CompileException {
        try {
            return compileNodeExceptionally(stripped);
        } catch (CompileException e) {
            throw new CompileException("Failed to compile: " + stripped, e);
        }
    }

    private static String compileNodeExceptionally(String stripped) throws CompileException {
        if (stripped.startsWith("package ")) {
            return "";
        }

        if (stripped.startsWith("\"") && stripped.startsWith("\"")) {
            return stripped;
        }

        if (stripped.startsWith("else ")) {
            return compileElse(stripped);
        }

        var condition = compileIf(stripped);
        if (condition != null) return condition;

        if (stripped.startsWith("{") && stripped.endsWith("}")) {
            return compileBlock(stripped);
        }

        if (stripped.startsWith("import ")) {
            return compileImport(stripped);
        }

        var nameString = compileInterface(stripped);
        if (nameString != null) return nameString;

        var index = stripped.indexOf("record");
        var paramStart = stripped.indexOf('(');
        var braceStart = stripped.indexOf('{');
        if (index != -1 && index < paramStart && paramStart < braceStart) {
            var name = stripped.substring(index + "record".length(), paramStart).strip();
            var bodyString = stripped.substring(braceStart).strip();
            var s = compileNode(bodyString);
            return "class def " + name + "()" + s;
        }

        var valueString = compileReturn(stripped);
        if (valueString != null) return valueString;

        var condSlice = compileFor(stripped);
        if (condSlice != null) return condSlice;

        var keywords = compileClass(stripped);
        if (keywords != null) return keywords;

        var s = compileConstructor(stripped);
        if (s != null) return s;

        var name = compileAssignment(stripped);
        if (name != null) return name;

        var name1 = compileMethod(stripped);
        if (name1 != null) return name1;


        if (stripped.startsWith("try")) {
            var valueString2 = stripped.substring("try".length()).strip();
            return "try" + compileNode(valueString2);
        }

        var left1 = compileField(stripped);
        if (left1 != null) return left1;

        var stripped1 = compileInteger(stripped);
        if (stripped1 != null) return stripped1;

        var variable = compileVariable(stripped);
        if (variable != null) return stripped;

        var left = compileOperator(stripped);
        if (left != null) return left;

        if (stripped.startsWith("throw ")) {
            var valueString1 = stripped.substring("throw ".length()).strip();
            return "throw " + compileNode(valueString1) + ";";
        }

        var value = compileInvocation(stripped);
        if (value != null) return value;

        var inner = compileChar(stripped);
        if (inner != null) return inner;

        var operandString = compileUnaryOperator(stripped);
        if (operandString != null) return operandString;

        if (stripped.startsWith("@")) {
            return stripped;
        }

        throw new CompileException("Unknown node: " + stripped);
    }

    private static String compileInterface(String stripped) throws CompileException {
        var keywordIndex = stripped.indexOf("interface ");
        var braceStart = stripped.indexOf('{');
        if (keywordIndex != -1 && braceStart != -1 && keywordIndex < braceStart) {
            var nameString = stripped.substring(keywordIndex + "interface ".length(), braceStart).strip();
            var bodyString = stripped.substring(braceStart).strip();
            return "trait " + nameString + compileNode(bodyString);
        }
        return null;
    }

    private static String compileReturn(String stripped) throws CompileException {
        if (stripped.startsWith("return ")) {
            var valueString = stripped.substring("return ".length());
            var value = compileNode(valueString);
            return "return " + value + ";";
        }
        return null;
    }

    private static String compileUnaryOperator(String stripped) throws CompileException {
        var operators = Set.of("++", "--");
        for (var operator : operators) {
            if (stripped.endsWith(operator)) {
                var operandString = stripped.substring(0, stripped.length() - operator.length());
                return compileNode(operandString) + operator;
            }
        }
        return null;
    }

    private static String compileField(String stripped) throws CompileException {
        var separator = stripped.indexOf('.');
        if (separator != -1) {
            var leftString = stripped.substring(0, separator).strip();
            var left = compileNode(leftString);
            var right = stripped.substring(separator + 1).strip();
            return left + "." + right;
        }
        return null;
    }

    private static String compileInteger(String stripped) {
        try {
            Integer.parseInt(stripped);
            return stripped;
        } catch (NumberFormatException e) {
        }
        return null;
    }

    private static String compileConstructor(String stripped) throws CompileException {
        if (stripped.startsWith("new ")) {
            var endIndex = stripped.indexOf("(");
            if (endIndex == -1) {
                throw new CompileException("Not a constructor: " + stripped);
            }

            var substring = stripped.substring("new ".length(), endIndex).strip();
            var s = compileType(substring);
            return "new " + s;
        }
        return null;
    }

    private static String compileChar(String stripped) {
        if (stripped.startsWith("'") && stripped.endsWith("'")) {
            var inner = stripped.substring(1, stripped.length() - 1);
            return "'" + inner + "'";
        }
        return null;
    }

    private static String compileOperator(String stripped) throws CompileException {
        var operators = Set.of("&&", "==", "=", "+");
        for (String operator : operators) {
            var operatorIndex = stripped.indexOf(operator);
            if (operatorIndex != -1) {
                var leftString = stripped.substring(0, operatorIndex).strip();
                var left = compileNode(leftString);

                var rightString = stripped.substring(operatorIndex + operator.length()).strip();
                var right = compileNode(rightString);

                return left + " " + operator + " " + right;
            }
        }
        return null;
    }

    private static String compileVariable(String stripped) {
        for (int i = 0; i < stripped.length(); i++) {
            var ch = stripped.charAt(i);
            if (!Character.isLetter(ch) && (i == 0 || !Character.isDigit(ch))) {
                return null;
            }
        }
        return stripped;
    }

    private static String compileFor(String stripped) throws CompileException {
        if (stripped.startsWith("for")) {
            if (!stripped.endsWith("}")) {
                throw new CompileException("Malformed for loop: " + stripped);
            }

            var condStart = stripped.indexOf('(');
            var condEnd = stripped.indexOf(')');
            var condSlice = stripped.substring(condStart + 1, condEnd);
            var bodyStart = stripped.indexOf('{');
            var bodyEnd = stripped.lastIndexOf('}');
            var bodyString = stripped.substring(bodyStart, bodyEnd + 1);
            var body = compileNode(bodyString);
            return "for(" + condSlice + ")" + body;
        }
        return null;
    }

    private static String compileType(String input) {
        var genericStart = input.indexOf('<');
        var genericEnd = input.indexOf('>');

        if (genericStart != -1 && genericEnd != -1 && genericStart < genericEnd) {
            var nameString = input.substring(0, genericStart).strip();
            var typeString = input.substring(genericStart + 1, genericEnd).strip();
            return nameString + "[" + typeString + "]";
        }

        return input;
    }

    private static String compileInvocation(String stripped) throws CompileException {
        var argStart = stripped.indexOf('(');
        var argEnd = stripped.indexOf(')');
        if (argStart != -1 && argEnd != -1 && stripped.endsWith(")")) {
            var leftSlice = stripped.substring(0, argStart);
            var left = compileNode(leftSlice);

            var rightString = slice(stripped, argStart + 1, argEnd);
            var right = compileNode(rightString);

            return left + "(" + right + ")";
        }
        return null;
    }

    private static String compileClass(String stripped) throws CompileException {
        var classIndicator = stripped.indexOf("class ");
        if (classIndicator != -1) {
            var keywords = slice(stripped, classIndicator).strip();
            for (int i = 0; i < keywords.length(); i++) {
                if (!Character.isLetterOrDigit(keywords.charAt(i))) {
                    return null;
                }
            }

            var nameStart = classIndicator + "class ".length();

            var nameEnd = stripped.indexOf('{');

            String name;
            try {
                name = slice(stripped, nameStart, nameEnd).strip();
            } catch (Exception e) {
                var format = "%d %d: '%s'";
                var message = format.formatted(nameStart, nameEnd, stripped);
                throw new IndexOutOfBoundsException(message);
            }

            var block = slice(stripped, nameEnd, stripped.lastIndexOf('}') + 1);
            var blockOutput = compileNode(block);

            return keywords +
                   " object " +
                   name +
                   " " + blockOutput;
        }
        return null;
    }

    private static String compileMethod(String stripped) throws CompileException {
        var paramStart = stripped.indexOf('(');
        var paramEnd = stripped.indexOf(')');

        var bodyStart = stripped.indexOf('{');
        var bodyEnd = stripped.lastIndexOf('}');

        if (paramStart != -1 && paramEnd != -1 && paramStart < paramEnd) {
            var args1 = List.of(slice(stripped, paramStart).split(" "));
            if(args1.size() < 2) {
                return null;
            }

            if (args1.size() > 2) {
                if (!args1.subList(0, args1.size() - 2).stream()
                        .map(String::strip)
                        .filter(value -> !value.isEmpty())
                        .filter(value -> !value.startsWith("<") && !value.endsWith(">"))
                        .allMatch(Main::isSymbol)) {
                    return null;
                }
            }

            if (!isSymbol(args1.get(args1.size() - 1))) {
                return null;
            }

            var name1 = args1.get(args1.size() - 1);
            String body;
            if (bodyStart != -1 && bodyEnd != -1 && bodyStart < bodyEnd) {
                var bodyString = slice(stripped, bodyStart, bodyEnd + 1).strip();
                body = " => " + compileNode(bodyString);
            } else {
                body = ";";
            }

            return "public def " + name1 + "()" + body;
        }
        return null;
    }

    private static boolean isSymbol(String value) {
        for (int i = 0; i < value.length(); i++) {
            var ch = value.charAt(i);
            if (!Character.isDigit(ch) && !Character.isLetter(ch)) {
                return false;
            }
        }

        return true;
    }

    private static String compileAssignment(String stripped) throws CompileException {
        var equals = stripped.indexOf('=');
        if (equals != -1 && stripped.startsWith("var ")) {
            var left = stripped.substring(0, equals).strip();
            var space = left.indexOf(' ');
            var name = left.substring(space + 1);

            var rightString = stripped.substring(equals + 1).strip();
            var right = compileNode(rightString);
            return "let " + name + " = " + right + ";";
        }
        return null;
    }

    private static String compileIf(String stripped) throws CompileException {
        var condStart = stripped.indexOf('(');
        var condEnd = stripped.indexOf(')');

        var braceStart = stripped.indexOf('{');
        var braceEnd = stripped.lastIndexOf('}');

        var hasBraceStart = braceStart != -1 && condEnd < braceStart;
        var hasBraceEnd = braceEnd != -1 && condEnd < braceEnd;
        var hasBraces = hasBraceStart && hasBraceEnd;
        var hasNoBraces = !hasBraceStart && !hasBraceEnd;
        var validWrapper = hasBraces || hasNoBraces;

        if (stripped.startsWith("if")
            && condStart != -1 && condEnd != -1
            && validWrapper) {
            var conditionString = slice(stripped, condStart + 1, condEnd);
            var condition = compileNode(conditionString);

            var i = stripped.lastIndexOf("}");
            String bodyString;
            if (hasBraces) {
                bodyString = slice(stripped, braceStart, i + 1).strip();
            } else {
                bodyString = slice(stripped, condEnd + 1, stripped.length()).strip();
            }
            var body = compileNode(bodyString);

            return "if " + condition + " " + body;
        }
        return null;
    }

    private static String slice(String stripped, int start, int end) throws CompileException {
        try {
            return stripped.substring(start, end);
        } catch (Exception e) {
            throw new CompileException(start + " " + end + ": " + stripped);
        }
    }

    private static String slice(String stripped, int index) {
        try {
            return slice(stripped, 0, index);
        } catch (Exception e) {
            throw new IndexOutOfBoundsException(index + ": " + stripped);
        }
    }

    private static String compileImport(String stripped) {
        var name = stripped.substring("import ".length());
        var nameSegment = List.of(name.split("\\."));
        var joinedNames = String.join(".", nameSegment.subList(0, nameSegment.size() - 1));

        return "import " +
               nameSegment.get(nameSegment.size() - 1) +
               " from " +
               joinedNames +
               ";\n";
    }

    private static String compileBlock(String stripped) throws CompileException {
        var sliced = slice(stripped, 1, stripped.length() - 1);
        var lines = split(sliced);
        var s = "{\n" + lines
                .stream()
                .map(String::strip)
                .map(line -> {
                    try {
                        depth += 1;
                        var s1 = "\t".repeat(depth) + compileNode(line) + "\n";
                        depth -= 1;
                        return s1;
                    } catch (CompileException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.joining()) + "\t".repeat(depth) + "}";
        return s;
    }

    private static String compileElse(String stripped) throws CompileException {
        var withoutElse = stripped.substring("else ".length());
        var withoutElseRendered = compileNode(withoutElse);
        return "else " + withoutElseRendered;
    }

    private static Result<Void> writeString(Path path, String output) {
        try {
            Files.writeString(path, output);
            return Result.ok();
        } catch (IOException e) {
            return Result.err(e);
        }
    }

    private static Result<String> readStringImpl(Path path) {
        try {
            return Result.ok(Files.readString(path));
        } catch (IOException e) {
            return Result.err(e);
        }
    }

    private interface Result<T> {
        static <T> Result<T> err(IOException value) {
            return new Err<>(value);
        }

        static <T> Result<T> ok(T value) {
            return new Ok<>(value);
        }

        static Result<Void> ok() {
            return Result.ok(null);
        }

        T match(Function<T, T> onOk, Function<IOException, T> onErr);

        record Err<T>(IOException value) implements Result<T> {

            @Override
            public T match(Function<T, T> onOk, Function<IOException, T> onErr) {
                return onErr.apply(value);
            }
        }

        record Ok<T>(T value) implements Result<T> {
            @Override
            public T match(Function<T, T> onOk, Function<IOException, T> onErr) {
                return onOk.apply(value);
            }
        }
    }

    static class CompileException extends Exception {
        public CompileException(String message) {
            super(message);
        }

        public CompileException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
