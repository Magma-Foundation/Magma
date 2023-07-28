package com.meti.app.compile;

import com.meti.app.Attribute;
import com.meti.core.None;
import com.meti.core.Option;
import com.meti.core.Some;
import com.meti.java.JavaMap;
import com.meti.java.Map;
import com.meti.java.String_;

public record MapNode(String_ name1, Map<String_, Attribute> attributes) implements Node {
    public MapNode(String_ name) {
        this(name, JavaMap.empty());
    }

    public MapNode(String_ name1, Map<String_, Attribute> attributes) {
        this.name1 = name1;
        this.attributes = attributes;
    }

    @Override
    public Option<Attribute> applyOptionally(String_ key) {
        return attributes.applyOptionally(key);
    }


    @Override
    public Option<Node> with(String_ key, Attribute attribute) {
        if (attributes.hasKey(key)) {
            return Some.apply(new MapNode(name1, attributes.insert(key, attribute)));
        } else {
            return None.apply();
        }
    }

    @Override
    public boolean is(String_ name) {
        return this.name1.equalsTo(name);
    }
}
