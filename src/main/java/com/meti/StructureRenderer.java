package com.meti;

public record StructureRenderer(String delimiter) {
    Output render(Node node) throws CompileException {
        var builder = new StringOutput("struct ")
                .concat(new StringOutput(node.apply(Attribute.Group.Name).asString()))
                .concat(new StringOutput("{"));

        var members = node.apply(Attribute.Group.Members).asNodeList();
        for (var member : members) {
            builder = builder.concat(new NodeOutput(member))
                    .concat(new StringOutput(delimiter));
        }

        return builder.concat(new StringOutput("}"));
    }
}