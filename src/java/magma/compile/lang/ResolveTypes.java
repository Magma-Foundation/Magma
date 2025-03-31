package magma.compile.lang;

import jvm.collect.list.Lists;
import magma.collect.list.List_;
import magma.compile.CompileError;
import magma.compile.Node;
import magma.compile.context.NodeContext;
import magma.compile.transform.State;
import magma.compile.transform.Transformer;
import magma.option.None;
import magma.option.Option;
import magma.option.Some;
import magma.option.Tuple;
import magma.result.Err;
import magma.result.Ok;
import magma.result.Result;

public class ResolveTypes implements Transformer {
    private static Option<Result<List_<String>, CompileError>> qualifyName(State state, List_<String> name) {
        if (name.size() == 1) {
            return state.qualifyName(name.findLast().orElse("")).map(Ok::new);
        }
        return new Some<>(new Ok<>(name));
    }

    private static Result<List_<String>, CompileError> attachNamespaceToName(State state, List_<String> name, Node node) {
        return name.findLast()
                .filter(value -> !value.isEmpty())
                .<Result<List_<String>, CompileError>>map(
                        last -> new Ok<>(state.namespace().add(last))).orElseGet(
                        () -> new Err<>(new CompileError("At least one name is required", new NodeContext(node))));
    }

    private static Option<Result<List_<String>, CompileError>> getListOption(List_<String> oldName) {
        String last = oldName.findLast().orElse("");
        if (last.equals("int") || last.equals("Integer")) {
            return new Some<>(new Ok<>(Lists.of("int")));
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
            if (oldName.isEmpty())
                return new Err<>(new CompileError("At least one segment must be present", new NodeContext(node)));

            return qualifyName(state, oldName)
                    .or(() -> getListOption(oldName))
                    .orElseGet(() -> attachNamespaceToName(state, oldName, node))
                    .mapValue(newName -> {
                        Node qualifiedNode = StringLists.toQualified(newName);
                        return new Tuple<>(state, qualifiedNode);
                    });
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
