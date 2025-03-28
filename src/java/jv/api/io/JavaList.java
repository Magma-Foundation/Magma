package jv.api.io;

import jv.api.collect.HeadedStream;
import magma.api.collect.List_;
import magma.api.collect.RangeHead;
import magma.api.collect.Stream;

import java.util.List;

public record JavaList<T>(List<T> list) implements List_<T> {
    @Override
    public Stream<T> stream() {
        return new HeadedStream<>(new RangeHead(list.size()))
                .map(list::get);
    }
}
