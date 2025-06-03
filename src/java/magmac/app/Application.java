package magmac.app;

import magmac.api.Option;
import magmac.api.error.Error;
import magmac.app.lang.java.JavaLang;
import magmac.app.stage.unit.UnitSet;

public interface Application {
    Option<Error> parseAndStore(UnitSet<JavaLang.Root> units);
}
