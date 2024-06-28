package magma.compile.annotate;

import magma.Unit;
import magma.api.collect.List;
import magma.java.JavaList;
import magma.java.Set;

public final class ImmutableState implements State {
    private final Set<Unit> sources;
    private final List<List<String>> frames;

    public ImmutableState(Set<Unit> sources, List<List<String>> frames) {
        this.sources = sources;
        this.frames = frames;
    }

    public ImmutableState(Set<Unit> sources) {
        this(sources, new JavaList<>());
    }
}
