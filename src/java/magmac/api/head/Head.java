package magmac.api.head;

import magmac.api.Option;

interface Head<T> {
    Option<T> next();
}
