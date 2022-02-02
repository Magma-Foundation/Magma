package com.meti.app.compile.node.attribute;

import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.Stream;
import com.meti.api.json.JSONNode;
import com.meti.app.compile.common.Definition;

public record FlagsAttribute(List<Definition.Flag> flags) implements Attribute {
    @Override
    public Stream<Definition.Flag> asStreamOfFlags() throws AttributeException {
        return flags.stream();
    }

    @Override
    public JSONNode toJSON() {
        throw new UnsupportedOperationException(getClass() + " cannot be converted into JSON yet.");
    }
}
