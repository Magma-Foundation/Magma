package com.meti.compile;

import com.meti.api.collect.JavaString;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;

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
        return value.prepend("<Content>")
                .append("</Content>");
    }
}
