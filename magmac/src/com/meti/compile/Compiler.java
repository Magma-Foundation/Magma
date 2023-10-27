package com.meti.compile;

import com.meti.CompileException;
import com.meti.api.collect.List;
import com.meti.api.iterator.ArrayIterator;
import com.meti.api.iterator.Collectors;
import com.meti.api.option.Option;
import com.meti.api.result.Err;
import com.meti.api.result.Ok;
import com.meti.api.result.Result;
import com.meti.api.result.ThrowableOption;
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
                ).fromString(line).flatMap(List::head)
                .map(evaluated -> render(new ResultNode(evaluated, "import")));
    }

    private static Result<String, CompileException> render(ResultNode node) {
        if ("import".equals(node.type())) {
            return ConjunctionRule.join(
                    new EqualRule("import { "),
                    new ValueRule("child"),
                    new EqualRule(" } from "),
                    new ValueRule("parent"),
                    new EqualRule(";")
            ).toString(node).into(ThrowableOption::new).unwrapOrThrow(new CompileException("Invalid import configuration."));
        } else if ("function".equals(node.type())) {
            var name = node.getString("name").unwrapOrElse("");
            var value = "class def " + name + "() => {}";
            return Ok.apply(value);
        } else {
            return new Err<>(new CompileException("Unknown type: '" + node.type() + "'."));
        }
    }

    private static Option<Result<String, CompileException>> compileRecord(String line) {
        return ConjunctionRule.join(
                        new EqualRule("record "),
                        new ValueRule("name"),
                        new EqualRule("(){}"))
                .fromString(line)
                .flatMap(List::head)
                .map(evaluated -> render(new ResultNode(evaluated, "function")));
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