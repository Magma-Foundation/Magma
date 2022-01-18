package com.meti.app.compile.magma;

import com.meti.api.collect.IndexException;
import com.meti.api.collect.java.List;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.common.Declaration;
import com.meti.app.compile.common.EmptyField;
import com.meti.app.compile.common.Field;
import com.meti.app.compile.common.ValuedField;
import com.meti.app.compile.lex.Lexer;
import com.meti.app.compile.node.InputNode;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.text.Input;

import java.util.Arrays;
import java.util.stream.Collectors;

public record DeclarationLexer(Input text) implements Lexer {
    @Override
    public Option<Node> lex() {
        return text.firstIndexOfChar(':').flatMap(typeSeparator -> {
            var keys = text.slice(0, typeSeparator);
            var separator = keys.lastIndexOfChar(' ');
            var flags = separator.map(space -> {
                var flagString = Arrays.stream(keys.slice(0, space).computeTrimmed().split(" "))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .collect(Collectors.toList());
                List<Field.Flag> innerFlags = List.createList();
                for (String thisName : flagString) {
                    for (EmptyField.Flag value : EmptyField.Flag.values()) {
                        var thatName = value.name();
                        if (thisName.equalsIgnoreCase(thatName)) {
                            innerFlags.add(value);
                        }
                    }
                }
                return innerFlags;
            }).orElse(List.createList());
            if (flags.contains(Field.Flag.Let) || flags.contains(Field.Flag.Const)) {
                int valueSeparator = findValueSeparator();
                if (valueSeparator == -1) {
                    var nameText = separator.map(space -> keys.slice(space + 1)).orElse(keys);

                    var typeText = text.slice(typeSeparator + 1);
                    var type = new InputNode(typeText);

                    return new Some<>(new Declaration(new EmptyField(nameText, type, flags)));
                } else {
                    var nameText = separator.map(space -> keys.slice(space + 1)).orElse(keys);

                    var typeText = text.slice(typeSeparator + 1, valueSeparator);
                    var type = new InputNode(typeText);

                    var valueText = text.slice(valueSeparator + 1);
                    var value = new InputNode(valueText);

                    return new Some<>(new Declaration(new ValuedField(flags, nameText, type, value)));
                }
            } else {
                return new None<>();
            }
        });
    }

    private int findValueSeparator() {
        try {
            int valueSeparator = -1;
            for (int i = 0; i < text.size(); i++) {
                var c = text.apply(i);
                if (c == '=' && !text.slice(i, i + 2).compute().equals("=>")) {
                    valueSeparator = i;
                    break;
                }
            }
            return valueSeparator;
        } catch (IndexException e) {
            return -1;
        }
    }
}
