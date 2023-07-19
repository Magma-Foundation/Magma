package com.meti.nio;

import com.meti.core.Option;
import com.meti.iterate.Iterator;
import com.meti.java.String_;

import java.nio.file.Path;
import java.util.function.Function;

public interface Location {
    String_ asString();

    String_ findFileNameAsString();

    Location resolveSibling(String_ other);

    Path unwrap();

    boolean isExtendedBy(String_ extension);

    Iterator<Location> iter();

    Option<Location> last();

    Location relativize(Location child);

    Location resolve(String_ child);

    Option<Location> parent();

    <R> R into(Function<Location, R> mapper);
}
