package com.meti;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class DirectoryModule implements Module {
    private final NIOPath root;

    public DirectoryModule(NIOPath root) {
        this.root = root;
    }

    @Override
    public List<NIOPath> listSources() throws IOException {
        return listSources1().stream()
                .map(NIOPath::new)
                .collect(Collectors.toList());
    }

    private List<Path> listSources1() throws IOException {
        return root.list().collect(Collectors.toList());
    }
}
