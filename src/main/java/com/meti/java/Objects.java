package com.meti.java;

import com.meti.core.None;
import com.meti.core.Option;
import com.meti.core.Some;

public class Objects {
    public static <T> Option<T> cast(Class<T> clazz, Object instance) {
        try {
            return Some.apply(clazz.cast(instance));
        } catch (ClassCastException e) {
            return None.apply();
        }
    }
}

