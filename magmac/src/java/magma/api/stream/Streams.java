package magma.api.stream;

import magma.api.option.Option;
import magma.java.JavaOptionals;

import java.util.List;
import java.util.Optional;

public class Streams {

    public static <T> Stream<T> fromNativeList(List<T> list) {
        return new AbstractStream<T>(new Head<T>() {
            @Override
            public Option<T> head() {
                return JavaOptionals.fromNative(head0());
            }

            private int counter = 0;

            private Optional<T> head0() {
                if (counter >= list.size()) return Optional.empty();
                var next = list.get(counter);
                counter++;
                return Optional.of(next);
            }
        });
    }
}
