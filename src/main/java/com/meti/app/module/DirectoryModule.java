package com.meti.app.module;

import com.meti.api.io.Directory;
import com.meti.api.io.File;
import com.meti.api.io.Path;
import com.meti.app.source.FileSource;
import com.meti.app.source.Source;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public record DirectoryModule(Directory root) implements Module {
    public boolean hasSource(String name, List<String> packageList) {
        return packageList.stream()
                .reduce(root, Path::resolveChild, (previous, next) -> next)
                .resolveChild(name + ".mg").exists();
    }

    @Override
    public List<Source> listSources() throws IOException {
        var oldSources = root.walk().collect(Collectors.toList());
        var newSources = new ArrayList<Source>();
        for (Path oldSource : oldSources) {
            if(oldSource.hasExtensionOf("mg")) {
                newSources.add(createSource(oldSource.ensureAsFile()));
            }
        }
        return newSources;
    }

    private Source createSource(File file) {
        var relative = root.relativize(file);
        var names = relative.streamNames().collect(Collectors.toList());
        var packageList = names.subList(0, names.size() - 1);
        return new FileSource(file, packageList);
    }
}
