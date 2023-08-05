package com.meti.app.feature;

import org.junit.jupiter.api.Test;

public class RecordTest extends CompiledTest {
    @Test
    void test() {
        assertCompile("record Test(){}", "class def Test() => {}");
    }
}
