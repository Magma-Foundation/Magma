package com.meti.compile;

import com.meti.api.result.Err;
import com.meti.api.result.Ok;
import com.meti.api.result.Result;
import com.meti.node.IntAttribute;
import com.meti.node.StringAttribute;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static com.meti.api.result.Results.$Result;

public final class ValueCompiler {
    private final String input;
    private final int indent;

    private ValueCompiler(String input, int indent) {
        this.input = input;
        this.indent = indent;
    }

    private static Optional<Result<String, CompileException>> compileAccess(String stripped, List<String> stack) {
        var objectEnd = stripped.lastIndexOf('.');
        if (objectEnd == -1) return Optional.empty();

        if (objectEnd == stripped.length() - 1) return Optional.empty();

        var objectString = stripped.substring(0, objectEnd);

        int childStart;
        if (stripped.charAt(objectEnd + 1) != '<') childStart = objectEnd + 1;
        else {
            var newChildStart = stripped.indexOf('>');
            if (newChildStart == -1) {
                return Optional.empty();
            } else {
                childStart = newChildStart + 1;
            }
        }

        var child = stripped.substring(childStart);

        if (!Strings.isAssignable(child)) {
            return Optional.empty();
        }

        try {
            var compiledObject = ValueCompiler.compileRequired(createValueCompiler(objectString, 0), stack);
            return Optional.of(new Ok<>(compiledObject + "." + child));
        } catch (CompileException e) {
            var format = "Failed to compile object reference of access statement - %s: %s";
            var message = format.formatted(stack, objectString);
            return Optional.of(new Err<>(new CompileException(message, e)));
        }
    }

    private static Optional<Result<String, CompileException>> compileString(String stripped) {
        return stripped.startsWith("\"") && stripped.endsWith("\"") ? Optional.of(new Ok<>(stripped)) : Optional.empty();
    }

    private static Optional<Result<String, CompileException>> compileOperation(String stripped, String operator) {
        var operatorIndex = stripped.indexOf(operator);
        if (operatorIndex == -1) return Optional.empty();

        var left = stripped.substring(0, operatorIndex).strip();
        var right = stripped.substring(operatorIndex + operator.length());

        return Optional.of($Result(() -> {
            var leftCompiled = getLeftCompiled(left);
            var rightCompiled = getLeftCompiled(right);

            return leftCompiled + " " + operator + " " + rightCompiled;
        }).mapErr(err -> new CompileException("Failed to compile operation '" + operator + "': " + stripped, err)));
    }

    private static String getLeftCompiled(String left) throws CompileException {
        return ValueCompiler.compileRequired(createValueCompiler(left, 0), Collections.emptyList());
    }

    public static ValueCompiler createValueCompiler(String input, int indent) {
        return new ValueCompiler(input, indent);
    }

    static Optional<Result<String, CompileException>> compile(List<String> stack, String argString, int indent) {
        return compile(createValueCompiler(argString, indent), stack);
    }

    static Optional<Result<String, CompileException>> compile(ValueCompiler valueCompiler, List<String> stack) {
        var stripped = valueCompiler.input().strip();
        return compileString(stripped).or(() -> SymbolCompiler.compile(stack, stripped))
                .or(() -> valueCompiler.compileLambda(stripped, valueCompiler.indent, stack))
                .or(() -> InvocationCompiler.compileInvocation(stack, stripped, valueCompiler.indent))
                .or(() -> compileAccess(stripped, stack))
                .or(() -> valueCompiler.compileTernary(stripped))
                .or(() -> valueCompiler.compileNumbers(stripped))
                .or(() -> compileAnonymousClass(stripped))
                .or(() -> valueCompiler.compileOperation(stripped))
                .or(() -> valueCompiler.compileChar(stripped))
                .or(() -> valueCompiler.compileNot(stripped))
                .or(() -> valueCompiler.compileMethodReference(stripped))
                .or(() -> valueCompiler.compileCast(stripped));
    }

    private static Optional<? extends Result<String, CompileException>> compileAnonymousClass(String stripped) {
        if (!stripped.startsWith("new ")) return Optional.empty();

        var paramStart = stripped.indexOf('(');
        if (paramStart == -1) return Optional.empty();

        var paramEnd = stripped.indexOf(')');
        if (paramEnd == -1) return Optional.empty();

        var contentStart = stripped.indexOf('{');
        if (contentStart == -1) return Optional.empty();

        var contentEnd = stripped.lastIndexOf('}');
        if (contentEnd == -1) return Optional.empty();

        var name = stripped.substring("new ".length(), paramStart);
        return Optional.of(new Ok<>("<" + name + ">{}"));
    }

    static String compileRequired(ValueCompiler valueCompiler, List<String> stack) throws CompileException {
        return compile(valueCompiler, stack).orElseGet(() -> new Err<>(new CompileException("Unknown value: " + valueCompiler.input))).$();
    }

    private Optional<? extends Result<String, CompileException>> compileCast(String stripped) {
        if (stripped.startsWith("(")) {
            var end = stripped.indexOf(')');
            var type = stripped.substring(1, end);

            return Optional.of($Result(() -> {
                var outputType = TypeCompiler.compile(type).$();
                var valueString = stripped.substring(end + 1).strip();

                var compiledValue = getLeftCompiled(valueString);
                return "(" + outputType + ") " + compiledValue;
            }));
        } else {
            return Optional.empty();
        }
    }

