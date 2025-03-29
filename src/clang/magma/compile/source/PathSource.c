package magma.compile.source;class package magma.compile.source;{package magma.compile.source;

import magma.collect.list.List_;class import magma.collect.list.List_;{

import magma.collect.list.List_;
import magma.collect.list.ListCollector;class import magma.collect.list.ListCollector;{
import magma.collect.list.ListCollector;
import magma.io.IOError;class import magma.io.IOError;{
import magma.io.IOError;
import magma.io.Path_;class import magma.io.Path_;{
import magma.io.Path_;
import magma.result.Result;class import magma.result.Result;{
import magma.result.Result;

public record PathSource(Path_ sourceDirectory, Path_ source) implements Source {
    @Override
    public List_<String> computeNamespace() {
        Path_ relative = sourceDirectory.relativize(source);
        Path_ parent = relative.getParent();
        return parent.stream().collect(new ListCollector<>());
    }

    @Override
    public String computeName() {
        String nameWithExt = source().getFileName().asString();
        return nameWithExt.substring(0, nameWithExt.lastIndexOf("."));
    }

    @Override
    public Result<String, IOError> readString() {
        return source.readString();
    }
}class public record PathSource(Path_ sourceDirectory, Path_ source) implements Source {
    @Override
    public List_<String> computeNamespace() {
        Path_ relative = sourceDirectory.relativize(source);
        Path_ parent = relative.getParent();
        return parent.stream().collect(new ListCollector<>());
    }

    @Override
    public String computeName() {
        String nameWithExt = source().getFileName().asString();
        return nameWithExt.substring(0, nameWithExt.lastIndexOf("."));
    }

    @Override
    public Result<String, IOError> readString() {
        return source.readString();
    }
}{

public record PathSource(Path_ sourceDirectory, Path_ source) implements Source {
    @Override
    public List_<String> computeNamespace() {
        Path_ relative = sourceDirectory.relativize(source);
        Path_ parent = relative.getParent();
        return parent.stream().collect(new ListCollector<>());
    }

    @Override
    public String computeName() {
        String nameWithExt = source().getFileName().asString();
        return nameWithExt.substring(0, nameWithExt.lastIndexOf("."));
    }

    @Override
    public Result<String, IOError> readString() {
        return source.readString();
    }
}