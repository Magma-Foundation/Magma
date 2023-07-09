package com.meti.app;

import com.meti.core.Ok;
import com.meti.core.Result;
import com.meti.java.JavaSet;
import com.meti.nio.NIOFile;
import com.meti.nio.NIOPath;

import java.io.IOException;

public record SingleVolatileGateway(NIOPath source) implements Gateway {
    @Override
    public Result<JavaSet<NIOFile>, IOException> collectSources() {
        return new Ok<>(source().existsAsFile()
                .map(JavaSet::of)
                .unwrapOrElse(JavaSet.empty()));
    }
}