package magma.app.compile.define;

import magma.api.collect.Joiner;
import magma.api.collect.list.List;
import magma.api.option.Option;
import magma.api.option.Some;
import magma.api.text.Strings;
import magma.app.Compiler;
import magma.app.compile.type.Type;

public record Definition(
        List<String> annotations,
        List<String> modifiers,
        List<String> typeParams,
        Type type,
        String name
) implements MethodHeader, Parameter {
    public Type findType() {
        return this.type;
    }

    public String toAssignment() {
        return "\n\t\tthis." + this.name + " = " + this.name + ";";
    }

    @Override
    public String generate() {
        return this.generateWithAfterName("");
    }

    @Override
    public Option<Definition> asDefinition() {
        return new Some<Definition>(this);
    }

    @Override
    public String generateWithAfterName(String afterName) {
        var joinedTypeParams = this.joinTypeParams();
        var joinedModifiers = this.modifiers.query()
                .map((String value) -> value + " ")
                .collect(new Joiner(""))
                .orElse("");

        return joinedModifiers + this.type.generateBeforeName() + this.name + joinedTypeParams + afterName + this.generateType();
    }

    private String generateType() {
        if (this.type.isVar()) {
            return "";
        }

        return ": " + this.type.generate();
    }

    private String joinTypeParams() {
        return Compiler.joinTypeParams(this.typeParams);
    }

    @Override
    public boolean hasAnnotation(String annotation) {
        return this.annotations.contains(annotation);
    }

    @Override
    public MethodHeader removeModifier(String modifier) {
        return new Definition(this.annotations, this.modifiers.removeValue(modifier), this.typeParams, this.type, this.name);
    }

    public boolean isNamed(String name) {
        return Strings.equalsTo(this.name, name);
    }
}
