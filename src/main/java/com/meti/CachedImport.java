package com.meti;

import java.util.*;

public final class CachedImport {
    private final String name;
    private final Set<CachedImport> children;

    public CachedImport(String name, Set<CachedImport> children) {
        this.name = name;
        this.children = children;
    }

    public CachedImport(String name) {
        this(name, new HashSet<>());
    }

    boolean isLeaf() {
        return children.isEmpty();
    }

    Optional<CachedImport> findChild(String name) {
        return children.stream()
                .filter(child -> child.hasNameOf(name))
                .findAny();
    }

    private boolean hasNameOf(String name) {
        return this.name.equals(name);
    }

    CachedImport addChild(List<String> names) {
        var child = names.get(0);
        var grandChildren = names.subList(1, names.size());

        var childOptional = findChild(child);
        CachedImport childValue;
        if (childOptional.isPresent()) {
            childValue = childOptional.get();
        } else {
            childValue = new CachedImport(child);
            children.add(childValue);
        }

        if (!grandChildren.isEmpty()) {
            childValue.addChild(grandChildren);
        }

        return this;
    }

    public String computeName() {
        return name;
    }

    public Set<CachedImport> children() {
        return children;
    }
}
