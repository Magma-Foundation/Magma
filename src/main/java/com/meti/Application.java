package com.meti;

import java.io.IOException;

public class Application {
    private final Module module;

    public Application(Module module) {
        this.module = module;
    }

    void run() throws IOException {
        for (var source : module.listSources()) {
            compile(source);
        }
    }

    private void compile(NIOPath source) throws IOException {
        var name = source.computeFileNameWithoutExtension();
        var target = source.resolveSibling(name + ".c");
        target.createAsFile();
    }
}
