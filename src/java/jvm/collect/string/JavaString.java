package jvm.collect.string;

import jvm.collect.stream.Streams;
import magma.collect.stream.Stream;
import magma.collect.string.String_;

public record JavaString(String value) implements String_ {
    @Override
    public Stream<Character> stream() {
        return Streams.fromString(value());
    }
}
