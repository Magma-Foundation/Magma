package magma;

import magma.api.result.Err;
import magma.api.result.Ok;
import magma.api.result.Result;
import magma.compile.CompileException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public final class PathUnit implements Unit {
    private final Path root;
    private final Path child;

    public PathUnit(Path root, Path child) {
        this.root = root;
        this.child = child;
    }

    @Override
    public String toString() {
        var list = new ArrayList<>(computeNamespace());
        list.add(computeName());
        return String.join(".", list);
    }

    @Override
    public Result<String, CompileException> read() {
        try {
            return new Ok<>(Files.readString(child));
        } catch (IOException e) {
            return new Err<>(new CompileException(e));
        }
    }

    @Override
    public List<String> computeNamespace() {
        var relativized = root.relativize(child.getParent());
        return IntStream.range(0, relativized.getNameCount())
                .mapToObj(index -> relativized.getName(index).toString())
                .toList();
    }

    @Override
    public String computeName() {
        var fileName = child.getFileName().toString();
        return fileName.substring(0, fileName.indexOf('.'));
    }
}