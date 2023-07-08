package com.meti;

public record SingleVolatileGateway(NIOPath source) {
    JavaSet<NIOFile> collectSources() {
        return source().existsAsFile()
                .map(JavaSet::of)
                .unwrapOrElse(JavaSet.empty());
    }
}