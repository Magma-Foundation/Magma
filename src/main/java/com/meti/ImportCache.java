package com.meti;

import java.util.*;

public final class ImportCache {
    private final CachedImport root;

    public ImportCache() {
        this(Collections.emptySet());
    }

    public ImportCache(Set<CachedImport> root) {
        this.root = new CachedImport("", new HashSet<>(root));
    }

    Set<CachedImport> collectChildren() {
        return root.children();
    }

    void addImport(List<String> child) {
        root.addChild(child);
    }
}