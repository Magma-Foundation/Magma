package magmac.api.head;

import magmac.api.Option;

public interface Head<T> {
    Option<T> next();
}
