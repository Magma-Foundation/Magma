package magmac.app.io;

import magmac.api.result.Result;

import java.io.IOException;
import java.util.Map;

public interface Sources {
    Result<Map<Location, String>, IOException> readAll();
}
