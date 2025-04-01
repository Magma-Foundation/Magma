#include "ResolveTypes.h"
int __lambda0__(){return !value.isEmpty();
}
int __lambda1__(){return (state.namespace().add(last));
}
int __lambda2__(){return (CompileError("At least one name is required", NodeContext(node)));
}
Option<Result<List_<String>, CompileError>> resolveAsImport(State state, List_<String> name){if (name.size() == 1) {
            return state.qualifyName(name.findLast().orElse("")).map(Ok::new);
        }return ((name));
}
Result<List_<String>, CompileError> resolveAsLocal(State state, List_<String> name, Node node){return name.findLast().filter(__lambda0__).map(__lambda1__).orElseGet(__lambda2__);
}
Option<Result<List_<String>, CompileError>> resolveAsPrimitive(List_<String> oldName){String last = oldName.findLast().orElse("");return resolvePrimitiveString(last).map(Lists.of).map(Ok.new);
}
Option<String> resolvePrimitiveString(String maybePrimitive){if (maybePrimitive.equals("int") || maybePrimitive.equals("Integer") || maybePrimitive.equals("boolean") || maybePrimitive.equals("Boolean")) {
            return new Some<>("int");
        }else if (maybePrimitive.equals("char") || maybePrimitive.equals("Character")) {
            return new Some<>("char");
        }else if (maybePrimitive.equals("String")) {
            return new Some<>("String");
        }return ();
}
Tuple<State, Node> wrapToTuple(State state, List_<String> newName){Node qualifiedNode = Qualified.to(newName);return (state, qualifiedNode);
}
Option<Result<List_<String>, CompileError>> resolveAsTypeParam(State state, List_<String> segments){if (state.isTypeParamDefined(segments.findLast().orElse(""))) {
            return new Some<>(new Ok<>(segments));
        }else {
            return new None<>();
        }
}
Result<Tuple<State, Node>, CompileError> beforePass(State state, Node node){if (node.is("import")) {
            State newState = state.defineImport(node);
            return new Ok<>(new Tuple<>(newState, node));
        }if (node.is("class") || node.is("record") || node.is("interface")) {
            State nodeList = node.findNodeList("type-params")
                    .orElse(Lists.empty())
                    .stream()
                    .foldWithInitial(state.enter(), State::defineType);

            return new Ok<>(new Tuple<>(nodeList, node));
        }if (node.is("qualified")) {
            List_<String> oldName = Qualified.from(node);
            if (oldName.isEmpty())
                return new Err<>(new CompileError("At least one segment must be present", new NodeContext(node)));

            return resolveAsImport(state, oldName)
                    .or(() -> resolveAsPrimitive(oldName))
                    .or(() -> resolveAsTypeParam(state, oldName))
                    .orElseGet(() -> resolveAsLocal(state, oldName, node))
                    .mapValue(newName -> wrapToTuple(state, newName));
        }return ((state, node));
}
Result<Tuple<State, Node>, CompileError> afterPass(State state, Node node){if (node.is("root")) {
            return new Ok<>(new Tuple<>(state.clearImports(), node));
        }if (node.is("class") || node.is("record") || node.is("interface")) {
            return new Ok<>(new Tuple<>(state.exit(), node));
        }return ((state, node));
}
