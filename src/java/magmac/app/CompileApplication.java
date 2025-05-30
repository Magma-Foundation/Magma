package magmac.app;

import magmac.api.Option;
import magmac.api.Some;
import magmac.api.error.Error;
import magmac.app.compile.Compiler;
import magmac.app.error.ApplicationError;
import magmac.app.error.ThrowableError;
import magmac.app.io.targets.Targets;
import magmac.app.lang.Serializable;
import magmac.app.lang.node.JavaRoot;
import magmac.app.stage.unit.UnitSet;

public final class CompileApplication<R extends Serializable> implements Application {
    private final Targets targets;
    private final Compiler compiler;

    public CompileApplication(Compiler compiler, Targets targets) {
        this.targets = targets;
        this.compiler = compiler;
    }

    @Override
    public Option<Error> compileAndWrite(UnitSet<JavaRoot> units) {
        return this.compiler.parseAndGenerator(units)
                .toResult()
                .mapErr(ApplicationError::new)
                .match((UnitSet<String> outputs) -> this.targets.writeAll(outputs).map(ThrowableError::new).map(ApplicationError::new), Some::new);
    }
}

