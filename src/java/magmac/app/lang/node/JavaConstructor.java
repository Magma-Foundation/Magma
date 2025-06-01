package magmac.app.lang.node;

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.Destructors;

public record JavaConstructor(String name) implements JavaMethodHeader {
    public static Option<CompileResult<JavaMethodHeader>> deserialize(Node node) {
        return Destructors.destructWithType("constructor", node).map(constructor -> constructor.withString("name").complete(JavaConstructor::new));
    }
}
