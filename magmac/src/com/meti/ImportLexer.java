package com.meti;

import com.meti.java.Lexer;
import com.meti.node.MapNode;
import com.meti.node.Node;
import com.meti.node.StringAttribute;

import java.util.Map;
import java.util.Optional;

public record ImportLexer(String input) implements Lexer {

    public static final String PARENT = "parent";
    public static final String CHILD = "child";
    public static final String ID = "import";

    @Override
    public Optional<Node> lex() {
        if (input().startsWith("import ")) {
            var isStatic = input().startsWith("import static ");
            var separator = input().lastIndexOf('.');

            var parentStart = isStatic ? "import static ".length() : "import ".length();
            var parentName = input().substring(parentStart, separator);

            var childName = input().substring(separator + 1);
            var childString = isStatic && childName.equals("*")
                    ? "*"
                    : "{ " + childName + " }";

            return Optional.of(new MapNode(ID, Map.of(
                    PARENT, new StringAttribute(parentName),
                    CHILD, new StringAttribute(childString)
            )));
        }
        return Optional.empty();
    }
}