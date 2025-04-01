#include "StripRule.h"
int __lambda0__(){return attachPadding;
}
public StripRule(Rule childRule){this("", childRule, "");
}
Result<Node, CompileError> parse(String input){return childRule().parse(input.strip());
}
Result<String, CompileError> generate(Node node){return childRule.generate(node).mapValue(__lambda0__(node, value));
}
String attachPadding(Node node, String value){String leftPad = node.findString(leftKey).orElse("");
        String rightPad = node.findString(rightKey).orElse("");return leftPad+value+rightPad;
}
