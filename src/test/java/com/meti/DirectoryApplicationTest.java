package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.meti.NIOPath.Root;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DirectoryApplicationTest {

    private static final NIOPath Parent = Root.resolveChild("parent");
    private static final NIOPath Out = Root.resolveChild("out");

    @Test
    void test() throws IOException {
        Parent.createAsDirectory();
        Parent.resolveChild("child.mg").createAsFile();

        var module = new DirectoryModule(Parent);
        new Application(module).run();

        assertTrue(Out.resolveChild("parent")
                .resolveChild("child.c")
                .exists());
    }

    @AfterEach
    void tearDown() throws IOException {
        Parent.deleteAsDirectory();
        Out.deleteAsDirectory();
    }
}
