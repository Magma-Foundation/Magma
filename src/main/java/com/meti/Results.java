package com.meti;

import java.util.function.Function;

class Results {
    static <T, E extends Exception> Result<T, E> Ok(T value) {
        return new Result<>() {
            @Override
            public <R> Result<R, E> mapValue(Function<T, R> mapper) {
                return Ok(mapper.apply(value));
            }

            @Override
            public T unwrap() {
                return value;
            }
        };
    }

    static <T, E extends Exception> Result<T, E> Err(E e) {
        return new Result<>() {
            @Override
            public <R> Result<R, E> mapValue(Function<T, R> mapper) {
                return Err(e);
            }

            @Override
            public T unwrap() throws E {
                throw e;
            }
        };
    }
}
