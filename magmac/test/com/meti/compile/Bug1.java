package com.meti.compile;

import org.junit.jupiter.api.Test;

public class Bug1 extends CompiledTest {
    @Test
    void root() throws CompileException {
        assertCompile("""
                return new Application(new Source(SOURCE)).run().map(result -> {
                    try {
                        return result.$();
                    } catch (CompileException e) {
                        return fail(e);
                    }
                });""", "");
    }

    @Test
    void lambda_block() throws CompileException {
        assertCompile("""
                try {
                    return result.$();
                } catch (CompileException e) {
                    return fail(e);
                }""",
                """
                        try {
                        \treturn result.$();
                        }catch (CompileException e){
                        \treturn fail(e);
                        }""");
    }

    @Test
    void no_lambda() throws CompileException {
        assertCompile("return new Application(new Source(SOURCE)).run()",
                "return Application(Source(SOURCE)).run()");
    }
}
