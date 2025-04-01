#include "Sorter.h"
auto __lambda0__(){return (state, value);
}
auto __lambda1__(){return (state, value);
}
magma.compile.Node asRoot(magma.collect.list.List_<magma.compile.Node> left){Node node1 = new MapNode("block").withNodeList("children", left);return MapNode("root").withNode("content", node1);
}
magma.result.Result<magma.compile.Node, magma.compile.CompileError> afterPass0(magma.compile.transform.State state, magma.compile.Node node){if (!node.is("root")) return new Ok<>(node);

        List_<Node> children = node.findNode("content").orElse(new MapNode())
                .findNodeList("children").orElse(Lists.empty());

        Tuple<List_<Node>, List_<Node>> tuple = children.stream().foldWithInitial(new Tuple<>(Lists.empty(), Lists.empty()), Sorter::foldIntoBuckets);

        String joined = state.namespace()
                .add(state.name())
                .stream()
                .collect(new Joiner("_"))
                .orElse("");

        List_<Node> headerChildren = Lists.<Node>empty()
                .add(new MapNode("ifndef").withString("value", joined))
                .add(new MapNode("define").withString("value", joined))
                .addAll(tuple.left())
                .add(new MapNode("endif"));

        List_<Node> sourceChildren = Lists.<Node>empty()
                .add(new MapNode("include").withNodeList("path", Lists.of(new MapNode().withString("value", state.name()))))
                .addAll(tuple.right());

        Node separated = new MapNode()
                .withNode(".h", asRoot(headerChildren))
                .withNode(".c", asRoot(sourceChildren));return (separated);
}
magma.option.Tuple<magma.collect.list.List_<magma.compile.Node>, magma.collect.list.List_<magma.compile.Node>> foldIntoBuckets(magma.option.Tuple<magma.collect.list.List_<magma.compile.Node>, magma.collect.list.List_<magma.compile.Node>> tuple, magma.compile.Node node){if (node.is("include") || node.is("struct") || node.is("expansion"))
            return new Tuple<>(tuple.left().add(node), tuple.right());if(node.is("function")) {
            return new Tuple<>(tuple.left().add(node.removeNode("content")), tuple.right().add(node));
        }return (tuple.left(), tuple.right().add(node));
}
magma.result.Result<magma.compile.Node, magma.compile.CompileError> beforePass0(magma.compile.transform.State state, magma.compile.Node node){return (node);
}
magma.result.Result<magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>, magma.compile.CompileError> beforePass(magma.compile.transform.State state, magma.compile.Node node){return beforePass0(state, node).mapValue(__lambda0__);
}
magma.result.Result<magma.option.Tuple<magma.compile.transform.State, magma.compile.Node>, magma.compile.CompileError> afterPass(magma.compile.transform.State state, magma.compile.Node node){return afterPass0(state, node).mapValue(__lambda1__);
}
