package magmac.app;

import magmac.api.Option;
import magmac.api.error.Error;
import magmac.app.lang.java.JavaLang;
import magmac.app.stage.unit.UnitSet;

/**
 * Entry point for loading source units and persisting compiler results.
 */
public interface Application {
    /**
     * Parses the supplied source units and stores the outputs.
     *
     * @param units compilation units describing the program sources
     * @return an {@link Option} containing an error when parsing failed
     */
    Option<Error> parseAndStore(UnitSet<JavaLang.Root> units);
}
