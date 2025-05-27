package magmac.app;

import magmac.api.error.Error;

import java.util.Optional;

public interface Application {
    Optional<Error> run();
}
