package com.meti.feature.integer;

import com.meti.feature.Lexer;
import com.meti.feature.Node;
import com.meti.safe.NativeString;
import com.meti.safe.option.None;
import com.meti.safe.option.Option;
import com.meti.safe.option.Some;

public record NumberLexer(NativeString input) implements Lexer {
    @Override
    public Option<Node> lex() {
        try {
            var withoutSuffix = input()
                    .firstIndexOfCharByPredicate(Character::isLetter)
                    .map(input()::sliceTo)
                    .unwrapOrElse(input());

            var i = Integer.parseInt(withoutSuffix.strip().internalValue());
            return Some.apply(new IntNode(i));
        } catch (NumberFormatException e) {
            return None.apply();
        }
    }
}