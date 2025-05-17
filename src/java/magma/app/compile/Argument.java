package magma.app.compile;

import magma.api.option.Option;
import magma.app.Value;

public interface Argument {
    Option<Value> toValue();
}
