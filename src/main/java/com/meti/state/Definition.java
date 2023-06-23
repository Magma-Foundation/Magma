package com.meti.state;

import com.meti.safe.NativeString;

public record Definition(NativeString name, NativeString type, NativeString value) {
    public Definition withValue(NativeString value) {
        return new Definition(name, type, value);
    }
}
