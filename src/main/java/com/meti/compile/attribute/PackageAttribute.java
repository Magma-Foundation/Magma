package com.meti.compile.attribute;

import com.meti.source.Packaging;

public record PackageAttribute(Packaging packaging_) implements Attribute {
    @Override
    public Packaging asPackaging() {
        return packaging_;
    }
}
