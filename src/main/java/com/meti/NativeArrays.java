package com.meti;

public class NativeArrays {
    public static char[] unbox(Character[] boxed) {
        var c = new char[boxed.length];
        for (int i = 0; i < boxed.length; i++) {
            c[i] = boxed[i];
        }
        return c;
    }
}
