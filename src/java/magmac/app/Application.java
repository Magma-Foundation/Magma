package magmac.app;

import magmac.api.Option;
import magmac.api.error.Error;
import magmac.app.lang.node.JavaRootSegment;
import magmac.app.lang.node.Root;
import magmac.app.stage.unit.UnitSet;

public interface Application {
    Option<Error> parseAndStore(UnitSet<Root<JavaRootSegment>> units);
}
