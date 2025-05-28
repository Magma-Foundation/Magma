package magmac.app.io.sources;

import magmac.app.io.IOResult;

import magmac.app.stage.UnitSet;

public interface Sources {
    IOResult<UnitSet<String>> readAll();
}
