package com.meti;

public interface Renderer {
    Output render(Node anImport) throws CompileException;
}
