package magma.compile.source;

import magma.collect.list.ListCollector;
import magma.collect.list.List_;
import magma.io.IOError;
import magma.io.Path_;
import magma.result.Result;

public record PathSource(Path_ sourceDirectory, Path_ source) implements Source {
    public List_<String> computeNamespace() {
        Path_ relative = sourceDirectory.relativize(source);
        Path_ parent = relative.getParent();
        return parent.stream().collect(new ListCollector<>());
    }

    public String computeName() {
        String nameWithExt = source().getFileName().asString();
        return nameWithExt.substring(0, nameWithExt.lastIndexOf("."));
    }

    @Override
    public Location location() {
        return new Location(computeNamespace(), computeName());
    }

    @Override
    public Result<String, IOError> readString() {
        return source.readString();
    }
}