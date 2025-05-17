package jvm.api.io;

import magma.annotate.Actual;
import magma.annotate.Namespace;
import magma.api.io.Path;

import java.nio.file.Paths;

@Namespace
public final class Files {
    @Actual
    public static Path get(final String first, final String... more) {
        return new JVMPath(Paths.get(first, more));
    }
}
