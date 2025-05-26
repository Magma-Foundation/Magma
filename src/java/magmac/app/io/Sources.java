package magmac.app.io;

import magmac.api.result.Result;

import java.io.IOException;
import java.util.Set;

public interface Sources {
    Result<Set<Unit>, IOException> collect();
}
