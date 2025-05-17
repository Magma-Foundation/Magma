package magma.app.compile.type;

import jvm.api.text.Strings;
import magma.api.Type;
import magma.api.collect.list.List;
import magma.app.Main;

public record TemplateType(String base, List<String> args) implements Type {
    private static String generateValueStrings(final List<String> values) {
        return Main.generateAll(values, TemplateType::mergeValues);
    }

    private static String mergeValues(final String cache, final String element) {
        if (Strings.isEmpty(cache)) {
            return cache + element;
        }
        return cache + ", " + element;
    }

    @Override
    public String generate() {
        return this.base + "<" + TemplateType.generateValueStrings(this.args) + ">";
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
}
