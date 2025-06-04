package magmac.app.io.sources;

import magmac.app.io.IOResult;

import magmac.app.stage.unit.UnitSet;

/**
 * Abstraction for retrieving source code units.
 */
public interface Sources {
    IOResult<UnitSet<String>> readAll();
}
