package magma.compile;

import jvm.collect.list.Lists;
import magma.Path_;
import magma.collect.list.List_;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public record PathSource(magma.Path_ sourceDirectory, magma.Path_ source) implements Source {
    @Override
    public List_<String> computeNamespace() {
        Path_ relative = sourceDirectory.relativize(source);
        Path_ parent = relative.getParent();
        return parent.stream().collect(new ListCollector<>());
    }

    @Override
    public String computeName() {
        String nameWithExt = source().getFileName().asString();
        return nameWithExt.substring(0, nameWithExt.lastIndexOf("."));
    }

    @Override
    public String readString() throws IOException {
        return source.readString();
    }
}