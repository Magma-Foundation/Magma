package magma.compile.rule;class package magma.compile.rule;{package magma.compile.rule;

import jvm.collect.list.Lists;class import jvm.collect.list.Lists;{

import jvm.collect.list.Lists;
import magma.collect.list.List_;class import magma.collect.list.List_;{
import magma.collect.list.List_;
import magma.compile.CompileError;class import magma.compile.CompileError;{
import magma.compile.CompileError;
import magma.compile.MapNode;class import magma.compile.MapNode;{
import magma.compile.MapNode;
import magma.compile.Node;class import magma.compile.Node;{
import magma.compile.Node;
import magma.compile.context.NodeContext;class import magma.compile.context.NodeContext;{
import magma.compile.context.NodeContext;
import magma.compile.rule.divide.Divider;class import magma.compile.rule.divide.Divider;{
import magma.compile.rule.divide.Divider;
import magma.result.Err;class import magma.result.Err;{
import magma.result.Err;
import magma.result.Result;class import magma.result.Result;{
import magma.result.Result;

public record DivideRule(Divider divider, Rule childRule, String propertyKey) implements Rule {
    @Override
    public Result<Node, CompileError> parse(String input) {
        return divider.divide(input)
                .stream()
                .<List_<Node>, CompileError>foldToResult(Lists.empty(), (children, element) -> childRule().parse(element).mapValue(children::add))
                .mapValue(children -> new MapNode().withNodeList(propertyKey(), children));
    }

    @Override
    public Result<String, CompileError> generate(Node node) {
        return node.findNodeList(propertyKey)
                .map(this::generateChildren)
                .orElseGet(() -> new Err<>(new CompileError("Node list '" + propertyKey + "' not present", new NodeContext(node))));
    }

    private Result<String, CompileError> generateChildren(List_<Node> children) {
        return children.stream().foldToResult("", (current, element) -> childRule
                .generate(element)
                .mapValue(result -> divider.join(current, result)));
    }
}class public record DivideRule(Divider divider, Rule childRule, String propertyKey) implements Rule {
    @Override
    public Result<Node, CompileError> parse(String input) {
        return divider.divide(input)
                .stream()
                .<List_<Node>, CompileError>foldToResult(Lists.empty(), (children, element) -> childRule().parse(element).mapValue(children::add))
                .mapValue(children -> new MapNode().withNodeList(propertyKey(), children));
    }

    @Override
    public Result<String, CompileError> generate(Node node) {
        return node.findNodeList(propertyKey)
                .map(this::generateChildren)
                .orElseGet(() -> new Err<>(new CompileError("Node list '" + propertyKey + "' not present", new NodeContext(node))));
    }

    private Result<String, CompileError> generateChildren(List_<Node> children) {
        return children.stream().foldToResult("", (current, element) -> childRule
                .generate(element)
                .mapValue(result -> divider.join(current, result)));
    }
}{

public record DivideRule(Divider divider, Rule childRule, String propertyKey) implements Rule {
    @Override
    public Result<Node, CompileError> parse(String input) {
        return divider.divide(input)
                .stream()
                .<List_<Node>, CompileError>foldToResult(Lists.empty(), (children, element) -> childRule().parse(element).mapValue(children::add))
                .mapValue(children -> new MapNode().withNodeList(propertyKey(), children));
    }

    @Override
    public Result<String, CompileError> generate(Node node) {
        return node.findNodeList(propertyKey)
                .map(this::generateChildren)
                .orElseGet(() -> new Err<>(new CompileError("Node list '" + propertyKey + "' not present", new NodeContext(node))));
    }

    private Result<String, CompileError> generateChildren(List_<Node> children) {
        return children.stream().foldToResult("", (current, element) -> childRule
                .generate(element)
                .mapValue(result -> divider.join(current, result)));
    }
}