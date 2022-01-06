package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DirectoryModuleTest {
    private static final NIOPath Parent = NIOPath.Root.resolveChild("parent");

    @Test
    void hasSource() throws IOException {
        Parent.createAsDirectory();

        var child = Parent.resolveChild("child.mg");
        child.createAsFile();

        var module = new DirectoryModule(Parent);

        assertTrue(module.hasSource("child", Collections.emptyList()));
    }

    @Test
    void listSources() throws IOException {
        Parent.createAsDirectory();

        var child = Parent.resolveChild("child.mg");
        child.createAsFile();

        var module = new DirectoryModule(Parent);
        assertTrue(module.listSources()
                .stream()
                .map(Source::computeName)
                .allMatch(name -> module.hasSource(name, Collections.emptyList())));
    }

    @Test
    void listSubSources() throws IOException {
        Parent.createAsDirectory();

        var child = Parent.resolveChild("child");
        child.createAsDirectory();

        var grandChild = child.resolveChild("grandChild.mg");
        grandChild.createAsFile();

        var module = new DirectoryModule(Parent);
        assertTrue(module.hasSource("grandChild", List.of("child")));
    }

    @AfterEach
    void tearDown() throws IOException {
        Parent.deleteAsDirectory();
    }
}