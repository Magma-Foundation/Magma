package com.meti.compile;

import org.junit.jupiter.api.Test;

public class Bug1 extends FeatureTest {
    @Test
    void call0() throws CompileException {
        assertCompile("""
                map(result -> {
                    try {
                        return result.$();
                    } catch (CompileException e) {
                        return fail(e);
                    }
                });""", """
                map(result => {
                    try {
                        return result.$();
                    } catch (CompileException e) {
                        return fail(e);
                    }
                })""");
    }

    @Test
    void call1() throws CompileException {
        assertCompile("""
                run().map(result -> {
                    try {
                        return result.$();
                    } catch (CompileException e) {
                        return fail(e);
                    }
                });""", """
                run().map(result => {
                    try {
                        return result.$();
                    } catch (CompileException e) {
                        return fail(e);
                    }
                })""");
    }

    @Test
    void call2() throws CompileException {
        assertCompile("""
                new Application(new Source(SOURCE)).run().map(result -> {
                    try {
                        return result.$();
                    } catch (CompileException e) {
                        return fail(e);
                    }
                });""", """
                Application(Source(SOURCE)).run().map(result => {
                    try {
                        return result.$();
                    } catch (CompileException e) {
                        return fail(e);
                    }
                })""");
    }


    @Test
    void root() throws CompileException {
        assertCompile("""
                return new Application(new Source(SOURCE)).run().map(result -> {
                    try {
                        return result.$();
                    } catch (CompileException e) {
                        return fail(e);
                    }
                });""", """
                return Application(Source(SOURCE)).run().map(result => {
                    try {
                        return result.$();
                    } catch (CompileException e) {
                        return fail(e);
                    }
                })""");
    }

    @Test
    void lambda_block() throws CompileException {
        assertCompile("""
                try {
                    return result.$();
                } catch (CompileException e) {
                    return fail(e);
                }""", """
                try {
                \treturn result.$();
                }catch (CompileException e){
                \treturn fail(e);
                }""");
    }

    @Test
    void lambda() throws CompileException {
        assertCompile("""
                result -> {
                    try {
                        return result.$();
                    } catch (CompileException e) {
                        return fail(e);
                    }
                }""", """
                result => {
                    try {
                        return result.$();
                    } catch (CompileException e) {
                        return fail(e);
                    }
                }""");
    }

    @Test
    void no_lambda() throws CompileException {
        assertCompile("return new Application(new Source(SOURCE)).run()", "return Application(Source(SOURCE)).run()");
    }
}
