package magma.compile.transform;

import jvm.collect.list.Lists;
import jvm.collect.stream.Streams;
import magma.collect.list.List_;
import magma.compile.Node;

public record Frame(List_<Node> types) {
    public Frame() {
        this(Lists.empty());
    }

    public Frame defineType(Node type) {
        return new Frame(types.add(type));
    }

    public boolean isTypeDefined(String typeParam) {
        return types.stream()
                .map(node -> node.findString("value"))
                .flatMap(Streams::fromOption)
                .anyMatch(typeParam::equals);
    }
}
