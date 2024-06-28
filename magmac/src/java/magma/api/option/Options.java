package magma.api.option;

import magma.api.Tuple;

public class Options {
    public static <A, B> Option<Tuple<A, B>> invertRight(Tuple<A, Option<B>> tuple) {
        return tuple.right().map(inner -> new Tuple<>(tuple.left(), inner));
    }
}
