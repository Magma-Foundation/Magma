package com.meti.app.compile.text;

import com.meti.api.core.F1;

public interface Output {
    Output appendOutput(Output other);

    Output appendSlice(String slice);

    /**
     * Computes the trimmed value of this text.
     *
     * @return The trimmed value.
     */
    String compute();

    /**
     * Computes the value of this {@link Output} object as-is, with no trimming involved.
     *
     * @return The raw value.
     */
    String computeRaw();

    <E extends Exception> Output map(F1<String, String, E> mapper) throws E;

    Output prepend(String prefix);
}
