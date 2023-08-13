package com.meti.app.compile.transform;

import com.meti.app.compile.Node;
import com.meti.app.compile.attribute.Attribute;
import com.meti.app.compile.attribute.ExtractAttribute;
import com.meti.core.None;
import com.meti.core.Option;
import com.meti.core.Some;
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
            return format.entries().map(entry -> {
                        var formatKey = entry.a().unwrap();
                        var formatAttribute = entry.b();
                        return root.applyOptionally(formatKey).flatMap(rootAttribute -> {
                            if (formatAttribute.equalsTo(ExtractAttribute.Extract)) {
                                return Some.apply(JavaMap.<String_, Attribute>empty().insert(formatKey, rootAttribute));
                            } else if (formatAttribute.equalsTo(rootAttribute)) {
                                return Some.apply(JavaMap.<String_, Attribute>empty());
                            } else {
                                return None.apply();
                            }
                        });
                    })
                    .collect(Collectors.andRequireAll(JavaMap.toIntersection()));
        }
        return None.apply();
    }
}
