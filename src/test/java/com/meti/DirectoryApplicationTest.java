package com.meti;

import com.meti.io.Directory;
import com.meti.io.Path;
import com.meti.module.DirectoryModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.meti.io.NIOPath.Root;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DirectoryApplicationTest {
    private static final Path Parent = Root.resolveChild("parent");
    private static final Path Out = Root.resolveChild("out");

    @AfterEach
    void tearDown() throws IOException {
        Parent.existingAsDirectory().ifPresent(Directory::deleteAsDirectory);
        Out.existingAsDirectory().ifPresent(Directory::deleteAsDirectory);
    }

    @Test
    void test() throws IOException, ApplicationException {
        var parent = Parent.ensureAsDirectory();
        var child = Parent.resolveChild("child.mg");
        if (!child.exists()) child.createAsFile();

        var module = new DirectoryModule(parent);
        new Application(module).run();

        assertTrue(Out.resolveChild("child.c").exists());
    }
}
