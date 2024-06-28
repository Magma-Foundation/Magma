package magma.compile.attribute;

import magma.api.Tuple;
import magma.api.collect.stream.HeadedStream;
import magma.java.NativeListHead;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record MapAttributes(Map<String, Attribute> values) implements Attributes {
    public MapAttributes() {
        this(Collections.emptyMap());
    }

    @Override
    public <T> Attributes mapValue(String key, Factory<T> factory, Function<T, T> mapper) {
        if (!values.containsKey(key)) return this;

        return factory.fromAttribute(values.get(key)).map(mapper).map(factory::toAttribute)
                .map(inner -> with(key, inner))
                .orElse(this);
    }

    @Override
    public Attributes with(String key, Attribute value) {
        var copy = new HashMap<>(values);
        copy.put(key, value);
        return new MapAttributes(copy);
    }

    @Override
    public Optional<Attribute> apply(String key) {
        return values.containsKey(key)
                ? Optional.of(values.get(key))
                : Optional.empty();
    }

    @Override
    public Attributes merge(Attributes other) {
        var entries = other.streamEntries().collect(Collectors.toSet());

        Attributes current = this;
        for (var entry : entries) {
            current = current.with(entry.left(), entry.right());
        }

        return current;
    }

    @Override
    public Stream<Tuple<String, Attribute>> streamEntries() {
        return values.entrySet()
                .stream()
                .map(entry -> new Tuple<>(entry.getKey(), entry.getValue()));
    }

    @Override
    public String format(int depth) {
        return values.entrySet()
                .stream()
                .map(entry -> "\n" + "\t".repeat(depth + 1) + entry.getKey() + " : " + entry.getValue().format(depth + 1))
                .sorted(Comparator.comparingInt(String::length))
                .collect(Collectors.joining(",", "{", "\n" + "\t".repeat(depth) + "}"));
    }

    @Override
    public boolean has(String name) {
        return this.values.containsKey(name);
    }

    @Override
    public boolean isEmpty() {
        return values.isEmpty();
    }

    @Override
    public Attributes remove(String key) {
        var copy = new HashMap<>(values);
        copy.remove(key);
        return new MapAttributes(copy);
    }

    @Override
    public magma.api.collect.stream.Stream<String> streamKeys() {
        return new HeadedStream<>(new NativeListHead<>(new ArrayList<>(values.keySet())));
    }
}
