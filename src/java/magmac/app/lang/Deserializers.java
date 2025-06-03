package magmac.app.lang;

import magmac.api.Option;
import magmac.api.collect.list.List;
import magmac.api.collect.list.Lists;
import magmac.api.iter.Iters;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.error.CompileResults;
import magmac.app.compile.error.context.NodeContext;
import magmac.app.compile.error.error.CompileError;
import magmac.app.compile.node.InitialDestructor;
import magmac.app.compile.node.Node;
import magmac.app.error.ImmutableCompileError;
import magmac.app.lang.common.Annotation;
import magmac.app.lang.java.JavaLang;
import magmac.app.lang.node.Modifier;
import magmac.app.lang.node.TypedDeserializer;
import magmac.app.lang.web.TypescriptLang;

public final class Deserializers {
    public static <T> CompileResult<T> orError(String type, Node node, List<TypedDeserializer<T>> deserializers) {
        return Deserializers.or(node, deserializers)
                .map(result -> result.mapErr(err -> Deserializers.wrap(type, node, err)))
                .orElseGet(() -> CompileResults.NodeErr("Cannot deserialize of type '" + type + "'", node));
    }

    private static CompileError wrap(String type, Node node, CompileError err) {
        return new ImmutableCompileError("Invalid type '" + type + "'", new NodeContext(node), Lists.of(err));
    }

    public static <T> Option<CompileResult<T>> or(Node node, List<TypedDeserializer<T>> deserializers) {
        return deserializers.iter()
                .map(deserializer -> deserializer.deserialize(node))
                .flatMap(Iters::fromOption)
                .next();
    }

    public static <T extends R, R> TypedDeserializer<R> wrap(TypedDeserializer<T> deserializer) {
        return node -> deserializer.deserialize(node).map(result -> result.mapValue(value -> value));
    }

    public static CompileResult<CommonLang.Definition<JavaLang.JavaType>> deserialize0(InitialDestructor deserialize) {
        return deserialize.withString("name")
                .withNode("type", JavaLang.JavaTypes::deserialize)
                .withNodeList("modifiers", Modifier::deserialize)
                .withNodeListOptionally("annotations", Annotation::deserialize)
                .withNodeListOptionally("type-parameters", TypescriptLang.TypeParam::deserialize)
                .complete((result) -> new CommonLang.Definition<JavaLang.JavaType>(result.left().right(), result.left().left().right(), result.left().left().left().left(), result.right(), result.left().left().left().right()));
    }
}