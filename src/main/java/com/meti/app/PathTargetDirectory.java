package com.meti.app;

import com.meti.api.io.File;
import com.meti.api.io.IOException;
import com.meti.api.io.Path;

public record PathTargetDirectory(Path root) implements TargetDirectory {
    @Override
    public File write(String output, Reference reference_) throws IOException {
        var target = root.resolveChild(reference_.computeName() + ".c");
        return target.create().writeAsString(output);
    }
}
