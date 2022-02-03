package com.meti.app.compile.node;

import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.StreamException;
import com.meti.api.json.ArrayNode;
import com.meti.api.json.JSONException;
import com.meti.api.json.JSONNode;
import com.meti.api.json.ObjectNode;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.attribute.TypesAttribute;

public class Union extends AbstractNode implements Type {
    private final List<Type> options;

    public Union(Type... options) {
        this(List.apply(options));
    }

    public Union(List<Type> options) {
        this.options = options;
    }

    @Override
    public boolean is(Category category) {
        return category == Category.Union;
    }

    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        if (type == Attribute.Type.Children) return new TypesAttribute(options);
        throw new AttributeException(type);
    }

    @Override
    public JSONNode toJSON() throws JSONException {
        try {
            var options = this.options.stream()
                    .foldRight(new ArrayNode.Builder(), ArrayNode.Builder::addJSON)
                    .build();
            return new ObjectNode().addObject("options", options);
        } catch (StreamException e) {
            throw new JSONException(e);
        }
    }

    @Override
    public boolean isAssignableTo(Type other) throws AttributeException {
        try {
            return options.stream().anyMatch(other::isInstanceOf);
        } catch (StreamException e) {
            throw new AttributeException(e);
        }
    }

    @Override
    public Type reduce() throws AttributeException {
        return options.first().orElseThrow(() -> new AttributeException("Union is empty."));
    }
}
