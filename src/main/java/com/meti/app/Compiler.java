package com.meti.app;

import com.meti.core.Ok;
import com.meti.core.Option;
import com.meti.core.Result;
import com.meti.iterate.Iterator;
import com.meti.java.JavaList;
import com.meti.java.JavaMap;
import com.meti.java.JavaString;
import com.meti.java.String_;

import static com.meti.core.Options.$Option;
import static com.meti.java.JavaString.*;

public record Compiler(String_ input) {
    private static Option<Content> compileImport(String_ line) {
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
        }).map(Content::new);
    }

    private Option<Content> compileClass(String_ line) {
        return $Option(() -> {
            var classIndex = line.firstIndexOfSlice("class ").$()
                    .nextExclusive("class ".length()).$();

            var contentStart = line.firstIndexOfChar('{').$();

            var name = line.sliceBetween(classIndex.to(contentStart).$()).strip();
            var body = line.sliceFrom(contentStart);
            var compiledBody = compileNode(body).unwrap();

            return fromSlice("class def " + name.unwrap() + "() => " + compiledBody.unwrap());
        }).map(Content::new);
    }

    Result<String_, CompileException> compile() {
        var output = split(input)
                .filter(line -> !line.strip().startsWith("package "))
                .map(line1 -> compileNode(line1).unwrap())
                .collect(joining(Empty))
                .unwrapOrElse(Empty);

        return Ok.apply(output);
    }

    private Content compileNode(String_ line) {
        return compileImport(line)
                .or(compileClass(line))
                .or(compileBlock(line))
                .or(compileDeclaration(line))
                .unwrapOrElse(new Content(line));
    }

    private Option<Content> compileDeclaration(String_ line) {
        return line.firstIndexOfChar(' ').flatMap(index -> $Option(() -> {
            var type = line.sliceTo(index).strip();
            var name = line.sliceFrom(index.nextExclusive().$()).strip();

            var map = JavaMap.<String, String>empty()
                    .insert("int", "i16")
                    .applyOptionally(type.unwrap())
                    .unwrapOrElse(type.unwrap());

            return new Content(name.append(" : ").append(map));
        }));
    }

    private Option<Content> compileBlock(String_ line) {
        return $Option(() -> {
            var bodyStart = line.firstIndexOfChar('{').$();
            var bodyEnd = line.firstIndexOfChar('}').$();
            var range = bodyStart.nextExclusive().$().to(bodyEnd).$();
            var content = line.sliceBetween(range);
            return split(content)
                    .map(line1 -> compileNode(line1).unwrap())
                    .collect(JavaString.joining(fromSlice(";")))
                    .unwrapOrElse(fromSlice(""))
                    .prepend("{")
                    .append("}");
        }).map(Content::new);
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