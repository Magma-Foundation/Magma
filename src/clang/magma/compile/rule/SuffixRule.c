package magma.compile.rule;class package magma.compile.rule;{package magma.compile.rule;

import magma.compile.CompileError;class import magma.compile.CompileError;{

import magma.compile.CompileError;
import magma.compile.Node;class import magma.compile.Node;{
import magma.compile.Node;
import magma.compile.context.StringContext;class import magma.compile.context.StringContext;{
import magma.compile.context.StringContext;
import magma.result.Err;class import magma.result.Err;{
import magma.result.Err;
import magma.result.Result;class import magma.result.Result;{
import magma.result.Result;

public record SuffixRule(Rule childRule, String suffix) implements Rule {
    @Override
    public Result<Node, CompileError> parse(String input) {
        if (!input.endsWith(suffix()))
            return new Err<>(new CompileError("Suffix '" + suffix() + "' not present", new StringContext(input)));

        String slice = input.substring(0, input.length() - suffix.length());
        return childRule.parse(slice);
    }

    @Override
    public Result<String, CompileError> generate(Node node) {
        return childRule.generate(node).mapValue(result -> result + suffix);
    }
}class public record SuffixRule(Rule childRule, String suffix) implements Rule {
    @Override
    public Result<Node, CompileError> parse(String input) {
        if (!input.endsWith(suffix()))
            return new Err<>(new CompileError("Suffix '" + suffix() + "' not present", new StringContext(input)));

        String slice = input.substring(0, input.length() - suffix.length());
        return childRule.parse(slice);
    }

    @Override
    public Result<String, CompileError> generate(Node node) {
        return childRule.generate(node).mapValue(result -> result + suffix);
    }
}{

public record SuffixRule(Rule childRule, String suffix) implements Rule {
    @Override
    public Result<Node, CompileError> parse(String input) {
        if (!input.endsWith(suffix()))
            return new Err<>(new CompileError("Suffix '" + suffix() + "' not present", new StringContext(input)));

        String slice = input.substring(0, input.length() - suffix.length());
        return childRule.parse(slice);
    }

    @Override
    public Result<String, CompileError> generate(Node node) {
        return childRule.generate(node).mapValue(result -> result + suffix);
    }
}