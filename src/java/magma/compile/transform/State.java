package magma.compile.transform;

import jvm.collect.list.Lists;
import jvm.collect.stream.Streams;
import magma.collect.list.ListCollector;
import magma.collect.list.List_;
import magma.compile.Node;
import magma.compile.source.Location;
import magma.option.None;
import magma.option.Option;
import magma.option.Some;

public final class State {
    private final List_<List_<String>> imports;
    private final List_<Frame> frames;
    private final Location location;

    public State(
            Location location,
            List_<List_<String>> imports,
            List_<Frame> frames) {
        this.location = location;
        this.imports = imports;
        this.frames = frames;
    }

    public State(Location location) {
        this(location, Lists.empty(), Lists.of(new Frame()));
    }

    public State defineImport(Node import_) {
        List_<String> namespace = import_.findNodeList("namespace")
                .orElse(Lists.empty())
                .stream()
                .map(node -> node.findString("value"))
                .flatMap(Streams::fromOption)
                .collect(new ListCollector<>());

        return new State(location, imports.add(namespace), frames);
    }

    public State clearImports() {
        return new State(location, imports.clear(), frames);
    }

    public Option<List_<String>> qualifyName(String name) {
        if (name.isEmpty()) {
            return new None<>();
        }

        if (name.equals(location.name())) {
            return new Some<>(location.namespace().add(location.name()));
        }

        return imports.stream()
                .filter(import_ -> import_.findLast().orElse("").equals(name))
                .next();
    }

    public State defineType(Node type) {
        List_<Frame> newFrames = frames.mapLast(last -> last.defineType(type));
        return new State(location, imports, newFrames);
    }

    public State enter() {
        return new State(location, imports, frames.add(new Frame()));
    }

    public State exit() {
        return new State(location, imports, frames.subList(0, frames.size() - 1));
    }

    public boolean isTypeParamDefined(String type) {
        return frames.stream().anyMatch(frame -> frame.isTypeDefined(type));
    }

    public List_<String> namespace() {
        return location.namespace();
    }

    public String name() {
        return location.name();
    }
}