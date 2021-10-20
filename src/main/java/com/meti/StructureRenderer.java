package com.meti;

public record StructureRenderer(String delimiter) {
    Output render(Node node) {
        var builder = new StringOutput("struct ")
                .concat(new StringOutput(node.name()))
                .concat(new StringOutput("{"));

        var members = node.members();
        for (var member : members) {
            builder = builder.concat(new NodeOutput(member))
                    .concat(new StringOutput(delimiter));
        }

        return builder.concat(new StringOutput("}"));
    }
}