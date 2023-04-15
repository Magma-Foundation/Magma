package com.meti;

import java.util.*;

public final class ImportCache {
    private final Import root;

    public ImportCache() {
        this(Collections.emptySet());
    }

    public ImportCache(Set<Import> root) {
        this.root = new Import("", new HashSet<>(root));
    }

    Set<Import> collectChildren() {
        return root.children();
    }

    void addImport(List<String> child) {
        root.addChild(child);
    }
}