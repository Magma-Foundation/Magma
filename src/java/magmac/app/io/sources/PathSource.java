package magmac.app.io.sources;

import magmac.api.collect.list.List;
import magmac.api.collect.list.Lists;
import magmac.app.io.IOResult;
import magmac.app.io.Location;
import magmac.app.io.SafeFiles;

import java.nio.file.Path;

public record PathSource(Path root, Path child) implements Source {
    @Override
    public String computeName() {
        String fileName = this.child.getFileName().toString();
        int fileSeparator = fileName.lastIndexOf('.');
        return fileName.substring(0, fileSeparator);
    }

    @Override
    public IOResult<String> read() {
        return SafeFiles.readString(this.child);
    }

    @Override
    public Location computeLocation() {
        return new Location(this.computeNamespace(), this.computeName());
    }

    private List<String> computeNamespace() {
        List<String> segments = Lists.empty();
        Path relative = this.root.relativize(this.child).getParent();
        if (null == relative) {
            return Lists.empty();
        }

        int nameCount = relative.getNameCount();
        for (int i = 0; i < nameCount; i++) {
            segments = segments.add(relative.getName(i).toString());
        }
        return segments;
    }
}