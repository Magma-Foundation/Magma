package magma.app.compile.value;

import magma.api.Type;
import magma.api.collect.Joiner;
import magma.api.collect.list.List;
import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;
import magma.app.compile.CompileState;
import magma.app.compile.define.Definition;
import magma.app.compile.type.PrimitiveType;

public record LambdaNode(List<Definition> paramNames, String content) implements Value {
    @Override
    public String generate() {
        final String joinedParamNames = this.paramNames.query()
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

    public Type resolve(final CompileState state) {
        return PrimitiveType.Unknown;
    }

    @Override
    public Option<String> generateAsEnumValue(final String structureName) {
        return new None<String>();
    }
}
