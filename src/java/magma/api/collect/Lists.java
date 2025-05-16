package magma.api.collect;

import magma.annotate.Actual;
import magma.annotate.Namespace;

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
