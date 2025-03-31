#include "OptionalNodeRule.h"
magma.compile.rule.public OptionalNodeRule(String propertyKey, magma.compile.rule.Rule ifPresent, magma.compile.rule.Rule ifEmpty){this.propertyKey = propertyKey;
        this.ifPresent = ifPresent;
        this.ifEmpty = ifEmpty;
        this.rule = new OrRule(Lists.of(ifPresent, ifEmpty));
}
magma.result.Result<magma.compile.Node, magma.compile.CompileError> parse(String input){return rule.parse(input);
}
magma.result.Result<String, magma.compile.CompileError> generate(magma.compile.Node node){return node.hasNode(propertyKey)?ifPresent.generate(node):ifEmpty.generate(node);
}
