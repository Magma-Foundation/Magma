package com.meti.app.compile.parse;

import com.meti.api.collect.stream.Stream;

public interface Visitor {
    Stream<Parser> streamParsers(State state);
}
