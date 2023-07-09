package com.meti.app;

import com.meti.core.Result;
import com.meti.iterate.Iterators;
import com.meti.java.JavaSet;
import com.meti.nio.NIODirectory;
import com.meti.nio.NIOFile;
import com.meti.nio.NIOPath;

import java.io.IOException;

public record DirectoryGateway(NIODirectory directory) implements Gateway {
    @Override
    public Result<JavaSet<NIOFile>, IOException> collectSources() {
        return directory.walk().mapValue(set -> set.iter()
                .filter(path -> path.isExtendedBy("java"))
                .map(NIOPath::existsAsFile)
                .flatMap(Iterators::fromOption)
                .collect(JavaSet.asSet()));
    }
}
