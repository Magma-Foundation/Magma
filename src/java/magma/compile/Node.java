package magma.compile;

import magma.option.Option;

public interface Node {
    Node withString(String propertyKey, String propertyValue);

    Option<String> findString(String propertyKey);
}
