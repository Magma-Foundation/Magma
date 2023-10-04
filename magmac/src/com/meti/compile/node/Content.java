package com.meti.compile.node;

import com.meti.api.collect.JavaString;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.StringAttribute;

public class Content implements Node {
    private final JavaString value;
    private final JavaString type;

    public Content(JavaString value, JavaString type) {
        this.value = value;
        this.type = type;
    }

    public static Node from(JavaString value, JavaString type) {
        return new Content(value, type);
    }

    public static Node from(String slice, String type) {
        return from(JavaString.apply(slice), JavaString.apply(type));
    }

    @Override
    public boolean is(String name) {
        return name.equals("content");
    }

    @Override
    public Option<Attribute> apply(JavaString name) {
        if (name.equalsToSlice("value")) {
            return Some.apply(new StringAttribute(this.value));
        } else if (name.equalsToSlice("type")) {
            return Some.apply(new StringAttribute(this.type));
        } else {
            return None.apply();
        }
    }

    @Override
    public JavaString toXML() {
        return JavaString.apply("<Content type=\"" + this.type.value() + "\"/>");
    }
}
