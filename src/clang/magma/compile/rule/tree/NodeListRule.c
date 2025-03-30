#include "NodeListRule.h"
struct Result_Node_CompileError parse(struct String input){List_<String> segments = divider.divide(input);if (segments.isEmpty()) return new Ok<>(new MapNode());return segments.stream().foldToResult(Lists.empty(), __lambda0__).mapValue(__lambda1__).mapErr(__lambda2__);
}
struct Result_String_CompileError generate(struct Node node){return node.findNodeList(propertyKey).map(this.generateChildren).orElseGet(__lambda3__).mapErr(__lambda4__);
}
struct Result_String_CompileError generateChildren(struct List__Node children){return children.stream().foldToResult("", __lambda6__);
}
auto __lambda0__();
auto __lambda1__();
auto __lambda2__();
auto __lambda3__();
auto __lambda4__();
auto __lambda6__();

