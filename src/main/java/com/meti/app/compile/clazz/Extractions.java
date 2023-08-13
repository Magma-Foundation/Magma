package com.meti.app.compile.clazz;

import com.meti.app.compile.attribute.Attribute;
import com.meti.app.compile.attribute.ExtractAttribute;
import com.meti.java.*;

public class Extractions {
    private Map<String_, Extraction> mapper = new JavaMap<>();

    public Extraction extract(String name) {
        var ownedName = JavaString.fromSlice(name);
        var extraction = new Extraction(ownedName, ExtractAttribute.Extract);
        mapper = mapper.insert(ownedName, extraction);
        return extraction;
    }

    public record Extraction(String_ name1, Attribute attribute1) {
        public String_ name() {
            return name1;
        }

        public Key<String_> key() {
            return new ImmutableKey<>(name1);
        }

        public Attribute attribute() {
            return attribute1;
        }
    }
}
