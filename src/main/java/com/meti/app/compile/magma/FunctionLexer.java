package com.meti.app.compile.magma;

import com.meti.api.collect.IndexException;
import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.Stream;
import com.meti.api.collect.stream.StreamException;
import com.meti.api.collect.stream.Streams;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.common.*;
import com.meti.app.compile.node.InputNode;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.process.Processor;
import com.meti.app.compile.stage.CompileException;
import com.meti.app.compile.text.Input;
import com.meti.app.compile.text.RootText;

public record FunctionLexer(Input text) implements Processor<Node> {
    private Option<Node> extractWithSeparator(Integer paramStart, RootText keys, Integer space) throws CompileException {
        var flags = extractFlags(keys.slice(0, space));
        if (flags.contains(Field.Flag.Def)) {
            var function = extractFunction(paramStart, flags, keys.slice(space + 1));
            return new Some<>(function);
        } else {
            return new None<>();
        }
    }

    @Override
    public Option<Node> process() throws CompileException {
        return text.firstIndexOfChar('(')
                .filter(value -> text.containsChar(')'))
                .flatMap(this::extract);
    }

    private List<Field.Flag> extractFlags(RootText flagString) throws CompileException {
        try {
            return Streams.apply(flagString.compute()
                    .split(" "))
                    .filter(value -> !value.isBlank())
                    .map(String::toUpperCase)
                    .flatMap(this::validateFlag)
                    .foldRight(List.createList(), List::add);
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    private Option<Function> attachValue(EmptyField identity, List<Node> parameters, Option<Integer> valueSeparator) {
        return valueSeparator.map(separator -> {
            var value = new InputNode(text.slice(separator + 2));
            return new Implementation(identity, value, parameters);
        });
    }

    private Node extractReturnType(int paramEnd, Option<Integer> valueSeparator) {
        return text.firstIndexOfCharWithOffset(':', paramEnd)
                .filter(value -> value < valueSeparator.orElse(text.size()))
                .map(value -> sliceToContent(value + 1, valueSeparator))
                .orElse(ImplicitType.ImplicitType_);
    }

    private Function extractFunction(int paramStart, List<Field.Flag> flags, RootText name) throws CompileException {
        try {
            int paramEnd = locateParameterEnd(paramStart);
            var parameters = splitParameters(paramStart, paramEnd);
            var valueSeparator = text.firstIndexOfSliceWithOffset("=>", paramEnd);
            var returnType = extractReturnType(paramEnd, valueSeparator);
            var identity = new EmptyField(name, returnType, flags);
            var map = attachValue(identity, parameters, valueSeparator);
            return map.orElseGet(() -> new Abstraction(identity, parameters));
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    private Option<Node> extract(Integer paramStart) throws CompileException {
        var keys = text.slice(0, paramStart);
        return keys.lastIndexOfChar(' ')
                .flatMap(space -> extractWithSeparator(paramStart, keys, space))
                .or(() -> paramStart == 0
                        ? new Some<>(extractFunction(paramStart, List.createList(), new RootText("")))
                        : new None<>());
    }

    private int locateParameterEnd(int paramStart) {
        try {
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
        } catch (IndexException e) {
            return -1;
        }
    }

    private Node sliceToContent(int start, Option<Integer> terminus) {
        var end = terminus.orElse(text.size());
        var slice = text.slice(start, end);
        return new InputNode(slice);
    }

    private List<Node> splitParameters(Integer paramStart, int paramEnd) throws StreamException {
        return Streams.apply(text.slice(paramStart + 1, paramEnd).compute()
                .split(","))
                .filter(value -> !value.isBlank())
                .map(RootText::new)
                .map(InputNode::new)
                .foldRight(List.createList(), List::add);
    }

    private Stream<Field.Flag> validateFlag(String flagString) {
        return Streams.apply(Field.Flag.values())
                .filter(flag -> flag.name().equalsIgnoreCase(flagString));
    }
}
