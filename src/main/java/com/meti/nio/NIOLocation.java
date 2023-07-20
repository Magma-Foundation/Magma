package com.meti.nio;

import com.meti.core.None;
import com.meti.core.Option;
import com.meti.core.Some;
import com.meti.iterate.Index;
import com.meti.iterate.IndexIterator;
import com.meti.iterate.Iterator;
import com.meti.java.JavaString;
import com.meti.java.String_;

import java.nio.file.Path;
import java.util.function.Function;

public class NIOLocation implements Location {
    protected final Path value;

    public NIOLocation(Path value) {
        this.value = value;
    }

    @Override
    public String_ asString() {
        return JavaString.from(value.toString());
    }

    @Override
    public String_ findFileNameAsString() {
        return JavaString.from(value.getFileName().toString());
    }

    @Override
    public Location resolveSibling(String_ other) {
        return new NIOLocation(value.resolveSibling(other.unwrap()));
    }

    @Override
    public Path unwrap() {
        return value;
    }

    @Override
    public boolean isExtendedBy(String_ extension) {
        return value.toString().endsWith("." + extension.unwrap());
    }

    @Override
    public Iterator<Location> iter() {
        return new IndexIterator<>() {
            @Override
            protected Location apply(Index index) {
                return new NIOLocation(value.getName(index.value()));
            }

            @Override
            protected Index length() {
                return new Index(value.getNameCount(), value.getNameCount());
            }
        };
    }

    @Override
    public Option<Location> last() {
        var length = value.getNameCount();
        return length == 0
                ? new None<>()
                : new Some<>(new NIOLocation(value.getName(length - 1)));
    }

    @Override
    public Location relativize(Location child) {
        return new NIOLocation(value.relativize(child.unwrap()));
    }

    @Override
    public Location resolve(String_ child) {
        return new NIOLocation(value.resolve(child.unwrap()));
    }

    @Override
    public Option<Location> parent() {
        var parent = value.getParent();
        if (parent == null) return new None<>();
        else return Some.apply(new NIOLocation(parent));
    }

    @Override
    public <R> R into(Function<Location, R> mapper) {
        return mapper.apply(this);
    }
}
