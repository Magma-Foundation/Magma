package com.meti.source;

import com.meti.io.File;

import java.io.IOException;
import java.util.List;

public record FileSource(File file, List<String> packageList) implements Source {

    @Override
    public Package computePackage() {
        return new Package(packageList, file.computeFileNameWithoutExtension());
    }

    @Override
    public String read() throws IOException {
        return file.readAsString();
    }
}
