package jvm;

import jvm.collect.list.Lists;
import magma.collect.list.List_;
import magma.compile.MapNode;
import magma.compile.Node;
import magma.compile.lang.Qualified;
import magma.option.None;
import magma.option.Option;

public class Temp {
    public static List_<Node> walkTopLevel(List_<Node> seen, Node topLevel) {
        if (topLevel.is("root")) {
            /*
            By definition, a root type has no expansions,
            and therefore we can return it.
             */
            return Lists.of(topLevel);
        } else {
            // We know this has to be a group.
            return walkGroup(topLevel, seen);
        }
    }

    private static List_<Node> walkGroup(Node topLevel,  List_<Node> seen) {
        /*
        We walk through all the expansions and collect them.
         */
        return topLevel.findNodeList("expansions")
                .orElse(Lists.empty())
                .stream()
                .filter(node -> !seen.contains(node, Node::equalsTo))
                .foldWithInitial(seen, (seen0, expansion) -> seen0.addAll(walkEnclosedInType(expansion, seen0)));
    }

    private static List_<Node> walkEnclosedInType(Node expansion, List_<Node> seen) {
        // fully expanded, something like first.second.Third

        // This is the base type, so the base of Map<K, V> would be Map.
        Node qualifiedBaseType = expansion.findNode("base").orElse(new MapNode());
        List_<String> listBaseType = Qualified.from(qualifiedBaseType);

        // These are the argument types, so for Map<K, V>, the types would be [ K, V ].
        List_<Node> argumentTypes = expansion.findNodeList("arguments").orElse(Lists.empty());

        /*
        Note that we have to expand groups as well.
        For example, assume that Map<K, V> depends on Option<K>.
        If Map<K, V> were to be Map<String, Node>, then Option<String> would also have to created.

        Therefore, we monomorphize the expansions within the group, since the expansions are just nodes in the AST anyways.

        TODO: something we have to be careful of is of circular expansions.
        For example, Option<T> could depend on itself (actually, it does, since it's a monad).
        */
        return lookupTree(listBaseType)
                .map(group -> monomorphizeGroup(group, argumentTypes))
                .map(topLevel -> walkTopLevel(seen, topLevel))
                .orElse(Lists.empty());
    }

    private static Node monomorphizeGroup(Node rootOrGroup, List_<Node> argumentTypes) {
        /*
        This is a stub.
        If this root or the child of this group doesn't have type parameters, then nothing should happen.
        If this root or the child of this group child does have type parameters, then those type parameters should be inlined using argument types.

        Need to walk through the tree here.
        */
        return rootOrGroup;
    }

    private static Option<Node> lookupTree(List_<String> qualifiedType) {
        // Stub
        // To lookup the root or group of a qualified type. Assume all source files are loaded here.
        return new None<>();
    }
}
