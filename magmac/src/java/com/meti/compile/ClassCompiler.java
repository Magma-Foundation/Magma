package com.meti.compile;

import java.util.List;
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
    protected Optional<String> computeParamString(String input, List<String> stack) {
        return Optional.of("");
    }

    @Override
    protected int computeNameEnd(String input, int contentStart) {
        return contentStart;
    }
}