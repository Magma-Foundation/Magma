package com.meti.app;

import com.meti.api.io.File;
import com.meti.api.io.Path;

public record SingleSource(Path source) implements Source {
    @Override
    public Stream<File> stream() {
        return new OptionStream<>(source.existing());
    }
}
