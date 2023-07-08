package com.meti;

import java.nio.file.Path;

public interface NIOLocation {
    JavaString computeFileNameAsString();

    NIOPath resolveSibling(JavaString other);

    Path unwrap();
}
