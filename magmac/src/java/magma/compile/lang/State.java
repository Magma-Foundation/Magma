package magma.compile.lang;

import magma.api.Tuple;
import magma.api.collect.LinkedList;
import magma.api.collect.List;
import magma.api.collect.stream.Streams;
import magma.api.result.Err;
import magma.api.result.Ok;
import magma.api.result.Result;
import magma.compile.CompileError;
import magma.compile.Error_;
import magma.java.Set;

public final class State {
    private final Set<List<String>> locations;
    private final List<List<String>> frames;

    public State(Set<List<String>> locations, List<List<String>> frames) {
        this.locations = locations;
        this.frames = frames;
    }

    public State(Set<List<String>> locations) {
        this(locations, new LinkedList<>());
    }

    public State exit() {
        return new State(locations, frames.popFirst().map(Tuple::right).orElse(frames));
    }

    public State enter() {
        return new State(locations, frames.push(new LinkedList<>()));
    }

    public boolean isDefined(String value) {
        if (isDefinedAsLocation(value)) {
            return true;
        }

        return frames.stream().anyMatch(frame -> frame.contains(value));
    }

    private boolean isDefinedAsLocation(String value) {
        return locations.stream()
                .map(List::last)
                .flatMap(Streams::fromOption)
                .anyMatch(last -> last.equals(value));
    }

    public int computeDepth() {
        return frames.size();
    }

    public Result<State, Error_> define(String name) {
        if (frames.isEmpty()) {
            return new Err<>(new CompileError("No frames present.", frames.toString()));
        }

        var newFrames = frames.mapLast(last -> last.add(name)).orElse(frames);
        return new Ok<>(new State(locations, newFrames));
    }
}
