package jv.app.compile;

import jv.api.io.JavaFiles;
import jv.api.collect.JavaLists;
import magma.app.compile.Source;
import magma.api.collect.List_;
import magma.api.result.Result;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public record PathSource(Path source) implements Source {
    public static final Path SOURCE_DIRECTORY = Paths.get(".", "src", "java");

    @Override
    public Result<String, IOException> read() {
        return JavaFiles.readSafe(source());
    }

    private List<String> computeNamespace0() {
        Path parent = SOURCE_DIRECTORY.relativize(source()).getParent();
        ArrayList<String> namespace = new ArrayList<>();
        for (int i = 0; i < parent.getNameCount(); i++) {
            namespace.add(parent.getName(i).toString());
        }
        return namespace;
    }

    @Override
    public String computeName() {
        String nameWithExt = SOURCE_DIRECTORY.relativize(source()).getFileName().toString();
        return nameWithExt.substring(0, nameWithExt.lastIndexOf("."));
    }

    @Override
    public List_<String> computeNamespace() {
        return JavaLists.fromNative(computeNamespace0());
    }
}