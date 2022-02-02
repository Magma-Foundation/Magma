package com.meti.app.compile.node.attribute;

import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.Stream;
import com.meti.api.json.JSONNode;

public record TypesAttribute(List<com.meti.app.compile.node.Type> values) implements Attribute {
    @Override
    public JSONNode toJSON() {
        throw new UnsupportedOperationException(getClass() + " cannot be converted into JSON yet.");
    }

    @Override
    public Stream<com.meti.app.compile.node.Type> asStreamOfTypes() {
        return values.stream();
    }
}
