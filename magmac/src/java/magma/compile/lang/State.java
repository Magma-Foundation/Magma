package magma.compile.lang;

import magma.api.Tuple;
import magma.api.result.Err;
import magma.api.result.Ok;
import magma.api.result.Result;
import magma.api.stream.Streams;
import magma.compile.CompileError;
import magma.compile.Error_;
import magma.java.JavaList;
import magma.java.JavaSet;

public final class State {
    private final JavaSet<JavaList<String>> locations;
    private final JavaList<JavaList<String>> frames;

    public State(JavaSet<JavaList<String>> locations, JavaList<JavaList<String>> frames) {
        this.locations = locations;
        this.frames = frames;
    }

    public State(JavaSet<JavaList<String>> locations) {
        this(locations, new JavaList<>());
    }

    public State exit() {
        return new State(locations, frames.pop().map(Tuple::right).orElse(frames));
    }

    public State enter() {
        return new State(locations, frames.push(new JavaList<>()));
    }

    public boolean isDefined(String value) {
        if (isDefinedAsLocation(value)) {
            return true;
        }

        return frames.stream().anyMatch(frame -> frame.contains(value));
    }

    private boolean isDefinedAsLocation(String value) {
        return locations.stream()
                .map(JavaList::last)
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
