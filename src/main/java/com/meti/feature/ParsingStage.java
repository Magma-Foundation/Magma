package com.meti.feature;

import com.meti.InterpretationError;
import com.meti.feature.assign.AssignmentParser;
import com.meti.feature.block.BlockParser;
import com.meti.feature.integer.IntParser;
import com.meti.safe.iter.Iterator;
import com.meti.safe.iter.Iterators;
import com.meti.safe.result.Err;
import com.meti.safe.result.Result;
import com.meti.state.State;

public record ParsingStage(State state, Node node) {
    public Result<State, InterpretationError> parse() {
        return collectParsers()
                .map(Parser::parse)
                .flatMap(Iterators::fromOption)
                .head()
                .unwrapOrElse(Err.apply(new InterpretationError("Failed to parse node.")));
    }

    private Iterator<Parser> collectParsers() {
        return Iterators.of(
                new AssignmentParser(state, node),
                new IntParser(state, node));
    }
}