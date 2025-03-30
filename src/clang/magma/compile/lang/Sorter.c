#include "Sorter.h"
struct Node asRoot(struct List__Node left){
Node node1 = new MapNode().withNodeList(, left);
        return new MapNode().withNode(, node1);}
struct Result_Node_CompileError afterPass(struct State state, struct Node node){
if (!node.is()) return new Ok<>(node);

        List_<Node> children = node.findNode().orElse(new MapNode())
                .findNodeList().orElse(Lists.empty());

        Tuple<List_<Node>, List_<Node>> tuple = children.stream().foldWithInitial(new Tuple<>(Lists.empty(), Lists.empty()), Sorter::foldIntoBuckets);

        String joined = state.namespace()
                .add(state.name())
                .stream()
                .collect(new Joiner())
                .orElse();

        List_<Node> headerChildren = Lists.<Node>empty()
                .add(new MapNode().withString(, joined))
                .add(new MapNode().withString(, joined))
                .addAll(tuple.left())
                .add(new MapNode());

        List_<Node> sourceChildren = Lists.<Node>empty()
                .add(new MapNode().withNodeList(, Lists.of(new MapNode().withString(, state.name()))))
                .addAll(tuple.right());

        Node separated = new MapNode()
                .withNode(, asRoot(headerChildren))
                .withNode(, asRoot(sourceChildren));

        return new Ok<>(separated);}
struct Tuple_List__Node_List__Node foldIntoBuckets(struct Tuple_List__Node_List__Node tuple, struct Node node){
if (node.is() || node.is() || node.is())
            return new Tuple<>(tuple.left().add(node), tuple.right());if(node.is()) {
            return new Tuple<>(tuple.left().add(node.removeNode()), tuple.right().add(node));
        }

        return new Tuple<>(tuple.left(), tuple.right().add(node));}
struct Result_Node_CompileError beforePass(struct State state, struct Node node){
return new Ok<>(node);}
