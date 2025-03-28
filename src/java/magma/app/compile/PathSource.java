package magma.app.compile;

import jvm.api.collect.Lists;
import magma.Application;
import magma.api.collect.List_;
import magma.api.io.IOError;
import magma.api.io.Path_;
import magma.api.result.Result;

public record PathSource(Path_ source) implements Source {
    @Override
    public Result<String, IOError> read() {
        return source.readString();
    }

    @Override
    public String computeName() {
        String nameWithExt = Application.SOURCE_DIRECTORY.relativize(source()).last().asString();
        return nameWithExt.substring(0, nameWithExt.lastIndexOf("."));
    }

    @Override
    public List_<String> computeNamespace() {
        return Application.SOURCE_DIRECTORY.relativize(this.source()).findParent()
                .map(parent -> parent.stream().collect(Lists.collectToList()))
                .orElseGet(Lists::empty);
    }
}