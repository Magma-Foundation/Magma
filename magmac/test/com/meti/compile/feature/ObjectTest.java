package com.meti.compile.feature;

import com.meti.compile.FeatureTest;
import org.junit.jupiter.api.Test;

public class ObjectTest extends FeatureTest {
    @Test
    void extends_() {
        assertCompile("class IntentionalException extends Exception {}",
                "\nobject IntentionalException = {\n}");
    }

    @Test
    void test() {
        assertCompile("class Test {}", "\n" +
                                       "object Test = {\n" +
                                       "}");
    }
}
