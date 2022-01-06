package com.meti;

import java.io.IOException;
import java.util.List;

public interface Module {
    boolean hasSource(String name, List<String> packageList);

    List<Source> listSources() throws IOException;
}
