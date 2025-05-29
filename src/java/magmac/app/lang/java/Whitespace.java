package magmac.app.lang.java;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.java.function.FunctionSegment;
import magmac.app.lang.java.function.Parameter;
import magmac.app.lang.java.structure.ClassMember;
import magmac.app.lang.java.value.Argument;

public class Whitespace implements Argument, FunctionSegment, Parameter, ClassMember {
    public static Option<CompileResult<Whitespace>> deserialize(Node node) {
        return node.deserializeWithType("whitespace").map(deserializer -> {
            return deserializer.complete(Whitespace::new);
        });
    }
}
