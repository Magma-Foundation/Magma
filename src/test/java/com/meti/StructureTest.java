package com.meti;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

public class StructureTest extends FeatureTest {
    @Test
    void empty() {
        assertStructure("Empty");
    }

    private void assertStructure(String name) {
        assertStructure(name, Collections.emptyList(), Collections.emptyList());
    }

    private void assertStructure(String name, List<String> inputMembers, List<String> outputMembers) {
        var input = new StructureRenderer(",").render(new Structure(name, inputMembers));
        var expected = new StructureRenderer(";").render(new Structure(name, outputMembers));
        assertCompile(input, expected + ";");
    }

    @Test
    void name() {
        assertStructure("Wrapper");
    }

    @Test
    void one_member() {
        var inputMembers = Collections.singletonList("x : I16");
        var outputMembers = Collections.singletonList("int x;");
        assertStructure("Wrapper", inputMembers, outputMembers);
    }

    @Test
    void two_members() {
        var inputMembers = List.of("x : I16", "y : I16");
        var outputMembers = List.of("int x;", "int y;");
        assertStructure("Point", inputMembers, outputMembers);
    }
}
