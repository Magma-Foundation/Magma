package magma.compile.attribute;

import magma.api.Tuple;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record MapAttributes(Map<String, Attribute> values) implements Attributes {
    public MapAttributes() {
        this(Collections.emptyMap());
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
}
