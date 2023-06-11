package com.meti;

import java.io.IOException;

import static com.meti.Options.None;

public record Application(NativePath source) {
    Result<Option<NativePath>, IOException> run() {
        if (source.exists()) {
            var sourceName = source.getFileName().asString();
            var targetName = sourceName.indexOf('.')
                    .map(separator -> sourceName.slice(0, separator))
                    .unwrapOrElse(sourceName)
                    .concat(NativeString.fromNative(".mgs"));

            return source.resolveSibling(targetName).createIfNotExists().mapValue(Options::Some);
        } else {
            return Results.Ok(None());
        }
    }
}