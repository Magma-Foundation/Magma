package com.meti;

import java.io.IOException;

public final class Application {
    private final SourceGateway sourceGateway;

    public Application(SourceGateway sourceGateway) {
        this.sourceGateway = sourceGateway;
    }

    private static Result<NativePath, IOException> compile(NativePath source1) {
        var sourceName = source1.getFileName().asString();
        var targetName = sourceName.indexOf('.')
                .map(separator -> sourceName.slice(0, separator))
                .unwrapOrElse(sourceName)
                .concat(NativeString.fromNative(".mgs"));

        return source1.resolveSibling(targetName).createIfNotExists();
    }

    Result<Option<NativePath>, IOException> runOnce() {
        return run().iter()
                .foldLeft(Results.<NativeSet<NativePath>, IOException>Ok(NativeSet.empty()), (resultSet, resultElement) -> resultSet.merge(resultElement, NativeSet::add))
                .mapValue(NativeSet::any);
    }

    NativeSet<Result<NativePath, IOException>> run() {
        return sourceGateway.collectSources().iter()
                .map(Application::compile)
                .collect(Iterables.toSet());
    }
}