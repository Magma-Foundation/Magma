package com.meti.compile;

import com.meti.result.Err;
import com.meti.result.Ok;
import com.meti.result.Result;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.meti.compile.MagmaLang.renderDefinedFunction;
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

        var contentStart = stripped.indexOf("{");
        var contentEnd = stripped.lastIndexOf('}');

        String finalRenderedParams = renderedParams;
        if (contentStart != -1 && contentEnd != -1) {
            var content = stripped.substring(contentStart + 1, contentEnd);
            var inputContent = Strings.splitMembers(content);

            return Optional.of($Result(() -> {
                var outputType = new TypeCompiler(inputType).compile().$();
                var outputContent = compileMethodMembers(inputContent, 2, stack).$();
                var rendered = renderDefinedFunction(1, modifierString, name, finalRenderedParams, ": " + outputType, "{\n" + outputContent + "\t}");

                return modifiers.contains("static")
                        ? new ClassMemberResult(Collections.emptyList(), Collections.singletonList(rendered))
                        : new ClassMemberResult(Collections.singletonList(rendered), Collections.emptyList());
            })).map(result -> result.mapErr(err -> {
                return new CompileException("Failed to compile method - " + stack + ": " + input, err);
            }));
        } else if (contentStart == -1 && contentEnd == -1) {
            return Optional.of($Result(() -> {
                var outputType = new TypeCompiler(inputType).compile().$();
                var rendered = MagmaLang.renderFunction(1, modifierString +
                                                           "def ", name, finalRenderedParams, ": " + outputType, ";");

                return modifiers.contains("static")
                        ? new ClassMemberResult(Collections.emptyList(), Collections.singletonList(rendered))
                        : new ClassMemberResult(Collections.singletonList(rendered), Collections.emptyList());
            }));
        }

        return Optional.empty();
    }
}