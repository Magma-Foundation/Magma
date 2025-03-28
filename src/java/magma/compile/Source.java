package magma.compile;

import magma.java.JavaFiles;
import magma.result.Result;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public record Source(Path source) {
    public static final Path SOURCE_DIRECTORY = Paths.get(".", "src", "java");

    public Result<String, IOException> read() {
        return JavaFiles.readSafe(source());
    }

    public List<String> computeNamespace() {
        Path parent = SOURCE_DIRECTORY.relativize(source()).getParent();
        ArrayList<String> namespace = new ArrayList<>();
        for (int i = 0; i < parent.getNameCount(); i++) {
            namespace.add(parent.getName(i).toString());
        }
        return namespace;
    }

    public String computeName() {
        String nameWithExt = SOURCE_DIRECTORY.relativize(source()).getFileName().toString();
        return nameWithExt.substring(0, nameWithExt.lastIndexOf("."));
    }
}