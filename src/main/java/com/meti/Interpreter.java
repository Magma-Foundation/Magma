package com.meti;

import com.meti.feature.*;
import com.meti.feature.assign.AssignmentActor;
import com.meti.feature.block.BlockActor;
import com.meti.feature.definition.DefinitionActor;
import com.meti.feature.integer.NumberActor;
import com.meti.feature.variable.VariableActor;
import com.meti.safe.NativeString;
import com.meti.safe.iter.Iterators;
import com.meti.safe.result.Err;
import com.meti.safe.result.Result;
import com.meti.split.Splitter;
import com.meti.state.EmptyState;
import com.meti.state.PresentState;
import com.meti.state.State;

public final class Interpreter {
    private final NativeString input;

    public Interpreter(NativeString input) {
        this.input = input;
    }

    public static Result<State, InterpretationError> interpretStatement(PresentState state) {
        var input = state.value;
        return Iterators.of(
                        new BlockActor(state, input),
                        new DefinitionActor(state, input),
                        new AssignmentActor(state, input),
                        new VariableActor(state, input),
                        new NumberActor(state, input))
                .map(Actor::act)
                .flatMap(Iterators::fromOption)
                .head()
                .unwrapOrElse(Err.apply(new InterpretationError("Unknown value: " + input.internalValue())));
    }

    public Result<NativeString, InterpretationError> interpret() {
        return interpret(EmptyState.create());
    }

    public Result<NativeString, InterpretationError> interpret(State state) {
        return new Splitter(input)
                .split()
                .iter()
                .foldLeftResult(state, (previous, line) -> interpretStatement(previous.withValue(new Content(line))))
                .mapValue(internal -> internal.findValue1().flatMap(Node::valueAsString).unwrapOrElse(NativeString.from("")));
    }
}