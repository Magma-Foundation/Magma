package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class Application {
    private final NIOPath source;

    public Application(NIOPath source) {
        this.source = source;
    }

    Result<Option<Path>, IOException> run() {
        if (source.isExists()) {
            var fileName = source.computeFileNameAsString();
            var other = fileName.firstIndexOfChar('.')
                    .map(fileName::sliceToEnd)
                    .unwrapOrElse(fileName)
                    .concat(".mgs");

            var target = source.resolveSibling(other);
            return Results.asOption(() -> Files.createFile(target))
                    .map(Err::<Option<Path>, IOException>from)
                    .unwrapOrElse(new Ok<>(new Some<>(target)));
        } else {
            return new Ok<>(new None<>());
        }
    }

}