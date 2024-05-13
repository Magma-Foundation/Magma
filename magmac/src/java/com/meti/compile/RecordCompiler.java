package com.meti.compile;

import java.util.Optional;

public final class RecordCompiler extends InstanceCompiler {
    public RecordCompiler(String input) {
        super(input);
    }

    @Override
    protected String computeKeyword() {
        return "record";
    }

    @Override
    protected Optional<String> computeParamString(String input) throws CompileException {
        var paramStart = input.indexOf('(');
        if (paramStart == -1) return Optional.empty();

        var paramEnd = input.indexOf(')', paramStart );
        if (paramEnd == -1) return Optional.empty();

        var slice = input.substring(paramStart + 1, paramEnd);
        var compiledParams = new ParamsCompiler(slice).compile();

        return Optional.of(compiledParams);
    }

    @Override
    protected int computeNameEnd(String input, int contentStart) {
        return input.indexOf('(');
    }
}