package com.meti.app.compile.common;

import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.StreamException;
import com.meti.api.json.ArrayNode;
import com.meti.api.json.EmptyNode;
import com.meti.api.json.JSONNode;
import com.meti.api.json.ObjectNode;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.text.Input;

public class EmptyField extends Field {
    public EmptyField(Input name, Node type, Flag... flags) {
        this(name, type, List.apply(flags));
    }

    public EmptyField(Input name, Node type, List<Flag> flags) {
        super(flags, name, type);
    }

    @Override
    public JSONNode toJSON() {
        try {
            var flags = this.flags.stream()
                    .map(Enum::name)
                    .foldRight(new ArrayNode.Builder(), ArrayNode.Builder::addString)
                    .build();

            return new ObjectNode()
                    .add("name", name.toOutput().compute())
                    .add("type", type.toJSON())
                    .add("flags", flags);
        } catch (StreamException e) {
            return new EmptyNode();
        }
    }

    @Override
    protected Field complete(Input name, Node type) {
        return new EmptyField(name, type, flags);
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Declaration;
    }
}
