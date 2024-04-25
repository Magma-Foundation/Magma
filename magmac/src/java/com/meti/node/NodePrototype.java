package com.meti.node;

import com.meti.collect.JavaString;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public interface NodePrototype {
    NodePrototype withString(String key, JavaString value);

    Node complete(JavaString name);

    NodePrototype withListOfStrings(String key, List<JavaString> values);

    NodePrototype merge(NodePrototype other);

    Stream<Map.Entry<String, Attribute>> entries();
}
