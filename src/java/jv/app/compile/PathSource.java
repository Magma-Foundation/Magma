package jv.app.compile;

import jv.api.collect.JavaLists;
import jv.api.io.JavaIOError;
import jv.api.result.JavaResults;
import magma.Application;
import magma.api.collect.List_;
import magma.api.io.IOError;
import magma.api.result.Result;
import magma.app.compile.Source;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public record PathSource(Path source) implements Source {
    @Override
    public Result<String, IOError> read() {
        return JavaResults
                .wrap(() -> Files.readString(this.source()))
                .mapErr(JavaIOError::new);
    }

    private List<String> computeNamespace0() {
        Path parent = Application.SOURCE_DIRECTORY.relativize(source()).getParent();
        ArrayList<String> namespace = new ArrayList<>();
        for (int i = 0; i < parent.getNameCount(); i++) {
            namespace.add(parent.getName(i).toString());
        }
        return namespace;
    }

    @Override
    public String computeName() {
        String nameWithExt = Application.SOURCE_DIRECTORY.relativize(source()).getFileName().toString();
        return nameWithExt.substring(0, nameWithExt.lastIndexOf("."));
    }

    @Override
    public List_<String> computeNamespace() {
        return JavaLists.fromNative(computeNamespace0());
    }
}