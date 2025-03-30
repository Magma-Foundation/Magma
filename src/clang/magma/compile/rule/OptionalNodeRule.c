#include "OptionalNodeRule.h"
struct public OptionalNodeRule(struct String propertyKey, struct Rule ifPresent, struct Rule ifEmpty}{this.propertyKey = propertyKey;
        this.ifPresent = ifPresent;
        this.ifEmpty = ifEmpty;
        this.rule = new OrRule(Lists.of(ifPresent, ifEmpty));}Result<struct Node, struct CompileError> parse(struct String input}{return rule.parse(input);}Result<struct String, struct CompileError> generate(struct Node node}{return node.hasNode(propertyKey)
                ? ifPresent.generate(node)
                : ifEmpty.generate(node);}