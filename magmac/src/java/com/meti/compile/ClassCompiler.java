package com.meti.compile;

import java.util.Optional;

public final class ClassCompiler extends InstanceCompiler {
    public ClassCompiler(String input) {
        super(input);
    }

    @Override
    protected String computeKeyword() {
        return "class";
    }

    @Override
    protected Optional<String> computeParamString(String input) {
        return Optional.of("");
    }

    @Override
    protected int computeContentStart(String input, int contentStart) {
        return contentStart;
    }
}