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
        return TypeScriptAfterPasser.passImport(state, node)
                .orElseGet(() -> InlinePassResult.empty());
    }

    private static Option<PassResult> passImport(ParseState state, Node node) {
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

        Option<Tuple2<ParseState, Node>> map = segments.findLast().map(last -> {
            String value = last.findString("value").orElse("");
            NodeList values = copy.addAll(segments);
            Node node1 = node.withString("child", value).withNodeList("segments", values);
            return new Tuple2<>(state, node1);
        });

        return new Some<>(new InlinePassResult(map));
    }
}
