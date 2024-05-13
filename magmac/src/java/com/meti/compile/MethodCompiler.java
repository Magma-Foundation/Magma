package com.meti.compile;

import com.meti.node.Attribute;
import com.meti.node.IntAttribute;
import com.meti.node.StringAttribute;
import com.meti.result.Err;
import com.meti.result.Ok;
import com.meti.result.Result;
import com.meti.result.Results;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.meti.compile.MagmaLang.renderFunction;
import static com.meti.result.Results.$Result;

public final class MethodCompiler {
    static Result<String, CompileException> compileMethodMembers(List<String> inputContent, int indent, List<String> stack) {
        var outputContent = new StringBuilder();
        for (String inputMember : inputContent) {
            if (inputMember.isBlank()) continue;

            try {
                outputContent.append(new StatementCompiler(inputMember, indent).compile(stack));
            } catch (CompileException e) {
                var format = "Failed to compile method member - %s: %s";
                var message = format.formatted(stack, inputMember);
                return new Err<>(new CompileException(message, e));
            }
        }

        return new Ok<>(outputContent.toString());
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

        return attachValue(stack, node, inputType, stripped)
                .into(Results::unwrapOptional)
                .map(result -> result.mapErr(err -> new CompileException("Failed to compile method - " + stack + ": " + input, err)))
                .map(value -> value.mapValue(inner -> handleStatic(modifiers, inner)));
    }

    private static Result<Optional<String>, CompileException> attachValue(List<String> stack, Map<String, Attribute> node, String inputType, String input) {
        return $Result(() -> {
            var copy = new HashMap<>(node);
            copy.put("type", new StringAttribute(": " + new TypeCompiler(inputType).compile().$()));

            var contentStart = input.indexOf("{");
            var contentEnd = input.lastIndexOf('}');
            if (contentStart != -1 && contentEnd != -1) {
                var content = input.substring(contentStart + 1, contentEnd);
                var inputContent = Strings.splitMembers(content);
                var outputContent = compileMethodMembers(inputContent, 2, stack).$();
                copy.put("content", new StringAttribute("{\n" + outputContent + "\t}"));

                return Optional.of(renderFunction(copy));
            } else if (contentStart == -1 && contentEnd == -1) {
                return Optional.of(MagmaLang.renderFunctionDeclaration(copy) + ";" + "\n");
            } else {
                return Optional.empty();
            }
        });
    }

    private static ClassMemberResult handleStatic(List<String> modifiers, String rendered) {
        return modifiers.contains("static") ? new ClassMemberResult(Collections.emptyList(), Collections.singletonList(rendered)) : new ClassMemberResult(Collections.singletonList(rendered), Collections.emptyList());
    }
}