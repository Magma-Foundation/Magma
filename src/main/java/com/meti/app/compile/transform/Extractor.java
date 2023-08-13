package com.meti.app.compile.transform;

import com.meti.app.compile.Node;
import com.meti.app.compile.attribute.Attribute;
import com.meti.core.None;
import com.meti.core.Option;
import com.meti.core.Tuple;
import com.meti.iterate.Collectors;
import com.meti.java.JavaMap;
import com.meti.java.Map;
import com.meti.java.String_;

public class Extractor {
    private final Node root;
    private final Node format;

    public Extractor(Node root, Node format) {
        this.root = root;
        this.format = format;
    }

    public Option<Map<String_, Attribute>> extract() {
        if (root.is(format.getType())) {

            return format.entries().map(entry ->
                            root.applyOptionally(entry.a().unwrap()).map(value ->
                                    new Tuple<>(entry.a().unwrap(), value)))
                    .collect(Collectors.andRequireAll(JavaMap.toMap()));
        }
        return None.apply();
    }
}
