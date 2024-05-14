package com.meti.compile;

import com.meti.api.result.Result;

import java.util.List;
import java.util.Optional;

public interface RootCompiler {
    Optional<Result<String, CompileException>> compile(List<String> stack);
}
