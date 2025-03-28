package magma.api.collect;

import magma.api.option.None;
import magma.api.option.Option;

public class EmptyHead<T> implements Head<T> {
    @Override
    public Option<T> next() {
        return new None<>();
    }
}
