package com.meti;

import org.junit.jupiter.api.Test;

public class RecordTest extends CompiledTest {
    @Test
    void parameters() {
        assertCompile("record Test(int first, int second){}", "class def Test(first : I16, second : I16) => {}");
    }

    @Test
    void parameter() {
        assertCompile("record Test(int value){}", "class def Test(value : I16) => {}");
    }

    @Test
    void test() {
        assertCompile("record Test(){}", "class def Test() => {}");
    }
}
