package magma.api.collect.stream;

import magma.api.option.Option;
import magma.lang.Instantiator;

import static magma.api.option.None.None;

public class EmptyHead<T> implements Head<T> {
    @Instantiator
    public static <T> Head<T> EmptyHead() {
        return new EmptyHead<>();
    }

    @Override
    public Option<T> head() {
        return None();
    }
}
