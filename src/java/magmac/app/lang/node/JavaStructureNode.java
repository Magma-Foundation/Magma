package magmac.app.lang.node;

import magmac.api.Option;
import magmac.api.collect.list.List;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;

public final class JavaStructureNode implements JavaRootSegment {
    private final JavaStructureType type;
    public final StructureValue<JavaType, JavaStructureMember> value;
    private final Option<List<JavaParameter>> parameters;
    private final Option<List<JavaType>> variants;

    public JavaStructureNode(
            JavaStructureType type,
            StructureValue<JavaType, JavaStructureMember> structureNode,
            Option<List<JavaParameter>> parameters,
            Option<List<JavaType>> variants
    ) {
        this.type = type;
        this.value = structureNode;
        this.parameters = parameters;
        this.variants = variants;
    }

    public JavaStructureType type() {
        return this.type;
    }

    public String name() {
        return this.value.name();
    }

    public Option<List<JavaType>> implemented() {
        return this.value.maybeImplemented();
    }

    public Option<List<JavaType>> extended() {
        return this.value.maybeExtended();
    }
}
