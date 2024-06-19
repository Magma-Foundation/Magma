package magma.api.stream;

import magma.api.option.Option;

public interface Head<T> {
    Option<T> head();
}
