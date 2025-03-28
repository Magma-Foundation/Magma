package jvm.api.collect;

import magma.api.collect.EmptyHead;
import magma.api.collect.RangeHead;
import magma.api.collect.Stream;

import java.util.List;

public class Streams {
    public static <T> Stream<T> stream(List<T> list) {
        return new HeadedStream<>(new RangeHead(list.size())).map(list::get);
    }

    public static <T> Stream<T> empty() {
        return new HeadedStream<>(new EmptyHead<>());
    }
}
