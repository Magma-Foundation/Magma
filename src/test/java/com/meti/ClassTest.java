package com.meti;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

public class ClassTest extends FeatureTest {
    @Test
    void simple() {
        assertCompile(new JavaClass("Test", new Block()),
                new Implementation("Test", new Block(), List.of(Definition.Flag.Class)));
    }

    @Test
    void simpleAnother() {
        assertCompile(new JavaClass("Bar", new Block()),
                new Implementation("Bar", new Block(), List.of(Definition.Flag.Class)));
    }

}
