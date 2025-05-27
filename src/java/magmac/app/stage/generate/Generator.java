package magmac.app.stage.generate;

import magmac.api.result.Result;
import magmac.app.compile.error.CompileError;
import magmac.app.io.Location;
import magmac.app.stage.Roots;

import java.util.Map;

public interface Generator {
    Result<Map<Location, String>, CompileError> generateAll(Roots roots);
}
