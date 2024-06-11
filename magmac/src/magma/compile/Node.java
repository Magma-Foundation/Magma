package magma.compile;

import magma.api.Tuple;
import magma.compile.attribute.Attribute;

import java.util.Optional;
import java.util.stream.Stream;

public interface Node {
    Node with(String key, Attribute value);

    Optional<Attribute> apply(String key);

    Node merge(Node other);

    Stream<Tuple<String, Attribute>> streamEntries();
}
