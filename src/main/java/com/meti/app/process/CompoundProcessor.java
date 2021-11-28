package com.meti.app.process;

import com.meti.api.option.Option;
import com.meti.api.stream.OptionStream;
import com.meti.api.stream.Stream;
import com.meti.api.stream.StreamException;
import com.meti.app.CompileException;

public abstract class CompoundProcessor<T> implements Processor<T> {
    @Override
    public Option<T> process() throws CompileException {
        try {
            return stream()
                    .map(Processor::process)
                    .flatMap(OptionStream::new)
                    .first();
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    protected abstract Stream<Processor<T>> stream();
}
