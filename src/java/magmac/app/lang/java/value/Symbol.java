package magmac.app.lang.java.value;

import magmac.app.lang.java.Type;
import magmac.app.lang.java.type.Base;

public record Symbol(String value) implements Type, Value, Base {
}
