package com.meti.app;

import com.meti.core.Result;
import com.meti.iterate.Iterators;
import com.meti.java.JavaSet;
import com.meti.nio.NIODirectory;
import com.meti.nio.NIOFile;
import com.meti.nio.NIOPath;

import java.io.IOException;

public record DirectorySources(NIODirectory directory) implements Sources {
    @Override
    public Result<JavaSet<NIOSource>, IOException> collect() {
        return directory.walk().mapValue(set -> set.iter()
                .filter(path -> path.isExtendedBy("java"))
                .map(NIOPath::existsAsFile)
                .flatMap(Iterators::fromOption)
                .map((NIOFile parent) -> new NIOSource(directory, parent))
                .collect(JavaSet.asSet()));
    }
}
