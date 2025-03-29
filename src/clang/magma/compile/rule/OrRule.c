package magma.compile.rule;class package magma.compile.rule;{package magma.compile.rule;

import magma.collect.list.List_;class import magma.collect.list.List_;{

import magma.collect.list.List_;
import magma.compile.CompileError;class import magma.compile.CompileError;{
import magma.compile.CompileError;
import magma.compile.MapNode;class import magma.compile.MapNode;{
import magma.compile.MapNode;
import magma.compile.Node;class import magma.compile.Node;{
import magma.compile.Node;
import magma.compile.context.StringContext;class import magma.compile.context.StringContext;{
import magma.compile.context.StringContext;
import magma.result.Err;class import magma.result.Err;{
import magma.result.Err;
import magma.result.Ok;class import magma.result.Ok;{
import magma.result.Ok;
import magma.result.Result;class import magma.result.Result;{
import magma.result.Result;

public record OrRule(List_<Rule> rules) implements Rule {
    static OrState fold(String input, OrState orState, Rule rule) {
        return rule.parse(input).flatMapValue(rule::generate)
                .findValue()
                .map(orState::withValue)
                .orElse(orState);
    }

    private Result<String, CompileError> compile(String input) {
        return rules.stream()
                .foldWithInitial(new OrState(), (orState, rule) -> fold(input, orState, rule))
                .toOption().<Result<String, CompileError>>match(Ok::new, () -> new Err<>(new CompileError("Invalid root segment", new StringContext(input))));
    }

    @Override
    public Result<Node, CompileError> parse(String input) {
        return compile(input).mapValue(value -> new MapNode().withString("value", value));
    }

    @Override
    public Result<String, CompileError> generate(Node input) {
        return new Ok<>(input.findString("value").orElse(""));
    }
}class public record OrRule(List_<Rule> rules) implements Rule {
    static OrState fold(String input, OrState orState, Rule rule) {
        return rule.parse(input).flatMapValue(rule::generate)
                .findValue()
                .map(orState::withValue)
                .orElse(orState);
    }

    private Result<String, CompileError> compile(String input) {
        return rules.stream()
                .foldWithInitial(new OrState(), (orState, rule) -> fold(input, orState, rule))
                .toOption().<Result<String, CompileError>>match(Ok::new, () -> new Err<>(new CompileError("Invalid root segment", new StringContext(input))));
    }

    @Override
    public Result<Node, CompileError> parse(String input) {
        return compile(input).mapValue(value -> new MapNode().withString("value", value));
    }

    @Override
    public Result<String, CompileError> generate(Node input) {
        return new Ok<>(input.findString("value").orElse(""));
    }
}{

public record OrRule(List_<Rule> rules) implements Rule {
    static OrState fold(String input, OrState orState, Rule rule) {
        return rule.parse(input).flatMapValue(rule::generate)
                .findValue()
                .map(orState::withValue)
                .orElse(orState);
    }

    private Result<String, CompileError> compile(String input) {
        return rules.stream()
                .foldWithInitial(new OrState(), (orState, rule) -> fold(input, orState, rule))
                .toOption().<Result<String, CompileError>>match(Ok::new, () -> new Err<>(new CompileError("Invalid root segment", new StringContext(input))));
    }

    @Override
    public Result<Node, CompileError> parse(String input) {
        return compile(input).mapValue(value -> new MapNode().withString("value", value));
    }

    @Override
    public Result<String, CompileError> generate(Node input) {
        return new Ok<>(input.findString("value").orElse(""));
    }
}