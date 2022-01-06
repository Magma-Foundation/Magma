package com.meti.module;

import com.meti.source.Source;

import java.io.IOException;
import java.util.List;

public interface Module {
    List<Source> listSources() throws IOException;
}
