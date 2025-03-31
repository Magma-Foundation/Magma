#include "NodeListRule.h"
magma.result.Result<magma.compile.Node, magma.compile.CompileError> parse(String input){List_<String> segments = divider.divide(input);if (segments.isEmpty()) return new Ok<>(new MapNode());return segments.stream().foldToResult(Lists.empty(), __lambda0__().parse(element).mapValue(children.add)).mapValue(__lambda1__.withNodeList(propertyKey(), children)).mapErr(__lambda2__);
}
magma.result.Result<String, magma.compile.CompileError> generate(magma.compile.Node node){return node.findNodeList(propertyKey).map(this.generateChildren).orElseGet(__lambda3__).mapErr(__lambda4__);
}
magma.result.Result<String, magma.compile.CompileError> generateChildren(magma.collect.list.List_<magma.compile.Node> children){return children.stream().foldToResult("", __lambda5__.generate(element).mapValue(__lambda6__.join(current, result)));
}
auto __lambda0__();
auto __lambda1__();
auto __lambda2__();
auto __lambda3__();
auto __lambda4__();
auto __lambda5__();
auto __lambda6__();

