package com.meti.app.module;

import com.meti.api.io.Path;
import com.meti.app.source.FileSource;
import com.meti.app.source.Source;

import java.util.Collections;
import java.util.List;

public record SingleModule(Path path) implements Module {
    @Override
    public List<Source> listSources1() {
        return path.existingAsFile()
                .map(value -> new FileSource(value, Collections.emptyList()))
                .<List<Source>, RuntimeException>map(List::of)
                .orElse(Collections.emptyList());
    }
}
