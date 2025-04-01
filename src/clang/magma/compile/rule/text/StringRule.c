#include "StringRule.h"
auto __lambda0__(){return (CompileError("String '" + propertyKey + "' not present", NodeContext(input)));
}
magma.result.Result<magma.compile.Node, magma.compile.CompileError> parse(String value){return (MapNode().withString(propertyKey(), value));
}
magma.result.Result<String, magma.compile.CompileError> generate(magma.compile.Node input){return input.findString(propertyKey).map(Ok.new).orElseGet(__lambda0__);
}
