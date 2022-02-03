package com.meti.app.module;

import com.meti.api.collect.java.JavaList;
import com.meti.api.collect.stream.Stream;
import com.meti.app.source.Source;

import java.io.IOException;
import java.util.List;

public interface Module {
    default Stream<Source> streamSources() throws IOException {
        return new JavaList<>(listSources1()).stream();
    }

    List<Source> listSources1() throws IOException;
}
