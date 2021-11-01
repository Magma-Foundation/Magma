package com.meti.app.compile;

import com.meti.api.OptionStream;
import com.meti.api.Stream;
import com.meti.api.StreamException;
import com.meti.app.clang.Processor;

public abstract class AbstractStage<A, B extends Processor<A>> {
    public A process() throws CompileException {
        try {
            return createProcessors()
                    .map(Processor::process)
                    .flatMap(OptionStream::new)
                    .first()
                    .orElseThrow(() -> new CompileException("Failed to process."));
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    protected abstract Stream<B> createProcessors();
}
