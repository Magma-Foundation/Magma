package magma.collect.stream.head;

import magma.option.Option;

public interface Head<T> {
    Option<T> next();
}
