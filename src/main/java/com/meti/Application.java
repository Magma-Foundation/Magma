package com.meti;

import java.io.IOException;
import java.util.HashSet;

public final class Application {
    private final NativePath source;

    public Application(NativePath source) {
        this.source = source;
    }

    Result<Option<NativePath>, IOException> run() {
        var targets = new HashSet<Result<NativePath, IOException>>();

        if (source.exists()) {
            var sourceName = source.getFileName().asString();
            var targetName = sourceName.indexOf('.')
                    .map(separator -> sourceName.slice(0, separator))
                    .unwrapOrElse(sourceName)
                    .concat(NativeString.fromNative(".mgs"));

            var ifNotExists = source.resolveSibling(targetName).createIfNotExists();
            targets.add(ifNotExists);
        }

        return new NativeSet<>(targets)
                .iter()
                .foldLeft(Results.<NativeSet<NativePath>, IOException>Ok(NativeSet.empty()), (resultSet, resultElement) -> resultSet.merge(resultElement, NativeSet::add))
                .mapValue(NativeSet::any);
    }
}