package com.meti;

import java.io.IOException;

public record VolatileFileGateway(NativePath source) implements Gateway {
    @Override
    public Result<NativeSet<NativePath>, IOException> collectSources() {
        if (source.exists()) {
            return Results.Ok(NativeSet.<NativePath>empty().add(source));
        } else {
            return Results.Ok(NativeSet.empty());
        }
    }
}