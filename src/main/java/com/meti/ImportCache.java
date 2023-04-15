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

    void addImport(List<String> child) {
        root.addChild(child);
    }

    public Import getRoot() {
        return root;
    }

    public record Import(String name, Set<Import> children) {

        public Import(String name) {
            this(name, new HashSet<>());
        }

        boolean isLeaf() {
            return children.isEmpty();
        }

        Optional<Import> findChild(String name) {
            return children.stream()
                    .filter(child -> child.hasNameOf(name))
                    .findAny();
        }

        private boolean hasNameOf(String name) {
            return this.name.equals(name);
        }

        Import addChild(List<String> names) {
            var child = names.get(0);
            var grandChildren = names.subList(1, names.size());

            var childOptional = findChild(child);
            Import childValue;
            if (childOptional.isPresent()) {
                childValue = childOptional.get();
            } else {
                childValue = new Import(child);
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
    }
}