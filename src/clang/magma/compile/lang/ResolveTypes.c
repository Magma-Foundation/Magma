#include "ResolveTypes.h"
auto __lambda0__();
auto __lambda1__();
auto __lambda2__();
magma.option.Option<magma.result.Result<magma.collect.list.List_<String>, magma.compile.CompileError>> resolveAsImport(magma.compile.transform.State state, magma.collect.list.List_<String> name){if (name.size() == 1) {
            return state.qualifyName(name.findLast().orElse("")).map(Ok::new);
        }return ((name));
}
magma.result.Result<magma.collect.list.List_<String>, magma.compile.CompileError> resolveAsLocal(magma.compile.transform.State state, magma.collect.list.List_<String> name, magma.compile.Node node){return name.findLast().filter(__lambda0__).map(__lambda1__).orElseGet(__lambda2__);
}
magma.option.Option<magma.result.Result<magma.collect.list.List_<String>, magma.compile.CompileError>> resolveAsPrimitive(magma.collect.list.List_<String> oldName){String last = oldName.findLast().orElse("");return resolvePrimitiveString(last).map(Lists.of).map(Ok.new);
}
magma.option.Option<String> resolvePrimitiveString(String maybePrimitive){if (maybePrimitive.equals("int") || maybePrimitive.equals("Integer") || maybePrimitive.equals("boolean") || maybePrimitive.equals("Boolean")) {
            return new Some<>("int");
        }else if (maybePrimitive.equals("char") || maybePrimitive.equals("Character")) {
            return new Some<>("char");
        }else if (maybePrimitive.equals("String")) {
            return new Some<>("String");
        }return ();
}
magma.option.Tuple<magma.compile.transform.State, magma.compile.Node> wrapToTuple(magma.compile.transform.State state, magma.collect.list.List_<String> newName){Node qualifiedNode = StringLists.toQualified(newName);return (state, qualifiedNode);
}
magma.option.Option<magma.result.Result<magma.collect.list.List_<String>, magma.compile.CompileError>> resolveAsTypeParam(magma.compile.transform.State state, magma.collect.list.List_<String> segments){if (state.isTypeParamDefined(segments.findLast().orElse(""))) {
            return new Some<>(new Ok<>(segments));
        }else {
            return new None<>();
        }
}
magma.result.Result<magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>, magma.compile.CompileError> beforePass(magma.compile.transform.State state, magma.compile.Node node){if (node.is("import")) {
            State newState = state.defineImport(node);
            return new Ok<>(new Tuple<>(newState, node));
        }if (node.is("class") || node.is("record") || node.is("interface")) {
            State nodeList = node.findNodeList("type-params")
                    .orElse(Lists.empty())
                    .stream()
                    .foldWithInitial(state.enter(), State::defineType);

            return new Ok<>(new Tuple<>(nodeList, node));
        }if (node.is("qualified")) {
            List_<String> oldName = StringLists.fromQualifiedType(node);
            if (oldName.isEmpty())
                return new Err<>(new CompileError("At least one segment must be present", new NodeContext(node)));

            return resolveAsImport(state, oldName)
                    .or(() -> resolveAsPrimitive(oldName))
                    .or(() -> resolveAsTypeParam(state, oldName))
                    .orElseGet(() -> resolveAsLocal(state, oldName, node))
                    .mapValue(newName -> wrapToTuple(state, newName));
        }return ((state, node));
}
magma.result.Result<magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>, magma.compile.CompileError> afterPass(magma.compile.transform.State state, magma.compile.Node node){if (node.is("root")) {
            return new Ok<>(new Tuple<>(state.clearImports(), node));
        }if (node.is("class") || node.is("record") || node.is("interface")) {
            return new Ok<>(new Tuple<>(state.exit(), node));
        }return ((state, node));
}
