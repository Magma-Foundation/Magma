package com.meti.app.compile;

import com.meti.app.Attribute;
import com.meti.core.None;
import com.meti.core.Option;
import com.meti.core.Some;
import com.meti.iterate.Iterator;
import com.meti.java.JavaMap;
import com.meti.java.Key;
import com.meti.java.Map;
import com.meti.java.String_;

public record MapNode(String_ name1, Map<String_, Attribute> attributes) implements Node {
    public MapNode(String_ name) {
        this(name, JavaMap.empty());
    }

    @Override
    public Option<Attribute> applyOptionally(String_ key) {
        return attributes.applyOptionally(key);
    }


    @Override
    public Option<Node> withOptionally(String_ key, Attribute attribute) {
        if (attributes.hasKey(key)) {
            return Some.apply(new MapNode(name1, attributes.insert(key, attribute)));
        } else {
            return None.apply();
        }
    }

    @Override
    public Node with(Key<String_> key, Attribute attribute) {
        return key.peek(keyValue -> new MapNode(name1, attributes.insert(keyValue, attribute)));
    }

    @Override
    public boolean is(String_ name) {
        return this.name1.equalsTo(name);
    }

    @Override
    public Attribute apply(Key<String_> key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<Key<String_>> ofGroup(Group group) {
        throw new UnsupportedOperationException();
    }
}
