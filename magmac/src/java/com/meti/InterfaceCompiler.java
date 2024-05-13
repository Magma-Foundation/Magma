package com.meti;

import com.meti.compile.InstanceCompiler;

import java.util.Optional;

public class InterfaceCompiler extends InstanceCompiler {
    public InterfaceCompiler(String input) {
        super(input);
    }

    @Override
    protected String computeKeyword() {
        return "interface";
    }

    @Override
    protected int computeNameEnd(String input, int contentStart) {
        return contentStart;
    }

    @Override
    protected Optional<String> computeParamString(String input) {
        return Optional.of("");
    }
}