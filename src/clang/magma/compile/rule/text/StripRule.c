#include "StripRule.h"
struct public StripRule(struct Rule childRule){this("", childRule, "");
}
struct Result_Node_CompileError parse(struct String input){return childRule().parse(input.strip());
}
struct Result_String_CompileError generate(struct Node node){return childRule.generate(node).mapValue(__lambda0__);
}
struct String attachPadding(struct Node node, struct String value){String leftPad = node.findString(leftKey).orElse("");
        String rightPad = node.findString(rightKey).orElse("");return leftPad+value+rightPad;
}
auto __lambda0__();

