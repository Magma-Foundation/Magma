package com.meti.compile;

import com.meti.api.option.None;
import com.meti.api.option.Option;

import java.util.List;

public interface Node {
    default Node withBody(String compiledBody) {
        return this;
    }

    default Option<String> getChild() {
        return None.apply();
    }

    default Option<String> getParent() {
        return None.apply();
    }

    default Option<List<String>> getLines() {
        return None.apply();
    }

    default Option<String> getName() {
        return None.apply();
    }

    default Option<String> getParameters() {
        return None.apply();
    }

    default Option<String> getBody() {
        return None.apply();
    }
}
