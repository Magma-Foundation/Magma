package com.meti.compile;

import com.meti.api.JavaList;
import com.meti.api.Streams;
import com.meti.api.option.ErrorOption;
import com.meti.api.option.Option;
import com.meti.api.option.Options;
import com.meti.api.result.Err;
import com.meti.api.result.Ok;
import com.meti.api.result.Result;
import com.meti.compile.value.Operations;
import com.meti.node.Attribute;
import com.meti.node.IntAttribute;
import com.meti.node.StringAttribute;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.meti.api.result.Results.$Result;

public final class ValueCompiler {
    public final String input;
    public final int indent;

    private ValueCompiler(String input, int indent) {
        this.input = input;
        this.indent = indent;
    }

    private static Option<Result<JavaString, CompileException>> compileAccess(JavaString stripped, JavaList<JavaString> stack) {
        Optional<Result<String, CompileException>> result = Optional.empty();
        boolean finished = false;
        String stripped1 = stripped.value();
        List<String> stack1 = stack.stream()
                        .map(JavaString::value)
                        .collect(Collectors.toNativeList());
        var objectEnd = stripped1.lastIndexOf('.');
        if (objectEnd == -1) {
            result = Optional.empty();
        } else if (objectEnd == stripped1.length() - 1) {
            result = Optional.empty();
        } else {
            var objectString = stripped1.substring(0, objectEnd);
            int childStart = 0;
            if (stripped1.charAt(objectEnd + 1) != '<') childStart = objectEnd + 1;
            else {
                var newChildStart = stripped1.indexOf('>');
                if (newChildStart == -1) {
                    result = Optional.empty();
                    finished = true;
                } else {
                    childStart = newChildStart + 1;
                }
            }
            if (!finished) {
                var child = stripped1.substring(childStart);
                if (!Strings.isAssignable(child)) {
                    result = Optional.empty();
                } else {
                    try {
                        var compiledObject = compileRequired(createValueCompiler(objectString, 0), stack1,
                                objectString, 0);
                        result = Optional.of(new Ok<>(compiledObject + "." + child));
                    } catch (CompileException e) {
                        var format = "Failed to compile object reference of access statement - %s: %s";
                        var message = format.formatted(stack1, objectString);
                        result = Optional.of(new Err<>(new CompileException(message, e)));
                    }
                }
            }
        }

        return Options.fromNative(result)
                .into(ErrorOption::new)
                .mapValue(JavaString::new);
    }

    private static Optional<Result<String, CompileException>> compileString(String stripped) {
        return stripped.startsWith("\"") && stripped.endsWith("\"") ? Optional.of(new Ok<>(stripped)) : Optional.empty();
    }

    private static Result<Node, CompileException> compileOperationValues(Node node) {
        return $Result(() -> {
            var left = node.apply("left").flatMap(Attribute::asString).orElse(JavaString.EMPTY);
            var right = node.apply("right").flatMap(Attribute::asString).orElse(JavaString.EMPTY);

            var leftCompiled = compileNoIndent(left).$();
            var rightCompiled = compileNoIndent(right).$();

            return node.withString("left", leftCompiled).withString("right", rightCompiled);
        });
    }

    private static Result<JavaString, CompileException> compileNoIndent(JavaString left) {
        return $Result(() -> compile(left.value(), 0)).mapValue(JavaString::new);
    }

    private static String compile(String left, int indent) throws CompileException {
        return compileRequired(createValueCompiler(left, indent), Collections.emptyList(), left, indent);
    }

    public static ValueCompiler createValueCompiler(String input, int indent) {
        return new ValueCompiler(input, indent);
    }

    static Optional<Result<String, CompileException>> compile(
            ValueCompiler valueCompiler,
            List<String> stack,
            String input,
            int indent) {
        var stripped = input.strip();
        return compileString(stripped).or(() -> SymbolCompiler.compile(stack, stripped))
                .or(() -> valueCompiler.compileLambda(stripped, indent, stack))
                .or(() -> InvocationCompiler.compileInvocation(stack, stripped, indent))
                .or(() -> compileAccess(new JavaString(stripped), Streams.fromNativeList(stack)
                        .map(JavaString::new)
                        .collect(Collectors.toList()))
                        .into(ErrorOption::new)
                        .mapValue(JavaString::value)
                        .into(Options::toNative))
                .or(() -> valueCompiler.compileTernary(stripped))
                .or(() -> valueCompiler.compileNumbers(stripped))
                .or(() -> compileAnonymousClass(stripped))
                .or(() -> Options.toNative(compileOperations(new JavaString(stripped)))
                        .map(result -> result.mapValue(JavaString::value)))
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

    static String compileRequired(ValueCompiler valueCompiler, List<String> stack, String input, int indent) throws CompileException {
        return compile(valueCompiler, stack, input, indent).orElseGet(() -> new Err<>(new CompileException("Unknown value: " + input))).$();
    }

    private static Option<Result<JavaString, CompileException>> compileOperations(JavaString stripped) {
        return Operations.lex(stripped)
                .map(ValueCompiler::compileOperationValues)
                .into(ErrorOption::new)
                .mapValue(compiled -> Operations.render(compiled).orElse(JavaString.EMPTY));
    }

    private Optional<? extends Result<String, CompileException>> compileCast(String stripped) {
        if (stripped.startsWith("(")) {
            var end = stripped.indexOf(')');
            var type = stripped.substring(1, end);

            return Optional.of($Result(() -> {
                var outputType = TypeCompiler.compile(type).$();
                var valueString = stripped.substring(end + 1).strip();

                var compiledValue = compileNoIndent(new JavaString(valueString)).mapValue(JavaString::value).$();
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

            ValueCompiler valueCompiler = createValueCompiler(before, 0);
            return compile(valueCompiler, Collections.emptyList(), createValueCompiler(before, 0).input, valueCompiler.indent).map(value -> {
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
                compiledValue = compileRequired(createValueCompiler(value, indent), stack, createValueCompiler(value, indent).input, createValueCompiler(value, indent).indent);
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
                return Optional.of(new Ok<>(compileNoIndent(new JavaString(valueString)).mapValue(JavaString::value).$()));
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

    private Optional<? extends Result<String, CompileException>> compileTernary(String stripped) {
        var conditionMarker = stripped.indexOf('?');
        if (conditionMarker == -1) return Optional.empty();

        var statementMarker = stripped.indexOf(":", conditionMarker);
        if (statementMarker == -1) return Optional.empty();

        var conditionString = stripped.substring(0, conditionMarker).strip();
        ValueCompiler valueCompiler2 = createValueCompiler(conditionString, 0);
        var condition = compile(valueCompiler2, Collections.emptyList(), createValueCompiler(conditionString, 0).input, valueCompiler2.indent);
        if (condition.isEmpty()) return Optional.empty();

        var thenString = stripped.substring(conditionMarker + 1, statementMarker).strip();
        ValueCompiler valueCompiler1 = createValueCompiler(thenString, 0);
        var thenBlock = compile(valueCompiler1, Collections.emptyList(), createValueCompiler(thenString, 0).input, valueCompiler1.indent);
        if (thenBlock.isEmpty()) return Optional.empty();

        var elseString = stripped.substring(statementMarker + 1).strip();
        ValueCompiler valueCompiler = createValueCompiler(elseString, 0);
        var elseBlock = compile(valueCompiler, Collections.emptyList(), createValueCompiler(elseString, 0).input, valueCompiler.indent);
        return elseBlock.map(stringCompileExceptionResult -> $Result(() -> condition.get().$() + " ? " + thenBlock.get().$() + " : " + stringCompileExceptionResult.$()));
    }
}