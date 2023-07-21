package com.meti.app;

import com.meti.java.JavaMap;
import com.meti.java.List;
import com.meti.java.Map;
import com.meti.java.String_;

public record State(Map<String_, List<String_>> imports) {
    public State() {
        this(JavaMap.empty());
    }

    State define(String_ name, List<String_> namespace) {
        return new State(imports.insert(name, namespace));
    }
}