package magma.api.collect.stream;

import magma.api.option.Option;
import magma.lang.Instance;

import static magma.api.option.None.None;

public class EmptyHead<T> implements Head<T> {
    private EmptyHead() {
    }

    @Instance
    public static <T> Head<T> EmptyHead() {
        return new EmptyHead<>();
    }

    @Override
    public Option<T> head() {
        return None();
    }
}
