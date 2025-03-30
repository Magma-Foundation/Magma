#include "ExpandGenerics.h"
struct Result_Node_CompileError beforePass(struct State state, struct Node node){if (!node.is()) return new Ok<>(node);

        Node type = node.findNode().orElse(new MapNode());
        if (!type.is()) return new Ok<>(node);

        String value = type.findNodeList()
                .orElse(Lists.empty())
                .get(0)
                .findString()
                .orElse();if (value.equals()) {
            List_<Node> arguments = type.findNodeList().orElseGet(Lists::empty);
            Node param = arguments.get(0);
            Node returns = arguments.get(1);

            return new Ok<>(node.retype()
                    .removeNode()
                    .withNode(, returns)
                    .withNodeList(, Lists.of(param)));
        }if(value.equals()) {
            List_<Node> arguments = type.findNodeList().orElseGet(Lists::empty);
            Node returns = arguments.get(0);

            return new Ok<>(node.retype()
                    .removeNode()
                    .withNode(, returns));
        }

        return new Ok<>(node);
}
struct Result_Node_CompileError afterPass(struct State state, struct Node node){if(node.is()) {
            String value = stringify(node);

            Node symbol = new MapNode().withString(, value);
            Node expansion = new MapNode()
                    .withString(, value)
                    .withNode(, node);

            return new Ok<>(new MapNode()
                    .withNode(, symbol)
                    .withNodeList(, Lists.of(expansion)));
        }

        return new Ok<>(node);
}
struct String stringify(struct Node node){if (node.is()) {
            String caller = node.findNodeList()
                    .orElse(Lists.empty())
                    .stream()
                    .map(element -> element.findString())
                    .flatMap(Streams::fromOption)
                    .collect(new Joiner())
                    .orElse();

            return node.findNodeList().orElse(new JavaList<>())
                    .stream()
                    .map(this::stringify)
                    .collect(new Joiner())
                    .map(arguments -> caller +  + arguments)
                    .orElse(caller);
        }

        return node.findString()
                .or(() -> node.findNode().orElse(new MapNode()).findString())
                .orElse();
}

