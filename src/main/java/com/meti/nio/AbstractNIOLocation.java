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

public class AbstractNIOLocation implements NIOLocation {
    protected final Path location;

    public AbstractNIOLocation(Path location) {
        this.location = location;
    }

    @Override
    public JavaString asString() {
        return new JavaString(location.getFileName().toString());
    }

    @Override
    public NIOPath resolveSibling(JavaString other) {
        return new NIOPath(location.resolveSibling(other.unwrap()));
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
    public Iterator<NIOLocation> iter() {
        return new IndexIterator<>() {
            @Override
            protected NIOLocation apply(Index index) {
                return new AbstractNIOLocation(location.getName(index.value()));
            }

            @Override
            protected Index length() {
                return new Index(location.getNameCount());
            }
        };
    }

    @Override
    public Option<NIOLocation> last() {
        var length = location.getNameCount();
        return length == 0
                ? new None<>()
                : new Some<>(new AbstractNIOLocation(location.getName(length - 1)));
    }

    @Override
    public NIOPath relativize(NIOLocation child) {
        return new NIOPath(location.relativize(child.unwrap()));
    }

    @Override
    public NIOLocation resolve(JavaString child) {
        return new AbstractNIOLocation(location.resolve(child.unwrap()));
    }

    @Override
    public Option<NIOLocation> parent() {
        var parent = location.getParent();
        if (parent == null) return new None<>();
        else return Some.apply(new AbstractNIOLocation(parent));
    }

    @Override
    public <R> R into(Function<NIOLocation, R> mapper) {
        return mapper.apply(this);
    }
}
