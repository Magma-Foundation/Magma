package magmac.app;

import java.util.Optional;

public interface Rule {
    Optional<MapNode> lex(String input);
}
