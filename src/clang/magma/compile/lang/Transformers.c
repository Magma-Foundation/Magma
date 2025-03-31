#include "Transformers.h"
auto __lambda0__();
auto __lambda1__();
magma.result.Result<magma.compile.Node, magma.compile.CompileError> find(magma.compile.Node node, String propertyKey){return node.findNode(propertyKey).map(Ok.new).orElseGet(__lambda0__);
}
magma.result.Result<magma.collect.list.List_<magma.compile.Node>, magma.compile.CompileError> findNodeList(magma.compile.Node value, String propertyKey){return value.findNodeList(propertyKey).map(Ok.new).orElseGet(__lambda1__);
}
