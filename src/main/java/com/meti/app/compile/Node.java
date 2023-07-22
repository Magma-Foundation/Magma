package com.meti.app.compile;

import com.meti.core.None;
import com.meti.core.Option;
import com.meti.java.String_;

public interface Node {
    default Option<String_> value() {
        return None.apply();
    }

    @Deprecated
    String_ render();
}
