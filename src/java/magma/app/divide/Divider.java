package magma.app.divide;

import magma.api.collect.Iter;

public interface Divider {
    Iter<String> divide(String input);
}
