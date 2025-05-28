package magmac.app;

import magmac.api.Some;
import magmac.api.error.Error;
import magmac.app.compile.Compiler;
import magmac.app.error.ApplicationError;
import magmac.app.error.ThrowableError;
import magmac.app.io.Location;
import magmac.app.io.sources.Sources;
import magmac.app.io.targets.Targets;

import magmac.api.collect.map.Map;
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
                .mapErr(throwable -> new ThrowableError(throwable))
                .mapErr(error -> new ApplicationError(error))
                .match(units -> this.compileAndWrite(units), value -> new Some<>(value));
    }

    private Option<Error> compileAndWrite(Map<Location, String> units) {
        return this.compiler.compile(units).result()
                .mapErr(error1 -> new ApplicationError(error1))
                .match(outputs -> this.targets.writeAll(outputs).map(throwable -> new ThrowableError(throwable)).map(error -> new ApplicationError(error)), err -> new Some<>(err));
    }
}

