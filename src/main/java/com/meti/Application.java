package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public record Application(Path source) {
    Result<Option<Path>, IOException> run1() {
        if (Files.exists(source)) {
            var fileName = new JavaString(source.getFileName().toString());

            var other = fileName.firstIndexOfChar('.')
                    .map(fileName::sliceToEnd)
                    .unwrapOrElse(fileName)
                    .concat(".mgs")
                    .unwrap();

            var target = source.resolveSibling(other);
            return Results.asOption(() -> Files.createFile(target))
                    .map(Err::<Option<Path>, IOException>from)
                    .unwrapOrElse(new Ok<>(new Some<>(target)));
        } else {
            return new Ok<>(new None<>());
        }
    }
}