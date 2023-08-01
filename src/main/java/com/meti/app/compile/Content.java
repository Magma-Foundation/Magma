package com.meti.app.compile;

import com.meti.app.compile.attribute.Attribute;
import com.meti.app.compile.attribute.StringAttribute;
import com.meti.core.None;
import com.meti.core.Option;
import com.meti.core.Some;
import com.meti.iterate.Iterator;
import com.meti.iterate.Iterators;
import com.meti.java.ImmutableKey;
import com.meti.java.Key;
import com.meti.java.String_;

import static com.meti.java.JavaString.fromSlice;

public record Content(String_ value) implements Node {
    public static Node ofContent(String_ input) {
        return new Content(input);
    }

    @Override
    public String toString() {
        return "'" + value.toString() + "'";
    }

    @Override
    public Iterator<Key<String_>> ofGroup(Group group) {
        return Iterators.empty();
    }

    @Override
    public boolean is(String_ name) {
        return name.equalsTo(fromSlice("content"));
    }

    @Override
    public Option<Node> withOptionally(String_ key, Attribute attribute) {
        return key.equalsTo(fromSlice("value")) ? Some.apply(with(new ImmutableKey<>(key), attribute)) : None.apply();
    }

    @Override
    public Node with(Key<String_> key, Attribute attribute) {
        return new Content(attribute.asString().unwrapOrElse(fromSlice("")));
    }

    @Override
    public Attribute apply(Key<String_> key) {
        return new StringAttribute(value);
    }

    @Override
    public Option<Attribute> applyOptionally(String_ key) {
        return key.equalsTo(fromSlice("value"))
                ? Some.apply(new StringAttribute(value))
                : None.apply();
    }
}
