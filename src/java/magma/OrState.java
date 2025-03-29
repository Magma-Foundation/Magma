package magma;

import magma.option.None;
import magma.option.Option;
import magma.option.Some;

public record OrState(Option<String> result) {
    public OrState() {
        this(new None<>());
    }

    public OrState withValue(String value) {
        return new OrState(new Some<>(value));
    }

    public Option<String> toOption() {
        return result;
    }
}
