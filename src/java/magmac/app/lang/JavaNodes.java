package magmac.app.lang;

import magmac.api.collect.list.List;
import magmac.app.lang.node.FunctionSegmentValue;
import magmac.app.lang.node.JavaType;
import magmac.app.lang.node.Value;

public class JavaNodes {
    public interface Caller {
    }

    public interface Argument extends Serializable {
    }

    public record Construction(JavaType type) implements Caller {
    }

    public record Invokable(Caller caller, List<Argument> arguments) implements Value, FunctionSegmentValue {
    }
}
