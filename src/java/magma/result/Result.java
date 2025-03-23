package magma.result;

import magma.option.Option;

public interface Result<T, X> {
    Option<T> findValue();

    Option<X> findError();

    boolean isOk();
}
