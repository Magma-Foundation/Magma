package magma.compile.source;

import magma.collect.list.List_;

public record Location(List_<String> namespace, String name) {
    public boolean equalsTo(List_<String> qualified) {
        return namespace.add(name).equalsTo(qualified);
    }
}
