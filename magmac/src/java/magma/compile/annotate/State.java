package magma.compile.annotate;

import magma.Unit;
import magma.api.Tuple;
import magma.api.collect.LinkedList;
import magma.api.collect.List;
import magma.api.result.Err;
import magma.api.result.Ok;
import magma.api.result.Result;
import magma.compile.CompileError;
import magma.compile.Error_;
import magma.java.Set;

public final class State {
    private final Set<Unit> sources;
    private final List<List<String>> frames;

    public State(Set<Unit> sources, List<List<String>> frames) {
        this.sources = sources;
        this.frames = frames;
    }

    public State(Set<Unit> sources) {
        this(sources, new LinkedList<>());
    }

    public State exit() {
        return new State(sources, frames.popFirst().map(Tuple::right).orElse(frames));
    }

    public State enter() {
        return new State(sources, frames.push(new LinkedList<>()));
    }

    public boolean isDefined(String value) {
        if (isDefinedAsLocation(value)) {
            return true;
        }

        return frames.stream().anyMatch(frame -> frame.contains(value));
    }

    private boolean isDefinedAsLocation(String value) {
        return sources.stream()
                .map(Unit::computeName)
                .anyMatch(name -> name.equals(value));
    }

    public int computeDepth() {
        return frames.size();
    }

    public Result<State, Error_> define(String name) {
        if (frames.isEmpty()) {
            return new Err<>(new CompileError("No frames present.", frames.toString()));
        }

        var newFrames = frames.mapLast(last -> last.add(name)).orElse(frames);
        return new Ok<>(new State(sources, newFrames));
    }

    public Result<State, Error_> defineAll(List<String> names) {
        return names.stream().foldRightToResult(this, State::define);
    }
}
