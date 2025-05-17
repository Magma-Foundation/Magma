package magma.app.compile.define;

import jvm.api.collect.list.Lists;
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
    public Definition(final Type type, final String name) {
        this(Lists.empty(), Lists.empty(), Lists.empty(), type, name);
    }

    @Override
    public String generate(final Platform platform) {
        return this.generateWithAfterName(platform, "");
    }

    @Override
    public Option<Definition> asDefinition() {
        return new Some<Definition>(this);
    }

    private String generateWithAfterName(final Platform platform, final String afterName) {
        final String joinedTypeParams = this.joinTypeParams();
        final String joinedModifiers = this.joinModifiers();

        if (Platform.Windows == platform) {
            return joinedModifiers + this.type.generateBeforeName() + this.type.generate() + " " + this.name + afterName;
        }

        return joinedModifiers + this.type.generateBeforeName() + this.name + joinedTypeParams + afterName + this.generateType();
    }

    private String joinModifiers() {
        return this.modifiers.query()
                .map((String value) -> value + " ")
                .collect(new Joiner(""))
                .orElse("");
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
    public Definition addModifierLast(final String modifier) {
        return new Definition(this.annotations, this.modifiers.addLast(modifier), this.typeParams, this.type, this.name);
    }

    @Override
    public String generateWithDefinitions(final Platform platform, final List<Definition> definitions) {
        final String joinedDefinitions = definitions.query()
                .map((Definition definition) -> definition.generate(platform))
                .collect(new Joiner(", "))
                .orElse("");

        return this.generateWithAfterName(platform, "(" + joinedDefinitions + ")");
    }
}
