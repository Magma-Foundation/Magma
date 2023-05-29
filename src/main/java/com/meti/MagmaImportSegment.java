package com.meti;

import java.util.*;
import java.util.stream.Collectors;

public final class MagmaImportSegment implements Node {
    private final List<String> namespace;
    private final List<Node> names;

    public MagmaImportSegment(String... namespace) {
        this(List.of(namespace));
    }

    public MagmaImportSegment(List<String> namespace) {
        this(namespace, Collections.emptyList());
    }

    public MagmaImportSegment(List<String> namespace, List<Node> names) {
        this.namespace = new ArrayList<>(namespace);
        this.names = new ArrayList<>(names);
    }

    public static MagmaImportSegment fromChildren(List<String> namespace, List<String> name) {
        return new MagmaImportSegment(namespace, name.stream()
                .map(Content::new)
                .collect(Collectors.toList()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MagmaImportSegment that = (MagmaImportSegment) o;
        return Objects.equals(namespace, that.namespace) && Objects.equals(names, that.names);
    }

    @Override
    public String toString() {
        return "MagmaImportSegment{" +
               "namespace=" + namespace +
               ", names=" + names +
               '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(namespace, names);
    }

    @Override
    public String render() {
        var renderedNamespace = String.join(".", namespace);
        var renderedNames = names.stream().map(Node::render).collect(Collectors.joining(", "));
        return "{ " + renderedNames + " } from " + renderedNamespace;
    }

    public MagmaImportSegment insert(List<String> values) {
        var node = get(values.subList(0, values.size() - 1));
        return new MagmaImportSegment(this.namespace, null);
    }

    @Override
    public boolean hasNamespace(List<String> node) {
        return  namespace.equals(node);
    }

    @Override
    public Optional<Node> get(List<String> values) {
        if(values.isEmpty()) return Optional.of(this);

        var first = values.get(0);
        return names.stream()
                .filter(name -> name.hasNamespace(List.of(first)))
                .map(name -> name.get(values.subList(1, values.size())))
                .flatMap(Optional::stream)
                .findFirst();
    }

    public Optional<Node> get(String... values) {
        return get(List.of(values));
    }
}