    private Optional<Result<String, CompileException>> compileMethodReference(String stripped) {
        var index = stripped.indexOf("::");
        if (index != -1) {
            var before = stripped.substring(0, index);
            var after = stripped.substring(index + "::".length());
            if (!Strings.isSymbol(after)) return Optional.empty();

            return ValueCompiler.compile(createValueCompiler(before, 0), Collections.emptyList()).map(value -> {
                return value.mapValue(inner -> {
                    return inner + "." + after;
                });
            });
        }

        return Optional.empty();
    }

    private Optional<? extends Result<String, CompileException>> compileLambda(String stripped, int indent, List<String> stack) {
        var separator = stripped.indexOf("->");
        if (separator == -1) return Optional.empty();

        var before = stripped.substring(0, separator).strip();
        var paramStart = before.indexOf('(');
        var paramEnd = before.lastIndexOf(')');

        var params = new ArrayList<String>();
        if (paramStart == 0 && paramEnd == before.length() - 1) {
            /*
            TODO: Pull params
             */
        } else if (paramStart == -1 && paramEnd == -1) {
            if (Strings.isSymbol(before)) {
                params.add(before);
            } else {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }

        var value = stripped.substring(separator + "->".length()).strip();
        try {
            String compiledValue;
            if (value.startsWith("{") && value.endsWith("}")) {
                var inputContent = value.substring(1, value.length() - 1).strip();
                var members = Strings.splitMembers(inputContent);
                compiledValue = "{\n" + MethodCompiler.compileMethodMembers(members, indent, stack).$() + "}";
            } else {
                compiledValue = ValueCompiler.compileRequired(createValueCompiler(value, indent), stack);
            }
            var rendered = MagmaLang.renderFunctionDeclaration(Map.of(
                    "indent", new IntAttribute(0),
                    "modifiers", new StringAttribute(""),
                    "name", new StringAttribute(""),
                    "params", new StringAttribute(""),
                    "type", new StringAttribute("")
            )) + " => " + compiledValue;
            return Optional.of(new Ok<>(rendered));
        } catch (CompileException e) {
            return Optional.of(new Err<>(new CompileException("Failed to compile lambda: " + stripped, e)));
        }
    }

    private Optional<? extends Result<String, CompileException>> compileNot(String stripped) {
        if (stripped.startsWith("!")) {
            var valueString = stripped.substring(1).strip();
            try {
                return Optional.of(new Ok<>(getLeftCompiled(valueString)));
            } catch (CompileException e) {
                return Optional.of(new Err<>(e));
            }
        } else {
            return Optional.empty();
        }
    }

    private Optional<? extends Result<String, CompileException>> compileChar(String stripped) {
        if (stripped.startsWith("'") && stripped.endsWith("'")) {
            return Optional.of(new Ok<>(stripped));
        } else {
            return Optional.empty();
        }
    }

    private Optional<? extends Result<String, CompileException>> compileNumbers(String stripped) {
        try {
            Integer.parseInt(stripped);
            return Optional.of(new Ok<>(stripped));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    private Optional<? extends Result<String, CompileException>> compileOperation(String stripped) {
        return Stream.of("&&", "==", "!=", "+", "||", "-", "<=", "<")
                .map(operator -> compileOperation(stripped, operator))
                .flatMap(Optional::stream)
                .findFirst();
    }

    private Optional<? extends Result<String, CompileException>> compileTernary(String stripped) {
        var conditionMarker = stripped.indexOf('?');
        if (conditionMarker == -1) return Optional.empty();

        var statementMarker = stripped.indexOf(":", conditionMarker);
        if (statementMarker == -1) return Optional.empty();

        var conditionString = stripped.substring(0, conditionMarker).strip();
        var condition = ValueCompiler.compile(createValueCompiler(conditionString, 0), Collections.emptyList());
        if (condition.isEmpty()) return Optional.empty();

        var thenString = stripped.substring(conditionMarker + 1, statementMarker).strip();
        var thenBlock = ValueCompiler.compile(createValueCompiler(thenString, 0), Collections.emptyList());
        if (thenBlock.isEmpty()) return Optional.empty();

        var elseString = stripped.substring(statementMarker + 1).strip();
        var elseBlock = ValueCompiler.compile(createValueCompiler(elseString, 0), Collections.emptyList());
        if (elseBlock.isEmpty()) return Optional.empty();

        return Optional.of($Result(() -> {
            return condition.get().$() + " ? " + thenBlock.get().$() + " : " + elseBlock.get().$();
        }));
    }

    public String input() {
        return input;
    }

    public int indent() {
        return indent;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ValueCompiler) obj;
        return Objects.equals(this.input, that.input) &&
               this.indent == that.indent;
    }

    @Override
    public int hashCode() {
        return Objects.hash(input, indent);
    }

    @Override
    public String toString() {
        return "ValueCompiler[" +
               "input=" + input + ", " +
               "indent=" + indent + ']';
    }

}