package com.meti;

public interface Output {
    String compute();

    Output concat(Output other);
}
