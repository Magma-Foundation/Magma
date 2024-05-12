package com.meti.compile;

import com.meti.result.Err;
import com.meti.result.Ok;
import com.meti.result.Result;
import com.meti.result.Results;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record ClassCompiler(String input) implements Compiler {

    private static Result<ClassMemberResult, CompileException> compileClassMembers(List<String> inputContent) {
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

    @Override
    public Optional<Result<String, CompileException>> compile() {
        var classIndex = input().indexOf("class");
        if (classIndex == -1) return Optional.empty();

        var classEnd = classIndex + "class".length();

        var contentStart = input().indexOf('{', classEnd);
        if (contentStart == -1) return Optional.empty();

        var name = input.substring(classEnd, contentStart).strip();
        var modifierString = input().startsWith("public ") ? "export " : "";

        var contentEnd = input().lastIndexOf('}');
        if (contentEnd == -1) return Optional.empty();

        var content = input().substring(contentStart + 1, contentEnd).strip();
        var inputContent = Strings.splitMembers(content);

        return compileClassMembers(inputContent)
                .mapErr(err -> new CompileException("Failed to compile class body: " + input(), err))
                .mapValue(output -> Optional.of(MagmaLang.renderClass(modifierString, name, output)))
                .into(Results::unwrapOptional);

    }
}