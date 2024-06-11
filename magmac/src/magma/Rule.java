package magma;

import java.util.Optional;

public interface Rule {
    Optional<Node> toNode(String content);
}
