#include "Transformers.h"
int __lambda0__(){return (CompileError("Node '" + propertyKey + "' not present", NodeContext(node)));
}
int __lambda1__(){return (CompileError("Node list '" + propertyKey + "' not present", NodeContext(value)));
}
Result<Node, CompileError> findNode(Node node, String propertyKey){return node.findNode(propertyKey).map(Ok.new).orElseGet(__lambda0__);
}
Result<List_<Node>, CompileError> findNodeList(Node value, String propertyKey){return value.findNodeList(propertyKey).map(Ok.new).orElseGet(__lambda1__);
}
