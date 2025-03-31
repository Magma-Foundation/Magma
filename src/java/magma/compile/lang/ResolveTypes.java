package magma.compile.lang;

import jvm.collect.list.Lists;
import magma.collect.list.List_;
import magma.compile.CompileError;
import magma.compile.Node;
import magma.compile.transform.State;
import magma.compile.transform.Transformer;
import magma.option.None;
import magma.option.Option;
import magma.option.Some;
import magma.option.Tuple;
import magma.result.Ok;
import magma.result.Result;

public class ResolveTypes implements Transformer {
    private static Option<List_<String>> qualifyName(State state, List_<String> name) {
        if (name.size() != 1) return new Some<>(name);
        return state.qualifyName(name.findFirst().orElse(""));
    }

    private static List_<String> attachNamespaceToName(State state, List_<String> name) {
        String last = name.findLast().orElse("");
        return state.namespace().add(last);
    }

    private static Option<List_<String>> getTupleCompileErrorResult(String oldValue) {
        if (oldValue.equals("boolean")) return new Some<>(Lists.of("int"));
        if (oldValue.equals("int")) return new Some<>(Lists.of("int"));
        return new None<>();
    }

    private static Option<List_<String>> getListOption(List_<String> oldName) {
        String last = oldName.findLast().orElse("");
        if (last.equals("int") || last.equals("Integer")) {
            return new Some<>(Lists.of("int"));
        } else {
            return new None<>();
        }
    }

    @Override
    public Result<Tuple<State, Node>, CompileError> beforePass(State state, Node node) {
        if (node.is("import")) {
            State newState = state.defineImport(node);
            return new Ok<>(new Tuple<>(newState, node));
        }

        if (node.is("qualified")) {
            List_<String> oldName = StringLists.fromQualified(node);
            List_<String> newName = qualifyName(state, oldName)
                    .or(() -> getListOption(oldName))
                    .orElseGet(() -> attachNamespaceToName(state, oldName));

            Node qualifiedNode = StringLists.toQualified(newName);
            return new Ok<>(new Tuple<>(state, qualifiedNode));
        }

        return new Ok<>(new Tuple<>(state, node));
    }

    @Override
    public Result<Tuple<State, Node>, CompileError> afterPass(State state, Node node) {
        if (node.is("root")) {
            return new Ok<>(new Tuple<>(state.clearImports(), node));
        }

        return new Ok<>(new Tuple<>(state, node));
    }
}
