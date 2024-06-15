package magma.api;

import java.util.List;
import java.util.Optional;

public class Streams {

    public static <T> Stream<T> fromNativeList(List<T> list) {
        return new AbstractStream<T>() {
            private int counter = 0;

            @Override
            public Optional<T> head() {
                if (counter >= list.size()) return Optional.empty();
                var next = list.get(counter);
                counter++;
                return Optional.of(next);
            }
        };
    }
}
