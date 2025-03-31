#include "NodeRule.h"
auto __lambda0__();
auto __lambda1__();
auto __lambda2__();
magma.result.Result<magma.compile.Node, magma.compile.CompileError> parse(String input){return childRule.parse(input).mapValue(__lambda0__.withNode(propertyKey, node)).mapErr(__lambda1__);
}
magma.result.Result<String, magma.compile.CompileError> generate(magma.compile.Node node){return node.findNode(propertyKey).map(childRule.generate).orElseGet(__lambda2__);
}
