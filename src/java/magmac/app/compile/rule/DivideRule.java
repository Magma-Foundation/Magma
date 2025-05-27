package magmac.app.compile.rule;

import magmac.api.collect.Iters;
import magmac.api.collect.collect.ResultCollector;
import magmac.api.result.Err;
import magmac.api.result.Ok;
import magmac.api.result.Result;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.divide.DivideState;
import magmac.app.compile.rule.divide.MutableDivideState;
import magmac.app.error.CompileError;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record DivideRule(String key, Rule childRule) implements Rule {
    private static List<String> divide(String input) {
        DivideState current = new MutableDivideState();
        int length = input.length();
        for (int i = 0; i < length; i++) {
            char c = input.charAt(i);
            current = DivideRule.fold(current, c);
        }

        return current.advance().stream().toList();
    }

    private static DivideState fold(DivideState state, char c) {
        DivideState appended = state.append(c);
        if (';' == c && appended.isLevel()) {
            return appended.advance();
        }
        if ('{' == c) {
            return appended.enter();
        }
        if ('}' == c) {
            return appended.exit();
        }
        return appended;
    }

    @Override
    public Result<Node, CompileError> lex(String input) {
        List<String> segments = DivideRule.divide(input);
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
                .orElseGet(() -> new Err<>(new CompileError("Node list '" + this.key + "' not present", new NodeContext(node))))
                .mapValue(value -> value.orElse(""));
    }

    private Result<Optional<String>, CompileError> join(List<Node> list) {
        return Iters.fromList(list)
                .map(this.childRule::generate)
                .collect(new ResultCollector<>(new Joiner()));
    }
}