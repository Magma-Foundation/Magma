package magmac.app.compile.rule;

import magmac.api.collect.Iters;
import magmac.api.collect.collect.ResultCollector;
import magmac.api.result.Ok;
import magmac.api.result.Result;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.divide.DivideState;
import magmac.app.compile.rule.divide.MutableDivideState;
import magmac.app.compile.rule.fold.Folder;
import magmac.app.error.CompileError;
import magmac.app.error.CompileErrors;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record DivideRule(String key, Folder folder, Rule childRule) implements Rule {
    private List<String> divide(String input) {
        DivideState current = new MutableDivideState();
        int length = input.length();
        for (int i = 0; i < length; i++) {
            char c = input.charAt(i);
            current = this.folder.fold(current, c);
        }

        return current.advance().stream().toList();
    }

    @Override
    public Result<Node, CompileError> lex(String input) {
        List<String> segments = this.divide(input);
        var result = (Result<List<Node>, CompileError>) new Ok<List<Node>, CompileError>(new ArrayList<Node>());
        for (String segment : segments) {
            String stripped = segment.strip();
            result = result.and(() -> this.childRule.lex(stripped)).mapValue(tuple -> {
                tuple.left().add(tuple.right());
                return tuple.left();
            });
        }

        return result.mapValue(children -> new MapNode().withNodeList(this.key(), children));
    }

    @Override
    public Result<String, CompileError> generate(Node node) {
        return node.findNodeList(this.key)
                .map(list -> this.join(list))
                .orElseGet(() -> CompileErrors.createNodeError(node, "Node list '" + this.key + "' not present"))
                .mapValue(value -> value.orElse(""));
    }

    private Result<Optional<String>, CompileError> join(List<Node> list) {
        return Iters.fromList(list)
                .map(this.childRule::generate)
                .collect(new ResultCollector<>(new Joiner()));
    }
}