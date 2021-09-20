package com.meti.source;

import com.meti.EmptyStream;
import com.meti.Script;
import com.meti.SingleStream;
import com.meti.Stream;

public class SingleSource implements Source {
    private final Script script;

    public SingleSource(Script script) {
        this.script = script;
    }

    @Override
    public Stream<Script> stream() {
        if (script.exists()) {
            return new SingleStream(script);
        } else {
            return new EmptyStream<>();
        }
    }
}
