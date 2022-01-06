package com.meti;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface Module {
    List<Path> listSources() throws IOException;
}
