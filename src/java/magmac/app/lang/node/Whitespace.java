package magmac.app.lang.node;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.ExactRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.lang.Deserializers;

public final class Whitespace implements Argument, FunctionSegment, Parameter, StructureMember, JavaRootSegment {
    public static Option<CompileResult<Whitespace>> deserialize(Node node) {
        return Deserializers.deserializeWithType(node, "whitespace").map(deserializer -> deserializer.complete(Whitespace::new));
    }

    public static Rule createWhitespaceRule() {
        return new TypeRule("whitespace", new StripRule(new ExactRule("")));
    }

    @Override
    public Node serialize() {
        return new MapNode("whitespace");
    }
}
