package magmac.app.compile.node;

import magmac.api.Option;
import magmac.api.Some;
import magmac.api.collect.list.List;
import magmac.api.collect.list.Lists;
import magmac.api.iter.Iter;
import magmac.api.iter.collect.Joiner;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileResultCollector;

import java.util.function.Function;

public final class InlineNodeList implements NodeList {
    private final List<Node> elements;

    public InlineNodeList(List<Node> elements) {
        this.elements = elements;
    }

    public static NodeList empty() {
        return new InlineNodeList(Lists.empty());
    }

    public static NodeList of(Node... elements) {
        return new InlineNodeList(Lists.of(elements));
    }

    @Override
    public Iter<Node> iter() {
        return this.elements.iter();
    }

    @Override
    public NodeList add(Node element) {
        return new InlineNodeList(this.elements.addLast(element));
    }

    @Override
    public NodeList addAll(NodeList others) {
        return others.iter().fold(this, NodeList::add);
    }

    @Override
    public Option<Node> findLast() {
        return this.elements.findLast();
    }

    @Override
    public CompileResult<String> join(String delimiter, Function<Node, CompileResult<String>> generator) {
        return this.iter()
                .map(generator)
                .collect(new CompileResultCollector<>(new Joiner(delimiter)))
                .mapValue((Option<String> option) -> option.orElse(""));
    }
}
