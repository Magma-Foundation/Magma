package com.meti.app;

import com.meti.core.Ok;
import com.meti.core.Option;
import com.meti.core.Result;
import com.meti.iterate.Iterator;
import com.meti.java.JavaList;
import com.meti.java.JavaString;
import com.meti.java.String_;

import static com.meti.core.Options.$Option;
import static com.meti.java.JavaString.*;

public record Compiler(String_ input) {
    private static Option<String_> compileImport(String_ line) {
        return line.firstIndexOfSlice("import ").flatMap(index -> index.nextExclusive("import ".length())).map(index -> {
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
        });
    }

    Result<String_, CompileException> compile() {
        var output = split()
                .filter(line -> {
                    return !line.strip().startsWith("package ");
                })
                .map(line -> compileImport(line)
                        .or($Option(() -> {
                            var classIndex = line.firstIndexOfSlice("class ").$()
                                    .nextExclusive("class ".length()).$();

                            var contentStart = line.firstIndexOfChar('{').$();

                            var name = line.sliceBetween(classIndex.to(contentStart).$()).strip();
                            var body = line.sliceFrom(contentStart);

                            return fromSlice("class def " + name.unwrap() + "() => " + body.unwrap());
                        }))
                        .unwrapOrElse(line))
                .collect(joining(Empty))
                .unwrapOrElse(Empty);

        return Ok.apply(output);
    }

    private Iterator<String_> split() {
        var unwrapped = input.unwrap();
        var lines = JavaList.<String>empty();
        var buffer = new StringBuffer();
        var depth = 0;

        for (int i = 0; i < unwrapped.length(); i++) {
            var c = unwrapped.charAt(i);
            if (c == '}' && depth == 1) {
                buffer.append(c);
                depth -= 1;

                lines.add(buffer.toString());
                buffer = new StringBuffer();
            } else if (c == ';' && depth == 0) {
                lines.add(buffer.toString());
                buffer = new StringBuffer();
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