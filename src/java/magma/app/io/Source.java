package magma.app.io;

import magma.api.collect.list.List;
import magma.api.collect.list.ListCollector;
import magma.api.io.IOError;
import magma.api.io.Path;
import magma.api.result.Result;

public record Source(Path sourceDirectory, Path source) {
    public Result<String, IOError> read() {
        return this.source.readString();
    }

    public String computeName() {
        final String fileName = this.source.findFileName();
        final int separator = fileName.lastIndexOf('.');
        return fileName.substring(0, separator);
    }

    public List<String> computeNamespace() {
        return this.sourceDirectory.relativize(this.source)
                .getParent()
                .query()
                .collect(new ListCollector<String>());
    }

    public Location computeLocation() {
        return new Location(this.computeNamespace(), this.computeName());
    }
}
