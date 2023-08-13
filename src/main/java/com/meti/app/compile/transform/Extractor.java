package com.meti.app.compile.transform;

import com.meti.app.compile.Node;
import com.meti.app.compile.attribute.Attribute;
import com.meti.core.None;
import com.meti.core.Option;
import com.meti.core.Some;
import com.meti.java.JavaMap;
import com.meti.java.Map;
import com.meti.java.String_;

public class Extractor {
    private final Node left;
    private final Node right;

    public Extractor(Node left, Node right) {
        this.left = left;
        this.right = right;
    }

    public Option<Map<String_, Attribute>> extract() {
        if (left.is(right.getType())) {
            return Some.apply(JavaMap.empty());
        }
        return None.apply();
    }
}
