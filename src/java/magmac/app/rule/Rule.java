package magmac.app.rule;

import magmac.app.node.Node;

import java.util.Optional;

public interface Rule {
    Optional<Node> lex(String input);
}
