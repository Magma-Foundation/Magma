package magma.compile;

import jvm.collect.list.Lists;
import magma.collect.list.List_;
import magma.compile.lang.CLang;
import magma.compile.lang.JavaLang;
import magma.compile.lang.RootTransformer;
import magma.compile.lang.RootUnwrapper;
import magma.compile.transform.GroupTransformer;
import magma.compile.transform.TreeTransformingStage;
import magma.result.Result;

public class Compiler {
    public static Result<String, CompileError> compile(String input, List_<String> namespace, String name) {
        return JavaLang.createJavaRootRule().parse(input)
                .mapValue(tree -> new TreeTransformingStage(new RootTransformer()).transform(tree, namespace))
                .mapValue(tree -> new TreeTransformingStage(new GroupTransformer()).transform(tree, namespace))
                .mapValue(tree -> new TreeTransformingStage(new RootUnwrapper()).transform(tree, namespace))
                .flatMapValue(generate -> CLang.createCRootRule().generate(generate))
                .mapValue(output -> complete(namespace, name, output));
    }

    private static String complete(List_<String> namespace, String name, String output) {
        if (namespace.equals(Lists.of("magma")) && name.equals("Main")) {
            return output + "int main(){\n\treturn 0;\n}\n";
        }

        return output;
    }
}