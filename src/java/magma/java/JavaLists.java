package magma.java;

import magma.list.List_;

import java.util.ArrayList;
import java.util.List;

public class JavaLists {
    public static <T> List<T> unwrap(List_<T> list) {
        return list.stream().fold(new ArrayList<T>(), (current, element) -> {
            current.add(element);
            return current;
        });
    }

    public static List_<String> wrap(List<String> list) {
        return new JavaList<>(list);
    }
}
