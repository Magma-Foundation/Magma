package com.meti.app.compile.attribute;

import com.meti.app.source.Packaging;

public record PackageAttribute(Packaging packaging_) implements Attribute {
    @Override
    public Packaging asPackaging() {
        return packaging_;
    }
}
