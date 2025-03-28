package jvm.api.collect;

import magma.api.collect.Collector;

public class Sets {
    public static <T> Collector<T,magma.api.collect.Set_<T>> collectToSet() {
        return new JavaSetCollector<T>();
    }
}
