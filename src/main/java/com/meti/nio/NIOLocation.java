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
    protected final Path location;

    public NIOLocation(Path location) {
        this.location = location;
    }

    @Override
    public JavaString asString() {
        return new JavaString(location.getFileName().toString());
    }

    @Override
    public Location resolveSibling(JavaString other) {
        return new NIOLocation(location.resolveSibling(other.unwrap()));
    }

    @Override
    public Path unwrap() {
        return location;
    }

    @Override
    public boolean isExtendedBy(String extension) {
        return location.toString().endsWith("." + extension);
    }

    @Override
    public Iterator<Location> iter() {
        return new IndexIterator<>() {
            @Override
            protected Location apply(Index index) {
                return new NIOLocation(location.getName(index.value()));
            }

            @Override
            protected Index length() {
                return new Index(location.getNameCount());
            }
        };
    }

    @Override
    public Option<Location> last() {
        var length = location.getNameCount();
        return length == 0
                ? new None<>()
                : new Some<>(new NIOLocation(location.getName(length - 1)));
    }

    @Override
    public Location relativize(Location child) {
        return new NIOLocation(location.relativize(child.unwrap()));
    }

    @Override
    public Location resolve(JavaString child) {
        return new NIOLocation(location.resolve(child.unwrap()));
    }

    @Override
    public Option<Location> parent() {
        var parent = location.getParent();
        if (parent == null) return new None<>();
        else return Some.apply(new NIOLocation(parent));
    }

    @Override
    public <R> R into(Function<Location, R> mapper) {
        return mapper.apply(this);
    }
}
