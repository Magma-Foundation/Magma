package magmac.app;

import magmac.api.Some;
import magmac.api.error.Error;
import magmac.app.compile.Compiler;
import magmac.app.error.ApplicationError;
import magmac.app.error.ThrowableError;
import magmac.app.io.Location;
import magmac.app.io.sources.Sources;
import magmac.app.io.targets.Targets;

import magmac.api.collect.Map;
import magmac.api.Option;

public final class CompileApplication implements Application {
    private final Sources sources;
    private final Targets targets;
    private final Compiler compiler;

    public CompileApplication(Sources sources, Compiler compiler, Targets targets) {
        this.sources = sources;
        this.targets = targets;
        this.compiler = compiler;
    }

    @Override
    public Option<Error> run() {
        return this.sources.readAll()
                .mapErr(ThrowableError::new)
                .mapErr(ApplicationError::new)
                .match(units -> this.compileAndWrite(units), value -> new Some<>(value));
    }

    private Option<Error> compileAndWrite(Map<Location, String> units) {
        return this.compiler.compile(units).result()
                .mapErr(ApplicationError::new)
                .match(outputs -> this.targets.writeAll(outputs).map(ThrowableError::new).map(ApplicationError::new), err -> new Some<>(err));
    }
}

