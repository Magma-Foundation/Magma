package com.meti;

import com.meti.node.Block;
import com.meti.node.Definition;
import com.meti.node.Implementation;
import com.meti.node.Method;
import org.junit.jupiter.api.Test;

public class MethodFeatureTest extends FeatureTest {
    @Test
    void test() {
        var method = new Method("empty");
        var function = new Implementation("empty", new Block());

        assertCompile(new JavaClass("Test", new Block(method)),
                new Implementation("Test", new Block(function), Definition.Flag.Class));
    }
}
