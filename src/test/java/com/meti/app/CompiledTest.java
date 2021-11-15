package com.meti.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class CompiledTest {
     public static void assertCompile(String input, String output) {
         try {
             var actual = new Compiler(input).compile();
             assertEquals(output, actual);
         } catch (CompileException e) {
             fail(e);
         }
     }
}
