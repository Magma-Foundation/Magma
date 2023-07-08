package com.meti;

import java.io.IOException;

public final class Application {
    private final NIOPath source;

    public Application(NIOPath source) {
        this.source = source;
    }

    Result<Option<NIOFile>, IOException> getOptionIOExceptionResult() {
        if (source.isExists()) {
            var fileName = source.computeFileNameAsString();
            var other = fileName.firstIndexOfChar('.')
                    .map(fileName::sliceToEnd)
                    .unwrapOrElse(fileName)
                    .concat(".mgs");

            var target = source.resolveSibling(other);
            return target.ensureAsFile().mapValue(Some::new);
        } else {
            return new Ok<>(new None<>());
        }
    }
}