package com.meti.module;

import com.meti.io.NIOPath;
import com.meti.source.PathSource;
import com.meti.source.Source;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public record DirectoryModule(NIOPath root) implements Module {

    public boolean hasSource(String name, List<String> packageList) {
        return packageList.stream()
                .reduce(root, NIOPath::resolveChild, (previous, next) -> next)
                .resolveChild(name + ".mg").exists();
    }

    @Override
    public List<Source> listSources() throws IOException {
        return root.walk()
                .filter(value -> value.hasExtensionOf("mg"))
                .map(this::createSource)
                .collect(Collectors.toList());
    }

    private PathSource createSource(NIOPath path) {
        var relative = root.relativize(path);
        var names = relative.streamNames().collect(Collectors.toList());
        var packageList = names.subList(0, names.size() - 1);
        return new PathSource(path, packageList);
    }
}
