package jvm.collect.stream;

import magma.collect.stream.head.EmptyHead;
import magma.collect.stream.head.Head;
import magma.collect.stream.head.HeadedStream;
import magma.collect.stream.head.RangeHead;
import magma.collect.stream.head.SingleHead;
import magma.collect.stream.Stream;
import magma.option.Option;

import java.util.List;

public class Streams {
    public static <T> Stream<T> fromNativeList(List<T> list) {
        return new HeadedStream<>(new RangeHead(list.size())).map(list::get);
    }

    public static <T> Stream<T> empty() {
        return new HeadedStream<>(new EmptyHead<>());
    }

    public static <T> Stream<T> fromOption(Option<T> option) {
        return new HeadedStream<>(option.<Head<T>>map(SingleHead::new).orElseGet(EmptyHead::new));
    }
}
