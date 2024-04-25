package com.meti.node;

import com.meti.option.Option;

public interface Node {

    Option<Attribute> apply(String name);
}
