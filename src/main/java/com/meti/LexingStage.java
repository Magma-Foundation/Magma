package com.meti;

import com.meti.feature.Attribute;
import com.meti.feature.Lexer;
import com.meti.feature.Node;
import com.meti.feature.NodeListAttribute;
import com.meti.feature.assign.AssignmentLexer;
import com.meti.feature.block.BlockLexer;
import com.meti.feature.definition.DefinitionLexer;
import com.meti.feature.integer.NumberLexer;
import com.meti.feature.variable.VariableLexer;
import com.meti.safe.NativeString;
import com.meti.safe.SafeList;
import com.meti.safe.iter.Collectors;
import com.meti.safe.iter.Iterators;
import com.meti.safe.result.Err;
import com.meti.safe.result.Ok;
import com.meti.safe.result.Result;
import com.meti.safe.result.Results;

import static com.meti.feature.Content.Key;

public record LexingStage(NativeString line) {
    Result<Node, InterpretationError> lexTree() {
        return lexChild(line()).flatMapValue(this::lexBlock);
    }

    private Result<Node, InterpretationError> lexBlock(Node value) {
        return value.apply(NativeString.from("lines")).flatMap(Attribute::asListOfNodes)
                .map(list -> lexContentList(list).mapValue(lines -> value.withAttribute(NativeString.from("lines"), new NodeListAttribute(lines))))
                .unwrapOrElse(Ok.apply(value));
    }

    private Result<SafeList<Node>, InterpretationError> lexContentList(SafeList<? extends Node> list) {
        return list.iter()
                .mapToResult(this::lexContent)
                .collectToResult(Collectors.toList());
    }

    private Result<Node, InterpretationError> lexContent(Node element) {
        if (element.is(Key.Id)) {
            return Results.flatten(element.valueAsString()
                    .map(this::lexChild)
                    .unwrapOrThrow(() -> new InterpretationError("No value present in '" + element + "'.")));
        } else {
            return Err.apply(new InterpretationError("No value present in content."));
        }
    }

    private Result<Node, InterpretationError> lexChild(NativeString input) {
        return Iterators.<Lexer>of(new AssignmentLexer(input),
                        new BlockLexer(input),
                        new DefinitionLexer(input),
                        new NumberLexer(input),
                        new VariableLexer(input))
                .map(Lexer::lex)
                .flatMap(Iterators::fromOption)
                .head()
                .unwrapOrThrow(() -> new InterpretationError("Unknown input: " + input));
    }
}