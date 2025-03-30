package magma.compile;

import jvm.collect.map.Maps;
import magma.collect.list.List_;
import magma.collect.map.Map_;
import magma.compile.lang.CLang;
import magma.compile.lang.FlattenRoot;
import magma.compile.lang.JavaLang;
import magma.compile.lang.Sorter;
import magma.compile.lang.TransformAll;
import magma.compile.transform.FlattenGroup;
import magma.compile.transform.State;
import magma.compile.transform.TreeTransformingStage;
import magma.option.Tuple;
import magma.result.Result;

public class Compiler {
    public static Result<Map_<String, String>, CompileError> compile(String input, List_<String> namespace, String name) {
        State state = new State(namespace, name);
        return JavaLang.createJavaRootRule().parse(input)
                .flatMapValue(tree -> new TreeTransformingStage(new TransformAll()).transform(tree, state))
                .flatMapValue(tree -> new TreeTransformingStage(new FlattenGroup()).transform(tree, state))
                .flatMapValue(tree -> new TreeTransformingStage(new FlattenRoot()).transform(tree, state))
                .flatMapValue(tree -> new TreeTransformingStage(new Sorter()).transform(tree, state))
                .flatMapValue(Compiler::generateRoots);
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