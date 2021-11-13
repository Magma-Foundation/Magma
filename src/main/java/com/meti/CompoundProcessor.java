package com.meti;

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
