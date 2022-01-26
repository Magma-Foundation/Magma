package com.meti.app.compile.node.attribute;

import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.Stream;
import com.meti.api.json.JSONNode;
import com.meti.app.compile.common.Field;

public record FlagsAttribute(List<Field.Flag> flags) implements Attribute {
    @Override
    public Stream<Field.Flag> asStreamOfFlags1() throws AttributeException {
        return flags.stream();
    }

    @Override
    public JSONNode toJSON() {
        throw new UnsupportedOperationException(getClass() + " cannot be converted into JSON yet.");
    }
}
