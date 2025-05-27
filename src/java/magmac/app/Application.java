package magmac.app;

import magmac.app.error.Error;

import java.util.Optional;

public interface Application {
    Optional<Error> run();
}
