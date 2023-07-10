package com.meti.nio;

import com.meti.collect.Index;
import com.meti.core.None;
import com.meti.core.Option;
import com.meti.core.Some;
import com.meti.iterate.IndexIterator;
import com.meti.iterate.Iterator;
import com.meti.java.JavaString;

import java.nio.file.Path;
import java.util.function.Function;

public class NIOLocation implements Location {
    protected final Path value;

    public NIOLocation(Path value) {
        this.value = value;
    }

    @Override
    public JavaString asString() {
        return new JavaString(value.getFileName().toString());
    }

    @Override
    public Location resolveSibling(JavaString other) {
        return new NIOLocation(value.resolveSibling(other.unwrap()));
    }

    @Override
    public Path unwrap() {
        return value;
    }

    @Override
    public boolean isExtendedBy(String extension) {
        return value.toString().endsWith("." + extension);
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
    public Location resolve(JavaString child) {
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
