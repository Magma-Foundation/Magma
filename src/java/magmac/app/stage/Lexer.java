package magmac.app.stage;

import magmac.app.io.Location;

import java.util.Map;

public interface Lexer {
    Roots lexAll(Map<Location, String> values);
}
