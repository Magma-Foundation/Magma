package com.meti.nio;

import com.meti.java.JavaString;

import java.nio.file.Path;

public interface NIOLocation {
    JavaString computeFileNameAsString();

    NIOPath resolveSibling(JavaString other);

    Path unwrap();

    boolean isExtendedBy(String extension);
}
