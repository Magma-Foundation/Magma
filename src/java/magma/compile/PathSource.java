package magma.compile;

import magma.collect.list.List_;
import magma.io.IOError;
import magma.io.Path_;
import magma.result.Result;

public record PathSource(Path_ sourceDirectory, Path_ source) implements Source {
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
    public Result<String, IOError> readString() {
        return source.readString();
    }
}