package magma.compile.transform;

import jvm.collect.list.Lists;
import jvm.collect.stream.Streams;
import magma.collect.list.ListCollector;
import magma.collect.list.List_;
import magma.compile.Node;
import magma.option.None;
import magma.option.Option;
import magma.option.Some;

public record State(
        List_<String> namespace,
        String name,
        List_<List_<String>> imports,
        List_<Frame> frames) {
    public State(List_<String> namespace, String name) {
        this(namespace, name, Lists.empty(), Lists.of(new Frame()));
    }

    public State defineImport(Node import_) {
        List_<String> namespace = import_.findNodeList("namespace")
                .orElse(Lists.empty())
                .stream()
                .map(node -> node.findString("value"))
                .flatMap(Streams::fromOption)
                .collect(new ListCollector<>());

        return new State(this.namespace, name, imports.add(namespace), frames);
    }

    public State clearImports() {
        return new State(namespace, name, imports.clear(), frames);
    }

    public Option<List_<String>> qualifyName(String name) {
        if (name.isEmpty()) {
            return new None<>();
        }

        if (name.equals(this.name)) {
            return new Some<>(namespace.add(this.name));
        }

        return imports.stream()
                .filter(import_ -> import_.findLast().orElse("").equals(name))
                .next();
    }

    public State defineType(Node type) {
        List_<Frame> newFrames = frames.mapLast(last -> last.defineType(type));
        return new State(namespace, name, imports, newFrames);
    }

    public State enter() {
        return new State(namespace, name, imports, frames.add(new Frame()));
    }

    public State exit() {
        return new State(namespace, name, imports, frames.subList(0, frames.size() - 1));
    }

    public boolean isTypeParamDefined(String type) {
        return frames.stream().anyMatch(frame -> frame.isTypeDefined(type));
    }
}