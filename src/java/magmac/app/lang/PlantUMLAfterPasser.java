package magmac.app.lang;

import magmac.api.Some;
import magmac.api.collect.list.List;
import magmac.api.iter.collect.ListCollector;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileResultCollector;
import magmac.app.compile.error.CompileResults;
import magmac.app.compile.error.error.CompileErrors;
import magmac.app.compile.node.InlineNodeList;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.compile.node.NodeList;
import magmac.app.compile.node.NodeListCollector;
import magmac.app.compile.rule.StringRule;
import magmac.app.stage.result.InlinePassResult;
import magmac.app.stage.result.ParseResult;
import magmac.app.stage.unit.ParseUnit;
import magmac.app.stage.unit.ParseUnitImpl;
import magmac.app.stage.Passer;
import magmac.app.stage.parse.ParseState;

public class PlantUMLAfterPasser implements Passer {
    private static CompileResult<NodeList> createInherits(Node child, String key) {
        return child.findNode(key)
                .map((Node implemented) -> PlantUMLAfterPasser.getNodeListCompileResult(child, implemented))
                .orElseGet(() -> CompileResults.Ok(InlineNodeList.empty()));
    }

    private static CompileResult<NodeList> getNodeListCompileResult(Node child, Node implemented) {
        return StringRule.findString(child, "name").flatMapValue((String name) -> PlantUMLAfterPasser.createInherits0(implemented, name));
    }

    private static CompileResult<NodeList> createInherits0(Node type, String child) {
        return PlantUMLAfterPasser.stringifyType(type).mapValue((String parent) -> {
            var node = new MapNode("inherits")
                    .withString("child", child)
                    .withString("parent", parent);

            return InlineNodeList.of(node);
        });
    }

    private static CompileResult<String> stringifyType(Node type) {
        if (type.is("template")) {
            return StringRule.findString(type, "base");
        }

        if (type.is("symbol-type")) {
            return StringRule.findString(type, "value");
        }

        return CompileErrors.createNodeError("Cannot find value for type", type);
    }

    private static CompileResult<NodeList> replaceRootChild(Node child) {
        var maybeExtended = PlantUMLAfterPasser.createInherits(child, "extended");
        var maybeImplemented = PlantUMLAfterPasser.createInherits(child, "implemented");
        return maybeExtended.flatMapValue((NodeList extendedResult) -> maybeImplemented.mapValue((NodeList implementedResult) -> InlineNodeList.of(child)
                .addAll(extendedResult)
                .addAll(implementedResult)));
    }

    private static CompileResult<NodeList> replaceRootChildren(Node node) {
        return PlantUMLAfterPasser.replaceChildrenToList(node).mapValue((List<NodeList> lists) -> lists.iter()
                .flatMap(NodeList::iter)
                .collect(new NodeListCollector()));
    }

    private static CompileResult<List<NodeList>> replaceChildrenToList(Node node) {
        return node.findNodeList("children")
                .orElse(InlineNodeList.empty())
                .iter()
                .map(PlantUMLAfterPasser::replaceRootChild)
                .collect(new CompileResultCollector<>(new ListCollector<>()));
    }

    @Override
    public ParseResult pass(ParseState state, Node node) {
        if (node.is("root")) {
            var result = PlantUMLAfterPasser.replaceRootChildren(node)
                    .flatMapValue((NodeList values) -> PlantUMLAfterPasser.getTuple2CompileResult(state, node, values));

            return new InlinePassResult(new Some<>(result));
        }

        if (node.is("import")) {
            var child = node.findNodeList("segments")
                    .orElse(InlineNodeList.empty()).findLast().orElse(null)
                    .findString("value")
                    .orElse("");

            var dependency = new MapNode("dependency")
                    .withString("parent", state.findLocation().name())
                    .withString("child", child);

            ParseUnit<Node> tuple = new ParseUnitImpl<Node>(state, dependency);
            return new InlinePassResult(new Some<>(CompileResults.Ok(tuple)));
        }

        return InlinePassResult.empty();
    }

    private static CompileResult<ParseUnit<Node>> getTuple2CompileResult(ParseState state, Node node, NodeList values) {
        return CompileResults.Ok(new ParseUnitImpl<Node>(state, node.withNodeList("children", values)));
    }
}