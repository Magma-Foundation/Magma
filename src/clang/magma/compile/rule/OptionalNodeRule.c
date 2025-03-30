#include "OptionalNodeRule.h"
// expand Result_Node_CompileError = Result<struct Node, struct CompileError>
// expand Result_String_CompileError = Result<struct String, struct CompileError>
struct public OptionalNodeRule(struct String propertyKey, struct Rule ifPresent, struct Rule ifEmpty){this.propertyKey = propertyKey;
        this.ifPresent = ifPresent;
        this.ifEmpty = ifEmpty;
        this.rule = new OrRule(Lists.of(ifPresent, ifEmpty));}struct Result_Node_CompileError parse(struct String input){return rule.parse(input);}struct Result_String_CompileError generate(struct Node node){return node.hasNode(propertyKey)
                ? ifPresent.generate(node)
                : ifEmpty.generate(node);}