package magmac.app.lang.node;

import magmac.app.compile.node.Node;

public final class TypeScriptDefinition implements TypeScriptParameter, Assignable, TypeScriptMethodHeader {
    private final Definition<TypeScriptType> definition;

    public TypeScriptDefinition(Definition<TypeScriptType> definition) {
        this.definition = definition;
    }

    @Override
    public Node serialize() {
        return this.definition.serialize();
    }
}
