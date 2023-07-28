package com.meti.app.compile;

import com.meti.app.Attribute;
import com.meti.core.None;
import com.meti.core.Option;
import com.meti.core.Some;
import com.meti.java.Map;
import com.meti.java.String_;

public record MapNode(Map<String_, Attribute> attributes) implements Node {
    @Override
    public Option<Attribute> apply(String_ key) {
        return attributes.applyOptionally(key);
    }


    @Override
    public Option<Node> with(String_ key, Attribute attribute) {
        if (attributes.hasKey(key)) {
            return Some.apply(new MapNode(attributes.insert(key, attribute)));
        } else {
            return None.apply();
        }
    }

    @Override
    public boolean is(String_ name) {
        throw new UnsupportedOperationException();
    }
}
