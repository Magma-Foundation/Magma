package magma.app;

import jvm.api.text.Strings;
import magma.api.Type;
import magma.api.collect.list.List;

record Generic(String base, List<String> args) implements Type {
    private static String generateValueStrings(final List<String> values) {
        return Main.generateAll(values, Generic::mergeValues);
    }

    private static String mergeValues(final String cache, final String element) {
        if (Strings.isEmpty(cache)) {
            return cache + element;
        }
        return cache + ", " + element;
    }

    @Override
    public String generate() {
        return this.base + "<" + Generic.generateValueStrings(this.args) + ">";
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
