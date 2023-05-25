package com.meti;

import java.util.Collections;

public class Function extends Definition {

    public Function(String name, boolean isPublic) {
        super(name, isPublic ? Collections.singleton(Flag.Public) : Collections.emptySet());
    }

    @Override
    public String render() {
        var prefix = isPublic() ? "public " : "";
        return prefix + "class def " + name + "()";
    }
}
