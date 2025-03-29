package magma.collect.list;

import java.util.ArrayList;
import java.util.List;

public class Lists {
    public static <T> List<T> toNative(List_<T> list) {
        return list.stream().fold(new ArrayList<T>(), (current, element) -> {
            current.add(element);
            return current;
        });
    }
}
