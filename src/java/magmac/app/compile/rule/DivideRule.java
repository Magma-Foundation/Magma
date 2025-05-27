package magmac.app.compile.rule;

import magmac.api.Tuple2;
import magmac.api.collect.Joiner;
import magmac.api.collect.ListCollector;
import magmac.api.collect.ResultCollector;
import magmac.api.iter.Iter;
import magmac.api.iter.Iters;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.error.CompileErrors;
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

    private static Optional<DivideState> foldEscape(DivideState current, char c) {
        if ('\\' == c) {
            return current.popAndAppendToOption();
        }
        else {
            return Optional.of(current);
        }
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

            DivideState finalCurrent = current;
            current = this.foldSingleQuotes(current, c)
                    .orElseGet(() -> this.folder.fold(finalCurrent, c));
        }

        return Iters.fromList(current.advance().stream().toList());
    }

    private Optional<DivideState> foldSingleQuotes(DivideState current, char c) {
        if ('\'' != c) {
            return Optional.empty();
        }

        return current.append(c)
                .popAndAppendToTuple()
                .flatMap(tuple -> DivideRule.foldEscape(current, tuple.right()))
                .flatMap(DivideState::popAndAppendToOption);
    }

    private CompileResult<Optional<String>> join(NodeList list) {
        return new CompileResult<>(list.iter()
                .map(node -> this.childRule.generate(node).result())
                .collect(new ResultCollector<>(new Joiner())));
    }

    @Override
    public CompileResult<Node> lex(String input) {
        return new CompileResult<>(this.divide(input)
                .map(segment -> this.childRule.lex(segment).result())
                .collect(new ResultCollector<>(new ListCollector<>()))
                .mapValue(children -> {
                    Node node = new MapNode();
                    return node.withNodeList(this.key(), new InlineNodeList(children));
                }));
    }

    @Override
    public CompileResult<String> generate(Node node) {
        return node.findNodeList(this.key)
                .map(list -> this.join(list))
                .orElseGet(() -> CompileErrors.createNodeError("Node list '" + this.key + "' not present", node))
                .mapValue(value -> value.orElse(""));
    }
}