package com.meti.compile.magma;

import com.meti.compile.common.Declaration;
import com.meti.compile.common.EmptyField;
import com.meti.compile.common.Field;
import com.meti.compile.common.ValuedField;
import com.meti.compile.lex.Lexer;
import com.meti.compile.node.Content;
import com.meti.compile.node.Node;
import com.meti.compile.node.Text;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public record DeclarationLexer(Text text) implements Lexer {
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
                Set<EmptyField.Flag> innerFlags = new HashSet<>();
                for (String thisName : flagString) {
                    for (EmptyField.Flag value : EmptyField.Flag.values()) {
                        var thatName = value.name();
                        if (thisName.equalsIgnoreCase(thatName)) {
                            innerFlags.add(value);
                        }
                    }
                }
                return innerFlags;
            }).orElse(Collections.emptySet());

            if (flags.contains(Field.Flag.Let) || flags.contains(Field.Flag.Const)) {
                int valueSeparator = -1;
                for (int i = 0; i < text.size(); i++) {
                    var c = text.apply(i);
                    if (c == '=' && !text.slice(i, i + 2).compute().equals("=>")) {
                        valueSeparator = i;
                        break;
                    }
                }

                if (valueSeparator == -1) {
                    var nameText = separator.map(space -> keys.slice(space + 1)).orElse(keys);

                    var typeText = text.slice(typeSeparator + 1);
                    var type = new Content(typeText);

                    return new Some<>(new Declaration(new EmptyField(flags, nameText, type)));
                } else {
                    var nameText = separator.map(space -> keys.slice(space + 1)).orElse(keys);

                    var typeText = text.slice(typeSeparator + 1, valueSeparator);
                    var type = new Content(typeText);

                    var valueText = text.slice(valueSeparator + 1);
                    var value = new Content(valueText);

                    return new Some<>(new Declaration(new ValuedField(flags, nameText, type, value)));
                }
            } else {
                return new None<>();
            }
        });
    }
}
