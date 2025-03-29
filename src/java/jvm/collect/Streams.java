package jvm.collect;

import magma.collect.stream.EmptyHead;
import magma.collect.stream.HeadedStream;
import magma.collect.stream.RangeHead;
import magma.collect.stream.Stream;

import java.util.List;

public class Streams {
    public static <T> Stream<T> fromNativeList(List<T> list) {
        return new HeadedStream<>(new RangeHead(list.size())).map(list::get);
    }

    public static <T> Stream<T> empty() {
        return new HeadedStream<>(new EmptyHead<>());
    }
}
