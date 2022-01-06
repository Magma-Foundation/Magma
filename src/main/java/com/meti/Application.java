package com.meti;

import java.io.IOException;

import static com.meti.NIOPath.Root;

public class Application {
    public static final NIOPath Out = Root.resolveChild("out");
    private final Module module;

    public Application(Module module) {
        this.module = module;
    }

    void run() throws IOException {
        var sources = module.listSources();
        for (var source : sources) {
            compile(source);
        }
    }

    private void compile(Source source) throws IOException {
        var name = source.computeName();

        if (!Out.exists()) Out.createAsDirectory();
        var reduce = source.computePackage().reduce(Out, NIOPath::resolveChild, (previous, next) -> next);

        var target = reduce.resolveChild(name + ".c");
        if (!target.exists()) target.createAsFile();
    }
}
