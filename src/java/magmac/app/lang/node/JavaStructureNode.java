package magmac.app.lang.node;

import magmac.api.Option;
import magmac.api.collect.list.List;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;

public final class JavaStructureNode implements JavaRootSegment {
    private final StructureType type;
    private final StructureValue value;
    private final Option<List<Parameter>> parameters;
    private final Option<List<Type>> variants;

    public JavaStructureNode(
            StructureType type,
            StructureValue structureNode,
            Option<List<Parameter>> parameters,
            Option<List<Type>> variants
    ) {
        this.type = type;
        this.value = structureNode;
        this.parameters = parameters;
        this.variants = variants;
    }

    @Override
    public Node serialize() {
        return new MapNode();
    }

    public StructureType type() {
        return this.type;
    }

    public String name() {
        return this.value.name();
    }

    public Option<List<Type>> implemented() {
        return this.value.implemented();
    }

    public Option<List<Type>> extended() {
        return this.value.extended();
    }
}
