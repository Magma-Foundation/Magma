package magmac.app.lang.node;

/**
 * The label that introduces a {@code switch} branch. In code it appears as
 * {@code case name:} or with a type like {@code case name(Type)}.
 */

import magmac.api.Option;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.lang.Destructors;
import magmac.app.lang.java.JavaDeserializers;
import magmac.app.lang.java.JavaLang;

public record CaseDefinition(String name, Option<JavaLang.JavaType> type) {
    public static CompileResult<CaseDefinition> deserialize(Node node) {
        return Destructors.destruct(node)
                .withString("name")
                .withNodeOptionally("type", JavaDeserializers::deserializeType)
                .complete(tuple -> new CaseDefinition(tuple.left(), tuple.right()));
    }
}
