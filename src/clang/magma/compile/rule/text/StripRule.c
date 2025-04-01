#include "StripRule.h"
auto __lambda0__(){return attachPadding;
}
magma.compile.rule.text.public StripRule(magma.compile.rule.Rule childRule){this("", childRule, "");
}
magma.result.Result<magma.compile.Node, magma.compile.CompileError> parse(String input){return childRule().parse(input.strip());
}
magma.result.Result<String, magma.compile.CompileError> generate(magma.compile.Node node){return childRule.generate(node).mapValue(__lambda0__(node, value));
}
String attachPadding(magma.compile.Node node, String value){String leftPad = node.findString(leftKey).orElse("");
        String rightPad = node.findString(rightKey).orElse("");return leftPad+value+rightPad;
}
