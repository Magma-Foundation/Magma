package com.meti.source;

import java.util.stream.Stream;

public interface Source {
    String computeName();

    Stream<String> computePackage();
}
