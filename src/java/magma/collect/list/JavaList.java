package magma.collect.list;

import magma.collect.stream.RangeHead;
import magma.collect.stream.HeadedStream;
import magma.collect.stream.Stream;

import java.util.List;

public record JavaList<T>(List<T> list) implements List_<T> {
    @Override
    public Stream<T> stream() {
        return new HeadedStream<>(new RangeHead(list.size()))
                .map(list::get);
    }
}
