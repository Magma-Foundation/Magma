package magmac.app.lang;

import magmac.api.None;
import magmac.api.Option;
import magmac.api.Some;
import magmac.api.Tuple2;
import magmac.api.iter.collect.ListCollector;
import magmac.api.iter.Iter;
import magmac.api.iter.Iters;
import magmac.app.compile.node.InlineNodeList;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.compile.node.NodeList;
import magmac.app.stage.InlinePassResult;
import magmac.app.stage.Passer;
import magmac.app.stage.parse.ParseState;

public class AfterPasser implements Passer {
    private static Option<Node> createInherits(Node child, String key) {
        return child.findNode(key).map(implemented -> new MapNode("inherits")
                .withString("child", child.findString("name").orElse(""))
                .withString("parent", AfterPasser.findValue(implemented)));
    }

    private static String findValue(Node type) {
        if (type.is("template")) {
            return type.findString("base").orElse("?");
        }

        if (type.is("symbol")) {
            return type.findString("value").orElse("?");
        }

        return "?";
    }

    private static Iter<Node> replaceRootChild(Node child) {
        Option<Node> extended = AfterPasser.createInherits(child, "extended");
        Option<Node> implemented = AfterPasser.createInherits(child, "implemented");
        return Iters.fromValues(child)
                .concat(Iters.fromOption(extended))
                .concat(Iters.fromOption(implemented));
    }

    private static NodeList replaceRootChildren(Node node) {
        return new InlineNodeList(node.findNodeList("children")
                .orElse(InlineNodeList.empty())
                .iter()
                .flatMap(child -> AfterPasser.replaceRootChild(child))
                .collect(new ListCollector<>()));
    }

    @Override
    public magmac.app.stage.PassResult pass(ParseState state, Node node) {
        if (node.is("root")) {
            NodeList values = AfterPasser.replaceRootChildren(node);
            return new InlinePassResult(new Some<>(new Tuple2<ParseState, Node>(state, node.withNodeList("children", values))));
        }

        if (node.is("import")) {
            String child = node.findNodeList("segments")
                    .orElse(InlineNodeList.empty())
                    .last()
                    .findString("value")
                    .orElse("");

            Node dependency = new MapNode("dependency")
                    .withString("parent", state.findLocation().name())
                    .withString("child", child);

            return new InlinePassResult(new Some<>(new Tuple2<ParseState, Node>(state, dependency)));
        }

        return InlinePassResult.empty();
    }
}