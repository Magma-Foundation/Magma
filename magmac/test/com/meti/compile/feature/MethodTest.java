package com.meti.compile.feature;

import com.meti.compile.FeatureTest;
import org.junit.jupiter.api.Test;

public class MethodTest extends FeatureTest {
    @Test
    void abstractSimple() {
        assertCompile("void empty()", "\n" +
                                      "def empty() : Void");
    }
}
