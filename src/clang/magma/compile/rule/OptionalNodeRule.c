#include "OptionalNodeRule.h"
public OptionalNodeRule(String propertyKey, Rule ifPresent, Rule ifEmpty){this.propertyKey = propertyKey;
        this.ifPresent = ifPresent;
        this.ifEmpty = ifEmpty;
        this.rule = new OrRule(Lists.of(ifPresent, ifEmpty));
}
Result<Node, CompileError> parse(String input){return rule.parse(input);
}
Result<String, CompileError> generate(Node node){return node.hasNode(propertyKey)?ifPresent.generate(node):ifEmpty.generate(node);
}
