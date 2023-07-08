package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public record Application(Path source) {

    Result<Option<Path>, IOException> run1() {
        if (Files.exists(source)) {
            var fileName = source.getFileName().toString();
            var separator = fileName.indexOf('.');
            var fileNameWithoutSeparator = fileName.substring(0, separator);
            var target = source().resolveSibling(fileNameWithoutSeparator + ".mgs");
            try {
                Files.createFile(target);
                return new Ok<>(new Some<>(target));
            } catch (IOException e) {
                return new Err<>(e);
            }
        } else {
            return new Ok<>(new None<>());
        }
    }
}