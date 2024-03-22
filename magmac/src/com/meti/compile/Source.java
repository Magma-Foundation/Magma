package com.meti.compile;

import com.meti.collect.result.Result;

import java.io.IOException;
import java.util.List;

public interface Source {
    Result<String, IOException> read();

    String findName();

    List<String> findPackage();
}
