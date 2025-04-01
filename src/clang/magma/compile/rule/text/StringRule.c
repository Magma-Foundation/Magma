#include "StringRule.h"
int __lambda0__(){return (CompileError("String '" + propertyKey + "' not present", NodeContext(input)));
}
Result<Node, CompileError> parse(String value){return (MapNode().withString(propertyKey(), value));
}
Result<String, CompileError> generate(Node input){return input.findString(propertyKey).map(Ok.new).orElseGet(__lambda0__);
}
