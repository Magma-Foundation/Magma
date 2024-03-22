package com.meti.compile.bugs;

import com.meti.compile.FeatureTest;
import org.junit.jupiter.api.Test;

public class Bug2 extends FeatureTest {
    @Test
    void compile() {
        assertCompile("""
                package com.meti.compile;
                                
                import static org.junit.jupiter.api.Assertions.assertEquals;
                import static org.junit.jupiter.api.Assertions.fail;
                                
                public class CompiledTest {
                    protected static void assertCompile(String input, String output) {
                        try {
                            var actual = new Compiler(input).compile();
                            assertEquals(output, actual);
                        } catch (CompileException e) {
                            fail(e);
                        }
                    }
                }
                """, """
                import { assertEquals } from org.junit.jupiter.api.Assertions;
                import { fail } from org.junit.jupiter.api.Assertions;

                export object CompiledTest = {
                \t
                \tdef assertCompile() : Void => {
                \t\ttry {
                \t\t\tlet actual = Compiler(input).compile();
                \t\t\tassertEquals(output, actual);
                \t\t}
                \t\tcatch (CompileException e){
                \t\t\tfail(e);
                \t\t}
                \t}
                }""");
    }
}
