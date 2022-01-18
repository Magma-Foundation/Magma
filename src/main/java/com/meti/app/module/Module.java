package com.meti.app.module;

import com.meti.app.source.Source;

import java.io.IOException;
import java.util.List;

public interface Module {
    List<Source> listSources() throws IOException;
}
