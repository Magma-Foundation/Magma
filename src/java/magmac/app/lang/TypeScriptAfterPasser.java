package magmac.app.lang;

import magmac.api.None;
import magmac.api.Option;
import magmac.api.Some;
import magmac.api.Tuple2;
import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.InlineCompileResult;
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
    private static Option<PassResult> passImport(ParseState state, Node node) {
        if (!node.is("import")) {
            return new None<>();
        }

        int namespaceSize = state.findLocation().namespace().size();
        NodeList copy = Lists.repeat("..", namespaceSize)
                .iter()
                .map((String value) -> new MapNode().withString("value", value))
                .collect(new NodeListCollector());
        NodeList segments = node.findNodeList("segments")
                .orElse(InlineNodeList.empty());

        Option<CompileResult<Tuple2<ParseState, Node>>> map = segments.findLast().map((Node last) -> {
            String value = last.findString("value").orElse("");
            NodeList values = copy.addAll(segments);
            Node node1 = node.withString("child", value).withNodeList("segments", values);
            return InlineCompileResult.fromOk(new Tuple2<>(state, node1));
        });

        return new Some<>(new InlinePassResult(map));
    }

    private static Option<PassResult> passMethod(ParseState state, Node node) {
        if (node.is("method")) {
            Node header = node.findNode("header").orElse(new MapNode());
            NodeList parameters = node.findNodeList("parameters").orElse(InlineNodeList.empty());
            Node withParameters = header.withNodeList("parameters", parameters);
            return new Some<>(new InlinePassResult(new Some<>(InlineCompileResult.fromOk(new Tuple2<>(state, node.withNode("header", withParameters))))));
        }

        return new None<>();
    }

    @Override
    public PassResult pass(ParseState state, Node node) {
        return TypeScriptAfterPasser.passImport(state, node)
                .or(() -> TypeScriptAfterPasser.passMethod(state, node))
                .orElseGet(() -> InlinePassResult.empty());
    }
}
