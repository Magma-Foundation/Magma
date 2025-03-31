#include "OptionalNodeListRule.h"
magma.compile.rule.public OptionalNodeListRule(String propertyKey, magma.compile.rule.Rule ifPresent, magma.compile.rule.Rule ifMissing){this.propertyKey = propertyKey;
        this.ifPresent = ifPresent;
        this.ifMissing = ifMissing;
        parseRule = new OrRule(Lists.of(ifPresent, ifMissing));
}
magma.result.Result<magma.compile.Node, magma.compile.CompileError> parse(String input){return parseRule.parse(input);
}
magma.result.Result<String, magma.compile.CompileError> generate(magma.compile.Node node){return node.hasNodeList(propertyKey)?ifPresent.generate(node):ifMissing.generate(node);
}

