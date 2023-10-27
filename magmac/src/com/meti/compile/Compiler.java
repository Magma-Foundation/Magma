package com.meti.compile;

import com.meti.CompileException;
import com.meti.compile.collect.List;
import com.meti.compile.iterator.ArrayIterator;
import com.meti.compile.iterator.Collectors;
import com.meti.compile.option.Option;
import com.meti.compile.result.Ok;
import com.meti.compile.result.Result;
import com.meti.compile.rule.ConjunctionRule;
import com.meti.compile.rule.EqualRule;
import com.meti.compile.rule.ValueRule;

public record Compiler(String input) {

    private static Option<Result<String, CompileException>> compileImport(String line) {
        return ConjunctionRule.join(
                        new EqualRule("import "),
                        new ValueRule("parent"),
                        new EqualRule("."),
                        new ValueRule("child")
                ).evaluate(line).flatMap(List::head)
                .map(evaluated -> {
                    var parent = evaluated.values().get("parent");
                    var child = evaluated.values().get("child");
                    return Ok.apply("import { %s } from %s;".formatted(child, parent));
                });
    }

    private static Option<Result<String, CompileException>> compileRecord(String line) {
        return ConjunctionRule.join(
                        new EqualRule("record "),
                        new ValueRule("name"),
                        new EqualRule("(){}"))
                .evaluate(line)
                .flatMap(List::head)
                .map(evaluated -> {
                    var name = evaluated.values().get("name");
                    return Ok.apply("class def " + name + "() => {}");
                });
    }

    public String compile() throws CompileException {
        var args = input.split(";");
        return foldRight(args).unwrap();
    }

    private Result<String, CompileException> foldRight(String[] args) {
        return new ArrayIterator<>(args)
                .map(this::compileLine)
                .collect(Collectors.exceptionally(Collectors.joining()));
    }

    private Result<String, CompileException> compileLine(String line) {
        return compileImport(line)
                .or(compileRecord(line))
                .unwrapOrElse(Ok.apply(""));
    }
}