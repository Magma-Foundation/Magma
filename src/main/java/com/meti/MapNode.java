package com.meti;

import java.util.Objects;

public final class MapNode {
    private final String parent;
    private final String child;

    private MapNode(String parent, String child) {
        this.parent = parent;
        this.child = child;
    }

    public static MapNode createMapNode(String parent, String child) {
        return new MapNode(parent, child);
    }

    public String child() {
        return child;
    }

    public String parent() {
        return parent;
    }
}