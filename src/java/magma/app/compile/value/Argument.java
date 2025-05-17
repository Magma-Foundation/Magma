package magma.app.compile.value;

import magma.api.option.Option;

public interface Argument {
    Option<Value> toValue();
}
