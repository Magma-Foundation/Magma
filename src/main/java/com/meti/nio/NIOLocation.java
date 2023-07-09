package com.meti.nio;

import com.meti.core.Option;
import com.meti.iterate.Iterator;
import com.meti.java.JavaString;

import java.nio.file.Path;
import java.util.function.Function;

public interface NIOLocation {
    JavaString asString();

    NIOPath resolveSibling(JavaString other);

    Path unwrap();

    boolean isExtendedBy(String extension);

    Iterator<NIOLocation> iter();

    Option<NIOLocation> last();

    NIOPath relativize(NIOLocation child);

    NIOLocation resolve(JavaString child);

    Option<NIOLocation> parent();

    <R> R into(Function<NIOLocation, R> mapper);
}
