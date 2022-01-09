package com.meti;

import com.meti.io.NIOPath;
import com.meti.module.DirectoryModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.meti.io.NIOPath.Root;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DirectoryApplicationTest {

    private static final NIOPath Parent = Root.resolveChild("parent");
    private static final NIOPath Out = Root.resolveChild("out");

    @Test
    void test() throws IOException, ApplicationException {
        if(!Parent.exists()) Parent.createAsDirectory();

        var child = Parent.resolveChild("child.mg");
        if(!child.exists()) child.createAsFile();

        var module = new DirectoryModule(Parent);
        new Application(module).run();

        assertTrue(Out.resolveChild("child.c").exists());
    }

    @AfterEach
    void tearDown() throws IOException {
        Parent.deleteAsDirectory();
        Out.deleteAsDirectory();
    }
}