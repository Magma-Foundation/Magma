package com.meti.app;

import com.meti.api.io.File;
import com.meti.api.io.IOException;

public record FIleSource(File file) implements Source {
    @Override
    public Reference computePackage() {
        return new Reference(file.computeFileNameWithoutExtension());
    }

    @Override
    public String read() throws IOException {
        return file.readAsString();
    }
}
