package com.meti.nio;

import com.meti.core.Option;
import com.meti.iterate.Iterator;
import com.meti.java.JavaString;

import java.nio.file.Path;
import java.util.function.Function;

public interface Location {
    JavaString asString();

    Location resolveSibling(JavaString other);

    Path unwrap();

    boolean isExtendedBy(String extension);

    Iterator<Location> iter();

    Option<Location> last();

    Location relativize(Location child);

    Location resolve(JavaString child);

    Option<Location> parent();

    <R> R into(Function<Location, R> mapper);
}
