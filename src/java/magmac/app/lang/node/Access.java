package magmac.app.lang.node;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.LocatingRule;
import magmac.app.compile.rule.NodeRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.lang.Deserializers;
import magmac.app.lang.LazyRule;

record Access(AccessType type, Value receiver, String property) implements Value {
    public static Option<CompileResult<Value>> deserialize(AccessType type, Node node) {
        return Deserializers.deserializeWithType(type.type(), node)
                .map(deserializer -> deserializer.withString("property")
                        .withNode("receiver", Values::deserializeOrError)
                        .complete(tuple -> new Access(type, tuple.right(), tuple.left())));
    }

    public static Rule createAccessRule(String type, String infix, LazyRule value) {
        Rule property = Symbols.createSymbolRule("property");
        return new TypeRule(type, LocatingRule.Last(new NodeRule("receiver", value), infix, property));
    }

    @Override
    public Node serialize() {
        return new MapNode(this.type.type())
                .withNodeSerialized("receiver", this.receiver)
                .withString("property", this.property);
    }
}
