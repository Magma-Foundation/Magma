#include "ExpandGenerics.h"
struct Result_Node_CompileError beforePass(struct State state, struct Node node){if (!node.is("definition")) return new Ok<>(node);

        Node type = node.findNode("type").orElse(new MapNode());if (!type.is("generic")) return new Ok<>(node);

        String value = type.findNodeList("base")
                .orElse(Lists.empty())
                .get(0)
                .findString("value")
                .orElse("");if (value.equals("Function")) {
            List_<Node> arguments = type.findNodeList("arguments").orElseGet(Lists::empty);
            Node param = arguments.get(0);
            Node returns = arguments.get(1);

            return new Ok<>(node.retype("functional-definition")
                    .removeNode("type")
                    .withNode("return", returns)
                    .withNodeList("params", Lists.of(param)));
        }if(value.equals("Supplier")) {
            List_<Node> arguments = type.findNodeList("arguments").orElseGet(Lists::empty);
            Node returns = arguments.get(0);

            return new Ok<>(node.retype("functional-definition")
                    .removeNode("type")
                    .withNode("return", returns));
        }return Ok_(node);
}
struct Result_Node_CompileError afterPass(struct State state, struct Node node){if(node.is("generic")) {
            String value = stringify(node);

            Node symbol = new MapNode("symbol-type").withString("value", value);
            Node expansion = new MapNode("expansion")
                    .withString("name", value)
                    .withNode("type", node);

            return new Ok<>(new MapNode("group")
                    .withNode("child", symbol)
                    .withNodeList("expansions", Lists.of(expansion)));
        }if (node.is("construction")) {
            Node type = node.findNode("type").orElse(new MapNode());
            return new Ok<>(node.retype("invocation")
                    .withNode("caller", new MapNode("symbol-value").withString("value", stringify(type))));
        }return Ok_(node);
}
struct String stringify(struct Node node){if (node.is("generic")) {
            String caller = node.findNodeList("base")
                    .orElse(Lists.empty())
                    .stream()
                    .map(element -> element.findString("value"))
                    .flatMap(Streams::fromOption)
                    .collect(new Joiner("_"))
                    .orElse("");

            return node.findNodeList("arguments").orElse(new JavaList<>())
                    .stream()
                    .map(this::stringify)
                    .collect(new Joiner("_"))
                    .map(arguments -> caller + "_" + arguments)
                    .orElse(caller);
        }return node.findString("value").or(__lambda0__).orElse("?");
}
auto __lambda0__();

