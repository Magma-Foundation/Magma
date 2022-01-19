package com.meti.app.compile.condition;

import com.meti.app.compile.CompiledTest;
import org.junit.jupiter.api.Test;

public class ElseTest {
    @Test
    void test(){
        CompiledTest.assertSourceCompile("else {}", "else {}");
    }
}
