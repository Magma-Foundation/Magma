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

    public static <T> List<T> repeat(T element, int size) {
        List<T> copy = Lists.empty();
        var i = 0;
        while (i < size) {
            copy = copy.addLast(element);
            i++;
        }
        return copy;
    }
}
