package com.meti.app.compile.common;

import com.meti.app.compile.node.Primitive;
import com.meti.app.compile.text.RootText;

public class Fields {
    public record Builder(RootText name) {
        public Builder() {
            this(new RootText(""));
        }

        public EmptyField build() {
            return new EmptyField(name(), Primitive.Void, Field.Flag.Def);
        }

        public Builder withName(String name) {
            return new Builder(new RootText(name));
        }
    }
}
