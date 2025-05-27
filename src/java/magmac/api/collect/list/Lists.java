package magmac.api.collect.list;

import java.util.ArrayList;
import java.util.Arrays;

public final class Lists {
    public static <T> List<T> of(T... elements) {
        return new JVMList<>(new ArrayList<>(Arrays.asList(elements)));
    }

    public static <T> List<T> empty() {
        return new JVMList<>();
    }
}
