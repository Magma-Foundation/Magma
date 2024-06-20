package magma.compile.lang;

import magma.api.result.Err;
import magma.api.result.Ok;
import magma.api.result.Result;
import magma.compile.CompileError;
import magma.compile.Error_;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public record State(Set<List<String>> locations, List<List<String>> frames) {
    public State(Set<List<String>> locations) {
        this(locations, new ArrayList<>());
    }

    public State exit() {
        var previous = frames.subList(0, frames.size() - 1);
        return new State(locations, previous);
    }

    public State enter() {
        var copy = new ArrayList<>(frames);
        copy.add(new ArrayList<>());
        return new State(locations, copy);
    }

    public boolean isDefined(String value) {
        if (isDefinedAsLocation(value)) {
            return true;
        }

        return frames.stream().anyMatch(frame -> frame.contains(value));
    }

    private boolean isDefinedAsLocation(String value) {
        return locations.stream()
                .filter(location -> !location.isEmpty())
                .map(location -> location.get(location.size() - 1))
                .anyMatch(last -> last.equals(value));
    }

    public int computeDepth() {
        return frames.size();
    }

    public Result<State, Error_> define(String name) {
        if (frames.isEmpty()) {
            return new Err<>(new CompileError("No frames present.", frames.toString()));
        }

        var framesCopy = new ArrayList<>(frames);
        var frameCopy = new ArrayList<>(framesCopy.remove(framesCopy.size() - 1));
        frameCopy.add(name);
        framesCopy.add(frameCopy);
        return new Ok<>(new State(locations, framesCopy));
    }
}
