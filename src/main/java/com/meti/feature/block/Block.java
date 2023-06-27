package com.meti.feature.block;

import com.meti.feature.attribute.Attribute;
import com.meti.feature.Node;
import com.meti.feature.attribute.NodeListAttribute;
import com.meti.safe.NativeString;
import com.meti.safe.SafeList;
import com.meti.safe.option.None;
import com.meti.safe.option.Option;
import com.meti.safe.option.Some;

public record Block(SafeList<? extends Node> lines) implements Node {
    @Override
    public boolean is(Object key) {
        return key == Key.Id;
    }

    @Override
    public Option<Attribute> apply(NativeString key) {
        if (key.equalsTo(NativeString.from("lines"))) {
            return Some.apply(new NodeListAttribute(lines));
        } else {
            return None.apply();
        }
    }

    @Override
    public Option<Node> withAttribute(NativeString name, Attribute attribute) {
        if (name.equalsTo(NativeString.from("lines"))) {
            return attribute.asListOfNodes().map(Block::new);
        } else {
            return None.apply();
        }
    }

    enum Key {
        Id
    }
}