package com.meti;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class DirectoryModule implements Module {
    private final NIOPath root;

    public DirectoryModule(NIOPath root) {
        this.root = root;
    }

    @Override
    public List<NIOPath> listSources() throws IOException {
        return root.walk()
                .filter(value -> value.hasExtensionOf("mg"))
                .collect(Collectors.toList());
    }
}
