package com.meti.node;

import com.meti.collect.JavaString;

import java.util.List;

public interface NodePrototype {
    NodePrototype withString(String key, JavaString value);

    Node complete(JavaString name);

    NodePrototype withListOfStrings(String key, List<JavaString> values);
}
