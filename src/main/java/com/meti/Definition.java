package com.meti;

record Definition(NativeString name, NativeString type, NativeString value) {
    public Definition withValue(NativeString value) {
        return new Definition(name, type, value);
    }
}
