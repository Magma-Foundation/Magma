package com.meti.app;

import com.meti.core.Ok;
import com.meti.core.Option;
import com.meti.core.Options;
import com.meti.core.Result;
import com.meti.iterate.Iterator;
import com.meti.java.*;

import static com.meti.core.Options.$$;
import static com.meti.core.Options.$Option;
import static com.meti.java.JavaString.*;

public record Compiler(String_ input) {

    private static String_ resolveType(String_ type) {
        return JavaMap.<String, String>empty()
                .insert("int", "I16")
                .insert("void", "Void")
                .applyOptionally(type.unwrap())
                .map(JavaString::fromSlice)
                .unwrapOrElse(type);
    }

    private Option<Renderable> compileClass(String_ line) {
        return lexClass(new ClassLexer(line)).flatMap(Class_::transform);
    }

    private Option<Class_> lexClass(ClassLexer classLexer) {
        return $Option(() -> {
            var classIndex = classLexer.line().firstIndexOfSlice("class ").$()
                    .nextExclusive("class ".length()).$();

            var contentStart = classLexer.line().firstIndexOfChar('{').$();

            var name = classLexer.line().sliceBetween(classIndex.to(contentStart).$()).strip();
            var body = classLexer.line().sliceFrom(contentStart);
            var compiledBody = compileNode(body);

            return new Class_(name, compiledBody);
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
        System.out.println(line.unwrap());
        return new ImportLexer(line).lex()
                .or(compileClass(line))
                .or(compileBlock(line))
                .or(compileMethod(line))
                .or(compileDeclaration(line))
                .unwrapOrElse(Content.ofContent(line));
    }

    private Option<Renderable> compileMethod(String_ line) {
        return $Option(() -> {
            var paramStart = line.firstIndexOfChar('(').$();
            var paramEnd = line.firstIndexOfChar(')').$();

            var beforeParams = line.sliceTo(paramStart);
            var nameSeparator = beforeParams.firstIndexOfChar(' ').$();
            var type = beforeParams.sliceTo(nameSeparator);
            var resolvedType = resolveType(type);

            var paramString = line.sliceBetween(paramStart.nextExclusive().$().to(paramEnd).$());
            var parameters = paramString.split(",")
                    .map(String_::strip)
                    .filter(value -> !value.isEmpty())
                    .map(node -> compileDeclaration(node).$()).collect(JavaSet.asSet());

            var name = beforeParams.sliceFrom(nameSeparator.nextExclusive().$());
            var renderedParameters = parameters.iter()
                    .map(Renderable::render)
                    .collect(joining(fromSlice(", ")))
                    .unwrapOrElse(Empty);

            var bodyStart = line.firstIndexOfChar('{').$();
            var body = line.sliceFrom(bodyStart);
            var node = compileNode(body);

            return new Content(fromSlice("def " + name.unwrap() + "(" + renderedParameters.unwrap() + ") : " + resolvedType.unwrap() + " => " + node.render().unwrap()));
        });
    }

    private Option<Renderable> compileDeclaration(String_ line) {
        return line.lastIndexOfChar(' ').flatMap(index -> $Option(() -> {
            var args = line.sliceTo(index).strip();
            var name = line.sliceFrom(index.nextExclusive().$()).strip();
            var isValid = name.iter().allMatch(Character::isLetter);
            if (!isValid) {
                return Options.$$();
            }

            var list = args.split(" ").collect(JavaList.asList())
                    .into(NonEmptyJavaList::from)
                    .unwrap();

            var type = list.last();
            var map = resolveType(type);

            return new Declaration(name, map);
        }));
    }

    private Option<Renderable> compileBlock(String_ line) {
        return $Option(() -> {
            var stripped = line.strip();
            var bodyStart = stripped.firstIndexOfChar('{').$();
            var bodyEnd = stripped.lastIndexOfChar('}').$();
            if (!bodyStart.isStart() || !bodyEnd.isEnd()) {
                return $$();
            }

            var range = bodyStart.nextExclusive().$().to(bodyEnd).$();
            var content = stripped.sliceBetween(range);
            var map = split(content)
                    .map(String_::strip)
                    .filter(value -> !value.isEmpty())
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