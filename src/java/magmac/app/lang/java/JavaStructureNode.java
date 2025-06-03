package magmac.app.lang.java;

import magmac.api.Option;
import magmac.api.collect.list.List;
import magmac.app.lang.node.StructureValue;

public final class JavaStructureNode implements JavaRootSegment, JavaStructureMember {
    private final JavaLang.JavaStructureType type;
    public final StructureValue<JavaLang.JavaType, JavaStructureMember> value;
    private final Option<List<JavaParameter>> parameters;
    private final Option<List<JavaLang.JavaType>> variants;

    public JavaStructureNode(
            JavaLang.JavaStructureType type,
            StructureValue<JavaLang.JavaType, JavaStructureMember> structureNode,
            Option<List<JavaParameter>> parameters,
            Option<List<JavaLang.JavaType>> variants
    ) {
        this.type = type;
        this.value = structureNode;
        this.parameters = parameters;
        this.variants = variants;
    }

    public JavaLang.JavaStructureType type() {
        return this.type;
    }

    public String name() {
        return this.value.name();
    }

    public Option<List<JavaLang.JavaType>> implemented() {
        return this.value.maybeImplemented();
    }

    public Option<List<JavaLang.JavaType>> extended() {
        return this.value.maybeExtended();
    }
}
