package com.meti;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SingleModule implements Module {
    private final NIOPath path;

    public SingleModule(NIOPath path) {
        this.path = path;
    }

    @Override
    public List<NIOPath> listSources() {
        return path.exists()
                ? List.of(path)
                : Collections.emptyList();
    }
}
