package com.meti.app.compile.attribute;

import com.meti.app.compile.node.Text;

public record TextAttribute(Text value) implements Attribute {
    @Override
    public Text asText() {
        return value;
    }
}
