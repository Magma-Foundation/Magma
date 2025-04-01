package magma.compile;

import jvm.collect.map.Maps;
import magma.collect.map.Map_;
import magma.compile.lang.CLang;
import magma.compile.lang.Compacter;
import magma.compile.lang.ExpandGenerics;
import magma.compile.lang.FlattenRoot;
import magma.compile.lang.FlattenStructs;
import magma.compile.lang.Formatter;
import magma.compile.lang.JavaLang;
import magma.compile.lang.PruneTypeParameterized;
import magma.compile.lang.ResolveTypes;
import magma.compile.lang.Sorter;
import magma.compile.lang.TransformAll;
import magma.compile.transform.FlattenGroup;
import magma.compile.transform.State;
import magma.compile.transform.Transformer;
import magma.compile.transform.TreeTransformingStage;
import magma.option.Tuple;
import magma.result.Result;

import java.util.function.Function;

public class Compiler {
    public static Result<Map_<String, String>, CompileError> postLoad(State state, Node tree) {
        return new TreeTransformingStage(new FlattenStructs()).transform(state, tree)
                .flatMapValue(transformUsing(new FlattenGroup()))
                .flatMapValue(transformUsing(new Compacter()))
                .flatMapValue(transformUsing(new Formatter()))
                .flatMapValue(transformUsing(new FlattenRoot()))
                .flatMapValue(transformUsing(new Sorter()))
                .mapValue(Tuple::right)
                .flatMapValue(Compiler::generateRoots);
    }

    public static Result<Node, CompileError> preLoad(String input, State state) {
        return JavaLang.createJavaRootRule().parse(input)
                .mapValue(tree -> new Tuple<>(state, tree))
                .flatMapValue(transformUsing(new ResolveTypes()))
                .flatMapValue(transformUsing(new TransformAll()))
                .flatMapValue(transformUsing(new FlattenGroup()))
                .flatMapValue(transformUsing(new ExpandGenerics()))
                .flatMapValue(transformUsing(new FlattenGroup()))
                .flatMapValue(transformUsing(new PruneTypeParameterized()))
                .mapValue(Tuple::right);
    }

    private static Function<Tuple<State, Node>, Result<Tuple<State, Node>, CompileError>> transformUsing(Transformer transformer) {
        return tree -> new TreeTransformingStage(transformer).transform(tree.left(), tree.right());
    }

    private static Result<Map_<String, String>, CompileError> generateRoots(Node roots) {
        return roots.streamNodes().foldToResult(Maps.empty(), Compiler::generateTarget);
    }

    private static Result<Map_<String, String>, CompileError> generateTarget(Map_<String, String> current, Tuple<String, Node> tuple) {
        String extension = tuple.left();
        Node root = tuple.right();
        return CLang.createCRootRule().generate(root).mapValue(generated -> current.with(extension, generated));
    }
}