package magma.app.compile.value;

import magma.api.collect.Joiner;
import magma.api.collect.list.List;
import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;
import magma.app.Main;
import magma.app.compile.CompileState;
import magma.app.compile.define.Definition;
import magma.app.compile.type.Type;

public record Lambda(List<Definition> paramNames, String content) implements Value {
    @Override
    public String generate() {
        var joinedParamNames = this.paramNames.query()
                .map((Definition definition) -> definition.generate())
                .collect(new Joiner(", "))
                .orElse("");

        return "(" + joinedParamNames + ")" + " => " + this.content;
    }

    @Override
    public Option<Value> toValue() {
        return new Some<Value>(this);
    }

    @Override
    public Option<Value> findChild() {
        return new None<Value>();
    }

    public Type resolve(CompileState state) {
        return Main.Primitive.Unknown;
    }

    @Override
    public Option<String> generateAsEnumValue(String structureName) {
        return new None<String>();
    }
}
