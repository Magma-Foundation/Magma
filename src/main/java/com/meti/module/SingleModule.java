package com.meti.module;

import com.meti.io.Path;
import com.meti.source.FileSource;
import com.meti.source.Source;

import java.util.Collections;
import java.util.List;

public record SingleModule(Path path) implements Module {
    @Override
    public List<Source> listSources() {
        return path.existingAsFile()
                .map(value -> new FileSource(value, Collections.emptyList()))
                .<List<Source>, RuntimeException>map(List::of)
                .orElse(Collections.emptyList());
    }
}
