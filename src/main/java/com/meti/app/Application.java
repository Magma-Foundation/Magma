package com.meti.app;

import com.meti.java.JavaSet;
import com.meti.core.Option;
import com.meti.core.Result;
import com.meti.iterate.ResultIterator;
import com.meti.nio.NIOFile;
import com.meti.nio.NIOLocation;

import java.io.IOException;

public final class Application {
    private final SingleVolatileGateway sourceGateway;

    public Application(SingleVolatileGateway sourceGateway) {
        this.sourceGateway = sourceGateway;
    }

    Result<Option<NIOFile>, IOException> compileAll() {
        return sourceGateway.collectSources()
                .iter()
                .map(this::compile)
                .into(ResultIterator::new)
                .collectToResult(JavaSet.asSet())
                .mapValue(set -> set.iter().head());
    }

    private Result<NIOFile, IOException> compile(NIOLocation source) {
        var fileName = source.computeFileNameAsString();
        var other = fileName.firstIndexOfChar('.')
                .map(fileName::sliceToEnd)
                .unwrapOrElse(fileName)
                .concat(".mgs");

        var target = source.resolveSibling(other);
        return target.ensureAsFile();
    }
}