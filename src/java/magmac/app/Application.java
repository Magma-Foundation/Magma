package magmac.app;

import java.io.IOException;
import java.util.Optional;

public interface Application {
    Optional<IOException> run();
}
