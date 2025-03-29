package magma.collect.list;

import magma.collect.stream.HeadedStream;
import magma.collect.stream.RangeHead;
import magma.collect.stream.Stream;

import java.util.ArrayList;
import java.util.List;

public record JavaList<T>(List<T> list) implements List_<T> {
    public JavaList() {
        this(new ArrayList<>());
    }

    @Override
    public Stream<T> stream() {
        return new HeadedStream<>(new RangeHead(list.size()))
                .map(list::get);
    }

    @Override
    public List_<T> add(T element) {
        ArrayList<T> copy = new ArrayList<>(list);
        copy.add(element);
        return new JavaList<>(copy);
    }
}
