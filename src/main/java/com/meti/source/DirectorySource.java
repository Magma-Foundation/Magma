package com.meti.source;

import com.meti.PathScript;
import com.meti.Script;
import com.meti.stream.JavaListStream;
import com.meti.stream.Stream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

public class DirectorySource implements Source {
    private final Path root;

    public DirectorySource(Path root) {
        this.root = root;
    }

    @Override
    public Stream<? extends Script> stream() throws IOException {
        var list = Files.walk(root)
                .filter(Files::isRegularFile)
                .collect(Collectors.toList());
        return new JavaListStream<>(list)
                .map(PathScript::new)
                .filter(script -> script.hasExtensionOf("mgs"));
    }
}
