package magmac.app.lang.node;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.NodeRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.SuffixRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.lang.Destructors;
import magmac.app.lang.JavaNodes;

record FunctionStatement(JavaNodes.FunctionSegmentValue child) implements FunctionSegment {
    public static Option<CompileResult<FunctionSegment>> deserialize(Node node) {
        return Destructors.destructWithType("statement", node)
                .map(deserializer -> deserializer.withNode("child", FunctionSegmentValues::deserialize)
                        .complete(FunctionStatement::new)
                        .mapValue(value -> value));
    }

    public static Rule createStatementRule(Rule rule) {
        NodeRule child = new NodeRule("child", rule);
        return new TypeRule("statement", new StripRule(new SuffixRule(child, ";")));
    }

    @Override
    public Node serialize() {
        return new MapNode("statement").withNodeSerialized("child", this.child);
    }
}
