package com.meti;

public record IncludeDirectiveRenderer(String name) {
    String render() {
        return "#include <" + name() + ".h>\n";
    }
}