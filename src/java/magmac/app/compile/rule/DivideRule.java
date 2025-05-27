package magmac.app.compile.rule;

import magmac.api.Tuple2;
import magmac.api.collect.Joiner;
import magmac.api.collect.ListCollector;
import magmac.api.collect.ResultCollector;
import magmac.api.iter.Iter;
import magmac.api.iter.Iters;
import magmac.api.result.Result;
import magmac.app.compile.error.CompileError;
import magmac.app.compile.error.CompileErrors;
import magmac.app.compile.node.InlineNodeList;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.compile.node.NodeList;
import magmac.app.compile.rule.divide.DivideState;
import magmac.app.compile.rule.divide.MutableDivideState;
import magmac.app.compile.rule.fold.Folder;
import magmac.app.compile.rule.fold.StatementFolder;

import java.util.Optional;

public record DivideRule(String key, Folder folder, Rule childRule) implements Rule {
    public static DivideRule Statements(String key, Rule childRule) {
        return new DivideRule(key, new StatementFolder(), childRule);
    }

    @Override
    public Result<Node, CompileError> lex(String input) {
        return this.divide(input)
                .map(segment -> this.childRule.lex(segment))
                .collect(new ResultCollector<>(new ListCollector<>()))
                .mapValue(children -> {
                    Node node = new MapNode();
                    return node.withNodeList(this.key(), new InlineNodeList(children));
                });
    }

    private Iter<String> divide(String input) {
        DivideState current = new MutableDivideState(input);
        while (true) {
            Optional<Tuple2<DivideState, Character>> maybePopped = current.pop();
            if (maybePopped.isEmpty()) {
                break;
            }

            current = maybePopped.get().left();
            char c = maybePopped.get().right();

            current = this.folder.fold(current, c);
        }

        return Iters.fromList(current.advance().stream().toList());
    }

    @Override
    public Result<String, CompileError> generate(Node node) {
        return node.findNodeList(this.key)
                .map(list -> this.join(list))
                .orElseGet(() -> CompileErrors.createNodeError("Node list '" + this.key + "' not present", node))
                .mapValue(value -> value.orElse(""));
    }

    private Result<Optional<String>, CompileError> join(NodeList list) {
        return list.iter()
                .map(this.childRule::generate)
                .collect(new ResultCollector<>(new Joiner()));
    }
}