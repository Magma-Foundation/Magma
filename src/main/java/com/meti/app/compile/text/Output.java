package com.meti.app.compile.text;

public interface Output {
    Output appendSlice(String slice);

    Output appendOutput(Output other);

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

    RootText prepend(String prefix);
}
