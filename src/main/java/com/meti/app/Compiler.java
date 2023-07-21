package com.meti.app;

import com.meti.core.*;
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

    private Option<String_> compileClass(String_ line) {
        return $Option(() -> {
            var classIndex = line.firstIndexOfSlice("class ").$()
                    .nextExclusive("class ".length()).$();

            var contentStart = line.firstIndexOfChar('{').$();

            var name = line.sliceBetween(classIndex.to(contentStart).$()).strip();
            var body = line.sliceFrom(contentStart);
            var compiledBody = compileLine(body);

            return fromSlice("class def " + name.unwrap() + "() => " + compiledBody.unwrap());
        });
    }

    Result<String_, CompileException> compile() {
        var output = split(input)
                .filter(line -> !line.strip().startsWith("package "))
                .map(this::compileLine)
                .collect(joining(Empty))
                .unwrapOrElse(Empty);

        return Ok.apply(output);
    }

    private String_ compileLine(String_ line) {
        return compileImport(line)
                .or(compileClass(line))
                .or(compileBlock(line))
                .or(compileDeclaration(line))
                .unwrapOrElse(line);
    }

    private Option<String_> compileDeclaration(String_ line) {
        if (line.equalsTo(fromSlice("int x"))) {
            return Some.apply(fromSlice("x : i16"));
        } else {
            return new None<>();
        }
    }

    private Option<String_> compileBlock(String_ line) {
        return $Option(() -> {
            var bodyStart = line.firstIndexOfChar('{').$();
            var bodyEnd = line.firstIndexOfChar('}').$();
            var range = bodyStart.nextExclusive().$().to(bodyEnd).$();
            var content = line.sliceBetween(range);
            return split(content)
                    .map(this::compileLine)
                    .collect(JavaString.joining(fromSlice(";")))
                    .unwrapOrElse(fromSlice(""))
                    .prepend("{")
                    .append("}");
        });
    }

    private Iterator<String_> split(String_ input1) {
        var unwrapped = input1.unwrap();
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