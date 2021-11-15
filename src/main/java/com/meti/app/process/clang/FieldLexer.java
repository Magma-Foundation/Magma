package com.meti.app.process.clang;

import com.meti.api.option.Option;
import com.meti.api.stream.RangeStream;
import com.meti.api.stream.StreamException;
import com.meti.app.CompileException;
import com.meti.app.Input;
import com.meti.app.Primitive;
import com.meti.app.node.Content;
import com.meti.app.node.Node;
import com.meti.app.node.Sequence;
import com.meti.app.process.Lexer;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

public record FieldLexer(Input input) implements Lexer {
    @Override
    public Option<Node> process() throws CompileException {
        try {
            return input.firstChar(':').map(this::processWithTypeSeparator);
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    private Node processWithTypeSeparator(Integer typeSeparator) throws StreamException {
        var keyString = input.slice(0, typeSeparator);
        var lastSpace = keyString.lastIndexOf(' ');
        var flagString = lastSpace != -1
                ? keyString.substring(0, lastSpace).trim()
                : "";

        var flags = Arrays.stream(flagString.split(" "))
                .map(String::trim)
                .filter(value -> !value.isEmpty())
                .map(String::toUpperCase)
                .collect(Collectors.toList());

        var onsetStart = keyString.indexOf('(');
        var nameExtent = onsetStart == -1
                ? keyString.length()
                : onsetStart;

        var name = lastSpace != -1
                ? keyString.substring(lastSpace + 1, nameExtent).trim()
                : keyString.substring(0, nameExtent);

        var valueSeparator = new RangeStream(0, input.length())
                .filter(value -> input.apply(value) == '=')
                .filter(value -> !(input.slice(value, value + 2).equals("=>")))
                .first();

        return valueSeparator
                .map(coda -> withCoda(typeSeparator, flags, name, coda))
                .orElseGet(() -> withoutCoda(typeSeparator, flags, name));
    }

    private Node withCoda(Integer typeSeparator, java.util.List<String> flags, String name, Integer coda) {
        var type = input.slice(typeSeparator + 1, coda);
        var value = input.slice(coda + 1, input.length());
        var primitive = Primitive.valueOf(type);
        return flags.contains("DEF")
                ? primitive.asFieldWithOnset(name, new Sequence(Collections.emptyList()))
                : primitive.asField(name, new Content(value));
    }

    private Node withoutCoda(Integer typeSeparator, java.util.List<String> flags, String name) {
        var type = input.slice(typeSeparator + 1, input.length());
        var primitive = Primitive.valueOf(type);
        return flags.contains("DEF")
                ? primitive.asFieldWithOnset(name, new Sequence(Collections.emptyList()))
                : primitive.asField(name);
    }
}
