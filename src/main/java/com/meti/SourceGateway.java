package com.meti;

public record SourceGateway(NativePath source) {
    NativeSet<NativePath> collectSources() {
        var sources = NativeSet.<NativePath>empty();
        if (source().exists()) {
            sources = sources.add(source());
        }
        return sources;
    }
}