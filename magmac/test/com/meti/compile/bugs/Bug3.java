package com.meti.compile.bugs;

import com.meti.compile.CompiledTest;
import org.junit.jupiter.api.Test;

public class Bug3 extends CompiledTest {
    @Test
    void test() {
        assertCompile("""
                \"\"\"
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
                \"\"\"
                """, """
                \"\"\"
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
                \"\"\"""");
    }
}
