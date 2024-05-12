package com.meti.compile;

import com.meti.result.Err;
import com.meti.result.Ok;
import com.meti.result.Result;
import com.meti.result.Results;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.meti.compile.MagmaLang.renderFunction;

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
        return compileMethod(input).orElseGet(() -> new Err<>(new CompileException("Unknown class member: " + input)));
    }

    private static Optional<Result<ClassMemberResult, CompileException>> compileMethod(String input) {
        var stripped = input.strip();

        var paramStart = stripped.indexOf('(');
        if (paramStart == -1) return Optional.empty();

        var paramEnd = stripped.indexOf(')');
        if (paramEnd == -1) return Optional.empty();

        var paramString = stripped.substring(paramStart + 1, paramEnd);
        var paramStrings = List.of(paramString.split(","));
        var renderedParams = compileMethodParams(paramStrings);

        var before = stripped.substring(0, paramStart).strip();
        var separator = before.lastIndexOf(' ');

        var modifiersAndTypeString = before.substring(0, separator).strip();
        var name = before.substring(separator + 1).strip();

        var modifiersAndType = Strings.splitTypeString(modifiersAndTypeString);
        if (modifiersAndType.isEmpty()) return Optional.empty();

        var modifiers = modifiersAndType.subList(0, modifiersAndType.size() - 1);
        var type = modifiersAndType.get(modifiersAndType.size() - 1);

        var modifierString = modifiers.contains("private") ? "private " : "";

        var contentStart = stripped.indexOf("{");
        if (contentStart == -1) return Optional.empty();

        var contentEnd = stripped.lastIndexOf('}');
        if (contentEnd == -1) return Optional.empty();

        var content = stripped.substring(contentStart + 1, contentEnd);
        var inputContent = Strings.splitMembers(content);
        return Optional.of(compileMethodMembers(inputContent).mapValue(outputContent -> {
            var rendered = renderFunction(modifierString, name, renderedParams, ": " + type, 1, outputContent);

            return modifiers.contains("static")
                    ? new ClassMemberResult(Collections.emptyList(), Collections.singletonList(rendered))
                    : new ClassMemberResult(Collections.singletonList(rendered), Collections.emptyList());
        }));
    }

    private static Result<String, CompileException> compileMethodMembers(List<String> inputContent) {
        var outputContent = new StringBuilder();
        for (String inputMember : inputContent) {
            try {
                outputContent.append(compileStatement(inputMember));
            } catch (CompileException e) {
                return new Err<>(e);
            }
        }

        return new Ok<>(outputContent.toString());
    }

    private static String compileStatement(String input) throws CompileException {
        var stripped = input.strip();

        return compileTry(stripped)
                .or(() -> compileInvocation(stripped))
                .orElseGet(() -> new Err<>(new CompileException("Unknown statement: " + stripped)))
                .$();
    }

    private static Optional<Result<String, CompileException>> compileInvocation(String stripped) {
        var start = stripped.indexOf('(');
        if (start == -1) return Optional.empty();

        var end = stripped.lastIndexOf(')');
        if (end == -1) return Optional.empty();

        var caller = stripped.substring(0, start);
        var inputArguments = stripped.substring(start + 1, end).split(",");
        var outputArguments = new StringBuilder();
        for (String inputArgument : inputArguments) {
            try {
                outputArguments.append(compileValue(inputArgument));
            } catch (CompileException e) {
                return Optional.of(new Err<>(e));
            }
        }

        try {
            return Optional.of(new Ok<>(compileValue(caller) + "(" + outputArguments + ")"));
        } catch (CompileException e) {
            return Optional.of(new Err<>(e));
        }
    }

    private static String compileValue(String input) throws CompileException {
        var stripped = input.strip();

        return compileString(stripped)
                .or(() -> compileAccess(stripped))
                .orElseGet(() -> new Err<>(new CompileException("Unknown value: " + stripped)))
                .$();
    }

    private static Optional<Result<String, CompileException>> compileAccess(String stripped) {
        var separator = stripped.indexOf('.');
        if (separator == -1) return Optional.empty();

        var parent = stripped.substring(0, separator);
        var child = stripped.substring(separator + 1);

        String compiledParent;
        try {
            compiledParent = compileValue(parent);
        } catch (CompileException e) {
            return Optional.of(new Err<>(e));
        }

        return Optional.of(new Ok<>(compiledParent + "." + child));
    }

    private static Optional<Result<String, CompileException>> compileString(String stripped) {
        return stripped.startsWith("\"") && stripped.endsWith("\"")
                ? Optional.of(new Ok<>(stripped))
                : Optional.empty();
    }

    private static Optional<Result<String, CompileException>> compileTry(String stripped) throws CompileException {
        if (!stripped.startsWith("try ")) return Optional.empty();

        var contentStart = stripped.indexOf('{');
        var contentEnd = stripped.lastIndexOf('}');
        var after = stripped.substring(contentStart + 1, contentEnd);

        var inputStatements = Strings.splitMembers(after);
        var output = new StringBuilder();
        for (String inputStatement : inputStatements) {
            output.append(compileStatement(inputStatement));
        }

        return Optional.of(new Ok<>("try {" + output + "}"));
    }

    private static String compileMethodParams(List<String> paramStrings) {
        var outputParams = Optional.<StringBuilder>empty();
        for (String string : paramStrings) {
            if (string.isBlank()) continue;

            var strippedParam = string.strip();
            var separator = strippedParam.lastIndexOf(' ');
            var type = strippedParam.substring(0, separator);
            var name = strippedParam.substring(separator + 1);

            var next = name + " : " + type;
            outputParams = Optional.of(outputParams.map(value -> value.append(", ").append(next))
                    .orElse(new StringBuilder(next)));
        }

        return outputParams.orElse(new StringBuilder()).toString();
    }

    @Override
    public Optional<Result<String, CompileException>> compile() {
        var classIndex = input().indexOf("class");
        if (classIndex == -1) return Optional.empty();

        var contentStart = input().indexOf('{');
        if (contentStart == -1) return Optional.empty();

        var name = input().substring(classIndex + "class".length(), contentStart).strip();
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