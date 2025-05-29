package magmac.app.lang.java;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.ExactRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.StripRule;
import magmac.app.compile.rule.TypeRule;
import magmac.app.lang.java.function.FunctionSegment;
import magmac.app.lang.java.function.Parameter;
import magmac.app.lang.java.root.JavaRootSegment;
import magmac.app.lang.java.structure.StructureMember;
import magmac.app.lang.java.value.Argument;

public class Whitespace implements Argument, FunctionSegment, Parameter, StructureMember, JavaRootSegment {
    public static Option<CompileResult<Whitespace>> deserialize(Node node) {
        return node.deserializeWithType("whitespace").map(deserializer -> {
            return deserializer.complete(Whitespace::new);
        });
    }

    public static Rule createWhitespaceRule() {
        return new TypeRule("whitespace", new StripRule(new ExactRule("")));
    }
}
