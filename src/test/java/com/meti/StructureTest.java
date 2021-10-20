package com.meti;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

public class StructureTest extends FeatureTest {
    @Test
    void empty() {
        assertStructure("Empty");
    }

    private void assertStructure(String name) {
        assertStructure(name, Collections.emptyList(), Collections.emptyList());
    }

    private void assertStructure(String name, List<? extends Node> inputMembers, List<? extends Node> outputMembers) {
        try {
            var input = new StructureRenderer(",").render(new Structure(name, inputMembers)).compute();
            var expected = new StructureRenderer(";").render(new Structure(name, outputMembers)).compute();
            assertCompile(input, expected + ";");
        } catch (CompileException e) {
            fail(e);
        }
    }

    @Test
    void name() {
        assertStructure("Wrapper");
    }

    @Test
    void one_member() {
        var inputMembers = Collections.singletonList(new Content("x : I16"));
        var outputMembers = Collections.singletonList(new Content("int x;"));
        assertStructure("Wrapper", inputMembers, outputMembers);
    }

    @Test
    void two_members() {
        var inputMembers = List.of(new Content("x : I16"), new Content("y : I16"));
        var outputMembers = List.of(new Content("int x;"), new Content("int y;"));
        assertStructure("Point", inputMembers, outputMembers);
    }
}
