package magma.api.contain.stream;

import magma.api.option.Option;

import static magma.api.option.None.None;

public class EmptyHead<T> implements Head<T> {
    public static <T> Head<T> EmptyHead() {
        return new EmptyHead<>();
    }

    @Override
    public Option<T> head() {
        return None();
    }
}
