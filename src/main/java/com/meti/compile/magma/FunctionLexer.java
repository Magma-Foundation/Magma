package com.meti.compile.magma;

import com.meti.collect.JavaList;
import com.meti.collect.Stream;
import com.meti.collect.StreamException;
import com.meti.collect.Streams;
import com.meti.compile.CompileException;
import com.meti.compile.common.*;
import com.meti.compile.lex.Lexer;
import com.meti.compile.node.Content;
import com.meti.compile.node.Node;
import com.meti.compile.node.Text;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

public record FunctionLexer(Text text) implements Lexer {
    private Option<Function> attachValue(EmptyField identity, JavaList<Node> parameters, Option<Integer> valueSeparator) {
        return valueSeparator.map(separator -> {
            var value = new Content(text.slice(separator + 2));
            return new Implementation(identity, parameters, value);
        });
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

    private Function extractFunction(int paramStart, JavaList<Field.Flag> flags, Text name) throws CompileException {
        try {
            int paramEnd = locateParameterEnd(paramStart);
            var parameters = splitParameters(paramStart, paramEnd);
            var valueSeparator = text.firstIndexOfSlice("=>", paramEnd);
            var returnType = extractReturnType(paramEnd, valueSeparator);
            var identity = new EmptyField(flags, name, returnType);
            var map = attachValue(identity, parameters, valueSeparator);
            return map.orElseGet(() -> new Abstraction(identity, parameters));
        } catch (StreamException e) {
            throw new CompileException(e);
        }
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

    @Override
    public Option<Node> lex() throws CompileException {
        return text.firstIndexOfChar('(').flatMap(paramStart -> {
            var keys = text.slice(0, paramStart);
            return keys.lastIndexOfChar(' ')
                    .flatMap(space -> extractWithSeparator(paramStart, keys, space))
                    .or(() -> paramStart == 0
                            ? new Some<>(extractFunction(paramStart, new JavaList<>(), new Text("")))
                            : new None<>());
        });
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

    private JavaList<Node> splitParameters(Integer paramStart, int paramEnd) throws com.meti.collect.StreamException {
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
