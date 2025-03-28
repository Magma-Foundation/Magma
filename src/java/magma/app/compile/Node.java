package magma.app.compile;

import magma.api.collect.Stream;
import magma.api.option.Option;
import magma.api.result.Tuple;

public interface Node {
    Node withString(String propertyKey, String propertyValue);

    Option<String> findString(String propertyKey);

    Node merge(Node other);

    String display();

    Stream<Tuple<String, String>> streamStrings();
}
