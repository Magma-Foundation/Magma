package magma.compile;

import magma.java.JavaLists;
import magma.list.List_;

import java.util.List;

public final class ImmutableState implements State {
    private final List<String> namespace;
    private final String name;

    public ImmutableState(List<String> namespace, String name) {
        this.namespace = namespace;
        this.name = name;
    }

    private List<String> namespace0() {
        return namespace;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public List_<String> namespace() {
        return JavaLists.wrap(namespace0());
    }
}