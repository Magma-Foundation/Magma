package com.meti.compile.attribute;

import com.meti.compile.node.Text;

public record TextAttribute(Text value) implements Attribute {
    @Override
    public Text asInput() {
        return value;
    }
}
