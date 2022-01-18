package com.meti.compile;

import com.meti.collect.Stream;
import com.meti.collect.Streams;
import com.meti.compile.node.Node;
import com.meti.source.Packaging;

public class CFormatter extends StreamStage {
    private final Packaging thisPackage;

    public CFormatter(Packaging thisPackage) {
        this.thisPackage = thisPackage;
    }

    @Override
    protected Stream<Transformer> streamNodeTransformers(Node node) {
        return Streams.apply(new FunctionFormatter(node), new PackageFormatter(thisPackage, node));
    }
}
