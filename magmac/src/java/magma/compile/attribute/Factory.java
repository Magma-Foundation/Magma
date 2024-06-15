package magma.compile.attribute;

import java.util.Optional;

public interface Factory<T> {

    Optional<T> fromAttribute(Attribute attribute);

    Attribute toAttribute(T value);
}
