package magma.app.compile.type;

import magma.api.collect.list.List;
import magma.app.Main;

public record TemplateType(String base, List<String> args) implements Type {
    @Override
    public String generate() {
        return this.base + "<" + Main.generateValueStrings(this.args) + ">";
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
