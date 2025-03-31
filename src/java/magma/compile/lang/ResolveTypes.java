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
    private static Option<Result<List_<String>, CompileError>> resolveAsImport(State state, List_<String> name) {
        if (name.size() == 1) {
            return state.qualifyName(name.findLast().orElse("")).map(Ok::new);
        }
        return new Some<>(new Ok<>(name));
    }

    private static Result<List_<String>, CompileError> resolveAsLocal(State state, List_<String> name, Node node) {
        return name.findLast()
                .filter(value -> !value.isEmpty())
                .<Result<List_<String>, CompileError>>map(
                        last -> new Ok<>(state.namespace().add(last))).orElseGet(
                        () -> new Err<>(new CompileError("At least one name is required", new NodeContext(node))));
    }

    private static Option<Result<List_<String>, CompileError>> resolveAsPrimitive(List_<String> oldName) {
        String last = oldName.findLast().orElse("");
        return resolvePrimitiveString(last).map(Lists::of).map(Ok::new);
    }

    private static Option<String> resolvePrimitiveString(String maybePrimitive) {
        if (maybePrimitive.equals("int") || maybePrimitive.equals("Integer")) return new Some<>("int");
        if (maybePrimitive.equals("char") || maybePrimitive.equals("Character")) return new Some<>("char");
        if (maybePrimitive.equals("String")) return new Some<>("String");
        return new None<>();
    }

    private static Tuple<State, Node> wrapToTuple(State state, List_<String> newName) {
        Node qualifiedNode = StringLists.toQualified(newName);
        return new Tuple<>(state, qualifiedNode);
    }

    private static Option<Result<List_<String>, CompileError>> resolveAsTypeParam(State state, List_<String> segments) {
        if (state.isTypeParamDefined(segments.findLast().orElse(""))) {
            return new Some<>(new Ok<>(segments));
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

        if (node.is("class") || node.is("record") || node.is("interface")) {
            State nodeList = node.findNodeList("type-params")
                    .orElse(Lists.empty())
                    .stream()
                    .foldWithInitial(state.enter(), State::defineType);

            return new Ok<>(new Tuple<>(nodeList, node));
        }

        if (node.is("qualified")) {
            List_<String> oldName = StringLists.fromQualified(node);
            if (oldName.isEmpty())
                return new Err<>(new CompileError("At least one segment must be present", new NodeContext(node)));

            return resolveAsImport(state, oldName)
                    .or(() -> resolveAsPrimitive(oldName))
                    .or(() -> resolveAsTypeParam(state, oldName))
                    .orElseGet(() -> resolveAsLocal(state, oldName, node))
                    .mapValue(newName -> wrapToTuple(state, newName));
        }

        return new Ok<>(new Tuple<>(state, node));
    }

    @Override
    public Result<Tuple<State, Node>, CompileError> afterPass(State state, Node node) {
        if (node.is("root")) {
            return new Ok<>(new Tuple<>(state.clearImports(), node));
        }

        if (node.is("class") || node.is("record") || node.is("interface")) {
            return new Ok<>(new Tuple<>(state.exit(), node));
        }

        return new Ok<>(new Tuple<>(state, node));
    }
}
