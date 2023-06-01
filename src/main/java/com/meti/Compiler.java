package com.meti;

import static com.meti.Option.None;
import static com.meti.Option.Some;

public record Compiler(String input) {
    private static String render(MapNode MapNode) {
        return "import { " + MapNode.child() + " } from " + MapNode.parent() + ";";
    }

    String compile() {
        return compile(input).unwrapOrElse("");
    }

    private Option<String> compile(String input) {
        /*
        Java

        import : {
            args = [ TEXT / "." ]
        } = "import" whitespace args
        statement = import
        root = statement
         */


        if (input.startsWith("import java.io.IOException;")) {
            var parent = "java.io";
            var child = "IOException";
            return Some(render(MapNode.createMapNode(parent, child)));
        } else {
            return None();
        }
    }
}