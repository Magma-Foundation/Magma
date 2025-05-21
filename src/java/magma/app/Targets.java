package magma.app;

import magma.api.io.IOError;
import magma.api.option.Option;

public interface Targets {
    Option<IOError> writeSource(Location location, String output);
}
