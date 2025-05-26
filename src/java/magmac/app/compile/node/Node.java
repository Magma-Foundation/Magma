package magmac.app.compile.node;

import java.util.Map;
import java.util.Optional;

public interface Node {
    Node withString(String key, String value);

    Optional<String> findString(String key);

    Node merge(Node other);

    Map<String, String> strings();

    boolean is(String type);

    Node retype(String type);
}
