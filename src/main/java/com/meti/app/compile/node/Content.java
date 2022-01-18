package com.meti.app.compile.node;

import com.meti.app.compile.attribute.Attribute;
import com.meti.app.compile.attribute.AttributeException;
import com.meti.app.compile.attribute.TextAttribute;
import com.meti.app.compile.text.RootText;
import com.meti.app.compile.text.Text;

public record Content(Text text) implements Node {
    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        if (type == Attribute.Type.Value) return new TextAttribute((RootText) text);
        throw new AttributeException("No attribute exists of name: " + type);
    }

    @Override
    public boolean is(Type type) {
        return type == Node.Type.Content;
    }
}
