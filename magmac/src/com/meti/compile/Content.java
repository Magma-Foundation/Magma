package com.meti.compile;

import com.meti.api.iterator.Iterator;
import com.meti.api.iterator.Iterators;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;

import java.util.Objects;

public final class Content implements Node {
    private final String value;

    private Content(String value) {
        this.value = value;
    }

    public static Node of(String value) {
        return new Content(value);
    }


    @Override
    public boolean is(String type) {
        return type.equals("__content__");
    }

    @Override
    public Option<String> getString(String name) {
        return None.apply();
    }

    @Override
    public Iterator<Tuple<String, Node>> getNodes() {
        return Iterators.empty();
    }

    @Override
    public Option<Node> withNode(String name, Node value) {
        return None.apply();
    }

    @Override
    public Option<String> getValue() {
        return Some.apply(value);
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Content) obj;
        return Objects.equals(this.value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Content[" +
               "value=" + value + ']';
    }

}
