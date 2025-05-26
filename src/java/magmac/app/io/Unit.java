package magmac.app.io;

import jvm.io.SafeFiles;
import magmac.api.result.Result;

import java.io.IOException;
import java.nio.file.Path;

public record Unit(Path source) {
    public String computeName() {
        String fileName = this.source.getFileName().toString();
        int fileSeparator = fileName.lastIndexOf('.');
        return fileName.substring(0, fileSeparator);
    }

    public Result<String, IOException> read() {
        return SafeFiles.readString(this.source);
    }
}