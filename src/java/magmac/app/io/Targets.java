package magmac.app.io;

import java.io.IOException;
import java.util.Optional;

public interface Targets {
    Optional<IOException> write(Location location, String output);
}
