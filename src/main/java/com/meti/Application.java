package com.meti;

import java.io.IOException;

public final class Application {
    private final NIOPath source;

    public Application(NIOPath source) {
        this.source = source;
    }

    Result<Option<NIOFile>, IOException> compileAll() {
        return source.existsAsFile()
                .map(this::compile)
                .map(f -> f.mapValue(Some::apply))
                .unwrapOrElse(new Ok<>(new None<>()));
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