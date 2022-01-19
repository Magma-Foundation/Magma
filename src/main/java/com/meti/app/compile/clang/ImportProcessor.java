package com.meti.app.compile.clang;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.process.Processor;
import com.meti.app.compile.text.Output;
import com.meti.app.compile.text.RootText;

import java.util.stream.Collectors;

public record ImportProcessor(Node node) implements Processor<Output> {
    @Override
    public Option<Output> process() throws AttributeException {
        if (node.is(Node.Type.Import)) {
            var packaging = node.apply(Attribute.Type.Value).asPackaging();
            var parent = packaging.streamParent().collect(Collectors.joining("/"));
            var name = packaging.computeName();
            var formatted = parent.isEmpty() ? name : parent + "/" + name;
            return new Some<>(new RootText("#include \"" + formatted + ".h\"\n"));
        } else {
            return new None<>();
        }
    }
}
