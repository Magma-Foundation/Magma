package com.meti;

import com.meti.lex.InvokeLexer;
import com.meti.node.Attribute;
import com.meti.node.Content;
import com.meti.node.Node;
import com.meti.stage.Rendererer;

import java.util.Optional;
import java.util.stream.Collectors;

public class InvokeRenderer implements Rendererer {
    private final Node node;

    public InvokeRenderer(Node node) {
        this.node = node;
    }

    @Override
    public Optional<String> render() {
        if (node.is(InvokeLexer.ID)) {
            var callerOptional = node.apply(InvokeLexer.CALLER)
                    .flatMap(Attribute::asNode)
                    .flatMap(node -> node.apply(Content.VALUE))
                    .flatMap(Attribute::asString);
            if (callerOptional.isEmpty()) return Optional.empty();
            var caller = callerOptional.get();

            var argumentsOptional = node.apply(InvokeLexer.ARGUMENTS)
                    .flatMap(Attribute::asListOfNodes)
                    .map(list -> {
                        return list.stream()
                                .map(element -> element.apply(Content.VALUE))
                                .flatMap(Optional::stream)
                                .map(Attribute::asString)
                                .flatMap(Optional::stream)
                                .collect(Collectors.toList());
                    });
            if (argumentsOptional.isEmpty()) return Optional.empty();
            var arguments = argumentsOptional.get();

            return Optional.of(caller + "(" + String.join(", ", arguments) + ")");
        } else {
            return Optional.empty();
        }
    }
}
