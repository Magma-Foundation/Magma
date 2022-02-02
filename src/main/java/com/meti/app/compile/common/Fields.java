package com.meti.app.compile.common;

import com.meti.api.collect.java.List;
import com.meti.app.compile.feature.scope.Declaration;
import com.meti.app.compile.node.InputNode;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.primitive.Primitive;
import com.meti.app.compile.text.RootText;

public class Fields {
    public record Builder(RootText name, Node type, List<Definition.Flag> flags) {
        public Builder() {
            this(new RootText(""), Primitive.Void, List.createList());
        }

        public Declaration build() {
            return new Declaration(name, type, flags);
        }

        public Builder withFlag(Definition.Flag flag) {
            return new Builder(name, type, flags.add(flag));
        }

        public Builder withName(String name) {
            return new Builder(new RootText(name), type, flags);
        }

        public Builder withType(String type) {
            return new Builder(name, new InputNode(new RootText(type)), flags);
        }

        public Builder withType(Node type) {
            return new Builder(name, type, flags);
        }
    }
}
