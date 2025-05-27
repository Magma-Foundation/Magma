package magmac.app.compile.rule.result;

import magmac.app.compile.Context;

public record StringContext(String value) implements Context {
    @Override
    public String display() {
        return this.value;
    }
}
