package com.meti.app;

import com.meti.java.JavaList;
import com.meti.java.JavaString;
import com.meti.nio.NIOLocation;

public record NIOSource(NIOLocation parent, NIOLocation source) {
    JavaList<JavaString> computePackage() {
        return parent.relativize(source)
                .iter()
                .map(NIOLocation::asString)
                .collect(JavaList.asList());
    }

    JavaString computeName() {
        var fileName = source.last()
                .map(NIOLocation::asString)
                .unwrapOrElse(JavaString.empty());

        return fileName.firstIndexOfChar('.')
                .map(fileName::sliceToEnd)
                .unwrapOrElse(fileName);
    }
}