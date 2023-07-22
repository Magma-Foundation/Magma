package com.meti.app.compile;

import com.meti.iterate.Iterator;
import com.meti.java.JavaList;
import com.meti.java.JavaString;
import com.meti.java.String_;

public record Splitter(String_ input1) {
    public Iterator<String_> split() {
        var unwrapped = input1().unwrap();
        var lines = JavaList.<String>empty();
        var buffer = new StringBuilder();
        var depth = 0;

        for (int i = 0; i < unwrapped.length(); i++) {
            var c = unwrapped.charAt(i);
            if (c == '}' && depth == 1) {
                buffer.append(c);
                depth -= 1;

                lines.add(buffer.toString());
                buffer = new StringBuilder();
            } else if (c == ';' && depth == 0) {
                lines.add(buffer.toString());
                buffer = new StringBuilder();
            } else {
                if (c == '{') depth++;
                if (c == '}') depth--;
                buffer.append(c);
            }
        }

        lines.add(buffer.toString());
        return lines.iter().map(JavaString::fromSlice);
    }
}