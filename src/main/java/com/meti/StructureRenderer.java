package com.meti;

import java.util.List;

public record StructureRenderer(String delimiter) {
    static String renderedMembers(List<String> members, String delimiter) {
        return String.join(delimiter, members);
    }

    String render(final String name, List<String> members) {
        var format = "struct %s{%s}";
        var renderedMembers = renderedMembers(members, delimiter());
        return format.formatted(name, renderedMembers);
    }
}