package com.meti.source;

import com.meti.Script;
import com.meti.stream.EmptyStream;
import com.meti.stream.SingleStream;
import com.meti.stream.Stream;

public class SingleSource implements Source {
    private final Script script;

    public SingleSource(Script script) {
        this.script = script;
    }

    @Override
    public Stream<? extends Script> stream() {
        if (script.exists()) {
            return new SingleStream(script);
        } else {
            return new EmptyStream<>();
        }
    }
}
