package com.meti.app.compile.magma;

import com.meti.api.collect.JavaList;
import com.meti.api.collect.Stream;
import com.meti.api.collect.StreamException;
import com.meti.api.collect.Streams;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.CompileException;
import com.meti.app.compile.common.*;
import com.meti.app.compile.lex.Lexer;
import com.meti.app.compile.node.Content;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.Text;

public record FunctionLexer(Text text) implements Lexer {
    private Function extractFunction(int paramStart, JavaList<Field.Flag> flags, Text name) throws CompileException {
        try {
            int paramEnd = locateParameterEnd(paramStart);
            var parameters = splitParameters(paramStart, paramEnd);
            var valueSeparator = text.firstIndexOfSlice("=>", paramEnd);
            var returnType = extractReturnType(paramEnd, valueSeparator);
            var identity = new EmptyField(name, returnType, flags);
            var map = attachValue(identity, parameters, valueSeparator);
            return map.orElseGet(() -> new Abstraction(identity, parameters));
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    @Override
    public Option<Node> lex() throws CompileException {
        return text.firstIndexOfChar('(')
                .filter(value -> text.containsChar(')'))
                .flatMap(this::extract);
    }

    private JavaList<Field.Flag> extractFlags(Text flagString) throws CompileException {
        try {
            return Streams.apply(flagString.computeTrimmed()
                    .split(" "))
                    .filter(value -> !value.isBlank())
                    .map(String::toUpperCase)
                    .flatMap(this::validateFlag)
                    .foldRight(new JavaList<>(), JavaList::add);
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    private Option<Function> attachValue(EmptyField identity, JavaList<Node> parameters, Option<Integer> valueSeparator) {
        return valueSeparator.map(separator -> {
            var value = new Content(text.slice(separator + 2));
            return new Implementation(identity, value, parameters);
        });
    }

    private Node extractReturnType(int paramEnd, Option<Integer> valueSeparator) {
        return text.firstIndexOfCharWithOffset(':', paramEnd)
                .filter(value -> value < valueSeparator.orElse(text.size()))
                .map(value -> sliceToContent(value + 1, valueSeparator))
                .orElse(ImplicitType.ImplicitType_);
    }

    private Option<Node> extractWithSeparator(Integer paramStart, Text keys, Integer space) throws CompileException {
        var flags = extractFlags(keys.slice(0, space));
        if (flags.contains(Field.Flag.Def)) {
            var function = extractFunction(paramStart, flags, keys.slice(space + 1));
            return new Some<>(function);
        } else {
            return new None<>();
        }
    }

    private Option<Node> extract(Integer paramStart) throws CompileException {
        var keys = text.slice(0, paramStart);
        return keys.lastIndexOfChar(' ')
                .flatMap(space -> extractWithSeparator(paramStart, keys, space))
                .or(() -> paramStart == 0
                        ? new Some<>(extractFunction(paramStart, new JavaList<>(), new Text("")))
                        : new None<>());
    }

    private int locateParameterEnd(int paramStart) {
        var depth = 0;
        var paramEnd = -1;
        for (int i = paramStart + 1; i < text.size(); i++) {
            var c = text.apply(i);
            if (c == ')' && depth == 0) {
                paramEnd = i;
            } else {
                if (c == '(') depth++;
                if (c == ')') depth--;
            }
        }
        return paramEnd;
    }

    private Node sliceToContent(int start, Option<Integer> terminus) {
        var end = terminus.orElse(text.size());
        var slice = text.slice(start, end);
        return new Content(slice);
    }

    private JavaList<Node> splitParameters(Integer paramStart, int paramEnd) throws com.meti.api.collect.StreamException {
        return Streams.apply(text.slice(paramStart + 1, paramEnd).computeTrimmed()
                .split(","))
                .filter(value -> !value.isBlank())
                .map(Text::new)
                .map(Content::new)
                .foldRight(new JavaList<>(), JavaList::add);
    }

    private Stream<Field.Flag> validateFlag(String flagString) {
        return Streams.apply(Field.Flag.values())
                .filter(flag -> flag.name().equalsIgnoreCase(flagString));
    }
}
