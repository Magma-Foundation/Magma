package magma.compile.annotate;

import magma.api.contain.List;
import magma.java.JavaList;

public final class ImmutableState implements State {
    private final List<List<String>> frames;

    public ImmutableState(List<List<String>> frames) {
        this.frames = frames;
    }

    public ImmutableState() {
        this(new JavaList<>());
    }

    @Override
    public State enter() {
        return new ImmutableState(frames.pushLast(JavaList.empty()));
    }

    @Override
    public int computeDepth() {
        return frames.size();
    }

    @Override
    public State exit() {
        return new ImmutableState(frames.popLastAndDiscard().orElse(frames));
    }
}
