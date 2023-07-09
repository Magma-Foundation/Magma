package com.meti.app;

import com.meti.core.Result;
import com.meti.java.JavaList;
import com.meti.java.JavaString;
import com.meti.nio.NIOLocation;
import com.meti.nio.NIOPath;

import java.io.IOException;

public record DirectoryTargets(NIOLocation root) implements Targets {
    @Override
    public Result<NIOTarget, IOException> resolve(JavaList<JavaString> package_, JavaString name) {
        var resolve = package_.iter()
                .foldLeft(root, NIOLocation::resolve)
                .resolve(name)
                .into(NIOPath::from);

        return resolve.ensureAsFile().mapValue(NIOTarget::new);
    }
}
