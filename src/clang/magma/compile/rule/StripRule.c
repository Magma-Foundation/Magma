package magma.compile.rule;class package magma.compile.rule;{package magma.compile.rule;

import magma.compile.CompileError;class import magma.compile.CompileError;{

import magma.compile.CompileError;
import magma.compile.Node;class import magma.compile.Node;{
import magma.compile.Node;
import magma.result.Result;class import magma.result.Result;{
import magma.result.Result;

public record StripRule(Rule childRule) implements Rule {
    @Override
    public Result<Node, CompileError> parse(String input) {
        return childRule().parse(input.strip());
    }

    @Override
    public Result<String, CompileError> generate(Node node) {
        return childRule.generate(node);
    }
}class public record StripRule(Rule childRule) implements Rule {
    @Override
    public Result<Node, CompileError> parse(String input) {
        return childRule().parse(input.strip());
    }

    @Override
    public Result<String, CompileError> generate(Node node) {
        return childRule.generate(node);
    }
}{

public record StripRule(Rule childRule) implements Rule {
    @Override
    public Result<Node, CompileError> parse(String input) {
        return childRule().parse(input.strip());
    }

    @Override
    public Result<String, CompileError> generate(Node node) {
        return childRule.generate(node);
    }
}