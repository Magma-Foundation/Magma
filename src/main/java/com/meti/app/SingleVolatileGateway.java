package com.meti.app;

import com.meti.core.Ok;
import com.meti.core.Result;
import com.meti.java.JavaSet;
import com.meti.java.List;
import com.meti.java.Set;
import com.meti.java.String_;
import com.meti.nio.NIOFile;
import com.meti.nio.NIOPath;

import java.io.IOException;

public record SingleVolatileGateway(NIOPath source) implements Sources, Targets {
    @Override
    public Result<Set<NIOSource>, IOException> collect() {
        return Ok.apply(source.existsAsFile()
                .map((NIOFile asFile) -> new NIOSource(source.parent().unwrapOrElse(asFile), asFile))
                .map(JavaSet::of)
                .unwrapOrElse(JavaSet.empty()));
    }

    @Override
    public Result<NIOTarget, IOException> resolve(List<String_> package_, String_ name) {
        return source.resolveSibling(name)
                .into(NIOPath::from)
                .ensureAsFile()
                .mapValue(NIOTarget::new);
    }
}