package com.meti.app;

import com.meti.java.JavaList;
import com.meti.java.JavaString;
import com.meti.nio.Location;

public record NIOSource(Location parent, Location source) {
    JavaList<JavaString> computePackage() {
        return parent.relativize(source)
                .iter()
                .map(Location::asString)
                .collect(JavaList.asList());
    }

    JavaString computeName() {
        var fileName = source.last()
                .map(Location::asString)
                .unwrapOrElse(JavaString.empty());

        return fileName.firstIndexOfChar('.')
                .map(fileName::sliceToEnd)
                .unwrapOrElse(fileName);
    }
}