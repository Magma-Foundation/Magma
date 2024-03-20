package com.meti.compile;

import com.meti.collect.stream.Collectors;
import com.meti.compile.node.Content;
import com.meti.compile.scope.DefinitionNode;
import com.meti.compile.string.StringNode;
import com.meti.java.JavaString;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class CompilerTest extends FeatureTest {
    @Test
    void lexTree1() {
        var input = new DefinitionNode(0, Collections.emptyList(),
                JavaString.from("actual"),
                new Content("new Compiler(input)\"", 0));

        var actual = Compiler.lexTree(input).collect(Collectors.toNativeList());
        assertIterableEquals(Collections.emptyList(), actual);
    }

    @Test
    void lexTree0() {
        var input = new StringNode(JavaString.from("var actual = new Compiler(input)"));
        var actual = Compiler.lexTree(input).collect(Collectors.toNativeList());
        assertIterableEquals(Collections.singletonList(input), actual);
    }

    @Test
    void test1() throws CompileException {
        assertCompile("SOURCE", "SOURCE");
    }

    @Test
    void field() throws CompileException {
        assertCompile("first.second", "first.second");
    }

    @Test
    void test2() throws CompileException {
        assertCompile("new Application(SOURCE)", "Application(SOURCE)");
    }

    @Test
    void test3() throws CompileException {
        assertCompile("new Application(SOURCE).run()", "Application(SOURCE).run()");
    }

    @Test
    void test4() throws CompileException {
        assertCompile("new Application(SOURCE).run", "Application(SOURCE).run");
    }


    @Test
    void test() throws CompileException {
        assertCompile("runWithSource()", "runWithSource()");
    }
}