package jvm.app.compile;

import jvm.api.io.JavaIOError;
import jvm.api.io.JavaList;
import jvm.api.result.JavaResults;
import magma.Application;
import magma.api.collect.List_;
import magma.api.io.IOError;
import magma.api.result.Result;
import magma.app.compile.Source;

public record PathSource(magma.api.io.Path_ source) implements Source {
    @Override
    public Result<String, IOError> read() {
        return JavaResults
                .wrapSupplier(source::readString)
                .mapErr(JavaIOError::new);
    }

    @Override
    public String computeName() {
        String nameWithExt = Application.SOURCE_DIRECTORY.relativize(source()).last().asString();
        return nameWithExt.substring(0, nameWithExt.lastIndexOf("."));
    }

    @Override
    public List_<String> computeNamespace() {
        return Application.SOURCE_DIRECTORY.relativize(this.source()).getParent()
                .map(parent -> parent.stream().collect(JavaList.collector()))
                .orElseGet(JavaList::new);
    }
}