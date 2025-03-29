package magma.compile.rule;class package magma.compile.rule;{package magma.compile.rule;

import magma.compile.CompileError;class import magma.compile.CompileError;{

import magma.compile.CompileError;
import magma.compile.MapNode;class import magma.compile.MapNode;{
import magma.compile.MapNode;
import magma.compile.Node;class import magma.compile.Node;{
import magma.compile.Node;
import magma.compile.context.NodeContext;class import magma.compile.context.NodeContext;{
import magma.compile.context.NodeContext;
import magma.result.Err;class import magma.result.Err;{
import magma.result.Err;
import magma.result.Ok;class import magma.result.Ok;{
import magma.result.Ok;
import magma.result.Result;class import magma.result.Result;{
import magma.result.Result;

public record StringRule(String propertyKey) implements Rule {
    @Override
    public Result<Node, CompileError> parse(String value) {
        return new Ok<>(new MapNode().withString(propertyKey(), value));
    }

    @Override
    public Result<String, CompileError> generate(Node input) {
        return input.findString(propertyKey)
                .<Result<String, CompileError>>map(Ok::new)
                .orElseGet(() -> new Err<>(new CompileError("String '" + propertyKey + "' not present", new NodeContext(input))));
    }
}class public record StringRule(String propertyKey) implements Rule {
    @Override
    public Result<Node, CompileError> parse(String value) {
        return new Ok<>(new MapNode().withString(propertyKey(), value));
    }

    @Override
    public Result<String, CompileError> generate(Node input) {
        return input.findString(propertyKey)
                .<Result<String, CompileError>>map(Ok::new)
                .orElseGet(() -> new Err<>(new CompileError("String '" + propertyKey + "' not present", new NodeContext(input))));
    }
}{

public record StringRule(String propertyKey) implements Rule {
    @Override
    public Result<Node, CompileError> parse(String value) {
        return new Ok<>(new MapNode().withString(propertyKey(), value));
    }

    @Override
    public Result<String, CompileError> generate(Node input) {
        return input.findString(propertyKey)
                .<Result<String, CompileError>>map(Ok::new)
                .orElseGet(() -> new Err<>(new CompileError("String '" + propertyKey + "' not present", new NodeContext(input))));
    }
}