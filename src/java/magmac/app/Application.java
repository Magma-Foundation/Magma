package magmac.app;

import magmac.api.Option;
import magmac.api.error.Error;
import magmac.app.lang.node.JavaRoot;
import magmac.app.stage.unit.UnitSet;

public interface Application {
    Option<Error> compileAndWrite(UnitSet<JavaRoot> units);
}
