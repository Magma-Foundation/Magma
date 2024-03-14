package com.meti.collect.option;

import static com.meti.collect.option.None.None;
import static com.meti.collect.option.Some.Some;

public class Options {
    public static <T> Option<T> $Option(Action<T, IntentionalException> action) {
        try {
            return Some(action.perform());
        } catch (IntentionalException e) {
            return None();
        }
    }
}
