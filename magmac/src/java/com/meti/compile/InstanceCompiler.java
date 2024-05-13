package com.meti.compile;

import com.meti.result.Err;
import com.meti.result.Ok;
import com.meti.result.Result;
import com.meti.result.Results;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class InstanceCompiler implements Compiler {
    protected final String input;

    public InstanceCompiler(String input) {
        this.input = input;
    }

    static Result<ClassMemberResult, CompileException> compileClassMembers(List<String> inputContent) {
        var instanceContent = new ArrayList<String>();
        var staticContent = new ArrayList<String>();

        for (var input : inputContent) {
            if (input.isBlank()) continue;

            try {
                var result = compileClassMember(input).$();
                instanceContent.addAll(result.instanceMembers());
                staticContent.addAll(result.staticMembers());
            } catch (CompileException e) {
                return new Err<>(e);
            }
        }

        return new Ok<>(new ClassMemberResult(instanceContent, staticContent));
    }

    private static Result<ClassMemberResult, CompileException> compileClassMember(String input) {
        return MethodCompiler
                .compileMethod(new MethodCompiler(input))
                .orElseGet(() -> new Err<>(new CompileException("Unknown class member: " + input)));
    }

    protected abstract String computeKeyword();

    @Override
    public Optional<Result<String, CompileException>> compile() {
        var classIndex = input.indexOf(computeKeyword());
        if (classIndex == -1) return Optional.empty();

        var classEnd = classIndex + computeKeyword().length();

        var contentStart = input.indexOf('{', classEnd);
        if (contentStart == -1) return Optional.empty();

        var name = input.substring(classEnd, computeContentStart(input, contentStart)).strip();
        var modifierString = input.startsWith("public ") ? "export " : "";

        var contentEnd = input.lastIndexOf('}');
        if (contentEnd == -1) return Optional.empty();

        var content = input.substring(contentStart + 1, contentEnd).strip();
        var inputContent = Strings.splitMembers(content);

        var paramString = computeParamString(input);
        if (paramString.isEmpty()) return Optional.empty();

        return compileClassMembers(inputContent)
                .mapErr(err -> {
                    var format = "Failed to compile %s body: %s";
                    var message = format.formatted(computeKeyword(), input);
                    return new CompileException(message, err);
                })
                .mapValue(output -> Optional.of(MagmaLang.renderClass(modifierString, name, output, paramString.get())))
                .into(Results::unwrapOptional);

    }

    protected abstract int computeContentStart(String input, int contentStart);

    protected abstract Optional<String> computeParamString(String input);
}
