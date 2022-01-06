package com.meti;

import java.util.Collections;
import java.util.List;

public class SingleModule implements Module {
    private final NIOPath path;

    public SingleModule(NIOPath path) {
        this.path = path;
    }

    @Override
    public boolean hasSource(String name, List<String> packageList) {
        return packageList.isEmpty() && path.computeFileNameWithoutExtension().equals(name + ".mg");
    }

    @Override
    public List<Source> listSources() {
        return path.exists()
                ? List.of(new PathSource(path, Collections.emptyList()))
                : Collections.emptyList();
    }
}
