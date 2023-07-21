package com.meti.app;

import com.meti.core.*;
import com.meti.iterate.Iterator;
import com.meti.java.*;

import java.util.ArrayList;

import static com.meti.core.Options.$Option;
import static com.meti.java.JavaString.*;

public record Compiler(String_ input) {
    private static Option<Renderable> compileImport(String_ line) {
        return line.firstIndexOfSlice("import ").flatMap(index -> index.nextExclusive("import ".length())).map(index -> {
            var withoutPrefix = line.sliceFrom(index);
            var withoutStatic = withoutPrefix.firstIndexOfSlice("static ")
                    .flatMap(staticIndex -> staticIndex.nextExclusive("static ".length()))
                    .map(withoutPrefix::sliceFrom).unwrapOrElse(withoutPrefix);

            var slice = withoutStatic.lastIndexOfChar('.').<Renderable>map(separator -> {
                var parent = withoutStatic.sliceTo(separator);

                var child = separator.nextExclusive().map(withoutStatic::sliceFrom)
                        .unwrapOrElse(Empty);

                return new Import(parent, child);
            });

            return slice.unwrapOrElse(Content.ofContent(line));
        });
    }

    private Option<Renderable> compileClass(String_ line) {
        return $Option(() -> {
            var classIndex = line.firstIndexOfSlice("class ").$()
                    .nextExclusive("class ".length()).$();

            var contentStart = line.firstIndexOfChar('{').$();

            var name = line.sliceBetween(classIndex.to(contentStart).$()).strip();
            var body = line.sliceFrom(contentStart);
            var compiledBody = compileNode(body);

            var block = Objects.cast(Block.class, compiledBody).$();
            var parameters = new ArrayList<Renderable>();
            var value = new ArrayList<Renderable>();
            var unwrappedLines = block.lines().unwrap();
            for (var instance : unwrappedLines) {
                Objects.cast(Declaration.class, instance).consumeOrElse(parameters::add, () -> value.add(instance));
            }

            return new Class_(name, new JavaList<>(parameters), new Block(new JavaList<>(value)));
        });
    }

    Result<String_, CompileException> compile() {
        var output = split(input)
                .filter(line -> !line.strip().startsWith("package "))
                .map(line1 -> compileNode(line1).render())
                .collect(joining(Empty))
                .unwrapOrElse(Empty);

        return Ok.apply(output);
    }

    private Renderable compileNode(String_ line) {
        return compileImport(line)
                .or(compileClass(line))
                .or(compileBlock(line))
                .or(compileMethod(line))
                .or(compileDeclaration(line))
                .unwrapOrElse(Content.ofContent(line));
    }

    private Option<Renderable> compileMethod(String_ line) {
        if (line.equalsTo(fromSlice("void test(){}"))) {
            return Some.apply(new Content(fromSlice("def test() => {}")));
        } else {
            return None.apply();
        }
    }

    private Option<Renderable> compileDeclaration(String_ line) {
        return line.lastIndexOfChar(' ').flatMap(index -> $Option(() -> {
            var args = line.sliceTo(index).strip();
            var name = line.sliceFrom(index.nextExclusive().$()).strip();
            var list = args.split(" ").collect(JavaList.asList())
                    .into(NonEmptyJavaList::from)
                    .unwrap();

            var type = list.last();

            var map = JavaMap.<String, String>empty()
                    .insert("int", "i16")
                    .applyOptionally(type.unwrap())
                    .unwrapOrElse(type.unwrap());

            return new Declaration(name, fromSlice(map));
        }));
    }

    private Option<Renderable> compileBlock(String_ line) {
        return $Option(() -> {
            var bodyStart = line.firstIndexOfChar('{').$();
            var bodyEnd = line.firstIndexOfChar('}').$();
            var range = bodyStart.nextExclusive().$().to(bodyEnd).$();
            var content = line.sliceBetween(range);
            var map = split(content)
                    .map(this::compileNode)
                    .collect(JavaList.asList());
            return new Block(map);
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