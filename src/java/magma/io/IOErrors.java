package magma.io;

import magma.error.Error;

import java.io.IOException;

public class IOErrors {
    public static IOException unwrap(Error error) {
        return new IOException(error.display());
    }
}
