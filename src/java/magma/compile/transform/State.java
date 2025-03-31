package magma.compile.transform;

import jvm.collect.list.Lists;
import jvm.collect.stream.Streams;
import magma.collect.list.ListCollector;
import magma.collect.list.List_;
import magma.compile.Node;
import magma.option.None;
import magma.option.Option;
import magma.option.Some;

public record State(List_<String> namespace, String name, List_<List_<String>> imports) {
    public State(List_<String> namespace, String name) {
        this(namespace, name, Lists.empty());
    }

    public State defineImport(Node import_) {
        List_<String> namespace = import_.findNodeList("namespace")
                .orElse(Lists.empty())
                .stream()
                .map(node -> node.findString("value"))
                .flatMap(Streams::fromOption)
                .collect(new ListCollector<>());

        return new State(this.namespace, name, imports.add(namespace));
    }

    public State clearImports() {
        return new State(namespace, name, imports.clear());
    }

    public Option<List_<String>> qualifyName(String name) {
        if(name.isEmpty()) {
            return new None<>();
        }

        if(name.equals(this.name)) {
            return new Some<>(namespace.add(this.name));
        }

        return imports.stream()
                .filter(import_ -> import_.findLast().orElse("").equals(name))
                .next();
    }
}