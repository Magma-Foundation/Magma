package com.meti.process;

import com.meti.CompileException;
import com.meti.Processor;
import com.meti.option.Option;
import com.meti.stream.OptionStream;
import com.meti.stream.Stream;
import com.meti.stream.StreamException;

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
