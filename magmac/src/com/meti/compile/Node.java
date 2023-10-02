package com.meti.compile;

import com.meti.api.collect.JavaString;
import com.meti.api.collect.List;
import com.meti.api.option.None;
import com.meti.api.option.Option;

public interface Node {
    default Node withBody(JavaString compiledBody) {
        return this;
    }

    default Option<JavaString> getChild() {
        return None.apply();
    }

    default Option<JavaString> getParent() {
        return None.apply();
    }

    default Option<List<JavaString>> getLines() {
        return None.apply();
    }

    default Option<JavaString> getName() {
        return None.apply();
    }

    default Option<JavaString> getParameters() {
        return None.apply();
    }

    default Option<JavaString> getBody() {
        return None.apply();
    }

    boolean is(String name);
}
