package com.meti.feature;

import com.meti.Input;
import com.meti.clang.AbstractProcessor;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BlockLexerTest {
    @Test
    void validate() {
        assertTrue(new BlockLexer(new Input("{}")).validate());
    }

    @Test
    void processDefined() {
        var expected = new Block(List.of(new Content(new Input("return 420"))));
        var actual = new BlockLexer(new Input("{return 420;}")).processDefined();
        assertEquals(expected, actual);
    }

    static class BlockLexer extends AbstractProcessor<Node> {
        private final Input input;

        BlockLexer(Input input) {
            this.input = input;
        }

        @Override
        protected boolean validate() {
            return input.startsWithChar('{') && input.endsWithChar('}');
        }

        @Override
        protected Node processDefined() {
            var split = input.slice(1, input.length() - 1).split(";");
            var lines = createChildren(split);
            return new Block(lines);
        }

        private List<Content> createChildren(String[] split) {
            return Arrays.stream(split)
                    .map(String::trim)
                    .filter(value -> !value.isEmpty())
                    .map(Input::new)
                    .map(Content::new)
                    .collect(Collectors.toList());
        }
    }
}
