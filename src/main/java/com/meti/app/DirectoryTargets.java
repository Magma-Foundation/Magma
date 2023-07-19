package com.meti.app;

import com.meti.core.Err;
import com.meti.core.Result;
import com.meti.java.List;
import com.meti.java.String_;
import com.meti.nio.Location;
import com.meti.nio.NIOPath;

import java.io.IOException;

public record DirectoryTargets(Location root) implements Targets {
    @Override
    public Result<NIOTarget, IOException> resolve(List<String_> package_, String_ name) {
        var targetPath = package_.iter()
                .foldLeft(root, Location::resolve)
                .resolve(name)
                .into(NIOPath::from);

        return targetPath.parent()
                .map(NIOPath::from)
                .flatMap(parent -> parent.ensureAsDirectory().err())
                .map(Err::<NIOTarget, IOException>apply)
                .unwrapOrElseGet(() -> targetPath.ensureAsFile().mapValue(NIOTarget::new));
    }
}
