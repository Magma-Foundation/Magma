package com.meti.app.compile;

import com.meti.app.compile.attribute.Attribute;
import com.meti.core.None;
import com.meti.core.Option;
import com.meti.core.Some;
import com.meti.core.Tuple;
import com.meti.iterate.Iterator;
import com.meti.java.*;

public record MapNode(String_ name1, Map<String_, Attribute> attributes) implements Node {
    public MapNode(String_ name) {
        this(name, JavaMap.empty());
    }

    @Override
    public Option<Attribute> applyOptionally(String_ key) {
        return attributes.applyOptionally(key);
    }


    @Override
    public String toString() {
        return "{" +
               "name: '" + name1.unwrap() + "', " +
               "attributes: " + attributes.toString() +
               "}";
    }

    @Override
    public Option<Node> withOptionally(String_ key, Attribute attribute) {
        return attributes.hasKey(key)
                ? Some.apply(new MapNode(name1, attributes.insert(key, attribute)))
                : None.apply();
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
        return attributes.apply(key);
    }

    @Override
    public Iterator<Key<String_>> ofGroup(Group group) {
        return attributes.iter()
                .filter(tuple -> tuple.b().is(group))
                .map(Tuple::a)
                .map(ImmutableKey::new);
    }

    @Override
    public String_ getType() {
        throw new UnsupportedOperationException();
    }
}
