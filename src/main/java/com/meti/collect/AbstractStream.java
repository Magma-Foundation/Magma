package com.meti.collect;

import com.meti.core.F1;
import com.meti.core.F2;

public abstract class AbstractStream<T> implements Stream<T> {
    @Override
    public <R, E extends Exception> R foldRight(R identity, F2<R, T, R, E> folder) throws StreamException, E {
        R current = identity;
        do {
            try {
                var next = head();
                current = folder.apply(current, next);
            } catch (EndOfStreamException e) {
                break;
            }
        } while (true);
        return current;
    }

    @Override
    public <R> Stream<R> map(F1<T, R, ?> mapper) throws StreamException {
        return new AbstractStream<>() {
            @Override
            public R head() throws StreamException {
                try {
                    return mapper.apply(AbstractStream.this.head());
                } catch (EndOfStreamException e) {
                    throw e;
                } catch (Exception e) {
                    throw new StreamException(e);
                }
            }
        };
    }
}
