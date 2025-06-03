package magmac.app.lang.node;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.LocatingRule;
import magmac.app.compile.rule.NodeRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.SuffixRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.lang.Destructors;
import magmac.app.lang.LazyRule;

public record IndexNode(JavaValue parent, JavaValue argument) implements JavaValue, TypeScriptValue {
    public static Rule createIndexRule(LazyRule value) {
        NodeRule parent = new NodeRule("parent", value);
        NodeRule argument = new NodeRule("argument", value);
        return new TypeRule("index", new StripRule(new SuffixRule(LocatingRule.First(parent, "[", argument), "]")));
    }

    public static Option<CompileResult<IndexNode>> deserialize(Node value) {
        return Destructors.destructWithType("index", value).map(destructor -> {
            return destructor.withNode("parent", Values::deserializeOrError)
                    .withNode("argument", Values::deserializeOrError)
                    .complete(tuple -> new IndexNode(tuple.left(), tuple.right()));
        });
    }

    @Override
    public Node serialize() {
        return new MapNode(this.getClass().getName());
    }
}
