package com.meti;

import java.io.IOException;

public interface Gateway {
    Result<NativeSet<NativePath>, IOException> collectSources();
}
