package com.meti.compile.common;

import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.attribute.NodeListAttribute;
import com.meti.compile.attribute.TextAttribute;
import com.meti.compile.node.Node;
import com.meti.compile.node.Text;

import java.util.List;

public record Structure(Text name, List<Node> fields) implements Node {
    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        if (type == Attribute.Type.Name) return new TextAttribute(name);
        if (type == Attribute.Type.Fields) return new NodeListAttribute(fields);
        throw new AttributeException(type);
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Structure;
    }
}
