package com.meti.app.compile.text;

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

    Output prepend(String prefix);
}
