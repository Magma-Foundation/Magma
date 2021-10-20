package com.meti;

public interface Output {
    String compute() throws AttributeException;

    Output concat(Output other);
}
