package magmac.app;

import magmac.api.Some;
import magmac.api.error.Error;
import magmac.app.compile.Compiler;
import magmac.app.compile.error.error.CompileError;
import magmac.app.error.ApplicationError;
import magmac.app.error.ThrowableError;
import magmac.app.io.Location;
import magmac.app.io.sources.Sources;
import magmac.app.io.targets.Targets;

import magmac.api.collect.map.Map;
import magmac.api.Option;

import java.io.IOException;

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
                .mapErr((IOException throwable) -> new ThrowableError(throwable))
                .mapErr((ThrowableError error) -> new ApplicationError(error))
                .match((Map<Location, String> units) -> this.compileAndWrite(units), (ApplicationError value) -> new Some<>(value));
    }

    private Option<Error> compileAndWrite(Map<Location, String> units) {
        return this.compiler.compile(units).result()
                .mapErr((CompileError error1) -> new ApplicationError(error1))
                .match((Map<Location, String> outputs) -> this.targets.writeAll(outputs).map((IOException throwable) -> new ThrowableError(throwable)).map((ThrowableError error) -> new ApplicationError(error)), (ApplicationError err) -> new Some<>(err));
    }
}

