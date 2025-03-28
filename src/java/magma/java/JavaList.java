package magma.java;

import magma.list.List_;
import magma.list.RangeHead;
import magma.list.Stream;

import java.util.List;

public record JavaList<T>(List<T> list) implements List_<T> {
    @Override
    public Stream<T> stream() {
        return new HeadedStream<>(new RangeHead(list.size()))
                .map(list::get);
    }
}
