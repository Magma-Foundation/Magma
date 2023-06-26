package com.meti;

import com.meti.feature.Lexer;
import com.meti.feature.Node;
import com.meti.feature.Parser;
import com.meti.feature.assign.AssignmentLexer;
import com.meti.feature.assign.AssignmentParser;
import com.meti.feature.block.BlockLexer;
import com.meti.feature.block.BlockParser;
import com.meti.feature.definition.DefinitionLexer;
import com.meti.feature.definition.DefinitionParser;
import com.meti.feature.integer.NumberLexer;
import com.meti.feature.integer.NumberParser;
import com.meti.feature.variable.VariableLexer;
import com.meti.feature.variable.VariableParser;
import com.meti.safe.NativeString;
import com.meti.safe.iter.Collectors;
import com.meti.safe.iter.Iterators;
import com.meti.safe.result.Ok;
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
        var input = state.findValue1().unwrapOrPanic();

        return Iterators.of(
                        new BlockParser(state, input),
                        new DefinitionParser(state, input),
                        new AssignmentParser(state, input),
                        new VariableParser(state, input),
                        new NumberParser(state, input))
                .map(Parser::parse)
                .flatMap(Iterators::fromOption)
                .head()
                .unwrapOrElse(Ok.apply(state));
    }

    private static Result<Node, InterpretationError> lex(NativeString line) {
        return Iterators.<Lexer>of(new AssignmentLexer(line),
                        new BlockLexer(line),
                        new DefinitionLexer(line),
                        new NumberLexer(line),
                        new VariableLexer(line))
                .map(Lexer::lex)
                .flatMap(Iterators::fromOption)
                .head()
                .unwrapOrThrow(() -> new InterpretationError("Unknown input: " + line));
    }

    public Result<NativeString, InterpretationError> interpret() {
        return interpret(EmptyState.create());
    }

    public Result<NativeString, InterpretationError> interpret(State state) {
        var statementsResult = new Splitter(input)
                .split()
                .iter()
                .mapToResult(Interpreter::lex)
                .collectToResult(Collectors.toList());
        return statementsResult.mapValueToResult(statements -> {
            return statements.iter().foldLeftResult(state, (previous, line) -> interpretStatement(state.withValue(line)))
                    .mapValue(internal -> internal.findValue1()
                            .flatMap(Node::valueAsString)
                            .unwrapOrElse(NativeString.from("")));
        });
    }
}