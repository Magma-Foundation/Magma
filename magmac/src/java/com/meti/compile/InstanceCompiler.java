package com.meti.compile;

import com.meti.api.Streams;
import com.meti.api.result.Err;
import com.meti.api.result.Ok;
import com.meti.api.result.Result;
import com.meti.api.result.Results;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.meti.compile.MagmaLang.renderClass;

public abstract class InstanceCompiler implements RootCompiler {
    protected final String input;

    public InstanceCompiler(String input) {
        this.input = input;
    }

    static Result<ClassMemberResult, CompileException> compileClassMembers(List<String> inputContent, List<String> stack) {
        var instanceContent = new ArrayList<String>();
        var staticContent = new ArrayList<String>();

        for (var input : inputContent) {
            if (input.isBlank()) continue;

            try {
                var result = compileClassMember(input, stack).$();
                instanceContent.addAll(result.instanceMembers());
                staticContent.addAll(result.staticMembers());
            } catch (CompileException e) {
                return new Err<>(e);
            }
        }

        return new Ok<>(new ClassMemberResult(instanceContent, staticContent));
    }

    private static Result<ClassMemberResult, CompileException> compileClassMember(String input, List<String> stack) {
        JavaString javaString = new JavaString(input);
        return MethodCompiler.compile(javaString, Streams.fromNativeList(stack)
                        .map(JavaString::new)
                        .collect(Collectors.toList()))
                .or(() -> {
                    return DeclarationCompiler.compile(stack, input, 0).map(result -> {
                        return result.mapValue(value -> {
                            return new ClassMemberResult(List.of(value), stack);
                        });
                    });
                })
                .orElseGet(() -> new Err<>(new CompileException("Unknown class member: " + input)));
    }

    protected abstract String computeKeyword();

    @Override
    public Optional<Result<String, CompileException>> compile(List<String> stack) {
        var classIndex = input.indexOf(computeKeyword());
        if (classIndex == -1) return Optional.empty();

        var classEnd = classIndex + computeKeyword().length();

        var contentStart = input.indexOf('{', classEnd);
        if (contentStart == -1) return Optional.empty();

        var name = input.substring(classEnd, computeNameEnd(input, contentStart)).strip();
        var modifierString = input.startsWith("public ") ? "export " : "";

        var contentEnd = input.lastIndexOf('}');
        if (contentEnd == -1) return Optional.empty();

        var content = input.substring(contentStart + 1, contentEnd).strip();
        var inputContent = Strings.splitMembers(content);

        Optional<String> paramString;
        try {
            paramString = computeParamString(input, stack);
        } catch (CompileException e) {
            return Optional.of(new Err<>(e));
        }

        if (paramString.isEmpty()) return Optional.empty();

        return compileClassMembers(inputContent, stack).mapErr(err -> {
            var format = "Failed to compile %s body - %s: %s";
            var message = format.formatted(computeKeyword(), stack, input);
            return new CompileException(message, err);
        }).mapValue(output -> Optional.of(renderClass(modifierString, name, output , paramString.get()))).into(Results::unwrapOptional);

    }

    protected abstract int computeNameEnd(String input, int contentStart);

    protected abstract Optional<String> computeParamString(String input, List<String> stack) throws CompileException;
}