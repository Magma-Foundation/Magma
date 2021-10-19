package com.meti;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class StructureTest extends FeatureTest {
    @Test
    void empty() {
        assertStructure("Empty");
    }

    private void assertStructure(String name) {
        assertStructure(name, "", "");
    }

    private void assertStructure(String name, String inputMember, String outputMember) {
        var inputMembers = Collections.singletonList(inputMember);
        var outputMembers = Collections.singletonList(outputMember);
        assertStructure(name, inputMembers, outputMembers);
    }

    private void assertStructure(String name, List<String> inputMembers, List<String> outputMembers) {
        var input = render(name, inputMembers);
        var expected = render(name, outputMembers);
        assertCompile(input, expected + ";");
    }

    @Test
    void name() {
        assertStructure("Wrapper");
    }

    @Test
    void one_member() {
        assertStructure("Wrapper", "x : I16", "int x;");
    }

    @Test
    void two_members() {
        var inputMembers = List.of("x : I16", "y : I16");
        var outputMembers = List.of("int x;", "int y;");
        assertStructure("Point", inputMembers, outputMembers);
    }

    private String render(final String name, List<String> members) {
        var format = "struct %s{%s}";
        var renderedMembers = renderedMembers(members);
        return format.formatted(name, renderedMembers);
    }

    private String renderedMembers(List<String> members) {
        return members.stream()
                .map(value -> value + ";")
                .collect(Collectors.joining());
    }
}
