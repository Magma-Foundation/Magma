package magmac;

import magmac.app.MapNode;

import java.util.Optional;

public interface Rule {
    Optional<MapNode> lex(String input);
}
