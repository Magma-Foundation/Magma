package magmac.app;

import magmac.api.Some;
import magmac.api.error.Error;
import magmac.app.compile.Compiler;
import magmac.app.error.ApplicationError;
import magmac.app.error.ThrowableError;
import magmac.app.io.sources.Sources;
import magmac.app.io.targets.Targets;

import magmac.api.Option;
import magmac.app.stage.unit.UnitSet;

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
                .match(this::compileAndWrite, Some::new);
    }

    private Option<Error> compileAndWrite(UnitSet<String> units) {
        return this.compiler.compile(units).result()
                .mapErr(ApplicationError::new)
                .match((UnitSet<String> outputs) -> this.targets.writeAll(outputs).map(ThrowableError::new).map(ApplicationError::new), Some::new);
    }
}

