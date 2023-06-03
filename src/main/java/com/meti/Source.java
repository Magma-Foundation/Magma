package com.meti;

import java.util.List;

public interface Source {
    List<String> computeNamespace();

    String computeName();
}
