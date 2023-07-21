package com.meti.app;

import com.meti.core.Ok;
import com.meti.core.Result;
import com.meti.java.String_;

import static com.meti.java.JavaString.*;

public record Compiler(String_ input) {
    Result<String_, CompileException> compile() {
        var output = input.split(";")
                .filter(line -> {
                    return !line.strip().startsWith("package ");
                })
                .map(line -> {
                    return line.firstIndexOfSlice("import ").flatMap(index -> {
                        return index.nextExclusive("import ".length());
                    }).map(index -> {
                        var slice1 = line.sliceFrom(index);
                        var slice = slice1.lastIndexOfChar('.').map(separator -> {
                            var parent = slice1.sliceTo(separator);
                            var child = separator.nextExclusive().map(next -> slice1.sliceFrom(next))
                                    .unwrapOrElse(Empty);

                            return fromSlice("import { ")
                                    .appendOwned(child)
                                    .append(" } from ")
                                    .appendOwned(parent)
                                    .append(";");
                        });
                        return slice.unwrapOrElse(line);
                    }).unwrapOrElse(line);
                })
                .collect(joining(fromSlice(";")))
                .unwrapOrElse(Empty);

        return Ok.apply(output);
    }
}