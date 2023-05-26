package com.meti;

import com.meti.node.Block;
import com.meti.node.Definition;
import com.meti.node.Implementation;
import org.junit.jupiter.api.Test;

import java.util.List;

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
