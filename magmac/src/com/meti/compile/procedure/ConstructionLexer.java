package com.meti.compile.procedure;

import com.meti.java.JavaString;

public final class ConstructionLexer extends InvocationLexer {
    public ConstructionLexer(JavaString javaString) {
        super(javaString);
    }

    @Override
    protected String prefix() {
        return "new ";
    }
}