package com.meti;

import java.util.List;

public interface Node {
    default Option<String> getChild() {
        return None.apply();
    }

    default Option<String> getParent() {
        return None.apply();
    }

    default Option<List<String>> getLines() {
        return None.apply();
    }
}
