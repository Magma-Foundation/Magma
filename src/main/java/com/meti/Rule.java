package com.meti;

import java.util.Map;
import java.util.Optional;

public interface Rule {
    Optional<Map<String, String>> extract(String value);
}
