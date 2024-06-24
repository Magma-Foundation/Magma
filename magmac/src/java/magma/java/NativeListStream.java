package magma.java;

import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;
import magma.api.collect.stream.Head;

import java.util.List;

class NativeListStream<T> implements Head<T> {
    private final List<T> list;
    private int counter = 0;

    public NativeListStream(List<T> list) {
        this.list = list;
    }

    @Override
    public Option<T> head() {
        if (counter >= list.size()) return new None<>();

        var value = list.get(counter);
        counter++;
        return new Some<>(value);
    }
}
