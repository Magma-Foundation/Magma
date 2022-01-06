package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class DirectoryModuleTest {
    private static final NIOPath Parent = NIOPath.Root.resolveChild("parent");

    @Test
    void listSources() throws IOException {
        Parent.createAsDirectory();

        var child = Parent.resolveChild("child.mgs");
        child.createAsFile();

        var module = new DirectoryModule(Parent);
        var sources = module.listSources();

        assertIterableEquals(Collections.singletonList(child), sources);
    }

    @AfterEach
    void tearDown() throws IOException {
        Parent.deleteAsDirectory();
    }
}