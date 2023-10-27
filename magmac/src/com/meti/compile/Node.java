package com.meti.compile;

import com.meti.api.option.Option;

public interface Node {
    boolean is(String type);

    Option<String> getString(String name);
}
