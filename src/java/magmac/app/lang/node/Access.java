package magmac.app.lang.node;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.LocatingRule;
import magmac.app.compile.rule.NodeRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.lang.LazyRule;
import magmac.app.lang.Deserializers;

record Access(AccessType type, String property, Value caller) implements Value {
    public static Option<CompileResult<Value>> deserialize(AccessType type, Node node) {
        return Deserializers.deserializeWithType(node, type.type()).map(deserializer -> deserializer.withString("property")
                .withNode("instance", Values::deserializeOrError)
                .complete(tuple -> new Access(type, tuple.left(), tuple.right())));
    }

    public static Rule createAccessRule(String type, String infix, LazyRule value) {
        Rule property = Symbols.createSymbolRule("property");
        return new TypeRule(type, LocatingRule.Last(new NodeRule("instance", value), infix, property));
    }

    @Override
    public Node serialize() {
        return new MapNode();
    }
}
