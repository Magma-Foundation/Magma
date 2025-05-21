package jvm.api.collect.list;

import magma.annotate.Actual;
import magma.annotate.Namespace;
import magma.api.collect.list.Iterable;
import magma.api.collect.list.List;

import java.util.ArrayList;
import java.util.Arrays;

@Namespace
public final class Lists {
    @Actual
    public static <T> List<T> empty() {
        return new JVMList<T>(new ArrayList<T>());
    }

    @Actual
    public static <T> List<T> of(T... elements) {
        return new JVMList<T>(new ArrayList<T>(Arrays.asList(elements)));
    }
}
