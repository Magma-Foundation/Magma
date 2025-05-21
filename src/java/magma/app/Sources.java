package magma.app;

import magma.api.collect.list.Iterable;
import magma.api.io.IOError;
import magma.api.io.Path;
import magma.api.result.Result;
import magma.app.io.Source;

interface Sources {
    Result<Iterable<Source>, IOError> listSources();

    Iterable<Source> retainSources(Iterable<Path> children);
}
