package com.meti.stream;

import com.meti.EndOfStreamException;
import com.meti.Script;

public class SingleStream extends AbstractStream<Script> {
    private final Script value;
    private boolean retrieved = false;

    public SingleStream(Script value) {
        this.value = value;
    }

    @Override
    protected Script head() throws StreamException {
        if (retrieved) {
            throw new EndOfStreamException();
        } else {
            retrieved = true;
            return value;
        }
    }
}
