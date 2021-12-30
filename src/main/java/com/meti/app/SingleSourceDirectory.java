package com.meti.app;

import com.meti.api.io.File;
import com.meti.api.io.Path;

public record SingleSourceDirectory(Path source) implements SourceDirectory {
    @Override
    public Stream<Source> stream() {
        return stream1().map(FIleSource::new);
    }

    private Stream<File> stream1() {
        return new OptionStream<>(source.existing());
    }
}
