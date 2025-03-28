package magma.api.collect;

import magma.api.option.Option;

public interface Head<T> {
    Option<T> next();
}
