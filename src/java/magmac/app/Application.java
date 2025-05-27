package magmac.app;

import java.util.Optional;

public interface Application {
    Optional<ApplicationError> run();
}
