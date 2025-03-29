package magma.compile.rule;class package magma.compile.rule;{package magma.compile.rule;

import magma.compile.CompileError;class import magma.compile.CompileError;{

import magma.compile.CompileError;
import magma.compile.Node;class import magma.compile.Node;{
import magma.compile.Node;
import magma.compile.context.NodeContext;class import magma.compile.context.NodeContext;{
import magma.compile.context.NodeContext;
import magma.result.Err;class import magma.result.Err;{
import magma.result.Err;
import magma.result.Result;class import magma.result.Result;{
import magma.result.Result;

public record TypeRule(String type, Rule rule) implements Rule {
    @Override
    public Result<Node, CompileError> parse(String input) {
        return rule.parse(input).mapValue(node -> node.withType(type));
    }

    @Override
    public Result<String, CompileError> generate(Node node) {
        if (node.is(type)) {
            return rule.generate(node);
        }

        return new Err<>(new CompileError("Node was not of type '" + type + "'", new NodeContext(node)));
    }
}
class public record TypeRule(String type, Rule rule) implements Rule {
    @Override
    public Result<Node, CompileError> parse(String input) {
        return rule.parse(input).mapValue(node -> node.withType(type));
    }

    @Override
    public Result<String, CompileError> generate(Node node) {
        if (node.is(type)) {
            return rule.generate(node);
        }

        return new Err<>(new CompileError("Node was not of type '" + type + "'", new NodeContext(node)));
    }
}{

public record TypeRule(String type, Rule rule) implements Rule {
    @Override
    public Result<Node, CompileError> parse(String input) {
        return rule.parse(input).mapValue(node -> node.withType(type));
    }

    @Override
    public Result<String, CompileError> generate(Node node) {
        if (node.is(type)) {
            return rule.generate(node);
        }

        return new Err<>(new CompileError("Node was not of type '" + type + "'", new NodeContext(node)));
    }
}
