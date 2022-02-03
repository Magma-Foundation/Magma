package com.meti.app.compile.common;

import com.meti.api.collect.java.List;
import com.meti.app.compile.feature.scope.Declaration;
import com.meti.app.compile.node.InputType;
import com.meti.app.compile.node.Type;
import com.meti.app.compile.primitive.Primitive;
import com.meti.app.compile.text.RootText;

public class Fields {
    public record Builder(RootText name, Type type, List<Definition.Flag> flags) {
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
            return new Builder(name, new InputType(new RootText(type)), flags);
        }

        public Builder withType(Type type) {
            return new Builder(name, type, flags);
        }
    }
}
