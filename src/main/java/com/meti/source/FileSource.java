package com.meti.source;

import com.meti.api.io.File;

import java.io.IOException;
import java.util.List;

public record FileSource(File file, List<String> packageList) implements Source {

    @Override
    public Packaging computePackage() {
        return new Packaging(file.computeFileNameWithoutExtension(), packageList);
    }

    @Override
    public String read() throws IOException {
        return file.readAsString();
    }
}
