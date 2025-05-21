package magma.app.io;

import magma.api.collect.list.Iterable;
import magma.api.collect.list.List;
import magma.api.collect.list.ListCollector;
import magma.api.io.IOError;
import magma.api.io.Path;
import magma.api.result.Result;
import magma.app.Location;

public record PathSource(Path sourceDirectory, Path source) implements Source {
    @Override
    public Result<String, IOError> read() {
        return this.source.readString();
    }

    private String computeName() {
        var fileName = this.source.findFileName();
        var separator = fileName.lastIndexOf('.');
        return fileName.substring(0, separator);
    }

    private List<String> computeNamespace() {
        return this.sourceDirectory.relativize(this.source)
                .getParent()
                .query()
                .collect(new ListCollector<String>());
    }

    @Override
    public Location createLocation() {
        return new Location(this.computeNamespace(), this.computeName());
    }
}
