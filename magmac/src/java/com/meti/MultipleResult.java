package com.meti;

import java.util.List;

public record MultipleResult(List<String> instanceMembers, List<String> staticMembers) implements Result {
    @Override
    public List<String> staticValue() {
        return staticMembers;
    }

    @Override
    public List<String> instanceValue() {
        return instanceMembers;
    }
}
