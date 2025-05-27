package magmac.app;

import magmac.api.error.Error;

import magmac.api.Option;

public interface Application {
    Option<Error> run();
}
