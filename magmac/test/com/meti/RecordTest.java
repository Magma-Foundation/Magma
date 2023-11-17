package com.meti;

import org.junit.jupiter.api.Test;

public class RecordTest extends CompiledTest {
    @Test
    void parameter() {
        assertCompile("record Test(int value){}", "class def Test(value : I16) => {}");
    }

    @Test
    void test() {
        assertCompile("record Test(){}", "class def Test() => {}");
    }
}
