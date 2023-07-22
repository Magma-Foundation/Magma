package com.meti.app.compile;

import com.meti.core.Option;

public interface Transformable {
    Option<Node> transform();
}
