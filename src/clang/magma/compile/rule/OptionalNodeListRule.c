#include "OptionalNodeListRule.h"
public OptionalNodeListRule(String propertyKey, Rule ifPresent, Rule ifMissing){this.propertyKey = propertyKey;
        this.ifPresent = ifPresent;
        this.ifMissing = ifMissing;
        parseRule = new OrRule(Lists.of(ifPresent, ifMissing));
}
Result<Node, CompileError> parse(String input){return parseRule.parse(input);
}
Result<String, CompileError> generate(Node node){return node.hasNodeList(propertyKey)?ifPresent.generate(node):ifMissing.generate(node);
}
