package magmac.app;

import magmac.app.error.ApplicationError;
import magmac.app.error.ThrowableError;
import magmac.app.io.Location;
import magmac.app.io.Sources;
import magmac.app.io.Targets;

import java.util.Map;
import java.util.Optional;

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
    public Optional<Error> run() {
        return this.sources.readAll()
                .mapErr(ThrowableError::new)
                .mapErr(ApplicationError::new)
                .match(units -> this.compileAndWrite(units), Optional::of);
    }

    private Optional<Error> compileAndWrite(Map<Location, String> units) {
        return this.compiler.compile(units)
                .mapErr(ApplicationError::new)
                .match(outputs -> this.targets.writeAll(outputs).map(ThrowableError::new).map(ApplicationError::new), err -> Optional.of(err));
    }
}

