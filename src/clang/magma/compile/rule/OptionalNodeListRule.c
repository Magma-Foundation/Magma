#include "OptionalNodeListRule.h"
struct public OptionalNodeListRule(struct String propertyKey, struct Rule ifPresent, struct Rule ifMissing){this.propertyKey = propertyKey;
        this.ifPresent = ifPresent;
        this.ifMissing = ifMissing;
        parseRule = new OrRule(Lists.of(ifPresent, ifMissing));
}
struct Result_Node_CompileError parse(struct String input){return parseRule.parse(input);
}
struct Result_String_CompileError generate(struct Node node){return node.hasNodeList(propertyKey)
                ? ifPresent.generate(node)
                : ifMissing.generate(node);
}

