package magmac.api.head;

import magmac.api.None;
import magmac.api.Option;

public class EmptyHead<T> implements Head<T> {
    @Override
    public Option<T> next() {
        return new None<>();
    }
}
