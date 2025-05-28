package magmac.app;

import magmac.api.error.Error;

import magmac.api.Option;

interface Application {
    Option<Error> run();
}
