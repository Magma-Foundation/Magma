package com.meti;

public record StructureRenderer(String delimiter) {
    Output render(Structure structure) {
        var builder = new StringOutput("struct ")
                .concat(new StringOutput(structure.name()))
                .concat(new StringOutput("{"));

        var members = structure.members();
        for (var member : members) {
            builder = builder.concat(new NodeOutput(member))
                    .concat(new StringOutput(delimiter));
        }

        return builder.concat(new StringOutput("}"));
    }
}