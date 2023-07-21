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
                        var withoutPrefix = line.sliceFrom(index);
                        var withoutStatic = withoutPrefix.firstIndexOfSlice("static ")
                                .flatMap(staticIndex -> staticIndex.nextExclusive("static ".length()))
                                .map(withoutPrefix::sliceFrom).unwrapOrElse(withoutPrefix);

                        var slice = withoutStatic.lastIndexOfChar('.').map(separator -> {
                            var parent = withoutStatic.sliceTo(separator);

                            var child = separator.nextExclusive().map(withoutStatic::sliceFrom)
                                    .unwrapOrElse(Empty);

                            return fromSlice("import { ")
                                    .appendOwned(child)
                                    .append(" } from ")
                                    .appendOwned(parent)
                                    .append(";\n");
                        });
                        return slice.unwrapOrElse(line);
                    }).unwrapOrElse(line);
                })
                .collect(joining(Empty))
                .unwrapOrElse(Empty);

        return Ok.apply(output);
    }
}