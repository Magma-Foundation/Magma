package com.meti.compile;

import com.meti.result.Result;

import java.util.Optional;

public interface RootCompiler {
    Optional<Result<String, CompileException>> compile();
}
