package magma.api.collect.stream;

import magma.api.option.None;
import magma.api.option.Option;

public class EmptyHead<T> implements Head<T> {
    @Override
    public Option<T> head() {
        return new None<>();
    }
}
