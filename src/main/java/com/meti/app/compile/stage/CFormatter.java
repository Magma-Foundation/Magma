package com.meti.app.compile.stage;

import com.meti.api.collect.stream.Stream;
import com.meti.api.collect.stream.Streams;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.process.Processor;
import com.meti.app.source.Packaging;

public class CFormatter extends AfterNodeStreamStage {
    private final Packaging thisPackage;

    public CFormatter(Packaging thisPackage) {
        this.thisPackage = thisPackage;
    }

    @Override
    protected Stream<Processor<Node>> streamNodeTransformers(Node node) {
        return Streams.apply(new LambdaFormatter(node), new PackageFormatter(thisPackage, node));
    }
}
