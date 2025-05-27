package magmac.api.iter.collect;

import java.util.ArrayList;
import java.util.List;

public record ListCollector<T>() implements Collector<T, List<T>> {
    @Override
    public List<T> createInitial() {
        return new ArrayList<>();
    }

    @Override
    public List<T> fold(List<T> current, T element) {
        current.add(element);
        return current;
    }
}
