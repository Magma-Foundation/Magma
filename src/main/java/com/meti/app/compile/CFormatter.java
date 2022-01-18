package com.meti.app.compile;

import com.meti.api.collect.stream.Stream;
import com.meti.api.collect.stream.Streams;
import com.meti.api.option.None;
import com.meti.api.option.Some;
import com.meti.app.compile.attribute.Attribute;
import com.meti.app.compile.attribute.NodeAttribute;
import com.meti.app.compile.attribute.TextAttribute;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.Text;
import com.meti.app.source.Packaging;

public class CFormatter extends StreamStage {
    private final Packaging thisPackage;
    private int counter = -1;

    public CFormatter(Packaging thisPackage) {
        this.thisPackage = thisPackage;
    }

    @Override
    protected Stream<Transformer> streamNodeTransformers(Node node) {
        return Streams.apply(() -> {
            if (node.is(Node.Type.Abstraction) || node.is(Node.Type.Implementation)) {
                var oldIdentity = node.apply(Attribute.Type.Identity).asNode();
                var oldName = oldIdentity.apply(Attribute.Type.Name).asText();
                Text newName;
                if (oldName.isEmpty()) {
                    counter++;
                    newName = new Text("__lambda" + counter + "__");
                } else {
                    newName = oldName;
                }
                var newIdentity = oldIdentity.with(Attribute.Type.Name, new TextAttribute(newName));
                return new Some<>(node.with(Attribute.Type.Identity, new NodeAttribute(newIdentity)));
            } else {
                return new None<>();
            }
        }, new PackageFormatter(thisPackage, node));
    }
}
