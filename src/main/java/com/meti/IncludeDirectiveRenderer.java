package com.meti;

public final class IncludeDirectiveRenderer {
    String render(String name) {
        return "#include <" + name + ".h>\n";
    }
}