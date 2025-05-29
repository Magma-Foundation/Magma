package magmac.app.lang.java.node;

import magmac.api.collect.list.List;
import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.lang.CommonLang;
import magmac.app.lang.java.Deserializers;
import magmac.app.lang.java.Serializable;

public record JavaRoot(List<JavaRootSegment> children) implements Serializable {
    public static CompileResult<JavaRoot> deserialize(Node node) {
        return Deserializers.deserialize(node)
                .withNodeList("children", (Node node1) -> JavaRootSegments.deserialize(node1))
                .complete((List<JavaRootSegment> segments) -> new JavaRoot(segments));
    }

    public static Rule createRule() {
        return new TypeRule("root", CommonLang.Statements("children", new OrRule(Lists.of(
                Whitespace.createWhitespaceRule(),
                Namespaced.createNamespacedRule("package"),
                Namespaced.createNamespacedRule("import"),
                Structures.createStructureRule("record"),
                Structures.createStructureRule("interface"),
                Structures.createStructureRule("class"),
                Structures.createStructureRule("enum")
        ))));
    }

    @Override
    public Node serialize() {
        return new MapNode().withNodeListFromElements("children", this.children, (JavaRootSegment segment) -> JavaRootSegments.serialize(segment));
    }
}
