package com.meti.app.compile.feature.scope;

import com.meti.api.json.JSONNode;
import com.meti.api.json.ObjectNode;
import com.meti.app.compile.node.AbstractNode;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.attribute.InputAttribute;
import com.meti.app.compile.text.Input;
import com.meti.app.compile.text.RootText;

import java.util.Objects;

public final class Variable extends AbstractNode {
    private final Input value;

    public Variable(String value) {
        this(new RootText(value));
    }

    public Variable(Input value) {
        this.value = value;
    }

    @Override
    public Attribute apply(Attribute.Category category) throws AttributeException {
        if (category == Attribute.Category.Value) return new InputAttribute(value);
        throw new AttributeException(category);
    }

    @Override
    public boolean is(Category category) {
        return category == Node.Category.Variable;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Variable) obj;
        return Objects.equals(this.value, that.value);
    }

    @Override
    public Node with(Attribute.Category category, Attribute attribute) throws AttributeException {
        return category == Attribute.Category.Value
                ? new Variable(attribute.asInput())
                : this;
    }

    @Override
    public JSONNode toJSON() {
        return new ObjectNode().addString("value", value.toOutput().compute());
    }
}
