package com.meti.compile;

import com.meti.ExceptionalStream;
import com.meti.Streams;
import com.meti.node.Attribute;
import com.meti.node.IntAttribute;
import com.meti.node.StringAttribute;
import com.meti.result.Err;
import com.meti.result.Ok;
import com.meti.result.Result;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.meti.compile.MagmaLang.renderFunction;
import static com.meti.compile.MagmaLang.renderFunctionDeclaration;
import static com.meti.result.Results.$Result;

public final class MethodCompiler {
    static Result<String, CompileException> compileMethodMembers(List<String> inputContent, int indent, List<String> stack) {
        return Streams.fromList(inputContent)
                .filter(member -> !member.isBlank())
                .map(member -> StatementCompiler.compile(indent, stack, member).mapErr(err -> {
                    var format = "Failed to compile method member - %s: %s";
                    var message = format.formatted(stack, member);
                    return new CompileException(message, err);
                }))
                .into(ExceptionalStream::new)
                .collectExceptionally(Collectors.joining())
                .mapValue(value -> value.orElse(""));
    }

    static Optional<Result<ClassMemberResult, CompileException>> compile(String input, List<String> stack) {
        return compile1(input, stack);
    }

    private static Optional<Result<ClassMemberResult, CompileException>> compile1(String input, List<String> stack) {
        var stripped = input.strip();

        var paramStart = stripped.indexOf('(');
        if (paramStart == -1) return Optional.empty();

        var paramEnd = stripped.indexOf(')');
        if (paramEnd == -1) return Optional.empty();

        var paramString = stripped.substring(paramStart + 1, paramEnd);
        String renderedParams;
        try {
            renderedParams = new ParamsCompiler(paramString, stack).compile();
        } catch (CompileException e) {
            return Optional.of(new Err<>(new CompileException("Failed to compile parameters: " + paramString, e)));
        }

        var before = stripped.substring(0, paramStart).strip();
        var separator = before.lastIndexOf(' ');

        var modifiersAndTypeString = before.substring(0, separator).strip();
        var name = before.substring(separator + 1).strip();

        var modifiersAndType = Strings.splitTypeString(modifiersAndTypeString);
        if (modifiersAndType.isEmpty()) return Optional.empty();

        var modifiers = modifiersAndType.subList(0, modifiersAndType.size() - 1);
        var inputType = modifiersAndType.get(modifiersAndType.size() - 1);

        var modifierString = modifiers.contains("private") ? "private " : "";

        var node = Map.<String, Attribute>of(
                "indent", new IntAttribute(1),
                "modifiers", new StringAttribute(modifierString + "def "),
                "name", new StringAttribute(name),
                "params", new StringAttribute(renderedParams));

        Optional<Result<String, CompileException>> result1;
        Node node1 = new Node(node);
        try {
            var node11 = node1.withString("type", ": " + new TypeCompiler(inputType).compile().$());
            result1 = attachValue(stack, stripped, node11);
        } catch (CompileException e) {
            result1 = Optional.of(new Err<>(e));
        }
        return result1
                .map(result -> result.mapErr(err -> new CompileException("Failed to compile method - " + stack + ": " + input, err)))
                .map(value -> value.mapValue(inner -> handleStatic(modifiers, inner)));
    }

    private static Optional<Result<String, CompileException>> attachValue(List<String> stack, String input, Node node) {
        var contentStart = input.indexOf("{");
        var contentEnd = input.lastIndexOf('}');
        if (contentStart != -1 && contentEnd != -1) {
            return Optional.of($Result(() -> {
                var content = input.substring(contentStart + 1, contentEnd);
                var inputContent = Strings.splitMembers(content);
                var outputContent = compileMethodMembers(inputContent, 2, stack).$();
                var withContent = node.withString("content", "{\n" + outputContent + "\t}");

                return renderFunction(withContent);
            }));
        } else if (contentStart == -1 && contentEnd == -1) {
            return Optional.of(new Ok<>(renderFunctionDeclaration(node) + ";" + "\n"));
        } else {
            return Optional.empty();
        }
    }

    private static ClassMemberResult handleStatic(List<String> modifiers, String rendered) {
        return modifiers.contains("static") ? new ClassMemberResult(Collections.emptyList(), Collections.singletonList(rendered)) : new ClassMemberResult(Collections.singletonList(rendered), Collections.emptyList());
    }
}