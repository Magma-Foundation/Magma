package magmac.app.lang.node;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.ExactRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.lang.Destructors;

public final class Whitespace implements
        Argument,
        FunctionSegment,
        JavaRootSegment,
        JavaParameter,
        JavaStructureMember,
        TypeScriptRootSegment,
        TypescriptStructureMember,
        TypeScriptParameter {
    public static Option<CompileResult<Whitespace>> deserialize(Node node) {
        return Destructors.destructWithType("whitespace", node).map(deserializer -> deserializer.complete(Whitespace::new));
    }

    public static Rule createWhitespaceRule() {
        return new TypeRule("whitespace", new StripRule(new ExactRule("")));
    }

    @Override
    public Node serialize() {
        return new MapNode("whitespace");
    }
}
