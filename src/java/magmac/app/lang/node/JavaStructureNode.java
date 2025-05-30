package magmac.app.lang.node;

import magmac.api.Option;
import magmac.api.collect.list.List;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;

public final class JavaStructureNode implements JavaRootSegment {
    private final JavaStructureType type;
    public final StructureValue<JavaStructureMember> value;
    private final Option<List<Parameter>> parameters;
    private final Option<List<Type>> variants;

    public JavaStructureNode(
            JavaStructureType type,
            StructureValue<JavaStructureMember> structureNode,
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

    public JavaStructureType type() {
        return this.type;
    }

    public String name() {
        return this.value.name();
    }

    public Option<List<Type>> implemented() {
        return this.value.maybeImplemented();
    }

    public Option<List<Type>> extended() {
        return this.value.maybeExtended();
    }
}
