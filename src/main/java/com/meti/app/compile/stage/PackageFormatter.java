package com.meti.app.compile.stage;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.PackageAttribute;
import com.meti.app.compile.process.Processor;
import com.meti.app.source.Packaging;

record PackageFormatter(Packaging thisPackage, Node node) implements Processor<Node> {
    public Option<Node> process() throws CompileException {
        if (node.is(Node.Type.Import)) {
            var thatPackage = node.apply(Attribute.Type.Value).asPackaging();
            var newPackage = thisPackage.relativize(thatPackage);
            return new Some<>(node.with(Attribute.Type.Value, new PackageAttribute(newPackage)));
        } else {
            return new None<>();
        }
    }
}
