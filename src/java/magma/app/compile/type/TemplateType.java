package magma.app.compile.type;

import magma.api.collect.list.Iterable;
import magma.app.compile.ValueUtils;

public record TemplateType(String base, Iterable<String> args) implements Type {
    @Override
    public String generate() {
        return this.base + "<" + ValueUtils.generateValueStrings(this.args) + ">";
    }

    @Override
    public boolean isFunctional() {
        return false;
    }

    @Override
    public boolean isVar() {
        return false;
    }

    @Override
    public String generateBeforeName() {
        return "";
    }

    @Override
    public String generateSimple() {
        return this.base;
    }
}
