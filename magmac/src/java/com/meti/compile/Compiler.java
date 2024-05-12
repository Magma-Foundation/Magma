package com.meti.compile;

import com.meti.result.Result;

import java.util.Optional;

public interface Compiler {
    Optional<Result<String, CompileException>> compile();
}
