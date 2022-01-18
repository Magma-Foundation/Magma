package com.meti.app.compile.attribute;

import com.meti.app.compile.text.Text;

public record TextAttribute(Text value) implements Attribute {
    @Override
    public Text asText() {
        return value;
    }
}
