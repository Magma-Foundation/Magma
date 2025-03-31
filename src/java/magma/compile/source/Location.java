package magma.compile.source;

import magma.collect.list.List_;

public record Location(List_<String> namespace, String name) {
    public Location resolveSibling(String otherName) {
        return new Location(namespace, otherName);
    }

    public Location resolveChild(String child) {
        return new Location(namespace.add(name.toLowerCase()), child);
    }
}
