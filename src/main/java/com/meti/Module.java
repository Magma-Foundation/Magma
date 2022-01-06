package com.meti;

import java.io.IOException;
import java.util.List;

public interface Module {
    List<NIOPath> listSources() throws IOException;
}
