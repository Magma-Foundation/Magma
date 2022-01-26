package com.meti.app.compile.stage;

import com.meti.api.collect.stream.Stream;
import com.meti.api.collect.stream.StreamException;
import com.meti.api.collect.stream.Streams;
import com.meti.api.option.None;
import com.meti.api.option.Some;
import com.meti.app.compile.magma.NodeStage;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.InputAttribute;
import com.meti.app.compile.node.attribute.NodeAttribute;
import com.meti.app.compile.process.Processor;
import com.meti.app.compile.text.Input;
import com.meti.app.compile.text.RootText;
import com.meti.app.source.Packaging;

public class CFormatter extends NodeStage {
    private final Packaging thisPackage;
    private int counter = -1;

    public CFormatter(Packaging thisPackage) {
        this.thisPackage = thisPackage;
    }

    protected Stream<Processor<Node>> streamNodeTransformers(Node node) {
        return Streams.apply(() -> {
            if (node.is(Node.Type.Abstraction) || node.is(Node.Type.Implementation)) {
                var oldIdentity = node.apply(Attribute.Type.Identity).asNode();
                var oldName = oldIdentity.apply(Attribute.Type.Name).asInput();
                Input newName;
                if (oldName.isEmpty()) {
                    counter++;
                    newName = new RootText("__lambda" + counter + "__");
                } else {
                    newName = oldName;
                }
                var newIdentity = oldIdentity.with(Attribute.Type.Name, new InputAttribute(newName));
                return new Some<>(node.with(Attribute.Type.Identity, new NodeAttribute(newIdentity)));
            } else {
                return new None<>();
            }
        }, new PackageFormatter(thisPackage, node));
    }

    @Override
    protected Node transformUsingStreams(Node node, Stream<Processor<Node>> transformers) throws CompileException {
        try {
            return transformers.map(Processor::process)
                    .flatMap(Streams::optionally)
                    .first()
                    .orElse(node);
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }
}
