package com.meti.app;

import com.meti.java.JavaSet;
import com.meti.nio.NIOFile;
import com.meti.nio.NIOPath;

public record SingleVolatileGateway(NIOPath source) implements Gateway {
    @Override
    public JavaSet<NIOFile> collectSources() {
        return source().existsAsFile()
                .map(JavaSet::of)
                .unwrapOrElse(JavaSet.empty());
    }
}