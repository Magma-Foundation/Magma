package magma.app.compile.define;

import jvm.api.text.Strings;
import magma.api.Type;
import magma.api.collect.Joiner;
import magma.api.collect.list.List;
import magma.api.option.Option;
import magma.api.option.Some;
import magma.app.io.Platform;

public record Definition(
        List<String> annotations,
        List<String> modifiers,
        List<String> typeParams,
        Type type,
        String name
) implements FunctionHeader<Definition>, Parameter {
    @Override
    public String generate(Platform platform) {
        return this.generateWithAfterName(platform, "");
    }

    @Override
    public Option<Definition> asDefinition() {
        return new Some<Definition>(this);
    }

    @Override
    public String generateWithAfterName(final Platform platform, final String afterName) {
        final String joinedTypeParams = this.joinTypeParams();
        final String joinedModifiers = this.modifiers.query()
                .map((String value) -> value + " ")
                .collect(new Joiner(""))
                .orElse("");

        if (Platform.Windows == platform) {
            return joinedModifiers + this.type.generateBeforeName() + this.type.generate() +  " " + this.name + afterName;
        }
        return joinedModifiers + this.type.generateBeforeName() + this.name + joinedTypeParams + afterName + this.generateType();
    }

    private String generateType() {
        if (this.type.isVar()) {
            return "";
        }

        return ": " + this.type.generate();
    }

    private String joinTypeParams() {
        return Joiner.joinOrEmpty(this.typeParams, ", ", "<", ">");
    }

    @Override
    public boolean hasAnnotation(final String annotation) {
        return this.annotations.contains(annotation, Strings::equalsTo);
    }

    @Override
    public Definition removeModifier(final String modifier) {
        return new Definition(this.annotations, this.modifiers.removeValue(modifier, Strings::equalsTo), this.typeParams, this.type, this.name);
    }

    @Override
    public Definition addModifier(final String modifier) {
        return new Definition(this.annotations, this.modifiers.addFirst(modifier), this.typeParams, this.type, this.name);
    }
}
