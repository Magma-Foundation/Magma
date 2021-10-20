package com.meti;

public record StructureRenderer(String delimiter) {
    String render(Structure structure) {
        var format = "struct %s{%s}";
        var renderedMembers = String.join(delimiter, structure.members());
        return format.formatted(structure.name(), renderedMembers);
    }
}