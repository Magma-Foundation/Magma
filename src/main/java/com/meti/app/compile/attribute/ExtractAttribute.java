package com.meti.app.compile.attribute;

import com.meti.app.compile.Node;

public class ExtractAttribute implements Attribute {

    public static final Attribute Extract = new ExtractAttribute();

    private ExtractAttribute() {
    }

    @Override
    public boolean is(Node.Group group) {
        return group == Node.Group.Extract;
    }
}
