package com.meti.compile.node;

import com.meti.api.collect.JavaString;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.StringAttribute;

public class Content implements Node {
    private final JavaString value;

    public Content(JavaString value) {
        this.value = value;
    }

    public static Node from(JavaString value) {
        return new Content(value);
    }

    public static Node from(String slice) {
        return new Content(JavaString.apply(slice));
    }

    @Override
    public boolean is(String name) {
        return name.equals("content");
    }

    @Override
    public Option<Attribute> apply(JavaString name) {
        if (name.equalsToSlice("value")) {
            return Some.apply(new StringAttribute(this.value));
        } else {
            return None.apply();
        }
    }

    @Override
    public JavaString toXML() {
        var truncated = value.truncate(5).append("...");
        return truncated.prepend("<Content>\"").append("\"</Content>");
    }
}
