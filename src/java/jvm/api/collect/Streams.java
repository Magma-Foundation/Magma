package jvm.api.collect;

import magma.api.collect.RangeHead;
import magma.api.collect.Stream;

import java.util.List;

public class Streams {
    public static <T> Stream<T> streamList(List<T> list) {
        return new HeadedStream<>(new RangeHead(list.size())).map(list::get);
    }

    public static <T> Stream<T> streamArray(T[] array) {
        return new HeadedStream<>(new RangeHead(array.length)).map(index -> array[index]);
    }

    public static Stream<Character> streamString(String value) {
        return new HeadedStream<>(new RangeHead(value.length())).map(value::charAt);
    }
}
