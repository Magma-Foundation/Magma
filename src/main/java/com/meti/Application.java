package com.meti;

import java.io.IOException;

public final class Application {
    private final Gateway gateway;

    public Application(Gateway gateway) {
        this.gateway = gateway;
    }

    private static Result<NativePath, IOException> compile(NativePath source) {
        var sourceName = source.getFileName().asString();
        var targetName = sourceName.indexOf('.')
                .flatMap(separator -> sourceName.slice(0, separator))
                .unwrapOrElse(sourceName)
                .concat(NativeString.fromNative(".mgs"));

        return source.resolveSibling(targetName).createIfNotExists();
    }

    Result<Option<NativePath>, IOException> runOnce() {
        return run().match(value -> IterableResults.apply(value.iter())
                .foldLeftByValue(NativeSet.<NativePath>empty(), NativeSet::add)
                .mapValue(NativeSet::any), Results::Err);
    }

    Result<NativeSet<Result<NativePath, IOException>>, IOException> run() {
        return gateway.collectSources().mapValue(sources -> sources.iter()
                .map(Application::compile)
                .collect(Iterables.toSet()));
    }
}