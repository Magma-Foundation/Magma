package com.meti.app.compile.node.attribute;

import com.meti.api.json.JSONNode;
import com.meti.app.source.Packaging;

public record PackageAttribute(Packaging packaging_) implements Attribute {
    @Override
    public Packaging asPackaging() {
        return packaging_;
    }

    @Override
    public JSONNode toJSON() {
        throw new UnsupportedOperationException(getClass() + " cannot be converted into JSON yet.");
    }
}
