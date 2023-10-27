package com.meti.compile;

import com.meti.CompileException;
import com.meti.compile.iterator.ArrayIterator;
import com.meti.compile.iterator.Collectors;
import com.meti.compile.option.None;
import com.meti.compile.option.Some;
import com.meti.compile.result.Err;
import com.meti.compile.result.Ok;
import com.meti.compile.result.Result;
import com.meti.compile.rule.ConjunctionRule;
import com.meti.compile.rule.EqualRule;
import com.meti.compile.rule.Rule;
import com.meti.compile.rule.ValueRule;
import org.jetbrains.annotations.NotNull;

public record Compiler(String input) {
    static Result<String, CompileException> compileImport(String input) {
        var separator = input.lastIndexOf('.');
        if (separator == -1) {
            return new Err<>(new CompileException("Invalid import syntax."));
        } else {
            var parent = input.substring("import ".length(), separator).strip();
            var child = input.substring(separator + 1).strip();
            return Ok.apply("import { %s } from %s;".formatted(child, parent));
        }
    }

    public String compile() throws CompileException {
        var args = input.split(";");
        return foldRight(args).unwrap();
    }

    @NotNull
    private Result<String, CompileException> foldRight(String[] args) {
        return new ArrayIterator<String>(args)
                .map(this::compileLine)
                .collect(Collectors.exceptionally(Collectors.joining()));
    }

    private Result<String, CompileException> compileLine(String line) {
        if (line.startsWith("import ")) {
            return compileImport(line);
        }

        var rule = ConjunctionRule.join(
                new EqualRule("record "),
                new ValueRule("name"),
                new EqualRule("(){}")
        );
        return rule.evaluate(line)
                .flatMap(evaluated -> evaluated.isEmpty() ? None.apply() : Some.apply(evaluated.get(0)))
                .map(evaluated -> {
                    var name = evaluated.values().get("name");
                    return Ok.<String, CompileException>apply("class def " + name + "() => {}");
                })
                .unwrapOrElse(Ok.apply(""));
    }
}