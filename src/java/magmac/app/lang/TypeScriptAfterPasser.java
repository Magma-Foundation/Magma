package magmac.app.lang;

import magmac.api.None;
import magmac.api.Option;
import magmac.api.Some;
import magmac.api.Tuple2;
import magmac.api.collect.list.Lists;
import magmac.app.compile.node.InlineNodeList;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.compile.node.NodeList;
import magmac.app.compile.node.NodeListCollector;
import magmac.app.stage.InlinePassResult;
import magmac.app.stage.PassResult;
import magmac.app.stage.Passer;
import magmac.app.stage.parse.ParseState;

public class TypeScriptAfterPasser implements Passer {
    @Override
    public PassResult pass(ParseState state, Node node) {
        return this.passImport(state, node)
                .<PassResult>map(result -> new InlinePassResult(new Some<>(new Tuple2<>(state, result))))
                .orElseGet(() -> InlinePassResult.empty());
    }

    private Option<Node> passImport(ParseState state, Node node) {
        if (!node.is("import")) {
            return new None<>();
        }

        int namespaceSize = state.findLocation().namespace().size();
        NodeList copy = Lists.repeat("..", namespaceSize)
                .iter()
                .map(value -> new MapNode().withString("value", value))
                .collect(new NodeListCollector());

        NodeList segments = node.findNodeList("segments")
                .orElse(InlineNodeList.empty());

        return segments.findLast().map(last -> {
            return node.withString("child", last.findString("value").orElse(""))
                    .withNodeList("segments", copy.addAll(segments));
        });
    }
}
