package magma.api.collect.head;

import magma.api.option.Option;

public interface Head<T> {
    Option<T> next();
}
