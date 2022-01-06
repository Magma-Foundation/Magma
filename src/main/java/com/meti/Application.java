package com.meti;

import com.meti.io.NIOPath;
import com.meti.module.Module;
import com.meti.source.Source;

import java.io.IOException;

import static com.meti.io.NIOPath.Root;

public record Application(Module module) {
    public static final NIOPath Out = Root.resolveChild("out");

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
