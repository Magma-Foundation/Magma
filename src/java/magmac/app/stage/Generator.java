package magmac.app.stage;

import magmac.api.result.Result;
import magmac.app.error.CompileError;
import magmac.app.io.Location;

import java.util.Map;

public interface Generator {
    Result<Map<Location, String>, CompileError> generateAll(Roots roots);
}
