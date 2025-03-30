#include "NodeListRule.h"
Result<struct Node, struct CompileError> parse(struct String input}{List_<String> segments = divider.divide(input);
        if (segments.isEmpty()) return new Ok<>(new MapNode());

        return segments.stream()
                .<List_<Node>, CompileError>foldToResult(Lists.empty(), (children, element) -> childRule().parse(element).mapValue(children::add))
                .mapValue(children -> new MapNode().withNodeList(propertyKey(), children));}Result<struct String, struct CompileError> generate(struct Node node}{return node.findNodeList(propertyKey)
                .map(this::generateChildren)
                .orElseGet(() -> new Err<>(new CompileError( + propertyKey + , new NodeContext(node))));}Result<struct String, struct CompileError> generateChildren(List_<struct Node> children}{return children.stream().foldToResult(, (current, element) -> childRule
                .generate(element)
                .mapValue(result -> divider.join(current, result)));}