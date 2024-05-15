package com.meti.compile;

import com.meti.api.ExceptionalStream;
import com.meti.api.Index;
import com.meti.api.JavaList;
import com.meti.api.Streams;
import com.meti.api.option.Option;
import com.meti.api.option.Options;
import com.meti.api.option.Some;
import com.meti.api.result.Err;
import com.meti.api.result.Ok;
import com.meti.api.result.Result;
import com.meti.node.Attribute;
import com.meti.node.IntAttribute;
import com.meti.node.StringAttribute;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.meti.api.result.Results.$Result;
import static com.meti.compile.MagmaLang.renderFunction;
import static com.meti.compile.MagmaLang.renderFunctionDeclaration;

public final class MethodCompiler {
    static Result<String, CompileException> compileMethodMembers(List<String> inputContent, int indent, List<String> stack) {
        return Streams.fromNativeList(inputContent)
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

    static Optional<Result<ClassMemberResult, CompileException>> compile(JavaString input, JavaList<JavaString> stack) {
        var stripped = input.strip();

        var paramStart = stripped.firstIndexOfChar('(')
                .map(Index::value)
                .orElse(-1);

        if (paramStart == -1) return Optional.empty();

        var paramEnd = stripped.firstIndexOfChar(')')
                .map(Index::value)
                .orElse(-1);

        if (paramEnd == -1) return Optional.empty();

        var before = stripped.unwrap().substring(0, paramStart).strip();
        var paramString = stripped.unwrap().substring(paramStart + 1, paramEnd);

        String renderedParams;
        try {
            ParamsCompiler paramsCompiler = new ParamsCompiler(paramString, stack.stream()
                    .map(JavaString::unwrap)
                    .collect(Collectors.toNativeList()));
            renderedParams = paramsCompiler.compile().$();
        } catch (CompileException e) {
            return Optional.of(new Err<>(new CompileException("Failed to compile parameters: " + paramString, e)));
        }

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

        Option<Result<String, CompileException>> result1;
        Node node1 = new Node(node);
        try {
            var node11 = node1.withString("type", ": " + TypeCompiler.compile(inputType).$());
            result1 = attachValue(stack.stream()
                            .map(JavaString::unwrap)
                            .collect(Collectors.toNativeList()),
                    new com.meti.java.JavaString(stripped.unwrap()), node11);

        } catch (CompileException e) {
            result1 = new Some<>(new Err<>(e));

        }
        return Options.toNative(result1)
                .map(result -> result.mapErr(err -> new CompileException("Failed to compile method - " + stack.stream()
                        .map(JavaString::unwrap)
                        .collect(Collectors.toNativeList()) + ": " + input.unwrap(), err)))
                .map(value -> value.mapValue(inner -> handleStatic(modifiers, inner)));
    }

    private static Option<Result<String, CompileException>> attachValue(List<String> stack, com.meti.java.JavaString javaString, Node node) {
        return javaString.firstIndexOfChar('{').xnor(javaString.lastIndexOfChar('}'), (first, second) -> $Result(() -> {
            var content = javaString.input().substring(first + 1, second);
            var inputContent = Strings.splitMembers(content);
            var outputContent = compileMethodMembers(inputContent, 2, stack).$();
            var withContent = node.withString("content", "{\n" + outputContent + "\t}");

            return renderFunction(withContent);
        }), () -> new Ok<>(renderFunctionDeclaration(node) + ";" + "\n"));
    }

    private static ClassMemberResult handleStatic(List<String> modifiers, String rendered) {
        return modifiers.contains("static") ? new ClassMemberResult(Collections.emptyList(), Collections.singletonList(rendered)) : new ClassMemberResult(Collections.singletonList(rendered), Collections.emptyList());
    }
}