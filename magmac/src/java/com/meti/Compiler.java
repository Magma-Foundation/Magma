package com.meti;

import java.util.Optional;

public interface Compiler {
    Optional<Result<String, CompileException>> compile();
}
