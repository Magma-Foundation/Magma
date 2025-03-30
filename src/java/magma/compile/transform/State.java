package magma.compile.transform;

import magma.collect.list.List_;

public record State(List_<String> namespace, String name) {
    public List_<String> getNamespace() {
        return namespace;
    }
}