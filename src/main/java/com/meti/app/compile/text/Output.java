package com.meti.app.compile.text;

public interface Output {
    Output appendSlice(String slice);

    Output appendOutput(Output other);

    /**
     * Computes the value of this {@link Text} object as-is, with no trimming involved.
     * @return The raw value.
     */
    String compute();

    /**
     * Computes the trimmed value of this {@link Text} object.
     * Equivalent to {@link #compute()} followed by {@link String#trim()}.
     * @return The trimmed value.
     */
    String computeTrimmed();
}
