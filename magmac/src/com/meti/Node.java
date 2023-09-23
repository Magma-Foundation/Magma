package com.meti;

public interface Node {
    default Option<String> getChild() {
        return None.apply();
    }

    default Option<String> getParent() {
        return None.apply();
    }
}
