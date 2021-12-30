package com.meti.app;

import com.meti.api.io.File;

public interface Source {
    Stream<File> stream();
}
